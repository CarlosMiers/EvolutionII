/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.BuscadorImpresora;
import Clases.Config;
import Clases.Parametros;
import Clases.TablaCobros;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import DAO.cajaDAO;
import DAO.clienteDAO;
import DAO.cobradorDAO;
import DAO.cobranzaDAO;
import DAO.configuracionDAO;
import DAO.monedaDAO;
import DAO.sucursalDAO;
import DAO.usuarioDAO;
import Modelo.Tablas;
import Modelo.caja;
import Modelo.cliente;
import Modelo.cobrador;
import Modelo.cobranza;
import Modelo.configuracion;
import Modelo.moneda;
import Modelo.sucursal;
import Modelo.usuario;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class detalle_cobranzas_pb extends javax.swing.JFrame {

    /**
     * Creates new form cobranzas
     */
    String cDescuentos, cValorActual = null;
    double nDescuentos, sumatoria, sumtotal = 0.00;

    Conexion con = null;
    Statement stm = null;
    TablaCobros modelo = new TablaCobros();
    Tablas modelo2 = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelocaja = new Tablas();
    Tablas modelocobrador = new Tablas();
    Date dEmision;
    Date dVence;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltromoneda, trsfiltrocaja, trsfiltrocobrador;

    JScrollPane scroll = new JScrollPane();
    ResultSet results = null;
    BDConexion BD = new BDConexion();
    String Operacion = null;
    ObtenerFecha ODate = new ObtenerFecha();
    String Sucursal = null;
    String Cobrador = null;
    String Moneda = null;
    int cCuota = 1;
    int nFila = 0;
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconosql = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconoaceptar = new ImageIcon("src/Iconos/aceptar.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/cancelar.png");

    public detalle_cobranzas_pb(String cOpcion) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.buscarCaja.setIcon(iconobuscar);
        this.buscarCobrador.setIcon(iconobuscar);
        this.buscarMoneda.setIcon(iconobuscar);
        this.BuscarCliente.setIcon(iconobuscar);
        this.buscarSucursal.setIcon(iconobuscar);
        this.MostrarCuentas.setIcon(iconosql);
        this.GrabarCobro.setIcon(iconoaceptar);
        this.Printer.setVisible(false);
        this.Salir.setIcon(iconosalir);
        this.totalamortizacion.setHorizontalAlignment(JTextField.RIGHT);
        this.interesordinario.setHorizontalAlignment(JTextField.RIGHT);
        this.interesvencido.setHorizontalAlignment(JTextField.RIGHT);
        this.interesavencer.setHorizontalAlignment(JTextField.RIGHT);
        this.totalbruto.setHorizontalAlignment(JTextField.RIGHT);
        this.descuentoxpagos.setHorizontalAlignment(JTextField.RIGHT);
        this.gastosxcobrador.setHorizontalAlignment(JTextField.RIGHT);
        this.totalpago.setHorizontalAlignment(JTextField.RIGHT);
        this.cliente.setHorizontalAlignment(JTextField.RIGHT);
        this.cotizacion.setHorizontalAlignment(JTextField.RIGHT);
        this.totalamortizacion.setHorizontalAlignment(JTextField.RIGHT);
        this.Modo.setVisible(false);
        this.idControl.setVisible(false);
        Calendar c2 = new GregorianCalendar();
        this.sucursal.requestFocus();
        if (cOpcion == "new") {
            this.Modo.setText(cOpcion);
            this.idControl.setText("");
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            this.fecha.setCalendar(c2);
            this.emisioncheque.setCalendar(c2);
            this.emisiontarjeta.setCalendar(c2);

            this.totalamortizacion.setText("0");
            this.interesordinario.setText("0");
            this.interesvencido.setText("0");
            this.interesavencer.setText("0");
            this.totalbruto.setText("0");
            this.descuentoxpagos.setText("0");
            this.gastosxcobrador.setText("0");
            this.totalpago.setText("0");
            this.pagotarjeta.setText("0");

            this.cobroefectivo.setText("0");
            this.cobrocheque.setText("0");
            this.cobroefectivo.setText("0");
            this.totalbrutoapagar.setText("0");
            this.cobrador.setText("0");
            this.nombrecobrador.setText("");
            this.cliente.setText("0");
            this.nombrecliente.setText("");
            this.ruc.setText("");
            this.direccion.setText("");

        } else {
            this.idControl.setText(cOpcion);
        }

        this.Titulo();
        this.TitSuc();
        this.TitClie();
        this.TitMoneda();
        this.TitCaja();
        this.TitCobrador();

        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();

        configuracionDAO configDAO = new configuracionDAO();
        configuracion configinicial = configDAO.consultar();
        this.sucursal.setText(String.valueOf(configinicial.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(configinicial.getSucursaldefecto().getNombre());
        this.moneda.setText(String.valueOf(configinicial.getMonedadefecto().getCodigo()));
        this.nombremoneda.setText(configinicial.getMonedadefecto().getNombre());
        this.cotizacion.setText(formato.format(configinicial.getMonedadefecto().getVenta()));
        this.caja.setText(String.valueOf(configinicial.getCajadefecto().getCodigo()));
        this.nombrecaja.setText(configinicial.getCajadefecto().getNombre());
        this.nrorecibo.setText(formato.format(configinicial.getCajadefecto().getRecibo() + 1));
    }

    private void Titulo() {
        modelo.addColumn("Ref.");
        modelo.addColumn("N° Op.");
        modelo.addColumn("Emisión");
        modelo.addColumn("Vence");
        modelo.addColumn("Tipo");
        modelo.addColumn("Nº");
        modelo.addColumn("Amortización");
        modelo.addColumn("Int. Ord.");
        modelo.addColumn("Saldo Cuota");
        modelo.addColumn("Int. Mora");
        modelo.addColumn("Gastos");
        modelo.addColumn("Total a Pagar");
        modelo.addColumn("Su Pago");
        modelo.addColumn("Capital");
        modelo.addColumn("Inversor");
        modelo.addColumn("Cuotas");
        modelo.addColumn("Cód.");
        modelo.addColumn("Mora");
        modelo.addColumn("IVA Int.");
        modelo.addColumn("Int. Punitorio");
        int[] anchos = {5, 120, 120, 120, 40, 40, 80, 80, 80, 80, 80, 80, 80, 80, 80, 50, 30, 50, 80, 80};
        //           1  2    3    4    5  6    7   8  9   10  11  12  13  14  15  16  17  18  19  20   
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 8));

        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(13).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(16).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(17).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
        jTable1.getColumnModel().getColumn(19).setCellRenderer(TablaRenderer);

    }

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");
        modelocliente.addColumn("Dirección");
        modelocliente.addColumn("RUC");

        int[] anchos = {90, 150, 100, 100};
        for (int i = 0; i < modelocliente.getColumnCount(); i++) {
            tablacliente.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacliente.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacliente.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacliente.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacliente.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void sumarcobros() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 

        double amortizacion = 0.00;
        double supago = 0.00;
        double sumpago = 0.00;
        double sumamortizacion = 0.00;
        double totalinteresordinario = 0.00;
        double suminteresordinario = 0.00;
        double interesmoratorio = 0.00;
        double gastos = 0.00;
        double totalcobro = 0.00;
        double ninteresvencido = 0.00;
        double ninteresavencer = 0.00;
        double nDiaConteo = 0.00;
        String cinteresvencido = "";
        String cinteresavencer = "";
        String cDiaConteo = "";

        String camortizacion = "";
        String csupago = "";
        String cinteresordinario = "";
        String cinteresmoratorio = "";
        String cgastos = "";
        String ctotalcobro = "";
        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CHEQUES
            cDiaConteo = String.valueOf(modelo.getValueAt(i, 17));
            cDiaConteo = cDiaConteo.replace(".", "");
            cDiaConteo = cDiaConteo.replace(",", ".");
            nDiaConteo = Double.parseDouble(String.valueOf(cDiaConteo));

            csupago = String.valueOf(modelo.getValueAt(i, 12));
            csupago = csupago.replace(".", "");
            csupago = csupago.replace(",", ".");
            supago = Double.parseDouble(String.valueOf(csupago));
            sumpago += supago;

            if (supago != 0) {
                camortizacion = String.valueOf(modelo.getValueAt(i, 6));
                camortizacion = camortizacion.replace(".", "");
                camortizacion = camortizacion.replace(",", ".");
                amortizacion = Double.parseDouble(String.valueOf(camortizacion));
                sumamortizacion += amortizacion;
                //SUMA EL TOTAL DE DESCUENTOS

                cinteresordinario = String.valueOf(modelo.getValueAt(i, 7));
                cinteresordinario = cinteresordinario.replace(".", "");
                cinteresordinario = cinteresordinario.replace(",", ".");
                totalinteresordinario = Double.parseDouble(String.valueOf(cinteresordinario));
                suminteresordinario += totalinteresordinario;

                if (nDiaConteo >= 0) {
                    ninteresvencido += totalinteresordinario;
                }
                if (nDiaConteo < 0) {
                    ninteresavencer += totalinteresordinario;
                }
            }
        }
        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.interesavencer.setText(formato.format(ninteresavencer));
        this.interesvencido.setText(formato.format(ninteresvencido));
        this.totalamortizacion.setText(formato.format(sumamortizacion));
        this.interesordinario.setText(formato.format(suminteresordinario));
        this.totalbruto.setText(formato.format(sumpago));
        this.totalpago.setText(formato.format(sumpago));
        //formato.format(sumatoria1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PagoParcial = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        nrodocumento = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        concepto = new javax.swing.JTextField();
        importe_a_pagar = new javax.swing.JFormattedTextField();
        importe_pagado = new javax.swing.JFormattedTextField();
        nrocuota = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        GrabarPagoParcial = new javax.swing.JButton();
        SalirPagoParcial = new javax.swing.JButton();
        ingresar_cobros = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        totalbrutoapagar = new javax.swing.JFormattedTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        cobroefectivo = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        cobrocheque = new javax.swing.JFormattedTextField();
        cargobanco = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        nrocheque = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        emisioncheque = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        pagotarjeta = new javax.swing.JFormattedTextField();
        cargoemisor = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        nrotarjeta = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        emisiontarjeta = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        AceptarCobro = new javax.swing.JButton();
        SalirFormaCobro = new javax.swing.JButton();
        BSucursal = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BCliente = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        BuscarMoneda = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        BCaja = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        combocaja = new javax.swing.JComboBox();
        jTBuscarCaja = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablacaja = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarCaja = new javax.swing.JButton();
        SalirCaja = new javax.swing.JButton();
        BCobrador = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combocobrador = new javax.swing.JComboBox();
        jTBuscarCobrador = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablacobrador = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarCobrador = new javax.swing.JButton();
        SalirCobrador = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        nombrecliente = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        Modo = new javax.swing.JTextField();
        idControl = new javax.swing.JTextField();
        ruc = new javax.swing.JTextField();
        MostrarCuentas = new javax.swing.JButton();
        BuscarCliente = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        observaciones = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JFormattedTextField();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        moneda = new javax.swing.JTextField();
        buscarMoneda = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        cobrador = new javax.swing.JTextField();
        buscarCobrador = new javax.swing.JButton();
        nombrecobrador = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        nombrecaja = new javax.swing.JTextField();
        buscarCaja = new javax.swing.JButton();
        caja = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nrorecibo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        GrabarCobro = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        Printer = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        totalamortizacion = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        interesordinario = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        interesvencido = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        interesavencer = new javax.swing.JFormattedTextField();
        boxdescuentos = new javax.swing.JCheckBox();
        boxcuotas = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        totalbruto = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        descuentoxpagos = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        gastosxcobrador = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        totalpago = new javax.swing.JFormattedTextField();
        boxgastos = new javax.swing.JCheckBox();
        descuentomora = new javax.swing.JCheckBox();
        descuentogastos = new javax.swing.JCheckBox();

        PagoParcial.setTitle("Pago Parcial de Cuotas");

        jPanel7.setBackground(new java.awt.Color(102, 255, 204));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(102, 102, 255)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("N° Documento");

        nrodocumento.setEditable(false);
        nrodocumento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrodocumento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Concepto");

        concepto.setEditable(false);
        concepto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        concepto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conceptoActionPerformed(evt);
            }
        });

        importe_a_pagar.setEditable(false);
        importe_a_pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_a_pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_a_pagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        importe_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_pagado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        importe_pagado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                importe_pagadoFocusGained(evt);
            }
        });
        importe_pagado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importe_pagadoKeyPressed(evt);
            }
        });

        nrocuota.setEditable(false);
        nrocuota.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrocuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Cuota");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Importe a Pagar");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Su Pago");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(importe_a_pagar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(nrodocumento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importe_pagado)
                    .addComponent(nrocuota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(concepto, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(nrodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nrocuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importe_a_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importe_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(153, 153, 255));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarPagoParcial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarPagoParcial.setText("Aceptar");
        GrabarPagoParcial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarPagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarPagoParcialActionPerformed(evt);
            }
        });

        SalirPagoParcial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirPagoParcial.setText("Salir");
        SalirPagoParcial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPagoParcialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(GrabarPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(SalirPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarPagoParcial)
                    .addComponent(SalirPagoParcial))
                .addContainerGap())
        );

        javax.swing.GroupLayout PagoParcialLayout = new javax.swing.GroupLayout(PagoParcial.getContentPane());
        PagoParcial.getContentPane().setLayout(PagoParcialLayout);
        PagoParcialLayout.setHorizontalGroup(
            PagoParcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PagoParcialLayout.setVerticalGroup(
            PagoParcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagoParcialLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel20.setText("Total a Cobrar");

        totalbrutoapagar.setEditable(false);
        totalbrutoapagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalbrutoapagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalbrutoapagar.setDisabledTextColor(new java.awt.Color(255, 51, 51));
        totalbrutoapagar.setEnabled(false);
        totalbrutoapagar.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalbrutoapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalbrutoapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel26.setText("Efectivo");

        cobroefectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cobroefectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobroefectivo.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        cobroefectivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cobroefectivoFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cobroefectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(cobroefectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Pago en Cheque"));

        jLabel27.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel27.setText("Cargo Banco");

        cobrocheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cobrocheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobrocheque.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        cargobanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel28.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel28.setText("Nº Cheque");

        jLabel29.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel29.setText("Emisión");

        jLabel33.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel33.setText("Importe Cheque");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel27))
                    .addComponent(cargobanco, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(cobrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel28)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33)
                        .addGap(30, 30, 30))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cargobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cobrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Pago con Tarjetas"));

        jLabel30.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel30.setText("Banco Emisor");

        pagotarjeta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        pagotarjeta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pagotarjeta.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        cargoemisor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel31.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel31.setText("Nº Tarjeta");

        jLabel32.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel32.setText("Emisión");

        jLabel34.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel34.setText("Importe Tarjeta");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel30))
                    .addComponent(cargoemisor, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel31)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel32)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(11, 11, 11)))
                .addGap(17, 17, 17))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(14, 14, 14)
                        .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cargoemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        AceptarCobro.setText("Aceptar");
        AceptarCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCobroActionPerformed(evt);
            }
        });

        SalirFormaCobro.setText("Salir");
        SalirFormaCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFormaCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFormaCobroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AceptarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(SalirFormaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCobro)
                    .addComponent(SalirFormaCobro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ingresar_cobrosLayout = new javax.swing.GroupLayout(ingresar_cobros.getContentPane());
        ingresar_cobros.getContentPane().setLayout(ingresar_cobrosLayout);
        ingresar_cobrosLayout.setHorizontalGroup(
            ingresar_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ingresar_cobrosLayout.setVerticalGroup(
            ingresar_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingresar_cobrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarSucursalActionPerformed(evt);
            }
        });
        jTBuscarSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarSucursalKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablasucursal.setModel(modelosucursal);
        tablasucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablasucursalMouseClicked(evt);
            }
        });
        tablasucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablasucursalKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablasucursal);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText("Aceptar");
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText("Salir");
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarClienteActionPerformed(evt);
            }
        });
        jTBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarClienteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacliente.setModel(modelocliente        );
        tablacliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaclienteMouseClicked(evt);
            }
        });
        tablacliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaclienteKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablacliente);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText("Aceptar");
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText("Salir");
        SalirCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        javax.swing.GroupLayout BClienteLayout = new javax.swing.GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("Buscar Giraduría");

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combogiraduria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combogiraduria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combogiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combogiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combogiraduriaActionPerformed(evt);
            }
        });

        BuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarMonedaActionPerformed(evt);
            }
        });
        BuscarMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarMonedaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablamoneda.setModel(modelomoneda);
        tablamoneda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablamonedaMouseClicked(evt);
            }
        });
        tablamoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablamonedaKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablamoneda);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText("Aceptar");
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText("Salir");
        SalirGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BMonedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCaja.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCaja.setTitle("null");

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocaja.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocaja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocajaActionPerformed(evt);
            }
        });

        jTBuscarCaja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCajaActionPerformed(evt);
            }
        });
        jTBuscarCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCajaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacaja.setModel(modelocaja);
        tablacaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacajaMouseClicked(evt);
            }
        });
        tablacaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacajaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablacaja);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCaja.setText("Aceptar");
        AceptarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCajaActionPerformed(evt);
            }
        });

        SalirCaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCaja.setText("Salir");
        SalirCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCaja)
                    .addComponent(SalirCaja))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCajaLayout = new javax.swing.GroupLayout(BCaja.getContentPane());
        BCaja.getContentPane().setLayout(BCajaLayout);
        BCajaLayout.setHorizontalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCajaLayout.setVerticalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCobrador.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCobrador.setTitle("null");

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocobrador.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocobrador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocobradorActionPerformed(evt);
            }
        });

        jTBuscarCobrador.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCobrador.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas_pb.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCobradorActionPerformed(evt);
            }
        });
        jTBuscarCobrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCobradorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(combocobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacobrador.setModel(modelocobrador);
        tablacobrador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacobradorMouseClicked(evt);
            }
        });
        tablacobrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacobradorKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablacobrador);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCobrador.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas_pb.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCobradorActionPerformed(evt);
            }
        });

        SalirCobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCobrador.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas_pb.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCobradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCobrador)
                    .addComponent(SalirCobrador))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCobradorLayout = new javax.swing.GroupLayout(BCobrador.getContentPane());
        BCobrador.getContentPane().setLayout(BCobradorLayout);
        BCobradorLayout.setHorizontalGroup(
            BCobradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCobradorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCobradorLayout.setVerticalGroup(
            BCobradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCobradorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Cliente");

        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });
        cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clienteKeyPressed(evt);
            }
        });

        nombrecliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        direccion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Modo.setText(" ");

        ruc.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        MostrarCuentas.setText("Mostrar Cuentas");
        MostrarCuentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MostrarCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarCuentasActionPerformed(evt);
            }
        });

        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        jLabel37.setText("Observaciones");

        observaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                observacionesKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(MostrarCuentas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(direccion)
                                        .addComponent(nombrecliente, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(observaciones))))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MostrarCuentas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(observaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Sucursal");

        fecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaFocusGained(evt);
            }
        });
        fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaKeyPressed(evt);
            }
        });

        jLabel3.setText("Fecha");

        jLabel14.setText("Cobrador");

        jLabel4.setText("Moneda");

        jLabel5.setText("Cotización");

        cotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });

        sucursal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucursal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sucursalFocusGained(evt);
            }
        });
        sucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucursalActionPerformed(evt);
            }
        });
        sucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sucursalKeyPressed(evt);
            }
        });

        buscarSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        moneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monedaActionPerformed(evt);
            }
        });
        moneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                monedaKeyPressed(evt);
            }
        });

        buscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarMonedaActionPerformed(evt);
            }
        });

        nombremoneda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombremoneda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombremoneda.setEnabled(false);

        cobrador.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobradorActionPerformed(evt);
            }
        });
        cobrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cobradorKeyPressed(evt);
            }
        });

        buscarCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCobradorActionPerformed(evt);
            }
        });

        nombrecobrador.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombrecobrador.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecobrador.setEnabled(false);

        jLabel36.setText("Caja");

        nombrecaja.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombrecaja.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecaja.setEnabled(false);

        buscarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCajaActionPerformed(evt);
            }
        });

        caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaActionPerformed(evt);
            }
        });
        caja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cajaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(13, 13, 13)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(buscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(buscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 68, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel36)
                                .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14))
                            .addComponent(buscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(buscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("N° Recibo");

        nrorecibo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrorecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nrorecibo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nrorecibo.setEnabled(false);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(nrorecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nrorecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTable1.setModel(modelo);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCobro.setText("Grabar");
        GrabarCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCobroActionPerformed(evt);
            }
        });

        Salir.setText("Salir");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("[F1] Seleccionar Pago");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("[F2] Ingresar Pago Parcial");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("[F3] Cobrar");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("[F4] Descontar esta Mora");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("[F5] Descontar Línea Gastos");

        Printer.setText("Print");
        Printer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrinterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Printer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCobro)
                    .addComponent(Salir)
                    .addComponent(jLabel9)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel35)
                    .addComponent(Printer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Amortización");

        totalamortizacion.setEditable(false);
        totalamortizacion.setBackground(new java.awt.Color(204, 204, 204));
        totalamortizacion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalamortizacion.setForeground(new java.awt.Color(255, 0, 0));
        totalamortizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalamortizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalamortizacion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Interés Ordinario");

        interesordinario.setEditable(false);
        interesordinario.setBackground(new java.awt.Color(204, 204, 204));
        interesordinario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        interesordinario.setForeground(new java.awt.Color(255, 0, 0));
        interesordinario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        interesordinario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Interés Vencido");

        interesvencido.setEditable(false);
        interesvencido.setBackground(new java.awt.Color(204, 204, 204));
        interesvencido.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        interesvencido.setForeground(new java.awt.Color(255, 0, 0));
        interesvencido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        interesvencido.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        interesvencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interesvencidoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Interés a Vencer");

        interesavencer.setEditable(false);
        interesavencer.setBackground(new java.awt.Color(204, 204, 204));
        interesavencer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        interesavencer.setForeground(new java.awt.Color(255, 0, 0));
        interesavencer.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        interesavencer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        interesavencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interesavencerActionPerformed(evt);
            }
        });

        boxdescuentos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxdescuentos.setText("Aplicar Descuentos por Pagos Adelantados");
        boxdescuentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxdescuentosActionPerformed(evt);
            }
        });

        boxcuotas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxcuotas.setText("Cobrar todas las Cuotas");
        boxcuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxcuotasActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Bruto a Cobrar");

        totalbruto.setEditable(false);
        totalbruto.setBackground(new java.awt.Color(204, 204, 204));
        totalbruto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalbruto.setForeground(new java.awt.Color(255, 0, 0));
        totalbruto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalbruto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Descuentos x Pagos Adelantados");

        descuentoxpagos.setEditable(false);
        descuentoxpagos.setBackground(new java.awt.Color(204, 204, 204));
        descuentoxpagos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        descuentoxpagos.setForeground(new java.awt.Color(255, 0, 0));
        descuentoxpagos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        descuentoxpagos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Gastos envío de Cobrador");

        gastosxcobrador.setEditable(false);
        gastosxcobrador.setBackground(new java.awt.Color(204, 204, 204));
        gastosxcobrador.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gastosxcobrador.setForeground(new java.awt.Color(255, 0, 0));
        gastosxcobrador.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gastosxcobrador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gastosxcobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gastosxcobradorActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Neto a Cobrar");

        totalpago.setEditable(false);
        totalpago.setBackground(new java.awt.Color(204, 204, 204));
        totalpago.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalpago.setForeground(new java.awt.Color(255, 0, 0));
        totalpago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalpago.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        boxgastos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxgastos.setText("Aplicar Gastos x Envío de Cobrador");
        boxgastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxgastosActionPerformed(evt);
            }
        });

        descuentomora.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        descuentomora.setText("Aplicar Descuentos de Interés Moratorio");
        descuentomora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descuentomoraActionPerformed(evt);
            }
        });

        descuentogastos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        descuentogastos.setText("Aplicar Descuentos de Gastos x Mora");
        descuentogastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descuentogastosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel7)))
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(interesvencido, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalamortizacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(interesavencer)
                    .addComponent(interesordinario))
                .addGap(136, 136, 136)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxcuotas)
                    .addComponent(boxdescuentos)
                    .addComponent(boxgastos)
                    .addComponent(descuentomora)
                    .addComponent(descuentogastos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(descuentoxpagos)
                    .addComponent(totalbruto)
                    .addComponent(gastosxcobrador)
                    .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(totalamortizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalbruto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(boxdescuentos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descuentoxpagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(boxcuotas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(interesvencido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(boxgastos))
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(gastosxcobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(descuentomora))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(interesavencer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(descuentogastos))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void interesvencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interesvencidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_interesvencidoActionPerformed

    private void interesavencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interesavencerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_interesavencerActionPerformed

    private void boxdescuentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxdescuentosActionPerformed
        if (this.boxdescuentos.isSelected()) {
            cDescuentos = this.interesavencer.getText();
            cDescuentos = cDescuentos.replace(".", "").replace(",", ".");
            nDescuentos = Double.parseDouble(cDescuentos);
            nDescuentos = Math.round(nDescuentos * Config.limitedescuento / 100);
            this.descuentoxpagos.setText(formato.format(nDescuentos));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(nBruto - nDescuentos));

        } else {
            this.descuentoxpagos.setText(formato.format(0));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(cBruto));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_boxdescuentosActionPerformed

    private void boxcuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxcuotasActionPerformed
        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        sumatoria = 0.00;
        sumtotal = 0.00;
        if (boxcuotas.isSelected()) { // Si hemos dado clic en el jCheckBox
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(this.jTable1.getValueAt(i, 11), i, 12);
                cValorActual = String.valueOf(modelo.getValueAt(i, 12));
                cValorActual = cValorActual.replace(".", "");
                cValorActual = cValorActual.replace(",", ".");
                sumatoria = Double.parseDouble(cValorActual);
                sumtotal += sumatoria;
            }
        } else {
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(0, i, 12);
            }
        }
        this.sumarcobros();
    }//GEN-LAST:event_boxcuotasActionPerformed

    private void clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clienteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fecha.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            this.BuscarCliente.doClick();
        }
    }//GEN-LAST:event_clienteKeyPressed

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.caja.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        BusClientes buscliente = new BusClientes();
        buscliente.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.cliente.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombrecliente.setText(Parametros.NOMBRE_ELEGIDO.trim());
            this.ruc.setText(Parametros.RUC_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
            Parametros.RUC_ELEGIDO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            this.jTable1.setValueAt(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 11), this.jTable1.getSelectedRow(), 12);
            this.sumarcobros();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            nFila = this.jTable1.getSelectedRow();
            this.nrodocumento.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1).toString());
            this.concepto.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 4).toString());
            this.nrocuota.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 5).toString());
            this.importe_a_pagar.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 11).toString());
            this.importe_pagado.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 12).toString());
            PagoParcial.setSize(448, 360);
            PagoParcial.setLocationRelativeTo(null);
            PagoParcial.setVisible(true);
            this.importe_pagado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            totalbrutoapagar.setText(totalbruto.getText());
            ingresar_cobros.setSize(650, 420);
            ingresar_cobros.setLocationRelativeTo(null);
            ingresar_cobros.setVisible(true);
            this.cobroefectivo.requestFocus();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            nFila = this.jTable1.getSelectedRow();
            this.jTable1.setValueAt(0, nFila, 9); //COLUMNA INTERES MORATORIO
            this.jTable1.setValueAt(0, nFila, 19); //COLUMNA INTERES PUNITORIO

            String cSaldo = this.jTable1.getValueAt(nFila, 8).toString(); // COLUMNA SALDO
            String cGasto = this.jTable1.getValueAt(nFila, 10).toString();// COLUMNA GASTO

            cSaldo = cSaldo.replace(".", "");
            cSaldo = cSaldo.replace(",", ".");

            cGasto = cGasto.replace(".", "");
            cGasto = cGasto.replace(",", ".");
            double nItemTotal = Double.valueOf(cGasto) + Double.valueOf(cSaldo);

            this.jTable1.setValueAt(formato.format(nItemTotal), nFila, 11);

            //this.jTable1.setValueAt(0, i, 10); // 
            //formato.format(sumatoria1);
            this.sumarcobros();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            nFila = this.jTable1.getSelectedRow();
            this.jTable1.setValueAt(0, nFila, 10); //COLUMNA GASTOS

            //     this.jTable1.setValueAt(0, i, 9); //COLUMNA INTERES MORATORIO
            //     this.jTable1.setValueAt(0, i, 19); //COLUMNA INTERES PUNITORIO
            String cSaldo = this.jTable1.getValueAt(nFila, 8).toString(); // COLUMNA SALDO
            String cIntMora = this.jTable1.getValueAt(nFila, 9).toString();// INTERES MORATORIO
            String cIntPuni = this.jTable1.getValueAt(nFila, 19).toString();// INTERES PUNITORIO

            cSaldo = cSaldo.replace(".", "");
            cSaldo = cSaldo.replace(",", ".");

            cIntMora = cIntMora.replace(".", "");
            cIntMora = cIntMora.replace(",", ".");

            cIntPuni = cIntPuni.replace(".", "");
            cIntPuni = cIntPuni.replace(",", ".");

            double nItemT = Double.valueOf(cSaldo) + Double.valueOf(cIntMora) + Double.valueOf(cIntPuni);

            this.jTable1.setValueAt(formato.format(nItemT), nFila, 11);

            this.sumarcobros();
        }


    }//GEN-LAST:event_jTable1KeyPressed

    private void MostrarCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarCuentasActionPerformed
        CargarSaldos cargarsaldo = new CargarSaldos();
        cargarsaldo.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_MostrarCuentasActionPerformed

    private void boxgastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxgastosActionPerformed
        if (this.boxgastos.isSelected()) {
            cDescuentos = this.descuentoxpagos.getText();
            cDescuentos = cDescuentos.replace(".", "").replace(",", ".");
            nDescuentos = Double.parseDouble(cDescuentos);
            this.gastosxcobrador.setText(formato.format(Config.nCobradorEnCasa));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(nBruto - nDescuentos + Config.nCobradorEnCasa));

        } else {
            cDescuentos = this.descuentoxpagos.getText();
            cDescuentos = cDescuentos.replace(".", "").replace(",", ".");
            nDescuentos = Double.parseDouble(cDescuentos);
            this.gastosxcobrador.setText(formato.format(0));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(nBruto - nDescuentos));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_boxgastosActionPerformed

    private void conceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conceptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoActionPerformed

    private void SalirPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPagoParcialActionPerformed
        PagoParcial.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPagoParcialActionPerformed

    private void importe_pagadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importe_pagadoFocusGained
        this.importe_a_pagar.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoFocusGained

    private void GrabarPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarPagoParcialActionPerformed
        this.jTable1.setValueAt(this.importe_pagado.getText(), nFila, 12);
        nFila = 0;
        PagoParcial.setVisible(false);
        this.sumarcobros();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarPagoParcialActionPerformed

    private void importe_pagadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importe_pagadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarPagoParcial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoKeyPressed

    private void SalirFormaCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFormaCobroActionPerformed
        this.GrabarCobro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFormaCobroActionPerformed

    private void cobroefectivoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cobroefectivoFocusGained
        this.cobroefectivo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoFocusGained

    private void AceptarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobroActionPerformed
        this.ingresar_cobros.setVisible(false);
        this.GrabarCobro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCobroActionPerformed

    private void GrabarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCobroActionPerformed
        if (this.totalpago.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "No existen Pagos Seleccionados");
            this.totalpago.requestFocus();
            return;
        }

        Object[] opciones = {"   Grabar   ", "   Salir   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            UUID id = new UUID();
            String idunico = UUID.crearUUID();
            idunico = idunico.substring(1, 25);
            idControl.setText(idunico.trim());
            double nsupago = 0.00;

            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            String cTotalPago = totalpago.getText();
            if (cTotalPago.trim().length() > 0) {
                cTotalPago = cTotalPago.replace(".", "").replace(",", ".");
            } else {
                cTotalPago = "0";
            }

            String cGastosCobrador = gastosxcobrador.getText();
            if (cGastosCobrador.trim().length() > 0) {
                cGastosCobrador = cGastosCobrador.replace(".", "").replace(",", ".");
            } else {
                cGastosCobrador = "0";
            }

            String cTotalBruto = totalbruto.getText();
            if (cTotalBruto.trim().length() > 0) {
                cTotalBruto = cTotalBruto.replace(".", "").replace(",", ".");
            } else {
                cTotalBruto = "0";
            }

            String cDescuentos = descuentoxpagos.getText();
            if (cDescuentos.trim().length() > 0) {
                cDescuentos = cDescuentos.replace(".", "").replace(",", ".");
            } else {
                cDescuentos = "0";
            }
            BigDecimal nDescuento = new BigDecimal(cDescuentos);

            String cGastosEnvio = gastosxcobrador.getText();
            if (cGastosEnvio.trim().length() > 0) {
                cGastosEnvio = cGastosEnvio.replace(".", "").replace(",", ".");
            } else {
                cGastosEnvio = "0";
            }
            String cCotizacion = cotizacion.getText();
            cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
            BigDecimal cotizacionmoneda = new BigDecimal(cCotizacion);
            cobranzaDAO cobDAO = new cobranzaDAO();
            cobranza cob = new cobranza();
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal sc = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cl = null;
            monedaDAO monDAO = new monedaDAO();
            moneda mo = null;
            cobradorDAO cobroDAO = new cobradorDAO();
            cobrador co = null;
            usuarioDAO usuDAO = new usuarioDAO();
            usuario usu = null;
            cajaDAO caDAO = new cajaDAO();
            caja ca = null;

            try {
                cl = cliDAO.buscarId(Integer.valueOf(cliente.getText()));
                sc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
                co = cobroDAO.buscarId(Integer.valueOf(cobrador.getText()));
                usu = usuDAO.buscarId(Integer.valueOf(Config.CodUsuario));
                ca = caDAO.buscarId(Integer.valueOf(caja.getText()));
                mo = monDAO.buscarId(Integer.valueOf(moneda.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            cob.setIdpagos(idControl.getText().trim());
            String cNroRecibo = nrorecibo.getText();
            cNroRecibo = cNroRecibo.replace(".", "").replace(",", ".");
            BigDecimal nrec = new BigDecimal(cNroRecibo);
            cob.setNumero(nrec);
            cob.setFecha(FechaProceso);
            cob.setCliente(cl);
            cob.setMoneda(mo);
            BigDecimal TotalPago = new BigDecimal(cTotalPago);
            cob.setTotalpago(TotalPago);
            cob.setObservacion(observaciones.getText());
            cob.setValores(TotalPago);
            cob.setCobrador(co);
            cob.setSucursal(sc);
            cob.setCotizacionmoneda(cotizacionmoneda);
            cob.setDescuentos(nDescuento);
            cob.setEnviocobrador(Integer.valueOf(cGastosEnvio));
            cob.setCodusuario(usu);
            cob.setCaja(ca);

            String detalle = "[";
            int Items = jTable1.getRowCount();
            for (int i = 0; i < Items; i++) {
                String supago = jTable1.getValueAt(i, 12).toString();
                supago = supago.replace(".", "");
                supago = supago.replace(",", ".");
                nsupago = Double.valueOf(supago);

                String idFactura = (jTable1.getValueAt(i, 0).toString().trim());
                String cFactura = (jTable1.getValueAt(i, 1).toString().trim());
                try {
                    dEmision = ODate.de_java_a_sql(formatoFecha.parse(jTable1.getValueAt(i, 2).toString()));
                    dVence = ODate.de_java_a_sql(formatoFecha.parse(jTable1.getValueAt(i, 3).toString()));
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cCuota = (jTable1.getValueAt(i, 5).toString());

                String cAmortiza = (jTable1.getValueAt(i, 6).toString());
                cAmortiza = cAmortiza.replace(".", "");
                cAmortiza = cAmortiza.replace(",", ".");

                String cInteres = (jTable1.getValueAt(i, 7).toString());
                cInteres = cInteres.replace(".", "");
                cInteres = cInteres.replace(",", ".");

                String cSaldo = (jTable1.getValueAt(i, 8).toString());
                cSaldo = cSaldo.replace(".", "");
                cSaldo = cSaldo.replace(",", ".");

                String cMora = (jTable1.getValueAt(i, 9).toString());
                if (cMora.trim().length() > 0) {
                    cMora = cMora.replace(".", "");
                    cMora = cMora.replace(",", ".");
                } else {
                    cMora = "0";
                }

                String cGastos = (jTable1.getValueAt(i, 10).toString());
                if (cGastos.trim().length() > 0) {
                    cGastos = cGastos.replace(".", "");
                    cGastos = cGastos.replace(",", ".");
                } else {
                    cGastos = "0";
                }

                String cPagoCuota = (jTable1.getValueAt(i, 11).toString());
                cPagoCuota = cPagoCuota.replace(".", "").replace(",", ".");

                String cCapital = (jTable1.getValueAt(i, 13).toString());
                cCapital = cCapital.replace(".", "").replace(",", ".");

                String cInversionista = (jTable1.getValueAt(i, 14).toString());
                cInversionista = cInversionista.replace(".", "").replace(",", ".");

                String cNumerocuota = (jTable1.getValueAt(i, 15).toString());
                String cComprobante = (jTable1.getValueAt(i, 16).toString());
                String cAtraso = (jTable1.getValueAt(i, 17).toString());

                String cIvaInteres = (jTable1.getValueAt(i, 18).toString());
                if (cIvaInteres.trim().length() > 0) {
                    cIvaInteres = cIvaInteres.replace(".", "");
                    cIvaInteres = cIvaInteres.replace(",", ".");
                } else {
                    cIvaInteres = "0";
                }

                String cPunitorio = (jTable1.getValueAt(i, 19).toString());
                if (cPunitorio.trim().length() > 0) {
                    cPunitorio = cPunitorio.replace(".", "");
                    cPunitorio = cPunitorio.replace(",", ".");
                } else {
                    cPunitorio = "0";
                }
                //En caso que el pago sea menor al monto a pagar   
                if (nsupago < Double.valueOf(cPagoCuota)) {
                    double nresto = nsupago;
                    if (Double.valueOf(cGastos) > 0) {
                        if (nresto <= Double.valueOf(cGastos)) {
                            cGastos = String.valueOf(nresto);
                            nresto = 0;
                        } else {
                            nresto = nresto - Double.valueOf(cGastos);
                        }
                    }

                    if (Double.valueOf(cPunitorio) > 0 && nresto > 0) {
                        if (nresto <= Double.valueOf(cPunitorio)) {
                            cPunitorio = String.valueOf(nresto);
                            nresto = 0;
                        } else {
                            nresto = nresto - Double.valueOf(cPunitorio);
                        }
                    } else {
                        cPunitorio = "0";
                    }

                    if (Double.valueOf(cMora) > 0 && nresto > 0) {
                        if (nresto <= Double.valueOf(cMora)) {
                            cMora = String.valueOf(nresto);
                            nresto = 0;
                        } else {
                            nresto = nresto - Double.valueOf(cMora);
                        }
                    } else {
                        cMora = "0";
                    }

                    if (Double.valueOf(cInteres) > 0 && nresto > 0) {
                        if (nresto <= Double.valueOf(cInteres)) {
                            cInteres = String.valueOf(nresto);
                            nresto = 0;
                        } else {
                            nresto = nresto - Double.valueOf(cInteres);
                        }
                    } else {
                        cInteres = "0";
                    }
                    cAmortiza = String.valueOf(nresto);
                }
                if (Double.valueOf(supago) > 0) {
                    String linea = "{iddetalle : " + cob.getIdpagos().trim() + ","
                            + "idfactura : " + idFactura + ","
                            + "nrofactura : " + cFactura + ","
                            + "emision : " + dEmision + ","
                            + "comprobante : " + cComprobante + ","
                            + "pago : " + supago + ","
                            + "capital : " + cCapital + ","
                            + "diamora : " + cAtraso + ","
                            + "mora : " + cMora + ","
                            + "gastos_cobranzas : " + cGastos + ","
                            + "moneda : " + moneda.getText() + ","
                            + "amortiza : " + cAmortiza + ","
                            + "minteres : " + cInteres + ","
                            + "vence : " + dVence + ","
                            + "acreedor : " + cInversionista + ","
                            + "cuota : " + cCuota + ","
                            + "numerocuota : " + cNumerocuota + ","
                            + "importe_iva: " + cIvaInteres + ","
                            + "punitorio: " + cPunitorio + ","
                            + "fechacobro: " + FechaProceso + "},";
                    detalle += linea;
                }
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            try {
                cobDAO.insertarCobranza(cob, detalle);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
            this.Printer.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCobroActionPerformed

    private void gastosxcobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gastosxcobradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gastosxcobradorActionPerformed

    private void descuentomoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descuentomoraActionPerformed
        if (this.descuentomora.isSelected()) {
            int totalRow = modelo.getRowCount();
            totalRow -= 1;
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(0, i, 9); //COLUMNA INTERES MORATORIO
                this.jTable1.setValueAt(0, i, 19); //COLUMNA INTERES PUNITORIO

                String cSaldo = this.jTable1.getValueAt(i, 8).toString(); // COLUMNA SALDO
                String cGasto = this.jTable1.getValueAt(i, 10).toString();// COLUMNA GASTO

                cSaldo = cSaldo.replace(".", "");
                cSaldo = cSaldo.replace(",", ".");

                cGasto = cGasto.replace(".", "");
                cGasto = cGasto.replace(",", ".");
                double nItemTotal = Double.valueOf(cGasto) + Double.valueOf(cSaldo);

                this.jTable1.setValueAt(formato.format(nItemTotal), i, 11);

                //this.jTable1.setValueAt(0, i, 10); // 
                //formato.format(sumatoria1);
            }
            this.sumarcobros();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_descuentomoraActionPerformed

    private void descuentogastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descuentogastosActionPerformed
        if (this.descuentogastos.isSelected()) {
            int totalRow = modelo.getRowCount();
            totalRow -= 1;
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(0, i, 10); //COLUMNA GASTOS

                //     this.jTable1.setValueAt(0, i, 9); //COLUMNA INTERES MORATORIO
                //     this.jTable1.setValueAt(0, i, 19); //COLUMNA INTERES PUNITORIO
                String cSaldo = this.jTable1.getValueAt(i, 8).toString(); // COLUMNA SALDO
                String cIntMora = this.jTable1.getValueAt(i, 9).toString();// INTERES MORATORIO
                String cIntPuni = this.jTable1.getValueAt(i, 19).toString();// INTERES PUNITORIO

                cSaldo = cSaldo.replace(".", "");
                cSaldo = cSaldo.replace(",", ".");

                cIntMora = cIntMora.replace(".", "");
                cIntMora = cIntMora.replace(",", ".");

                cIntPuni = cIntPuni.replace(".", "");
                cIntPuni = cIntPuni.replace(",", ".");

                double nItemT = Double.valueOf(cSaldo) + Double.valueOf(cIntMora) + Double.valueOf(cIntPuni);

                this.jTable1.setValueAt(formato.format(nItemT), i, 11);
            }
            this.sumarcobros();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_descuentogastosActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void sucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void buscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (sucu.getCodigo() == 0) {
                BSucursal.setModal(true);
                BSucursal.setSize(500, 575);
                BSucursal.setLocationRelativeTo(null);
                BSucursal.setTitle("Buscar Sucursal");
                BSucursal.setVisible(true);
                BSucursal.setModal(false);
                fecha.requestFocus();
            } else {
                nombresucursal.setText(sucu.getNombre());
                //Establecemos un título para el jDialog
                fecha.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void buscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarMonedaActionPerformed
        monedaDAO monDAO = new monedaDAO();
        moneda mo = null;
        try {
            mo = monDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mo.getCodigo() == 0) {
                GrillaMoneda grillamo = new GrillaMoneda();
                Thread hiloca = new Thread(grillamo);
                hiloca.start();
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                BMoneda.setTitle("Buscar Moneda");
                cotizacion.requestFocus();
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mo.getNombre());
                cotizacion.setText(formato.format(mo.getVenta()));
                //Establecemos un título para el jDialog
                cotizacion.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarMonedaActionPerformed

    private void buscarCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCobradorActionPerformed
        cobradorDAO coDAO = new cobradorDAO();
        cobrador cob = null;
        try {
            cob = coDAO.buscarId(Integer.valueOf(this.cobrador.getText()));
            if (cob.getCodigo() == 0) {
                GrillaCobrador grillaco = new GrillaCobrador();
                Thread hilocobr = new Thread(grillaco);
                hilocobr.start();
                BCobrador.setModal(true);
                BCobrador.setSize(500, 575);
                BCobrador.setLocationRelativeTo(null);
                BCobrador.setVisible(true);
                BCobrador.setTitle("Buscar Cobrador");
                BCobrador.setModal(false);
            } else {
                nombrecobrador.setText(cob.getNombre());
                //Establecemos un título para el jDialog
            }
            moneda.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarCobradorActionPerformed

    private void buscarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCajaActionPerformed
        cajaDAO cajDAO = new cajaDAO();
        caja ca = null;
        try {
            ca = cajDAO.buscarId(Integer.valueOf(this.caja.getText()));
            if (ca.getCodigo() == 0) {
                GrillaCaja grillaca = new GrillaCaja();
                Thread hilocaja = new Thread(grillaca);
                hilocaja.start();
                BCaja.setModal(true);
                BCaja.setSize(500, 575);
                BCaja.setLocationRelativeTo(null);
                BCaja.setVisible(true);
                BCaja.setTitle("Buscar Caja");
                BCaja.setModal(false);
            } else {
                nombrecaja.setText(ca.getNombre());
                double nrecibo = ca.getRecibo() + 1;
                nrorecibo.setText(formato.format(nrecibo));
                //Establecemos un título para el jDialog
            }
            cobrador.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarCajaActionPerformed

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.cliente.getText()));
            if (cl.getCodigo() == 0) {
                GrillaCliente grillacl = new GrillaCliente();
                Thread hilocl = new Thread(grillacl);
                hilocl.start();
                BCliente.setModal(true);
                BCliente.setSize(500, 575);
                BCliente.setLocationRelativeTo(null);
                BCliente.setTitle("Buscar Cliente");
                BCliente.setVisible(true);
                MostrarCuentas.requestFocus();
                BCliente.setModal(false);
            } else {
                nombrecliente.setText(cl.getNombre());
                direccion.setText(cl.getDireccion());
                ruc.setText(cl.getRuc());
                //Establecemos un título para el jDialog
                MostrarCuentas.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

    private void jTBuscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalActionPerformed

    private void jTBuscarSucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarSucursalKeyPressed
        this.jTBuscarSucursal.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarSucursal.getText()).toUpperCase();
                jTBuscarSucursal.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combosucursal.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltrosuc = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltrosuc);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalKeyPressed

    private void TitMoneda() {
        modelomoneda.addColumn("Código");
        modelomoneda.addColumn("Nombre");
        modelomoneda.addColumn("Cotización");

        int[] anchos = {90, 100, 90};
        for (int i = 0; i < modelomoneda.getColumnCount(); i++) {
            tablamoneda.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamoneda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamoneda.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablamoneda.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablamoneda.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablamoneda.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
    }

    private void TitCaja() {
        modelocaja.addColumn("Código");
        modelocaja.addColumn("Nombre");
        modelocaja.addColumn("Nro. Recibo");

        int[] anchos = {90, 100, 90};
        for (int i = 0; i < modelocaja.getColumnCount(); i++) {
            tablacaja.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacaja.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacaja.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacaja.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacaja.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablacaja.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
    }

    public void filtrocobrador(int nNumeroColumna) {
        trsfiltrocobrador.setRowFilter(RowFilter.regexFilter(this.jTBuscarCobrador.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrocaja(int nNumeroColumna) {
        trsfiltrocaja.setRowFilter(RowFilter.regexFilter(this.jTBuscarCaja.getText(), nNumeroColumna));
    }

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.BuscarMoneda.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }


    private void tablasucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasucursalMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalMouseClicked

    private void tablasucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalKeyPressed

    private void TitSuc() {
        modelosucursal.addColumn("Código");
        modelosucursal.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelosucursal.getColumnCount(); i++) {
            tablasucursal.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablasucursal.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablasucursal.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablasucursal.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablasucursal.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitCobrador() {
        modelocobrador.addColumn("Código");
        modelocobrador.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelocobrador.getColumnCount(); i++) {
            tablacobrador.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacobrador.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacobrador.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacobrador.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacobrador.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");
        this.fecha.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setModal(true);
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void comboclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

    private void jTBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

    private void jTBuscarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarClienteKeyPressed
        this.jTBuscarCliente.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCliente.getText()).toUpperCase();
                jTBuscarCliente.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocliente.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 3;
                        break;//por RUC
                }
                repaint();
                filtrocli(indiceColumnaTabla);
            }
        });
        trsfiltrocli = new TableRowSorter(tablacliente.getModel());
        tablacliente.setRowSorter(trsfiltrocli);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteKeyPressed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.AceptarCli.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void tablaclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCli.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteKeyPressed

    private void AceptarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCliActionPerformed
        int nFila = this.tablacliente.getSelectedRow();
        this.cliente.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombrecliente.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.direccion.setText(this.tablacliente.getValueAt(nFila, 2).toString());
        this.ruc.setText(this.tablacliente.getValueAt(nFila, 3).toString());
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        MostrarCuentas.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private void BuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarMonedaKeyPressed
        this.BuscarMoneda.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (BuscarMoneda.getText()).toUpperCase();
                BuscarMoneda.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combogiraduria.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtromoneda(indiceColumnaTabla);
            }
        });
        trsfiltromoneda = new TableRowSorter(tablamoneda.getModel());
        tablamoneda.setRowSorter(trsfiltromoneda);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaKeyPressed

    private void tablamonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamonedaMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaMouseClicked

    private void tablamonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamonedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablamoneda.getSelectedRow();
        this.moneda.setText(this.tablamoneda.getValueAt(nFila, 0).toString());
        this.nombremoneda.setText(this.tablamoneda.getValueAt(nFila, 1).toString());
        this.cotizacion.setText(this.tablamoneda.getValueAt(nFila, 2).toString());

        this.BMoneda.setVisible(false);
        BMoneda.setModal(true);
        this.cotizacion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        buscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void combocajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocajaActionPerformed

    private void jTBuscarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCajaActionPerformed

    private void jTBuscarCajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCajaKeyPressed
        this.jTBuscarCaja.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCaja.getText()).toUpperCase();
                jTBuscarCaja.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocaja.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltrocaja = new TableRowSorter(tablacaja.getModel());
        tablacaja.setRowSorter(trsfiltrocaja);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCajaKeyPressed

    private void tablacajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacajaMouseClicked
        this.AceptarCaja.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacajaMouseClicked

    private void tablacajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacajaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCaja.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacajaKeyPressed

    private void AceptarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCajaActionPerformed
        int nFila = this.tablacaja.getSelectedRow();
        this.caja.setText(this.tablacaja.getValueAt(nFila, 0).toString());
        this.nombrecaja.setText(this.tablacaja.getValueAt(nFila, 1).toString());
        this.nrorecibo.setText(tablacaja.getValueAt(nFila, 2).toString());
        this.BCaja.setVisible(false);
        this.jTBuscarCaja.setText("");
        this.cobrador.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCajaActionPerformed

    private void SalirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCajaActionPerformed
        this.BCaja.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCajaActionPerformed

    private void cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaActionPerformed
        this.buscarCaja.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaActionPerformed

    private void combocobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocobradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocobradorActionPerformed

    private void jTBuscarCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCobradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCobradorActionPerformed

    private void jTBuscarCobradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCobradorKeyPressed
        this.jTBuscarCobrador.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCobrador.getText()).toUpperCase();
                jTBuscarCobrador.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocobrador.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por RUC
                }
                repaint();
                filtrocobrador(indiceColumnaTabla);
            }
        });
        trsfiltrocobrador = new TableRowSorter(tablacobrador.getModel());
        tablacobrador.setRowSorter(trsfiltrocobrador);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCobradorKeyPressed

    private void tablacobradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacobradorMouseClicked
        this.AceptarCobrador.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacobradorMouseClicked

    private void tablacobradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacobradorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCobrador.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacobradorKeyPressed

    private void AceptarCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobradorActionPerformed
        int nFila = this.tablacobrador.getSelectedRow();
        this.cobrador.setText(this.tablacobrador.getValueAt(nFila, 0).toString());
        this.nombrecobrador.setText(this.tablacobrador.getValueAt(nFila, 1).toString());

        this.BCobrador.setVisible(false);
        this.jTBuscarCobrador.setText("");
        this.moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCobradorActionPerformed

    private void SalirCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCobradorActionPerformed
        this.BCobrador.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCobradorActionPerformed

    private void cobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobradorActionPerformed
        this.buscarCobrador.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cobradorActionPerformed

    private void cajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cobrador.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaKeyPressed

    private void cobradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cobradorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.caja.requestFocus();
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_cobradorKeyPressed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cotizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cobrador.requestFocus();
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void cotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cliente.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionKeyPressed

    private void PrinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrinterActionPerformed
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreRecibo = config.getNombrerecibo();

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0) {

            //se elige la impresora
            PrintService impresora = printer.buscar(suc.getImpresorarecibosuc());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport
                    String num = String.valueOf(totalpago.getText());
                    num = num.replace(".", "").replace(",", ".");
                    numero_a_letras numero = new numero_a_letras();
                    parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                    parameters.put("cReferencia", idControl.getText());
                    parameters.put("Letra", numero.Convertir(num, true, 1));

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    URL url = getClass().getClassLoader().getResource("Reports/" + suc.getNombrerecibosuc().trim());

                    jasperReport = (JasperReport) JRLoader.loadObject(url);
                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stm.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            System.out.println("NO HAY IMPRESORA");
        }
        this.Salir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_PrinterActionPerformed

    private void observacionesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_observacionesKeyTyped
        int limite = 50;
        if (observaciones.getText().length() == limite) {
            evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_observacionesKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {    //</editor-fold>
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new detalle_cobranzas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCaja;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarCobrador;
    private javax.swing.JButton AceptarCobro;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JDialog BCaja;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BCobrador;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JTextField BuscarMoneda;
    private javax.swing.JButton GrabarCobro;
    private javax.swing.JButton GrabarPagoParcial;
    private javax.swing.JTextField Modo;
    private javax.swing.JButton MostrarCuentas;
    private javax.swing.JDialog PagoParcial;
    private javax.swing.JButton Printer;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCaja;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCobrador;
    private javax.swing.JButton SalirFormaCobro;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirPagoParcial;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JCheckBox boxcuotas;
    private javax.swing.JCheckBox boxdescuentos;
    private javax.swing.JCheckBox boxgastos;
    private javax.swing.JButton buscarCaja;
    private javax.swing.JButton buscarCobrador;
    private javax.swing.JButton buscarMoneda;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JTextField caja;
    private javax.swing.JComboBox<String> cargobanco;
    private javax.swing.JComboBox<String> cargoemisor;
    private javax.swing.JTextField cliente;
    private javax.swing.JTextField cobrador;
    private javax.swing.JFormattedTextField cobrocheque;
    private javax.swing.JFormattedTextField cobroefectivo;
    private javax.swing.JComboBox combocaja;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combocobrador;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JTextField concepto;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JCheckBox descuentogastos;
    private javax.swing.JCheckBox descuentomora;
    private javax.swing.JFormattedTextField descuentoxpagos;
    private javax.swing.JTextField direccion;
    private com.toedter.calendar.JDateChooser emisioncheque;
    private com.toedter.calendar.JDateChooser emisiontarjeta;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JFormattedTextField gastosxcobrador;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField importe_a_pagar;
    private javax.swing.JFormattedTextField importe_pagado;
    private javax.swing.JDialog ingresar_cobros;
    private javax.swing.JFormattedTextField interesavencer;
    private javax.swing.JFormattedTextField interesordinario;
    private javax.swing.JFormattedTextField interesvencido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCaja;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarCobrador;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField nombrecaja;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombrecobrador;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nrocheque;
    private javax.swing.JTextField nrocuota;
    private javax.swing.JTextField nrodocumento;
    private javax.swing.JTextField nrorecibo;
    private javax.swing.JTextField nrotarjeta;
    private javax.swing.JTextField observaciones;
    private javax.swing.JFormattedTextField pagotarjeta;
    private javax.swing.JTextField ruc;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablacaja;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacobrador;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JFormattedTextField totalamortizacion;
    private javax.swing.JFormattedTextField totalbruto;
    private javax.swing.JFormattedTextField totalbrutoapagar;
    private javax.swing.JFormattedTextField totalpago;
    // End of variables declaration//GEN-END:variables

    public class NumRecibo extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT MAX(numero)+1 AS nRegistro FROM cobranzas");
                if (results.next()) {
                    nrorecibo.setText(results.getString("nRegistro"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }
        }
    }

    public class BusClientes extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("select codigo, nombre,ruc  from clientes where codigo=" + cliente.getText());
                if (results.next()) {
                    nombrecliente.setText(results.getString("nombre").trim());
                    ruc.setText(results.getString("ruc"));
                } else {
                    BuscarCliente.doClick();
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }

        }
    }

    public class CargarSaldos extends Thread {

        public void run() {
            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            String cCotizacionMoneda = cotizacion.getText();
            cCotizacionMoneda = cCotizacionMoneda.replace(".", "");
            cCotizacionMoneda = cCotizacionMoneda.replace(",", ".");
            double nCotizacionMoneda = Double.valueOf(cCotizacionMoneda);

            String cSql = "SELECT cuenta_clientes.*,clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF('" + FechaProceso + "',cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nomalias,DATEDIFF('" + FechaProceso + "',cuenta_clientes.fecha_pago) AS di,";
            cSql = cSql + "comprobantes.interespunitorio,comprobantes.diasgracia_gastos,comprobantes.diasgracia,cuenta_clientes.tasaoperativa as interesmora,comprobantes.gastoscobros,cuenta_clientes.importe_iva,cuenta_clientes.punitorio, ";
            cSql = cSql + "(SELECT SUM(mora)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) morapagada, ";
            cSql = cSql + "(SELECT SUM(punitorio)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) punitoriocobrado, ";
            cSql = cSql + "(SELECT SUM(gastos_cobranzas)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) gastoscobrado ";
            cSql = cSql + " FROM cuenta_clientes ";
            cSql = cSql + " INNER JOIN CLIENTES ";
            cSql = cSql + " ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + " INNER JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " and cuenta_clientes.cliente= " + cliente.getText();
            cSql = cSql + " and cuenta_clientes.moneda= " + moneda.getText();
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            System.out.println(cSql);
            double nCapital = 0.00;
            double nMora = 0.00;
            double nInteres = 0.00;
            double nPunitorio = 0.00;
            double nTasaPunitoria = 0.00;
            int nDiaGraciaMora = 0;
            int nDiaCompletoMora = 0;
            int nComprobante = 0;
            double nImporteGastos, nDiasGracia, nDiasGraciaGastos = 0.00;
            double nMoraPagada, nPunitorioPagado, nGastosPagados = 0.00;
            double nMoraTotalPagar = 0.00;
            int natraso, ndiasmora = 0;
            String catraso = null;

            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;

            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    // Se crea un array que será una de las filas de la tabla.
                    Object[] fila = new Object[20]; // Hay 8 columnas en la tabla
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    //Verificamos si tiene fecha de pago cargado
                    //Si tiene fecha de pago
                    //usamos como fecha de ultimo pago
                    //de lo contrario la fecha de vencimiento
                    nPunitorio = 0.00;
                    nMora = 0.00;
                    nImporteGastos = 0.00;
                    nMoraPagada = results.getDouble("morapagada");
                    nPunitorioPagado = results.getDouble("punitoriocobrado");
                    nGastosPagados = results.getDouble("gastoscobrado");
                    nCapital = results.getDouble("saldo");
                    catraso = results.getString("di");
                    nDiasGraciaGastos = results.getInt("diasgracia_gastos");
                    int ndiamora = results.getInt("atraso");
                    natraso = results.getInt("di");
                    nTasaPunitoria = results.getDouble("interespunitorio");
                    nDiaGraciaMora = results.getInt("diasgracia");
                    nComprobante = results.getInt("comprobante");
                    nInteres = results.getDouble("interesmora");
                    nDiaCompletoMora = ndiamora - natraso;
                    if (ndiamora > 0 && catraso != null) {
                        if (natraso <= ndiamora) {
                            natraso = results.getInt("di");
                        } else {
                            natraso = ndiamora;
                        }
                    } else {
                        natraso = ndiamora;
                    }
                    //Calculamos el total de mora por todo el atraso para ver si hizo algun pago
                    //anterior a cuenta de la misma
                    //Aqui calculamos la mora anterior
                    if (natraso > nDiaGraciaMora) {
                        nMora = Math.round(nCapital * ((nInteres / 100) / 360 * natraso));
                        if (Config.nIvaIncluido == 1) {
                            nMora = Math.round(nMora + (nMora * Config.porcentajeiva / 100));
                        }
                    }
                    nMora = nMora - nMoraPagada;
                    fila[9] = formato.format(nMora);
                    fila[10] = formato.format(0);

                    if (Config.nIvaIncluido == 1) {
                        if (natraso > nDiasGraciaGastos) {
                            nImporteGastos = results.getDouble("gastoscobros");
                            nImporteGastos = nImporteGastos / nCotizacionMoneda;
                            fila[10] = formato.format(nImporteGastos);
                        }
                    } else {
                        if (natraso > nDiasGraciaGastos) {
                            if (nCapital <= 25000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 1200;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 3300;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 6600;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 7700;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 8800;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 9900;
                                }
                            }
                            if (nCapital >= 25000 && nCapital <= 50000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 2200;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 4400;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 8800;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 12100;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 14300;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 16500;
                                }
                            }
                            if (nCapital >= 50001 && nCapital <= 75000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 3300;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 6600;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 12100;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 15200;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 16500;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 18700;
                                }
                            }
                            if (nCapital >= 75001 && nCapital <= 100000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 4400;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 8800;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 14300;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 16500;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 18700;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 20900;
                                }
                            }
                            if (nCapital >= 100001 && nCapital <= 125000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 5500;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 11000;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 16500;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 18700;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 20900;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 23100;
                                }
                            }
                            if (nCapital >= 125001 && nCapital <= 150000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 6600;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 13200;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 18700;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 20900;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 23100;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 25300;
                                }
                            }
                            if (nCapital >= 150001 && nCapital <= 175000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 7700;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 15400;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 20900;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 23100;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 25300;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 27500;
                                }
                            }
                            if (nCapital >= 175001 && nCapital <= 200000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 8800;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 17600;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 23100;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 25300;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 27500;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 29700;
                                }
                            }
                            if (nCapital >= 200001 && nCapital <= 250000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9500;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 19800;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 25300;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 27500;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 29700;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 31900;
                                }
                            }

                            if (nCapital >= 250001 && nCapital <= 300000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 20400;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 28800;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 29600;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 31900;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 34100;
                                }
                            }
                            if (nCapital >= 300001 && nCapital <= 400000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 22800;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 31200;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 32000;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 34100;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 36300;
                                }
                            }
                            if (nCapital >= 400001 && nCapital <= 500000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 25200;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 33600;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 34400;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 36300;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 38500;
                                }
                            }
                            if (nCapital >= 500001 && nCapital <= 600000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 27600;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 34500;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 35000;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 38500;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 40700;
                                }
                            }
                            if (nCapital >= 600001 && nCapital <= 700000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 30000;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 36000;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 36500;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 40700;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 42900;
                                }
                            }
                            if (nCapital >= 700001 && nCapital <= 800000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 32300;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 37500;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 38000;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 42900;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 45100;
                                }
                            }
                            if (nCapital >= 800001 && nCapital <= 900000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 33700;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 39100;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 39500;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 45100;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 47300;
                                }
                            }
                            if (nCapital >= 900001 && nCapital <= 1000000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 35200;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 40500;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 41000;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 47300;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 49500;
                                }
                            }
                            if (nCapital >= 1000000) {
                                if (natraso <= 10) {
                                    nImporteGastos = 9900;
                                } else if (natraso >= 11 && natraso <= 30) {
                                    nImporteGastos = 36700;
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = 42000;
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = 42500;
                                } else if (natraso >= 91 && natraso <= 120) {
                                    nImporteGastos = 49500;
                                } else if (natraso >= 121) {
                                    nImporteGastos = 51700;
                                }
                            }

                            nImporteGastos = nImporteGastos / nCotizacionMoneda;
                            fila[10] = formato.format(nImporteGastos);
                        }
                    }
                    //Se calcula el interes punitorio en caso que tenga la tasa estipulada
                    if (nTasaPunitoria > 0 && nMora > 0) {
                        nPunitorio = Math.round(nMora * nTasaPunitoria / 100);
                    }
                    nPunitorio = nPunitorio - nPunitorioPagado;
                    fila[0] = results.getString("iddocumento");
                    fila[1] = results.getString("documento");
                    fila[2] = formatoFecha.format(results.getDate("fecha"));
                    fila[3] = formatoFecha.format(results.getDate("vencimiento"));
                    fila[4] = results.getString("nomalias");
                    fila[5] = results.getInt("cuota");
                    fila[6] = formato.format(results.getDouble("amortiza"));
                    fila[7] = formato.format(results.getDouble("minteres"));
                    fila[8] = formato.format(results.getDouble("saldo"));
                    fila[11] = formato.format(nCapital + nMora + nImporteGastos + nPunitorio);
                    fila[12] = formato.format(0);
                    fila[13] = formato.format(results.getDouble("capital"));
                    fila[14] = results.getDouble("inversionista");
                    fila[15] = results.getInt("numerocuota");
                    fila[16] = results.getInt("comprobante");
                    fila[17] = formato.format(natraso);
                    fila[18] = formato.format(results.getDouble("importe_iva"));
                    fila[19] = formato.format(nPunitorio);
                    modelo.addRow(fila);
                }
                jTable1.setRowSorter(new TableRowSorter(modelo));
                stm.close();
            } catch (SQLException ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Ingresar a la Consulta de Cuotas",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex2);
            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Conectarse a la Base de Datos",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex2);
            }
        }
    }

    private class GrillaSucursal extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelosucursal.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelosucursal.removeRow(0);
            }

            sucursalDAO DAOSUC = new sucursalDAO();
            try {
                for (sucursal suc : DAOSUC.todos()) {
                    String Datos[] = {String.valueOf(suc.getCodigo()), suc.getNombre()};
                    modelosucursal.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablasucursal.setRowSorter(new TableRowSorter(modelosucursal));
            int cantFilas = tablasucursal.getRowCount();
        }
    }

    private class GrillaCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todos()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getDireccion(), cli.getRuc()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class GrillaMoneda extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }
            monedaDAO DAOMON = new monedaDAO();
            try {
                for (moneda mo : DAOMON.todos()) {
                    String Datos[] = {String.valueOf(mo.getCodigo()), mo.getNombre(), formato.format(mo.getVenta())};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private class GrillaCaja extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocaja.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocaja.removeRow(0);
            }
            cajaDAO DAOCAJA = new cajaDAO();
            try {
                for (caja ca : DAOCAJA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre(), formato.format(ca.getRecibo() + 1)};
                    modelocaja.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacaja.setRowSorter(new TableRowSorter(modelocaja));
            int cantFilas = tablacaja.getRowCount();
        }
    }

    private class GrillaCobrador extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocobrador.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocobrador.removeRow(0);
            }
            cobradorDAO COBCAJA = new cobradorDAO();
            try {
                for (cobrador co : COBCAJA.todos()) {
                    String Datos[] = {String.valueOf(co.getCodigo()), co.getNombre()};
                    modelocobrador.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacobrador.setRowSorter(new TableRowSorter(modelocobrador));
            int cantFilas = tablacobrador.getRowCount();
        }
    }

}

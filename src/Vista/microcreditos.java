/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ControlGrabado;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.clienteDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.giraduriaDAO;
import DAO.prestamoDAO;
import DAO.cuenta_clienteDAO;
import DAO.monedaDAO;
import DAO.sucursalDAO;
import DAO.vendedorDAO;
import Modelo.Tablas;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.giraduria;
import Modelo.moneda;
import Modelo.prestamo;
import Modelo.sucursal;
import Modelo.vendedor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
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
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class microcreditos extends javax.swing.JFrame {

    String referencia = null;
    Conexion con = null;
    Statement stm = null;
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelorequerimiento = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelovendedor = new Tablas();
    Tablas modelocomprobante = new Tablas();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrogira, trsfiltrocli, trsfiltrocomprobante, trsfiltrovendedor;
    ObtenerFecha ODate = new ObtenerFecha();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    String cTotalImprimir = null;
    String cCliente, cReferencia, cSql = null;
    String cCodigoGastos, idPrestamo = null;
    Calendar c2 = new GregorianCalendar();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // FORMATO DE FECHA
    DecimalFormat formatea = new DecimalFormat("###,###.##");

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconoitemnuevo = new ImageIcon("src/Iconos/pencil_add.png");
    ImageIcon iconoitemupdate = new ImageIcon("src/Iconos/pencil.png");
    ImageIcon iconoitemdelete = new ImageIcon("src/Iconos/pencil_delete.png");

    public microcreditos() {
        initComponents();
        this.venceanterior.setVisible(false);
        this.vencimientos.setVisible(false);
        this.BotonSolicitar.setIcon(icononuevo);
        this.BotonEmitir.setIcon(iconoeditar);
        this.BotonAnular.setIcon(iconoborrar);
        this.BuscarSocio.setIcon(iconobuscar);
        this.BuscarAsesor.setIcon(iconobuscar);
        this.BuscarPrestamo.setIcon(iconobuscar);
        this.BotonGrabar.setIcon(iconograbar);
        this.BotonSalir.setIcon(iconosalir);
        this.Salir.setIcon(iconosalir);
        this.tablamicrocredito.setShowGrid(false);
        this.tablamicrocredito.setOpaque(true);
        this.tablamicrocredito.setBackground(new Color(102, 204, 255));
        this.tablamicrocredito.setForeground(Color.BLACK);
        this.creferencia.setVisible(false);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.Opciones.setVisible(false);

        this.cargarTitulo();
        this.TitVendedor();
        this.TitGir();
        this.Inicializar();
        this.cargarTabla();
        this.TitClie();
        this.TituloComprobante();
        ///SOCIOS
        GrillaSocio grillacl = new GrillaSocio();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();
        ///TIPO DE PRESTAMOS
        GrillaPrestamo grillapr = new GrillaPrestamo();
        Thread hilopr = new Thread(grillapr);
        hilopr.start();
        ///ASESORES
        GrillaAsesor grillaas = new GrillaAsesor();
        Thread hiloas = new Thread(grillaas);
        hiloas.start();

    }

    Control hand = new Control();

    private void ImprimirDocumentos(String reporte) throws SQLException {
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            int nFila = tablamicrocredito.getSelectedRow();

            numero_a_letras numero = new numero_a_letras();

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);

            String num = tablamicrocredito.getValueAt(nFila, 7).toString();
            num = num.replace(".", "").replace(",", ".");
            parameters.put("Letra", numero.Convertir(num, true, 1));

            num = tablamicrocredito.getValueAt(nFila, 14).toString();
            num = num.replace(".", "").replace(",", ".");

            parameters.put("LetraImporteCuota", numero.Convertir(num, true, 1));
            parameters.put("LetraCuota", numero.Convertir("8", true, 1));

            parameters.put("cNumero", tablamicrocredito.getValueAt(nFila, 1).toString());
            parameters.put("cImportePrestamo", tablamicrocredito.getValueAt(nFila, 7).toString());
            parameters.put("cImporteCuota", tablamicrocredito.getValueAt(nFila, 14).toString());

            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            JasperReport jr = null;

            URL url = getClass().getClassLoader().getResource("Reports/" + reporte.trim());
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos

            masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
            JasperPrintManager.printReport(masterPrint, false);

        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(null, "No puede emitirse el Reporte");
            System.out.println(e);
        }        // TODO add your handling code here:
    }

    private void TitVendedor() {
        modelovendedor.addColumn("Código");
        modelovendedor.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelovendedor.getColumnCount(); i++) {
            tablavendedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablavendedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablavendedor.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablavendedor.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablavendedor.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitGir() {
        modelogiraduria.addColumn("Código");
        modelogiraduria.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelogiraduria.getColumnCount(); i++) {
            tablagiraduria.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablagiraduria.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablagiraduria.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablagiraduria.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablagiraduria.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TituloComprobante() {
        modelocomprobante.addColumn("Código");
        modelocomprobante.addColumn("Nombre");
        modelocomprobante.addColumn("Tasa");

        int[] anchos = {90, 200, 90};
        for (int i = 0; i < modelocomprobante.getColumnCount(); i++) {
            tablacomprobante.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacomprobante.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacomprobante.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacomprobante.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacomprobante.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablacomprobante.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
    }

    private void limpiar() {
        this.numeroprestamo.setText("0");
        this.tipoprestamo.setText("0");
        this.nombreprestamo.setText("");
        this.fecha.setCalendar(c2);
        this.primervencimiento.setCalendar(c2);
        this.socio.setText("0");
        this.nombresocio.setText("");
        this.importesolicitado.setText("0");
        this.tasa.setText("0");
        this.asesor.setText("0");
        this.nombreasesor.setText("");

        this.capitalizacion.setText("0");
        this.interesadevengar.setText("0");
        this.gastos.setText("0");
        this.seguro.setText("0");
        this.aporte.setText("0");
        this.solidaridad.setText("0");
        this.cobrador.setText("0");
        this.fondo.setText("0");
        this.totalprestamo.setText("0");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genfacturaotros = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        salirfactura2 = new javax.swing.JButton();
        nroprestamo2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        fechaprestamo2 = new com.toedter.calendar.JDateChooser();
        grabarfactura2 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        nombrecliente2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        moneda2 = new javax.swing.JTextField();
        lblgastos = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        nrofactura2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacion2 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        interesordinario = new javax.swing.JFormattedTextField();
        capitalafacturar = new javax.swing.JFormattedTextField();
        lblgastos1 = new javax.swing.JLabel();
        gastosadministrativos = new javax.swing.JFormattedTextField();
        lblgastos2 = new javax.swing.JLabel();
        nrotimbrado = new javax.swing.JTextField();
        vencetimbrado = new com.toedter.calendar.JDateChooser();
        importefactura = new javax.swing.JFormattedTextField();
        nrotimbrado1 = new javax.swing.JTextField();
        debito = new javax.swing.JDialog();
        labelTask1 = new org.edisoncor.gui.label.LabelTask();
        jPanel7 = new javax.swing.JPanel();
        codgiraduria = new javax.swing.JTextField();
        nombregiraduria = new javax.swing.JTextField();
        nrocuenta = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        buscarcodgiraduria = new javax.swing.JButton();
        BotonConfirma = new javax.swing.JButton();
        SalirDebito = new javax.swing.JButton();
        numero = new javax.swing.JTextField();
        creferencia = new javax.swing.JTextField();
        autorizacion = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        BGiraduria = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        jTBuscarGiraduria = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablagiraduria = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        solicitud = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        numeroprestamo = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        socio = new javax.swing.JTextField();
        BuscarSocio = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        importesolicitado = new javax.swing.JFormattedTextField();
        nombresocio = new javax.swing.JTextField();
        tasa = new javax.swing.JFormattedTextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        asesor = new javax.swing.JTextField();
        BuscarAsesor = new javax.swing.JButton();
        nombreasesor = new javax.swing.JTextField();
        importecuota = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        tipoprestamo = new javax.swing.JTextField();
        BuscarPrestamo = new javax.swing.JButton();
        nombreprestamo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        primervencimiento = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        capitalizacion = new javax.swing.JFormattedTextField();
        interesadevengar = new javax.swing.JFormattedTextField();
        gastos = new javax.swing.JFormattedTextField();
        seguro = new javax.swing.JFormattedTextField();
        aporte = new javax.swing.JFormattedTextField();
        solidaridad = new javax.swing.JFormattedTextField();
        cobrador = new javax.swing.JFormattedTextField();
        fondo = new javax.swing.JFormattedTextField();
        totalprestamo = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        BotonGrabar = new javax.swing.JButton();
        BotonSalir = new javax.swing.JButton();
        BCliente = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BPrestamo = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combocomprobante = new javax.swing.JComboBox();
        jTBuscarComprobante = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablacomprobante = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarComprobante = new javax.swing.JButton();
        SalirComprobante = new javax.swing.JButton();
        BVendedor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combovendedor = new javax.swing.JComboBox();
        jTBuscarVendedor = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablavendedor = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        BotonEmitir = new javax.swing.JButton();
        BotonSolicitar = new javax.swing.JButton();
        BotonAnular = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        Opciones = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        Refrescar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        BotonAprobar = new javax.swing.JButton();
        BotonGestionar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        venceanterior = new com.toedter.calendar.JDateChooser();
        vencimientos = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablamicrocredito = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setText("N° Préstamo");

        salirfactura2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        salirfactura2.setText("Salir");
        salirfactura2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirfactura2ActionPerformed(evt);
            }
        });

        nroprestamo2.setEditable(false);
        nroprestamo2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel20.setText("Fecha");

        fechaprestamo2.setBackground(new java.awt.Color(255, 255, 255));
        fechaprestamo2.setForeground(new java.awt.Color(255, 255, 255));
        fechaprestamo2.setEnabled(false);

        grabarfactura2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabarfactura2.setText("Grabar Factura");
        grabarfactura2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarfactura2ActionPerformed(evt);
            }
        });

        jLabel21.setText("Nombre Cliente");

        nombrecliente2.setEditable(false);
        nombrecliente2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel22.setText("Moneda");

        moneda2.setEditable(false);
        moneda2.setForeground(new java.awt.Color(255, 0, 0));

        lblgastos.setText("Interés Ordinario");

        jLabel24.setText("Nº Factura");

        nrofactura2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        observacion2.setColumns(20);
        observacion2.setRows(5);
        jScrollPane3.setViewportView(observacion2);

        jButton7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton7.setText("Imprimir Factura");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel25.setText("Observaciones al píe del Comprobante");

        interesordinario.setEditable(false);
        interesordinario.setForeground(new java.awt.Color(255, 0, 0));
        interesordinario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        interesordinario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        capitalafacturar.setEditable(false);
        capitalafacturar.setForeground(new java.awt.Color(255, 0, 0));
        capitalafacturar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        capitalafacturar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lblgastos1.setText("Capital");

        gastosadministrativos.setEditable(false);
        gastosadministrativos.setForeground(new java.awt.Color(255, 0, 0));
        gastosadministrativos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gastosadministrativos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lblgastos2.setText("Gastos Administrativos");

        nrotimbrado.setEditable(false);
        nrotimbrado.setEnabled(false);

        vencetimbrado.setBackground(new java.awt.Color(255, 255, 255));
        vencetimbrado.setForeground(new java.awt.Color(255, 255, 255));
        vencetimbrado.setEnabled(false);

        importefactura.setEditable(false);
        importefactura.setForeground(new java.awt.Color(255, 0, 0));
        importefactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        importefactura.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        nrotimbrado1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(grabarfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(salirfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblgastos1)
                                .addComponent(jLabel22))
                            .addComponent(capitalafacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(fechaprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(moneda2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(lblgastos))
                                            .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(gastosadministrativos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblgastos2)
                                                    .addComponent(importefactura, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(nrotimbrado1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(nombrecliente2))
                                .addGap(88, 88, 88))))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nrotimbrado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneda2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblgastos2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblgastos1)
                        .addComponent(lblgastos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capitalafacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gastosadministrativos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(importefactura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salirfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grabarfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        javax.swing.GroupLayout genfacturaotrosLayout = new javax.swing.GroupLayout(genfacturaotros.getContentPane());
        genfacturaotros.getContentPane().setLayout(genfacturaotrosLayout);
        genfacturaotrosLayout.setHorizontalGroup(
            genfacturaotrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        genfacturaotrosLayout.setVerticalGroup(
            genfacturaotrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        labelTask1.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "actualizar_web.labelTask1.text")); // NOI18N
        labelTask1.setDescription(org.openide.util.NbBundle.getMessage(microcreditos.class, "actualizar_web.labelTask1.description")); // NOI18N

        codgiraduria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codgiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codgiraduriaActionPerformed(evt);
            }
        });

        nombregiraduria.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        nrocuenta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nrocuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrocuentaKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Giraduría");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Descripción");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("N° Cuenta");

        buscarcodgiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcodgiraduriaActionPerformed(evt);
            }
        });

        BotonConfirma.setText("Confirmar Débito Automático");
        BotonConfirma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonConfirmaActionPerformed(evt);
            }
        });

        SalirDebito.setText("Salir");
        SalirDebito.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirDebitoActionPerformed(evt);
            }
        });

        numero.setEnabled(false);

        creferencia.setEnabled(false);

        autorizacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        autorizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                autorizacionKeyPressed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("N° Autorización");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(BotonConfirma)
                        .addGap(68, 68, 68)
                        .addComponent(SalirDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel1)
                            .addComponent(jLabel29)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(autorizacion)
                                .addGap(31, 31, 31)
                                .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(codgiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(buscarcodgiraduria))
                                .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(numero, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nrocuenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codgiraduria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarcodgiraduria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nrocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(19, 19, 19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(autorizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonConfirma)
                    .addComponent(SalirDebito))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout debitoLayout = new javax.swing.GroupLayout(debito.getContentPane());
        debito.getContentPane().setLayout(debitoLayout);
        debitoLayout.setHorizontalGroup(
            debitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTask1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        debitoLayout.setVerticalGroup(
            debitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(debitoLayout.createSequentialGroup()
                .addComponent(labelTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BGiraduria.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BGiraduria.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combogiraduria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combogiraduria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combogiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combogiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combogiraduriaActionPerformed(evt);
            }
        });

        jTBuscarGiraduria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarGiraduria.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarGiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarGiraduriaActionPerformed(evt);
            }
        });
        jTBuscarGiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarGiraduriaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablagiraduria.setModel(modelogiraduria);
        tablagiraduria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablagiraduriaMouseClicked(evt);
            }
        });
        tablagiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablagiraduriaKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablagiraduria);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout BGiraduriaLayout = new javax.swing.GroupLayout(BGiraduria.getContentPane());
        BGiraduria.getContentPane().setLayout(BGiraduriaLayout);
        BGiraduriaLayout.setHorizontalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BGiraduriaLayout.setVerticalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Solicitud de Crédito N°");

        numeroprestamo.setEditable(false);
        numeroprestamo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        numeroprestamo.setForeground(new java.awt.Color(0, 0, 255));
        numeroprestamo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        numeroprestamo.setBorder(null);
        numeroprestamo.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        numeroprestamo.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numeroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addComponent(numeroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        fecha.setBackground(new java.awt.Color(255, 255, 255));
        fecha.setForeground(new java.awt.Color(255, 255, 255));
        fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaKeyPressed(evt);
            }
        });

        jLabel35.setText("Fecha");

        jLabel36.setText("Socio");

        socio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        socio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                socioActionPerformed(evt);
            }
        });
        socio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                socioKeyPressed(evt);
            }
        });

        BuscarSocio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarSocioActionPerformed(evt);
            }
        });

        jLabel37.setText("Importe");

        importesolicitado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importesolicitado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importesolicitado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importesolicitadoActionPerformed(evt);
            }
        });
        importesolicitado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importesolicitadoKeyPressed(evt);
            }
        });

        nombresocio.setEditable(false);
        nombresocio.setEnabled(false);

        tasa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        tasa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tasaKeyPressed(evt);
            }
        });

        jLabel47.setText("Tasa");

        jLabel4.setText("Asesor");

        asesor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        asesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asesorActionPerformed(evt);
            }
        });
        asesor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                asesorKeyPressed(evt);
            }
        });

        BuscarAsesor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarAsesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarAsesorActionPerformed(evt);
            }
        });

        nombreasesor.setEditable(false);
        nombreasesor.setEnabled(false);

        importecuota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importecuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importecuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importecuotaKeyPressed(evt);
            }
        });

        jLabel5.setText("Cuota");

        tipoprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tipoprestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoprestamoActionPerformed(evt);
            }
        });

        BuscarPrestamo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPrestamoActionPerformed(evt);
            }
        });

        nombreprestamo.setEditable(false);
        nombreprestamo.setEnabled(false);

        jLabel6.setText("Tipo");

        jLabel38.setText("1ra. Cuota");

        primervencimiento.setBackground(new java.awt.Color(255, 255, 255));
        primervencimiento.setForeground(new java.awt.Color(255, 255, 255));
        primervencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primervencimientoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(12, 12, 12)
                        .addComponent(primervencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(nombreprestamo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addComponent(asesor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BuscarAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(nombreasesor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel47))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(nombresocio, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel37)
                            .addGap(27, 27, 27)
                            .addComponent(importesolicitado, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(socio, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(BuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(primervencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombresocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importesolicitado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(asesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreasesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel39.setText("Capitalización de Aporte");

        jLabel40.setText("Interes a Devengar");

        jLabel41.setText("Gastos Administrativos");

        jLabel42.setText("Seguro");

        jLabel43.setText("Aporte");

        jLabel44.setText("Solidaridad");

        jLabel45.setText("Servicio Cobrador");

        jLabel46.setText("Fondo Protección Crédito");

        capitalizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        capitalizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        capitalizacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                capitalizacionFocusLost(evt);
            }
        });

        interesadevengar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        interesadevengar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        interesadevengar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                interesadevengarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                interesadevengarFocusLost(evt);
            }
        });

        gastos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        gastos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gastos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                gastosFocusLost(evt);
            }
        });

        seguro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        seguro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        seguro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                seguroFocusLost(evt);
            }
        });

        aporte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        aporte.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aporte.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                aporteFocusLost(evt);
            }
        });

        solidaridad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        solidaridad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        solidaridad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                solidaridadFocusLost(evt);
            }
        });

        cobrador.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        cobrador.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobrador.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cobradorFocusLost(evt);
            }
        });

        fondo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        fondo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        fondo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fondoFocusLost(evt);
            }
        });

        totalprestamo.setEditable(false);
        totalprestamo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        totalprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalprestamo.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        totalprestamo.setEnabled(false);

        jLabel2.setText("Total Préstamo");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42)
                    .addComponent(jLabel43)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalprestamo, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(cobrador)
                    .addComponent(solidaridad)
                    .addComponent(aporte)
                    .addComponent(seguro)
                    .addComponent(gastos)
                    .addComponent(interesadevengar)
                    .addComponent(capitalizacion))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(capitalizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(interesadevengar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(gastos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42)
                    .addComponent(seguro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(solidaridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonGrabar.setText("Grabar");
        BotonGrabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGrabarActionPerformed(evt);
            }
        });

        BotonSalir.setText("Salir");
        BotonSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(BotonGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(BotonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonGrabar)
                    .addComponent(BotonSalir))
                .addContainerGap())
        );

        javax.swing.GroupLayout solicitudLayout = new javax.swing.GroupLayout(solicitud.getContentPane());
        solicitud.getContentPane().setLayout(solicitudLayout);
        solicitudLayout.setHorizontalGroup(
            solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        solicitudLayout.setVerticalGroup(
            solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(solicitudLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        javax.swing.GroupLayout BClienteLayout = new javax.swing.GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BPrestamo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BPrestamo.setTitle("Tipo de Préstamo");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocomprobante.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocomprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocomprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocomprobanteActionPerformed(evt);
            }
        });

        jTBuscarComprobante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarComprobanteActionPerformed(evt);
            }
        });
        jTBuscarComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarComprobanteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacomprobante.setModel(modelocomprobante);
        tablacomprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacomprobanteMouseClicked(evt);
            }
        });
        tablacomprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacomprobanteKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablacomprobante);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarComprobante.setText("Aceptar");
        AceptarComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarComprobanteActionPerformed(evt);
            }
        });

        SalirComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirComprobante.setText("Salir");
        SalirComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirComprobanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarComprobante)
                    .addComponent(SalirComprobante))
                .addContainerGap())
        );

        javax.swing.GroupLayout BPrestamoLayout = new javax.swing.GroupLayout(BPrestamo.getContentPane());
        BPrestamo.getContentPane().setLayout(BPrestamoLayout);
        BPrestamoLayout.setHorizontalGroup(
            BPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPrestamoLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BPrestamoLayout.setVerticalGroup(
            BPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPrestamoLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BVendedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BVendedor.setTitle("null");

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combovendedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combovendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combovendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combovendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combovendedorActionPerformed(evt);
            }
        });

        jTBuscarVendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarVendedor.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarVendedorActionPerformed(evt);
            }
        });
        jTBuscarVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarVendedorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(combovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablavendedor.setModel(modelovendedor);
        tablavendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablavendedorMouseClicked(evt);
            }
        });
        tablavendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablavendedorKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablavendedor);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(microcreditos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarVendedor)
                    .addComponent(SalirVendedor))
                .addContainerGap())
        );

        javax.swing.GroupLayout BVendedorLayout = new javax.swing.GroupLayout(BVendedor.getContentPane());
        BVendedor.getContentPane().setLayout(BVendedorLayout);
        BVendedorLayout.setHorizontalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BVendedorLayout.setVerticalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Microcréditos");
        setName("frame_clientes"); // NOI18N
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        BotonEmitir.setBackground(new java.awt.Color(255, 255, 255));
        BotonEmitir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonEmitir.setText("Imprimir");
        BotonEmitir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonEmitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEmitirActionPerformed(evt);
            }
        });

        BotonSolicitar.setBackground(new java.awt.Color(255, 255, 255));
        BotonSolicitar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonSolicitar.setText("Agregar");
        BotonSolicitar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSolicitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSolicitarActionPerformed(evt);
            }
        });

        BotonAnular.setBackground(new java.awt.Color(255, 255, 255));
        BotonAnular.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonAnular.setText("Anular");
        BotonAnular.setToolTipText("");
        BotonAnular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAnularActionPerformed(evt);
            }
        });

        Salir.setBackground(new java.awt.Color(255, 255, 255));
        Salir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Salir.setText(" Salir");
        Salir.setToolTipText("");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        Opciones.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar entre los Días"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(FechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        Refrescar.setText("Refrescar");
        Refrescar.setActionCommand("Filtrar");
        Refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        jButton1.setText("Editar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        BotonAprobar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonAprobar.setText("Aprobar");
        BotonAprobar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        BotonGestionar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonGestionar.setText("Emitir Solicitud");
        BotonGestionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton2.setText("Orden de Pago");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        venceanterior.setEnabled(false);

        vencimientos.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotonAnular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonEmitir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BotonSolicitar, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BotonAprobar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BotonGestionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Opciones, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Refrescar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BotonSolicitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonAprobar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonGestionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(8, 8, 8)
                .addComponent(BotonEmitir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonAnular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Salir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vencimientos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(venceanterior, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        tablamicrocredito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablamicrocredito.setModel(modelo);
        tablamicrocredito.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablamicrocredito.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablamicrocredito.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablamicrocredito.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablamicrocreditoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablamicrocreditoFocusLost(evt);
            }
        });
        tablamicrocredito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablamicrocreditoMouseClicked(evt);
            }
        });
        tablamicrocredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablamicrocreditoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablamicrocredito);

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setText("Microcréditos");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del Socio", "N° de Operación", "Estado" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setSelectionColor(new java.awt.Color(0, 63, 62));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(371, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        this.jTextField1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTextField1.getText()).toUpperCase();
                jTextField1.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 3;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 12;
                        break;//por Estado
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablamicrocredito.getModel());
        tablamicrocredito.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void BotonSolicitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSolicitarActionPerformed
        this.limpiar();
        solicitud.setModal(true);
        solicitud.setSize(493, 440);
        //Establecemos un título para el jDialog
        solicitud.setLocationRelativeTo(null);
        solicitud.setVisible(true);
        socio.requestFocus();
    }//GEN-LAST:event_BotonSolicitarActionPerformed


    private void tablamicrocreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamicrocreditoKeyPressed
        int nFila = this.tablamicrocredito.getSelectedRow();
        this.Opciones.setText(this.tablamicrocredito.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamicrocreditoKeyPressed

    private void tablamicrocreditoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamicrocreditoMouseClicked
        int nFila = this.tablamicrocredito.getSelectedRow();
        this.Opciones.setText(this.tablamicrocredito.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamicrocreditoMouseClicked

    private void BotonEmitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEmitirActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir los Documentos? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            try {
                this.ImprimirDocumentos("pagare_pablo_bogarin.jasper");
                this.ImprimirDocumentos("contrato_pablo_bogarin.jasper");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }//GEN-LAST:event_BotonEmitirActionPerformed

    private void tablamicrocreditoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablamicrocreditoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamicrocreditoFocusGained

    private void jScrollPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if (ControlGrabado.REGISTRO_GRABADO == "SI") {
            this.cargarTabla();
            ControlGrabado.REGISTRO_GRABADO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void BotonAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAnularActionPerformed
        int nFila = this.tablamicrocredito.getSelectedRow();
        String cNumero = this.tablamicrocredito.getValueAt(nFila, 1).toString();
        String cReferencia = this.tablamicrocredito.getValueAt(nFila, 0).toString();
        if (Config.cNivelUsuario.equals("1")) {
            if (!this.Opciones.getText().isEmpty()) {
                cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                BDConexion BD = new BDConexion();
                BD.borrarRegistro("detalle_prestamo", "nprestamo=" + cNumero);
                BD.BorrarDetalles("prestamos", "numero=" + cNumero);
                try {
                    ctaDAO.borrarDetalleCuenta(cReferencia);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                this.cargarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar un Préstamo");
            return;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonAnularActionPerformed

    private void tablamicrocreditoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablamicrocreditoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamicrocreditoFocusLost

    private void RefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarActionPerformed
        this.cargarTabla();
        // TODO add your handling code here:
    }//GEN-LAST:event_RefrescarActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        cReferencia = "";
        cTotalImprimir = "";
        int nFila = this.tablamicrocredito.getSelectedRow();
        cReferencia = (this.tablamicrocredito.getValueAt(nFila, 0).toString());
        String cSqlControl2 = "SELECT creferencia,totalneto FROM cabecera_ventas WHERE creferencia='" + cReferencia.toString() + "'";
        try {
            results = stm.executeQuery(cSqlControl2);
            while (results.next()) {
                cTotalImprimir = results.getString("totalneto");
                importefactura.setText(results.getString("totalneto"));
            }
            results.close();
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        System.out.println("Total Imprimir " + cTotalImprimir);
        if (cTotalImprimir == null) {
            JOptionPane.showMessageDialog(null, "La Factura aún no fue Generada");
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Factura? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            ImprimirFactura GenerarFactura = new ImprimirFactura();
            Thread HiloReporte = new Thread(GenerarFactura);
            HiloReporte.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void grabarfactura2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarfactura2ActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Factura por Gastos? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
//            GrabarDetalleOtros GrabarGastos = new GrabarDetalleOtros();
            GrabarDetalleAsolac GrabarGastos = new GrabarDetalleAsolac();
            Thread HiloGrabarGastos = new Thread(GrabarGastos);
            HiloGrabarGastos.start();
            JOptionPane.showMessageDialog(null, "Factura Generada con Éxito");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_grabarfactura2ActionPerformed

    private void salirfactura2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfactura2ActionPerformed
        this.genfacturaotros.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirfactura2ActionPerformed

    private void SalirDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirDebitoActionPerformed
        debito.setModal(false);
        debito.setVisible(false);
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirDebitoActionPerformed

    private void buscarcodgiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcodgiraduriaActionPerformed
        giraduriaDAO girDAO = new giraduriaDAO();
        giraduria gi = null;
        try {
            gi = girDAO.buscarId(Integer.valueOf(this.codgiraduria.getText()));
            if (gi.getCodigo() == 0) {
                GrillaBGiraduria grillagi = new GrillaBGiraduria();
                Thread hilogi = new Thread(grillagi);
                hilogi.start();
                BGiraduria.setModal(true);
                BGiraduria.setSize(482, 575);
                BGiraduria.setLocationRelativeTo(null);
                BGiraduria.setVisible(true);
                BGiraduria.setTitle("Buscar Giraduria");
                nrocuenta.requestFocus();
                BGiraduria.setModal(false);
            } else {
                nombregiraduria.setText(gi.getNombre());
                //Establecemos un título para el jDialog
                nrocuenta.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcodgiraduriaActionPerformed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    public void filtrogira(int nNumeroColumna) {
        trsfiltrogira.setRowFilter(RowFilter.regexFilter(this.jTBuscarGiraduria.getText(), nNumeroColumna));
    }


    private void jTBuscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaActionPerformed
        this.jTBuscarGiraduria.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarGiraduria.getText()).toUpperCase();
                jTBuscarGiraduria.setText(cadena);
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
                filtrogira(indiceColumnaTabla);
            }
        });
        trsfiltrogira = new TableRowSorter(tablagiraduria.getModel());
        tablagiraduria.setRowSorter(trsfiltrogira);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaActionPerformed

    private void jTBuscarGiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaKeyPressed

    private void tablagiraduriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablagiraduriaMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaMouseClicked

    private void tablagiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablagiraduriaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablagiraduria.getSelectedRow();
        this.codgiraduria.setText(this.tablagiraduria.getValueAt(nFila, 0).toString());
        this.nombregiraduria.setText(this.tablagiraduria.getValueAt(nFila, 1).toString());

        this.BGiraduria.setVisible(false);
        this.jTBuscarGiraduria.setText("");
        this.nrocuenta.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BGiraduria.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void BotonConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonConfirmaActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            prestamoDAO grabarPRE = new prestamoDAO();
            prestamo pr = new prestamo();
            giraduriaDAO giraDAO = new giraduriaDAO();
            giraduria gi = null;
            try {
                gi = giraDAO.buscarId(Integer.valueOf(this.codgiraduria.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            pr.setGiraduria(gi);
            pr.setNrocuenta(nrocuenta.getText());
            pr.setNumero(Integer.valueOf(numero.getText()));

            cuenta_clientes cta = new cuenta_clientes();
            cta.setCreferencia(creferencia.getText());
            cta.setGiraduria(gi);
            cta.setNrocuenta(nrocuenta.getText());
            cta.setAutorizacion(autorizacion.getText());
            cuenta_clienteDAO GrabarCta = new cuenta_clienteDAO();
            try {
                grabarPRE.ActualizarPrestamoDebitoAutomatico(pr);
                GrabarCta.ActualizarDebitoAutomaticoCuenta(cta);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");

        }
        debito.setModal(false);
        debito.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonConfirmaActionPerformed

    private void codgiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codgiraduriaActionPerformed
        this.buscarcodgiraduria.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codgiraduriaActionPerformed

    private void nrocuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrocuentaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.autorizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codgiraduria.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nrocuentaKeyPressed

    private void autorizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_autorizacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocuenta.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_autorizacionKeyPressed

    private void BotonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirActionPerformed
        this.solicitud.setVisible(false);
    }//GEN-LAST:event_BotonSalirActionPerformed

    private void BotonGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGrabarActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            if (Integer.valueOf(numeroprestamo.getText()) == 0) {
                UUID id = new UUID();
                referencia = UUID.crearUUID();
                creferencia.setText(referencia.substring(1, 25));
            }

            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            Date FechaVence = ODate.de_java_a_sql(primervencimiento.getDate());

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cli = null;
            comprobanteDAO coDAO = new comprobanteDAO();
            comprobante com = null;
            monedaDAO mnDAO = new monedaDAO();
            moneda mn = null;
            vendedorDAO veDAO = new vendedorDAO();
            vendedor ve = null;

            prestamo pr = new prestamo();
            prestamoDAO GrabarDAO = new prestamoDAO();

            try {
                suc = sucDAO.buscarId(1);
                cli = cliDAO.buscarId(Integer.valueOf(socio.getText()));
                com = coDAO.buscarId(Integer.valueOf(tipoprestamo.getText()));
                mn = mnDAO.buscarId(1);
                ve = veDAO.buscarId(Integer.valueOf(asesor.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            pr.setIdprestamos(creferencia.getText());
            pr.setFecha(FechaProceso);
            pr.setPrimer_vence(FechaVence);
            pr.setMoneda(mn);
            pr.setSucursal(suc);
            pr.setSocio(cli);
            pr.setPlazo(8);

            String cImporte = this.importesolicitado.getText();
            cImporte = cImporte.replace(".", "").replace(",", ".");
            pr.setImporte(new BigDecimal(cImporte));

            String cImporteCuota = this.importecuota.getText();
            cImporteCuota = cImporteCuota.replace(".", "").replace(",", ".");
            pr.setMonto_cuota(new BigDecimal(cImporteCuota));

            pr.setTipo(com);

            String cTasa = this.tasa.getText();
            cTasa = cTasa.replace(".", "").replace(",", ".");
            pr.setTasa(new BigDecimal(cTasa));

            String cInteres = this.interesadevengar.getText();
            cInteres = cInteres.replace(".", "").replace(",", ".");
            pr.setInteres(new BigDecimal(cInteres));

            String cTotalPrestamo = this.totalprestamo.getText();
            cTotalPrestamo = cTotalPrestamo.replace(".", "").replace(",", ".");
            pr.setTotalprestamo(new BigDecimal(cTotalPrestamo));

            String cGastos = this.gastos.getText();
            cGastos = cGastos.replace(".", "").replace(",", ".");
            pr.setGastos_escritura(new BigDecimal(cGastos));

            pr.setAsesor(ve);
            pr.setCodusuario(Integer.valueOf(Config.CodUsuario));
            pr.setDestino(1);

            String cEntregar = this.importesolicitado.getText();
            cEntregar = cEntregar.replace(".", "").replace(",", ".");
            pr.setEntregarneto(new BigDecimal(cEntregar));

            String cSeguro = this.seguro.getText();
            cSeguro = cSeguro.replace(".", "").replace(",", ".");
            pr.setSeguro(new BigDecimal(cSeguro));

            String cCapital = this.capitalizacion.getText();
            cCapital = cCapital.replace(".", "").replace(",", ".");
            pr.setCapitalizacion(new BigDecimal(cCapital));

            String cAporte = this.aporte.getText();
            cAporte = cAporte.replace(".", "").replace(",", ".");
            pr.setAporte(new BigDecimal(cAporte));

            String cSolidaridad = this.solidaridad.getText();
            cSolidaridad = cSolidaridad.replace(".", "").replace(",", ".");
            pr.setSolidaridad(new BigDecimal(cSolidaridad));

            String cServicio = this.cobrador.getText();
            cServicio = cServicio.replace(".", "").replace(",", ".");
            pr.setServiciocobrador(new BigDecimal(cServicio));

            String cFondo = this.fondo.getText();
            cFondo = cFondo.replace(".", "").replace(",", ".");
            pr.setFondoproteccion(new BigDecimal(cFondo));

            String detacuota = "[";
            String detalle = "[";
            Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
            calendar.setTime(primervencimiento.getDate()); // Capturamos en el setTime el valor de la fecha ingresada

            String iddoc = null;

            detacuota = "[";
            detalle = "[";
            for (int i = 1; i <= 8; i++) {
                vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                Date VenceCuota = ODate.de_java_a_sql(vencimientos.getDate());
                iddoc = UUID.crearUUID();
                iddoc = iddoc.substring(1, 25);

                String lineacuota = "{iddocumento : '" + iddoc + "',"
                        + "creferencia : '" + creferencia.getText() + "',"
                        + "documento : " + this.numeroprestamo.getText() + ","
                        + "fecha : " + FechaProceso + ","
                        + "vencimiento : " + VenceCuota + ","
                        + "cliente : " + socio.getText() + ","
                        + "sucursal: " + 1 + ","
                        + "moneda : " + 1 + ","
                        + "comprobante : " + com.getCodigo() + ","
                        + "vendedor : " + asesor.getText() + ","
                        + "idedificio : " + 1 + ","
                        + "importe : " + cImporteCuota + ","
                        + "numerocuota : " + 8 + ","
                        + "cuota : " + i + ","
                        + "saldo : " + cImporteCuota
                        + "},";

                detacuota += lineacuota;

                String lineadetalle = "{nprestamo : '" + numeroprestamo.getText() + "',"
                        + "nrocuota : '" + i + "',"
                        + "capital : " + cImporteCuota + ","
                        + "emision : " + FechaProceso + ","
                        + "vence : " + VenceCuota + ","
                        + "dias : " + 7 + ","
                        + "monto : " + cImporteCuota
                        + "},";

                detalle += lineadetalle;

                calendar.setTime(vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior
                int mes = venceanterior.getCalendar().get(Calendar.MONTH) + 1;
                int dia = venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
                calendar.add(Calendar.DAY_OF_YEAR, 7);  // numero de días a añadir, o restar en caso de días<0
                vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
            }
            if (!detacuota.equals("[")) {
                detacuota = detacuota.substring(0, detacuota.length() - 1);
            }
            detacuota += "]";

            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";

            cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();

            if (Integer.valueOf(numeroprestamo.getText()) == 0) {
                try {
                    GrabarDAO.InsertarMicroCredito(pr, detalle);
                    ctaDAO.guardarCuentaFerremax(detacuota);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                try {
                    pr.setIdprestamos(creferencia.getText());
                    pr.setNumero(Integer.valueOf(numeroprestamo.getText()));
                    ctaDAO.borrarDetalleCuenta(creferencia.getText());
                    GrabarDAO.ActualizarMicroCredito(pr, detalle);
                    ctaDAO.guardarCuentaFerremax(detacuota);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            this.BotonSalir.doClick();
            this.Refrescar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonGrabarActionPerformed

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

    public void filtrosuc(int nNumeroColumna) {
        //   trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    public void filtrocomprobante(int nNumeroColumna) {
        trsfiltrocomprobante.setRowFilter(RowFilter.regexFilter(this.jTBuscarComprobante.getText(), nNumeroColumna));
    }

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");
        modelocliente.addColumn("Dirección");
        modelocliente.addColumn("RUC");
        modelocliente.addColumn("Plazo");

        int[] anchos = {90, 150, 100, 100, 90};
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

        this.tablacliente.getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacliente.getColumnModel().getColumn(4).setMinWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);

    }


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
        this.socio.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombresocio.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        //        this.giraduria.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void BuscarSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarSocioActionPerformed
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Socio");
        BCliente.setVisible(true);
        BCliente.setModal(false);
        importesolicitado.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarSocioActionPerformed

    private void socioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_socioActionPerformed

        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.socio.getText()));
            if (cl.getCodigo() != 0) {
                nombresocio.setText(cl.getNombre());
                importesolicitado.requestFocus();
            } else {
                this.BuscarSocio.doClick();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_socioActionPerformed

    private void combocomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocomprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocomprobanteActionPerformed

    private void jTBuscarComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarComprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobanteActionPerformed

    private void jTBuscarComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarComprobanteKeyPressed
        this.jTBuscarComprobante.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarComprobante.getText()).toUpperCase();
                jTBuscarComprobante.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocomprobante.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrocomprobante(indiceColumnaTabla);
            }
        });
        trsfiltrocomprobante = new TableRowSorter(tablacomprobante.getModel());
        tablacomprobante.setRowSorter(trsfiltrocomprobante);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobanteKeyPressed


    private void tablacomprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacomprobanteMouseClicked
        this.AceptarComprobante.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteMouseClicked

    private void tablacomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacomprobanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarComprobante.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteKeyPressed

    private void AceptarComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarComprobanteActionPerformed
        int nFila = this.tablacomprobante.getSelectedRow();
        this.tipoprestamo.setText(this.tablacomprobante.getValueAt(nFila, 0).toString());
        this.nombreprestamo.setText(this.tablacomprobante.getValueAt(nFila, 1).toString());
        this.tasa.setText(this.tablacomprobante.getValueAt(nFila, 2).toString());

        this.BPrestamo.setVisible(false);
        this.jTBuscarComprobante.setText("");
        this.fecha.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarComprobanteActionPerformed

    private void SalirComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirComprobanteActionPerformed
        this.BPrestamo.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirComprobanteActionPerformed

    private void BuscarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPrestamoActionPerformed
        comprobanteDAO cmDAO = new comprobanteDAO();
        comprobante cm = null;
        try {
            cm = cmDAO.buscarIdxtipo(Integer.valueOf(this.tipoprestamo.getText()), 3);
            if (cm.getCodigo() == 0) {
                BPrestamo.setModal(true);
                BPrestamo.setSize(500, 575);
                BPrestamo.setLocationRelativeTo(null);
                BPrestamo.setVisible(true);
                BPrestamo.setTitle("Buscar Préstamo");
                BPrestamo.setModal(false);
            } else {
                nombreprestamo.setText(cm.getNombre());
                tasa.setText(formatea.format(cm.getTasainteres()));
                //Establecemos un título para el jDialog
            }
            fecha.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarPrestamoActionPerformed

    private void tipoprestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoprestamoActionPerformed
        BuscarPrestamo.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoprestamoActionPerformed

    private void importesolicitadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importesolicitadoActionPerformed
        ///CAPITAL
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.configMicroCredito();
        String cCapital = this.importesolicitado.getText();
        cCapital = cCapital.replace(".", "").replace(",", ".");
        //TASA
        String cTasa = this.tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");
        //CALCULAMOS TASA ANUAL
        double nTasaAnual = Double.valueOf(cTasa) * 12;
        //CALCULAMOS EL TOTAL DEL PRESTAMO
        this.totalprestamo.setText(formatea.format(Double.valueOf(cCapital)
                + Math.round(Double.valueOf(cCapital) * nTasaAnual / 100)));
        //CALCULAMOS EL IMPORTE DE LAS CUOTAS
        String cTotalPrestamo = this.totalprestamo.getText();
        cTotalPrestamo = cTotalPrestamo.replace(".", "").replace(",", ".");
        this.importecuota.setText(formatea.format(Math.round(Double.valueOf(cTotalPrestamo) / 8)));
        //CAPITALIZACION DE APORTE % SOBRE EL CAPITAL
        this.capitalizacion.setText(formatea.format(Math.round(Double.valueOf(cCapital)
                * config.getCapitalizacionaporte() / 100)));
        //INTERES A DEVENGAR
        // CAPITAL * TASA *2
        this.interesadevengar.setText(formatea.format(
                Math.round(Double.valueOf(cCapital)
                        * (Double.valueOf(cTasa) * 2)) / 100));
        ///GASTOS ADMINISTRATIVOS
        this.gastos.setText(formatea.format(config.getArancelgastos()));
        ///SEGURO
        this.seguro.setText(formatea.format(config.getArancelseguro()));
        ///APORTE
        this.aporte.setText(formatea.format(config.getArancelaporte()));
        ///SOLIDARIDAD
        this.solidaridad.setText(formatea.format(config.getArancelsolidaridad()));
        ///COBRADOR
        this.cobrador.setText(formatea.format(config.getArancelcobrador()));
        ///FONDO DE PROTECCION
        this.fondo.setText(formatea.format(Math.round(Double.valueOf(cCapital)
                * config.getArancelfondoproteccion().doubleValue() / 100)));
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_importesolicitadoActionPerformed

    private void Sumar() {
        String cImporte = importesolicitado.getText();
        cImporte = cImporte.replace(".", "").replace(",", ".");

        String cCapitalizacion = capitalizacion.getText();
        cCapitalizacion = cCapitalizacion.replace(".", "").replace(",", ".");

        String cInteres = interesadevengar.getText();
        cInteres = cInteres.replace(".", "").replace(",", ".");

        String cGastos = gastos.getText();
        cGastos = cGastos.replace(".", "").replace(",", ".");

        String cSeguro = seguro.getText();
        cSeguro = cSeguro.replace(".", "").replace(",", ".");

        String cAporte = aporte.getText();
        cAporte = cAporte.replace(".", "").replace(",", ".");

        String cSolidaridad = solidaridad.getText();
        cSolidaridad = cSolidaridad.replace(".", "").replace(",", ".");

        String cCobrador = cobrador.getText();
        cCobrador = cCobrador.replace(".", "").replace(",", ".");

        String cFondo = fondo.getText();
        cFondo = cFondo.replace(".", "").replace(",", ".");

        this.totalprestamo.setText(formatea.format(Double.valueOf(cImporte)
                + Double.valueOf(cCapitalizacion) + Double.valueOf(cInteres)
                + Double.valueOf(cGastos)
                + Double.valueOf(cSeguro)
                + Double.valueOf(cAporte)
                + Double.valueOf(cSolidaridad)
                + Double.valueOf(cCobrador)
                + Double.valueOf(cFondo)));

        String cTotalPrestamo = this.totalprestamo.getText();
        cTotalPrestamo = cTotalPrestamo.replace(".", "").replace(",", ".");
        this.importecuota.setText(formatea.format(Math.round(Double.valueOf(cTotalPrestamo) / 8)));
    }

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.primervencimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipoprestamo.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void primervencimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primervencimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.socio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_primervencimientoKeyPressed

    private void socioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_socioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.primervencimiento.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_socioKeyPressed

    private void importesolicitadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importesolicitadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tasa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.socio.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importesolicitadoKeyPressed

    private void tasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tasaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importecuota.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importesolicitado.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaKeyPressed

    private void importecuotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importecuotaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.asesor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tasa.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importecuotaKeyPressed

    private void asesorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_asesorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.BotonGrabar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importecuota.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorKeyPressed

    private void combovendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combovendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combovendedorActionPerformed

    private void jTBuscarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorActionPerformed

    private void jTBuscarVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarVendedorKeyPressed
        this.jTBuscarVendedor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarVendedor.getText()).toUpperCase();
                jTBuscarVendedor.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combovendedor.getSelectedIndex()) {
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
                filtrovendedor(indiceColumnaTabla);
            }
        });
        trsfiltrovendedor = new TableRowSorter(tablavendedor.getModel());
        tablavendedor.setRowSorter(trsfiltrovendedor);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorKeyPressed

    public void filtrovendedor(int nNumeroColumna) {
        trsfiltrovendedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarVendedor.getText(), nNumeroColumna));
    }


    private void tablavendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablavendedorMouseClicked
        this.AceptarVendedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorMouseClicked

    private void tablavendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablavendedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarVendedor.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorKeyPressed

    private void AceptarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarVendedorActionPerformed
        int nFila = this.tablavendedor.getSelectedRow();
        this.asesor.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
        this.nombreasesor.setText(this.tablavendedor.getValueAt(nFila, 1).toString());

        this.BVendedor.setVisible(false);
        this.jTBuscarVendedor.setText("");
        this.BotonGrabar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void BuscarAsesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarAsesorActionPerformed
        vendedorDAO veDAO = new vendedorDAO();
        vendedor vn = null;
        try {
            vn = veDAO.buscarId(Integer.valueOf(this.asesor.getText()));
            if (vn.getCodigo() == 0) {
                BVendedor.setModal(true);
                BVendedor.setSize(500, 575);
                BVendedor.setLocationRelativeTo(null);
                BVendedor.setVisible(true);
                BVendedor.setTitle("Buscar Vendedor");
                BVendedor.setModal(false);
            } else {
                nombreasesor.setText(vn.getNombre());
                //Establecemos un título para el jDialog
            }
            BotonGrabar.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarAsesorActionPerformed

    private void asesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asesorActionPerformed
        this.BuscarAsesor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.limpiar();
        int nFila = tablamicrocredito.getSelectedRow();
        creferencia.setText(tablamicrocredito.getValueAt(nFila, 0).toString());
        this.numeroprestamo.setText(tablamicrocredito.getValueAt(nFila, 1).toString());
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablamicrocredito.requestFocus();
            return;
        }
        cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
        cuenta_clientes saldo = null;
        try {
            saldo = saldoDAO.SaldoMovimiento(creferencia.getText());
            if (saldo.getDocumento() != null) {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                return;
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            prestamoDAO prDAO = new prestamoDAO();
            prestamo pr = null;
            try {
                pr = prDAO.buscarMicroCredito(Integer.valueOf(this.numeroprestamo.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (pr != null) {
                this.fecha.setDate(pr.getFecha());
                this.primervencimiento.setDate(pr.getPrimer_vence());
                this.tipoprestamo.setText(String.valueOf(pr.getTipo().getCodigo()));
                this.nombreprestamo.setText(pr.getTipo().getNombre());
                this.socio.setText(String.valueOf(pr.getSocio().getCodigo()));
                this.nombresocio.setText(pr.getSocio().getNombre());
                this.importesolicitado.setText(formatea.format(pr.getImporte()));
                this.importecuota.setText(formatea.format(pr.getMonto_cuota()));
                this.tasa.setText(formatea.format(pr.getTasa()));
                this.interesadevengar.setText(formatea.format(pr.getInteres()));
                this.totalprestamo.setText(formatea.format(pr.getTotalprestamo()));
                this.gastos.setText(formatea.format(pr.getGastos_escritura()));
                this.asesor.setText(String.valueOf(pr.getAsesor().getCodigo()));
                this.nombreasesor.setText(pr.getAsesor().getNombre());
                this.capitalizacion.setText(formatea.format(pr.getCapitalizacion()));
                this.aporte.setText(formatea.format(pr.getAporte()));
                this.solidaridad.setText(formatea.format(pr.getSolidaridad()));
                this.cobrador.setText(formatea.format(pr.getServiciocobrador()));
                this.fondo.setText(formatea.format(pr.getFondoproteccion()));
                this.seguro.setText(formatea.format(pr.getSeguro()));
                solicitud.setModal(true);
                solicitud.setSize(493, 440);
                //Establecemos un título para el jDialog
                solicitud.setLocationRelativeTo(null);
                solicitud.setVisible(true);
                tipoprestamo.requestFocus();

            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void capitalizacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_capitalizacionFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_capitalizacionFocusLost

    private void interesadevengarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_interesadevengarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_interesadevengarFocusGained

    private void interesadevengarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_interesadevengarFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_interesadevengarFocusLost

    private void gastosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gastosFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_gastosFocusLost

    private void seguroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_seguroFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_seguroFocusLost

    private void aporteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_aporteFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_aporteFocusLost

    private void solidaridadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_solidaridadFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_solidaridadFocusLost

    private void cobradorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cobradorFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_cobradorFocusLost

    private void fondoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fondoFocusLost
        this.Sumar();
        // TODO add your handling code here:
    }//GEN-LAST:event_fondoFocusLost

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Operacion");
        modelo.addColumn("Fecha");
        modelo.addColumn("Nombre del Socio");
        modelo.addColumn("Moneda");
        modelo.addColumn("Capital"); //COLUMNA 5 CAPITAL
        modelo.addColumn("Interés"); //COLUMNA 6 INTERES
        modelo.addColumn("Total Préstamo");
        modelo.addColumn("Total Desembolso");
        modelo.addColumn("Tasa");
        modelo.addColumn("Tipo Préstamo");
        modelo.addColumn("Nombre Asesor");
        modelo.addColumn("Estado");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Monto Cuota");

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();

        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.tablamicrocredito.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);

        ((DefaultTableCellRenderer) tablamicrocredito.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamicrocredito.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        this.tablamicrocredito.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablamicrocredito.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablamicrocredito.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablamicrocredito.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        int[] anchos = {3, 120, 90, 350, 100, 100, 100, 100, 100, 90, 200, 200, 100, 90, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.tablamicrocredito.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        this.tablamicrocredito.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        this.tablamicrocredito.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
    }

    public void cargarTabla() {
        //Uso la Clase SimpleDateFormat para darle formato al campo fecha
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        cSql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.socio,prestamos.tasa,";
        cSql = cSql + "clientes.nombre AS nombrecliente,vendedores.nombre AS nombreasesor,prestamos.totalprestamo,monto_cuota,";
        cSql = cSql + "importe,monedas.nombre AS nombremoneda,comprobantes.nombre AS nombreprestamo, ";
        cSql = cSql + "prestamos.interes,prestamos.monto_entregar,prestamos.estado,prestamos.ivainteres,prestamos.comision_deudor,prestamos.tipo,prestamos.idfactura,";
        cSql = cSql + "prestamos.gastos_escritura,prestamos.seguro,prestamos.monto_cuota ";
        cSql = cSql + "FROM prestamos ";
        cSql = cSql + "LEFT JOIN comprobantes ";
        cSql = cSql + "ON comprobantes.codigo=prestamos.tipo ";
        cSql = cSql + "LEFT JOIN vendedores ";
        cSql = cSql + "ON vendedores.codigo=prestamos.asesor ";
        cSql = cSql + "LEFT JOIN monedas ";
        cSql = cSql + "ON monedas.codigo=prestamos.moneda ";
        cSql = cSql + "LEFT JOIN clientes ";
        cSql = cSql + "ON clientes.codigo=prestamos.socio ";
        cSql = cSql + "WHERE prestamos.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
        cSql = cSql + " ORDER BY prestamos.fecha";

        //Instanciamos esta clase para alinear las celdas numericas a la derecha
        //Llamo a la clase conexion para conectarme a la base de datos
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje

        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        try {
            results = stm.executeQuery(cSql);
            while (results.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.

                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[15]; // Cantidad de columnas en la tabla
                fila[0] = results.getString("idprestamos");
                fila[1] = results.getString("numero");
                fila[2] = formatoFecha.format(results.getDate("fecha"));
                fila[3] = results.getString("nombrecliente");
                fila[4] = results.getString("nombremoneda");
                fila[5] = formatea.format(results.getDouble("importe"));
                fila[6] = formatea.format(results.getDouble("interes"));
                fila[7] = formatea.format(results.getDouble("totalprestamo"));
                fila[8] = formatea.format(results.getDouble("importe"));
                fila[9] = formatea.format(results.getDouble("tasa"));
                fila[10] = results.getString("nombreprestamo");
                fila[11] = results.getString("nombreasesor");
                fila[12] = results.getString("estado");
                fila[13] = results.getString("socio");
                fila[14] = formatea.format(results.getDouble("monto_cuota"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.tablamicrocredito.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = this.tablamicrocredito.getRowCount();
            if (cantFilas > 0) {
                this.BotonEmitir.setEnabled(true);
                this.BotonAnular.setEnabled(true);
            } else {
                this.BotonEmitir.setEnabled(false);
                this.BotonAnular.setEnabled(false);
            }
            stm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }

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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new microcreditos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarComprobante;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BGiraduria;
    private javax.swing.JDialog BPrestamo;
    private javax.swing.JDialog BVendedor;
    private javax.swing.JButton BotonAnular;
    private javax.swing.JButton BotonAprobar;
    private javax.swing.JButton BotonConfirma;
    private javax.swing.JButton BotonEmitir;
    private javax.swing.JButton BotonGestionar;
    private javax.swing.JButton BotonGrabar;
    private javax.swing.JButton BotonSalir;
    private javax.swing.JButton BotonSolicitar;
    private javax.swing.JButton BuscarAsesor;
    private javax.swing.JButton BuscarPrestamo;
    private javax.swing.JButton BuscarSocio;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JTextField Opciones;
    private javax.swing.JButton Refrescar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirComprobante;
    private javax.swing.JButton SalirDebito;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JFormattedTextField aporte;
    private javax.swing.JTextField asesor;
    private javax.swing.JTextField autorizacion;
    private javax.swing.JButton buscarcodgiraduria;
    private javax.swing.JFormattedTextField capitalafacturar;
    private javax.swing.JFormattedTextField capitalizacion;
    private javax.swing.JFormattedTextField cobrador;
    private javax.swing.JTextField codgiraduria;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combocomprobante;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JComboBox combovendedor;
    private javax.swing.JTextField creferencia;
    private javax.swing.JDialog debito;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechaprestamo2;
    private javax.swing.JFormattedTextField fondo;
    private javax.swing.JFormattedTextField gastos;
    private javax.swing.JFormattedTextField gastosadministrativos;
    private javax.swing.JDialog genfacturaotros;
    private javax.swing.JButton grabarfactura2;
    private javax.swing.JFormattedTextField importecuota;
    private javax.swing.JFormattedTextField importefactura;
    private javax.swing.JFormattedTextField importesolicitado;
    private javax.swing.JFormattedTextField interesadevengar;
    private javax.swing.JFormattedTextField interesordinario;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarComprobante;
    private javax.swing.JTextField jTBuscarGiraduria;
    private javax.swing.JTextField jTBuscarVendedor;
    private javax.swing.JTextField jTextField1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelTask labelTask1;
    private javax.swing.JLabel lblgastos;
    private javax.swing.JLabel lblgastos1;
    private javax.swing.JLabel lblgastos2;
    private javax.swing.JTextField moneda2;
    private javax.swing.JTextField nombreasesor;
    private javax.swing.JTextField nombrecliente2;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nombreprestamo;
    private javax.swing.JTextField nombresocio;
    private javax.swing.JTextField nrocuenta;
    private javax.swing.JTextField nrofactura2;
    private javax.swing.JTextField nroprestamo2;
    private javax.swing.JTextField nrotimbrado;
    private javax.swing.JTextField nrotimbrado1;
    private javax.swing.JTextField numero;
    private javax.swing.JTextField numeroprestamo;
    private javax.swing.JTextArea observacion2;
    private org.edisoncor.gui.panel.Panel panel1;
    private com.toedter.calendar.JDateChooser primervencimiento;
    private javax.swing.JButton salirfactura2;
    private javax.swing.JFormattedTextField seguro;
    private javax.swing.JTextField socio;
    private javax.swing.JDialog solicitud;
    private javax.swing.JFormattedTextField solidaridad;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacomprobante;
    private javax.swing.JTable tablagiraduria;
    private javax.swing.JTable tablamicrocredito;
    private javax.swing.JTable tablavendedor;
    private javax.swing.JFormattedTextField tasa;
    private javax.swing.JTextField tipoprestamo;
    private javax.swing.JFormattedTextField totalprestamo;
    private com.toedter.calendar.JDateChooser venceanterior;
    private com.toedter.calendar.JDateChooser vencetimbrado;
    private com.toedter.calendar.JDateChooser vencimientos;
    // End of variables declaration//GEN-END:variables

    private class GenerarOp extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                int nFila = tablamicrocredito.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = tablamicrocredito.getValueAt(nFila, 8).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Referencia", tablamicrocredito.getValueAt(nFila, 0).toString());
                parameters.put("Letra", numero.Convertir(num, true, 1));
                JasperReport jr = null;
                //OTRAS EMPRESAS
                URL url = getClass().getClassLoader().getResource("Reports/orden_pago_prestamos.jasper");
                //PRECIOS BAJOS    
                //URL url = getClass().getClassLoader().getResource("Reports/orden_pago_preciosbajos.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
    }

    private class GenerarPagare extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombrePagare = config.getNombrepagare();

            try {
                Map parameters = new HashMap();
                int nFila = tablamicrocredito.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = tablamicrocredito.getValueAt(nFila, 7).toString();

                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = tablamicrocredito.getValueAt(nFila, 6).toString();
                cpInteres = cpInteres.replace(".", "");
                System.out.println("Interes " + cpInteres);

                BigDecimal n1 = new BigDecimal(cpImporte);
//                BigDecimal n2 = new BigDecimal(cpInteres);
                //              n1 = n1.add(n2); // Se suma los valores 
                //double nTotal=n1;

                String num = String.valueOf(n1);
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                // parameters.put("cNombreEmpresa", "EMPRESA DE PRUEBA");
                parameters.put("nNumeroPrestamo", Opciones.getText().trim());
                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/pagares.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombrePagare.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
                stm.close();
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
    }

    private class GenerarLiquidacion extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreLiquidacion = config.getNombreliquidacion();
            try {
                Map parameters = new HashMap();
                int nFila = tablamicrocredito.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", tablamicrocredito.getValueAt(nFila, 1).toString());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacion.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
    }

    private class ImprimirFacturaGastos extends Thread {

        public void run() {
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = tablamicrocredito.getSelectedRow();
                String num = tablamicrocredito.getValueAt(nFila, 6).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class ImprimirSolicitud extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreSolicitud = config.getNombresolicitud();
            int nFila = tablamicrocredito.getSelectedRow();
            cReferencia = tablamicrocredito.getValueAt(nFila, 1).toString();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("nNumeroPrestamo", cReferencia);
                System.out.println(cReferencia);
                JasperReport jr = null;
                //Ficha de Cliente de PUERTO SEGURO
                //   URL url = getClass().getClassLoader().getResource("Reports/fichacliente.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreSolicitud.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class GrillaBGiraduria extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelogiraduria.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelogiraduria.removeRow(0);
            }
            giraduriaDAO DAOGIR = new giraduriaDAO();
            try {
                for (giraduria gi : DAOGIR.todos()) {
                    String Datos[] = {String.valueOf(gi.getCodigo()), gi.getNombre()};
                    modelogiraduria.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablagiraduria.setRowSorter(new TableRowSorter(modelogiraduria));
            int cantFilas = tablagiraduria.getRowCount();
        }
    }

    private class ImprimirFactura extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = tablamicrocredito.getSelectedRow();
                String num = importefactura.getText();
                numero_a_letras numero = new numero_a_letras();
                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cRuc", Config.cRucEmpresa);
                parameters.put("cTelefono", Config.cTelefono);
                parameters.put("cDireccion", Config.cDireccion);
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia.trim());

                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
                stm.close();
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }

    }

    private class GrabarDetalleAsolac extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha

            Date FechaProceso = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date PrimerVence = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date VenceTimbrado = ODate.de_java_a_sql(vencetimbrado.getDate());
            //Obteniendo Datos de los Combodatos
            String cmoneda = Config.cMonedaDefecto;
            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();

            String cNumeroFactura = nrofactura2.getText();
            cNumeroFactura = cNumeroFactura.replace("-", "");
            String cContadorFactura = cNumeroFactura.substring(6, 13);

            String cFactura = nrofactura2.getText();

            //SE CAPTURA CAPITAL
            String cCapital = capitalafacturar.getText();
            cCapital = cCapital.replace(".", "");
            cCapital = cCapital.replace(",", ".");

            //SE CAPTURA GASTOS ADMINISTRATIVOS
            String cGastos = gastosadministrativos.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");

            //SE CAPTURA LOS INTERESES ORDINARIOS EN CASO DE QUERER FACTURAR
            String cInteresOrdinario = interesordinario.getText();
            cInteresOrdinario = cInteresOrdinario.replace(".", "");
            cInteresOrdinario = cInteresOrdinario.replace(",", ".");
            // SE CALCULA EL TOTAL

            double TotalNeto = 0.0;
            double montoiva10 = 0.0;

            if (Config.cInteresPrestamo.isEmpty()) {
                cInteresOrdinario = "0";
            }
            if (Config.cCodColocacion.isEmpty()) {
                cGastos = "0";
            }

            TotalNeto = Double.valueOf(cGastos) + Double.valueOf(cInteresOrdinario);
            montoiva10 = TotalNeto;

            String cTotalNeto = String.valueOf(TotalNeto);
            //SE CALCULA EL IVA 10% SOLO SOBRE GASTOS E INTERESES
            String cImpiva = String.valueOf(montoiva10);

            String cExentas = "0";

            String cIva = String.valueOf(Config.nArancelIva);
            String cCotizacion = "1";
            String cIvaDetalle = "";

            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            Connection conn = null;

            try {
                conn = stm.getConnection();
                conn.setAutoCommit(false);

                String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,formatofactura,vencimiento,cliente,sucursal,moneda,giraduria,";
                cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idprestamo,idusuario,vencimientotimbrado,nrotimbrado)";
                cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cNumeroFactura + "','" + cFactura + "','" + PrimerVence + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
                cSqlCab += "64" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + cExentas + "','" + cImpiva + "','" + "0" + "','" + cTotalNeto + "','" + observacion2.getText().toString() + "','" + nroprestamo2.getText() + "','" + Config.CodUsuario + "','" + VenceTimbrado + "','" + nrotimbrado1.getText() + "')";
                try {
                    stm.executeUpdate(cSqlCab);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cCantidad = "1";
                //SE GRABA DETALLE DE 
                if (Config.cCodColocacion != null && !Config.cCodColocacion.equals("")) {
                    cIvaDetalle = String.valueOf(Math.round(Double.valueOf(cGastos) / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodColocacion + "','" + cCantidad + "','" + '0' + "','" + cGastos + "','" + cGastos + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                if (Config.cInteresPrestamo != null && !Config.cInteresPrestamo.equals("")) {
                    cIvaDetalle = String.valueOf(Math.round(Double.valueOf(cInteresOrdinario) / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cInteresPrestamo + "','" + cCantidad + "','" + '0' + "','" + cInteresOrdinario + "','" + cInteresOrdinario + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                String cSqlFactura = "UPDATE sucursales SET factura= " + Double.valueOf(cContadorFactura) + 1;
                try {
                    stm.executeUpdate(cSqlFactura);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cSqlControlFactura2 = "INSERT INTO facturaprestamos (idprestamo,idfactura,opcion) VALUES('" + cReferencia + "','" + cReferencia + "','" + '2' + "')";
                try {
                    stm.executeUpdate(cSqlControlFactura2);
                    conn.commit();
                    stm.close();
                } catch (SQLException ex) {
                    conn.rollback();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "La Factura ya fue Generada, Verifique");
                    Exceptions.printStackTrace(ex);
                }
                conn.close();
                stm.close();

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class GenerarContrato extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cContrato1 = config.getNombrecontrato1();

            try {
                Map parameters = new HashMap();
                int nFila = tablamicrocredito.getSelectedRow();
                String cMoneda = tablamicrocredito.getValueAt(nFila, 4).toString();
                String cNumeroPrestamo = tablamicrocredito.getValueAt(nFila, 1).toString();

                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = tablamicrocredito.getValueAt(nFila, 7).toString();
                String cMonto_Entregar = tablamicrocredito.getValueAt(nFila, 7).toString();
                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = tablamicrocredito.getValueAt(nFila, 6).toString();
                cpInteres = cpInteres.replace(".", "");
                System.out.println("Interes " + cpInteres);

                BigDecimal n1 = new BigDecimal(cpImporte);
//                BigDecimal n2 = new BigDecimal(cpInteres);
                //              n1 = n1.add(n2); // Se suma los valores 
                //double nTotal=n1;

                String num = String.valueOf(n1);
                numero_a_letras numero = new numero_a_letras();
                if (cMoneda.equals("GS.")) {
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                } else {
                    parameters.put("Letra", numero.Convertir(num, true, 2));
                }
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", cNumeroPrestamo.toString());
                parameters.put("cMonto_Entregar", cMonto_Entregar.toString());
                System.out.println(num);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cContrato1.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
    }

    private class GrillaSocio extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todos()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getDireccion(), cli.getRuc(), formatea.format(cli.getPlazocredito())};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class GrillaPrestamo extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocomprobante.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocomprobante.removeRow(0);
            }
            comprobanteDAO DAOcm = new comprobanteDAO();
            try {
                for (comprobante com : DAOcm.todosxtipo(3)) {
                    String Datos[] = {String.valueOf(com.getCodigo()), com.getNombre(), formatea.format(com.getTasainteres())};
                    modelocomprobante.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacomprobante.setRowSorter(new TableRowSorter(modelocomprobante));
            int cantFilas = tablacomprobante.getRowCount();
        }
    }

    private class GrillaAsesor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelovendedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelovendedor.removeRow(0);
            }
            vendedorDAO DAOve = new vendedorDAO();
            try {
                for (vendedor ve : DAOve.todosActivos()) {
                    String Datos[] = {String.valueOf(ve.getCodigo()), ve.getNombre()};
                    modelovendedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablavendedor.setRowSorter(new TableRowSorter(modelovendedor));
            int cantFilas = tablavendedor.getRowCount();
        }
    }

}

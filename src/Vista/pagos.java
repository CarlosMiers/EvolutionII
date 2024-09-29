/*1
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;

import Clases.ConvertirMayusculas;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.GenerarAsientosDAO;
import DAO.bancosDAO;
import DAO.cabecera_asientoDAO;
import DAO.config_contableDAO;
import DAO.configuracionDAO;
import DAO.cuenta_proveedoresDAO;
import DAO.detalle_forma_pagoDAO;
import DAO.detallepagoDAO;
import DAO.extraccionDAO;
import DAO.formapagoDAO;
import DAO.monedaDAO;
import DAO.pagoDAO;
import DAO.proveedorDAO;
import DAO.saldo_proveedoresDAO;
import DAO.sucursalDAO;
import Modelo.Tablas;
import Modelo.banco;
import Modelo.config_contable;
import Modelo.configuracion;
import Modelo.cuenta_proveedores;
import Modelo.detalle_forma_pago;
import Modelo.detallepago;
import Modelo.formapago;
import Modelo.moneda;
import Modelo.pago;
import Modelo.proveedor;
import Modelo.saldo_proveedores;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
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
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
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
public class pagos extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm, stm2, st, st2 = null;
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modeloproveedor = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelopagos = new Tablas();
    Tablas modeloformapago = new Tablas();
    Tablas modelobanco = new Tablas();
    Tablas modeloimprimir = new Tablas();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro;
    private TableRowSorter trsfiltro2, trsfiltromoneda, trsfiltroformapago, trsfiltrobanco, trsfiltropro;
    Date dEmision;
    Date dVence;
    Date dConfirma;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    String cSql = null;
    String cReferencia = null;
    int nPeriodo = 0;
    int nFila = 0;
    String supago = null;
    String cCuota;
    String cNumerocuota;
    int nNum = 0;
    String referencia = null;

    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // FORMATO DE FECHA
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("####");

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconorefrescar = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconoitemnuevo = new ImageIcon("src/Iconos/pencil_add.png");
    ImageIcon iconoitemupdate = new ImageIcon("src/Iconos/pencil.png");
    ImageIcon iconoitemdelete = new ImageIcon("src/Iconos/pencil_delete.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");

    public pagos() {
        initComponents();
        this.cModo.setVisible(false);
        this.totalvalores.setText("0");
        this.GrabarCobro.setIcon(iconograbar);
        this.Agregar.setIcon(icononuevo);
        this.Anular.setIcon(iconoborrar);
        this.imprimir.setIcon(iconoprint);
        this.modificar.setIcon(iconoeditar);
        this.Salir.setIcon(iconosalir);
        this.Salir3.setIcon(iconosalir);
        this.BuscaProveedor.setIcon(iconobuscar);
        this.BuscaSucursal.setIcon(iconobuscar);
        this.BuscarMoneda.setIcon(iconobuscar);
        this.BuscarFormapago.setIcon(iconobuscar);
        this.BuscarBanco.setIcon(iconobuscar);
        modo.setVisible(false);
        this.refrescar.setIcon(iconorefrescar);
        this.NewItem.setIcon(iconoitemnuevo);
        this.Upditem.setIcon(iconoitemupdate);
        this.DelItem.setIcon(iconoitemdelete);
        this.SalirPagoParcial.setIcon(iconosalir);
        this.GrabaPagoParcial.setIcon(iconograbar);

        this.idControl.setVisible(false);

        this.tablaordenes.setShowGrid(false);
        this.tablaordenes.setOpaque(true);
        this.tablaordenes.setBackground(new Color(102, 204, 255));
        this.tablaordenes.setForeground(Color.BLACK);

        GrillaProveedor grillaprov = new GrillaProveedor();
        Thread hiloproveedor = new Thread(grillaprov);
        hiloproveedor.start();

        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idcontrol.setVisible(false);
        this.cargarTitulo();
        this.CargarTitulo1();
        this.cargarTitulo2();
        this.TituloProveedor();
        this.TituloFormaPago();
        this.CargarTituloFormaPago();
        this.TituloBanco();
        this.Inicializar();
        this.cargarTabla();
        this.CargarTituloImprimir();
    }

    Control hand = new Control();

    public void CargarTituloImprimir() {
        modeloimprimir.addColumn("Cód.");
        modeloimprimir.addColumn("Forma Pago");
        modeloimprimir.addColumn("Banco");
        modeloimprimir.addColumn("Denominación");
        modeloimprimir.addColumn("N° Cheque");
        modeloimprimir.addColumn("Confirmación");
        modeloimprimir.addColumn("Importe");
        int[] anchos = {80, 100, 80, 100, 100, 100, 100};
        for (int i = 0; i < modeloimprimir.getColumnCount(); i++) {
            this.tablaimpresion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaimpresion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaimpresion.getTableHeader().setFont(new Font("Arial Black", 1, 9));
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaimpresion.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablaimpresion.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaimpresion.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
    }

    public void sumarforma() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double supago = 0.00;
        double sumpago = 0.00;

        String csupago = "";
        String ctotalcobro = "";

        int totalRow = tablapagos.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL A PAGAR
            csupago = String.valueOf(tablapagos.getValueAt(i, 6));
            csupago = csupago.replace(".", "").replace(",", ".");
            supago = Double.valueOf(csupago);
            sumpago += supago;
        }
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalvalores.setText(formatea.format(sumpago));
        if (sumpago != 0) {
            this.Upditem.setEnabled(true);
            this.DelItem.setEnabled(true);
        } else {
            this.Upditem.setEnabled(false);
            this.DelItem.setEnabled(false);
        }
        //formato.format(sumatoria1);
    }

    public void limpiarformapago() {
        forma.setText("0");
        nombreformapago.setText("");
        banco.setText("0");
        nombrebanco.setText("");
        nrocheque.setText("");
        importecheque.setText("0");
        confirmacion.setCalendar(c2);
        totalvalores.setText("0");
    }

    private void TituloBanco() {
        modelobanco.addColumn("Código");
        modelobanco.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelobanco.getColumnCount(); i++) {
            tablabanco.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablabanco.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablabanco.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablabanco.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablabanco.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TituloFormaPago() {
        modeloformapago.addColumn("Código");
        modeloformapago.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloformapago.getColumnCount(); i++) {
            tablaformapago.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaformapago.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaformapago.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaformapago.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaformapago.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void limpiar() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion configinicial = configDAO.consultar();
        this.sucursal.setText(String.valueOf(configinicial.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(configinicial.getSucursaldefecto().getNombre());
        this.numero.setText("0");
        this.recibo.setText("0");
        this.idControl.setText("");
        this.fecha.setCalendar(c2);
        this.proveedor.setText("0");
        this.nombreproveedor.setText("");
        this.rucproveedor.setText("");
        this.moneda.setText(String.valueOf(configinicial.getMonedadefecto().getCodigo()));
        this.nombremoneda.setText(configinicial.getMonedadefecto().getNombre());
        this.cotizacion.setText(formatea.format(configinicial.getMonedadefecto().getVenta()));
        this.observacion.setText("");
        this.totalpago.setText("0");
        this.totalvalores.setText("0");

        int cantidadRegistro = tablafacturas.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }

        cantidadRegistro = tablapagos.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelopagos.removeRow(0);
        }
    }

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.jTBuscarbanco.getText(), nNumeroColumna));
    }

    public void filtroformapago(int nNumeroColumna) {
        trsfiltroformapago.setRowFilter(RowFilter.regexFilter(this.jTBuscarForma.getText(), nNumeroColumna));
    }

    public void filtropro(int nNumeroColumna) {
        trsfiltropro.setRowFilter(RowFilter.regexFilter(this.buscarproveedor.getText(), nNumeroColumna));
    }

    public void CargarTituloFormaPago() {
        modelopagos.addColumn("Cód.");
        modelopagos.addColumn("Forma Pago");
        modelopagos.addColumn("Banco");
        modelopagos.addColumn("Denominación");
        modelopagos.addColumn("N° Cheque");
        modelopagos.addColumn("Confirmación");
        modelopagos.addColumn("Importe");
        int[] anchos = {80, 100, 80, 100, 100, 100, 100};
        for (int i = 0; i < modelopagos.getColumnCount(); i++) {
            this.tablapagos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapagos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapagos.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.tablapagos.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablapagos.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablapagos.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
    }

    public void sumarcobros() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double supago = 0.00;
        double sumpago = 0.00;

        String csupago = "";
        String ctotalcobro = "";

        int totalRow = tablafacturas.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL A PAGAR
            csupago = String.valueOf(tablafacturas.getValueAt(i, 9));
            csupago = csupago.replace(".", "").replace(",", ".");
            supago = Double.valueOf(csupago);
            System.out.println("SU PAGO " + supago);
            sumpago += supago;
        }
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalpago.setText(formatea.format(sumpago));
    }

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

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

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
    }

    public void limpiardetalle() {
        Calendar c2 = new GregorianCalendar();
        this.fecha.setCalendar(c2);
        proveedor.setText("0");
        nombreproveedor.setText("");
        rucproveedor.setText("");
        sucursal.setText("1");
        nombresucursal.setText("CENTRAL");
        cotizacion.setText("1");
        numero.setText("0");
        moneda.setText("1");
        nombremoneda.setText("GUARANIES");

        int cantidadRegistro = modeloproveedor.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloproveedor.removeRow(0);
        }
        cantidadRegistro = modeloproveedor.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloproveedor.removeRow(0);
        }
        cantidadRegistro = modeloproveedor.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloproveedor.removeRow(0);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detalle = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        nombresucursal = new javax.swing.JTextField();
        BuscaSucursal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        proveedor = new javax.swing.JTextField();
        BuscaProveedor = new javax.swing.JButton();
        nombreproveedor = new javax.swing.JTextField();
        rucproveedor = new javax.swing.JTextField();
        idControl = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        numero = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        nombremoneda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JFormattedTextField();
        btncuenta = new javax.swing.JButton();
        BuscarMoneda = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        recibo = new javax.swing.JTextField();
        modo = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        tabpagos = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablafacturas = new javax.swing.JTable();
        panelpagos = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablapagos = new javax.swing.JTable();
        NewItem = new javax.swing.JButton();
        Upditem = new javax.swing.JButton();
        DelItem = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        totalvalores = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        totalpago = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        GrabarCobro = new javax.swing.JButton();
        Salir3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        BProveedor = new javax.swing.JDialog();
        jPanel16 = new javax.swing.JPanel();
        comboproveedor = new javax.swing.JComboBox();
        buscarproveedor = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaproveedor = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        Aceptar1 = new javax.swing.JButton();
        Salir1 = new javax.swing.JButton();
        BSucursal = new javax.swing.JDialog();
        jPanel18 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        buscarsucursal = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        Aceptar2 = new javax.swing.JButton();
        Salir2 = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel20 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        formapago = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        forma = new javax.swing.JTextField();
        BuscarFormapago = new javax.swing.JButton();
        nombreformapago = new javax.swing.JTextField();
        banco = new javax.swing.JTextField();
        BuscarBanco = new javax.swing.JButton();
        nombrebanco = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        nrocheque = new javax.swing.JTextField();
        importecheque = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        confirmacion = new com.toedter.calendar.JDateChooser();
        cModo = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        grabarPago = new javax.swing.JButton();
        salirPago = new javax.swing.JButton();
        BFormaPago = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        comboforma = new javax.swing.JComboBox();
        jTBuscarForma = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaformapago = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        BBancos = new javax.swing.JDialog();
        jPanel24 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        jTBuscarbanco = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        AceptarCasa = new javax.swing.JButton();
        SalirCasa = new javax.swing.JButton();
        pagoparcial = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        nrofactura = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        emision = new com.toedter.calendar.JDateChooser();
        vence = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        comprobante = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        apagar = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        importepago = new javax.swing.JFormattedTextField();
        jPanel14 = new javax.swing.JPanel();
        GrabaPagoParcial = new javax.swing.JButton();
        SalirPagoParcial = new javax.swing.JButton();
        imprimircheque = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        referenciaoperacion = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaimpresion = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        BotonImprimirCheque = new javax.swing.JButton();
        BotonSalirImpresion = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Agregar = new javax.swing.JButton();
        Anular = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        idcontrol = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        modificar = new javax.swing.JButton();
        imprimir = new javax.swing.JButton();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaordenes = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        pagosproveedores = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Sucursal");

        sucursal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucursalActionPerformed(evt);
            }
        });

        nombresucursal.setEnabled(false);

        BuscaSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscaSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscaSucursalActionPerformed(evt);
            }
        });

        jLabel2.setText("Fecha");

        jLabel3.setText("Proveedor");

        proveedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proveedorActionPerformed(evt);
            }
        });
        proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                proveedorKeyPressed(evt);
            }
        });

        BuscaProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscaProveedorActionPerformed(evt);
            }
        });

        nombreproveedor.setEnabled(false);

        rucproveedor.setEnabled(false);

        idControl.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreproveedor)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rucproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscaProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rucproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fecha, proveedor, sucursal});

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("N° OP");

        numero.setEditable(false);
        numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numero.setEnabled(false);

        jLabel5.setText("Moneda");

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

        nombremoneda.setEditable(false);
        nombremoneda.setEnabled(false);

        jLabel6.setText("Cotización");

        cotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });

        btncuenta.setText("Mostrar Facturas Pendientes en Cartera");
        btncuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncuentaActionPerformed(evt);
            }
        });

        BuscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarMonedaActionPerformed(evt);
            }
        });

        jLabel7.setText("N° Recibo");

        recibo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        recibo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                reciboKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel7))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btncuenta)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cotizacion, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                            .addComponent(recibo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(recibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncuenta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablafacturas.setModel(modelodetalle   );
        tablafacturas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablafacturasKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablafacturas);

        tabpagos.addTab("Detalle Facturas", jScrollPane2);

        tablapagos.setModel(modelopagos        );
        jScrollPane4.setViewportView(tablapagos);

        NewItem.setText("Nuevo");
        NewItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewItemActionPerformed(evt);
            }
        });

        Upditem.setText("Editar");
        Upditem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Upditem.setEnabled(false);
        Upditem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpditemActionPerformed(evt);
            }
        });

        DelItem.setText("Borrar");
        DelItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DelItem.setEnabled(false);
        DelItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelItemActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Total Valores");

        totalvalores.setEditable(false);
        totalvalores.setBackground(new java.awt.Color(0, 153, 255));
        totalvalores.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalvalores.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalvalores.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalvalores.setEnabled(false);

        javax.swing.GroupLayout panelpagosLayout = new javax.swing.GroupLayout(panelpagos);
        panelpagos.setLayout(panelpagosLayout);
        panelpagosLayout.setHorizontalGroup(
            panelpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelpagosLayout.createSequentialGroup()
                .addGroup(panelpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelpagosLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(totalvalores, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelpagosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NewItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Upditem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DelItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelpagosLayout.setVerticalGroup(
            panelpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelpagosLayout.createSequentialGroup()
                .addGroup(panelpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelpagosLayout.createSequentialGroup()
                        .addContainerGap(31, Short.MAX_VALUE)
                        .addComponent(NewItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Upditem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DelItem)
                        .addGap(31, 31, 31))
                    .addGroup(panelpagosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(panelpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalvalores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(14, 14, 14))
        );

        tabpagos.addTab("Forma Pago", panelpagos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabpagos)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(tabpagos, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Comentario"), "Comentario"));

        observacion.setColumns(20);
        observacion.setRows(5);
        jScrollPane3.setViewportView(observacion);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        totalpago.setEditable(false);
        totalpago.setBackground(new java.awt.Color(0, 153, 255));
        totalpago.setForeground(new java.awt.Color(0, 153, 255));
        totalpago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalpago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalpago.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalpago.setEnabled(false);
        totalpago.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Total Facturas");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(8, 8, 8)
                .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setForeground(new java.awt.Color(204, 204, 255));

        GrabarCobro.setText("Grabar");
        GrabarCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCobroActionPerformed(evt);
            }
        });

        Salir3.setText("Salir");
        Salir3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Salir3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("[F1] Seleccionar Pago");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("[F2] Pago Parcial");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("[F3] Cancelar Pago");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Salir3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCobro)
                    .addComponent(Salir3)
                    .addComponent(jLabel9)
                    .addComponent(jLabel17)
                    .addComponent(jLabel23))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout detalleLayout = new javax.swing.GroupLayout(detalle.getContentPane());
        detalle.getContentPane().setLayout(detalleLayout);
        detalleLayout.setHorizontalGroup(
            detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        detalleLayout.setVerticalGroup(
            detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproveedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        comboproveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        comboproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproveedorActionPerformed(evt);
            }
        });

        buscarproveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buscarproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscarproveedorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaproveedor.setModel(modeloproveedor);
        tablaproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaproveedorKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablaproveedor);

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Aceptar1.setText("Aceptar");
        Aceptar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Aceptar1ActionPerformed(evt);
            }
        });

        Salir1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Salir1.setText("Salir");
        Salir1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Salir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptar1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(Salir1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptar1)
                    .addComponent(Salir1))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProveedorLayout = new javax.swing.GroupLayout(BProveedor.getContentPane());
        BProveedor.getContentPane().setLayout(BProveedorLayout);
        BProveedorLayout.setHorizontalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProveedorLayout.setVerticalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        buscarsucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buscarsucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscarsucursalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarsucursalKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buscarsucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarsucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablasucursal.setModel(modelosucursal);
        tablasucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablasucursalKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablasucursal);

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptar2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Aceptar2.setText("Aceptar");
        Aceptar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Aceptar2ActionPerformed(evt);
            }
        });

        Salir2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Salir2.setText("Salir");
        Salir2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Salir2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptar2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(Salir2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptar2)
                    .addComponent(Salir2))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarMonedaActionPerformed(evt);
            }
        });
        jTBuscarMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarMonedaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jScrollPane7.setViewportView(tablamoneda);

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText("Aceptar");
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText("Salir");
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel11.setText("Forma Pago");

        jLabel12.setText("Banco/Caja");

        forma.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        forma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formaActionPerformed(evt);
            }
        });

        BuscarFormapago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarFormapago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarFormapagoActionPerformed(evt);
            }
        });

        banco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bancoActionPerformed(evt);
            }
        });

        BuscarBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarBancoActionPerformed(evt);
            }
        });

        jLabel13.setText("N° Cheque");

        nrocheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrocheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrochequeKeyPressed(evt);
            }
        });

        importecheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importecheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importecheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importechequeKeyPressed(evt);
            }
        });

        jLabel14.setText("Importe");

        jLabel15.setText("Confirmación");

        confirmacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmacionKeyPressed(evt);
            }
        });

        cModo.setText(org.openide.util.NbBundle.getMessage(pagos.class, "detalle_facturas.cModo.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(forma, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarFormapago)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreformapago, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addGap(3, 3, 3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nrocheque, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                        .addComponent(importecheque))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BuscarBanco)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(72, 72, 72))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreformapago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarFormapago, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(forma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importecheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(confirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        grabarPago.setText("Grabar");
        grabarPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        grabarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarPagoActionPerformed(evt);
            }
        });
        grabarPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grabarPagoKeyPressed(evt);
            }
        });

        salirPago.setText("Salir");
        salirPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salirPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(grabarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salirPago, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grabarPago)
                    .addComponent(salirPago))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout formapagoLayout = new javax.swing.GroupLayout(formapago.getContentPane());
        formapago.getContentPane().setLayout(formapagoLayout);
        formapagoLayout.setHorizontalGroup(
            formapagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        formapagoLayout.setVerticalGroup(
            formapagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formapagoLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BFormaPago.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BFormaPago.setTitle("null");

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboforma.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboforma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboforma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboforma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboformaActionPerformed(evt);
            }
        });

        jTBuscarForma.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarForma.setText(org.openide.util.NbBundle.getMessage(pagos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarFormaActionPerformed(evt);
            }
        });
        jTBuscarForma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarFormaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(comboforma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarForma, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboforma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaformapago.setModel(modeloformapago);
        tablaformapago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaformapagoMouseClicked(evt);
            }
        });
        tablaformapago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaformapagoKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tablaformapago);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(pagos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(pagos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout BFormaPagoLayout = new javax.swing.GroupLayout(BFormaPago.getContentPane());
        BFormaPago.getContentPane().setLayout(BFormaPagoLayout);
        BFormaPagoLayout.setHorizontalGroup(
            BFormaPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFormaPagoLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BFormaPagoLayout.setVerticalGroup(
            BFormaPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFormaPagoLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BBancos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BBancos.setTitle("null");

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarbanco.setText(org.openide.util.NbBundle.getMessage(pagos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarbancoActionPerformed(evt);
            }
        });
        jTBuscarbanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarbancoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablabanco.setModel(modelobanco       );
        tablabanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablabancoMouseClicked(evt);
            }
        });
        tablabanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablabancoKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablabanco);

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(org.openide.util.NbBundle.getMessage(pagos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(org.openide.util.NbBundle.getMessage(pagos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa)
                    .addComponent(SalirCasa))
                .addContainerGap())
        );

        javax.swing.GroupLayout BBancosLayout = new javax.swing.GroupLayout(BBancos.getContentPane());
        BBancos.getContentPane().setLayout(BBancosLayout);
        BBancosLayout.setHorizontalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBancosLayout.setVerticalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pagoparcial.setTitle("Pago Parcial");

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("N° Factura");

        nrofactura.setEnabled(false);

        jLabel18.setText("Emisión");

        emision.setEnabled(false);

        vence.setEnabled(false);

        jLabel19.setText("Vence");

        jLabel20.setText("Comprobante");

        comprobante.setEnabled(false);

        jLabel21.setText("A Pagar");

        apagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        apagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        apagar.setEnabled(false);

        jLabel22.setText("Su Pago");

        importepago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importepago.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importepago.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                importepagoFocusGained(evt);
            }
        });
        importepago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importepagoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20)
                    .addComponent(jLabel18)
                    .addComponent(jLabel16)
                    .addComponent(jLabel19))
                .addGap(40, 40, 40)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(importepago, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                        .addComponent(apagar, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(emision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(vence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importepago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabaPagoParcial.setText("Grabar");
        GrabaPagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabaPagoParcialActionPerformed(evt);
            }
        });

        SalirPagoParcial.setText("Salir");
        SalirPagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPagoParcialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(GrabaPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SalirPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabaPagoParcial)
                    .addComponent(SalirPagoParcial))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pagoparcialLayout = new javax.swing.GroupLayout(pagoparcial.getContentPane());
        pagoparcial.getContentPane().setLayout(pagoparcialLayout);
        pagoparcialLayout.setHorizontalGroup(
            pagoparcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pagoparcialLayout.setVerticalGroup(
            pagoparcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pagoparcialLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        referenciaoperacion.setEditable(false);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Imprimir Cheques");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(67, 67, 67)
                .addComponent(referenciaoperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenciaoperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap())
        );

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablaimpresion.setModel(modeloimprimir);
        jScrollPane10.setViewportView(tablaimpresion);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonImprimirCheque.setText("Imprimir");
        BotonImprimirCheque.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonImprimirCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonImprimirChequeActionPerformed(evt);
            }
        });

        BotonSalirImpresion.setText("Salir");
        BotonSalirImpresion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSalirImpresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirImpresionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(BotonImprimirCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(BotonSalirImpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonImprimirCheque)
                    .addComponent(BotonSalirImpresion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout imprimirchequeLayout = new javax.swing.GroupLayout(imprimircheque.getContentPane());
        imprimircheque.getContentPane().setLayout(imprimirchequeLayout);
        imprimirchequeLayout.setHorizontalGroup(
            imprimirchequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        imprimirchequeLayout.setVerticalGroup(
            imprimirchequeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imprimirchequeLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        Agregar.setBackground(new java.awt.Color(255, 255, 255));
        Agregar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Agregar.setText(" Agregar Registro");
        Agregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Anular.setBackground(new java.awt.Color(255, 255, 255));
        Anular.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Anular.setText("Anular Pago");
        Anular.setToolTipText("");
        Anular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Anular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnularActionPerformed(evt);
            }
        });

        Salir.setBackground(new java.awt.Color(255, 255, 255));
        Salir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Salir.setText("     Salir");
        Salir.setToolTipText("");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        idcontrol.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar entre los Días"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
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

        modificar.setBackground(new java.awt.Color(255, 255, 255));
        modificar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        modificar.setText("Modificar Registro");
        modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        imprimir.setBackground(new java.awt.Color(255, 255, 255));
        imprimir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        imprimir.setText("Imprimir");
        imprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        imprimir.setPreferredSize(new java.awt.Dimension(95, 21));
        imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirActionPerformed(evt);
            }
        });

        refrescar.setText("Refrescar");
        refrescar.setActionCommand("Filtrar");
        refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idcontrol, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(refrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(Salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Anular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 12, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Agregar)
                .addGap(18, 18, 18)
                .addComponent(modificar)
                .addGap(18, 18, 18)
                .addComponent(Anular)
                .addGap(17, 17, 17)
                .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Salir)
                .addGap(43, 43, 43)
                .addComponent(idcontrol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(refrescar)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        tablaordenes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablaordenes.setModel(modelo);
        tablaordenes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaordenes.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablaordenes.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablaordenes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablaordenesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablaordenesFocusLost(evt);
            }
        });
        tablaordenes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaordenesMouseClicked(evt);
            }
        });
        tablaordenes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaordenesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaordenes);

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        pagosproveedores.setText("Pagos");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del Proveedor", "N° de Recibo" }));
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
                .addContainerGap()
                .addComponent(pagosproveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pagosproveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jMenu1.setText("Menú de Pagos");

        jMenuItem1.setText("Imprimir Cheque");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        indiceColumnaTabla = 4;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por codigo
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablaordenes.getModel());
        tablaordenes.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        modo.setText("1");
        limpiar();
        this.btncuenta.setEnabled(true);
        detalle.setModal(true);
        detalle.setSize(800, 620);
        detalle.setTitle("Detalle de Pagos");
        detalle.setLocationRelativeTo(null);
        detalle.setVisible(true);
    }//GEN-LAST:event_AgregarActionPerformed

    private void tablaordenesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaordenesKeyPressed
        int nFila = this.tablaordenes.getSelectedRow();
        this.idcontrol.setText(this.tablaordenes.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaordenesKeyPressed

    private void tablaordenesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaordenesMouseClicked
        int nFila = this.tablaordenes.getSelectedRow();
        this.idcontrol.setText(this.tablaordenes.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaordenesMouseClicked

    private void tablaordenesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaordenesFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaordenesFocusGained

    private void jScrollPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        this.cargarTabla();
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void AnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnularActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {

            int nFila = tablaordenes.getSelectedRow();
            idControl.setText(tablaordenes.getValueAt(nFila, 0).toString());
            nNum = Integer.valueOf(tablaordenes.getValueAt(nFila, 1).toString());
            double num = Double.valueOf(tablaordenes.getValueAt(nFila, 9).toString());

            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                pagoDAO pa = new pagoDAO();
                detallepagoDAO deta = new detallepagoDAO();
                detalle_forma_pagoDAO frm = new detalle_forma_pagoDAO();
                extraccionDAO ext = new extraccionDAO();
                cabecera_asientoDAO cabDAO = new cabecera_asientoDAO();
                try {
                    pago pg = pa.MostrarxPago(nNum);
                    if (pg == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        ext.borrarExtraccion(pg.getIdpagos()); // BORRA EXTRACCIONES BANCARIAS
                        frm.borrarDetalleFormaPago(pg.getIdpagos()); // BORRA DETALLE DE FORMA DE PAGOS
                        deta.borrarDetallePago(pg.getIdpagos()); // BORRA DETALLE FACTURAS PAGADAS
                        pa.borrarPagos(idControl.getText()); //BORRA CABECERA DE PAGOS
                        cabDAO.eliminarAsiento(num);
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
            this.cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AnularActionPerformed

    private void tablaordenesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaordenesFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaordenesFocusLost

    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        this.cargarTabla();
        // TODO add your handling code here:
    }//GEN-LAST:event_refrescarActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        modo.setText("2");
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nFila = this.tablaordenes.getSelectedRow();
            this.idControl.setText(this.tablaordenes.getValueAt(nFila, 0).toString());
            nNum = Integer.valueOf(this.tablaordenes.getValueAt(nFila, 1).toString());
            this.btncuenta.setEnabled(false);
            pagoDAO pDAO = new pagoDAO();
            pago pa = null;
            try {
                pa = pDAO.MostrarxPago(nNum);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (pa != null) {
                //SE CARGAN LOS DATOS DE LA CABECERA
                numero.setText(formatosinpunto.format(pa.getNumero()));
                System.out.println(pa.getNumero());
                sucursal.setText(String.valueOf(pa.getSucursal().getCodigo()));
                nombresucursal.setText(pa.getSucursal().getNombre());
                fecha.setDate(pa.getFecha());
                proveedor.setText(String.valueOf(pa.getProveedor().getCodigo()));
                nombreproveedor.setText(String.valueOf(pa.getProveedor().getNombre()));
                rucproveedor.setText(String.valueOf(pa.getProveedor().getRuc()));
                recibo.setText(pa.getRecibo());
                moneda.setText(formatosinpunto.format(pa.getMoneda().getCodigo()));
                nombremoneda.setText(pa.getMoneda().getNombre());
                cotizacion.setText(formatea.format(pa.getCotizacionmoneda()));
                totalvalores.setText(formatea.format(pa.getTotalpago()));
                totalpago.setText(formatea.format(pa.getTotalpago()));
                observacion.setText(pa.getObservacion());

                // SE CARGAN LOS DETALLES DE FACTURAS
                int cantidadRegistro = modelodetalle.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelodetalle.removeRow(0);
                }

                detallepagoDAO detDAO = new detallepagoDAO();
                try {

                    for (detallepago cta : detDAO.MostrarDetalle(pa.getIdpagos())) {
                        String Datos[] = {cta.getIdfactura(), formatosinpunto.format(cta.getNrofactura()), formatoFecha.format(cta.getEmision()), String.valueOf(cta.getComprobante().getCodigo()), cta.getComprobante().getNombre(), String.valueOf(cta.getCuota()) + '/' + String.valueOf(cta.getNumerocuota()), formatoFecha.format(cta.getVencecuota()), formatea.format(cta.getPago()), formatea.format(cta.getPago()), formatea.format(cta.getPago())};
                        modelodetalle.addRow(Datos);
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                cantidadRegistro = modelopagos.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelopagos.removeRow(0);
                }

                detalle_forma_pagoDAO fDAO = new detalle_forma_pagoDAO();
                try {

                    for (detalle_forma_pago ft : fDAO.MostrarDetalle(idControl.getText())) {
                        String Datos[] = {String.valueOf(ft.getForma().getCodigo()), ft.getForma().getNombre(), String.valueOf(ft.getBanco().getCodigo()), ft.getBanco().getNombre(), ft.getNrocheque(), formatoFecha.format(ft.getConfirmacion()), formatea.format(ft.getNetocobrado())};
                        modelopagos.addRow(Datos);
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                int cantFilas = tablapagos.getRowCount();
                if (cantFilas > 0) {
                    Upditem.setEnabled(true);
                    DelItem.setEnabled(true);
                } else {
                    Upditem.setEnabled(false);
                    DelItem.setEnabled(false);
                }

                detalle.setModal(true);
                detalle.setSize(800, 620);
                detalle.setTitle("Detalle de Pagos");

                detalle.setLocationRelativeTo(null);
                detalle.setVisible(true);
                sucursal.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_modificarActionPerformed

    private void imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirActionPerformed
        int nFila = this.tablaordenes.getSelectedRow();
        EmitirOrdenPago EmitirOrdenPago = new EmitirOrdenPago();
        Thread HiloOrden = new Thread(EmitirOrdenPago);
        HiloOrden.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirActionPerformed

    private void BuscaSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscaSucursalActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (sucu.getCodigo() == 0) {
                GrillaSucursal grillasu = new GrillaSucursal();
                Thread hilosuc = new Thread(grillasu);
                hilosuc.start();
                BSucursal.setModal(true);
                BSucursal.setSize(482, 575);
                BSucursal.setLocationRelativeTo(null);
                BSucursal.setTitle("Buscar Sucursal");
                BSucursal.setVisible(true);
                BSucursal.setModal(false);
            } else {
                nombresucursal.setText(sucu.getNombre());
                //Establecemos un título para el jDialog
            }
            fecha.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscaSucursalActionPerformed

    private void BuscaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscaProveedorActionPerformed
        BProveedor.setModal(true);
        BProveedor.setSize(499, 535);
        BProveedor.setLocationRelativeTo(null);
        BProveedor.setVisible(true);
        BProveedor.setModal(true);
        recibo.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscaProveedorActionPerformed

    private void cargarTitulo2() {
        modelosucursal.addColumn("Código");
        modelosucursal.addColumn("Denominación");

        int[] anchos = {70, 200};
        for (int i = 0; i < modelosucursal.getColumnCount(); i++) {
            tablasucursal.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablasucursal.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablasucursal.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablasucursal.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablasucursal.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TituloProveedor() {
        modeloproveedor.addColumn("Código");
        modeloproveedor.addColumn("Denominación");
        modeloproveedor.addColumn("RUC");

        int[] anchos = {70, 200, 150};
        for (int i = 0; i < modeloproveedor.getColumnCount(); i++) {
            tablaproveedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproveedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproveedor.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablaproveedor.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaproveedor.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    public void filtroproveedor(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarproveedor.getText(), nNumeroColumna));
    }


    private void comboproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproveedorActionPerformed

    private void buscarproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarproveedorKeyPressed
        this.buscarproveedor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarproveedor.getText()).toUpperCase();
                buscarproveedor.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboproveedor.getSelectedIndex()) {
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
                filtropro(indiceColumnaTabla);
            }
        });
        trsfiltropro = new TableRowSorter(tablaproveedor.getModel());
        tablaproveedor.setRowSorter(trsfiltropro);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarproveedorKeyPressed

    private void tablaproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptar1.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorKeyPressed

    private void Aceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aceptar1ActionPerformed
        int nFila = this.tablaproveedor.getSelectedRow();
        this.proveedor.setText(this.tablaproveedor.getValueAt(nFila, 0).toString());
        this.nombreproveedor.setText(this.tablaproveedor.getValueAt(nFila, 1).toString());
        this.rucproveedor.setText(this.tablaproveedor.getValueAt(nFila, 2).toString());
        this.BProveedor.setModal(false);
        this.BProveedor.setVisible(false);
        this.buscarproveedor.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_Aceptar1ActionPerformed

    private void Salir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Salir1ActionPerformed
        BProveedor.setModal(false);
        BProveedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_Salir1ActionPerformed

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

    private void buscarsucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarsucursalKeyPressed
        this.buscarsucursal.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarsucursal.getText()).toUpperCase();
                buscarsucursal.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combosucursal.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltro);
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarsucursalKeyPressed

    private void tablasucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptar2.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalKeyPressed

    private void Aceptar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aceptar2ActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());
        BSucursal.setModal(false);
        BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_Aceptar2ActionPerformed

    private void Salir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Salir2ActionPerformed
        BSucursal.setModal(true);
        BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_Salir2ActionPerformed

    private void buscarsucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarsucursalKeyReleased
        String letras = ConvertirMayusculas.cadena(buscarsucursal);
        buscarsucursal.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarsucursalKeyReleased

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.BuscaSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorActionPerformed

        proveedorDAO proveedor = new proveedorDAO();
        proveedor prov = null;
        try {
            prov = proveedor.buscarId(Integer.valueOf(this.proveedor.getText()));
            if (prov.getCodigo() == 0) {
                this.BuscaProveedor.doClick();
            } else {
                nombreproveedor.setText(prov.getNombre());
                rucproveedor.setText(prov.getRuc());
                //Establecemos un título para el jDialog
            }
            recibo.requestFocus();
            if (observacion.getText().isEmpty()) {
                observacion.setText("PAGO FACTURAS DE " + nombreproveedor.getText().trim());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorActionPerformed

    private void combomonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combomonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomonedaActionPerformed

    private void jTBuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMonedaActionPerformed

    private void jTBuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarMonedaKeyPressed
        this.jTBuscarMoneda.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarMoneda.getText()).toUpperCase();
                jTBuscarMoneda.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combomoneda.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtromoneda(indiceColumnaTabla);
            }
        });
        trsfiltromoneda = new TableRowSorter(tablamoneda.getModel());
        tablamoneda.setRowSorter(trsfiltromoneda);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMonedaKeyPressed

    private void tablamonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamonedaMouseClicked
        this.AceptarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaMouseClicked

    private void tablamonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamonedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarMoneda.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaKeyPressed

    private void AceptarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarMonedaActionPerformed
        int nFila = this.tablamoneda.getSelectedRow();
        this.moneda.setText(this.tablamoneda.getValueAt(nFila, 0).toString());
        this.nombremoneda.setText(this.tablamoneda.getValueAt(nFila, 1).toString());
        this.cotizacion.setText(this.tablamoneda.getValueAt(nFila, 2).toString());

        this.BMoneda.setVisible(false);
        this.jTBuscarMoneda.setText("");
        this.cotizacion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        monedaDAO casDAO = new monedaDAO();
        moneda mn = null;
        try {
            mn = casDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mn.getCodigo() == 0) {
                GrillaMoneda grillamo = new GrillaMoneda();
                Thread hiloca = new Thread(grillamo);
                hiloca.start();
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setVisible(true);
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mn.getNombre());
                cotizacion.setText(formatea.format(mn.getVenta()));
                //Establecemos un título para el jDialog
            }
            cotizacion.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        BuscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void btncuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncuentaActionPerformed
        if (Integer.valueOf(proveedor.getText()) == 0 || proveedor.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "Ingrese la Cuenta del proveedor");
            proveedor.requestFocus();
            return;
        }
        if (Integer.valueOf(moneda.getText()) == 0 || moneda.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "Ingrese el Tipo de Moneda");
            moneda.requestFocus();
            return;
        }
        mostrarsaldos mostrarsaldos = new mostrarsaldos();
        mostrarsaldos.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_btncuentaActionPerformed

    private void GrabarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCobroActionPerformed
        String cTotalPago = totalpago.getText();
        double TotalPago = Double.valueOf(cTotalPago.replace(".", "").replace(",", "."));

        String cTotalForma = totalvalores.getText();
        double TotalForma = Double.valueOf(cTotalForma.replace(".", "").replace(",", "."));

        if (TotalPago != TotalForma) {
            JOptionPane.showMessageDialog(null, "Los importes de Facturas y Pagos son diferentes");
            this.tablafacturas.requestFocus();
            return;
        }

        if (cTotalPago.equals("0")) {
            JOptionPane.showMessageDialog(null, "No existen Pagos Seleccionados");
            this.tablafacturas.requestFocus();
            return;
        }

        Object[] opciones = {"   Grabar   ", "   Salir   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GuardarPago();
        }
        detalle.setModal(false);
        detalle.setVisible(false);
        this.cargarTabla();

        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCobroActionPerformed

    private void Salir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Salir3ActionPerformed
        detalle.setModal(false);
        detalle.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_Salir3ActionPerformed

    private void tablafacturasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablafacturasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            this.tablafacturas.setValueAt(this.tablafacturas.getValueAt(this.tablafacturas.getSelectedRow(), 8), this.tablafacturas.getSelectedRow(), 9);
            this.sumarcobros();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            nFila = this.tablafacturas.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una Fila");
                return;
            }
            this.nrofactura.setText(this.tablafacturas.getValueAt(this.tablafacturas.getSelectedRow(), 1).toString());
            this.comprobante.setText(this.tablafacturas.getValueAt(this.tablafacturas.getSelectedRow(), 4).toString());
            this.apagar.setText(this.tablafacturas.getValueAt(this.tablafacturas.getSelectedRow(), 8).toString());
            this.importepago.setText(this.tablafacturas.getValueAt(this.tablafacturas.getSelectedRow(), 9).toString());
            try {
                emision.setDate(formatoFecha.parse(this.tablafacturas.getValueAt(this.tablafacturas.getSelectedRow(), 2).toString()));
                vence.setDate(formatoFecha.parse(this.tablafacturas.getValueAt(this.tablafacturas.getSelectedRow(), 2).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
            pagoparcial.setModal(true);
            pagoparcial.setSize(448, 360);
            pagoparcial.setLocationRelativeTo(null);
            pagoparcial.setVisible(true);
            this.importepago.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            nFila = this.tablafacturas.getSelectedRow();
            this.tablafacturas.setValueAt(0, nFila, 9); //COLUMNA PAGO
            this.sumarcobros();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablafacturasKeyPressed

    private void salirPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirPagoActionPerformed
        formapago.setModal(false);
        formapago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirPagoActionPerformed

    private void NewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewItemActionPerformed
        String csupago = totalpago.getText();
        csupago = csupago.replace(".", "").replace(",", ".");
        if (Double.valueOf(csupago) <= 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar las Facturas a Pagar");
        } else {
            limpiarformapago();
            this.cModo.setText("");
            formapago.setModal(true);
            formapago.setSize(513, 270);
            formapago.setTitle("Detalle de Pagos");
            formapago.setLocationRelativeTo(null);
            formapago.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_NewItemActionPerformed

    private void comboformaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboformaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboformaActionPerformed

    private void jTBuscarFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarFormaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFormaActionPerformed

    private void jTBuscarFormaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarFormaKeyPressed
        this.jTBuscarForma.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarForma.getText()).toUpperCase();
                jTBuscarForma.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboforma.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtroformapago(indiceColumnaTabla);
            }
        });
        trsfiltroformapago = new TableRowSorter(tablaformapago.getModel());
        tablaformapago.setRowSorter(trsfiltroformapago);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFormaKeyPressed

    private void tablaformapagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaformapagoMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaformapagoMouseClicked

    private void tablaformapagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaformapagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaformapagoKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablaformapago.getSelectedRow();
        this.forma.setText(this.tablaformapago.getValueAt(nFila, 0).toString());
        this.nombreformapago.setText(this.tablaformapago.getValueAt(nFila, 1).toString());

        this.BFormaPago.setVisible(false);
        this.jTBuscarForma.setText("");
        this.banco.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BFormaPago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void BuscarFormapagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarFormapagoActionPerformed
        formapagoDAO frmDAO = new formapagoDAO();
        formapago frm = null;
        try {
            frm = frmDAO.buscarId(Integer.valueOf(this.forma.getText()));
            if (frm.getCodigo() == 0) {
                GrillaFormaPago grillafrm = new GrillaFormaPago();
                Thread hilofrm = new Thread(grillafrm);
                hilofrm.start();
                BFormaPago.setModal(true);
                BFormaPago.setSize(482, 575);
                BFormaPago.setLocationRelativeTo(null);
                BFormaPago.setVisible(true);
                BFormaPago.setModal(true);
            } else {
                nombreformapago.setText(frm.getNombre());
                //Establecemos un título para el jDialog
            }
            banco.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarFormapagoActionPerformed

    private void formaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formaActionPerformed
        this.BuscarFormapago.doClick();
// TODO add your handling code here:
    }//GEN-LAST:event_formaActionPerformed

    private void combobancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobancoActionPerformed

    private void jTBuscarbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarbancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarbancoActionPerformed

    private void jTBuscarbancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarbancoKeyPressed
        this.jTBuscarbanco.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarbanco.getText()).toUpperCase();
                jTBuscarbanco.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combobanco.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrobanco(indiceColumnaTabla);
            }
        });
        trsfiltrobanco = new TableRowSorter(tablabanco.getModel());
        tablabanco.setRowSorter(trsfiltrobanco);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarbancoKeyPressed

    private void tablabancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.banco.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.BBancos.setVisible(false);
        this.jTBuscarbanco.setText("");
        this.nrocheque.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.BBancos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    private void BuscarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarBancoActionPerformed
        bancosDAO bDAO = new bancosDAO();
        banco bco = null;
        try {
            bco = bDAO.buscarId(Integer.valueOf(this.banco.getText()));
            if (bco.getCodigo() == 0) {
                GrillaBanco grillabco = new GrillaBanco();
                Thread hilobco = new Thread(grillabco);
                hilobco.start();
                BBancos.setModal(true);
                BBancos.setSize(482, 575);
                BBancos.setLocationRelativeTo(null);
                BBancos.setVisible(true);
                BBancos.setModal(true);
            } else {
                nombrebanco.setText(bco.getNombre());
                //Establecemos un título para el jDialog
            }
            nrocheque.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarBancoActionPerformed

    private void bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bancoActionPerformed
        BuscarBanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_bancoActionPerformed

    private void grabarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarPagoActionPerformed
        String cImporteCheque = this.importecheque.getText();
        cImporteCheque = cImporteCheque.replace(".", "").replace(",", ".");

        if (cImporteCheque.isEmpty() || cImporteCheque.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Importe Moneda");
            this.importecheque.requestFocus();
            return;
        }

        if (this.banco.getText().isEmpty() || this.banco.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código del Banco");
            this.banco.requestFocus();
            return;
        }

        if (this.forma.getText().isEmpty() || this.forma.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Forma de Banco");
            this.forma.requestFocus();
            return;
        }

        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[7];
            fila[0] = this.forma.getText().toString();
            fila[1] = this.nombreformapago.getText().toString();
            fila[2] = this.banco.getText();
            fila[3] = this.nombrebanco.getText();
            fila[4] = this.nrocheque.getText();
            fila[5] = formatoFecha.format(this.confirmacion.getDate());
            fila[6] = this.importecheque.getText();
            modelopagos.addRow(fila);
            this.banco.requestFocus();
        } else {
            this.tablapagos.setValueAt(this.forma.getText(), nFila, 0);
            this.tablapagos.setValueAt(this.nombreformapago.getText(), nFila, 1);
            this.tablapagos.setValueAt(this.banco.getText(), nFila, 2);
            this.tablapagos.setValueAt(this.nombrebanco.getText(), nFila, 3);
            this.tablapagos.setValueAt(this.nrocheque.getText(), nFila, 4);
            this.tablapagos.setValueAt(formatoFecha.format(this.confirmacion.getDate()), nFila, 5);
            this.tablapagos.setValueAt(this.importecheque.getText(), nFila, 6);
            nFila = 0;
            this.salirPago.doClick();
        }
        this.limpiarformapago();
        this.sumarforma();
        // TODO add your handling code here:
    }//GEN-LAST:event_grabarPagoActionPerformed

    private void UpditemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpditemActionPerformed
        nFila = this.tablapagos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una Fila");
            return;
        }
        formapago.setSize(513, 270);
        formapago.setTitle("Detalle de Pagos");
        formapago.setLocationRelativeTo(null);
        cModo.setText(String.valueOf(nFila));

        forma.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 0).toString());
        nombreformapago.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 1).toString());
        banco.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 2).toString());
        nombrebanco.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 3).toString());
        nrocheque.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 4).toString());
        try {
            confirmacion.setDate(formatoFecha.parse(this.tablapagos.getValueAt(nFila, 5).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        importecheque.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 6).toString());
        formapago.setModal(true);
        formapago.setVisible(true);
        forma.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_UpditemActionPerformed

    private void DelItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelItemActionPerformed
        nFila = this.tablapagos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar un Registro");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelopagos.removeRow(nFila);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                this.sumarforma();;
            }
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_DelItemActionPerformed

    private void reciboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_reciboKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.proveedor.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_reciboKeyPressed

    private void nrochequeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrochequeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importecheque.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.banco.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeKeyPressed

    private void importechequeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importechequeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.confirmacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocheque.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importechequeKeyPressed

    private void proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorKeyPressed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.recibo.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void grabarPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grabarPagoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_grabarPagoKeyPressed

    private void confirmacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.grabarPago.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importecheque.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmacionKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int nFila = this.tablaordenes.getSelectedRow();
        this.idControl.setText(this.tablaordenes.getValueAt(nFila, 0).toString());
        referenciaoperacion.setText(idControl.getText());
        System.out.println("idcontrol " + idControl.getText());
        int cantidadRegistro = 0;
        cantidadRegistro = modeloimprimir.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloimprimir.removeRow(0);
        }
        detalle_forma_pagoDAO fDAO = new detalle_forma_pagoDAO();
        try {

            for (detalle_forma_pago ft : fDAO.MostrarDetalle(idControl.getText())) {
                String Datos[] = {String.valueOf(ft.getForma().getCodigo()), ft.getForma().getNombre(), String.valueOf(ft.getBanco().getCodigo()), ft.getBanco().getNombre(), ft.getNrocheque(), formatoFecha.format(ft.getConfirmacion()), formatea.format(ft.getNetocobrado())};
                modeloimprimir.addRow(Datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        int cantFilas = tablaimpresion.getRowCount();
        if (cantFilas > 0) {
            BotonImprimirCheque.setEnabled(true);
            BotonSalirImpresion.setEnabled(true);
        } else {
            BotonImprimirCheque.setEnabled(false);
            BotonSalirImpresion.setEnabled(true);
        }

        imprimircheque.setModal(true);
        imprimircheque.setSize(634, 350);
        imprimircheque.setTitle("Detalle de Cheques a Imprimir");
        imprimircheque.setLocationRelativeTo(null);
        imprimircheque.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void importepagoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importepagoFocusGained
        importepago.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importepagoFocusGained

    private void SalirPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPagoParcialActionPerformed
        pagoparcial.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPagoParcialActionPerformed

    private void GrabaPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabaPagoParcialActionPerformed
        String cImportePago = this.importepago.getText();
        cImportePago = cImportePago.replace(".", "").replace(",", ".");

        if (cImportePago.isEmpty() || cImportePago.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Pago");
            this.importepago.requestFocus();
            return;
        }

        this.tablafacturas.setValueAt(this.importepago.getText(), nFila, 9);
        pagoparcial.dispose();
        this.sumarcobros();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabaPagoParcialActionPerformed

    private void importepagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importepagoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabaPagoParcial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importepagoKeyPressed

    private void cotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.btncuenta.doClick();
            tablafacturas.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionKeyPressed

    private void BotonImprimirChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonImprimirChequeActionPerformed

        int nFila = this.tablaimpresion.getSelectedRow();
        Object[] opciones = {"   Imprimir Cheques ", "   Salir   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir los cheques de esta OP ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            con = new Conexion();
            st = con.conectar();
            st2 = con.conectar();

            String cCheque = tablaimpresion.getValueAt(nFila, 4).toString();
            String num = tablaimpresion.getValueAt(nFila, 6).toString();
            num = num.replace(".", "").replace(",", ".");

            try {
                Map parameters = new HashMap();
                numero_a_letras numero = new numero_a_letras();
                parameters.put("IdPagos", referenciaoperacion.getText());
                parameters.put("cCheque", cCheque);
                parameters.put("cLetra", numero.Convertir(num, true, 1));
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/chequepago_rufino_gustavo.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, st2.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonImprimirChequeActionPerformed

    private void BotonSalirImpresionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirImpresionActionPerformed
        this.imprimircheque.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSalirImpresionActionPerformed

    public void filtro2(int nNumeroColumna) {
        trsfiltro2.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    public void CargarTitulo1() {
        modelodetalle.addColumn("Id.");
        modelodetalle.addColumn("Nro. Factura");
        modelodetalle.addColumn("Emisión");
        modelodetalle.addColumn("Tipo");
        modelodetalle.addColumn("Comprobante");
        modelodetalle.addColumn("Cuota");
        modelodetalle.addColumn("Vence");
        modelodetalle.addColumn("Importe");
        modelodetalle.addColumn("Saldo a Pagar");
        modelodetalle.addColumn("Su Pago");
        int[] anchos = {0, 150, 100, 50, 150, 80, 100, 150, 150, 150};
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            this.tablafacturas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablafacturas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablafacturas.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.tablafacturas.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablafacturas.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablafacturas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablafacturas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablafacturas.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablafacturas.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablafacturas.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablafacturas.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.tablafacturas.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablafacturas.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.tablafacturas.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Recibo");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Denominación Proveedor");
        modelo.addColumn("Moneda");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Cotización");
        modelo.addColumn("Total Pago");
        modelo.addColumn("Asiento");
        modelo.addColumn("Usuario");
        this.tablaordenes.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaordenes.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaordenes.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaordenes.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        ((DefaultTableCellRenderer) tablaordenes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaordenes.getTableHeader().setFont(new Font("Arial Black", 1, 10));

    }

    public void cargarTabla() {
        //Uso la Clase SimpleDateFormat para darle formato al campo fecha

        //Instanciamos esta clase para alinear las celdas numericas a la derecha
        cSql = "SELECT idpagos,numero,sucursales.nombre AS nombresucursal,recibo,pagos.fecha,proveedores.nombre AS nombreproveedor,pagos.asiento,";
        cSql = cSql + "pagos.proveedor,monedas.nombre AS nombremoneda,totalpago,pagos.cotizacionmoneda,usuarios.last_name as nombreusuario ";
        cSql = cSql + " FROM pagos ";
        cSql = cSql + " LEFT JOIN proveedores ";
        cSql = cSql + " ON proveedores.codigo=pagos.proveedor ";
        cSql = cSql + " LEFT JOIN monedas ";
        cSql = cSql + " ON monedas.codigo=pagos.moneda ";
        cSql = cSql + " LEFT JOIN sucursales ";
        cSql = cSql + " ON sucursales.codigo=pagos.sucursal ";
        cSql = cSql + " LEFT JOIN usuarios ";
        cSql = cSql + " ON usuarios.employee_id=pagos.codusuario ";
        cSql = cSql + " WHERE pagos.fecha BETWEEN '" + dFechaInicio + "' AND '" + dFechaFinal + "'";
        cSql = cSql + " ORDER BY pagos.fecha";

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        //Llamo a la clase conexion para conectarme a la base de datos
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
        int[] anchos = {3, 100, 90, 90, 200, 100, 100, 100, 100, 90, 200, 200};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.tablaordenes.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        this.tablaordenes.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablaordenes.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablaordenes.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablaordenes.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.tablaordenes.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablaordenes.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.tablaordenes.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);

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
                Object[] fila = new Object[11]; // Cantidad de columnas en la tabla
                fila[0] = results.getString("idpagos");
                fila[1] = results.getString("numero");
                fila[2] = formatoFecha.format(results.getDate("fecha"));
                fila[3] = results.getString("proveedor");
                fila[4] = results.getString("nombreproveedor");
                fila[5] = results.getString("nombremoneda");
                fila[6] = results.getString("nombresucursal");
                fila[7] = formatea.format(results.getDouble("cotizacionmoneda"));
                fila[8] = formatea.format(results.getDouble("totalpago"));
                fila[9] = formatosinpunto.format(results.getDouble("asiento"));
                fila[10] = results.getString("nombreusuario");
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.tablaordenes.setRowSorter(new TableRowSorter(modelo));
            this.tablaordenes.updateUI();
            int cantFilas = this.tablaordenes.getRowCount();
            if (cantFilas > 0) {
                this.Anular.setEnabled(true);
                this.modificar.setEnabled(true);
                this.imprimir.setEnabled(true);
            } else {
                this.Anular.setEnabled(false);
                this.modificar.setEnabled(false);
                this.imprimir.setEnabled(false);
            }
            stm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex + "NO SE");
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
                new pagos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar1;
    private javax.swing.JButton Aceptar2;
    private javax.swing.JButton AceptarCasa;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Anular;
    private javax.swing.JDialog BBancos;
    private javax.swing.JDialog BFormaPago;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BProveedor;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton BotonImprimirCheque;
    private javax.swing.JButton BotonSalirImpresion;
    private javax.swing.JButton BuscaProveedor;
    private javax.swing.JButton BuscaSucursal;
    private javax.swing.JButton BuscarBanco;
    private javax.swing.JButton BuscarFormapago;
    private javax.swing.JButton BuscarMoneda;
    private javax.swing.JButton DelItem;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JButton GrabaPagoParcial;
    private javax.swing.JButton GrabarCobro;
    private javax.swing.JButton NewItem;
    private javax.swing.JButton Salir;
    private javax.swing.JButton Salir1;
    private javax.swing.JButton Salir2;
    private javax.swing.JButton Salir3;
    private javax.swing.JButton SalirCasa;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirPagoParcial;
    private javax.swing.JButton Upditem;
    private javax.swing.JFormattedTextField apagar;
    private javax.swing.JTextField banco;
    private javax.swing.JButton btncuenta;
    private javax.swing.JTextField buscarproveedor;
    private javax.swing.JTextField buscarsucursal;
    private javax.swing.JTextField cModo;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox comboforma;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox comboproveedor;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JTextField comprobante;
    private com.toedter.calendar.JDateChooser confirmacion;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JDialog detalle;
    private com.toedter.calendar.JDateChooser emision;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JTextField forma;
    private javax.swing.JDialog formapago;
    private javax.swing.JButton grabarPago;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idcontrol;
    private javax.swing.JFormattedTextField importecheque;
    private javax.swing.JFormattedTextField importepago;
    private javax.swing.JButton imprimir;
    private javax.swing.JDialog imprimircheque;
    private javax.swing.JComboBox jComboBox1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarForma;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarbanco;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton modificar;
    private javax.swing.JTextField modo;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField nombrebanco;
    private javax.swing.JTextField nombreformapago;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombreproveedor;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nrocheque;
    private javax.swing.JTextField nrofactura;
    private javax.swing.JTextField numero;
    private javax.swing.JTextArea observacion;
    private javax.swing.JDialog pagoparcial;
    private org.edisoncor.gui.label.LabelMetric pagosproveedores;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JPanel panelpagos;
    private javax.swing.JTextField proveedor;
    private javax.swing.JTextField recibo;
    private javax.swing.JTextField referenciaoperacion;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField rucproveedor;
    private javax.swing.JButton salirPago;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablabanco;
    private javax.swing.JTable tablafacturas;
    private javax.swing.JTable tablaformapago;
    private javax.swing.JTable tablaimpresion;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablaordenes;
    private javax.swing.JTable tablapagos;
    private javax.swing.JTable tablaproveedor;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTabbedPane tabpagos;
    private javax.swing.JFormattedTextField totalpago;
    private javax.swing.JFormattedTextField totalvalores;
    private com.toedter.calendar.JDateChooser vence;
    // End of variables declaration//GEN-END:variables

    private class mostrarsaldos extends Thread {

        public void run() {
            int nMoneda = Integer.valueOf(moneda.getText());
            int nProveedor = Integer.valueOf(proveedor.getText());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelodetalle.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodetalle.removeRow(0);
            }
            saldo_proveedoresDAO DAO = new saldo_proveedoresDAO();
            try {
                for (saldo_proveedores cta : DAO.buscarId(nProveedor, nMoneda)) {
                    String Datos[] = {cta.getIdreferencia(), cta.getNrofactura(), formatoFecha.format(cta.getEmision()), String.valueOf(cta.getComprobante().getCodigo()), cta.getComprobante().getNombre(), String.valueOf(cta.getCuota()) + '/' + String.valueOf(cta.getNumerocuota()), formatoFecha.format(cta.getVencimiento()), formatea.format(cta.getImporte()), formatea.format(cta.getSaldo()), formatea.format(0)};
                    modelodetalle.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablafacturas.setRowSorter(new TableRowSorter(modelodetalle));
            int cantFilas = tablafacturas.getRowCount();
        }
    }

    private void GuardarPago() {

        if (Integer.valueOf(modo.getText()) == 1) {
            UUID id = new UUID();
            referencia = UUID.crearUUID();
            referencia = referencia.substring(1, 25);
            idControl.setText(referencia);
        } else {
            referencia = idControl.getText();
        }

        Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
        String cTotalPago = totalpago.getText();
        cTotalPago = cTotalPago.replace(".", "").replace(",", ".");

        String cCotizacion = cotizacion.getText();
        cCotizacion = cCotizacion.replace(".", "").replace(",", ".");

        pagoDAO pagDAO = new pagoDAO();
        pago pag = new pago();
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sc = null;
        proveedorDAO proDAO = new proveedorDAO();
        proveedor pr = null;
        monedaDAO monDAO = new monedaDAO();
        moneda mo = null;
        extraccionDAO extrac = new extraccionDAO();

        try {
            pr = proDAO.buscarId(Integer.valueOf(proveedor.getText()));
            sc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
            mo = monDAO.buscarId(Integer.valueOf(moneda.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        pag.setIdpagos(idControl.getText().trim());
        pag.setRecibo(recibo.getText());
        pag.setFecha(FechaProceso);
        pag.setProveedor(pr);
        pag.setMoneda(mo);
        pag.setTotalpago(Double.valueOf(cTotalPago));
        pag.setObservacion(observacion.getText().trim());
        pag.setValores(Double.valueOf(cTotalPago));
        pag.setSucursal(sc);
        pag.setCotizacionmoneda(Double.valueOf(cCotizacion));
        pag.setCodusuario(Integer.valueOf(Config.CodUsuario));
        int posicion = 0;

        // SE CAPTURAN LOS DETALLES DE LAS FACTURAS COBRADAS
        String detalle = "[";
        int Items = tablafacturas.getRowCount();
        for (int i = 0; i < Items; i++) {
            supago = tablafacturas.getValueAt(i, 9).toString();

            supago = supago.replace(".", "").replace(",", ".");

            String idFactura = (tablafacturas.getValueAt(i, 0).toString().trim());
            String cFactura = (tablafacturas.getValueAt(i, 1).toString().trim());
            try {
                dEmision = ODate.de_java_a_sql(formatoFecha.parse(tablafacturas.getValueAt(i, 2).toString()));
                dVence = ODate.de_java_a_sql(formatoFecha.parse(tablafacturas.getValueAt(i, 6).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
            cCuota = (tablafacturas.getValueAt(i, 5).toString());
            posicion = cCuota.indexOf('/');
            int n = cCuota.length();
            posicion = cCuota.indexOf('/');

            switch (n) {
                case 3:
                    cNumerocuota = cCuota.substring(posicion + 1, 3);
                    cCuota = cCuota.substring(0, 1);
                    break;
                case 4:
                    cNumerocuota = cCuota.substring(posicion + 1, 4);
                    cCuota = cCuota.substring(0, 1);
                    break;
                case 5:
                    cNumerocuota = cCuota.substring(posicion + 1, 5);
                    cCuota = cCuota.substring(0, 2);
                    break;
            }

            String cComprobante = (tablafacturas.getValueAt(i, 3).toString());
            if (Double.valueOf(supago) != 0) {
                String linea = "{iddetalle : " + idControl.getText() + ","
                        + "idfactura : " + idFactura + ","
                        + "nrofactura : " + cFactura + ","
                        + "emision : " + dEmision + ","
                        + "comprobante : " + cComprobante + ","
                        + "pago : " + supago + ","
                        + "numerocuota : " + cNumerocuota + ","
                        + "cuota : " + cCuota + ","
                        + "vencecuota: " + dVence + "},";
                detalle += linea;
            }
        }
        if (!detalle.equals("[")) {
            detalle = detalle.substring(0, detalle.length() - 1);
        }
        detalle += "]";

        // SE CAPTURAN LOS DETALLES DE LA FORMA DE PAGO
        String detalleformapago = "[";
        Items = tablapagos.getRowCount();
        for (int i = 0; i < Items; i++) {
            supago = tablapagos.getValueAt(i, 6).toString();
            supago = supago.replace(".", "").replace(",", ".");
            String cNrocheque = tablapagos.getValueAt(i, 4).toString();
            if (cNrocheque.isEmpty()) {
                cNrocheque = "0";
            }
            try {
                dConfirma = ODate.de_java_a_sql(formatoFecha.parse(tablapagos.getValueAt(i, 5).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

            String linea = "{idmovimiento : " + idControl.getText() + ","
                    + "forma : " + tablapagos.getValueAt(i, 0).toString() + ","
                    + "banco : " + tablapagos.getValueAt(i, 2).toString() + ","
                    + "nrocheque : " + cNrocheque + ","
                    + "confirmacion: " + dConfirma + ","
                    + "netocobrado : " + supago + "},";
            detalleformapago += linea;
        }
        if (!detalleformapago.equals("[")) {
            detalleformapago = detalleformapago.substring(0, detalleformapago.length() - 1);
        }
        detalleformapago += "]";

        // SE CAPTURAN LOS DETALLES PARA BANCOS
        String cTipo = "'" + nombreproveedor.getText().trim() + "'";
        String detallebanco = "[";
        Items = tablapagos.getRowCount();
        for (int i = 0; i < Items; i++) {
            supago = tablapagos.getValueAt(i, 6).toString();
            supago = supago.replace(".", "").replace(",", ".");
            String cNrocheque = tablapagos.getValueAt(i, 4).toString();
            if (cNrocheque.isEmpty()) {
                cNrocheque = "0";
            }
            try {
                dConfirma = ODate.de_java_a_sql(formatoFecha.parse(tablapagos.getValueAt(i, 5).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

            String linea = "{idmovimiento : " + idControl.getText() + ","
                    + "documento : " + recibo.getText() + ","
                    + "proveedor : " + proveedor.getText() + ","
                    + "fecha : " + FechaProceso + ","
                    + "sucursal : " + sucursal.getText() + ","
                    + "banco : " + tablapagos.getValueAt(i, 2).toString() + ","
                    + "moneda : " + moneda.getText() + ","
                    + "cotizacion : " + cCotizacion + ","
                    + "importe : " + supago + ","
                    + "chequenro : " + cNrocheque + ","
                    + "observaciones : " + cTipo + ","
                    + "tipo : " + "D" + ","
                    + "vencimiento: " + dConfirma + "},";
            detallebanco += linea;
        }
        if (!detallebanco.equals("[")) {
            detallebanco = detallebanco.substring(0, detallebanco.length() - 1);
        }
        detallebanco += "]";
        if (Integer.valueOf(modo.getText()) == 1) {
            try {
                pagDAO.insertarPagos(pag, detalle, detalleformapago, detallebanco);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
        } else {
            try {
                detallepagoDAO detpagoDAO = new detallepagoDAO();
                detalle_forma_pagoDAO formaDAO = new detalle_forma_pagoDAO();
                bancosDAO baDAO = new bancosDAO();
                detpagoDAO.borrarDetallePago(idControl.getText());
                formaDAO.borrarDetalleFormaPago(idControl.getText());
                baDAO.borrarMovimiento(idControl.getText());
                pagDAO.ActualizarPagos(pag, detalle, detalleformapago, detallebanco);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        config_contableDAO contableDAO = new config_contableDAO();
        config_contable contable = null;
        contable = contableDAO.consultar();
        GenerarAsientosDAO genDAO = new GenerarAsientosDAO();
        if (contable.getPagos() == 1) {
            genDAO.generarPagosItem(idControl.getText());
        }

    }

    private class GrillaProveedor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproveedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproveedor.removeRow(0);
            }
            proveedorDAO proveeDAO = new proveedorDAO();
            try {
                for (proveedor provee : proveeDAO.todos()) {
                    String Datos[] = {String.valueOf(provee.getCodigo()), provee.getNombre(), provee.getRuc()};
                    modeloproveedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproveedor.setRowSorter(new TableRowSorter(modeloproveedor));
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

    private class GrillaMoneda extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }
            monedaDAO DAOCASA = new monedaDAO();
            try {
                for (moneda ca : DAOCASA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre(), formatea.format(ca.getVenta())};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private class GrillaFormaPago extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloformapago.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloformapago.removeRow(0);
            }
            formapagoDAO formaDAO = new formapagoDAO();
            try {
                for (formapago fr : formaDAO.todos()) {
                    String Datos[] = {String.valueOf(fr.getCodigo()), fr.getNombre()};
                    modeloformapago.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaformapago.setRowSorter(new TableRowSorter(modeloformapago));
            int cantFilas = tablaformapago.getRowCount();
        }
    }

    private class GrillaBanco extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            bancosDAO bancoDAO = new bancosDAO();
            try {
                for (banco ba : bancoDAO.todos()) {
                    String Datos[] = {String.valueOf(ba.getCodigo()), ba.getNombre()};
                    modelobanco.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablabanco.setRowSorter(new TableRowSorter(modelobanco));
            int cantFilas = tablabanco.getRowCount();
        }
    }

    private class EmitirOrdenPago extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            nFila = tablaordenes.getSelectedRow();
            String cIdpagos = tablaordenes.getValueAt(nFila, 0).toString();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cIdpagos", cIdpagos.toString());
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cRucEmpresa", Config.cRucEmpresa);
                parameters.put("cTelefonoEmpresa", Config.cTelefono);
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/orden_de_pagos.jasper");
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

    private class PrintCheques extends Thread {

        public void run() {
            con = new Conexion();
            st = con.conectar();
            st2 = con.conectar();
            String cIdpagos = tablaordenes.getValueAt(nFila, 0).toString();

            String cSql = "SELECT bancos.reporte AS nombrecheque,netocobrado "
                    + "FROM detalle_forma_pago "
                    + "LEFT JOIN bancos "
                    + "ON bancos.codigo=detalle_forma_pago.banco "
                    + "WHERE idmovimiento='" + cIdpagos + "'"
                    + " ORDER BY nrocheque ";
            try {
                System.out.println("PASANDO A IMPRIMIR");
                results = st.executeQuery(cSql);
                while (results.next()) {
                    String cNombreCheque = results.getString("nombrecheque");
                    double nTotalNeto = results.getDouble("netocobrado");

                    try {
                        System.out.println("NOMBRE REPORTE" + cNombreCheque);
                        Map parameters = new HashMap();
                        String num = String.valueOf(nTotalNeto);
                        numero_a_letras numero = new numero_a_letras();
                        parameters.put("IdPagos", cIdpagos);
                        parameters.put("cLetra", numero.Convertir(num, true, 1));
                        JasperReport jr = null;
                        URL url = getClass().getClassLoader().getResource("Reports/" + cNombreCheque.toString());
                        jr = (JasperReport) JRLoader.loadObject(url);
                        JasperPrint masterPrint = null;
                        //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                        masterPrint = JasperFillManager.fillReport(jr, parameters, st2.getConnection());
                        JasperViewer ventana = new JasperViewer(masterPrint, false);
                        ventana.setTitle("Vista Previa");
                        ventana.setVisible(true);
                    } catch (Exception e) {
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), 1);
                        System.out.println(e.getMessage());
                    }
                }
                results.close();
                st.close();
                st2.close();
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
                System.out.println(e.getMessage());
            }
        }
    }
}

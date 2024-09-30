/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.ApiBancardSuscripcion;
import Clases.SwingBrowser;
import Clases.Config;
import java.util.Base64;
import Clases.ControlGrabado;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.configuracionDAO;
import DAO.giraduriaDAO;
import DAO.prestamoDAO;
import DAO.cuenta_clienteDAO;
import DAO.custodiaDAO;
import DAO.historico_custodiasDAO;
import DAO.sucursalDAO;
import Modelo.Tablas;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.custodia;
import Modelo.giraduria;
import Modelo.historico_custodia;
import Modelo.prestamo;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openide.util.Exceptions;

/**
 *
 */
public class prestamos extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm, stm2 = null;
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocustodia = new Tablas();
    Tablas modelocusto = new Tablas();
    Tablas modeloentidadcustodia = new Tablas();
    Tablas modelobancard1 = new Tablas();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrogira, trsfiltrocustodia;
    ObtenerFecha ODate = new ObtenerFecha();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    String cTotalImprimir = null;
    String cCliente, cReferencia, cSql = null;
    String url = null;
    String cCodigoGastos, idPrestamo = null;
    Calendar c2 = new GregorianCalendar();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // FORMATO DE FECHA
    DecimalFormat formatoSinpunto = new DecimalFormat("######");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    int tipoconsulta = 0;
    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconologo = new ImageIcon("src/Iconos/logobancard.png");

    public prestamos() {
        initComponents();
        this.idmovimiento.setVisible(false);
        this.jButton2.setIcon(icononuevo);
        this.jButton1.setIcon(iconoeditar);
        this.jButton3.setIcon(iconoborrar);
        this.jButton5.setIcon(iconosalir);
        this.Refrescar.setIcon(icorefresh);
        this.BuscarCustodiaOrigen.setIcon(iconobuscar);
        this.BuscarCustodiaDestino.setIcon(iconobuscar);
        this.logobancard.setIcon(iconologo);
        this.vencetimbrado.setVisible(false);
        this.nrotimbrado.setVisible(false);
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.importe.setHorizontalAlignment(JTextField.RIGHT);
        this.montoaprobado.setHorizontalAlignment(JTextField.RIGHT);
        this.interes.setHorizontalAlignment(JTextField.RIGHT);
        this.neto.setHorizontalAlignment(JTextField.RIGHT);
        this.imprimirfactura1.setVisible(false);
        this.numero.setVisible(false);
        this.creferencia.setVisible(false);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.Opciones.setVisible(false);
        this.cargarTitulo();
        this.TitGir();
        this.Inicializar();
        this.cargarTabla();
        this.TituloCustodia();
        this.TituloCu();
        this.TituloEntidadCustodia();
        this.TituloBancard();

        GrillaCustodia grillacm = new GrillaCustodia();
        Thread hiloca = new Thread(grillacm);
        hiloca.start();

    }

    Control hand = new Control();

    private void TituloEntidadCustodia() {
        modeloentidadcustodia.addColumn("Código");
        modeloentidadcustodia.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloentidadcustodia.getColumnCount(); i++) {
            tablaentidadcustodia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaentidadcustodia.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaentidadcustodia.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaentidadcustodia.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaentidadcustodia.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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

    private void Inicializar() {
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aprobar = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nroprestamo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fechaprestamo = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        nombrecliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        interes = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        montoaprobado = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tipoprestamo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        oficialcuenta = new javax.swing.JTextField();
        neto = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel30 = new javax.swing.JLabel();
        totalprestamo = new javax.swing.JFormattedTextField();
        jLabel31 = new javax.swing.JLabel();
        importecuota = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        grabar = new javax.swing.JButton();
        condicionar = new javax.swing.JButton();
        rechazar = new javax.swing.JButton();
        saliropcion = new javax.swing.JButton();
        genfacturacapital = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        salirfactura1 = new javax.swing.JButton();
        nroprestamo1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        fechaprestamo1 = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        grabarfactura1 = new javax.swing.JButton();
        nombrecliente1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        moneda1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        intordinario = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        nrofactura1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        observacion1 = new javax.swing.JTextArea();
        imprimirfactura1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        importegasto = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        comisiones = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        seguro = new javax.swing.JFormattedTextField();
        vencetimbrado = new com.toedter.calendar.JDateChooser();
        nrotimbrado = new javax.swing.JTextField();
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
        custodiaPagare = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        numeroprestamo = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        idmovimiento = new javax.swing.JTextField();
        nombretitularprestamo = new javax.swing.JTextField();
        importeprestamo = new javax.swing.JFormattedTextField();
        jLabel36 = new javax.swing.JLabel();
        fechaemisionprestamo = new com.toedter.calendar.JDateChooser();
        jLabel37 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        origencustodia = new javax.swing.JTextField();
        BuscarCustodiaOrigen = new javax.swing.JButton();
        BuscarCustodiaDestino = new javax.swing.JButton();
        nombrecustodiaorigen = new javax.swing.JTextField();
        destinocustodia = new javax.swing.JTextField();
        fechaprocesocustodia = new com.toedter.calendar.JDateChooser();
        nombrecustodiadestino = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        GrabarCustodia = new javax.swing.JButton();
        BorrarCustodia = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacustodia = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        BCustodia = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combocustodia = new javax.swing.JComboBox();
        JTBuscarCustodia = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaentidadcustodia = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarCustodia = new javax.swing.JButton();
        SalirCustodia = new javax.swing.JButton();
        ExportarCustodia = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        origenc = new javax.swing.JTextField();
        BuscarCustodiaO = new javax.swing.JButton();
        BuscarCustodiaD = new javax.swing.JButton();
        nombrecustodiao = new javax.swing.JTextField();
        destinoc = new javax.swing.JTextField();
        fechaprocesoc = new com.toedter.calendar.JDateChooser();
        nombrecustodiad = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        GrabarCus = new javax.swing.JButton();
        BorrarC = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablacusto = new javax.swing.JTable();
        Salircus = new javax.swing.JButton();
        bancard = new javax.swing.JDialog();
        fondo = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablabancard1 = new javax.swing.JTable();
        BtnActualizar = new javax.swing.JButton();
        BtnPagar = new javax.swing.JButton();
        BtnReintentarPagar = new javax.swing.JButton();
        BtnPausarPago = new javax.swing.JButton();
        BtnReversar = new javax.swing.JButton();
        BtnReversarPago = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        Opciones = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        Refrescar = new javax.swing.JButton();
        BotonSeleccionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        logobancard = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        printsolicitud = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        printpagaretotal = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        actualizarclientes = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        printliquidacion = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        printpagarecuotas = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        pagarecuotas = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        printcontrato = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        ordenpago = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        facturacapital = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        facturainteresprestamo = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("N° Préstamo");

        nroprestamo.setEditable(false);
        nroprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel3.setText("Fecha");

        fechaprestamo.setBackground(new java.awt.Color(255, 255, 255));
        fechaprestamo.setForeground(new java.awt.Color(255, 255, 255));
        fechaprestamo.setEnabled(false);
        fechaprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel4.setText("Nombre Cliente");

        nombrecliente.setEditable(false);
        nombrecliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        nombrecliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nombrecliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nombreclienteMouseClicked(evt);
            }
        });

        jLabel5.setText("Moneda");

        moneda.setEditable(false);
        moneda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel6.setText("Capital");

        importe.setEditable(false);
        importe.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel7.setText("Interés Ordinario");

        interes.setEditable(false);
        interes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel8.setText("Total Préstamo");

        montoaprobado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        montoaprobado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        montoaprobado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        montoaprobado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                montoaprobadoActionPerformed(evt);
            }
        });

        jLabel9.setText("Monto a Entregar");

        jLabel10.setText("Tipo Préstamo");

        tipoprestamo.setEditable(false);
        tipoprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel11.setText("Oficial de Cuenta");

        oficialcuenta.setEditable(false);
        oficialcuenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        neto.setEditable(false);
        neto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane4.setViewportView(observaciones);

        jLabel30.setText("Importe Aprobado");

        totalprestamo.setEditable(false);
        totalprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel31.setText("Observaciones");

        importecuota.setEditable(false);
        importecuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importecuota.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel32.setText("Cuota Préstamo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addGap(54, 54, 54)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nombrecliente, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(importecuota, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(totalprestamo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(interes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                        .addComponent(importe, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(moneda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nroprestamo, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fechaprestamo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(99, 99, 99))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(neto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(oficialcuenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                .addComponent(tipoprestamo, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(montoaprobado, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(fechaprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(interes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(totalprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oficialcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(montoaprobado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(122, 122, 122))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        grabar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabar.setText("Aprobar");
        grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarActionPerformed(evt);
            }
        });

        condicionar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        condicionar.setText("Condicionar");
        condicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                condicionarActionPerformed(evt);
            }
        });

        rechazar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        rechazar.setText("Rechazar");
        rechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechazarActionPerformed(evt);
            }
        });

        saliropcion.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        saliropcion.setText("Salir");
        saliropcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saliropcionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(condicionar)
                .addGap(36, 36, 36)
                .addComponent(rechazar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(condicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rechazar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout aprobarLayout = new javax.swing.GroupLayout(aprobar.getContentPane());
        aprobar.getContentPane().setLayout(aprobarLayout);
        aprobarLayout.setHorizontalGroup(
            aprobarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        aprobarLayout.setVerticalGroup(
            aprobarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aprobarLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setText("N° Préstamo");

        salirfactura1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        salirfactura1.setText("Salir");
        salirfactura1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirfactura1ActionPerformed(evt);
            }
        });

        nroprestamo1.setEditable(false);
        nroprestamo1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel13.setText("Fecha");

        fechaprestamo1.setBackground(new java.awt.Color(255, 255, 255));
        fechaprestamo1.setForeground(new java.awt.Color(255, 255, 255));
        fechaprestamo1.setEnabled(false);

        jLabel14.setText("Nombre Cliente");

        grabarfactura1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabarfactura1.setText("Grabar Factura");
        grabarfactura1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarfactura1ActionPerformed(evt);
            }
        });

        nombrecliente1.setEditable(false);
        nombrecliente1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel15.setText("Moneda");

        moneda1.setEditable(false);
        moneda1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel16.setText("Interés Ordinario");

        intordinario.setEditable(false);
        intordinario.setForeground(new java.awt.Color(255, 0, 0));
        intordinario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel17.setText("Nº Factura");

        observacion1.setColumns(20);
        observacion1.setRows(5);
        jScrollPane2.setViewportView(observacion1);

        imprimirfactura1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        imprimirfactura1.setText("Imprimir Factura");
        imprimirfactura1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirfactura1ActionPerformed(evt);
            }
        });

        jLabel18.setText("Observaciones al píe del Comprobante");

        jLabel23.setText("Gastos Administrativos");

        importegasto.setEditable(false);
        importegasto.setForeground(new java.awt.Color(255, 0, 0));
        importegasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel26.setText("Comisiones Varias");

        comisiones.setEditable(false);
        comisiones.setForeground(new java.awt.Color(255, 0, 0));
        comisiones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel27.setText("Seguro de Vida");

        seguro.setEditable(false);
        seguro.setForeground(new java.awt.Color(255, 0, 0));
        seguro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        nrotimbrado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(grabarfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(imprimirfactura1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(salirfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombrecliente1)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(moneda1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                            .addComponent(intordinario, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(88, 88, 88))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13))
                                        .addGap(82, 82, 82)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nroprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fechaprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(237, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jScrollPane2)
                                .addContainerGap())
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(88, 88, 88)
                                            .addComponent(nrofactura1))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel26)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comisiones, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel23)
                                            .addGap(31, 31, 31)
                                            .addComponent(importegasto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel27)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(seguro, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(78, 78, 78))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(139, 139, 139))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel12))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nroprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(nombrecliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(moneda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(intordinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(importegasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(comisiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(seguro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nrofactura1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salirfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grabarfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imprimirfactura1))
                .addContainerGap())
        );

        javax.swing.GroupLayout genfacturacapitalLayout = new javax.swing.GroupLayout(genfacturacapital.getContentPane());
        genfacturacapital.getContentPane().setLayout(genfacturacapitalLayout);
        genfacturacapitalLayout.setHorizontalGroup(
            genfacturacapitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, genfacturacapitalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        genfacturacapitalLayout.setVerticalGroup(
            genfacturacapitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(genfacturacapitalLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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
        interesordinario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(grabarfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(salirfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(lblgastos)
                            .addComponent(jLabel24))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombrecliente2)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(interesordinario, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                            .addComponent(moneda2))
                                        .addGap(0, 168, Short.MAX_VALUE)))
                                .addGap(88, 88, 88))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fechaprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(186, 186, 186))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblgastos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        labelTask1.setText(org.openide.util.NbBundle.getMessage(prestamos.class, "actualizar_web.labelTask1.text")); // NOI18N
        labelTask1.setDescription(org.openide.util.NbBundle.getMessage(prestamos.class, "actualizar_web.labelTask1.description")); // NOI18N

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
        jTBuscarGiraduria.setText(org.openide.util.NbBundle.getMessage(prestamos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(prestamos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(prestamos.class, "ventas.SalirCliente.text")); // NOI18N
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

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel34.setText("N° Préstamo");

        numeroprestamo.setEditable(false);
        numeroprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel35.setText("Títular Préstamo");

        nombretitularprestamo.setEditable(false);

        importeprestamo.setEditable(false);
        importeprestamo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importeprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel36.setText("Importe");

        fechaemisionprestamo.setBackground(new java.awt.Color(255, 255, 255));
        fechaemisionprestamo.setForeground(new java.awt.Color(255, 255, 255));
        fechaemisionprestamo.setEnabled(false);

        jLabel37.setText("Fecha Emisión");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombretitularprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importeprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numeroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaemisionprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(idmovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(numeroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idmovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaemisionprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(nombretitularprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(importeprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel38.setText("Origen");

        origencustodia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        origencustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                origencustodiaActionPerformed(evt);
            }
        });

        BuscarCustodiaOrigen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaOrigenActionPerformed(evt);
            }
        });

        BuscarCustodiaDestino.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaDestinoActionPerformed(evt);
            }
        });

        nombrecustodiaorigen.setEditable(false);

        destinocustodia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        destinocustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinocustodiaActionPerformed(evt);
            }
        });

        fechaprocesocustodia.setBackground(new java.awt.Color(255, 255, 255));
        fechaprocesocustodia.setForeground(new java.awt.Color(255, 255, 255));

        nombrecustodiadestino.setEditable(false);

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCustodia.setText("Grabar");
        GrabarCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCustodiaActionPerformed(evt);
            }
        });

        BorrarCustodia.setText("Borrar");
        BorrarCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarCustodiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrabarCustodia, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(BorrarCustodia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(GrabarCustodia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BorrarCustodia)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel39.setText("Destino");

        jLabel40.setText("Fecha");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(65, 65, 65))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(origencustodia)
                                .addGap(18, 18, 18)
                                .addComponent(BuscarCustodiaOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel39)
                        .addGap(69, 69, 69)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(fechaprocesocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(nombrecustodiaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrecustodiadestino, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(destinocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarCustodiaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel38)
                                .addComponent(jLabel39))
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(destinocustodia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(origencustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fechaprocesocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombrecustodiaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecustodiadestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablacustodia.setModel(modelocustodia        );
        jScrollPane5.setViewportView(tablacustodia);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton8.setText("Salir de la Opción Custodia");
        jButton8.setToolTipText("Salir");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout custodiaPagareLayout = new javax.swing.GroupLayout(custodiaPagare.getContentPane());
        custodiaPagare.getContentPane().setLayout(custodiaPagareLayout);
        custodiaPagareLayout.setHorizontalGroup(
            custodiaPagareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        custodiaPagareLayout.setVerticalGroup(
            custodiaPagareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(custodiaPagareLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        BCustodia.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCustodia.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocustodia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocustodia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocustodiaActionPerformed(evt);
            }
        });

        JTBuscarCustodia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        JTBuscarCustodia.setText(org.openide.util.NbBundle.getMessage(prestamos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        JTBuscarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTBuscarCustodiaActionPerformed(evt);
            }
        });
        JTBuscarCustodia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTBuscarCustodiaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JTBuscarCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTBuscarCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaentidadcustodia.setModel(modeloentidadcustodia        );
        tablaentidadcustodia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaentidadcustodiaMouseClicked(evt);
            }
        });
        tablaentidadcustodia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaentidadcustodiaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablaentidadcustodia);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCustodia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCustodia.setText(org.openide.util.NbBundle.getMessage(prestamos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCustodiaActionPerformed(evt);
            }
        });

        SalirCustodia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCustodia.setText(org.openide.util.NbBundle.getMessage(prestamos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCustodiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCustodia)
                    .addComponent(SalirCustodia))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCustodiaLayout = new javax.swing.GroupLayout(BCustodia.getContentPane());
        BCustodia.getContentPane().setLayout(BCustodiaLayout);
        BCustodiaLayout.setHorizontalGroup(
            BCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCustodiaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCustodiaLayout.setVerticalGroup(
            BCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCustodiaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel41.setText("Origen");

        origenc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        origenc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                origencActionPerformed(evt);
            }
        });

        BuscarCustodiaO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaOActionPerformed(evt);
            }
        });

        BuscarCustodiaD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaDActionPerformed(evt);
            }
        });

        nombrecustodiao.setEditable(false);

        destinoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        destinoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinocActionPerformed(evt);
            }
        });

        fechaprocesoc.setBackground(new java.awt.Color(255, 255, 255));
        fechaprocesoc.setForeground(new java.awt.Color(255, 255, 255));

        nombrecustodiad.setEditable(false);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCus.setText("Grabar");
        GrabarCus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCusActionPerformed(evt);
            }
        });

        BorrarC.setText("Borrar");
        BorrarC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrabarCus, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(BorrarC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(GrabarCus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BorrarC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel42.setText("Destino");

        jLabel43.setText("Fecha");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecustodiao, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(jLabel41)
                            .addGap(65, 65, 65))
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(origenc)
                            .addGap(18, 18, 18)
                            .addComponent(BuscarCustodiaO, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(117, 117, 117))
                    .addComponent(nombrecustodiad, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(destinoc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarCustodiaD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(fechaprocesoc, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(destinoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrecustodiad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(origenc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaprocesoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaO, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrecustodiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablacusto.setModel(modelocusto    );
        jScrollPane8.setViewportView(tablacusto);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        Salircus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Salircus.setText("Salir de la Opción Custodia");
        Salircus.setToolTipText("Salir");
        Salircus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salircus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalircusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ExportarCustodiaLayout = new javax.swing.GroupLayout(ExportarCustodia.getContentPane());
        ExportarCustodia.getContentPane().setLayout(ExportarCustodiaLayout);
        ExportarCustodiaLayout.setHorizontalGroup(
            ExportarCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Salircus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ExportarCustodiaLayout.createSequentialGroup()
                .addGroup(ExportarCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        ExportarCustodiaLayout.setVerticalGroup(
            ExportarCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExportarCustodiaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Salircus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bancard.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        bancard.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                bancardWindowOpened(evt);
            }
        });

        fondo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablabancard1.setModel(modelobancard1);
        jScrollPane9.setViewportView(tablabancard1);

        BtnActualizar.setText("Actualizar");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        BtnPagar.setText("Pagar");
        BtnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPagarActionPerformed(evt);
            }
        });

        BtnReintentarPagar.setText("Reintentar Pago");
        BtnReintentarPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReintentarPagarActionPerformed(evt);
            }
        });

        BtnPausarPago.setText("Pausar Pago");
        BtnPausarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPausarPagoActionPerformed(evt);
            }
        });

        BtnReversar.setText("Cancelar Suscripción");
        BtnReversar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReversarActionPerformed(evt);
            }
        });

        BtnReversarPago.setText("Reversar Pago");
        BtnReversarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReversarPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addComponent(BtnReversar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnReintentarPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnPausarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnReversarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 991, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnPagar)
                    .addComponent(BtnReintentarPagar)
                    .addComponent(BtnPausarPago)
                    .addComponent(BtnReversar)
                    .addComponent(BtnReversarPago))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout bancardLayout = new javax.swing.GroupLayout(bancard.getContentPane());
        bancard.getContentPane().setLayout(bancardLayout);
        bancardLayout.setHorizontalGroup(
            bancardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bancardLayout.createSequentialGroup()
                .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        bancardLayout.setVerticalGroup(
            bancardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bancardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton1.setText("Editar Registro");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton2.setText(" Agregar Registro");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton3.setText("Anular Préstamo");
        jButton3.setToolTipText("");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton5.setText("     Salir");
        jButton5.setToolTipText("");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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

        BotonSeleccionar.setText("Custodia");
        BotonSeleccionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSeleccionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotonSeleccionarMouseClicked(evt);
            }
        });
        BotonSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonSeleccionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Refrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(Opciones, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(53, 53, 53)
                .addComponent(Opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(Refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(BotonSeleccionar)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        jTable1.setModel(modelo);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setSelectionBackground(new java.awt.Color(51, 204, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 255));
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable1FocusLost(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setText("Préstamos");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del Cliente", "N° de Operación", "Estado" }));
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

        logobancard.setBackground(new java.awt.Color(255, 255, 255));
        logobancard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
                .addComponent(logobancard, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addComponent(logobancard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenu1.setText("Menú Préstamos");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        printsolicitud.setText("Solicitud de Crédito");
        printsolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printsolicitudActionPerformed(evt);
            }
        });
        jMenu1.add(printsolicitud);
        jMenu1.add(jSeparator5);

        printpagaretotal.setText("Aprobar Préstamo");
        printpagaretotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printpagaretotalMouseClicked(evt);
            }
        });
        printpagaretotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printpagaretotalActionPerformed(evt);
            }
        });
        jMenu1.add(printpagaretotal);
        jMenu1.add(jSeparator4);

        actualizarclientes.setText("Datos del Cliente");
        actualizarclientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarclientesActionPerformed(evt);
            }
        });
        jMenu1.add(actualizarclientes);
        jMenu1.add(jSeparator10);

        printliquidacion.setText("Liquidación del Préstamo");
        printliquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printliquidacionActionPerformed(evt);
            }
        });
        jMenu1.add(printliquidacion);
        jMenu1.add(jSeparator3);

        printpagarecuotas.setText("Pagaré por el Total");
        printpagarecuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printpagarecuotasActionPerformed(evt);
            }
        });
        jMenu1.add(printpagarecuotas);
        jMenu1.add(jSeparator1);

        pagarecuotas.setText("Pagaré por Cuotas");
        pagarecuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagarecuotasActionPerformed(evt);
            }
        });
        jMenu1.add(pagarecuotas);
        jMenu1.add(jSeparator11);

        printcontrato.setText("Contrato ");
        printcontrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printcontratoActionPerformed(evt);
            }
        });
        jMenu1.add(printcontrato);
        jMenu1.add(jSeparator2);

        ordenpago.setText("Orden de Pago");
        ordenpago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordenpagoActionPerformed(evt);
            }
        });
        jMenu1.add(ordenpago);
        jMenu1.add(jSeparator6);

        facturacapital.setText("Emitir Factura ");
        facturacapital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturacapitalActionPerformed(evt);
            }
        });
        jMenu1.add(facturacapital);
        jMenu1.add(jSeparator7);

        facturainteresprestamo.setText("Facturar Interés Ordinario");
        facturainteresprestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturainteresprestamoActionPerformed(evt);
            }
        });
        jMenu1.add(facturainteresprestamo);
        jMenu1.add(jSeparator9);

        jMenuItem5.setText("Débito Automático");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator8);

        jMenuItem6.setText("Tarifario de Gastos");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator12);

        jMenuItem1.setText("Custodia del Pagaré");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Bancard");
        jMenu2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem2.setText("Agregar Suscripción");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);
        jMenu2.add(jSeparator13);

        jMenuItem3.setText("Actualizar Suscripción");
        jMenuItem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);
        jMenu2.add(jSeparator14);

        jMenuItem4.setText("Listado Suscripciones");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

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
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "";
        String cOpcion = "new";
        try {
            //  new detalle_prestamos_crediseguro(cOpcion).setVisible(true);

            //              new detalle_prestamos_carlyle2(cOpcion).setVisible(true);
//          if (Config.nIvaIncluido == 1) {
            //              new detalle_prestamos(cOpcion).setVisible(true);
            //        } else {
            new detalle_prestamos_preciosbajos(cOpcion).setVisible(true); //PRECIOS BAJOS
//          new detalle_prestamos_fincresa(cOpcion).setVisible(true); //FINCRESA
            //   new detalle_prestamos_asociaciones(cOpcion).setVisible(true); // FAPASA
            //          new detalle_prestamos_aso(cOpcion).setVisible(true);*/
            //      }
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "";
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        String cEstado = this.jTable1.getValueAt(nFila, 12).toString();

        if (Integer.valueOf(Config.cNivelUsuario) != 1) {
            if (cEstado.equals("DESEMBOLSADO")) {
                JOptionPane.showMessageDialog(null, "El Préstamo ya fue Desembolsado ");
                return;
            }
            if (cEstado.equals("RECHAZADO")) {
                JOptionPane.showMessageDialog(null, "El Préstamo fue Rechazado ");
                return;
            }
        }

        String cOpcion = this.Opciones.getText();
        try {
            //     if (Config.nIvaIncluido == 1) {
            //     new detalle_prestamos(cOpcion).setVisible(true);
            // } else {
//            new detalle_prestamos_fincresa(cOpcion).setVisible(true); //FINCRESA
            new detalle_prestamos_preciosbajos(cOpcion).setVisible(true); //PRECIOS BAJOS
//                             new detalle_prestamos_aso(cOpcion).setVisible(true);
            //             new detalle_prestamos_carlyle2(cOpcion).setVisible(true);
            //            new detalle_prestamos_crediseguro(cOpcion).setVisible(true);
//
            //        new detalle_prestamos_asociaciones(cOpcion).setVisible(true); // FAPASA

            //}
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusGained

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        String cNumero = this.jTable1.getValueAt(nFila, 1).toString();
        String cReferencia = this.jTable1.getValueAt(nFila, 0).toString();
        if (Config.cNivelUsuario.equals("1")) {
            if (!this.Opciones.getText().isEmpty()) {
                BDConexion BD = new BDConexion();
                BD.borrarRegistro("detalle_prestamo", "nprestamo=" + cNumero);
                BD.BorrarDetalles("prestamos", "numero=" + cNumero);
                BD.borrarRegistro("deducciones", "nprestamo=" + cNumero);

                this.cargarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar un Préstamo");
            return;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void RefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarActionPerformed
        this.cargarTabla();
        BotonSeleccionar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_RefrescarActionPerformed

    private void printpagarecuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printpagarecuotasActionPerformed
        GenerarPagare GenerarPagare = new GenerarPagare();
        Thread HiloPagare = new Thread(GenerarPagare);
        HiloPagare.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_printpagarecuotasActionPerformed

    private void printliquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printliquidacionActionPerformed
        GenerarLiquidacion GenerarLiquidacion = new GenerarLiquidacion();
        Thread HiloLiquidacion = new Thread(GenerarLiquidacion);
        HiloLiquidacion.start();        // TODO add your handling code here:
    }//GEN-LAST:event_printliquidacionActionPerformed

    private void ordenpagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordenpagoActionPerformed
        GenerarOp OrdenPago = new GenerarOp();
        Thread HiloOrdenPago = new Thread(OrdenPago);
        HiloOrdenPago.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_ordenpagoActionPerformed

    private void printpagaretotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printpagaretotalActionPerformed
        this.aprobar.setSize(623, 510);
        int nFila = this.jTable1.getSelectedRow();
        String cEstado = this.jTable1.getValueAt(nFila, 12).toString();
        if (cEstado.equals("APROBADO") || cEstado.equals("DESEMBOLSADO") || cEstado.equals("RECHAZADO")) {
            JOptionPane.showMessageDialog(null, "El Préstamo ya fue Aprobado o Desembolsado");
            return;
        }
        this.nroprestamo.setText(this.jTable1.getValueAt(nFila, 1).toString());
        try {
            this.fechaprestamo.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.nombrecliente.setText(this.jTable1.getValueAt(nFila, 3).toString().trim());
        this.moneda.setText(this.jTable1.getValueAt(nFila, 4).toString().trim());
        this.importe.setText(this.jTable1.getValueAt(nFila, 5).toString());
        this.interes.setText(this.jTable1.getValueAt(nFila, 6).toString());
        this.importecuota.setText(this.jTable1.getValueAt(nFila, 20).toString());
        this.totalprestamo.setText(this.jTable1.getValueAt(nFila, 7).toString());
        this.montoaprobado.setText("0");
        this.neto.setText(this.jTable1.getValueAt(nFila, 8).toString());
        this.tipoprestamo.setText(this.jTable1.getValueAt(nFila, 10).toString().trim());
        this.oficialcuenta.setText(this.jTable1.getValueAt(nFila, 11).toString().trim());
        this.grabar.setText("Aprobar");
        aprobar.setTitle("Aprobar Préstamo");
        aprobar.setLocationRelativeTo(null);
        aprobar.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_printpagaretotalActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void saliropcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saliropcionActionPerformed
        aprobar.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_saliropcionActionPerformed

    private void grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            String cAprobar = "APROBADO";
            int ret = JOptionPane.showOptionDialog(null, "Desea Aprobar el Préstamo Seleccionado? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                con = new Conexion();
                stm = con.conectar();
                ResultSet results = null;
                String cMontoAprobado = this.montoaprobado.getText();
                cMontoAprobado = cMontoAprobado.replace(".", "");
                cMontoAprobado = cMontoAprobado.replace(",", ".");
                String cSqlAprobar = "UPDATE Prestamos  SET estado='" + cAprobar + "',aprobadopor='" + Config.CodUsuario + "',montoaprobado='" + cMontoAprobado + "',observaciones='" + this.observaciones.getText();
                cSqlAprobar += "' WHERE numero=" + this.Opciones.getText();
                try {
                    stm.executeUpdate(cSqlAprobar);
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Aprobar un Préstamo");
            return;
        }
        this.aprobar.setVisible(false);
    }//GEN-LAST:event_grabarActionPerformed

    private void facturainteresprestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturainteresprestamoActionPerformed

        GenFacturaGastos GenFacGastos = new GenFacturaGastos();
        Thread HiloGastos = new Thread(GenFacGastos);
        HiloGastos.start();

        this.genfacturaotros.setSize(671, 506);
        int nFila = this.jTable1.getSelectedRow();
        cReferencia = this.jTable1.getValueAt(nFila, 0).toString();

        this.nroprestamo2.setText(this.jTable1.getValueAt(nFila, 1).toString());
        cCliente = this.jTable1.getValueAt(nFila, 13).toString();
        cCodigoGastos = this.jTable1.getValueAt(nFila, 16).toString();
        try {
            this.fechaprestamo2.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.nombrecliente2.setText(this.jTable1.getValueAt(nFila, 3).toString().trim());
        this.moneda2.setText(this.jTable1.getValueAt(nFila, 4).toString().trim());
        this.interesordinario.setText(this.jTable1.getValueAt(nFila, 6).toString());
        genfacturaotros.setTitle("Generar Factura");
        genfacturaotros.setLocationRelativeTo(null);
        genfacturaotros.setVisible(true);

        // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_facturainteresprestamoActionPerformed

    private void grabarfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarfactura1ActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Factura del Capital? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GrabarDetalleCapital GrabarDet = new GrabarDetalleCapital();
            Thread HiloGrabarDet = new Thread(GrabarDet);
            HiloGrabarDet.start();
            JOptionPane.showMessageDialog(null, "Factura Generada con Éxito");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_grabarfactura1ActionPerformed

    private void salirfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfactura1ActionPerformed
        genfacturacapital.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirfactura1ActionPerformed

    private void facturacapitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturacapitalActionPerformed
        int nsucu = 1;
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(1);
            String cBoca = sucu.getExpedicion().trim();
            Double nFactura = sucu.getFactura();
            int n = (int) nFactura.doubleValue();
            String formatString = String.format("%%0%dd", 7);
            String formattedString = String.format(formatString, n);
            this.nrofactura1.setText(cBoca + "-" + formattedString);
            vencetimbrado.setDate(sucu.getVencetimbrado());
            nrotimbrado.setText(sucu.getNrotimbrado());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.genfacturacapital.setSize(680, 506);
        int nFila = this.jTable1.getSelectedRow();
        cReferencia = this.jTable1.getValueAt(nFila, 0).toString();
        this.nroprestamo1.setText(this.jTable1.getValueAt(nFila, 1).toString());
        cCliente = this.jTable1.getValueAt(nFila, 13).toString();
        cCodigoGastos = this.jTable1.getValueAt(nFila, 16).toString();
        try {
            this.fechaprestamo1.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.nombrecliente1.setText(this.jTable1.getValueAt(nFila, 3).toString().trim());
        this.moneda1.setText(this.jTable1.getValueAt(nFila, 4).toString().trim());
        this.intordinario.setText(this.jTable1.getValueAt(nFila, 6).toString());
        this.importegasto.setText(this.jTable1.getValueAt(nFila, 15).toString());
        this.comisiones.setText(this.jTable1.getValueAt(nFila, 18).toString());
        this.seguro.setText(this.jTable1.getValueAt(nFila, 19).toString());

        genfacturacapital.setTitle("Generar Factura");
        genfacturacapital.setLocationRelativeTo(null);
        genfacturacapital.setVisible(true);

// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_facturacapitalActionPerformed

    private void imprimirfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirfactura1ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        String cSqlControl = "SELECT creferencia,totalneto FROM cabecera_ventas WHERE creferencia='" + this.Opciones.getText() + "'";
        try {
            stm.executeQuery(cSqlControl);
            while (results.next()) {
                cReferencia = results.getString("creferencia");
                cTotalImprimir = results.getString("totalneto");
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (cReferencia.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La Factura aún no fue Generada");
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Factura? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            ImprimirFacturaCapital GenerarReporte1 = new ImprimirFacturaCapital();
            Thread HiloReporte = new Thread(GenerarReporte1);
            HiloReporte.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirfactura1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        con = new Conexion();
        stm = con.conectar();
        String cSqlControl2 = "SELECT * FROM facturaprestamos WHERE opcion = 2 AND idprestamo='" + cReferencia + "'";
        System.out.println(cSqlControl2);
        try {
            stm.executeQuery(cSqlControl2);
            while (results.next()) {
                cReferencia = results.getString("idfactura");
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (cReferencia.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La Factura aún no fue Generada");
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Factura? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            ImprimirFacturaGastos GenerarReporte2 = new ImprimirFacturaGastos();
            Thread HiloReporte2 = new Thread(GenerarReporte2);
            HiloReporte2.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void grabarfactura2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarfactura2ActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Factura por Gastos? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GrabarDetalleOtros GrabarGastos = new GrabarDetalleOtros();
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

    private void printsolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printsolicitudActionPerformed
        int nFila = jTable1.getSelectedRow();
        cReferencia = jTable1.getValueAt(nFila, 0).toString();
        ImprimirSolicitud ImpSolicitud = new ImprimirSolicitud();
        Thread HiloSolicitud = new Thread(ImpSolicitud);
        HiloSolicitud.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_printsolicitudActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cReferencia", cReferencia);
            JasperReport jr = null;
            // URL url = getClass().getClassLoader().getResource("Reports/liquidacion_prestamos_iva.jasper");
            URL url = getClass().getClassLoader().getResource("Reports/gastosxmora.jasper");
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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        this.debito.setSize(525, 347);
        this.debito.setModal(true);
        int nFila = this.jTable1.getSelectedRow();
        this.creferencia.setText(this.jTable1.getValueAt(nFila, 0).toString());
        this.numero.setText(this.jTable1.getValueAt(nFila, 1).toString());
        String idpre = this.jTable1.getValueAt(nFila, 1).toString();
        prestamoDAO pr1 = new prestamoDAO();
        prestamo p = null;
        try {
            p = pr1.buscarId(Integer.valueOf(idpre));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (p.getNrocuenta() != null) {
            codgiraduria.setText(String.valueOf(p.getGiraduria().getCodigo()));
            nombregiraduria.setText(p.getGiraduria().getNombre());
            nrocuenta.setText(p.getNrocuenta());
        } else {
            codgiraduria.setText("0");
            nombregiraduria.setText("");
            nrocuenta.setText("");
        }
        debito.setTitle("Aprobar Préstamo");
        debito.setLocationRelativeTo(null);
        debito.setVisible(true);        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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

    private void rechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechazarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            String cAprobar = "RECHAZADO";
            int ret = JOptionPane.showOptionDialog(null, "Se rechazará el Préstamo Seleccionado? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                con = new Conexion();
                stm = con.conectar();
                ResultSet results = null;
                String cSqlAprobar = "UPDATE Prestamos  SET estado='" + cAprobar + "',aprobadopor='" + Config.CodUsuario + "',observaciones='" + this.observaciones.getText();
                cSqlAprobar += "' WHERE numero=" + this.Opciones.getText();
                try {
                    stm.executeUpdate(cSqlAprobar);
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Aprobar un Préstamo");
            return;
        }
        this.aprobar.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_rechazarActionPerformed

    private void actualizarclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarclientesActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        String cOpcion = this.jTable1.getValueAt(nFila, 13).toString();
        try {
            new detalle_clientes(cOpcion).setVisible(true);
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_actualizarclientesActionPerformed

    private void printpagaretotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printpagaretotalMouseClicked
        int nFila = this.jTable1.getSelectedRow();
        String cOpcion = this.jTable1.getValueAt(nFila, 13).toString();
        try {
            new detalle_clientes(cOpcion).setVisible(true);
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_printpagaretotalMouseClicked

    private void nombreclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombreclienteMouseClicked
        int nFila = this.jTable1.getSelectedRow();
        String cOpcion = this.jTable1.getValueAt(nFila, 13).toString();
        try {
            new detalle_clientes(cOpcion).setVisible(true);
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_nombreclienteMouseClicked

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

    private void condicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_condicionarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            String cAprobar = "CONDICIONAL";
            int ret = JOptionPane.showOptionDialog(null, "La Solicitud estará sujeta a otras condiciones ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                con = new Conexion();
                stm = con.conectar();
                ResultSet results = null;
                String cSqlAprobar = "UPDATE Prestamos  SET estado='" + cAprobar + "',aprobadopor='" + Config.CodUsuario + "',observaciones='" + this.observaciones.getText();
                cSqlAprobar += "' WHERE numero=" + this.Opciones.getText();
                try {
                    stm.executeUpdate(cSqlAprobar);
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Condicionar un Préstamo");
            return;
        }
        this.aprobar.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_condicionarActionPerformed

    private void montoaprobadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoaprobadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_montoaprobadoActionPerformed

    private void pagarecuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagarecuotasActionPerformed
        PagareCuotas PagareCuotas = new PagareCuotas();
        Thread HiloPagare = new Thread(PagareCuotas);
        HiloPagare.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_pagarecuotasActionPerformed

    private void printcontratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printcontratoActionPerformed
        GenerarContrato GenerarContrato = new GenerarContrato();
        Thread HiloContrato = new Thread(GenerarContrato);
        HiloContrato.start();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_printcontratoActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        custodiaPagare.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void TituloCu() {
        modelocusto.addColumn("id");
        modelocusto.addColumn("N° Préstamo");
        modelocusto.addColumn("Fecha");
        modelocusto.addColumn("Cliente ");
        modelocusto.addColumn("Importe");

        int[] anchos = {1, 100, 100, 180, 120};
        for (int i = 0; i < modelocusto.getColumnCount(); i++) {
            tablacusto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        ((DefaultTableCellRenderer) tablacusto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacusto.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        this.tablacusto.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacusto.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablacusto.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacusto.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.tablacusto.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablacusto.setFont(font);
    }

    private void TituloCustodia() {
        modelocustodia.addColumn("id");
        modelocustodia.addColumn("Fecha");
        modelocustodia.addColumn("Origen ");
        modelocustodia.addColumn("Destino");
        modelocustodia.addColumn("Registro");
        modelocustodia.addColumn("Hora");

        int[] anchos = {1, 100, 120, 120, 120, 120};
        for (int i = 0; i < modelocustodia.getColumnCount(); i++) {
            tablacustodia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacustodia.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacustodia.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        this.tablacustodia.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacustodia.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablacustodia.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacustodia.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablacustodia.setFont(font);
    }

    private void limpiaCustoda() {
        this.origencustodia.setText("0");
        this.destinocustodia.setText("0");
        this.nombrecustodiaorigen.setText("");
        this.nombrecustodiadestino.setText("");
        this.fechaprocesocustodia.setCalendar(c2);

        this.origenc.setText("0");
        this.destinoc.setText("0");
        this.nombrecustodiao.setText("");
        this.nombrecustodiad.setText("");
        this.fechaprocesocustodia.setCalendar(c2);
        this.fechaprocesoc.setCalendar(c2);
        this.idmovimiento.setText("");
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //CUSTODIA

        this.limpiaCustoda();
        int nFila = this.jTable1.getSelectedRow();
        if (nFila >= 0) {
            this.idmovimiento.setText(this.jTable1.getValueAt(nFila, 0).toString());
            this.numeroprestamo.setText(this.jTable1.getValueAt(nFila, 1).toString());
            this.nombretitularprestamo.setText(this.jTable1.getValueAt(nFila, 3).toString());
            String cImporteP = this.jTable1.getValueAt(nFila, 7).toString();
            cImporteP = cImporteP.replace(".", "").replace(",", ".");
            this.importeprestamo.setText(formatea.format(Double.valueOf(cImporteP)));

            try {
                fechaemisionprestamo.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

        }

        if (this.idmovimiento.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar una Operación");
            return;
        }
        int cantidadRegistro = modelocustodia.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocustodia.removeRow(0);
        }

        historico_custodiasDAO cuDAO = new historico_custodiasDAO();
        try {
            for (historico_custodia detcu : cuDAO.BuscarId(idmovimiento.getText())) {
                String Detalle[] = {formatoSinpunto.format(detcu.getId()), formatoFecha.format(detcu.getFechaproceso()), detcu.getNombreorigen(), detcu.getNombredestino(), detcu.getPc(), detcu.getHora()};
                modelocustodia.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        int cantFilas = tablacustodia.getRowCount();
        if (cantFilas > 0) {
            BorrarCustodia.setEnabled(true);
        } else {
            BorrarCustodia.setEnabled(false);
        }

        this.custodiaPagare.setSize(520, 540);
        this.custodiaPagare.setModal(true);
        String idpre = this.jTable1.getValueAt(nFila, 1).toString();
        custodiaPagare.setTitle("Actualizar Custodia");
        custodiaPagare.setLocationRelativeTo(null);
        custodiaPagare.setVisible(true);


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void combocustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocustodiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocustodiaActionPerformed

    private void JTBuscarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTBuscarCustodiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTBuscarCustodiaActionPerformed

    private void JTBuscarCustodiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTBuscarCustodiaKeyPressed
        this.JTBuscarCustodia.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (JTBuscarCustodia.getText()).toUpperCase();
                JTBuscarCustodia.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocustodia.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrocustodia(indiceColumnaTabla);
            }
        });
        trsfiltrocustodia = new TableRowSorter(tablaentidadcustodia.getModel());
        tablaentidadcustodia.setRowSorter(trsfiltrocustodia);
        // TODO add your handling code here:
    }//GEN-LAST:event_JTBuscarCustodiaKeyPressed

    public void filtrocustodia(int nNumeroColumna) {
        trsfiltrocustodia.setRowFilter(RowFilter.regexFilter(this.JTBuscarCustodia.getText(), nNumeroColumna));
    }


    private void tablaentidadcustodiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaentidadcustodiaMouseClicked
        this.AceptarCustodia.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaentidadcustodiaMouseClicked

    private void tablaentidadcustodiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaentidadcustodiaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCustodia.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaentidadcustodiaKeyPressed

    private void AceptarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCustodiaActionPerformed
        int nFila = this.tablaentidadcustodia.getSelectedRow();

        if (tipoconsulta == 1) {
            this.origencustodia.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiaorigen.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        } else if (tipoconsulta == 2) {
            this.destinocustodia.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiadestino.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        } else if (tipoconsulta == 3) {
            this.origenc.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiao.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        } else {
            this.destinoc.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiad.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        }
        tipoconsulta = 0;
        this.BCustodia.setVisible(false);
        this.JTBuscarCustodia.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCustodiaActionPerformed

    private void SalirCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCustodiaActionPerformed
        this.BCustodia.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCustodiaActionPerformed

    private void BuscarCustodiaOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaOrigenActionPerformed
        tipoconsulta = 1;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.origencustodia.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiaorigen.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            destinocustodia.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaOrigenActionPerformed

    private void BuscarCustodiaDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaDestinoActionPerformed
        tipoconsulta = 2;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.destinocustodia.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiadestino.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            fechaprocesocustodia.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaDestinoActionPerformed

    private void origencustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_origencustodiaActionPerformed
        this.BuscarCustodiaOrigen.doClick();
// TODO add your handling code here:
    }//GEN-LAST:event_origencustodiaActionPerformed

    private void destinocustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinocustodiaActionPerformed
        this.BuscarCustodiaDestino.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_destinocustodiaActionPerformed

    private void GrabarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCustodiaActionPerformed
        if (Integer.valueOf(origencustodia.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            origencustodia.requestFocus();
            return;
        }
        if (Integer.valueOf(destinocustodia.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            destinocustodia.requestFocus();
            return;
        }

        historico_custodia ot = new historico_custodia();
        historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();
        Date dFechaHoy = ODate.de_java_a_sql(fechaprocesocustodia.getDate());
        ot.setOrigencustodia(Integer.valueOf(origencustodia.getText()));
        ot.setDestinocustodia(Integer.valueOf(destinocustodia.getText()));
        ot.setFechaproceso(dFechaHoy);
        ot.setIdmovimiento(idmovimiento.getText().toString());
        ot.setIdnumero(Integer.valueOf(numeroprestamo.getText()));
        //SE CAPTURA N° DE IP DE LA PC
        InetAddress addr;
        String ip = null;
        try {
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (UnknownHostException ex) {
            Exceptions.printStackTrace(ex);
        }
        ot.setPc(ip);;

        try {
            otcontrolDAO.InsertarCustodia(ot);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        RefrescarGrillaCustodia GrillaP = new RefrescarGrillaCustodia();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.origencustodia.setText("0");
        this.destinocustodia.setText("0");
        this.nombrecustodiaorigen.setText("");
        this.nombrecustodiadestino.setText("");
        this.origencustodia.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCustodiaActionPerformed

    private void BorrarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarCustodiaActionPerformed

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = this.tablacustodia.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nItem = Integer.valueOf(this.tablacustodia.getValueAt(nFila, 0).toString());

            historico_custodia ot = null;
            historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();

            try {
                otcontrolDAO.EliminarCustodia(nItem);
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar ");
            return;
        }

        RefrescarGrillaCustodia GrillaP = new RefrescarGrillaCustodia();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.origencustodia.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarCustodiaActionPerformed


    private void BotonSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSeleccionarActionPerformed
        //Obtener el número de filas seleccionadas 
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            //El método valueChange se debe sobreescribir obligatoriamente. 
            //Se ejecuta automáticamente cada vez que se hace una nueva selección. 
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Obtener el número de filas seleccionadas 
                int cuentaFilasSeleccionadas = jTable1.getSelectedRowCount();
                System.out.println("Hay seleccionadas: " + cuentaFilasSeleccionadas + " filas");

                if (cuentaFilasSeleccionadas == 1) {
                    //Sólo hay una fila seleccionada 
                    int indiceFilaSeleccionada = jTable1.getSelectedRow();
                    System.out.println("Fila seleccionada: " + indiceFilaSeleccionada);
                } else {
                    //Hay varias filas seleccionadas 
                    int[] indicesfilasSeleccionadas = jTable1.getSelectedRows();
                    System.out.println("Filas seleccionadas: ");
                    for (int indice : indicesfilasSeleccionadas) {
                        System.out.print(indice + " ");
                        String cNumero = jTable1.getValueAt(indice, 1).toString();
                        String cReferencia = jTable1.getValueAt(indice, 0).toString();
                        System.out.print("Numero " + cNumero);
                        System.out.print("Creferencia " + cReferencia);

                    }
                    System.out.println();
                }
            }

        }
        );

        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSeleccionarActionPerformed


    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void BotonSeleccionarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonSeleccionarMouseClicked
        Object[] opciones = {"   Si   ", "   No   "};
        Object[] fila = new Object[5];
        this.limpiaCustoda();
        int ret = JOptionPane.showOptionDialog(null, "Desea Exportar los Préstamos Seleccionados? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            int cantidadRegistro = modelocusto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocusto.removeRow(0);
            }

            int[] indicesfilasSeleccionadas = jTable1.getSelectedRows();
            System.out.println("Filas seleccionadas: ");
            for (int indice : indicesfilasSeleccionadas) {
                String cReferencia = jTable1.getValueAt(indice, 0).toString();
                System.out.print(indice + " ");
                String cNumero = jTable1.getValueAt(indice, 1).toString();
                String cFecha = jTable1.getValueAt(indice, 2).toString();
                String cCliente = jTable1.getValueAt(indice, 3).toString();
                String cMontoPrestamo = jTable1.getValueAt(indice, 7).toString();
                fila[0] = cReferencia;
                fila[1] = cNumero;
                fila[2] = cFecha;
                fila[3] = cCliente;
                fila[4] = cMontoPrestamo;
                modelocusto.addRow(fila);
            }
            this.ExportarCustodia.setSize(645, 518);
            this.ExportarCustodia.setModal(true);
            ExportarCustodia.setTitle("Enviar a Custodia");
            ExportarCustodia.setLocationRelativeTo(null);
            ExportarCustodia.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSeleccionarMouseClicked

    private void origencActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_origencActionPerformed
        this.BuscarCustodiaO.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_origencActionPerformed

    private void BuscarCustodiaOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaOActionPerformed
        tipoconsulta = 3;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.origenc.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiao.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            destinoc.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaOActionPerformed

    private void BuscarCustodiaDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaDActionPerformed
        tipoconsulta = 4;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.destinoc.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiad.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            fechaprocesoc.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaDActionPerformed

    private void destinocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinocActionPerformed
        this.BuscarCustodiaD.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_destinocActionPerformed

    private void GrabarCusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCusActionPerformed
        if (Integer.valueOf(origenc.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            origenc.requestFocus();
            return;
        }
        if (Integer.valueOf(destinoc.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            destinoc.requestFocus();
            return;
        }
        Date dFechaHoy = ODate.de_java_a_sql(fechaprocesoc.getDate());

        int totalRow = modelocusto.getRowCount();
        totalRow -= 1;
        InetAddress addr;
        String ip = null;

        for (int i = 0; i <= (totalRow); i++) {
            historico_custodia ot = new historico_custodia();
            historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();
            ot.setOrigencustodia(Integer.valueOf(origenc.getText()));
            ot.setDestinocustodia(Integer.valueOf(destinoc.getText()));
            ot.setFechaproceso(dFechaHoy);
            ot.setIdmovimiento(String.valueOf(modelocusto.getValueAt(i, 0)));
            ot.setIdnumero(Integer.valueOf(modelocusto.getValueAt(i, 1).toString()));
            //SE CAPTURA N° DE IP DE LA PC
            try {
                addr = InetAddress.getLocalHost();
                ip = addr.getHostAddress();
            } catch (UnknownHostException ex) {
                Exceptions.printStackTrace(ex);
            }
            ot.setPc(ip);;

            try {
                otcontrolDAO.InsertarCustodia(ot);
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        JOptionPane.showMessageDialog(null, "Proceso Terminado");

        this.origenc.setText("0");
        this.destinoc.setText("0");
        this.nombrecustodiao.setText("");
        this.nombrecustodiad.setText("");
        this.origenc.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCusActionPerformed

    private void BorrarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarCActionPerformed

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = this.tablacusto.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nItem = Integer.valueOf(this.tablacusto.getValueAt(nFila, 0).toString());

            historico_custodia ot = null;
            historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();

            try {
                otcontrolDAO.EliminarCustodia(nItem);
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar ");
            return;
        }

        RefrescarGrillaCustodia GrillaP = new RefrescarGrillaCustodia();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.origenc.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarCActionPerformed

    private void SalircusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalircusActionPerformed
        ExportarCustodia.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalircusActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        int nNumero = Integer.valueOf(jTable1.getValueAt(nFila, 1).toString());
        prestamoDAO pDAO = new prestamoDAO();
        prestamo p = new prestamo();
        try {
            p = pDAO.buscarIdBancard(nNumero);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_crear();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "POST";

        String cJson = "{\"amount\":" + String.valueOf(p.getMonto_cuota()) + ",\n"
                + "\"first_installment_amount\":" + String.valueOf(p.getMonto_cuota()) + ",\n"
                + "\"description\":\"FINANCIACION\",\n"
                + "\"periodicity\":" + 1 + ",\n"
                + "\"debit_day\":" + p.getDia_debito() + ",\n"
                + "\"unlimited\":" + true + ",\n"
                + "\"start_date\": \"" + p.getFecha_inicio() + "\",\n" // Aquí concatenas la variable
                + "\"end_date\": \"" + p.getFecha_final() + "\",\n" // Aquí también
                + "\"reference_id\":\"" + p.getNumero() + "\"}";

        String cRespuesta = curl.ApiSuscripcion(cUrl, cUser, cPass, cJson, cPost);

        url = cRespuesta.substring(cRespuesta.indexOf("https"), cRespuesta.lastIndexOf("\""));
        goToURL(url);

//        this.AbrirBancard();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public void goToURL(String URL) {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI(URL);
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    System.out.println(ex);
                }
            }
        }
    }


    private void bancardWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_bancardWindowOpened
    }//GEN-LAST:event_bancardWindowOpened

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        int nFila = this.jTable1.getSelectedRow();
        int nNumero = Integer.valueOf(jTable1.getValueAt(nFila, 1).toString());
        prestamoDAO pDAO = new prestamoDAO();
        prestamo p = new prestamo();
        try {
            p = pDAO.buscarIdBancard(nNumero);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_recuperar();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "GET";

        String cJson = "{\"amount\":" + String.valueOf(p.getMonto_cuota()) + ",\n"
                + "\"first_installment_amount\":" + String.valueOf(p.getMonto_cuota()) + ",\n"
                + "\"description\":\"FINANCIACION\",\n"
                + "\"periodicity\":" + 1 + ",\n"
                + "\"debit_day\":" + p.getDia_debito() + ",\n"
                + "\"unlimited\":" + true + ",\n"
                + "\"start_date\": \"" + p.getFecha_inicio() + "\",\n" // Aquí concatenas la variable
                + "\"end_date\": \"" + p.getFecha_final() + "\",\n" // Aquí también
                + "\"reference_id\":\"" + p.getNumero() + "\"}";

        String cRespuesta = curl.ApiSuscripcion(cUrl, cUser, cPass, cJson, cPost);

        url = cRespuesta.substring(cRespuesta.indexOf("https"), cRespuesta.lastIndexOf("\""));
        goToURL(url);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_recuperar();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String auth = cUser + ":" + cPass;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        try {
            // URL del endpoint
            // URL de la API
            this.doTrustToCertificates();
            URL obj = new URL(cUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Establecer el método de la solicitud
            con.setRequestMethod("GET");

            // Añadir el encabezado de autorización
            con.setRequestProperty("Authorization", "Basic " + encodedAuth);

            // Obtener el código de respuesta
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            // Leer la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Procesar la respuesta JSON
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(response.toString());

            for (Object objData : jsonArray) {
                JSONObject jsonObject = (JSONObject) objData;
                Object[] rowData = {
                    jsonObject.get("_id"),
                    jsonObject.get("codigo"),
                    jsonObject.get("referencia"),
                    jsonObject.get("enlace"),
                    jsonObject.get("tiempo"),
                    jsonObject.get("estado")
                };
                modelobancard1.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.AbrirBancard();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        int nFila = this.tablabancard1.getSelectedRow();
        int nNumero = Integer.valueOf(tablabancard1.getValueAt(nFila, 2).toString());
        String cID = tablabancard1.getValueAt(nFila, 1).toString();
        prestamoDAO pDAO = new prestamoDAO();
        prestamo p = new prestamo();
        try {
            p = pDAO.buscarIdBancard(nNumero);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_actualizar();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "PUT";

        String cJson = "{\"amount\":" + p.getMonto_cuota() + ",\n"
                + "\"debit_day\":" + p.getDia_debito() + "}";

        System.out.println(cJson);

        String cRespuesta = curl.ApiSuscripcion(cUrl + cID, cUser, cPass, cJson, cPost);

        System.out.println(cRespuesta);

        //     goToURL(url);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPagarActionPerformed
        int nFila = this.tablabancard1.getSelectedRow();
        int nNumero = Integer.valueOf(tablabancard1.getValueAt(nFila, 2).toString());
        String cID = tablabancard1.getValueAt(nFila, 1).toString();
        prestamoDAO pDAO = new prestamoDAO();
        prestamo p = new prestamo();
        try {
            p = pDAO.buscarIdBancard(nNumero);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_adelantarpago();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "PUT";

        String cJson = "{"
                + "\"payments_to_advance\": " + 1 + ","
                + "\"description\": \"FINANCIACION\""
                + "}";

        String cRespuesta = curl.ApiSuscripcion(cUrl + cID, cUser, cPass, cJson, cPost);

        System.out.println(cRespuesta);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPagarActionPerformed

    private void BtnReintentarPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReintentarPagarActionPerformed
        int nFila = this.tablabancard1.getSelectedRow();
        int nNumero = Integer.valueOf(tablabancard1.getValueAt(nFila, 2).toString());
        String cID = tablabancard1.getValueAt(nFila, 1).toString();

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_reintentarpago();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "POST";

        String cJson = "{"
                + "}";

        String cRespuesta = curl.ApiSuscripcion(cUrl + cID, cUser, cPass, cJson, cPost);

        System.out.println(cRespuesta);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnReintentarPagarActionPerformed

    private void BtnPausarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPausarPagoActionPerformed
        int nFila = this.tablabancard1.getSelectedRow();
        int nNumero = Integer.valueOf(tablabancard1.getValueAt(nFila, 2).toString());
        String cID = tablabancard1.getValueAt(nFila, 1).toString();

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_pausarpago();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "PUT";

        String cJson = "{"
                + "\"months_to_pause\": " + 7
                + "}";
        String cRespuesta = curl.ApiSuscripcion(cUrl + cID, cUser, cPass, cJson, cPost);

        System.out.println(cRespuesta);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPausarPagoActionPerformed

    private void BtnReversarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReversarActionPerformed
        int nFila = this.tablabancard1.getSelectedRow();
        int nNumero = Integer.valueOf(tablabancard1.getValueAt(nFila, 2).toString());
        String cID = tablabancard1.getValueAt(nFila, 1).toString();

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_link_suscription_cancelar();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "DELETE";

        String cJson = "{"
                + "}";

        String cRespuesta = curl.ApiSuscripcion(cUrl + cID, cUser, cPass, cJson, cPost);

        System.out.println(cRespuesta);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnReversarActionPerformed

    private void BtnReversarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReversarPagoActionPerformed
        int nFila = this.tablabancard1.getSelectedRow();
        int nNumero = Integer.valueOf(tablabancard1.getValueAt(nFila, 2).toString());
        String cID = tablabancard1.getValueAt(nFila, 1).toString();

        configuracionDAO UrlDao = new configuracionDAO();
        configuracion config = new configuracion();
        config = UrlDao.configBancard();
        ApiBancardSuscripcion curl = new ApiBancardSuscripcion();
        String cUrl = config.getBancard_tpago_linkpago_reversar_hook_alias();
        String cUser = config.getAuth_bancard_user();
        String cPass = config.getAuth_bancard_password();
        String cPost = "PUT";

        String cJson = "{"
                + "}";

        String cRespuesta = curl.ApiSuscripcion(cUrl + cID, cUser, cPass, cJson, cPost);

        System.out.println(cRespuesta);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnReversarPagoActionPerformed

    private void doTrustToCertificates() throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    return;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    return;
                }
            }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private void TituloBancard() {
        modelobancard1.addColumn("ID");
        modelobancard1.addColumn("Código");
        modelobancard1.addColumn("Referencia");
        modelobancard1.addColumn("Enlace");
        modelobancard1.addColumn("Tiempo");
        modelobancard1.addColumn("Estado");

        int[] anchos = {100, 100, 100, 250, 150, 100};
        for (int i = 0; i < modelobancard1.getColumnCount(); i++) {
            tablabancard1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablabancard1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablabancard1.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablabancard1.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablabancard1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void AbrirBancard() {
        bancard.setModal(true);
        //(Ancho,Alto)
        bancard.setSize(1487, 954);
        //Establecemos un título para el jDialog
        bancard.setTitle("Bancard");
        bancard.setLocationRelativeTo(null);
        bancard.setVisible(true);
    }

    //El método valueChange se debe sobreescribir obligatoriamente. 
    //Se ejecuta automáticamente cada vez que se hace una nueva selección. 
    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Operacion");
        modelo.addColumn("Fecha");
        modelo.addColumn("Denominación Cliente");
        modelo.addColumn("Moneda");
        modelo.addColumn("Capital");
        modelo.addColumn("Interés");
        modelo.addColumn("Total Préstamo");
        modelo.addColumn("Total Desembolso");
        modelo.addColumn("Tasa");
        modelo.addColumn("Tipo Préstamo");
        modelo.addColumn("Nombre Asesor");
        modelo.addColumn("Estado");
        modelo.addColumn("Cuenta");
        modelo.addColumn("IVA S/Interés");
        modelo.addColumn("Gastos");
        modelo.addColumn("Tipo");
        modelo.addColumn("IdFactura");
        modelo.addColumn("Comisiones");
        modelo.addColumn("Serv. Déb. Automático");
        modelo.addColumn("Importe Cuota");
        modelo.addColumn("Socio");

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(16).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(16).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(17).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(17).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(21).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(21).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);

        int[] anchos = {3, 120, 90, 350, 100, 100, 100, 100, 100, 90, 100, 100, 100, 90, 100, 100, 90, 100, 100, 100, 100, 1};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(19).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(20).setCellRenderer(TablaRenderer);

    }

    public void cargarTabla() {
        //Uso la Clase SimpleDateFormat para darle formato al campo fecha
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        cSql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.socio,prestamos.tasa,";
        cSql = cSql + "clientes.nombre AS nombrecliente,vendedores.nombre AS nombreasesor,prestamos.totalprestamo,monto_cuota,";
        cSql = cSql + "importe,monedas.etiqueta AS nombremoneda,comprobantes.nomalias AS nombreprestamo, ";
        cSql = cSql + "prestamos.interes,prestamos.monto_entregar,prestamos.estado,prestamos.ivainteres,prestamos.comision_deudor,prestamos.tipo,prestamos.idfactura,";
        cSql = cSql + "prestamos.gastos_escritura,prestamos.segurovida ";
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
                Object[] fila = new Object[22]; // Cantidad de columnas en la tabla
                fila[0] = results.getString("idprestamos");
                fila[1] = results.getString("numero");
                fila[2] = formatoFecha.format(results.getDate("fecha"));
                fila[3] = results.getString("nombrecliente");
                fila[4] = results.getString("nombremoneda");
                fila[5] = formatea.format(results.getDouble("importe"));
                fila[6] = formatea.format(results.getDouble("interes"));
                fila[7] = formatea.format(results.getDouble("totalprestamo"));
                fila[8] = formatea.format(results.getDouble("monto_entregar"));
                fila[9] = formatea.format(results.getDouble("tasa"));
                fila[10] = results.getString("nombreprestamo");
                fila[11] = results.getString("nombreasesor");
                fila[12] = results.getString("estado");
                fila[13] = results.getString("socio");
                fila[14] = formatea.format(results.getDouble("ivainteres"));
                fila[15] = formatea.format(results.getDouble("comision_deudor"));
                fila[16] = results.getString("tipo");
                fila[17] = results.getString("idfactura");
                fila[18] = formatea.format(results.getDouble("gastos_escritura"));
                fila[19] = formatea.format(results.getDouble("segurovida"));
                fila[20] = formatea.format(results.getDouble("monto_cuota"));
                fila[21] = results.getInt("socio");
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = this.jTable1.getRowCount();
            if (cantFilas > 0) {
                this.jButton1.setEnabled(true);
                this.jButton3.setEnabled(true);
                this.BotonSeleccionar.setEnabled(true);
            } else {
                this.jButton1.setEnabled(false);
                this.jButton3.setEnabled(false);
                this.BotonSeleccionar.setEnabled(false);
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
                new prestamos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCustodia;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JDialog BCustodia;
    private javax.swing.JDialog BGiraduria;
    private javax.swing.JButton BorrarC;
    private javax.swing.JButton BorrarCustodia;
    private javax.swing.JButton BotonConfirma;
    private javax.swing.JButton BotonSeleccionar;
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnPagar;
    private javax.swing.JButton BtnPausarPago;
    private javax.swing.JButton BtnReintentarPagar;
    private javax.swing.JButton BtnReversar;
    private javax.swing.JButton BtnReversarPago;
    private javax.swing.JButton BuscarCustodiaD;
    private javax.swing.JButton BuscarCustodiaDestino;
    private javax.swing.JButton BuscarCustodiaO;
    private javax.swing.JButton BuscarCustodiaOrigen;
    private javax.swing.JDialog ExportarCustodia;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JButton GrabarCus;
    private javax.swing.JButton GrabarCustodia;
    private javax.swing.JTextField JTBuscarCustodia;
    private javax.swing.JTextField Opciones;
    private javax.swing.JButton Refrescar;
    private javax.swing.JButton SalirCustodia;
    private javax.swing.JButton SalirDebito;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton Salircus;
    private javax.swing.JMenuItem actualizarclientes;
    private javax.swing.JDialog aprobar;
    private javax.swing.JTextField autorizacion;
    private javax.swing.JDialog bancard;
    private javax.swing.JButton buscarcodgiraduria;
    private javax.swing.JTextField codgiraduria;
    private javax.swing.JComboBox combocustodia;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JFormattedTextField comisiones;
    private javax.swing.JButton condicionar;
    private javax.swing.JTextField creferencia;
    private javax.swing.JDialog custodiaPagare;
    private javax.swing.JDialog debito;
    private javax.swing.JTextField destinoc;
    private javax.swing.JTextField destinocustodia;
    private javax.swing.JMenuItem facturacapital;
    private javax.swing.JMenuItem facturainteresprestamo;
    private com.toedter.calendar.JDateChooser fechaemisionprestamo;
    private com.toedter.calendar.JDateChooser fechaprestamo;
    private com.toedter.calendar.JDateChooser fechaprestamo1;
    private com.toedter.calendar.JDateChooser fechaprestamo2;
    private com.toedter.calendar.JDateChooser fechaprocesoc;
    private com.toedter.calendar.JDateChooser fechaprocesocustodia;
    private javax.swing.JPanel fondo;
    private javax.swing.JDialog genfacturacapital;
    private javax.swing.JDialog genfacturaotros;
    private javax.swing.JButton grabar;
    private javax.swing.JButton grabarfactura1;
    private javax.swing.JButton grabarfactura2;
    private javax.swing.JTextField idmovimiento;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JFormattedTextField importecuota;
    private javax.swing.JFormattedTextField importegasto;
    private javax.swing.JFormattedTextField importeprestamo;
    private javax.swing.JButton imprimirfactura1;
    private javax.swing.JFormattedTextField interes;
    private javax.swing.JFormattedTextField interesordinario;
    private javax.swing.JFormattedTextField intordinario;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTextField jTBuscarGiraduria;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelTask labelTask1;
    private javax.swing.JLabel lblgastos;
    private javax.swing.JButton logobancard;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField moneda1;
    private javax.swing.JTextField moneda2;
    private javax.swing.JFormattedTextField montoaprobado;
    private javax.swing.JFormattedTextField neto;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombrecliente1;
    private javax.swing.JTextField nombrecliente2;
    private javax.swing.JTextField nombrecustodiad;
    private javax.swing.JTextField nombrecustodiadestino;
    private javax.swing.JTextField nombrecustodiao;
    private javax.swing.JTextField nombrecustodiaorigen;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nombretitularprestamo;
    private javax.swing.JTextField nrocuenta;
    private javax.swing.JTextField nrofactura1;
    private javax.swing.JTextField nrofactura2;
    private javax.swing.JTextField nroprestamo;
    private javax.swing.JTextField nroprestamo1;
    private javax.swing.JTextField nroprestamo2;
    private javax.swing.JTextField nrotimbrado;
    private javax.swing.JTextField numero;
    private javax.swing.JTextField numeroprestamo;
    private javax.swing.JTextArea observacion1;
    private javax.swing.JTextArea observacion2;
    private javax.swing.JTextArea observaciones;
    private javax.swing.JTextField oficialcuenta;
    private javax.swing.JMenuItem ordenpago;
    private javax.swing.JTextField origenc;
    private javax.swing.JTextField origencustodia;
    private javax.swing.JMenuItem pagarecuotas;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JMenuItem printcontrato;
    private javax.swing.JMenuItem printliquidacion;
    private javax.swing.JMenuItem printpagarecuotas;
    private javax.swing.JMenuItem printpagaretotal;
    private javax.swing.JMenuItem printsolicitud;
    private javax.swing.JButton rechazar;
    private javax.swing.JButton salirfactura1;
    private javax.swing.JButton salirfactura2;
    private javax.swing.JButton saliropcion;
    private javax.swing.JFormattedTextField seguro;
    private javax.swing.JTable tablabancard1;
    private javax.swing.JTable tablacusto;
    private javax.swing.JTable tablacustodia;
    private javax.swing.JTable tablaentidadcustodia;
    private javax.swing.JTable tablagiraduria;
    private javax.swing.JTextField tipoprestamo;
    private javax.swing.JFormattedTextField totalprestamo;
    private com.toedter.calendar.JDateChooser vencetimbrado;
    // End of variables declaration//GEN-END:variables

    private class GenerarOp extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = jTable1.getValueAt(nFila, 8).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Referencia", jTable1.getValueAt(nFila, 0).toString());
                parameters.put("Letra", numero.Convertir(num, true, 1));
                JasperReport jr = null;
                //OTRAS EMPRESAS
                //  URL url = getClass().getClassLoader().getResource("Reports/orden_pago_prestamos.jasper");
                //PRECIOS BAJOS    
                URL url = getClass().getClassLoader().getResource("Reports/orden_pago_preciosbajos.jasper");
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

    private class GenerarLiquidacion extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreLiquidacion = config.getNombreliquidacion();
            String cNombreLiquidacionPh = config.getNombreliquidacionph();
            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                int nTipo = Integer.valueOf(jTable1.getValueAt(nFila, 16).toString());
                System.out.println("tipo " + nTipo);
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", jTable1.getValueAt(nFila, 1).toString());

                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/pagares.jasper");

                //PARA A+B
                /*   if (nTipo == 8) {
                    URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacion.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                } else {
                    URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacionPh.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                }*/
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

    private class GrabarDetalleCapital extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha

            con = new Conexion();
            stm = con.conectar();
            Connection conn = null;
            Date FechaProceso = ODate.de_java_a_sql(fechaprestamo1.getDate());
            Date PrimerVence = ODate.de_java_a_sql(fechaprestamo1.getDate());
            Date VenceTimbrado = ODate.de_java_a_sql(vencetimbrado.getDate());

            String cNumeroFactura = nrofactura1.getText();
            cNumeroFactura = cNumeroFactura.replace("-", "");
            String cContadorFactura = cNumeroFactura.substring(6, 13);

            String cFactura = nrofactura1.getText();

            String cmoneda = Config.cMonedaDefecto;
            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();
            String cOrdinario = intordinario.getText();
            cOrdinario = cOrdinario.replace(".", "");
            cOrdinario = cOrdinario.replace(",", ".");
            double nOrdi = Double.valueOf(cOrdinario);

            String cGastos = importegasto.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");

            String cComi = comisiones.getText();
            cComi = cComi.replace(".", "");
            cComi = cComi.replace(",", ".");

            String cSegu = seguro.getText();
            cSegu = cSegu.replace(".", "");
            cSegu = cSegu.replace(",", ".");

            double nGasto = Double.valueOf(cGastos) + Math.round(Double.valueOf(cGastos) * Config.nArancelIva / 100);
            cGastos = String.valueOf(nGasto);
            double nComi = Double.valueOf(cComi) + Math.round(Double.valueOf(cComi) * Config.nArancelIva / 100);
            cComi = String.valueOf(nComi);
            double nSegu = Double.valueOf(cSegu) + Math.round(Double.valueOf(cSegu) * Config.nArancelIva / 100);
            cSegu = String.valueOf(nSegu);

            double TotalNeto = Double.valueOf(cOrdinario) + nGasto + nComi + nSegu;
            String cTotalNeto = String.valueOf(TotalNeto);
            double montoiva10 = Math.round(Double.valueOf(Double.valueOf(cOrdinario) + nGasto + nComi + nSegu / 11));
            String cImpiva = String.valueOf(montoiva10);
            String cIva = String.valueOf(Config.nArancelIva);
            String cCotizacion = "1";
            String cIvaDetalle = "";
            String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,formatofactura,vencimiento,vencimientotimbrado,nrotimbrado,cliente,sucursal,moneda,giraduria,";
            cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idusuario)";
            cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cNumeroFactura + "','" + cFactura + "','" + PrimerVence + "','" + VenceTimbrado + "','" + nrotimbrado.getText() + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
            cSqlCab += "2" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + "0" + "','" + cTotalNeto + "','" + "0" + "','" + cTotalNeto + "','" + observacion1.getText().toString() + "','" + Config.CodUsuario + "')";
            try {
                conn = stm.getConnection();
                conn.setAutoCommit(false);

                try {
                    stm.executeUpdate(cSqlCab);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cCantidad = "1";

                if (Integer.valueOf(Config.cInteresPrestamo) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nOrdi / 11));
                    String cSqlDetalle1 = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle1 += "VALUES ('" + cReferencia + "','" + Config.cInteresPrestamo + "','" + cCantidad + "','" + '0' + "','" + cOrdinario + "','" + cOrdinario + "','" + cIvaDetalle + "','" + cIva + "')";

                    try {
                        stm.executeUpdate(cSqlDetalle1);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                if (Integer.valueOf(Config.cCodColocacion) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nGasto / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodColocacion + "','" + cCantidad + "','" + '0' + "','" + cGastos + "','" + cGastos + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                if (Integer.valueOf(Config.cCodComision) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nComi / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodComision + "','" + cCantidad + "','" + '0' + "','" + cComi + "','" + cComi + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                if (Integer.valueOf(Config.cCodSeguroVida) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nSegu / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodSeguroVida + "','" + cCantidad + "','" + '0' + "','" + cSegu + "','" + cSegu + "','" + cIvaDetalle + "','" + cIva + "')";
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

                String cSqlControlFactura = "INSERT INTO facturaprestamos (idprestamo,idfactura,opcion) VALUES('" + cReferencia + "','" + cReferencia + "','" + '1' + "')";
                try {
                    stm.executeUpdate(cSqlControlFactura);
                    conn.commit();
                    stm.close();
                } catch (SQLException ex) {
                    conn.rollback();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "La Factura ya fue Generada, Verifique");
                    Exceptions.printStackTrace(ex);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class ImprimirFacturaCapital extends Thread {

        public void run() {
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte

                String num = cTotalImprimir;
                System.out.println(num);
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //  URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + Config.cNombreFactura.toString());
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

    private class GenFacturaGastos extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            String cSql = "SELECT factura+1  AS nRegistro FROM sucursales";
            try {
                results = stm.executeQuery(cSql);
                if (results.next()) {
                    nrofactura2.setText(results.getString("nRegistro"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class GrabarDetalleOtros extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha

            Date FechaProceso = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date PrimerVence = ODate.de_java_a_sql(fechaprestamo2.getDate());
            //Obteniendo Datos de los Combodatos
            String cmoneda = Config.cMonedaDefecto;
            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();
            String cFactura = nrofactura2.getText();
            String cGravadas10 = interesordinario.getText();
            cGravadas10 = cGravadas10.replace(".", "");
            cGravadas10 = cGravadas10.replace(",", ".");

            con = new Conexion();
            stm = con.conectar();
            String cCotizacion = "1";
            String cCodGasto = null;
            ResultSet results = null;
            Connection conn = null;

            try {
                conn = stm.getConnection();
                conn.setAutoCommit(false);

                String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,";
                cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idprestamo,idusuario)";
                cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cFactura + "','" + PrimerVence + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
                cSqlCab += "2" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + "0" + "','" + cGravadas10 + "','" + "0" + "','" + cGravadas10 + "','" + observacion2.getText().toString() + "','" + nroprestamo2.getText() + "','" + Config.CodUsuario + "')";
                try {
                    stm.executeUpdate(cSqlCab);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cMonto = interesordinario.getText();
                cMonto = cMonto.replace(".", "");
                cMonto = cMonto.replace(",", ".");

                String cCantidad = "1";

                String cPrecio = cMonto;

                String cIva = "10";
                double montoiva = Double.valueOf(cMonto);
                montoiva = Math.round(montoiva / 11);
                String cImpiva = String.valueOf(montoiva);
                //cImpiva = cImpiva.replace(".", "");
                //cImpiva = cImpiva.replace(",", ".");

                cCodGasto = Config.cInteresPrestamo;
                String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                cSqlDetalle += "VALUES ('" + cReferencia + "','" + cCodGasto + "','" + cCantidad + "','" + '0' + "','" + cPrecio + "','" + cMonto + "','" + cImpiva + "','" + cIva + "')";

                try {
                    stm.executeUpdate(cSqlDetalle);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cSqlCuentas = "INSERT INTO cuenta_clientes (iddocumento,creferencia,documento,fecha,vencimiento,";
                cSqlCuentas += "numerocuota,cuota,cliente,sucursal,moneda,comprobante,importe,saldo,totalconcedido,capital,minteres,amortiza,inversionista,tasaoperativa)";
                cSqlCuentas += "values('" + cReferencia + "','" + cReferencia + "','" + nroprestamo2.getText() + "','" + FechaProceso + "','" + PrimerVence + "','";
                cSqlCuentas += "1" + "','" + "1" + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "2" + "','";
                cSqlCuentas += cMonto + "','" + cMonto + "','" + cMonto + "','" + "0" + "','" + "0" + "','" + "0" + "','" + "0" + "','" + "0" + "')";

                try {
                    stm.executeUpdate(cSqlCuentas);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cSqlFactura = "UPDATE sucursales SET factura= '" + cFactura + "'";

                try {
                    stm.executeUpdate(cSqlFactura);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                String cSqlControlFactura2 = "INSERT INTO facturaprestamos (idprestamo,idfactura,opcion) VALUES('" + idPrestamo + "','" + cReferencia + "','" + '2' + "')";
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
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class ImprimirFacturaGastos extends Thread {

        public void run() {

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = jTable1.getSelectedRow();
                String num = jTable1.getValueAt(nFila, 6).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //  URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + Config.cNombreFactura.toString());
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

            int nFila = jTable1.getSelectedRow();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            cReferencia = jTable1.getValueAt(nFila, 0).toString();
            int nCliente = Integer.valueOf(jTable1.getValueAt(nFila, 21).toString());

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cReferencia", cReferencia);
                parameters.put("nCliente", nCliente);
                JasperReport jr = null;
                //Ficha de Cliente de PUERTO SEGURO
                //   URL url = getClass().getClassLoader().getResource("Reports/fichacliente.jasper");
                // URL url = getClass().getClassLoader().getResource("Reports/fichacliente_crediseguro.jasper");
                //             URL url = getClass().getClassLoader().getResource("Reports/fichapreciosbajos.jasper");
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

    private class PagareCuotas extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            stm2 = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombrePagare = config.getNombrepagarecuota();
            int nFila = jTable1.getSelectedRow();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nNumero = Integer.valueOf(jTable1.getValueAt(nFila, 1).toString());
            String cMoneda = jTable1.getValueAt(nFila, 4).toString();
            String nNumeroPrestamo = String.valueOf(nNumero);
            String cSql = "SELECT nrocuota,monto FROM detalle_prestamo WHERE nprestamo=" + nNumero + " ORDER BY nrocuota ";
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    double nTotalNeto = results.getDouble("monto");
                    String nCuota = results.getString("nrocuota");
                    try {
                        Map parameters = new HashMap();
                        //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                        //en el reporte
                        String num = String.valueOf(nTotalNeto);
                        numero_a_letras numero = new numero_a_letras();
                        if (cMoneda.equals("GS.")) {
                            parameters.put("Letra", numero.Convertir(num, true, 1));
                        } else {
                            parameters.put("Letra", numero.Convertir(num, true, 2));
                        }
                        parameters.put("nNumeroPrestamo", nNumeroPrestamo);
                        parameters.put("nCuota", nCuota);
                        JasperReport jr = null;
                        URL url = getClass().getClassLoader().getResource("Reports/" + cNombrePagare.toString());
                        jr = (JasperReport) JRLoader.loadObject(url);
                        JasperPrint masterPrint = null;
                        //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                        masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                        //         JasperViewer ventana = new JasperViewer(masterPrint, false);
                        //         ventana.setTitle("Vista Previa");
                        //          ventana.setVisible(true);

                        //Enviar Directo a Impresora
                        JasperPrintManager.printReport(masterPrint, false);
                        //Enviar Directo a Impresora
                        // JasperPrintManager.printReport(masterPrint, false);
                    } catch (Exception e) {
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
                    }
                }
                results.close();
                stm.close();
                stm2.close();
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
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
                int nFila = jTable1.getSelectedRow();
                String cMoneda = jTable1.getValueAt(nFila, 4).toString();
                String cNumeroPrestamo = jTable1.getValueAt(nFila, 1).toString();

                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = jTable1.getValueAt(nFila, 7).toString();

                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = jTable1.getValueAt(nFila, 6).toString();
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
                System.out.println(num);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombrePagare.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);

                //Enviar Directo a Impresora
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
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
                int nFila = jTable1.getSelectedRow();
                String cMoneda = jTable1.getValueAt(nFila, 4).toString();
                String cNumeroPrestamo = jTable1.getValueAt(nFila, 1).toString();

                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = jTable1.getValueAt(nFila, 7).toString();
                String cMonto_Entregar = jTable1.getValueAt(nFila, 7).toString();
                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = jTable1.getValueAt(nFila, 6).toString();
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

    private class GrillaCustodia extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloentidadcustodia.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloentidadcustodia.removeRow(0);
            }
            custodiaDAO DAOCAJA = new custodiaDAO();
            try {
                for (custodia ca : DAOCAJA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre()};
                    modeloentidadcustodia.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaentidadcustodia.setRowSorter(new TableRowSorter(modeloentidadcustodia));
            int cantFilas = tablaentidadcustodia.getRowCount();
        }
    }

    private class RefrescarGrillaCustodia extends Thread {

        public void run() {
            int cantidadRegistro = modelocustodia.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocustodia.removeRow(0);
            }

            historico_custodiasDAO cuDAO = new historico_custodiasDAO();
            try {
                for (historico_custodia detcu : cuDAO.BuscarId(idmovimiento.getText())) {
                    String Detalle[] = {formatoSinpunto.format(detcu.getId()), formatoFecha.format(detcu.getFechaproceso()), detcu.getNombreorigen(), detcu.getNombredestino(), detcu.getPc(), detcu.getHora()};
                    modelocustodia.addRow(Detalle);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            int cantFilas = tablacustodia.getRowCount();
            if (cantFilas > 0) {
                BorrarCustodia.setEnabled(true);
            } else {
                BorrarCustodia.setEnabled(false);
            }
        }
    }
}

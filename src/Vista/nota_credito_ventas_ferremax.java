/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.BuscadorImpresora;
import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.cajaDAO;
import DAO.clienteDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.cuenta_clienteDAO;
import DAO.detalle_ventaDAO;
import DAO.giraduriaDAO;
import DAO.monedaDAO;
import DAO.nota_creditoDAO;
import DAO.sucursalDAO;
import DAO.vendedorDAO;
import DAO.ventaDAO;
import DAO.productoDAO;
import Modelo.Tablas;
import Modelo.caja;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.detalle_venta;
import Modelo.giraduria;
import Modelo.moneda;
import Modelo.nota_credito;
import Modelo.producto;
import Modelo.sucursal;
import Modelo.vendedor;
import Modelo.venta;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class nota_credito_ventas_ferremax extends javax.swing.JFrame {

    Conexion con = null;
    ResultSet results = null;
    Statement stm, stm2 = null;
    Tablas modelo = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelocomprobante = new Tablas();
    Tablas modeloproducto = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelovendedor = new Tablas();
    Tablas modelocaja = new Tablas();
    Tablas modelonota = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltrocomprobante, trsfiltromoneda, trsfiltroproducto, trsfiltrovendedor, trsfiltrocaja;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formatcantidad = new DecimalFormat("######.####");
    int nFila = 0;
    String cTotalNeto = null;
    String cSql = null;
    String cEfectivo = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String cTasa, referencia = null;
    double nPorcentajeIVA = 0.00;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");

    public nota_credito_ventas_ferremax() {
        initComponents();
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.SalirCompleto.setIcon(iconosalir);
        this.Salir.setIcon(iconosalir);
        this.buscarSucursal.setIcon(iconobuscar);
        this.BuscarProducto.setIcon(iconobuscar);
        this.BuscarVendedor.setIcon(iconobuscar);
        this.buscarCliente.setIcon(iconobuscar);
        this.buscarMoneda.setIcon(iconobuscar);
        this.BuscarEmisor.setIcon(iconobuscar);
        this.BuscarBanco.setIcon(iconobuscar);
        this.BuscarVendedor.setIcon(iconobuscar);
        this.buscarcomprobante.setIcon(iconobuscar);
        this.BuscarCaja.setIcon(iconobuscar);
        this.refrescar.setIcon(icorefresh);
        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        this.idfactura.setVisible(false);
        this.idnotacredito.setVisible(false);
        this.tablanotacredito.setShowGrid(false);
        this.tablanotacredito.setOpaque(true);
        this.tablanotacredito.setBackground(new Color(204, 204, 255));
        this.tablanotacredito.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setVisible(false);
        this.venceanterior.setVisible(false);
        this.vencimientos.setVisible(false);
        this.creferencia.setVisible(false);
        this.cModo.setVisible(false);
        this.nombreproducto.setEnabled(false);
        this.modo.setVisible(false);
        this.totalitem.setEnabled(false);
        this.idControl.setText("0");
        this.Inicializar();
        this.cargarTitulo();
        this.TituloDetalle();
        this.CargarTituloReferencia();
        this.TituloCaja();
        this.TitSuc();
        this.TitVendedor();
        this.TitMoneda();
        this.TituloProductos();;
        this.TituloComprobante();
        this.TitClie();
        GrillaVenta GrillaOC = new GrillaVenta();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        GrillaCajaDevo grillacaja = new GrillaCajaDevo();
        Thread hilocaja = new Thread(grillacaja);
        hilocaja.start();

    }
    Control hand = new Control();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BProducto = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        comboproducto = new javax.swing.JComboBox();
        jTBuscarProducto = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaproducto = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarProducto = new javax.swing.JButton();
        SalirProducto = new javax.swing.JButton();
        detalle_notacredito = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        comprobante = new javax.swing.JTextField();
        buscarcomprobante = new javax.swing.JButton();
        nombrecomprobante = new javax.swing.JTextField();
        creferencia = new javax.swing.JTextField();
        modo = new javax.swing.JTextField();
        factura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        vencetimbrado = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        nrotimbrado = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        nombrecliente = new javax.swing.JTextField();
        direccioncliente = new javax.swing.JTextField();
        nombremoneda = new javax.swing.JTextField();
        buscarMoneda = new javax.swing.JButton();
        moneda = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JFormattedTextField();
        vendedor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        BuscarVendedor = new javax.swing.JButton();
        nombrevendedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        primervence = new com.toedter.calendar.JDateChooser();
        buscarCliente = new javax.swing.JButton();
        venceanterior = new com.toedter.calendar.JDateChooser();
        vencimientos = new com.toedter.calendar.JDateChooser();
        cuotas = new javax.swing.JFormattedTextField();
        jLabel45 = new javax.swing.JLabel();
        caja = new javax.swing.JTextField();
        BuscarCaja = new javax.swing.JButton();
        nombrecaja = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabladetalle = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        nuevoitem = new javax.swing.JButton();
        editaritem = new javax.swing.JButton();
        delitem = new javax.swing.JButton();
        panelimagen = new javax.swing.JPanel();
        imagen = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        Salir = new javax.swing.JButton();
        Grabar = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        totalneto = new javax.swing.JFormattedTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        exentas = new javax.swing.JFormattedTextField();
        gravadas10 = new javax.swing.JFormattedTextField();
        gravadas5 = new javax.swing.JFormattedTextField();
        BSucursal = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BCliente = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BComprobante = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combocomprobante = new javax.swing.JComboBox();
        jTBuscarComprobante = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablacomprobante = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarComprobante = new javax.swing.JButton();
        SalirComprobante = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        aprobarcredito = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        grabaroc = new javax.swing.JButton();
        saliropcion = new javax.swing.JButton();
        numeroc = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        fechac = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        nombreclientec = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        importec = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        descuentoc = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        neto = new javax.swing.JFormattedTextField();
        itemnotacreditovta = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        codprod = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        BuscarProducto = new javax.swing.JButton();
        nombreproducto = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cantidad = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        precio = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        totalitem = new javax.swing.JFormattedTextField();
        jLabel29 = new javax.swing.JLabel();
        cModo = new javax.swing.JTextField();
        porcentaje = new javax.swing.JFormattedTextField();
        jLabel30 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        GrabarItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        BVendedor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combovendedor = new javax.swing.JComboBox();
        jTBuscarVendedor = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablavendedor = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        ingresar_cobros = new javax.swing.JDialog();
        jPanel27 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        totalventa = new javax.swing.JFormattedTextField();
        jPanel28 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        efectivo = new javax.swing.JFormattedTextField();
        jPanel29 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        importecheque = new javax.swing.JFormattedTextField();
        jLabel34 = new javax.swing.JLabel();
        nrocheque = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        emisioncheque = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        cargobanco = new javax.swing.JTextField();
        nombrebanco = new javax.swing.JTextField();
        BuscarBanco = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        pagotarjeta = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        nrotarjeta = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        emisiontarjeta = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        emisor = new javax.swing.JTextField();
        nombreemisor = new javax.swing.JTextField();
        BuscarEmisor = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        AceptarCobro = new javax.swing.JButton();
        SalirFormaCobro = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        sucambio = new javax.swing.JFormattedTextField();
        lotes = new javax.swing.JDialog();
        jPanel33 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        facturainicial = new javax.swing.JTextField();
        facturafinal = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        imprimirlotes = new javax.swing.JButton();
        SalirLotes = new javax.swing.JButton();
        BCaja = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        combocaja = new javax.swing.JComboBox();
        jTBuscarCaja = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablacaja = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        AceptarCaja = new javax.swing.JButton();
        SalirCaja = new javax.swing.JButton();
        itemreferencias = new javax.swing.JDialog();
        jPanel38 = new javax.swing.JPanel();
        notacredito = new javax.swing.JTextField();
        timbrado = new javax.swing.JTextField();
        nrofactura = new javax.swing.JTextField();
        GrabarNota = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        codcliente = new javax.swing.JTextField();
        nombreclientecredito = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        idnotacredito = new javax.swing.JTextField();
        idfactura = new javax.swing.JTextField();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        nota = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetacredito = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        buscarcadena = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        SalirCompleto = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablanotacredito = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        BProducto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProducto.setTitle("null");

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproducto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproductoActionPerformed(evt);
            }
        });

        jTBuscarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarProducto.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarProductoActionPerformed(evt);
            }
        });
        jTBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarProductoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaproducto.setModel(modeloproducto);
        tablaproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproductoMouseClicked(evt);
            }
        });
        tablaproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaproductoKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tablaproducto);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarProducto.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProductoActionPerformed(evt);
            }
        });

        SalirProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirProducto.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarProducto)
                    .addComponent(SalirProducto))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProductoLayout = new javax.swing.GroupLayout(BProducto.getContentPane());
        BProducto.getContentPane().setLayout(BProductoLayout);
        BProductoLayout.setHorizontalGroup(
            BProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProductoLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProductoLayout.setVerticalGroup(
            BProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProductoLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        detalle_notacredito.setTitle("Registrar Nota de Crédito");
        detalle_notacredito.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_notacreditoFocusGained(evt);
            }
        });
        detalle_notacredito.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_notacreditoWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_notacredito.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_notacreditoWindowActivated(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setText("Sucursal");

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
        buscarSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                buscarSucursalMousePressed(evt);
            }
        });
        buscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        jLabel1.setText("Nota Crédito N°");

        jLabel2.setText("Fecha Emisión");

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

        jLabel6.setText("Comprobante");

        comprobante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comprobante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comprobanteFocusGained(evt);
            }
        });
        comprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprobanteActionPerformed(evt);
            }
        });
        comprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comprobanteKeyPressed(evt);
            }
        });

        buscarcomprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarcomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcomprobanteActionPerformed(evt);
            }
        });

        nombrecomprobante.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecomprobante.setEnabled(false);

        creferencia.setEnabled(false);

        modo.setEnabled(false);

        factura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        factura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                facturaKeyPressed(evt);
            }
        });

        jLabel5.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jLabel5.text")); // NOI18N

        vencetimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vencetimbradoKeyPressed(evt);
            }
        });

        jLabel7.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jLabel7.text")); // NOI18N

        nrotimbrado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrotimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrotimbradoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(factura, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombresucursal))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(vencetimbrado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarcomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarcomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Cliente");

        cliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clienteFocusGained(evt);
            }
        });
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

        nombrecliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecliente.setEnabled(false);

        direccioncliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        direccioncliente.setEnabled(false);

        nombremoneda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombremoneda.setEnabled(false);

        buscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarMonedaActionPerformed(evt);
            }
        });

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

        jLabel9.setText("Moneda");

        jLabel14.setText("Cotización");

        cotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        cotizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });

        vendedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendedorActionPerformed(evt);
            }
        });
        vendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vendedorKeyPressed(evt);
            }
        });

        jLabel15.setText("Vendedor");

        BuscarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarVendedorActionPerformed(evt);
            }
        });

        nombrevendedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrevendedor.setEnabled(false);

        jLabel10.setText("Plazo");

        jLabel13.setText("Vencimiento");

        primervence.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primervenceKeyPressed(evt);
            }
        });

        buscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarClienteActionPerformed(evt);
            }
        });

        venceanterior.setEnabled(false);

        vencimientos.setEnabled(false);

        cuotas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        cuotas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cuotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cuotasKeyPressed(evt);
            }
        });

        jLabel45.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jLabel13.text")); // NOI18N

        caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        caja.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.caja.text")); // NOI18N
        caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaActionPerformed(evt);
            }
        });

        BuscarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCajaActionPerformed(evt);
            }
        });

        nombrecaja.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.nombrecaja.text")); // NOI18N
        nombrecaja.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(moneda, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(cuotas))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(primervence, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel15)
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vendedor)
                            .addComponent(cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(BuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nombrevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel13)
                                .addComponent(cuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel45)
                                .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(primervence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabladetalle.setModel(modelodetalle);
        tabladetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabladetalleMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tabladetalle);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nuevoitem.setText("Agregar");
        nuevoitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoitemActionPerformed(evt);
            }
        });

        editaritem.setText("Editar");
        editaritem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editaritemActionPerformed(evt);
            }
        });

        delitem.setText("Eliminar");
        delitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delitemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editaritem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuevoitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nuevoitem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editaritem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delitem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelimagen.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        imagen.setText("Imagen");

        javax.swing.GroupLayout panelimagenLayout = new javax.swing.GroupLayout(panelimagen);
        panelimagen.setLayout(panelimagenLayout);
        panelimagenLayout.setHorizontalGroup(
            panelimagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelimagenLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(imagen)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        panelimagenLayout.setVerticalGroup(
            panelimagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelimagenLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(imagen)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));

        observacion.setColumns(20);
        observacion.setRows(5);
        jScrollPane3.setViewportView(observacion);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Salir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salir.setText("Salir");
        Salir.setToolTipText("Salir sin Guardar");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        Grabar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Grabar.setText("Grabar");
        Grabar.setToolTipText("Guardar los Cambios");
        Grabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Salir)
                    .addComponent(Grabar))
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Total Neto"));

        totalneto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalneto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalneto.setEnabled(false);
        totalneto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalneto, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Liquidación IVA"));

        jLabel19.setText("Exentas");

        jLabel21.setText("10 %");

        jLabel24.setText("5%");

        exentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        exentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        exentas.setEnabled(false);

        gravadas10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        gravadas10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas10.setEnabled(false);

        gravadas5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        gravadas5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas5.setEnabled(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19)
                    .addComponent(jLabel24))
                .addGap(28, 28, 28)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exentas, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(gravadas10)
                    .addComponent(gravadas5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(exentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(gravadas10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detalle_notacreditoLayout = new javax.swing.GroupLayout(detalle_notacredito.getContentPane());
        detalle_notacredito.getContentPane().setLayout(detalle_notacreditoLayout);
        detalle_notacreditoLayout.setHorizontalGroup(
            detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_notacreditoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detalle_notacreditoLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(81, 81, 81)
                        .addGroup(detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detalle_notacreditoLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelimagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        detalle_notacreditoLayout.setVerticalGroup(
            detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_notacreditoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(detalle_notacreditoLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelimagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_notacreditoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(detalle_notacreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_notacreditoLayout.createSequentialGroup()
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19))
        );

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
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

        BComprobante.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BComprobante.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocomprobante.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocomprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocomprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocomprobanteActionPerformed(evt);
            }
        });

        jTBuscarComprobante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarComprobante.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane6.setViewportView(tablacomprobante);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarComprobante.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarComprobanteActionPerformed(evt);
            }
        });

        SalirComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirComprobante.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirComprobanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarComprobante)
                    .addComponent(SalirComprobante))
                .addContainerGap())
        );

        javax.swing.GroupLayout BComprobanteLayout = new javax.swing.GroupLayout(BComprobante.getContentPane());
        BComprobante.getContentPane().setLayout(BComprobanteLayout);
        BComprobanteLayout.setHorizontalGroup(
            BComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BComprobanteLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BComprobanteLayout.setVerticalGroup(
            BComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BComprobanteLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("N° Orden Crédito");

        grabaroc.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabaroc.setText("Aceptar");
        grabaroc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarocActionPerformed(evt);
            }
        });

        saliropcion.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        saliropcion.setText("Salir");
        saliropcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saliropcionActionPerformed(evt);
            }
        });

        numeroc.setEditable(false);
        numeroc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel17.setText("Fecha");

        fechac.setBackground(new java.awt.Color(255, 255, 255));
        fechac.setForeground(new java.awt.Color(255, 255, 255));
        fechac.setEnabled(false);
        fechac.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel18.setText("Nombre del Socio");

        nombreclientec.setEditable(false);
        nombreclientec.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel20.setText("Importe Solicitado");

        importec.setEditable(false);
        importec.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel22.setText("Descuentos");

        descuentoc.setEditable(false);
        descuentoc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel23.setText("Monto a Entregar");

        neto.setEditable(false);
        neto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addGap(58, 58, 58)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreclientec)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(descuentoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                        .addComponent(importec, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(numeroc, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fechac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                                .addGap(0, 146, Short.MAX_VALUE)))
                        .addGap(88, 88, 88))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(neto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(grabaroc)
                .addGap(18, 18, 18)
                .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(numeroc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(fechac, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreclientec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(descuentoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grabaroc, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout aprobarcreditoLayout = new javax.swing.GroupLayout(aprobarcredito.getContentPane());
        aprobarcredito.getContentPane().setLayout(aprobarcreditoLayout);
        aprobarcreditoLayout.setHorizontalGroup(
            aprobarcreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        aprobarcreditoLayout.setVerticalGroup(
            aprobarcreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        codprod.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.codprod.text")); // NOI18N
        codprod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codprodFocusGained(evt);
            }
        });
        codprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codprodActionPerformed(evt);
            }
        });
        codprod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codprodKeyPressed(evt);
            }
        });

        jLabel25.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.jLabel14.text")); // NOI18N

        BuscarProducto.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.BuscarProducto.text")); // NOI18N
        BuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarProductoActionPerformed(evt);
            }
        });

        nombreproducto.setEditable(false);
        nombreproducto.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.nombreproducto.text")); // NOI18N
        nombreproducto.setEnabled(false);

        jLabel26.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.jLabel15.text")); // NOI18N

        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.cantidad.text")); // NOI18N
        cantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cantidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cantidadFocusLost(evt);
            }
        });
        cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadKeyPressed(evt);
            }
        });

        jLabel27.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.jLabel16.text")); // NOI18N

        precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precio.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.preciounitario.text")); // NOI18N
        precio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                precioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                precioFocusLost(evt);
            }
        });
        precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioActionPerformed(evt);
            }
        });
        precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                precioKeyPressed(evt);
            }
        });

        jLabel28.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.jLabel17.text")); // NOI18N

        totalitem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalitem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalitem.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.totalitem.text")); // NOI18N
        totalitem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                totalitemFocusLost(evt);
            }
        });

        jLabel29.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.jLabel19.text")); // NOI18N

        cModo.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.cModo.text")); // NOI18N

        porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        porcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        porcentaje.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.preciounitario.text")); // NOI18N
        porcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                porcentajeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                porcentajeFocusLost(evt);
            }
        });
        porcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porcentajeActionPerformed(evt);
            }
        });
        porcentaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                porcentajeKeyPressed(evt);
            }
        });

        jLabel30.setText("IVA%");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel21Layout.createSequentialGroup()
                                            .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cantidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                        .addComponent(precio, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(totalitem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(209, 209, 209)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29))
                    .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(11, 11, 11)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addContainerGap())
        );

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItem.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.NuevoItem.text")); // NOI18N
        GrabarItem.setBorder(null);
        GrabarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarItem.setPreferredSize(new java.awt.Dimension(39, 20));
        GrabarItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                GrabarItemFocusGained(evt);
            }
        });
        GrabarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemActionPerformed(evt);
            }
        });

        SalirItem.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "detalle_facturas.SalirItem.text")); // NOI18N
        SalirItem.setBorder(null);
        SalirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(SalirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(GrabarItem, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout itemnotacreditovtaLayout = new javax.swing.GroupLayout(itemnotacreditovta.getContentPane());
        itemnotacreditovta.getContentPane().setLayout(itemnotacreditovtaLayout);
        itemnotacreditovtaLayout.setHorizontalGroup(
            itemnotacreditovtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemnotacreditovtaLayout.setVerticalGroup(
            itemnotacreditovtaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemnotacreditovtaLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jTBuscarVendedor.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
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

        jPanel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel31.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel31.setText("Total a Cobrar");

        totalventa.setEditable(false);
        totalventa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalventa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalventa.setDisabledTextColor(new java.awt.Color(255, 51, 51));
        totalventa.setEnabled(false);
        totalventa.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalventa, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalventa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel32.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel32.setText("Efectivo");

        efectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        efectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        efectivo.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        efectivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                efectivoFocusGained(evt);
            }
        });
        efectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                efectivoActionPerformed(evt);
            }
        });
        efectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                efectivoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel32)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder("Pago en Cheque"));

        jLabel33.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel33.setText("Cargo Banco");

        importecheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importecheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importecheque.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel34.setText("Nº Cheque");

        jLabel35.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel35.setText("Emisión");

        jLabel36.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel36.setText("Importe Cheque");

        cargobanco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        nombrebanco.setEnabled(false);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cargobanco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel33)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel34)))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel35)))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addComponent(importecheque, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(30, 30, 30))))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 2, Short.MAX_VALUE))
                            .addComponent(BuscarBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(importecheque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(cargobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder("Pago con Tarjetas"));

        jLabel37.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel37.setText("Banco Emisor");

        pagotarjeta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        pagotarjeta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pagotarjeta.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel38.setText("Nº Tarjeta");

        jLabel39.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel39.setText("Emisión");

        jLabel40.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel40.setText("Importe Tarjeta");

        nombreemisor.setEnabled(false);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel37))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(emisor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarEmisor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreemisor, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel38)))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel39)))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(11, 11, 11)))
                .addGap(17, 17, 17))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(47, 47, 47))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel39))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombreemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BuscarEmisor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(emisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))
                        .addContainerGap())))
        );

        jPanel31.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AceptarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(SalirFormaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCobro)
                    .addComponent(SalirFormaCobro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel41.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel41.setText("Su Cambio");

        sucambio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        sucambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucambio.setEnabled(false);
        sucambio.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        sucambio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sucambioFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sucambio, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sucambio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel41)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ingresar_cobrosLayout = new javax.swing.GroupLayout(ingresar_cobros.getContentPane());
        ingresar_cobros.getContentPane().setLayout(ingresar_cobrosLayout);
        ingresar_cobrosLayout.setHorizontalGroup(
            ingresar_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ingresar_cobrosLayout.setVerticalGroup(
            ingresar_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingresar_cobrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Impresión de Facturas por Lote");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText("Iniciar Impresión desde la Factura");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText("Culminar Impresión en la Factura");

        facturainicial.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        facturafinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel43)))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(facturainicial, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(facturafinal, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel43)
                .addGap(12, 12, 12)
                .addComponent(facturainicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(facturafinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        imprimirlotes.setText("Envíar a Impresora");
        imprimirlotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirlotesActionPerformed(evt);
            }
        });

        SalirLotes.setText("Salir");
        SalirLotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirLotesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(imprimirlotes, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(SalirLotes, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imprimirlotes)
                    .addComponent(SalirLotes))
                .addContainerGap())
        );

        javax.swing.GroupLayout lotesLayout = new javax.swing.GroupLayout(lotes.getContentPane());
        lotes.getContentPane().setLayout(lotesLayout);
        lotesLayout.setHorizontalGroup(
            lotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        lotesLayout.setVerticalGroup(
            lotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lotesLayout.createSequentialGroup()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCaja.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCaja.setTitle(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.BCaja.title")); // NOI18N

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane14.setViewportView(tablacaja);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCaja.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.AceptarCaja.text")); // NOI18N
        AceptarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCajaActionPerformed(evt);
            }
        });

        SalirCaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCaja.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "ventas.SalirCaja.text")); // NOI18N
        SalirCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCaja)
                    .addComponent(SalirCaja))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCajaLayout = new javax.swing.GroupLayout(BCaja.getContentPane());
        BCaja.getContentPane().setLayout(BCajaLayout);
        BCajaLayout.setHorizontalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCajaLayout.setVerticalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        notacredito.setBackground(new java.awt.Color(153, 255, 255));
        notacredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        notacredito.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        notacredito.setEnabled(false);

        timbrado.setBackground(new java.awt.Color(153, 255, 255));
        timbrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timbrado.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        timbrado.setEnabled(false);

        nrofactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nrofactura.setToolTipText("Formato Sugerido 001-001-0001234");
        nrofactura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nrofacturaFocusLost(evt);
            }
        });
        nrofactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nrofacturaActionPerformed(evt);
            }
        });
        nrofactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nrofacturaKeyReleased(evt);
            }
        });

        GrabarNota.setText("Grabar");
        GrabarNota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarNotaActionPerformed(evt);
            }
        });

        jLabel11.setText("Nota Crédito");

        jLabel46.setText("N° Timbrado");

        codcliente.setBackground(new java.awt.Color(153, 255, 255));
        codcliente.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        codcliente.setEnabled(false);

        nombreclientecredito.setBackground(new java.awt.Color(153, 255, 255));
        nombreclientecredito.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        nombreclientecredito.setEnabled(false);

        jLabel47.setText("Ingrese Factura");

        idnotacredito.setEnabled(false);

        idfactura.setEnabled(false);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel11))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(notacredito, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreclientecredito, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addComponent(timbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel47)
                        .addGap(48, 48, 48)))
                .addGap(44, 44, 44)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrabarNota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idfactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idnotacredito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(125, 125, 125))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47)
                    .addComponent(idfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(notacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GrabarNota))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreclientecredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idnotacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel40.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        nota.setModel(modelonota        );
        nota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                notaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                notaKeyReleased(evt);
            }
        });
        jScrollPane10.setViewportView(nota);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout itemreferenciasLayout = new javax.swing.GroupLayout(itemreferencias.getContentPane());
        itemreferencias.getContentPane().setLayout(itemreferenciasLayout);
        itemreferenciasLayout.setHorizontalGroup(
            itemreferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemreferenciasLayout.setVerticalGroup(
            itemreferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemreferenciasLayout.createSequentialGroup()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Notas de Crédito");
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

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetacredito.setBackground(new java.awt.Color(255, 255, 255));
        etiquetacredito.setText("Nota Credito Ventas");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Factura", "Sucursal", "Nombre Cliente" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        buscarcadena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buscarcadena.setSelectionColor(new java.awt.Color(0, 63, 62));
        buscarcadena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscarcadenaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(343, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        Modificar.setBackground(new java.awt.Color(255, 255, 255));
        Modificar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Modificar.setText("Editar Registro");
        Modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
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

        Eliminar.setBackground(new java.awt.Color(255, 255, 255));
        Eliminar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Eliminar.setText("Anular");
        Eliminar.setToolTipText("");
        Eliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        Listar.setBackground(new java.awt.Color(255, 255, 255));
        Listar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Listar.setText("Listar/Imprimir");
        Listar.setToolTipText("");
        Listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListarActionPerformed(evt);
            }
        });

        SalirCompleto.setBackground(new java.awt.Color(255, 255, 255));
        SalirCompleto.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        SalirCompleto.setText("     Salir");
        SalirCompleto.setToolTipText("");
        SalirCompleto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCompletoActionPerformed(evt);
            }
        });

        idControl.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(nota_credito_ventas_ferremax.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

        refrescar.setText("Refrescar");
        refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(refrescar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(SalirCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Eliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(Agregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Modificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SalirCompleto)
                .addGap(26, 26, 26)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablanotacredito.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablanotacredito.setModel(modelo);
        tablanotacredito.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablanotacredito.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablanotacredito.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablanotacredito.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablanotacreditoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablanotacreditoFocusLost(evt);
            }
        });
        tablanotacredito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablanotacreditoMouseClicked(evt);
            }
        });
        tablanotacredito.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablanotacreditoPropertyChange(evt);
            }
        });
        tablanotacredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablanotacreditoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablanotacredito);

        jMenu1.setText("Opciones");

        jMenuItem1.setText("Referencias");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
    }

    public void detalle_referencia(String id) {

        //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
        int cantidadRegistro = modelonota.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelonota.removeRow(0);
        }
        nota_creditoDAO DAO = new nota_creditoDAO();
        int item = 0;
        try {
            for (nota_credito nt : DAO.MostrarDetalle(id)) {
                item++;
                String Datos[] = {formatosinpunto.format(nt.getIditem()), String.valueOf(item), nt.getNrofactura()};
                modelonota.addRow(Datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        this.nota.setRowSorter(new TableRowSorter(modelonota));
        int cantFilas = nota.getRowCount();

    }

    public void limpiarCombos() {

    }

    private void jComboBox1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText()).toUpperCase();
                buscarcadena.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por factura
                    case 1:
                        indiceColumnaTabla = 4;
                        break;//por sucursal
                    case 2:
                        indiceColumnaTabla = 5;
                        break;//por nombre del cliente
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablanotacredito.getModel());
        tablanotacredito.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void SalirCompletoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

    public void filtrocaja(int nNumeroColumna) {
        trsfiltrocaja.setRowFilter(RowFilter.regexFilter(this.jTBuscarCaja.getText(), nNumeroColumna));
    }

    private void TituloCaja() {
        modelocaja.addColumn("Código");
        modelocaja.addColumn("Nombre");

        int[] anchos = {90, 100};
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
    }

    public void sumatoria() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA
        double nPorcentajeIva = 0.00;
        double sumexentas = 0.00;
        double sumgravadas10 = 0.00;
        double sumgravadas5 = 0.00;
        double sumtotal = 0.00;
        double sumatoria = 0.00;
        String cValorImporte = "";
        String cPorcentaje = "";
        int totalRow = this.tabladetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //Primero capturamos el porcentaje del IVA
            cPorcentaje = String.valueOf(this.tabladetalle.getValueAt(i, 3));
            cPorcentaje = cPorcentaje.replace(".", "").replace(",", ".");
            nPorcentajeIva = Double.valueOf(cPorcentaje);
            //Luego capturamos el importe de la celda total del item
            cValorImporte = String.valueOf(this.tabladetalle.getValueAt(i, 5));
            cValorImporte = cValorImporte.replace(".", "").replace(",", ".");
            sumatoria = Double.valueOf(cValorImporte);
            sumtotal += sumatoria;
            //Calculamos el total de exentos
            if (nPorcentajeIva == 0) {
                sumatoria = Double.valueOf(cValorImporte);
                sumexentas += sumatoria;
            }
            //Calculamos el total del 5%
            if (nPorcentajeIva == 5) {
                sumatoria = Double.valueOf(cValorImporte);
                sumgravadas5 += sumatoria;
            }
            if (nPorcentajeIva == 10) {
                sumatoria = Double.valueOf(cValorImporte);
                sumgravadas10 += sumatoria;
            }
        }
        tabladetalle.setRowSorter(new TableRowSorter(modelodetalle));
        int cantFilas = tabladetalle.getRowCount();
        if (cantFilas > 0) {
            delitem.setEnabled(true);
            editaritem.setEnabled(true);
        } else {
            delitem.setEnabled(false);
            editaritem.setEnabled(false);
        }

        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.gravadas5.setText(formatea.format(sumgravadas5));
        this.exentas.setText(formatea.format(sumexentas));
        this.gravadas10.setText(formatea.format(sumgravadas10));
        this.totalneto.setText(formatea.format(sumtotal));
        //formato.format(sumatoria1);
    }


    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        idControl.setText("0");
        modo.setText("1");
        this.limpiar();
        this.limpiaritems();
        detalle_notacredito.setModal(true);
        detalle_notacredito.setSize(974, 709);
        //Establecemos un título para el jDialog

        int cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }
        detalle_notacredito.setTitle("Registrar Nota de Crédito");
        detalle_notacredito.setLocationRelativeTo(null);
        detalle_notacredito.setVisible(true);
        sucursal.requestFocus();
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion configinicial = configDAO.consultar();
        this.sucursal.setText("0");
        this.nombresucursal.setText("");
        this.factura.setText(formatosinpunto.format(configinicial.getSucursaldefecto().getNotacredito()));

        this.fecha.setCalendar(c2);
        this.primervence.setCalendar(c2);
        this.vencetimbrado.setCalendar(c2);
        this.nrotimbrado.setText("0");
        this.cliente.setText("0");
        this.nombrecliente.setText("");
        this.direccioncliente.setText("");
        this.comprobante.setText("0");
        this.nombrecomprobante.setText("");
        this.moneda.setText("0");
        this.nombremoneda.setText("");
        this.cotizacion.setText("");
        this.vendedor.setText("0");
        this.nombrevendedor.setText("");
        this.cuotas.setText("0");
        this.caja.setText("0");
        this.nombrecaja.setText("");
        this.observacion.setText("");
        this.exentas.setText("0");
        this.gravadas5.setText("0");
        this.gravadas10.setText("0");
        this.totalneto.setText("0");
        this.imagen.setText("Imagen");
        this.editaritem.setEnabled(false);
        this.delitem.setEnabled(false);

        this.totalventa.setText("0");
        this.efectivo.setText("0");
        this.cargobanco.setText("0");
        this.nombrebanco.setText("");
        this.importecheque.setText("0");
        this.emisioncheque.setCalendar(c2);
        this.nrocheque.setText("0");

        this.emisiontarjeta.setCalendar(c2);
        this.emisor.setText("0");
        this.nombreemisor.setText("");
        this.nrotarjeta.setText("");
        this.pagotarjeta.setText("0");
        this.sucambio.setText("0");
    }
    private void tablanotacreditoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablanotacreditoKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotacreditoKeyPressed

    private void tablanotacreditoMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablanotacreditoMouseClicked
        int nFila = this.tablanotacredito.getSelectedRow();
        this.idControl.setText(this.tablanotacredito.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotacreditoMouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        modo.setText("2");
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            nFila = this.tablanotacredito.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
            cuenta_clientes saldo = null;
            try {
                saldo = saldoDAO.SaldoMovimiento(this.idControl.getText());
                if (saldo.getDocumento() != null) {
                    JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                    return;
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            ventaDAO veDAO = new ventaDAO();
            venta ve = null;
            try {
                ve = veDAO.buscarId(this.idControl.getText());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (ve != null) {
                //SE CARGAN LOS DATOS DE LA CABECERA
                creferencia.setText(ve.getCreferencia());
                sucursal.setText(String.valueOf(ve.getSucursal().getCodigo()));
                nombresucursal.setText(ve.getSucursal().getNombre());
                factura.setText(ve.getFormatofactura());

                // credito.setText(String.valueOf(ve.getFactura()));
                fecha.setDate(ve.getFecha());
                cliente.setText(String.valueOf(ve.getCliente().getCodigo()));
                nombrecliente.setText(ve.getCliente().getNombre());
                direccioncliente.setText(ve.getCliente().getDireccion());
                vendedor.setText(String.valueOf(ve.getVendedor().getCodigo()));
                nombrevendedor.setText(String.valueOf(ve.getVendedor().getNombre()));
                comprobante.setText(String.valueOf(ve.getComprobante().getCodigo()));
                nombrecomprobante.setText(String.valueOf(ve.getComprobante().getNombre()));
                moneda.setText(formatosinpunto.format(ve.getMoneda().getCodigo()));
                nombremoneda.setText(ve.getMoneda().getNombre());
                cotizacion.setText(formatea.format(ve.getCotizacion()));
                caja.setText(formatosinpunto.format(ve.getCaja().getCodigo()));
                nombrecaja.setText(ve.getCaja().getNombre());
                nrotimbrado.setText(formatosinpunto.format(ve.getNrotimbrado()));
                vencetimbrado.setDate(ve.getVencimientotimbrado());
                exentas.setText(formatea.format(ve.getExentas()));
                gravadas5.setText(formatea.format(ve.getGravadas5()));
                gravadas10.setText(formatea.format(ve.getGravadas10()));
                totalneto.setText(formatea.format(ve.getTotalneto()));
                primervence.setDate(ve.getVencimiento());
                cuotas.setText(formatosinpunto.format(ve.getCuotas()));
                observacion.setText(ve.getObservacion());

                // SE CARGAN LOS DETALLES
                int cantidadRegistro = modelodetalle.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelodetalle.removeRow(0);
                }

                detalle_ventaDAO detDAO = new detalle_ventaDAO();
                try {
                    for (detalle_venta detvta : detDAO.MostrarDetalle(creferencia.getText())) {
                        String Detalle[] = {detvta.getCodprod().getCodigo(), detvta.getCodprod().getNombre(), formatcantidad.format(detvta.getCantidad()), formatea.format(detvta.getPorcentaje()), formatea.format(detvta.getPrecio()), formatea.format(detvta.getMonto())};
                        modelodetalle.addRow(Detalle);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                int cantFilas = tabladetalle.getRowCount();
                if (cantFilas > 0) {
                    editaritem.setEnabled(true);
                    delitem.setEnabled(true);
                } else {
                    editaritem.setEnabled(false);
                    delitem.setEnabled(false);
                }

                detalle_notacredito.setModal(true);
                detalle_notacredito.setSize(974, 709);
                //Establecemos un título para el jDialog
                detalle_notacredito.setTitle("Registrar Nota de Crédito");
                detalle_notacredito.setLocationRelativeTo(null);
                detalle_notacredito.setVisible(true);
                sucursal.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void tablanotacreditoFocusGained(FocusEvent evt) {//GEN-FIRST:event_tablanotacreditoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotacreditoFocusGained

    private void jScrollPane1FocusGained(FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowActivated(WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formFocusGained(FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void EliminarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = tablanotacredito.getSelectedRow();
            String num = tablanotacredito.getValueAt(nFila, 0).toString();
            cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
            cuenta_clientes saldo = null;
            try {
                saldo = saldoDAO.SaldoMovimiento(num);
                if (saldo.getDocumento() != null) {
                    JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                    return;
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                ventaDAO vl = new ventaDAO();
                detalle_ventaDAO det = new detalle_ventaDAO();
                cuenta_clienteDAO cl = new cuenta_clienteDAO();
                try {
                    venta vt = vl.buscarId(num);
                    if (vt == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        vl.borrarDetalleCuenta(num);
                        det.borrarDetalleVenta(num);
                        vl.borrarVenta(Config.cToken,num);
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
            GrillaVenta GrillaOC = new GrillaVenta();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }

    }//GEN-LAST:event_EliminarActionPerformed

    private void tablanotacreditoFocusLost(FocusEvent evt) {//GEN-FIRST:event_tablanotacreditoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotacreditoFocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreRecibo = config.getNombrerecibo();

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(config.getSucursaldefecto().getCodigo());
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
                    int nFila = tablanotacredito.getSelectedRow();
                    String num = tablanotacredito.getValueAt(nFila, 10).toString();
                    num = num.replace(".", "").replace(",", ".");
                    num = num.replace("-", "");
                    numero_a_letras numero = new numero_a_letras();

                    parameters.put("Letra", numero.Convertir(num, true, Integer.valueOf(tablanotacredito.getValueAt(nFila, 11).toString())));
                    parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                    parameters.put("cRuc", config.getRuc());
                    parameters.put("cTelefono", config.getTelefono());
                    parameters.put("cDireccion", config.getDireccion());
                    parameters.put("cReferencia", tablanotacredito.getValueAt(nFila, 0).toString());
                    JasperReport jr = null;

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    URL url = getClass().getClassLoader().getResource("Reports/notacreditoferremax.jasper");

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
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_notacredito.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito

        int nCaracter = factura.getText().indexOf("-");
        String cNumeroFactura = factura.getText();
        if (nCaracter >= 0) {
            cNumeroFactura = cNumeroFactura.replace("-", "");
            boolean isNumeric = cNumeroFactura.matches("[+-]?\\d*(\\.\\d+)?");
            if (isNumeric == false) {
                JOptionPane.showMessageDialog(null, "Formato de Número de Factura no Corresponde");
                this.factura.getText();
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de Número de Factura no Corresponde");
            this.factura.getText();
            return;
        }
        this.ingresar_cobros.setVisible(false);

        if (this.vendedor.getText().isEmpty() || this.vendedor.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione al Vendedor");
            this.vendedor.requestFocus();
            return;
        }

        if (this.comprobante.getText().isEmpty() || this.comprobante.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Tipo de Comprobante");
            this.comprobante.requestFocus();
            return;
        }

        if (this.moneda.getText().isEmpty() || this.moneda.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Moneda e Ingrese la Cotización");
            this.moneda.requestFocus();
            return;
        }

        if (this.cotizacion.getText().isEmpty() || this.cotizacion.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cotización de Moneda");
            this.cotizacion.requestFocus();
            return;
        }

        if (this.factura.getText().isEmpty() || this.factura.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Número de Factura");
            this.factura.requestFocus();
            return;
        }

        if (this.cuotas.getText().isEmpty()) {
            this.cuotas.setText("0");
        }
        if (this.nrotimbrado.getText().isEmpty() || this.factura.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Número de Timbrado");
            this.nrotimbrado.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            if (Integer.valueOf(this.modo.getText()) == 1) {
                UUID id = new UUID();
                referencia = UUID.crearUUID();
                referencia = referencia.substring(1, 25);
            } else {
                referencia = this.creferencia.getText();
            }

            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            Date FechaVence = ODate.de_java_a_sql(primervence.getDate());
            Date FechaVenceTimbrado = ODate.de_java_a_sql(vencetimbrado.getDate());

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cli = null;
            comprobanteDAO coDAO = new comprobanteDAO();
            comprobante com = null;
            monedaDAO mnDAO = new monedaDAO();
            moneda mn = null;
            giraduriaDAO giDAO = new giraduriaDAO();
            giraduria gi = null;
            vendedorDAO veDAO = new vendedorDAO();
            vendedor ve = null;
            cajaDAO caDAO = new cajaDAO();
            caja ca = null;

            try {
                suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                cli = cliDAO.buscarId(Integer.valueOf(this.cliente.getText()));
                com = coDAO.buscarId(Integer.valueOf(this.comprobante.getText()));
                mn = mnDAO.buscarId(Integer.valueOf(this.moneda.getText()));
                gi = giDAO.buscarId(1); //verificar y tener en cuenta para ingreso por teclado o asociar a Cliente
                ve = veDAO.buscarId(Integer.valueOf(this.vendedor.getText()));
                ca = caDAO.buscarId(Integer.valueOf(this.caja.getText()));

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            venta v = new venta();
            ventaDAO grabarventa = new ventaDAO();
            //Capturamos los Valores BigDecimal
            String cExentas = this.exentas.getText();
            cExentas = cExentas.replace(".", "").replace(",", ".");
            BigDecimal nExentas = new BigDecimal(cExentas);

            String cGravadas10 = this.gravadas10.getText();
            cGravadas10 = cGravadas10.replace(".", "").replace(",", ".");
            BigDecimal nGravadas10 = new BigDecimal(cGravadas10);

            String cGravadas5 = this.gravadas5.getText();
            cGravadas5 = cGravadas5.replace(".", "").replace(",", ".");
            BigDecimal nGravadas5 = new BigDecimal(cGravadas5);

            cTotalNeto = this.totalneto.getText();
            cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
            BigDecimal nTotalNeto = new BigDecimal(cTotalNeto);

            String cCotizacion = this.cotizacion.getText();
            cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
            BigDecimal nCotizacion = new BigDecimal(cCotizacion);

            cNumeroFactura = factura.getText();
            cNumeroFactura = cNumeroFactura.replace("-", "");
            String cContadorFactura = cNumeroFactura.substring(6, 13);

            v.setCreferencia(referencia);
            v.setFecha(FechaProceso);
            v.setFactura(Double.valueOf(cNumeroFactura));
            v.setFormatofactura(this.factura.getText());
            v.setVencimiento(FechaVence);
            v.setCliente(cli);
            v.setMoneda(mn);
            v.setGiraduria(gi);
            v.setComprobante(com);
            v.setSucursal(suc);
            v.setCotizacion(nCotizacion);
            v.setVendedor(ve);
            v.setCaja(ca);
            v.setExentas(nExentas);
            v.setGravadas10(nGravadas10);
            v.setGravadas5(nGravadas5);
            v.setTotalneto(nTotalNeto);
            v.setCuotas(Integer.valueOf(this.cuotas.getText()));
            v.setVencimientotimbrado(FechaVenceTimbrado);
            v.setNrotimbrado(Integer.valueOf(nrotimbrado.getText()));
            v.setFinanciado(nTotalNeto);
            v.setObservacion(this.observacion.getText());
            v.setSupago(nTotalNeto);
            v.setIdusuario(Integer.valueOf(Config.CodUsuario));
            v.setTurno(1);

            productoDAO producto = new productoDAO();
            producto p = null;
            int comprobante = 0;
            String cProducto = null;
            String cCosto = null;
            String cCantidad = null;
            String cPrecio = null;
            String cMonto = null;
            String civa = null;
            String cIvaItem = null;

            BigDecimal totalitem = null;
            int totalRow = modelodetalle.getRowCount();
            totalRow -= 1;

            String detalle = "[";
            for (int i = 0; i <= (totalRow); i++) {
                //Capturo y valido Producto
                cProducto = modelodetalle.getValueAt(i, 0).toString();
                try {
                    p = producto.BuscarProductoBasico(cProducto);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                //Capturo cantidad    
                cCantidad = String.valueOf(modelodetalle.getValueAt(i, 2));
                cCantidad = cCantidad.replace(".", "").replace(",", ".");
                //Porcentaje
                civa = String.valueOf(modelodetalle.getValueAt(i, 3));
                civa = civa.replace(".", "").replace(",", ".");
                //Precio
                cPrecio = String.valueOf(modelodetalle.getValueAt(i, 4));
                cPrecio = cPrecio.replace(".", "").replace(",", ".");
                //Total Item
                cMonto = String.valueOf(modelodetalle.getValueAt(i, 5));
                cMonto = cMonto.replace(".", "").replace(",", ".");
                cCosto = String.valueOf(p.getCosto());
                switch (Integer.valueOf(civa)) {
                    case 10:
                        if (moneda.getText().equals("1")) {
                            cIvaItem = String.valueOf(Math.round(Double.valueOf(cMonto) / 11));
                        } else {
                            cIvaItem = String.valueOf(Double.valueOf(cMonto) / 11);
                        }
                        break;
                    case 5:
                        if (moneda.getText().equals("1")) {
                            cIvaItem = String.valueOf(Math.round(Double.valueOf(cMonto) / 21));
                        } else {
                            cIvaItem = String.valueOf(Double.valueOf(cMonto) / 21);
                        }
                        break;
                    case 0:
                        cIvaItem = "0";
                        break;
                }

                String linea = "{dreferencia : " + referencia + ","
                        + "codprod : '" + cProducto + "',"
                        + "prcosto : " + cCosto + ","
                        + "cantidad : " + cCantidad + ","
                        + "precio : " + cPrecio + ","
                        + "monto : " + cMonto + ","
                        + "impiva: " + cIvaItem + ","
                        + "porcentaje : " + civa + ","
                        + "suc : " + Integer.valueOf(sucursal.getText())
                        + "},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            System.out.println(detalle);

            String detacuota = "[";
            if (Integer.valueOf(this.cuotas.getText()) > 0) {
                Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
                calendar.setTime(this.primervence.getDate()); // Capturamos en el setTime el valor de la fecha ingresada

                String iddoc = null;
                String cImporteCuota = null;
                if (Integer.valueOf(moneda.getText()) == 1) {
                    cImporteCuota = String.valueOf(Math.round(Double.valueOf(cTotalNeto) / Double.valueOf(cuotas.getText())));
                } else {
                    cImporteCuota = String.valueOf(Double.valueOf(cTotalNeto) / Double.valueOf(cuotas.getText()));
                }

                detacuota = "[";
                for (int i = 1; i <= Integer.valueOf(this.cuotas.getText()); i++) {
                    this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                    Date VenceCuota = ODate.de_java_a_sql(vencimientos.getDate());
                    iddoc = UUID.crearUUID();
                    iddoc = iddoc.substring(1, 25);
                    String lineacuota = "{iddocumento : " + iddoc + ","
                            + "creferencia : " + referencia + ","
                            + "documento : " + cNumeroFactura + ","
                            + "fecha : " + FechaProceso + ","
                            + "vencimiento : " + VenceCuota + ","
                            + "cliente : " + cliente.getText() + ","
                            + "sucursal: " + sucursal.getText() + ","
                            + "moneda : " + moneda.getText() + ","
                            + "comprobante : " + this.comprobante.getText() + ","
                            + "vendedor : " + vendedor.getText() + ","
                            + "importe : " + cImporteCuota + ","
                            + "numerocuota : " + this.cuotas.getText() + ","
                            + "cuota : " + i + ","
                            + "saldo : " + cImporteCuota
                            + "},";
                    detacuota += lineacuota;
                    calendar.setTime(this.vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                    this.venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior
                    int mes = this.venceanterior.getCalendar().get(Calendar.MONTH) + 1;
                    int dia = this.venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
                    calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0
                    if (mes == 2 && dia == 28) {
                        calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días                            //el vencimiento
                    }
                    this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                }
                if (!detacuota.equals("[")) {
                    detacuota = detacuota.substring(0, detacuota.length() - 1);
                }
                detacuota += "]";

            }
            if (Integer.valueOf(modo.getText()) == 1) {
                try {
                    grabarventa.AgregarNotaCredito(v, detalle);
                    if (Integer.valueOf(cuotas.getText()) > 0) {
                        cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                        ctaDAO.guardarCuenta(detacuota);
                    }
                    grabarventa.ActualizarNumeroNotaCredito(Integer.valueOf(sucursal.getText()), Double.valueOf(cContadorFactura) + 1);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                try {
                    detalle_ventaDAO delDAO = new detalle_ventaDAO();
                    // delDAO.borrarDetalleVenta(referencia);
                    grabarventa.borrarDetalleCuenta(referencia);
                    grabarventa.ActualizarNotaCredito(v, detalle);
                    if (Integer.valueOf(cuotas.getText()) > 0) {
                        cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                        ctaDAO.guardarCuenta(detacuota);
                    }
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            detalle_notacredito.setModal(false);
            detalle_notacredito.setVisible(false);
            GrillaVenta GrillaVE = new GrillaVenta();
            Thread HiloGrilla = new Thread(GrillaVE);
            HiloGrilla.start();
        }
    }//GEN-LAST:event_GrabarActionPerformed

    private void detalle_notacreditoFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_notacreditoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_notacreditoFocusGained

    private void detalle_notacreditoWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_notacreditoWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_notacreditoWindowGainedFocus

    private void detalle_notacreditoWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_notacreditoWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_notacreditoWindowActivated

    private void tablanotacreditoPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_tablanotacreditoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotacreditoPropertyChange

    private void refrescarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaVenta GrillaOC = new GrillaVenta();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();        // TODO add your handling code here:
    }//GEN-LAST:event_refrescarActionPerformed

    private void sucursalKeyPressed(KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fecha.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void fechaFocusGained(FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void fechaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.comprobante.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.factura.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void clienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_clienteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nuevoitem.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.primervence.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_clienteKeyPressed

    private void comprobanteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_comprobanteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }
    }//GEN-LAST:event_comprobanteKeyPressed

    private void primervenceKeyPressed(KeyEvent evt) {//GEN-FIRST:event_primervenceKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.caja.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cuotas.requestFocus();
        }   // TO        // TODO add your handling code here:*/
    }//GEN-LAST:event_primervenceKeyPressed

    private void buscarSucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (sucu.getCodigo() == 0) {
                GrillaSucursal grillasu = new GrillaSucursal();
                Thread hilosuc = new Thread(grillasu);
                hilosuc.start();
                BSucursal.setModal(true);
                BSucursal.setSize(500, 575);
                BSucursal.setLocationRelativeTo(null);
                BSucursal.setTitle("Buscar Sucursal");
                BSucursal.setVisible(true);
                BSucursal.setModal(false);
            } else {
                if (Integer.valueOf(modo.getText()) == 1) {
                    String cBoca = sucu.getExpedicion_nota().trim();
                    Double nFactura = sucu.getNotacredito();
                    int n = (int) nFactura.doubleValue();
                    String formatString = String.format("%%0%dd", 7);
                    String formattedString = String.format(formatString, n);
                    this.factura.setText(cBoca + "-" + formattedString);
                    this.observacion.setText("Nota de Crédito N° " + this.factura.getText());
                }
                factura.requestFocus();
            }
            factura.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void sucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void comboclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

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
        this.direccioncliente.setText(this.tablacliente.getValueAt(nFila, 2).toString());
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
//        this.giraduria.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        this.buscarCliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

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

    private void tablacomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacomprobanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarComprobante.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteKeyPressed

    private void AceptarComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarComprobanteActionPerformed
        int nFila = this.tablacomprobante.getSelectedRow();
        this.comprobante.setText(this.tablacomprobante.getValueAt(nFila, 0).toString());
        this.nombrecomprobante.setText(this.tablacomprobante.getValueAt(nFila, 1).toString());

        this.BComprobante.setVisible(false);
        this.jTBuscarComprobante.setText("");
        this.vencetimbrado.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarComprobanteActionPerformed

    private void SalirComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirComprobanteActionPerformed
        this.BComprobante.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirComprobanteActionPerformed

    private void jTBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.AceptarCli.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void tablacomprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacomprobanteMouseClicked
        this.AceptarComprobante.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteMouseClicked

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

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        String cBoca = tablasucursal.getValueAt(nFila, 3).toString();
        Double nFactura = Double.valueOf(tablasucursal.getValueAt(nFila, 2).toString());
        int n = (int) nFactura.doubleValue();
        String formatString = String.format("%%0%dd", 7);
        String formattedString = String.format(formatString, n);
        this.factura.setText(cBoca + "-" + formattedString);
        this.observacion.setText("Nota de Crédito N° " + this.factura.getText());

        this.BSucursal.setVisible(false);
        this.fecha.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void tablasucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalKeyPressed

    private void tablasucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasucursalMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalMouseClicked

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
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por RUC
                }
                repaint();
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltrosuc = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltrosuc);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalKeyPressed

    private void jTBuscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalActionPerformed

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

    private void comprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprobanteActionPerformed
        this.buscarcomprobante.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteActionPerformed

    private void buscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarMonedaActionPerformed
        monedaDAO casDAO = new monedaDAO();
        moneda mn = null;
        try {
            mn = casDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mn.getCodigo() == 0) {
                GrillaCasa grillaca = new GrillaCasa();
                Thread hiloca = new Thread(grillaca);
                hiloca.start();
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                BMoneda.setTitle("Buscar Comercio");
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
    }//GEN-LAST:event_buscarMonedaActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void clienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clienteFocusGained
        cliente.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_clienteFocusGained

    private void comprobanteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comprobanteFocusGained
        comprobante.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteFocusGained

    private void grabarocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarocActionPerformed
        int nFila = this.tablanotacredito.getSelectedRow();
//        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            String cAprobar = "APROBADO";
            int ret = JOptionPane.showOptionDialog(null, "Desea Aprobar el Préstamo Seleccionado? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Aprobar un Préstamo");
            return;
        }
        this.aprobarcredito.setVisible(false);
    }//GEN-LAST:event_grabarocActionPerformed

    private void saliropcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saliropcionActionPerformed
        aprobarcredito.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_saliropcionActionPerformed

    private void buscarcomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcomprobanteActionPerformed
        comprobanteDAO cmDAO = new comprobanteDAO();
        comprobante cm = null;
        try {
            cm = cmDAO.buscarIdxtipo(Integer.valueOf(this.comprobante.getText()), 2);
            if (cm.getCodigo() == 0) {
                GrillaComprobante grillacm = new GrillaComprobante();
                Thread hiloca = new Thread(grillacm);
                hiloca.start();
                BComprobante.setModal(true);
                BComprobante.setSize(500, 575);
                BComprobante.setLocationRelativeTo(null);
                BComprobante.setVisible(true);
                BComprobante.setModal(false);
            } else {
                nombrecomprobante.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            vencetimbrado.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarcomprobanteActionPerformed

    private void BuscarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarVendedorActionPerformed
        vendedorDAO veDAO = new vendedorDAO();
        vendedor vn = null;
        try {
            vn = veDAO.buscarId(Integer.valueOf(this.vendedor.getText()));
            if (vn.getCodigo() == 0) {
                GrillaVendedor grillavn = new GrillaVendedor();
                Thread hilove = new Thread(grillavn);
                hilove.start();
                BVendedor.setModal(true);
                BVendedor.setSize(500, 575);
                BVendedor.setLocationRelativeTo(null);
                BVendedor.setVisible(true);
                BVendedor.setTitle("Buscar Vendedor");
                BVendedor.setModal(false);
            } else {
                nombrevendedor.setText(vn.getNombre());
                //Establecemos un título para el jDialog
            }
            cuotas.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarVendedorActionPerformed

    private void buscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarClienteActionPerformed
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
//                giraduria.requestFocus();
                BCliente.setModal(false);
            } else {
                nombrecliente.setText(cl.getNombre());
                direccioncliente.setText(cl.getDireccion());
                //Establecemos un título para el jDialog
            }
            nuevoitem.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarClienteActionPerformed

    private void codprodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codprodFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodFocusGained

    private void codprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codprodActionPerformed
        this.BuscarProducto.doClick();
    }//GEN-LAST:event_codprodActionPerformed

    private void codprodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codprodKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodKeyPressed

    private void BuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarProductoActionPerformed
        productoDAO proDAO = new productoDAO();
        producto pro = null;
        try {
            pro = proDAO.BuscarProductoBasico(this.codprod.getText());
            if (pro.getCodigo() == null) {
                GrillaProductos grillapr = new GrillaProductos();
                Thread hilopr = new Thread(grillapr);
                hilopr.start();
                BProducto.setModal(true);
                BProducto.setSize(500, 575);
                BProducto.setLocationRelativeTo(null);
                BProducto.setTitle("Buscar Producto");
                BProducto.setVisible(true);
            } else {
                nombreproducto.setText(pro.getNombre().trim());
                precio.setText(formatea.format(pro.getPrecio_minimo()));

                if (pro.getIvaporcentaje() == null) {
                    porcentaje.setText("0");
                } else {
                    porcentaje.setText(formatea.format(pro.getIvaporcentaje()));
                }
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        this.cantidad.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarProductoActionPerformed

    private void cantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadFocusGained
        cantidad.selectAll();
    }//GEN-LAST:event_cantidadFocusGained

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.precio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codprod.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioActionPerformed

    private void precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.porcentaje.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_precioKeyPressed

    private void GrabarItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_GrabarItemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemFocusGained

    private void GrabarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemActionPerformed
        String cPorcentaje = this.porcentaje.getText();
        cPorcentaje = cPorcentaje.replace(".", "").replace(",", ".");;
        if (Integer.valueOf(cPorcentaje) != 0 && Integer.valueOf(cPorcentaje) != 5 && Integer.valueOf(cPorcentaje) != 10) {
            JOptionPane.showMessageDialog(null, "No corresponde a las Tasas de IVA que rigen en la actualidad");
            this.porcentaje.requestFocus();
            return;
        }

        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[7];
            fila[0] = this.codprod.getText().toString();
            fila[1] = this.nombreproducto.getText().toString();
            fila[2] = this.cantidad.getText();
            fila[3] = this.porcentaje.getText();
            fila[4] = this.precio.getText();
            fila[5] = this.totalitem.getText();
            modelodetalle.addRow(fila);
            this.codprod.requestFocus();
        } else {
            this.tabladetalle.setValueAt(this.codprod.getText(), nFila, 0);
            this.tabladetalle.setValueAt(this.nombreproducto.getText(), nFila, 1);
            this.tabladetalle.setValueAt(this.cantidad.getText(), nFila, 2);
            this.tabladetalle.setValueAt(this.porcentaje.getText(), nFila, 3);
            this.tabladetalle.setValueAt(this.precio.getText(), nFila, 4);
            this.tabladetalle.setValueAt(this.totalitem.getText(), nFila, 5);
            nFila = 0;
            this.SalirItem.doClick();
        }
        this.limpiaritems();
        this.sumatoria();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemActionPerformed

    private void SalirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        itemnotacreditovta.setModal(false);
        itemnotacreditovta.setVisible(false);
        this.detalle_notacredito.setModal(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void porcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porcentajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeActionPerformed

    private void porcentajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcentajeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.GrabarItem.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.precio.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeKeyPressed

    private void nuevoitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoitemActionPerformed
        itemnotacreditovta.setSize(442, 290);
        itemnotacreditovta.setLocationRelativeTo(null);
        this.limpiaritems();
        this.GrabarItem.setText("Agregar");
        this.cModo.setText("");
        itemnotacreditovta.setModal(true);
        itemnotacreditovta.setVisible(true);
        codprod.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoitemActionPerformed

    private void porcentajeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentajeFocusGained
        porcentaje.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeFocusGained

    private void precioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precioFocusGained
        precio.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_precioFocusGained

    private void editaritemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editaritemActionPerformed
        nFila = this.tabladetalle.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        itemnotacreditovta.setSize(442, 290);
        itemnotacreditovta.setLocationRelativeTo(null);
        cModo.setText(String.valueOf(nFila));
        codprod.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 0).toString());
        nombreproducto.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 1).toString());
        cantidad.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 2).toString());
        porcentaje.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 3).toString());
        precio.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 4).toString());
        totalitem.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 5).toString());
        itemnotacreditovta.setModal(true);
        itemnotacreditovta.setVisible(true);
        codprod.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_editaritemActionPerformed

    private void delitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delitemActionPerformed
        int a = this.tabladetalle.getSelectedRow();
        if (a < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelodetalle.removeRow(a);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                this.sumatoria();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_delitemActionPerformed

    private void tabladetalleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalleMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalleMouseEntered

    private void precioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precioFocusLost
        String cCosto = this.precio.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        this.precio.setText(formatea.format(Double.valueOf(cCosto)));
    }//GEN-LAST:event_precioFocusLost

    private void comboproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproductoActionPerformed

    private void jTBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProductoActionPerformed

    private void jTBuscarProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarProductoKeyPressed
        this.jTBuscarProducto.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarProducto.getText()).toUpperCase();
                jTBuscarProducto.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboproducto.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtroproducto(indiceColumnaTabla);
            }
        });
        trsfiltroproducto = new TableRowSorter(tablaproducto.getModel());
        tablaproducto.setRowSorter(trsfiltroproducto);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProductoKeyPressed

    private void tablaproductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproductoMouseClicked
        this.AceptarProducto.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoMouseClicked

    private void tablaproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarProducto.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoKeyPressed

    private void AceptarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProductoActionPerformed
        int nFila = this.tablaproducto.getSelectedRow();
        this.codprod.setText(this.tablaproducto.getValueAt(nFila, 0).toString());
        this.nombreproducto.setText(this.tablaproducto.getValueAt(nFila, 1).toString());
        this.precio.setText(this.tablaproducto.getValueAt(nFila, 2).toString());
        this.porcentaje.setText(this.tablaproducto.getValueAt(nFila, 3).toString());

        this.BProducto.setVisible(false);
        this.jTBuscarProducto.setText("");
        this.cantidad.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProductoActionPerformed

    private void SalirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProductoActionPerformed
        this.BProducto.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProductoActionPerformed

    private void porcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentajeFocusLost
        String cCantidad = this.cantidad.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");
        String cPrecio = this.precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");
        double nCantidad = Double.valueOf(cCantidad);//CANTIDAD
        double nPrecio = Double.valueOf(cPrecio);//PRECIO
        double ntotal = nCantidad * nPrecio;
        this.totalitem.setText(formatea.format(Double.valueOf(ntotal)));
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeFocusLost

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cotizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comprobante.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void cotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.vendedor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_cotizacionKeyPressed

    private void vendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vendedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cuotas.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cotizacion.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorKeyPressed

    private void cuotasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cuotasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!cuotas.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                cliente.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Debe ingresar su dato, no puede quedar vacio", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
    }//GEN-LAST:event_cuotasKeyPressed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        this.buscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

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
        this.vendedor.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
        this.nombrevendedor.setText(this.tablavendedor.getValueAt(nFila, 1).toString());

        this.BVendedor.setVisible(false);
        this.jTBuscarVendedor.setText("");
        this.cuotas.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendedorActionPerformed
        this.BuscarVendedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorActionPerformed

    private void efectivoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_efectivoFocusGained
        this.efectivo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_efectivoFocusGained

    private void AceptarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobroActionPerformed
        cTotalNeto = this.totalneto.getText();
        cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
        if (Double.valueOf(cTotalNeto) > 0) {
            Object[] opciones = {"  Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Confirmar la Venta ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                this.Grabar.doClick();
            }
        }
    }//GEN-LAST:event_AceptarCobroActionPerformed

    private void SalirFormaCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFormaCobroActionPerformed
        this.ingresar_cobros.setModal(false);
        this.ingresar_cobros.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFormaCobroActionPerformed

    private void sucambioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucambioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_sucambioFocusGained

    private void imprimirlotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirlotesActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Facturas? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            Lotes Lote = new Lotes();
            Thread HiloLote = new Thread(Lote);
            HiloLote.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirlotesActionPerformed

    private void SalirLotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirLotesActionPerformed
        lotes.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirLotesActionPerformed

    private void efectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_efectivoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.AceptarCobro.requestFocus();
        }
        if (efectivo.getText().length() > 0) {
            efectivo.setText(formatea.format(Double.valueOf(efectivo.getText().replace(".", "").replace(",", "."))));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_efectivoKeyPressed

    private void efectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_efectivoActionPerformed
        /*      if(Double.valueOf(efectivo.getText().replace(".","").replace(",","."))>Double.valueOf(totalventa.getText().replace(".","").replace(",","."))){
            Double nCambio=Double.valueOf(efectivo.getText().replace(".","").replace(",","."))- Double.valueOf(totalventa.getText().replace(".","").replace(",","."));
        }*/

        // TODO add your handling code here:
    }//GEN-LAST:event_efectivoActionPerformed

    private void buscarSucursalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarSucursalMousePressed

    }//GEN-LAST:event_buscarSucursalMousePressed

    private void cantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadFocusLost
        String cCantidad = this.cantidad.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");
        this.cantidad.setText(formatea.format(Double.valueOf(cCantidad) * -1));
    }//GEN-LAST:event_cantidadFocusLost

    private void totalitemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_totalitemFocusLost
        String cTotal = this.totalitem.getText();
        cTotal = cTotal.replace(".", "").replace(",", ".");
        this.totalitem.setText(formatea.format(Double.valueOf(cTotal) * -1));
    }//GEN-LAST:event_totalitemFocusLost

    private void facturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_facturaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fecha.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_facturaKeyPressed

    private void vencetimbradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencetimbradoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nrotimbrado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comprobante.requestFocus();
        }   // TODO add yo        // TODO add your handling code here:
    }//GEN-LAST:event_vencetimbradoKeyPressed

    private void nrotimbradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrotimbradoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.vencetimbrado.requestFocus();
        }   // TODO add yo        // TODO add your handling code here:
    }//GEN-LAST:event_nrotimbradoKeyPressed

    private void cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaActionPerformed
        this.BuscarCaja.doClick();

        // TODO add your handling code here:
    }//GEN-LAST:event_cajaActionPerformed

    private void BuscarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCajaActionPerformed
        cajaDAO cajDAO = new cajaDAO();
        caja ca = null;
        try {
            ca = cajDAO.buscarId(Integer.valueOf(this.caja.getText()));
            if (ca.getCodigo() == 0) {
                BCaja.setModal(true);
                BCaja.setSize(500, 575);
                BCaja.setLocationRelativeTo(null);
                BCaja.setVisible(true);
                BCaja.setTitle("Buscar Caja");
                BCaja.setModal(false);
            } else {
                nombrecaja.setText(ca.getNombre());
                //Establecemos un título para el jDialog
            }
            cliente.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCajaActionPerformed

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
        this.BCaja.setVisible(false);
        this.jTBuscarCaja.setText("");
        this.cliente.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCajaActionPerformed

    private void SalirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCajaActionPerformed
        this.BCaja.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCajaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int nFila = tablanotacredito.getSelectedRow();
        String cReferencia = tablanotacredito.getValueAt(nFila, 0).toString();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablanotacredito.requestFocus();
            return;
        }
        ventaDAO veDAO = new ventaDAO();
        venta ve = null;
        try {
            ve = veDAO.buscarId(cReferencia);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (ve != null) {
            //SE CARGAN LOS DATOS DE LA CABECERA
            idnotacredito.setText(ve.getCreferencia());
            timbrado.setText(formatosinpunto.format(ve.getNrotimbrado()));
            notacredito.setText(ve.getFormatofactura());
            codcliente.setText(String.valueOf(ve.getCliente().getCodigo()));
            nombreclientecredito.setText(ve.getCliente().getNombre());
            this.detalle_referencia(idnotacredito.getText());
        }
        itemreferencias.setModal(true);
        itemreferencias.setSize(530, 345);
        //Establecemos un título para el jDialog
        itemreferencias.setTitle("Asociar a Factura");
        itemreferencias.setLocationRelativeTo(null);
        itemreferencias.setVisible(true);
        nrofactura.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void CargarTituloReferencia() {
        modelonota.addColumn("IdItem");
        modelonota.addColumn("N° Orden");
        modelonota.addColumn("Factura");
        int[] anchos = {10, 150, 150};
        for (int i = 0; i < modelonota.getColumnCount(); i++) {
            this.nota.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) nota.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        nota.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        this.nota.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.nota.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.nota.getColumnModel().getColumn(2).setCellRenderer(TablaCentro);

        this.nota.getColumnModel().getColumn(0).setMaxWidth(0);
        this.nota.getColumnModel().getColumn(0).setMinWidth(0);
        this.nota.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.nota.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

    }


    private void GrabarNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarNotaActionPerformed
        ventaDAO vtDAO = new ventaDAO();
        venta vt = null;
        try {
            vt = vtDAO.buscarIdReferencia(nrofactura.getText(), Integer.valueOf(codcliente.getText()));
            if (vt.getCreferencia() == null) {
                JOptionPane.showMessageDialog(null, "Factura no Existe");
                nrofactura.requestFocus();
                return;
            } else {
                idfactura.setText(vt.getCreferencia());
            }
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar el Registro? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            nota_creditoDAO ntDAO = new nota_creditoDAO();
            nota_credito nt = new nota_credito();

            if (this.nrofactura.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese Factura");
                this.nrofactura.requestFocus();
                return;
            }
            nt.setIdnotacredito(idnotacredito.getText());
            nt.setTimbrado(Integer.valueOf(timbrado.getText()));
            nt.setNotacredito(notacredito.getText());
            nt.setNrofactura(nrofactura.getText());
            nt.setIdfactura(idfactura.getText());
            nt.setTimbradoasociado(vt.getNrotimbrado());
            nt.setTipo(2);

            try {
                ntDAO.insertarReferencia(nt);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.detalle_referencia(idnotacredito.getText());
            this.nrofactura.setText("");
            this.idfactura.setText("");
            this.nrofactura.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarNotaActionPerformed

    private void nrofacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nrofacturaActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaActionPerformed

    private void nrofacturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nrofacturaFocusLost
    }//GEN-LAST:event_nrofacturaFocusLost

    private void nrofacturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrofacturaKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarNota.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaKeyReleased

    private void notaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notaKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_notaKeyPressed

    private void notaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_notaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            int nFila = this.nota.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            String cItem = nota.getValueAt(nota.getSelectedRow(), 0).toString();
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Borrar este Item ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                nota_creditoDAO ntDAO = new nota_creditoDAO();
                nota_credito nt = new nota_credito();
                try {
                    nt.setIditem(Double.valueOf(cItem));
                    ntDAO.borrarReferencia(nt);
                    this.detalle_referencia(this.idnotacredito.getText());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }        // TODO add your handling code here:
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_notaKeyReleased

    private void limpiaritems() {
        this.codprod.setText("");
        this.cantidad.setText("0");
        this.nombreproducto.setText("");
        this.precio.setText("0");
        this.porcentaje.setText("0");
        this.totalitem.setText("0");
    }

    public void filtrovendedor(int nNumeroColumna) {
        trsfiltrovendedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarVendedor.getText(), nNumeroColumna));
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtroproducto(int nNumeroColumna) {
        trsfiltroproducto.setRowFilter(RowFilter.regexFilter(jTBuscarProducto.getText(), nNumeroColumna));
    }

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    public void filtrocomprobante(int nNumeroColumna) {
        trsfiltrocomprobante.setRowFilter(RowFilter.regexFilter(this.jTBuscarComprobante.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Nota");
        modelo.addColumn("Fecha");
        modelo.addColumn("Comprobante");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Denominación Cliente");
        modelo.addColumn("Moneda");
        modelo.addColumn("Exentas");
        modelo.addColumn("Gravadas 5%");
        modelo.addColumn("Gravadas 10%");
        modelo.addColumn("Total Neto");
        modelo.addColumn("Moneda");

        int[] anchos = {3, 120, 90, 200, 100, 200, 200, 100, 100, 100, 100, 10};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.tablanotacredito.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        ((DefaultTableCellRenderer) tablanotacredito.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablanotacredito.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        this.tablanotacredito.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablanotacredito.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablanotacredito.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablanotacredito.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablanotacredito.getColumnModel().getColumn(11).setMaxWidth(0);
        this.tablanotacredito.getColumnModel().getColumn(11).setMinWidth(0);
        this.tablanotacredito.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
        this.tablanotacredito.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);

        this.tablanotacredito.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablanotacredito.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablanotacredito.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.tablanotacredito.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.tablanotacredito.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
    }

    private void TituloDetalle() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelodetalle.addColumn("Código");
        modelodetalle.addColumn("Descripción");
        modelodetalle.addColumn("Cantidad");
        modelodetalle.addColumn("% IVA");
        modelodetalle.addColumn("Precio");
        modelodetalle.addColumn("Total");
        int[] anchos = {60, 200, 50, 50, 100, 100};
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            tabladetalle.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalle.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladetalle.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        //Se usa para poner invisible una determinada celda

        tabladetalle.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 10);
        tabladetalle.setFont(font);
    }

    private void TitSuc() {
        modelosucursal.addColumn("Código");
        modelosucursal.addColumn("Nombre");
        modelosucursal.addColumn("Factura");
        modelosucursal.addColumn("Expedicion");

        int[] anchos = {90, 200, 90, 90};
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

        this.tablasucursal.getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablasucursal.getColumnModel().getColumn(2).setMinWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
        this.tablasucursal.getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablasucursal.getColumnModel().getColumn(3).setMinWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
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

    private void TituloComprobante() {
        modelocomprobante.addColumn("Código");
        modelocomprobante.addColumn("Nombre");

        int[] anchos = {90, 200};
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

    private void TituloProductos() {
        modeloproducto.addColumn("Código");
        modeloproducto.addColumn("Descripción");
        modeloproducto.addColumn("Precio");
        modeloproducto.addColumn("IVA");
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        int[] anchos = {90, 200, 100, 50};
        for (int i = 0; i < modeloproducto.getColumnCount(); i++) {
            tablaproducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproducto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproducto.getTableHeader().setFont(new Font("Arial Black", 1, 11));
        this.tablaproducto.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaproducto.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacomprobante.setFont(font);
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nota_credito_ventas_ferremax().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCaja;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarCobro;
    private javax.swing.JButton AceptarComprobante;
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton AceptarProducto;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BCaja;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BComprobante;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BProducto;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JDialog BVendedor;
    private javax.swing.JButton BuscarBanco;
    private javax.swing.JButton BuscarCaja;
    private javax.swing.JButton BuscarEmisor;
    private javax.swing.JButton BuscarProducto;
    private javax.swing.JButton BuscarVendedor;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton GrabarItem;
    private javax.swing.JButton GrabarNota;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCaja;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirComprobante;
    private javax.swing.JButton SalirFormaCobro;
    private javax.swing.JButton SalirItem;
    private javax.swing.JButton SalirLotes;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirProducto;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JDialog aprobarcredito;
    private javax.swing.JButton buscarCliente;
    private javax.swing.JButton buscarMoneda;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JButton buscarcomprobante;
    private javax.swing.JTextField cModo;
    private javax.swing.JTextField caja;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JTextField cargobanco;
    private javax.swing.JTextField cliente;
    private javax.swing.JTextField codcliente;
    private javax.swing.JTextField codprod;
    private javax.swing.JComboBox combocaja;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combocomprobante;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox comboproducto;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JComboBox combovendedor;
    private javax.swing.JTextField comprobante;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JTextField creferencia;
    private javax.swing.JFormattedTextField cuotas;
    private com.toedter.calendar.JDateChooser dFinal;
    private com.toedter.calendar.JDateChooser dInicial;
    private javax.swing.JButton delitem;
    private javax.swing.JFormattedTextField descuentoc;
    private javax.swing.JDialog detalle_notacredito;
    private javax.swing.JTextField direccioncliente;
    private javax.swing.JButton editaritem;
    private javax.swing.JFormattedTextField efectivo;
    private com.toedter.calendar.JDateChooser emisioncheque;
    private com.toedter.calendar.JDateChooser emisiontarjeta;
    private javax.swing.JTextField emisor;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private javax.swing.JFormattedTextField exentas;
    private javax.swing.JTextField factura;
    private javax.swing.JTextField facturafinal;
    private javax.swing.JTextField facturainicial;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechac;
    private javax.swing.JButton grabaroc;
    private javax.swing.JFormattedTextField gravadas10;
    private javax.swing.JFormattedTextField gravadas5;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idfactura;
    private javax.swing.JTextField idnotacredito;
    private javax.swing.JLabel imagen;
    private javax.swing.JFormattedTextField importec;
    private javax.swing.JFormattedTextField importecheque;
    private javax.swing.JButton imprimirlotes;
    private javax.swing.JDialog ingresar_cobros;
    private javax.swing.JDialog itemnotacreditovta;
    private javax.swing.JDialog itemreferencias;
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
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
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
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCaja;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarComprobante;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarProducto;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTBuscarVendedor;
    private javax.swing.JDialog lotes;
    private javax.swing.JTextField modo;
    private javax.swing.JTextField moneda;
    private javax.swing.JFormattedTextField neto;
    private javax.swing.JTextField nombrebanco;
    private javax.swing.JTextField nombrecaja;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreclientec;
    private javax.swing.JTextField nombreclientecredito;
    private javax.swing.JTextField nombrecomprobante;
    private javax.swing.JTextField nombreemisor;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nombrevendedor;
    private javax.swing.JTable nota;
    private javax.swing.JTextField notacredito;
    private javax.swing.JTextField nrocheque;
    private javax.swing.JTextField nrofactura;
    private javax.swing.JTextField nrotarjeta;
    private javax.swing.JTextField nrotimbrado;
    private javax.swing.JButton nuevoitem;
    private javax.swing.JTextField numeroc;
    private javax.swing.JTextArea observacion;
    private javax.swing.JFormattedTextField pagotarjeta;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JPanel panelimagen;
    private javax.swing.JFormattedTextField porcentaje;
    private javax.swing.JFormattedTextField precio;
    private com.toedter.calendar.JDateChooser primervence;
    private javax.swing.JButton refrescar;
    private javax.swing.JButton saliropcion;
    private javax.swing.JFormattedTextField sucambio;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablacaja;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacomprobante;
    private javax.swing.JTable tabladetalle;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablanotacredito;
    private javax.swing.JTable tablaproducto;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTable tablavendedor;
    private javax.swing.JTextField timbrado;
    private javax.swing.JFormattedTextField totalitem;
    private javax.swing.JFormattedTextField totalneto;
    private javax.swing.JFormattedTextField totalventa;
    private com.toedter.calendar.JDateChooser venceanterior;
    private com.toedter.calendar.JDateChooser vencetimbrado;
    private com.toedter.calendar.JDateChooser vencimientos;
    private javax.swing.JTextField vendedor;
    // End of variables declaration//GEN-END:variables

    private class GrillaVenta extends Thread {

        public void run() {

            Date dFechaInicio = ODate.de_java_a_sql(dInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(dFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            ventaDAO DAO = new ventaDAO();
            try {
                for (venta orden : DAO.MostrarxFecha(dFechaInicio, dFechaFinal, 0)) {
                    String Datos[] = {orden.getCreferencia(), orden.getFormatofactura(), formatoFecha.format(orden.getFecha()), orden.getComprobante().getNombre(), orden.getSucursal().getNombre(), orden.getCliente().getNombre(), orden.getMoneda().getEtiqueta(), formatea.format(orden.getExentas()), formatea.format(orden.getGravadas5()), formatea.format(orden.getGravadas10()), formatea.format(orden.getTotalneto()), String.valueOf(orden.getMoneda().getCodigo())};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablanotacredito.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = tablanotacredito.getRowCount();
            if (cantFilas > 0) {
                Modificar.setEnabled(true);
                Eliminar.setEnabled(true);
                Listar.setEnabled(true);
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                Listar.setEnabled(false);
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
                    String Datos[] = {String.valueOf(suc.getCodigo()), suc.getNombre(), formatosinpunto.format(suc.getNotacredito()), suc.getExpedicion_nota()};
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

    private class GrillaComprobante extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocomprobante.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocomprobante.removeRow(0);
            }
            comprobanteDAO DAOcm = new comprobanteDAO();
            try {
                for (comprobante com : DAOcm.todosxtipo(2)) {
                    String Datos[] = {String.valueOf(com.getCodigo()), com.getNombre()};
                    modelocomprobante.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacomprobante.setRowSorter(new TableRowSorter(modelocomprobante));
            int cantFilas = tablacomprobante.getRowCount();
        }
    }

    private class GrillaCasa extends Thread {

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

    private class GrillaProductos extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproducto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproducto.removeRow(0);
            }
            productoDAO DAOpro = new productoDAO();
            try {
                for (producto pr : DAOpro.todosbasico()) {
                    if (pr.getIvaporcentaje() == null) {
                        double nPorcentajeIVA = 0.00;
                    } else {
                        nPorcentajeIVA = pr.getIvaporcentaje().doubleValue();
                    }
                    String Datos[] = {String.valueOf(pr.getCodigo()), pr.getNombre(), formatea.format(pr.getPrecio_minimo()), formatea.format(nPorcentajeIVA)};
                    modeloproducto.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
            int cantFilas = tablaproducto.getRowCount();
        }
    }

    private class GrillaVendedor extends Thread {

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

    private class GenerarReporte extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = tablanotacredito.getSelectedRow();
                String num = tablanotacredito.getValueAt(nFila, 10).toString();
                num = num.replace(".", "").replace(",", ".");
                num = num.replace("-", "");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, Integer.valueOf(tablanotacredito.getValueAt(nFila, 11).toString())));
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", tablanotacredito.getValueAt(nFila, 0).toString());
                JasperReport jr = null;
//              URL url = getClass().getClassLoader().getResource("Reports/notacredito_la_casita.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/notacreditoferremax.jasper");
//                URL url = getClass().getClassLoader().getResource("Reports/notacredito_gaposa.jasper");
                //URL url = getClass().getClassLoader().getResource("Reports/nota_credito_clickcash.jasper");
                //  URL url = getClass().getClassLoader().getResource("Reports/notacredito_dioro.jasper");
                //URL url = getClass().getClassLoader().getResource("Reports/" + Config.cNombreFactura.toString());
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

    private class Lotes extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            stm2 = con.conectar();
            String cSql = "SELECT creferencia,totalneto,factura FROM cabecera_ventas WHERE factura>=" + facturainicial.getText() + " AND factura<= " + facturafinal.getText() + " ORDER BY factura ";
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    referencia = results.getString("creferencia");
                    double nTotalNeto = results.getDouble("totalneto");
                    System.out.println(results.getString("creferencia"));

                    try {
                        Map parameters = new HashMap();
                        //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                        //en el reporte
                        String num = String.valueOf(nTotalNeto);
                        numero_a_letras numero = new numero_a_letras();

                        parameters.put("Letra", numero.Convertir(num, true, 1));
                        parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                        parameters.put("cReferencia", referencia);
                        JasperReport jr = null;
                        URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                        // URL url = getClass().getClassLoader().getResource("Reports/" + Config.cNombreFactura.toString());
                        jr = (JasperReport) JRLoader.loadObject(url);
                        JasperPrint masterPrint = null;
                        //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                        masterPrint = JasperFillManager.fillReport(jr, parameters, stm2.getConnection());
                        //         JasperViewer ventana = new JasperViewer(masterPrint, false);
                        //         ventana.setTitle("Vista Previa");
                        //          ventana.setVisible(true);

                        //Enviar Directo a Impresora
                        JasperPrintManager.printReport(masterPrint, false);
                    } catch (Exception e) {
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
                    }
                }
                stm.close();
                stm2.close();
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class GrillaCajaDevo extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocaja.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocaja.removeRow(0);
            }
            cajaDAO DAOCAJA = new cajaDAO();
            try {
                for (caja ca : DAOCAJA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre()};
                    modelocaja.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacaja.setRowSorter(new TableRowSorter(modelocaja));
            int cantFilas = tablacaja.getRowCount();
        }
    }

}

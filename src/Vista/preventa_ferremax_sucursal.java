/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.CalcularRuc;
import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.FormatoTablaStock;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import DAO.clienteDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.localidadDAO;
import DAO.monedaDAO;
import DAO.preventaDAO;
import DAO.productoDAO;
import DAO.sucursalDAO;
import DAO.vendedorDAO;
import DAO.obraDAO;
import Modelo.obra;
import Modelo.albumfoto_producto;
import DAO.albumfoto_productoDAO;
import DAO.aperturaDAO;
import DAO.bancosDAO;
import DAO.cajaDAO;
import DAO.casaDAO;
import DAO.cuenta_clienteDAO;
import DAO.detalle_preventaDAO;
import DAO.extraccionDAO;
import DAO.gastos_comprasDAO;
import DAO.giraduriaDAO;
import DAO.ordencompraDAO;
import DAO.proveedorDAO;
import DAO.rubro_compraDAO;
import DAO.stockDAO;
import DAO.usuarioDAO;
import ExportaVtaDAO.clienteVpnDAO;
import ExportaVtaDAO.stockVpnDAO;
import Modelo.Tablas;
import Modelo.apertura;
import Modelo.banco;
import Modelo.caja;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.detalle_preventa;
import Modelo.extraccion;
import Modelo.giraduria;
import Modelo.localidad;
import Modelo.moneda;
import Modelo.ordencompra;
import Modelo.preventa;
import Modelo.producto;
import Modelo.proveedor;
import Modelo.rubro_compra;
import Modelo.stock;
import Modelo.sucursal;
import Modelo.usuario;
import Modelo.vendedor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.Socket;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.util.Log;
import org.openide.util.Exceptions;

/**
 *
 * @author Usuario
 */
public class preventa_ferremax_sucursal extends javax.swing.JFrame {

    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconoaceptar = new ImageIcon("src/Iconos/aceptar.png");
    ImageIcon imagenfondo = new ImageIcon("src/Iconos/producto.png");

    double nExentas = 0.0D;
    double nGravadas10 = 0.0D;
    double nGravadas5 = 0.0D;
    double nTotal = 0.0D;
    double nIva10 = 0.0D;
    double nIva5 = 0.0D;
    double nCotizacion = 0.0D;
    String cCotizacion = null;

    int indiceTabla = 0;
    int nBusqueda = 0;
    ObtenerFecha ODate = new ObtenerFecha();
    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas busquedalocalidad = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrocliente,
            trsfiltrolocalidad, trsfiltrosuc,
            trsfiltrovendedor, trsfiltroproducto, trsfiltrobanco,
            trsfiltroproveedor, trsfiltromoneda, trsfiltrorubro, trsfiltrocompr;
    DecimalFormat formatosinpunto = new DecimalFormat("###");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatcantidad = new DecimalFormat("######.###");
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    String cSql = null;
    int nFila = 0;
    Tablas buscarproductos = new Tablas();
    Tablas modelorubro = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modeloproveedor = new Tablas();
    Tablas modelocomprobante = new Tablas();
    Tablas modeloproductoubicacion = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelobanco = new Tablas();
    Tablas modelovendedor = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modeloproducto = new Tablas();
    Tablas modelostock = new Tablas();
    String idunico = null;
    double exen = 0.00;
    double grav10 = 0.00;
    double grav5 = 0.00;
    Calendar c2 = new GregorianCalendar();
    double nPorcentajeIVA = 0.00;
    int nItemProductos = 0;
    int nTipoImpresora = 0;
    String dirWeb = Config.cIpServerCopia.toString();

    /**
     * Creates new form ventas
     */
    public preventa_ferremax_sucursal() {
        initComponents();

        this.setLocationRelativeTo(null); //Centramos el formulario
        this.TituloClientes();
        this.TituloProductos();
        this.TitSuc();
        this.TitVendedor();
        TituloPOS();
        this.TitStock();
        inicializar();
        this.creferenciaOrdenCompra.setVisible(false);
        this.vencimientos.setVisible(false);
        this.venceanterior.setVisible(false);
        this.fechaalta.setVisible(false);
        this.numero.setVisible(false);
        this.iditem.setVisible(false);
        this.estacompra.setVisible(false);
        this.disponible.setVisible(false);
        this.limitecredito.setVisible(false);
        this.codcliente.setText("1");
        this.nombrecliente.setText("CLIENTES VARIOS");
        this.vendedor.setText("1");
        this.nombrevendedor.setText("MOSTRADOR");
        this.sucursal.setText("1");
        this.obra.setText("0");
        this.nombreobra.setText("");
        this.nombresucursal.setText("CENTRAL");
        this.codigoproducto.setText("");
        this.nombreproducto.setText("");
        this.cantidad.setText("1");
        this.precio.setText("0");
        this.cambiarprecio.setText("0");
        this.cambiarprecio.setVisible(false);
        this.iva.setVisible(false);
        this.costo.setVisible(false);
        this.precio.setVisible(true);
        this.control.setVisible(false);
        this.AgregarItem.setVisible(false);
        this.BtnProductos.setIcon(iconobuscar);
        this.buscarcliente.setIcon(iconobuscar);
        this.buscarvendedor.setIcon(iconobuscar);
        this.buscarobra.setIcon(iconobuscar);
        this.BuscarRubro.setIcon(iconobuscar);
        this.BuscarProveedor.setIcon(iconobuscar);
        this.BuscarComprobante.setIcon(iconobuscar);
        this.BuscarMoneda.setIcon(iconobuscar);
        this.BuscarCliente.setIcon(iconobuscar);
        this.EtiquetaUsuario.setText(Config.cNombreUsuario);
        this.TituloBanco();
        this.TituloUbicacion();
        this.TitRubro();
        this.TitCompr();
        this.TitMoneda();
        this.TitProveedor();

        //CLIENTES
        GrillaClientes grilla2 = new GrillaClientes();
        Thread hiloclientes = new Thread(grilla2);
        hiloclientes.start();

        GrillaVendedor grillavn = new GrillaVendedor();
        Thread hilove = new Thread(grillavn);
        hilove.start();

        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();

        configuracionDAO configDAO = new configuracionDAO();
        configuracion configbase = null;
        configbase = configDAO.consultar();
        nItemProductos = configbase.getItemfacturas();
        nTipoImpresora = configbase.getTipo_factura_impresion();
        sucursal.setText(String.valueOf(configbase.getSucursaldefecto().getCodigo()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BuscarClientes = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        comboclientes = new javax.swing.JComboBox();
        jTBuscarClientes = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        Aceptarcliente = new javax.swing.JButton();
        SalirCliente = new javax.swing.JButton();
        BuscarLocalidad = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combolocalidad = new javax.swing.JComboBox();
        jTBuscarLocalidad = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablalocalidad = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        Aceptarcliente1 = new javax.swing.JButton();
        SalirCliente1 = new javax.swing.JButton();
        configurar = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        nombresucursal = new javax.swing.JTextField();
        BuscarSucursal = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        BVendedor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combovendedor = new javax.swing.JComboBox();
        jTBuscarVendedor = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablavendedor = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        BSucursal = new javax.swing.JDialog();
        jPanel18 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        RegistrarCliente = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        BuscarCliente = new javax.swing.JButton();
        nombre = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        fechaingreso = new com.toedter.calendar.JDateChooser();
        jLabel22 = new javax.swing.JLabel();
        nacimiento = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        ruc = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        celular = new javax.swing.JTextField();
        habilitado = new javax.swing.JCheckBox();
        control = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        GrabarCliente = new javax.swing.JButton();
        SalirCargaCliente = new javax.swing.JButton();
        BProducto = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        comboproducto = new javax.swing.JComboBox();
        textoproducto = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaproducto = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarProducto = new javax.swing.JButton();
        SalirProducto = new javax.swing.JButton();
        EditarCantidad = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        codigoeditar = new javax.swing.JTextField();
        nombreeditar = new javax.swing.JTextField();
        precioeditar = new javax.swing.JFormattedTextField();
        cantidadeditar = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        UpdateCantidad = new javax.swing.JButton();
        SalirCantidad = new javax.swing.JButton();
        panelfoto = new javax.swing.JDialog();
        jPanel20 = new javax.swing.JPanel();
        etiquetanombreproducto = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        fotoProducto = new JPanelWebCam.JPanelWebCam();
        jPanel22 = new javax.swing.JPanel();
        SalirFoto = new javax.swing.JButton();
        BBancos = new javax.swing.JDialog();
        jPanel27 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        jTBuscarbanco = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        Aceptar = new javax.swing.JButton();
        SalirBanco = new javax.swing.JButton();
        inventario = new javax.swing.JDialog();
        jPanel51 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tablastock = new javax.swing.JTable();
        jPanel52 = new javax.swing.JPanel();
        SalirStockSucursal = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        etiquetacodigo = new javax.swing.JLabel();
        etiquetanombre = new javax.swing.JLabel();
        ubicacion = new javax.swing.JDialog();
        jPanel54 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tablaproductoubicacion = new javax.swing.JTable();
        jPanel55 = new javax.swing.JPanel();
        SalirUbicacion = new javax.swing.JButton();
        jPanel56 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        etiquetacodigoubicacion = new javax.swing.JLabel();
        etiquetanombreubicacion = new javax.swing.JLabel();
        detalle_orden_compra = new javax.swing.JDialog();
        GrabarOrdenCompra = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        cliente1 = new javax.swing.JTextField();
        buscarCliente1 = new javax.swing.JButton();
        nombrecliente1 = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        jLabel94 = new javax.swing.JLabel();
        plazo = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        montocuota = new javax.swing.JFormattedTextField();
        primervence = new com.toedter.calendar.JDateChooser();
        jLabel97 = new javax.swing.JLabel();
        vencimientos = new com.toedter.calendar.JDateChooser();
        venceanterior = new com.toedter.calendar.JDateChooser();
        fechaalta = new com.toedter.calendar.JDateChooser();
        creferenciaOrdenCompra = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        fecha1 = new com.toedter.calendar.JDateChooser();
        itemgastos = new javax.swing.JDialog();
        jPanel30 = new javax.swing.JPanel();
        GrabarItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel35 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        fechafactura = new com.toedter.calendar.JDateChooser();
        jLabel37 = new javax.swing.JLabel();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JFormattedTextField();
        jLabel41 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        exentas = new javax.swing.JFormattedTextField();
        gravadas10 = new javax.swing.JFormattedTextField();
        iva10 = new javax.swing.JFormattedTextField();
        gravadas5 = new javax.swing.JFormattedTextField();
        iva5 = new javax.swing.JFormattedTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        Totalneto = new javax.swing.JFormattedTextField();
        jLabel47 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        observacion1 = new javax.swing.JTextArea();
        proveedor = new javax.swing.JTextField();
        nombreproveedor = new javax.swing.JTextField();
        timbrado = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        vencimientotimbrado = new com.toedter.calendar.JDateChooser();
        BuscarProveedor = new javax.swing.JButton();
        moneda = new javax.swing.JTextField();
        BuscarMoneda = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        comprobante = new javax.swing.JTextField();
        BuscarComprobante = new javax.swing.JButton();
        nombrecomprobante = new javax.swing.JTextField();
        rubro = new javax.swing.JTextField();
        BuscarRubro = new javax.swing.JButton();
        nombrerubro = new javax.swing.JTextField();
        nombreinmuebleeti = new javax.swing.JLabel();
        nrofactura = new javax.swing.JTextField();
        fondo = new javax.swing.JLabel();
        BRubro = new javax.swing.JDialog();
        jPanel34 = new javax.swing.JPanel();
        comborubro = new javax.swing.JComboBox();
        jTBuscarRubro = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablarubro = new javax.swing.JTable();
        jPanel35 = new javax.swing.JPanel();
        AceptarRubro = new javax.swing.JButton();
        SalirRubro = new javax.swing.JButton();
        BComprobantes = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        comboComprobantes = new javax.swing.JComboBox();
        jTBuscarComprobantes = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablacomprobante = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        AceptarComprobantes = new javax.swing.JButton();
        SalirComprobantes = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel38 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel39 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        BProveedor = new javax.swing.JDialog();
        jPanel40 = new javax.swing.JPanel();
        comboproveedor = new javax.swing.JComboBox();
        jTBuscarProveedor = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablaproveedor = new javax.swing.JTable();
        jPanel41 = new javax.swing.JPanel();
        AceptarProveedor = new javax.swing.JButton();
        SalirProveedor = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        codigoproducto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nombreproducto = new javax.swing.JTextField();
        cantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        AgregarItem = new javax.swing.JButton();
        BtnProductos = new javax.swing.JButton();
        cambiarprecio = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        precio = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabladetalle = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        iva = new javax.swing.JTextField();
        costo = new javax.swing.JTextField();
        GuardarPreventa = new javax.swing.JButton();
        SalirCompleto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        estacompra = new javax.swing.JFormattedTextField();
        disponible = new javax.swing.JFormattedTextField();
        limitecredito = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        codcliente = new javax.swing.JTextField();
        buscarcliente = new javax.swing.JButton();
        nombrecliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        vendedor = new javax.swing.JTextField();
        buscarvendedor = new javax.swing.JButton();
        nombrevendedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tipocomprobante = new javax.swing.JComboBox<>();
        obra = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        buscarobra = new javax.swing.JButton();
        nombreobra = new javax.swing.JTextField();
        numero = new javax.swing.JFormattedTextField();
        iditem = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        EtiquetaUsuario = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        descuento = new javax.swing.JFormattedTextField();
        totalneto = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        BuscarClientes.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BuscarClientes.setTitle("Buscar Clientes");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboclientes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboclientes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        comboclientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclientesActionPerformed(evt);
            }
        });

        jTBuscarClientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarClientesKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(comboclientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboclientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacliente.setModel(modelocliente);
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
        jScrollPane4.setViewportView(tablacliente);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptarcliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Aceptarcliente.setText("Aceptar");
        Aceptarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarclienteActionPerformed(evt);
            }
        });

        SalirCliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SalirCliente.setText("Salir");
        SalirCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptarcliente)
                    .addComponent(SalirCliente))
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscarClientesLayout = new javax.swing.GroupLayout(BuscarClientes.getContentPane());
        BuscarClientes.getContentPane().setLayout(BuscarClientesLayout);
        BuscarClientesLayout.setHorizontalGroup(
            BuscarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarClientesLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscarClientesLayout.setVerticalGroup(
            BuscarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarClientesLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BuscarLocalidad.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BuscarLocalidad.setTitle("Buscar Localidad");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combolocalidad.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combolocalidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combolocalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combolocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combolocalidadActionPerformed(evt);
            }
        });

        jTBuscarLocalidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarLocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarLocalidadKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combolocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combolocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablalocalidad.setModel(busquedalocalidad);
        tablalocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablalocalidadKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablalocalidad);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptarcliente1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Aceptarcliente1.setText("Aceptar");
        Aceptarcliente1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptarcliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Aceptarcliente1ActionPerformed(evt);
            }
        });

        SalirCliente1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SalirCliente1.setText("Salir");
        SalirCliente1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliente1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptarcliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptarcliente1)
                    .addComponent(SalirCliente1))
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscarLocalidadLayout = new javax.swing.GroupLayout(BuscarLocalidad.getContentPane());
        BuscarLocalidad.getContentPane().setLayout(BuscarLocalidadLayout);
        BuscarLocalidadLayout.setHorizontalGroup(
            BuscarLocalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarLocalidadLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscarLocalidadLayout.setVerticalGroup(
            BuscarLocalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarLocalidadLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        configurar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("Sucursal");

        sucursal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucursalActionPerformed(evt);
            }
        });

        BuscarSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarSucursalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel4)
                .addGap(53, 53, 53)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton5.setText("Aceptar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Salir");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(33, 33, 33)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout configurarLayout = new javax.swing.GroupLayout(configurar.getContentPane());
        configurar.getContentPane().setLayout(configurarLayout);
        configurarLayout.setHorizontalGroup(
            configurarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        configurarLayout.setVerticalGroup(
            configurarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configurarLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jTBuscarVendedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCliente.text")); // NOI18N
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

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane6.setViewportView(tablasucursal);

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        RegistrarCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        RegistrarCliente.setTitle("Registrar Cliente");

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.jLabel1.text")); // NOI18N

        codigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        codigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codigo.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.codigo.text")); // NOI18N
        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoFocusLost(evt);
            }
        });
        codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoActionPerformed(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
        });

        BuscarCliente.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.BuscarCliente.text")); // NOI18N
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        nombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nombre.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.codigo.text")); // NOI18N
        nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombreFocusLost(evt);
            }
        });
        nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombreKeyReleased(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.jLabel6.text")); // NOI18N

        fechaingreso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechaingreso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaingresoFocusGained(evt);
            }
        });
        fechaingreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaingresoKeyPressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.jLabel5.text")); // NOI18N

        nacimiento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nacimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nacimientoFocusGained(evt);
            }
        });
        nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacimientoKeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.jLabel2.text")); // NOI18N

        ruc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ruc.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.codigo.text")); // NOI18N
        ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rucKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.jLabel4.text")); // NOI18N

        celular.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        celular.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.codigo.text")); // NOI18N
        celular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                celularKeyPressed(evt);
            }
        });

        habilitado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        habilitado.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.habilitado.text")); // NOI18N

        control.setEditable(false);
        control.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.control.text")); // NOI18N
        control.setEnabled(false);

        direccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        direccion.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.codigo.text")); // NOI18N
        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                direccionKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Nombre");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("Dirección");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel21)
                        .addComponent(jLabel22)
                        .addComponent(jLabel23)
                        .addComponent(jLabel24)
                        .addComponent(jLabel18))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel29))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(150, 150, 150)
                                .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(nacimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                .addComponent(fechaingreso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(habilitado))))
                .addGap(29, 29, 29))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(11, 11, 11)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(habilitado)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCliente.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.BotonGrabar.text")); // NOI18N
        GrabarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarClienteActionPerformed(evt);
            }
        });

        SalirCargaCliente.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "clientes_sparta.BotonSalir.text")); // NOI18N
        SalirCargaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCargaClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(GrabarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(SalirCargaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SalirCargaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout RegistrarClienteLayout = new javax.swing.GroupLayout(RegistrarCliente.getContentPane());
        RegistrarCliente.getContentPane().setLayout(RegistrarClienteLayout);
        RegistrarClienteLayout.setHorizontalGroup(
            RegistrarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        RegistrarClienteLayout.setVerticalGroup(
            RegistrarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistrarClienteLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BProducto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProducto.setTitle("null");
        BProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BProductoFocusGained(evt);
            }
        });
        BProducto.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                BProductoWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproducto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproductoActionPerformed(evt);
            }
        });

        textoproducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        textoproducto.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.jTBuscarClientes.text")); // NOI18N
        textoproducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textoproductoFocusGained(evt);
            }
        });
        textoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoproductoActionPerformed(evt);
            }
        });
        textoproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textoproductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textoproductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoproductoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaproductoKeyReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tablaproducto);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarProducto.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProductoActionPerformed(evt);
            }
        });

        SalirProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirProducto.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCliente.text")); // NOI18N
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
                .addGap(194, 194, 194)
                .addComponent(AceptarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarProducto)
                    .addComponent(SalirProducto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BProductoLayout = new javax.swing.GroupLayout(BProducto.getContentPane());
        BProducto.getContentPane().setLayout(BProductoLayout);
        BProductoLayout.setHorizontalGroup(
            BProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane8)
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

        EditarCantidad.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        EditarCantidad.setTitle("Modificar Cantidad");
        EditarCantidad.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                EditarCantidadWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setText("Código");

        jLabel17.setText("Descripción");

        jLabel19.setText("Precio");

        jLabel20.setText("Cantidad");

        codigoeditar.setEditable(false);

        nombreeditar.setEditable(false);

        precioeditar.setEditable(false);
        precioeditar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        precioeditar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        cantidadeditar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cantidadeditar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidadeditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cantidadeditarFocusGained(evt);
            }
        });
        cantidadeditar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadeditarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(precioeditar, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(cantidadeditar)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel15))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codigoeditar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreeditar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codigoeditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(nombreeditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(precioeditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cantidadeditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UpdateCantidad.setText("Aceptar");
        UpdateCantidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdateCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateCantidadActionPerformed(evt);
            }
        });
        UpdateCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UpdateCantidadKeyPressed(evt);
            }
        });

        SalirCantidad.setText("Salir");
        SalirCantidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCantidadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(UpdateCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(SalirCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateCantidad)
                    .addComponent(SalirCantidad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout EditarCantidadLayout = new javax.swing.GroupLayout(EditarCantidad.getContentPane());
        EditarCantidad.getContentPane().setLayout(EditarCantidadLayout);
        EditarCantidadLayout.setHorizontalGroup(
            EditarCantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        EditarCantidadLayout.setVerticalGroup(
            EditarCantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditarCantidadLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetanombreproducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetanombreproducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetanombreproducto.setText("jLabel31");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetanombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiquetanombreproducto)
                .addContainerGap())
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        fotoProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fotoProducto.setToolTipText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.fotoProducto.toolTipText")); // NOI18N
        fotoProducto.setACTIVARCAMARA(false);
        fotoProducto.setFONDO(false);
        fotoProducto.setEnabled(false);

        javax.swing.GroupLayout fotoProductoLayout = new javax.swing.GroupLayout(fotoProducto);
        fotoProducto.setLayout(fotoProductoLayout);
        fotoProductoLayout.setHorizontalGroup(
            fotoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        fotoProductoLayout.setVerticalGroup(
            fotoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fotoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fotoProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirFoto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirFoto.setText("Salir de Imágen");
        SalirFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelfotoLayout = new javax.swing.GroupLayout(panelfoto.getContentPane());
        panelfoto.getContentPane().setLayout(panelfotoLayout);
        panelfotoLayout.setHorizontalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelfotoLayout.setVerticalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfotoLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BBancos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BBancos.setTitle(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_extracciones.BBancos.title")); // NOI18N

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarbanco.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_extracciones.jTBuscarbanco.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane10.setViewportView(tablabanco);

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptar.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_extracciones.Aceptar.text")); // NOI18N
        Aceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        SalirBanco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirBanco.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_extracciones.SalirBanco.text")); // NOI18N
        SalirBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirBancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SalirBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Aceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout BBancosLayout = new javax.swing.GroupLayout(BBancos.getContentPane());
        BBancos.getContentPane().setLayout(BBancosLayout);
        BBancosLayout.setHorizontalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBancosLayout.setVerticalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        inventario.setTitle(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.inventario.title")); // NOI18N

        jPanel51.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablastock.setModel(modelostock);
        jScrollPane16.setViewportView(tablastock);

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel52.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirStockSucursal.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.SalirStock.text")); // NOI18N
        SalirStockSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirStockSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirStockSucursalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                .addComponent(SalirStockSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SalirStockSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel53.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel56.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.jLabel56.text")); // NOI18N

        jLabel57.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.jLabel57.text")); // NOI18N

        etiquetacodigo.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.etiquetacodigo.text")); // NOI18N

        etiquetanombre.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.etiquetanombre.text")); // NOI18N

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addGap(18, 18, 18)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetacodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetanombre, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(etiquetacodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(etiquetanombre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout inventarioLayout = new javax.swing.GroupLayout(inventario.getContentPane());
        inventario.getContentPane().setLayout(inventarioLayout);
        inventarioLayout.setHorizontalGroup(
            inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel51, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel53, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inventarioLayout.setVerticalGroup(
            inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ubicacion.setTitle(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "vencimientoxgiraduria.ubicacion.title")); // NOI18N

        jPanel54.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablaproductoubicacion.setModel(modeloproductoubicacion  );
        jScrollPane17.setViewportView(tablaproductoubicacion);

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel55.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirUbicacion.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.SalirStock.text")); // NOI18N
        SalirUbicacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirUbicacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addComponent(SalirUbicacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SalirUbicacion, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel56.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel60.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.jLabel56.text")); // NOI18N

        jLabel61.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.jLabel57.text")); // NOI18N

        etiquetacodigoubicacion.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.etiquetacodigo.text")); // NOI18N

        etiquetanombreubicacion.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas_mercaderias.etiquetanombre.text")); // NOI18N

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60)
                    .addComponent(jLabel61))
                .addGap(18, 18, 18)
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetacodigoubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetanombreubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(etiquetacodigoubicacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(etiquetanombreubicacion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ubicacionLayout = new javax.swing.GroupLayout(ubicacion.getContentPane());
        ubicacion.getContentPane().setLayout(ubicacionLayout);
        ubicacionLayout.setHorizontalGroup(
            ubicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ubicacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ubicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel54, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel56, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ubicacionLayout.setVerticalGroup(
            ubicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ubicacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        detalle_orden_compra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_orden_compraFocusGained(evt);
            }
        });
        detalle_orden_compra.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_orden_compraWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_orden_compra.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_orden_compraWindowActivated(evt);
            }
        });

        GrabarOrdenCompra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarOrdenCompra.setText("Grabar");
        GrabarOrdenCompra.setToolTipText("Guardar los Cambios");
        GrabarOrdenCompra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarOrdenCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarOrdenCompraActionPerformed(evt);
            }
        });

        Salir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salir.setText("Salir");
        Salir.setToolTipText("Salir sin Guardar");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel81.setText("Cliente");

        cliente1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cliente1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cliente1FocusGained(evt);
            }
        });
        cliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cliente1ActionPerformed(evt);
            }
        });
        cliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cliente1KeyPressed(evt);
            }
        });

        buscarCliente1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCliente1ActionPerformed(evt);
            }
        });

        nombrecliente1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecliente1.setEnabled(false);

        jLabel93.setText("Importe Solicitado");

        importe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        importe.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                importeFocusGained(evt);
            }
        });
        importe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importeKeyPressed(evt);
            }
        });

        jLabel94.setText("Plazo");

        plazo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        plazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                plazoFocusGained(evt);
            }
        });
        plazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plazoActionPerformed(evt);
            }
        });
        plazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plazoKeyPressed(evt);
            }
        });

        jLabel95.setText("Importe Cuota");

        montocuota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        montocuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        montocuota.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                montocuotaFocusGained(evt);
            }
        });
        montocuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                montocuotaKeyPressed(evt);
            }
        });

        primervence.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primervenceKeyPressed(evt);
            }
        });

        jLabel97.setText("Primera Cuota");

        vencimientos.setEnabled(false);

        venceanterior.setEnabled(false);

        fechaalta.setEnabled(false);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel93))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel81)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(cliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombrecliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fechaalta, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                    .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addGap(56, 56, 56)
                                .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel94)
                                    .addComponent(jLabel95)))
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel97)))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(primervence, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(montocuota, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel81))
                    .addComponent(buscarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrecliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel93)
                            .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel94))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel95)
                            .addComponent(montocuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel97)
                    .addComponent(primervence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        creferenciaOrdenCompra.setEnabled(false);

        jLabel34.setText("Fecha Emisión");

        fecha1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fecha1FocusGained(evt);
            }
        });
        fecha1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fecha1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout detalle_orden_compraLayout = new javax.swing.GroupLayout(detalle_orden_compra.getContentPane());
        detalle_orden_compra.getContentPane().setLayout(detalle_orden_compraLayout);
        detalle_orden_compraLayout.setHorizontalGroup(
            detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_orden_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detalle_orden_compraLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(creferenciaOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(detalle_orden_compraLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(GrabarOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        detalle_orden_compraLayout.setVerticalGroup(
            detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_compraLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(creferenciaOrdenCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Salir)
                    .addComponent(GrabarOrdenCompra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        itemgastos.setMinimumSize(new java.awt.Dimension(800, 590));
        itemgastos.setResizable(false);
        itemgastos.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel30.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItem.setText("Grabar");
        GrabarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemActionPerformed(evt);
            }
        });

        SalirItem.setText("Salir");
        SalirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(504, 504, 504)
                .addComponent(GrabarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SalirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarItem)
                    .addComponent(SalirItem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        itemgastos.getContentPane().add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 730, 50));

        panel2.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel2.setColorSecundario(new java.awt.Color(0, 204, 255));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addContainerGap(683, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        itemgastos.getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel1.text")); // NOI18N
        jPanel31.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, 20));
        jPanel31.add(fechafactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 100, 22));

        jLabel37.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel2.text")); // NOI18N
        jPanel31.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 50, 20));
        jPanel31.add(vencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 100, 22));

        jLabel38.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel3.text")); // NOI18N
        jPanel31.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, 20));

        jLabel39.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel5.text")); // NOI18N
        jPanel31.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 70, 20));

        jLabel40.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel8.text")); // NOI18N
        jPanel31.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 60, 20));

        cotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion.setText("1");
        cotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });
        jPanel31.add(cotizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 80, -1));

        jLabel41.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel9.text")); // NOI18N
        jPanel31.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 50, 20));

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jPanel1.border.title"))); // NOI18N

        exentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        exentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        exentas.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.exentas.text")); // NOI18N
        exentas.setToolTipText("");
        exentas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                exentasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                exentasFocusLost(evt);
            }
        });
        exentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                exentasKeyPressed(evt);
            }
        });

        gravadas10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gravadas10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas10.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.exentas.text")); // NOI18N
        gravadas10.setToolTipText("");
        gravadas10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gravadas10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gravadas10FocusLost(evt);
            }
        });
        gravadas10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gravadas10KeyPressed(evt);
            }
        });

        iva10.setEditable(false);
        iva10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        iva10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        iva10.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.exentas.text")); // NOI18N
        iva10.setToolTipText("");
        iva10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                iva10FocusGained(evt);
            }
        });
        iva10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                iva10KeyPressed(evt);
            }
        });

        gravadas5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gravadas5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas5.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.exentas.text")); // NOI18N
        gravadas5.setToolTipText("");
        gravadas5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gravadas5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gravadas5FocusLost(evt);
            }
        });
        gravadas5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gravadas5KeyPressed(evt);
            }
        });

        iva5.setEditable(false);
        iva5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        iva5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        iva5.setToolTipText("");
        iva5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                iva5FocusGained(evt);
            }
        });
        iva5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                iva5KeyPressed(evt);
            }
        });

        jLabel42.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel10.text")); // NOI18N

        jLabel43.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel11.text")); // NOI18N

        jLabel44.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel12.text")); // NOI18N

        jLabel45.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel13.text")); // NOI18N

        jLabel46.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel14.text")); // NOI18N

        Totalneto.setEditable(false);
        Totalneto.setBackground(new java.awt.Color(255, 51, 51));
        Totalneto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        Totalneto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Totalneto.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.exentas.text")); // NOI18N
        Totalneto.setToolTipText("");
        Totalneto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Totalneto.setEnabled(false);
        Totalneto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalnetoActionPerformed(evt);
            }
        });

        jLabel47.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel18.text")); // NOI18N

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel43)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Totalneto)
                    .addComponent(exentas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gravadas10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iva10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gravadas5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iva5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gravadas10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iva10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iva5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel31.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 280, 240));

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jPanel4.border.title"))); // NOI18N

        observacion1.setColumns(20);
        observacion1.setRows(5);
        jScrollPane11.setViewportView(observacion1);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel31.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 440, 240));

        proveedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        proveedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.proveedor.text")); // NOI18N
        proveedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                proveedorFocusGained(evt);
            }
        });
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
        jPanel31.add(proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 40, -1));

        nombreproveedor.setEditable(false);
        nombreproveedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.nombreproveedor.text")); // NOI18N
        nombreproveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreproveedor.setEnabled(false);
        jPanel31.add(nombreproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 220, -1));

        timbrado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        timbrado.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.timbrado.text")); // NOI18N
        timbrado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                timbradoFocusGained(evt);
            }
        });
        timbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                timbradoKeyPressed(evt);
            }
        });
        jPanel31.add(timbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 90, -1));

        jLabel48.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel15.text")); // NOI18N
        jPanel31.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        jLabel49.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel16.text")); // NOI18N
        jPanel31.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        jLabel50.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.jLabel17.text")); // NOI18N
        jPanel31.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 110, 20));

        vencimientotimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vencimientotimbradoKeyPressed(evt);
            }
        });
        jPanel31.add(vencimientotimbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 100, 22));

        BuscarProveedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "detalle_gastos.BuscarProveedor.text")); // NOI18N
        BuscarProveedor.setBorder(null);
        BuscarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarProveedorActionPerformed(evt);
            }
        });
        jPanel31.add(BuscarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 20, 22));

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        moneda.setText("0");
        moneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                monedaKeyPressed(evt);
            }
        });
        jPanel31.add(moneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 40, -1));

        BuscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarMoneda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarMonedaMouseClicked(evt);
            }
        });
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
        jPanel31.add(BuscarMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 22, 22));

        nombremoneda.setEnabled(false);
        nombremoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombremonedaKeyPressed(evt);
            }
        });
        jPanel31.add(nombremoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 130, -1));

        comprobante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comprobante.setText("0");
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
        jPanel31.add(comprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 40, -1));

        BuscarComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarComprobanteMouseClicked(evt);
            }
        });
        BuscarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarComprobanteActionPerformed(evt);
            }
        });
        BuscarComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarComprobanteKeyPressed(evt);
            }
        });
        jPanel31.add(BuscarComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 22, 22));

        nombrecomprobante.setEnabled(false);
        nombrecomprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrecomprobanteKeyPressed(evt);
            }
        });
        jPanel31.add(nombrecomprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 130, -1));

        rubro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rubro.setText("0");
        rubro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rubroFocusGained(evt);
            }
        });
        rubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rubroActionPerformed(evt);
            }
        });
        rubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rubroKeyPressed(evt);
            }
        });
        jPanel31.add(rubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 32, -1));

        BuscarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarRubro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarRubroMouseClicked(evt);
            }
        });
        BuscarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarRubroActionPerformed(evt);
            }
        });
        BuscarRubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarRubroKeyPressed(evt);
            }
        });
        jPanel31.add(BuscarRubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 22, 22));

        nombrerubro.setEnabled(false);
        nombrerubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrerubroKeyPressed(evt);
            }
        });
        jPanel31.add(nombrerubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 150, -1));

        nombreinmuebleeti.setText("Rubro");
        jPanel31.add(nombreinmuebleeti, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, -1, -1));

        nrofactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrofactura.setToolTipText("Format Ejemplo 001-001-0000712");
        nrofactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrofacturaKeyPressed(evt);
            }
        });
        jPanel31.add(nrofactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 120, -1));

        itemgastos.getContentPane().add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 776, 420));
        itemgastos.getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, -4, 800, 570));

        BRubro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BRubro.setTitle("null");

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comborubro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comborubro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comborubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comborubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comborubroActionPerformed(evt);
            }
        });

        jTBuscarRubro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarRubro.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarRubroActionPerformed(evt);
            }
        });
        jTBuscarRubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarRubroKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablarubro.setModel(modelorubro       );
        tablarubro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablarubroMouseClicked(evt);
            }
        });
        tablarubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablarubroKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablarubro);

        jPanel35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarRubro.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarRubroActionPerformed(evt);
            }
        });

        SalirRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirRubro.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCliente.text")); // NOI18N
        SalirRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarRubro)
                    .addComponent(SalirRubro))
                .addContainerGap())
        );

        javax.swing.GroupLayout BRubroLayout = new javax.swing.GroupLayout(BRubro.getContentPane());
        BRubro.getContentPane().setLayout(BRubroLayout);
        BRubroLayout.setHorizontalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BRubroLayout.setVerticalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BComprobantes.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BComprobantes.setTitle("null");
        BComprobantes.setMinimumSize(new java.awt.Dimension(490, 595));

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboComprobantes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboComprobantes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboComprobantesActionPerformed(evt);
            }
        });

        jTBuscarComprobantes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarComprobantes.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarComprobantesActionPerformed(evt);
            }
        });
        jTBuscarComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarComprobantesKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(comboComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTBuscarComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jScrollPane12.setViewportView(tablacomprobante);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarComprobantes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarComprobantes.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarComprobantesActionPerformed(evt);
            }
        });

        SalirComprobantes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirComprobantes.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCliente.text")); // NOI18N
        SalirComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirComprobantesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarComprobantes)
                    .addComponent(SalirComprobantes))
                .addContainerGap())
        );

        javax.swing.GroupLayout BComprobantesLayout = new javax.swing.GroupLayout(BComprobantes.getContentPane());
        BComprobantes.getContentPane().setLayout(BComprobantesLayout);
        BComprobantesLayout.setHorizontalGroup(
            BComprobantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane12)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BComprobantesLayout.setVerticalGroup(
            BComprobantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BComprobantesLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");

        jPanel38.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane13.setViewportView(tablamoneda);

        jPanel39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BProveedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProveedor.setTitle("null");

        jPanel40.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproveedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproveedorActionPerformed(evt);
            }
        });

        jTBuscarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarProveedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarProveedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscarProveedorFocusGained(evt);
            }
        });
        jTBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarProveedorActionPerformed(evt);
            }
        });
        jTBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarProveedorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaproveedor.setModel(modeloproveedor);
        tablaproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproveedorMouseClicked(evt);
            }
        });
        tablaproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaproveedorKeyPressed(evt);
            }
        });
        jScrollPane14.setViewportView(tablaproveedor);

        jPanel41.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarProveedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProveedorActionPerformed(evt);
            }
        });

        SalirProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirProveedor.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCliente.text")); // NOI18N
        SalirProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarProveedor)
                    .addComponent(SalirProveedor))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProveedorLayout = new javax.swing.GroupLayout(BProveedor.getContentPane());
        BProveedor.getContentPane().setLayout(BProveedorLayout);
        BProveedorLayout.setHorizontalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProveedorLayout.setVerticalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        jPanel3.setBackground(new java.awt.Color(4, 200, 230));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setForeground(new java.awt.Color(102, 204, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Código");

        codigoproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        codigoproducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigoproducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codigoproductoFocusGained(evt);
            }
        });
        codigoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoproductoActionPerformed(evt);
            }
        });
        codigoproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoproductoKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Descripción");

        nombreproducto.setBackground(new java.awt.Color(240, 240, 240));
        nombreproducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nombreproducto.setEnabled(false);

        cantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cantidadFocusGained(evt);
            }
        });
        cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Cantidad");

        AgregarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItemActionPerformed(evt);
            }
        });

        BtnProductos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BtnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProductosActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Precio");

        precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precio.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(codigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel1)))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cambiarprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cambiarprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(codigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tabladetalle.setModel(modelo);
        tabladetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabladetalleMouseClicked(evt);
            }
        });
        tabladetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabladetalleKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabladetalle);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBackground(new java.awt.Color(4, 200, 230));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        GuardarPreventa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        GuardarPreventa.setText("Guardar Preventa");
        GuardarPreventa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GuardarPreventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarPreventaActionPerformed(evt);
            }
        });

        SalirCompleto.setBackground(new java.awt.Color(255, 255, 255));
        SalirCompleto.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        SalirCompleto.setText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCompleto.text")); // NOI18N
        SalirCompleto.setToolTipText(org.openide.util.NbBundle.getMessage(preventa_ferremax_sucursal.class, "ventas.SalirCompleto.toolTipText")); // NOI18N
        SalirCompleto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCompletoActionPerformed(evt);
            }
        });

        observacion.setColumns(20);
        observacion.setRows(5);
        jScrollPane1.setViewportView(observacion);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel14.setText("Observación");

        estacompra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        estacompra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        estacompra.setToolTipText("");
        estacompra.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        estacompra.setEnabled(false);

        disponible.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        disponible.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        disponible.setToolTipText("");
        disponible.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        disponible.setEnabled(false);

        limitecredito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        limitecredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        limitecredito.setToolTipText("");
        limitecredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        limitecredito.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(estacompra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(limitecredito, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(disponible))
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(iva, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SalirCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(GuardarPreventa)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GuardarPreventa)
                                    .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(SalirCompleto)
                                    .addComponent(iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(disponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(limitecredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(estacompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(4, 200, 230));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Fecha");

        fecha.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Cliente");

        codcliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codcliente.setEnabled(false);
        codcliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codclienteFocusGained(evt);
            }
        });
        codcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codclienteActionPerformed(evt);
            }
        });
        codcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codclienteKeyPressed(evt);
            }
        });

        buscarcliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buscarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarclienteActionPerformed(evt);
            }
        });

        nombrecliente.setBackground(new java.awt.Color(240, 240, 240));
        nombrecliente.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Tipo Venta");

        vendedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vendedor.setEnabled(false);
        vendedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vendedorFocusGained(evt);
            }
        });
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

        buscarvendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buscarvendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarvendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarvendedorActionPerformed(evt);
            }
        });

        nombrevendedor.setBackground(new java.awt.Color(240, 240, 240));
        nombrevendedor.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Vendedor");

        tipocomprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Venta Contado", "Venta Crédito", "Nota Crédito" }));
        tipocomprobante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tipocomprobanteFocusLost(evt);
            }
        });
        tipocomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipocomprobanteActionPerformed(evt);
            }
        });
        tipocomprobante.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tipocomprobantePropertyChange(evt);
            }
        });

        obra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        obra.setEnabled(false);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Obra");

        buscarobra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buscarobra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarobra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarobraActionPerformed(evt);
            }
        });

        nombreobra.setEnabled(false);

        numero.setEditable(false);
        numero.setEnabled(false);

        iditem.setEditable(false);
        iditem.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(tipocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(obra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarobra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreobra, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buscarvendedor)
                                    .addComponent(buscarcliente))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nombrevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)))))
                .addGap(60, 60, 60))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buscarobra, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(buscarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5)))
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel16)))
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(nombrevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(buscarvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tipocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(obra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31)))
                            .addComponent(nombreobra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(4, 200, 230));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("<F2> Grabar Preventa");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("<F3> Buscar Cliente");

        jLabel25.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel25.setText("<F4> Buscar Vendedor");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("<F5> Nuevo Cliente");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel26.setText("<F6> Modificar Cantidad");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setText("<F8> Buscar Producto");

        jLabel27.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel27.setText("<SUPR> Borrar Línea");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel30.setText("<F7> Cancelar Preventa");

        jLabel51.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel51.setText("<F9> Anticipos");

        jLabel52.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel52.setText("<F10> Gastos");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel25)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel26)
                                .addComponent(jLabel12))
                            .addComponent(jLabel30)
                            .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel52)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel52)))
        );

        jPanel8.setBackground(new java.awt.Color(4, 200, 230));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(204, 51, 0));
        jLabel28.setText("USUARIO ACTIVO");

        EtiquetaUsuario.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        EtiquetaUsuario.setForeground(new java.awt.Color(204, 51, 0));
        EtiquetaUsuario.setText("NOMBREUSUARIO");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel28)
                .addGap(32, 32, 32)
                .addComponent(EtiquetaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(EtiquetaUsuario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(4, 200, 230));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Total");

        descuento.setBackground(new java.awt.Color(240, 240, 240));
        descuento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        descuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        descuento.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        descuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descuentoActionPerformed(evt);
            }
        });

        totalneto.setBackground(new java.awt.Color(240, 240, 240));
        totalneto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        totalneto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalneto.setEnabled(false);
        totalneto.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel33.setText("Dscto");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addContainerGap(113, Short.MAX_VALUE)
                    .addComponent(totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(27, 27, 27))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(81, Short.MAX_VALUE)))
        );

        jMenu1.setText("Opciones");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Configurar Preventa");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Agregar Clientes");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TitRubro() {
        modelorubro.addColumn("Código");
        modelorubro.addColumn("Nombre");

        int[] anchos = {60, 120};
        for (int i = 0; i < modelorubro.getColumnCount(); i++) {
            tablarubro.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablarubro.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablarubro.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablarubro.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablarubro.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitCompr() {
        modelocomprobante.addColumn("Código");
        modelocomprobante.addColumn("Nombre");

        int[] anchos = {80, 210};
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

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

    private void TitProveedor() {
        modeloproveedor.addColumn("Código");
        modeloproveedor.addColumn("Nombre");
        modeloproveedor.addColumn("Ruc");
        modeloproveedor.addColumn("Nro. Timbrado");
        modeloproveedor.addColumn("Vencimiento");

        int[] anchos = {90, 200, 90, 90, 90};
        for (int i = 0; i < modeloproveedor.getColumnCount(); i++) {
            tablaproveedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproveedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproveedor.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaproveedor.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaproveedor.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtroproveedor(int nNumeroColumna) {
        trsfiltroproveedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarProveedor.getText(), nNumeroColumna));
    }

    private void limpiaritems() {

        this.nrofactura.setText("");
        this.fechafactura.setCalendar(c2);
        this.vencimiento.setCalendar(c2);
        this.comprobante.setText("0");
        this.nombrecomprobante.setText("");
        this.rubro.setText("0");
        this.nombrerubro.setText("");
        this.moneda.setText("1");
        this.nombremoneda.setText("GUARANIES");
        this.cotizacion.setText("1");
        this.timbrado.setText("0");
        this.vencimientotimbrado.setCalendar(c2);
        this.proveedor.setText("0");
        this.nombreproveedor.setText("");
        this.exentas.setText("0");
        this.gravadas10.setText("0");
        this.iva10.setText("0");
        this.gravadas5.setText("0");
        this.iva5.setText("0");
        this.Totalneto.setText("0");
        this.observacion.setText("Observaciones de ");
    }

    private void ActualizarDetalle() {
        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        detalle_preventaDAO dtDAO = new detalle_preventaDAO();
        try {
            for (detalle_preventa dt : dtDAO.MostrarDetalle(Integer.valueOf(this.numero.getText()))) {
                String Detalle[] = {dt.getCodprod().getCodigo(),
                    dt.getCodprod().getNombre(),
                    formatcantidad.format(dt.getCantidad()),
                    formatea.format(dt.getPrecio()),
                    formatea.format(dt.getMonto()),
                    formatosinpunto.format(dt.getIditem())};
                modelo.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        int cantFilas = tabladetalle.getRowCount();
        if (cantFilas > 0) {
            GuardarPreventa.setEnabled(true);
        } else {
            GuardarPreventa.setEnabled(false);
        }
        this.sumatoria();
    }

    private void TitStock() {
        modelostock.addColumn("Sucursal");
        modelostock.addColumn("Stock");

        int[] anchos = {200, 90};
        for (int i = 0; i < modelostock.getColumnCount(); i++) {
            tablastock.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablastock.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablastock.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablastock.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablastock.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
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

    private void TituloUbicacion() {
        modeloproductoubicacion.addColumn("Código");
        modeloproductoubicacion.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloproductoubicacion.getColumnCount(); i++) {
            tablaproductoubicacion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproductoubicacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproductoubicacion.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaproductoubicacion.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaproductoubicacion.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

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

    public void filtroproducto(int nNumeroColumna) {
        trsfiltroproducto.setRowFilter(RowFilter.regexFilter(textoproducto.getText(), nNumeroColumna));
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

    private void limpiarproductos() {
        Config.cCodProducto = "";
        Config.cNombreProducto = "";
        Config.nPrecio = 0;
        Config.nIvaProducto = 0;
    }

    private void limpiarClientes() {
        this.codigo.setText("0");
        this.ruc.setText("");
        this.nombre.setText("");
        this.direccion.setText("");
        this.celular.setText("");
        this.nacimiento.setCalendar(c2);
        this.fechaingreso.setCalendar(c2);
        this.control.setText("0");
        habilitado.setSelected(true);
    }

    public void sumatoria() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA

        double sumtotal = 0.00;
        double sumatoria = 0.00;
        String cValorImporte = "";
        int nIva = 0;
        int totalRow = this.tabladetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //Primero capturamos el porcentaje del IVA
            //Luego capturamos el importe de la celda total del item
            cValorImporte = String.valueOf(this.tabladetalle.getValueAt(i, 4));
            cValorImporte = cValorImporte.replace(".", "");
            cValorImporte = cValorImporte.replace(",", ".");
            //Calculamos el total
            sumtotal += Double.valueOf(cValorImporte);
            //Calculamos el total de exentos
        }
        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalneto.setText(formatea.format(sumtotal));
        this.estacompra.setText(formatea.format(sumtotal));
        if (this.tipocomprobante.getSelectedIndex() == 1) {
            VerificarSaldo();
        }
        //formato.format(sumatoria1);
    }

    private void VerificarSaldo() {
        String cEstaCompra = this.estacompra.getText();
        cEstaCompra = cEstaCompra.replace(".", "");
        String cDisponible = this.disponible.getText();
        cDisponible = cDisponible.replace(".", "");
        if (Double.valueOf(cEstaCompra) > Double.valueOf(cDisponible)) {
            JOptionPane.showMessageDialog(null, "El Límite de Crédito ha Excedido");
            this.codigoproducto.requestFocus();
            return;
        }
    }

    private void TituloClientes() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre del Cliente");
        modelocliente.addColumn("RUC");
        modelocliente.addColumn("Dirección");
        int[] anchos = {100, 200, 100, 150};
        for (int i = 0; i < modelocliente.getColumnCount(); i++) {
            tablacliente.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacliente.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacliente.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacliente.setFont(font);
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(textoproducto.getText(), nNumeroColumna));
    }

    public void filtrocliente(int nNumeroColumnacliente) {
        trsfiltrocliente.setRowFilter(RowFilter.regexFilter(jTBuscarClientes.getText(), nNumeroColumnacliente));
    }

    public void filtrolocalidad(int nNumeroColumnacliente) {
        trsfiltrolocalidad.setRowFilter(RowFilter.regexFilter(jTBuscarLocalidad.getText(), nNumeroColumnacliente));
    }

    private void TituloProductos() {
        modeloproducto.addColumn("Código");
        modeloproducto.addColumn("Descripción");
        modeloproducto.addColumn("Mayorista");
        modeloproducto.addColumn("Minorista");
        modeloproducto.addColumn("Stock");
        modeloproducto.addColumn("IVA");
        modeloproducto.addColumn("CambiarPrecio");
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        int[] anchos = {90, 400, 110, 110, 80, 50, 1};
        for (int i = 0; i < modeloproducto.getColumnCount(); i++) {
            tablaproducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproducto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproducto.getTableHeader().setFont(new Font("Arial Black", 1, 11));
        this.tablaproducto.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaproducto.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablaproducto.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);

        this.tablaproducto.getColumnModel().getColumn(6).setMaxWidth(0);
        this.tablaproducto.getColumnModel().getColumn(6).setMinWidth(0);
        this.tablaproducto.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        this.tablaproducto.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaproducto.setFont(font);
    }

    private void TituloPOS() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Total");
        modelo.addColumn("item");
        int[] anchos = {60, 200, 100, 100, 100, 10};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tabladetalle.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalle.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladetalle.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);

        this.tabladetalle.getColumnModel().getColumn(5).setMaxWidth(0);
        this.tabladetalle.getColumnModel().getColumn(5).setMinWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
        tabladetalle.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 10);
        tabladetalle.setFont(font);
    }


    private void AgregarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarItemActionPerformed
        if (this.codigoproducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código del Producto");
            this.codigoproducto.requestFocus();
            return;
        }

        String cProducto = this.codigoproducto.getText().toString();

        String cPrecio = this.precio.getText();
        //  cPrecio = cPrecio.replace(".", "").replace(",", ".");
        String cIva = this.iva.getText();
        String cValorCantidad = this.cantidad.getText();
        cValorCantidad = cValorCantidad.replace(".", "").replace(",", ".");

        double ncantidad = Double.valueOf(cValorCantidad);
        if ((tipocomprobante.getSelectedIndex() + 1) == 3) {
            ncantidad = ncantidad * -1;
        }

        Object[] fila = new Object[5];
        fila[0] = this.codigoproducto.getText().toString();
        fila[1] = this.nombreproducto.getText().toString();
        fila[2] = formatcantidad.format(ncantidad);
        fila[3] = formatea.format(Double.valueOf(cPrecio));
        double nSubtotal = (ncantidad * Double.valueOf(cPrecio));
        fila[4] = formatea.format(nSubtotal);
        modelo.addRow(fila);
        this.sumatoria();
        this.codigoproducto.setText("");
        this.precio.setText("0");
        this.nombreproducto.setText("");
        this.cantidad.setText("1");
        this.cambiarprecio.setText("0");
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarItemActionPerformed

    private void buscarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarclienteActionPerformed
        tipocomprobante.setSelectedIndex(0);
        limitecredito.setText("0");
        disponible.setText("0");
        estacompra.setText("0");
        nBusqueda = 1;
        BuscarClientes.setTitle("Buscar Cliente");
        BuscarClientes.setModal(true);
        BuscarClientes.setSize(499, 535);
        BuscarClientes.setLocationRelativeTo(null);
        BuscarClientes.setVisible(true);
        codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarclienteActionPerformed

    private void codclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codclienteActionPerformed
        this.buscarcliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codclienteActionPerformed

    private void comboclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclientesActionPerformed

    private void jTBuscarClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarClientesKeyPressed
        this.jTBuscarClientes.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarClientes.getText()).toUpperCase();
                jTBuscarClientes.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboclientes.getSelectedIndex()) {
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
                filtrocliente(indiceColumnaTabla);
            }
        });
        trsfiltrocliente = new TableRowSorter(tablacliente.getModel());
        tablacliente.setRowSorter(trsfiltrocliente);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClientesKeyPressed

    private void tablaclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptarcliente.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteKeyPressed

    private void AceptarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarclienteActionPerformed
        nFila = this.tablacliente.getSelectedRow();
        if (nBusqueda == 1) {
            this.codcliente.setText(this.tablacliente.getValueAt(nFila, 0).toString());
            this.nombrecliente.setText(this.tablacliente.getValueAt(nFila, 1).toString());
            this.obra.setText("0");
            this.nombreobra.setText("");
            this.BuscarClientes.setVisible(false);
            this.jTBuscarClientes.setText("");
            this.codigoproducto.requestFocus();
        } else if (nBusqueda == 2) {
            clienteDAO clDAO = new clienteDAO();
            cliente cl = null;
            int nestado = 0;
            try {
                cl = clDAO.buscarId(Integer.valueOf(this.tablacliente.getValueAt(nFila, 0).toString()));
                if (cl.getCodigo() != 0) {
                    control.setText("1");
                    nestado = cl.getEstado();
                    if (nestado == 1) {
                        habilitado.setSelected(true);
                    } else {
                        habilitado.setSelected(false);
                    }
                    codigo.setText(String.valueOf(cl.getCodigo()));
                    nombre.setText(cl.getNombre());
                    celular.setText(cl.getTelefono());
                    fechaingreso.setDate(cl.getFechaingreso());
                    nacimiento.setDate(cl.getFechanacimiento());
                    ruc.setText(cl.getRuc());
                    direccion.setText(cl.getDireccion());
                    celular.setText(cl.getTelefono());
                }

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            nombre.requestFocus();
        } else {
            this.cliente1.setText(this.tablacliente.getValueAt(nFila, 0).toString());
            this.nombrecliente1.setText(this.tablacliente.getValueAt(nFila, 1).toString());
            this.BuscarClientes.setVisible(false);
            this.jTBuscarClientes.setText("");
            this.importe.requestFocus();
        }// TODO add your handling code here:
        SalirCliente.doClick();
    }//GEN-LAST:event_AceptarclienteActionPerformed

    public void limpiarOrden() {
        this.fecha1.setCalendar(c2);
        this.fechaalta.setCalendar(c2);
        this.primervence.setCalendar(c2);
        this.cliente1.setText("0");
        this.nombrecliente1.setText("");
        this.importe.setText("0");
        this.montocuota.setText("0");
        this.plazo.setText("0");
    }


    private void SalirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirClienteActionPerformed
        this.BuscarClientes.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirClienteActionPerformed

    private void BtnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProductosActionPerformed
        /*      GrillaProductos grilla1 = new GrillaProductos();
        Thread hiloproductos = new Thread(grilla1);
        hiloproductos.start();*/

 /*        GrillaProd grilla1 = new GrillaProd();
        Thread hiloproductos = new Thread(grilla1);
        hiloproductos.start();*/
        int cantidadRegistro = modeloproducto.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloproducto.removeRow(0);
        }
        BProducto.setModal(true);
        BProducto.setSize(716, 575);
        BProducto.setLocationRelativeTo(null);
        BProducto.setVisible(true);
        codigoproducto.requestFocus();
        BProducto.setModal(false);

    }//GEN-LAST:event_BtnProductosActionPerformed

    private void codigoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoproductoActionPerformed
        if (this.codigoproducto.getText().length() > 0) {

            int nPosicionPrecio = codigoproducto.getText().indexOf("$"); //PARA CAPTURAR PRECIO
            int nPosicionCantidad = codigoproducto.getText().indexOf("*");//PARA CAPTURAR CANTIDAD
            int nPosicionPeso = codigoproducto.getText().indexOf(",");//PARA CAPTURAR PESO

            //CAPTURAR PRECIO POR TECLADO
            if (nPosicionPrecio >= 0) {
                String cPrecio = codigoproducto.getText().substring(1, codigoproducto.getText().length());
                precio.setText(cPrecio);
                codigoproducto.setText("");
                codigoproducto.requestFocus();
                return;
            }

            //PARA CAPTURAR CANTIDAD POR TECLADO
            if (nPosicionCantidad >= 0) {
                Double nCantidad = Double.valueOf(codigoproducto.getText().substring(1, codigoproducto.getText().length()));
                cantidad.setText(formatcantidad.format(nCantidad));
                codigoproducto.setText("");
                codigoproducto.requestFocus();
                return;
            }

            //PARA CAPTURAR PESO POR TECLADO
            if (nPosicionPeso > 0) {
                String cCantidad = codigoproducto.getText();
                cCantidad = cCantidad.replace(",", ".");
                Double nCantidad = Double.valueOf(cCantidad);
                cantidad.setText(formatcantidad.format(nCantidad));
                codigoproducto.setText("");
                codigoproducto.requestFocus();
                return;
            }

        }

        if (Config.cCodProducto == "") {;
            productoDAO producto = new productoDAO();
            producto p = null;
            try {
                p = producto.BuscarProductoBasico(this.codigoproducto.getText());
                if (p.getCodigo() == null) {
                    codigoproducto.setText("");
                    this.BtnProductos.doClick();
                } else {
                    this.cambiarprecio.setText(formatea.format(p.getCambiarprecio()));
                    double nPrecio = p.getPrecio_maximo().doubleValue();
                    if (Integer.valueOf(this.cambiarprecio.getText()) == 1) {
                        if (Double.valueOf(precio.getText()) == 0) {
                            precio.setText("");
                            precio.setText(String.valueOf(nPrecio));
                        }
                    } else {
                        precio.setText("");
                        precio.setText(String.valueOf(nPrecio));
                    }
                    this.nombreproducto.setText(p.getNombre());
                    this.iva.setText(String.valueOf(p.getIvaporcentaje()));
                    //Establecemos un título para el jDialog
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        nFila = tabladetalle.getRowCount() + 1;
        if (nTipoImpresora == 1) {
            if (nFila <= nItemProductos) {
                this.AgregarItem.doClick();
            } else {
                JOptionPane.showMessageDialog(null, "Los Items de Productos están Limitados a " + nItemProductos);
                this.codigoproducto.requestFocus();
                return;
            }
        } else {
            this.AgregarItem.doClick();
        }
        this.limpiarproductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoproductoActionPerformed

    private void tabladetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalleMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalleMouseClicked

    private void cantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadFocusGained
        cantidad.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadFocusGained

    private void cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadActionPerformed
        this.AgregarItem.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadActionPerformed

    private void codclienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codclienteFocusGained
        this.codcliente.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_codclienteFocusGained

    private void codigoproductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoproductoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoproductoFocusGained

    private void combolocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combolocalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combolocalidadActionPerformed

    private void jTBuscarLocalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarLocalidadKeyPressed
        this.jTBuscarLocalidad.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarLocalidad.getText()).toUpperCase();
                jTBuscarLocalidad.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combolocalidad.getSelectedIndex()) {
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
                filtrolocalidad(indiceColumnaTabla);
            }
        });
        trsfiltrolocalidad = new TableRowSorter(tablalocalidad.getModel());
        tablalocalidad.setRowSorter(trsfiltrolocalidad);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarLocalidadKeyPressed

    private void tablalocalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablalocalidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptarcliente1.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablalocalidadKeyPressed

    private void Aceptarcliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aceptarcliente1ActionPerformed
        int nFila = this.tablalocalidad.getSelectedRow();
        this.BuscarLocalidad.setVisible(false);
        this.jTBuscarLocalidad.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_Aceptarcliente1ActionPerformed

    private void SalirCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliente1ActionPerformed
        this.BuscarLocalidad.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliente1ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        configurar.setSize(430, 280);
        configurar.setLocationRelativeTo(null);
        configurar.setVisible(true);
        configurar.setModal(true);
        sucursal.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.limpiarClientes();
        this.RegistrarCliente.setSize(498, 390);
        RegistrarCliente.setLocationRelativeTo(null);
        //Cargo el Vendedor en el punto de Vent
        RegistrarCliente.setModal(true);
        RegistrarCliente.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void combovendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combovendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combovendedorActionPerformed

    private void jTBuscarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorActionPerformed

    public void filtrovendedor(int nNumeroColumna) {
        trsfiltrovendedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarVendedor.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }


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
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

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
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void BuscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarSucursalActionPerformed
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
            } else {
                nombresucursal.setText(sucu.getNombre());
            }
            vendedor.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarSucursalActionPerformed

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.BuscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        configurar.setModal(false);
        configurar.setVisible(false);
        codcliente.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        configurar.setModal(false);
        configurar.setVisible(false);
        codcliente.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost

        if (Double.valueOf(this.codigo.getText()) != 0) {
            CalcularRuc rucDigito = new CalcularRuc();
            String cCodigo = this.codigo.getText();
            int base = 11;
            int digito = rucDigito.CalcularDigito(cCodigo, base);
            ruc.setText(cCodigo.toString() + '-' + String.valueOf(digito));
        }
        this.BuscarCliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoFocusLost

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoActionPerformed

    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.BuscarCliente.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyPressed

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        int nestado = 0;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codigo.getText()));
            if (cl.getCodigo() != 0) {
                control.setText("1");
                nestado = cl.getEstado();
                if (nestado == 1) {
                    habilitado.setSelected(true);
                } else {
                    habilitado.setSelected(false);
                }
                nombre.setText(cl.getNombre());
                celular.setText(cl.getTelefono());
                fechaingreso.setDate(cl.getFechaingreso());
                nacimiento.setDate(cl.getFechanacimiento());
                ruc.setText(cl.getRuc());
                direccion.setText(cl.getDireccion());
                celular.setText(cl.getTelefono());
                //Establecemos un título para el jDialog
            } else {
                nBusqueda = 2;
                BuscarClientes.setTitle("Buscar Cliente");
                BuscarClientes.setModal(true);
                BuscarClientes.setSize(499, 535);
                BuscarClientes.setLocationRelativeTo(null);
                BuscarClientes.setVisible(true);
                codigoproducto.requestFocus();

            }
            nombre.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.direccion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codigo.requestFocus();
        }                // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyPressed

    private void nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyReleased

    private void fechaingresoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaingresoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaingresoFocusGained

    private void fechaingresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaingresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nacimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombre.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_fechaingresoKeyPressed

    private void nacimientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nacimientoFocusGained

        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoFocusGained

    private void nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ruc.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechaingreso.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoKeyPressed

    private void rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.celular.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nacimiento.requestFocus();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_rucKeyPressed

    private void celularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_celularKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarCliente.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ruc.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_celularKeyPressed

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechaingreso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombre.requestFocus();
        }                // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionKeyPressed

    private void direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionKeyReleased

    private void GrabarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarClienteActionPerformed
        if (this.codigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nro. de Cédula");
            this.codigo.requestFocus();
            return;
        }
        if (this.nombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nombre del Cliente");
            this.nombre.requestFocus();
            return;
        }
        if (this.ruc.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de CI Cliente");
            this.ruc.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Cliente? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            Date dFechaCumple = ODate.de_java_a_sql(nacimiento.getDate());
            Date dFechaIngreso = ODate.de_java_a_sql(fechaingreso.getDate());
            clienteDAO grabarcliente = new clienteDAO();
            localidadDAO local = new localidadDAO();
            localidad loc = null;
            cliente c = new cliente();
            c.setCodigo(Integer.valueOf(codigo.getText()));
            c.setNombre(nombre.getText());
            c.setDireccion(direccion.getText());
            c.setEstado(1);
            try {
                loc = local.buscarLocalidad(1);
                c.setLocalidad(loc);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            c.setRuc(ruc.getText());
            c.setCedula(ruc.getText());
            c.setTelefono(celular.getText());
            c.setFechanacimiento(dFechaCumple);
            c.setFechaingreso(dFechaIngreso);
            c.setDireccion(direccion.getText());
            if (Integer.valueOf(control.getText()) == 0) {
                // Agregar nuevo Cliente
                if (codigo.getText().compareTo("") != 0 && nombre.getText().compareTo("") != 0
                        && ruc.getText().compareTo("") != 0) {
                    try {
                        grabarcliente.insertarCliente(c);
                        try {
                            Socket s = new Socket(dirWeb, 3306);
                            if (s.isConnected()) {
                                System.out.println("Conexión establecida con la dirección: " + dirWeb + " a travéz del puerto: 3306");
                                new GrabarClienteVpn(c, 1).start();
                            }
                        } catch (Exception e) {
                            System.err.println("No se pudo establecer conexión con: " + dirWeb + " a travez del puerto: 3306");
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Algunos Datos son Necesarios");
                }
            } else {
                //Actualizar Datos de Clientes
                if (codigo.getText().compareTo("") != 0 && nombre.getText().compareTo("") != 0
                        && ruc.getText().compareTo("") != 0) {
                    try {
                        grabarcliente.actualizarCliente(c);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Algunos Datos son Necesarios");
                }
            }
        }
        this.codcliente.setText(codigo.getText());
        this.nombrecliente.setText(nombre.getText());
        GrillaClientes grilla2 = new GrillaClientes();
        Thread hiloclientes = new Thread(grilla2);
        hiloclientes.start();
        this.limpiarClientes();
        this.SalirCargaCliente.doClick();

        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarClienteActionPerformed

    private void SalirCargaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCargaClienteActionPerformed
        this.limpiarClientes();
        RegistrarCliente.dispose();
        codigoproducto.requestFocus();
    }//GEN-LAST:event_SalirCargaClienteActionPerformed

    private void comboproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproductoActionPerformed

    private void textoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoActionPerformed

    private void textoproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoproductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.tablaproducto.requestFocus();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            if (comboproducto.getSelectedIndex() == 0) {
                comboproducto.setSelectedIndex(1);
            } else {
                comboproducto.setSelectedIndex(0);
            }
            switch (comboproducto.getSelectedIndex()) {
                case 0:
                    indiceTabla = 1;
                    break;//por nombre
                case 1:
                    indiceTabla = 0;
                    break;//por codigo
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoKeyPressed

    private void ConsultaProducto() {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;

        int cantidadRegistro = modeloproducto.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloproducto.removeRow(0);
        }
        try {
            String sql = "SELECT productos.codigo,productos.nombre,productos.costo,productos.precio_maximo,productos.precioventa,"
                    + "productos.precio_minimo,stock.stock,"
                    + "productos.ivaporcentaje,productos.estado,productos.cambiarprecio "
                    + " FROM productos "
                    + " LEFT JOIN stock "
                    + " ON stock.producto=productos.codigo ";
            if (comboproducto.getSelectedIndex() == 0) {
                sql = sql + " WHERE nombre LIKE '%" + textoproducto.getText() + "%'";
            } else {
                sql = sql + " WHERE codigo LIKE '%" + textoproducto.getText() + "%'";
            }
            sql = sql + " AND productos.estado=1 "
                    + " AND stock.sucursal= " + Integer.valueOf(sucursal.getText())
                    + " ORDER BY productos.codigo ";
            System.out.println(sql);

            results = stm.executeQuery(sql);
            while (results.next()) {
                // Se crea un array que será una de las filas de la tabla.
                Object[] fila = new Object[7]; // Hay 8 columnas en la tabla
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                fila[0] = results.getString("codigo");
                fila[1] = results.getString("nombre");
                fila[2] = formatea.format(results.getDouble("precio_minimo"));
                fila[3] = formatea.format(results.getDouble("precio_maximo"));
                fila[4] = formatea.format(results.getDouble("stock"));
                fila[5] = formatea.format(results.getDouble("ivaporcentaje"));
                fila[6] = formatea.format(results.getInt("cambiarprecio"));

                // Se añade al modelo la fila completa.
                modeloproducto.addRow(fila);
            }
            tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
            tablaproducto.updateUI();
            results.close();
            stm.close();
        } catch (SQLException ex2) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos de Productos ",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex2) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Conectarse a la Base de Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        }
        FormatoTablaStock ft = new FormatoTablaStock(3, 4);
        tablaproducto.setDefaultRenderer(Object.class, ft);

        /* BProducto.setModal(true);
        BProducto.setSize(716, 575);
        BProducto.setLocationRelativeTo(null);
        BProducto.setVisible(true);
        codigoproducto.requestFocus();
        BProducto.setModal(false);*/
    }

    private void FiltroPro() {
        trsfiltroproducto = new TableRowSorter(tablaproducto.getModel());
        tablaproducto.setRowSorter(trsfiltroproducto);
        trsfiltroproducto.setRowFilter(RowFilter.regexFilter(textoproducto.getText().toUpperCase(), indiceTabla));
    }

    private void tablaproductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproductoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoMouseClicked

    private void tablaproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarProducto.doClick();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            int nFila = tablaproducto.getSelectedRow();
            String cProducto = tablaproducto.getValueAt(nFila, 0).toString();
            String cNombre = tablaproducto.getValueAt(nFila, 1).toString();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproducto.requestFocus();
                return;
            }

            albumfoto_producto al = null;
            albumfoto_productoDAO imgDAO = new albumfoto_productoDAO();

            try {
                al = imgDAO.buscaId(cProducto);
                if (al.getNombre() != null) {
                    fotoProducto.setImagen(al.getImagen());
                } else {
                    fotoProducto.setImagen(imagenfondo.getImage());
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            etiquetanombreproducto.setText(cProducto + "-" + cNombre);
            panelfoto.setModal(true);
            panelfoto.setSize(580, 460);
            //Establecemos un título para el jDialog
            panelfoto.setTitle("Imágen del Producto");
            panelfoto.setLocationRelativeTo(null);
            panelfoto.setVisible(true);
            SalirFoto.requestFocus();

        }

        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            int nFila = tablaproducto.getSelectedRow();
            String cProducto = tablaproducto.getValueAt(nFila, 0).toString();
            String cNombre = tablaproducto.getValueAt(nFila, 1).toString();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproducto.requestFocus();
                return;
            }

            int cantidadRegistro = modelostock.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelostock.removeRow(0);
            }
            new BuscaProductoVpn(cProducto).start();

            tablastock.setRowSorter(new TableRowSorter(modelostock));
            int cantFilas = tablastock.getRowCount();

            etiquetacodigo.setText(cProducto);
            etiquetanombre.setText(cNombre);
            inventario.setModal(true);
            inventario.setSize(470, 375);
            //Establecemos un título para el jDialog
            inventario.setLocationRelativeTo(null);
            inventario.setVisible(true);
            SalirStockSucursal.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            int nFila = tablaproducto.getSelectedRow();
            String cProducto = tablaproducto.getValueAt(nFila, 0).toString();
            String cNombre = tablaproducto.getValueAt(nFila, 1).toString();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproducto.requestFocus();
                return;
            }

            int cantidadRegistro = modeloproductoubicacion.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproductoubicacion.removeRow(0);
            }

            productoDAO stDAO = new productoDAO();
            try {
                for (producto st : stDAO.BuscarProductoUbicacion(cProducto)) {
                    String Datos[] = {formatosinpunto.format(st.getUbicacion().getCodigo()),
                        st.getUbicacion().getNombre()};
                    System.out.println(st.getUbicacion().getNombre());
                    System.out.println(st.getNombre());
                    modeloproductoubicacion.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproductoubicacion.setRowSorter(new TableRowSorter(modeloproductoubicacion));
            int cantFilas = tablaproductoubicacion.getRowCount();

            etiquetacodigoubicacion.setText(cProducto);
            etiquetanombreubicacion.setText(cNombre);
            ubicacion.setModal(true);
            ubicacion.setSize(470, 375);
            //Establecemos un título para el jDialog
            ubicacion.setLocationRelativeTo(null);
            ubicacion.setVisible(true);
            SalirUbicacion.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoKeyPressed

    private void AceptarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProductoActionPerformed
        int nFila = this.tablaproducto.getSelectedRow();
        this.codigoproducto.setText(this.tablaproducto.getValueAt(nFila, 0).toString());
        this.nombreproducto.setText(this.tablaproducto.getValueAt(nFila, 1).toString());
        this.cambiarprecio.setText(this.tablaproducto.getValueAt(nFila, 6).toString());

        if (Integer.valueOf(this.cambiarprecio.getText()) == 1) {
            if (Double.valueOf(this.precio.getText()) == 0) {
                String cPrecio = this.tablaproducto.getValueAt(nFila, 3).toString();
                cPrecio = cPrecio.replace(".", "");
                this.precio.setText(cPrecio);
            }
        } else {
            String cPrecio = this.tablaproducto.getValueAt(nFila, 3).toString();
            cPrecio = cPrecio.replace(".", "");
            this.precio.setText(cPrecio);
        }

        this.iva.setText(this.tablaproducto.getValueAt(nFila, 6).toString());
        this.BProducto.dispose();
        this.textoproducto.setText("");
        Config.cCodProducto = this.tablaproducto.getValueAt(nFila, 0).toString();
        this.codigoproducto.requestFocus();
        this.textoproducto.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProductoActionPerformed

    private void SalirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProductoActionPerformed
        this.BProducto.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProductoActionPerformed

    private void codigoproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoproductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            this.GuardarPreventa.doClick();
        }

        //Pulsamos el Botón F3 para Buscar Clientes
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            buscarcliente.doClick();
        }

        //PULSAMOS F4 PARA BUSCAR VENDEDOR
        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            buscarvendedor.doClick();
        }
        //PULSAMOS F5 PARA AGREGAR NUEVO CLIENTE

        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            this.limpiarClientes();
            this.RegistrarCliente.setSize(498, 390);
            RegistrarCliente.setLocationRelativeTo(null);
            //Cargo el Vendedor en el punto de Vent
            RegistrarCliente.setModal(true);
            RegistrarCliente.setVisible(true);
        }

        if (evt.getKeyCode() == KeyEvent.VK_F7) {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Cancelar la Operación? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                this.inicializar();
                this.sumatoria();
                JOptionPane.showMessageDialog(null, "Venta Cancelada");
            }

        }
        //PULSAMOS F8 PARA BUSCAR PRODUCTOS
        if (evt.getKeyCode() == KeyEvent.VK_F8) {
            this.BtnProductos.doClick();
        }

        //PULSAMOS F8 PARA BUSCAR PRODUCTOS
        if (evt.getKeyCode() == KeyEvent.VK_F9) {
            this.limpiarOrden();
            detalle_orden_compra.setSize(448, 345);
            detalle_orden_compra.setLocationRelativeTo(null);
            //Cargo el Vendedor en el punto de Vent
            detalle_orden_compra.setModal(true);
            detalle_orden_compra.setVisible(true);
            detalle_orden_compra.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            this.limpiaritems();
            itemgastos.setSize(520, 410);
            itemgastos.setLocationRelativeTo(null);
            this.GrabarItem.setText("Agregar");
            itemgastos.setModal(true);
            itemgastos.setVisible(true);
            nrofactura.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoproductoKeyPressed

    private void BProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BProductoFocusGained
        this.limpiarproductos();
        textoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BProductoFocusGained

    private void codclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codclienteKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            buscarcliente.doClick();
        }
        //PULSAMOS F5 PARA AGREGAR NUEVO CLIENTE
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            this.limpiarClientes();
            this.RegistrarCliente.setSize(498, 390);
            RegistrarCliente.setLocationRelativeTo(null);
            //Cargo el Vendedor en el punto de Vent
            RegistrarCliente.setModal(true);
            RegistrarCliente.setVisible(true);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_codclienteKeyPressed

    private void vendedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vendedorFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorFocusGained

    private void vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorActionPerformed

    private void vendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vendedorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorKeyPressed

    private void buscarvendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarvendedorActionPerformed
        BVendedor.setTitle("Buscar Vendedor");
        BVendedor.setModal(true);
        BVendedor.setSize(500, 575);
        BVendedor.setLocationRelativeTo(null);
        BVendedor.setVisible(true);
        BVendedor.setModal(false);
        codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarvendedorActionPerformed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.Aceptarcliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void BProductoWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_BProductoWindowGainedFocus
        textoproducto.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_BProductoWindowGainedFocus

    private void SalirCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCantidadActionPerformed
        EditarCantidad.dispose();
        codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCantidadActionPerformed

    private void EditarCantidadWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_EditarCantidadWindowGainedFocus
        cantidadeditar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_EditarCantidadWindowGainedFocus

    private void cantidadeditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadeditarFocusGained
        cantidadeditar.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadeditarFocusGained

    private void tabladetalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabladetalleKeyPressed
        //PULSAMOS F6 PARA EDITAR CANTIDAD
        if (evt.getKeyCode() == KeyEvent.VK_F6) {
            nFila = this.tabladetalle.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            codigoeditar.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 0).toString());
            nombreeditar.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 1).toString());
            cantidadeditar.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 2).toString());
            precioeditar.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 3).toString());
            cantidadeditar.requestFocus();
            EditarCantidad.setModal(true);
            EditarCantidad.setSize(415, 250);
            EditarCantidad.setLocationRelativeTo(null);
            EditarCantidad.setVisible(true);
        }

        //PULSAMOS DELETE PARA BORRAR ITEM
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            int a = this.tabladetalle.getSelectedRow();

            if (a < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
            } else {
                int confirmar = JOptionPane.showConfirmDialog(null,
                        "Esta seguro que desea Eliminar el registro? ");
                if (JOptionPane.OK_OPTION == confirmar) {
                    modelo.removeRow(a);
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    this.sumatoria();
                }
            }
        }
    }//GEN-LAST:event_tabladetalleKeyPressed

    private void UpdateCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateCantidadActionPerformed
        String cCantidad = this.cantidadeditar.getText();
        cCantidad = cCantidad.replace(",", ".");
        String cPrecio = this.precioeditar.getText();
        cPrecio = cPrecio.replace(".", "");

        detalle_preventa dp = new detalle_preventa();
        detalle_preventaDAO dpDAO = new detalle_preventaDAO();
        productoDAO producto = new productoDAO();
        producto p = null;

        String cProducto = this.codigoeditar.getText().toString();

        double ncantidad = Double.valueOf(cCantidad);
        if ((tipocomprobante.getSelectedIndex() + 1) == 3) {
            ncantidad = ncantidad * -1;
        }
        try {
            p = producto.BuscarProductoBasico(cProducto);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        dp.setIddetalle(Integer.valueOf(numero.getText()));
        dp.setIditem(Double.valueOf(iditem.getText()));
        dp.setCodprod(p);
        dp.setPrcosto(p.getCosto().doubleValue());
        dp.setPorcentaje(p.getIvaporcentaje().doubleValue());
        dp.setCantidad(ncantidad);
        dp.setPrecio(Double.valueOf(cPrecio));
        dp.setMonto(Math.round(Double.valueOf(cPrecio) * ncantidad));
        dp.setComentario("Preventa");
        dp.setImpiva(Math.round((Double.valueOf(cPrecio) * ncantidad)) / 11);

        try {
            dpDAO.actualizarItem(dp);
            this.ActualizarDetalle();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println("--->" + ex.getLocalizedMessage());
        }
        this.codigoproducto.setText("");
        this.precio.setText("0");
        this.nombreproducto.setText("");
        this.cantidad.setText("1");
        this.cambiarprecio.setText("0");
        this.codigoproducto.requestFocus();
        this.SalirCantidad.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateCantidadActionPerformed

    private void cantidadeditarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadeditarKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.UpdateCantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadeditarKeyPressed

    private void UpdateCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UpdateCantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String cCantidad = this.cantidadeditar.getText();
            cCantidad = cCantidad.replace(",", ".");
            String cPrecio = this.precioeditar.getText();
            cPrecio = cPrecio.replace(".", "");
            this.tabladetalle.setValueAt(this.cantidadeditar.getText(), nFila, 2);
            this.tabladetalle.setValueAt(formatea.format(Double.valueOf(cCantidad) * Double.valueOf(cPrecio)), nFila, 4);
            this.sumatoria();
            this.SalirCantidad.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateCantidadKeyPressed

    private void textoproductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoproductoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoKeyTyped

    private void textoproductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textoproductoFocusGained
        switch (comboproducto.getSelectedIndex()) {
            case 0:
                indiceTabla = 1;
                break;//por nombre
            case 1:
                indiceTabla = 0;
                break;//por codigo
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoFocusGained

    private void textoproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoproductoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.tablaproducto.requestFocus();
        }

        if (textoproducto.getText().length() > 2) {
            this.ConsultaProducto();
        } else {
            int cantidadRegistro = modeloproducto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproducto.removeRow(0);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoKeyReleased

    private void SalirFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFotoActionPerformed
        panelfoto.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFotoActionPerformed

    private void buscarobraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarobraActionPerformed
        GrillaObra grillab = new GrillaObra();
        Thread hilobanco = new Thread(grillab);
        hilobanco.start();
        BBancos.setModal(true);
        BBancos.setSize(482, 575);
        BBancos.setLocationRelativeTo(null);
        BBancos.setVisible(true);
        BBancos.setModal(true);        // TODO add your handling code here:
    }//GEN-LAST:event_buscarobraActionPerformed

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

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.jTBuscarbanco.getText(), nNumeroColumna));
    }

    private void tablabancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.Aceptar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.obra.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombreobra.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.BBancos.setVisible(false);
        this.jTBuscarbanco.setText("");
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarActionPerformed

    private void SalirBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirBancoActionPerformed
        this.BBancos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirBancoActionPerformed

    private void descuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descuentoActionPerformed

        int totalRow = this.tabladetalle.getRowCount();
        totalRow -= 1;
        int pos = -1;
        String cCod = "";
        for (int i = 0; i <= (totalRow); i++) {
            //Primero capturamos el porcentaje del IVA
            //Luego capturamos el importe de la celda total del item
            cCod = String.valueOf(this.tabladetalle.getValueAt(i, 0));
            if (cCod.equals("DC0001")) {
                pos = i;
            }
        }
        if (pos >= 0) {
            modelo.removeRow(pos);
            this.sumatoria();
        }
        String cProducto = "DC0001";

        String cPrecio = this.descuento.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");

        if (Double.valueOf(cPrecio) == 0) {
            return;
        }
        String cIva = "10";
        String cValorCantidad = "-1";

        double ncantidad = Double.valueOf(cValorCantidad);

        Object[] fila = new Object[5];
        fila[0] = cProducto;
        fila[1] = "DESCUENTOS CONCEDIDOS";
        fila[2] = formatcantidad.format(ncantidad);
        fila[3] = formatea.format(Double.valueOf(cPrecio));
        double nSubtotal = (ncantidad * Double.valueOf(cPrecio));
        fila[4] = formatea.format(nSubtotal);

        modelo.addRow(fila);

        this.sumatoria();

        this.codigoproducto.setText(
                "");

        this.precio.setText(
                "0");

        this.nombreproducto.setText(
                "");

        this.cantidad.setText(
                "1");

        this.cambiarprecio.setText(
                "0");

        this.codigoproducto.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_descuentoActionPerformed

    private void SalirCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        String cNumero = this.numero.getText();
        cNumero = cNumero.replace(".", "").replace(",", ".");
        String cTotalNeto = this.totalneto.getText();
        cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
        if (Double.valueOf(cTotalNeto) > 0 || Double.valueOf(cNumero) > 0) {
            JOptionPane.showMessageDialog(null, "No puede Salir de la Preventa");
            this.codigoproducto.requestFocus();
            return;
        } else {
            this.dispose();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

    private void VerificarCuenta() {
        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
        cuenta_clientes cta = null;

        try {
            cl = cliDAO.buscarIdSimple(Integer.valueOf(codcliente.getText()));
            cta = ctaDAO.MostrarAgrupadoxCliente(Integer.valueOf(codcliente.getText()));
            limitecredito.setText(formatea.format(cl.getLimitecredito()));
            disponible.setText(formatea.format(cl.getLimitecredito() - cta.getSaldo().doubleValue()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        String cEstaCompra = this.totalneto.getText();
        cEstaCompra = cEstaCompra.replace(".", "");
        String cDisponible = this.disponible.getText();
        cDisponible = cDisponible.replace(".", "");
        if (Double.valueOf(cEstaCompra) > Double.valueOf(cDisponible)) {
            JOptionPane.showMessageDialog(null, "Crédito no Disponible");
            this.codigoproducto.requestFocus();
            return;
        }
    }


    private void GuardarPreventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarPreventaActionPerformed
        Runtime rt = Runtime.getRuntime();
        System.out.println("Memoria Total de la JVM: " + rt.totalMemory());
        System.out.println("Memoria Antes: " + rt.freeMemory());
        if (this.tipocomprobante.getSelectedIndex() == 1) {
            clienteDAO cliDAO = new clienteDAO();
            cliente cl = null;
            cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
            cuenta_clientes cta = null;

            try {
                cl = cliDAO.buscarIdSimple(Integer.valueOf(codcliente.getText()));
                cta = ctaDAO.MostrarAgrupadoxCliente(Integer.valueOf(codcliente.getText()));
                limitecredito.setText(formatea.format(cl.getLimitecredito()));
                disponible.setText(formatea.format(cl.getLimitecredito() - cta.getSaldo().doubleValue()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            String cEstaCompra = this.totalneto.getText();
            cEstaCompra = cEstaCompra.replace(".", "");
            String cDisponible = this.disponible.getText();
            cDisponible = cDisponible.replace(".", "");
            if (Double.valueOf(cEstaCompra) > Double.valueOf(cDisponible)) {
                JOptionPane.showMessageDialog(null, "Crédito no Disponible");
                this.codigoproducto.requestFocus();
                return;
            }
        }
        String cProducto = null;
        String cCosto = null;
        String cCantidad = null;
        String cPrecio = null;
        String cMonto = null;
        String civa = "10";
        BigDecimal costo = null;
        String cTotalNeto = null;
        cTotalNeto = this.totalneto.getText();
        cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
        String cDescuento = this.descuento.getText();
        cDescuento = cDescuento.replace(".", "").replace(",", ".");

        if (this.codcliente.getText().isEmpty() || this.codcliente.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código de Cliente");
            this.codcliente.requestFocus();
            return;
        }

        if (Double.valueOf(cTotalNeto) != 0) {
            Object[] opciones = {"  Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Confirmar la Preventa ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                productoDAO producto = new productoDAO();
                producto p = null;
                int comprobante = 0;
                double importeiva = 0.00;
                exen = 0;
                grav10 = 0;
                grav5 = 0;
                BigDecimal totalitem = null;
                String cComentario = "Preventa";
                int totalRow = modelo.getRowCount();

                totalRow -= 1;

                String detalle = "[";
                for (int i = 0; i <= (totalRow); i++) {
                    cProducto = String.valueOf(modelo.getValueAt(i, 0));
                    try {
                        p = producto.BuscarProductoBasico(cProducto);
                        civa = String.valueOf(p.getIvaporcentaje());
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    cCantidad = String.valueOf(modelo.getValueAt(i, 2));
                    cCantidad = cCantidad.replace(".", "").replace(",", ".");

                    cPrecio = String.valueOf(modelo.getValueAt(i, 3));
                    cPrecio = cPrecio.replace(".", "").replace(",", ".");

                    cMonto = String.valueOf(modelo.getValueAt(i, 4));
                    cMonto = cMonto.replace(".", "").replace(",", ".");

                    costo = p.getCosto();
                    switch (civa) {
                        case "10.00":
                            importeiva = Math.round(Double.valueOf(cMonto) / 11);
                            break;
                        case "5.00":
                            importeiva = Math.round(Double.valueOf(cMonto) / 21);
                            break;
                        case "0.00":
                            importeiva = 0;
                            break;
                    }
                    String cImporteIva = String.valueOf(importeiva);

                    String linea = "{codprod : '" + cProducto + "',"
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cPrecio + ","
                            + "monto : " + cMonto + ","
                            + "impiva : " + cImporteIva + ","
                            + "porcentaje : " + civa + ","
                            + "comentario : '" + cComentario + "'"
                            + "},";
                    detalle += linea;
                }
                if (!detalle.equals("[")) {
                    detalle = detalle.substring(0, detalle.length() - 1);
                }
                detalle += "]";
                System.out.println(detalle);

                comprobante = tipocomprobante.getSelectedIndex() + 1;
                preventa v = new preventa();
                preventaDAO grabar = new preventaDAO();
                sucursalDAO sucDAO = new sucursalDAO();
                sucursal suc = null;
                vendedorDAO venDAO = new vendedorDAO();
                vendedor ven = null;
                comprobanteDAO comDAO = new comprobanteDAO();
                comprobante cm = null;
                clienteDAO clDAO = new clienteDAO();
                cliente cl = null;
                try {
                    cm = comDAO.buscarId(comprobante);
                    suc = sucDAO.buscarId(1);
                    ven = venDAO.buscarId(Integer.valueOf(vendedor.getText()));
                    cl = clDAO.buscarId(Integer.valueOf(codcliente.getText()));
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
                v.setCliente(cl);
                v.setFecha(FechaProceso);
                v.setVencimiento(FechaProceso);
                v.setObservacion(this.observacion.getText());
                v.setSucursal(suc);
                v.setComprobante(cm);
                v.setVendedor(ven);
                v.setMoneda(1);
                v.setTipo(1);
                v.setCuotas(Integer.valueOf(obra.getText()));
                v.setCaja(1);
                v.setTotalneto(Double.valueOf(cTotalNeto));
                v.setTotaldescuento(Double.valueOf(cDescuento));

                try {
                    grabar.InsertarPreVentaFerremax(Config.cToken, v, detalle);
                    this.inicializar();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                    System.out.println("--->" + ex.getLocalizedMessage());
                }
                //             this.imprimir.doClick();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay Productos en el Detalle");
        }
        rt.gc();
        System.out.println("Memoria Despues: " + rt.freeMemory());
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarPreventaActionPerformed

    private void tipocomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipocomprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipocomprobanteActionPerformed

    private void tipocomprobantePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tipocomprobantePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tipocomprobantePropertyChange

    private void tipocomprobanteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tipocomprobanteFocusLost
        if (tipocomprobante.getSelectedIndex() == 1) {
            clienteDAO cliDAO = new clienteDAO();
            cliente cl = null;
            cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
            cuenta_clientes cta = null;

            try {
                cl = cliDAO.buscarIdSimple(Integer.valueOf(codcliente.getText()));
                cta = ctaDAO.MostrarAgrupadoxCliente(Integer.valueOf(codcliente.getText()));
                limitecredito.setText(formatea.format(cl.getLimitecredito()));
                System.out.println(cta.getSaldo());
                disponible.setText(formatea.format(cl.getLimitecredito() - cta.getSaldo().doubleValue()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tipocomprobanteFocusLost

    private void nombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusLost
        String letras = ConvertirMayusculas.cadena(nombre);
        nombre.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusLost

    private void SalirStockSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirStockSucursalActionPerformed
        this.inventario.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirStockSucursalActionPerformed

    private void tablaproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproductoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoKeyReleased

    private void SalirUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirUbicacionActionPerformed
        this.ubicacion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirUbicacionActionPerformed

    private void GrabarOrdenCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarOrdenCompraActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito

        if (this.cliente1.getText().isEmpty() || this.cliente1.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Cliente");
            this.cliente1.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            ordencompraDAO grabarOCR = new ordencompraDAO();
            ordencompra cr = new ordencompra();
            giraduriaDAO giraDAO = new giraduriaDAO();
            giraduria gi = null;
            casaDAO caDAO = new casaDAO();
            casa ca = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cl = null;
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal sc = null;
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = null;
            config = configDAO.consultar();
            comprobanteDAO cmDAO = new comprobanteDAO();
            comprobante cm = null;
            /*ARQUEO CAJA Y EXTRACCIONES*/
            aperturaDAO grabar = new aperturaDAO();
            apertura ap = new apertura();
            usuarioDAO usuDAO = new usuarioDAO();
            usuario usu = null;

            cajaDAO cajaDAO = new cajaDAO();
            caja caj = null;
            monedaDAO moDAO = new monedaDAO();
            moneda mo = null;
            bancosDAO bcoDAO = new bancosDAO();
            banco bco = null;

            extraccionDAO exDAO = new extraccionDAO();
            extraccion ext = new extraccion();
            try {
                gi = giraDAO.buscarId(1);
                ca = caDAO.buscarId(1);
                cl = cliDAO.buscarId(Integer.valueOf(this.cliente1.getText()));
                sc = sucDAO.buscarId(1);
                cm = cmDAO.buscarId(config.getCodigoordencompra());
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.creferenciaOrdenCompra.setText(UUID.crearUUID());
            String idreferencia = this.creferenciaOrdenCompra.getText().substring(1, 25);

            //CAPTURAMOS LOS DATOS DE LA CABECERA
            cr.setIdordencompra(idreferencia);
            cr.setSucursal(sc);
            Date FechaProceso = ODate.de_java_a_sql(fecha1.getDate());
            Date Fechaalta = ODate.de_java_a_sql(fechaalta.getDate());
            cr.setFecha(FechaProceso);
            cr.setFechaalta(Fechaalta);
            cr.setGiraduria(gi);
            cr.setCasas(ca);
            cr.setCliente(cl);
            cr.setTipo(cm);
            cr.setGarante(0);
            cr.setPlazo(Integer.valueOf(this.plazo.getText()));
            String cImporte = this.importe.getText();
            cImporte = cImporte.replace(".", "");
            cImporte = cImporte.replace(",", ".");
            BigDecimal nimporte = new BigDecimal(cImporte);
            cr.setImporte(nimporte);

            String cMontoCuota = this.montocuota.getText();
            cMontoCuota = cMontoCuota.replace(".", "");
            cMontoCuota = cMontoCuota.replace(",", ".");

            String cMontoCuotaAnterior = cMontoCuota;
            BigDecimal nMontoCuota = new BigDecimal(cMontoCuota);
            cr.setMonto_cuota(nMontoCuota);

            Date PrimerVence = ODate.de_java_a_sql(primervence.getDate());
            cr.setPrimer_vence(PrimerVence);
            //cr.setUsuarioalta(Integer.valueOf(Config.CodUsuario));
            cr.setUsuarioalta(Integer.valueOf(Config.CodUsuario));

            //ESTA FUNCION USAMOS PARA REDONDEAR LAS CUOTAS
            double nImportecuotas = Double.valueOf(cMontoCuota) * Integer.valueOf(this.plazo.getText());

            double nResto = Double.valueOf(cImporte) - nImportecuotas;
            double nPrimeraCuota = Double.valueOf(cMontoCuota);
            if (nResto != 0) {
                if (nResto < 0) {
                    nPrimeraCuota = nPrimeraCuota - (Math.abs(nResto));
                } else {
                    nPrimeraCuota = nPrimeraCuota + nResto;
                }
            }

            //AHORA CAPTURAMOS LOS DATOS DE LOS DETALLES DE LA CUOTA
            //INICIAMOS EL CICLO FOR PARA EL MODELADO
            String iddocumento = null;
            Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
            calendar.setTime(this.primervence.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
            double nInteres, nMonto, nAmortiza = 0.00;
            nMonto = Double.valueOf(cImporte);
            String detalle = "[";
            for (int i = 1; i <= Integer.valueOf(this.plazo.getText()); i++) {
                iddocumento = UUID.crearUUID();
                iddocumento = iddocumento.substring(1, 25);
                this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el
                Date VenceCuota = ODate.de_java_a_sql(vencimientos.getDate());
                if (i == 1) {
                    cMontoCuota = String.valueOf(nPrimeraCuota);
                }
                String linea = "{iddocumento : " + iddocumento + ","
                        + "creferencia : " + idreferencia + ","
                        + "documento : " + "1" + this.cliente1.getText() + ","
                        + "fecha : " + FechaProceso + ","
                        + "vencimiento : " + VenceCuota + ","
                        + "cliente : " + this.cliente1.getText() + ","
                        + "sucursal : " + this.sucursal.getText() + ","
                        + "moneda : " + 1 + ","
                        + "importe : " + cMontoCuota + ","
                        + "comprobante : " + cm.getCodigo() + ","
                        + "giraduria : " + "1" + ","
                        + "numerocuota : " + this.plazo.getText() + ","
                        + "cuota : " + i + ","
                        + "saldo : " + cMontoCuota + ","
                        + "comercial : " + "1" + "},";
                detalle += linea;
                calendar.setTime(this.vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                this.venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior
                System.out.println(detalle);
                // Se capturan el día y el mes para saber si hay que aumentar los días en el siguiente mes
                // esto se hace en caso que el mes sea febrero
                int mes = this.venceanterior.getCalendar().get(Calendar.MONTH) + 1;
                int dia = this.venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
                calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0

                if (mes == 2 && dia == 28) {
                    calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días
                    //el vencimiento
                }
                this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el
                nMonto = nMonto - nAmortiza;
                cMontoCuota = cMontoCuotaAnterior;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";

            try {
                usu = usuDAO.buscarId(Integer.valueOf(Config.CodUsuario));
                caj = cajaDAO.buscarId(1);
                mo = moDAO.buscarId(1);
                bco = bcoDAO.buscarId(1);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            ap.setTurno(1);
            ap.setFecha(FechaProceso);
            ap.setCaja(caj);
            ap.setUsuario(usu);
            ap.setMoneda(mo);
            ap.setImporte(Double.valueOf(cImporte) * -1);
            ap.setCotizacion(1);
            ap.setMonto(Double.valueOf(cImporte));
            ap.setNombre("Anticipo " + nombrecliente1.getText());
            ap.setBanco(bco);
            /*------------EXTRACCIONES------------------------------*/

            UUID id = new UUID();
            String idmovimiento = UUID.crearUUID();
            idmovimiento = idmovimiento.substring(1, 25);
            String cDocumento = "1" + this.cliente1.getText();
            String cTipo = "C";
            String cCheque = "";
            ext.setIdmovimiento(idmovimiento);
            ext.setDocumento(cDocumento);
            ext.setFecha(FechaProceso);
            ext.setSucursal(sc);
            ext.setBanco(bco);
            ext.setTipo(cTipo);
            ext.setCotizacion(new BigDecimal(1));
            ext.setImporte(new BigDecimal(Double.valueOf(cImporte)));
            ext.setObservaciones("Anticipo " + this.nombrecliente1.getText());
            ext.setChequenro(cCheque);
            ext.setVencimiento(FechaProceso);
            ext.setMoneda(mo);
            try {
                grabarOCR.insertarOrdenCompra(Config.cToken, cr, detalle);
                // grabar.insertarApertura(ap);
                // exDAO.insertarMovBancoFerremax(ext);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            detalle_orden_compra.setVisible(false);
        }
    }//GEN-LAST:event_GrabarOrdenCompraActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_orden_compra.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void cliente1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cliente1FocusGained
        cliente1.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_cliente1FocusGained

    private void cliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cliente1ActionPerformed
        this.buscarCliente1.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cliente1ActionPerformed

    private void cliente1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cliente1KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importe.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha1.requestFocus();
        }   // TODO add your handling code
    }//GEN-LAST:event_cliente1KeyPressed

    private void buscarCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCliente1ActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarIdSimple(Integer.valueOf(this.cliente1.getText()));
            if (cl.getCodigo() == 0) {
                nBusqueda = 3;
                BuscarClientes.setModal(true);
                BuscarClientes.setSize(500, 575);
                BuscarClientes.setLocationRelativeTo(null);
                BuscarClientes.setTitle("Buscar Cliente");
                BuscarClientes.setVisible(true);
                //                giraduria.requestFocus();
            } else {
                nombrecliente1.setText(cl.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        importe.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarCliente1ActionPerformed

    private void importeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importeFocusGained
        importe.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importeFocusGained

    private void importeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.plazo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cliente1.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_importeKeyPressed

    private void plazoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_plazoFocusGained
        plazo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoFocusGained

    private void plazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plazoActionPerformed
        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.fecha.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        calendar.add(Calendar.DAY_OF_YEAR, 0);  // numero de días a añadir, o restar en caso de días<0
        this.primervence.setDate(calendar.getTime()); //Y cargamos finalmente en el
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoActionPerformed

    private void plazoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plazoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.montocuota.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.plazo.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_plazoKeyPressed

    private void montocuotaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_montocuotaFocusGained
        montocuota.selectAll();
        String cImporte = this.importe.getText();
        cImporte = cImporte.replace(".", "");
        double nMontoCuota = Math.round(Double.valueOf(cImporte) / Double.valueOf(plazo.getText()));
        montocuota.setText(formatea.format(nMontoCuota));
        // TODO add your handling code here:
    }//GEN-LAST:event_montocuotaFocusGained

    private void montocuotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_montocuotaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.primervence.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.plazo.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_montocuotaKeyPressed

    private void primervenceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primervenceKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarOrdenCompra.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.montocuota.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_primervenceKeyPressed

    private void fecha1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fecha1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fecha1FocusGained

    private void fecha1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fecha1KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cliente1.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fecha1KeyPressed

    private void detalle_orden_compraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_detalle_orden_compraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_compraFocusGained

    private void detalle_orden_compraWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_orden_compraWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_compraWindowGainedFocus

    private void detalle_orden_compraWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_orden_compraWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_compraWindowActivated

    private void GrabarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemActionPerformed
        int nCaracter = nrofactura.getText().indexOf("-");
        String cNumeroFactura = nrofactura.getText();
        if (nCaracter >= 0) {
            cNumeroFactura = cNumeroFactura.replace("-", "");
            boolean isNumeric = cNumeroFactura.matches("[+-]?\\d*(\\.\\d+)?");
            if (isNumeric == false) {
                JOptionPane.showMessageDialog(null, "Formato de Número de Factura no Corresponde");
                this.nrofactura.getText();
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de Número de Factura no Corresponde");
            this.nrofactura.getText();
            return;
        }
        if (this.sucursal.getText().isEmpty() || this.sucursal.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Sucursal");
            this.sucursal.requestFocus();
            return;
        }
        if (this.proveedor.getText().isEmpty() || this.proveedor.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Proveedor");
            this.proveedor.requestFocus();
            return;
        }

        if (this.moneda.getText().isEmpty() || this.moneda.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Moneda");
            this.moneda.requestFocus();
            return;
        }

        if (this.comprobante.getText().isEmpty() || this.comprobante.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Comprobante");
            this.comprobante.requestFocus();
            return;
        }

        if (Integer.valueOf(this.comprobante.getText()) != 4) {
            JOptionPane.showMessageDialog(null, "Solo puede ser Contado");
            this.comprobante.requestFocus();
            return;
        }

        if (this.rubro.getText().isEmpty() || this.rubro.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Rubro");
            this.rubro.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            String iddoc = null;
            int nItem = 0;
            int totalRow = 1;
            totalRow -= 1;

            //CAPTURAMOS LOS DETALLES DE GASTOS
            String detalle = "[";
            for (int i = 0; i <= (totalRow); i++) {
                iddoc = UUID.crearUUID();
                iddoc = iddoc.substring(1, 30);

                //Capturo y valido Producto
                nItem = nItem + 1;
                //Porcentaje
                //
                String cExenta = this.exentas.getText();
                cExenta = cExenta.replace(".", "").replace(",", ".");
                String cGravada10 = this.gravadas10.getText();
                cGravada10 = cGravada10.replace(".", "").replace(",", ".");
                String cIva10 = this.iva10.getText();
                cIva10 = cIva10.replace(".", "").replace(",", ".");
                String cGravada5 = this.gravadas5.getText();
                cGravada5 = cGravada5.replace(".", "").replace(",", ".");

                String cIva5 = this.iva5.getText();
                cIva5 = cIva5.replace(".", "").replace(",", ".");

                String cImporte = this.Totalneto.getText();
                cImporte = cImporte.replace(".", "").replace(",", ".");
                String cCotizacion = this.cotizacion.getText();
                cCotizacion = cCotizacion.replace(".", "").replace(",", ".");

                String cObservacion = this.observacion1.getText();

                if (cObservacion.equals("")) {
                    cObservacion = "SIN DATOS";
                }
                String cFondoFijo = "0";
                String cFactura = this.nrofactura.getText();
                cFactura = cFactura.replace("-", "");
                String linea = "{fondofijo : " + cFondoFijo + ","
                        + "formatofactura : " + nrofactura.getText() + ","
                        + "nrofactura : " + cFactura + ","
                        + "fecha : " + ODate.de_java_a_sql(fechafactura.getDate()) + ","
                        + "primer_vence: " + ODate.de_java_a_sql(vencimiento.getDate()) + ","
                        + "comprobante : " + comprobante.getText() + ","
                        + "concepto : " + rubro.getText() + ","
                        + "moneda : " + moneda.getText() + ","//8
                        + "timbrado : " + timbrado.getText() + ","
                        + "vencetimbrado : " + ODate.de_java_a_sql(vencimientotimbrado.getDate()) + ","
                        + "proveedor : " + proveedor.getText() + ","
                        + "exentas : " + cExenta + ","//13
                        + "gravadas10 : " + cGravada10 + ","
                        + "iva10 : " + cIva10 + ","
                        + "gravadas5 : " + cGravada5 + ","
                        + "iva5 : " + cIva5 + ","
                        + "totalneto : " + cImporte + ","//18
                        + "observacion : '" + cObservacion + "',"
                        + "cotizacion : " + cCotizacion + ","
                        + "sucursal : " + sucursal.getText() + ","
                        + "creferencia : '" + iddoc + "'},";
                detalle += linea;
            }

            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";

            //
            gastos_comprasDAO grabarDAO = new gastos_comprasDAO();
            try {
                grabarDAO.guardarItemGasto(detalle);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                return;
            }
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            this.SalirItem.doClick();
            this.limpiaritems();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemActionPerformed

    private void SalirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        itemgastos.setModal(false);
        itemgastos.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void cotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!cotizacion.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                this.proveedor.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Cotización", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }
    }//GEN-LAST:event_cotizacionKeyPressed

    private void exentasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_exentasFocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_exentasFocusGained

    private void exentasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_exentasFocusLost
        if (this.exentas.getText().trim().length() > 0) {
            this.Calcular();
            String cExentas = this.exentas.getText();
            cExentas = cExentas.replace(",", ".");
            double nExentas = Double.parseDouble(cExentas);
        }
    }//GEN-LAST:event_exentasFocusLost

    private void Calcular() {
        //Hacemos el Formateo de Numeros para los Valores Exentos
        if (this.exentas.getText().trim().length() > 0) {
            String cExentas = this.exentas.getText();
            cExentas = cExentas.replace(".", "");
            cExentas = cExentas.replace(",", ".");
            nExentas = Double.parseDouble(cExentas);
        }

        if (this.gravadas10.getText().trim().length() > 0) {
            String cGravadas10 = this.gravadas10.getText();
            cGravadas10 = cGravadas10.replace(".", "");
            cGravadas10 = cGravadas10.replace(",", ".");
            nGravadas10 = Double.parseDouble(cGravadas10);
        }

        if (this.gravadas5.getText().trim().length() > 0) {
            String cGravadas5 = this.gravadas5.getText();
            cGravadas5 = cGravadas5.replace(".", "");
            cGravadas5 = cGravadas5.replace(",", ".");
            nGravadas5 = Double.parseDouble(cGravadas5);
        }

        nTotal = nExentas + nGravadas10 + nGravadas5;
        String Numero = String.valueOf(nTotal);
        Numero = Numero.replace(",", ".");
        // BigDecimal bd1 = new BigDecimal(Numero);

        double Total = Double.parseDouble(Numero);
        this.Totalneto.setText(formatea.format(Total));
    }


    private void exentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exentasKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (exentas.getText().matches("")) {
                exentas.setText("0");
            }
            this.gravadas10.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            proveedor.requestFocus();
        }
    }//GEN-LAST:event_exentasKeyPressed

    private void gravadas10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas10FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_gravadas10FocusGained

    private void gravadas10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas10FocusLost
        if (this.gravadas10.getText().trim().length() > 0) {
            this.iva10.setText("");
            this.Calcular();
            String cGravadas10 = this.gravadas10.getText();

            cGravadas10 = cGravadas10.replace(",", ".");
            cGravadas10 = cGravadas10.replace(".", "");

            double nGravadas10 = Double.parseDouble(cGravadas10);

            if (Double.valueOf(moneda.getText()) == 1) {
                nIva10 = Math.round(nGravadas10 / 11);
            } else {
                nIva10 = nGravadas10 / 11;
            }
            this.iva10.setText(formatea.format(nIva10));
        }
    }//GEN-LAST:event_gravadas10FocusLost

    private void gravadas10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gravadas10KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (gravadas10.getText().matches("")) {
                gravadas10.setText("0");
            }
            this.iva10.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            exentas.requestFocus();
        }
    }//GEN-LAST:event_gravadas10KeyPressed

    private void iva10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_iva10FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_iva10FocusGained

    private void iva10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iva10KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (iva10.getText().matches("")) {
                iva10.setText("0");
            }
            this.gravadas5.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            gravadas10.requestFocus();
        }
    }//GEN-LAST:event_iva10KeyPressed

    private void gravadas5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas5FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_gravadas5FocusGained

    private void gravadas5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas5FocusLost
        if (this.gravadas5.getText().trim().length() > 0) {
            this.iva5.setText("");
            this.Calcular();
            String cGravadas5 = this.gravadas5.getText();

            cGravadas5 = cGravadas5.replace(",", ".");
            cGravadas5 = cGravadas5.replace(".", "");

            double nGravadas5 = Double.parseDouble(cGravadas5);

            if (Double.valueOf(moneda.getText()) == 1) {
                nIva5 = Math.round(nGravadas5 / 21);
            } else {
                nIva5 = nGravadas5 / 21;
            }
            this.iva5.setText(formatea.format(nIva5));
        }
    }//GEN-LAST:event_gravadas5FocusLost

    private void gravadas5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gravadas5KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (gravadas5.getText().matches("")) {
                gravadas5.setText("0");
            }
            this.iva5.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            iva10.requestFocus();
        }
    }//GEN-LAST:event_gravadas5KeyPressed

    private void iva5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_iva5FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_iva5FocusGained

    private void iva5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iva5KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (iva5.getText().matches("")) {
                iva5.setText("0");
            }
            this.observacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            gravadas5.requestFocus();
        }
    }//GEN-LAST:event_iva5KeyPressed

    private void TotalnetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalnetoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalnetoActionPerformed

    private void proveedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_proveedorFocusGained
        proveedor.selectAll();
    }//GEN-LAST:event_proveedorFocusGained

    private void proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorActionPerformed
        BuscarProveedor.doClick();
    }//GEN-LAST:event_proveedorActionPerformed

    private void proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!proveedor.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                this.exentas.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Proveedor", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            vencimientotimbrado.requestFocus();
        }
    }//GEN-LAST:event_proveedorKeyPressed

    private void timbradoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_timbradoFocusGained

    }//GEN-LAST:event_timbradoFocusGained

    private void timbradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_timbradoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.gravadas10.requestFocus();
        }
    }//GEN-LAST:event_timbradoKeyPressed

    private void BuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarProveedorActionPerformed

        proveedorDAO proDAO = new proveedorDAO();
        proveedor pro = null;
        try {
            pro = proDAO.buscarId(Integer.valueOf(this.proveedor.getText()));
            if (pro.getCodigo() == 0) {
                GrillaProveedor grillapro = new GrillaProveedor();
                Thread hilopro = new Thread(grillapro);
                hilopro.start();
                BProveedor.setModal(true);
                BProveedor.setSize(500, 585);
                BProveedor.setLocationRelativeTo(null);
                BProveedor.setVisible(true);
                BProveedor.setTitle("Proveedores");
                BProveedor.setModal(false);
            } else {
                nombreproveedor.setText(pro.getNombre());
                timbrado.setText(pro.getTimbrado());
                vencimientotimbrado.setDate(pro.getVencimiento());
                //Establecemos un título para el jDialog
            }
            gravadas10.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());

        }
    }//GEN-LAST:event_BuscarProveedorActionPerformed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!moneda.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                BuscarMoneda.doClick();
                this.cotizacion.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Moneda", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (rubro.isEnabled()) {
                this.rubro.requestFocus();
            } else {
                comprobante.requestFocus();
            }
        }
    }//GEN-LAST:event_monedaKeyPressed

    private void BuscarMonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarMonedaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaMouseClicked

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        monedaDAO casDAO = new monedaDAO();
        moneda mn = null;
        try {
            mn = casDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mn.getCodigo() == 0) {
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mn.getNombre());
                this.cotizacion.setText(formatea.format(mn.getVenta()));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        moneda.requestFocus();
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private void BuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarMonedaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaKeyPressed

    private void nombremonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombremonedaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombremonedaKeyPressed

    private void comprobanteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comprobanteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteFocusGained

    private void comprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteActionPerformed

    private void comprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comprobanteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!comprobante.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                BuscarComprobante.doClick();
                if (rubro.isEnabled()) {
                    this.rubro.requestFocus();
                } else {
                    moneda.requestFocus();
                }
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Comprobante", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            rubro.requestFocus();
        }
    }//GEN-LAST:event_comprobanteKeyPressed

    private void BuscarComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarComprobanteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarComprobanteMouseClicked

    private void BuscarComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarComprobanteActionPerformed
        comprobanteDAO compDAO = new comprobanteDAO();
        comprobante compr = null;
        try {
            compr = compDAO.buscarId(Integer.valueOf(this.comprobante.getText()));
            //System.out.println("codigo: "+compr.getCodigo());
            if (compr.getCodigo() == 0) {
                GrillaComprobante grillacomp = new GrillaComprobante();
                Thread hilocompr = new Thread(grillacomp);
                hilocompr.start();
                BComprobantes.setModal(true);
                BComprobantes.setSize(500, 575);
                BComprobantes.setLocationRelativeTo(null);
                BComprobantes.setTitle("Buscar Comprobantes");
                BComprobantes.setVisible(true);
                BComprobantes.setModal(false);
            } else {
                nombrecomprobante.setText(compr.getNombre());
            }
            rubro.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_BuscarComprobanteActionPerformed

    private void BuscarComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarComprobanteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarComprobanteKeyPressed

    private void nombrecomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrecomprobanteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecomprobanteKeyPressed

    private void rubroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rubroFocusGained
        rubro.selectAll();
    }//GEN-LAST:event_rubroFocusGained

    private void rubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubroActionPerformed
        this.BuscarRubro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroActionPerformed

    private void rubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rubroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!rubro.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                BuscarRubro.doClick();
                this.moneda.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Rubro", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comprobante.requestFocus();
        }
    }//GEN-LAST:event_rubroKeyPressed

    private void BuscarRubroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarRubroMouseClicked
        jTBuscarRubro.requestFocus();
    }//GEN-LAST:event_BuscarRubroMouseClicked

    private void BuscarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarRubroActionPerformed
        rubro_compraDAO ruDAO = new rubro_compraDAO();
        rubro_compra ru = null;
        try {
            ru = ruDAO.buscarRubroBanco(Integer.valueOf(this.rubro.getText()));
            if (ru.getCodigo() == 0) {
                GrillaRubroCompra grillaru = new GrillaRubroCompra();
                Thread hiloru = new Thread(grillaru);
                hiloru.start();
                BRubro.setModal(true);
                BRubro.setSize(482, 575);
                BRubro.setLocationRelativeTo(null);
                BRubro.setVisible(true);
                BRubro.setTitle("Buscar Tipo de Inmueble");
                BRubro.setModal(false);
            } else {
                nombrerubro.setText(ru.getNombre());
                //Establecemos un título para el jDialog
                moneda.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_BuscarRubroActionPerformed

    private void BuscarRubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarRubroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarRubroKeyPressed

    private void nombrerubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrerubroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrerubroKeyPressed

    private void nrofacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrofacturaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechafactura.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaKeyPressed

    private void comborubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comborubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comborubroActionPerformed

    private void jTBuscarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarRubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarRubroActionPerformed

    private void jTBuscarRubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarRubroKeyPressed
        this.jTBuscarRubro.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarRubro.getText()).toUpperCase();
                jTBuscarRubro.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comborubro.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrorubro(indiceColumnaTabla);
            }
        });
        trsfiltrorubro = new TableRowSorter(tablarubro.getModel());
        tablarubro.setRowSorter(trsfiltrorubro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarRubroKeyPressed

    public void filtrorubro(int nNumeroColumna) {
        trsfiltrorubro.setRowFilter(RowFilter.regexFilter(this.jTBuscarRubro.getText(), nNumeroColumna));
    }

    private void tablarubroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablarubroMouseClicked
        this.AceptarRubro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablarubroMouseClicked

    private void tablarubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablarubroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarRubro.doClick();
        }
    }//GEN-LAST:event_tablarubroKeyPressed

    private void AceptarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarRubroActionPerformed
        int nFila = this.tablarubro.getSelectedRow();
        this.rubro.setText(this.tablarubro.getValueAt(nFila, 0).toString());
        this.nombrerubro.setText(this.tablarubro.getValueAt(nFila, 1).toString());

        this.BRubro.setVisible(false);
        this.jTBuscarRubro.setText("");
        moneda.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarRubroActionPerformed

    private void SalirRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirRubroActionPerformed
        this.BRubro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirRubroActionPerformed

    private void comboComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboComprobantesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboComprobantesActionPerformed

    private void jTBuscarComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarComprobantesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobantesActionPerformed

    private void jTBuscarComprobantesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarComprobantesKeyPressed
        this.jTBuscarComprobantes.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarComprobantes.getText()).toUpperCase();
                jTBuscarComprobantes.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboComprobantes.getSelectedIndex()) {
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
                filtrocompr(indiceColumnaTabla);
            }
        });
        trsfiltrocompr = new TableRowSorter(tablacomprobante.getModel());
        tablacomprobante.setRowSorter(trsfiltrocompr);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobantesKeyPressed

    public void filtrocompr(int nNumeroColumna) {
        trsfiltrocompr.setRowFilter(RowFilter.regexFilter(this.jTBuscarComprobantes.getText(), nNumeroColumna));
    }

    private void tablacomprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacomprobanteMouseClicked
        this.AceptarComprobantes.doClick();
    }//GEN-LAST:event_tablacomprobanteMouseClicked

    private void tablacomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacomprobanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarComprobantes.doClick();
        }
    }//GEN-LAST:event_tablacomprobanteKeyPressed

    private void AceptarComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarComprobantesActionPerformed
        int nFila = this.tablacomprobante.getSelectedRow();
        this.comprobante.setText(this.tablacomprobante.getValueAt(nFila, 0).toString());
        this.nombrecomprobante.setText(this.tablacomprobante.getValueAt(nFila, 1).toString());

        this.BComprobantes.setVisible(false);
        this.jTBuscarComprobantes.setText("");
        this.rubro.requestFocus();
    }//GEN-LAST:event_AceptarComprobantesActionPerformed

    private void SalirComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirComprobantesActionPerformed
        this.BComprobantes.setVisible(false);
    }//GEN-LAST:event_SalirComprobantesActionPerformed

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
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void comboproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproveedorActionPerformed

    private void jTBuscarProveedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscarProveedorFocusGained
        proveedor.selectAll();
    }//GEN-LAST:event_jTBuscarProveedorFocusGained

    private void jTBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProveedorActionPerformed

    private void jTBuscarProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarProveedorKeyPressed
        this.jTBuscarProveedor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarProveedor.getText()).toUpperCase();
                jTBuscarProveedor.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboproveedor.getSelectedIndex()) {
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
                filtroproveedor(indiceColumnaTabla);
            }
        });
        trsfiltroproveedor = new TableRowSorter(tablaproveedor.getModel());
        tablaproveedor.setRowSorter(trsfiltroproveedor);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProveedorKeyPressed

    private void tablaproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproveedorMouseClicked
        this.AceptarProveedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorMouseClicked

    private void tablaproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarProveedor.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorKeyPressed

    private void AceptarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProveedorActionPerformed
        int nFila = this.tablaproveedor.getSelectedRow();
        this.proveedor.setText(this.tablaproveedor.getValueAt(nFila, 0).toString());
        this.nombreproveedor.setText(this.tablaproveedor.getValueAt(nFila, 1).toString());
        this.timbrado.setText(this.tablaproveedor.getValueAt(nFila, 3).toString());
        try {
            vencimientotimbrado.setDate(formatoFecha.parse(this.tablaproveedor.getValueAt(nFila, 4).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        gravadas10.requestFocus();
        this.BProveedor.setVisible(false);
        this.jTBuscarProveedor.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProveedorActionPerformed

    private void SalirProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProveedorActionPerformed
        this.BProveedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProveedorActionPerformed

    private void vencimientotimbradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencimientotimbradoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.gravadas10.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientotimbradoKeyPressed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(preventa_ferremax_sucursal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(preventa_ferremax_sucursal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(preventa_ferremax_sucursal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(preventa_ferremax_sucursal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new preventa_ferremax_sucursal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JButton AceptarComprobantes;
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton AceptarProducto;
    private javax.swing.JButton AceptarProveedor;
    private javax.swing.JButton AceptarRubro;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JButton Aceptarcliente;
    private javax.swing.JButton Aceptarcliente1;
    private javax.swing.JButton AgregarItem;
    private javax.swing.JDialog BBancos;
    private javax.swing.JDialog BComprobantes;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BProducto;
    private javax.swing.JDialog BProveedor;
    private javax.swing.JDialog BRubro;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JDialog BVendedor;
    private javax.swing.JButton BtnProductos;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JDialog BuscarClientes;
    private javax.swing.JButton BuscarComprobante;
    private javax.swing.JDialog BuscarLocalidad;
    private javax.swing.JButton BuscarMoneda;
    private javax.swing.JButton BuscarProveedor;
    private javax.swing.JButton BuscarRubro;
    private javax.swing.JButton BuscarSucursal;
    private javax.swing.JDialog EditarCantidad;
    private javax.swing.JLabel EtiquetaUsuario;
    private javax.swing.JButton GrabarCliente;
    private javax.swing.JButton GrabarItem;
    private javax.swing.JButton GrabarOrdenCompra;
    private javax.swing.JButton GuardarPreventa;
    private javax.swing.JDialog RegistrarCliente;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirBanco;
    private javax.swing.JButton SalirCantidad;
    private javax.swing.JButton SalirCargaCliente;
    private javax.swing.JButton SalirCliente;
    private javax.swing.JButton SalirCliente1;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirComprobantes;
    private javax.swing.JButton SalirFoto;
    private javax.swing.JButton SalirItem;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirProducto;
    private javax.swing.JButton SalirProveedor;
    private javax.swing.JButton SalirRubro;
    private javax.swing.JButton SalirStockSucursal;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton SalirUbicacion;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JFormattedTextField Totalneto;
    private javax.swing.JButton UpdateCantidad;
    private javax.swing.JButton buscarCliente1;
    private javax.swing.JButton buscarcliente;
    private javax.swing.JButton buscarobra;
    private javax.swing.JButton buscarvendedor;
    private javax.swing.JTextField cambiarprecio;
    private javax.swing.JTextField cantidad;
    private javax.swing.JFormattedTextField cantidadeditar;
    private javax.swing.JTextField celular;
    private javax.swing.JTextField cliente1;
    private javax.swing.JTextField codcliente;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField codigoeditar;
    private javax.swing.JTextField codigoproducto;
    private javax.swing.JComboBox comboComprobantes;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox comboclientes;
    private javax.swing.JComboBox combolocalidad;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox comboproducto;
    private javax.swing.JComboBox comboproveedor;
    private javax.swing.JComboBox comborubro;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JComboBox combovendedor;
    private javax.swing.JTextField comprobante;
    private javax.swing.JDialog configurar;
    private javax.swing.JTextField control;
    private javax.swing.JTextField costo;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JTextField creferenciaOrdenCompra;
    private javax.swing.JFormattedTextField descuento;
    private javax.swing.JDialog detalle_orden_compra;
    private javax.swing.JTextField direccion;
    private javax.swing.JFormattedTextField disponible;
    private javax.swing.JFormattedTextField estacompra;
    private javax.swing.JLabel etiquetacodigo;
    private javax.swing.JLabel etiquetacodigoubicacion;
    private javax.swing.JLabel etiquetanombre;
    private javax.swing.JLabel etiquetanombreproducto;
    private javax.swing.JLabel etiquetanombreubicacion;
    private javax.swing.JFormattedTextField exentas;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fecha1;
    private com.toedter.calendar.JDateChooser fechaalta;
    private com.toedter.calendar.JDateChooser fechafactura;
    private com.toedter.calendar.JDateChooser fechaingreso;
    private javax.swing.JLabel fondo;
    private JPanelWebCam.JPanelWebCam fotoProducto;
    private javax.swing.JFormattedTextField gravadas10;
    private javax.swing.JFormattedTextField gravadas5;
    private javax.swing.JCheckBox habilitado;
    private javax.swing.JTextField iditem;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JDialog inventario;
    private javax.swing.JDialog itemgastos;
    private javax.swing.JTextField iva;
    private javax.swing.JFormattedTextField iva10;
    private javax.swing.JFormattedTextField iva5;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
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
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTBuscarClientes;
    private javax.swing.JTextField jTBuscarComprobantes;
    private javax.swing.JTextField jTBuscarLocalidad;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarProveedor;
    private javax.swing.JTextField jTBuscarRubro;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTBuscarVendedor;
    private javax.swing.JTextField jTBuscarbanco;
    private javax.swing.JFormattedTextField limitecredito;
    private javax.swing.JTextField moneda;
    private javax.swing.JFormattedTextField montocuota;
    private com.toedter.calendar.JDateChooser nacimiento;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombrecliente1;
    private javax.swing.JTextField nombrecomprobante;
    private javax.swing.JTextField nombreeditar;
    private javax.swing.JLabel nombreinmuebleeti;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombreobra;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField nombreproveedor;
    private javax.swing.JTextField nombrerubro;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nombrevendedor;
    private javax.swing.JTextField nrofactura;
    private javax.swing.JFormattedTextField numero;
    private javax.swing.JTextField obra;
    private javax.swing.JTextArea observacion;
    private javax.swing.JTextArea observacion1;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JDialog panelfoto;
    private javax.swing.JTextField plazo;
    private javax.swing.JFormattedTextField precio;
    private javax.swing.JFormattedTextField precioeditar;
    private com.toedter.calendar.JDateChooser primervence;
    private javax.swing.JTextField proveedor;
    private javax.swing.JTextField rubro;
    private javax.swing.JTextField ruc;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablabanco;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacomprobante;
    private javax.swing.JTable tabladetalle;
    private javax.swing.JTable tablalocalidad;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablaproducto;
    private javax.swing.JTable tablaproductoubicacion;
    private javax.swing.JTable tablaproveedor;
    private javax.swing.JTable tablarubro;
    private javax.swing.JTable tablastock;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTable tablavendedor;
    private javax.swing.JTextField textoproducto;
    private javax.swing.JTextField timbrado;
    private javax.swing.JComboBox<String> tipocomprobante;
    private javax.swing.JFormattedTextField totalneto;
    private javax.swing.JDialog ubicacion;
    private com.toedter.calendar.JDateChooser venceanterior;
    private com.toedter.calendar.JDateChooser vencimiento;
    private com.toedter.calendar.JDateChooser vencimientos;
    private com.toedter.calendar.JDateChooser vencimientotimbrado;
    private javax.swing.JTextField vendedor;
    // End of variables declaration//GEN-END:variables

    private void inicializar() {
        Config.cCodProducto = "";
        Config.cNombreProducto = "";
        Config.nPrecio = 0;
        Config.nIvaProducto = 0;
        Config.nNumeroPrecierre = 0;

        this.numero.setText("0");
        this.codcliente.setText("1");
        this.nombrecliente.setText("CLIENTES VARIOS");
        this.vendedor.setText("0");
        this.nombrevendedor.setText("");

        Calendar c2 = new GregorianCalendar();
        this.fecha.setCalendar(c2);
        tipocomprobante.setSelectedIndex(0);
        cantidad.setText("1");
        codigoproducto.setText("");
        nombreproducto.setText("");
        descuento.setText("0");
        totalneto.setText("0");
        this.obra.setText("0");
        this.nombreobra.setText("");
        this.observacion.setText("");
        this.limitecredito.setText("0");
        this.disponible.setText("0");
        this.estacompra.setText("0");
        fotoProducto.setImagen(imagenfondo.getImage());
        int filas = tabladetalle.getRowCount();
        if (filas > 0) {
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);

            }
        }
    }

    private class GrillaClientes extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todos()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getRuc(), cli.getDireccion()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
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
                for (producto pr : DAOpro.todosmini()) {
                    if (pr.getIvaporcentaje() == null) {
                        nPorcentajeIVA = 0.00;
                    } else {
                        nPorcentajeIVA = pr.getIvaporcentaje().doubleValue();
                    }
                    String Datos[] = {String.valueOf(pr.getCodigo()), pr.getNombre(), formatea.format(pr.getPrecio_minimo()), formatea.format(pr.getPrecio_maximo()), formatea.format(pr.getStock()), formatea.format(nPorcentajeIVA), String.valueOf(pr.getCambiarprecio())};
                    modeloproducto.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
            int cantFilas = tablaproducto.getRowCount();

            BProducto.setModal(true);
            BProducto.setSize(500, 575);
            BProducto.setLocationRelativeTo(null);
            BProducto.setVisible(true);
            codigoproducto.requestFocus();
            BProducto.setModal(false);

        }
    }

    private class FacturaPOS extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = descuento.getText();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", idunico);
                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                //+ Config.cNombreFactura);
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());

                //  if (Config.cDestinoImpresion == "") {
                //Enviar a Vista Previa
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
                //} else {
                //Enviar a Impresora 
                //    JasperPrintManager.printReport(masterPrint, false);
                // }
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
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

    private class GrillaProd extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            String sql = "SELECT productos.codigo,productos.nombre,productos.costo,productos.precio_maximo,productos.precioventa,"
                    + "productos.precio_minimo,productos.stock,"
                    + "productos.ivaporcentaje,productos.estado,productos.cambiarprecio "
                    + " FROM productos "
                    + " WHERE productos.estado=1 "
                    + " ORDER BY productos.nombre ";

            int cantidadRegistro = modeloproducto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproducto.removeRow(0);
            }

            try {
                results = stm.executeQuery(sql);
                while (results.next()) {
                    // Se crea un array que será una de las filas de la tabla.
                    Object[] fila = new Object[7]; // Hay 8 columnas en la tabla
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    fila[0] = results.getString("codigo");
                    fila[1] = results.getString("nombre");
                    fila[2] = formatea.format(results.getDouble("precio_minimo"));
                    fila[3] = formatea.format(results.getDouble("precio_maximo"));
                    fila[4] = formatea.format(results.getDouble("stock"));
                    fila[5] = formatea.format(results.getDouble("ivaporcentaje"));
                    fila[6] = formatea.format(results.getInt("cambiarprecio"));

                    // Se añade al modelo la fila completa.
                    modeloproducto.addRow(fila);
                }
                tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
                tablaproducto.updateUI();
                stm.close();
            } catch (SQLException ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Ingresar a los Datos de Proveedores",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Conectarse a la Base de Datos",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            }
            FormatoTablaStock ft = new FormatoTablaStock(3, 4);
            tablaproducto.setDefaultRenderer(Object.class, ft);

            BProducto.setModal(true);
            BProducto.setSize(716, 575);
            BProducto.setLocationRelativeTo(null);
            BProducto.setVisible(true);
            codigoproducto.requestFocus();
            BProducto.setModal(false);
        }
    }

    private class GrillaObra extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            obraDAO obDAO = new obraDAO();
            try {
                for (obra ba : obDAO.todosxClienteActivo(Integer.valueOf(codcliente.getText()))) {
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

    private class GrillaComprobante extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocomprobante.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocomprobante.removeRow(0);
            }

            comprobanteDAO DAOCOMP = new comprobanteDAO();
            try {
                for (comprobante compr : DAOCOMP.todosxtipo(1)) {
                    String Datos[] = {String.valueOf(compr.getCodigo()), compr.getNombre()};
                    modelocomprobante.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablacomprobante.setModel(modelocomprobante);
            tablacomprobante.setRowSorter(new TableRowSorter(modelocomprobante));
            int cantFilas = tablacomprobante.getRowCount();
            System.out.println("agregado modelo: " + modelocomprobante.getRowCount());
            System.out.println("agregado tabla: " + tablacomprobante.getRowCount());
        }
    }

    private class GrillaRubroCompra extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelorubro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelorubro.removeRow(0);
            }
            rubro_compraDAO ruDAO = new rubro_compraDAO();
            try {
                for (Modelo.rubro_compra ru : ruDAO.todos()) {
                    String Datos[] = {String.valueOf(ru.getCodigo()), ru.getNombre()};
                    modelorubro.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablarubro.setRowSorter(new TableRowSorter(modelorubro));
            int cantFilas = tablarubro.getRowCount();
        }
    }

    private class GrillaProveedor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproveedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproveedor.removeRow(0);
            }

            proveedorDAO DAOpro = new proveedorDAO();
            try {
                for (proveedor prov : DAOpro.todos()) {
                    String Datos[] = {String.valueOf(prov.getCodigo()), prov.getNombre(), prov.getRuc(), prov.getTimbrado(), formatoFecha.format(prov.getVencimiento())};
                    modeloproveedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproveedor.setRowSorter(new TableRowSorter(modeloproveedor));
            int cantFilas = tablaproveedor.getRowCount();
        }
    }

    private class GrabarClienteVpn extends Thread {

        cliente cli = new cliente();
        int accion = 0;

        public GrabarClienteVpn(cliente clie, int tipo) {
            cli = clie;
            accion = tipo;
        }

        public void run() {
            {
                clienteVpnDAO cliDAO = new clienteVpnDAO();
                try {
                    if (accion == 1) {
                      cliDAO.insertarClienteVpn(cli,1);
                    } else {
                      cliDAO.actualizarClienteVpn(cli,1);
                    }
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    
    private class BuscaProductoVpn extends Thread {

        String cprod;

        public BuscaProductoVpn(String prod) {
            cprod = prod;
        }

        public void run() {
            {
                System.out.println("entre a consultar inventario");
                stockVpnDAO stVpnDAO = new stockVpnDAO();
                try {
                    Socket s = new Socket(dirWeb, 3306);
                    if (s.isConnected()) {
                        for (stock st : stVpnDAO.BuscarStockProductoVpn(cprod,1)) {
                            String Datos[] = {st.getSucursal().getNombre(), formatea.format(st.getStock())};
                            modelostock.addRow(Datos);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("No se pudo establecer conexión con: " + dirWeb + " a travez del puerto: 3306" );
                }
            }
        }
    }
    
    
}

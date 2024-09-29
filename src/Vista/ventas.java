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
import Clases.FormatoTabla;
import Modelo.preventa;
import Clases.FormatoTablaStock;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.albumfoto_productoDAO;
import DAO.bancoplazaDAO;
import DAO.preventaDAO;
import DAO.cajaDAO;
import DAO.clienteDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.cuenta_clienteDAO;
import DAO.detalle_forma_cobroDAO;
import DAO.detalle_forma_cuotasDAO;
import DAO.detalle_preventaDAO;
import DAO.detalle_ventaDAO;
import DAO.formapagoDAO;
import DAO.giraduriaDAO;
import DAO.lista_preciosDAO;
import DAO.monedaDAO;
import DAO.obraDAO;
import DAO.sucursalDAO;
import DAO.vendedorDAO;
import DAO.ventaDAO;
import DAO.productoDAO;
import DAO.retenciones_ventasDAO;
import DAO.ventas_anuladasDAO;
import JPanelWebCam.JPanelWebCam;
import Modelo.Tablas;
import Modelo.albumfoto_producto;
import Modelo.bancoplaza;
import Modelo.caja;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.detalle_forma_cobro;
import Modelo.detalle_forma_cuotas;
import Modelo.detalle_preventa;
import Modelo.detalle_venta;
import Modelo.formapago;
import Modelo.giraduria;
import Modelo.lista_precios;
import Modelo.moneda;
import Modelo.obra;
import Modelo.producto;
import Modelo.retenciones_ventas;
import Modelo.sucursal;
import Modelo.vendedor;
import Modelo.venta;
import Modelo.ventas_anuladas;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
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
import org.edisoncor.gui.label.LabelMetric;
import org.edisoncor.gui.panel.Panel;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 *
 */
public class ventas extends javax.swing.JFrame {

    int indiceTabla = 0;
    Conexion con = null;
    ResultSet results = null;
    Statement stm, stm2 = null;
    Tablas modelo = new Tablas();
    Tablas modelopais = new Tablas();
    Tablas modelofinanciacion = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelopreventa = new Tablas();
    Tablas modelocomprobante = new Tablas();
    Tablas modeloproducto = new Tablas();
    Tablas modelopagos = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelovendedor = new Tablas();
    Tablas modelocaja = new Tablas();
    Tablas modeloformapago = new Tablas();
    Tablas modelobanco = new Tablas();
    JScrollPane scroll = new JScrollPane();
    Date dConfirma;
    Date dPrimeraCuota;
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltrocomprobante, trsfiltromoneda, trsfiltroproducto, trsfiltrovendedor, trsfiltroformapago, trsfiltrobanco, trsfiltrocaja, trsfiltropais,trsfiltropreventa;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formatcantidad = new DecimalFormat("######.####");
    int nFila = 0;
    String cTotalNeto, cTotalValores, supago = null;
    String cSql = null;
    String cEfectivo = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String cTasa, referencia = null;
    String cadenabuscar = "";
    double nPorcentajeIVA = 0.00;

    /**
     * Creates new form Template
     */
    ImageIcon imagenfondo = new ImageIcon("src/Iconos/producto.png");
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

    public ventas() {
        initComponents();
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.visualizar.setIcon(iconobuscar);
        this.SalirCompleto.setIcon(iconosalir);
        this.creferenciaret.setVisible(false);
        this.Salir.setIcon(iconosalir);
        this.buscarobra.setIcon(iconobuscar);
        this.buscarSucursal.setIcon(iconobuscar);
        this.BuscarProducto.setIcon(iconobuscar);
        this.BuscarVendedor.setIcon(iconobuscar);
        this.buscarCliente.setIcon(iconobuscar);
        this.buscarMoneda.setIcon(iconobuscar);
        this.BuscarVendedor.setIcon(iconobuscar);
        this.buscarcomprobante.setIcon(iconobuscar);
        this.buscarpreventa.setIcon(iconobuscar);
        this.BuscarCaja.setIcon(iconobuscar);
        this.refrescar.setIcon(icorefresh);

        this.NewItem.setIcon(iconoitemnuevo);
        this.Upditem.setIcon(iconoitemupdate);
        this.DelItem.setIcon(iconoitemdelete);

        this.nuevoitem.setIcon(iconoitemnuevo);
        this.editaritem.setIcon(iconoitemupdate);
        this.delitem.setIcon(iconoitemdelete);

        this.NuevoF.setIcon(iconoitemnuevo);
        this.EditarF.setIcon(iconoitemupdate);
        this.BorrarF.setIcon(iconoitemdelete);

        this.Salir.setIcon(iconosalir);
        this.Grabar.setIcon(iconograbar);

        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        this.tablaventas.setShowGrid(false);
        this.tablaventas.setOpaque(true);
        this.tablaventas.setBackground(new Color(204, 204, 255));
        this.tablaventas.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.creferenciaticket.setVisible(false);
        this.idControl.setVisible(false);
        this.cModo1.setVisible(false);
        this.venceanterior.setVisible(false);
        this.vencimientos.setVisible(false);
        this.creferencia.setVisible(false);
        this.creferencia.setVisible(false);
        this.cModo.setVisible(false);
        this.modo.setVisible(false);
        this.nombreproducto.setEnabled(false);
        this.totalitem.setEnabled(false);
        this.idControl.setText("0");
        this.Inicializar();
        this.cargarTitulo();
        this.TituloPreventas();
        this.TituloDetalle();
        this.TitSuc();
        this.TitVendedor();
        this.TituloCaja();
        this.TitMoneda();
        this.TituloBanco();
        this.TituloFormaPago();
        this.CargarTituloFormaPago();
        this.CargarTituloFinanciacion();
        this.TituloProductos();
        this.TituloComprobante();
        this.TitClie();
        this.TitPais();
        GrillaVenta GrillaOC = new GrillaVenta();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        GrillaProductos grillapr = new GrillaProductos();
        Thread hilopr = new Thread(grillapr);
        hilopr.start();

        GrillaCajaVenta grillaca = new GrillaCajaVenta();
        Thread hilocaja = new Thread(grillaca);
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

        BProducto = new JDialog();
        jPanel23 = new JPanel();
        comboproducto = new JComboBox();
        jTBuscarProducto = new JTextField();
        jScrollPane8 = new JScrollPane();
        tablaproducto = new JTable();
        jPanel24 = new JPanel();
        AceptarProducto = new JButton();
        SalirProducto = new JButton();
        detalle_venta = new JDialog();
        jPanel1 = new JPanel();
        jPanel5 = new JPanel();
        jLabel12 = new JLabel();
        sucursal = new JTextField();
        buscarSucursal = new JButton();
        nombresucursal = new JTextField();
        jLabel2 = new JLabel();
        fecha = new JDateChooser();
        jLabel6 = new JLabel();
        comprobante = new JTextField();
        jLabel5 = new JLabel();
        vencetimbrado = new JDateChooser();
        jLabel7 = new JLabel();
        nrotimbrado = new JTextField();
        buscarcomprobante = new JButton();
        nombrecomprobante = new JTextField();
        creferencia = new JTextField();
        modo = new JTextField();
        jLabel1 = new JLabel();
        factura = new JTextField();
        jPanel6 = new JPanel();
        jLabel4 = new JLabel();
        cliente = new JTextField();
        buscarpreventa = new JButton();
        nombrecliente = new JTextField();
        direccioncliente = new JTextField();
        nombremoneda = new JTextField();
        buscarMoneda = new JButton();
        moneda = new JTextField();
        jLabel9 = new JLabel();
        jLabel14 = new JLabel();
        cotizacion = new JFormattedTextField();
        vendedor = new JTextField();
        etiquetavendedor = new JLabel();
        BuscarVendedor = new JButton();
        nombrevendedor = new JTextField();
        jLabel10 = new JLabel();
        primervence = new JDateChooser();
        jLabel11 = new JLabel();
        preventa = new JFormattedTextField();
        buscarCliente = new JButton();
        venceanterior = new JDateChooser();
        vencimientos = new JDateChooser();
        cuotas = new JFormattedTextField();
        jLabel32 = new JLabel();
        jLabel13 = new JLabel();
        caja = new JTextField();
        BuscarCaja = new JButton();
        nombrecaja = new JTextField();
        obra = new JTextField();
        nombreobra = new JTextField();
        buscarobra = new JButton();
        jLabel16 = new JLabel();
        jPanel7 = new JPanel();
        jTabbedPane1 = new JTabbedPane();
        jPanel36 = new JPanel();
        jScrollPane2 = new JScrollPane();
        tabladetalle = new JTable();
        jPanel37 = new JPanel();
        jScrollPane10 = new JScrollPane();
        tablapagos = new JTable();
        NewItem = new JButton();
        Upditem = new JButton();
        DelItem = new JButton();
        jLabel45 = new JLabel();
        totalvalores = new JFormattedTextField();
        jPanel27 = new JPanel();
        jScrollPane13 = new JScrollPane();
        tablafinanciacion = new JTable();
        NuevoF = new JButton();
        EditarF = new JButton();
        BorrarF = new JButton();
        jLabel51 = new JLabel();
        totalfinanciado = new JFormattedTextField();
        jPanel8 = new JPanel();
        nuevoitem = new JButton();
        editaritem = new JButton();
        delitem = new JButton();
        jPanel9 = new JPanel();
        jScrollPane3 = new JScrollPane();
        observacion = new JTextArea();
        jPanel10 = new JPanel();
        Salir = new JButton();
        Grabar = new JButton();
        jPanel11 = new JPanel();
        totalneto = new JFormattedTextField();
        jPanel12 = new JPanel();
        jLabel19 = new JLabel();
        jLabel21 = new JLabel();
        jLabel24 = new JLabel();
        exentas = new JFormattedTextField();
        gravadas10 = new JFormattedTextField();
        gravadas5 = new JFormattedTextField();
        fotoProducto = new JPanelWebCam();
        BSucursal = new JDialog();
        jPanel13 = new JPanel();
        combosucursal = new JComboBox();
        jTBuscarSucursal = new JTextField();
        jScrollPane4 = new JScrollPane();
        tablasucursal = new JTable();
        jPanel15 = new JPanel();
        AceptarSuc = new JButton();
        SalirSuc = new JButton();
        BCliente = new JDialog();
        jPanel14 = new JPanel();
        combocliente = new JComboBox();
        jTBuscarCliente = new JTextField();
        jScrollPane5 = new JScrollPane();
        tablacliente = new JTable();
        jPanel16 = new JPanel();
        AceptarCli = new JButton();
        SalirCli = new JButton();
        BComprobante = new JDialog();
        jPanel17 = new JPanel();
        combocomprobante = new JComboBox();
        jTBuscarComprobante = new JTextField();
        jScrollPane6 = new JScrollPane();
        tablacomprobante = new JTable();
        jPanel18 = new JPanel();
        AceptarComprobante = new JButton();
        SalirComprobante = new JButton();
        BMoneda = new JDialog();
        jPanel19 = new JPanel();
        combomoneda = new JComboBox();
        jTBuscarMoneda = new JTextField();
        jScrollPane7 = new JScrollPane();
        tablamoneda = new JTable();
        jPanel20 = new JPanel();
        AceptarMoneda = new JButton();
        SalirMoneda = new JButton();
        itemventas = new JDialog();
        jPanel21 = new JPanel();
        codprod = new JTextField();
        lblnombre = new JLabel();
        BuscarProducto = new JButton();
        nombreproducto = new JTextField();
        lblcantidad = new JLabel();
        cantidad = new JFormattedTextField();
        precio = new JFormattedTextField();
        jLabel28 = new JLabel();
        totalitem = new JFormattedTextField();
        cModo = new JTextField();
        porcentaje = new JFormattedTextField();
        jLabel30 = new JLabel();
        jLabel25 = new JLabel();
        jLabel54 = new JLabel();
        jPanel22 = new JPanel();
        GrabarItem = new JButton();
        SalirItem = new JButton();
        BVendedor = new JDialog();
        jPanel25 = new JPanel();
        combovendedor = new JComboBox();
        jTBuscarVendedor = new JTextField();
        jScrollPane9 = new JScrollPane();
        tablavendedor = new JTable();
        jPanel26 = new JPanel();
        AceptarVendedor = new JButton();
        SalirVendedor = new JButton();
        lotes = new JDialog();
        jPanel33 = new JPanel();
        jLabel42 = new JLabel();
        jPanel34 = new JPanel();
        jLabel43 = new JLabel();
        jLabel44 = new JLabel();
        facturainicial = new JTextField();
        facturafinal = new JTextField();
        jPanel35 = new JPanel();
        imprimirlotes = new JButton();
        SalirLotes = new JButton();
        formapago = new JDialog();
        jPanel38 = new JPanel();
        jLabel46 = new JLabel();
        jLabel47 = new JLabel();
        forma = new JTextField();
        BuscarFormapago = new JButton();
        nombreformapago = new JTextField();
        banco = new JTextField();
        BuscarBanco = new JButton();
        nombrebanco = new JTextField();
        jLabel48 = new JLabel();
        nrocheque = new JTextField();
        importecheque = new JFormattedTextField();
        jLabel49 = new JLabel();
        jLabel50 = new JLabel();
        confirmacion = new JDateChooser();
        cModo1 = new JTextField();
        jPanel39 = new JPanel();
        grabarPago = new JButton();
        salirPago = new JButton();
        BFormaPago = new JDialog();
        jPanel40 = new JPanel();
        comboforma = new JComboBox();
        jTBuscarForma = new JTextField();
        jScrollPane11 = new JScrollPane();
        tablaformapago = new JTable();
        jPanel41 = new JPanel();
        AceptarGir = new JButton();
        SalirGir = new JButton();
        BBancos = new JDialog();
        jPanel42 = new JPanel();
        combobanco = new JComboBox();
        jTBuscarbanco = new JTextField();
        jScrollPane12 = new JScrollPane();
        tablabanco = new JTable();
        jPanel43 = new JPanel();
        AceptarCasa = new JButton();
        SalirCasa = new JButton();
        lblcodigo = new JLabel();
        financiacion = new JDialog();
        jPanel44 = new JPanel();
        autorizacion = new JTextField();
        monto = new JFormattedTextField();
        primeracuota = new JDateChooser();
        ncuotas = new JFormattedTextField();
        montocuota = new JFormattedTextField();
        jLabel15 = new JLabel();
        jLabel26 = new JLabel();
        jLabel27 = new JLabel();
        jLabel29 = new JLabel();
        jLabel31 = new JLabel();
        jPanel45 = new JPanel();
        grabarFinanciacion = new JButton();
        salirFinanciacion = new JButton();
        BCaja = new JDialog();
        jPanel28 = new JPanel();
        combocaja = new JComboBox();
        jTBuscarCaja = new JTextField();
        jScrollPane14 = new JScrollPane();
        tablacaja = new JTable();
        jPanel29 = new JPanel();
        AceptarCaja = new JButton();
        SalirCaja = new JButton();
        retenciones = new JDialog();
        jPanel30 = new JPanel();
        GrabarRetencion = new JButton();
        SalirRetencion = new JButton();
        jPanel31 = new JPanel();
        jLabel33 = new JLabel();
        jLabel34 = new JLabel();
        nroretencion = new JTextField();
        fecharetencion = new JDateChooser();
        jLabel35 = new JLabel();
        nrofactura = new JTextField();
        jLabel36 = new JLabel();
        sucursalret = new JTextField();
        nombresucursalret = new JTextField();
        jLabel37 = new JLabel();
        monedaret = new JTextField();
        nombremonedaret = new JTextField();
        jLabel38 = new JLabel();
        importe_sin_iva = new JFormattedTextField();
        jLabel39 = new JLabel();
        importe_iva = new JFormattedTextField();
        jLabel40 = new JLabel();
        importe_gravado_total = new JFormattedTextField();
        jLabel41 = new JLabel();
        porcentaje_retencion = new JFormattedTextField();
        jLabel52 = new JLabel();
        valor_retencion = new JFormattedTextField();
        enviarcta = new JCheckBox();
        creferenciaret = new JTextField();
        jLabel53 = new JLabel();
        clienteret = new JTextField();
        nombreclienteret = new JTextField();
        actualizaTicket = new JDialog();
        jPanel32 = new JPanel();
        GrabarTicket = new JButton();
        SalirTicket = new JButton();
        jPanel46 = new JPanel();
        jLabel55 = new JLabel();
        jLabel56 = new JLabel();
        nroticket = new JTextField();
        fechaticket = new JDateChooser();
        jLabel57 = new JLabel();
        nrofacturaticket = new JTextField();
        jLabel58 = new JLabel();
        sucursalticket = new JTextField();
        nombresucursalticket = new JTextField();
        jLabel59 = new JLabel();
        monedaticket = new JTextField();
        nombremonedaticket = new JTextField();
        jLabel62 = new JLabel();
        totalticket = new JFormattedTextField();
        creferenciaticket = new JTextField();
        jLabel65 = new JLabel();
        clienteticket = new JTextField();
        nombreclienteticket = new JTextField();
        comprobanteticket = new JTextField();
        jLabel60 = new JLabel();
        nombrecomprobanteticket = new JTextField();
        BObras = new JDialog();
        jPanel47 = new JPanel();
        combopais = new JComboBox();
        jTBuscarPais = new JTextField();
        jScrollPane15 = new JScrollPane();
        tablapais = new JTable();
        jPanel48 = new JPanel();
        AceptarPais = new JButton();
        SalirPais = new JButton();
        preventas = new JDialog();
        jPanel49 = new JPanel();
        jLabel61 = new JLabel();
        ComboBuscarPreventa = new JComboBox<>();
        FiltrarPreventa = new JTextField();
        jPanel50 = new JPanel();
        jScrollPane16 = new JScrollPane();
        tablapreventa = new JTable();
        jPanel51 = new JPanel();
        AceptarPreventa = new JButton();
        SalirPreventa = new JButton();
        panel1 = new Panel();
        etiquetaventas = new LabelMetric();
        comboventa = new JComboBox();
        buscarcadena = new JTextField();
        jPanel2 = new JPanel();
        Modificar = new JButton();
        Agregar = new JButton();
        Eliminar = new JButton();
        Listar = new JButton();
        SalirCompleto = new JButton();
        idControl = new JTextField();
        jPanel3 = new JPanel();
        jLabel3 = new JLabel();
        dInicial = new JDateChooser();
        jLabel8 = new JLabel();
        dFinal = new JDateChooser();
        refrescar = new JButton();
        visualizar = new JButton();
        jScrollPane1 = new JScrollPane();
        tablaventas = new JTable();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem6 = new JMenuItem();
        jSeparator5 = new JPopupMenu.Separator();
        jMenuItem7 = new JMenuItem();
        jSeparator6 = new JPopupMenu.Separator();
        jMenuItem2 = new JMenuItem();
        jSeparator2 = new JPopupMenu.Separator();
        jMenuItem3 = new JMenuItem();
        jSeparator3 = new JPopupMenu.Separator();
        jMenuItem1 = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        jMenuItem4 = new JMenuItem();
        jSeparator4 = new JPopupMenu.Separator();
        jMenuItem5 = new JMenuItem();

        BProducto.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BProducto.setTitle("null");
        BProducto.setName("BProducto"); // NOI18N
        BProducto.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent evt) {
                BProductoWindowGainedFocus(evt);
            }
            public void windowLostFocus(WindowEvent evt) {
            }
        });

        jPanel23.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel23.setName("jPanel23"); // NOI18N

        comboproducto.setFont(new Font("Arial", 1, 14)); // NOI18N
        comboproducto.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comboproducto.setName("comboproducto"); // NOI18N
        comboproducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comboproductoActionPerformed(evt);
            }
        });

        jTBuscarProducto.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarProducto.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarProducto.setName("jTBuscarProducto"); // NOI18N
        jTBuscarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarProductoActionPerformed(evt);
            }
        });
        jTBuscarProducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarProductoKeyPressed(evt);
            }
        });

        GroupLayout jPanel23Layout = new GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(jPanel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(comboproducto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarProducto, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(jPanel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproducto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarProducto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        tablaproducto.setModel(modeloproducto);
        tablaproducto.setName("tablaproducto"); // NOI18N
        tablaproducto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablaproductoMouseClicked(evt);
            }
        });
        tablaproducto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablaproductoKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tablaproducto);

        jPanel24.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel24.setName("jPanel24"); // NOI18N

        AceptarProducto.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarProducto.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarProducto.setName("AceptarProducto"); // NOI18N
        AceptarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarProductoActionPerformed(evt);
            }
        });

        SalirProducto.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirProducto.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirProducto.setName("SalirProducto"); // NOI18N
        SalirProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirProductoActionPerformed(evt);
            }
        });

        GroupLayout jPanel24Layout = new GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarProducto, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirProducto, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(jPanel24Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarProducto)
                    .addComponent(SalirProducto))
                .addContainerGap())
        );

        GroupLayout BProductoLayout = new GroupLayout(BProducto.getContentPane());
        BProducto.getContentPane().setLayout(BProductoLayout);
        BProductoLayout.setHorizontalGroup(BProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BProductoLayout.createSequentialGroup()
                .addComponent(jPanel23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProductoLayout.setVerticalGroup(BProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BProductoLayout.createSequentialGroup()
                .addComponent(jPanel23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        detalle_venta.setName("detalle_venta"); // NOI18N
        detalle_venta.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                detalle_ventaFocusGained(evt);
            }
        });
        detalle_venta.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent evt) {
                detalle_ventaWindowGainedFocus(evt);
            }
            public void windowLostFocus(WindowEvent evt) {
            }
        });
        detalle_venta.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                detalle_ventaWindowActivated(evt);
            }
        });

        jPanel1.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel5.setBorder(BorderFactory.createEtchedBorder());
        jPanel5.setName("jPanel5"); // NOI18N

        jLabel12.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        sucursal.setHorizontalAlignment(JTextField.RIGHT);
        sucursal.setName("sucursal"); // NOI18N
        sucursal.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                sucursalFocusGained(evt);
            }
        });
        sucursal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sucursalActionPerformed(evt);
            }
        });
        sucursal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                sucursalKeyPressed(evt);
            }
        });

        buscarSucursal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarSucursal.setName("buscarSucursal"); // NOI18N
        buscarSucursal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setDisabledTextColor(new Color(0, 0, 0));
        nombresucursal.setEnabled(false);
        nombresucursal.setName("nombresucursal"); // NOI18N

        jLabel2.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        fecha.setName("fecha"); // NOI18N
        fecha.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                fechaFocusGained(evt);
            }
        });
        fecha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                fechaKeyPressed(evt);
            }
        });

        jLabel6.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        comprobante.setHorizontalAlignment(JTextField.RIGHT);
        comprobante.setName("comprobante"); // NOI18N
        comprobante.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                comprobanteFocusGained(evt);
            }
        });
        comprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comprobanteActionPerformed(evt);
            }
        });
        comprobante.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                comprobanteKeyPressed(evt);
            }
        });

        jLabel5.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        vencetimbrado.setName("vencetimbrado"); // NOI18N

        jLabel7.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        nrotimbrado.setHorizontalAlignment(JTextField.RIGHT);
        nrotimbrado.setName("nrotimbrado"); // NOI18N

        buscarcomprobante.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarcomprobante.setName("buscarcomprobante"); // NOI18N
        buscarcomprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarcomprobanteActionPerformed(evt);
            }
        });

        nombrecomprobante.setDisabledTextColor(new Color(0, 0, 0));
        nombrecomprobante.setEnabled(false);
        nombrecomprobante.setName("nombrecomprobante"); // NOI18N

        creferencia.setEnabled(false);
        creferencia.setName("creferencia"); // NOI18N

        modo.setEnabled(false);
        modo.setName("modo"); // NOI18N

        jLabel1.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        factura.setText(NbBundle.getMessage(ventas.class, "ventas.factura.text")); // NOI18N
        factura.setName("factura"); // NOI18N
        factura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                facturaActionPerformed(evt);
            }
        });
        factura.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                facturaKeyPressed(evt);
            }
        });

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(sucursal, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(buscarSucursal, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(factura))
                    .addComponent(nombresucursal, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(vencetimbrado, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comprobante, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarcomprobante, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nrotimbrado, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(creferencia, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modo, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(nombrecomprobante))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(comprobante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarcomprobante, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecomprobante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(nrotimbrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(creferencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(modo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(buscarSucursal, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(sucursal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)
                                .addComponent(factura, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(fecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(nombresucursal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(vencetimbrado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(BorderFactory.createEtchedBorder());
        jPanel6.setName("jPanel6"); // NOI18N

        jLabel4.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        cliente.setHorizontalAlignment(JTextField.RIGHT);
        cliente.setName("cliente"); // NOI18N
        cliente.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                clienteFocusGained(evt);
            }
        });
        cliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });
        cliente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                clienteKeyPressed(evt);
            }
        });

        buscarpreventa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarpreventa.setName("buscarpreventa"); // NOI18N
        buscarpreventa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarpreventaActionPerformed(evt);
            }
        });

        nombrecliente.setDisabledTextColor(new Color(0, 0, 0));
        nombrecliente.setEnabled(false);
        nombrecliente.setName("nombrecliente"); // NOI18N

        direccioncliente.setDisabledTextColor(new Color(0, 0, 0));
        direccioncliente.setEnabled(false);
        direccioncliente.setName("direccioncliente"); // NOI18N

        nombremoneda.setDisabledTextColor(new Color(0, 0, 0));
        nombremoneda.setEnabled(false);
        nombremoneda.setName("nombremoneda"); // NOI18N

        buscarMoneda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarMoneda.setName("buscarMoneda"); // NOI18N
        buscarMoneda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarMonedaActionPerformed(evt);
            }
        });

        moneda.setHorizontalAlignment(JTextField.RIGHT);
        moneda.setName("moneda"); // NOI18N
        moneda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                monedaActionPerformed(evt);
            }
        });
        moneda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                monedaKeyPressed(evt);
            }
        });

        jLabel9.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel14.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        cotizacion.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,###.00"))));
        cotizacion.setHorizontalAlignment(JTextField.RIGHT);
        cotizacion.setName("cotizacion"); // NOI18N
        cotizacion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });

        vendedor.setHorizontalAlignment(JTextField.RIGHT);
        vendedor.setName("vendedor"); // NOI18N
        vendedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                vendedorActionPerformed(evt);
            }
        });
        vendedor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                vendedorKeyPressed(evt);
            }
        });

        etiquetavendedor.setText(NbBundle.getMessage(ventas.class, "ventas.etiquetavendedor.text")); // NOI18N
        etiquetavendedor.setName("etiquetavendedor"); // NOI18N

        BuscarVendedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BuscarVendedor.setName("BuscarVendedor"); // NOI18N
        BuscarVendedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BuscarVendedorActionPerformed(evt);
            }
        });

        nombrevendedor.setDisabledTextColor(new Color(0, 0, 0));
        nombrevendedor.setEnabled(false);
        nombrevendedor.setName("nombrevendedor"); // NOI18N

        jLabel10.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        primervence.setName("primervence"); // NOI18N
        primervence.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                primervenceKeyPressed(evt);
            }
        });

        jLabel11.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        preventa.setEditable(false);
        preventa.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0"))));
        preventa.setHorizontalAlignment(JTextField.RIGHT);
        preventa.setEnabled(false);
        preventa.setName("preventa"); // NOI18N
        preventa.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                preventaKeyPressed(evt);
            }
        });

        buscarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarCliente.setName("buscarCliente"); // NOI18N
        buscarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarClienteActionPerformed(evt);
            }
        });

        venceanterior.setEnabled(false);
        venceanterior.setName("venceanterior"); // NOI18N

        vencimientos.setEnabled(false);
        vencimientos.setName("vencimientos"); // NOI18N

        cuotas.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#0"))));
        cuotas.setHorizontalAlignment(JTextField.RIGHT);
        cuotas.setName("cuotas"); // NOI18N
        cuotas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                cuotasKeyPressed(evt);
            }
        });

        jLabel32.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel32.text")); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N

        jLabel13.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        caja.setHorizontalAlignment(JTextField.RIGHT);
        caja.setText(NbBundle.getMessage(ventas.class, "ventas.caja.text")); // NOI18N
        caja.setName("caja"); // NOI18N
        caja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cajaActionPerformed(evt);
            }
        });

        BuscarCaja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BuscarCaja.setName("BuscarCaja"); // NOI18N
        BuscarCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BuscarCajaActionPerformed(evt);
            }
        });

        nombrecaja.setText(NbBundle.getMessage(ventas.class, "ventas.nombrecaja.text")); // NOI18N
        nombrecaja.setEnabled(false);
        nombrecaja.setName("nombrecaja"); // NOI18N

        obra.setText(NbBundle.getMessage(ventas.class, "ventas.obra.text")); // NOI18N
        obra.setEnabled(false);
        obra.setName("obra"); // NOI18N
        obra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                obraActionPerformed(evt);
            }
        });

        nombreobra.setText(NbBundle.getMessage(ventas.class, "ventas.nombreobra.text")); // NOI18N
        nombreobra.setEnabled(false);
        nombreobra.setName("nombreobra"); // NOI18N

        buscarobra.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buscarobra.setName("buscarobra"); // NOI18N
        buscarobra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buscarobraActionPerformed(evt);
            }
        });

        jLabel16.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel16.text_1")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(moneda, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(cuotas)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(14, 14, 14)
                        .addComponent(preventa)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(buscarMoneda, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombremoneda, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(buscarpreventa, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(primervence, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 77, Short.MAX_VALUE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(obra, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buscarobra, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nombreobra, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(183, 183, 183)
                                .addComponent(vencimientos, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel13)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(cotizacion, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                            .addComponent(caja))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(BuscarCaja, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(venceanterior, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(etiquetavendedor)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(vendedor)
                            .addComponent(cliente, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarVendedor, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarCliente, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(nombrecaja, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)))
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombrecliente, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(nombrevendedor, GroupLayout.Alignment.TRAILING)
                    .addComponent(direccioncliente))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(vendedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(etiquetavendedor))
                            .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(buscarMoneda, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(nombremoneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(cotizacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(BuscarVendedor, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addComponent(nombrevendedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(venceanterior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(moneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cuotas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32))
                    .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(cliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(nombrecliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarCliente, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addComponent(primervence, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(caja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuscarCaja, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addComponent(vencimientos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecaja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccioncliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarpreventa, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(preventa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(obra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombreobra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(buscarobra, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(BorderFactory.createEtchedBorder());
        jPanel7.setName("jPanel7"); // NOI18N

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel36.setName("jPanel36"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tabladetalle.setModel(modelodetalle);
        tabladetalle.setName("tabladetalle"); // NOI18N
        tabladetalle.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                tabladetalleMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tabladetalle);

        GroupLayout jPanel36Layout = new GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(jPanel36Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(jPanel36Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(NbBundle.getMessage(ventas.class, "ventas.jPanel36.TabConstraints.tabTitle"), jPanel36); // NOI18N

        jPanel37.setName("jPanel37"); // NOI18N

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        tablapagos.setModel(modelopagos        );
        tablapagos.setName("tablapagos"); // NOI18N
        jScrollPane10.setViewportView(tablapagos);

        NewItem.setText(NbBundle.getMessage(ventas.class, "ventas.NewItem.text")); // NOI18N
        NewItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        NewItem.setName("NewItem"); // NOI18N
        NewItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                NewItemActionPerformed(evt);
            }
        });

        Upditem.setText(NbBundle.getMessage(ventas.class, "ventas.Upditem.text")); // NOI18N
        Upditem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Upditem.setEnabled(false);
        Upditem.setName("Upditem"); // NOI18N
        Upditem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                UpditemActionPerformed(evt);
            }
        });

        DelItem.setText(NbBundle.getMessage(ventas.class, "ventas.DelItem.text")); // NOI18N
        DelItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        DelItem.setEnabled(false);
        DelItem.setName("DelItem"); // NOI18N
        DelItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                DelItemActionPerformed(evt);
            }
        });

        jLabel45.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jLabel45.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel45.text")); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        totalvalores.setEditable(false);
        totalvalores.setBackground(new Color(0, 153, 255));
        totalvalores.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        totalvalores.setHorizontalAlignment(JTextField.CENTER);
        totalvalores.setDisabledTextColor(new Color(0, 0, 0));
        totalvalores.setEnabled(false);
        totalvalores.setName("totalvalores"); // NOI18N

        GroupLayout jPanel37Layout = new GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(jPanel37Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane10, GroupLayout.PREFERRED_SIZE, 621, GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel37Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel37Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(NewItem, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Upditem, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(DelItem, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel37Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                                .addComponent(totalvalores, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addGap(25, 25, 25))))))
        );
        jPanel37Layout.setVerticalGroup(jPanel37Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(NewItem)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Upditem)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DelItem)
                .addGap(18, 18, 18)
                .addComponent(jLabel45)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalvalores, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(NbBundle.getMessage(ventas.class, "ventas.jPanel37.TabConstraints.tabTitle"), jPanel37); // NOI18N

        jPanel27.setName("jPanel27"); // NOI18N

        jScrollPane13.setName("jScrollPane13"); // NOI18N

        tablafinanciacion.setModel(modelofinanciacion     );
        tablafinanciacion.setName("tablafinanciacion"); // NOI18N
        jScrollPane13.setViewportView(tablafinanciacion);

        NuevoF.setText(NbBundle.getMessage(ventas.class, "ventas.NuevoF.text")); // NOI18N
        NuevoF.setName("NuevoF"); // NOI18N
        NuevoF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                NuevoFActionPerformed(evt);
            }
        });

        EditarF.setText(NbBundle.getMessage(ventas.class, "ventas.EditarF.text")); // NOI18N
        EditarF.setName("EditarF"); // NOI18N
        EditarF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                EditarFActionPerformed(evt);
            }
        });

        BorrarF.setText(NbBundle.getMessage(ventas.class, "ventas.BorrarF.text")); // NOI18N
        BorrarF.setName("BorrarF"); // NOI18N
        BorrarF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BorrarFActionPerformed(evt);
            }
        });

        jLabel51.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jLabel51.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel51.text")); // NOI18N
        jLabel51.setName("jLabel51"); // NOI18N

        totalfinanciado.setEditable(false);
        totalfinanciado.setBackground(new Color(0, 153, 255));
        totalfinanciado.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        totalfinanciado.setHorizontalAlignment(JTextField.CENTER);
        totalfinanciado.setDisabledTextColor(new Color(0, 0, 0));
        totalfinanciado.setEnabled(false);
        totalfinanciado.setName("totalfinanciado"); // NOI18N

        GroupLayout jPanel27Layout = new GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(NuevoF, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                        .addComponent(EditarF, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                        .addComponent(BorrarF, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
                    .addComponent(totalfinanciado, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addGap(15, 15, 15)))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(jPanel27Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(NuevoF)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarF)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BorrarF)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalfinanciado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab(NbBundle.getMessage(ventas.class, "ventas.jPanel27.TabConstraints.tabTitle"), jPanel27); // NOI18N

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jPanel8.setBorder(BorderFactory.createEtchedBorder());
        jPanel8.setName("jPanel8"); // NOI18N

        nuevoitem.setText(NbBundle.getMessage(ventas.class, "ventas.nuevoitem.text")); // NOI18N
        nuevoitem.setName("nuevoitem"); // NOI18N
        nuevoitem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                nuevoitemActionPerformed(evt);
            }
        });

        editaritem.setText(NbBundle.getMessage(ventas.class, "ventas.editaritem.text")); // NOI18N
        editaritem.setName("editaritem"); // NOI18N
        editaritem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editaritemActionPerformed(evt);
            }
        });

        delitem.setText(NbBundle.getMessage(ventas.class, "ventas.delitem.text")); // NOI18N
        delitem.setName("delitem"); // NOI18N
        delitem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                delitemActionPerformed(evt);
            }
        });

        GroupLayout jPanel8Layout = new GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(delitem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editaritem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuevoitem, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nuevoitem)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editaritem)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delitem)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(BorderFactory.createTitledBorder(NbBundle.getMessage(ventas.class, "ventas.jPanel9.border.title"))); // NOI18N
        jPanel9.setName("jPanel9"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        observacion.setColumns(20);
        observacion.setRows(5);
        observacion.setName("observacion"); // NOI18N
        jScrollPane3.setViewportView(observacion);

        GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jPanel10.setBorder(BorderFactory.createEtchedBorder());
        jPanel10.setName("jPanel10"); // NOI18N

        Salir.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        Salir.setText(NbBundle.getMessage(ventas.class, "ventas.Salir.text")); // NOI18N
        Salir.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.Salir.toolTipText")); // NOI18N
        Salir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Salir.setName("Salir"); // NOI18N
        Salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        Grabar.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        Grabar.setText(NbBundle.getMessage(ventas.class, "ventas.Grabar.text")); // NOI18N
        Grabar.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.Grabar.toolTipText")); // NOI18N
        Grabar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Grabar.setName("Grabar"); // NOI18N
        Grabar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GrabarActionPerformed(evt);
            }
        });

        GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Grabar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Salir, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(Salir)
                    .addComponent(Grabar))
                .addContainerGap())
        );

        jPanel11.setBorder(BorderFactory.createTitledBorder(NbBundle.getMessage(ventas.class, "ventas.jPanel11.border.title"))); // NOI18N
        jPanel11.setName("jPanel11"); // NOI18N

        totalneto.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        totalneto.setHorizontalAlignment(JTextField.CENTER);
        totalneto.setEnabled(false);
        totalneto.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        totalneto.setName("totalneto"); // NOI18N

        GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(totalneto, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalneto, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setBorder(BorderFactory.createTitledBorder(NbBundle.getMessage(ventas.class, "ventas.jPanel12.border.title"))); // NOI18N
        jPanel12.setName("jPanel12"); // NOI18N

        jLabel19.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel21.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel24.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        exentas.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,###.00"))));
        exentas.setHorizontalAlignment(JTextField.RIGHT);
        exentas.setEnabled(false);
        exentas.setName("exentas"); // NOI18N

        gravadas10.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,###.00"))));
        gravadas10.setHorizontalAlignment(JTextField.RIGHT);
        gravadas10.setEnabled(false);
        gravadas10.setName("gravadas10"); // NOI18N

        gravadas5.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,###.00"))));
        gravadas5.setHorizontalAlignment(JTextField.RIGHT);
        gravadas5.setEnabled(false);
        gravadas5.setName("gravadas5"); // NOI18N

        GroupLayout jPanel12Layout = new GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19)
                    .addComponent(jLabel24))
                .addGap(28, 28, 28)
                .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(exentas, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(gravadas10)
                    .addComponent(gravadas5))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(exentas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(gravadas10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel12Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(gravadas5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fotoProducto.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        fotoProducto.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.fotoProducto.toolTipText")); // NOI18N
        fotoProducto.setACTIVARCAMARA(false);
        fotoProducto.setFONDO(false);
        fotoProducto.setEnabled(false);
        fotoProducto.setName("fotoProducto"); // NOI18N

        GroupLayout fotoProductoLayout = new GroupLayout(fotoProducto);
        fotoProducto.setLayout(fotoProductoLayout);
        fotoProductoLayout.setHorizontalGroup(fotoProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        fotoProductoLayout.setVerticalGroup(fotoProductoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        GroupLayout detalle_ventaLayout = new GroupLayout(detalle_venta.getContentPane());
        detalle_venta.getContentPane().setLayout(detalle_ventaLayout);
        detalle_ventaLayout.setHorizontalGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(detalle_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(detalle_ventaLayout.createSequentialGroup()
                        .addComponent(jPanel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(81, 81, 81)
                        .addGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detalle_ventaLayout.createSequentialGroup()
                        .addComponent(jPanel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fotoProducto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        detalle_ventaLayout.setVerticalGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, detalle_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detalle_ventaLayout.createSequentialGroup()
                        .addComponent(jPanel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fotoProducto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, detalle_ventaLayout.createSequentialGroup()
                        .addGap(0, 17, Short.MAX_VALUE)
                        .addGroup(detalle_ventaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, detalle_ventaLayout.createSequentialGroup()
                                .addComponent(jPanel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel12, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19))
        );

        BSucursal.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");
        BSucursal.setName("BSucursal"); // NOI18N

        jPanel13.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel13.setName("jPanel13"); // NOI18N

        combosucursal.setFont(new Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combosucursal.setName("combosucursal"); // NOI18N
        combosucursal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarSucursal.setName("jTBuscarSucursal"); // NOI18N
        jTBuscarSucursal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarSucursalActionPerformed(evt);
            }
        });
        jTBuscarSucursal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarSucursalKeyPressed(evt);
            }
        });

        GroupLayout jPanel13Layout = new GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(combosucursal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combosucursal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarSucursal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tablasucursal.setModel(modelosucursal);
        tablasucursal.setName("tablasucursal"); // NOI18N
        tablasucursal.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablasucursalMouseClicked(evt);
            }
        });
        tablasucursal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablasucursalKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablasucursal);

        jPanel15.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel15.setName("jPanel15"); // NOI18N

        AceptarSuc.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarSuc.setName("AceptarSuc"); // NOI18N
        AceptarSuc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirSuc.setName("SalirSuc"); // NOI18N
        SalirSuc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        GroupLayout jPanel15Layout = new GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        GroupLayout BSucursalLayout = new GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(BSucursalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(BSucursalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        BCliente.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");
        BCliente.setName("BCliente"); // NOI18N

        jPanel14.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel14.setName("jPanel14"); // NOI18N

        combocliente.setFont(new Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combocliente.setName("combocliente"); // NOI18N
        combocliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCliente.setName("jTBuscarCliente"); // NOI18N
        jTBuscarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarClienteActionPerformed(evt);
            }
        });
        jTBuscarCliente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarClienteKeyPressed(evt);
            }
        });

        GroupLayout jPanel14Layout = new GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combocliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCliente, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combocliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCliente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        tablacliente.setModel(modelocliente        );
        tablacliente.setName("tablacliente"); // NOI18N
        tablacliente.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablaclienteMouseClicked(evt);
            }
        });
        tablacliente.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablaclienteKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablacliente);

        jPanel16.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel16.setName("jPanel16"); // NOI18N

        AceptarCli.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarCli.setName("AceptarCli"); // NOI18N
        AceptarCli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCli.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirCli.setName("SalirCli"); // NOI18N
        SalirCli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        GroupLayout jPanel16Layout = new GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        GroupLayout BClienteLayout = new GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(BClienteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(BClienteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        BComprobante.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BComprobante.setTitle("null");
        BComprobante.setName("BComprobante"); // NOI18N

        jPanel17.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel17.setName("jPanel17"); // NOI18N

        combocomprobante.setFont(new Font("Arial", 1, 14)); // NOI18N
        combocomprobante.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocomprobante.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combocomprobante.setName("combocomprobante"); // NOI18N
        combocomprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combocomprobanteActionPerformed(evt);
            }
        });

        jTBuscarComprobante.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarComprobante.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarComprobante.setName("jTBuscarComprobante"); // NOI18N
        jTBuscarComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarComprobanteActionPerformed(evt);
            }
        });
        jTBuscarComprobante.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarComprobanteKeyPressed(evt);
            }
        });

        GroupLayout jPanel17Layout = new GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combocomprobante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarComprobante, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combocomprobante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarComprobante, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        tablacomprobante.setModel(modelocomprobante);
        tablacomprobante.setName("tablacomprobante"); // NOI18N
        tablacomprobante.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablacomprobanteMouseClicked(evt);
            }
        });
        tablacomprobante.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablacomprobanteKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablacomprobante);

        jPanel18.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel18.setName("jPanel18"); // NOI18N

        AceptarComprobante.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarComprobante.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarComprobante.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarComprobante.setName("AceptarComprobante"); // NOI18N
        AceptarComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarComprobanteActionPerformed(evt);
            }
        });

        SalirComprobante.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirComprobante.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirComprobante.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirComprobante.setName("SalirComprobante"); // NOI18N
        SalirComprobante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirComprobanteActionPerformed(evt);
            }
        });

        GroupLayout jPanel18Layout = new GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarComprobante, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirComprobante, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarComprobante)
                    .addComponent(SalirComprobante))
                .addContainerGap())
        );

        GroupLayout BComprobanteLayout = new GroupLayout(BComprobante.getContentPane());
        BComprobante.getContentPane().setLayout(BComprobanteLayout);
        BComprobanteLayout.setHorizontalGroup(BComprobanteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BComprobanteLayout.createSequentialGroup()
                .addComponent(jPanel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BComprobanteLayout.setVerticalGroup(BComprobanteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BComprobanteLayout.createSequentialGroup()
                .addComponent(jPanel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");
        BMoneda.setName("BMoneda"); // NOI18N

        jPanel19.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel19.setName("jPanel19"); // NOI18N

        combomoneda.setFont(new Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combomoneda.setName("combomoneda"); // NOI18N
        combomoneda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarMoneda.setName("jTBuscarMoneda"); // NOI18N
        jTBuscarMoneda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarMonedaActionPerformed(evt);
            }
        });
        jTBuscarMoneda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarMonedaKeyPressed(evt);
            }
        });

        GroupLayout jPanel19Layout = new GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combomoneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combomoneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMoneda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        tablamoneda.setModel(modelomoneda);
        tablamoneda.setName("tablamoneda"); // NOI18N
        tablamoneda.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablamonedaMouseClicked(evt);
            }
        });
        tablamoneda.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablamonedaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablamoneda);

        jPanel20.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel20.setName("jPanel20"); // NOI18N

        AceptarMoneda.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarMoneda.setName("AceptarMoneda"); // NOI18N
        AceptarMoneda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMoneda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirMoneda.setName("SalirMoneda"); // NOI18N
        SalirMoneda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        GroupLayout jPanel20Layout = new GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(jPanel20Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(jPanel20Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        GroupLayout BMonedaLayout = new GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(BMonedaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(BMonedaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        itemventas.setName("itemventas"); // NOI18N

        jPanel21.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel21.setName("jPanel21"); // NOI18N

        codprod.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.codprod.text")); // NOI18N
        codprod.setName("codprod"); // NOI18N
        codprod.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                codprodFocusGained(evt);
            }
        });
        codprod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                codprodActionPerformed(evt);
            }
        });
        codprod.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                codprodKeyPressed(evt);
            }
        });

        lblnombre.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.jLabel14.text")); // NOI18N
        lblnombre.setName("lblnombre"); // NOI18N

        BuscarProducto.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.BuscarProducto.text")); // NOI18N
        BuscarProducto.setName("BuscarProducto"); // NOI18N
        BuscarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BuscarProductoActionPerformed(evt);
            }
        });

        nombreproducto.setEditable(false);
        nombreproducto.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.nombreproducto.text")); // NOI18N
        nombreproducto.setEnabled(false);
        nombreproducto.setName("nombreproducto"); // NOI18N

        lblcantidad.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.jLabel15.text")); // NOI18N
        lblcantidad.setName("lblcantidad"); // NOI18N

        cantidad.setHorizontalAlignment(JTextField.RIGHT);
        cantidad.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.cantidad.text")); // NOI18N
        cantidad.setName("cantidad"); // NOI18N
        cantidad.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                cantidadFocusGained(evt);
            }
        });
        cantidad.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                cantidadKeyPressed(evt);
            }
        });

        precio.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        precio.setHorizontalAlignment(JTextField.RIGHT);
        precio.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.preciounitario.text")); // NOI18N
        precio.setName("precio"); // NOI18N
        precio.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                precioFocusGained(evt);
            }
            public void focusLost(FocusEvent evt) {
                precioFocusLost(evt);
            }
        });
        precio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                precioActionPerformed(evt);
            }
        });
        precio.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                precioKeyPressed(evt);
            }
        });

        jLabel28.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.jLabel17.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        totalitem.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        totalitem.setHorizontalAlignment(JTextField.RIGHT);
        totalitem.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.totalitem.text")); // NOI18N
        totalitem.setName("totalitem"); // NOI18N

        cModo.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.cModo.text")); // NOI18N
        cModo.setName("cModo"); // NOI18N

        porcentaje.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#0"))));
        porcentaje.setHorizontalAlignment(JTextField.RIGHT);
        porcentaje.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.preciounitario.text")); // NOI18N
        porcentaje.setName("porcentaje"); // NOI18N
        porcentaje.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                porcentajeFocusGained(evt);
            }
            public void focusLost(FocusEvent evt) {
                porcentajeFocusLost(evt);
            }
        });
        porcentaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                porcentajeActionPerformed(evt);
            }
        });
        porcentaje.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                porcentajeKeyPressed(evt);
            }
        });

        jLabel30.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        jLabel25.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jLabel54.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel54.text")); // NOI18N
        jLabel54.setName("jLabel54"); // NOI18N

        GroupLayout jPanel21Layout = new GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(totalitem, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                        .addGap(209, 209, 209))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, GroupLayout.Alignment.TRAILING)
                            .addComponent(lblnombre, GroupLayout.Alignment.TRAILING)
                            .addComponent(lblcantidad, GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel54, GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel21Layout.createSequentialGroup()
                                            .addComponent(codprod, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(BuscarProducto, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cModo, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(nombreproducto, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cantidad, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                        .addComponent(precio, GroupLayout.Alignment.LEADING))))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(porcentaje, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarProducto, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(codprod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25))
                    .addComponent(cModo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreproducto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnombre))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcantidad))
                .addGap(11, 11, 11)
                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(precio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(porcentaje, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(totalitem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addContainerGap())
        );

        jPanel22.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel22.setName("jPanel22"); // NOI18N

        GrabarItem.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.NuevoItem.text")); // NOI18N
        GrabarItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        GrabarItem.setName("GrabarItem"); // NOI18N
        GrabarItem.setPreferredSize(new Dimension(39, 20));
        GrabarItem.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                GrabarItemFocusGained(evt);
            }
        });
        GrabarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GrabarItemActionPerformed(evt);
            }
        });

        SalirItem.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.SalirItem.text")); // NOI18N
        SalirItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirItem.setName("SalirItem"); // NOI18N
        SalirItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });

        GroupLayout jPanel22Layout = new GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarItem, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(SalirItem, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        jPanel22Layout.setVerticalGroup(jPanel22Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(SalirItem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(GrabarItem, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        GroupLayout itemventasLayout = new GroupLayout(itemventas.getContentPane());
        itemventas.getContentPane().setLayout(itemventasLayout);
        itemventasLayout.setHorizontalGroup(itemventasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemventasLayout.setVerticalGroup(itemventasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(itemventasLayout.createSequentialGroup()
                .addComponent(jPanel21, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BVendedor.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BVendedor.setTitle("null");
        BVendedor.setName("BVendedor"); // NOI18N

        jPanel25.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel25.setName("jPanel25"); // NOI18N

        combovendedor.setFont(new Font("Arial", 1, 14)); // NOI18N
        combovendedor.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combovendedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combovendedor.setName("combovendedor"); // NOI18N
        combovendedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combovendedorActionPerformed(evt);
            }
        });

        jTBuscarVendedor.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarVendedor.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarVendedor.setName("jTBuscarVendedor"); // NOI18N
        jTBuscarVendedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarVendedorActionPerformed(evt);
            }
        });
        jTBuscarVendedor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarVendedorKeyPressed(evt);
            }
        });

        GroupLayout jPanel25Layout = new GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(jPanel25Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(combovendedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarVendedor, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(jPanel25Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combovendedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarVendedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        tablavendedor.setModel(modelovendedor);
        tablavendedor.setName("tablavendedor"); // NOI18N
        tablavendedor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablavendedorMouseClicked(evt);
            }
        });
        tablavendedor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablavendedorKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablavendedor);

        jPanel26.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel26.setName("jPanel26"); // NOI18N

        AceptarVendedor.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarVendedor.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarVendedor.setName("AceptarVendedor"); // NOI18N
        AceptarVendedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirVendedor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirVendedor.setName("SalirVendedor"); // NOI18N
        SalirVendedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirVendedorActionPerformed(evt);
            }
        });

        GroupLayout jPanel26Layout = new GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(jPanel26Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarVendedor, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirVendedor, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(jPanel26Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarVendedor)
                    .addComponent(SalirVendedor))
                .addContainerGap())
        );

        GroupLayout BVendedorLayout = new GroupLayout(BVendedor.getContentPane());
        BVendedor.getContentPane().setLayout(BVendedorLayout);
        BVendedorLayout.setHorizontalGroup(BVendedorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BVendedorLayout.setVerticalGroup(BVendedorLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        lotes.setName("lotes"); // NOI18N

        jPanel33.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel33.setName("jPanel33"); // NOI18N

        jLabel42.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel42.text")); // NOI18N
        jLabel42.setName("jLabel42"); // NOI18N

        GroupLayout jPanel33Layout = new GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(jPanel33Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel42)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(jPanel33Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel34.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel34.setName("jPanel34"); // NOI18N

        jLabel43.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel43.text")); // NOI18N
        jLabel43.setName("jLabel43"); // NOI18N

        jLabel44.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel44.text")); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N

        facturainicial.setHorizontalAlignment(JTextField.RIGHT);
        facturainicial.setName("facturainicial"); // NOI18N

        facturafinal.setHorizontalAlignment(JTextField.RIGHT);
        facturafinal.setName("facturafinal"); // NOI18N

        GroupLayout jPanel34Layout = new GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(jPanel34Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(jPanel34Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel43)))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(facturainicial, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(facturafinal, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(jPanel34Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel43)
                .addGap(12, 12, 12)
                .addComponent(facturainicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(facturafinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel35.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel35.setName("jPanel35"); // NOI18N

        imprimirlotes.setText(NbBundle.getMessage(ventas.class, "ventas.imprimirlotes.text")); // NOI18N
        imprimirlotes.setName("imprimirlotes"); // NOI18N
        imprimirlotes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                imprimirlotesActionPerformed(evt);
            }
        });

        SalirLotes.setText(NbBundle.getMessage(ventas.class, "ventas.SalirLotes.text")); // NOI18N
        SalirLotes.setName("SalirLotes"); // NOI18N
        SalirLotes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirLotesActionPerformed(evt);
            }
        });

        GroupLayout jPanel35Layout = new GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(jPanel35Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(imprimirlotes, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(SalirLotes, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(jPanel35Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(imprimirlotes)
                    .addComponent(SalirLotes))
                .addContainerGap())
        );

        GroupLayout lotesLayout = new GroupLayout(lotes.getContentPane());
        lotes.getContentPane().setLayout(lotesLayout);
        lotesLayout.setHorizontalGroup(lotesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel34, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel35, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        lotesLayout.setVerticalGroup(lotesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(lotesLayout.createSequentialGroup()
                .addComponent(jPanel33, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        formapago.setName("formapago"); // NOI18N

        jPanel38.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel38.setName("jPanel38"); // NOI18N

        jLabel46.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel46.text")); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        jLabel47.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel47.text")); // NOI18N
        jLabel47.setName("jLabel47"); // NOI18N

        forma.setHorizontalAlignment(JTextField.RIGHT);
        forma.setName("forma"); // NOI18N
        forma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                formaActionPerformed(evt);
            }
        });

        BuscarFormapago.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BuscarFormapago.setName("BuscarFormapago"); // NOI18N
        BuscarFormapago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BuscarFormapagoActionPerformed(evt);
            }
        });

        nombreformapago.setName("nombreformapago"); // NOI18N

        banco.setHorizontalAlignment(JTextField.RIGHT);
        banco.setName("banco"); // NOI18N
        banco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bancoActionPerformed(evt);
            }
        });

        BuscarBanco.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BuscarBanco.setName("BuscarBanco"); // NOI18N
        BuscarBanco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BuscarBancoActionPerformed(evt);
            }
        });

        nombrebanco.setName("nombrebanco"); // NOI18N

        jLabel48.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel48.text")); // NOI18N
        jLabel48.setName("jLabel48"); // NOI18N

        nrocheque.setHorizontalAlignment(JTextField.RIGHT);
        nrocheque.setName("nrocheque"); // NOI18N
        nrocheque.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                nrochequeKeyPressed(evt);
            }
        });

        importecheque.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        importecheque.setHorizontalAlignment(JTextField.RIGHT);
        importecheque.setName("importecheque"); // NOI18N
        importecheque.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                importechequeKeyPressed(evt);
            }
        });

        jLabel49.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel49.text")); // NOI18N
        jLabel49.setName("jLabel49"); // NOI18N

        jLabel50.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel50.text")); // NOI18N
        jLabel50.setName("jLabel50"); // NOI18N

        confirmacion.setName("confirmacion"); // NOI18N
        confirmacion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                confirmacionKeyPressed(evt);
            }
        });

        cModo1.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.cModo.text")); // NOI18N
        cModo1.setName("cModo1"); // NOI18N

        GroupLayout jPanel38Layout = new GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel46)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(forma, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarFormapago)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreformapago, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel47)
                                .addComponent(jLabel48))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel50))
                                .addGap(3, 3, 3)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(confirmacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel38Layout.createSequentialGroup()
                                    .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nrocheque, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                        .addComponent(importecheque))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cModo1, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel38Layout.createSequentialGroup()
                                    .addComponent(banco, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BuscarBanco)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(nombrebanco, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))))))
                .addGap(72, 72, 72))
        );
        jPanel38Layout.setVerticalGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(nombreformapago, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarFormapago, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel46)
                        .addComponent(forma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel47)
                    .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nombrebanco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(BuscarBanco, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(banco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(nrocheque, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(importecheque, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49)))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(cModo1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(confirmacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel39.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel39.setName("jPanel39"); // NOI18N

        grabarPago.setText(NbBundle.getMessage(ventas.class, "ventas.grabarPago.text")); // NOI18N
        grabarPago.setCursor(new Cursor(Cursor.HAND_CURSOR));
        grabarPago.setName("grabarPago"); // NOI18N
        grabarPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                grabarPagoActionPerformed(evt);
            }
        });
        grabarPago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                grabarPagoKeyPressed(evt);
            }
        });

        salirPago.setText(NbBundle.getMessage(ventas.class, "ventas.salirPago.text")); // NOI18N
        salirPago.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salirPago.setName("salirPago"); // NOI18N
        salirPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                salirPagoActionPerformed(evt);
            }
        });

        GroupLayout jPanel39Layout = new GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(jPanel39Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(grabarPago, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salirPago, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel39Layout.setVerticalGroup(jPanel39Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGroup(jPanel39Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(grabarPago)
                    .addComponent(salirPago))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        GroupLayout formapagoLayout = new GroupLayout(formapago.getContentPane());
        formapago.getContentPane().setLayout(formapagoLayout);
        formapagoLayout.setHorizontalGroup(formapagoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel39, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel38, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        formapagoLayout.setVerticalGroup(formapagoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(formapagoLayout.createSequentialGroup()
                .addComponent(jPanel38, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        BFormaPago.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BFormaPago.setTitle("null");
        BFormaPago.setName("BFormaPago"); // NOI18N

        jPanel40.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel40.setName("jPanel40"); // NOI18N

        comboforma.setFont(new Font("Arial", 1, 14)); // NOI18N
        comboforma.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboforma.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comboforma.setName("comboforma"); // NOI18N
        comboforma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comboformaActionPerformed(evt);
            }
        });

        jTBuscarForma.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarForma.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarForma.setName("jTBuscarForma"); // NOI18N
        jTBuscarForma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarFormaActionPerformed(evt);
            }
        });
        jTBuscarForma.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarFormaKeyPressed(evt);
            }
        });

        GroupLayout jPanel40Layout = new GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(jPanel40Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(comboforma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarForma, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(jPanel40Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel40Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(comboforma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarForma, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane11.setName("jScrollPane11"); // NOI18N

        tablaformapago.setModel(modeloformapago);
        tablaformapago.setName("tablaformapago"); // NOI18N
        tablaformapago.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablaformapagoMouseClicked(evt);
            }
        });
        tablaformapago.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablaformapagoKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(tablaformapago);

        jPanel41.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel41.setName("jPanel41"); // NOI18N

        AceptarGir.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarGir.setName("AceptarGir"); // NOI18N
        AceptarGir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirGir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirGir.setName("SalirGir"); // NOI18N
        SalirGir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        GroupLayout jPanel41Layout = new GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(jPanel41Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(jPanel41Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        GroupLayout BFormaPagoLayout = new GroupLayout(BFormaPago.getContentPane());
        BFormaPago.getContentPane().setLayout(BFormaPagoLayout);
        BFormaPagoLayout.setHorizontalGroup(BFormaPagoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BFormaPagoLayout.createSequentialGroup()
                .addComponent(jPanel40, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane11, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel41, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BFormaPagoLayout.setVerticalGroup(BFormaPagoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BFormaPagoLayout.createSequentialGroup()
                .addComponent(jPanel40, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        BBancos.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BBancos.setTitle("null");
        BBancos.setName("BBancos"); // NOI18N

        jPanel42.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel42.setName("jPanel42"); // NOI18N

        combobanco.setFont(new Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combobanco.setName("combobanco"); // NOI18N
        combobanco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarbanco.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarbanco.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarbanco.setName("jTBuscarbanco"); // NOI18N
        jTBuscarbanco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarbancoActionPerformed(evt);
            }
        });
        jTBuscarbanco.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarbancoKeyPressed(evt);
            }
        });

        GroupLayout jPanel42Layout = new GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(jPanel42Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(combobanco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarbanco, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(jPanel42Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel42Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combobanco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarbanco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane12.setName("jScrollPane12"); // NOI18N

        tablabanco.setModel(modelobanco       );
        tablabanco.setName("tablabanco"); // NOI18N
        tablabanco.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablabancoMouseClicked(evt);
            }
        });
        tablabanco.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablabancoKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(tablabanco);

        jPanel43.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel43.setName("jPanel43"); // NOI18N

        AceptarCasa.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(NbBundle.getMessage(ventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarCasa.setName("AceptarCasa"); // NOI18N
        AceptarCasa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCasa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirCasa.setName("SalirCasa"); // NOI18N
        SalirCasa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirCasaActionPerformed(evt);
            }
        });

        GroupLayout jPanel43Layout = new GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(jPanel43Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(jPanel43Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa)
                    .addComponent(SalirCasa))
                .addContainerGap())
        );

        GroupLayout BBancosLayout = new GroupLayout(BBancos.getContentPane());
        BBancos.getContentPane().setLayout(BBancosLayout);
        BBancosLayout.setHorizontalGroup(BBancosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel42, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel43, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBancosLayout.setVerticalGroup(BBancosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel42, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        lblcodigo.setText(NbBundle.getMessage(ventas.class, "detalle_facturas.jLabel19.text")); // NOI18N
        lblcodigo.setName("lblcodigo"); // NOI18N

        financiacion.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        financiacion.setName("financiacion"); // NOI18N

        jPanel44.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel44.setName("jPanel44"); // NOI18N

        autorizacion.setHorizontalAlignment(JTextField.RIGHT);
        autorizacion.setName("autorizacion"); // NOI18N
        autorizacion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                autorizacionKeyPressed(evt);
            }
        });

        monto.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0"))));
        monto.setHorizontalAlignment(JTextField.RIGHT);
        monto.setName("monto"); // NOI18N
        monto.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                montoKeyPressed(evt);
            }
        });

        primeracuota.setName("primeracuota"); // NOI18N
        primeracuota.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                primeracuotaKeyPressed(evt);
            }
        });

        ncuotas.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0"))));
        ncuotas.setHorizontalAlignment(JTextField.RIGHT);
        ncuotas.setText(NbBundle.getMessage(ventas.class, "ventas.ncuotas.text")); // NOI18N
        ncuotas.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.ncuotas.toolTipText")); // NOI18N
        ncuotas.setName("ncuotas"); // NOI18N
        ncuotas.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                ncuotasFocusLost(evt);
            }
        });
        ncuotas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ncuotasActionPerformed(evt);
            }
        });
        ncuotas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                ncuotasKeyPressed(evt);
            }
        });

        montocuota.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0"))));
        montocuota.setHorizontalAlignment(JTextField.RIGHT);
        montocuota.setText(NbBundle.getMessage(ventas.class, "ventas.montocuota.text")); // NOI18N
        montocuota.setName("montocuota"); // NOI18N
        montocuota.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                montocuotaKeyPressed(evt);
            }
        });

        jLabel15.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel26.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel27.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        jLabel29.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jLabel31.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N

        GroupLayout jPanel44Layout = new GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29)
                    .addComponent(jLabel31))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(primeracuota, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                        .addComponent(montocuota, GroupLayout.Alignment.LEADING)
                        .addComponent(ncuotas, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addComponent(autorizacion, GroupLayout.Alignment.LEADING)
                        .addComponent(monto, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
                .addGap(180, 180, 180))
        );
        jPanel44Layout.setVerticalGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(autorizacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(monto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ncuotas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(montocuota, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(11, 11, 11)
                .addGroup(jPanel44Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(primeracuota, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel45.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel45.setName("jPanel45"); // NOI18N

        grabarFinanciacion.setText(NbBundle.getMessage(ventas.class, "ventas.grabarFinanciacion.text")); // NOI18N
        grabarFinanciacion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        grabarFinanciacion.setName("grabarFinanciacion"); // NOI18N
        grabarFinanciacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                grabarFinanciacionActionPerformed(evt);
            }
        });
        grabarFinanciacion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                grabarFinanciacionKeyPressed(evt);
            }
        });

        salirFinanciacion.setText(NbBundle.getMessage(ventas.class, "ventas.salirFinanciacion.text")); // NOI18N
        salirFinanciacion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        salirFinanciacion.setName("salirFinanciacion"); // NOI18N
        salirFinanciacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                salirFinanciacionActionPerformed(evt);
            }
        });

        GroupLayout jPanel45Layout = new GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(jPanel45Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(grabarFinanciacion, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salirFinanciacion, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(jPanel45Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGroup(jPanel45Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(grabarFinanciacion)
                    .addComponent(salirFinanciacion))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        GroupLayout financiacionLayout = new GroupLayout(financiacion.getContentPane());
        financiacion.getContentPane().setLayout(financiacionLayout);
        financiacionLayout.setHorizontalGroup(financiacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel44, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        financiacionLayout.setVerticalGroup(financiacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(financiacionLayout.createSequentialGroup()
                .addComponent(jPanel44, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel45, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        BCaja.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BCaja.setTitle(NbBundle.getMessage(ventas.class, "ventas.BCaja.title")); // NOI18N
        BCaja.setName("BCaja"); // NOI18N

        jPanel28.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel28.setName("jPanel28"); // NOI18N

        combocaja.setFont(new Font("Arial", 1, 14)); // NOI18N
        combocaja.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocaja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combocaja.setName("combocaja"); // NOI18N
        combocaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combocajaActionPerformed(evt);
            }
        });

        jTBuscarCaja.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCaja.setName("jTBuscarCaja"); // NOI18N
        jTBuscarCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarCajaActionPerformed(evt);
            }
        });
        jTBuscarCaja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarCajaKeyPressed(evt);
            }
        });

        GroupLayout jPanel28Layout = new GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(jPanel28Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(combocaja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCaja, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(jPanel28Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel28Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combocaja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCaja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane14.setName("jScrollPane14"); // NOI18N

        tablacaja.setModel(modelocaja);
        tablacaja.setName("tablacaja"); // NOI18N
        tablacaja.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablacajaMouseClicked(evt);
            }
        });
        tablacaja.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablacajaKeyPressed(evt);
            }
        });
        jScrollPane14.setViewportView(tablacaja);

        jPanel29.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel29.setName("jPanel29"); // NOI18N

        AceptarCaja.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarCaja.setText(NbBundle.getMessage(ventas.class, "ventas.AceptarCaja.text")); // NOI18N
        AceptarCaja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarCaja.setName("AceptarCaja"); // NOI18N
        AceptarCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarCajaActionPerformed(evt);
            }
        });

        SalirCaja.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirCaja.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCaja.text")); // NOI18N
        SalirCaja.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirCaja.setName("SalirCaja"); // NOI18N
        SalirCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirCajaActionPerformed(evt);
            }
        });

        GroupLayout jPanel29Layout = new GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(jPanel29Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCaja, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCaja, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(jPanel29Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCaja)
                    .addComponent(SalirCaja))
                .addContainerGap())
        );

        GroupLayout BCajaLayout = new GroupLayout(BCaja.getContentPane());
        BCaja.getContentPane().setLayout(BCajaLayout);
        BCajaLayout.setHorizontalGroup(BCajaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel28, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane14, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel29, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCajaLayout.setVerticalGroup(BCajaLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel28, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        retenciones.setName("retenciones"); // NOI18N

        jPanel30.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel30.setName("jPanel30"); // NOI18N

        GrabarRetencion.setText(NbBundle.getMessage(ventas.class, "ventas.GrabarRetencion.text")); // NOI18N
        GrabarRetencion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        GrabarRetencion.setName("GrabarRetencion"); // NOI18N
        GrabarRetencion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GrabarRetencionActionPerformed(evt);
            }
        });

        SalirRetencion.setText(NbBundle.getMessage(ventas.class, "ventas.SalirRetencion.text")); // NOI18N
        SalirRetencion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirRetencion.setName("SalirRetencion"); // NOI18N
        SalirRetencion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirRetencionActionPerformed(evt);
            }
        });

        GroupLayout jPanel30Layout = new GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(jPanel30Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(GrabarRetencion, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(SalirRetencion, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(jPanel30Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarRetencion)
                    .addComponent(SalirRetencion))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel31.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel31.setName("jPanel31"); // NOI18N

        jLabel33.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel33.text")); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N

        jLabel34.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel34.text")); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N

        nroretencion.setHorizontalAlignment(JTextField.RIGHT);
        nroretencion.setText(NbBundle.getMessage(ventas.class, "ventas.nroretencion.text")); // NOI18N
        nroretencion.setName("nroretencion"); // NOI18N
        nroretencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                nroretencionKeyPressed(evt);
            }
        });

        fecharetencion.setName("fecharetencion"); // NOI18N
        fecharetencion.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                fecharetencionFocusGained(evt);
            }
        });
        fecharetencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                fecharetencionKeyPressed(evt);
            }
        });

        jLabel35.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N

        nrofactura.setEditable(false);
        nrofactura.setHorizontalAlignment(JTextField.RIGHT);
        nrofactura.setText(NbBundle.getMessage(ventas.class, "ventas.sucursalret.text")); // NOI18N
        nrofactura.setName("nrofactura"); // NOI18N

        jLabel36.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel36.text")); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N

        sucursalret.setEditable(false);
        sucursalret.setHorizontalAlignment(JTextField.RIGHT);
        sucursalret.setText(NbBundle.getMessage(ventas.class, "ventas.sucursalret.text")); // NOI18N
        sucursalret.setName("sucursalret"); // NOI18N

        nombresucursalret.setEditable(false);
        nombresucursalret.setText(NbBundle.getMessage(ventas.class, "ventas.sucursalret.text")); // NOI18N
        nombresucursalret.setName("nombresucursalret"); // NOI18N

        jLabel37.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel37.text")); // NOI18N
        jLabel37.setName("jLabel37"); // NOI18N

        monedaret.setEditable(false);
        monedaret.setHorizontalAlignment(JTextField.RIGHT);
        monedaret.setText(NbBundle.getMessage(ventas.class, "ventas.sucursalret.text")); // NOI18N
        monedaret.setName("monedaret"); // NOI18N

        nombremonedaret.setEditable(false);
        nombremonedaret.setText(NbBundle.getMessage(ventas.class, "ventas.sucursalret.text")); // NOI18N
        nombremonedaret.setName("nombremonedaret"); // NOI18N

        jLabel38.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel38.text")); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N

        importe_sin_iva.setEditable(false);
        importe_sin_iva.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        importe_sin_iva.setHorizontalAlignment(JTextField.RIGHT);
        importe_sin_iva.setText(NbBundle.getMessage(ventas.class, "ventas.importe_sin_iva.text")); // NOI18N
        importe_sin_iva.setName("importe_sin_iva"); // NOI18N

        jLabel39.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel39.text")); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N

        importe_iva.setEditable(false);
        importe_iva.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        importe_iva.setHorizontalAlignment(JTextField.RIGHT);
        importe_iva.setText(NbBundle.getMessage(ventas.class, "ventas.importe_sin_iva.text")); // NOI18N
        importe_iva.setName("importe_iva"); // NOI18N

        jLabel40.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel40.text")); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N

        importe_gravado_total.setEditable(false);
        importe_gravado_total.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        importe_gravado_total.setHorizontalAlignment(JTextField.RIGHT);
        importe_gravado_total.setText(NbBundle.getMessage(ventas.class, "ventas.importe_sin_iva.text")); // NOI18N
        importe_gravado_total.setName("importe_gravado_total"); // NOI18N

        jLabel41.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel41.text")); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N

        porcentaje_retencion.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        porcentaje_retencion.setHorizontalAlignment(JTextField.RIGHT);
        porcentaje_retencion.setText(NbBundle.getMessage(ventas.class, "ventas.importe_sin_iva.text")); // NOI18N
        porcentaje_retencion.setName("porcentaje_retencion"); // NOI18N
        porcentaje_retencion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                porcentaje_retencionActionPerformed(evt);
            }
        });
        porcentaje_retencion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                porcentaje_retencionKeyPressed(evt);
            }
        });

        jLabel52.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel52.text")); // NOI18N
        jLabel52.setName("jLabel52"); // NOI18N

        valor_retencion.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        valor_retencion.setHorizontalAlignment(JTextField.RIGHT);
        valor_retencion.setText(NbBundle.getMessage(ventas.class, "ventas.importe_sin_iva.text")); // NOI18N
        valor_retencion.setName("valor_retencion"); // NOI18N

        enviarcta.setText(NbBundle.getMessage(ventas.class, "ventas.enviarcta.text")); // NOI18N
        enviarcta.setName("enviarcta"); // NOI18N

        creferenciaret.setEditable(false);
        creferenciaret.setHorizontalAlignment(JTextField.RIGHT);
        creferenciaret.setText(NbBundle.getMessage(ventas.class, "ventas.creferenciaret.text")); // NOI18N
        creferenciaret.setName("creferenciaret"); // NOI18N

        jLabel53.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel53.text")); // NOI18N
        jLabel53.setName("jLabel53"); // NOI18N

        clienteret.setEditable(false);
        clienteret.setText(NbBundle.getMessage(ventas.class, "ventas.clienteret.text")); // NOI18N
        clienteret.setName("clienteret"); // NOI18N

        nombreclienteret.setEditable(false);
        nombreclienteret.setText(NbBundle.getMessage(ventas.class, "ventas.nombreclienteret.text")); // NOI18N
        nombreclienteret.setName("nombreclienteret"); // NOI18N

        GroupLayout jPanel31Layout = new GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel36))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(nroretencion, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(creferenciaret, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(clienteret)
                                    .addComponent(sucursalret, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addComponent(nombresucursalret, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(nombreclienteret)))))
                        .addGap(130, 130, 130))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel38)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel53)
                                            .addComponent(jLabel37))))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(importe_iva, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(importe_sin_iva, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(fecharetencion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nrofactura, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addComponent(monedaret, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(nombremonedaret, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel39)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel52))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(porcentaje_retencion, GroupLayout.Alignment.LEADING)
                                    .addComponent(importe_gravado_total, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(valor_retencion, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addComponent(enviarcta)))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel31Layout.setVerticalGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nroretencion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(creferenciaret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sucursalret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursalret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(clienteret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombreclienteret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel53, GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(monedaret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombremonedaret, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel34)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel35))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fecharetencion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nrofactura, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(importe_sin_iva, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(importe_iva, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(importe_gravado_total, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(porcentaje_retencion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(valor_retencion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(enviarcta))
                .addContainerGap())
        );

        GroupLayout retencionesLayout = new GroupLayout(retenciones.getContentPane());
        retenciones.getContentPane().setLayout(retencionesLayout);
        retencionesLayout.setHorizontalGroup(retencionesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel31, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        retencionesLayout.setVerticalGroup(retencionesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, retencionesLayout.createSequentialGroup()
                .addComponent(jPanel31, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel30, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        actualizaTicket.setName("actualizaTicket"); // NOI18N
        actualizaTicket.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                actualizaTicketWindowActivated(evt);
            }
        });

        jPanel32.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel32.setName("jPanel32"); // NOI18N

        GrabarTicket.setText(NbBundle.getMessage(ventas.class, "ventas.GrabarTicket.text")); // NOI18N
        GrabarTicket.setCursor(new Cursor(Cursor.HAND_CURSOR));
        GrabarTicket.setName("GrabarTicket"); // NOI18N
        GrabarTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GrabarTicketActionPerformed(evt);
            }
        });

        SalirTicket.setText(NbBundle.getMessage(ventas.class, "ventas.SalirTicket.text")); // NOI18N
        SalirTicket.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirTicket.setName("SalirTicket"); // NOI18N
        SalirTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirTicketActionPerformed(evt);
            }
        });

        GroupLayout jPanel32Layout = new GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(jPanel32Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(GrabarTicket, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(SalirTicket, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(jPanel32Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarTicket)
                    .addComponent(SalirTicket))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel46.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel46.setName("jPanel46"); // NOI18N

        jLabel55.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel55.text")); // NOI18N
        jLabel55.setName("jLabel55"); // NOI18N

        jLabel56.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel56.text")); // NOI18N
        jLabel56.setName("jLabel56"); // NOI18N

        nroticket.setEditable(false);
        nroticket.setHorizontalAlignment(JTextField.RIGHT);
        nroticket.setText(NbBundle.getMessage(ventas.class, "ventas.nroticket.text")); // NOI18N
        nroticket.setName("nroticket"); // NOI18N
        nroticket.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                nroticketKeyPressed(evt);
            }
        });

        fechaticket.setEnabled(false);
        fechaticket.setName("fechaticket"); // NOI18N
        fechaticket.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                fechaticketFocusGained(evt);
            }
        });
        fechaticket.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                fechaticketKeyPressed(evt);
            }
        });

        jLabel57.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel57.text")); // NOI18N
        jLabel57.setName("jLabel57"); // NOI18N

        nrofacturaticket.setHorizontalAlignment(JTextField.RIGHT);
        nrofacturaticket.setText(NbBundle.getMessage(ventas.class, "ventas.nrofacturaticket.text")); // NOI18N
        nrofacturaticket.setName("nrofacturaticket"); // NOI18N
        nrofacturaticket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                nrofacturaticketActionPerformed(evt);
            }
        });

        jLabel58.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel58.text")); // NOI18N
        jLabel58.setName("jLabel58"); // NOI18N

        sucursalticket.setHorizontalAlignment(JTextField.RIGHT);
        sucursalticket.setText(NbBundle.getMessage(ventas.class, "ventas.sucursalticket.text")); // NOI18N
        sucursalticket.setName("sucursalticket"); // NOI18N
        sucursalticket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sucursalticketActionPerformed(evt);
            }
        });
        sucursalticket.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                sucursalticketKeyPressed(evt);
            }
        });

        nombresucursalticket.setEditable(false);
        nombresucursalticket.setText(NbBundle.getMessage(ventas.class, "ventas.nombresucursalticket.text")); // NOI18N
        nombresucursalticket.setName("nombresucursalticket"); // NOI18N

        jLabel59.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel59.text")); // NOI18N
        jLabel59.setName("jLabel59"); // NOI18N

        monedaticket.setEditable(false);
        monedaticket.setHorizontalAlignment(JTextField.RIGHT);
        monedaticket.setText(NbBundle.getMessage(ventas.class, "ventas.monedaticket.text")); // NOI18N
        monedaticket.setName("monedaticket"); // NOI18N

        nombremonedaticket.setEditable(false);
        nombremonedaticket.setText(NbBundle.getMessage(ventas.class, "ventas.nombremonedaticket.text")); // NOI18N
        nombremonedaticket.setName("nombremonedaticket"); // NOI18N

        jLabel62.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel62.text")); // NOI18N
        jLabel62.setName("jLabel62"); // NOI18N

        totalticket.setEditable(false);
        totalticket.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,##0.00"))));
        totalticket.setHorizontalAlignment(JTextField.RIGHT);
        totalticket.setText(NbBundle.getMessage(ventas.class, "ventas.totalticket.text")); // NOI18N
        totalticket.setName("totalticket"); // NOI18N

        creferenciaticket.setEditable(false);
        creferenciaticket.setHorizontalAlignment(JTextField.RIGHT);
        creferenciaticket.setText(NbBundle.getMessage(ventas.class, "ventas.creferenciaticket.text")); // NOI18N
        creferenciaticket.setName("creferenciaticket"); // NOI18N

        jLabel65.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel65.text")); // NOI18N
        jLabel65.setName("jLabel65"); // NOI18N

        clienteticket.setEditable(false);
        clienteticket.setHorizontalAlignment(JTextField.RIGHT);
        clienteticket.setText(NbBundle.getMessage(ventas.class, "ventas.clienteticket.text")); // NOI18N
        clienteticket.setName("clienteticket"); // NOI18N

        nombreclienteticket.setEditable(false);
        nombreclienteticket.setText(NbBundle.getMessage(ventas.class, "ventas.nombreclienteticket.text")); // NOI18N
        nombreclienteticket.setName("nombreclienteticket"); // NOI18N

        comprobanteticket.setEditable(false);
        comprobanteticket.setHorizontalAlignment(JTextField.RIGHT);
        comprobanteticket.setText(NbBundle.getMessage(ventas.class, "ventas.comprobanteticket.text")); // NOI18N
        comprobanteticket.setName("comprobanteticket"); // NOI18N

        jLabel60.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel60.text")); // NOI18N
        jLabel60.setName("jLabel60"); // NOI18N

        nombrecomprobanteticket.setEditable(false);
        nombrecomprobanteticket.setText(NbBundle.getMessage(ventas.class, "ventas.nombrecomprobanteticket.text")); // NOI18N
        nombrecomprobanteticket.setName("nombrecomprobanteticket"); // NOI18N

        GroupLayout jPanel46Layout = new GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel65)
                    .addComponent(jLabel59)
                    .addComponent(jLabel57)
                    .addComponent(jLabel62)
                    .addComponent(jLabel56)
                    .addComponent(jLabel60)
                    .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel58)
                        .addComponent(jLabel55)))
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(nroticket, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(nrofacturaticket, GroupLayout.Alignment.TRAILING)
                                .addComponent(fechaticket, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                .addComponent(clienteticket, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                .addComponent(monedaticket, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                                .addComponent(comprobanteticket, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                                .addComponent(totalticket, GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(sucursalticket, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addComponent(creferenciaticket, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 244, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(nombresucursalticket, GroupLayout.Alignment.LEADING)
                            .addComponent(nombremonedaticket, GroupLayout.Alignment.LEADING)
                            .addComponent(nombreclienteticket, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(nombrecomprobanteticket, GroupLayout.Alignment.LEADING))
                        .addGap(87, 87, 87))))
        );
        jPanel46Layout.setVerticalGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(creferenciaticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nroticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sucursalticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursalticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addGap(8, 8, 8)
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nrofacturaticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(clienteticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59)
                            .addComponent(monedaticket, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(nombreclienteticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nombremonedaticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(comprobanteticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrecomprobanteticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(totalticket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addGap(28, 28, 28))
        );

        GroupLayout actualizaTicketLayout = new GroupLayout(actualizaTicket.getContentPane());
        actualizaTicket.getContentPane().setLayout(actualizaTicketLayout);
        actualizaTicketLayout.setHorizontalGroup(actualizaTicketLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel32, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel46, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        actualizaTicketLayout.setVerticalGroup(actualizaTicketLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, actualizaTicketLayout.createSequentialGroup()
                .addComponent(jPanel46, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel32, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BObras.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BObras.setTitle(NbBundle.getMessage(ventas.class, "ventas.BObras.title")); // NOI18N
        BObras.setName("BObras"); // NOI18N

        jPanel47.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel47.setName("jPanel47"); // NOI18N

        combopais.setFont(new Font("Arial", 1, 14)); // NOI18N
        combopais.setModel(new DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combopais.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combopais.setName("combopais"); // NOI18N
        combopais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                combopaisActionPerformed(evt);
            }
        });

        jTBuscarPais.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPais.setText(NbBundle.getMessage(ventas.class, "ventas.jTBuscarPais.text")); // NOI18N
        jTBuscarPais.setName("jTBuscarPais"); // NOI18N
        jTBuscarPais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTBuscarPaisActionPerformed(evt);
            }
        });
        jTBuscarPais.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTBuscarPaisKeyPressed(evt);
            }
        });

        GroupLayout jPanel47Layout = new GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(jPanel47Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addComponent(combopais, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPais, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(jPanel47Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel47Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(combopais, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPais, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane15.setName("jScrollPane15"); // NOI18N

        tablapais.setModel(modelopais);
        tablapais.setName("tablapais"); // NOI18N
        tablapais.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablapaisMouseClicked(evt);
            }
        });
        tablapais.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablapaisKeyPressed(evt);
            }
        });
        jScrollPane15.setViewportView(tablapais);

        jPanel48.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel48.setName("jPanel48"); // NOI18N

        AceptarPais.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        AceptarPais.setText(NbBundle.getMessage(ventas.class, "ventas.AceptarPais.text")); // NOI18N
        AceptarPais.setCursor(new Cursor(Cursor.HAND_CURSOR));
        AceptarPais.setName("AceptarPais"); // NOI18N
        AceptarPais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarPaisActionPerformed(evt);
            }
        });

        SalirPais.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        SalirPais.setText(NbBundle.getMessage(ventas.class, "ventas.SalirPais.text")); // NOI18N
        SalirPais.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirPais.setName("SalirPais"); // NOI18N
        SalirPais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirPaisActionPerformed(evt);
            }
        });

        GroupLayout jPanel48Layout = new GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(jPanel48Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarPais, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirPais, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(jPanel48Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel48Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel48Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPais)
                    .addComponent(SalirPais))
                .addContainerGap())
        );

        GroupLayout BObrasLayout = new GroupLayout(BObras.getContentPane());
        BObras.getContentPane().setLayout(BObrasLayout);
        BObrasLayout.setHorizontalGroup(BObrasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BObrasLayout.createSequentialGroup()
                .addComponent(jPanel47, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane15, GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel48, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BObrasLayout.setVerticalGroup(BObrasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BObrasLayout.createSequentialGroup()
                .addComponent(jPanel47, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel48, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        preventas.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        preventas.setTitle(NbBundle.getMessage(ventas.class, "ventas.preventas.title")); // NOI18N
        preventas.setName("preventas"); // NOI18N

        jPanel49.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel49.setName("jPanel49"); // NOI18N

        jLabel61.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        jLabel61.setText(NbBundle.getMessage(ventas.class, "ventas.jLabel61.text")); // NOI18N
        jLabel61.setName("jLabel61"); // NOI18N

        ComboBuscarPreventa.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        ComboBuscarPreventa.setModel(new DefaultComboBoxModel<>(new String[] { "N° de Preventa", "Nombre del Cliente" }));
        ComboBuscarPreventa.setName("ComboBuscarPreventa"); // NOI18N

        FiltrarPreventa.setName("FiltrarPreventa"); // NOI18N
        FiltrarPreventa.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                FiltrarPreventaKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                FiltrarPreventaKeyReleased(evt);
            }
        });

        GroupLayout jPanel49Layout = new GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(jPanel49Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel61, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(ComboBuscarPreventa, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FiltrarPreventa, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(jPanel49Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel49Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(ComboBuscarPreventa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(FiltrarPreventa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel50.setBorder(BorderFactory.createEtchedBorder());
        jPanel50.setName("jPanel50"); // NOI18N

        jScrollPane16.setName("jScrollPane16"); // NOI18N

        tablapreventa.setModel(modelopreventa);
        tablapreventa.setName("tablapreventa"); // NOI18N
        tablapreventa.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablapreventaMouseClicked(evt);
            }
        });
        tablapreventa.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablapreventaKeyPressed(evt);
            }
        });
        jScrollPane16.setViewportView(tablapreventa);

        GroupLayout jPanel50Layout = new GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(jPanel50Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(jPanel50Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );

        jPanel51.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        jPanel51.setName("jPanel51"); // NOI18N

        AceptarPreventa.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        AceptarPreventa.setText(NbBundle.getMessage(ventas.class, "ventas.AceptarPreventa.text")); // NOI18N
        AceptarPreventa.setName("AceptarPreventa"); // NOI18N
        AceptarPreventa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AceptarPreventaActionPerformed(evt);
            }
        });

        SalirPreventa.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        SalirPreventa.setText(NbBundle.getMessage(ventas.class, "ventas.SalirPreventa.text")); // NOI18N
        SalirPreventa.setName("SalirPreventa"); // NOI18N
        SalirPreventa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirPreventaActionPerformed(evt);
            }
        });

        GroupLayout jPanel51Layout = new GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(jPanel51Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(AceptarPreventa, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(SalirPreventa, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(jPanel51Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel51Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPreventa)
                    .addComponent(SalirPreventa))
                .addContainerGap())
        );

        GroupLayout preventasLayout = new GroupLayout(preventas.getContentPane());
        preventas.getContentPane().setLayout(preventasLayout);
        preventasLayout.setHorizontalGroup(preventasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel49, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel50, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel51, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        preventasLayout.setVerticalGroup(preventasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(preventasLayout.createSequentialGroup()
                .addComponent(jPanel49, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel50, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel51, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setName("frame_clientes"); // NOI18N
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(WindowEvent evt) {
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        panel1.setColorPrimario(new Color(102, 153, 255));
        panel1.setColorSecundario(new Color(0, 204, 255));
        panel1.setName("panel1"); // NOI18N

        etiquetaventas.setBackground(new Color(255, 255, 255));
        etiquetaventas.setText(NbBundle.getMessage(ventas.class, "ventas.etiquetaventas.text")); // NOI18N
        etiquetaventas.setName("etiquetaventas"); // NOI18N

        comboventa.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        comboventa.setModel(new DefaultComboBoxModel(new String[] { "Factura", "Sucursal", "Nombre Cliente" }));
        comboventa.setName("comboventa"); // NOI18N
        comboventa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                comboventaActionPerformed(evt);
            }
        });

        buscarcadena.setFont(new Font("Tahoma", 0, 14)); // NOI18N
        buscarcadena.setName("buscarcadena"); // NOI18N
        buscarcadena.setSelectionColor(new Color(0, 63, 62));
        buscarcadena.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                buscarcadenaFocusGained(evt);
            }
        });
        buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                buscarcadenaKeyPressed(evt);
            }
        });

        GroupLayout panel1Layout = new GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(etiquetaventas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(comboventa, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(585, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboventa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetaventas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        Modificar.setBackground(new Color(255, 255, 255));
        Modificar.setFont(new Font("Tahoma", 1, 10)); // NOI18N
        Modificar.setText(NbBundle.getMessage(ventas.class, "ventas.Modificar.text")); // NOI18N
        Modificar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Modificar.setName("Modificar"); // NOI18N
        Modificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        Agregar.setBackground(new Color(255, 255, 255));
        Agregar.setFont(new Font("Tahoma", 1, 10)); // NOI18N
        Agregar.setText(NbBundle.getMessage(ventas.class, "ventas.Agregar.text")); // NOI18N
        Agregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Agregar.setName("Agregar"); // NOI18N
        Agregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Eliminar.setBackground(new Color(255, 255, 255));
        Eliminar.setFont(new Font("Tahoma", 1, 10)); // NOI18N
        Eliminar.setText(NbBundle.getMessage(ventas.class, "ventas.Eliminar.text")); // NOI18N
        Eliminar.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.Eliminar.toolTipText")); // NOI18N
        Eliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Eliminar.setName("Eliminar"); // NOI18N
        Eliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        Listar.setBackground(new Color(255, 255, 255));
        Listar.setFont(new Font("Tahoma", 1, 10)); // NOI18N
        Listar.setText(NbBundle.getMessage(ventas.class, "ventas.Listar.text")); // NOI18N
        Listar.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.Listar.toolTipText")); // NOI18N
        Listar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Listar.setName("Listar"); // NOI18N
        Listar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ListarActionPerformed(evt);
            }
        });

        SalirCompleto.setBackground(new Color(255, 255, 255));
        SalirCompleto.setFont(new Font("Tahoma", 1, 10)); // NOI18N
        SalirCompleto.setText(NbBundle.getMessage(ventas.class, "ventas.SalirCompleto.text")); // NOI18N
        SalirCompleto.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.SalirCompleto.toolTipText")); // NOI18N
        SalirCompleto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        SalirCompleto.setName("SalirCompleto"); // NOI18N
        SalirCompleto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                SalirCompletoActionPerformed(evt);
            }
        });

        idControl.setEditable(false);
        idControl.setName("idControl"); // NOI18N

        jPanel3.setBorder(BorderFactory.createTitledBorder(NbBundle.getMessage(ventas.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel3.setText(NbBundle.getMessage(ventas.class, "libroventaconsolidado.jLabel1.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        dInicial.setName("dInicial"); // NOI18N

        jLabel8.setText(NbBundle.getMessage(ventas.class, "libroventaconsolidado.jLabel2.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        dFinal.setName("dFinal"); // NOI18N

        refrescar.setText(NbBundle.getMessage(ventas.class, "ventas.refrescar.text")); // NOI18N
        refrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refrescar.setName("refrescar"); // NOI18N
        refrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refrescarActionPerformed(evt);
            }
        });

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(dInicial, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                        .addComponent(dFinal, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(refrescar, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFinal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(refrescar)
                .addContainerGap())
        );

        visualizar.setBackground(new Color(255, 255, 255));
        visualizar.setFont(new Font("Tahoma", 1, 10)); // NOI18N
        visualizar.setText(NbBundle.getMessage(ventas.class, "ventas.visualizar.text")); // NOI18N
        visualizar.setToolTipText(NbBundle.getMessage(ventas.class, "ventas.visualizar.toolTipText")); // NOI18N
        visualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visualizar.setName("visualizar"); // NOI18N
        visualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                visualizarActionPerformed(evt);
            }
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(SalirCompleto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Modificar, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Eliminar, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
                            .addComponent(visualizar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(idControl, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(Agregar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Modificar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Eliminar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Listar)
                .addGap(8, 8, 8)
                .addComponent(visualizar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SalirCompleto)
                .addGap(18, 18, 18)
                .addComponent(idControl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        tablaventas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablaventas.setModel(modelo);
        tablaventas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        tablaventas.setName("tablaventas"); // NOI18N
        tablaventas.setSelectionBackground(new Color(51, 204, 255));
        tablaventas.setSelectionForeground(new Color(0, 0, 255));
        tablaventas.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                tablaventasFocusGained(evt);
            }
            public void focusLost(FocusEvent evt) {
                tablaventasFocusLost(evt);
            }
        });
        tablaventas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tablaventasMouseClicked(evt);
            }
        });
        tablaventas.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                tablaventasPropertyChange(evt);
            }
        });
        tablaventas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                tablaventasKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaventas);

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText(NbBundle.getMessage(ventas.class, "ventas.jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem6.setText(NbBundle.getMessage(ventas.class, "ventas.jMenuItem6.text")); // NOI18N
        jMenuItem6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jMenuItem6.setName("jMenuItem6"); // NOI18N
        jMenuItem6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jSeparator5.setName("jSeparator5"); // NOI18N
        jMenu1.add(jSeparator5);

        jMenuItem7.setText(NbBundle.getMessage(ventas.class, "ventas.jMenuItem7.text")); // NOI18N
        jMenuItem7.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jMenuItem7.setName("jMenuItem7"); // NOI18N
        jMenuItem7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jSeparator6.setName("jSeparator6"); // NOI18N
        jMenu1.add(jSeparator6);

        jMenuItem2.setText(NbBundle.getMessage(ventas.class, "ventas.jMenuItem2.text")); // NOI18N
        jMenuItem2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jMenu1.add(jSeparator2);

        jMenuItem3.setText(NbBundle.getMessage(ventas.class, "ventas.jMenuItem3.text")); // NOI18N
        jMenuItem3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jMenu1.add(jSeparator3);

        jMenuItem1.setText(NbBundle.getMessage(ventas.class, "ventas.jMenuItem1.text")); // NOI18N
        jMenuItem1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenu1.add(jSeparator1);

        jMenuItem4.setText(NbBundle.getMessage(ventas.class, "ventas.jMenuItem4.text")); // NOI18N
        jMenuItem4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jSeparator4.setName("jSeparator4"); // NOI18N
        jMenu1.add(jSeparator4);

        jMenuItem5.setText(NbBundle.getMessage(ventas.class, "ventas.jMenuItem5.text")); // NOI18N
        jMenuItem5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
    }

    private void TitPais() {
        modelopais.addColumn("Código");
        modelopais.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelopais.getColumnCount(); i++) {
            tablapais.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapais.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapais.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapais.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablapais.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtrocaja(int nNumeroColumna) {
        trsfiltrocaja.setRowFilter(RowFilter.regexFilter(this.jTBuscarCaja.getText(), nNumeroColumna));
    }

    private void TituloPreventas() {
        modelopreventa.addColumn("Número"); //0
        modelopreventa.addColumn("Fecha");//1
        modelopreventa.addColumn("Nombre del Cliente");//2
        modelopreventa.addColumn("Importe");//3
        modelopreventa.addColumn("cliente");//4
        modelopreventa.addColumn("ruc");//5
        modelopreventa.addColumn("direccion");//6
        modelopreventa.addColumn("vendedor");//7
        modelopreventa.addColumn("nombrevendedor");//8
        modelopreventa.addColumn("comprobante");//9
        modelopreventa.addColumn("nombrecomprobante");//10
        modelopreventa.addColumn("observacion");//11
        modelopreventa.addColumn("obra");//12
        modelopreventa.addColumn("nombreobra");//13
        modelopreventa.addColumn("Descuento");//14
        modelopreventa.addColumn("Aprobado");//15
        modelopreventa.addColumn("Caja");//16

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        int[] anchos = {100, 90, 150, 100, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        for (int i = 0; i < modelopreventa.getColumnCount(); i++) {
            tablapreventa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapreventa.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapreventa.getTableHeader().setFont(new Font("Arial Black", 1, 11));
        this.tablapreventa.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablapreventa.getColumnModel().getColumn(1).setCellRenderer(TablaCentro);
        this.tablapreventa.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);

        //recorro todas las columnas que voy a hacer invisibles
        for (int i = 4; i < 17; i++) {
            this.tablapreventa.getColumnModel().getColumn(i).setMaxWidth(0);
            this.tablapreventa.getColumnModel().getColumn(i).setMinWidth(0);
            this.tablapreventa.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
            this.tablapreventa.getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);
        }
        // Hacemos Invisible la Celda de Costos de los Productos

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapreventa.setFont(font);
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

    public void limpiarfinanciacion() {
        autorizacion.setText("");
        monto.setText("0");
        ncuotas.setText("0");
        montocuota.setText("0");
        primeracuota.setCalendar(c2);
        totalfinanciado.setText("0");
    }

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.jTBuscarbanco.getText(), nNumeroColumna));
    }

    public void filtroformapago(int nNumeroColumna) {
        trsfiltroformapago.setRowFilter(RowFilter.regexFilter(this.jTBuscarForma.getText(), nNumeroColumna));
    }

    public void sumarfinanciacion() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double supago = 0.00;
        double sumpago = 0.00;

        String csupago = "";
        String ctotalcobro = "";

        int totalRow = tablafinanciacion.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL A PAGAR
            csupago = String.valueOf(tablafinanciacion.getValueAt(i, 1));
            csupago = csupago.replace(".", "").replace(",", ".");
            supago = Double.valueOf(csupago);
            sumpago += supago;
        }
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalfinanciado.setText(formatea.format(sumpago));
        if (sumpago > 0) {
            this.EditarF.setEnabled(true);
            this.BorrarF.setEnabled(true);
        } else {
            this.EditarF.setEnabled(false);
            this.BorrarF.setEnabled(false);
        }
        //formato.format(sumatoria1);
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
        if (sumpago > 0) {
            this.Upditem.setEnabled(true);
            this.DelItem.setEnabled(true);
        } else {
            this.Upditem.setEnabled(false);
            this.DelItem.setEnabled(false);
        }
        //formato.format(sumatoria1);
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

    public void CargarTituloFinanciacion() {
        modelofinanciacion.addColumn("N° Autorización");
        modelofinanciacion.addColumn("Monto Autorizado");
        modelofinanciacion.addColumn("N° Cuotas");
        modelofinanciacion.addColumn("Monto Cuota");
        modelofinanciacion.addColumn("1ra. Cuota");
        int[] anchos = {120, 120, 80, 120, 100};
        for (int i = 0; i < modelofinanciacion.getColumnCount(); i++) {
            this.tablafinanciacion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablafinanciacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablafinanciacion.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlineoCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlineoCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        this.tablafinanciacion.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablafinanciacion.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablafinanciacion.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablafinanciacion.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablafinanciacion.getColumnModel().getColumn(4).setCellRenderer(AlineoCentro);
    }


    private void comboventaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_comboventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboventaActionPerformed

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText()).toUpperCase();
                buscarcadena.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboventa.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por factura
                    case 1:
                        indiceColumnaTabla = 5;
                        break;//por sucursal
                    case 2:
                        indiceColumnaTabla = 6;
                        break;//por nombre del cliente
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablaventas.getModel());
        tablaventas.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void SalirCompletoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

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
        factura.setEnabled(true);
        this.limpiar();
        this.limpiaritems();
        this.limpiarformapago();
        //Establecemos un título para el jDialog

        int cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }

        int cantidadReg = modelopagos.getRowCount();
        for (int i = 1; i <= cantidadReg; i++) {
            modelopagos.removeRow(0);
        }

        int cantidadR = modelofinanciacion.getRowCount();
        for (int i = 1; i <= cantidadR; i++) {
            modelofinanciacion.removeRow(0);
        }

        detalle_venta.setModal(true);
        detalle_venta.setSize(980, 709);
        detalle_venta.setTitle("Generar Nueva Venta");
        detalle_venta.setLocationRelativeTo(null);
        detalle_venta.setVisible(true);
        sucursal.requestFocus();
        this.refrescar.doClick();
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = null;
        config = configDAO.consultar();
        this.sucursal.setText(String.valueOf(config.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(config.getSucursaldefecto().getNombre());
        this.vencetimbrado.setDate(config.getSucursaldefecto().getVencetimbrado());
        this.nrotimbrado.setText(config.getSucursaldefecto().getNrotimbrado());

        String cBoca = config.getSucursaldefecto().getExpedicion().trim();
        Double nFactura = config.getSucursaldefecto().getFactura();
        System.out.println("Factura " + nFactura);
        int n = (int) nFactura.doubleValue();
        String formatString = String.format("%%0%dd", 7);
        String formattedString = String.format(formatString, n);
        this.factura.setText(cBoca + "-" + formattedString);
        this.moneda.setText(String.valueOf(config.getMonedadefecto().getCodigo()));
        this.nombremoneda.setText(config.getMonedadefecto().getNombre());
        this.cotizacion.setText(formatea.format(config.getMonedadefecto().getVenta()));

        this.vendedor.setText(String.valueOf(config.getVendedordefecto().getCodigo()));
        this.nombrevendedor.setText(config.getVendedordefecto().getNombre());

        this.comprobante.setText(String.valueOf(config.getComprobantedefecto().getCodigo()));
        this.nombrecomprobante.setText(String.valueOf(config.getComprobantedefecto().getNombre()));

        this.cliente.setText(String.valueOf(config.getClientedefecto().getCodigo()));
        this.nombrecliente.setText(config.getClientedefecto().getNombre());
        this.direccioncliente.setText(config.getClientedefecto().getDireccion());
        this.obra.setText("0");
        this.nombreobra.setText("");

        this.caja.setText(String.valueOf(config.getCajadefecto().getCodigo()));
        this.nombrecaja.setText(config.getCajadefecto().getNombre());
        this.fecha.setCalendar(c2);
        this.primervence.setCalendar(c2);
        this.cuotas.setText("0");
        this.preventa.setText("0");
        this.observacion.setText("");
        this.exentas.setText("0");
        this.gravadas5.setText("0");
        this.gravadas10.setText("0");
        this.totalneto.setText("0");
//      this.imagen.setText("Imagen");
        this.editaritem.setEnabled(false);
        this.delitem.setEnabled(false);
        this.nombrebanco.setText("");
        this.importecheque.setText("0");
        this.nrocheque.setText("0");
        this.totalfinanciado.setText("0");
        fotoProducto.setImagen(imagenfondo.getImage());
        if (config.getModificarprecio() == 0) {
            precio.setEnabled(false);
        } else {
            precio.setEnabled(true);
        }

    }
    private void tablaventasKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablaventasKeyPressed
        int nFila = this.tablaventas.getSelectedRow();
        this.idControl.setText(this.tablaventas.getValueAt(nFila, 0).toString());

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaventasKeyPressed

    private void tablaventasMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablaventasMouseClicked
        int nFila = this.tablaventas.getSelectedRow();
        this.idControl.setText(this.tablaventas.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaventasMouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        cadenabuscar = this.buscarcadena.getText();
        modo.setText("2");
        int nFila = tablaventas.getSelectedRow();
        String cReferencia = tablaventas.getValueAt(nFila, 0).toString();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaventas.requestFocus();
            return;
        }

        cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
        cuenta_clientes saldo = null;
        try {
            saldo = saldoDAO.SaldoMovimiento(cReferencia);
            if (saldo.getDocumento() != null) {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                return;
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            ventaDAO veDAO = new ventaDAO();
            venta ve = null;
            try {
                ve = veDAO.buscarIdFerremax(cReferencia);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (ve != null) {
                //SE CARGAN LOS DATOS DE LA CABECERA
                creferencia.setText(ve.getCreferencia());
                sucursal.setText(String.valueOf(ve.getSucursal().getCodigo()));
                nombresucursal.setText(ve.getSucursal().getNombre());
                factura.setText(ve.getFormatofactura());
                fecha.setDate(ve.getFecha());
                cliente.setText(String.valueOf(ve.getCliente().getCodigo()));
                nombrecliente.setText(ve.getCliente().getNombre());
                direccioncliente.setText(ve.getCliente().getDireccion());
                vendedor.setText(String.valueOf(ve.getVendedor().getCodigo()));
                nombrevendedor.setText(String.valueOf(ve.getVendedor().getNombre()));
                caja.setText(String.valueOf(ve.getCaja().getCodigo()));
                nombrecaja.setText(ve.getCaja().getNombre());
                comprobante.setText(String.valueOf(ve.getComprobante().getCodigo()));
                if (Integer.valueOf(comprobante.getText()) != 1) {
                    this.cuotas.setText("1");
                }
                nombrecomprobante.setText(String.valueOf(ve.getComprobante().getNombre()));
                moneda.setText(formatosinpunto.format(ve.getMoneda().getCodigo()));
                nombremoneda.setText(ve.getMoneda().getNombre());
                cotizacion.setText(formatea.format(ve.getCotizacion()));
                nrotimbrado.setText(formatosinpunto.format(ve.getNrotimbrado()));
                vencetimbrado.setDate(ve.getVencimientotimbrado());
                preventa.setText(String.valueOf(ve.getPreventa()));
                exentas.setText(formatea.format(ve.getExentas()));
                gravadas5.setText(formatea.format(ve.getGravadas5()));
                gravadas10.setText(formatea.format(ve.getGravadas10()));
                totalneto.setText(formatea.format(ve.getTotalneto()));
                primervence.setDate(ve.getVencimiento());
                totalvalores.setText(formatea.format(ve.getSupago()));
                cuotas.setText(formatosinpunto.format(ve.getCuotas()));
                observacion.setText(ve.getObservacion());
                totalfinanciado.setText(formatea.format(ve.getFinanciado()));
                obra.setText(String.valueOf(ve.getCentro()));
                nombreobra.setText(ve.getNombreobra());

                // SE CARGAN LOS DETALLES DE VENTAS
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

                // SE CARGAN LOS DETALLES DE COBROS SI LOS HAY
                detalle_forma_cobroDAO fDAO = new detalle_forma_cobroDAO();

                cantidadRegistro = modelopagos.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelopagos.removeRow(0);
                }
                try {

                    for (detalle_forma_cobro ft : fDAO.MostrarDetalle(idControl.getText())) {
                        String Datos[] = {String.valueOf(ft.getForma().getCodigo()), ft.getForma().getNombre(), String.valueOf(ft.getBanco().getCodigo()), ft.getBanco().getNombre(), ft.getNrocheque(), formatoFecha.format(ft.getConfirmacion()), formatea.format(ft.getNetocobrado())};
                        modelopagos.addRow(Datos);
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                int tFilas = tablapagos.getRowCount();
                if (tFilas > 0) {
                    Upditem.setEnabled(true);
                    DelItem.setEnabled(true);
                } else {
                    Upditem.setEnabled(false);
                    DelItem.setEnabled(false);
                }

                // SE CARGAN LOS DETALLES DE FINANCIACION SI LOS HAY
                detalle_forma_cuotasDAO cuotasDAO = new detalle_forma_cuotasDAO();

                cantidadRegistro = modelofinanciacion.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelofinanciacion.removeRow(0);
                }
                try {

                    for (detalle_forma_cuotas cuot : cuotasDAO.MostrarDetalle(idControl.getText())) {
                        String Datos[] = {String.valueOf(cuot.getAutorizacion()), formatea.format(cuot.getMonto()), formatea.format(cuot.getNcuotas()), formatea.format(cuot.getMontocuota()), formatoFecha.format(cuot.getPrimeracuota())};
                        modelofinanciacion.addRow(Datos);
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                tFilas = tablafinanciacion.getRowCount();
                if (tFilas > 0) {
                    EditarF.setEnabled(true);
                    BorrarF.setEnabled(true);
                } else {
                    EditarF.setEnabled(false);
                    BorrarF.setEnabled(false);
                }
                this.sumatoria();
                this.sumarforma();
                this.sumarfinanciacion();

                detalle_venta.setModal(true);
                detalle_venta.setSize(980, 709);
                //Establecemos un título para el jDialog
                detalle_venta.setTitle("Generar Nueva Venta");
                detalle_venta.setLocationRelativeTo(null);
                detalle_venta.setVisible(true);
                sucursal.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
            }
            this.refrescar.doClick();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void tablaventasFocusGained(FocusEvent evt) {//GEN-FIRST:event_tablaventasFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaventasFocusGained

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
            int nFila = tablaventas.getSelectedRow();
            String num = tablaventas.getValueAt(nFila, 0).toString();
            //VERIFICAMOS SI EXISTE ALGUN PAGO
            cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
            cuenta_clientes saldo = null;
            try {
                saldo = saldoDAO.SaldoMovimiento(num);
                if (saldo.getDocumento() != null) {
                    JOptionPane.showMessageDialog(null, "La Operación ya no puede Eliminarse");
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
                detalle_forma_cobroDAO cob = new detalle_forma_cobroDAO();
                try {
                    venta vt = vl.buscarId(num);
                    if (vt == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        vl.borrarDetalleCuenta(num);
                        det.borrarDetalleVenta(num);
                        cob.borrarDetalleFormaPago(num);
                        vl.borrarVenta(Config.cToken, num);
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
            this.refrescar.doClick();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }

    }//GEN-LAST:event_EliminarActionPerformed

    private void tablaventasFocusLost(FocusEvent evt) {//GEN-FIRST:event_tablaventasFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaventasFocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        /*      GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();*/

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir este Documento ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        double nt=0;
        String num ="";

        if (ret == 0) {
            int nFila = tablaventas.getSelectedRow();
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();

            cajaDAO cajDAO = new cajaDAO();
            caja caj = new caja();
            ventaDAO veDAO = new ventaDAO();
            venta ve = null;

            try {
                caj = cajDAO.buscarId(Integer.valueOf(tablaventas.getValueAt(nFila, 13).toString()));
                ve = veDAO.CalcularTotalFerremax(tablaventas.getValueAt(nFila, 0).toString());
                nt = ve.getTotaldescuento().doubleValue();
                nt=Math.round(nt);
                num=formatea.format(nt);
                num=num.replace(".","");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            BuscadorImpresora printer = new BuscadorImpresora();

            PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

            if (printService.length > 0)//si existen impresoras
            {
                //se elige la impresora
                PrintService impresora = printer.buscar(caj.getImpresoracaja());
                if (impresora != null) //Si se selecciono una impresora
                {
                    try {
                        Map parameters = new HashMap();
                        //esto para el JasperReport
                        numero_a_letras numero = new numero_a_letras();
                        parameters.put("cRuc", config.getRuc());
                        parameters.put("cTelefono", config.getTelefono());
                        parameters.put("cDireccion", config.getDireccion());
                        parameters.put("Letra", numero.Convertir(num, true, 1));
                        parameters.put("cNombreEmpresa", config.getEmpresa());
                        parameters.put("cReferencia", tablaventas.getValueAt(nFila, 0).toString());
                        JasperReport jasperReport;
                        JasperPrint jasperPrint;
                        //se carga el reporte
                        //URL in = this.getClass().getResource("reporte.jasper");
                        URL url = getClass().getClassLoader().getResource("Reports/" + caj.getNombrefactura().trim());

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
            }
        }
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_venta.setVisible(false);
        this.Grabar.setEnabled(true);
        this.nuevoitem.setEnabled(true);
        this.editaritem.setEnabled(true);
        this.delitem.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito
        /*     ventas_anuladasDAO vnullDAO = new ventas_anuladasDAO();
        ventas_anuladas vnull = null;
        try {
            vnull = vnullDAO.buscarId(factura.getText());
            if (vnull != null) {
                JOptionPane.showMessageDialog(null, "La Factura está Anulada");
                this.factura.getText();
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }*/

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

        if (this.caja.getText().isEmpty() || this.caja.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Caja");
            this.caja.requestFocus();
            return;
        }

        if (this.nrotimbrado.getText().isEmpty() || this.nrotimbrado.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Número de Timbrado");
            this.nrotimbrado.requestFocus();
            return;
        }

        String cTotal = totalneto.getText();
        cTotal = cTotal.replace(".", "").replace(",", ".");
        if (cTotal.equals("0") || cTotal.isEmpty()) {
            this.cliente.requestFocus();
            JOptionPane.showMessageDialog(null, "No Existe detalle de Ventas");
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Venta ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            if (this.cuotas.getText().isEmpty()) {
                this.cuotas.setText("0");
            }
            if (this.preventa.getText().isEmpty()) {
                this.preventa.setText("0");
            }

            if (Integer.valueOf(modo.getText()) == 1) {
                UUID id = new UUID();
                referencia = UUID.crearUUID();
                referencia = referencia.substring(1, 25);
            } else {
                referencia = creferencia.getText();
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
            vendedorDAO veDAO = new vendedorDAO();
            vendedor ve = null;
            giraduriaDAO giDAO = new giraduriaDAO();
            giraduria gi = null;
            cajaDAO caDAO = new cajaDAO();
            caja ca = null;
            venta v = new venta();
            ventaDAO grabarventa = new ventaDAO();
            venta verificarVta = null;

            try {
                suc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
                cli = cliDAO.buscarId(Integer.valueOf(cliente.getText()));
                com = coDAO.buscarId(Integer.valueOf(comprobante.getText()));
                mn = mnDAO.buscarId(Integer.valueOf(moneda.getText()));
                gi = giDAO.buscarId(1); //verificar y tener en cuenta para ingreso por teclado o asociar a Cliente
                ve = veDAO.buscarId(Integer.valueOf(vendedor.getText()));
                ca = caDAO.buscarId(Integer.valueOf(caja.getText()));
                verificarVta = grabarventa.VerificarFactura(factura.getText());

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            if (Integer.valueOf(modo.getText()) == 1) {
                if (verificarVta.getFormatofactura() != null) {
                    JOptionPane.showMessageDialog(null, "N° Factura ya Existe, verifique");
                    this.nrofactura.requestFocus();
                    return;
                }
            }

            //Capturamos los Valores BigDecimal
            String cExentas = exentas.getText();
            cExentas = cExentas.replace(".", "").replace(",", ".");
            BigDecimal nExentas = new BigDecimal(cExentas);

            String cGravadas10 = gravadas10.getText();
            cGravadas10 = cGravadas10.replace(".", "").replace(",", ".");
            BigDecimal nGravadas10 = new BigDecimal(cGravadas10);

            String cGravadas5 = gravadas5.getText();
            cGravadas5 = cGravadas5.replace(".", "").replace(",", ".");
            BigDecimal nGravadas5 = new BigDecimal(cGravadas5);

            cTotalValores = totalvalores.getText();
            cTotalValores = cTotalValores.replace(".", "").replace(",", ".");
            BigDecimal nSupago = new BigDecimal(cTotalValores);

            cTotalNeto = totalneto.getText();
            cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
            BigDecimal nTotalNeto = new BigDecimal(cTotalNeto);

            String cFinanciado = this.totalfinanciado.getText();
            cFinanciado = cFinanciado.replace(".", "").replace(",", ".");
            BigDecimal nFinanciado = new BigDecimal(cFinanciado);

            String cCotizacion = cotizacion.getText();
            cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
            cNumeroFactura = factura.getText();
            cNumeroFactura = cNumeroFactura.replace("-", "");
            BigDecimal nCotizacion = new BigDecimal(cCotizacion);
            String cContadorFactura = cNumeroFactura.substring(6, 13);
            v.setCreferencia(referencia);
            v.setFecha(FechaProceso);
            v.setFormatofactura(factura.getText());
            v.setFactura(Double.valueOf(cNumeroFactura));
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
            v.setCuotas(Integer.valueOf(cuotas.getText()));
            v.setFinanciado(nTotalNeto.subtract(nSupago));
            v.setObservacion(observacion.getText());
            v.setSucambio(new BigDecimal("0"));
            v.setSupago(nSupago);
            v.setVencimientotimbrado(FechaVenceTimbrado);
            v.setNrotimbrado(Integer.valueOf(nrotimbrado.getText()));
            v.setIdusuario(Integer.valueOf(Config.CodUsuario));
            v.setPreventa(Integer.valueOf(preventa.getText()));
            v.setTurno(1);
            v.setCentro(Integer.valueOf(obra.getText()));

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
                cProducto = String.valueOf(modelodetalle.getValueAt(i, 0));
                try {
                    p = producto.BuscarProductoBasico(cProducto);
                    if (p.getCodigo() == null) {
                        System.out.println("no se encontro " + cProducto);
                        JOptionPane.showMessageDialog(null, "Producto " + cProducto + " no Existe");
                        this.sucursal.requestFocus();
                        return;
                    }

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

                String linea = "{dreferencia : '" + referencia + "',"
                        + "codprod : '" + cProducto + "',"
                        + "prcosto : " + cCosto + ","
                        + "cantidad : " + cCantidad + ","
                        + "precio : " + cPrecio + ","
                        + "monto : " + cMonto + ","
                        + "impiva: " + cIvaItem + ","
                        + "porcentaje : " + civa + ","
                        + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";

            ///EN CASO QUE LA VENTA SEA A CREDITO 
            String detacuota = "[";
            if (Integer.valueOf(cuotas.getText()) > 0) {
                Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
                calendar.setTime(primervence.getDate()); // Capturamos en el setTime el valor de la fecha ingresada

                String iddoc = null;
                String cImporteCuota = null;
                if (Integer.valueOf(moneda.getText()) == 1) {
                    cImporteCuota = String.valueOf(Math.round((Double.valueOf(cTotalNeto) - Double.valueOf(cTotalValores)) / Double.valueOf(cuotas.getText())));
                } else {
                    cImporteCuota = String.valueOf((Double.valueOf(cTotalNeto) - Double.valueOf(cTotalValores)) / Double.valueOf(cuotas.getText()));
                }

                detacuota = "[";
                for (int i = 1; i <= Integer.valueOf(cuotas.getText()); i++) {
                    vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                    Date VenceCuota = ODate.de_java_a_sql(vencimientos.getDate());
                    iddoc = UUID.crearUUID();
                    iddoc = iddoc.substring(1, 25);

                    String lineacuota = "{iddocumento : '" + iddoc + "',"
                            + "creferencia : '" + referencia + "',"
                            + "documento : " + cNumeroFactura + ","
                            + "fecha : " + FechaProceso + ","
                            + "vencimiento : " + VenceCuota + ","
                            + "cliente : " + cliente.getText() + ","
                            + "sucursal: " + sucursal.getText() + ","
                            + "moneda : " + moneda.getText() + ","
                            + "comprobante : " + com.getCodigo() + ","
                            + "vendedor : " + vendedor.getText() + ","
                            + "idedificio : " + obra.getText() + ","
                            + "importe : " + cImporteCuota + ","
                            + "numerocuota : " + 1 + ","
                            + "cuota : " + 1 + ","
                            + "saldo : " + cImporteCuota
                            + "},";

                    detacuota += lineacuota;
                    calendar.setTime(vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                    venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior
                    int mes = venceanterior.getCalendar().get(Calendar.MONTH) + 1;
                    int dia = venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
                    calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0
                    if (mes == 2 && dia == 28) {
                        calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días                            //el vencimiento
                    }
                    vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                }
                if (!detacuota.equals("[")) {
                    detacuota = detacuota.substring(0, detacuota.length() - 1);
                }
                detacuota += "]";
                System.out.println(detacuota);

            }
            ///EN CASO QUE LA VENTA SEA FINANCIADO POR OTROS MEDIOS
            String detaformafinanciacion = "[";
            String detaformacuotas = "[";

            if (Integer.valueOf(cuotas.getText()) == 0 && Double.valueOf(cFinanciado) > 0) {
                String iddoc = null;
                String cImporteCuota = null;
                detaformafinanciacion = "[";
                detaformacuotas = "[";

                int item = tablafinanciacion.getRowCount();
                item -= 1;
                for (int i = 0; i <= item; i++) {
                    String cMontoCredito = tablafinanciacion.getValueAt(i, 1).toString();
                    cMontoCredito = cMontoCredito.replace(".", "").replace(",", ".");
                    String cMontoCuota = tablafinanciacion.getValueAt(i, 3).toString();
                    cMontoCuota = cMontoCuota.replace(".", "").replace(",", ".");
                    try {
                        dPrimeraCuota = ODate.de_java_a_sql(formatoFecha.parse(tablafinanciacion.getValueAt(i, 4).toString()));
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    String lineamodo = "{iddetalle : " + referencia + ","
                            + "autorizacion: " + tablafinanciacion.getValueAt(i, 0).toString() + ","
                            + "monto : " + cMontoCredito + ","
                            + "ncuotas : " + tablafinanciacion.getValueAt(i, 2).toString() + ","
                            + "montocuota : " + cMontoCuota + ","
                            + "primeracuota : " + dPrimeraCuota
                            + "},";
                    detaformafinanciacion += lineamodo;
                    int ncuotas = Integer.valueOf(tablafinanciacion.getValueAt(i, 2).toString());
                    Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
                    calendar.setTime(dPrimeraCuota); // Capturamos en el setTime el valor de la fecha ingresada

                    for (int j = 1; j <= ncuotas; j++) {
                        vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                        Date VenceCuota = ODate.de_java_a_sql(vencimientos.getDate());
                        iddoc = UUID.crearUUID();
                        iddoc = iddoc.substring(1, 25);
                        String lineacuota = "{iddocumento : " + iddoc + ","
                                + "creferencia : " + referencia + ","
                                + "documento : " + tablafinanciacion.getValueAt(i, 0).toString() + ","
                                + "fecha : " + FechaProceso + ","
                                + "vencimiento : " + VenceCuota + ","
                                + "cliente : " + cliente.getText() + ","
                                + "sucursal: " + sucursal.getText() + ","
                                + "moneda : " + moneda.getText() + ","
                                + "comprobante : " + com.getCodigo() + ","
                                + "vendedor : " + vendedor.getText() + ","
                                + "idedificio : '" + obra.getText() + "',"
                                + "importe : " + cMontoCuota + ","
                                + "numerocuota : " + tablafinanciacion.getValueAt(i, 2).toString() + ","
                                + "cuota : " + j + ","
                                + "saldo : " + cMontoCuota
                                + "},";
                        detaformacuotas += lineacuota;
                        calendar.setTime(vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                        venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior
                        int mes = venceanterior.getCalendar().get(Calendar.MONTH) + 1;
                        int dia = venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
                        calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0
                        if (mes == 2 && dia == 28) {
                            calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días                            //el vencimiento
                        }
                        vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                    }
                }
                if (!detaformacuotas.equals("[")) {
                    detaformacuotas = detaformacuotas.substring(0, detaformacuotas.length() - 1);
                }
                detaformacuotas += "]";
                if (!detaformafinanciacion.equals("[")) {
                    detaformafinanciacion = detaformafinanciacion.substring(0, detaformafinanciacion.length() - 1);
                }
                detaformafinanciacion += "]";
            }

            String detalleformapago = "[";
            if (Double.valueOf(cTotalValores) > 0) {
                int item = tablapagos.getRowCount();
                item -= 1;
                for (int i = 0; i <= item; i++) {
                    supago = tablapagos.getValueAt(i, 6).toString();
                    supago = supago.replace(".", "").replace(",", ".");
                    String cNrocheque = tablapagos.getValueAt(i, 4).toString();
                    if (cNrocheque.isEmpty()) {
                        cNrocheque = "XXXX";
                    }
                    try {
                        dConfirma = ODate.de_java_a_sql(formatoFecha.parse(tablapagos.getValueAt(i, 5).toString()));
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    String linea = "{idmovimiento : '" + referencia + "',"
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
            }

            if (Integer.valueOf(modo.getText()) == 1) {
                try {
                    grabarventa.AgregarFacturaVentaFerremax(Config.cToken, v, detalle);
                    if (Integer.valueOf(cuotas.getText()) > 0 && Double.valueOf(cFinanciado) == 0) {
                        cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                        ctaDAO.guardarCuentaFerremax(detacuota);
                    }
                    if (Integer.valueOf(cuotas.getText()) == 0 && Double.valueOf(cFinanciado) > 0) {
                        cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                        ctaDAO.guardarCuentaFerremax(detaformacuotas);
                        detalle_forma_cuotasDAO formaDAO = new detalle_forma_cuotasDAO();
                        formaDAO.guardarFormaCuotas(detaformafinanciacion);
                    }

                    if (Double.valueOf(cTotalValores) > 0) {
                        detalle_forma_cobroDAO cobDAO = new detalle_forma_cobroDAO();
                        cobDAO.guardarFormaPago(detalleformapago);
                    }
                    grabarventa.ActualizarFactura(Integer.valueOf(sucursal.getText()), Double.valueOf(cContadorFactura) + 1);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                try {
                    detalle_forma_cuotasDAO formaDAO = new detalle_forma_cuotasDAO();
                    formaDAO.borrarDetalleFormaPago(referencia);
                    cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                    ctaDAO.borrarDetalleCuenta(referencia);

                    detalle_ventaDAO delDAO = new detalle_ventaDAO();
                    //  delDAO.borrarDetalleVenta(referencia);
                    grabarventa.borrarDetalleCuenta(referencia);
                    grabarventa.ActualizarVentaFerremax(v, detalle);
                    detalle_forma_cobroDAO cobDAO = new detalle_forma_cobroDAO();
                    cobDAO.borrarDetalleFormaPago(referencia);
                    if (Integer.valueOf(cuotas.getText()) > 0 && Double.valueOf(cFinanciado) == 0) {
                        ctaDAO.guardarCuentaFerremax(detacuota);
                    }
                    if (Integer.valueOf(cuotas.getText()) == 0 && Double.valueOf(cFinanciado) > 0) {
                        ctaDAO.guardarCuentaFerremax(detaformacuotas);
                        formaDAO.guardarFormaCuotas(detaformafinanciacion);
                    }

                    if (Double.valueOf(cTotalValores) > 0) {
                        cobDAO.guardarFormaPago(detalleformapago);
                    }

                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            detalle_venta.setModal(false);
            detalle_venta.setVisible(false);
        }
    }//GEN-LAST:event_GrabarActionPerformed

    private void detalle_ventaFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_ventaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_ventaFocusGained

    private void detalle_ventaWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_ventaWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_ventaWindowGainedFocus

    private void detalle_ventaWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_ventaWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_ventaWindowActivated

    private void tablaventasPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_tablaventasPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaventasPropertyChange

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
                    String cBoca = sucu.getExpedicion().trim();
                    Double nFactura = sucu.getFactura();
                    int n = (int) nFactura.doubleValue();
                    String formatString = String.format("%%0%dd", 7);
                    String formattedString = String.format(formatString, n);
                    this.factura.setText(cBoca + "-" + formattedString);
                    vencetimbrado.setDate(sucu.getVencetimbrado());
                    nrotimbrado.setText(sucu.getNrotimbrado());
                }
                factura.requestFocus();
            }
            nombresucursal.setText(sucu.getNombre());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void sucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void comboclienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

    private void jTBuscarClienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarClienteKeyPressed
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

    private void tablaclienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCli.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteKeyPressed

    private void AceptarCliActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarCliActionPerformed
        int nFila = this.tablacliente.getSelectedRow();
        this.cliente.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombrecliente.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.direccioncliente.setText(this.tablacliente.getValueAt(nFila, 2).toString());
        int plazocredito = Integer.valueOf(this.tablacliente.getValueAt(nFila, 4).toString());
        calcularVencimiento(plazocredito);
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
//        this.giraduria.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void buscarpreventaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarpreventaActionPerformed
        GrillaPreventas grillapre = new GrillaPreventas();
        Thread hilopreventa = new Thread(grillapre);
        hilopreventa.start();
        preventas.setModal(true);
        preventas.setSize(500, 575);
        preventas.setLocationRelativeTo(null);
        preventas.setTitle("Buscar Preventa");
        preventas.setVisible(true);
        //                giraduria.requestFocus();
        preventas.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarpreventaActionPerformed

    private void clienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed

        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.cliente.getText()));
            nombrecliente.setText(cl.getNombre());
            direccioncliente.setText(cl.getDireccion());
            calcularVencimiento(cl.getPlazocredito());
            nuevoitem.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void combocomprobanteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combocomprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocomprobanteActionPerformed

    private void jTBuscarComprobanteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarComprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobanteActionPerformed

    private void jTBuscarComprobanteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarComprobanteKeyPressed
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

    private void tablacomprobanteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablacomprobanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarComprobante.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteKeyPressed

    private void AceptarComprobanteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarComprobanteActionPerformed
        int nFila = this.tablacomprobante.getSelectedRow();
        this.comprobante.setText(this.tablacomprobante.getValueAt(nFila, 0).toString());
        this.nombrecomprobante.setText(this.tablacomprobante.getValueAt(nFila, 1).toString());

        this.BComprobante.setVisible(false);
        this.jTBuscarComprobante.setText("");
        this.moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarComprobanteActionPerformed

    private void SalirComprobanteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirComprobanteActionPerformed
        this.BComprobante.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirComprobanteActionPerformed

    private void jTBuscarClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

    private void tablaclienteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.AceptarCli.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void tablacomprobanteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablacomprobanteMouseClicked
        this.AceptarComprobante.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteMouseClicked

    private void combomonedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combomonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomonedaActionPerformed

    private void jTBuscarMonedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMonedaActionPerformed

    private void jTBuscarMonedaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarMonedaKeyPressed
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

    private void tablamonedaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablamonedaMouseClicked
        this.AceptarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaMouseClicked

    private void tablamonedaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablamonedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarMoneda.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaKeyPressed

    private void AceptarMonedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarMonedaActionPerformed
        int nFila = this.tablamoneda.getSelectedRow();
        this.moneda.setText(this.tablamoneda.getValueAt(nFila, 0).toString());
        this.nombremoneda.setText(this.tablamoneda.getValueAt(nFila, 1).toString());
        this.cotizacion.setText(this.tablamoneda.getValueAt(nFila, 2).toString());

        this.BMoneda.setVisible(false);
        this.jTBuscarMoneda.setText("");
        this.cotizacion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void SalirSucActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void AceptarSucActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        String cBoca = tablasucursal.getValueAt(nFila, 3).toString();
        Double nFactura = Double.valueOf(tablasucursal.getValueAt(nFila, 2).toString());
        int n = (int) nFactura.doubleValue();
        String formatString = String.format("%%0%dd", 7);
        String formattedString = String.format(formatString, n);
        this.factura.setText(cBoca + "-" + formattedString);

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");
        this.fecha.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void tablasucursalKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablasucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalKeyPressed

    private void tablasucursalMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablasucursalMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalMouseClicked

    private void jTBuscarSucursalKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarSucursalKeyPressed
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

    private void jTBuscarSucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalActionPerformed

    private void combosucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

    private void comprobanteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_comprobanteActionPerformed
        comprobanteDAO cmDAO = new comprobanteDAO();
        comprobante cm = null;
        try {
            cm = cmDAO.buscarIdxtipo(Integer.valueOf(this.comprobante.getText()), 2);
            nombrecomprobante.setText(cm.getNombre());
            //Establecemos un título para el jDialog
            moneda.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        if (Integer.valueOf(comprobante.getText()) == 1) {
            this.cuotas.setText("0");
        } else {
            this.cuotas.setText("1");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteActionPerformed

    private void buscarMonedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarMonedaActionPerformed
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
                BMoneda.setTitle("Buscar Moneda");
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

    private void sucursalFocusGained(FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void clienteFocusGained(FocusEvent evt) {//GEN-FIRST:event_clienteFocusGained
        cliente.selectAll();

        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.cliente.getText()));
            nombrecliente.setText(cl.getNombre());
            direccioncliente.setText(cl.getDireccion());
            calcularVencimiento(cl.getPlazocredito());
            nuevoitem.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_clienteFocusGained

    private void comprobanteFocusGained(FocusEvent evt) {//GEN-FIRST:event_comprobanteFocusGained
        comprobante.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteFocusGained

    private void jMenu1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void buscarcomprobanteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarcomprobanteActionPerformed
        GrillaComprobante grillacm = new GrillaComprobante();
        Thread hiloca = new Thread(grillacm);
        hiloca.start();
        BComprobante.setModal(true);
        BComprobante.setSize(500, 575);
        BComprobante.setLocationRelativeTo(null);
        BComprobante.setVisible(true);
        BComprobante.setTitle("Buscar Comprobante");
        BComprobante.setModal(false);
        moneda.requestFocus();
        if (Integer.valueOf(comprobante.getText()) == 1) {
            this.cuotas.setText("0");
        } else {
            this.cuotas.setText("1");
        }

    }//GEN-LAST:event_buscarcomprobanteActionPerformed

    private void BuscarVendedorActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BuscarVendedorActionPerformed
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

    private void calcularVencimiento(int dias) {
        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.fecha.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        this.primervence.setDate(calendar.getTime()); //Y cargamos
    }


    private void buscarClienteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarClienteActionPerformed
        GrillaCliente grillacl = new GrillaCliente();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Cliente");
        BCliente.setVisible(true);
        BCliente.setModal(false);
        nuevoitem.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarClienteActionPerformed

    private void codprodFocusGained(FocusEvent evt) {//GEN-FIRST:event_codprodFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodFocusGained

    private void codprodActionPerformed(ActionEvent evt) {//GEN-FIRST:event_codprodActionPerformed
        this.BuscarProducto.doClick();
    }//GEN-LAST:event_codprodActionPerformed

    private void codprodKeyPressed(KeyEvent evt) {//GEN-FIRST:event_codprodKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodKeyPressed

    private void BuscarProductoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BuscarProductoActionPerformed
        productoDAO proDAO = new productoDAO();
        producto pro = null;
        try {
            pro = proDAO.BuscarProductoBasico(this.codprod.getText());
            if (pro.getCodigo() == null) {
                BProducto.setModal(true);
                BProducto.setSize(500, 575);
                BProducto.setLocationRelativeTo(null);
                BProducto.setTitle("Buscar Producto");
                BProducto.setVisible(true);
            } else {
                nombreproducto.setText(pro.getNombre().trim());
                precio.setText(formatea.format(pro.getPrecio_maximo()));

                if (pro.getIvaporcentaje() == null) {
                    porcentaje.setText("0");
                } else {
                    porcentaje.setText(formatea.format(pro.getIvaporcentaje()));
                }
                //Establecemos un título para el jDialog
            }
            configuracionDAO cfDAO = new configuracionDAO();
            configuracion cf = null;
            cf = cfDAO.consultar();
            if (Integer.valueOf(cuotas.getText()) > 0 && cf.getLista_precio() == 2) {
                lista_preciosDAO lstDAO = new lista_preciosDAO();
                try {
                    for (lista_precios ls : lstDAO.todos(codprod.getText())) {
                        if (ls.getLimitecantidad() <= Integer.valueOf(cuotas.getText())) {
                            if (ls.getPrecioventa() > 0) {
                                precio.setText(formatea.format(ls.getPrecioventa()));
                            }
                        }
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        this.cantidad.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarProductoActionPerformed

    private void cantidadFocusGained(FocusEvent evt) {//GEN-FIRST:event_cantidadFocusGained
        albumfoto_producto al = null;
        albumfoto_productoDAO imgDAO = new albumfoto_productoDAO();

        try {
            al = imgDAO.buscaId(codprod.getText());
            if (al.getNombre() != null) {
                fotoProducto.setImagen(al.getImagen());
            } else {
                fotoProducto.setImagen(imagenfondo.getImage());
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        cantidad.selectAll();
    }//GEN-LAST:event_cantidadFocusGained

    private void cantidadKeyPressed(KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            if (this.precio.isEnabled()) {
                this.precio.requestFocus();
            } else {
                this.porcentaje.requestFocus();
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codprod.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void precioActionPerformed(ActionEvent evt) {//GEN-FIRST:event_precioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioActionPerformed

    private void precioKeyPressed(KeyEvent evt) {//GEN-FIRST:event_precioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.porcentaje.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_precioKeyPressed

    private void GrabarItemFocusGained(FocusEvent evt) {//GEN-FIRST:event_GrabarItemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemFocusGained

    private void GrabarItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarItemActionPerformed
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

    private void SalirItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        itemventas.setModal(false);
        itemventas.setVisible(false);
        this.detalle_venta.setModal(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void porcentajeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_porcentajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeActionPerformed

    private void porcentajeKeyPressed(KeyEvent evt) {//GEN-FIRST:event_porcentajeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.GrabarItem.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.precio.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeKeyPressed

    private void nuevoitemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_nuevoitemActionPerformed
        itemventas.setSize(442, 290);
        itemventas.setLocationRelativeTo(null);
        this.limpiaritems();
        this.GrabarItem.setText("Agregar");
        this.cModo.setText("");
        itemventas.setModal(true);
        itemventas.setVisible(true);
        codprod.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoitemActionPerformed

    private void porcentajeFocusGained(FocusEvent evt) {//GEN-FIRST:event_porcentajeFocusGained
        porcentaje.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeFocusGained

    private void precioFocusGained(FocusEvent evt) {//GEN-FIRST:event_precioFocusGained
        precio.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_precioFocusGained

    private void editaritemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_editaritemActionPerformed
        nFila = this.tabladetalle.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        itemventas.setSize(442, 290);
        itemventas.setLocationRelativeTo(null);
        cModo.setText(String.valueOf(nFila));
        codprod.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 0).toString());
        nombreproducto.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 1).toString());
        cantidad.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 2).toString());
        porcentaje.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 3).toString());
        precio.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 4).toString());
        totalitem.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 5).toString());
        itemventas.setModal(true);
        itemventas.setVisible(true);
        codprod.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_editaritemActionPerformed

    private void delitemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_delitemActionPerformed
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

    private void tabladetalleMouseEntered(MouseEvent evt) {//GEN-FIRST:event_tabladetalleMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalleMouseEntered

    private void precioFocusLost(FocusEvent evt) {//GEN-FIRST:event_precioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_precioFocusLost

    private void comboproductoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_comboproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproductoActionPerformed

    private void jTBuscarProductoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProductoActionPerformed

    private void jTBuscarProductoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarProductoKeyPressed
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

    private void tablaproductoMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablaproductoMouseClicked
        this.AceptarProducto.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoMouseClicked

    private void tablaproductoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablaproductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarProducto.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoKeyPressed

    private void AceptarProductoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarProductoActionPerformed
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

    private void SalirProductoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirProductoActionPerformed
        this.BProducto.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProductoActionPerformed

    private void porcentajeFocusLost(FocusEvent evt) {//GEN-FIRST:event_porcentajeFocusLost
        String cCantidad = this.cantidad.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");
        String cPrecio = this.precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");
        double nCantidad = Double.valueOf(cCantidad);//CANTIDAD
        double nPrecio = Double.valueOf(cPrecio);//PRECIO
        double ntotal = nCantidad * nPrecio;
        this.totalitem.setText(formatea.format(ntotal));
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeFocusLost

    private void monedaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cotizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comprobante.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void cotizacionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.vendedor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_cotizacionKeyPressed

    private void vendedorKeyPressed(KeyEvent evt) {//GEN-FIRST:event_vendedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cuotas.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cotizacion.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorKeyPressed

    private void cuotasKeyPressed(KeyEvent evt) {//GEN-FIRST:event_cuotasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!cuotas.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                cliente.setEnabled(true);
                cliente.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Ingrese Numero de Factura", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_cuotasKeyPressed

    private void preventaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_preventaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_preventaKeyPressed

    private void monedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        this.buscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void combovendedorActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combovendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combovendedorActionPerformed

    private void jTBuscarVendedorActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorActionPerformed

    private void jTBuscarVendedorKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarVendedorKeyPressed
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

    private void tablavendedorMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablavendedorMouseClicked
        this.AceptarVendedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorMouseClicked

    private void tablavendedorKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablavendedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarVendedor.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorKeyPressed

    private void AceptarVendedorActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarVendedorActionPerformed
        int nFila = this.tablavendedor.getSelectedRow();
        this.vendedor.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
        this.nombrevendedor.setText(this.tablavendedor.getValueAt(nFila, 1).toString());

        this.BVendedor.setVisible(false);
        this.jTBuscarVendedor.setText("");
        this.cuotas.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void vendedorActionPerformed(ActionEvent evt) {//GEN-FIRST:event_vendedorActionPerformed
        this.BuscarVendedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorActionPerformed

    private void imprimirlotesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_imprimirlotesActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Facturas? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            Lotes Lote = new Lotes();
            Thread HiloLote = new Thread(Lote);
            HiloLote.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirlotesActionPerformed

    private void SalirLotesActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirLotesActionPerformed
        lotes.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirLotesActionPerformed

    private void jMenuItem2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.lotes.setSize(439, 288);
        lotes.setTitle("Impresión de Facturas por Lotes");
        lotes.setLocationRelativeTo(null);
        lotes.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void NewItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_NewItemActionPerformed
        String csupago = totalneto.getText();
        csupago = csupago.replace(".", "").replace(",", ".");
        if (Double.valueOf(csupago) <= 0) {
            JOptionPane.showMessageDialog(null, "La Factura no tiene Totales");
        } else {
            limpiarformapago();
            this.cModo.setText("");
            formapago.setSize(513, 270);
            formapago.setTitle("Detalle de Pagos");
            formapago.setLocationRelativeTo(null);
            formapago.setModal(true);
            formapago.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_NewItemActionPerformed

    private void UpditemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_UpditemActionPerformed
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

    private void DelItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_DelItemActionPerformed
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

    private void formaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_formaActionPerformed
        this.BuscarFormapago.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_formaActionPerformed

    private void BuscarFormapagoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BuscarFormapagoActionPerformed
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

    private void bancoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_bancoActionPerformed
        this.BuscarBanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_bancoActionPerformed

    private void BuscarBancoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BuscarBancoActionPerformed
        bancoplazaDAO bDAO = new bancoplazaDAO();
        bancoplaza bco = null;
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

    private void nrochequeKeyPressed(KeyEvent evt) {//GEN-FIRST:event_nrochequeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importecheque.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.banco.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeKeyPressed

    private void importechequeKeyPressed(KeyEvent evt) {//GEN-FIRST:event_importechequeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.confirmacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocheque.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importechequeKeyPressed

    private void confirmacionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_confirmacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.grabarPago.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importecheque.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmacionKeyPressed

    private void grabarPagoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_grabarPagoActionPerformed
        String cImporteCheque = this.importecheque.getText();
        cImporteCheque = cImporteCheque.replace(".", "").replace(",", ".");

        if (cImporteCheque.isEmpty() || cImporteCheque.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Importe");
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

    private void grabarPagoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_grabarPagoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_grabarPagoKeyPressed

    private void salirPagoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_salirPagoActionPerformed
        formapago.setModal(false);
        formapago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirPagoActionPerformed

    private void comboformaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_comboformaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboformaActionPerformed

    private void jTBuscarFormaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarFormaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFormaActionPerformed

    private void jTBuscarFormaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarFormaKeyPressed
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

    private void tablaformapagoMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablaformapagoMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaformapagoMouseClicked

    private void tablaformapagoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablaformapagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaformapagoKeyPressed

    private void AceptarGirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablaformapago.getSelectedRow();
        this.forma.setText(this.tablaformapago.getValueAt(nFila, 0).toString());
        this.nombreformapago.setText(this.tablaformapago.getValueAt(nFila, 1).toString());

        this.BFormaPago.setVisible(false);
        this.jTBuscarForma.setText("");
        this.banco.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BFormaPago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void combobancoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combobancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobancoActionPerformed

    private void jTBuscarbancoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarbancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarbancoActionPerformed

    private void jTBuscarbancoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarbancoKeyPressed
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

    private void tablabancoMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarCasaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.banco.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.BBancos.setVisible(false);
        this.jTBuscarbanco.setText("");
        this.nrocheque.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.BBancos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    private void jMenuItem4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        GenerarContrato GenerarC = new GenerarContrato();
        Thread HiloReporte = new Thread(GenerarC);
        HiloReporte.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GenerarPagare GenerarP = new GenerarPagare();
        Thread HiloReporte = new Thread(GenerarP);
        HiloReporte.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        GenerarDevolucion GenerarD = new GenerarDevolucion();
        Thread HiloDevo = new Thread(GenerarD);
        HiloDevo.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void facturaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_facturaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!factura.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                comprobante.setEnabled(true);
                comprobante.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Ingrese Numero de Factura", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_facturaKeyPressed

    private void BProductoWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_BProductoWindowGainedFocus
        jTBuscarProducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BProductoWindowGainedFocus

    private void autorizacionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_autorizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.monto.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_autorizacionKeyPressed

    private void montoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_montoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ncuotas.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.autorizacion.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_montoKeyPressed

    private void primeracuotaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_primeracuotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_primeracuotaKeyPressed

    private void grabarFinanciacionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_grabarFinanciacionActionPerformed

        if (autorizacion.equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Autorización");
            this.autorizacion.requestFocus();
            return;
        }
        String cImporteCheque = this.monto.getText();
        cImporteCheque = cImporteCheque.replace(".", "").replace(",", ".");

        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[5];
            fila[0] = this.autorizacion.getText().toString();
            fila[1] = this.monto.getText().toString();
            fila[2] = this.ncuotas.getText();
            fila[3] = this.montocuota.getText();
            fila[4] = formatoFecha.format(this.primeracuota.getDate());
            modelofinanciacion.addRow(fila);
            this.autorizacion.requestFocus();
        } else {
            this.tablafinanciacion.setValueAt(this.autorizacion.getText(), nFila, 0);
            this.tablafinanciacion.setValueAt(this.monto.getText(), nFila, 1);
            this.tablafinanciacion.setValueAt(this.ncuotas.getText(), nFila, 2);
            this.tablafinanciacion.setValueAt(this.montocuota.getText(), nFila, 3);
            this.tablafinanciacion.setValueAt(formatoFecha.format(this.primeracuota.getDate()), nFila, 4);
            nFila = 0;
            this.salirFinanciacion.doClick();
        }
        this.limpiarfinanciacion();
        this.sumarfinanciacion();
        // TODO add your handling code here:
    }//GEN-LAST:event_grabarFinanciacionActionPerformed

    private void grabarFinanciacionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_grabarFinanciacionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_grabarFinanciacionKeyPressed

    private void salirFinanciacionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_salirFinanciacionActionPerformed
        financiacion.setModal(false);
        financiacion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirFinanciacionActionPerformed

    private void ncuotasKeyPressed(KeyEvent evt) {//GEN-FIRST:event_ncuotasKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.montocuota.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.monto.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_ncuotasKeyPressed

    private void montocuotaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_montocuotaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.primeracuota.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ncuotas.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_montocuotaKeyPressed

    private void ncuotasFocusLost(FocusEvent evt) {//GEN-FIRST:event_ncuotasFocusLost
        String cImporteCheque = this.monto.getText();
        cImporteCheque = cImporteCheque.replace(".", "").replace(",", ".");
        this.montocuota.setText(formatea.format(Math.round(Double.valueOf(cImporteCheque) / Integer.valueOf(ncuotas.getText()))));
        // TODO add your handling code here:
    }//GEN-LAST:event_ncuotasFocusLost

    private void NuevoFActionPerformed(ActionEvent evt) {//GEN-FIRST:event_NuevoFActionPerformed
        cuotas.setText("0");
        String csupago = totalneto.getText();
        csupago = csupago.replace(".", "").replace(",", ".");
        if (Double.valueOf(csupago) <= 0) {
            JOptionPane.showMessageDialog(null, "No puede financiar sin un Total");
        } else {
            limpiarfinanciacion();
            this.cModo.setText("");
            financiacion.setModal(true);
            financiacion.setSize(513, 270);
            financiacion.setTitle("Detalle de Financión");
            financiacion.setLocationRelativeTo(null);
            financiacion.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoFActionPerformed

    private void EditarFActionPerformed(ActionEvent evt) {//GEN-FIRST:event_EditarFActionPerformed
        nFila = this.tablafinanciacion.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una Fila");
            return;
        }
        financiacion.setSize(513, 270);
        financiacion.setTitle("Detalle Financiación");
        financiacion.setLocationRelativeTo(null);
        cModo.setText(String.valueOf(nFila));
        autorizacion.setText(tablafinanciacion.getValueAt(tablafinanciacion.getSelectedRow(), 0).toString());
        monto.setText(tablafinanciacion.getValueAt(tablafinanciacion.getSelectedRow(), 1).toString());
        ncuotas.setText(tablafinanciacion.getValueAt(tablafinanciacion.getSelectedRow(), 2).toString());
        montocuota.setText(tablafinanciacion.getValueAt(tablafinanciacion.getSelectedRow(), 3).toString());
        try {
            primeracuota.setDate(formatoFecha.parse(this.tablafinanciacion.getValueAt(nFila, 4).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        financiacion.setModal(true);
        financiacion.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_EditarFActionPerformed

    private void BorrarFActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BorrarFActionPerformed
        nFila = this.tablafinanciacion.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar un Registro");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelofinanciacion.removeRow(nFila);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                this.sumarfinanciacion();;
            }
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarFActionPerformed

    private void BuscarCajaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_BuscarCajaActionPerformed
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

    private void combocajaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combocajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocajaActionPerformed

    private void jTBuscarCajaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCajaActionPerformed

    private void jTBuscarCajaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarCajaKeyPressed
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
                filtrocaja(indiceColumnaTabla);
            }
        });
        trsfiltrocaja = new TableRowSorter(tablacaja.getModel());
        tablacaja.setRowSorter(trsfiltrocaja);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCajaKeyPressed

    private void tablacajaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablacajaMouseClicked
        this.AceptarCaja.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacajaMouseClicked

    private void tablacajaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablacajaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCaja.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacajaKeyPressed

    private void AceptarCajaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarCajaActionPerformed
        int nFila = this.tablacaja.getSelectedRow();
        this.caja.setText(this.tablacaja.getValueAt(nFila, 0).toString());
        this.nombrecaja.setText(this.tablacaja.getValueAt(nFila, 1).toString());
        this.BCaja.setVisible(false);
        this.jTBuscarCaja.setText("");
        this.cliente.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCajaActionPerformed

    private void SalirCajaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCajaActionPerformed
        this.BCaja.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCajaActionPerformed

    private void cajaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cajaActionPerformed
        this.BuscarCaja.doClick();

        // TODO add your handling code here:
    }//GEN-LAST:event_cajaActionPerformed

    private void fecharetencionFocusGained(FocusEvent evt) {//GEN-FIRST:event_fecharetencionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fecharetencionFocusGained

    private void fecharetencionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_fecharetencionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fecharetencionKeyPressed

    private void jMenuItem5ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        int nFila = tablaventas.getSelectedRow();
        String cReferencia = tablaventas.getValueAt(nFila, 0).toString();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaventas.requestFocus();
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
            nroretencion.setText("");
            porcentaje_retencion.setText("30");
            this.valor_retencion.setText("0");
            this.cotizacion.setText(formatea.format(ve.getCotizacion()));
            creferenciaret.setText(ve.getCreferencia());
            sucursalret.setText(String.valueOf(ve.getSucursal().getCodigo()));
            nombresucursalret.setText(ve.getSucursal().getNombre());
            nrofactura.setText(formatosinpunto.format(ve.getFactura()));
            fecharetencion.setDate(ve.getFecha());
            clienteret.setText(String.valueOf(ve.getCliente().getCodigo()));
            nombreclienteret.setText(ve.getCliente().getNombre());
            monedaret.setText(formatosinpunto.format(ve.getMoneda().getCodigo()));
            nombremonedaret.setText(ve.getMoneda().getNombre());
            cotizacion.setText(formatea.format(ve.getCotizacion()));
            double niva10 = Math.round(ve.getGravadas10().doubleValue() / 11);
            double niva5 = Math.round(ve.getGravadas5().doubleValue() / 21);
            importe_iva.setText(formatea.format(niva10 + niva5));
            importe_sin_iva.setText(formatea.format(ve.getTotalneto().doubleValue() - (niva10 + niva5)));
            importe_gravado_total.setText(formatea.format(niva10 + niva5));
            caja.setText(String.valueOf(ve.getCaja().getCodigo()));
            vendedor.setText(String.valueOf(ve.getCaja().getCodigo()));
            totalneto.setText(formatea.format(ve.getTotalneto()));
        }
        retenciones.setModal(true);
        retenciones.setSize(610, 480);
        //Establecemos un título para el jDialog
        retenciones.setTitle("Generar Retención");
        retenciones.setLocationRelativeTo(null);
        retenciones.setVisible(true);
        nroretencion.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void SalirRetencionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirRetencionActionPerformed
        retenciones.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirRetencionActionPerformed

    private void nroretencionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_nroretencionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.porcentaje_retencion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nroretencion.requestFocus();
        }   // TODO add your handling code */

        // TODO add your handling code here:
    }//GEN-LAST:event_nroretencionKeyPressed

    private void porcentaje_retencionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_porcentaje_retencionActionPerformed
        String cImporteIva = importe_iva.getText();
        String cPorcentaje = porcentaje_retencion.getText();
        cImporteIva = cImporteIva.replace(".", "").replace(",", ".");
        cPorcentaje = cPorcentaje.replace(".", "").replace(",", ".");
        valor_retencion.setText(formatea.format(Math.round(Double.valueOf(cImporteIva) * Double.valueOf(cPorcentaje) / 100)));
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentaje_retencionActionPerformed

    private void GrabarRetencionActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarRetencionActionPerformed
        if (this.nroretencion.getText().isEmpty() || this.nroretencion.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Número de Retención");
            this.nroretencion.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Retención ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            configuracionDAO confDAO = new configuracionDAO();
            configuracion configrete = new configuracion();
            configrete = confDAO.consultar();

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cli = null;
            monedaDAO mnDAO = new monedaDAO();
            moneda mn = null;

            retenciones_ventas rete = new retenciones_ventas();
            retenciones_ventasDAO retDAO = new retenciones_ventasDAO();

            try {
                suc = sucDAO.buscarId(Integer.valueOf(sucursalret.getText()));
                cli = cliDAO.buscarId(Integer.valueOf(clienteret.getText()));
                mn = mnDAO.buscarId(Integer.valueOf(monedaret.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            rete.setSucursal(suc);
            rete.setMoneda(mn);
            rete.setCliente(cli);
            rete.setCreferencia(this.creferenciaret.getText());
            rete.setNroretencion(nroretencion.getText());
            rete.setNrofactura(nrofactura.getText());
            Date FechaProceso = ODate.de_java_a_sql(fecharetencion.getDate());
            rete.setFecha(FechaProceso);

            String cImporteSinIva = this.importe_sin_iva.getText();
            cImporteSinIva = cImporteSinIva.replace(".", "").replace(",", ".");
            rete.setImporte_sin_iva(Double.valueOf(cImporteSinIva));

            String cImporteIva = this.importe_iva.getText();
            cImporteIva = cImporteIva.replace(".", "").replace(",", ".");
            rete.setImporte_iva(Double.valueOf(cImporteIva));

            String cImporteGravadoTotal = this.importe_gravado_total.getText();
            cImporteGravadoTotal = cImporteGravadoTotal.replace(".", "").replace(",", ".");

            rete.setImporte_gravado_total(Double.valueOf(cImporteGravadoTotal));
            rete.setTotalneto(Double.valueOf(cImporteGravadoTotal));

            String cPorcentaje = this.porcentaje_retencion.getText();
            cPorcentaje = cPorcentaje.replace(".", "").replace(",", ".");
            rete.setPorcentaje_retencion(Double.valueOf(cPorcentaje));

            String cValorRetencion = this.valor_retencion.getText();
            cValorRetencion = cValorRetencion.replace(".", "").replace(",", ".");
            rete.setValor_retencion(Double.valueOf(cValorRetencion));

            String cCotizacion = this.cotizacion.getText();
            cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
            rete.setCotizacion(Double.valueOf(cCotizacion));
            rete.setGenerarasiento(1);
            rete.setObservacion("Retención s/comprobante N° " + nrofactura.getText());

            int enviacta = 0;

            if (this.enviarcta.isSelected()) {
                enviacta = 1;
            } else {
                enviacta = 0;
            }
            rete.setEnviarcta(enviacta);

            double nValorCuenta = Double.valueOf(cValorRetencion);
            nValorCuenta = nValorCuenta * -1;

            String detacuota = "[";
            if (enviacta == 1) {
                String iddoc = UUID.crearUUID();
                iddoc = iddoc.substring(1, 25);
                String lineacuota = "{iddocumento : " + iddoc + ","
                        + "creferencia : " + this.creferenciaret.getText() + ","
                        + "documento : " + nrofactura.getText() + ","
                        + "fecha : " + FechaProceso + ","
                        + "vencimiento : " + FechaProceso + ","
                        + "cliente : " + clienteret.getText() + ","
                        + "sucursal: " + sucursalret.getText() + ","
                        + "moneda : " + monedaret.getText() + ","
                        + "comprobante : " + configrete.getCod_retencion() + ","
                        + "vendedor : " + vendedor.getText() + ","
                        + "importe : " + nValorCuenta + ","
                        + "numerocuota : " + "1" + ","
                        + "cuota : " + "1" + ","
                        + "saldo : " + nValorCuenta
                        + "},";
                detacuota += lineacuota;
                if (!detacuota.equals("[")) {
                    detacuota = detacuota.substring(0, detacuota.length() - 1);
                }
                detacuota += "]";
                System.out.println(detacuota);
            }
            try {
                retDAO.borrarRetencion(this.creferenciaret.getText());
                retDAO.borrarCuenta(this.creferenciaret.getText(), configrete.getCod_retencion());
                retDAO.AgregarRetencionesVenta(rete);
                cuenta_clienteDAO ctaDAOrete = new cuenta_clienteDAO();
                if (enviacta == 1) {
                    ctaDAOrete.guardarCuenta(detacuota);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        retenciones.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarRetencionActionPerformed

    private void porcentaje_retencionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_porcentaje_retencionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.enviarcta.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_porcentaje_retencionKeyPressed

    private void ncuotasActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ncuotasActionPerformed
        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.fecha.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        calendar.add(Calendar.DAY_OF_YEAR, 30);  // numero de días a añadir, o restar en caso de días<0
        this.primeracuota.setDate(calendar.getTime()); //Y cargamos
        // TODO add your handling code here:
    }//GEN-LAST:event_ncuotasActionPerformed

    private void buscarcadenaFocusGained(FocusEvent evt) {//GEN-FIRST:event_buscarcadenaFocusGained
        switch (comboventa.getSelectedIndex()) {
            case 0:
                indiceTabla = 1;
                break;//por codigo
            case 1:
                indiceTabla = 5;
                break;//por nombre
            case 2:
                indiceTabla = 6;
                break;//por codigo
            }
        if (!buscarcadena.getText().isEmpty()) {
            FiltroVenta();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaFocusGained

    private void GrabarTicketActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarTicketActionPerformed
        if (this.nrofacturaticket.getText().isEmpty() || this.nrofacturaticket.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Número de Factura");
            this.nrofacturaticket.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Actualizar el Ticket ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            venta vta = new venta();
            ventaDAO veDAO = new ventaDAO();
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            try {
                suc = sucDAO.buscarId(Integer.valueOf(sucursalticket.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            vta.setCreferencia(this.creferenciaticket.getText());
            vta.setFormatofactura(nroticket.getText());
            Date FechaVencimiento = ODate.de_java_a_sql(this.vencetimbrado.getDate());
            vta.setVencimientotimbrado(FechaVencimiento);
            vta.setNrotimbrado(Integer.valueOf(nrotimbrado.getText()));
            vta.setTicketold(nrofacturaticket.getText());
            vta.setSucursal(suc);
            String cNumeroFactura = nroticket.getText();
            cNumeroFactura = cNumeroFactura.replace("-", "");
            vta.setFactura(Double.valueOf(cNumeroFactura));
            String cContadorFactura = cNumeroFactura.substring(6, 13);

            try {
                veDAO.ActualizarTicket(vta);
                if (Integer.valueOf(comprobanteticket.getText()) == 2) {
                    cuenta_clienteDAO ctaDAOrete = new cuenta_clienteDAO();
                    cuenta_clientes cta = new cuenta_clientes();
                    cta.setCreferencia(this.creferenciaticket.getText());
                    cta.setDocumento(cNumeroFactura);
                    cta.setAutorizacion(nrofacturaticket.getText());
                    cta.setSucursal(Integer.valueOf(sucursalticket.getText()));
                    ctaDAOrete.ActualizarTicket(cta);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        actualizaTicket.setVisible(false);
        this.refrescar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarTicketActionPerformed

    private void SalirTicketActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirTicketActionPerformed
        actualizaTicket.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirTicketActionPerformed

    private void nroticketKeyPressed(KeyEvent evt) {//GEN-FIRST:event_nroticketKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.porcentaje_retencion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nroretencion.requestFocus();
        }   // TODO add your handling code */

        // TODO add your handling code here:
    }//GEN-LAST:event_nroticketKeyPressed

    private void fechaticketFocusGained(FocusEvent evt) {//GEN-FIRST:event_fechaticketFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaticketFocusGained

    private void fechaticketKeyPressed(KeyEvent evt) {//GEN-FIRST:event_fechaticketKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaticketKeyPressed

    private void facturaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_facturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_facturaActionPerformed

    private void nrofacturaticketActionPerformed(ActionEvent evt) {//GEN-FIRST:event_nrofacturaticketActionPerformed
        int nCaracter = nrofacturaticket.getText().indexOf("-");
        String cNumeroFactura = nrofacturaticket.getText();
        if (nCaracter >= 0) {
            cNumeroFactura = cNumeroFactura.replace("-", "");
            boolean isNumeric = cNumeroFactura.matches("[+-]?\\d*(\\.\\d+)?");
            if (isNumeric == false) {
                JOptionPane.showMessageDialog(null, "Formato de Número de Factura no Corresponde");
                this.nrofacturaticket.getText();
                return;
            }
        }// TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaticketActionPerformed

    private void jMenuItem6ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        int nFila = tablaventas.getSelectedRow();
        String cReferencia = tablaventas.getValueAt(nFila, 0).toString();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaventas.requestFocus();
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
            nroticket.setText(ve.getFormatofactura());
            creferenciaticket.setText(ve.getCreferencia());
            sucursalticket.setText(String.valueOf(ve.getSucursal().getCodigo()));
            nombresucursalticket.setText(ve.getSucursal().getNombre());
            nrofacturaticket.setText("");
            fechaticket.setDate(ve.getFecha());
            clienteticket.setText(String.valueOf(ve.getCliente().getCodigo()));
            nombreclienteticket.setText(ve.getCliente().getNombre());
            monedaticket.setText(formatosinpunto.format(ve.getMoneda().getCodigo()));
            nombremonedaticket.setText(ve.getMoneda().getNombre());
            comprobanteticket.setText(String.valueOf(ve.getComprobante().getCodigo()));
            nombrecomprobanteticket.setText(ve.getComprobante().getNombre());
            totalticket.setText(formatea.format(ve.getTotalneto()));
        }
        actualizaTicket.setModal(true);
        actualizaTicket.setSize(615, 400);
        //Establecemos un título para el jDialog
        actualizaTicket.setTitle("Actualizar Ticket");
        actualizaTicket.setLocationRelativeTo(null);
        actualizaTicket.setVisible(true);
        sucursalticket.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void actualizaTicketWindowActivated(WindowEvent evt) {//GEN-FIRST:event_actualizaTicketWindowActivated
        sucursalticket.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_actualizaTicketWindowActivated

    private void sucursalticketActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sucursalticketActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursalticket.getText()));
            if (sucu.getCodigo() != 0) {
                nrofactura.setText("");
                vencetimbrado.setDate(sucu.getVencetimbrado());
                nrotimbrado.setText(sucu.getNrotimbrado());
                nombresucursalticket.setText(sucu.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalticketActionPerformed

    private void sucursalticketKeyPressed(KeyEvent evt) {//GEN-FIRST:event_sucursalticketKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nrofacturaticket.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalticketKeyPressed

    private void jMenuItem7ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreFactura = config.getNombrefactura();

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nFila = tablaventas.getSelectedRow();
            String num = tablaventas.getValueAt(nFila, 11).toString();
            num = num.replace(".", "").replace(",", ".");
            numero_a_letras numero = new numero_a_letras();

            parameters.put("Letra", numero.Convertir(num, true, Integer.valueOf(tablaventas.getValueAt(nFila, 12).toString())));
            parameters.put("cRuc", Config.cRucEmpresa);
            parameters.put("cTelefono", Config.cTelefono);
            parameters.put("cDireccion", Config.cDireccion);
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("cReferencia", tablaventas.getValueAt(nFila, 0).toString());

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

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void visualizarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_visualizarActionPerformed
        cadenabuscar = this.buscarcadena.getText();
        modo.setText("2");
        int nFila = tablaventas.getSelectedRow();
        String cReferencia = tablaventas.getValueAt(nFila, 0).toString();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaventas.requestFocus();
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
            creferencia.setText(ve.getCreferencia());
            sucursal.setText(String.valueOf(ve.getSucursal().getCodigo()));
            nombresucursal.setText(ve.getSucursal().getNombre());
            factura.setText(ve.getFormatofactura());
            fecha.setDate(ve.getFecha());
            cliente.setText(String.valueOf(ve.getCliente().getCodigo()));
            nombrecliente.setText(ve.getCliente().getNombre());
            direccioncliente.setText(ve.getCliente().getDireccion());
            vendedor.setText(String.valueOf(ve.getVendedor().getCodigo()));
            nombrevendedor.setText(String.valueOf(ve.getVendedor().getNombre()));
            caja.setText(String.valueOf(ve.getCaja().getCodigo()));
            nombrecaja.setText(ve.getCaja().getNombre());
            comprobante.setText(String.valueOf(ve.getComprobante().getCodigo()));
            if (Integer.valueOf(comprobante.getText()) != 1) {
                this.cuotas.setText("1");
            }
            nombrecomprobante.setText(String.valueOf(ve.getComprobante().getNombre()));
            moneda.setText(formatosinpunto.format(ve.getMoneda().getCodigo()));
            nombremoneda.setText(ve.getMoneda().getNombre());
            cotizacion.setText(formatea.format(ve.getCotizacion()));
            nrotimbrado.setText(String.valueOf(ve.getNrotimbrado()));
            vencetimbrado.setDate(ve.getVencimientotimbrado());
            preventa.setText(String.valueOf(ve.getPreventa()));
            exentas.setText(formatea.format(ve.getExentas()));
            gravadas5.setText(formatea.format(ve.getGravadas5()));
            gravadas10.setText(formatea.format(ve.getGravadas10()));
            totalneto.setText(formatea.format(ve.getTotalneto()));
            primervence.setDate(ve.getVencimiento());
            totalvalores.setText(formatea.format(ve.getSupago()));
            cuotas.setText(formatosinpunto.format(ve.getCuotas()));
            observacion.setText(ve.getObservacion());
            totalfinanciado.setText(formatea.format(ve.getFinanciado()));

            // SE CARGAN LOS DETALLES DE VENTAS
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

            // SE CARGAN LOS DETALLES DE COBROS SI LOS HAY
            detalle_forma_cobroDAO fDAO = new detalle_forma_cobroDAO();

            cantidadRegistro = modelopagos.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopagos.removeRow(0);
            }
            try {

                for (detalle_forma_cobro ft : fDAO.MostrarDetalle(idControl.getText())) {
                    String Datos[] = {String.valueOf(ft.getForma().getCodigo()), ft.getForma().getNombre(), String.valueOf(ft.getBanco().getCodigo()), ft.getBanco().getNombre(), ft.getNrocheque(), formatoFecha.format(ft.getConfirmacion()), formatea.format(ft.getNetocobrado())};
                    modelopagos.addRow(Datos);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            int tFilas = tablapagos.getRowCount();
            if (tFilas > 0) {
                Upditem.setEnabled(true);
                DelItem.setEnabled(true);
            } else {
                Upditem.setEnabled(false);
                DelItem.setEnabled(false);
            }

            // SE CARGAN LOS DETALLES DE FINANCIACION SI LOS HAY
            detalle_forma_cuotasDAO cuotasDAO = new detalle_forma_cuotasDAO();

            cantidadRegistro = modelofinanciacion.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelofinanciacion.removeRow(0);
            }
            try {

                for (detalle_forma_cuotas cuot : cuotasDAO.MostrarDetalle(idControl.getText())) {
                    String Datos[] = {String.valueOf(cuot.getAutorizacion()), formatea.format(cuot.getMonto()), formatea.format(cuot.getNcuotas()), formatea.format(cuot.getMontocuota()), formatoFecha.format(cuot.getPrimeracuota())};
                    modelofinanciacion.addRow(Datos);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tFilas = tablafinanciacion.getRowCount();
            if (tFilas > 0) {
                EditarF.setEnabled(true);
                BorrarF.setEnabled(true);
            } else {
                EditarF.setEnabled(false);
                BorrarF.setEnabled(false);
            }
            this.sumatoria();
            this.sumarforma();
            this.sumarfinanciacion();
            this.Grabar.setEnabled(false);
            this.nuevoitem.setEnabled(false);
            this.editaritem.setEnabled(false);
            this.delitem.setEnabled(false);

            detalle_venta.setModal(true);
            detalle_venta.setSize(980, 709);
            //Establecemos un título para el jDialog
            detalle_venta.setTitle("Generar Nueva Venta");
            detalle_venta.setLocationRelativeTo(null);
            detalle_venta.setVisible(true);
            sucursal.requestFocus();
            this.refrescar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_visualizarActionPerformed

    private void buscarobraActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarobraActionPerformed
        GrillaObra grillab = new GrillaObra();
        Thread hilobanco = new Thread(grillab);
        hilobanco.start();
        BObras.setModal(true);
        BObras.setSize(482, 575);
        BObras.setLocationRelativeTo(null);
        BObras.setVisible(true);
        BObras.setModal(true);        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarobraActionPerformed

    private void combopaisActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combopaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combopaisActionPerformed

    private void jTBuscarPaisActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jTBuscarPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPaisActionPerformed

    private void jTBuscarPaisKeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTBuscarPaisKeyPressed
        this.jTBuscarPais.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarPais.getText()).toUpperCase();
                jTBuscarPais.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combopais.getSelectedIndex()) {
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
        trsfiltropais = new TableRowSorter(tablapais.getModel());
        tablapais.setRowSorter(trsfiltropais);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPaisKeyPressed

    public void filtrogira(int nNumeroColumna) {
        trsfiltropais.setRowFilter(RowFilter.regexFilter(this.jTBuscarPais.getText(), nNumeroColumna));
    }

    private void tablapaisMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablapaisMouseClicked
        this.AceptarPais.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapaisMouseClicked

    private void tablapaisKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablapaisKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPais.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablapaisKeyPressed

    private void AceptarPaisActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarPaisActionPerformed
        int nFila = this.tablapais.getSelectedRow();
        this.obra.setText(this.tablapais.getValueAt(nFila, 0).toString());
        this.nombreobra.setText(this.tablapais.getValueAt(nFila, 1).toString());

        this.BObras.setVisible(false);
        this.jTBuscarPais.setText("");
        this.cliente.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarPaisActionPerformed

    private void SalirPaisActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirPaisActionPerformed
        this.BObras.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPaisActionPerformed

    private void obraActionPerformed(ActionEvent evt) {//GEN-FIRST:event_obraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_obraActionPerformed

    private void FiltrarPreventaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_FiltrarPreventaKeyPressed
        this.ComboBuscarPreventa.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (FiltrarPreventa.getText()).toUpperCase();
                FiltrarPreventa.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (ComboBuscarPreventa.getSelectedIndex()) {
                    case 0:
                    indiceColumnaTabla = 0;
                    break;//por numero
                    case 1:
                    indiceColumnaTabla = 2;
                    break;//por nombre
                }
                repaint();
                filtropreventa(indiceColumnaTabla);
            }
        });
        trsfiltropreventa = new TableRowSorter(tablapreventa.getModel());
        tablapreventa.setRowSorter(trsfiltropreventa);

        // TODO add your handling code here:
    }//GEN-LAST:event_FiltrarPreventaKeyPressed

     public void filtropreventa(int nNumeroColumna) {
        trsfiltropreventa.setRowFilter(RowFilter.regexFilter(this.FiltrarPreventa.getText(), nNumeroColumna));
    }

    
    
    private void FiltrarPreventaKeyReleased(KeyEvent evt) {//GEN-FIRST:event_FiltrarPreventaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_FiltrarPreventaKeyReleased

    private void tablapreventaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablapreventaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapreventaMouseClicked

    private void tablapreventaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablapreventaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPreventa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapreventaKeyPressed

    private void AceptarPreventaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AceptarPreventaActionPerformed
        int nFila = this.tablapreventa.getSelectedRow();
        String cDescuento = this.tablapreventa.getValueAt(nFila, 14).toString();
        cDescuento = cDescuento.replace(".", "");
        String cFirma = this.tablapreventa.getValueAt(nFila, 15).toString();

        this.preventa.setText(this.tablapreventa.getValueAt(nFila, 0).toString());
        this.nombrecliente.setText(this.tablapreventa.getValueAt(nFila, 2).toString());
        this.cliente.setText(this.tablapreventa.getValueAt(nFila, 4).toString());


        if (this.tablapreventa.getValueAt(nFila, 6).toString().isEmpty()) {
            this.direccioncliente.setText("SD");
        } else {
            this.direccioncliente.setText(this.tablapreventa.getValueAt(nFila, 6).toString());
        }
        this.vendedor.setText(this.tablapreventa.getValueAt(nFila, 7).toString());
        this.nombrevendedor.setText(this.tablapreventa.getValueAt(nFila, 8).toString());
        this.comprobante.setText(this.tablapreventa.getValueAt(nFila, 9).toString());
        this.nombrecomprobante.setText(this.tablapreventa.getValueAt(nFila, 10).toString());
        if (this.tablapreventa.getValueAt(nFila, 11).toString().isEmpty()) {
            this.observacion.setText("Venta de Mercaderías");
        } else {
            this.observacion.setText(this.tablapreventa.getValueAt(nFila, 11).toString());
        }
        this.obra.setText(this.tablapreventa.getValueAt(nFila, 12).toString());
        this.nombreobra.setText(this.tablapreventa.getValueAt(nFila, 13).toString().trim());
        this.caja.setText(this.tablapreventa.getValueAt(nFila, 16).toString().trim());

        this.preventas.setVisible(false);
        this.FiltrarPreventa.setText("");
        CargaDetallePreventas detpreventa = new CargaDetallePreventas();
        Thread hilodetalle = new Thread(detpreventa);
        hilodetalle.start();
        //this.CargaPreventa();
        cliente.requestFocus();
        this.SalirPreventa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarPreventaActionPerformed

    private void CargaPreventa() {
        int cantidadRegistro = tabladetalle.getRowCount();
        double nTotalItem = 0.00;
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        detalle_preventaDAO detDAO = new detalle_preventaDAO();
        try {
            for (detalle_preventa detpre : detDAO.MostrarDetalle(Integer.valueOf(preventa.getText()))) {
                int nItem = tabladetalle.getRowCount() + 1;
                nTotalItem = detpre.getCantidad() * detpre.getPrecio();
                if (nTotalItem != detpre.getMonto()) {
                    JOptionPane.showMessageDialog(null, "En el Item " + nItem + " hay Diferencia, Verifique");
                }
                String Datos[] = {String.valueOf(String.valueOf(nItem)),
                    detpre.getCodprod().getCodigo(),
                    detpre.getCodprod().getNombre(),
                    formatcantidad.format(detpre.getCantidad()),
                    formatea.format(detpre.getPrecio()),
                    formatea.format(detpre.getMonto()),
                    formatea.format(detpre.getPorcentaje())};
                modelo.addRow(Datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
        sumatoria();
        tabladetalle.setRowSorter(new TableRowSorter(modelo));
        int cantFilas = tabladetalle.getRowCount();
    }
    
    
    private void SalirPreventaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirPreventaActionPerformed
        this.preventas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPreventaActionPerformed

    private void FiltroVenta() {
        trsfiltro = new TableRowSorter(tablaventas.getModel());
        tablaventas.setRowSorter(trsfiltro);
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText().toUpperCase(), indiceTabla));
    }

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
        modelo.addColumn("N° Transacción");
        modelo.addColumn("N° Factura");
        modelo.addColumn("Fecha");
        modelo.addColumn("Comprobante");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Denominación Cliente");
        modelo.addColumn("Moneda");
        modelo.addColumn("Exentas");
        modelo.addColumn("5%");
        modelo.addColumn("10%");
        modelo.addColumn("Total Neto");
        modelo.addColumn("Moneda");
        modelo.addColumn("Suc");
        modelo.addColumn("Hora Grabado");

        int[] anchos = {3, 120, 120, 90, 90, 90, 120, 60, 50, 50, 50, 80, 10, 1, 150};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.tablaventas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        ((DefaultTableCellRenderer) tablaventas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaventas.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        this.tablaventas.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaventas.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaventas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaventas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablaventas.getColumnModel().getColumn(12).setMaxWidth(0);
        this.tablaventas.getColumnModel().getColumn(12).setMinWidth(0);
        this.tablaventas.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(0);
        this.tablaventas.getTableHeader().getColumnModel().getColumn(12).setMinWidth(0);

        this.tablaventas.getColumnModel().getColumn(13).setMaxWidth(0);
        this.tablaventas.getColumnModel().getColumn(13).setMinWidth(0);
        this.tablaventas.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
        this.tablaventas.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);

        this.tablaventas.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablaventas.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.tablaventas.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.tablaventas.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.tablaventas.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
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
        this.tablaproducto.setFont(font);
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
                new ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    JButton AceptarCaja;
    JButton AceptarCasa;
    JButton AceptarCli;
    JButton AceptarComprobante;
    JButton AceptarGir;
    JButton AceptarMoneda;
    JButton AceptarPais;
    JButton AceptarPreventa;
    JButton AceptarProducto;
    JButton AceptarSuc;
    JButton AceptarVendedor;
    JButton Agregar;
    JDialog BBancos;
    JDialog BCaja;
    JDialog BCliente;
    JDialog BComprobante;
    JDialog BFormaPago;
    JDialog BMoneda;
    JDialog BObras;
    JDialog BProducto;
    JDialog BSucursal;
    JDialog BVendedor;
    JButton BorrarF;
    JButton BuscarBanco;
    JButton BuscarCaja;
    JButton BuscarFormapago;
    JButton BuscarProducto;
    JButton BuscarVendedor;
    JComboBox<String> ComboBuscarPreventa;
    JButton DelItem;
    JButton EditarF;
    JButton Eliminar;
    JTextField FiltrarPreventa;
    JButton Grabar;
    JButton GrabarItem;
    JButton GrabarRetencion;
    JButton GrabarTicket;
    JButton Listar;
    JButton Modificar;
    JButton NewItem;
    JButton NuevoF;
    JButton Salir;
    JButton SalirCaja;
    JButton SalirCasa;
    JButton SalirCli;
    JButton SalirCompleto;
    JButton SalirComprobante;
    JButton SalirGir;
    JButton SalirItem;
    JButton SalirLotes;
    JButton SalirMoneda;
    JButton SalirPais;
    JButton SalirPreventa;
    JButton SalirProducto;
    JButton SalirRetencion;
    JButton SalirSuc;
    JButton SalirTicket;
    JButton SalirVendedor;
    JButton Upditem;
    JDialog actualizaTicket;
    JTextField autorizacion;
    JTextField banco;
    JButton buscarCliente;
    JButton buscarMoneda;
    JButton buscarSucursal;
    JTextField buscarcadena;
    JButton buscarcomprobante;
    JButton buscarobra;
    JButton buscarpreventa;
    JTextField cModo;
    JTextField cModo1;
    JTextField caja;
    JFormattedTextField cantidad;
    JTextField cliente;
    JTextField clienteret;
    JTextField clienteticket;
    JTextField codprod;
    JComboBox combobanco;
    JComboBox combocaja;
    JComboBox combocliente;
    JComboBox combocomprobante;
    JComboBox comboforma;
    JComboBox combomoneda;
    JComboBox combopais;
    JComboBox comboproducto;
    JComboBox combosucursal;
    JComboBox combovendedor;
    JComboBox comboventa;
    JTextField comprobante;
    JTextField comprobanteticket;
    JDateChooser confirmacion;
    JFormattedTextField cotizacion;
    JTextField creferencia;
    JTextField creferenciaret;
    JTextField creferenciaticket;
    JFormattedTextField cuotas;
    JDateChooser dFinal;
    JDateChooser dInicial;
    JButton delitem;
    JDialog detalle_venta;
    JTextField direccioncliente;
    JButton editaritem;
    JCheckBox enviarcta;
    JLabel etiquetavendedor;
    LabelMetric etiquetaventas;
    JFormattedTextField exentas;
    JTextField factura;
    JTextField facturafinal;
    JTextField facturainicial;
    JDateChooser fecha;
    JDateChooser fecharetencion;
    JDateChooser fechaticket;
    JDialog financiacion;
    JTextField forma;
    JDialog formapago;
    JPanelWebCam fotoProducto;
    JButton grabarFinanciacion;
    JButton grabarPago;
    JFormattedTextField gravadas10;
    JFormattedTextField gravadas5;
    JTextField idControl;
    JFormattedTextField importe_gravado_total;
    JFormattedTextField importe_iva;
    JFormattedTextField importe_sin_iva;
    JFormattedTextField importecheque;
    JButton imprimirlotes;
    JDialog itemventas;
    JLabel jLabel1;
    JLabel jLabel10;
    JLabel jLabel11;
    JLabel jLabel12;
    JLabel jLabel13;
    JLabel jLabel14;
    JLabel jLabel15;
    JLabel jLabel16;
    JLabel jLabel19;
    JLabel jLabel2;
    JLabel jLabel21;
    JLabel jLabel24;
    JLabel jLabel25;
    JLabel jLabel26;
    JLabel jLabel27;
    JLabel jLabel28;
    JLabel jLabel29;
    JLabel jLabel3;
    JLabel jLabel30;
    JLabel jLabel31;
    JLabel jLabel32;
    JLabel jLabel33;
    JLabel jLabel34;
    JLabel jLabel35;
    JLabel jLabel36;
    JLabel jLabel37;
    JLabel jLabel38;
    JLabel jLabel39;
    JLabel jLabel4;
    JLabel jLabel40;
    JLabel jLabel41;
    JLabel jLabel42;
    JLabel jLabel43;
    JLabel jLabel44;
    JLabel jLabel45;
    JLabel jLabel46;
    JLabel jLabel47;
    JLabel jLabel48;
    JLabel jLabel49;
    JLabel jLabel5;
    JLabel jLabel50;
    JLabel jLabel51;
    JLabel jLabel52;
    JLabel jLabel53;
    JLabel jLabel54;
    JLabel jLabel55;
    JLabel jLabel56;
    JLabel jLabel57;
    JLabel jLabel58;
    JLabel jLabel59;
    JLabel jLabel6;
    JLabel jLabel60;
    JLabel jLabel61;
    JLabel jLabel62;
    JLabel jLabel65;
    JLabel jLabel7;
    JLabel jLabel8;
    JLabel jLabel9;
    JMenu jMenu1;
    JMenuBar jMenuBar1;
    JMenuItem jMenuItem1;
    JMenuItem jMenuItem2;
    JMenuItem jMenuItem3;
    JMenuItem jMenuItem4;
    JMenuItem jMenuItem5;
    JMenuItem jMenuItem6;
    JMenuItem jMenuItem7;
    JPanel jPanel1;
    JPanel jPanel10;
    JPanel jPanel11;
    JPanel jPanel12;
    JPanel jPanel13;
    JPanel jPanel14;
    JPanel jPanel15;
    JPanel jPanel16;
    JPanel jPanel17;
    JPanel jPanel18;
    JPanel jPanel19;
    JPanel jPanel2;
    JPanel jPanel20;
    JPanel jPanel21;
    JPanel jPanel22;
    JPanel jPanel23;
    JPanel jPanel24;
    JPanel jPanel25;
    JPanel jPanel26;
    JPanel jPanel27;
    JPanel jPanel28;
    JPanel jPanel29;
    JPanel jPanel3;
    JPanel jPanel30;
    JPanel jPanel31;
    JPanel jPanel32;
    JPanel jPanel33;
    JPanel jPanel34;
    JPanel jPanel35;
    JPanel jPanel36;
    JPanel jPanel37;
    JPanel jPanel38;
    JPanel jPanel39;
    JPanel jPanel40;
    JPanel jPanel41;
    JPanel jPanel42;
    JPanel jPanel43;
    JPanel jPanel44;
    JPanel jPanel45;
    JPanel jPanel46;
    JPanel jPanel47;
    JPanel jPanel48;
    JPanel jPanel49;
    JPanel jPanel5;
    JPanel jPanel50;
    JPanel jPanel51;
    JPanel jPanel6;
    JPanel jPanel7;
    JPanel jPanel8;
    JPanel jPanel9;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane10;
    JScrollPane jScrollPane11;
    JScrollPane jScrollPane12;
    JScrollPane jScrollPane13;
    JScrollPane jScrollPane14;
    JScrollPane jScrollPane15;
    JScrollPane jScrollPane16;
    JScrollPane jScrollPane2;
    JScrollPane jScrollPane3;
    JScrollPane jScrollPane4;
    JScrollPane jScrollPane5;
    JScrollPane jScrollPane6;
    JScrollPane jScrollPane7;
    JScrollPane jScrollPane8;
    JScrollPane jScrollPane9;
    JPopupMenu.Separator jSeparator1;
    JPopupMenu.Separator jSeparator2;
    JPopupMenu.Separator jSeparator3;
    JPopupMenu.Separator jSeparator4;
    JPopupMenu.Separator jSeparator5;
    JPopupMenu.Separator jSeparator6;
    JTextField jTBuscarCaja;
    JTextField jTBuscarCliente;
    JTextField jTBuscarComprobante;
    JTextField jTBuscarForma;
    JTextField jTBuscarMoneda;
    JTextField jTBuscarPais;
    JTextField jTBuscarProducto;
    JTextField jTBuscarSucursal;
    JTextField jTBuscarVendedor;
    JTextField jTBuscarbanco;
    JTabbedPane jTabbedPane1;
    JLabel lblcantidad;
    JLabel lblcodigo;
    JLabel lblnombre;
    JDialog lotes;
    JTextField modo;
    JTextField moneda;
    JTextField monedaret;
    JTextField monedaticket;
    JFormattedTextField monto;
    JFormattedTextField montocuota;
    JFormattedTextField ncuotas;
    JTextField nombrebanco;
    JTextField nombrecaja;
    JTextField nombrecliente;
    JTextField nombreclienteret;
    JTextField nombreclienteticket;
    JTextField nombrecomprobante;
    JTextField nombrecomprobanteticket;
    JTextField nombreformapago;
    JTextField nombremoneda;
    JTextField nombremonedaret;
    JTextField nombremonedaticket;
    JTextField nombreobra;
    JTextField nombreproducto;
    JTextField nombresucursal;
    JTextField nombresucursalret;
    JTextField nombresucursalticket;
    JTextField nombrevendedor;
    JTextField nrocheque;
    JTextField nrofactura;
    JTextField nrofacturaticket;
    JTextField nroretencion;
    JTextField nroticket;
    JTextField nrotimbrado;
    JButton nuevoitem;
    JTextField obra;
    JTextArea observacion;
    Panel panel1;
    JFormattedTextField porcentaje;
    JFormattedTextField porcentaje_retencion;
    JFormattedTextField precio;
    JFormattedTextField preventa;
    JDialog preventas;
    JDateChooser primeracuota;
    JDateChooser primervence;
    JButton refrescar;
    JDialog retenciones;
    JButton salirFinanciacion;
    JButton salirPago;
    JTextField sucursal;
    JTextField sucursalret;
    JTextField sucursalticket;
    JTable tablabanco;
    JTable tablacaja;
    JTable tablacliente;
    JTable tablacomprobante;
    JTable tabladetalle;
    JTable tablafinanciacion;
    JTable tablaformapago;
    JTable tablamoneda;
    JTable tablapagos;
    JTable tablapais;
    JTable tablapreventa;
    JTable tablaproducto;
    JTable tablasucursal;
    JTable tablavendedor;
    JTable tablaventas;
    JFormattedTextField totalfinanciado;
    JFormattedTextField totalitem;
    JFormattedTextField totalneto;
    JFormattedTextField totalticket;
    JFormattedTextField totalvalores;
    JFormattedTextField valor_retencion;
    JDateChooser venceanterior;
    JDateChooser vencetimbrado;
    JDateChooser vencimientos;
    JTextField vendedor;
    JButton visualizar;
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
                for (venta orden : DAO.MostrarxFechaFerremax(dFechaInicio, dFechaFinal, 1)) {
                    String Datos[] = {orden.getCreferencia(),
                        formatosinpunto.format(orden.getFactura()),
                        orden.getTicketold(),
                        formatoFecha.format(orden.getFecha()),
                        orden.getComprobante().getNombre(),
                        orden.getSucursal().getNombre(),
                        orden.getCliente().getNombre(),
                        orden.getMoneda().getEtiqueta(),
                        formatea.format(orden.getExentas()),
                        formatea.format(orden.getGravadas5()),
                        formatea.format(orden.getGravadas10()),
                        formatea.format(orden.getTotalneto()),
                        String.valueOf(orden.getMoneda().getCodigo()),
                        String.valueOf(orden.getCaja().getCodigo()),
                        orden.getHoragrabado()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaventas.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = tablaventas.getRowCount();
            if (cantFilas > 0) {
                Modificar.setEnabled(true);
                Eliminar.setEnabled(true);
                Listar.setEnabled(true);
                visualizar.setEnabled(true);
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                Listar.setEnabled(false);
                visualizar.setEnabled(false);
            }
            tablaventas.requestFocus();
            buscarcadena.requestFocus();
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
                    String Datos[] = {String.valueOf(suc.getCodigo()), suc.getNombre(), formatosinpunto.format(suc.getFactura()), suc.getExpedicion()};
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
                    String Datos[] = {String.valueOf(pr.getCodigo()), pr.getNombre(), formatea.format(pr.getPrecio_maximo()), formatea.format(nPorcentajeIVA)};
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
            bancoplazaDAO bancoDAO = new bancoplazaDAO();
            try {
                for (bancoplaza ba : bancoDAO.todos()) {
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

    public class GuardarVenta extends Thread {

        public void run() {
            if (Integer.valueOf(modo.getText()) == 1) {
                UUID id = new UUID();
                referencia = UUID.crearUUID();
                referencia = referencia.substring(1, 25);
            } else {
                referencia = creferencia.getText();
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
                suc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
                cli = cliDAO.buscarId(Integer.valueOf(cliente.getText()));
                com = coDAO.buscarId(Integer.valueOf(comprobante.getText()));
                mn = mnDAO.buscarId(Integer.valueOf(moneda.getText()));
                gi = giDAO.buscarId(1); //verificar y tener en cuenta para ingreso por teclado o asociar a Cliente
                ve = veDAO.buscarId(Integer.valueOf(vendedor.getText()));
                ca = caDAO.buscarId(1);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            venta v = new venta();
            ventaDAO grabarventa = new ventaDAO();
            //Capturamos los Valores BigDecimal
            String cExentas = exentas.getText();
            cExentas = cExentas.replace(".", "").replace(",", ".");
            BigDecimal nExentas = new BigDecimal(cExentas);

            String cGravadas10 = gravadas10.getText();
            cGravadas10 = cGravadas10.replace(".", "").replace(",", ".");
            BigDecimal nGravadas10 = new BigDecimal(cGravadas10);

            String cGravadas5 = gravadas5.getText();
            cGravadas5 = cGravadas5.replace(".", "").replace(",", ".");
            BigDecimal nGravadas5 = new BigDecimal(cGravadas5);

            cTotalValores = totalvalores.getText();
            cTotalValores = cTotalValores.replace(".", "").replace(",", ".");
            BigDecimal nSupago = new BigDecimal(cTotalValores);

            cTotalNeto = totalneto.getText();
            cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
            BigDecimal nTotalNeto = new BigDecimal(cTotalNeto);

            String cCotizacion = cotizacion.getText();
            cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
            String cNumeroFactura = factura.getText();
            cNumeroFactura = cNumeroFactura.replace("-", "");
            BigDecimal nCotizacion = new BigDecimal(cCotizacion);

            String cContadorFactura = cNumeroFactura.substring(6, 13);
            v.setCreferencia(referencia);
            v.setFecha(FechaProceso);
            v.setFormatofactura(factura.getText());
            v.setFactura(Double.valueOf(cNumeroFactura));
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
            v.setCuotas(Integer.valueOf(cuotas.getText()));
            v.setFinanciado(nTotalNeto.subtract(nSupago));
            v.setObservacion(observacion.getText());
            v.setSupago(nSupago);
            v.setVencimientotimbrado(FechaVenceTimbrado);
            v.setNrotimbrado(Integer.valueOf(nrotimbrado.getText()));
            v.setIdusuario(Integer.valueOf(Config.CodUsuario));
            v.setPreventa(Integer.valueOf(preventa.getText()));
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
                cProducto = String.valueOf(modelodetalle.getValueAt(i, 0));
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

                String linea = "{dreferencia : '" + referencia + "',"
                        + "codprod : '" + cProducto + "',"
                        + "prcosto : " + cCosto + ","
                        + "cantidad : " + cCantidad + ","
                        + "precio : " + cPrecio + ","
                        + "monto : " + cMonto + ","
                        + "impiva: " + cIvaItem + ","
                        + "porcentaje : " + civa + ","
                        + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            System.out.println(detalle);
            String detacuota = "[";
            if (Integer.valueOf(cuotas.getText()) > 0) {
                Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
                calendar.setTime(primervence.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                String iddoc = null;
                String cImporteCuota = null;
                if (Integer.valueOf(moneda.getText()) == 1) {
                    cImporteCuota = String.valueOf(Math.round((Double.valueOf(cTotalNeto) - Double.valueOf(cTotalValores)) / Double.valueOf(cuotas.getText())));
                } else {
                    cImporteCuota = String.valueOf((Double.valueOf(cTotalNeto) - Double.valueOf(cTotalValores)) / Double.valueOf(cuotas.getText()));
                }

                detacuota = "[";
                for (int i = 1; i <= Integer.valueOf(cuotas.getText()); i++) {
                    vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
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
                            + "comprobante : " + com.getCodigo() + ","
                            + "vendedor : " + vendedor.getText() + ","
                            + "importe : " + cImporteCuota + ","
                            + "numerocuota : " + cuotas.getText() + ","
                            + "cuota : " + i + ","
                            + "saldo : " + cImporteCuota
                            + "},";
                    detacuota += lineacuota;
                    calendar.setTime(vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                    venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior
                    int mes = venceanterior.getCalendar().get(Calendar.MONTH) + 1;
                    int dia = venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
                    calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0
                    if (mes == 2 && dia == 28) {
                        calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días                            //el vencimiento
                    }
                    vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                }
                if (!detacuota.equals("[")) {
                    detacuota = detacuota.substring(0, detacuota.length() - 1);
                }
                detacuota += "]";
            }
            String detalleformapago = "[";
            if (Double.valueOf(cTotalValores) > 0) {
                int item = tablapagos.getRowCount();
                item -= 1;
                for (int i = 0; i <= item; i++) {
                    supago = tablapagos.getValueAt(i, 6).toString();
                    supago = supago.replace(".", "").replace(",", ".");
                    String cNrocheque = tablapagos.getValueAt(i, 4).toString();
                    if (cNrocheque.isEmpty()) {
                        cNrocheque = "XXXX";
                    }
                    try {
                        dConfirma = ODate.de_java_a_sql(formatoFecha.parse(tablapagos.getValueAt(i, 5).toString()));
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    String linea = "{idmovimiento : " + referencia + ","
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
            }

            if (Integer.valueOf(modo.getText()) == 1) {
                try {
                    grabarventa.AgregarFacturaVenta(Config.cToken, v, detalle);
                    if (Integer.valueOf(cuotas.getText()) > 0) {
                        cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                        ctaDAO.guardarCuenta(detacuota);
                    }
                    if (Double.valueOf(cTotalValores) > 0) {
                        detalle_forma_cobroDAO cobDAO = new detalle_forma_cobroDAO();
                        cobDAO.guardarFormaPago(detalleformapago);
                    }
                    grabarventa.ActualizarFactura(Integer.valueOf(sucursal.getText()), Double.valueOf(cContadorFactura) + 1);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                try {
                    detalle_ventaDAO delDAO = new detalle_ventaDAO();
                    delDAO.borrarDetalleVenta(referencia);
                    grabarventa.borrarDetalleCuenta(referencia);
                    grabarventa.ActualizarVenta(v, detalle);
                    detalle_forma_cobroDAO cobDAO = new detalle_forma_cobroDAO();
                    cobDAO.borrarDetalleFormaPago(referencia);
                    if (Integer.valueOf(cuotas.getText()) > 0) {
                        cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                        ctaDAO.guardarCuenta(detacuota);
                    }
                    if (Double.valueOf(cTotalValores) > 0) {
                        cobDAO.guardarFormaPago(detalleformapago);
                    }

                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }
    }

    private class GenerarContrato extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = tablaventas.getSelectedRow();
                String num = tablaventas.getValueAt(nFila, 10).toString();
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", idControl.getText().trim());

                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/contrato_ventas.jasper");
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

    private class GenerarPagare extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = tablaventas.getSelectedRow();
                String num = tablaventas.getValueAt(nFila, 10).toString();
                num = num.replace(".", "").replace(",", ".");
                numero_a_letras numero = new numero_a_letras();
                parameters.put("cEmpresa", Config.cNombreEmpresa);
                parameters.put("cDireccion", Config.cDireccion);
                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cReferencia", idControl.getText().trim());

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/pagare_generico.jasper");
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

    private class GenerarDevolucion extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = tablaventas.getSelectedRow();
                String num = tablaventas.getValueAt(nFila, 10).toString();
                num = num.replace(".", "").replace(",", ".");
                numero_a_letras numero = new numero_a_letras();
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", idControl.getText().trim());

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/retiro_mercaderia_carway.jasper");
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

    private class Lotes extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            stm2 = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();

            String cSql = "SELECT creferencia,totalneto,factura FROM cabecera_ventas WHERE factura>=" + facturainicial.getText() + " AND factura<= " + facturafinal.getText() + " ORDER BY factura ";
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    referencia = results.getString("creferencia");
                    double nTotalNeto = results.getDouble("totalneto");

                    try {
                        Map parameters = new HashMap();
                        //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                        //en el reporte
                        String num = String.valueOf(nTotalNeto);
                        numero_a_letras numero = new numero_a_letras();

                        parameters.put("Letra", numero.Convertir(num, true, 2));

                        parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                        parameters.put("cReferencia", referencia);
                        JasperReport jr = null;
                        URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.toString());
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
                results.close();
                stm.close();
                stm2.close();
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class GrillaCajaVenta extends Thread {

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

    private class GenerarReporte extends Thread {

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
                int nFila = tablaventas.getSelectedRow();
                String num = tablaventas.getValueAt(nFila, 10).toString();
                num = num.replace(".", "").replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, Integer.valueOf(tablaventas.getValueAt(nFila, 11).toString())));
                parameters.put("cRuc", Config.cRucEmpresa);
                parameters.put("cTelefono", Config.cTelefono);
                parameters.put("cDireccion", Config.cDireccion);
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", tablaventas.getValueAt(nFila, 0).toString());

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

    private class GrillaObra extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopais.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopais.removeRow(0);
            }
            obraDAO paDAO = new obraDAO();
            try {
                for (obra ba : paDAO.todosxCliente(Integer.valueOf(cliente.getText()))) {
                    String Datos[] = {String.valueOf(ba.getCodigo()), ba.getNombre()};
                    modelopais.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablapais.setRowSorter(new TableRowSorter(modelopais));
            int cantFilas = tablapais.getRowCount();
        }
    }

    
    private class GrillaPreventas extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopreventa.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopreventa.removeRow(0);
            }
            preventaDAO DAOpre = new preventaDAO();
            try {
                for (preventa prev : DAOpre.TodosFerremax()) {
                    System.out.println(prev.getTotaldescuento());
                    String Datos[] = {String.valueOf(formatosinpunto.format(prev.getNumero())),
                        formatoFecha.format(prev.getFecha()),
                        prev.getCliente().getNombre(),
                        formatea.format(prev.getTotalneto()),
                        String.valueOf(prev.getCliente().getCodigo()),
                        prev.getCliente().getRuc(),
                        prev.getCliente().getDireccion(),
                        String.valueOf(prev.getVendedor().getCodigo()),
                        prev.getVendedor().getNombre(),
                        String.valueOf(prev.getComprobante().getCodigo()),
                        prev.getComprobante().getNombre(),
                        prev.getObservacion(),
                        formatosinpunto.format(prev.getCuotas()),
                        prev.getNombreobra(), formatea.format(prev.getTotaldescuento()),
                        prev.getFirma(),
                        formatosinpunto.format(prev.getCaja())};
                    modelopreventa.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablapreventa.setRowSorter(new TableRowSorter(modelopreventa));
            int cantFilas = tablapreventa.getRowCount();
            FormatoTabla ft = new FormatoTabla(15, 14);
            tablapreventa.setDefaultRenderer(Object.class, ft);
        }
    }
    
    private class CargaDetallePreventas extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = tabladetalle.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            detalle_preventaDAO detDAO = new detalle_preventaDAO();
            try {
                for (detalle_preventa detpre : detDAO.MostrarDetalle(Integer.valueOf(preventa.getText()))) {
                    //int nItem = tabladetalle.getRowCount() + 1;
                    String Datos[] = {detpre.getCodprod().getCodigo(), detpre.getCodprod().getNombre(), formatcantidad.format(detpre.getCantidad()), formatea.format(detpre.getPorcentaje()), formatea.format(detpre.getPrecio()), formatea.format(detpre.getMonto())};
                    modelodetalle.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            sumatoria();
            tabladetalle.setRowSorter(new TableRowSorter(modelodetalle));
            int cantFilas = tabladetalle.getRowCount();
        }
    }
    
    
}

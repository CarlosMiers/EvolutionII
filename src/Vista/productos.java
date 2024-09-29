/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.GenerarEan13;
import Clases.GenerarEan8;
import Clases.UUID;
import Clases.clsExportarExcel;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.cabecera_compraDAO;
import Modelo.cabecera_compra;
import DAO.ajuste_mercaderiaDAO;
import DAO.albumfoto_productoDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.familiaDAO;
import DAO.lista_preciosDAO;
import DAO.marcaDAO;
import DAO.medidaDAO;
import DAO.paisDAO;
import DAO.planDAO;
import DAO.productoDAO;
import DAO.proveedorDAO;
import DAO.rubroDAO;
import DAO.stockDAO;
import DAO.sucursalDAO;
import DAO.ubicacionDAO;
import Modelo.Tablas;
import Modelo.albumfoto_producto;
import Modelo.familia;
import Modelo.lista_precios;
import Modelo.marca;
import Modelo.medida;
import Modelo.pais;
import Modelo.producto;
import Modelo.proveedor;
import Modelo.rubro;
import Modelo.ubicacion;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;
import Modelo.EtiquetaMultiple;
import Modelo.ajuste_mercaderia;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.plan;
import Modelo.stock;
import Modelo.sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 */
public class productos extends javax.swing.JFrame {

    Conexion con = null;
    Statement st = null;
    String cadenabuscar = "";
    clsExportarExcel obj;
    int indiceTabla = 0;
    int nbusqueda = 0;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelopais = new Tablas();
    Tablas modeloplan = new Tablas();
    Tablas modeloetiqueta = new Tablas();
    Tablas modeloproveedor = new Tablas();
    Tablas modelofamilia = new Tablas();
    Tablas modelorubro = new Tablas();
    Tablas modelomarca = new Tablas();
    Tablas modelomedida = new Tablas();
    Tablas modeloubicacion = new Tablas();
    Tablas modelolista = new Tablas();
    Tablas modeloprecios = new Tablas();
    Tablas modelostock = new Tablas();
    Tablas modelohistorico = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltroplan, trsfiltro, trsfiltropais, trsfiltroproveedor, trsfiltrofamilia, trsfiltrorubro, trsfiltromarca, trsfiltromedida, trsfiltroubicacion;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatoStock = new DecimalFormat("#######.####");
    DecimalFormat formatosinpunto = new DecimalFormat("#######");
    String cSql = null;
    int nFila = 0;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String ruta, cNombre = null;
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconodolar = new ImageIcon("src/Iconos/icono_dolar.jpg");
    ImageIcon iconoean = new ImageIcon("src/Iconos/pencil.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");

    /**
     * Creates new form Template
     */
    public productos() {
        initComponents();
        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        this.GrabaEnlace.setIcon(iconograbar);
        this.SalirEnlace.setIcon(iconosalir);
        this.UpdatePrecios.setIcon(iconodolar);
        this.BuscarRubroPrecio.setIcon(iconobuscar);
        this.GrabarProducto.setIcon(iconograbar);
        this.Salir.setIcon(iconosalir);
        this.iditem.setVisible(false);
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.SalirCompleto.setIcon(iconosalir);
        this.BuscarCta.setIcon(iconobuscar);
        this.buscarpais.setIcon(iconobuscar);
        this.PrintTicket.setIcon(iconoprint);
        this.Closeticket.setIcon(iconosalir);
        this.buscarfamilia.setIcon(iconobuscar);
        this.buscarmarca.setIcon(iconobuscar);
        this.buscarmedida.setIcon(iconobuscar);
        this.buscarproveedor.setIcon(iconobuscar);
        this.buscarrubro.setIcon(iconobuscar);
        this.buscarubicacion.setIcon(iconobuscar);
        this.fecha.setVisible(false);

        this.tablaproductos.setShowGrid(false);
        this.tablaproductos.setOpaque(true);
        this.tablaproductos.setBackground(new Color(204, 204, 255));
        this.tablaproductos.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.modo.setVisible(false);
        this.modo.setEnabled(false);
        this.idControl.setText("0");
        this.idControl.setVisible(false);
        this.imagen.setSize(372, 259);
        this.cargarTitulo();
        this.cargarTituloxRubro();
        this.TitFamilia();
        this.TitProveedor();
        this.TitRubro();
        this.TitPais();
        this.TitMarca();
        this.TitMedida();
        this.TitUbicacion();
        this.TitLista();
        this.TituloEtiqueta();
        this.TitPlan();
        this.TitStock();
        this.TitHistorico();
        if (Config.usar_ean == 1) {
            this.BotonCodigoBarra.setText("EAN 8");
        } else {
            this.BotonCodigoBarra.setText("EAN 13");
        }

        GrillaProductos GrillaOC = new GrillaProductos();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        GrillaPais grillapa = new GrillaPais();
        Thread hilopa = new Thread(grillapa);
        hilopa.start();

        GrillaProveedor grillaca = new GrillaProveedor();
        Thread hiloca = new Thread(grillaca);
        hiloca.start();

        GrillaFamilia grillafa = new GrillaFamilia();
        Thread hilofa = new Thread(grillafa);
        hilofa.start();

        GrillaRubro grillaru = new GrillaRubro();
        Thread hiloru = new Thread(grillaru);
        hiloru.start();

        GrillaMarca grillama = new GrillaMarca();
        Thread hiloma = new Thread(grillama);
        hiloma.start();

        GrillaMedida grillame = new GrillaMedida();
        Thread hilome = new Thread(grillame);
        hilome.start();

        GrillaUbicacion grillaub = new GrillaUbicacion();
        Thread hiloub = new Thread(grillaub);
        hiloub.start();

        GrillaPlanProducto grillapl = new GrillaPlanProducto();
        Thread hilopl = new Thread(grillapl);
        hilopl.start();
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

        detalle_productos = new javax.swing.JDialog();
        GrabarProducto = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        paises = new javax.swing.JTextField();
        buscarpais = new javax.swing.JButton();
        nombrepais = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        proveedor = new javax.swing.JTextField();
        buscarproveedor = new javax.swing.JButton();
        nombreproveedor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        codfamilia = new javax.swing.JTextField();
        rubro = new javax.swing.JTextField();
        marca = new javax.swing.JTextField();
        medida = new javax.swing.JTextField();
        LblUbicacion = new javax.swing.JLabel();
        ubicacion = new javax.swing.JTextField();
        buscarfamilia = new javax.swing.JButton();
        buscarrubro = new javax.swing.JButton();
        buscarmarca = new javax.swing.JButton();
        buscarmedida = new javax.swing.JButton();
        buscarubicacion = new javax.swing.JButton();
        nombrefamilia = new javax.swing.JTextField();
        nombrerubro = new javax.swing.JTextField();
        nombremarca = new javax.swing.JTextField();
        nombremedida = new javax.swing.JTextField();
        nombreubicacion = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        Descripción = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        codigobarra = new javax.swing.JTextField();
        modo = new javax.swing.JTextField();
        BotonCodigoBarra = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        costo = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        incremento1 = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        incremento2 = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        preciominorista = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        preciomayorista = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        ivaporcentaje = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        precioventa = new javax.swing.JFormattedTextField();
        minoristasugerido = new javax.swing.JFormattedTextField();
        mayoristasugerido = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        tipo_producto = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        conversion = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        conteomayorista = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        stockminimo = new javax.swing.JFormattedTextField();
        estado = new javax.swing.JCheckBox();
        cambiarprecio = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        etiquetaporcentaje = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        codprod = new javax.swing.JTextField();
        descripcionproducto = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaprecio = new javax.swing.JTable();
        EditarPrecio = new javax.swing.JButton();
        AgregarPrecio = new javax.swing.JButton();
        BorraPrecio = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablahistorico = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        BotonAbriArchivo = new javax.swing.JButton();
        nombrearchivo = new javax.swing.JTextField();
        imagen = new javax.swing.JLabel();
        GuardarArchivo = new javax.swing.JButton();
        BPaises = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combopais = new javax.swing.JComboBox();
        jTBuscarPais = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablapais = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarPais = new javax.swing.JButton();
        SalirPais = new javax.swing.JButton();
        BProveedor = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        comboproveedor = new javax.swing.JComboBox();
        jTBuscarProveedor = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaproveedor = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarCasa = new javax.swing.JButton();
        SalirCasa = new javax.swing.JButton();
        BFamilia = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        combofamilia = new javax.swing.JComboBox();
        jTBuscarFamilia = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablafamilia = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        AceptarFamilia = new javax.swing.JButton();
        SalirFamilia = new javax.swing.JButton();
        BRubro = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        comborubro = new javax.swing.JComboBox();
        jTBuscarRubro = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablarubro = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarRubro = new javax.swing.JButton();
        SalirRubro = new javax.swing.JButton();
        BMarca = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combomarca = new javax.swing.JComboBox();
        jTBuscarMarca = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablamarca = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarMarca = new javax.swing.JButton();
        SalirMarca = new javax.swing.JButton();
        BMedida = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        combomedida = new javax.swing.JComboBox();
        jTBuscarMedida = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablamedida = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarMedida = new javax.swing.JButton();
        SalirMedida = new javax.swing.JButton();
        BUbicacion = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        comboubicacion = new javax.swing.JComboBox();
        jTBuscarUbicacion = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaubicacion = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarUbicacion = new javax.swing.JButton();
        SalirUbicacion = new javax.swing.JButton();
        stock = new javax.swing.JDialog();
        jPanel30 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        codigoproducto = new javax.swing.JTextField();
        nombremercaderia = new javax.swing.JTextField();
        stockactual = new javax.swing.JFormattedTextField();
        conteo = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        costomercaderia = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jPanel32 = new javax.swing.JPanel();
        GrabarStock = new javax.swing.JButton();
        SalirStock = new javax.swing.JButton();
        etiquetas_multiples = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaetiqueta = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        PrintTicket = new javax.swing.JButton();
        Closeticket = new javax.swing.JButton();
        Ean8Vence = new javax.swing.JDialog();
        jPanel28 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        codproducto = new javax.swing.JTextField();
        nombreproducto = new javax.swing.JTextField();
        precioproducto = new javax.swing.JTextField();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jPanel29 = new javax.swing.JPanel();
        ImprimirEanVence = new javax.swing.JButton();
        SalirEanVence = new javax.swing.JButton();
        listaprecios = new javax.swing.JDialog();
        jPanel31 = new javax.swing.JPanel();
        cantidad = new javax.swing.JFormattedTextField();
        porcentaje = new javax.swing.JFormattedTextField();
        preciofinal = new javax.swing.JFormattedTextField();
        iditem = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        GrabarLista = new javax.swing.JButton();
        SalirLista = new javax.swing.JButton();
        cuentas = new javax.swing.JDialog();
        jPanel34 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        codcuenta = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        descripcion = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        idcta = new javax.swing.JTextField();
        nombrecuenta = new javax.swing.JTextField();
        BuscarCta = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        GrabaEnlace = new javax.swing.JButton();
        SalirEnlace = new javax.swing.JButton();
        BCuenta = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        comboplan = new javax.swing.JComboBox();
        jTBuscarPlan = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaplan = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        actualizar_precios = new javax.swing.JDialog();
        jPanel38 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        codrubro = new javax.swing.JTextField();
        nombrerubroprecio = new javax.swing.JTextField();
        BuscarRubroPrecio = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        mayorista = new javax.swing.JFormattedTextField();
        minorista = new javax.swing.JFormattedTextField();
        jLabel46 = new javax.swing.JLabel();
        CalcularPrecios = new javax.swing.JButton();
        ConsultarPrecios = new javax.swing.JButton();
        redondear = new javax.swing.JComboBox<>();
        jPanel39 = new javax.swing.JPanel();
        ActualizarPrecios = new javax.swing.JButton();
        SalirPrecios = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablaprecios = new javax.swing.JTable();
        inventario = new javax.swing.JDialog();
        jPanel51 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tablastock = new javax.swing.JTable();
        jPanel52 = new javax.swing.JPanel();
        SalirStock1 = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        etiquetacodigo = new javax.swing.JLabel();
        etiquetanombre = new javax.swing.JLabel();
        actualizar_precio = new javax.swing.JDialog();
        jPanel43 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        codprod1 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        nombreproducto1 = new javax.swing.JTextField();
        costo1 = new javax.swing.JFormattedTextField();
        porcentaje1 = new javax.swing.JFormattedTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        minorista1 = new javax.swing.JFormattedTextField();
        mayorista1 = new javax.swing.JFormattedTextField();
        jLabel52 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        GrabarPrecios = new javax.swing.JButton();
        Salir1 = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetacredito = new org.edisoncor.gui.label.LabelMetric();
        comboproducto = new javax.swing.JComboBox();
        buscarcadena = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        SalirCompleto = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        Eliminar = new javax.swing.JButton();
        UpdatePrecios = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaproductos = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();

        detalle_productos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_productosFocusGained(evt);
            }
        });
        detalle_productos.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_productosWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_productos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_productosWindowActivated(evt);
            }
        });

        GrabarProducto.setText("Grabar");
        GrabarProducto.setToolTipText("Guardar los Cambios");
        GrabarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarProductoActionPerformed(evt);
            }
        });

        Salir.setText("Salir");
        Salir.setToolTipText("Salir sin Guardar");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setText("Procedencia");

        paises.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        paises.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                paisesFocusGained(evt);
            }
        });
        paises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paisesActionPerformed(evt);
            }
        });
        paises.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                paisesKeyPressed(evt);
            }
        });

        buscarpais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarpais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarpaisActionPerformed(evt);
            }
        });

        nombrepais.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrepais.setEnabled(false);

        jLabel6.setText("Proveedor");

        proveedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        buscarproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarproveedorActionPerformed(evt);
            }
        });

        nombreproveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreproveedor.setEnabled(false);

        jLabel8.setText("Rubro");

        jLabel9.setText("Familia");

        jLabel10.setText("Marca");

        jLabel11.setText("Medida");

        codfamilia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codfamilia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codfamiliaFocusGained(evt);
            }
        });
        codfamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codfamiliaActionPerformed(evt);
            }
        });
        codfamilia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codfamiliaKeyPressed(evt);
            }
        });

        rubro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        marca.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        marca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                marcaFocusGained(evt);
            }
        });
        marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marcaActionPerformed(evt);
            }
        });
        marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                marcaKeyPressed(evt);
            }
        });

        medida.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        medida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                medidaFocusGained(evt);
            }
        });
        medida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medidaActionPerformed(evt);
            }
        });
        medida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                medidaKeyPressed(evt);
            }
        });

        LblUbicacion.setText("Ubicación");

        ubicacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ubicacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ubicacionFocusGained(evt);
            }
        });
        ubicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubicacionActionPerformed(evt);
            }
        });
        ubicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ubicacionKeyPressed(evt);
            }
        });

        buscarfamilia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarfamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarfamiliaActionPerformed(evt);
            }
        });

        buscarrubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarrubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarrubroActionPerformed(evt);
            }
        });

        buscarmarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarmarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarmarcaActionPerformed(evt);
            }
        });

        buscarmedida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarmedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarmedidaActionPerformed(evt);
            }
        });

        buscarubicacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarubicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarubicacionActionPerformed(evt);
            }
        });

        nombrefamilia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrefamilia.setEnabled(false);

        nombrerubro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrerubro.setEnabled(false);

        nombremarca.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombremarca.setEnabled(false);

        nombremedida.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombremedida.setEnabled(false);

        nombreubicacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreubicacion.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(codfamilia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rubro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(marca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(paises, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(medida, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(buscarmedida, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombremedida))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(buscarmarca, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombremarca))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(buscarrubro, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombrerubro))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(buscarfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombrefamilia))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(buscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(buscarpais, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombrepais, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(LblUbicacion)
                        .addGap(31, 31, 31)
                        .addComponent(ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nombreubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(paises, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(buscarpais, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(nombrepais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6))
                                                    .addComponent(buscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(codfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(nombrefamilia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(buscarfamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel8)
                                                .addComponent(rubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(nombrerubro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(buscarrubro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(nombremarca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(buscarmarca, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombremedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(medida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))))
                    .addComponent(buscarmedida, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblUbicacion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setText("Código Barra");

        Descripción.setText("Descripción");

        jLabel12.setText("Código");

        codigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codigoFocusGained(evt);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigoKeyReleased(evt);
            }
        });

        nombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nombreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombreFocusLost(evt);
            }
        });
        nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreActionPerformed(evt);
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

        codigobarra.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        codigobarra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codigobarraFocusGained(evt);
            }
        });
        codigobarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigobarraActionPerformed(evt);
            }
        });
        codigobarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigobarraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codigobarraKeyReleased(evt);
            }
        });

        BotonCodigoBarra.setText("EAN 8");
        BotonCodigoBarra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonCodigoBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCodigoBarraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Descripción)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codigobarra, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonCodigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonCodigoBarra))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Descripción)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(codigobarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Costo");

        costo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        costo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        costo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                costoFocusGained(evt);
            }
        });
        costo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                costoKeyPressed(evt);
            }
        });

        jLabel2.setText("% Minorista");

        incremento1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        incremento1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        incremento1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                incremento1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                incremento1FocusLost(evt);
            }
        });
        incremento1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incremento1ActionPerformed(evt);
            }
        });
        incremento1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                incremento1KeyPressed(evt);
            }
        });

        jLabel3.setText("% Mayorista");

        incremento2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        incremento2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        incremento2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                incremento2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                incremento2FocusLost(evt);
            }
        });
        incremento2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incremento2ActionPerformed(evt);
            }
        });
        incremento2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                incremento2KeyPressed(evt);
            }
        });

        jLabel4.setText("Precio Minorista");

        preciominorista.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        preciominorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        preciominorista.setToolTipText("Precio Minorista - con Recambio");
        preciominorista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                preciominoristaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                preciominoristaFocusLost(evt);
            }
        });
        preciominorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preciominoristaActionPerformed(evt);
            }
        });
        preciominorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                preciominoristaKeyPressed(evt);
            }
        });

        jLabel7.setText("Precio Mayorista");

        preciomayorista.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        preciomayorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        preciomayorista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                preciomayoristaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                preciomayoristaFocusLost(evt);
            }
        });
        preciomayorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                preciomayoristaKeyPressed(evt);
            }
        });

        jLabel18.setText("IVA %");

        ivaporcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ivaporcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ivaporcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ivaporcentajeFocusGained(evt);
            }
        });
        ivaporcentaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ivaporcentajeKeyPressed(evt);
            }
        });

        jLabel23.setText("Precio s/Recambio");

        precioventa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        precioventa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precioventa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                precioventaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                precioventaFocusLost(evt);
            }
        });
        precioventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                precioventaKeyPressed(evt);
            }
        });

        minoristasugerido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        minoristasugerido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        minoristasugerido.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        minoristasugerido.setEnabled(false);

        mayoristasugerido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        mayoristasugerido.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mayoristasugerido.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        mayoristasugerido.setEnabled(false);

        jLabel24.setText("Minorista Sugerido");

        jLabel25.setText("Mayorista Sugerido");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(preciomayorista, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(preciominorista, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(incremento2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(incremento1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(costo)
                            .addComponent(precioventa))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minoristasugerido, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel25))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(mayoristasugerido, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(66, 66, 66)
                        .addComponent(ivaporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(incremento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(incremento2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minoristasugerido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(preciominorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(preciomayorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mayoristasugerido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(precioventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ivaporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setText("Tipo");

        tipo_producto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mercaderías", "Materia Prima", "Productos en Proceso", "Productos Terminados", "Importaciones en Curso", "Servicios" }));
        tipo_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipo_productoKeyPressed(evt);
            }
        });

        jLabel14.setText("Conversión");

        conversion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        conversion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        conversion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                conversionKeyPressed(evt);
            }
        });

        jLabel16.setText("C. Mayorista");

        conteomayorista.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        conteomayorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        conteomayorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                conteomayoristaKeyPressed(evt);
            }
        });

        jLabel17.setText("Stock Mínimo");

        stockminimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        stockminimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        stockminimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stockminimoKeyPressed(evt);
            }
        });

        estado.setText("Estado ");

        cambiarprecio.setText("Cambiar Precio PV");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tipo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conversion, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conteomayorista, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockminimo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cambiarprecio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(estado)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tipo_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(conversion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(conteomayorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(stockminimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estado)
                    .addComponent(cambiarprecio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Generales", jPanel1);

        observacion.setColumns(20);
        observacion.setRows(5);
        jScrollPane2.setViewportView(observacion);

        jLabel19.setText("Ingrese aquí las Características Principales del Producto");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Observaciones", jPanel7);

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        etiquetaporcentaje.setText(org.openide.util.NbBundle.getMessage(productos.class, "detalle_clientes.jLabel44.text")); // NOI18N

        jLabel20.setText("Código Producto");

        codprod.setEnabled(false);

        descripcionproducto.setEnabled(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(etiquetaporcentaje)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descripcionproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descripcionproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetaporcentaje))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        tablaprecio.setModel(modelolista);
        jScrollPane3.setViewportView(tablaprecio);

        EditarPrecio.setText("Editar");
        EditarPrecio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EditarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarPrecioActionPerformed(evt);
            }
        });

        AgregarPrecio.setText("Grabar");
        AgregarPrecio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarPrecioActionPerformed(evt);
            }
        });

        BorraPrecio.setText("Borrar");
        BorraPrecio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorraPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorraPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(144, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(AgregarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EditarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BorraPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addGap(97, 97, 97))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EditarPrecio)
                    .addComponent(AgregarPrecio)
                    .addComponent(BorraPrecio))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lista de Precios", jPanel10);

        jPanel42.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablahistorico.setModel(modelohistorico);
        jScrollPane14.setViewportView(tablahistorico);

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Histórico de Costos", jPanel41);

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonAbriArchivo.setText("Abrir");
        BotonAbriArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAbriArchivoActionPerformed(evt);
            }
        });

        imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagen.setText("Presentación");

        GuardarArchivo.setText("Guardar");
        GuardarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarArchivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonAbriArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GuardarArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAbriArchivo)
                    .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(GuardarArchivo)))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Imagen del Producto", jPanel8);

        javax.swing.GroupLayout detalle_productosLayout = new javax.swing.GroupLayout(detalle_productos.getContentPane());
        detalle_productos.getContentPane().setLayout(detalle_productosLayout);
        detalle_productosLayout.setHorizontalGroup(
            detalle_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_productosLayout.createSequentialGroup()
                .addGap(424, 424, 424)
                .addComponent(GrabarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(detalle_productosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        detalle_productosLayout.setVerticalGroup(
            detalle_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_productosLayout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarProducto)
                    .addComponent(Salir))
                .addContainerGap())
        );

        BPaises.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BPaises.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combopais.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combopais.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combopais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combopais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combopaisActionPerformed(evt);
            }
        });

        jTBuscarPais.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPais.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarPaisActionPerformed(evt);
            }
        });
        jTBuscarPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPaisKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combopais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPais, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combopais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablapais.setModel(modelopais);
        tablapais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablapaisMouseClicked(evt);
            }
        });
        tablapais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablapaisKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablapais);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarPais.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarPais.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarPais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarPaisActionPerformed(evt);
            }
        });

        SalirPais.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirPais.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirPais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarPais, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirPais, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPais)
                    .addComponent(SalirPais))
                .addContainerGap())
        );

        javax.swing.GroupLayout BPaisesLayout = new javax.swing.GroupLayout(BPaises.getContentPane());
        BPaises.getContentPane().setLayout(BPaisesLayout);
        BPaisesLayout.setHorizontalGroup(
            BPaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPaisesLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BPaisesLayout.setVerticalGroup(
            BPaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPaisesLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BProveedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProveedor.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproveedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproveedorActionPerformed(evt);
            }
        });

        jTBuscarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarProveedor.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane7.setViewportView(tablaproveedor);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa)
                    .addComponent(SalirCasa))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProveedorLayout = new javax.swing.GroupLayout(BProveedor.getContentPane());
        BProveedor.getContentPane().setLayout(BProveedorLayout);
        BProveedorLayout.setHorizontalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProveedorLayout.setVerticalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BFamilia.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BFamilia.setTitle("null");

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combofamilia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combofamilia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combofamilia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combofamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combofamiliaActionPerformed(evt);
            }
        });

        jTBuscarFamilia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarFamilia.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarFamiliaActionPerformed(evt);
            }
        });
        jTBuscarFamilia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarFamiliaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(combofamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combofamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablafamilia.setModel(modelofamilia);
        tablafamilia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablafamiliaMouseClicked(evt);
            }
        });
        tablafamilia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablafamiliaKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tablafamilia);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarFamilia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarFamilia.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarFamilia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarFamiliaActionPerformed(evt);
            }
        });

        SalirFamilia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirFamilia.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirFamilia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFamiliaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarFamilia)
                    .addComponent(SalirFamilia))
                .addContainerGap())
        );

        javax.swing.GroupLayout BFamiliaLayout = new javax.swing.GroupLayout(BFamilia.getContentPane());
        BFamilia.getContentPane().setLayout(BFamiliaLayout);
        BFamiliaLayout.setHorizontalGroup(
            BFamiliaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFamiliaLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BFamiliaLayout.setVerticalGroup(
            BFamiliaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFamiliaLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BRubro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BRubro.setTitle("null");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comborubro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comborubro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comborubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comborubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comborubroActionPerformed(evt);
            }
        });

        jTBuscarRubro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarRubro.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablarubro.setModel(modelorubro);
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
        jScrollPane4.setViewportView(tablarubro);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarRubro.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarRubroActionPerformed(evt);
            }
        });

        SalirRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirRubro.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarRubro)
                    .addComponent(SalirRubro))
                .addContainerGap())
        );

        javax.swing.GroupLayout BRubroLayout = new javax.swing.GroupLayout(BRubro.getContentPane());
        BRubro.getContentPane().setLayout(BRubroLayout);
        BRubroLayout.setHorizontalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BRubroLayout.setVerticalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMarca.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMarca.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomarca.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomarcaActionPerformed(evt);
            }
        });

        jTBuscarMarca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMarca.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarMarcaActionPerformed(evt);
            }
        });
        jTBuscarMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarMarcaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combomarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablamarca.setModel(modelomarca);
        tablamarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablamarcaMouseClicked(evt);
            }
        });
        tablamarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablamarcaKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablamarca);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMarca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMarca.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMarcaActionPerformed(evt);
            }
        });

        SalirMarca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMarca.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMarcaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMarca)
                    .addComponent(SalirMarca))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMarcaLayout = new javax.swing.GroupLayout(BMarca.getContentPane());
        BMarca.getContentPane().setLayout(BMarcaLayout);
        BMarcaLayout.setHorizontalGroup(
            BMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMarcaLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMarcaLayout.setVerticalGroup(
            BMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMarcaLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMedida.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMedida.setTitle("null");

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomedida.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomedida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomedida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomedidaActionPerformed(evt);
            }
        });

        jTBuscarMedida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMedida.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarMedidaActionPerformed(evt);
            }
        });
        jTBuscarMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarMedidaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(combomedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablamedida.setModel(modelomedida);
        tablamedida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablamedidaMouseClicked(evt);
            }
        });
        tablamedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablamedidaKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablamedida);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMedida.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMedida.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMedida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMedidaActionPerformed(evt);
            }
        });

        SalirMedida.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMedida.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMedida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMedidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMedida)
                    .addComponent(SalirMedida))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMedidaLayout = new javax.swing.GroupLayout(BMedida.getContentPane());
        BMedida.getContentPane().setLayout(BMedidaLayout);
        BMedidaLayout.setHorizontalGroup(
            BMedidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMedidaLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMedidaLayout.setVerticalGroup(
            BMedidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMedidaLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BUbicacion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BUbicacion.setTitle("null");

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboubicacion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboubicacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboubicacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboubicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboubicacionActionPerformed(evt);
            }
        });

        jTBuscarUbicacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarUbicacion.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarUbicacionActionPerformed(evt);
            }
        });
        jTBuscarUbicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarUbicacionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(comboubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboubicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaubicacion.setModel(modeloubicacion);
        tablaubicacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaubicacionMouseClicked(evt);
            }
        });
        tablaubicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaubicacionKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(tablaubicacion);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarUbicacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarUbicacion.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarUbicacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarUbicacionActionPerformed(evt);
            }
        });

        SalirUbicacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirUbicacion.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirUbicacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirUbicacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarUbicacion)
                    .addComponent(SalirUbicacion))
                .addContainerGap())
        );

        javax.swing.GroupLayout BUbicacionLayout = new javax.swing.GroupLayout(BUbicacion.getContentPane());
        BUbicacion.getContentPane().setLayout(BUbicacionLayout);
        BUbicacionLayout.setHorizontalGroup(
            BUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BUbicacionLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BUbicacionLayout.setVerticalGroup(
            BUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BUbicacionLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        stock.setTitle("Ajuste de Mercadería");

        jPanel30.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setText("Código");

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setText("Nombre Mercadería");

        codigoproducto.setEditable(false);
        codigoproducto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        nombremercaderia.setEditable(false);
        nombremercaderia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        stockactual.setEditable(false);
        stockactual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        stockactual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        stockactual.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        conteo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        conteo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        conteo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setText("Stock Actual");

        jLabel33.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel33.setText("Conteo");

        costomercaderia.setEditable(false);
        costomercaderia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        costomercaderia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel35.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel35.setText("Costo");

        fecha.setEnabled(false);
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

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30))
                        .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel32)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombremercaderia, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(conteo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(stockactual, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(costomercaderia, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(86, 86, 86)
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(codigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(nombremercaderia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(costomercaderia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35))
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(stockactual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conteo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addContainerGap())
        );

        jPanel32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarStock.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        GrabarStock.setText("Grabar");
        GrabarStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarStockActionPerformed(evt);
            }
        });

        SalirStock.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SalirStock.setText("Salir");
        SalirStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addComponent(GrabarStock, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(SalirStock, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarStock)
                    .addComponent(SalirStock))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout stockLayout = new javax.swing.GroupLayout(stock.getContentPane());
        stock.getContentPane().setLayout(stockLayout);
        stockLayout.setHorizontalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        stockLayout.setVerticalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablaetiqueta.setModel(modeloetiqueta);
        jScrollPane11.setViewportView(tablaetiqueta);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel27.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        PrintTicket.setText("Imprimir");
        PrintTicket.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PrintTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintTicketActionPerformed(evt);
            }
        });

        Closeticket.setText("Salir");
        Closeticket.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Closeticket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseticketActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(PrintTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(Closeticket, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PrintTicket)
                    .addComponent(Closeticket))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout etiquetas_multiplesLayout = new javax.swing.GroupLayout(etiquetas_multiples.getContentPane());
        etiquetas_multiples.getContentPane().setLayout(etiquetas_multiplesLayout);
        etiquetas_multiplesLayout.setHorizontalGroup(
            etiquetas_multiplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(etiquetas_multiplesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(etiquetas_multiplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        etiquetas_multiplesLayout.setVerticalGroup(
            etiquetas_multiplesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(etiquetas_multiplesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Ean8Vence.setTitle("Imprimir Etiqueta del Producto");

        jPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setText("Código");

        jLabel27.setText("Nombre Producto");

        jLabel28.setText("Precio");

        jLabel29.setText("Vencimiento");

        codproducto.setEnabled(false);

        nombreproducto.setEnabled(false);

        precioproducto.setEnabled(false);

        vencimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vencimientoFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(25, 25, 25)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreproducto)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(precioproducto, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(vencimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(codproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(precioproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ImprimirEanVence.setText("Imprimir");
        ImprimirEanVence.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ImprimirEanVence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImprimirEanVenceActionPerformed(evt);
            }
        });

        SalirEanVence.setText("Salir");
        SalirEanVence.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirEanVence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirEanVenceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(ImprimirEanVence, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(SalirEanVence, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ImprimirEanVence)
                    .addComponent(SalirEanVence))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Ean8VenceLayout = new javax.swing.GroupLayout(Ean8Vence.getContentPane());
        Ean8Vence.getContentPane().setLayout(Ean8VenceLayout);
        Ean8VenceLayout.setHorizontalGroup(
            Ean8VenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Ean8VenceLayout.setVerticalGroup(
            Ean8VenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Ean8VenceLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        listaprecios.setTitle("Actualizar Lista de Precios");

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadKeyPressed(evt);
            }
        });

        porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        porcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        porcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                porcentajeFocusLost(evt);
            }
        });
        porcentaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                porcentajeKeyPressed(evt);
            }
        });

        preciofinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        preciofinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        preciofinal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                preciofinalFocusLost(evt);
            }
        });
        preciofinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                preciofinalKeyPressed(evt);
            }
        });

        iditem.setEnabled(false);

        jLabel36.setText("Cantidad");

        jLabel37.setText("% Utilidad");

        jLabel38.setText("Precio");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37))
                .addGap(26, 26, 26)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(porcentaje, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(preciofinal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(preciofinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarLista.setText("Grabar");
        GrabarLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarListaActionPerformed(evt);
            }
        });

        SalirLista.setText("Salir");
        SalirLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirListaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(SalirLista, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarLista)
                    .addComponent(SalirLista))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout listapreciosLayout = new javax.swing.GroupLayout(listaprecios.getContentPane());
        listaprecios.getContentPane().setLayout(listapreciosLayout);
        listapreciosLayout.setHorizontalGroup(
            listapreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        listapreciosLayout.setVerticalGroup(
            listapreciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listapreciosLayout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cuentas.setTitle("Configurar Cuenta de Enlace");

        jPanel34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel39.setText("Código");

        codcuenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codcuenta.setEnabled(false);

        jLabel40.setText("Descripción");

        descripcion.setEnabled(false);

        jLabel41.setText("Cuenta Contable");

        idcta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idcta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idctaActionPerformed(evt);
            }
        });

        nombrecuenta.setEnabled(false);

        BuscarCta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCtaActionPerformed(evt);
            }
        });

        jLabel42.setText("Denominación Cuenta");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(idcta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BuscarCta, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombrecuenta)
                    .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(codcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarCta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41)
                        .addComponent(idcta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabaEnlace.setText("Grabar");
        GrabaEnlace.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabaEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabaEnlaceActionPerformed(evt);
            }
        });

        SalirEnlace.setText("Salir");
        SalirEnlace.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirEnlaceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabaEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(SalirEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabaEnlace)
                    .addComponent(SalirEnlace))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout cuentasLayout = new javax.swing.GroupLayout(cuentas.getContentPane());
        cuentas.getContentPane().setLayout(cuentasLayout);
        cuentasLayout.setHorizontalGroup(
            cuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        cuentasLayout.setVerticalGroup(
            cuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cuentasLayout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCuenta.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCuenta.setTitle("null");

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboplan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboplan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboplan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboplan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboplanActionPerformed(evt);
            }
        });

        jTBuscarPlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPlan.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarPlanActionPerformed(evt);
            }
        });
        jTBuscarPlan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPlanKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(comboplan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboplan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaplan.setModel(modeloplan);
        tablaplan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaplanMouseClicked(evt);
            }
        });
        tablaplan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaplanKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(tablaplan);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCuentaLayout = new javax.swing.GroupLayout(BCuenta.getContentPane());
        BCuenta.getContentPane().setLayout(BCuentaLayout);
        BCuentaLayout.setHorizontalGroup(
            BCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCuentaLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCuentaLayout.setVerticalGroup(
            BCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCuentaLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel43.setText("Rubro");

        codrubro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codrubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codrubroActionPerformed(evt);
            }
        });

        nombrerubroprecio.setEnabled(false);

        BuscarRubroPrecio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarRubroPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarRubroPrecioActionPerformed(evt);
            }
        });

        jLabel44.setText("% Mayorista");

        jLabel45.setText("% Minorista");

        mayorista.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        mayorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        minorista.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        minorista.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel46.setText("Redondear");

        CalcularPrecios.setText("Calcular");
        CalcularPrecios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CalcularPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CalcularPreciosActionPerformed(evt);
            }
        });

        ConsultarPrecios.setText("Consultar");
        ConsultarPrecios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ConsultarPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarPreciosActionPerformed(evt);
            }
        });

        redondear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "50 Gs.", "100 Gs." }));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(codrubro, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarRubroPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombrerubroprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mayorista)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel45))
                    .addComponent(minorista, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(redondear, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CalcularPrecios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ConsultarPrecios, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(ConsultarPrecios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CalcularPrecios)
                            .addComponent(redondear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mayorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarRubroPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel43)
                                .addComponent(codrubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombrerubroprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel39.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ActualizarPrecios.setText("Actualizar Precios");
        ActualizarPrecios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ActualizarPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarPreciosActionPerformed(evt);
            }
        });

        SalirPrecios.setText("Salir");
        SalirPrecios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPreciosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(ActualizarPrecios, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(SalirPrecios, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ActualizarPrecios)
                    .addComponent(SalirPrecios))
                .addContainerGap())
        );

        tablaprecios.setModel(modeloprecios);
        jScrollPane13.setViewportView(tablaprecios);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout actualizar_preciosLayout = new javax.swing.GroupLayout(actualizar_precios.getContentPane());
        actualizar_precios.getContentPane().setLayout(actualizar_preciosLayout);
        actualizar_preciosLayout.setHorizontalGroup(
            actualizar_preciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actualizar_preciosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actualizar_preciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        actualizar_preciosLayout.setVerticalGroup(
            actualizar_preciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualizar_preciosLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        inventario.setTitle(org.openide.util.NbBundle.getMessage(productos.class, "ventas_mercaderias.inventario.title")); // NOI18N

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

        SalirStock1.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas_mercaderias.SalirStock.text")); // NOI18N
        SalirStock1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirStock1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirStock1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel52Layout.createSequentialGroup()
                .addComponent(SalirStock1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SalirStock1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel53.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel56.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas_mercaderias.jLabel56.text")); // NOI18N

        jLabel57.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas_mercaderias.jLabel57.text")); // NOI18N

        etiquetacodigo.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas_mercaderias.etiquetacodigo.text")); // NOI18N

        etiquetanombre.setText(org.openide.util.NbBundle.getMessage(productos.class, "ventas_mercaderias.etiquetanombre.text")); // NOI18N

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

        actualizar_precio.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel43.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel47.setText("Código");

        codprod1.setEnabled(false);

        jLabel48.setText("Descripción");

        nombreproducto1.setEnabled(false);

        costo1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        costo1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        costo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                costo1FocusGained(evt);
            }
        });
        costo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                costo1KeyReleased(evt);
            }
        });

        porcentaje1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        porcentaje1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        porcentaje1.setToolTipText("");
        porcentaje1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                porcentaje1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                porcentaje1FocusLost(evt);
            }
        });
        porcentaje1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                porcentaje1KeyReleased(evt);
            }
        });

        jLabel49.setText("Costo");

        jLabel50.setText("% ");

        jLabel51.setText("Minorista");

        minorista1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        minorista1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        minorista1.setToolTipText("");
        minorista1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                minorista1FocusGained(evt);
            }
        });
        minorista1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                minorista1KeyReleased(evt);
            }
        });

        mayorista1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        mayorista1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mayorista1.setToolTipText("");
        mayorista1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mayorista1FocusGained(evt);
            }
        });
        mayorista1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mayorista1KeyReleased(evt);
            }
        });

        jLabel52.setText("Mayorista");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel51)
                    .addComponent(jLabel48)
                    .addComponent(jLabel47)
                    .addComponent(jLabel49)
                    .addComponent(jLabel50)
                    .addComponent(jLabel52))
                .addGap(18, 18, 18)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreproducto1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(mayorista1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addComponent(minorista1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(porcentaje1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(costo1, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(codprod1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(codprod1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48)
                    .addComponent(nombreproducto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(porcentaje1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(minorista1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mayorista1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel44.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarPrecios.setText("Grabar");
        GrabarPrecios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarPreciosActionPerformed(evt);
            }
        });

        Salir1.setText("Salir");
        Salir1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Salir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(GrabarPrecios, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(Salir1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarPrecios)
                    .addComponent(Salir1))
                .addContainerGap())
        );

        javax.swing.GroupLayout actualizar_precioLayout = new javax.swing.GroupLayout(actualizar_precio.getContentPane());
        actualizar_precio.getContentPane().setLayout(actualizar_precioLayout);
        actualizar_precioLayout.setHorizontalGroup(
            actualizar_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualizar_precioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actualizar_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        actualizar_precioLayout.setVerticalGroup(
            actualizar_precioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualizar_precioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
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
        etiquetacredito.setText("Productos");

        comboproducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboproducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Nombre", "Código de Barra" }));
        comboproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproductoActionPerformed(evt);
            }
        });

        buscarcadena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buscarcadena.setName(""); // NOI18N
        buscarcadena.setSelectionColor(new java.awt.Color(0, 63, 62));
        buscarcadena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                buscarcadenaFocusGained(evt);
            }
        });
        buscarcadena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcadenaActionPerformed(evt);
            }
        });
        buscarcadena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscarcadenaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarcadenaKeyReleased(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("F2 = Seleccionar Productos");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("F3 = Impresión Ticket");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setText("F4 = Ajuste Stock");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel34))
                    .addComponent(jLabel22))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        Eliminar.setBackground(new java.awt.Color(255, 255, 255));
        Eliminar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Eliminar.setText("Eliminar Registro");
        Eliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        UpdatePrecios.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        UpdatePrecios.setText("Precios");
        UpdatePrecios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdatePrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdatePreciosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(SalirCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(UpdatePrecios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(48, Short.MAX_VALUE))
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
                .addGap(11, 11, 11)
                .addComponent(Listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UpdatePrecios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SalirCompleto)
                .addGap(55, 55, 55)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(289, Short.MAX_VALUE))
        );

        tablaproductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablaproductos.setModel(modelo);
        tablaproductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaproductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaproductos.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablaproductos.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablaproductos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablaproductosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablaproductosFocusLost(evt);
            }
        });
        tablaproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproductosMouseClicked(evt);
            }
        });
        tablaproductos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaproductosPropertyChange(evt);
            }
        });
        tablaproductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaproductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaproductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaproductos);

        jMenu1.setText("Opciones");

        jMenuItem1.setText("Etiquetas EAN 8");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem5.setText("Etiqueta EAN 8 con Vencimiento");
        jMenuItem5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator4);

        jMenuItem2.setText("Etiquetas EAN 13");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator3);

        jMenuItem4.setText("Etiqueta QR");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator2);

        jMenuItem7.setText("Actualizar Precios por Lotes");
        jMenuItem7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);
        jMenu1.add(jSeparator6);

        jMenuItem3.setText("Exportar Tabla a Excel");
        jMenuItem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator5);

        jMenuItem6.setText("Cuenta Contable");
        jMenuItem6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MostrarCostos(String cProducto) {
        //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
        int cantidadRegistro = modelohistorico.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelohistorico.removeRow(0);
        }

        cabecera_compraDAO DAO = new cabecera_compraDAO();
        try {
            for (cabecera_compra com : DAO.buscarIdProducto(cProducto)) {
                String Datos[] = {com.getFormatofactura(), formatoFecha.format(com.getFecha()), com.getProveedor().getNombre(), formatea.format(com.getPreciocompra())};
                modelohistorico.addRow(Datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
        tablahistorico.setRowSorter(new TableRowSorter(modelohistorico));
        int cantFilas = tablahistorico.getRowCount();

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

    private void TitHistorico() {
        modelohistorico.addColumn("N° Factura");
        modelohistorico.addColumn("Fecha");
        modelohistorico.addColumn("Nombre Proveedor");
        modelohistorico.addColumn("Costo");

        int[] anchos = {150, 90, 150, 100};
        for (int i = 0; i < modelohistorico.getColumnCount(); i++) {
            tablahistorico.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablahistorico.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablahistorico.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablahistorico.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.tablahistorico.getColumnModel().getColumn(0).setCellRenderer(AlinearCentro);
        this.tablahistorico.getColumnModel().getColumn(1).setCellRenderer(AlinearCentro);
        this.tablahistorico.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
    }

    private void refrescarCarrusel() {
        imagen.setText("");
        imagen.setIcon(null);
        albumfoto_productoDAO imag = new albumfoto_productoDAO();
        albumfoto_producto album = null;
        try {
            album = imag.buscarimagen(codigo.getText());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (album.getNombre() != null) {
            ImageIcon icon = new ImageIcon(album.getFoto().getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_DEFAULT));
            imagen.setText("");
            imagen.setIcon(icon);
            nombrearchivo.setText(album.getNombre().toString());
            this.repaint();
        }
    }

    private void comboproductoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_comboproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproductoActionPerformed

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.tablaproductos.requestFocus();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            if (comboproducto.getSelectedIndex() == 0) {
                comboproducto.setSelectedIndex(1);
            } else {
                comboproducto.setSelectedIndex(0);
            }
            switch (comboproducto.getSelectedIndex()) {
                case 0:
                    indiceTabla = 0;
                    break;//por nombre
                case 1:
                    indiceTabla = 1;
                    break;//por codigo
                case 2:
                    indiceTabla = 10;
                    break;//por codigo
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void SalirCompletoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        cadenabuscar = this.buscarcadena.getText();
        modo.setText("A");
        codigo.setEnabled(true);
        this.limpiar();
        detalle_productos.setModal(true);
        //                        (Ancho,Alto)
        detalle_productos.setSize(787, 550);
        //Establecemos un título para el jDialog
        detalle_productos.setTitle("Agregar Nuevo Producto");
        detalle_productos.setLocationRelativeTo(null);
        codigo.requestFocus();
        detalle_productos.setVisible(true);
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.codigo.setText("");
        this.nombre.setText("");
        this.codigobarra.setText("");
        this.paises.setText("0");
        this.nombrepais.setText("");
        this.proveedor.setText("0");
        this.nombreproveedor.setText("");
        this.rubro.setText("0");
        this.nombrerubro.setText("");
        this.marca.setText("0");
        this.nombremarca.setText("");
        this.ubicacion.setText("0");
        this.nombreubicacion.setText("");
        this.medida.setText("0");
        this.nombremedida.setText("");
        this.codfamilia.setText("0");
        this.nombrefamilia.setText("");
        this.costo.setText("0");
        this.incremento1.setText("0");
        this.incremento2.setText("0");
        this.preciominorista.setText("0");
        this.preciomayorista.setText("0");
        this.precioventa.setText("0");
        this.conteomayorista.setText("0");
        this.conversion.setText("0");
        this.stockminimo.setText("0");
        this.ivaporcentaje.setText("10");
        this.observacion.setText("");
        this.imagen.setText("");
        this.nombrearchivo.setText("");
        this.minoristasugerido.setText("0");
        this.mayoristasugerido.setText("0");
        cambiarprecio.setSelected(true);
        estado.setSelected(true);

        int cantidadRegistro = modelohistorico.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelohistorico.removeRow(0);
        }

    }
    private void tablaproductosKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablaproductosKeyPressed
//      int nReg = this.tablaproductos.getSelectedRow();
        //    this.idControl.setText(this.tablaproductos.getValueAt(nReg, 0).toString());

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductosKeyPressed

    private void tablaproductosMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablaproductosMouseClicked
        int nFila = this.tablaproductos.getSelectedRow();
        this.idControl.setText(this.tablaproductos.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductosMouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        cadenabuscar = this.buscarcadena.getText();
        nFila = this.tablaproductos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaproductos.requestFocus();
            return;
        }

        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            this.limpiar();
            int nestado = 0;
            int ncambiarprecio = 0;
            modo.setText("M");

            this.codigo.setText(this.tablaproductos.getValueAt(nFila, 0).toString());
            this.codigo.setEnabled(false);
            productoDAO pdDAO = new productoDAO();
            producto pd = null;
            try {
                pd = pdDAO.BuscarProducto(this.codigo.getText());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (pd != null) {
                nombre.setText(pd.getNombre());
                codprod.setText(pd.getCodigo());
                descripcionproducto.setText(pd.getNombre());
                codigobarra.setText(pd.getCodigobarra());
                proveedor.setText(String.valueOf(pd.getProveedor().getCodigo()));
                nombreproveedor.setText(pd.getProveedor().getNombre());
                paises.setText(String.valueOf(pd.getPaises().getCodigo()));
                nombrepais.setText(pd.getPaises().getNombre());
                codfamilia.setText(String.valueOf(pd.getFamilia().getCodigo()));
                nombrefamilia.setText(pd.getFamilia().getNombre());
                rubro.setText(String.valueOf(pd.getRubro().getCodigo()));
                nombrerubro.setText(pd.getRubro().getNombre());
                marca.setText(String.valueOf(pd.getMarca().getCodigo()));
                nombremarca.setText(pd.getMarca().getNombre());
                medida.setText(String.valueOf(pd.getMedida().getCodigo()));
                nombremedida.setText(pd.getMedida().getNombre());
                ubicacion.setText(String.valueOf(pd.getUbicacion().getCodigo()));
                nombreubicacion.setText(pd.getUbicacion().getNombre());
                costo.setText(formatea.format(pd.getCosto()));
                incremento1.setText(formatea.format(pd.getIncremento1()));
                incremento2.setText(formatea.format(pd.getIncremento2()));
                preciominorista.setText(formatea.format(pd.getPrecio_maximo()));
                preciomayorista.setText(formatea.format(pd.getPrecio_minimo()));
                precioventa.setText(formatea.format(pd.getPrecioventa()));
                tipo_producto.setSelectedIndex(pd.getTipo_producto() - 1);
                conteomayorista.setText(String.valueOf(pd.getConteomayorista()));
                stockminimo.setText(formatea.format(pd.getStockminimo()));
                conversion.setText(formatea.format(pd.getConversion()));
                ivaporcentaje.setText(formatea.format(pd.getIvaporcentaje()));
                observacion.setText(pd.getObservacion());
                nestado = pd.getEstado();
                ncambiarprecio = pd.getCambiarprecio();

                if (nestado == 1) {
                    estado.setSelected(true);
                } else {
                    estado.setSelected(false);
                }

                if (ncambiarprecio == 1) {
                    cambiarprecio.setSelected(true);
                } else {
                    cambiarprecio.setSelected(false);
                }

                this.refrescarCarrusel();

                GrillaLista GrillaL = new GrillaLista();
                Thread Hilo2 = new Thread(GrillaL);
                Hilo2.start();

                this.MostrarCostos(pd.getCodigo());
                detalle_productos.setModal(true);
                nombre.requestFocus();
                detalle_productos.setSize(787, 550);
                //Establecemos un título para el jDialog
                detalle_productos.setTitle("Modificar Datos del Producto");
                detalle_productos.setLocationRelativeTo(null);
                detalle_productos.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void tablaproductosFocusGained(FocusEvent evt) {//GEN-FIRST:event_tablaproductosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductosFocusGained

    private void jScrollPane1FocusGained(FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        buscarcadena.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowActivated(WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formFocusGained(FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void tablaproductosFocusLost(FocusEvent evt) {//GEN-FIRST:event_tablaproductosFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductosFocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_productos.setVisible(false);
        detalle_productos.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarProductoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarProductoActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito
        if (this.codigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código");
            this.codigo.requestFocus();
            return;
        }
        if (this.nombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Descripción");
            this.nombre.requestFocus();
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            long miliseconds = System.currentTimeMillis();
            Date FechaHoy = new Date(miliseconds);

            productoDAO grabarPRD = new productoDAO();
            producto pd = new producto();

            proveedorDAO provDAO = new proveedorDAO();
            proveedor pr = null;

            paisDAO paisDAO = new paisDAO();
            pais pa = null;

            familiaDAO famDAO = new familiaDAO();
            familia fl = null;

            marcaDAO marDAO = new marcaDAO();
            marca ma = null;

            medidaDAO medDAO = new medidaDAO();
            medida me = null;

            rubroDAO rubDAO = new rubroDAO();
            rubro ru = null;

            ubicacionDAO ubiDAO = new ubicacionDAO();
            ubicacion ubi = null;

            try {
                pr = provDAO.buscarId(Integer.valueOf(this.proveedor.getText()));
                pa = paisDAO.buscarId(Integer.valueOf(this.paises.getText()));
                fl = famDAO.buscarId(Integer.valueOf(this.codfamilia.getText()));
                ma = marDAO.buscarId(Integer.valueOf(this.marca.getText()));
                me = medDAO.buscarId(Integer.valueOf(this.medida.getText()));
                ru = rubDAO.buscarId(Integer.valueOf(this.rubro.getText()));
                ubi = ubiDAO.buscarId(Integer.valueOf(this.ubicacion.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            //CAPTURAMOS LOS DATOS DE LA CABECERA
            pd.setCodigo(this.codigo.getText());
            pd.setNombre(this.nombre.getText());
            pd.setCodigobarra(this.codigobarra.getText());
            pd.setProveedor(pr);
            pd.setFamilia(fl);
            pd.setMarca(ma);
            pd.setRubro(ru);
            pd.setMedida(me);
            pd.setPaises(pa);
            pd.setUbicacion(ubi);
            pd.setObservacion(this.observacion.getText());
            pd.setTipo_producto(tipo_producto.getSelectedIndex() + 1);
            pd.setFecha_ingreso(FechaHoy);

            //EMPEZAMOS A CAPTURAR DATOS NUMERICOS
            String cCosto = this.costo.getText();
            cCosto = cCosto.replace(".", "").replace(",", ".");
            BigDecimal ncosto = new BigDecimal(cCosto);
            pd.setCosto(ncosto);

            String cInc1 = this.incremento1.getText();
            cInc1 = cInc1.replace(".", "").replace(",", ".");
            BigDecimal inc1 = new BigDecimal(cInc1);
            pd.setIncremento1(inc1);

            String cInc2 = this.incremento2.getText();
            cInc2 = cInc2.replace(".", "").replace(",", ".");
            BigDecimal inc2 = new BigDecimal(cInc2);
            pd.setIncremento2(inc2);

            String cPMax = this.preciominorista.getText();
            cPMax = cPMax.replace(".", "").replace(",", ".");
            BigDecimal pmax = new BigDecimal(cPMax);
            pd.setPrecio_maximo(pmax);

            String cPMin = this.preciomayorista.getText();
            cPMin = cPMin.replace(".", "").replace(",", ".");
            BigDecimal pmin = new BigDecimal(cPMin);
            pd.setPrecio_minimo(pmin);

            String cImpuesto = this.ivaporcentaje.getText();
            cImpuesto = cImpuesto.replace(".", "").replace(",", ".");
            BigDecimal piva = new BigDecimal(cImpuesto);
            pd.setIvaporcentaje(piva);

            String cConversion = conversion.getText();
            cConversion = cConversion.replace(".", "").replace(",", ".");
            BigDecimal nconv = new BigDecimal(cConversion);
            pd.setConversion(nconv);

            String cStockMin = stockminimo.getText();
            cStockMin = cStockMin.replace(".", "").replace(",", ".");
            BigDecimal nStock = new BigDecimal(cStockMin);
            pd.setStockminimo(nStock);

            String cPrecioVenta = precioventa.getText();
            cPrecioVenta = cPrecioVenta.replace(".", "").replace(",", ".");
            BigDecimal nPrecioVenta = new BigDecimal(cPrecioVenta);
            pd.setPrecioventa(nPrecioVenta);

            String cConteo = conteomayorista.getText();
            cConteo = cConteo.replace(".", "").replace(",", ".");
            pd.setConteomayorista(Integer.valueOf(cConteo));

            if (estado.isSelected()) {
                pd.setEstado(1);
            } else {
                pd.setEstado(0);
            }

            if (cambiarprecio.isSelected()) {
                pd.setCambiarprecio(1);
            } else {
                pd.setCambiarprecio(0);
            }

            if (this.modo.getText().equals("A")) {
                try {
                    grabarPRD.insertarProducto(pd);

                    configuracionDAO cDAO = new configuracionDAO();
                    configuracion config = null;
                    config = cDAO.consultar();
                    double ninventario = 0.00;
                    stockDAO stDAO = new stockDAO();
                    stock stk = new stock();
                    stk.setProducto(pd);
                    stk.setSucursal(config.getSucursaldefecto());
                    stk.setStock(ninventario);
                    stDAO.insertarStockCero(stk);

                    /*if (Config.cIpServerCopia != null && !Config.cIpServerCopia.equals("")) {
                        productoEspejoDAO cpDAO = new productoEspejoDAO();
                        cpDAO.insertarProducto(pd);
                    }*/
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } else {
                //Actualizar 
                try {
                    grabarPRD.actualizarProductos(pd);
                    /* if (Config.cIpServerCopia != null && !Config.cIpServerCopia.equals("")) {
                        productoEspejoDAO cpDAO = new productoEspejoDAO();
                        cpDAO.actualizarProductos(pd);
                    }*/

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                    this.nombre.requestFocus();
                    return;
                }
                JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
            }
            detalle_productos.setVisible(false);
            this.detalle_productos.setModal(false);

            GrillaProductos GrillaP = new GrillaProductos();
            Thread HiloGrilla = new Thread(GrillaP);
            HiloGrilla.start();
        }
    }//GEN-LAST:event_GrabarProductoActionPerformed

    private void detalle_productosFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_productosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_productosFocusGained

    private void detalle_productosWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_productosWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_productosWindowGainedFocus

    private void detalle_productosWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_productosWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_productosWindowActivated

    private void tablaproductosPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_tablaproductosPropertyChange

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductosPropertyChange

    private void codigoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nombre.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyPressed

    private void paisesKeyPressed(KeyEvent evt) {//GEN-FIRST:event_paisesKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.proveedor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codigobarra.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_paisesKeyPressed

    private void proveedorKeyPressed(KeyEvent evt) {//GEN-FIRST:event_proveedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.codfamilia.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.paises.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorKeyPressed

    private void codigoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
        productoDAO pdDAO = new productoDAO();
        producto pd = null;
        try {
            pd = pdDAO.BuscarProducto(this.codigo.getText());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (pd.getCodigo() != null) {
            JOptionPane.showMessageDialog(null, "Este Producto ya Existe");
            this.codigo.requestFocus();
            return;
        }
    }//GEN-LAST:event_codigoActionPerformed

    private void combopaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combopaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combopaisActionPerformed

    private void jTBuscarPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarPaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPaisActionPerformed

    private void jTBuscarPaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPaisKeyPressed
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

    private void tablapaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablapaisKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPais.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablapaisKeyPressed

    private void AceptarPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarPaisActionPerformed
        int nFila = this.tablapais.getSelectedRow();
        this.paises.setText(this.tablapais.getValueAt(nFila, 0).toString());
        this.nombrepais.setText(this.tablapais.getValueAt(nFila, 1).toString());

        this.BPaises.setVisible(false);
        this.jTBuscarPais.setText("");
        this.proveedor.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarPaisActionPerformed

    private void SalirPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPaisActionPerformed
        this.BPaises.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPaisActionPerformed

    private void buscarpaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarpaisActionPerformed
        BPaises.setModal(true);
        BPaises.setSize(482, 575);
        BPaises.setLocationRelativeTo(null);
        BPaises.setVisible(true);
        BPaises.setTitle("Buscar Giraduria");
        proveedor.requestFocus();
        BPaises.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarpaisActionPerformed

    private void tablapaisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablapaisMouseClicked
        this.AceptarPais.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapaisMouseClicked

    private void paisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paisesActionPerformed
        paisDAO paDAO = new paisDAO();
        pais pa = null;
        try {
            pa = paDAO.buscarId(Integer.valueOf(this.paises.getText()));
            if (pa.getCodigo() == 0) {
                this.buscarpais.doClick();
            } else {
                nombrepais.setText(pa.getNombre());
                //Establecemos un título para el jDialog
                proveedor.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_paisesActionPerformed

    private void comboproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproveedorActionPerformed

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
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorMouseClicked

    private void tablaproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorKeyPressed

    private void AceptarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed
        int nFila = this.tablaproveedor.getSelectedRow();
        this.proveedor.setText(this.tablaproveedor.getValueAt(nFila, 0).toString());
        this.nombreproveedor.setText(this.tablaproveedor.getValueAt(nFila, 1).toString());

        this.BProveedor.setVisible(false);
        this.jTBuscarProveedor.setText("");
        this.codfamilia.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.BProveedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    private void proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorActionPerformed
        proveedorDAO provDAO = new proveedorDAO();
        proveedor pr = null;
        try {
            pr = provDAO.buscarId(Integer.valueOf(this.proveedor.getText()));
            if (pr.getCodigo() == 0) {
                this.buscarproveedor.doClick();
            } else {
                nombreproveedor.setText(pr.getNombre());
                //Establecemos un título para el jDialog
            }
            codfamilia.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorActionPerformed

    private void buscarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarproveedorActionPerformed
        BProveedor.setModal(true);
        BProveedor.setSize(482, 575);
        BProveedor.setLocationRelativeTo(null);
        BProveedor.setVisible(true);
        BProveedor.setTitle("Buscar Proveedores");
        BProveedor.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarproveedorActionPerformed

    private void codigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusGained
        codigo.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_codigoFocusGained

    private void paisesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paisesFocusGained
        paises.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_paisesFocusGained

    private void proveedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_proveedorFocusGained
        proveedor.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorFocusGained


    private void codfamiliaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codfamiliaFocusGained
        codfamilia.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_codfamiliaFocusGained

    private void codfamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codfamiliaActionPerformed
        familiaDAO faDAO = new familiaDAO();
        familia fa = null;
        try {
            fa = faDAO.buscarId(Integer.valueOf(this.codfamilia.getText()));
            if (fa.getCodigo() == 0) {
                this.buscarfamilia.doClick();
            } else {
                nombrefamilia.setText(fa.getNombre());
                //Establecemos un título para el jDialog
            }
            rubro.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_codfamiliaActionPerformed

    private void codfamiliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codfamiliaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.rubro.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.proveedor.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_codfamiliaKeyPressed

    private void rubroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rubroFocusGained
        rubro.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroFocusGained

    private void rubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubroActionPerformed
        rubroDAO ruDAO = new rubroDAO();
        rubro ru = null;
        try {
            ru = ruDAO.buscarId(Integer.valueOf(this.rubro.getText()));
            if (ru.getCodigo() == 0) {
                this.buscarrubro.doClick();
            } else {
                nombrerubro.setText(ru.getNombre());
                //Establecemos un título para el jDialog
            }
            marca.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroActionPerformed

    private void rubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rubroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.marca.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codfamilia.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroKeyPressed

    private void marcaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_marcaFocusGained
        marca.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_marcaFocusGained

    private void marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marcaActionPerformed
        marcaDAO maDAO = new marcaDAO();
        marca ma = null;
        try {
            ma = maDAO.buscarId(Integer.valueOf(this.marca.getText()));
            if (ma.getCodigo() == 0) {
                buscarmarca.doClick();
            } else {
                nombremarca.setText(ma.getNombre());
                //Establecemos un título para el jDialog
            }
            medida.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_marcaActionPerformed

    private void marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_marcaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.medida.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.rubro.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_marcaKeyPressed

    private void medidaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_medidaFocusGained
        medida.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_medidaFocusGained

    private void medidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medidaActionPerformed
        medidaDAO meDAO = new medidaDAO();
        medida me = null;
        try {
            me = meDAO.buscarId(Integer.valueOf(this.medida.getText()));
            if (me.getCodigo() == 0) {
                buscarmedida.doClick();
            } else {
                nombremedida.setText(me.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        ubicacion.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_medidaActionPerformed

    private void medidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_medidaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ubicacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.marca.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_medidaKeyPressed

    private void ubicacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ubicacionFocusGained
        ubicacion.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_ubicacionFocusGained

    private void ubicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubicacionActionPerformed
        ubicacionDAO ubiDAO = new ubicacionDAO();
        ubicacion ub = null;
        try {
            ub = ubiDAO.buscarId(Integer.valueOf(this.ubicacion.getText()));
            if (ub.getCodigo() == 0) {
                buscarubicacion.doClick();
            } else {
                nombreubicacion.setText(ub.getNombre());
                //Establecemos un título para el jDialog
            }
            costo.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_ubicacionActionPerformed

    private void ubicacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ubicacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.costo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.medida.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_ubicacionKeyPressed

    private void buscarfamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarfamiliaActionPerformed
        BFamilia.setModal(true);
        BFamilia.setSize(482, 575);
        BFamilia.setLocationRelativeTo(null);
        BFamilia.setVisible(true);
        BFamilia.setTitle("Buscar Familia");
        BFamilia.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarfamiliaActionPerformed

    private void buscarrubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarrubroActionPerformed
        nbusqueda = 1;
        BRubro.setModal(true);
        BRubro.setSize(482, 575);
        BRubro.setLocationRelativeTo(null);
        BRubro.setVisible(true);
        BRubro.setTitle("Buscar Rubro");
        BRubro.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarrubroActionPerformed

    private void buscarmarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarmarcaActionPerformed
        BMarca.setModal(true);
        BMarca.setSize(482, 575);
        BMarca.setLocationRelativeTo(null);
        BMarca.setVisible(true);
        BMarca.setTitle("Buscar Marca");
        BMarca.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarmarcaActionPerformed

    private void buscarmedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarmedidaActionPerformed
        BMedida.setModal(true);
        BMedida.setSize(482, 575);
        BMedida.setLocationRelativeTo(null);
        BMedida.setVisible(true);
        BMedida.setTitle("Buscar Médida");
        BMedida.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarmedidaActionPerformed

    private void buscarubicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarubicacionActionPerformed
        BUbicacion.setModal(true);
        BUbicacion.setSize(482, 575);
        BUbicacion.setLocationRelativeTo(null);
        BUbicacion.setVisible(true);
        BUbicacion.setTitle("Buscar Ubicación");
        BUbicacion.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarubicacionActionPerformed

    private void nombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusGained

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.codigobarra.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codigo.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyPressed

    private void codigobarraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigobarraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_codigobarraFocusGained

    private void codigobarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigobarraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigobarraActionPerformed

    private void codigobarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigobarraKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.paises.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombre.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_codigobarraKeyPressed

    private void nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyReleased

    private void costoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_costoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.incremento1.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ubicacion.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_costoKeyPressed

    private void incremento1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_incremento1KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.incremento2.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.costo.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_incremento1KeyPressed

    private void incremento2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_incremento2KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.preciominorista.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.incremento1.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_incremento2KeyPressed

    private void preciominoristaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preciominoristaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.preciomayorista.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.incremento2.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_preciominoristaKeyPressed

    private void preciomayoristaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preciomayoristaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.precioventa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.preciominorista.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_preciomayoristaKeyPressed

    private void ivaporcentajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ivaporcentajeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tipo_producto.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.precioventa.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_ivaporcentajeKeyPressed

    private void tipo_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipo_productoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.conversion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ivaporcentaje.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_tipo_productoKeyPressed

    private void conversionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_conversionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.conteomayorista.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipo_producto.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_conversionKeyPressed

    private void conteomayoristaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_conteomayoristaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.stockminimo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.conversion.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_conteomayoristaKeyPressed

    private void stockminimoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stockminimoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cambiarprecio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.stockminimo.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_stockminimoKeyPressed

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyReleased

    private void codigobarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigobarraKeyReleased
        String letras = ConvertirMayusculas.cadena(codigobarra);
        codigobarra.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_codigobarraKeyReleased

    private void BotonAbriArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAbriArchivoActionPerformed
        final JFileChooser elegirImagen = new JFileChooser();
        elegirImagen.setMultiSelectionEnabled(false);
        int o = elegirImagen.showOpenDialog(this);
        if (o == JFileChooser.APPROVE_OPTION) {
            String ruta = elegirImagen.getSelectedFile().getAbsolutePath();
            String cNombre = elegirImagen.getSelectedFile().getName();
            nombrearchivo.setText(ruta);
            Image preview = Toolkit.getDefaultToolkit().getImage(ruta);
            if (preview != null) {
                imagen.setText("");
                ImageIcon icon = new ImageIcon(preview.getScaledInstance(imagen.getWidth(), imagen.getHeight(), Image.SCALE_DEFAULT));
                imagen.setIcon(icon);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BotonAbriArchivoActionPerformed

    private void GuardarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarArchivoActionPerformed
        albumfoto_productoDAO GrabarImagen = new albumfoto_productoDAO();
        albumfoto_producto alb = new albumfoto_producto();

        productoDAO prod = new productoDAO();
        producto pr = null;
        try {
            pr = prod.BuscarProducto(codigo.getText());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        alb.setCodigo(pr);
        alb.setNombre(nombrearchivo.getText().toString());
        try {
            GrabarImagen.eliminarFoto(codigo.getText());
            GrabarImagen.insertarimagen(alb, nombrearchivo.getText().toString());
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            nombrearchivo.setText("");
            imagen.setIcon(null);
            refrescarCarrusel();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarArchivoActionPerformed

    private void combofamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combofamiliaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combofamiliaActionPerformed

    private void jTBuscarFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarFamiliaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFamiliaActionPerformed

    private void jTBuscarFamiliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarFamiliaKeyPressed
        this.jTBuscarFamilia.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarFamilia.getText()).toUpperCase();
                jTBuscarFamilia.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combofamilia.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrofamilia(indiceColumnaTabla);
            }
        });
        trsfiltrofamilia = new TableRowSorter(tablafamilia.getModel());
        tablafamilia.setRowSorter(trsfiltrofamilia);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFamiliaKeyPressed

    public void filtrofamilia(int nNumeroColumna) {
        trsfiltrofamilia.setRowFilter(RowFilter.regexFilter(this.jTBuscarFamilia.getText(), nNumeroColumna));
    }


    private void tablafamiliaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablafamiliaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablafamiliaMouseClicked

    private void tablafamiliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablafamiliaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablafamiliaKeyPressed

    private void AceptarFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarFamiliaActionPerformed
        int nFila = this.tablafamilia.getSelectedRow();
        this.codfamilia.setText(this.tablafamilia.getValueAt(nFila, 0).toString());
        this.nombrefamilia.setText(this.tablafamilia.getValueAt(nFila, 1).toString());

        this.BFamilia.setVisible(false);
        this.BFamilia.setModal(false);
        this.jTBuscarFamilia.setText("");
        this.rubro.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarFamiliaActionPerformed

    private void SalirFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFamiliaActionPerformed
        this.BFamilia.setVisible(false);
        this.BFamilia.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFamiliaActionPerformed

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
                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por RUC
                }
                repaint();
                filtrorubro(indiceColumnaTabla);
            }
        });
        trsfiltrorubro = new TableRowSorter(tablarubro.getModel());
        tablarubro.setRowSorter(trsfiltrorubro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarRubroKeyPressed

    private void tablarubroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablarubroMouseClicked
        this.AceptarRubro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablarubroMouseClicked

    private void tablarubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablarubroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarRubro.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablarubroKeyPressed

    private void AceptarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarRubroActionPerformed
        int nFila = this.tablarubro.getSelectedRow();
        if (nbusqueda == 1) {
            this.rubro.setText(this.tablarubro.getValueAt(nFila, 0).toString());
            this.nombrerubro.setText(this.tablarubro.getValueAt(nFila, 1).toString());
            this.marca.requestFocus();
        } else {
            this.codrubro.setText(this.tablarubro.getValueAt(nFila, 0).toString());
            this.nombrerubroprecio.setText(this.tablarubro.getValueAt(nFila, 1).toString());
            this.mayorista.requestFocus();
        }
        this.BRubro.setVisible(false);
        this.jTBuscarRubro.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarRubroActionPerformed

    private void SalirRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirRubroActionPerformed
        this.BRubro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirRubroActionPerformed

    private void combomarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combomarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomarcaActionPerformed

    private void jTBuscarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMarcaActionPerformed

    private void jTBuscarMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarMarcaKeyPressed
        this.jTBuscarMarca.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarMarca.getText()).toUpperCase();
                jTBuscarMarca.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combomarca.getSelectedIndex()) {
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
                filtromarca(indiceColumnaTabla);
            }
        });
        trsfiltromarca = new TableRowSorter(tablamarca.getModel());
        tablamarca.setRowSorter(trsfiltromarca);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMarcaKeyPressed

    private void tablamarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamarcaMouseClicked
        this.AceptarMarca.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamarcaMouseClicked

    private void tablamarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamarcaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarMarca.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamarcaKeyPressed

    private void AceptarMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarMarcaActionPerformed
        int nFila = this.tablamarca.getSelectedRow();
        this.marca.setText(this.tablamarca.getValueAt(nFila, 0).toString());
        this.nombremarca.setText(this.tablamarca.getValueAt(nFila, 1).toString());

        this.BMarca.setVisible(false);
        this.BMarca.setModal(false);
        this.jTBuscarMarca.setText("");
        this.medida.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMarcaActionPerformed

    private void SalirMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMarcaActionPerformed
        this.BMarca.setVisible(false);
        this.BMarca.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMarcaActionPerformed

    private void combomedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combomedidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomedidaActionPerformed

    private void jTBuscarMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarMedidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMedidaActionPerformed

    private void jTBuscarMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarMedidaKeyPressed
        this.jTBuscarMedida.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarMedida.getText()).toUpperCase();
                jTBuscarMedida.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combomedida.getSelectedIndex()) {
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
                filtromedida(indiceColumnaTabla);
            }
        });
        trsfiltromedida = new TableRowSorter(tablamedida.getModel());
        tablamedida.setRowSorter(trsfiltromedida);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMedidaKeyPressed

    private void tablamedidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamedidaMouseClicked
        this.AceptarMedida.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamedidaMouseClicked

    private void tablamedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamedidaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarMedida.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamedidaKeyPressed

    private void AceptarMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarMedidaActionPerformed
        int nFila = this.tablamedida.getSelectedRow();
        this.medida.setText(this.tablamedida.getValueAt(nFila, 0).toString());
        this.nombremedida.setText(this.tablamedida.getValueAt(nFila, 1).toString());

        this.BMedida.setVisible(false);
        this.jTBuscarMedida.setText("");
        this.ubicacion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMedidaActionPerformed

    private void SalirMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMedidaActionPerformed
        this.BMedida.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMedidaActionPerformed

    private void comboubicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboubicacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboubicacionActionPerformed

    private void jTBuscarUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarUbicacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarUbicacionActionPerformed

    private void jTBuscarUbicacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarUbicacionKeyPressed
        this.jTBuscarUbicacion.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarUbicacion.getText()).toUpperCase();
                jTBuscarUbicacion.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboubicacion.getSelectedIndex()) {
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
                filtroubicacion(indiceColumnaTabla);
            }
        });
        trsfiltroubicacion = new TableRowSorter(tablaubicacion.getModel());
        tablaubicacion.setRowSorter(trsfiltroubicacion);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarUbicacionKeyPressed

    private void tablaubicacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaubicacionMouseClicked
        this.AceptarUbicacion.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaubicacionMouseClicked

    private void tablaubicacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaubicacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarUbicacion.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaubicacionKeyPressed

    private void AceptarUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarUbicacionActionPerformed
        int nFila = this.tablaubicacion.getSelectedRow();
        this.ubicacion.setText(this.tablaubicacion.getValueAt(nFila, 0).toString());
        this.nombreubicacion.setText(this.tablaubicacion.getValueAt(nFila, 1).toString());

        this.BUbicacion.setVisible(false);
        this.jTBuscarUbicacion.setText("");
        this.costo.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarUbicacionActionPerformed

    private void SalirUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirUbicacionActionPerformed
        this.BUbicacion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirUbicacionActionPerformed

    private void costoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_costoFocusGained
        costo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_costoFocusGained

    private void incremento1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_incremento1FocusGained
        incremento1.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_incremento1FocusGained

    private void incremento2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_incremento2FocusGained
        incremento2.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_incremento2FocusGained

    private void preciominoristaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preciominoristaFocusGained
        preciominorista.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_preciominoristaFocusGained

    private void preciomayoristaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preciomayoristaFocusGained
        preciomayorista.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_preciomayoristaFocusGained

    private void ivaporcentajeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ivaporcentajeFocusGained
        ivaporcentaje.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_ivaporcentajeFocusGained

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        int nFila = tablaproductos.getSelectedRow();
        String num = tablaproductos.getValueAt(nFila, 0).toString();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaproductos.requestFocus();
            return;
        }

        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                productoDAO pl = new productoDAO();
                try {
                    producto p = pl.BuscarProducto(num);
                    if (p == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        pl.eliminarProducto(num);
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
        }
        GrillaProductos GrillaPr = new GrillaProductos();
        Thread HiloGrilla = new Thread(GrillaPr);
        HiloGrilla.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_EliminarActionPerformed

    private void LimpiarItem() {
        cantidad.setText("0");
        porcentaje.setText("0");
        preciofinal.setText("0");
        iditem.setText("0");
    }

    private void incremento1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incremento1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_incremento1ActionPerformed

    private void preciominoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preciominoristaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_preciominoristaActionPerformed

    private void incremento2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incremento2ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_incremento2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new GenerarEan("ean8.jasper").start();
        //    new GenerarEan("ean8negri.jasper").start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
//        new GenerarEan("ean13.jasper").start();
//      new GenerarEan("ean13_rita.jasper").start();
        //new GenerarEan("ean13lacasita.jasper").start();
        new GenerarEan("ean13piturro.jasper").start();
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void BotonCodigoBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCodigoBarraActionPerformed
        String cCodigo = "";
        if (Config.usar_ean == 1) {
            cCodigo = GenerarEan8.ean8(codigo);
        } else {
            cCodigo = GenerarEan13.ean13(codigo);
        }
        if (this.modo.getText().equals("A")) {
            codigo.setText(cCodigo);
            codigobarra.setText(cCodigo);
        } else {
            codigobarra.setText(cCodigo);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonCodigoBarraActionPerformed

    private void nombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusLost
        String letras = ConvertirMayusculas.cadena(nombre);
        nombre.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusLost

    private void buscarcadenaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buscarcadenaFocusGained
        switch (comboproducto.getSelectedIndex()) {
            case 0:
                indiceTabla = 0;
                break;//por codigo
            case 1:
                indiceTabla = 1;
                break;//por nombre
            case 2:
                indiceTabla = 10;
                break;//por codigo
            }
        if (!buscarcadena.getText().isEmpty()) {
            FiltroPro();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaFocusGained

    private void preciominoristaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preciominoristaFocusLost
        String cCosto = this.costo.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        String cPrecio = this.preciominorista.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCosto) > 0 || Double.valueOf(cPrecio) > 0) {
            this.incremento1.setText(formatea.format((Double.valueOf(cPrecio) - Double.valueOf(cCosto)) / Double.valueOf(cCosto) * 100));
        }
    }//GEN-LAST:event_preciominoristaFocusLost

    private void incremento1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_incremento1FocusLost
        String cCosto = costo.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        String cIncremen1 = incremento1.getText();
        cIncremen1 = cIncremen1.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCosto) > 0 || Double.valueOf(cIncremen1) > 0) {
            this.minoristasugerido.setText(formatea.format(Math.round((Double.valueOf(cCosto) * Double.valueOf(cIncremen1) / 100) + Double.valueOf(cCosto))));
        }          // TODO add your handling code here:
    }//GEN-LAST:event_incremento1FocusLost

    private void incremento2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_incremento2FocusLost
        String cCosto = costo.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        String cIncremen2 = incremento2.getText();
        cIncremen2 = cIncremen2.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCosto) > 0 || Double.valueOf(cIncremen2) > 0) {
            this.mayoristasugerido.setText(formatea.format(Math.round((Double.valueOf(cCosto) * Double.valueOf(cIncremen2) / 100) + Double.valueOf(cCosto))));
        }          // TODO add your handling code here:
    }//GEN-LAST:event_incremento2FocusLost

    private void preciomayoristaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preciomayoristaFocusLost
        String cCosto = this.costo.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        String cPrecio = this.preciomayorista.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCosto) > 0 || Double.valueOf(cPrecio) > 0) {
            this.incremento2.setText(formatea.format((Double.valueOf(cPrecio) - Double.valueOf(cCosto)) / Double.valueOf(cCosto) * 100));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_preciomayoristaFocusLost

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            obj = new clsExportarExcel();
            obj.exportarExcel(tablaproductos);
        } catch (IOException ex) {
            Logger.getLogger(productos.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        int nFila = tablaproductos.getSelectedRow();
        String cCodigo = tablaproductos.getValueAt(nFila, 0).toString();

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("cCodigo", cCodigo);
            parameters.put("cNombreRubro", tablaproductos.getValueAt(nFila, 3).toString());
            parameters.put("cPrecio", tablaproductos.getValueAt(nFila, 5).toString());
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/qrproducto.jasper");
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void limpiaretiqueta() {
        int cantidadRegistro = modeloetiqueta.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloetiqueta.removeRow(0);
        }
    }

    private void CloseticketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseticketActionPerformed
        etiquetas_multiples.setModal(false);
        etiquetas_multiples.setVisible(false);
        limpiaretiqueta();

        // TODO add your handling code here:
    }//GEN-LAST:event_CloseticketActionPerformed

    private void tablaproductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproductosKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            Object[] fila = new Object[3];
            int nReg = this.tablaproductos.getSelectedRow();
            if (nReg < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproductos.requestFocus();
                return;
            }
            String cCodigo = this.tablaproductos.getValueAt(nReg, 0).toString();
            String cNombreProd = this.tablaproductos.getValueAt(nReg, 1).toString();
            String cPrecio = this.tablaproductos.getValueAt(nReg, 3).toString();
            System.out.println(cCodigo);
            System.out.println(cNombreProd);
            fila[0] = cCodigo.toString();
            fila[1] = cNombreProd.toString();
            fila[2] = cPrecio.toString();
            modeloetiqueta.addRow(fila);
        }

        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            etiquetas_multiples.setSize(592, 410);
            //Establecemos un título para el jDialog
            etiquetas_multiples.setTitle("Impresión de Etiquetas");
            etiquetas_multiples.setLocationRelativeTo(null);
            etiquetas_multiples.setVisible(true);
        }

        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            int nFila = this.tablaproductos.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproductos.requestFocus();
                return;
            } else {
                if (Integer.valueOf(Config.cNivelUsuario) == 1) {
                    this.fecha.setCalendar(c2);
                    conteo.setText("0");
                    codigoproducto.setText(this.tablaproductos.getValueAt(nFila, 0).toString());
                    nombremercaderia.setText(this.tablaproductos.getValueAt(nFila, 1).toString());
                    costomercaderia.setText(this.tablaproductos.getValueAt(nFila, 2).toString());

                    productoDAO pdDAO = new productoDAO();
                    producto pd = null;

                    try {
                        pd = pdDAO.conteoxajuste(codigoproducto.getText());
                        stockactual.setText(formatea.format(pd.getStock()));
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    stock.setSize(586, 320);
                    //Establecemos un título para el jDialog
                    stock.setLocationRelativeTo(null);
                    stock.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario No Autorizado");
                    this.tablaproductos.requestFocus();
                    return;
                }
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            int nFila = tablaproductos.getSelectedRow();
            String cProducto = tablaproductos.getValueAt(nFila, 0).toString();
            String cNombre = tablaproductos.getValueAt(nFila, 1).toString();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproductos.requestFocus();
                return;
            }

            int cantidadRegistro = modelostock.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelostock.removeRow(0);
            }

            stockDAO stDAO = new stockDAO();
            try {
                for (stock st : stDAO.BuscarStockProducto(cProducto)) {
                    String Datos[] = {st.getSucursal().getNombre(), formatea.format(st.getStock())};
                    modelostock.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablastock.setRowSorter(new TableRowSorter(modelostock));
            int cantFilas = tablastock.getRowCount();

            etiquetacodigo.setText(cProducto);
            etiquetanombre.setText(cNombre);
            inventario.setModal(true);
            inventario.setSize(470, 375);
            //Establecemos un título para el jDialog
            inventario.setLocationRelativeTo(null);
            inventario.setVisible(true);
            SalirStock.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductosKeyReleased

    private void PrintTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintTicketActionPerformed
        this.etiquetas_multiples.setModal(false);
        int nFila = 0;
        List Resultados = new ArrayList();
        EtiquetaMultiple datos;
        for (nFila = 0; nFila < tablaetiqueta.getRowCount(); nFila++) {
            datos = new EtiquetaMultiple(tablaetiqueta.getValueAt(nFila, 0).toString(), tablaetiqueta.getValueAt(nFila, 1).toString(), tablaetiqueta.getValueAt(nFila, 2).toString());
            Resultados.add(datos);
        }

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());

            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/etiquetamultiple.jasper");
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(Resultados));
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(true);
        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(null, e, "Error", 1);
            System.out.println(e);
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_PrintTicketActionPerformed

    private void precioventaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precioventaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_precioventaFocusGained

    private void precioventaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precioventaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_precioventaFocusLost

    private void precioventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioventaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ivaporcentaje.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.preciomayorista.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_precioventaKeyPressed

    private void buscarcadenaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyReleased
        FiltroPro();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaKeyReleased

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Calendar c2 = new GregorianCalendar();
        this.vencimiento.setCalendar(c2);
        int nFila = tablaproductos.getSelectedRow();
        this.codproducto.setText(tablaproductos.getValueAt(nFila, 0).toString());
        this.nombreproducto.setText(tablaproductos.getValueAt(nFila, 1).toString());
        this.precioproducto.setText(tablaproductos.getValueAt(nFila, 3).toString());

        nombre.requestFocus();
        Ean8Vence.setSize(400, 265);
        //Establecemos un título para el jDialog
        Ean8Vence.setLocationRelativeTo(null);
        Ean8Vence.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void vencimientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vencimientoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoFocusGained

    private void ImprimirEanVenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImprimirEanVenceActionPerformed
        con = new Conexion();
        stm = con.conectar();
        int nFila = tablaproductos.getSelectedRow();
        String cCodigo = tablaproductos.getValueAt(nFila, 0).toString();
        Date dVencimiento = ODate.de_java_a_sql(vencimiento.getDate());

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cCodigo", codproducto.getText());
            parameters.put("vencimiento", dVencimiento);

            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/ean8lacasita.jasper");
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
        // TODO add your handling code here:
    }//GEN-LAST:event_ImprimirEanVenceActionPerformed

    private void SalirEanVenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirEanVenceActionPerformed
        this.Ean8Vence.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirEanVenceActionPerformed

    private void SalirStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirStockActionPerformed
        this.stock.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirStockActionPerformed

    private void GrabarStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarStockActionPerformed

        String cCosto = costomercaderia.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");

        if (Double.valueOf(cCosto) > 0) {
            Object[] opciones = {"  Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Confirmar la Operación ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                Date Fecha = ODate.de_java_a_sql(fecha.getDate());

                configuracionDAO configDAO = new configuracionDAO();
                configuracion config = configDAO.consultar();

                sucursalDAO sucDAO = new sucursalDAO();
                sucursal suc = null;
                comprobanteDAO comDAO = new comprobanteDAO();
                comprobante com = null;

                try {
                    suc = sucDAO.buscarId(config.getSucursaldefecto().getCodigo());
                    com = comDAO.buscarId(config.getProdingreso());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                ajuste_mercaderiaDAO ajDAO = new ajuste_mercaderiaDAO();
                ajuste_mercaderia aju = new ajuste_mercaderia();
                String referencia = UUID.crearUUID();
                referencia = referencia.substring(1, 25);

                aju.setIdreferencia(referencia);
                aju.setFecha(Fecha);
                aju.setSucursal(suc);
                aju.setTipo(com);
                aju.setObservacion("ENTRADA POR AJUSTE DE CONTEO");

                String cCantidad = null;

                String detalle = "[";

                String cProducto = codigoproducto.getText();
                cCantidad = conteo.getText();
                cCantidad = cCantidad.replace(".", "").replace(",", ".");

                String cStock = stockactual.getText();
                cStock = cStock.replace(".", "").replace(",", ".");

                double nDiferencia = 0;
                if (Double.valueOf(cStock) < 0) {
                    nDiferencia = (Math.abs(Double.valueOf(cStock)) + Double.valueOf(cCantidad));
                } else if (Double.valueOf(cStock) > 0) {
                    nDiferencia = (Double.valueOf(cStock) - Double.valueOf(cCantidad)) * -1;
                } else if (Double.valueOf(cStock) == 0) {
                    nDiferencia = Double.valueOf(cCantidad);
                }
                String linea = "{dreferencia : " + referencia + ","
                        + "producto : '" + cProducto + "',"
                        + "cantidad : " + String.valueOf(nDiferencia) + ","
                        + "costo : " + cCosto + ","
                        + "suc : " + String.valueOf(aju.getSucursal().getCodigo())
                        + "},";
                detalle += linea;
                if (!detalle.equals("[")) {
                    detalle = detalle.substring(0, detalle.length() - 1);
                }
                detalle += "]";
                System.out.println(detalle);
                try {
                    ajDAO.insertarmercaderia(aju, detalle);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            }

        } else {
            JOptionPane.showMessageDialog(null, "No hay Productos en el Detalle");
        }
        this.SalirStock.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarStockActionPerformed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        // TODO add your handling code here:*/
    }//GEN-LAST:event_fechaKeyPressed

    private void porcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentajeFocusLost
        String cCosto = costo.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        String cIncremen1 = porcentaje.getText();
        cIncremen1 = cIncremen1.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCosto) > 0 || Double.valueOf(cIncremen1) > 0) {
            this.preciofinal.setText(formatea.format(Math.round((Double.valueOf(cCosto) * Double.valueOf(cIncremen1) / 100) + Double.valueOf(cCosto))));
        }         // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeFocusLost

    private void GrabarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarListaActionPerformed
        String cCantidad = this.cantidad.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");

        String cUtilidad = this.porcentaje.getText();
        cUtilidad = cUtilidad.replace(".", "").replace(",", ".");

        String cPrecioLista = this.preciofinal.getText();
        cPrecioLista = cPrecioLista.replace(".", "").replace(",", ".");

        if (Double.valueOf(cCantidad) <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese Plazo/Cantidad");
            this.cantidad.requestFocus();
            return;
        }
        if (Double.valueOf(cUtilidad) <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese Porcentaje Utilidad");
            this.porcentaje.requestFocus();
            return;
        }
        if (Double.valueOf(cPrecioLista) <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Precio de Lista");
            this.preciofinal.requestFocus();
            return;
        }

        lista_preciosDAO grabarL = new lista_preciosDAO();
        lista_precios L = new lista_precios();
        //Clase de Cliente porque tiene que hacer referencia al cliente
        productoDAO prDAO = new productoDAO();
        producto pr = null;
        try {
            pr = prDAO.BuscarProductoBasico(this.codigo.getText());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        L.setProducto(pr);
        L.setLimitecantidad(Double.valueOf(cCantidad));
        L.setUtilidad(Double.valueOf(cUtilidad));
        L.setPrecioventa(Double.valueOf(cPrecioLista));
        L.setItemid(Double.valueOf(this.iditem.getText()));

        try {
            if (L.getItemid() == 0) {
                grabarL.insertarPrecios(L);
            } else {
                grabarL.actualizarItemPrecio(L);
            }
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        GrillaLista GrillaL = new GrillaLista();
        Thread Hilo2 = new Thread(GrillaL);
        Hilo2.start();
        this.SalirLista.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarListaActionPerformed

    private void EditarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarPrecioActionPerformed
        this.LimpiarItem();
        int nFila = this.tablaprecio.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        cantidad.setText(tablaprecio.getValueAt(nFila, 0).toString());
        porcentaje.setText(tablaprecio.getValueAt(nFila, 1).toString());
        preciofinal.setText(tablaprecio.getValueAt(nFila, 2).toString());
        iditem.setText(tablaprecio.getValueAt(nFila, 3).toString());
        listaprecios.setModal(true);
        listaprecios.setSize(400, 300);
        //Establecemos un título para el jDialog
        listaprecios.setLocationRelativeTo(null);
        listaprecios.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_EditarPrecioActionPerformed

    private void preciofinalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preciofinalFocusLost
        String cCosto = this.costo.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        String cPrecio = this.preciofinal.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCosto) > 0 || Double.valueOf(cPrecio) > 0) {
            this.porcentaje.setText(formatea.format((Double.valueOf(cPrecio) - Double.valueOf(cCosto)) / Double.valueOf(cCosto) * 100));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_preciofinalFocusLost

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.porcentaje.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void porcentajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcentajeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.preciofinal.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeKeyPressed

    private void preciofinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preciofinalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarLista.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.preciofinal.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_preciofinalKeyPressed

    private void SalirListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirListaActionPerformed
        this.listaprecios.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirListaActionPerformed

    private void buscarcadenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcadenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaActionPerformed

    private void idctaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idctaActionPerformed
        BuscarCta.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_idctaActionPerformed

    private void BuscarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCtaActionPerformed
        planDAO plaDAO = new planDAO();
        plan pl = null;
        try {
            pl = plaDAO.buscarId(this.idcta.getText());
            if (pl.getCodigo() == null) {
                BCuenta.setModal(true);
                BCuenta.setSize(482, 575);
                BCuenta.setLocationRelativeTo(null);
                BCuenta.setVisible(true);
                BCuenta.setTitle("Buscar Cuenta");
                BCuenta.setModal(false);
            } else {
                nombrecuenta.setText(pl.getNombre());
                //Establecemos un título para el jDialog
            }
            this.GrabaEnlace.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCtaActionPerformed

    private void GrabaEnlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabaEnlaceActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Actualizar el Enlace? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            productoDAO grabarDAO = new productoDAO();
            producto p = new producto();
            planDAO pDAO = new planDAO();
            plan plan = null;

            try {
                plan = pDAO.buscarId(idcta.getText());
                p.setCtadebe(plan);
                p.setCodigo(this.codcuenta.getText());;
                grabarDAO.ActualizarCuentaDebe(p);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            cuentas.setVisible(false);
            cuentas.setModal(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabaEnlaceActionPerformed

    private void SalirEnlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirEnlaceActionPerformed
        cuentas.setVisible(false);
        cuentas.setModal(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirEnlaceActionPerformed

    private void comboplanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboplanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboplanActionPerformed

    private void jTBuscarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarPlanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPlanActionPerformed

    private void jTBuscarPlanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPlanKeyPressed
        this.jTBuscarPlan.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarPlan.getText()).toUpperCase();
                jTBuscarPlan.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboplan.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtroplan(indiceColumnaTabla);
            }
        });
        trsfiltroplan = new TableRowSorter(tablaplan.getModel());
        tablaplan.setRowSorter(trsfiltroplan);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPlanKeyPressed

    public void filtroplan(int nNumeroColumna) {
        trsfiltroplan.setRowFilter(RowFilter.regexFilter(jTBuscarPlan.getText(), nNumeroColumna));
    }

    private void tablaplanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaplanMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaplanMouseClicked

    private void tablaplanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaplanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaplanKeyPressed

    private void TitPlan() {
        modeloplan.addColumn("Código");
        modeloplan.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloplan.getColumnCount(); i++) {
            tablaplan.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaplan.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaplan.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaplan.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaplan.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablaplan.getSelectedRow();
        this.idcta.setText(this.tablaplan.getValueAt(nFila, 0).toString());
        this.nombrecuenta.setText(this.tablaplan.getValueAt(nFila, 1).toString());
        this.BCuenta.setVisible(false);
        this.BCuenta.setModal(false);
        this.GrabaEnlace.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BCuenta.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        int nFila = this.tablaproductos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaproductos.requestFocus();
            return;
        } else {
            this.codcuenta.setText(this.tablaproductos.getValueAt(nFila, 0).toString());
            this.descripcion.setText(this.tablaproductos.getValueAt(nFila, 1).toString());
        }
        productoDAO pvDAO = new productoDAO();
        producto pro = null;
        try {
            pro = pvDAO.BuscarProductoCuenta(this.codcuenta.getText());
            if (pro != null) {
                idcta.setText(pro.getCtadebe().getCodigo());
                nombrecuenta.setText(pro.getCtadebe().getNombre());
                cuentas.setTitle("Modificar Enlace Contable");
                cuentas.setModal(true);

                //(Ancho,Alto)
                cuentas.setSize(450, 271);
                //Establecemos un título para el jDialog
                cuentas.setLocationRelativeTo(null);
                cuentas.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "La Operación  no puede Modificarse");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void SalirPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPreciosActionPerformed
        actualizar_precios.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPreciosActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        int cantidadReg = modeloprecios.getRowCount();
        for (int i = 1; i <= cantidadReg; i++) {
            modeloprecios.removeRow(0);
        }
        this.LimpiarPrecios();
        actualizar_precios.setSize(709, 645);
        //Establecemos un título para el jDialog
        actualizar_precios.setLocationRelativeTo(null);
        actualizar_precios.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void CalcularPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalcularPreciosActionPerformed
        double importe_redondeo = 0;
        double nresto = 0;

        int totalRow = tablaprecios.getRowCount();
        int digitos = 0;
        totalRow -= 1;

        for (int i = 0; i <= (totalRow); i++) {
            double porcentaje_mayorista = 0;
            double porcentaje_minorista = 0;
            double precio_mayorista;
            double precio_minorista;
            //CAPTURO EL COSTO
            String cCosto = String.valueOf(modeloprecios.getValueAt(i, 2));
            cCosto = cCosto.replace(".", "").replace(",", ".");
            //CAPTURO EL PORCENTAJE ACTUAL DEL PRECIO MAYORISTA
            String cPMayorista = String.valueOf(modeloprecios.getValueAt(i, 5));
            cPMayorista = cPMayorista.replace(".", "").replace(",", ".");
            //CAPTURO EL PORCENTAJE QUE VA A SUBIR EN MAYORISTA
            String cAMayorista = String.valueOf(mayorista.getText());
            cAMayorista = cAMayorista.replace(".", "").replace(",", ".");
            //CAPTURO EL PRECIO MAYORISTA ACTUAL
            String cPrecioMayorista = String.valueOf(modeloprecios.getValueAt(i, 4));
            cPrecioMayorista = cPrecioMayorista.replace(".", "").replace(",", ".");

            porcentaje_mayorista = Double.valueOf(cPMayorista) + Double.valueOf(cAMayorista);
            precio_mayorista = Math.round(Double.valueOf(cCosto) + (Double.valueOf(cCosto) * porcentaje_mayorista / 100));
            if (Double.valueOf(cAMayorista) > 0) {
                if (redondear.getSelectedIndex() == 0) {
                    tablaprecios.setValueAt(formatea.format(precio_mayorista), i, 8);
                } else if (redondear.getSelectedIndex() == 1) {
                    importe_redondeo = 50;
                    nresto = Math.round(precio_mayorista % importe_redondeo);
                    if (importe_redondeo == 50) {
                        if (nresto < 25) {
                            precio_mayorista = precio_mayorista - nresto;
                        } else {
                            precio_mayorista = (precio_mayorista - nresto) + importe_redondeo;
                        }
                    }
                } else if (redondear.getSelectedIndex() == 2) {
                    importe_redondeo = 100;
                    nresto = Math.round(precio_mayorista % importe_redondeo);
                    if (nresto < 50) {
                        precio_mayorista = precio_mayorista - nresto;
                    } else {
                        precio_mayorista = (precio_mayorista - nresto) + importe_redondeo;
                    }
                }
                tablaprecios.setValueAt(formatea.format(precio_mayorista), i, 8);
            } else {
                tablaprecios.setValueAt(formatea.format(Double.valueOf(cPrecioMayorista)), i, 8);
            }

            /////CALCULOS PARA PRECIO MINORISTA
            String cPMinorista = String.valueOf(modeloprecios.getValueAt(i, 5));
            cPMinorista = cPMinorista.replace(".", "").replace(",", ".");

            //CAPTURO EL PORCENTAJE QUE VA A SUBIR EN MINORISTA
            String cAMinorista = String.valueOf(minorista.getText());
            cAMinorista = cAMinorista.replace(".", "").replace(",", ".");
            //CAPTURO EL PRECIO MINORISTA ACTUAL
            String cPrecioMinorista = String.valueOf(modeloprecios.getValueAt(i, 3));
            cPrecioMinorista = cPrecioMinorista.replace(".", "").replace(",", ".");

            porcentaje_minorista = Double.valueOf(cPMinorista) + Double.valueOf(cAMinorista);
            precio_minorista = Math.round(Double.valueOf(cCosto) + (Double.valueOf(cCosto) * porcentaje_minorista / 100));

            if (Double.valueOf(cAMinorista) > 0) {
                if (redondear.getSelectedIndex() == 0) {
                    tablaprecios.setValueAt(formatea.format(precio_minorista), i, 7);
                } else if (redondear.getSelectedIndex() == 1) {
                    importe_redondeo = 50;
                    nresto = Math.round(precio_minorista % importe_redondeo);
                    if (importe_redondeo == 50) {
                        if (nresto < 25) {
                            precio_minorista = precio_minorista - nresto;
                        } else {
                            precio_minorista = (precio_minorista - nresto) + importe_redondeo;
                        }
                    }
                } else if (redondear.getSelectedIndex() == 2) {
                    importe_redondeo = 100;
                    nresto = Math.round(precio_minorista % importe_redondeo);
                    if (nresto < 50) {
                        precio_minorista = precio_minorista - nresto;
                    } else {
                        precio_minorista = (precio_minorista - nresto) + importe_redondeo;
                    }
                }
                tablaprecios.setValueAt(formatea.format(precio_minorista), i, 7);
            } else {
                tablaprecios.setValueAt(formatea.format(Double.valueOf(cPrecioMinorista)), i, 7);
            }
        }
    }//GEN-LAST:event_CalcularPreciosActionPerformed

    private void ConsultarPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarPreciosActionPerformed
        GrillaxRubro GrillaRu = new GrillaxRubro();
        Thread HiloRu = new Thread(GrillaRu);
        HiloRu.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_ConsultarPreciosActionPerformed

    private void BuscarRubroPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarRubroPrecioActionPerformed
        nbusqueda = 2;
        BRubro.setModal(true);
        BRubro.setSize(482, 575);
        BRubro.setLocationRelativeTo(null);
        BRubro.setVisible(true);
        BRubro.setTitle("Buscar Rubro");
        BRubro.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarRubroPrecioActionPerformed

    private void codrubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codrubroActionPerformed
        rubroDAO ruDAO = new rubroDAO();
        rubro ru = null;
        try {
            ru = ruDAO.buscarId(Integer.valueOf(this.codrubro.getText()));
            if (ru.getCodigo() == 0) {
                this.BuscarRubroPrecio.doClick();
            } else {
                nombrerubroprecio.setText(ru.getNombre());
                //Establecemos un título para el jDialog
            }
            mayorista.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codrubroActionPerformed

    private void ActualizarPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarPreciosActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Actualizar esta Lista de Precios ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            try {
                String sql = "";
                con = new Conexion();
                st = con.conectar();
                Connection conn = st.getConnection();
                PreparedStatement ps = null;
                int totalRow = modeloprecios.getRowCount();
                totalRow -= 1;
                for (int i = 0; i <= (totalRow); i++) {
                    //producto
                    String cProducto = String.valueOf(modeloprecios.getValueAt(i, 0));
                    //precio minorista viejo
                    String cPrecioMinoristaOld = String.valueOf(modeloprecios.getValueAt(i, 3));
                    cPrecioMinoristaOld = cPrecioMinoristaOld.replace(".", "").replace(",", ".");
                    //%precio minorista viejo
                    String cPorcMinoristaOld = String.valueOf(modeloprecios.getValueAt(i, 5));
                    cPorcMinoristaOld = cPorcMinoristaOld.replace(".", "").replace(",", ".");
                    //precio minorista nuevo
                    String cPrecioMinorista = String.valueOf(modeloprecios.getValueAt(i, 7));
                    cPrecioMinorista = cPrecioMinorista.replace(".", "").replace(",", ".");
                    //precio mayorista viejo
                    String cPrecioMayoristaOld = String.valueOf(modeloprecios.getValueAt(i, 4));
                    cPrecioMayoristaOld = cPrecioMayoristaOld.replace(".", "").replace(",", ".");
                    //%precio minorista viejo
                    String cPorcMayoristaOld = String.valueOf(modeloprecios.getValueAt(i, 6));
                    cPorcMayoristaOld = cPorcMayoristaOld.replace(".", "").replace(",", ".");

                    //precio mayorista nuevo
                    String cPrecioMayorista = String.valueOf(modeloprecios.getValueAt(i, 8));
                    cPrecioMayorista = cPrecioMayorista.replace(".", "").replace(",", ".");

                    //porcentajes
                    String cPMayorista = mayorista.getText();
                    cPMayorista = cPMayorista.replace(".", "").replace(",", ".");
                    double porcentaje_mayorista = Double.valueOf(cPMayorista) + Double.valueOf(cPorcMayoristaOld);

                    String cPMinorista = minorista.getText();
                    cPMinorista = cPMinorista.replace(".", "").replace(",", ".");
                    double porcentaje_minorista = Double.valueOf(cPMinorista) + Double.valueOf(cPorcMinoristaOld);

                    ps = st.getConnection().prepareStatement("UPDATE productos "
                            + "SET precio_maximo = ?,"
                            + "precio_minimo = ?,"
                            + "incremento1 =? ,"
                            + "incremento2 =? ,"
                            + "precio_mayorista_anterior = ?,"
                            + "precio_minorista_anterior = ?,"
                            + "porciento_mayorista_anterior = ?,"
                            + "porciento_minorista_anterior = ? WHERE codigo='" + cProducto + "'");
                    ps.setDouble(1, Double.valueOf(cPrecioMinorista));
                    ps.setDouble(2, Double.valueOf(cPrecioMayorista));
                    ps.setDouble(3, porcentaje_minorista);
                    ps.setDouble(4, porcentaje_mayorista);
                    ps.setDouble(5, Double.valueOf(cPrecioMayoristaOld));
                    ps.setDouble(6, Double.valueOf(cPrecioMinoristaOld));
                    ps.setDouble(7, Double.valueOf(cPorcMayoristaOld));
                    ps.setDouble(8, Double.valueOf(cPorcMinoristaOld));
                    ps.executeUpdate();
                }
                JOptionPane.showMessageDialog(null, "Proceso de Actualización de Precios, Terminado: ");
                this.LimpiarPrecios();
                int cantidadReg = modeloprecios.getRowCount();
                for (int i = 1; i <= cantidadReg; i++) {
                    modeloprecios.removeRow(0);
                }
                codrubro.requestFocus();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_ActualizarPreciosActionPerformed

    private void AgregarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarPrecioActionPerformed
        this.LimpiarItem();
        listaprecios.setModal(true);
        listaprecios.setSize(400, 300);
        //Establecemos un título para el jDialog
        listaprecios.setLocationRelativeTo(null);
        listaprecios.setVisible(true);
        cantidad.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarPrecioActionPerformed

    private void BorraPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorraPrecioActionPerformed
        int a = this.tablaprecio.getSelectedRow();
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            lista_preciosDAO borrarL = new lista_preciosDAO();
            String cItemBorrar = this.tablaprecio.getValueAt(a, 3).toString();
            try {
                borrarL.borrarItem(Double.valueOf(cItemBorrar));
                JOptionPane.showMessageDialog(null, "Registro Eliminado Exitósamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            GrillaLista GrillaL = new GrillaLista();
            Thread Hilo2 = new Thread(GrillaL);
            Hilo2.start();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BorraPrecioActionPerformed

    private void SalirStock1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirStock1ActionPerformed
        this.inventario.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirStock1ActionPerformed

    private void limpiarPrecios() {
        this.codprod1.setText("");
        this.nombreproducto1.setText("");
        this.costo1.setText("0");
        this.porcentaje1.setText("0");
        this.minorista1.setText("0");
        this.mayorista1.setText("0");
    }


    private void UpdatePreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdatePreciosActionPerformed
        nFila = this.tablaproductos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaproductos.requestFocus();
            return;
        }

        this.limpiarPrecios();

        this.codprod1.setText(this.tablaproductos.getValueAt(nFila, 0).toString());
        productoDAO pdDAO = new productoDAO();
        producto pd = null;
        try {
            pd = pdDAO.BuscarProducto(this.codprod1.getText());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (pd != null) {
            nombreproducto1.setText(pd.getNombre());
            costo1.setText(formatea.format(pd.getCosto()));
            porcentaje1.setText(formatea.format(pd.getIncremento1()));
            minorista1.setText(formatea.format(pd.getPrecio_maximo()));
            mayorista1.setText(formatea.format(pd.getPrecio_minimo()));
        }

        this.actualizar_precios.setModal(true);
        costo1.requestFocus();
        this.actualizar_precio.setSize(400, 300);
        //Establecemos un título para el jDialog
        this.actualizar_precio.setTitle("Actualizar Precios");
        this.actualizar_precio.setLocationRelativeTo(null);
        this.actualizar_precio.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_UpdatePreciosActionPerformed

    private void Salir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Salir1ActionPerformed
        this.actualizar_precio.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_Salir1ActionPerformed

    private void porcentaje1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentaje1FocusLost
        String cCosto = costo1.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        String cIncremen1 = porcentaje1.getText();
        cIncremen1 = cIncremen1.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCosto) > 0 || Double.valueOf(cIncremen1) > 0) {
            this.minorista1.setText(formatea.format(Math.round((Double.valueOf(cCosto) * Double.valueOf(cIncremen1) / 100) + Double.valueOf(cCosto))));
            this.mayorista1.setText(formatea.format(Math.round((Double.valueOf(cCosto) * Double.valueOf(cIncremen1) / 100) + Double.valueOf(cCosto))));
        }          // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentaje1FocusLost

    private void costo1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_costo1KeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.porcentaje1.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_costo1KeyReleased

    private void porcentaje1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcentaje1KeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.minorista1.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.costo1.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentaje1KeyReleased

    private void minorista1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_minorista1KeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.mayorista1.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.porcentaje1.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_minorista1KeyReleased

    private void mayorista1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mayorista1KeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarPrecios.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.minorista1.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_mayorista1KeyReleased

    private void GrabarPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarPreciosActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            productoDAO grabarPRD = new productoDAO();
            producto pd = new producto();

            String cCosto = this.costo1.getText();
            cCosto = cCosto.replace(".", "").replace(",", ".");
            BigDecimal ncosto = new BigDecimal(cCosto);
            pd.setCosto(ncosto);

            String cInc1 = this.porcentaje1.getText();
            cInc1 = cInc1.replace(".", "").replace(",", ".");
            BigDecimal inc1 = new BigDecimal(cInc1);
            pd.setIncremento1(inc1);

            String cInc2 = this.porcentaje1.getText();
            cInc2 = cInc2.replace(".", "").replace(",", ".");
            BigDecimal inc2 = new BigDecimal(cInc2);
            pd.setIncremento2(inc2);

            String cPMax = this.minorista1.getText();
            cPMax = cPMax.replace(".", "").replace(",", ".");
            BigDecimal pmax = new BigDecimal(cPMax);
            pd.setPrecio_maximo(pmax);

            String cPMin = this.mayorista1.getText();
            cPMin = cPMin.replace(".", "").replace(",", ".");
            BigDecimal pmin = new BigDecimal(cPMin);
            pd.setPrecio_minimo(pmin);
            pd.setCodigo(this.codprod1.getText());

            try {
                grabarPRD.actualizarCostos(pd);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
            this.actualizar_precio.setVisible(false);
            GrillaProductos GrillaP = new GrillaProductos();
            Thread HiloGrilla = new Thread(GrillaP);
            HiloGrilla.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarPreciosActionPerformed

    private void costo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_costo1FocusGained
        costo1.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_costo1FocusGained

    private void porcentaje1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentaje1FocusGained
        porcentaje1.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentaje1FocusGained

    private void minorista1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_minorista1FocusGained
        minorista1.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_minorista1FocusGained

    private void mayorista1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mayorista1FocusGained
        mayorista1.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_mayorista1FocusGained

    private void LimpiarPrecios() {
        ActualizarPrecios.setEnabled(false);
        CalcularPrecios.setEnabled(false);
        codrubro.setText("0");
        nombrerubroprecio.setText("");
        nombrerubro.setText("");
        mayorista.setText("0");
        minorista.setText("0");
        redondear.setSelectedIndex(0);
    }

    private void FiltroPro() {
        trsfiltro = new TableRowSorter(tablaproductos.getModel());
        tablaproductos.setRowSorter(trsfiltro);
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText().toUpperCase(), indiceTabla));
    }

    public void filtroubicacion(int nNumeroColumna) {
        trsfiltroubicacion.setRowFilter(RowFilter.regexFilter(this.jTBuscarUbicacion.getText(), nNumeroColumna));
    }

    public void filtromedida(int nNumeroColumna) {
        trsfiltromedida.setRowFilter(RowFilter.regexFilter(this.jTBuscarMedida.getText(), nNumeroColumna));
    }

    public void filtromarca(int nNumeroColumna) {
        trsfiltromarca.setRowFilter(RowFilter.regexFilter(this.jTBuscarMarca.getText(), nNumeroColumna));
    }

    public void filtroproveedor(int nNumeroColumna) {
        trsfiltroproveedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarProveedor.getText(), nNumeroColumna));
    }

    public void filtrorubro(int nNumeroColumna) {
        trsfiltrorubro.setRowFilter(RowFilter.regexFilter(this.jTBuscarRubro.getText(), nNumeroColumna));
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtrocasa(int nNumeroColumna) {
        trsfiltroproveedor.setRowFilter(RowFilter.regexFilter(jTBuscarProveedor.getText(), nNumeroColumna));
    }

    public void filtrogira(int nNumeroColumna) {
        trsfiltropais.setRowFilter(RowFilter.regexFilter(this.jTBuscarPais.getText(), nNumeroColumna));
    }

    private void TituloEtiqueta() {
        modeloetiqueta.addColumn("Código");
        modeloetiqueta.addColumn("Descripción del Producto");
        modeloetiqueta.addColumn("Precio");

        int[] anchos = {120, 250, 100};
        for (int i = 0; i < modeloetiqueta.getColumnCount(); i++) {
            tablaetiqueta.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaetiqueta.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaetiqueta.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablaetiqueta.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaetiqueta.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);

    }

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Descripción del Producto");
        modelo.addColumn("Costo");
        modelo.addColumn("P. Minorista");
        modelo.addColumn("P. Mayorista");
        modelo.addColumn("Stock");
        modelo.addColumn("Rubro");
        modelo.addColumn("Marca");
        modelo.addColumn("Ubicación");
        modelo.addColumn("Médida");
        modelo.addColumn("C. de Barra");

        int[] anchos = {120, 250, 100, 100, 100, 100, 100, 120, 120, 120, 120};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tablaproductos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproductos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproductos.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablaproductos.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);
        this.tablaproductos.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaproductos.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablaproductos.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.tablaproductos.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablaproductos.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
    }

    private void cargarTituloxRubro() {
        modeloprecios.addColumn("Código"); //0
        modeloprecios.addColumn("Descripción del Producto"); //1
        modeloprecios.addColumn("Costo");//2
        modeloprecios.addColumn("Minorista");//3
        modeloprecios.addColumn("Mayorista");//4
        modeloprecios.addColumn("% Minorista");//5
        modeloprecios.addColumn("% Mayorista");//6
        modeloprecios.addColumn("Hoy Minorista");//7
        modeloprecios.addColumn("Hoy Mayorista");//8
        int[] anchos = {120, 250, 100, 100, 100, 100, 100, 120, 120};
        for (int i = 0; i < modeloprecios.getColumnCount(); i++) {
            tablaprecios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaprecios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaprecios.getTableHeader().setFont(new Font("Arial Black", 1, 8));

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaprecios.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);
        this.tablaprecios.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaprecios.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablaprecios.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.tablaprecios.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablaprecios.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.tablaprecios.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablaprecios.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
    }

    private void TitMarca() {
        modelomarca.addColumn("Código");
        modelomarca.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelomarca.getColumnCount(); i++) {
            tablamarca.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamarca.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamarca.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablamarca.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablamarca.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitFamilia() {
        modelofamilia.addColumn("Código");
        modelofamilia.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelofamilia.getColumnCount(); i++) {
            tablafamilia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablafamilia.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablafamilia.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablafamilia.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablafamilia.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitLista() {
        modelolista.addColumn("Cantidad");
        modelolista.addColumn("% Utilidad");
        modelolista.addColumn("Precio Lista");
        modelolista.addColumn("Item");

        int[] anchos = {90, 200, 200, 10};
        for (int i = 0; i < modelolista.getColumnCount(); i++) {
            tablaprecio.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaprecio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaprecio.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaprecio.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaprecio.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablaprecio.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablaprecio.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);

        this.tablaprecio.getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablaprecio.getColumnModel().getColumn(3).setMinWidth(0);
        this.tablaprecio.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablaprecio.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);

    }

    private void TitProveedor() {
        modeloproveedor.addColumn("Código");
        modeloproveedor.addColumn("Nombre");

        int[] anchos = {90, 200};
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

    private void TitRubro() {
        modelorubro.addColumn("Código");
        modelorubro.addColumn("Nombre");

        int[] anchos = {90, 200};
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

    private void TitMedida() {
        modelomedida.addColumn("Código");
        modelomedida.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelomedida.getColumnCount(); i++) {
            tablamedida.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamedida.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamedida.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablamedida.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablamedida.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitUbicacion() {
        modeloubicacion.addColumn("Código");
        modeloubicacion.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloubicacion.getColumnCount(); i++) {
            tablaubicacion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaubicacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaubicacion.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaubicacion.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaubicacion.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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
                new productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCasa;
    private javax.swing.JButton AceptarFamilia;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarMarca;
    private javax.swing.JButton AceptarMedida;
    private javax.swing.JButton AceptarPais;
    private javax.swing.JButton AceptarRubro;
    private javax.swing.JButton AceptarUbicacion;
    private javax.swing.JButton ActualizarPrecios;
    private javax.swing.JButton Agregar;
    private javax.swing.JButton AgregarPrecio;
    private javax.swing.JDialog BCuenta;
    private javax.swing.JDialog BFamilia;
    private javax.swing.JDialog BMarca;
    private javax.swing.JDialog BMedida;
    private javax.swing.JDialog BPaises;
    private javax.swing.JDialog BProveedor;
    private javax.swing.JDialog BRubro;
    private javax.swing.JDialog BUbicacion;
    private javax.swing.JButton BorraPrecio;
    private javax.swing.JButton BotonAbriArchivo;
    private javax.swing.JButton BotonCodigoBarra;
    private javax.swing.JButton BuscarCta;
    private javax.swing.JButton BuscarRubroPrecio;
    private javax.swing.JButton CalcularPrecios;
    private javax.swing.JButton Closeticket;
    private javax.swing.JButton ConsultarPrecios;
    private javax.swing.JLabel Descripción;
    private javax.swing.JDialog Ean8Vence;
    private javax.swing.JButton EditarPrecio;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton GrabaEnlace;
    private javax.swing.JButton GrabarLista;
    private javax.swing.JButton GrabarPrecios;
    private javax.swing.JButton GrabarProducto;
    private javax.swing.JButton GrabarStock;
    private javax.swing.JButton GuardarArchivo;
    private javax.swing.JButton ImprimirEanVence;
    private javax.swing.JLabel LblUbicacion;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton PrintTicket;
    private javax.swing.JButton Salir;
    private javax.swing.JButton Salir1;
    private javax.swing.JButton SalirCasa;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirEanVence;
    private javax.swing.JButton SalirEnlace;
    private javax.swing.JButton SalirFamilia;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirLista;
    private javax.swing.JButton SalirMarca;
    private javax.swing.JButton SalirMedida;
    private javax.swing.JButton SalirPais;
    private javax.swing.JButton SalirPrecios;
    private javax.swing.JButton SalirRubro;
    private javax.swing.JButton SalirStock;
    private javax.swing.JButton SalirStock1;
    private javax.swing.JButton SalirUbicacion;
    private javax.swing.JButton UpdatePrecios;
    private javax.swing.JDialog actualizar_precio;
    private javax.swing.JDialog actualizar_precios;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JButton buscarfamilia;
    private javax.swing.JButton buscarmarca;
    private javax.swing.JButton buscarmedida;
    private javax.swing.JButton buscarpais;
    private javax.swing.JButton buscarproveedor;
    private javax.swing.JButton buscarrubro;
    private javax.swing.JButton buscarubicacion;
    private javax.swing.JCheckBox cambiarprecio;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JTextField codcuenta;
    private javax.swing.JTextField codfamilia;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField codigobarra;
    private javax.swing.JTextField codigoproducto;
    private javax.swing.JTextField codprod;
    private javax.swing.JTextField codprod1;
    private javax.swing.JTextField codproducto;
    private javax.swing.JTextField codrubro;
    private javax.swing.JComboBox combofamilia;
    private javax.swing.JComboBox combomarca;
    private javax.swing.JComboBox combomedida;
    private javax.swing.JComboBox combopais;
    private javax.swing.JComboBox comboplan;
    private javax.swing.JComboBox comboproducto;
    private javax.swing.JComboBox comboproveedor;
    private javax.swing.JComboBox comborubro;
    private javax.swing.JComboBox comboubicacion;
    private javax.swing.JFormattedTextField conteo;
    private javax.swing.JFormattedTextField conteomayorista;
    private javax.swing.JFormattedTextField conversion;
    private javax.swing.JFormattedTextField costo;
    private javax.swing.JFormattedTextField costo1;
    private javax.swing.JTextField costomercaderia;
    private javax.swing.JDialog cuentas;
    private javax.swing.JTextField descripcion;
    private javax.swing.JTextField descripcionproducto;
    private javax.swing.JDialog detalle_productos;
    private javax.swing.JCheckBox estado;
    private javax.swing.JLabel etiquetacodigo;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private javax.swing.JLabel etiquetanombre;
    private javax.swing.JLabel etiquetaporcentaje;
    private javax.swing.JDialog etiquetas_multiples;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idcta;
    private javax.swing.JTextField iditem;
    private javax.swing.JLabel imagen;
    private javax.swing.JFormattedTextField incremento1;
    private javax.swing.JFormattedTextField incremento2;
    private javax.swing.JDialog inventario;
    private javax.swing.JFormattedTextField ivaporcentaje;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
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
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTextField jTBuscarFamilia;
    private javax.swing.JTextField jTBuscarMarca;
    private javax.swing.JTextField jTBuscarMedida;
    private javax.swing.JTextField jTBuscarPais;
    private javax.swing.JTextField jTBuscarPlan;
    private javax.swing.JTextField jTBuscarProveedor;
    private javax.swing.JTextField jTBuscarRubro;
    private javax.swing.JTextField jTBuscarUbicacion;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JDialog listaprecios;
    private javax.swing.JTextField marca;
    private javax.swing.JFormattedTextField mayorista;
    private javax.swing.JFormattedTextField mayorista1;
    private javax.swing.JFormattedTextField mayoristasugerido;
    private javax.swing.JTextField medida;
    private javax.swing.JFormattedTextField minorista;
    private javax.swing.JFormattedTextField minorista1;
    private javax.swing.JFormattedTextField minoristasugerido;
    private javax.swing.JTextField modo;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombrearchivo;
    private javax.swing.JTextField nombrecuenta;
    private javax.swing.JTextField nombrefamilia;
    private javax.swing.JTextField nombremarca;
    private javax.swing.JTextField nombremedida;
    private javax.swing.JTextField nombremercaderia;
    private javax.swing.JTextField nombrepais;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField nombreproducto1;
    private javax.swing.JTextField nombreproveedor;
    private javax.swing.JTextField nombrerubro;
    private javax.swing.JTextField nombrerubroprecio;
    private javax.swing.JTextField nombreubicacion;
    private javax.swing.JTextArea observacion;
    private javax.swing.JTextField paises;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JFormattedTextField porcentaje;
    private javax.swing.JFormattedTextField porcentaje1;
    private javax.swing.JFormattedTextField preciofinal;
    private javax.swing.JFormattedTextField preciomayorista;
    private javax.swing.JFormattedTextField preciominorista;
    private javax.swing.JTextField precioproducto;
    private javax.swing.JFormattedTextField precioventa;
    private javax.swing.JTextField proveedor;
    private javax.swing.JComboBox<String> redondear;
    private javax.swing.JTextField rubro;
    private javax.swing.JDialog stock;
    private javax.swing.JFormattedTextField stockactual;
    private javax.swing.JFormattedTextField stockminimo;
    private javax.swing.JTable tablaetiqueta;
    private javax.swing.JTable tablafamilia;
    private javax.swing.JTable tablahistorico;
    private javax.swing.JTable tablamarca;
    private javax.swing.JTable tablamedida;
    private javax.swing.JTable tablapais;
    private javax.swing.JTable tablaplan;
    private javax.swing.JTable tablaprecio;
    private javax.swing.JTable tablaprecios;
    private javax.swing.JTable tablaproductos;
    private javax.swing.JTable tablaproveedor;
    private javax.swing.JTable tablarubro;
    private javax.swing.JTable tablastock;
    private javax.swing.JTable tablaubicacion;
    private javax.swing.JComboBox<String> tipo_producto;
    private javax.swing.JTextField ubicacion;
    private com.toedter.calendar.JDateChooser vencimiento;
    // End of variables declaration//GEN-END:variables

    private class GenerarReporte extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            int nFila = tablaproductos.getSelectedRow();
            String cCodigo = tablaproductos.getValueAt(nFila, 0).toString();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cCodigo", cCodigo);
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/ficha_producto.jasper");
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

    private class GrillaProductos extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            productoDAO DAO = new productoDAO();
            try {
                for (producto prod : DAO.todoslista()) {
                    System.out.println(prod.getCodigo());
                    String Datos[] = {prod.getCodigo(), prod.getNombre(),
                        formatea.format(prod.getCosto()),
                        formatea.format(prod.getPrecio_maximo()),
                        formatea.format(prod.getPrecio_minimo()),
                        formatea.format(prod.getStock()),
                        prod.getRubro().getNombre(),
                        prod.getMarca().getNombre(),
                        prod.getUbicacion().getNombre(),
                        prod.getMedida().getNombre(),
                        prod.getCodigobarra()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablaproductos.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = tablaproductos.getRowCount();
            if (cantFilas > 0) {
                Modificar.setEnabled(true);
                Eliminar.setEnabled(true);
                UpdatePrecios.setEnabled(true);
                Listar.setEnabled(true);
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                UpdatePrecios.setEnabled(false);
                Listar.setEnabled(false);
            }
            tablaproductos.requestFocus();
            buscarcadena.requestFocus();
        }
    }

    private class GrillaPais extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopais.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopais.removeRow(0);
            }
            paisDAO paDAO = new paisDAO();
            try {
                for (pais ba : paDAO.todos()) {
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

    private class GrillaProveedor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproveedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproveedor.removeRow(0);
            }
            proveedorDAO provDAO = new proveedorDAO();
            try {
                for (proveedor pr : provDAO.todos()) {
                    String Datos[] = {String.valueOf(pr.getCodigo()), pr.getNombre()};
                    modeloproveedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproveedor.setRowSorter(new TableRowSorter(modeloproveedor));
            int cantFilas = tablaproveedor.getRowCount();
        }
    }

    private class GrillaFamilia extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelofamilia.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelofamilia.removeRow(0);
            }
            familiaDAO faDAO = new familiaDAO();
            try {
                for (familia fa : faDAO.todos()) {
                    String Datos[] = {String.valueOf(fa.getCodigo()), fa.getNombre()};
                    modelofamilia.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablafamilia.setRowSorter(new TableRowSorter(modelofamilia));
            int cantFilas = tablafamilia.getRowCount();
        }
    }

    private class GrillaRubro extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelorubro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelorubro.removeRow(0);
            }
            rubroDAO ruDAO = new rubroDAO();
            try {
                for (rubro ru : ruDAO.todos()) {
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

    private class GrillaMarca extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomarca.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomarca.removeRow(0);
            }
            marcaDAO maDAO = new marcaDAO();
            try {
                for (marca ma : maDAO.todos()) {
                    String Datos[] = {String.valueOf(ma.getCodigo()), ma.getNombre()};
                    modelomarca.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablamarca.setRowSorter(new TableRowSorter(modelomarca));
            int cantFilas = tablamarca.getRowCount();
        }
    }

    private class GrillaMedida extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomedida.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomedida.removeRow(0);
            }
            medidaDAO medDAO = new medidaDAO();
            try {
                for (medida med : medDAO.todos()) {
                    String Datos[] = {String.valueOf(med.getCodigo()), med.getNombre()};
                    modelomedida.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablamedida.setRowSorter(new TableRowSorter(modelomedida));
            int cantFilas = tablamedida.getRowCount();
        }
    }

    private class GrillaUbicacion extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloubicacion.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloubicacion.removeRow(0);
            }
            ubicacionDAO ubiDAO = new ubicacionDAO();
            try {
                for (ubicacion ub : ubiDAO.todos()) {
                    String Datos[] = {String.valueOf(ub.getCodigo()), ub.getNombre()};
                    modeloubicacion.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablaubicacion.setRowSorter(new TableRowSorter(modeloubicacion));
            int cantFilas = tablaubicacion.getRowCount();
        }
    }

    private class GrillaLista extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelolista.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelolista.removeRow(0);
            }
            lista_preciosDAO listaDAO = new lista_preciosDAO();
            try {
                for (lista_precios li : listaDAO.todos(codigo.getText())) {
                    String Datos[] = {formatea.format(li.getLimitecantidad()), formatea.format(li.getUtilidad()), formatea.format(li.getPrecioventa()), formatosinpunto.format(li.getItemid())};
                    modelolista.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablaprecio.setRowSorter(new TableRowSorter(modelolista));
            int cantFilas = tablaprecio.getRowCount();
            if (cantFilas > 0) {
                EditarPrecio.setEnabled(true);
                BorraPrecio.setEnabled(true);
            } else {
                EditarPrecio.setEnabled(true);
                BorraPrecio.setEnabled(false);
            }
        }
    }

    private class GenerarEan extends Thread {

        private String cReporte;

        public GenerarEan(String ReporteEtiqueta) {
            cReporte = ReporteEtiqueta;
        }

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            int nFila = tablaproductos.getSelectedRow();
            String cCodigo = tablaproductos.getValueAt(nFila, 0).toString();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cCodigo", cCodigo);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cReporte.trim());
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

    private class GrillaPlanProducto extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadReg = modeloplan.getRowCount();
            for (int i = 1; i <= cantidadReg; i++) {
                modeloplan.removeRow(0);
            }
            planDAO DAOPLA = new planDAO();
            try {
                for (plan pl : DAOPLA.TodoAsentables()) {
                    String DatosPlan[] = {pl.getCodigo(), pl.getNombre()};
                    System.out.println(pl.getCodigo());
                    System.out.println(pl.getNombre());

                    modeloplan.addRow(DatosPlan);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaplan.setRowSorter(new TableRowSorter(modeloplan));
            int cantF = tablaplan.getRowCount();
        }
    }

    private class GrillaxRubro extends Thread {

        public void run() {
            String precios = "0";
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloprecios.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloprecios.removeRow(0);
            }

            productoDAO DAO = new productoDAO();
            try {
                for (producto prod : DAO.todosxrubromini(Integer.valueOf(codrubro.getText()))) {
                    String Datos[] = {prod.getCodigo(), prod.getNombre(), formatea.format(prod.getCosto()), formatea.format(prod.getPrecio_maximo()), formatea.format(prod.getPrecio_minimo()), formatea.format(prod.getIncremento1()), formatea.format(prod.getIncremento2()), precios, precios};
                    modeloprecios.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablaprecios.setRowSorter(new TableRowSorter(modeloprecios));
            int cantFilas = tablaprecios.getRowCount();
            if (cantFilas > 0) {
                ActualizarPrecios.setEnabled(true);
                CalcularPrecios.setEnabled(true);
            } else {
                ActualizarPrecios.setEnabled(false);
                CalcularPrecios.setEnabled(false);
            }
            tablaprecios.requestFocus();
        }
    }
}

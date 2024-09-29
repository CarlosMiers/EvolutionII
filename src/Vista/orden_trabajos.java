/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.UUID;
import Clases.clsExportarExcel;
import Clases.generarQR;
import Clases.numero_a_letras;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.detalle_compraDAO;
import DAO.detalle_funcionarios_otDAO;
import DAO.detalle_orden_trabajoDAO;
import DAO.detalle_propuesta_terceroDAO;
import DAO.detalle_tarea_orden_trabajoDAO;
import DAO.ficha_empleadoDAO;
import DAO.orden_trabajoDAO;
import DAO.productoDAO;
import DAO.seccionDAO;
import DAO.stockDAO;
import DAO.sucursalDAO;
import DAO.usuarioDAO;
import Modelo.Tablas;
import Modelo.detalle_compra;
import Modelo.detalle_funcionarios_ot;
import Modelo.detalle_orden_trabajo;
import Modelo.detalle_propuesta_tercero;
import Modelo.detalle_tarea_orden_trabajo;
import Modelo.ficha_empleado;
import Modelo.orden_trabajo;
import Modelo.producto;
import Modelo.seccion;
import Modelo.stock;
import Modelo.sucursal;
import Modelo.usuario;
import com.google.zxing.WriterException;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class orden_trabajos extends javax.swing.JFrame {
    clsExportarExcel obj;
    Conexion con = null;
    ResultSet results = null;
    Statement stm, stm2 = null;
    Tablas modelo = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelovence = new Tablas();
    Tablas modelocomprobante = new Tablas();
    Tablas modeloempleado = new Tablas();
    Tablas modeloproducto = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelodetalletercero = new Tablas();
    Tablas modelodetalletarea = new Tablas();
    Tablas modeloproveedor = new Tablas();
    Tablas modeloseccion = new Tablas();
    Tablas modelopersonal = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocomprobante, trsfiltromoneda, trsfiltroproducto, trsfiltropro;
    private TableRowSorter trsfiltroempleado, trsfiltroseccion;
    DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
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
    int counter = 0;
    int nSeleccionPersonal = 0;

    /**
     * Creates new form Template
     */
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconoitemnuevo = new ImageIcon("src/Iconos/pencil_add.png");
    ImageIcon iconoitemupdate = new ImageIcon("src/Iconos/pencil.png");
    ImageIcon iconoitemdelete = new ImageIcon("src/Iconos/pencil_delete.png");

    public orden_trabajos() {
        initComponents();
        fechaemision.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    sucursal.requestFocus();
                }
            }
        });

        this.GrabarItem.setIcon(iconograbar);
        this.GrabarDetalleMantenimiento.setIcon(iconograbar);
        this.SalirItem.setIcon(iconosalir);

        this.nuevoitem.setIcon(icononuevo);
        this.editaritem.setIcon(iconoitemupdate);
        this.delitem.setIcon(iconoitemdelete);
        //TERCEROS
        this.GrabarDetalleTercero.setIcon(iconograbar);
        this.SalirTercero.setIcon(iconosalir);
        this.nuevoitemtercero.setIcon(icononuevo);
        this.editaritemtercero.setIcon(iconoitemupdate);
        this.delitemtercero.setIcon(iconoitemdelete);
        //TAREAS
        this.BuscarPersonalTarea.setIcon(iconobuscar);
        this.GrabarPersonal.setIcon(iconograbar);
        this.EliminarPersonal.setIcon(iconoborrar);
        this.GrabarDetalleTarea.setIcon(iconograbar);
        this.SalirTarea.setIcon(iconosalir);
        this.nuevoitemtarea.setIcon(icononuevo);
        this.editaritemtarea.setIcon(iconoitemupdate);
        this.delitemtarea.setIcon(iconoitemdelete);
        this.fechahoy.setVisible(false);
        this.GrabarOt.setIcon(iconograbar);
        this.GrabarDetalleMantenimiento.setIcon(iconograbar);
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.SalirCompleto.setIcon(iconosalir);
        this.Salir.setIcon(iconosalir);
        this.buscarSucursal.setIcon(iconobuscar);
        this.buscarEmpleado.setIcon(iconobuscar);
        this.BuscarSeccion.setIcon(iconobuscar);
        this.BuscarProducto.setIcon(iconobuscar);
        this.refrescar.setIcon(icorefresh);
        this.disponible.setVisible(false);
        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setVisible(false);
        this.cModo.setVisible(false);
        this.modo.setVisible(false);
        this.nombreproducto.setEnabled(false);
        this.totalitem.setEnabled(false);
        this.idControl.setText("0");
        this.sucursal.setText("0");
        this.Inicializar();
        this.cargarTitulo();
        this.TituloDetalle();
        this.TituloDetalleTercero();
        this.TituloDetalleTarea();
        this.TituloVence();
        this.TitSeccion();
        this.TitSuc();
        this.TituloProductos();;
        this.TituloProveedor();
        this.TitEmpleado();
        this.TituloPersonalAsignado();
        GrillaOT GrillaOC = new GrillaOT();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        orden_trabajos.GrillaProductos grillapr = new orden_trabajos.GrillaProductos();
        Thread hilopr = new Thread(grillapr);
        hilopr.start();

        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();

        GrillaProveedor grillaprov = new GrillaProveedor();
        Thread hiloprov = new Thread(grillaprov);
        hiloprov.start();

        GrillaFuncionario grillafun = new GrillaFuncionario();
        Thread hilofun = new Thread(grillafun);
        hilofun.start();

        GrillaSecc grillaSecc = new GrillaSecc();
        Thread hilosecc = new Thread(grillaSecc);
        hilosecc.start();

        new HiloControl(1, 5400000).start(); //VERIFICANDO VENCIMIENTOS
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
        detalle_orden_mantenimiento = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        numerodetalle = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fechaemisiondetalle = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        nombresecciondetalle = new javax.swing.JTextField();
        modo = new javax.swing.JTextField();
        nombresucursaldetalle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        solicitadopordetalle = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        estadodetalle = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabladetalle = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        nuevoitem = new javax.swing.JButton();
        editaritem = new javax.swing.JButton();
        delitem = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        Salir = new javax.swing.JButton();
        GrabarDetalleMantenimiento = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        totalcosto = new javax.swing.JFormattedTextField();
        BSucursal = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BProveedor = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        comboproveedor = new javax.swing.JComboBox();
        jTBuscarproveedor = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaproveedor = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        Aceptarprov = new javax.swing.JButton();
        Salirprov = new javax.swing.JButton();
        itemdetalleorden = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        codprod = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        BuscarProducto = new javax.swing.JButton();
        nombreproducto = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cantidad = new javax.swing.JFormattedTextField();
        costo = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        totalitem = new javax.swing.JFormattedTextField();
        cModo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        disponible = new javax.swing.JFormattedTextField();
        potrero = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        GrabarItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        detalle_orden_trabajo = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        empleado = new javax.swing.JTextField();
        buscarEmpleado = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        numero = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        fechaemision = new com.toedter.calendar.JDateChooser();
        galpon = new javax.swing.JTextField();
        seccion = new javax.swing.JTextField();
        BuscarSeccion = new javax.swing.JButton();
        nombreseccion = new javax.swing.JTextField();
        Socio1 = new javax.swing.JLabel();
        nombreempleado = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tipo = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        trabajoarealizar = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        GrabarOt = new javax.swing.JButton();
        SalirOt = new javax.swing.JButton();
        BEmpleado = new javax.swing.JDialog();
        jPanel27 = new javax.swing.JPanel();
        comboempleado = new javax.swing.JComboBox();
        jTBuscarEmpleado = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaempleado = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        AceptarEmpleado = new javax.swing.JButton();
        SalirEmpleado = new javax.swing.JButton();
        BSeccion = new javax.swing.JDialog();
        jPanel29 = new javax.swing.JPanel();
        comboseccion = new javax.swing.JComboBox();
        jTBuscarSeccion = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaseccion = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        AceptarSecc = new javax.swing.JButton();
        SalirSecc = new javax.swing.JButton();
        itemterceros = new javax.swing.JDialog();
        jPanel31 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        proveedortercerizado = new javax.swing.JTextField();
        aprobarpresupuesto = new javax.swing.JCheckBox();
        presupuestotercero = new javax.swing.JFormattedTextField();
        jPanel32 = new javax.swing.JPanel();
        GrabarItemTercero = new javax.swing.JButton();
        SalirItemTercero = new javax.swing.JButton();
        detalle_tarea_ot = new javax.swing.JDialog();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        numerotarea = new javax.swing.JFormattedTextField();
        fechaemisiontarea = new com.toedter.calendar.JDateChooser();
        nombresecciontarea = new javax.swing.JTextField();
        modo2 = new javax.swing.JTextField();
        nombresucursaltarea = new javax.swing.JTextField();
        solicitadoportarea = new javax.swing.JTextField();
        estadotarea = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        etiquetanumerotarea = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tabladetalletarea = new javax.swing.JTable();
        jPanel36 = new javax.swing.JPanel();
        nuevoitemtarea = new javax.swing.JButton();
        editaritemtarea = new javax.swing.JButton();
        delitemtarea = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        observaciontarea = new javax.swing.JTextArea();
        jPanel38 = new javax.swing.JPanel();
        SalirTarea = new javax.swing.JButton();
        GrabarDetalleTarea = new javax.swing.JButton();
        itemtareas = new javax.swing.JDialog();
        jPanel39 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        concluirtarea = new javax.swing.JCheckBox();
        jScrollPane14 = new javax.swing.JScrollPane();
        descripciontarea = new javax.swing.JTextArea();
        jPanel40 = new javax.swing.JPanel();
        GrabarItemTarea = new javax.swing.JButton();
        SalirItemTarea = new javax.swing.JButton();
        detalle_orden_tercero = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        numerotercero = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        fechaemisiontercero = new com.toedter.calendar.JDateChooser();
        jLabel37 = new javax.swing.JLabel();
        nombresecciontercero = new javax.swing.JTextField();
        modo1 = new javax.swing.JTextField();
        nombresucursaltercero = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        solicitadoportercero = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        estadotercero = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabladetalletercero = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        nuevoitemtercero = new javax.swing.JButton();
        editaritemtercero = new javax.swing.JButton();
        delitemtercero = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        observaciontercero = new javax.swing.JTextArea();
        jPanel20 = new javax.swing.JPanel();
        SalirTercero = new javax.swing.JButton();
        GrabarDetalleTercero = new javax.swing.JButton();
        fechaprevista = new javax.swing.JDialog();
        jPanel41 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        fechaprevistaentrega = new com.toedter.calendar.JDateChooser();
        jPanel42 = new javax.swing.JPanel();
        GrabarFechaPrevista = new javax.swing.JButton();
        SalirFechaPrevista = new javax.swing.JButton();
        personal = new javax.swing.JDialog();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tablapersonalasignado = new javax.swing.JTable();
        jPanel44 = new javax.swing.JPanel();
        etiquetapersonal = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        GrabarPersonal = new javax.swing.JButton();
        EliminarPersonal = new javax.swing.JButton();
        nombrepersonalasignado = new javax.swing.JTextField();
        personalasignado = new javax.swing.JTextField();
        BuscarPersonalTarea = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        SalirPersonal = new javax.swing.JButton();
        vencimientos = new javax.swing.JDialog();
        jPanel46 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tablavence = new javax.swing.JTable();
        jPanel47 = new javax.swing.JPanel();
        SalirVencimiento = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetacredito = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        buscarcadena = new javax.swing.JTextField();
        idControl = new javax.swing.JTextField();
        fechahoy = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        SalirCompleto = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        mantenimiento = new javax.swing.JButton();
        tercerizados = new javax.swing.JButton();
        novedades = new javax.swing.JButton();
        finalizar = new javax.swing.JButton();
        aprobar = new javax.swing.JButton();
        iniciar = new javax.swing.JButton();
        detalle_tareas = new javax.swing.JButton();
        EtiquetaQr = new javax.swing.JLabel();
        entregaprevista = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

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
        jTBuscarProducto.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarProducto.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProductoActionPerformed(evt);
            }
        });

        SalirProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirProducto.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.SalirCliente.text")); // NOI18N
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

        detalle_orden_mantenimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_orden_mantenimientoFocusGained(evt);
            }
        });
        detalle_orden_mantenimiento.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_orden_mantenimientoWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_orden_mantenimiento.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_orden_mantenimientoWindowActivated(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel12.text_1")); // NOI18N

        numerodetalle.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        numerodetalle.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numerodetalle.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        numerodetalle.setEnabled(false);
        numerodetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numerodetalleActionPerformed(evt);
            }
        });
        numerodetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numerodetalleKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numerodetalleKeyReleased(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel1.text_1")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel2.text_1")); // NOI18N

        fechaemisiondetalle.setBackground(new java.awt.Color(0, 0, 0));
        fechaemisiondetalle.setEnabled(false);
        fechaemisiondetalle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaemisiondetalleFocusGained(evt);
            }
        });
        fechaemisiondetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaemisiondetalleKeyPressed(evt);
            }
        });

        jLabel6.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel6.text_1")); // NOI18N

        nombresecciondetalle.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresecciondetalle.setEnabled(false);

        modo.setEnabled(false);

        nombresucursaldetalle.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursaldetalle.setEnabled(false);

        jLabel4.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel4.text_1")); // NOI18N

        solicitadopordetalle.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        solicitadopordetalle.setEnabled(false);

        jLabel31.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel31.text_1")); // NOI18N

        estadodetalle.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        estadodetalle.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(numerodetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jLabel2))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(nombresucursaldetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(fechaemisiondetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel4))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(nombresecciondetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(solicitadopordetalle, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                    .addComponent(estadodetalle))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)
                        .addComponent(numerodetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fechaemisiondetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(solicitadopordetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(nombresucursaldetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(nombresecciondetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(estadodetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabladetalle.setModel(modelodetalle);
        tabladetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabladetalle.setAutoscrolls(false);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nuevoitem.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.nuevoitem.text_1")); // NOI18N
        nuevoitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoitemActionPerformed(evt);
            }
        });
        nuevoitem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nuevoitemKeyPressed(evt);
            }
        });

        editaritem.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.editaritem.text_1")); // NOI18N
        editaritem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editaritemActionPerformed(evt);
            }
        });

        delitem.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.delitem.text_1")); // NOI18N
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
                    .addComponent(nuevoitem, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(nuevoitem)
                .addGap(18, 18, 18)
                .addComponent(editaritem)
                .addGap(18, 18, 18)
                .addComponent(delitem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jPanel9.border.title_1"))); // NOI18N

        observacion.setColumns(20);
        observacion.setRows(5);
        observacion.setEnabled(false);
        jScrollPane3.setViewportView(observacion);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Salir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salir.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Salir.text_1")); // NOI18N
        Salir.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Salir.toolTipText_1_1")); // NOI18N
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });
        Salir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SalirKeyPressed(evt);
            }
        });

        GrabarDetalleMantenimiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarDetalleMantenimiento.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarDetalleMantenimiento.text_1")); // NOI18N
        GrabarDetalleMantenimiento.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarDetalleMantenimiento.toolTipText_1")); // NOI18N
        GrabarDetalleMantenimiento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarDetalleMantenimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarDetalleMantenimientoActionPerformed(evt);
            }
        });
        GrabarDetalleMantenimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GrabarDetalleMantenimientoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GrabarDetalleMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Salir)
                    .addComponent(GrabarDetalleMantenimiento))
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jPanel11.border.title_1"))); // NOI18N

        totalcosto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalcosto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalcosto.setEnabled(false);
        totalcosto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(totalcosto, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalcosto, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout detalle_orden_mantenimientoLayout = new javax.swing.GroupLayout(detalle_orden_mantenimiento.getContentPane());
        detalle_orden_mantenimiento.getContentPane().setLayout(detalle_orden_mantenimientoLayout);
        detalle_orden_mantenimientoLayout.setHorizontalGroup(
            detalle_orden_mantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_mantenimientoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_orden_mantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_mantenimientoLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detalle_orden_mantenimientoLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(detalle_orden_mantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        detalle_orden_mantenimientoLayout.setVerticalGroup(
            detalle_orden_mantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_mantenimientoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_orden_mantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(detalle_orden_mantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detalle_orden_mantenimientoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detalle_orden_mantenimientoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.SalirCliente.text")); // NOI18N
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

        BProveedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProveedor.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproveedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        comboproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproveedorActionPerformed(evt);
            }
        });

        jTBuscarproveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarproveedor.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarproveedorActionPerformed(evt);
            }
        });
        jTBuscarproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarproveedorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaproveedor.setModel(modeloproveedor     );
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
        jScrollPane5.setViewportView(tablaproveedor);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptarprov.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptarprov.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.Aceptarcliente.text")); // NOI18N
        Aceptarprov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptarprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarprovActionPerformed(evt);
            }
        });

        Salirprov.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salirprov.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.SalirCliente.text")); // NOI18N
        Salirprov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salirprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirprovActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptarprov, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(Salirprov, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptarprov)
                    .addComponent(Salirprov))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProveedorLayout = new javax.swing.GroupLayout(BProveedor.getContentPane());
        BProveedor.getContentPane().setLayout(BProveedorLayout);
        BProveedorLayout.setHorizontalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProveedorLayout.setVerticalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        itemdetalleorden.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        itemdetalleorden.setTitle(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.itemdetalleorden.title_1")); // NOI18N

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        codprod.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.codprod.text")); // NOI18N
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

        jLabel25.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.jLabel14.text")); // NOI18N

        BuscarProducto.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.BuscarProducto.text")); // NOI18N
        BuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarProductoActionPerformed(evt);
            }
        });

        nombreproducto.setEditable(false);
        nombreproducto.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.nombreproducto.text")); // NOI18N
        nombreproducto.setEnabled(false);

        jLabel26.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.jLabel15.text")); // NOI18N

        cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.cantidad.text")); // NOI18N
        cantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cantidadFocusGained(evt);
            }
        });
        cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadKeyPressed(evt);
            }
        });

        costo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        costo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        costo.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.preciounitario.text")); // NOI18N
        costo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                costoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                costoFocusLost(evt);
            }
        });
        costo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costoActionPerformed(evt);
            }
        });
        costo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                costoKeyPressed(evt);
            }
        });

        jLabel28.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.jLabel17.text")); // NOI18N

        totalitem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalitem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalitem.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.totalitem.text")); // NOI18N

        cModo.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.cModo.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel5.text_1")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel7.text_1")); // NOI18N

        disponible.setEnabled(false);

        potrero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.potrero.text")); // NOI18N
        potrero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                potreroKeyPressed(evt);
            }
        });

        jLabel35.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel35.text")); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addComponent(totalitem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cantidad, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(costo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel5)
                            .addComponent(jLabel35))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(potrero, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(disponible, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel21Layout.createSequentialGroup()
                                        .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())))))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(potrero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(disponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(11, 11, 11)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(totalitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addContainerGap())
        );

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItem.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.NuevoItem.text")); // NOI18N
        GrabarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarItem.setPreferredSize(new java.awt.Dimension(39, 22));
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

        SalirItem.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "detalle_facturas.SalirItem.text")); // NOI18N
        SalirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirItem.setPreferredSize(new java.awt.Dimension(21, 18));
        SalirItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalirItemMouseClicked(evt);
            }
        });
        SalirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });
        SalirItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SalirItemKeyPressed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(SalirItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GrabarItem, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout itemdetalleordenLayout = new javax.swing.GroupLayout(itemdetalleorden.getContentPane());
        itemdetalleorden.getContentPane().setLayout(itemdetalleordenLayout);
        itemdetalleordenLayout.setHorizontalGroup(
            itemdetalleordenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemdetalleordenLayout.setVerticalGroup(
            itemdetalleordenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemdetalleordenLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        detalle_orden_trabajo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_orden_trabajoFocusGained(evt);
            }
        });
        detalle_orden_trabajo.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_orden_trabajoWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_orden_trabajo.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_orden_trabajoWindowActivated(evt);
            }
        });

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        empleado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        empleado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                empleadoFocusGained(evt);
            }
        });
        empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empleadoActionPerformed(evt);
            }
        });
        empleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                empleadoKeyPressed(evt);
            }
        });

        buscarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarEmpleadoActionPerformed(evt);
            }
        });

        jLabel15.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel15.text_1")); // NOI18N

        numero.setEditable(false);
        numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numero.setEnabled(false);
        numero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numeroFocusGained(evt);
            }
        });
        numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroActionPerformed(evt);
            }
        });
        numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numeroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numeroKeyReleased(evt);
            }
        });

        jLabel17.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel17.text_1")); // NOI18N

        fechaemision.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaemisionFocusGained(evt);
            }
        });
        fechaemision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaemisionKeyPressed(evt);
            }
        });

        galpon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        galpon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                galponFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                galponFocusLost(evt);
            }
        });
        galpon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                galponActionPerformed(evt);
            }
        });
        galpon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                galponKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                galponKeyReleased(evt);
            }
        });

        seccion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        seccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                seccionFocusGained(evt);
            }
        });
        seccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seccionActionPerformed(evt);
            }
        });
        seccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                seccionKeyPressed(evt);
            }
        });

        BuscarSeccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarSeccionActionPerformed(evt);
            }
        });

        nombreseccion.setEnabled(false);
        nombreseccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreseccionKeyPressed(evt);
            }
        });

        Socio1.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Socio1.text_1")); // NOI18N

        nombreempleado.setEnabled(false);
        nombreempleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreempleadoKeyPressed(evt);
            }
        });

        jLabel18.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel18.text_1")); // NOI18N

        jLabel20.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel20.text_1")); // NOI18N

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sucursalKeyReleased(evt);
            }
        });

        buscarSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buscarSucursalMouseClicked(evt);
            }
        });
        buscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        jLabel16.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel16.text_1")); // NOI18N

        tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MANTENIMIENTO", "TERCERIZADO" }));

        jLabel22.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel22.text_1")); // NOI18N

        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        trabajoarealizar.setColumns(20);
        trabajoarealizar.setRows(5);
        jScrollPane9.setViewportView(trabajoarealizar);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel23.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(Socio1)
                    .addComponent(jLabel18)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(galpon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(seccion, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addComponent(empleado)
                            .addComponent(sucursal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(BuscarSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombreseccion))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombresucursal))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(buscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombreempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addComponent(nombreempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(seccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Socio1))
                    .addComponent(nombreseccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(galpon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarOt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarOt.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarOt.text_1")); // NOI18N
        GrabarOt.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarOt.toolTipText_1")); // NOI18N
        GrabarOt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarOt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarOtActionPerformed(evt);
            }
        });

        SalirOt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirOt.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirOt.text_1")); // NOI18N
        SalirOt.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirOt.toolTipText_1")); // NOI18N
        SalirOt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirOt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirOtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarOt, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SalirOt, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarOt)
                    .addComponent(SalirOt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detalle_orden_trabajoLayout = new javax.swing.GroupLayout(detalle_orden_trabajo.getContentPane());
        detalle_orden_trabajo.getContentPane().setLayout(detalle_orden_trabajoLayout);
        detalle_orden_trabajoLayout.setHorizontalGroup(
            detalle_orden_trabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_orden_trabajoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_orden_trabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        detalle_orden_trabajoLayout.setVerticalGroup(
            detalle_orden_trabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_trabajoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BEmpleado.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BEmpleado.setTitle("null");

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboempleado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboempleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboempleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboempleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboempleadoActionPerformed(evt);
            }
        });

        jTBuscarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarEmpleado.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarEmpleadoActionPerformed(evt);
            }
        });
        jTBuscarEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarEmpleadoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(comboempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaempleado.setModel(modeloempleado);
        tablaempleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaempleadoMouseClicked(evt);
            }
        });
        tablaempleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaempleadoKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(tablaempleado);

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarEmpleado.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarEmpleadoActionPerformed(evt);
            }
        });

        SalirEmpleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirEmpleado.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarEmpleado)
                    .addComponent(SalirEmpleado))
                .addContainerGap())
        );

        javax.swing.GroupLayout BEmpleadoLayout = new javax.swing.GroupLayout(BEmpleado.getContentPane());
        BEmpleado.getContentPane().setLayout(BEmpleadoLayout);
        BEmpleadoLayout.setHorizontalGroup(
            BEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BEmpleadoLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BEmpleadoLayout.setVerticalGroup(
            BEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BEmpleadoLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BSeccion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSeccion.setTitle("null");

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboseccion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboseccion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboseccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboseccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboseccionActionPerformed(evt);
            }
        });

        jTBuscarSeccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSeccion.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarSeccionActionPerformed(evt);
            }
        });
        jTBuscarSeccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarSeccionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(comboseccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboseccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaseccion.setModel(modeloseccion    );
        tablaseccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaseccionMouseClicked(evt);
            }
        });
        tablaseccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaseccionKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(tablaseccion);

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSecc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSecc.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSecc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSecc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSeccActionPerformed(evt);
            }
        });

        SalirSecc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSecc.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSecc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSecc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSeccActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSecc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSecc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSecc)
                    .addComponent(SalirSecc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSeccionLayout = new javax.swing.GroupLayout(BSeccion.getContentPane());
        BSeccion.getContentPane().setLayout(BSeccionLayout);
        BSeccionLayout.setHorizontalGroup(
            BSeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSeccionLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSeccionLayout.setVerticalGroup(
            BSeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSeccionLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        itemterceros.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        itemterceros.setTitle(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.itemterceros.title_1")); // NOI18N

        jPanel31.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel19.text_1")); // NOI18N

        jLabel21.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel21.text_1")); // NOI18N

        proveedortercerizado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                proveedortercerizadoFocusLost(evt);
            }
        });
        proveedortercerizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proveedortercerizadoActionPerformed(evt);
            }
        });
        proveedortercerizado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                proveedortercerizadoKeyPressed(evt);
            }
        });

        aprobarpresupuesto.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.aprobarpresupuesto.text_1")); // NOI18N

        presupuestotercero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        presupuestotercero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        presupuestotercero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                presupuestoterceroKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19))
                .addGap(28, 28, 28)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aprobarpresupuesto)
                    .addComponent(presupuestotercero, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proveedortercerizado, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(proveedortercerizado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(presupuestotercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(29, 29, 29)
                .addComponent(aprobarpresupuesto)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItemTercero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarItemTercero.text_1")); // NOI18N
        GrabarItemTercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemTerceroActionPerformed(evt);
            }
        });

        SalirItemTercero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirItemTercero.text_1")); // NOI18N
        SalirItemTercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemTerceroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(GrabarItemTercero, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(SalirItemTercero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarItemTercero)
                    .addComponent(SalirItemTercero))
                .addContainerGap())
        );

        javax.swing.GroupLayout itemtercerosLayout = new javax.swing.GroupLayout(itemterceros.getContentPane());
        itemterceros.getContentPane().setLayout(itemtercerosLayout);
        itemtercerosLayout.setHorizontalGroup(
            itemtercerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemtercerosLayout.setVerticalGroup(
            itemtercerosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemtercerosLayout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        detalle_tarea_ot.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        detalle_tarea_ot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_tarea_otFocusGained(evt);
            }
        });
        detalle_tarea_ot.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_tarea_otWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_tarea_ot.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_tarea_otWindowActivated(evt);
            }
        });

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel34.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        numerotarea.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        numerotarea.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numerotarea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        numerotarea.setEnabled(false);
        numerotarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numerotareaActionPerformed(evt);
            }
        });
        numerotarea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numerotareaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numerotareaKeyReleased(evt);
            }
        });

        fechaemisiontarea.setBackground(new java.awt.Color(0, 0, 0));
        fechaemisiontarea.setEnabled(false);
        fechaemisiontarea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaemisiontareaFocusGained(evt);
            }
        });
        fechaemisiontarea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaemisiontareaKeyPressed(evt);
            }
        });

        nombresecciontarea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresecciontarea.setEnabled(false);

        modo2.setEnabled(false);

        nombresucursaltarea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursaltarea.setEnabled(false);

        solicitadoportarea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        solicitadoportarea.setEnabled(false);

        estadotarea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        estadotarea.setEnabled(false);

        jLabel10.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel10.text_1")); // NOI18N

        etiquetanumerotarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.etiquetanumerotarea.text_1")); // NOI18N

        jLabel11.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel11.text")); // NOI18N

        jLabel24.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel24.text")); // NOI18N

        jLabel27.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel27.text")); // NOI18N

        jLabel36.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel36.text")); // NOI18N

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(etiquetanumerotarea))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numerotarea, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursaltarea, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(fechaemisiontarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(modo2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombresecciontarea, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(solicitadoportarea, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(estadotarea))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(numerotarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(etiquetanumerotarea)
                        .addComponent(jLabel11))
                    .addComponent(fechaemisiontarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(solicitadoportarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(modo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nombresucursaltarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(jLabel24)
                    .addComponent(nombresecciontarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(estadotarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabladetalletarea.setModel(modelodetalletarea);
        tabladetalletarea.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabladetalletarea.setAutoscrolls(false);
        tabladetalletarea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabladetalletareaMouseEntered(evt);
            }
        });
        jScrollPane12.setViewportView(tabladetalletarea);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane12)
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nuevoitemtarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ordentrabajomientras.nuevoitemtarea.text")); // NOI18N
        nuevoitemtarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoitemtareaActionPerformed(evt);
            }
        });
        nuevoitemtarea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nuevoitemtareaKeyPressed(evt);
            }
        });

        editaritemtarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ordentrabajomientras.editaritemtarea.text")); // NOI18N
        editaritemtarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editaritemtareaActionPerformed(evt);
            }
        });

        delitemtarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ordentrabajomientras.delitemtarea.text")); // NOI18N
        delitemtarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delitemtareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delitemtarea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editaritemtarea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuevoitemtarea, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(nuevoitemtarea)
                .addGap(18, 18, 18)
                .addComponent(editaritemtarea)
                .addGap(18, 18, 18)
                .addComponent(delitemtarea)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ordentrabajomientras.jPanel9.border.title"))); // NOI18N

        observaciontarea.setColumns(20);
        observaciontarea.setRows(5);
        observaciontarea.setEnabled(false);
        jScrollPane13.setViewportView(observaciontarea);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        SalirTarea.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SalirTarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirTarea.text")); // NOI18N
        SalirTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirTareaActionPerformed(evt);
            }
        });

        GrabarDetalleTarea.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        GrabarDetalleTarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarDetalleTarea.text")); // NOI18N
        GrabarDetalleTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarDetalleTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GrabarDetalleTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SalirTarea, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalirTarea)
                    .addComponent(GrabarDetalleTarea))
                .addContainerGap())
        );

        javax.swing.GroupLayout detalle_tarea_otLayout = new javax.swing.GroupLayout(detalle_tarea_ot.getContentPane());
        detalle_tarea_ot.getContentPane().setLayout(detalle_tarea_otLayout);
        detalle_tarea_otLayout.setHorizontalGroup(
            detalle_tarea_otLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_tarea_otLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_tarea_otLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detalle_tarea_otLayout.createSequentialGroup()
                        .addGroup(detalle_tarea_otLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(detalle_tarea_otLayout.createSequentialGroup()
                                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(detalle_tarea_otLayout.createSequentialGroup()
                                .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        detalle_tarea_otLayout.setVerticalGroup(
            detalle_tarea_otLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_tarea_otLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_tarea_otLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(detalle_tarea_otLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_tarea_otLayout.createSequentialGroup()
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );

        itemtareas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        itemtareas.setTitle(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "ordentrabajomientras.itemtareas.title")); // NOI18N

        jPanel39.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel29.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel29.text")); // NOI18N

        concluirtarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.concluirtarea.text")); // NOI18N

        descripciontarea.setColumns(100000);
        descripciontarea.setLineWrap(true);
        descripciontarea.setRows(150);
        descripciontarea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                descripciontareaKeyTyped(evt);
            }
        });
        jScrollPane14.setViewportView(descripciontarea);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(concluirtarea)
                    .addComponent(jLabel29))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(concluirtarea))
        );

        jPanel40.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItemTarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarItemTarea.text")); // NOI18N
        GrabarItemTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemTareaActionPerformed(evt);
            }
        });

        SalirItemTarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirItemTarea.text")); // NOI18N
        SalirItemTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemTareaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(201, 201, 201)
                .addComponent(GrabarItemTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(SalirItemTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarItemTarea)
                    .addComponent(SalirItemTarea))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout itemtareasLayout = new javax.swing.GroupLayout(itemtareas.getContentPane());
        itemtareas.getContentPane().setLayout(itemtareasLayout);
        itemtareasLayout.setHorizontalGroup(
            itemtareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        itemtareasLayout.setVerticalGroup(
            itemtareasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemtareasLayout.createSequentialGroup()
                .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        detalle_orden_tercero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_orden_terceroFocusGained(evt);
            }
        });
        detalle_orden_tercero.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_orden_terceroWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_orden_tercero.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_orden_terceroWindowActivated(evt);
            }
        });

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel13.text_1")); // NOI18N

        numerotercero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        numerotercero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numerotercero.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        numerotercero.setEnabled(false);
        numerotercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroterceroActionPerformed(evt);
            }
        });
        numerotercero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numeroterceroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numeroterceroKeyReleased(evt);
            }
        });

        jLabel14.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel14.text_1")); // NOI18N

        fechaemisiontercero.setBackground(new java.awt.Color(0, 0, 0));
        fechaemisiontercero.setEnabled(false);
        fechaemisiontercero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaemisionterceroFocusGained(evt);
            }
        });
        fechaemisiontercero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaemisionterceroKeyPressed(evt);
            }
        });

        jLabel37.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel37.text_1")); // NOI18N

        nombresecciontercero.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresecciontercero.setEnabled(false);

        modo1.setEnabled(false);

        nombresucursaltercero.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursaltercero.setEnabled(false);

        jLabel38.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel38.text_1")); // NOI18N

        solicitadoportercero.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        solicitadoportercero.setEnabled(false);

        jLabel32.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel32.text_1")); // NOI18N

        estadotercero.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        estadotercero.setEnabled(false);

        jLabel9.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel9.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(numerotercero, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jLabel14))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(nombresucursaltercero, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(nombresecciontercero, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel32))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(fechaemisiontercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(modo1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel38)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(solicitadoportercero, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(estadotercero))
                .addGap(181, 181, 181))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(numerotercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addComponent(fechaemisiontercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(solicitadoportercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(modo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel37)
                    .addComponent(nombresecciontercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(estadotercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursaltercero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabladetalletercero.setModel(modelodetalletercero);
        tabladetalletercero.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabladetalletercero.setAutoscrolls(false);
        tabladetalletercero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabladetalleterceroMouseEntered(evt);
            }
        });
        jScrollPane6.setViewportView(tabladetalletercero);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nuevoitemtercero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.nuevoitemtercero.text_1")); // NOI18N
        nuevoitemtercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoitemterceroActionPerformed(evt);
            }
        });
        nuevoitemtercero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nuevoitemterceroKeyPressed(evt);
            }
        });

        editaritemtercero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.editaritemtercero.text_1")); // NOI18N
        editaritemtercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editaritemterceroActionPerformed(evt);
            }
        });

        delitemtercero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.delitemtercero.text_1")); // NOI18N
        delitemtercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delitemterceroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delitemtercero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editaritemtercero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuevoitemtercero, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(nuevoitemtercero)
                .addGap(18, 18, 18)
                .addComponent(editaritemtercero)
                .addGap(18, 18, 18)
                .addComponent(delitemtercero)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jPanel19.border.title_1"))); // NOI18N

        observaciontercero.setColumns(20);
        observaciontercero.setRows(5);
        observaciontercero.setEnabled(false);
        jScrollPane7.setViewportView(observaciontercero);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        SalirTercero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirTercero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirTercero.text_1")); // NOI18N
        SalirTercero.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirTercero.toolTipText_1")); // NOI18N
        SalirTercero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirTercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirTerceroActionPerformed(evt);
            }
        });
        SalirTercero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SalirTerceroKeyPressed(evt);
            }
        });

        GrabarDetalleTercero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarDetalleTercero.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarDetalleTercero.text_1")); // NOI18N
        GrabarDetalleTercero.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarDetalleTercero.toolTipText_1")); // NOI18N
        GrabarDetalleTercero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarDetalleTercero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarDetalleTerceroActionPerformed(evt);
            }
        });
        GrabarDetalleTercero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GrabarDetalleTerceroKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GrabarDetalleTercero, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(SalirTercero, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalirTercero)
                    .addComponent(GrabarDetalleTercero))
                .addContainerGap())
        );

        javax.swing.GroupLayout detalle_orden_terceroLayout = new javax.swing.GroupLayout(detalle_orden_tercero.getContentPane());
        detalle_orden_tercero.getContentPane().setLayout(detalle_orden_terceroLayout);
        detalle_orden_terceroLayout.setHorizontalGroup(
            detalle_orden_terceroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_orden_terceroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_orden_terceroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detalle_orden_terceroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(detalle_orden_terceroLayout.createSequentialGroup()
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(detalle_orden_terceroLayout.createSequentialGroup()
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        detalle_orden_terceroLayout.setVerticalGroup(
            detalle_orden_terceroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_terceroLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_orden_terceroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(detalle_orden_terceroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_terceroLayout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))))
        );

        fechaprevista.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        fechaprevista.setTitle(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.fechaprevista.title")); // NOI18N

        jPanel41.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel30.text")); // NOI18N

        fechaprevistaentrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaprevistaentregaFocusGained(evt);
            }
        });
        fechaprevistaentrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaprevistaentregaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addGap(26, 26, 26))
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(fechaprevistaentrega, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel30)
                .addGap(29, 29, 29)
                .addComponent(fechaprevistaentrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel42.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarFechaPrevista.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarFechaPrevista.text")); // NOI18N
        GrabarFechaPrevista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarFechaPrevista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarFechaPrevistaActionPerformed(evt);
            }
        });

        SalirFechaPrevista.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirFechaPrevista.text")); // NOI18N
        SalirFechaPrevista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFechaPrevista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFechaPrevistaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel42Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarFechaPrevista, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(SalirFechaPrevista, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarFechaPrevista)
                    .addComponent(SalirFechaPrevista))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fechaprevistaLayout = new javax.swing.GroupLayout(fechaprevista.getContentPane());
        fechaprevista.getContentPane().setLayout(fechaprevistaLayout);
        fechaprevistaLayout.setHorizontalGroup(
            fechaprevistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        fechaprevistaLayout.setVerticalGroup(
            fechaprevistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fechaprevistaLayout.createSequentialGroup()
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        personal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel43.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablapersonalasignado.setModel(modelopersonal  );
        jScrollPane15.setViewportView(tablapersonalasignado);

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane15)
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(183, 183, 183))
        );

        jPanel44.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetapersonal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        etiquetapersonal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetapersonal.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.etiquetapersonal.text")); // NOI18N

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(etiquetapersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(etiquetapersonal)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel45.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel33.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel33.text")); // NOI18N

        GrabarPersonal.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.GrabarPersonal.text")); // NOI18N
        GrabarPersonal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarPersonalActionPerformed(evt);
            }
        });

        EliminarPersonal.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.EliminarPersonal.text")); // NOI18N
        EliminarPersonal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EliminarPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarPersonalActionPerformed(evt);
            }
        });

        nombrepersonalasignado.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.nombrepersonalasignado.text")); // NOI18N
        nombrepersonalasignado.setEnabled(false);

        personalasignado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        personalasignado.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.personalasignado.text")); // NOI18N
        personalasignado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personalasignadoActionPerformed(evt);
            }
        });

        BuscarPersonalTarea.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.BuscarPersonalTarea.text")); // NOI18N
        BuscarPersonalTarea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarPersonalTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPersonalTareaActionPerformed(evt);
            }
        });

        jLabel34.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jLabel34.text")); // NOI18N

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalasignado, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(BuscarPersonalTarea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nombrepersonalasignado, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33)
                        .addGap(114, 114, 114)))
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GrabarPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(EliminarPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(GrabarPersonal)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EliminarPersonal)
                    .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nombrepersonalasignado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(personalasignado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuscarPersonalTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        SalirPersonal.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirPersonal.text")); // NOI18N
        SalirPersonal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPersonalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout personalLayout = new javax.swing.GroupLayout(personal.getContentPane());
        personal.getContentPane().setLayout(personalLayout);
        personalLayout.setHorizontalGroup(
            personalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SalirPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        personalLayout.setVerticalGroup(
            personalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SalirPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        vencimientos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        vencimientos.setTitle(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.vencimientos.title")); // NOI18N

        tablavence.setModel(modelovence);
        jScrollPane16.setViewportView(tablavence);

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );

        SalirVencimiento.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirVencimiento.text")); // NOI18N
        SalirVencimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirVencimientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SalirVencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SalirVencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout vencimientosLayout = new javax.swing.GroupLayout(vencimientos.getContentPane());
        vencimientos.getContentPane().setLayout(vencimientosLayout);
        vencimientosLayout.setHorizontalGroup(
            vencimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vencimientosLayout.createSequentialGroup()
                .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        vencimientosLayout.setVerticalGroup(
            vencimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vencimientosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        etiquetacredito.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.etiquetacredito.text_1")); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Número", "Sucursal", "Sección", "Trabajo a Realizar" }));
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

        idControl.setEditable(false);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(fechahoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechahoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        Modificar.setBackground(new java.awt.Color(255, 255, 255));
        Modificar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Modificar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Modificar.text_1")); // NOI18N
        Modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        Agregar.setBackground(new java.awt.Color(255, 255, 255));
        Agregar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Agregar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Agregar.text_1")); // NOI18N
        Agregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });
        Agregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarKeyPressed(evt);
            }
        });

        Eliminar.setBackground(new java.awt.Color(255, 255, 255));
        Eliminar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Eliminar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Eliminar.text_1")); // NOI18N
        Eliminar.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Eliminar.toolTipText_1")); // NOI18N
        Eliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        Listar.setBackground(new java.awt.Color(255, 255, 255));
        Listar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Listar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Listar.text_1")); // NOI18N
        Listar.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.Listar.toolTipText_1")); // NOI18N
        Listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListarActionPerformed(evt);
            }
        });

        SalirCompleto.setBackground(new java.awt.Color(255, 255, 255));
        SalirCompleto.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        SalirCompleto.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirCompleto.text_1")); // NOI18N
        SalirCompleto.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.SalirCompleto.toolTipText_1")); // NOI18N
        SalirCompleto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCompletoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

        refrescar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.refrescar.text_1")); // NOI18N
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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(refrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(refrescar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mantenimiento.setBackground(new java.awt.Color(255, 255, 255));
        mantenimiento.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        mantenimiento.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.mantenimiento.text_1")); // NOI18N
        mantenimiento.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.mantenimiento.toolTipText_1")); // NOI18N
        mantenimiento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mantenimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantenimientoActionPerformed(evt);
            }
        });

        tercerizados.setBackground(new java.awt.Color(255, 255, 255));
        tercerizados.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        tercerizados.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.tercerizados.text_1")); // NOI18N
        tercerizados.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.tercerizados.toolTipText_1")); // NOI18N
        tercerizados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tercerizados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tercerizadosActionPerformed(evt);
            }
        });

        novedades.setBackground(new java.awt.Color(255, 255, 255));
        novedades.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        novedades.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.novedades.text_1")); // NOI18N
        novedades.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.novedades.toolTipText_1")); // NOI18N
        novedades.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        novedades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novedadesActionPerformed(evt);
            }
        });

        finalizar.setBackground(new java.awt.Color(255, 255, 255));
        finalizar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        finalizar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.finalizar.text_1")); // NOI18N
        finalizar.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.finalizar.toolTipText_1")); // NOI18N
        finalizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarActionPerformed(evt);
            }
        });

        aprobar.setBackground(new java.awt.Color(255, 255, 255));
        aprobar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        aprobar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.aprobar.text_1")); // NOI18N
        aprobar.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.aprobar.toolTipText_1")); // NOI18N
        aprobar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aprobarActionPerformed(evt);
            }
        });

        iniciar.setBackground(new java.awt.Color(255, 255, 255));
        iniciar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        iniciar.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.iniciar.text_1")); // NOI18N
        iniciar.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.iniciar.toolTipText_1")); // NOI18N
        iniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarActionPerformed(evt);
            }
        });

        detalle_tareas.setBackground(new java.awt.Color(255, 255, 255));
        detalle_tareas.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        detalle_tareas.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.detalle_tareas.text_1")); // NOI18N
        detalle_tareas.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.detalle_tareas.toolTipText_1")); // NOI18N
        detalle_tareas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        detalle_tareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detalle_tareasActionPerformed(evt);
            }
        });

        EtiquetaQr.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.EtiquetaQr.text")); // NOI18N

        entregaprevista.setBackground(new java.awt.Color(255, 255, 255));
        entregaprevista.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        entregaprevista.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.entregaprevista.text")); // NOI18N
        entregaprevista.setToolTipText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.entregaprevista.toolTipText")); // NOI18N
        entregaprevista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        entregaprevista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entregaprevistaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(EtiquetaQr)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(entregaprevista, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SalirCompleto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(finalizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(novedades, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(iniciar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(aprobar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(detalle_tareas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tercerizados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Eliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                .addComponent(mantenimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 10, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(Modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addComponent(Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(Listar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mantenimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tercerizados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detalle_tareas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aprobar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entregaprevista)
                .addGap(7, 7, 7)
                .addComponent(novedades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finalizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SalirCompleto)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(EtiquetaQr))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52))
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

        jMenu1.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jMenu1.text_1")); // NOI18N

        jMenuItem1.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jMenuItem1.text")); // NOI18N
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText(org.openide.util.NbBundle.getMessage(orden_trabajos.class, "orden_trabajos.jMenuItem2.text")); // NOI18N
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Qr() {
        EtiquetaQr.setText("");
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        String cNumero = this.jTable1.getValueAt(nFila, 0).toString();
        String cFecha = this.jTable1.getValueAt(nFila, 1).toString();
        String cSeccion = this.jTable1.getValueAt(nFila, 3).toString();
        String cTrabajo = this.jTable1.getValueAt(nFila, 4).toString();
        String cSolicitud = this.jTable1.getValueAt(nFila, 5).toString();
        generarQR generaQR = new generarQR();
        BufferedImage imagen;
        try {
            imagen = generaQR.crearQR("N° OT " + cNumero + " Emisión " + cFecha + " Sección: " + cSeccion + "Trabajo a Realizar " + cTrabajo + " Solicitado por " + cSolicitud, 80, 80);
            ImageIcon icono = new ImageIcon(imagen);
            EtiquetaQr.setIcon(icono);
        } catch (WriterException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    private void TitSeccion() {
        modeloseccion.addColumn("Código");
        modeloseccion.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloseccion.getColumnCount(); i++) {
            tablaseccion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaseccion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaseccion.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaseccion.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaseccion.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtroseccion(int nNumeroColumna) {
        trsfiltroseccion.setRowFilter(RowFilter.regexFilter(jTBuscarSeccion.getText(), nNumeroColumna));
    }

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
        this.fechahoy.setCalendar(c2);
    }

    public void filtroempleado(int nNumeroColumna) {
        trsfiltroempleado.setRowFilter(RowFilter.regexFilter(this.jTBuscarEmpleado.getText(), nNumeroColumna));
    }

    private void TitEmpleado() {
        modeloempleado.addColumn("Código");
        modeloempleado.addColumn("Nombre");
        modeloempleado.addColumn("Tipo");
        modeloempleado.addColumn("Salario");

        int[] anchos = {90, 150, 1, 1};
        for (int i = 0; i < modeloempleado.getColumnCount(); i++) {
            tablaempleado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaempleado.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaempleado.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaempleado.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaempleado.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);

        this.tablaempleado.getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablaempleado.getColumnModel().getColumn(2).setMinWidth(0);
        this.tablaempleado.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablaempleado.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);

        this.tablaempleado.getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablaempleado.getColumnModel().getColumn(3).setMinWidth(0);
        this.tablaempleado.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablaempleado.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);

    }

    public void limpiarCombos() {

    }

    private void jComboBox1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText());
                buscarcadena.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 0;
                        break;//por factura
                    case 1:
                        indiceColumnaTabla = 2;
                        break;//por sucursal
                    case 2:
                        indiceColumnaTabla = 3;
                        break;//por nombre del cliente
                    case 3:
                        indiceColumnaTabla = 4;
                        break;//por nombre del cliente
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void SalirCompletoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

    public void contaritemtareas() {
        int totalRow = this.tabladetalletarea.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            this.tabladetalletarea.setValueAt(i + 1, i, 0);
        }

        int cantFilas = tabladetalletarea.getRowCount();
        if (cantFilas > 0) {
            delitemtarea.setEnabled(true);
            editaritemtarea.setEnabled(true);
        } else {
            delitemtarea.setEnabled(false);
            editaritemtarea.setEnabled(false);
        }

    }

    public void contaritemterceros() {
        int totalRow = this.tabladetalletercero.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            this.tabladetalletercero.setValueAt(i + 1, i, 0);
        }
        int cantFilas = tabladetalletercero.getRowCount();
        if (cantFilas > 0) {
            delitemtercero.setEnabled(true);
            editaritemtercero.setEnabled(true);
        } else {
            delitemtercero.setEnabled(false);
            editaritemtercero.setEnabled(false);
        }
    }

    public void sumatoria() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA
        double sumtotal = 0.00;
        double sumatoria = 0.00;
        String cValorImporte = "";
        int totalRow = this.tabladetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //Primero capturamos el porcentaje del IVA
            this.tabladetalle.setValueAt(i + 1, i, 0);
            //Luego capturamos el importe de la celda total del item
            cValorImporte = String.valueOf(this.tabladetalle.getValueAt(i, 6));
            cValorImporte = cValorImporte.replace(".", "").replace(",", ".");
            sumatoria = Double.valueOf(cValorImporte);
            sumtotal += sumatoria;
            //Calculamos el total de exentos
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
        this.totalcosto.setText(formatea.format(sumtotal));
        //formato.format(sumatoria1);
    }


    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        idControl.setText("0");
        modo.setText("1");
        this.limpiarOT();

        //Establecemos un título para el jDialog
        detalle_orden_trabajo.setTitle("Generar Nueva OT");
        detalle_orden_trabajo.setModal(true);
        detalle_orden_trabajo.setSize(563, 540);
        detalle_orden_trabajo.setLocationRelativeTo(null);
        detalle_orden_trabajo.setVisible(true);
        //Establecemos un título para el jDialog
        fechaemision.requestFocus();
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiarOT() {
        this.numero.setText("0");
        this.sucursal.setText("0");
        this.nombresucursal.setText("");
        this.fechaemision.setCalendar(c2);
        this.empleado.setText("0");
        this.nombreempleado.setText("");
        this.seccion.setText("0");
        this.nombreseccion.setText("");
        this.galpon.setText("");
        this.tipo.setSelectedIndex(0);
        this.trabajoarealizar.setText("");
    }
    private void jTable1KeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
//      this.Qr();
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        this.limpiarOT();
        modo.setText("2");
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {

            nFila = this.jTable1.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nFila = this.jTable1.getSelectedRow();
            this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
            orden_trabajoDAO otDAO = new orden_trabajoDAO();
            orden_trabajo otr = null;
            try {
                otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (otr != null) {
                //SE CARGAN LOS DATOS DE LA CABECERA
                numero.setText(formatosinpunto.format(otr.getNumero()));
                fechaemision.setDate(otr.getFechaemision());
                sucursal.setText(String.valueOf(otr.getSucursal().getCodigo()));
                nombresucursal.setText(otr.getSucursal().getNombre());
                empleado.setText(String.valueOf(otr.getSolicitadopor().getCodigo()));
                nombreempleado.setText(String.valueOf(otr.getSolicitadopor().getNombreempleado()));
                seccion.setText(String.valueOf(otr.getSeccion().getCodigo()));
                nombreseccion.setText(otr.getSeccion().getNombre());
                galpon.setText(otr.getGalpon());
                tipo.setSelectedItem(otr.getTipo());
                trabajoarealizar.setText(otr.getTrabajoarealizar());

                //Establecemos un título para el jDialog
                detalle_orden_trabajo.setTitle("Modificar OT");
                detalle_orden_trabajo.setModal(true);
                detalle_orden_trabajo.setSize(563, 540);
                detalle_orden_trabajo.setLocationRelativeTo(null);
                detalle_orden_trabajo.setVisible(true);
                //Establecemos un título para el jDialog
                fechaemision.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void jTable1FocusGained(FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusGained

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

        /*      if (Integer.valueOf(Config.cNivelUsuario) == 1) {

            int nFila = jTable1.getSelectedRow();
            String num = jTable1.getValueAt(nFila, 0).toString();

            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                cabecera_compraDAO vl = new cabecera_compraDAO();
                detalle_compraDAO det = new detalle_compraDAO();
                cuenta_proveedoresDAO cl = new cuenta_proveedoresDAO();
                try {
                    cabecera_compra vt = vl.buscarId(num);
                    if (vt == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        vl.borrarDetalleCuenta(num);
                        det.borrarDetallecompra(num);
                        vl.borrarCompra(num);
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
            GrillaCompra GrillaOC = new GrillaCompra();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }*/

    }//GEN-LAST:event_EliminarActionPerformed

    private void jTable1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_orden_mantenimiento.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarDetalleMantenimientoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarDetalleMantenimientoActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito
        String cTotalCosto = this.totalcosto.getText();
        cTotalCosto = cTotalCosto.replace(".", "").replace(",", ".");

        if (Double.valueOf(cTotalCosto) == 0) {
            JOptionPane.showMessageDialog(null, "No Existen Productos Selecccionados");
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            orden_trabajo ot = new orden_trabajo();
            orden_trabajoDAO grabarot = new orden_trabajoDAO();
            //Capturamos los Valores BigDecimal

            ot.setNumero(Double.valueOf(this.numerodetalle.getText()));
            ot.setTotalpresupuesto(Double.valueOf(cTotalCosto));

            String cProducto = null;
            String cCantidad = null;
            String cCosto = null;
            String cDisponible = null;
            String cTotalItem = null;

            int totalRow = modelodetalle.getRowCount();
            totalRow -= 1;

            String detalle = "[";
            for (int i = 0; i <= (totalRow); i++) {

                cCosto = modelodetalle.getValueAt(i, 5).toString();
                cCosto = cCosto.replace(".", "").replace(",", ".");
                cDisponible = modelodetalle.getValueAt(i, 4).toString();
                cDisponible = cDisponible.replace(".", "").replace(",", ".");
                cCantidad = modelodetalle.getValueAt(i, 3).toString();
                cCantidad = cCantidad.replace(".", "").replace(",", ".");
                cTotalItem = modelodetalle.getValueAt(i, 6).toString();
                cTotalItem = cTotalItem.replace(".", "").replace(",", ".");

                String linea = "{dnumero : " + this.numerodetalle.getText() + ","
                        + "item : " + modelodetalle.getValueAt(i, 0).toString() + ","
                        + "codprod : '" + modelodetalle.getValueAt(i, 1).toString() + "',"
                        + "cantidad : " + cCantidad + ","
                        + "costo : " + cCosto + ","
                        + "disponible : " + cDisponible + ","
                        + "total: " + cTotalItem + ","
                        + "potrero: '" + modelodetalle.getValueAt(i, 7).toString()
                        + "'},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            System.out.println(detalle);
            try {
              //  detalle_orden_trabajoDAO borrardetalleDAO = new detalle_orden_trabajoDAO();
              //  borrardetalleDAO.borrarDetalleMercaderiaOT(Double.valueOf(this.numerodetalle.getText()));
                grabarot.ActualizarOTMantenimiento(ot, detalle);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            detalle_orden_mantenimiento.setModal(false);
            detalle_orden_mantenimiento.setVisible(false);
            this.refrescar.doClick();
        }
    }//GEN-LAST:event_GrabarDetalleMantenimientoActionPerformed

    private void detalle_orden_mantenimientoFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_orden_mantenimientoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_mantenimientoFocusGained

    private void detalle_orden_mantenimientoWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_orden_mantenimientoWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_mantenimientoWindowGainedFocus

    private void detalle_orden_mantenimientoWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_orden_mantenimientoWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_mantenimientoWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void refrescarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaOT GrillaOC = new GrillaOT();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();        // TODO add your handling code here:
    }//GEN-LAST:event_refrescarActionPerformed

    private void sucursalKeyPressed(KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.numerodetalle.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void fechaemisiondetalleFocusGained(FocusEvent evt) {//GEN-FIRST:event_fechaemisiondetalleFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaemisiondetalleFocusGained

    private void fechaemisiondetalleKeyPressed(KeyEvent evt) {//GEN-FIRST:event_fechaemisiondetalleKeyPressed
        // TODO add your handling code here:*/
    }//GEN-LAST:event_fechaemisiondetalleKeyPressed

    private void buscarSucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        numerodetalle.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void sucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void comboproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproveedorActionPerformed

    private void jTBuscarproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarproveedorKeyPressed
        this.jTBuscarproveedor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarproveedor.getText()).toUpperCase();
                jTBuscarproveedor.setText(cadena);
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
                filtrocli(indiceColumnaTabla);
            }
        });
        trsfiltropro = new TableRowSorter(tablaproveedor.getModel());
        tablaproveedor.setRowSorter(trsfiltropro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarproveedorKeyPressed

    private void tablaproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptarprov.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorKeyPressed

    private void AceptarprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarprovActionPerformed
        int nFila = this.tablaproveedor.getSelectedRow();
        this.solicitadopordetalle.setText(this.tablaproveedor.getValueAt(nFila, 1).toString());
        this.estadodetalle.setText(this.tablaproveedor.getValueAt(nFila, 2).toString());
        this.BProveedor.setVisible(false);
        this.jTBuscarproveedor.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarprovActionPerformed

    private void SalirprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirprovActionPerformed
        this.BProveedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirprovActionPerformed

    private void jTBuscarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarproveedorActionPerformed

    private void tablaproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproveedorMouseClicked
        this.Aceptarprov.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorMouseClicked

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");
        this.fechaemisiondetalle.requestFocus();
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

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

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
                BProducto.setModal(true);
                BProducto.setSize(500, 575);
                BProducto.setLocationRelativeTo(null);
                BProducto.setTitle("Buscar Producto");
                BProducto.setVisible(true);
            } else {
                nombreproducto.setText(pro.getNombre().trim());
                costo.setText(formatea.format(pro.getCosto()));
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        this.potrero.requestFocus();        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarProductoActionPerformed

    private void cantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadFocusGained
        stockDAO stkDAO = new stockDAO();
        stock stk = null;

        try {
            stk = stkDAO.BuscarStockAgrupado(this.codprod.getText());
            if (stk != null) {
                disponible.setText(formatea.format(stk.getStock()));
            } else {
                disponible.setText("0");
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        cantidad.selectAll();
    }//GEN-LAST:event_cantidadFocusGained

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.costo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.potrero.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void costoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costoActionPerformed
        String cCantidad = this.cantidad.getText();
        String cCosto = this.costo.getText();
        cCantidad = cCantidad.replace(",", ".");
        cCosto = cCosto.replace(".", "").replace(",", ".");
        this.totalitem.setText(formatea.format(Double.valueOf(cCosto) * Double.valueOf(cCantidad)));
    }//GEN-LAST:event_costoActionPerformed

    private void costoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_costoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.GrabarItem.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_costoKeyPressed

    private void GrabarItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_GrabarItemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemFocusGained

    private void GrabarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemActionPerformed
        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[8];
            fila[1] = this.codprod.getText();
            fila[2] = this.nombreproducto.getText();
            fila[3] = this.cantidad.getText();
            fila[4] = this.disponible.getText();
            fila[5] = this.costo.getText();
            fila[6] = this.totalitem.getText();
            fila[7] = this.potrero.getText();
            modelodetalle.addRow(fila);
            this.codprod.requestFocus();
        } else {
            this.tabladetalle.setValueAt(this.codprod.getText(), nFila, 1);
            this.tabladetalle.setValueAt(this.nombreproducto.getText(), nFila, 2);
            this.tabladetalle.setValueAt(this.cantidad.getText(), nFila, 3);
            this.tabladetalle.setValueAt(this.disponible.getText(), nFila, 4);
            this.tabladetalle.setValueAt(this.costo.getText(), nFila, 5);
            this.tabladetalle.setValueAt(this.totalitem.getText(), nFila, 6);
            this.tabladetalle.setValueAt(this.potrero.getText(), nFila, 7);
            nFila = 0;
            this.SalirItem.doClick();
        }
        this.limpiaritems();
        this.sumatoria();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemActionPerformed

    private void SalirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        itemdetalleorden.setModal(false);
        itemdetalleorden.setVisible(false);
        this.detalle_orden_mantenimiento.setModal(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void nuevoitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoitemActionPerformed
        itemdetalleorden.setSize(453, 310);
        itemdetalleorden.setLocationRelativeTo(null);
        this.limpiaritems();
        this.jTBuscarProducto.setText("0");
        this.GrabarItem.setText("Agregar"); //jTBuscarProducto
        this.cModo.setText("");
        itemdetalleorden.setModal(true);
        itemdetalleorden.setVisible(true);
        codprod.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoitemActionPerformed

    private void costoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_costoFocusGained
        costo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_costoFocusGained

    private void editaritemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editaritemActionPerformed
        nFila = this.tabladetalle.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        itemdetalleorden.setSize(453, 310);
        itemdetalleorden.setLocationRelativeTo(null);
        cModo.setText(String.valueOf(nFila));
        codprod.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 1).toString());
        nombreproducto.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 2).toString());
        cantidad.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 3).toString());
        costo.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 5).toString());
        totalitem.setText(tabladetalle.getValueAt(tabladetalle.getSelectedRow(), 6).toString());
        itemdetalleorden.setModal(true);
        itemdetalleorden.setVisible(true);
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

    private void costoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_costoFocusLost
        String cCantidad = this.cantidad.getText();
        String cCosto = this.costo.getText();
        cCantidad = cCantidad.replace(",", ".");
        cCosto = cCosto.replace(".", "").replace(",", ".");
        this.totalitem.setText(formatea.format(Double.valueOf(cCosto) * Double.valueOf(cCantidad)));
        // TODO add your handling code here:
    }//GEN-LAST:event_costoFocusLost

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


    }//GEN-LAST:event_tablaproductoKeyPressed

    private void AceptarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProductoActionPerformed
        int nFila = this.tablaproducto.getSelectedRow();
        this.codprod.setText(this.tablaproducto.getValueAt(nFila, 0).toString());
        this.nombreproducto.setText(this.tablaproducto.getValueAt(nFila, 1).toString());
        this.costo.setText(this.tablaproducto.getValueAt(nFila, 2).toString());

        this.BProducto.setVisible(false);
        this.jTBuscarProducto.setText("");
        this.potrero.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProductoActionPerformed

    private void SalirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProductoActionPerformed
        this.BProducto.setVisible(false);
        this.codprod.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProductoActionPerformed

    private void numerodetalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numerodetalleKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechaemisiondetalle.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_numerodetalleKeyPressed

    private void numerodetalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numerodetalleKeyReleased
    }//GEN-LAST:event_numerodetalleKeyReleased

    private void buscarSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarSucursalMouseClicked

    }//GEN-LAST:event_buscarSucursalMouseClicked

    private void sucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyReleased

    private void nuevoitemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nuevoitemKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.GrabarDetalleMantenimiento.requestFocus();
        }
    }//GEN-LAST:event_nuevoitemKeyPressed

    private void GrabarDetalleMantenimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GrabarDetalleMantenimientoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarDetalleMantenimientoKeyPressed

    private void AgregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AgregarKeyPressed
        this.limpiarOT();
    }//GEN-LAST:event_AgregarKeyPressed

    private void SalirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SalirKeyPressed
        this.limpiarOT();
    }//GEN-LAST:event_SalirKeyPressed

    private void numerodetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numerodetalleActionPerformed

    }//GEN-LAST:event_numerodetalleActionPerformed

    private void SalirItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SalirItemKeyPressed
        this.GrabarDetalleMantenimiento.requestFocus();
    }//GEN-LAST:event_SalirItemKeyPressed

    private void SalirItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirItemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemMouseClicked

    private void aprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aprobarActionPerformed
        nFila = this.jTable1.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Aprobar la Ejecución de la OT ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {

                //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
                int nFila = this.jTable1.getSelectedRow();
                this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
                orden_trabajoDAO otDAO = new orden_trabajoDAO();
                orden_trabajo otr = null;
                try {
                    otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
                }
                otr.setAprobado("SI");
                otr.setNumero(Double.valueOf(this.idControl.getText()));
                try {
                    otDAO.ActualizarOTAprobado(otr);
                    JOptionPane.showMessageDialog(null, "Presupuesto Aprobado");
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                    JOptionPane.showMessageDialog(null, "Presupuesto no fue Aprobado");
                }
            }
            this.refrescar.doClick();
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_aprobarActionPerformed

    private void iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        String cAprobado = this.jTable1.getValueAt(nFila, 6).toString();
        if (cAprobado.equals("NO")) {
            JOptionPane.showMessageDialog(null,
                    "No puede Iniciar Tareas, presupuesto no Aprobado ");
            return;
        }

        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Iniciar la Ejecución de la OT ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                Date dFechaHoy = ODate.de_java_a_sql(fechahoy.getDate());

                orden_trabajoDAO otDAO = new orden_trabajoDAO();
                orden_trabajo otr = null;
                try {
                    otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
                }
                otr.setFechainicio(dFechaHoy);
                otr.setNumero(Double.valueOf(this.idControl.getText()));
                otr.setEstado("TAREA INICIADA");
                try {
                    otDAO.ActualizarOTInicio(otr);
                    JOptionPane.showMessageDialog(null, "Se ha iniciado la Ejecución de la OT");
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                    JOptionPane.showMessageDialog(null, "No se inició la Ejecución de la OT");
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_iniciarActionPerformed

    private void mantenimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantenimientoActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        String cTipo = this.jTable1.getValueAt(nFila, 9).toString();
        orden_trabajoDAO otDAO = new orden_trabajoDAO();
        orden_trabajo otr = null;
        try {
            otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (otr != null) {
            //SE CARGAN LOS DATOS DE LA CABECERA
            numerodetalle.setText(formatosinpunto.format(otr.getNumero()));
            fechaemisiondetalle.setDate(otr.getFechaemision());
            nombresucursaldetalle.setText(String.valueOf(otr.getSucursal().getNombre()));
            solicitadopordetalle.setText(String.valueOf(otr.getSolicitadopor().getNombreempleado()));
            nombresecciondetalle.setText(String.valueOf(otr.getSeccion().getNombre()));
            observacion.setText(otr.getTrabajoarealizar());
            estadodetalle.setText(otr.getEstado());
        }
        int cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }
        detalle_orden_trabajoDAO detDAO = new detalle_orden_trabajoDAO();
        try {
            for (detalle_orden_trabajo detcom : detDAO.MostrarDetalle(Double.valueOf(this.numerodetalle.getText()))) {
                String Detalle[] = {String.valueOf(detcom.getItem()),
                    detcom.getCodprod().getCodigo(),
                    detcom.getCodprod().getNombre(), 
                    formatea.format(detcom.getCantidad()),
                    formatea.format(detcom.getDisponible()), 
                    formatea.format(detcom.getCosto()), 
                    formatea.format(detcom.getTotal()),
                    detcom.getPotrero()};
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

        detalle_orden_mantenimiento.setTitle("Ingresar Lote de Productos");
        detalle_orden_mantenimiento.setModal(true);
        detalle_orden_mantenimiento.setSize(919, 596);
        detalle_orden_mantenimiento.setLocationRelativeTo(null);
        detalle_orden_mantenimiento.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_mantenimientoActionPerformed

    private void tercerizadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tercerizadosActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        String cTipo = this.jTable1.getValueAt(nFila, 9).toString();
        orden_trabajoDAO otDAO = new orden_trabajoDAO();
        orden_trabajo otr = null;
        try {
            otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (otr != null) {
            //SE CARGAN LOS DATOS DE LA CABECERA
            numerotercero.setText(formatosinpunto.format(otr.getNumero()));
            fechaemisiontercero.setDate(otr.getFechaemision());
            nombresucursaltercero.setText(String.valueOf(otr.getSucursal().getNombre()));
            solicitadoportercero.setText(String.valueOf(otr.getSolicitadopor().getNombreempleado()));
            nombresecciontercero.setText(String.valueOf(otr.getSeccion().getNombre()));
            observaciontercero.setText(otr.getTrabajoarealizar());
            estadotercero.setText(otr.getEstado());
        }
        int cantidadRegistro = modelodetalletercero.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalletercero.removeRow(0);
        }
        detalle_propuesta_terceroDAO detDAO = new detalle_propuesta_terceroDAO();
        try {
            for (detalle_propuesta_tercero detcom : detDAO.MostrarDetalle(Double.valueOf(this.numerotercero.getText()))) {
                String Detalle[] = {String.valueOf(detcom.getItem()), detcom.getProveedor(), formatea.format(detcom.getPresupuestado()), detcom.getAprobado()};
                modelodetalletercero.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        int cantFilas = tabladetalletercero.getRowCount();
        if (cantFilas > 0) {
            editaritemtercero.setEnabled(true);
            delitemtercero.setEnabled(true);
        } else {
            editaritemtercero.setEnabled(false);
            delitemtercero.setEnabled(false);
        }

        detalle_orden_tercero.setTitle("Ingresar Propuestas Tercerizadas");
        detalle_orden_tercero.setModal(true);
        detalle_orden_tercero.setSize(919, 596);
        detalle_orden_tercero.setLocationRelativeTo(null);
        detalle_orden_tercero.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_tercerizadosActionPerformed

    private void novedadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novedadesActionPerformed
        this.limpiarOT();
        modo.setText("2");

        nFila = this.jTable1.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        orden_trabajoDAO otDAO = new orden_trabajoDAO();
        orden_trabajo otr = null;
        try {
            otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (otr != null) {
            //SE CARGAN LOS DATOS DE LA CABECERA
            //Establecemos un título para el jDialog
            personal.setTitle("Asignar personal para tareas° " + this.idControl.getText());
            this.etiquetapersonal.setText("ASIGNAR PERSONALES A LA OT N° " + this.idControl.getText());
            //Establecemos un título para el jDialog
        }
        int cantidadRegistro = modelopersonal.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelopersonal.removeRow(0);
        }
        detalle_funcionarios_otDAO detfunDAO = new detalle_funcionarios_otDAO();
        try {
            for (detalle_funcionarios_ot detfun : detfunDAO.MostrarDetalle(Double.valueOf(this.idControl.getText()))) {
                String Detalle[] = {String.valueOf(detfun.getItem()), String.valueOf(detfun.getEmpleado().getCodigo()), detfun.getEmpleado().getNombreempleado()};
                modelopersonal.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        int cantFilas = tablapersonalasignado.getRowCount();
        if (cantFilas > 0) {
            EliminarPersonal.setEnabled(true);
        } else {
            EliminarPersonal.setEnabled(false);
        }
        personal.setModal(true);
        personal.setSize(551, 540);
        personal.setLocationRelativeTo(null);
        personal.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_novedadesActionPerformed

    private void finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarActionPerformed
        int nFila = this.jTable1.getSelectedRow();

        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,"Debe seleccionar una fila de la tabla");
            return;
        }
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Finalizar la Ejecución de la OT ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                Date dFechaHoy = ODate.de_java_a_sql(fechahoy.getDate());

                orden_trabajoDAO otDAO = new orden_trabajoDAO();
                orden_trabajo otr = null;
                try {
                    otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
                }
                otr.setNumero(Double.valueOf(this.idControl.getText()));
                otr.setFechaentrega(dFechaHoy);;
                otr.setEstado("TAREA FINALIZADA");
                try {
                    otDAO.ActualizarOTFinal(otr);
                    JOptionPane.showMessageDialog(null, "Se ha Finalizado la Ejecución de la OT");
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                    JOptionPane.showMessageDialog(null, "No se finalizó la Ejecución de la OT");
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_finalizarActionPerformed

    private void GrabarOtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarOtActionPerformed

        if (this.empleado.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código de Empleado");
            this.empleado.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            // Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            Date Fecha = ODate.de_java_a_sql(fechaemision.getDate());
            orden_trabajoDAO grabarOT = new orden_trabajoDAO();
            orden_trabajo otr = new orden_trabajo();

            ficha_empleadoDAO fichaDAO = new ficha_empleadoDAO();
            ficha_empleado ficha = null;

            seccionDAO secDAO = new seccionDAO();
            seccion sec = null;

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;

            usuarioDAO usuDAO = new usuarioDAO();
            usuario usu = null;

            try {
                ficha = fichaDAO.buscarId(Integer.valueOf(this.empleado.getText()));
                sec = secDAO.buscarId(Integer.valueOf(this.seccion.getText()));
                suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                usu = usuDAO.buscarId(Integer.valueOf(Config.CodUsuario));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            //CAPTURAMOS LOS DATOS DE LA CABECERA
            otr.setNumero(Double.valueOf(this.numero.getText()));
            otr.setFechaemision(Fecha);
            otr.setSolicitadopor(ficha);
            otr.setSucursal(suc);
            otr.setSeccion(sec);
            otr.setCodusuario(usu);
            otr.setGalpon(galpon.getText());
            otr.setTipo(tipo.getSelectedItem().toString());
            otr.setTrabajoarealizar(trabajoarealizar.getText());

            //EMPEZAMOS A CAPTURAR DATOS NUMERICOS
            if (Double.valueOf(this.numero.getText()) == 0) {
                try {
                    grabarOT.insertarOT(otr);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            } else {
                //Actualizar
                try {
                    otr.setNumero(Integer.valueOf(this.numero.getText()));
                    grabarOT.actualizarOT(otr);
                    JOptionPane.showMessageDialog(null, "Datos Actualizados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            }
            detalle_orden_trabajo.setVisible(false);
            this.detalle_orden_trabajo.setModal(false);
            this.refrescar.doClick();
        }
    }//GEN-LAST:event_GrabarOtActionPerformed

    private void SalirOtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirOtActionPerformed
        detalle_orden_trabajo.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirOtActionPerformed

    private void empleadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_empleadoFocusGained
        empleado.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoFocusGained

    private void empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empleadoActionPerformed
        this.buscarEmpleado.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoActionPerformed

    private void empleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empleadoKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoKeyPressed

    private void buscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarEmpleadoActionPerformed
        nSeleccionPersonal = 1;
        ficha_empleadoDAO fichaDAO = new ficha_empleadoDAO();
        ficha_empleado ficha = null;
        try {
            ficha = fichaDAO.buscarId(Integer.valueOf(this.empleado.getText()));
            if (ficha.getCodigo() == 0) {
                BEmpleado.setModal(true);
                BEmpleado.setSize(482, 575);
                BEmpleado.setLocationRelativeTo(null);
                BEmpleado.setTitle("Buscar Empleado");
                BEmpleado.setVisible(true);
                BEmpleado.setModal(false);
            } else {
                nombreempleado.setText(ficha.getNombreempleado());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        nSeleccionPersonal = 0;
        seccion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarEmpleadoActionPerformed

    private void numeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numeroFocusGained
        numero.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroFocusGained

    private void numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroActionPerformed

    }//GEN-LAST:event_numeroActionPerformed

    private void numeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyPressed

    private void numeroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyReleased

    private void fechaemisionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaemisionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaemisionFocusGained

    private void fechaemisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaemisionKeyPressed

    }//GEN-LAST:event_fechaemisionKeyPressed

    private void galponFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_galponFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_galponFocusGained

    private void galponFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_galponFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_galponFocusLost

    private void galponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_galponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_galponActionPerformed

    private void galponKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_galponKeyPressed
    }//GEN-LAST:event_galponKeyPressed

    private void galponKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_galponKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_galponKeyReleased

    private void seccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_seccionFocusGained
        seccion.selectAll();
    }//GEN-LAST:event_seccionFocusGained

    private void seccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seccionActionPerformed
        BuscarSeccion.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_seccionActionPerformed

    private void seccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_seccionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!seccion.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                galpon.setEnabled(true);
                galpon.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Motivo Ausencia", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_seccionKeyPressed

    private void BuscarSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarSeccionActionPerformed
        seccionDAO seccDAO = new seccionDAO();
        seccion secc = null;
        try {
            secc = seccDAO.buscarId(Integer.valueOf(this.seccion.getText()));
            if (secc.getCodigo() == 0) {
                BSeccion.setModal(true);
                BSeccion.setSize(500, 575);
                BSeccion.setLocationRelativeTo(null);
                BSeccion.setTitle("Buscar Sección");
                BSeccion.setVisible(true);
                //                giraduria.requestFocus();
                BSeccion.setModal(false);
            } else {
                nombreseccion.setText(secc.getNombre());
                //Establecemos un título para el jDialog
            }
            galpon.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_BuscarSeccionActionPerformed

    private void nombreseccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreseccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreseccionKeyPressed

    private void nombreempleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreempleadoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreempleadoKeyPressed

    private void detalle_orden_trabajoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_detalle_orden_trabajoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_trabajoFocusGained

    private void detalle_orden_trabajoWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_orden_trabajoWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_trabajoWindowGainedFocus

    private void detalle_orden_trabajoWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_orden_trabajoWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_trabajoWindowActivated

    private void comboempleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboempleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboempleadoActionPerformed

    private void jTBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarEmpleadoActionPerformed

    private void jTBuscarEmpleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarEmpleadoKeyPressed
        this.jTBuscarEmpleado.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarEmpleado.getText()).toUpperCase();
                jTBuscarEmpleado.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboempleado.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtroempleado(indiceColumnaTabla);
            }
        });
        trsfiltroempleado = new TableRowSorter(tablaempleado.getModel());
        tablaempleado.setRowSorter(trsfiltroempleado);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarEmpleadoKeyPressed

    private void tablaempleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaempleadoMouseClicked
        this.AceptarEmpleado.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaempleadoMouseClicked

    private void tablaempleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaempleadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarEmpleado.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaempleadoKeyPressed

    private void AceptarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarEmpleadoActionPerformed
        int nFila = this.tablaempleado.getSelectedRow();
        if (nSeleccionPersonal == 1) {
            this.empleado.setText(this.tablaempleado.getValueAt(nFila, 0).toString());
            this.nombreempleado.setText(this.tablaempleado.getValueAt(nFila, 1).toString());
            this.BEmpleado.setVisible(false);
            this.jTBuscarEmpleado.setText("");
            this.seccion.requestFocus();
        } else {
            this.personalasignado.setText(this.tablaempleado.getValueAt(nFila, 0).toString());
            this.nombrepersonalasignado.setText(this.tablaempleado.getValueAt(nFila, 1).toString());
            this.BEmpleado.setVisible(false);
            this.jTBuscarEmpleado.setText("");
            this.GrabarPersonal.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarEmpleadoActionPerformed

    private void SalirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirEmpleadoActionPerformed
        this.BEmpleado.setModal(true);
        this.BEmpleado.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirEmpleadoActionPerformed

    private void comboseccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboseccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboseccionActionPerformed

    private void jTBuscarSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarSeccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSeccionActionPerformed

    private void jTBuscarSeccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarSeccionKeyPressed
        this.jTBuscarSeccion.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarSeccion.getText()).toUpperCase();
                jTBuscarSeccion.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboseccion.getSelectedIndex()) {
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
                filtroseccion(indiceColumnaTabla);
            }
        });
        trsfiltroseccion = new TableRowSorter(tablaseccion.getModel());
        tablaseccion.setRowSorter(trsfiltroseccion);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSeccionKeyPressed

    private void tablaseccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaseccionMouseClicked
        this.AceptarSecc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaseccionMouseClicked

    private void tablaseccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaseccionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSecc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaseccionKeyPressed

    private void AceptarSeccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSeccActionPerformed
        int nFila = this.tablaseccion.getSelectedRow();
        this.seccion.setText(this.tablaseccion.getValueAt(nFila, 0).toString());
        this.nombreseccion.setText(this.tablaseccion.getValueAt(nFila, 1).toString());

        this.BSeccion.setVisible(false);
        this.jTBuscarSeccion.setText("");
        this.galpon.requestFocus();
    }//GEN-LAST:event_AceptarSeccActionPerformed

    private void SalirSeccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSeccActionPerformed
        this.BSeccion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSeccActionPerformed

    private void SalirItemTerceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemTerceroActionPerformed
        this.itemterceros.setModal(false);
        this.itemterceros.setVisible(false);
        this.detalle_orden_tercero.setModal(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemTerceroActionPerformed

    private void GrabarItemTerceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemTerceroActionPerformed
        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[4];
            fila[1] = this.proveedortercerizado.getText();
            fila[2] = this.presupuestotercero.getText();
            if (aprobarpresupuesto.isSelected()) {
                fila[3] = "SI";
            } else {
                fila[3] = "NO";
            }
            modelodetalletercero.addRow(fila);
            this.proveedortercerizado.requestFocus();
        } else {
            this.tabladetalletercero.setValueAt(this.proveedortercerizado.getText(), nFila, 1);
            this.tabladetalletercero.setValueAt(this.presupuestotercero.getText(), nFila, 2);
            if (aprobarpresupuesto.isSelected()) {
                this.tabladetalletercero.setValueAt("SI", nFila, 3);
            } else {
                this.tabladetalletercero.setValueAt("NO", nFila, 3);
            }
            nFila = 0;
            this.SalirItemTercero.doClick();
        }
        this.contaritemterceros();
        this.limpiaritemterceros();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemTerceroActionPerformed

    private void proveedortercerizadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_proveedortercerizadoFocusLost
        String letras = ConvertirMayusculas.cadena(proveedortercerizado);
        proveedortercerizado.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedortercerizadoFocusLost

    private void proveedortercerizadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedortercerizadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.presupuestotercero.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedortercerizadoKeyPressed

    private void proveedortercerizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedortercerizadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedortercerizadoActionPerformed

    private void presupuestoterceroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_presupuestoterceroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.aprobarpresupuesto.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.proveedortercerizado.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_presupuestoterceroKeyPressed

    private void detalle_tareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detalle_tareasActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        orden_trabajoDAO otDAO = new orden_trabajoDAO();
        orden_trabajo otr = null;
        try {
            otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (otr != null) {
            //SE CARGAN LOS DATOS DE LA CABECERA
            numerotarea.setText(formatosinpunto.format(otr.getNumero()));
            fechaemisiontarea.setDate(otr.getFechaemision());
            nombresucursaltarea.setText(String.valueOf(otr.getSucursal().getNombre()));
            solicitadoportarea.setText(String.valueOf(otr.getSolicitadopor().getNombreempleado()));
            nombresecciontarea.setText(String.valueOf(otr.getSeccion().getNombre()));
            observaciontarea.setText(otr.getTrabajoarealizar());
            estadotarea.setText(otr.getEstado());
        }
        int cantidadRegistro = modelodetalletarea.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalletarea.removeRow(0);
        }
        detalle_tarea_orden_trabajoDAO detDAO = new detalle_tarea_orden_trabajoDAO();
        try {
            for (detalle_tarea_orden_trabajo detcom : detDAO.MostrarDetalle(Double.valueOf(this.numerotarea.getText()))) {
                String Detalle[] = {String.valueOf(detcom.getItem()), detcom.getDescripcion(), detcom.getEstado()};
                modelodetalletarea.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        int cantFilas = tabladetalletarea.getRowCount();
        if (cantFilas > 0) {
            editaritemtarea.setEnabled(true);
            delitemtarea.setEnabled(true);
        } else {
            editaritemtarea.setEnabled(false);
            delitemtarea.setEnabled(false);
        }
        detalle_tarea_ot.setModal(true);
        detalle_tarea_ot.setSize(919, 596);
        detalle_tarea_ot.setLocationRelativeTo(null);
        detalle_tarea_ot.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_tareasActionPerformed

    private void numerotareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numerotareaActionPerformed

    }//GEN-LAST:event_numerotareaActionPerformed

    private void numerotareaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numerotareaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechaemisiontarea.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_numerotareaKeyPressed

    private void numerotareaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numerotareaKeyReleased

    }//GEN-LAST:event_numerotareaKeyReleased

    private void fechaemisiontareaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaemisiontareaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaemisiontareaFocusGained

    private void fechaemisiontareaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaemisiontareaKeyPressed
        // TODO add your handling code here:*/
    }//GEN-LAST:event_fechaemisiontareaKeyPressed

    private void tabladetalletareaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalletareaMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalletareaMouseEntered

    private void nuevoitemtareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoitemtareaActionPerformed
        itemtareas.setSize(689, 490);
        itemtareas.setLocationRelativeTo(null);
        this.limpiartareas();
        this.GrabarItemTarea.setText("Agregar"); //jTBuscarProducto
        this.cModo.setText("");
        itemtareas.setModal(true);
        itemtareas.setVisible(true);
        descripciontarea.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoitemtareaActionPerformed

    private void nuevoitemtareaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nuevoitemtareaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.GrabarDetalleTarea.requestFocus();
        }
    }//GEN-LAST:event_nuevoitemtareaKeyPressed

    private void editaritemtareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editaritemtareaActionPerformed
        nFila = this.tabladetalletarea.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        itemtareas.setSize(689, 490);
        itemtareas.setLocationRelativeTo(null);
        this.limpiartareas();
        this.GrabarItemTercero.setText("Grabar"); //jTBuscarProducto
        cModo.setText(String.valueOf(nFila));
        cModo.setText(String.valueOf(nFila));
        descripciontarea.setText(tabladetalletarea.getValueAt(tabladetalletarea.getSelectedRow(), 1).toString());
        if (tabladetalletarea.getValueAt(tabladetalletarea.getSelectedRow(), 2).toString().equals("SI")) {
            concluirtarea.setSelected(true);
        } else {
            concluirtarea.setSelected(false);
        }
        itemtareas.setModal(true);
        itemtareas.setVisible(true);
        descripciontarea.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_editaritemtareaActionPerformed

    private void delitemtareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delitemtareaActionPerformed
        int a = this.tabladetalletarea.getSelectedRow();
        if (a < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelodetalletarea.removeRow(a);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_delitemtareaActionPerformed

    private void detalle_tarea_otFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_detalle_tarea_otFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_tarea_otFocusGained

    private void detalle_tarea_otWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_tarea_otWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_tarea_otWindowGainedFocus

    private void detalle_tarea_otWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_tarea_otWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_tarea_otWindowActivated

    private void numeroterceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroterceroActionPerformed

    }//GEN-LAST:event_numeroterceroActionPerformed

    private void numeroterceroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroterceroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechaemisiontercero.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_numeroterceroKeyPressed

    private void numeroterceroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroterceroKeyReleased

    }//GEN-LAST:event_numeroterceroKeyReleased

    private void fechaemisionterceroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaemisionterceroFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaemisionterceroFocusGained

    private void fechaemisionterceroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaemisionterceroKeyPressed
        // TODO add your handling code here:*/
    }//GEN-LAST:event_fechaemisionterceroKeyPressed

    private void tabladetalleterceroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalleterceroMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalleterceroMouseEntered

    private void nuevoitemterceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoitemterceroActionPerformed
        itemterceros.setSize(453, 290);
        itemterceros.setLocationRelativeTo(null);
        this.limpiaritemterceros();
        this.GrabarItemTercero.setText("Agregar"); //jTBuscarProducto
        this.cModo.setText("");
        itemterceros.setModal(true);
        itemterceros.setVisible(true);
        proveedortercerizado.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoitemterceroActionPerformed

    private void nuevoitemterceroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nuevoitemterceroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.GrabarDetalleTercero.requestFocus();
        }
    }//GEN-LAST:event_nuevoitemterceroKeyPressed

    private void editaritemterceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editaritemterceroActionPerformed
        nFila = this.tabladetalletercero.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        itemterceros.setSize(453, 290);
        itemterceros.setLocationRelativeTo(null);
        this.limpiaritemterceros();
        this.GrabarItemTercero.setText("Grabar"); //jTBuscarProducto
        this.cModo.setText("");
        cModo.setText(String.valueOf(nFila));
        proveedortercerizado.setText(tabladetalletercero.getValueAt(tabladetalletercero.getSelectedRow(), 1).toString());
        presupuestotercero.setText(tabladetalletercero.getValueAt(tabladetalletercero.getSelectedRow(), 2).toString());
        if (tabladetalletercero.getValueAt(tabladetalletercero.getSelectedRow(), 3).toString().equals("SI")) {
            aprobarpresupuesto.setSelected(true);
        } else {
            aprobarpresupuesto.setSelected(false);
        }
        itemterceros.setModal(true);
        itemterceros.setVisible(true);
        proveedortercerizado.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_editaritemterceroActionPerformed

    private void delitemterceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delitemterceroActionPerformed
        int a = this.tabladetalletercero.getSelectedRow();
        if (a < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelodetalletercero.removeRow(a);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
            }
        }
        this.contaritemterceros();
        // TODO add your handling code here:
    }//GEN-LAST:event_delitemterceroActionPerformed

    private void SalirTerceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirTerceroActionPerformed
        detalle_orden_tercero.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirTerceroActionPerformed

    private void SalirTerceroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SalirTerceroKeyPressed
        this.limpiarOT();
    }//GEN-LAST:event_SalirTerceroKeyPressed

    private void GrabarDetalleTerceroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarDetalleTerceroActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            orden_trabajo ot = new orden_trabajo();
            orden_trabajoDAO grabarot = new orden_trabajoDAO();
            //Capturamos los Valores BigDecimal

            ot.setNumero(Double.valueOf(this.numerotercero.getText()));

            String cPresupuesto = null;

            int totalRow = modelodetalletercero.getRowCount();
            totalRow -= 1;

            String detalle = "[";
            for (int i = 0; i <= (totalRow); i++) {

                cPresupuesto = modelodetalletercero.getValueAt(i, 2).toString();
                cPresupuesto = cPresupuesto.replace(".", "").replace(",", ".");

                if (modelodetalletercero.getValueAt(i, 3).toString().equals("SI")) {
                    ot.setTotalpresupuesto(Double.valueOf(cPresupuesto));
                } else {
                    ot.setTotalpresupuesto(0);
                }

                String linea = "{dnumero : " + this.numerotercero.getText() + ","
                        + "item : " + modelodetalletercero.getValueAt(i, 0).toString() + ","
                        + "proveedor: '" + modelodetalletercero.getValueAt(i, 1).toString() + "',"
                        + "presupuestado : " + cPresupuesto + ","
                        + "aprobado : " + modelodetalletercero.getValueAt(i, 3).toString()
                        + "},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            System.out.println(detalle);
            try {
                detalle_propuesta_terceroDAO borrardetalleDAO = new detalle_propuesta_terceroDAO();
                borrardetalleDAO.borrarDetallePropuestaOT(Double.valueOf(this.numerotercero.getText()));
                grabarot.ActualizarOTTerceros(ot, detalle);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            detalle_orden_tercero.setModal(false);
            detalle_orden_tercero.setVisible(false);
            this.refrescar.doClick();
        }
    }//GEN-LAST:event_GrabarDetalleTerceroActionPerformed

    private void GrabarDetalleTerceroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GrabarDetalleTerceroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarDetalleTerceroKeyPressed

    private void detalle_orden_terceroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_detalle_orden_terceroFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_terceroFocusGained

    private void detalle_orden_terceroWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_orden_terceroWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_terceroWindowGainedFocus

    private void detalle_orden_terceroWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_orden_terceroWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_terceroWindowActivated

    private void SalirTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirTareaActionPerformed
        detalle_tarea_ot.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirTareaActionPerformed

    private void GrabarDetalleTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarDetalleTareaActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            orden_trabajo ot = new orden_trabajo();
            orden_trabajoDAO grabarot = new orden_trabajoDAO();
            //Capturamos los Valores BigDecimal

            ot.setNumero(Double.valueOf(this.numerotarea.getText()));

            int totalRow = modelodetalletarea.getRowCount();
            totalRow -= 1;

            String detalle = "[";
            for (int i = 0; i <= (totalRow); i++) {

                String linea = "{dnumero : " + this.numerotarea.getText() + ","
                        + "item : " + modelodetalletarea.getValueAt(i, 0).toString() + ","
                        + "descripcion: '" + modelodetalletarea.getValueAt(i, 1).toString() + "',"
                        + "estado : " + modelodetalletarea.getValueAt(i, 2).toString()
                        + "},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            System.out.println(detalle);
            try {
                detalle_tarea_orden_trabajoDAO borrardetalleDAO = new detalle_tarea_orden_trabajoDAO();
                borrardetalleDAO.borrarDetalleTareaOT(Double.valueOf(this.numerotarea.getText()));
                grabarot.ActualizarOTTareas(ot, detalle);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            detalle_tarea_ot.setModal(false);
            detalle_tarea_ot.setVisible(false);
            this.refrescar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarDetalleTareaActionPerformed

    private void GrabarItemTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemTareaActionPerformed
        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[3];
            fila[1] = this.descripciontarea.getText();
            if (concluirtarea.isSelected()) {
                fila[2] = "SI";
            } else {
                fila[2] = "NO";
            }
            modelodetalletarea.addRow(fila);
            this.descripciontarea.requestFocus();
        } else {
            this.tabladetalletarea.setValueAt(this.descripciontarea.getText(), nFila, 1);
            if (concluirtarea.isSelected()) {
                this.tabladetalletarea.setValueAt("SI", nFila, 2);
            } else {
                this.tabladetalletarea.setValueAt("NO", nFila, 2);
            }
            nFila = 0;
            this.SalirItemTarea.doClick();
        }
        this.contaritemtareas();
        this.limpiartareas();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemTareaActionPerformed

    private void SalirItemTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemTareaActionPerformed
        this.itemtareas.setModal(false);
        this.itemtareas.setVisible(false);
        this.detalle_tarea_ot.setModal(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemTareaActionPerformed

    private void descripciontareaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descripciontareaKeyTyped
        if (descripciontarea.getText().length() == 150) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_descripciontareaKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nFila = jTable1.getSelectedRow();
            String num = jTable1.getValueAt(nFila, 9).toString();
            num = num.replace(".", "");
            num = num.replace(",", ".");
            numero_a_letras numero = new numero_a_letras();

            parameters.put("Letra", numero.Convertir(num, true, 1));

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("nNumero", idControl.getText().trim());
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/orden_trabajo_tareas.jasper");
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

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void entregaprevistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entregaprevistaActionPerformed
        this.limpiarOT();
        modo.setText("2");
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {

            nFila = this.jTable1.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nFila = this.jTable1.getSelectedRow();
            this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
            orden_trabajoDAO otDAO = new orden_trabajoDAO();
            orden_trabajo otr = null;
            try {
                otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (otr != null) {
                //SE CARGAN LOS DATOS DE LA CABECERA
                if (fechaprevistaentrega.equals("NULL")) {
                    fechaprevistaentrega.setCalendar(c2);
                } else {
                    fechaprevistaentrega.setDate(otr.getFechaentregaprevista());
                }

                //Establecemos un título para el jDialog
                fechaprevista.setTitle("Fijar Fecha Prevista de Entrega de la OT N° " + this.idControl.getText());
                fechaprevista.setModal(true);
                fechaprevista.setSize(434, 230);
                fechaprevista.setLocationRelativeTo(null);
                fechaprevista.setVisible(true);
                //Establecemos un título para el jDialog
                fechaprevistaentrega.requestFocus();
            } else {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_entregaprevistaActionPerformed

    private void fechaprevistaentregaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaprevistaentregaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprevistaentregaFocusGained

    private void fechaprevistaentregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaprevistaentregaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprevistaentregaKeyPressed

    private void SalirFechaPrevistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFechaPrevistaActionPerformed
        this.fechaprevista.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFechaPrevistaActionPerformed

    private void GrabarFechaPrevistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarFechaPrevistaActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Confirmar la Probable fecha de entrega de la OT ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                Date dFechaHoy = ODate.de_java_a_sql(fechaprevistaentrega.getDate());

                orden_trabajoDAO otDAO = new orden_trabajoDAO();
                orden_trabajo otr = null;
                try {
                    otr = otDAO.buscarId(Integer.valueOf(this.idControl.getText()));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
                }
                otr.setFechaentregaprevista(dFechaHoy);
                otr.setNumero(Double.valueOf(this.idControl.getText()));
                try {
                    otDAO.ActualizarFechaPrevista(otr);
                    JOptionPane.showMessageDialog(null, "Se ha grabado la fecha prevista de entrega de la OT");
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                    JOptionPane.showMessageDialog(null, "No se grabó la fecha");
                }
            }
        }
        this.SalirFechaPrevista.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarFechaPrevistaActionPerformed

    private void SalirPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPersonalActionPerformed
        this.personal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPersonalActionPerformed

    private void GrabarPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarPersonalActionPerformed

        orden_trabajo otcontrol = null;
        orden_trabajoDAO otcontrolDAO = new orden_trabajoDAO();
        int nempleado=Integer.valueOf(this.personalasignado.getText());
        Date dFechaHoy = ODate.de_java_a_sql(fechahoy.getDate());
//        otcontrol.setFechaentregaprevista(dFechaHoy);

        
        if (this.personalasignado.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código del Personal");
            this.personalasignado.requestFocus();
            return;
        }

        detalle_funcionarios_otDAO grabarf = new detalle_funcionarios_otDAO();
        detalle_funcionarios_ot F = new detalle_funcionarios_ot();
        //Clase de Cliente porque tiene que hacer referencia al cliente
        ficha_empleadoDAO funDAO = new ficha_empleadoDAO();
        ficha_empleado fun = null;
        try {
            fun = funDAO.buscarId(Integer.valueOf(this.personalasignado.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        int totalRow = this.tablapersonalasignado.getRowCount();

        F.setDnumero(Double.valueOf(this.idControl.getText()));
        F.setEmpleado(fun);
        F.setItem(totalRow + 1);
        try {
            grabarf.insertarPersonal(F);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        GrillaPersonal GrillaP = new GrillaPersonal();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.personalasignado.setText("");
        this.nombrepersonalasignado.setText("");
        this.personalasignado.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarPersonalActionPerformed

    private void EliminarPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarPersonalActionPerformed
        nFila = this.tablapersonalasignado.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
        int nFila = this.tablapersonalasignado.getSelectedRow();
        int nItem = Integer.valueOf(this.tablapersonalasignado.getValueAt(nFila, 0).toString());

        detalle_funcionarios_otDAO grabarf = new detalle_funcionarios_otDAO();
        detalle_funcionarios_ot F = new detalle_funcionarios_ot();
        //Clase de Cliente porque tiene que hacer referencia al cliente

        F.setDnumero(Double.valueOf(this.idControl.getText()));
        F.setItem(nItem);
        try {
            grabarf.borrarPersonal(F);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        GrillaPersonal GrillaP = new GrillaPersonal();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.personalasignado.setText("");
        this.nombrepersonalasignado.setText("");
        this.personalasignado.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_EliminarPersonalActionPerformed

    private void BuscarPersonalTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPersonalTareaActionPerformed
        nSeleccionPersonal = 2;
        if (this.personalasignado.getText().equals("")) {
            this.personalasignado.setText("0");
        }
        ficha_empleadoDAO fichaDAO = new ficha_empleadoDAO();
        ficha_empleado ficha = null;
        try {
            ficha = fichaDAO.buscarId(Integer.valueOf(this.personalasignado.getText()));
            if (ficha.getCodigo() == 0) {
                BEmpleado.setModal(true);
                BEmpleado.setSize(482, 575);
                BEmpleado.setLocationRelativeTo(null);
                BEmpleado.setTitle("Buscar Empleado");
                BEmpleado.setVisible(true);
                BEmpleado.setModal(false);
            } else {
                nombrepersonalasignado.setText(ficha.getNombreempleado());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        GrabarPersonal.requestFocus();
        nSeleccionPersonal = 0;
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarPersonalTareaActionPerformed

    private void personalasignadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personalasignadoActionPerformed
        if (this.personalasignado.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código del Personal o Pulse Cero(0)");
            this.personalasignado.requestFocus();
            return;
        }

        BuscarPersonalTarea.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_personalasignadoActionPerformed

    private void SalirVencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVencimientoActionPerformed
        this.vencimientos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVencimientoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            obj = new clsExportarExcel();
            obj.exportarExcel(jTable1);
        } catch (IOException ex) {
            Logger.getLogger(orden_trabajos.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void potreroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_potreroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.cantidad.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codprod.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_potreroKeyPressed

    private void limpiartareas() {
        this.descripciontarea.setText("");
        this.concluirtarea.setSelected(false);
    }

    private void limpiaritemterceros() {
        this.proveedortercerizado.setText("");
        this.presupuestotercero.setText("0");
        this.aprobarpresupuesto.setSelected(false);
    }

    private void limpiaritems() {
        this.codprod.setText("");
        this.cantidad.setText("0");
        this.nombreproducto.setText("");
        this.costo.setText("0");
        this.totalitem.setText("0");
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtroproducto(int nNumeroColumna) {
        trsfiltroproducto.setRowFilter(RowFilter.regexFilter(jTBuscarProducto.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltropro.setRowFilter(RowFilter.regexFilter(this.jTBuscarproveedor.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("N° OT");
        modelo.addColumn("Fecha Emisión");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Sección");
        modelo.addColumn("Trabajo a Realizar");
        modelo.addColumn("Solicitado por");
        modelo.addColumn("Aprobado");
        modelo.addColumn("Estado");
        modelo.addColumn("Presupuesto");
        modelo.addColumn("Tipo");

        int[] anchos = {100, 120, 180, 180, 250, 150, 100, 150, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        DefaultTableCellRenderer TablaCenter = new DefaultTableCellRenderer();
        TablaCenter.setHorizontalAlignment(SwingConstants.CENTER);

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        Font font = new Font("Arial", Font.BOLD, 9);
        this.jTable1.setFont(font);

        this.jTable1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaCenter);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaCenter);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);

    }

    private void TituloVence() {
        modelovence.addColumn("N° OT");
        modelovence.addColumn("Fecha Propuesta");
        modelovence.addColumn("Trabajo a Realizar");
        modelovence.addColumn("Solicitado por");

        int[] anchos = {100, 150, 200, 180};
        for (int i = 0; i < modelovence.getColumnCount(); i++) {
            this.tablavence.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        DefaultTableCellRenderer TablaCenter = new DefaultTableCellRenderer();
        TablaCenter.setHorizontalAlignment(SwingConstants.CENTER);

        ((DefaultTableCellRenderer) tablavence.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablavence.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablavence.setFont(font);

        this.tablavence.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablavence.getColumnModel().getColumn(1).setCellRenderer(TablaCenter);
    }

    private void TituloDetalle() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelodetalle.addColumn("Ïtem");
        modelodetalle.addColumn("Código");
        modelodetalle.addColumn("Descripción");
        modelodetalle.addColumn("Cantidad");
        modelodetalle.addColumn("Disponible");
        modelodetalle.addColumn("Costo");
        modelodetalle.addColumn("Total");
        modelodetalle.addColumn("Potrero");
        int[] anchos = {60, 100, 200, 100, 100, 100, 100,120};
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            tabladetalle.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalle.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladetalle.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);

        tabladetalle.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tabladetalle.setFont(font);
    }

    private void TituloDetalleTercero() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelodetalletercero.addColumn("Ïtem");
        modelodetalletercero.addColumn("Proveedor");
        modelodetalletercero.addColumn("Presupuesto");
        modelodetalletercero.addColumn("Aprobado");
        int[] anchos = {60, 200, 100, 100};
        for (int i = 0; i < modelodetalletercero.getColumnCount(); i++) {
            tabladetalletercero.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalletercero.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladetalletercero.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tabladetalletercero.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);

        tabladetalletercero.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tabladetalletercero.setFont(font);
    }

    private void TituloPersonalAsignado() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelopersonal.addColumn("Ïtem");
        modelopersonal.addColumn("Código");
        modelopersonal.addColumn("Nombre del Personal");
        int[] anchos = {60, 100, 300};
        for (int i = 0; i < modelopersonal.getColumnCount(); i++) {
            tablapersonalasignado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapersonalasignado.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapersonalasignado.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tablapersonalasignado.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        tablapersonalasignado.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tablapersonalasignado.setFont(font);
    }

    private void TituloDetalleTarea() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelodetalletarea.addColumn("Ïtem");
        modelodetalletarea.addColumn("Descripción");
        modelodetalletarea.addColumn("Concluido");
        int[] anchos = {60, 300, 100};
        for (int i = 0; i < modelodetalletarea.getColumnCount(); i++) {
            tabladetalletarea.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalletarea.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladetalletarea.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tabladetalletarea.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tabladetalletarea.setFont(font);
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

    private void TituloProductos() {
        modeloproducto.addColumn("Código");
        modeloproducto.addColumn("Descripción");
        modeloproducto.addColumn("Costo");
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

    private void TituloProveedor() {
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
                new orden_trabajos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarEmpleado;
    private javax.swing.JButton AceptarProducto;
    private javax.swing.JButton AceptarSecc;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton Aceptarprov;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BEmpleado;
    private javax.swing.JDialog BProducto;
    private javax.swing.JDialog BProveedor;
    private javax.swing.JDialog BSeccion;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton BuscarPersonalTarea;
    private javax.swing.JButton BuscarProducto;
    private javax.swing.JButton BuscarSeccion;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton EliminarPersonal;
    private javax.swing.JLabel EtiquetaQr;
    private javax.swing.JButton GrabarDetalleMantenimiento;
    private javax.swing.JButton GrabarDetalleTarea;
    private javax.swing.JButton GrabarDetalleTercero;
    private javax.swing.JButton GrabarFechaPrevista;
    private javax.swing.JButton GrabarItem;
    private javax.swing.JButton GrabarItemTarea;
    private javax.swing.JButton GrabarItemTercero;
    private javax.swing.JButton GrabarOt;
    private javax.swing.JButton GrabarPersonal;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirEmpleado;
    private javax.swing.JButton SalirFechaPrevista;
    private javax.swing.JButton SalirItem;
    private javax.swing.JButton SalirItemTarea;
    private javax.swing.JButton SalirItemTercero;
    private javax.swing.JButton SalirOt;
    private javax.swing.JButton SalirPersonal;
    private javax.swing.JButton SalirProducto;
    private javax.swing.JButton SalirSecc;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton SalirTarea;
    private javax.swing.JButton SalirTercero;
    private javax.swing.JButton SalirVencimiento;
    private javax.swing.JButton Salirprov;
    private javax.swing.JLabel Socio1;
    private javax.swing.JButton aprobar;
    private javax.swing.JCheckBox aprobarpresupuesto;
    private javax.swing.JButton buscarEmpleado;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JTextField cModo;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JTextField codprod;
    private javax.swing.JComboBox comboempleado;
    private javax.swing.JComboBox comboproducto;
    private javax.swing.JComboBox comboproveedor;
    private javax.swing.JComboBox comboseccion;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JCheckBox concluirtarea;
    private javax.swing.JFormattedTextField costo;
    private com.toedter.calendar.JDateChooser dFinal;
    private com.toedter.calendar.JDateChooser dInicial;
    private javax.swing.JButton delitem;
    private javax.swing.JButton delitemtarea;
    private javax.swing.JButton delitemtercero;
    private javax.swing.JTextArea descripciontarea;
    private javax.swing.JDialog detalle_orden_mantenimiento;
    private javax.swing.JDialog detalle_orden_tercero;
    private javax.swing.JDialog detalle_orden_trabajo;
    private javax.swing.JDialog detalle_tarea_ot;
    private javax.swing.JButton detalle_tareas;
    private javax.swing.JFormattedTextField disponible;
    private javax.swing.JButton editaritem;
    private javax.swing.JButton editaritemtarea;
    private javax.swing.JButton editaritemtercero;
    private javax.swing.JTextField empleado;
    private javax.swing.JButton entregaprevista;
    private javax.swing.JTextField estadodetalle;
    private javax.swing.JTextField estadotarea;
    private javax.swing.JTextField estadotercero;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private javax.swing.JLabel etiquetanumerotarea;
    private javax.swing.JLabel etiquetapersonal;
    private com.toedter.calendar.JDateChooser fechaemision;
    private com.toedter.calendar.JDateChooser fechaemisiondetalle;
    private com.toedter.calendar.JDateChooser fechaemisiontarea;
    private com.toedter.calendar.JDateChooser fechaemisiontercero;
    private com.toedter.calendar.JDateChooser fechahoy;
    private javax.swing.JDialog fechaprevista;
    private com.toedter.calendar.JDateChooser fechaprevistaentrega;
    private javax.swing.JButton finalizar;
    private javax.swing.JTextField galpon;
    private javax.swing.JTextField idControl;
    private javax.swing.JButton iniciar;
    private javax.swing.JDialog itemdetalleorden;
    private javax.swing.JDialog itemtareas;
    private javax.swing.JDialog itemterceros;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
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
    private javax.swing.JScrollPane jScrollPane15;
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
    private javax.swing.JTextField jTBuscarEmpleado;
    private javax.swing.JTextField jTBuscarProducto;
    private javax.swing.JTextField jTBuscarSeccion;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTBuscarproveedor;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton mantenimiento;
    private javax.swing.JTextField modo;
    private javax.swing.JTextField modo1;
    private javax.swing.JTextField modo2;
    private javax.swing.JTextField nombreempleado;
    private javax.swing.JTextField nombrepersonalasignado;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField nombreseccion;
    private javax.swing.JTextField nombresecciondetalle;
    private javax.swing.JTextField nombresecciontarea;
    private javax.swing.JTextField nombresecciontercero;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nombresucursaldetalle;
    private javax.swing.JTextField nombresucursaltarea;
    private javax.swing.JTextField nombresucursaltercero;
    private javax.swing.JButton novedades;
    private javax.swing.JButton nuevoitem;
    private javax.swing.JButton nuevoitemtarea;
    private javax.swing.JButton nuevoitemtercero;
    private javax.swing.JTextField numero;
    private javax.swing.JFormattedTextField numerodetalle;
    private javax.swing.JFormattedTextField numerotarea;
    private javax.swing.JFormattedTextField numerotercero;
    private javax.swing.JTextArea observacion;
    private javax.swing.JTextArea observaciontarea;
    private javax.swing.JTextArea observaciontercero;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JDialog personal;
    private javax.swing.JTextField personalasignado;
    private javax.swing.JTextField potrero;
    private javax.swing.JFormattedTextField presupuestotercero;
    private javax.swing.JTextField proveedortercerizado;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField seccion;
    private javax.swing.JTextField solicitadopordetalle;
    private javax.swing.JTextField solicitadoportarea;
    private javax.swing.JTextField solicitadoportercero;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tabladetalle;
    private javax.swing.JTable tabladetalletarea;
    private javax.swing.JTable tabladetalletercero;
    private javax.swing.JTable tablaempleado;
    private javax.swing.JTable tablapersonalasignado;
    private javax.swing.JTable tablaproducto;
    private javax.swing.JTable tablaproveedor;
    private javax.swing.JTable tablaseccion;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTable tablavence;
    private javax.swing.JButton tercerizados;
    private javax.swing.JComboBox<String> tipo;
    private javax.swing.JFormattedTextField totalcosto;
    private javax.swing.JFormattedTextField totalitem;
    private javax.swing.JTextArea trabajoarealizar;
    private javax.swing.JDialog vencimientos;
    // End of variables declaration//GEN-END:variables

    private class GrillaOT extends Thread {

        public void run() {

            Date dFechaInicio = ODate.de_java_a_sql(dInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(dFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            orden_trabajoDAO DAO = new orden_trabajoDAO();
            try {
                for (orden_trabajo ot : DAO.MostrarxFechaEmision(dFechaInicio, dFechaFinal)) {
                    String Datos[] = {formatosinpunto.format(ot.getNumero()), formatoFecha.format(ot.getFechaemision()), ot.getSucursal().getNombre(), ot.getSeccion().getNombre(), ot.getTrabajoarealizar(), ot.getSolicitadopor().getNombreempleado(), ot.getAprobado(), ot.getEstado(), formatea.format(ot.getTotalpresupuesto()), ot.getTipo()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            jTable1.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = jTable1.getRowCount();
            if (cantFilas > 0) {
                Modificar.setEnabled(true);
                Eliminar.setEnabled(true);
                Listar.setEnabled(true);
                mantenimiento.setEnabled(true);
                tercerizados.setEnabled(true);
                aprobar.setEnabled(true);
                iniciar.setEnabled(true);
                detalle_tareas.setEnabled(true);
                novedades.setEnabled(true);
                finalizar.setEnabled(true);
                entregaprevista.setEnabled(true);
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                Listar.setEnabled(false);
                mantenimiento.setEnabled(false);
                tercerizados.setEnabled(false);
                aprobar.setEnabled(false);
                iniciar.setEnabled(false);
                detalle_tareas.setEnabled(false);
                novedades.setEnabled(false);
                finalizar.setEnabled(false);
                entregaprevista.setEnabled(false);
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

    private class GrillaProveedor extends Thread {
        /*
        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproveedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproveedor.removeRow(0);
            }

            proveedorDAO DAOprov = new proveedorDAO();
            try {
                for (proveedor prov : DAOprov.todos()) {
                    String Datos[] = {String.valueOf(prov.getCodigo()), prov.getNombre(), prov.getRuc(), prov.getTimbrado(), formatoFecha.format(prov.getVencimiento())};
                    modeloproveedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproveedor.setRowSorter(new TableRowSorter(modeloproveedor));
            int cantFilas = tablaproveedor.getRowCount();
        }*/
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
                    String Datos[] = {String.valueOf(pr.getCodigo()), pr.getNombre(), formatea.format(pr.getCosto()), formatea.format(nPorcentajeIVA)};
                    modeloproducto.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
            int cantFilas = tablaproducto.getRowCount();
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
                int nFila = jTable1.getSelectedRow();
                String num = jTable1.getValueAt(nFila, 9).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumero", idControl.getText().trim());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/orden_trabajo_mantenimiento.jasper");
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

    private class GrillaFuncionario extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloempleado.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloempleado.removeRow(0);
            }

            ficha_empleadoDAO DAOFICHA = new ficha_empleadoDAO();
            try {
                for (ficha_empleado ficha : DAOFICHA.Todos()) {
                    String Datos[] = {String.valueOf(ficha.getCodigo()), ficha.getNombres(), String.valueOf(ficha.getTipo_salario()), formatea.format(ficha.getSalario())};
                    modeloempleado.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaempleado.setRowSorter(new TableRowSorter(modeloempleado));
            int cantFilas = tablaempleado.getRowCount();
        }
    }

    private class GrillaSecc extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloseccion.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloseccion.removeRow(0);
            }

            seccionDAO DAOsecc = new seccionDAO();
            try {
                for (seccion secc : DAOsecc.Todos()) {
                    String Datos[] = {String.valueOf(secc.getCodigo()), String.valueOf(secc.getNombre())};
                    modeloseccion.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaseccion.setRowSorter(new TableRowSorter(modeloseccion));
            int cantFilas = tablaseccion.getRowCount();
        }
    }

    private class GrillaPersonal extends Thread {

        public void run() {
            int cantidadRegistro = modelopersonal.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopersonal.removeRow(0);
            }
            detalle_funcionarios_otDAO detfunDAO = new detalle_funcionarios_otDAO();
            try {
                for (detalle_funcionarios_ot detfun : detfunDAO.MostrarDetalle(Double.valueOf(idControl.getText()))) {
                    String Detalle[] = {String.valueOf(detfun.getItem()), String.valueOf(detfun.getEmpleado().getCodigo()), detfun.getEmpleado().getNombreempleado()};
                    modelopersonal.addRow(Detalle);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            int cantFilas = tablapersonalasignado.getRowCount();
            if (cantFilas > 0) {
                EliminarPersonal.setEnabled(true);
            } else {
                EliminarPersonal.setEnabled(false);
            }

        }
    }

    public class HiloControl extends Thread {

        private int minutos = 0, segundos = 0;
        private int tipo;
        private int delay;
        String c1, c2;

        public HiloControl(int ntipo, int delayTime) {
            tipo = ntipo;
            delay = delayTime;
        }

        public void run() {
            Runtime rt = Runtime.getRuntime();

            try {
                for (;;) {
                    if (segundos == 59) {
                        segundos = 0;
                        minutos++;
                    }
                    segundos++;
                    //ESTE HILO MATA LAS CONEXIONES QUE YA NO SON UTILIZADAS
                    //Y EVITA QUE COLAPSE EL SERVIDOR
                    //DE NADA
                    if (tipo == 1) {
                        //INICIALIZAMOS LA FECHA DEL DIA    
                        System.out.println("ENTRE A LA CONSULTA");
                        Date dFecha = ODate.de_java_a_sql(fechahoy.getDate());

                        int cantidadRegistro = modelovence.getRowCount();
                        for (int i = 1; i <= cantidadRegistro; i++) {
                            modelovence.removeRow(0);
                        }

                        orden_trabajoDAO otvenceDAO = new orden_trabajoDAO();
                        try {
                            for (orden_trabajo otv : otvenceDAO.VenceFechaEntrega(dFecha)) {
                                String Datos[] = {formatosinpunto.format(otv.getNumero()), formatoFecha.format(otv.getFechaentregaprevista()), otv.getTrabajoarealizar(), otv.getSolicitadopor().getNombreempleado()};
                                modelovence.addRow(Datos);
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                        }

                        tablavence.setRowSorter(new TableRowSorter(modelovence));
                        int cantFilas = tablavence.getRowCount();
                        if (cantFilas > 0) {
                            Object[] opciones = {"   Si   ", "   No   "};
                            int ret = JOptionPane.showOptionDialog(null, "Tiene " + cantFilas + " OT cercanos a Entregar, desea ver cuales son ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
                            if (ret == 0) {
                                vencimientos.setModal(true);
                                vencimientos.setSize(603, 450);
                                vencimientos.setLocationRelativeTo(null);
                                vencimientos.setVisible(true);
                            }
                        }
                    }
                    System.out.println("tiempo  " + minutos + ":" + segundos);
                    Thread.sleep(delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rt.gc();
        }
    }
}

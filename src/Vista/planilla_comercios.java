/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.UUID;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.albumfoto_productoDAO;
import DAO.comprobanteDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.cuenta_casacomercialDAO;
//import DAO.detalle_planilla_comercioDAO;
import DAO.monedaDAO;
import DAO.planilla_comercioDAO;
import DAO.sucursalDAO;
import DAO.casaDAO;
import DAO.clienteDAO;
import DAO.detalle_planilla_comercioDAO;
import Modelo.Tablas;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_casascomerciales;
import Modelo.detalle_planilla_comercio;
import Modelo.moneda;
import Modelo.planilla_comercio;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.sql.Date;
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

/**
 *
 */
public class planilla_comercios extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    String idunico = null;
    Tablas modelo = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modeloproducto = new Tablas();
    Tablas modelocasa = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelovendedor = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltroproducto, trsfiltrocasa, trsfiltrovendedor;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formatcantidad = new DecimalFormat("######.####");
    int nFila = 0;
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String cTasa, referencia = null;
    double nPorcentajeIVA = 0.00;
    Date dEmision;
    Date dVence;

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
    ImageIcon imagenfondo = new ImageIcon("src/Iconos/producto.png");

    public planilla_comercios() {
        initComponents();
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.salir.setIcon(iconosalir);
        this.Salir.setIcon(iconosalir);
        this.buscarSucursal.setIcon(iconobuscar);
        this.BuscarProducto.setIcon(iconobuscar);
        this.BuscarCliente.setIcon(iconobuscar);
        this.BuscarVendedor.setIcon(iconobuscar);
        this.refrescar.setIcon(icorefresh);
        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        this.creferencia.setVisible(false);
        this.presupuesto.setShowGrid(false);
        this.presupuesto.setOpaque(true);
        this.presupuesto.setBackground(new Color(204, 204, 255));
        this.presupuesto.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setVisible(false);
        this.cModo.setVisible(false);
        this.nombreproducto.setEnabled(false);
        this.totalitem.setEnabled(false);
        this.idControl.setText("0");
        this.Inicializar();
        this.cargarTitulo();
        this.TituloDetalle();
        this.TitSuc();
        this.TitClie();
        this.TituloProductos();
        this.TitVendedor();

        GrillaCliente grillacl = new GrillaCliente();
        Thread hiloco = new Thread(grillacl);
        hiloco.start();

        GrillaPlanilla GrillaOC = new GrillaPlanilla();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        ListaVendedor grillavn = new ListaVendedor();
        Thread hilove = new Thread(grillavn);
        hilove.start();
    }
    configuracionDAO configDAO = new configuracionDAO();
    configuracion configinicial = configDAO.consultar();
    Control hand = new Control();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detalle_planilla = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        numero = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        servicio = new javax.swing.JTextField();
        BuscarCliente = new javax.swing.JButton();
        nombreservicio = new javax.swing.JTextField();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        casa = new javax.swing.JTextField();
        BuscarVendedor = new javax.swing.JButton();
        nombrecasa = new javax.swing.JTextField();
        Gestionar = new javax.swing.JButton();
        creferencia = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabladetalle = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        agregar = new javax.swing.JButton();
        editar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        total = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
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
        items = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        codprod = new javax.swing.JTextField();
        lblnombre = new javax.swing.JLabel();
        BuscarProducto = new javax.swing.JButton();
        nombreproducto = new javax.swing.JTextField();
        lblcantidad = new javax.swing.JLabel();
        cantidad = new javax.swing.JFormattedTextField();
        precio = new javax.swing.JFormattedTextField();
        jLabel30 = new javax.swing.JLabel();
        totalitem = new javax.swing.JFormattedTextField();
        cModo = new javax.swing.JTextField();
        porcentaje = new javax.swing.JFormattedTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        GrabarItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        BProducto = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        comboproducto = new javax.swing.JComboBox();
        jTBuscarProducto = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaproducto = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarProducto = new javax.swing.JButton();
        SalirProducto = new javax.swing.JButton();
        BVendedor = new javax.swing.JDialog();
        jPanel27 = new javax.swing.JPanel();
        combovendedor = new javax.swing.JComboBox();
        jTBuscarVendedor = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablavendedor = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        panelfoto = new javax.swing.JDialog();
        jPanel47 = new javax.swing.JPanel();
        etiquetanombreproducto = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        fotoProducto = new JPanelWebCam.JPanelWebCam();
        jPanel49 = new javax.swing.JPanel();
        SalirFoto = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetacredito = new org.edisoncor.gui.label.LabelMetric();
        combofiltro = new javax.swing.JComboBox();
        buscarcadena = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        presupuesto = new javax.swing.JTable();

        detalle_planilla.setTitle("Detalle de Operación");
        detalle_planilla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_planillaFocusGained(evt);
            }
        });
        detalle_planilla.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_planillaWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_planilla.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_planillaWindowActivated(evt);
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
        buscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        numero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numero.setEnabled(false);

        jLabel1.setText("Numero");

        jLabel2.setText(" Emisión");

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

        jLabel6.setText("Servicio");

        servicio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        servicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                servicioFocusGained(evt);
            }
        });
        servicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servicioActionPerformed(evt);
            }
        });
        servicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                servicioKeyPressed(evt);
            }
        });

        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        nombreservicio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreservicio.setEnabled(false);

        vencimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vencimientoFocusGained(evt);
            }
        });
        vencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vencimientoKeyPressed(evt);
            }
        });

        jLabel4.setText("Vencimiento");

        jLabel7.setText("Comercio");

        casa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        casa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                casaActionPerformed(evt);
            }
        });
        casa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                casaKeyPressed(evt);
            }
        });

        BuscarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarVendedorActionPerformed(evt);
            }
        });

        nombrecasa.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecasa.setEnabled(false);

        Gestionar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Gestionar.setText("Gestionar por Lotes");
        Gestionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Gestionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GestionarActionPerformed(evt);
            }
        });

        creferencia.setText("jTextField1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombresucursal))
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(13, 13, 13)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(servicio, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(numero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(casa, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrecasa, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreservicio, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Gestionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(casa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(BuscarVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nombrecasa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(servicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addComponent(BuscarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nombreservicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Gestionar)
                                .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buscarSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        tabladetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabladetalleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabladetalleMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabladetalleMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabladetalle);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        agregar.setText("Agregar");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });

        editar.setText("Editar");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(agregar, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(agregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(eliminar)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        grabar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        grabar.setText("Grabar");
        grabar.setToolTipText("Guardar los Cambios");
        grabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grabar)
                    .addComponent(Salir))
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Total General"));

        total.setBorder(null);
        total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        total.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        total.setEnabled(false);
        total.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Importes Varios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout detalle_planillaLayout = new javax.swing.GroupLayout(detalle_planilla.getContentPane());
        detalle_planilla.getContentPane().setLayout(detalle_planillaLayout);
        detalle_planillaLayout.setHorizontalGroup(
            detalle_planillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_planillaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_planillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(detalle_planillaLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detalle_planillaLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(detalle_planillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        detalle_planillaLayout.setVerticalGroup(
            detalle_planillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_planillaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(detalle_planillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(detalle_planillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(detalle_planillaLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
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
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.SalirCliente.text")); // NOI18N
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
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.SalirCliente.text")); // NOI18N
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

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        codprod.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.codprod.text")); // NOI18N
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

        lblnombre.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.jLabel14.text")); // NOI18N

        BuscarProducto.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.BuscarProducto.text")); // NOI18N
        BuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarProductoActionPerformed(evt);
            }
        });

        nombreproducto.setEditable(false);
        nombreproducto.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.nombreproducto.text")); // NOI18N
        nombreproducto.setEnabled(false);

        lblcantidad.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.jLabel15.text")); // NOI18N

        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.cantidad.text")); // NOI18N
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

        precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precio.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.preciounitario.text")); // NOI18N
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

        jLabel30.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.jLabel17.text")); // NOI18N

        totalitem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalitem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalitem.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.totalitem.text")); // NOI18N

        cModo.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.cModo.text")); // NOI18N

        porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        porcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        porcentaje.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.preciounitario.text")); // NOI18N
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

        jLabel31.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.jLabel30.text")); // NOI18N

        jLabel32.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.jLabel25.text")); // NOI18N

        jLabel10.setText("Precio");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblnombre)
                            .addComponent(jLabel32))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(lblcantidad)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cantidad, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(precio, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(totalitem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblnombre)))
                    .addComponent(jLabel32))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblcantidad))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel31)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItem.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.NuevoItem.text")); // NOI18N
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

        SalirItem.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "detalle_facturas.SalirItem.text")); // NOI18N
        SalirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addComponent(GrabarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(SalirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(GrabarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout itemsLayout = new javax.swing.GroupLayout(items.getContentPane());
        items.getContentPane().setLayout(itemsLayout);
        itemsLayout.setHorizontalGroup(
            itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemsLayout.setVerticalGroup(
            itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemsLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BProducto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProducto.setTitle("null");
        BProducto.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                BProductoWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproducto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproductoActionPerformed(evt);
            }
        });

        jTBuscarProducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarProducto.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarProducto.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProductoActionPerformed(evt);
            }
        });

        SalirProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirProducto.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.SalirCliente.text")); // NOI18N
        SalirProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarProducto)
                    .addComponent(SalirProducto))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProductoLayout = new javax.swing.GroupLayout(BProducto.getContentPane());
        BProducto.getContentPane().setLayout(BProductoLayout);
        BProductoLayout.setHorizontalGroup(
            BProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProductoLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProductoLayout.setVerticalGroup(
            BProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProductoLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BVendedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BVendedor.setTitle("null");

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combovendedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combovendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combovendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combovendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combovendedorActionPerformed(evt);
            }
        });

        jTBuscarVendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarVendedor.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(combovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.SalirCliente.text")); // NOI18N
        SalirVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarVendedor)
                    .addComponent(SalirVendedor))
                .addContainerGap())
        );

        javax.swing.GroupLayout BVendedorLayout = new javax.swing.GroupLayout(BVendedor.getContentPane());
        BVendedor.getContentPane().setLayout(BVendedorLayout);
        BVendedorLayout.setHorizontalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BVendedorLayout.setVerticalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel47.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetanombreproducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetanombreproducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetanombreproducto.setText("jLabel31");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetanombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiquetanombreproducto)
                .addContainerGap())
        );

        jPanel48.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        fotoProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fotoProducto.setToolTipText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "ventas.fotoProducto.toolTipText")); // NOI18N
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

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fotoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fotoProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel49.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirFoto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirFoto.setText("Salir de Imágen");
        SalirFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelfotoLayout = new javax.swing.GroupLayout(panelfoto.getContentPane());
        panelfoto.getContentPane().setLayout(panelfotoLayout);
        panelfotoLayout.setHorizontalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelfotoLayout.setVerticalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfotoLayout.createSequentialGroup()
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Planilla de Liquidación Casas Comerciales y Financieras");
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
        etiquetacredito.setText("Planilla Liquidación Comercios");

        combofiltro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        combofiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre Cliente", "Nombre Vendedor", "Número" }));
        combofiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combofiltroActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(combofiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(274, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combofiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        Eliminar.setText("Eliminar Registro");
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

        salir.setBackground(new java.awt.Color(255, 255, 255));
        salir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        salir.setText("     Salir");
        salir.setToolTipText("");
        salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        idControl.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(planilla_comercios.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

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
                            .addComponent(salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(salir)
                .addGap(26, 26, 26)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        presupuesto.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        presupuesto.setModel(modelo);
        presupuesto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        presupuesto.setSelectionBackground(new java.awt.Color(51, 204, 255));
        presupuesto.setSelectionForeground(new java.awt.Color(0, 0, 255));
        presupuesto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                presupuestoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                presupuestoFocusLost(evt);
            }
        });
        presupuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                presupuestoMouseClicked(evt);
            }
        });
        presupuesto.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                presupuestoPropertyChange(evt);
            }
        });
        presupuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                presupuestoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(presupuesto);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
    }

    public void limpiarCombos() {

    }

    private void combofiltroActionPerformed(ActionEvent evt) {//GEN-FIRST:event_combofiltroActionPerformed

    }//GEN-LAST:event_combofiltroActionPerformed

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText()).toUpperCase();
                buscarcadena.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combofiltro.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 4;
                        break;//por cliente
                    case 1:
                        indiceColumnaTabla = 6;
                        break;//por vendedor
                    case 2:
                        indiceColumnaTabla = 0;
                        break;//por numero
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(presupuesto.getModel());
        presupuesto.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void salirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirActionPerformed

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
            //Luego capturamos el importe de la celda total del item
            cValorImporte = String.valueOf(this.tabladetalle.getValueAt(i, 7));
            cValorImporte = cValorImporte.replace(".", "").replace(",", ".");
            sumatoria = Double.valueOf(cValorImporte);
            sumtotal += sumatoria;
            //Calculamos el total de exentos
        }
        tabladetalle.setRowSorter(new TableRowSorter(modelodetalle));
        int cantFilas = tabladetalle.getRowCount();
        if (cantFilas > 0) {
            eliminar.setEnabled(true);
            editar.setEnabled(true);
        } else {
            eliminar.setEnabled(false);
            editar.setEnabled(false);
        }
        total.setText(formatea.format(sumtotal));
    }


    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        this.Gestionar.setEnabled(true);
        UUID id = new UUID();
        idunico = UUID.crearUUID();
        idunico = idunico.substring(1, 25);
        creferencia.setText(idunico);
        servicio.setText("0");
        numero.setText("0");
        nombreservicio.setText("");
        this.limpiar();

        detalle_planilla.setModal(true);
        detalle_planilla.setSize(1058, 630);
        //Establecemos un título para el jDialog
        detalle_planilla.setTitle("Detalle de Operación");
        detalle_planilla.setLocationRelativeTo(null);
        detalle_planilla.setVisible(true);
        sucursal.requestFocus();
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.sucursal.setText(String.valueOf(configinicial.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(configinicial.getSucursaldefecto().getNombre());
        this.fecha.setCalendar(c2);
        this.servicio.setText("0");
        this.nombreservicio.setText("");
        this.vencimiento.setCalendar(c2);
        this.casa.setText("0");
        this.nombrecasa.setText("");
        this.editar.setEnabled(false);
        this.eliminar.setEnabled(false);
        this.codprod.setEnabled(true);
        this.total.setText("");
        int cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }

    }
    private void presupuestoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_presupuestoKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_presupuestoKeyPressed

    private void presupuestoMouseClicked(MouseEvent evt) {//GEN-FIRST:event_presupuestoMouseClicked
        int nFila = this.presupuesto.getSelectedRow();
        this.idControl.setText(this.presupuesto.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_presupuestoMouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed

        nFila = this.presupuesto.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }

        this.Gestionar.setEnabled(false);
        this.numero.setText(this.presupuesto.getValueAt(nFila, 0).toString());
        planilla_comercioDAO veDAO = new planilla_comercioDAO();
        planilla_comercio ve = null;
        try {
            ve = veDAO.buscarId(Double.valueOf(this.numero.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        if (ve != null) {
            //SE CARGAN LOS DATOS DE LA CABECERA
            creferencia.setText(ve.getCreferencia());
            sucursal.setText(String.valueOf(ve.getSucursal().getCodigo()));
            nombresucursal.setText(ve.getSucursal().getNombre());
            numero.setText(formatosinpunto.format(ve.getNumero()));
            fecha.setDate(ve.getFecha());
            vencimiento.setDate(ve.getVencimiento());
            casa.setText(String.valueOf(ve.getCasa().getCodigo()));
            nombrecasa.setText(ve.getCasa().getNombre());
            servicio.setText(String.valueOf(ve.getServicio().getCodigo()));
            nombreservicio.setText(ve.getServicio().getNombre());
            total.setText(formatea.format(ve.getTotales()));

            // SE CARGAN LOS DETALLES
            int cantidadRegistro = modelodetalle.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodetalle.removeRow(0);
            }

            detalle_planilla_comercioDAO detDAO = new detalle_planilla_comercioDAO();

            try {
                for (detalle_planilla_comercio prev : detDAO.MostrarDetalle(Integer.valueOf(numero.getText()))) {
                    String Datos[] = {prev.getIddocumento(),
                        formatosinpunto.format(prev.getNumeroorden()),
                        String.valueOf(prev.getSocio().getCodigo()),
                        prev.getSocio().getNombre(),
                        formatoFecha.format(prev.getEmision_orden()),
                        formatoFecha.format(prev.getVence_cuota()),
                        String.valueOf(prev.getCuota()) + "/" + String.valueOf(prev.getNumerocuota()),
                        formatea.format(prev.getMonto()), String.valueOf(prev.getCuota()), String.valueOf(prev.getNumerocuota())};
                    modelodetalle.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            this.sumatoria();

            int cantFilas = tabladetalle.getRowCount();
            if (cantFilas > 0) {
                editar.setEnabled(true);
                eliminar.setEnabled(true);
            } else {
                editar.setEnabled(false);
                eliminar.setEnabled(false);
            }

            detalle_planilla.setModal(true);
            detalle_planilla.setSize(1058, 630);
            //Establecemos un título para el jDialog
            detalle_planilla.setTitle("Detalle de Operación");
            detalle_planilla.setLocationRelativeTo(null);
            detalle_planilla.setVisible(true);
            sucursal.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void presupuestoFocusGained(FocusEvent evt) {//GEN-FIRST:event_presupuestoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_presupuestoFocusGained

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
        int nFila = presupuesto.getSelectedRow();
        String num = presupuesto.getValueAt(nFila, 0).toString();

        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                planilla_comercioDAO cabDAO = new planilla_comercioDAO();
                detalle_planilla_comercioDAO detDAO = new detalle_planilla_comercioDAO();
                try {
                    if (num == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        detDAO.EliminarDetallePlanilla(Integer.valueOf(num));
                        cabDAO.EliminarPlanilla(Double.valueOf(num));
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario No Autorizado");
        }
        this.refrescar.doClick();
    }//GEN-LAST:event_EliminarActionPerformed

    private void presupuestoFocusLost(FocusEvent evt) {//GEN-FIRST:event_presupuestoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_presupuestoFocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_planilla.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void grabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_grabarActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito
        if (sucursal.getText().equals("0") || sucursal.getText().isEmpty()) {
            this.sucursal.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese Datos de la Sucursal");
            return;
        }

        if (servicio.getText().equals("0") || servicio.getText().isEmpty()) {
            this.servicio.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese Datos del Servicio");
            return;
        }
        if (casa.getText().equals("0") || casa.getText().isEmpty()) {
            this.casa.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese la Casa Comercial");
            return;
        }

        String cCliente = null;
        String cTotal = total.getText();
        cTotal = cTotal.replace(".", "").replace(",", ".");

        if (Double.valueOf(cTotal) > 0) {
            Object[] opciones = {"  Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Confirmar la Operación ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                Date Fecha = ODate.de_java_a_sql(fecha.getDate());
                Date Vencimiento = ODate.de_java_a_sql(vencimiento.getDate());

                sucursalDAO sucDAO = new sucursalDAO();
                sucursal suc = null;

                comprobanteDAO comDAO = new comprobanteDAO();
                comprobante com = null;

                casaDAO casaDAO = new casaDAO();
                casa ca = null;

                clienteDAO clDAO = new clienteDAO();
                cliente cl = null;

                monedaDAO mnDAO = new monedaDAO();
                moneda mn = null;

                try {
                    suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                    ca = casaDAO.buscarId(Integer.valueOf(this.casa.getText()));
                    com = comDAO.buscarId(Integer.valueOf(servicio.getText()));
                    mn = mnDAO.buscarId(Integer.valueOf(1));
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                planilla_comercioDAO ajDAO = new planilla_comercioDAO();
                planilla_comercio aju = new planilla_comercio();

                aju.setFecha(Fecha);
                aju.setVencimiento(Vencimiento);
                aju.setMoneda(mn);
                aju.setSucursal(suc);
                aju.setServicio(com);
                aju.setCasa(ca);
                aju.setTotales(Double.valueOf(cTotal));
                aju.setCreferencia(creferencia.getText());
                aju.setUsuarioalta(Integer.valueOf(Config.CodUsuario));
                aju.setFechaalta(Fecha);
                int totalRow = modelodetalle.getRowCount();
                totalRow -= 1;

                String detalle = "[";
                for (int i = 0; i <= (totalRow); i++) {
                    String supago = tabladetalle.getValueAt(i, 7).toString();
                    supago = supago.replace(".", "").replace(",", ".");

                    try {
                        dEmision = ODate.de_java_a_sql(formatoFecha.parse(tabladetalle.getValueAt(i, 4).toString()));
                        dVence = ODate.de_java_a_sql(formatoFecha.parse(tabladetalle.getValueAt(i, 5).toString()));
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    String linea = "{iddocumento : '" + modelodetalle.getValueAt(i, 0).toString() + "',"
                            + "socio : " + modelodetalle.getValueAt(i, 2).toString() + ","
                            + "numeroorden : " + modelodetalle.getValueAt(i, 1).toString() + ","
                            + "vence_cuota : " + dVence + ","
                            + "monto : " + supago + ","
                            + "numerocuota : " + modelodetalle.getValueAt(i, 8) + ","
                            + "cuota : " + modelodetalle.getValueAt(i, 9) + ","
                            + "emision_orden : '" + dEmision + "'"
                            + "},";
                    detalle += linea;
                }
                if (!detalle.equals("[")) {
                    detalle = detalle.substring(0, detalle.length() - 1);
                }
                detalle += "]";
                System.out.println(detalle);

                if (Integer.valueOf(this.numero.getText()) == 0) {
                    try {
                        ajDAO.InsertarPlanilla(aju, detalle);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } else {
                    //se actualiza salida de mercaderia
                    detalle_planilla_comercioDAO detDAO = new detalle_planilla_comercioDAO();
                    try {
                        aju.setNumero(Integer.valueOf(this.numero.getText()));
                        // detDAO.EliminarDetallePreventa(Integer.valueOf(numero.getText()));
                        ajDAO.ActualizarPlanilla(aju, detalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    //   grabarOCR.ActualizarOrdenCredito(cr, detalle);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                }
                detalle_planilla.setVisible(false);
                this.detalle_planilla.setModal(false);
                this.refrescar.doClick();
            }

        } else {
            JOptionPane.showMessageDialog(null, "No hay Productos en el Detalle");
        }
    }//GEN-LAST:event_grabarActionPerformed

    private void detalle_planillaFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_planillaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_planillaFocusGained

    private void detalle_planillaWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_planillaWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_planillaWindowGainedFocus

    private void detalle_planillaWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_planillaWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_planillaWindowActivated

    private void presupuestoPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_presupuestoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_presupuestoPropertyChange

    private void refrescarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaPlanilla GrillaOC = new GrillaPlanilla();
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
            this.servicio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_fechaKeyPressed

    private void servicioKeyPressed(KeyEvent evt) {//GEN-FIRST:event_servicioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.casa.requestFocus();
        }
    }//GEN-LAST:event_servicioKeyPressed

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
                nombresucursal.setText(sucu.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        fecha.requestFocus();
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
        this.servicio.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombreservicio.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.BCliente.setVisible(false);
        this.casa.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void jTBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.AceptarCli.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

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

    private void servicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servicioActionPerformed
        comprobanteDAO cliDAO = new comprobanteDAO();
        comprobante cl = null;
        try {
            cl = cliDAO.buscarId(Integer.valueOf(this.servicio.getText()));
            if (cl.getCodigo() != 0) {
                nombreservicio.setText(cl.getNombre());
                this.casa.requestFocus();
            } else {
                this.BuscarCliente.doClick();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_servicioActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void servicioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_servicioFocusGained
        servicio.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_servicioFocusGained

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Servicio");
        BCliente.setVisible(true);
        BCliente.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        items.setSize(440, 305);
        items.setLocationRelativeTo(null);
        this.limpiaritems();
        this.GrabarItem.setText("Agregar");
        this.cModo.setText("");
        items.setModal(true);
        items.setVisible(true);
        codprod.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_agregarActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        nFila = this.tabladetalle.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        this.cModo.setText(String.valueOf(nFila));
        this.codprod.setText(this.tabladetalle.getValueAt(this.tabladetalle.getSelectedRow(), 0).toString());
        this.nombreproducto.setText(this.tabladetalle.getValueAt(this.tabladetalle.getSelectedRow(), 1).toString());
        this.cantidad.setText(this.tabladetalle.getValueAt(this.tabladetalle.getSelectedRow(), 2).toString());
        this.porcentaje.setText(this.tabladetalle.getValueAt(this.tabladetalle.getSelectedRow(), 3).toString());
        this.precio.setText(this.tabladetalle.getValueAt(this.tabladetalle.getSelectedRow(), 4).toString());
        this.totalitem.setText(this.tabladetalle.getValueAt(this.tabladetalle.getSelectedRow(), 5).toString());
        codprod.requestFocus();
        items.setSize(450, 305);
        items.setLocationRelativeTo(null);
        items.setModal(true);
        items.setVisible(true);

        // TODO add your handling code here:*/
    }//GEN-LAST:event_editarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
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
    }//GEN-LAST:event_eliminarActionPerformed

    private void tabladetalleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalleMouseEntered

        // TODO add your handling code here:*/
    }//GEN-LAST:event_tabladetalleMouseEntered

    private void tabladetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalleMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalleMouseClicked

    private void tabladetalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladetalleMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalleMousePressed

    private void vencimientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vencimientoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoFocusGained

    private void vencimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencimientoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoKeyPressed

    private void codprodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codprodFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodFocusGained

    private void codprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codprodActionPerformed

        /*        productoDAO proDAO = new productoDAO();
        producto pro = null;
        try {
            pro = proDAO.BuscarProductoBasico(this.codprod.getText());
            if (pro.getCodigo() == null) {
                this.BuscarProducto.doClick();
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        this.cantidad.requestFocus();        // TODO add your handling code here:*/

    }//GEN-LAST:event_codprodActionPerformed

    private void codprodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codprodKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodKeyPressed

    private void BuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarProductoActionPerformed
        /*      GrillaProductos GrillaPr = new GrillaProductos();
        Thread HiloProd = new Thread(GrillaPr);
        HiloProd.start();*/
    }//GEN-LAST:event_BuscarProductoActionPerformed

    private void cantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadFocusGained
        cantidad.selectAll();
    }//GEN-LAST:event_cantidadFocusGained

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.precio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codprod.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void precioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precioFocusGained
        precio.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_precioFocusGained

    private void precioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_precioFocusLost

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

    private void porcentajeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentajeFocusGained
        porcentaje.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeFocusGained

    private void porcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentajeFocusLost
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
            fila[6] = "PRESUPUESTO";
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
        items.setModal(false);
        items.setVisible(false);
        this.detalle_planilla.setModal(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

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

    }//GEN-LAST:event_tablaproductoMouseClicked

    private void tablaproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproductoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoKeyPressed

    private void AceptarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProductoActionPerformed
        int nFila = this.tablaproducto.getSelectedRow();
        this.codprod.setText(this.tablaproducto.getValueAt(nFila, 0).toString());
        this.nombreproducto.setText(this.tablaproducto.getValueAt(nFila, 1).toString());
        this.precio.setText(this.tablaproducto.getValueAt(nFila, 2).toString());
        this.porcentaje.setText(this.tablaproducto.getValueAt(nFila, 5).toString());

        this.BProducto.setVisible(false);
        this.jTBuscarProducto.setText("");
        this.cantidad.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProductoActionPerformed

    private void SalirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProductoActionPerformed
        this.BProducto.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProductoActionPerformed

    private void BProductoWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_BProductoWindowGainedFocus
        jTBuscarProducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BProductoWindowGainedFocus

    private void BuscarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarVendedorActionPerformed
        BVendedor.setModal(true);
        BVendedor.setSize(500, 575);
        BVendedor.setLocationRelativeTo(null);
        BVendedor.setVisible(true);
        BVendedor.setTitle("Buscar Entidad Comercial");
        BVendedor.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarVendedorActionPerformed

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
        this.casa.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
        this.nombrecasa.setText(this.tablavendedor.getValueAt(nFila, 1).toString());

        this.BVendedor.setVisible(false);
        this.jTBuscarVendedor.setText("");
        this.agregar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void casaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_casaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.agregar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.servicio.requestFocus();
        }   // TODO add your        // TODO add your handling code here:
    }//GEN-LAST:event_casaKeyPressed

    private void casaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_casaActionPerformed
        casaDAO veDAO = new casaDAO();
        casa vn = null;
        try {
            vn = veDAO.buscarId(Integer.valueOf(this.casa.getText()));
            if (vn.getCodigo() != 0) {
                nombrecasa.setText(vn.getNombre());
                this.grabar.requestFocus();
                //Establecemos un título para el jDialog
            } else {
                this.BuscarVendedor.doClick();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_casaActionPerformed

    private void SalirFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFotoActionPerformed
        panelfoto.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFotoActionPerformed

    private void GestionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GestionarActionPerformed
        GrillaCuotas GrillaCu = new GrillaCuotas();
        Thread HiloCu = new Thread(GrillaCu);
        HiloCu.start();

        // TODO add your handling code here:
    }//GEN-LAST:event_GestionarActionPerformed
    private void limpiaritems() {
        this.codprod.setText("");
        this.cantidad.setText("0");
        this.nombreproducto.setText("");
        this.precio.setText("0");
        this.porcentaje.setText("0");
        this.totalitem.setText("0");
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    public void filtroproducto(int nNumeroColumna) {
        trsfiltroproducto.setRowFilter(RowFilter.regexFilter(jTBuscarProducto.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Número");
        modelo.addColumn("Fecha");
        modelo.addColumn("Vencimiento");
        modelo.addColumn("Tipo Servicio");
        modelo.addColumn("Casa Comercial");
        modelo.addColumn("Total Neto");
        modelo.addColumn("Banco Pago");

        int[] anchos = {90, 100, 100, 100, 150, 100, 200};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.presupuesto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER);

        ((DefaultTableCellRenderer) presupuesto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        presupuesto.getTableHeader().setFont(new Font("Arial Black", 1, 9));
        this.presupuesto.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.presupuesto.getColumnModel().getColumn(1).setCellRenderer(TablaCentro);
        this.presupuesto.getColumnModel().getColumn(2).setCellRenderer(TablaCentro);
        this.presupuesto.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
    }

    private void TituloDetalle() {
        modelodetalle.addColumn("id"); //0
        modelodetalle.addColumn("N° Orden");//1
        modelodetalle.addColumn("Socio");//2
        modelodetalle.addColumn("Nombre del Socio");//3
        modelodetalle.addColumn("Emision");//4
        modelodetalle.addColumn("Vence");//5
        modelodetalle.addColumn("Cuota");//6
        modelodetalle.addColumn("Importe");//7
        modelodetalle.addColumn("Cuota");//8
        modelodetalle.addColumn("NumeroCuota");//9
        int[] anchos = {1, 100, 90, 150, 90, 90, 90, 100, 5, 5};
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            tabladetalle.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalle.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        tabladetalle.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);

        DefaultTableCellRenderer TablaCenter = new DefaultTableCellRenderer();
        TablaCenter.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 
        tabladetalle.getColumnModel().getColumn(4).setCellRenderer(TablaCenter);
        tabladetalle.getColumnModel().getColumn(5).setCellRenderer(TablaCenter);
        tabladetalle.getColumnModel().getColumn(6).setCellRenderer(TablaCenter);

        //FILAS INVISIBLES
        this.tabladetalle.getColumnModel().getColumn(8).setMaxWidth(0);
        this.tabladetalle.getColumnModel().getColumn(8).setMinWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);

        this.tabladetalle.getColumnModel().getColumn(9).setMaxWidth(0);
        this.tabladetalle.getColumnModel().getColumn(9).setMinWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(9).setMinWidth(0);

        this.tabladetalle.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tabladetalle.getColumnModel().getColumn(0).setMinWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        tabladetalle.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 10);
        tabladetalle.setFont(font);
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

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");

        int[] anchos = {90, 150};
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
        modeloproducto.addColumn("Minorista");
        modeloproducto.addColumn("Mayorista");
        modeloproducto.addColumn("Stock");
        modeloproducto.addColumn("IVA");
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        int[] anchos = {90, 200, 110, 110, 80, 50, 1};
        for (int i = 0; i < modeloproducto.getColumnCount(); i++) {
            tablaproducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproducto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproducto.getTableHeader().setFont(new Font("Arial Black", 1, 11));
        this.tablaproducto.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaproducto.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablaproducto.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);

        this.tablaproducto.getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablaproducto.getColumnModel().getColumn(5).setMinWidth(0);
        this.tablaproducto.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablaproducto.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);

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
                new planilla_comercios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarProducto;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BProducto;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JDialog BVendedor;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton BuscarProducto;
    private javax.swing.JButton BuscarVendedor;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Gestionar;
    private javax.swing.JButton GrabarItem;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirFoto;
    private javax.swing.JButton SalirItem;
    private javax.swing.JButton SalirProducto;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JButton agregar;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JTextField cModo;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JTextField casa;
    private javax.swing.JTextField codprod;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combofiltro;
    private javax.swing.JComboBox comboproducto;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JComboBox combovendedor;
    private javax.swing.JTextField creferencia;
    private com.toedter.calendar.JDateChooser dFinal;
    private com.toedter.calendar.JDateChooser dInicial;
    private javax.swing.JDialog detalle_planilla;
    private javax.swing.JButton editar;
    private javax.swing.JButton eliminar;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private javax.swing.JLabel etiquetanombreproducto;
    private com.toedter.calendar.JDateChooser fecha;
    private JPanelWebCam.JPanelWebCam fotoProducto;
    private javax.swing.JButton grabar;
    private javax.swing.JTextField idControl;
    private javax.swing.JDialog items;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarProducto;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTBuscarVendedor;
    private javax.swing.JLabel lblcantidad;
    private javax.swing.JLabel lblnombre;
    private javax.swing.JTextField nombrecasa;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField nombreservicio;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JFormattedTextField numero;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JDialog panelfoto;
    private javax.swing.JFormattedTextField porcentaje;
    private javax.swing.JFormattedTextField precio;
    private javax.swing.JTable presupuesto;
    private javax.swing.JButton refrescar;
    private javax.swing.JButton salir;
    private javax.swing.JTextField servicio;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tabladetalle;
    private javax.swing.JTable tablaproducto;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTable tablavendedor;
    private javax.swing.JFormattedTextField total;
    private javax.swing.JFormattedTextField totalitem;
    private com.toedter.calendar.JDateChooser vencimiento;
    // End of variables declaration//GEN-END:variables

    private class GenerarReporte extends Thread {

        String cReporte = null;

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            try {
                Map parameters = new HashMap();
                int Fila = presupuesto.getSelectedRow();

                if (Fila < 0) {
                    JOptionPane.showMessageDialog(null,
                            "Debe seleccionar una fila de la tabla");
                    return;
                }
                String nNumero = presupuesto.getValueAt(Fila, 0).toString();
                String cTipo = presupuesto.getValueAt(Fila, 3).toString();

                if (cTipo.equals("PREVENTA")) {
                    cReporte = "preventa.jasper";
                } else if (cTipo.equals("PRESUPUESTO")) {
                    cReporte = "presupuestoStandard.jasper";
                } else {
                    cReporte = "orden_trabajo_cedro.jasper";
                }

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumero", idControl.getText());
                parameters.put("cRuc", Config.cRucEmpresa);
                parameters.put("cTelefono", Config.cTelefono);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cReporte.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                System.out.println(e.getLocalizedMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
    }

    private class GrillaPlanilla extends Thread {

        public void run() {

            Date dFechaInicio = ODate.de_java_a_sql(dInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(dFinal.getDate());
            String cTipo = "";
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            planilla_comercioDAO DAO = new planilla_comercioDAO();
            try {
                for (planilla_comercio prev : DAO.MostrarxFecha(dFechaInicio, dFechaFinal)) {
                    String Datos[] = {formatosinpunto.format(prev.getNumero()), formatoFecha.format(prev.getFecha()), formatoFecha.format(prev.getVencimiento()), prev.getServicio().getNombre(), prev.getCasa().getNombre(), formatea.format(prev.getTotales()), prev.getBanco().getNombre()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            presupuesto.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = presupuesto.getRowCount();
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

    /*private class GrillaProductos extends Thread {

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
                        double nPorcentajeIVA = 0.00;
                    } else {
                        nPorcentajeIVA = pr.getIvaporcentaje().doubleValue();
                    }
                    String Datos[] = {String.valueOf(pr.getCodigo()), pr.getNombre(), formatea.format(pr.getPrecio_maximo()), formatea.format(pr.getPrecio_minimo()), formatea.format(pr.getStock()), formatea.format(nPorcentajeIVA), String.valueOf(pr.getCambiarprecio())};
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
            cantidad.requestFocus();
            BProducto.setModal(false);

        }
    }*/
    private class GrillaCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            comprobanteDAO DAOCLIE = new comprobanteDAO();
            try {
                for (comprobante cli : DAOCLIE.todos()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class ListaVendedor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelovendedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelovendedor.removeRow(0);
            }
            casaDAO DAOve = new casaDAO();
            try {
                for (casa ve : DAOve.todos()) {
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

    private class GrillaCuotas extends Thread {

        public void run() {

            int anual = vencimiento.getCalendar().get(Calendar.YEAR);
            int mensual = vencimiento.getCalendar().get(Calendar.MONTH) + 1;
            int ncasa = Integer.valueOf(casa.getText());
            int nservicio = Integer.valueOf(servicio.getText());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelodetalle.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodetalle.removeRow(0);
            }

            cuenta_casacomercialDAO DAO = new cuenta_casacomercialDAO();
            try {
                for (cuenta_casascomerciales prev : DAO.Vencimientos(ncasa, nservicio, anual, mensual)) {
                    String Datos[] = {prev.getIddocumento(),
                        prev.getDocumento(),
                        String.valueOf(prev.getCliente().getCodigo()),
                        prev.getCliente().getNombre(),
                        formatoFecha.format(prev.getFecha()),
                        formatoFecha.format(prev.getVencimiento()),
                        String.valueOf(prev.getCuota()) + "/" + String.valueOf(prev.getNumerocuota()),
                        formatea.format(prev.getImporte()), String.valueOf(prev.getCuota()), String.valueOf(prev.getNumerocuota())};
                    modelodetalle.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            // tabladetalle.setRowSorter(new TableRowSorter(modelodetalle));
            int cantFilas = tabladetalle.getRowCount();
            if (cantFilas > 0) {
                eliminar.setEnabled(true);
                editar.setEnabled(true);
            } else {
                editar.setEnabled(false);
                eliminar.setEnabled(false);
            }
            sumatoria();
        }

    }
}

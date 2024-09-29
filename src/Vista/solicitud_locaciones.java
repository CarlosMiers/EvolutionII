/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;

import Clases.ConvertirMayusculas;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.albumfoto_inmuebleDAO;
import DAO.albumfoto_solicitud_locacionDAO;
import DAO.clienteDAO;
import DAO.edificioDAO;
import DAO.solicitud_locacionDAO;
import DAO.solicitud_locacion_codeudorDAO;
import DAO.solicitud_locacion_juridicaDAO;
import DAO.solicitud_locacion_propietarioDAO;
import Modelo.Tablas;
import Modelo.albumfoto_inmueble;
import Modelo.albumfoto_solicitud_locacion;
import Modelo.cliente;
import Modelo.edificio;
import Modelo.solicitud_locacion;
import Modelo.solicitud_locacion_codeudor;
import Modelo.solicitud_locacion_juridica;
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
import java.net.URL;
import java.sql.Date;
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
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
public class solicitud_locaciones extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelobarrio = new Tablas();
    Tablas modelonacionalidad = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modeloconyu = new Tablas();
    Tablas modelopropietario1 = new Tablas();
    Tablas modelopropietario2 = new Tablas();
    Tablas modeloedificio = new Tablas();
    Tablas modelonacionalidadconyu = new Tablas();
    Tablas modelocodeudor2 = new Tablas();
    Tablas modelocode = new Tablas();
    Tablas modelojuridica = new Tablas();
    Tablas modelojuri = new Tablas();
    Tablas modelopro = new Tablas();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrojuridica, trsfiltropropietario1, trsfiltrocli, trsfiltroconyu, trsfiltroedificio, trsfiltrocodeudor;
    int nPro = 0;
    int nJuri = 0;
    int nCode = 0;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String cModo, ruta, cNombre = null;
    int contador = 0;
    int nOpcion = 0;
    ArrayList<albumfoto_inmueble> imagenes;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");

    public solicitud_locaciones() {

        initComponents();

        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.botonsalir.setIcon(iconosalir);
        this.buscarcliente.setIcon(iconobuscar);
        this.buscarconyugue.setIcon(iconobuscar);
        this.buscaredificio.setIcon(iconobuscar);
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setText("0");
        this.codcodeudor.setText("0");
        this.Inicializar();
        this.idControl.setVisible(false);
        this.imagen.setSize(372, 259);
        idcliente.requestFocus();

        this.cargarTitulo();
        this.TitCliente();

        this.TitPersonaJuridica();
        this.TitJuridica();
        this.TitPropietario1();
        this.TitPropietario2();
        this.TitConyuge();
        this.TitEdificio();
        this.TitCodeudor();
        this.TitCodeudor2();

        GrillaSolicitud GrillaOC = new GrillaSolicitud();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();
    }

    Control hand = new Control();

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
    }

    private void refrescarCarrusel(int p) {
        imagen.setText("");
        imagen.setIcon(null);
        albumfoto_inmuebleDAO imag = new albumfoto_inmuebleDAO();
        imagenes = imag.getImagenes(Integer.valueOf(this.idsolicitud.getText()));
        if (p < imagenes.size()) {
            if (imagenes.size() > 0) {
                ImageIcon icon = new ImageIcon(imagenes.get(p).getFoto().getScaledInstance(this.imagen.getWidth(), imagen.getHeight(), Image.SCALE_DEFAULT));
                imagen.setText("");
                imagen.setIcon(icon);
                nombrearchivo.setText(imagenes.get(p).getNombre());
            }
        } else {
            contador--;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detalle_solicitud = new javax.swing.JDialog();
        Salir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        fechasolicitud = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        nacimiento = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        nombrecode = new javax.swing.JTextField();
        documentoidencode = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        telefonofijocode = new javax.swing.JTextField();
        nacionalidadcode = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        direccionparticode = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        apellidocode = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        buscarnacionalidad = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        comboestadocivilcode = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        telefonomovilcode = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        emailcode = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        direccionlabocode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lugarlabocode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cargolabocode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ingresolabocode = new javax.swing.JTextField();
        fechanacicode = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        barriocode = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        ciudadcode = new javax.swing.JTextField();
        buscarbarriocode = new javax.swing.JButton();
        buscarciudadcode = new javax.swing.JButton();
        nombreinmuebleeti = new javax.swing.JLabel();
        buscarcliente = new javax.swing.JButton();
        nombre = new javax.swing.JTextField();
        ruc = new javax.swing.JTextField();
        direccionparti = new javax.swing.JTextField();
        telefonofijo = new javax.swing.JTextField();
        telefonomovil = new javax.swing.JTextField();
        estadocivil = new javax.swing.JTextField();
        sucursal = new javax.swing.JTextField();
        idcliente = new javax.swing.JTextField();
        idsolicitud = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        nacimientoconyugue = new com.toedter.calendar.JDateChooser();
        jLabel51 = new javax.swing.JLabel();
        nombreinmuebleeti1 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        idconyugue = new javax.swing.JTextField();
        buscarconyugue = new javax.swing.JButton();
        nombreconyu = new javax.swing.JTextField();
        rucconyu = new javax.swing.JTextField();
        direccionparticonyu = new javax.swing.JTextField();
        telefonofijoconyu = new javax.swing.JTextField();
        telefonomovilconyu = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        nombrecode1 = new javax.swing.JTextField();
        documentoidencode1 = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        telefonofijocode1 = new javax.swing.JTextField();
        nacionalidadcode1 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        direccionparticode1 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        apellidocode1 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        buscarnacionalidad1 = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        comboestadocivilcode1 = new javax.swing.JComboBox<>();
        jLabel74 = new javax.swing.JLabel();
        telefonomovilcode1 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        emailcode1 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        direccionlabocode1 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        lugarlabocode1 = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        cargolabocode1 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        ingresolabocode1 = new javax.swing.JTextField();
        fechanacicode1 = new com.toedter.calendar.JDateChooser();
        jLabel80 = new javax.swing.JLabel();
        barriocode1 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        ciudadcode1 = new javax.swing.JTextField();
        buscarbarriocode1 = new javax.swing.JButton();
        buscarciudadcode1 = new javax.swing.JButton();
        nombreinmuebleeti3 = new javax.swing.JLabel();
        ctactral = new javax.swing.JTextField();
        medidor_ande = new javax.swing.JTextField();
        nir = new javax.swing.JTextField();
        nis = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        medidor_essap = new javax.swing.JTextField();
        sucursal2 = new javax.swing.JTextField();
        buscaredificio = new javax.swing.JButton();
        idedificio = new javax.swing.JTextField();
        nombreinmuebleeti4 = new javax.swing.JLabel();
        nombreinmueble = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        nropersonas = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        alquiler = new javax.swing.JFormattedTextField();
        garantia = new javax.swing.JFormattedTextField();
        jPanel26 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        GenerarPropietarios = new javax.swing.JButton();
        DeletePropietarios = new javax.swing.JButton();
        codpropietario = new javax.swing.JTextField();
        nombrepropietario = new javax.swing.JTextField();
        BuscarPropietarios = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablapropietario2 = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        GenerarJuridica = new javax.swing.JButton();
        DeleteJuridica = new javax.swing.JButton();
        codjuridica = new javax.swing.JTextField();
        nombrejuridica = new javax.swing.JTextField();
        BuscarJuridica = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablajuridica = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        GenerarCodeudor = new javax.swing.JButton();
        DeleteCodeudor = new javax.swing.JButton();
        codcodeudor = new javax.swing.JTextField();
        nombrecodeudor = new javax.swing.JTextField();
        BuscarCodeudor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablacodeudor2 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        BotonAbriArchivo = new javax.swing.JButton();
        nombrearchivo = new javax.swing.JTextField();
        imagen = new javax.swing.JLabel();
        GuardarArchivo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Grabar = new javax.swing.JButton();
        Bcliente = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combocli = new javax.swing.JComboBox();
        jTBuscarCli = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        Aceptarcli = new javax.swing.JButton();
        Salircli = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablacli = new javax.swing.JTable();
        Bclienteconyu = new javax.swing.JDialog();
        jPanel30 = new javax.swing.JPanel();
        comboconyu = new javax.swing.JComboBox();
        jTBuscarconyu = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaconyuge = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        Aceptarconyu = new javax.swing.JButton();
        Salirconyu = new javax.swing.JButton();
        Bedificio = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        comboedificio = new javax.swing.JComboBox();
        jTBuscaredificio = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaedificio = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        Aceptaredif = new javax.swing.JButton();
        Saliredif = new javax.swing.JButton();
        BJuridica = new javax.swing.JDialog();
        jPanel18 = new javax.swing.JPanel();
        combojuridica = new javax.swing.JComboBox();
        jTBuscarJuridica = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablajuri = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        AceptarJuri = new javax.swing.JButton();
        SalirJuri = new javax.swing.JButton();
        BPropietario = new javax.swing.JDialog();
        jPanel27 = new javax.swing.JPanel();
        combopropietario1 = new javax.swing.JComboBox();
        jTBuscarPropietario1 = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablapropietario1 = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        AceptarPropietario1 = new javax.swing.JButton();
        SalirPropietario1 = new javax.swing.JButton();
        BCodeudor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combocode = new javax.swing.JComboBox();
        jTBuscarCode = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        tablacode = new javax.swing.JTable();
        jPanel34 = new javax.swing.JPanel();
        AceptarCode = new javax.swing.JButton();
        SalirCode = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetainmuebles = new org.edisoncor.gui.label.LabelMetric();
        jComboBoxnombres = new javax.swing.JComboBox();
        buscarcadena = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        botonsalir = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        Agregar = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        dInicial = new com.toedter.calendar.JDateChooser();
        dFinal = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        detalle_solicitud.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_solicitudFocusGained(evt);
            }
        });
        detalle_solicitud.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_solicitudWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_solicitud.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_solicitudWindowActivated(evt);
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

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel25.setText("Fecha");

        fechasolicitud.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechasolicitudFocusGained(evt);
            }
        });
        fechasolicitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechasolicitudKeyPressed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Solicitante", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel35.setText("Nombre (s) y Apellido (s)");

        jLabel10.setText("Dirección Particular");

        jLabel39.setText("Teléfono Fijo");

        jLabel46.setText("Cedula / RUC");

        jLabel47.setText("Fecha de Nacimiento");

        jLabel48.setText("Estado Civil");

        jLabel50.setText("Teléfono Móvil");

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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Codeudor Solidario", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel15.setText("Doc. de Identidad");

        jLabel12.setText("Nombre (s)");

        nombrecode.setEditable(false);
        nombrecode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nombrecode.setEnabled(false);
        nombrecode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nombrecodeFocusGained(evt);
            }
        });
        nombrecode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombrecodeActionPerformed(evt);
            }
        });
        nombrecode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrecodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombrecodeKeyReleased(evt);
            }
        });

        documentoidencode.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        documentoidencode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                documentoidencodeFocusGained(evt);
            }
        });
        documentoidencode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentoidencodeActionPerformed(evt);
            }
        });
        documentoidencode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                documentoidencodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                documentoidencodeKeyReleased(evt);
            }
        });

        jLabel5.setText("Dirección Particular");

        telefonofijocode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        telefonofijocode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                telefonofijocodeFocusGained(evt);
            }
        });
        telefonofijocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonofijocodeActionPerformed(evt);
            }
        });
        telefonofijocode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonofijocodeKeyPressed(evt);
            }
        });

        nacionalidadcode.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nacionalidadcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nacionalidadcodeFocusGained(evt);
            }
        });
        nacionalidadcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nacionalidadcodeActionPerformed(evt);
            }
        });
        nacionalidadcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacionalidadcodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nacionalidadcodeKeyReleased(evt);
            }
        });

        jLabel20.setText("Nacionalidad");

        jLabel13.setText("Teléfono Fijo");

        direccionparticode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        direccionparticode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                direccionparticodeFocusGained(evt);
            }
        });
        direccionparticode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionparticodeActionPerformed(evt);
            }
        });
        direccionparticode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionparticodeKeyPressed(evt);
            }
        });

        jLabel14.setText("Apellido  (s)");

        apellidocode.setEditable(false);
        apellidocode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        apellidocode.setEnabled(false);
        apellidocode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                apellidocodeFocusGained(evt);
            }
        });
        apellidocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidocodeActionPerformed(evt);
            }
        });
        apellidocode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                apellidocodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                apellidocodeKeyReleased(evt);
            }
        });

        jLabel17.setText("Fecha de Nacimiento");

        buscarnacionalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarnacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarnacionalidadActionPerformed(evt);
            }
        });

        jLabel21.setText("Estado Civil");

        comboestadocivilcode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setText("Teléfono Móvil");

        telefonomovilcode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        telefonomovilcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                telefonomovilcodeFocusGained(evt);
            }
        });
        telefonomovilcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonomovilcodeActionPerformed(evt);
            }
        });
        telefonomovilcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonomovilcodeKeyPressed(evt);
            }
        });

        jLabel23.setText("Email");

        emailcode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        emailcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                emailcodeFocusGained(evt);
            }
        });
        emailcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailcodeActionPerformed(evt);
            }
        });
        emailcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailcodeKeyPressed(evt);
            }
        });

        jLabel6.setText("Dirección Laboral");

        direccionlabocode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        direccionlabocode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                direccionlabocodeFocusGained(evt);
            }
        });
        direccionlabocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionlabocodeActionPerformed(evt);
            }
        });
        direccionlabocode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionlabocodeKeyPressed(evt);
            }
        });

        jLabel7.setText("Lugar de Trabajo");

        lugarlabocode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lugarlabocode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lugarlabocodeFocusGained(evt);
            }
        });
        lugarlabocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lugarlabocodeActionPerformed(evt);
            }
        });
        lugarlabocode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lugarlabocodeKeyPressed(evt);
            }
        });

        jLabel8.setText("Cargo");

        cargolabocode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cargolabocode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cargolabocodeFocusGained(evt);
            }
        });
        cargolabocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargolabocodeActionPerformed(evt);
            }
        });
        cargolabocode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cargolabocodeKeyPressed(evt);
            }
        });

        jLabel9.setText("Ingresos");

        ingresolabocode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ingresolabocode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ingresolabocodeFocusGained(evt);
            }
        });
        ingresolabocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresolabocodeActionPerformed(evt);
            }
        });
        ingresolabocode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ingresolabocodeKeyPressed(evt);
            }
        });

        fechanacicode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechanacicodeFocusGained(evt);
            }
        });
        fechanacicode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechanacicodeKeyPressed(evt);
            }
        });

        jLabel26.setText("Barrio");

        barriocode.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        barriocode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                barriocodeFocusGained(evt);
            }
        });
        barriocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barriocodeActionPerformed(evt);
            }
        });
        barriocode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barriocodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barriocodeKeyReleased(evt);
            }
        });

        jLabel27.setText("Ciudad");

        ciudadcode.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ciudadcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ciudadcodeFocusGained(evt);
            }
        });
        ciudadcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ciudadcodeActionPerformed(evt);
            }
        });
        ciudadcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ciudadcodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ciudadcodeKeyReleased(evt);
            }
        });

        buscarbarriocode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarbarriocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarbarriocodeActionPerformed(evt);
            }
        });

        buscarciudadcode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarciudadcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarciudadcodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(direccionparticode, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciudadcode))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nacionalidadcode, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarnacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboestadocivilcode, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barriocode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarbarriocode, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(telefonofijocode, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(telefonomovilcode, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(emailcode))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(direccionlabocode, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lugarlabocode, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cargolabocode, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ingresolabocode))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombrecode, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(28, 28, 28)
                                .addComponent(documentoidencode, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fechanacicode, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(apellidocode)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarciudadcode, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(nombrecode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(apellidocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechanacicode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(documentoidencode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(nacionalidadcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarnacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(comboestadocivilcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel26)
                                .addComponent(barriocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(direccionparticode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel27)
                                .addComponent(ciudadcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarciudadcode, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(telefonofijocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(telefonomovilcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(emailcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(direccionlabocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lugarlabocode)
                            .addComponent(jLabel8)
                            .addComponent(cargolabocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(ingresolabocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(buscarbarriocode, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        nombreinmuebleeti.setText("Cuenta Solicitante");

        buscarcliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarclienteActionPerformed(evt);
            }
        });

        nombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombre.setEnabled(false);

        ruc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ruc.setEnabled(false);
        ruc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rucActionPerformed(evt);
            }
        });

        direccionparti.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        direccionparti.setEnabled(false);

        telefonofijo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        telefonofijo.setEnabled(false);

        telefonomovil.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        telefonomovil.setEnabled(false);

        estadocivil.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        estadocivil.setEnabled(false);

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

        idcliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idcliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idclienteFocusGained(evt);
            }
        });
        idcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idclienteActionPerformed(evt);
            }
        });
        idcliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idclienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idclienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(nombreinmuebleeti)
                    .addComponent(jLabel46))
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(estadocivil))
                            .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(telefonomovil, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(telefonofijo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(direccionparti, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(334, 334, 334)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(635, Short.MAX_VALUE)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nombreinmuebleeti)
                        .addComponent(idcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarcliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel10)
                    .addComponent(direccionparti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(telefonomovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(telefonofijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(estadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(573, 573, 573)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        idsolicitud.setEditable(false);
        idsolicitud.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idsolicitud.setEnabled(false);
        idsolicitud.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idsolicitudFocusGained(evt);
            }
        });
        idsolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idsolicitudActionPerformed(evt);
            }
        });
        idsolicitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idsolicitudKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idsolicitudKeyReleased(evt);
            }
        });

        jLabel16.setText("N° Solicitud");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Cónyuge", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel29.setText("Nombre (s) y Apellido (s)");

        jLabel34.setText("Fecha de Nacimiento");

        nacimientoconyugue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nacimientoconyugueFocusGained(evt);
            }
        });
        nacimientoconyugue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacimientoconyugueKeyPressed(evt);
            }
        });

        jLabel51.setText("Cedula / RUC");

        nombreinmuebleeti1.setText("Id Conyugue");

        jLabel42.setText("Teléfono Fijo");

        jLabel19.setText("Dirección Particular");

        jLabel53.setText("Teléfono Móvil");

        idconyugue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idconyugue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idconyugueFocusGained(evt);
            }
        });
        idconyugue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idconyugueActionPerformed(evt);
            }
        });
        idconyugue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idconyugueKeyPressed(evt);
            }
        });

        buscarconyugue.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarconyugue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarconyugueActionPerformed(evt);
            }
        });

        nombreconyu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreconyu.setEnabled(false);

        rucconyu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rucconyu.setEnabled(false);

        direccionparticonyu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        direccionparticonyu.setEnabled(false);

        telefonofijoconyu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        telefonofijoconyu.setEnabled(false);

        telefonomovilconyu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        telefonomovilconyu.setEnabled(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(nombreinmuebleeti1)
                            .addGap(66, 66, 66))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel29)
                            .addGap(10, 10, 10)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(idconyugue, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarconyugue, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(nacimientoconyugue, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(635, 635, 635))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreconyu, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rucconyu, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel53)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(telefonomovilconyu, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(telefonofijoconyu, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(direccionparticonyu, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(150, 150, 150))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(idconyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombreinmuebleeti1))
                    .addComponent(buscarconyugue, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(nacimientoconyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(nombreconyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rucconyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(direccionparticonyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(telefonomovilconyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel53)
                                .addComponent(jLabel42))
                            .addComponent(telefonofijoconyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Observación"));

        observacion.setColumns(20);
        observacion.setRows(5);
        jScrollPane2.setViewportView(observacion);

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
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edificio", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel59.setText("Nombre de  Inmueble");

        jLabel22.setText("NIR");

        jLabel60.setText("NIS");

        jLabel61.setText("ANDE");

        jLabel63.setText("ESSAP");

        jLabel65.setText("Teléfono");

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Codeudor Solidario", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jLabel24.setText("Doc. de Identidad");

        jLabel66.setText("Nombre (s)");

        nombrecode1.setEditable(false);
        nombrecode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nombrecode1.setEnabled(false);
        nombrecode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nombrecode1FocusGained(evt);
            }
        });
        nombrecode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombrecode1ActionPerformed(evt);
            }
        });
        nombrecode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrecode1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombrecode1KeyReleased(evt);
            }
        });

        documentoidencode1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        documentoidencode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                documentoidencode1FocusGained(evt);
            }
        });
        documentoidencode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentoidencode1ActionPerformed(evt);
            }
        });
        documentoidencode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                documentoidencode1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                documentoidencode1KeyReleased(evt);
            }
        });

        jLabel68.setText("Dirección Particular");

        telefonofijocode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        telefonofijocode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                telefonofijocode1FocusGained(evt);
            }
        });
        telefonofijocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonofijocode1ActionPerformed(evt);
            }
        });
        telefonofijocode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonofijocode1KeyPressed(evt);
            }
        });

        nacionalidadcode1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nacionalidadcode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nacionalidadcode1FocusGained(evt);
            }
        });
        nacionalidadcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nacionalidadcode1ActionPerformed(evt);
            }
        });
        nacionalidadcode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacionalidadcode1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nacionalidadcode1KeyReleased(evt);
            }
        });

        jLabel69.setText("Nacionalidad");

        jLabel70.setText("Teléfono Fijo");

        direccionparticode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        direccionparticode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                direccionparticode1FocusGained(evt);
            }
        });
        direccionparticode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionparticode1ActionPerformed(evt);
            }
        });
        direccionparticode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionparticode1KeyPressed(evt);
            }
        });

        jLabel71.setText("Apellido  (s)");

        apellidocode1.setEditable(false);
        apellidocode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        apellidocode1.setEnabled(false);
        apellidocode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                apellidocode1FocusGained(evt);
            }
        });
        apellidocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidocode1ActionPerformed(evt);
            }
        });
        apellidocode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                apellidocode1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                apellidocode1KeyReleased(evt);
            }
        });

        jLabel72.setText("Fecha de Nacimiento");

        buscarnacionalidad1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarnacionalidad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarnacionalidad1ActionPerformed(evt);
            }
        });

        jLabel73.setText("Estado Civil");

        comboestadocivilcode1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel74.setText("Teléfono Móvil");

        telefonomovilcode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        telefonomovilcode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                telefonomovilcode1FocusGained(evt);
            }
        });
        telefonomovilcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonomovilcode1ActionPerformed(evt);
            }
        });
        telefonomovilcode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonomovilcode1KeyPressed(evt);
            }
        });

        jLabel75.setText("Email");

        emailcode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        emailcode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                emailcode1FocusGained(evt);
            }
        });
        emailcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailcode1ActionPerformed(evt);
            }
        });
        emailcode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailcode1KeyPressed(evt);
            }
        });

        jLabel76.setText("Dirección Laboral");

        direccionlabocode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        direccionlabocode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                direccionlabocode1FocusGained(evt);
            }
        });
        direccionlabocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionlabocode1ActionPerformed(evt);
            }
        });
        direccionlabocode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionlabocode1KeyPressed(evt);
            }
        });

        jLabel77.setText("Lugar de Trabajo");

        lugarlabocode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lugarlabocode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lugarlabocode1FocusGained(evt);
            }
        });
        lugarlabocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lugarlabocode1ActionPerformed(evt);
            }
        });
        lugarlabocode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lugarlabocode1KeyPressed(evt);
            }
        });

        jLabel78.setText("Cargo");

        cargolabocode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cargolabocode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cargolabocode1FocusGained(evt);
            }
        });
        cargolabocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargolabocode1ActionPerformed(evt);
            }
        });
        cargolabocode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cargolabocode1KeyPressed(evt);
            }
        });

        jLabel79.setText("Ingresos");

        ingresolabocode1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ingresolabocode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ingresolabocode1FocusGained(evt);
            }
        });
        ingresolabocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresolabocode1ActionPerformed(evt);
            }
        });
        ingresolabocode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ingresolabocode1KeyPressed(evt);
            }
        });

        fechanacicode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechanacicode1FocusGained(evt);
            }
        });
        fechanacicode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechanacicode1KeyPressed(evt);
            }
        });

        jLabel80.setText("Barrio");

        barriocode1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        barriocode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                barriocode1FocusGained(evt);
            }
        });
        barriocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barriocode1ActionPerformed(evt);
            }
        });
        barriocode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barriocode1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barriocode1KeyReleased(evt);
            }
        });

        jLabel81.setText("Ciudad");

        ciudadcode1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        ciudadcode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ciudadcode1FocusGained(evt);
            }
        });
        ciudadcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ciudadcode1ActionPerformed(evt);
            }
        });
        ciudadcode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ciudadcode1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ciudadcode1KeyReleased(evt);
            }
        });

        buscarbarriocode1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarbarriocode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarbarriocode1ActionPerformed(evt);
            }
        });

        buscarciudadcode1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarciudadcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarciudadcode1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(direccionparticode1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciudadcode1))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel69)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nacionalidadcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarnacionalidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboestadocivilcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel80)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barriocode1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarbarriocode1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(telefonofijocode1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(telefonomovilcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel75)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(emailcode1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel77)
                            .addComponent(jLabel76))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(direccionlabocode1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(lugarlabocode1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel78)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cargolabocode1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ingresolabocode1))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel66)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombrecode1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(28, 28, 28)
                                .addComponent(documentoidencode1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel72)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fechanacicode1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel32Layout.createSequentialGroup()
                                .addComponent(jLabel71)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(apellidocode1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarciudadcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(nombrecode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71)
                    .addComponent(apellidocode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechanacicode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(documentoidencode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24)
                        .addComponent(jLabel72)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel69)
                                .addComponent(nacionalidadcode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarnacionalidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel73)
                                .addComponent(comboestadocivilcode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel80)
                                .addComponent(barriocode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel68)
                                .addComponent(direccionparticode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel81)
                                .addComponent(ciudadcode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarciudadcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel70)
                            .addComponent(telefonofijocode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel74)
                            .addComponent(telefonomovilcode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel75)
                            .addComponent(emailcode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel76)
                            .addComponent(direccionlabocode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel77)
                            .addComponent(lugarlabocode1)
                            .addComponent(jLabel78)
                            .addComponent(cargolabocode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel79)
                            .addComponent(ingresolabocode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(buscarbarriocode1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        nombreinmuebleeti3.setText("Cta Ctral");

        ctactral.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ctactral.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ctactral.setEnabled(false);

        medidor_ande.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        medidor_ande.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        medidor_ande.setEnabled(false);

        nir.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nir.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nir.setEnabled(false);

        nis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nis.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nis.setEnabled(false);

        telefono.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        telefono.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        telefono.setEnabled(false);

        medidor_essap.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        medidor_essap.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        medidor_essap.setEnabled(false);

        sucursal2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucursal2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sucursal2FocusGained(evt);
            }
        });
        sucursal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucursal2ActionPerformed(evt);
            }
        });
        sucursal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sucursal2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sucursal2KeyReleased(evt);
            }
        });

        buscaredificio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscaredificio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaredificioActionPerformed(evt);
            }
        });

        idedificio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idedificio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idedificioFocusGained(evt);
            }
        });
        idedificio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idedificioActionPerformed(evt);
            }
        });
        idedificio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idedificioKeyPressed(evt);
            }
        });

        nombreinmuebleeti4.setText("Id Edificio");

        nombreinmueble.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreinmueble.setEnabled(false);

        jLabel49.setText("N\" de Personas");

        nropersonas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nropersonas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nropersonasFocusGained(evt);
            }
        });
        nropersonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nropersonasActionPerformed(evt);
            }
        });
        nropersonas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nropersonasKeyPressed(evt);
            }
        });

        jLabel54.setText("Garantia");

        jLabel55.setText("Alquiler");

        alquiler.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        alquiler.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        garantia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        garantia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreinmuebleeti3)
                            .addComponent(nombreinmuebleeti4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ctactral, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(idedificio, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buscaredificio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59)
                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel63)
                            .addComponent(jLabel61))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(medidor_ande, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(medidor_essap))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreinmueble)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(telefono)
                            .addComponent(nir, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(nis, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel49)
                            .addComponent(jLabel54))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(alquiler, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(garantia)
                            .addComponent(nropersonas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addGap(334, 334, 334)
                    .addComponent(sucursal2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(335, Short.MAX_VALUE)))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(nombreinmueble, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60)
                            .addComponent(jLabel55)
                            .addComponent(alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreinmuebleeti4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(idedificio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscaredificio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ctactral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombreinmuebleeti3)))
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(medidor_ande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel22)
                                .addComponent(nir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel54)
                                .addComponent(garantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel61))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(medidor_essap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel63))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel49)
                        .addComponent(nropersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel65)
                        .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(913, 913, 913)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addGap(573, 573, 573)
                    .addComponent(sucursal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(631, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(idsolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel25)
                                        .addGap(18, 18, 18)
                                        .addComponent(fechasolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechasolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(idsolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(50, 50, 50))))
        );

        jTabbedPane1.addTab("Generales", jPanel1);

        jPanel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        GenerarPropietarios.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.GrabarRefLaboral.text")); // NOI18N
        GenerarPropietarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GenerarPropietarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarPropietariosActionPerformed(evt);
            }
        });

        DeletePropietarios.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.DelRefLaboral.text")); // NOI18N
        DeletePropietarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeletePropietarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeletePropietariosActionPerformed(evt);
            }
        });

        codpropietario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codpropietario.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.referencialugartrabajo.text")); // NOI18N
        codpropietario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codpropietarioFocusGained(evt);
            }
        });
        codpropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codpropietarioActionPerformed(evt);
            }
        });
        codpropietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codpropietarioKeyReleased(evt);
            }
        });

        nombrepropietario.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.referenciafonolaboral.text")); // NOI18N
        nombrepropietario.setEnabled(false);

        BuscarPropietarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPropietariosActionPerformed(evt);
            }
        });

        jLabel32.setText("Código");

        jLabel33.setText("Nombre y Apellido");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(codpropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BuscarPropietarios, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel32))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(nombrepropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(DeletePropietarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GenerarPropietarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GenerarPropietarios)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeletePropietarios)
                    .addComponent(codpropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarPropietarios, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrepropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        tablapropietario2.setModel(modelopropietario2);
        jScrollPane14.setViewportView(tablapropietario2);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(289, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Otros Propietarios", jPanel26);

        jPanel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        GenerarJuridica.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.GrabarRefLaboral.text")); // NOI18N
        GenerarJuridica.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GenerarJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarJuridicaActionPerformed(evt);
            }
        });

        DeleteJuridica.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.DelRefLaboral.text")); // NOI18N
        DeleteJuridica.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteJuridicaActionPerformed(evt);
            }
        });

        codjuridica.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codjuridica.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.referencialugartrabajo.text")); // NOI18N
        codjuridica.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codjuridicaFocusGained(evt);
            }
        });
        codjuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codjuridicaActionPerformed(evt);
            }
        });
        codjuridica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codjuridicaKeyReleased(evt);
            }
        });

        nombrejuridica.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.referenciafonolaboral.text")); // NOI18N
        nombrejuridica.setEnabled(false);

        BuscarJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarJuridicaActionPerformed(evt);
            }
        });

        jLabel1.setText("Código");

        jLabel2.setText("Nombre y Apellido");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(codjuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BuscarJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(nombrejuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(DeleteJuridica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GenerarJuridica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GenerarJuridica)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteJuridica)
                    .addComponent(codjuridica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrejuridica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        tablajuridica.setModel(modelojuridica);
        jScrollPane9.setViewportView(tablajuridica);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(289, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Persona Juridica", jPanel21);

        jPanel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        GenerarCodeudor.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.GrabarRefLaboral.text")); // NOI18N
        GenerarCodeudor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GenerarCodeudor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarCodeudorActionPerformed(evt);
            }
        });

        DeleteCodeudor.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.DelRefLaboral.text")); // NOI18N
        DeleteCodeudor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteCodeudor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteCodeudorActionPerformed(evt);
            }
        });

        codcodeudor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codcodeudor.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.referencialugartrabajo.text")); // NOI18N
        codcodeudor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codcodeudorFocusGained(evt);
            }
        });
        codcodeudor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codcodeudorActionPerformed(evt);
            }
        });
        codcodeudor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                codcodeudorKeyReleased(evt);
            }
        });

        nombrecodeudor.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "detalle_clientes.referenciafonolaboral.text")); // NOI18N
        nombrecodeudor.setEnabled(false);

        BuscarCodeudor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCodeudorActionPerformed(evt);
            }
        });

        jLabel4.setText("Código");

        jLabel28.setText("Nombre y Apellido");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(codcodeudor, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BuscarCodeudor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(nombrecodeudor, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(DeleteCodeudor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GenerarCodeudor))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GenerarCodeudor)
                    .addComponent(jLabel4)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteCodeudor)
                    .addComponent(codcodeudor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarCodeudor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrecodeudor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        tablacodeudor2.setModel(modelocodeudor2);
        jScrollPane11.setViewportView(tablacodeudor2);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane11))
                .addContainerGap(289, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Codeudor", jPanel22);

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

        jButton1.setText("Atrás");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Adelante");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonAbriArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GuardarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAbriArchivo)
                    .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(GuardarArchivo)))
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(20, 20, 20))
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

        jTabbedPane1.addTab("Imágenes", jPanel8);

        Grabar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Grabar.setText("Grabar");
        Grabar.setToolTipText("Guardar los Cambios");
        Grabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout detalle_solicitudLayout = new javax.swing.GroupLayout(detalle_solicitud.getContentPane());
        detalle_solicitud.getContentPane().setLayout(detalle_solicitudLayout);
        detalle_solicitudLayout.setHorizontalGroup(
            detalle_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_solicitudLayout.createSequentialGroup()
                .addGroup(detalle_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(detalle_solicitudLayout.createSequentialGroup()
                        .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        detalle_solicitudLayout.setVerticalGroup(
            detalle_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_solicitudLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detalle_solicitudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grabar)
                    .addComponent(Salir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Bcliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Bcliente.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocli.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocliActionPerformed(evt);
            }
        });

        jTBuscarCli.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCli.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCliActionPerformed(evt);
            }
        });
        jTBuscarCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCliKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(combocli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jTBuscarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptarcli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptarcli.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        Aceptarcli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarcliActionPerformed(evt);
            }
        });

        Salircli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salircli.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.SalirCliente.text")); // NOI18N
        Salircli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salircli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalircliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Aceptarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(Salircli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptarcli)
                    .addComponent(Salircli))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane4FocusGained(evt);
            }
        });

        tablacli.setModel(modelocliente);
        tablacli.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablacli.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablacli.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablacli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablacliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablacliFocusLost(evt);
            }
        });
        tablacli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacliMouseClicked(evt);
            }
        });
        tablacli.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablacliPropertyChange(evt);
            }
        });
        tablacli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacliKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablacli);

        javax.swing.GroupLayout BclienteLayout = new javax.swing.GroupLayout(Bcliente.getContentPane());
        Bcliente.getContentPane().setLayout(BclienteLayout);
        BclienteLayout.setHorizontalGroup(
            BclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BclienteLayout.createSequentialGroup()
                .addGroup(BclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        BclienteLayout.setVerticalGroup(
            BclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BclienteLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Bclienteconyu.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Bclienteconyu.setTitle("null");

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboconyu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboconyu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        comboconyu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboconyu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboconyuActionPerformed(evt);
            }
        });

        jTBuscarconyu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarconyu.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarconyu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarconyuActionPerformed(evt);
            }
        });
        jTBuscarconyu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarconyuKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(comboconyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarconyu, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboconyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarconyu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaconyuge.setModel(modeloconyu);
        tablaconyuge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaconyugeMouseClicked(evt);
            }
        });
        tablaconyuge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaconyugeKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(tablaconyuge);

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptarconyu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptarconyu.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        Aceptarconyu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptarconyu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarconyuActionPerformed(evt);
            }
        });

        Salirconyu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salirconyu.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.SalirCliente.text")); // NOI18N
        Salirconyu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salirconyu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirconyuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptarconyu, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(Salirconyu, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptarconyu)
                    .addComponent(Salirconyu))
                .addContainerGap())
        );

        javax.swing.GroupLayout BclienteconyuLayout = new javax.swing.GroupLayout(Bclienteconyu.getContentPane());
        Bclienteconyu.getContentPane().setLayout(BclienteconyuLayout);
        BclienteconyuLayout.setHorizontalGroup(
            BclienteconyuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BclienteconyuLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BclienteconyuLayout.setVerticalGroup(
            BclienteconyuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BclienteconyuLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Bedificio.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Bedificio.setTitle("null");

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboedificio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboedificio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Cta.Ctral" }));
        comboedificio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboedificio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboedificioActionPerformed(evt);
            }
        });

        jTBuscaredificio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscaredificio.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscaredificio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscaredificioActionPerformed(evt);
            }
        });
        jTBuscaredificio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscaredificioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboedificio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscaredificio, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboedificio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscaredificio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tablaedificio.setModel(modeloedificio   );
        tablaedificio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaedificioMouseClicked(evt);
            }
        });
        tablaedificio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaedificioKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(tablaedificio);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptaredif.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptaredif.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        Aceptaredif.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptaredif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptaredifActionPerformed(evt);
            }
        });

        Saliredif.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Saliredif.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.SalirCliente.text")); // NOI18N
        Saliredif.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Saliredif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaliredifActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(Aceptaredif, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Saliredif, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptaredif)
                    .addComponent(Saliredif))
                .addContainerGap())
        );

        javax.swing.GroupLayout BedificioLayout = new javax.swing.GroupLayout(Bedificio.getContentPane());
        Bedificio.getContentPane().setLayout(BedificioLayout);
        BedificioLayout.setHorizontalGroup(
            BedificioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BedificioLayout.createSequentialGroup()
                .addGroup(BedificioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BedificioLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane10))
                    .addGroup(BedificioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        BedificioLayout.setVerticalGroup(
            BedificioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BedificioLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BJuridica.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BJuridica.setTitle("null");

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combojuridica.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combojuridica.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combojuridica.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combojuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combojuridicaActionPerformed(evt);
            }
        });

        jTBuscarJuridica.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarJuridica.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarJuridicaActionPerformed(evt);
            }
        });
        jTBuscarJuridica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarJuridicaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(combojuridica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combojuridica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarJuridica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablajuri.setModel(modelojuri       );
        tablajuri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablajuriMouseClicked(evt);
            }
        });
        tablajuri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablajuriKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablajuri);

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarJuri.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarJuri.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarJuri.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarJuri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarJuriActionPerformed(evt);
            }
        });

        SalirJuri.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirJuri.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirJuri.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirJuri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirJuriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarJuri, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirJuri, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarJuri)
                    .addComponent(SalirJuri))
                .addContainerGap())
        );

        javax.swing.GroupLayout BJuridicaLayout = new javax.swing.GroupLayout(BJuridica.getContentPane());
        BJuridica.getContentPane().setLayout(BJuridicaLayout);
        BJuridicaLayout.setHorizontalGroup(
            BJuridicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BJuridicaLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BJuridicaLayout.setVerticalGroup(
            BJuridicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BJuridicaLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BPropietario.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BPropietario.setTitle("null");

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combopropietario1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combopropietario1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combopropietario1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combopropietario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combopropietario1ActionPerformed(evt);
            }
        });

        jTBuscarPropietario1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPropietario1.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarPropietario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarPropietario1ActionPerformed(evt);
            }
        });
        jTBuscarPropietario1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPropietario1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(combopropietario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPropietario1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combopropietario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPropietario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablapropietario1.setModel(modelopropietario1       );
        tablapropietario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablapropietario1MouseClicked(evt);
            }
        });
        tablapropietario1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablapropietario1KeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(tablapropietario1);

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarPropietario1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarPropietario1.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarPropietario1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarPropietario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarPropietario1ActionPerformed(evt);
            }
        });

        SalirPropietario1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirPropietario1.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirPropietario1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPropietario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPropietario1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarPropietario1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirPropietario1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPropietario1)
                    .addComponent(SalirPropietario1))
                .addContainerGap())
        );

        javax.swing.GroupLayout BPropietarioLayout = new javax.swing.GroupLayout(BPropietario.getContentPane());
        BPropietario.getContentPane().setLayout(BPropietarioLayout);
        BPropietarioLayout.setHorizontalGroup(
            BPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPropietarioLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BPropietarioLayout.setVerticalGroup(
            BPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPropietarioLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCodeudor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCodeudor.setTitle("null");

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocode.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocodeActionPerformed(evt);
            }
        });

        jTBuscarCode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCode.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCodeActionPerformed(evt);
            }
        });
        jTBuscarCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCodeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(combocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCode, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacode.setModel(modelocode       );
        tablacode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacodeMouseClicked(evt);
            }
        });
        tablacode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacodeKeyPressed(evt);
            }
        });
        jScrollPane15.setViewportView(tablacode);

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCode.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCode.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCodeActionPerformed(evt);
            }
        });

        SalirCode.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCode.setText(org.openide.util.NbBundle.getMessage(solicitud_locaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCode, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCode)
                    .addComponent(SalirCode))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCodeudorLayout = new javax.swing.GroupLayout(BCodeudor.getContentPane());
        BCodeudor.getContentPane().setLayout(BCodeudorLayout);
        BCodeudorLayout.setHorizontalGroup(
            BCodeudorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCodeudorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCodeudorLayout.setVerticalGroup(
            BCodeudorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCodeudorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        etiquetainmuebles.setBackground(new java.awt.Color(255, 255, 255));
        etiquetainmuebles.setText("Solicitud Locación");

        jComboBoxnombres.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBoxnombres.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombres", "Apellidos", "N° Documento", "Código" }));
        jComboBoxnombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxnombresActionPerformed(evt);
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
                .addComponent(etiquetainmuebles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(jComboBoxnombres, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(303, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxnombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetainmuebles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        botonsalir.setBackground(new java.awt.Color(255, 255, 255));
        botonsalir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        botonsalir.setText("     Salir");
        botonsalir.setToolTipText("");
        botonsalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonsalirActionPerformed(evt);
            }
        });

        idControl.setEditable(false);

        Agregar.setBackground(new java.awt.Color(255, 255, 255));
        Agregar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Agregar.setText(" Agregar Registro");
        Agregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder("Rango de Fechas"));

        jLabel3.setText("Desde");

        jLabel11.setText("Hasta");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel11)
                .addGap(2, 2, 2)
                .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        refrescar.setBackground(new java.awt.Color(255, 255, 255));
        refrescar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        refrescar.setText("Refrescar");
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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(botonsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Eliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))))
                    .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addComponent(botonsalir)
                .addGap(26, 26, 26)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(refrescar)
                .addContainerGap(71, Short.MAX_VALUE))
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


    private void jComboBoxnombresActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBoxnombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxnombresActionPerformed

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText()).toUpperCase();
                buscarcadena.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBoxnombres.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 2;
                        break;//por Apellidos
                    case 2:
                        indiceColumnaTabla = 4;
                        break;//por Documento
                    case 3:
                        indiceColumnaTabla = 0;
                        break;//por Codigo
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);

    }//GEN-LAST:event_buscarcadenaKeyPressed

    public void limpiar() {

        GuardarArchivo.setEnabled(false);
        this.fechasolicitud.setCalendar(c2);
        this.nacimiento.setCalendar(c2);
        this.nacimientoconyugue.setCalendar(c2);

        //Datos del Solicitante 
        this.idsolicitud.setText("0");
        this.idcliente.setText("0");
        this.codjuridica.setText("0");
        this.codpropietario.setText("0");
        this.nombre.setText("");
        this.ruc.setText("");
        this.estadocivil.setText("SD");
        this.nombreinmueble.setText("SD");
        this.idedificio.setText("0");
        this.direccionparti.setText("SD");
        this.telefonofijo.setText("SD");
        this.telefonomovil.setText("SD");

        //Dato del conyugue
        this.idconyugue.setText("0");
        this.nombreconyu.setText("SD");
        this.rucconyu.setText("SD");
        this.direccionparticonyu.setText("SD");
        this.telefonofijoconyu.setText("SD");
        this.telefonomovilconyu.setText("SD");
        this.observacion.setText("");

        //Datos del Edificio
        this.nropersonas.setText("0");
        this.idedificio.setText("0");
        this.nombreinmueble.setText("");
        this.ctactral.setText("SD");
        this.medidor_ande.setText("SD");
        this.medidor_essap.setText("SD");
        this.nir.setText("SD");
        this.nis.setText("SD");
        this.telefono.setText("SD");
    }

    public void limpiarcodeudor() {
        this.codcodeudor.setText("0");
        this.nombrecodeudor.setText("");
        this.codcodeudor.requestFocus();
    }

    public void limpiarpersonajuridica() {
        this.codjuridica.setText("0");
        this.nombrejuridica.setText("");
        this.codjuridica.requestFocus();
    }

    public void limpiarpropietario2() {
        this.codpropietario.setText("0");
        this.nombrepropietario.setText("");
        this.codpropietario.requestFocus();
    }


    private void jTable1KeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

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

    private void jTable1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_solicitud.setVisible(false);
        detalle_solicitud.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed

        if (idcliente.getText().equals("0") || idcliente.getText().isEmpty()) {
            this.idcliente.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese Codigo del Solicitante");
            return;
        }

        if (idedificio.getText().equals("0") || idedificio.getText().isEmpty()) {
            this.idedificio.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese Codigo de Edificio");
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            Date FechaSolicitud = ODate.de_java_a_sql(fechasolicitud.getDate());
            Date Nacimiento = ODate.de_java_a_sql(nacimiento.getDate());
            Date NacimientoConyugue = ODate.de_java_a_sql(nacimientoconyugue.getDate());

            solicitud_locacionDAO grabar = new solicitud_locacionDAO();
            solicitud_locacion sl = new solicitud_locacion();

            clienteDAO clDAO = new clienteDAO();
            cliente cl = null;

            edificioDAO edDAO = new edificioDAO();
            edificio ed = null;

            try {
                cl = clDAO.buscarId(Integer.valueOf(this.idcliente.getText()));
                ed = edDAO.buscarId(Integer.valueOf(this.idedificio.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            //CAPTURAMOS LOS DATOS DE LA CABECERA

            sl.setFechasolicitud(FechaSolicitud);
            sl.setNacimiento(Nacimiento);
            sl.setNacimientoconyugue(NacimientoConyugue);
            String cDeposito=this.garantia.getText();
            cDeposito=cDeposito.replace(".", "").replace(",", ".");

            String cAlquiler=this.alquiler.getText();
            cAlquiler=cAlquiler.replace(".", "").replace(",", ".");
            
            sl.setDepositogarantia(Double.valueOf(cDeposito));  
            sl.setImportealquiler(Double.valueOf(cAlquiler));
            sl.setIdcliente(cl);
            sl.setIdunidad(ed);

            // Datos del cliente//
            sl.setNombres(String.valueOf(this.nombre.getText()));
            sl.setDireccionparticular(String.valueOf(this.direccionparti.getText()));
            sl.setRuc(String.valueOf(this.ruc.getText()));
            sl.setEstadocivil(String.valueOf(this.estadocivil.getText()));
            sl.setDireccionparticular(String.valueOf(this.direccionparti.getText()));
            sl.setTelefonofijo(String.valueOf(this.telefonomovil.getText()));
            sl.setTelefonofijo(String.valueOf(this.telefonofijo.getText()));

            //Datos de Conyugue///
            sl.setIdconyugue(Integer.valueOf(this.idconyugue.getText()));
            sl.setNombreconyugue(String.valueOf(this.nombreconyu.getText()));
            sl.setDireccionparticularconyugue(String.valueOf(this.direccionparticonyu.getText()));
            sl.setNrodocumentoconyugue(String.valueOf(this.rucconyu.getText()));
            sl.setTelefonomovilconyugue(String.valueOf(this.telefonomovilconyu.getText()));
            sl.setTelefonoconyugue(String.valueOf(this.telefonofijoconyu.getText()));
            sl.setUsuarioalta(1);
            sl.setNrohabitantes(Integer.valueOf(this.nropersonas.getText()));
            sl.setObservaciones(String.valueOf(this.observacion.getText()));
            sl.setNcontroldetalle(0);
            sl.setNcontroljuridica(0);
            sl.setNcontrolpropietario(0);
            //CAPTURAR DETALLE
            int totalRow = modelocodeudor2.getRowCount();
            totalRow -= 1;

            String detalle = "[";
            for (int i = 0; i <= (totalRow); i++) {
                //Capturo y valido Producto
                String cCodeudor = modelocodeudor2.getValueAt(i, 0).toString();
                //Capturo cantidad    
                String linea = "{idcliente : " + cCodeudor + "},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                sl.setNcontroldetalle(1);
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            /////////////////////////////////////

            //CAPTURAR JURIDICA
            int totaljuri = modelojuridica.getRowCount();
            totaljuri -= 1;

            String juridica = "[";
            for (int i = 0; i <= (totaljuri); i++) {
                //Capturo y valido Producto
                String cJuridica = modelojuridica.getValueAt(i, 0).toString();
                //Capturo cantidad    
                String linea = "{idcliente : " + cJuridica + "},";
                juridica += linea;
            }
            if (!juridica.equals("[")) {
                sl.setNcontroljuridica(1);
                juridica = juridica.substring(0, juridica.length() - 1);
            }
            juridica += "]";
            /////////////////////////////////////

            //CAPTURAR PROPIETARIO
            int totalprop = modelopropietario2.getRowCount();
            totalprop -= 1;

            String propietario = "[";
            for (int i = 0; i <= (totalprop); i++) {
                //Capturo y valido Producto
                String cPropietario = modelopropietario2.getValueAt(i, 0).toString();
                //Capturo cantidad    
                String linea = "{idcliente : " + cPropietario + "},";
                propietario += linea;
            }
            if (!propietario.equals("[")) {
                sl.setNcontrolpropietario(1);
                propietario = propietario.substring(0, propietario.length() - 1);
            }
            propietario += "]";
            /////////////////////////////////////

            if (Integer.valueOf(this.idsolicitud.getText()) == 0) {
                try {
                    grabar.insertarSolicitud(sl, detalle, juridica, propietario);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            } else {
                //Actualizar Orden de Credito
                try {
                    sl.setIdsolicitud(Integer.valueOf(this.idsolicitud.getText()));
                    solicitud_locacion_codeudorDAO delDAO = new solicitud_locacion_codeudorDAO();
                    solicitud_locacion_juridicaDAO deljDAO = new solicitud_locacion_juridicaDAO();
                    solicitud_locacion_propietarioDAO delpDAO = new solicitud_locacion_propietarioDAO();
                    delDAO.borrarDetalle(sl.getIdsolicitud());
                    deljDAO.borrarJuridica(sl.getIdsolicitud());
                    delpDAO.borrarPropietario(sl.getIdsolicitud());
                    grabar.ActualizarAsiento(sl, detalle, juridica, propietario);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            }
            detalle_solicitud.setVisible(false);
            this.detalle_solicitud.setModal(false);
            GrillaSolicitud GrillaP = new GrillaSolicitud();
            Thread HiloGrilla = new Thread(GrillaP);
            HiloGrilla.start();

        }

    }//GEN-LAST:event_GrabarActionPerformed


    private void detalle_solicitudFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_solicitudFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_solicitudFocusGained

    private void detalle_solicitudWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_solicitudWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_solicitudWindowGainedFocus

    private void detalle_solicitudWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_solicitudWindowActivated
        GrillaCodeudor GrillaL = new GrillaCodeudor();
        Thread Hilo2 = new Thread(GrillaL);
        Hilo2.start();

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_solicitudWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    public void filtrojuridica(int nNumeroColumna) {
        trsfiltrojuridica.setRowFilter(RowFilter.regexFilter(jTBuscarJuridica.getText(), nNumeroColumna));
    }

    public void filtropropietario1(int nNumeroColumna) {
        trsfiltropropietario1.setRowFilter(RowFilter.regexFilter(jTBuscarPropietario1.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(jTBuscarCli.getText(), nNumeroColumna));
    }

    public void filtrocliconyu(int nNumeroColumna) {
        trsfiltroconyu.setRowFilter(RowFilter.regexFilter(jTBuscarconyu.getText(), nNumeroColumna));
    }

    public void filtroedificio(int nNumeroColumna) {
        trsfiltroedificio.setRowFilter(RowFilter.regexFilter(jTBuscaredificio.getText(), nNumeroColumna));
    }

    public void filtrocodeudor(int nNumeroColumna) {
        trsfiltrocodeudor.setRowFilter(RowFilter.regexFilter(jTBuscarCode.getText(), nNumeroColumna));
    }


    private void botonsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonsalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_botonsalirActionPerformed

    private void ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        GenerarLibro Generar = new GenerarLibro();
        Thread HiloReporte = new Thread(Generar);
        HiloReporte.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {

            int nFila = jTable1.getSelectedRow();
            String num = jTable1.getValueAt(nFila, 0).toString();

            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                solicitud_locacion_codeudorDAO slc = new solicitud_locacion_codeudorDAO();
                solicitud_locacionDAO sl = new solicitud_locacionDAO();

                try {
                    solicitud_locacion l = sl.buscarSolicitud_locacion(num);
                    if (l == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        slc.borrarcodeudorSL(num);
                        sl.borrarAjustes(num);

                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
            GrillaSolicitud GrillaOC = new GrillaSolicitud();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();
        } else {

            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }

    }//GEN-LAST:event_EliminarActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        cModo = "M";
        // if (Integer.valueOf(Config.cNivelUsuario) < 3) {
        this.limpiar();
        // if (Integer.valueOf(Config.cNivelUsuario) < 3) {
        int nFila = this.jTable1.getSelectedRow();

        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.jTable1.requestFocus();
            return;
        } else {
            this.idsolicitud.setText(this.jTable1.getValueAt(nFila, 0).toString());
        }
        this.idsolicitud.setEnabled(false);
        solicitud_locacionDAO slDAO = new solicitud_locacionDAO();
        solicitud_locacion sl = null;

        try {
            sl = slDAO.buscarSolicitud_locacion(this.idsolicitud.getText());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (sl != null) {
            //System.out.println("Numero --- " + gu.getNumero());
            idsolicitud.setText(String.valueOf(sl.getIdsolicitud()));
            fechasolicitud.setDate(sl.getFechasolicitud());

            idcliente.setText(String.valueOf(sl.getIdcliente().getCodigo()));
            nacimiento.setDate(sl.getIdcliente().getFechanacimiento());
            nombre.setText(sl.getIdcliente().getNombre());
            direccionparti.setText(sl.getIdcliente().getDireccion());
            ruc.setText(sl.getIdcliente().getRuc());
            estadocivil.setText(sl.getIdcliente().getEstadocivil());
            telefonomovil.setText(sl.getIdcliente().getCelular());
            telefonofijo.setText(sl.getIdcliente().getTelefono());

            //datos del conyugue
            idconyugue.setText(String.valueOf(sl.getIdconyugue()));
            nacimientoconyugue.setDate(sl.getNacimientoconyugue());
            nombreconyu.setText(sl.getNombreconyugue());
            direccionparticonyu.setText(sl.getDireccionparticularconyugue());
            rucconyu.setText(sl.getNrodocumentoconyugue());
            telefonofijoconyu.setText(sl.getTelefonoconyugue());
            telefonomovilconyu.setText(sl.getTelefonomovilconyugue());

            //datos del edificio
            idedificio.setText(String.valueOf(sl.getIdunidad().getIdunidad()));
            nombreinmueble.setText(sl.getNombreinmueble());
            nropersonas.setText(String.valueOf(sl.getNrohabitantes()));
            ctactral.setText(sl.getIdunidad().getCtactral());
            nis.setText(sl.getIdunidad().getNis());
            nir.setText(sl.getIdunidad().getNir());
            telefono.setText(sl.getIdunidad().getTelunid());
            medidor_essap.setText(sl.getIdunidad().getMedcorpo());
            medidor_ande.setText(sl.getIdunidad().getMedande());
            alquiler.setText(formatea.format(sl.getIdunidad().getAlquiler()));
            garantia.setText(formatea.format(sl.getIdunidad().getDepgtia()));
            observacion.setText(String.valueOf(sl.getObservaciones()));

            GuardarArchivo.setEnabled(true);
            // SE CARGAN LOS DETALLES
            GrillaCodeudor GrillaL = new GrillaCodeudor();
            Thread Hilo2 = new Thread(GrillaL);
            Hilo2.start();
            // SE CARGAN LAS PERSONAS JURIDICAS
            GrillaJuridica GrillaJ = new GrillaJuridica();
            Thread Hilo3 = new Thread(GrillaJ);
            Hilo3.start();
            //(Ancho,Alto)
            detalle_solicitud.setSize(1080, 580);
            //Establecemos un título para el jDialog
            detalle_solicitud.setTitle("Modificar Solicitud Locaciones");
            detalle_solicitud.setLocationRelativeTo(null);
            detalle_solicitud.setVisible(true);

            GrillaSolicitud GrillaOC = new GrillaSolicitud();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();

        } else {
            JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
        }
        // } else {
        // JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        //  }

    }//GEN-LAST:event_ModificarActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed

        this.limpiar();
        detalle_solicitud.setModal(true);
        //                       (Ancho,Alto)
        detalle_solicitud.setSize(1075, 560);
        //Establecemos un título para el jDialog
        detalle_solicitud.setTitle("Agregar Solicitud");
        detalle_solicitud.setLocationRelativeTo(null);
        detalle_solicitud.setVisible(true);
        idcliente.requestFocus();

    }//GEN-LAST:event_AgregarActionPerformed

    private void combocliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocliActionPerformed

    private void jTBuscarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCliActionPerformed

    private void jTBuscarCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCliKeyPressed
        this.jTBuscarCli.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCli.getText()).toUpperCase();
                jTBuscarCli.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocli.getSelectedIndex()) {
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
        trsfiltrocli = new TableRowSorter(tablacli.getModel());
        tablacli.setRowSorter(trsfiltrocli);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCliKeyPressed

    private void AceptarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarcliActionPerformed
        int nFila = this.tablacli.getSelectedRow();
        this.idcliente.setText(this.tablacli.getValueAt(nFila, 0).toString());
        this.nombre.setText(this.tablacli.getValueAt(nFila, 1).toString());
        this.ruc.setText(this.tablacli.getValueAt(nFila, 2).toString());
        try {
            nacimiento.setDate(formatoFecha.parse(this.tablacli.getValueAt(nFila, 3).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.estadocivil.setText(this.tablacli.getValueAt(nFila, 4).toString());
        this.direccionparti.setText(this.tablacli.getValueAt(nFila, 5).toString());
        this.telefonofijo.setText(this.tablacli.getValueAt(nFila, 6).toString());
        this.telefonomovil.setText(this.tablacli.getValueAt(nFila, 7).toString());

        this.Bcliente.setVisible(false);
        this.jTBuscarCli.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarcliActionPerformed

    private void SalircliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalircliActionPerformed
        this.Bcliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalircliActionPerformed

    private void comboconyuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboconyuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboconyuActionPerformed

    private void jTBuscarconyuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarconyuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarconyuActionPerformed

    private void jTBuscarconyuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarconyuKeyPressed
        this.jTBuscarconyu.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarconyu.getText()).toUpperCase();
                jTBuscarconyu.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboconyu.getSelectedIndex()) {
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
                filtroclienteconyu(indiceColumnaTabla);
            }
        });
        trsfiltroconyu = new TableRowSorter(tablaconyuge.getModel());
        tablaconyuge.setRowSorter(trsfiltroconyu);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarconyuKeyPressed

    private void tablaconyugeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaconyugeMouseClicked
        this.Aceptarconyu.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaconyugeMouseClicked

    private void tablaconyugeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaconyugeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptarconyu.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaconyugeKeyPressed

    private void AceptarconyuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarconyuActionPerformed
        int nFila = this.tablaconyuge.getSelectedRow();
        this.idconyugue.setText(this.tablaconyuge.getValueAt(nFila, 0).toString());
        this.nombreconyu.setText(this.tablaconyuge.getValueAt(nFila, 1).toString());
        this.rucconyu.setText(this.tablaconyuge.getValueAt(nFila, 2).toString());
        try {
            nacimientoconyugue.setDate(formatoFecha.parse(this.tablaconyuge.getValueAt(nFila, 3).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.direccionparticonyu.setText(this.tablaconyuge.getValueAt(nFila, 4).toString());
        this.telefonofijoconyu.setText(this.tablaconyuge.getValueAt(nFila, 5).toString());
        this.telefonomovilconyu.setText(this.tablaconyuge.getValueAt(nFila, 6).toString());
        this.Bclienteconyu.setVisible(false);
        this.jTBuscarconyu.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarconyuActionPerformed

    private void SalirconyuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirconyuActionPerformed
        this.Bclienteconyu.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirconyuActionPerformed

    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaSolicitud GrillaOC = new GrillaSolicitud();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();        // TODO add your handling code here:
    }//GEN-LAST:event_refrescarActionPerformed

    private void jScrollPane4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane4FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane4FocusGained

    private void tablacliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacliKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptarcli.doClick();
        }

    }//GEN-LAST:event_tablacliKeyPressed

    private void tablacliPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablacliPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacliPropertyChange

    private void tablacliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacliMouseClicked
        this.Aceptarcli.doClick();
    }//GEN-LAST:event_tablacliMouseClicked

    private void tablacliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablacliFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacliFocusLost

    private void tablacliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablacliFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacliFocusGained

    private void comboedificioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboedificioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboedificioActionPerformed

    private void jTBuscaredificioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscaredificioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscaredificioActionPerformed

    private void jTBuscaredificioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscaredificioKeyPressed
        this.jTBuscaredificio.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscaredificio.getText()).toUpperCase();
                jTBuscaredificio.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboedificio.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 2;
                        break;//por cta ctral
                }
                repaint();
                filtroedificio(indiceColumnaTabla);
            }
        });
        trsfiltroedificio = new TableRowSorter(tablaedificio.getModel());
        tablaedificio.setRowSorter(trsfiltroedificio);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscaredificioKeyPressed

    private void tablaedificioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaedificioMouseClicked
        this.Aceptaredif.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaedificioMouseClicked

    private void tablaedificioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaedificioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptaredif.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaedificioKeyPressed

    private void AceptaredifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptaredifActionPerformed
        int nFila = this.tablaedificio.getSelectedRow();
        this.idedificio.setText(this.tablaedificio.getValueAt(nFila, 0).toString());
        this.nombreinmueble.setText(this.tablaedificio.getValueAt(nFila, 1).toString());
        this.ctactral.setText(this.tablaedificio.getValueAt(nFila, 2).toString());
        this.medidor_ande.setText(this.tablaedificio.getValueAt(nFila, 3).toString());
        this.medidor_essap.setText(this.tablaedificio.getValueAt(nFila, 4).toString());
        this.nir.setText(this.tablaedificio.getValueAt(nFila, 5).toString());
        this.nis.setText(this.tablaedificio.getValueAt(nFila, 6).toString());
        this.telefono.setText(this.tablaedificio.getValueAt(nFila, 7).toString());
        this.alquiler.setText(this.tablaedificio.getValueAt(nFila, 8).toString());
        this.garantia.setText(this.tablaedificio.getValueAt(nFila, 9).toString());
        this.Bedificio.setVisible(false);
        this.jTBuscaredificio.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptaredifActionPerformed

    private void SaliredifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaliredifActionPerformed
        this.Bedificio.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SaliredifActionPerformed

    private void combojuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combojuridicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combojuridicaActionPerformed

    private void jTBuscarJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarJuridicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarJuridicaActionPerformed

    private void jTBuscarJuridicaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarJuridicaKeyPressed
        this.jTBuscarJuridica.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarJuridica.getText()).toUpperCase();
                jTBuscarJuridica.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combojuridica.getSelectedIndex()) {
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
                filtrojuridica(indiceColumnaTabla);
            }
        });
        trsfiltrojuridica = new TableRowSorter(tablajuridica.getModel());
        tablajuridica.setRowSorter(trsfiltrojuridica);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarJuridicaKeyPressed

    private void tablajuriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablajuriMouseClicked
        this.AceptarJuri.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablajuriMouseClicked

    private void tablajuriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablajuriKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarJuri.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablajuriKeyPressed

    private void AceptarJuriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarJuriActionPerformed
        int nFila = this.tablajuri.getSelectedRow();
        this.codjuridica.setText(this.tablajuri.getValueAt(nFila, 0).toString());
        this.nombrejuridica.setText(this.tablajuri.getValueAt(nFila, 1).toString());
        this.BJuridica.setVisible(false);
        this.jTBuscarJuridica.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarJuriActionPerformed

    private void SalirJuriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirJuriActionPerformed
        this.BJuridica.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirJuriActionPerformed

    private void combopropietario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combopropietario1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combopropietario1ActionPerformed

    private void jTBuscarPropietario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarPropietario1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPropietario1ActionPerformed

    private void jTBuscarPropietario1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPropietario1KeyPressed
        this.jTBuscarPropietario1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarPropietario1.getText()).toUpperCase();
                jTBuscarPropietario1.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combopropietario1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtropropietario1(indiceColumnaTabla);
            }
        });
        trsfiltropropietario1 = new TableRowSorter(tablapropietario1.getModel());
        tablapropietario1.setRowSorter(trsfiltropropietario1);
    }//GEN-LAST:event_jTBuscarPropietario1KeyPressed

    private void tablapropietario1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablapropietario1MouseClicked
        this.AceptarPropietario1.doClick();
    }//GEN-LAST:event_tablapropietario1MouseClicked

    private void tablapropietario1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablapropietario1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPropietario1.doClick();
        }
    }//GEN-LAST:event_tablapropietario1KeyPressed

    private void AceptarPropietario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarPropietario1ActionPerformed
        int nFila = this.tablapropietario1.getSelectedRow();
        this.codpropietario.setText(this.tablapropietario1.getValueAt(nFila, 0).toString());
        this.nombrepropietario.setText(this.tablapropietario1.getValueAt(nFila, 1).toString());
        this.BPropietario.setVisible(false);
        this.jTBuscarPropietario1.setText("");
    }//GEN-LAST:event_AceptarPropietario1ActionPerformed

    private void SalirPropietario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPropietario1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPropietario1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        contador++;
        refrescarCarrusel(contador);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if ((contador - 1) >= 0) {
            contador--;
            refrescarCarrusel(contador);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void GuardarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarArchivoActionPerformed

        albumfoto_solicitud_locacionDAO GrabarImagen = new albumfoto_solicitud_locacionDAO();
        albumfoto_solicitud_locacion alb = new albumfoto_solicitud_locacion();

        solicitud_locacionDAO slDAO = new solicitud_locacionDAO();
        solicitud_locacion sl = null;
        try {
            sl = slDAO.buscarSolicitud_locacion(String.valueOf(idsolicitud.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        alb.setNrosolicitud(sl);
        alb.setNombre(nombrearchivo.getText().toString());
        try {
            GrabarImagen.eliminarFoto(idsolicitud.getText());
            GrabarImagen.insertarimagen(alb, nombrearchivo.getText().toString());
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            nombrearchivo.setText("");
            imagen.setIcon(null);
            refrescarCarrusel(0);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarArchivoActionPerformed

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

    private void BuscarPropietariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPropietariosActionPerformed
        if (this.codpropietario.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código de la Persona Juridica");
            this.codpropietario.requestFocus();
            return;
        }

        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codpropietario.getText()));
            if (cl.getCodigo() == 0) {
                nPro = 2;
                GrillaBuscarPropietario grillapro = new GrillaBuscarPropietario();
                Thread hilopro = new Thread(grillapro);
                hilopro.start();
                BPropietario.setModal(true);
                BPropietario.setSize(482, 575);
                BPropietario.setLocationRelativeTo(null);
                BPropietario.setVisible(true);
                BPropietario.setTitle("Buscar Propietario");
                BPropietario.requestFocus();
                BPropietario.setModal(false);
            } else {
                nombrepropietario.setText(cl.getNombre());
                //Establecemos un título para el jDialog
                codpropietario.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarPropietariosActionPerformed

    private void codpropietarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codpropietarioKeyReleased
        String letras = ConvertirMayusculas.cadena(codpropietario);
        codpropietario.setText(letras);
    }//GEN-LAST:event_codpropietarioKeyReleased

    private void codpropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codpropietarioActionPerformed
        this.BuscarPropietarios.doClick();
    }//GEN-LAST:event_codpropietarioActionPerformed

    private void codpropietarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codpropietarioFocusGained
        codpropietario.selectAll();
    }//GEN-LAST:event_codpropietarioFocusGained

    private void DeletePropietariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeletePropietariosActionPerformed
        DefaultTableModel modeloprop = (DefaultTableModel) tablapropietario2.getModel();
        int indFil = tablapropietario2.getSelectedRow();
        if (indFil >= 0) {
            modeloprop.removeRow(indFil);
        }
    }//GEN-LAST:event_DeletePropietariosActionPerformed

    private void GenerarPropietariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarPropietariosActionPerformed
        if (codpropietario.getText().equals("0") || codpropietario.getText().isEmpty()) {
            this.codpropietario.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese Codigo de la Persona Juridica");
            return;
        }
        DefaultTableModel modeloprop = (DefaultTableModel) tablapropietario2.getModel();
        //modelo.addRow(new Object[]{codcodeudor.getText(),nombrecodeudor.getText()});
        Object[] nFila = new Object[2];
        nFila[0] = codpropietario.getText();
        nFila[1] = nombrepropietario.getText();
        modeloprop.addRow(nFila);
        tablapropietario2.setModel(modeloprop);
        limpiarpropietario2();
    }//GEN-LAST:event_GenerarPropietariosActionPerformed

    private void nropersonasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nropersonasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!idedificio.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                observacion.setEnabled(true);
                observacion.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Ingrese N° de Personas", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_nropersonasKeyPressed

    private void nropersonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nropersonasActionPerformed

    }//GEN-LAST:event_nropersonasActionPerformed

    private void nropersonasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nropersonasFocusGained
        sucursal.selectAll();
    }//GEN-LAST:event_nropersonasFocusGained

    private void idedificioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idedificioKeyPressed

    }//GEN-LAST:event_idedificioKeyPressed

    private void idedificioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idedificioActionPerformed
        this.buscaredificio.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_idedificioActionPerformed

    private void idedificioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idedificioFocusGained
        idedificio.selectAll();
    }//GEN-LAST:event_idedificioFocusGained

    private void buscaredificioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaredificioActionPerformed
        edificioDAO edifDAO = new edificioDAO();
        edificio edif = null;
        try {
            edif = edifDAO.buscarId(Integer.valueOf(this.idedificio.getText()));
            if (edif.getIdunidad() == 0) {
                GrillaEdificio grillaed = new GrillaEdificio();
                Thread hiloed = new Thread(grillaed);
                hiloed.start();
                Bedificio.setModal(true);
                Bedificio.setSize(700, 588);
                Bedificio.setLocationRelativeTo(null);
                Bedificio.setTitle("Buscar Edificio");
                Bedificio.setVisible(true);
                //                giraduria.requestFocus();
                Bedificio.setModal(false);
            } else {
                nombreinmueble.setText(edif.getInmueble().getNomedif());
                ctactral.setText(edif.getCtactral());
                medidor_ande.setText(edif.getMedande());
                medidor_essap.setText(edif.getMedcorpo());
                nir.setText(edif.getNir());
                nis.setText(edif.getNis());
                telefono.setText(edif.getTelunid());
                alquiler.setText(formatea.format(edif.getAlquiler()));
                garantia.setText(formatea.format(edif.getDepgtia()));
                //Establecemos un título para el jDialog
            }
            nropersonas.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscaredificioActionPerformed

    private void sucursal2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursal2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursal2KeyReleased

    private void sucursal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursal2KeyPressed

    private void sucursal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursal2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursal2ActionPerformed

    private void sucursal2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursal2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursal2FocusGained

    private void buscarciudadcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarciudadcode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarciudadcode1ActionPerformed

    private void buscarbarriocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarbarriocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarbarriocode1ActionPerformed

    private void ciudadcode1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ciudadcode1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcode1KeyReleased

    private void ciudadcode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ciudadcode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcode1KeyPressed

    private void ciudadcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ciudadcode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcode1ActionPerformed

    private void ciudadcode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ciudadcode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcode1FocusGained

    private void barriocode1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barriocode1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocode1KeyReleased

    private void barriocode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barriocode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocode1KeyPressed

    private void barriocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barriocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocode1ActionPerformed

    private void barriocode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_barriocode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocode1FocusGained

    private void fechanacicode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechanacicode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechanacicode1KeyPressed

    private void fechanacicode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechanacicode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechanacicode1FocusGained

    private void ingresolabocode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingresolabocode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresolabocode1KeyPressed

    private void ingresolabocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresolabocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresolabocode1ActionPerformed

    private void ingresolabocode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ingresolabocode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresolabocode1FocusGained

    private void cargolabocode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cargolabocode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cargolabocode1KeyPressed

    private void cargolabocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargolabocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cargolabocode1ActionPerformed

    private void cargolabocode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cargolabocode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_cargolabocode1FocusGained

    private void lugarlabocode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lugarlabocode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lugarlabocode1KeyPressed

    private void lugarlabocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lugarlabocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lugarlabocode1ActionPerformed

    private void lugarlabocode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lugarlabocode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_lugarlabocode1FocusGained

    private void direccionlabocode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionlabocode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionlabocode1KeyPressed

    private void direccionlabocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionlabocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionlabocode1ActionPerformed

    private void direccionlabocode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionlabocode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionlabocode1FocusGained

    private void emailcode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailcode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailcode1KeyPressed

    private void emailcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailcode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailcode1ActionPerformed

    private void emailcode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailcode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_emailcode1FocusGained

    private void telefonomovilcode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonomovilcode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonomovilcode1KeyPressed

    private void telefonomovilcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonomovilcode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonomovilcode1ActionPerformed

    private void telefonomovilcode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonomovilcode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonomovilcode1FocusGained

    private void buscarnacionalidad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarnacionalidad1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarnacionalidad1ActionPerformed

    private void apellidocode1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidocode1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocode1KeyReleased

    private void apellidocode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidocode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocode1KeyPressed

    private void apellidocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocode1ActionPerformed

    private void apellidocode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_apellidocode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocode1FocusGained

    private void direccionparticode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionparticode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionparticode1KeyPressed

    private void direccionparticode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionparticode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionparticode1ActionPerformed

    private void direccionparticode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionparticode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionparticode1FocusGained

    private void nacionalidadcode1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacionalidadcode1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcode1KeyReleased

    private void nacionalidadcode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacionalidadcode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcode1KeyPressed

    private void nacionalidadcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nacionalidadcode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcode1ActionPerformed

    private void nacionalidadcode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nacionalidadcode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcode1FocusGained

    private void telefonofijocode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonofijocode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonofijocode1KeyPressed

    private void telefonofijocode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonofijocode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonofijocode1ActionPerformed

    private void telefonofijocode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonofijocode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonofijocode1FocusGained

    private void documentoidencode1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_documentoidencode1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoidencode1KeyReleased

    private void documentoidencode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_documentoidencode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoidencode1KeyPressed

    private void documentoidencode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentoidencode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoidencode1ActionPerformed

    private void documentoidencode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_documentoidencode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoidencode1FocusGained

    private void nombrecode1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrecode1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecode1KeyReleased

    private void nombrecode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrecode1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecode1KeyPressed

    private void nombrecode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombrecode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecode1ActionPerformed

    private void nombrecode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombrecode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecode1FocusGained

    private void buscarconyugueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarconyugueActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.idconyugue.getText()));
            if (cl.getCodigo() == 0) {
                GrillaConyuge grillacon = new GrillaConyuge();
                Thread hilocon = new Thread(grillacon);
                hilocon.start();
                Bclienteconyu.setModal(true);
                Bclienteconyu.setSize(500, 575);
                Bclienteconyu.setLocationRelativeTo(null);
                Bclienteconyu.setTitle("Buscar Datos Conyugue");
                Bclienteconyu.setVisible(true);
                //                giraduria.requestFocus();
                Bclienteconyu.setModal(false);
            } else {
                nombreconyu.setText(cl.getNombre());
                rucconyu.setText(cl.getRuc());
                nacimientoconyugue.setDate(cl.getFechanacimiento());
                direccionparticonyu.setText(cl.getDireccion());
                telefonofijoconyu.setText(cl.getTelefono());
                telefonomovilconyu.setText(cl.getCelular());
                //Establecemos un título para el jDialog
            }
            idedificio.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarconyugueActionPerformed

    private void idconyugueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idconyugueKeyPressed
        /*  if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nuevoitem.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.primervence.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_idconyugueKeyPressed

    private void idconyugueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idconyugueActionPerformed
        this.buscarconyugue.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_idconyugueActionPerformed

    private void idconyugueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idconyugueFocusGained
        idconyugue.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_idconyugueFocusGained

    private void nacimientoconyugueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientoconyugueKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoconyugueKeyPressed

    private void nacimientoconyugueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nacimientoconyugueFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoconyugueFocusGained

    private void idsolicitudKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idsolicitudKeyReleased
        String letras = ConvertirMayusculas.cadena(idsolicitud);
        idsolicitud.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_idsolicitudKeyReleased

    private void idsolicitudKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idsolicitudKeyPressed
        /* if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nombretipo.requestFocus();
        }*/
        // TODO add your handling code here:
    }//GEN-LAST:event_idsolicitudKeyPressed

    private void idsolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idsolicitudActionPerformed
        /*        propietarioDAO pdDAO = new propietarioDAO();
        propietario pd = null;
        try {
            pd = pdDAO.BuscarProducto(this.codpro.getText());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (pd.getCodigo() != null) {
            JOptionPane.showMessageDialog(null, "Este Producto ya Existe");
            this.codpro.requestFocus();
            return;
        }*/
    }//GEN-LAST:event_idsolicitudActionPerformed

    private void idsolicitudFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idsolicitudFocusGained
        idsolicitud.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_idsolicitudFocusGained

    private void idclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idclienteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_idclienteKeyReleased

    private void idclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idclienteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nacimiento.requestFocus();
        }
    }//GEN-LAST:event_idclienteKeyPressed

    private void idclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idclienteActionPerformed
        this.buscarcliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_idclienteActionPerformed

    private void idclienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idclienteFocusGained
        idcliente.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_idclienteFocusGained

    private void sucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyReleased

    private void sucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed

        // jTBuscarSucursal.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void rucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rucActionPerformed

    private void buscarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarclienteActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.idcliente.getText()));
            if (cl.getCodigo() == 0) {
                GrillaCliente grillacl = new GrillaCliente();
                Thread hilocl = new Thread(grillacl);
                hilocl.start();
                Bcliente.setModal(true);
                Bcliente.setSize(684, 575);
                Bcliente.setLocationRelativeTo(null);
                Bcliente.setTitle("Buscar Solicitante");
                Bcliente.setVisible(true);
                idedificio.requestFocus();
                Bcliente.setModal(false);
            } else {
                nombre.setText(cl.getNombre());
                ruc.setText(cl.getRuc());
                nacimiento.setDate(cl.getFechanacimiento());
                estadocivil.setText(cl.getEstadocivil());
                direccionparti.setText(cl.getDireccion());
                telefonofijo.setText(cl.getTelefono());
                telefonomovil.setText(cl.getCelular());
                //Establecemos un título para el jDialog
            }
            idconyugue.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarclienteActionPerformed

    private void buscarciudadcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarciudadcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarciudadcodeActionPerformed

    private void buscarbarriocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarbarriocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarbarriocodeActionPerformed

    private void ciudadcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ciudadcodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcodeKeyReleased

    private void ciudadcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ciudadcodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcodeKeyPressed

    private void ciudadcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ciudadcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcodeActionPerformed

    private void ciudadcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ciudadcodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadcodeFocusGained

    private void barriocodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barriocodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocodeKeyReleased

    private void barriocodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barriocodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocodeKeyPressed

    private void barriocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barriocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocodeActionPerformed

    private void barriocodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_barriocodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_barriocodeFocusGained

    private void fechanacicodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechanacicodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechanacicodeKeyPressed

    private void fechanacicodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechanacicodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechanacicodeFocusGained

    private void ingresolabocodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingresolabocodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresolabocodeKeyPressed

    private void ingresolabocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresolabocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresolabocodeActionPerformed

    private void ingresolabocodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ingresolabocodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresolabocodeFocusGained

    private void cargolabocodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cargolabocodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cargolabocodeKeyPressed

    private void cargolabocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargolabocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cargolabocodeActionPerformed

    private void cargolabocodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cargolabocodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_cargolabocodeFocusGained

    private void lugarlabocodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lugarlabocodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lugarlabocodeKeyPressed

    private void lugarlabocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lugarlabocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lugarlabocodeActionPerformed

    private void lugarlabocodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lugarlabocodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_lugarlabocodeFocusGained

    private void direccionlabocodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionlabocodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionlabocodeKeyPressed

    private void direccionlabocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionlabocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionlabocodeActionPerformed

    private void direccionlabocodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionlabocodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionlabocodeFocusGained

    private void emailcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailcodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailcodeKeyPressed

    private void emailcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailcodeActionPerformed

    private void emailcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_emailcodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_emailcodeFocusGained

    private void telefonomovilcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonomovilcodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonomovilcodeKeyPressed

    private void telefonomovilcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonomovilcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonomovilcodeActionPerformed

    private void telefonomovilcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonomovilcodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonomovilcodeFocusGained

    private void buscarnacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarnacionalidadActionPerformed

    }//GEN-LAST:event_buscarnacionalidadActionPerformed

    private void apellidocodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidocodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocodeKeyReleased

    private void apellidocodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidocodeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocodeKeyPressed

    private void apellidocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocodeActionPerformed

    private void apellidocodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_apellidocodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidocodeFocusGained

    private void direccionparticodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionparticodeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.telefonofijocode.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nacionalidadcode.requestFocus();
        }   // TO        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_direccionparticodeKeyPressed

    private void direccionparticodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionparticodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionparticodeActionPerformed

    private void direccionparticodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionparticodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionparticodeFocusGained

    private void nacionalidadcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacionalidadcodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcodeKeyReleased

    private void nacionalidadcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacionalidadcodeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.direccionparticode.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.documentoidencode.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcodeKeyPressed

    private void nacionalidadcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nacionalidadcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcodeActionPerformed

    private void nacionalidadcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nacionalidadcodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadcodeFocusGained

    private void telefonofijocodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonofijocodeKeyPressed
        /*   if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tipinmueble.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.direccionparticode.requestFocus();
        }*/
    }//GEN-LAST:event_telefonofijocodeKeyPressed

    private void telefonofijocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonofijocodeActionPerformed
        // this.BuscarTipo.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonofijocodeActionPerformed

    private void telefonofijocodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonofijocodeFocusGained
        telefonofijocode.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_telefonofijocodeFocusGained

    private void documentoidencodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_documentoidencodeKeyReleased
        String letras = ConvertirMayusculas.cadena(documentoidencode);
        documentoidencode.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoidencodeKeyReleased

    private void documentoidencodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_documentoidencodeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nacionalidadcode.requestFocus();
        }
    }//GEN-LAST:event_documentoidencodeKeyPressed

    private void documentoidencodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentoidencodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoidencodeActionPerformed

    private void documentoidencodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_documentoidencodeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoidencodeFocusGained

    private void nombrecodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrecodeKeyReleased
        String letras = ConvertirMayusculas.cadena(nombrecode);
        nombrecode.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecodeKeyReleased

    private void nombrecodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrecodeKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecodeKeyPressed

    private void nombrecodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombrecodeActionPerformed
        /*        propietarioDAO pdDAO = new propietarioDAO();
        propietario pd = null;
        try {
            pd = pdDAO.BuscarProducto(this.codpro.getText());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (pd.getCodigo() != null) {
            JOptionPane.showMessageDialog(null, "Este Producto ya Existe");
            this.codpro.requestFocus();
            return;
        }*/
    }//GEN-LAST:event_nombrecodeActionPerformed

    private void nombrecodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombrecodeFocusGained
        nombrecode.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecodeFocusGained

    private void nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoKeyPressed

    private void nacimientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nacimientoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoFocusGained

    private void fechasolicitudKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechasolicitudKeyPressed
        /*  if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.comprobante.requestFocus();
        }*/
    }//GEN-LAST:event_fechasolicitudKeyPressed

    private void fechasolicitudFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechasolicitudFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechasolicitudFocusGained

    private void GenerarCodeudorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarCodeudorActionPerformed
        if (codcodeudor.getText().equals("0") || codcodeudor.getText().isEmpty()) {
            this.codcodeudor.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese Codigo del Codeudor");
            return;
        }
        DefaultTableModel modelocod = (DefaultTableModel) tablacodeudor2.getModel();
        //modelo.addRow(new Object[]{codcodeudor.getText(),nombrecodeudor.getText()});
        Object[] nFila = new Object[2];
        nFila[0] = codcodeudor.getText();
        nFila[1] = nombrecodeudor.getText();
        modelocod.addRow(nFila);
        tablacodeudor2.setModel(modelocod);
        limpiarcodeudor();
    }//GEN-LAST:event_GenerarCodeudorActionPerformed

    private void DeleteCodeudorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteCodeudorActionPerformed
        DefaultTableModel modelocode = (DefaultTableModel) tablacodeudor2.getModel();
        int indFil = tablacodeudor2.getSelectedRow();
        if (indFil >= 0) {
            modelocode.removeRow(indFil);
        }
    }//GEN-LAST:event_DeleteCodeudorActionPerformed

    private void codcodeudorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codcodeudorFocusGained
        codcodeudor.selectAll();
    }//GEN-LAST:event_codcodeudorFocusGained

    private void codcodeudorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codcodeudorActionPerformed
        this.BuscarCodeudor.doClick();
    }//GEN-LAST:event_codcodeudorActionPerformed

    private void codcodeudorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codcodeudorKeyReleased
        String letras = ConvertirMayusculas.cadena(codcodeudor);
        codcodeudor.setText(letras);
    }//GEN-LAST:event_codcodeudorKeyReleased

    private void BuscarCodeudorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCodeudorActionPerformed
        if (this.codcodeudor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código del Codeudor");
            this.codcodeudor.requestFocus();
            return;
        }

        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codcodeudor.getText()));
            if (cl.getCodigo() == 0) {
                nCode = 2;
                GrillaBuscarCodeudor grillacod = new GrillaBuscarCodeudor();
                Thread hilocod = new Thread(grillacod);
                hilocod.start();
                BCodeudor.setModal(true);
                BCodeudor.setSize(482, 575);
                BCodeudor.setLocationRelativeTo(null);
                BCodeudor.setVisible(true);
                BCodeudor.setTitle("Buscar Codeudor");
                codcodeudor.requestFocus();
                BCodeudor.setModal(false);
            } else {
                nombrecodeudor.setText(cl.getNombre());
                //Establecemos un título para el jDialog
                codcodeudor.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCodeudorActionPerformed

    private void combocodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocodeActionPerformed

    private void jTBuscarCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCodeActionPerformed

    private void jTBuscarCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCodeKeyPressed
        this.jTBuscarCode.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCode.getText()).toUpperCase();
                jTBuscarCode.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocode.getSelectedIndex()) {
                    case 0:
                    indiceColumnaTabla = 1;
                    break;//por nombre
                    case 1:
                    indiceColumnaTabla = 0;
                    break;//por codigo
                }
                repaint();
                filtrocodeudor(indiceColumnaTabla);
            }
        });
        trsfiltrocodeudor = new TableRowSorter(tablacodeudor2.getModel());
        tablacodeudor2.setRowSorter(trsfiltrocodeudor);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCodeKeyPressed

    private void tablacodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacodeMouseClicked
        this.AceptarCode.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacodeMouseClicked

    private void tablacodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacodeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCode.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacodeKeyPressed

    private void AceptarCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCodeActionPerformed
        int nFila = this.tablacode.getSelectedRow();
        this.codcodeudor.setText(this.tablacode.getValueAt(nFila, 0).toString());
        this.nombrecodeudor.setText(this.tablacode.getValueAt(nFila, 1).toString());
        this.BCodeudor.setVisible(false);
        this.jTBuscarCode.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCodeActionPerformed

    private void SalirCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCodeActionPerformed
        this.BCodeudor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCodeActionPerformed

    private void BuscarJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarJuridicaActionPerformed
        if (this.codjuridica.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código de la Persona Juridica");
            this.codjuridica.requestFocus();
            return;
        }

        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codjuridica.getText()));
            if (cl.getCodigo() == 0) {
                nJuri = 2;
                GrillaBuscarJuridica grillaj = new GrillaBuscarJuridica();
                Thread hiloj = new Thread(grillaj);
                hiloj.start();
                BJuridica.setModal(true);
                BJuridica.setSize(482, 575);
                BJuridica.setLocationRelativeTo(null);
                BJuridica.setVisible(true);
                BJuridica.setTitle("Buscar Persona Juridica");
                codjuridica.requestFocus();
                BJuridica.setModal(false);
            } else {
                nombrejuridica.setText(cl.getNombre());
                //Establecemos un título para el jDialog
                codjuridica.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarJuridicaActionPerformed

    private void codjuridicaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codjuridicaKeyReleased
        String letras = ConvertirMayusculas.cadena(codjuridica);
        codjuridica.setText(letras);
    }//GEN-LAST:event_codjuridicaKeyReleased

    private void codjuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codjuridicaActionPerformed
        this.BuscarJuridica.doClick();
    }//GEN-LAST:event_codjuridicaActionPerformed

    private void codjuridicaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codjuridicaFocusGained
        codjuridica.selectAll();
    }//GEN-LAST:event_codjuridicaFocusGained

    private void DeleteJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteJuridicaActionPerformed
        DefaultTableModel modeloj = (DefaultTableModel) tablajuridica.getModel();
        int indFil = tablajuridica.getSelectedRow();
        if (indFil >= 0) {
            modeloj.removeRow(indFil);
        }
    }//GEN-LAST:event_DeleteJuridicaActionPerformed

    private void GenerarJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarJuridicaActionPerformed
        if (codjuridica.getText().equals("0") || codjuridica.getText().isEmpty()) {
            this.codjuridica.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese Codigo de la Persona Juridica");
            return;
        }
        DefaultTableModel modeloj = (DefaultTableModel) tablajuridica.getModel();
        //modelo.addRow(new Object[]{codcodeudor.getText(),nombrecodeudor.getText()});
        Object[] nFila = new Object[2];
        nFila[0] = codjuridica.getText();
        nFila[1] = nombrejuridica.getText();
        modeloj.addRow(nFila);
        tablajuridica.setModel(modeloj);
        limpiarpersonajuridica();
    }//GEN-LAST:event_GenerarJuridicaActionPerformed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtroclienteconyu(int nNumeroColumna) {
        trsfiltroconyu.setRowFilter(RowFilter.regexFilter(this.jTBuscarconyu.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Número");
        modelo.addColumn("Nombre Inmueble");
        modelo.addColumn("Nombre Solicitante");
        modelo.addColumn("Cedula / Ruc");
        modelo.addColumn("Estado Civil");
        modelo.addColumn("Dirección");
        modelo.addColumn("Cta. Catastral");
        int[] anchos = {120, 150, 250, 130, 120, 300, 120};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.jTable1.setFont(font);

        DefaultTableCellRenderer TablaRendererderecho = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRendererderecho.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        TablaRendererizquierdo.setHorizontalAlignment(SwingConstants.LEFT);
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRendererderecho);
    }

    private void TitCliente() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");
        modelocliente.addColumn("Ruc");
        modelocliente.addColumn("Fecha Nacimiento.");
        modelocliente.addColumn("Estado Civil");
        modelocliente.addColumn("Direccion");
        modelocliente.addColumn("Telefono Fijo");
        modelocliente.addColumn("Telefono Particular");

        int[] anchos = {100, 200, 120, 200, 200, 400, 150, 150};
        for (int i = 0; i < modelocliente.getColumnCount(); i++) {
            tablacli.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacli.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacli.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacli.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablacli.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRendererizquierdo);
    }

    private void TitPersonaJuridica() {
        modelojuri.addColumn("Código");
        modelojuri.addColumn("Nombre");

        int[] anchos = {100, 200};
        for (int i = 0; i < modelojuri.getColumnCount(); i++) {
            tablajuri.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablajuri.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablajuri.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablajuri.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablajuri.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablajuri.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
    }

    private void TitPropietario1() {
        modelopropietario1.addColumn("Código");
        modelopropietario1.addColumn("Nombre");

        int[] anchos = {100, 200};
        for (int i = 0; i < modelopropietario1.getColumnCount(); i++) {
            tablapropietario1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapropietario1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapropietario1.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapropietario1.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablapropietario1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablapropietario1.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
    }

    private void TitPropietario2() {
        modelopropietario2.addColumn("Código");
        modelopropietario2.addColumn("Nombre");

        int[] anchos = {100, 200};
        for (int i = 0; i < modelopropietario2.getColumnCount(); i++) {
            tablapropietario2.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapropietario2.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapropietario2.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapropietario2.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablapropietario2.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablapropietario2.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
    }

    private void TitCodeudor() {
        modelocode.addColumn("Código");
        modelocode.addColumn("Nombre");

        int[] anchos = {100, 200};
        for (int i = 0; i < modelocode.getColumnCount(); i++) {
            tablacode.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacode.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacode.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacode.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablacode.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablacode.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
    }

    private void TitCodeudor2() {
        modelocodeudor2.addColumn("Código");
        modelocodeudor2.addColumn("Nombre");

        int[] anchos = {100, 200};
        for (int i = 0; i < modelocodeudor2.getColumnCount(); i++) {
            tablacodeudor2.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacodeudor2.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacodeudor2.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacodeudor2.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablacodeudor2.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablacodeudor2.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
    }

    private void TitJuridica() {
        modelojuridica.addColumn("Código");
        modelojuridica.addColumn("Nombre");

        int[] anchos = {100, 200};
        for (int i = 0; i < modelojuridica.getColumnCount(); i++) {
            tablajuridica.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablajuridica.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablajuridica.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablajuridica.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablajuridica.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablajuridica.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
    }

    private void TitConyuge() {
        modeloconyu.addColumn("Código");
        modeloconyu.addColumn("Nombre");
        modeloconyu.addColumn("Ruc");
        modeloconyu.addColumn("Fecha Nacimiento");
        modeloconyu.addColumn("Direccion");
        modeloconyu.addColumn("Telefono Fijo");
        modeloconyu.addColumn("Telefono Particular");

        int[] anchos = {100, 200, 120, 200, 400, 150, 150};
        for (int i = 0; i < modeloconyu.getColumnCount(); i++) {
            tablaconyuge.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaconyuge.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaconyuge.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaconyuge.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaconyuge.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitEdificio() {
        modeloedificio.addColumn("Código");
        modeloedificio.addColumn("Nombre Inmueble");
        modeloedificio.addColumn("Cta Ctral");
        modeloedificio.addColumn("Medidor Ande");
        modeloedificio.addColumn("Medidor Essap");
        modeloedificio.addColumn("Nir");
        modeloedificio.addColumn("Nis");
        modeloedificio.addColumn("Telefono");
        modeloedificio.addColumn("Alquiler");
        modeloedificio.addColumn("Garantia");

        int[] anchos = {90, 300, 120, 200, 200, 100, 100, 150, 100, 100};
        for (int i = 0; i < modeloedificio.getColumnCount(); i++) {
            tablaedificio.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaedificio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaedificio.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaedificio.setFont(font);

        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        TablaRendererizquierdo.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear
        DefaultTableCellRenderer TablaRendererderecho = new DefaultTableCellRenderer();
        TablaRendererderecho.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tablaedificio.getColumnModel().getColumn(1).setCellRenderer(TablaRendererizquierdo);
        this.tablaedificio.getColumnModel().getColumn(2).setCellRenderer(TablaRendererderecho);
        this.tablaedificio.getColumnModel().getColumn(3).setCellRenderer(TablaRendererderecho);
        this.tablaedificio.getColumnModel().getColumn(4).setCellRenderer(TablaRendererderecho);
        this.tablaedificio.getColumnModel().getColumn(5).setCellRenderer(TablaRendererderecho);
        this.tablaedificio.getColumnModel().getColumn(6).setCellRenderer(TablaRendererderecho);
        ///////Ocultar Titulos/////
        this.tablaedificio.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaedificio.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablaedificio.getColumnModel().getColumn(7).setMaxWidth(0);
        this.tablaedificio.getColumnModel().getColumn(7).setMinWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);

        this.tablaedificio.getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablaedificio.getColumnModel().getColumn(8).setMinWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);

        this.tablaedificio.getColumnModel().getColumn(9).setMaxWidth(0);
        this.tablaedificio.getColumnModel().getColumn(9).setMinWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(0);
        this.tablaedificio.getTableHeader().getColumnModel().getColumn(9).setMinWidth(0);
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
                new solicitud_locaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCode;
    private javax.swing.JButton AceptarJuri;
    private javax.swing.JButton AceptarPropietario1;
    private javax.swing.JButton Aceptarcli;
    private javax.swing.JButton Aceptarconyu;
    private javax.swing.JButton Aceptaredif;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BCodeudor;
    private javax.swing.JDialog BJuridica;
    private javax.swing.JDialog BPropietario;
    private javax.swing.JDialog Bcliente;
    private javax.swing.JDialog Bclienteconyu;
    private javax.swing.JDialog Bedificio;
    private javax.swing.JButton BotonAbriArchivo;
    private javax.swing.JButton BuscarCodeudor;
    private javax.swing.JButton BuscarJuridica;
    private javax.swing.JButton BuscarPropietarios;
    private javax.swing.JButton DeleteCodeudor;
    private javax.swing.JButton DeleteJuridica;
    private javax.swing.JButton DeletePropietarios;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton GenerarCodeudor;
    private javax.swing.JButton GenerarJuridica;
    private javax.swing.JButton GenerarPropietarios;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton GuardarArchivo;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCode;
    private javax.swing.JButton SalirJuri;
    private javax.swing.JButton SalirPropietario1;
    private javax.swing.JButton Salircli;
    private javax.swing.JButton Salirconyu;
    private javax.swing.JButton Saliredif;
    private javax.swing.JFormattedTextField alquiler;
    private javax.swing.JTextField apellidocode;
    private javax.swing.JTextField apellidocode1;
    private javax.swing.JTextField barriocode;
    private javax.swing.JTextField barriocode1;
    private javax.swing.JButton botonsalir;
    private javax.swing.JButton buscarbarriocode;
    private javax.swing.JButton buscarbarriocode1;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JButton buscarciudadcode;
    private javax.swing.JButton buscarciudadcode1;
    private javax.swing.JButton buscarcliente;
    private javax.swing.JButton buscarconyugue;
    private javax.swing.JButton buscaredificio;
    private javax.swing.JButton buscarnacionalidad;
    private javax.swing.JButton buscarnacionalidad1;
    private javax.swing.JTextField cargolabocode;
    private javax.swing.JTextField cargolabocode1;
    private javax.swing.JTextField ciudadcode;
    private javax.swing.JTextField ciudadcode1;
    private javax.swing.JTextField codcodeudor;
    private javax.swing.JTextField codjuridica;
    private javax.swing.JTextField codpropietario;
    private javax.swing.JComboBox combocli;
    private javax.swing.JComboBox combocode;
    private javax.swing.JComboBox comboconyu;
    private javax.swing.JComboBox comboedificio;
    private javax.swing.JComboBox<String> comboestadocivilcode;
    private javax.swing.JComboBox<String> comboestadocivilcode1;
    private javax.swing.JComboBox combojuridica;
    private javax.swing.JComboBox combopropietario1;
    private javax.swing.JTextField ctactral;
    private com.toedter.calendar.JDateChooser dFinal;
    private com.toedter.calendar.JDateChooser dInicial;
    private javax.swing.JDialog detalle_solicitud;
    private javax.swing.JTextField direccionlabocode;
    private javax.swing.JTextField direccionlabocode1;
    private javax.swing.JTextField direccionparti;
    private javax.swing.JTextField direccionparticode;
    private javax.swing.JTextField direccionparticode1;
    private javax.swing.JTextField direccionparticonyu;
    private javax.swing.JTextField documentoidencode;
    private javax.swing.JTextField documentoidencode1;
    private javax.swing.JTextField emailcode;
    private javax.swing.JTextField emailcode1;
    private javax.swing.JTextField estadocivil;
    private org.edisoncor.gui.label.LabelMetric etiquetainmuebles;
    private com.toedter.calendar.JDateChooser fechanacicode;
    private com.toedter.calendar.JDateChooser fechanacicode1;
    private com.toedter.calendar.JDateChooser fechasolicitud;
    private javax.swing.JFormattedTextField garantia;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idcliente;
    private javax.swing.JTextField idconyugue;
    private javax.swing.JTextField idedificio;
    private javax.swing.JTextField idsolicitud;
    private javax.swing.JLabel imagen;
    private javax.swing.JTextField ingresolabocode;
    private javax.swing.JTextField ingresolabocode1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBoxnombres;
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
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCli;
    private javax.swing.JTextField jTBuscarCode;
    private javax.swing.JTextField jTBuscarJuridica;
    private javax.swing.JTextField jTBuscarPropietario1;
    private javax.swing.JTextField jTBuscarconyu;
    private javax.swing.JTextField jTBuscaredificio;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField lugarlabocode;
    private javax.swing.JTextField lugarlabocode1;
    private javax.swing.JTextField medidor_ande;
    private javax.swing.JTextField medidor_essap;
    private com.toedter.calendar.JDateChooser nacimiento;
    private com.toedter.calendar.JDateChooser nacimientoconyugue;
    private javax.swing.JTextField nacionalidadcode;
    private javax.swing.JTextField nacionalidadcode1;
    private javax.swing.JTextField nir;
    private javax.swing.JTextField nis;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombrearchivo;
    private javax.swing.JTextField nombrecode;
    private javax.swing.JTextField nombrecode1;
    private javax.swing.JTextField nombrecodeudor;
    private javax.swing.JTextField nombreconyu;
    private javax.swing.JTextField nombreinmueble;
    private javax.swing.JLabel nombreinmuebleeti;
    private javax.swing.JLabel nombreinmuebleeti1;
    private javax.swing.JLabel nombreinmuebleeti3;
    private javax.swing.JLabel nombreinmuebleeti4;
    private javax.swing.JTextField nombrejuridica;
    private javax.swing.JTextField nombrepropietario;
    private javax.swing.JTextField nropersonas;
    private javax.swing.JTextArea observacion;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField ruc;
    private javax.swing.JTextField rucconyu;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTextField sucursal2;
    private javax.swing.JTable tablacli;
    private javax.swing.JTable tablacode;
    private javax.swing.JTable tablacodeudor2;
    private javax.swing.JTable tablaconyuge;
    private javax.swing.JTable tablaedificio;
    private javax.swing.JTable tablajuri;
    private javax.swing.JTable tablajuridica;
    private javax.swing.JTable tablapropietario1;
    private javax.swing.JTable tablapropietario2;
    private javax.swing.JTextField telefono;
    private javax.swing.JTextField telefonofijo;
    private javax.swing.JTextField telefonofijocode;
    private javax.swing.JTextField telefonofijocode1;
    private javax.swing.JTextField telefonofijoconyu;
    private javax.swing.JTextField telefonomovil;
    private javax.swing.JTextField telefonomovilcode;
    private javax.swing.JTextField telefonomovilcode1;
    private javax.swing.JTextField telefonomovilconyu;
    // End of variables declaration//GEN-END:variables

    private class GrillaSolicitud extends Thread {

        public void run() {

            Date dFechaInicio = ODate.de_java_a_sql(dInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(dFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            solicitud_locacionDAO DAO = new solicitud_locacionDAO();
            try {
                for (solicitud_locacion sl : DAO.MostrarxFecha(dFechaInicio, dFechaFinal)) {
                    String Datos[] = {String.valueOf(sl.getIdsolicitud()), sl.getNombreinmueble(), sl.getIdcliente().getNombre(), sl.getIdcliente().getRuc(), sl.getIdcliente().getEstadocivil(), sl.getIdcliente().getDireccion(), sl.getIdunidad().getCtactral()};
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
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                Listar.setEnabled(false);
            }
        }

    }

    private class GrillaCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOcl = new clienteDAO();
            try {
                for (cliente cl : DAOcl.todos()) {
                    String Datos[] = {String.valueOf(cl.getCodigo()), cl.getNombre(), cl.getRuc(), formatoFecha.format(cl.getFechanacimiento()), cl.getEstadocivil(), cl.getDireccion(), cl.getTelefono(), cl.getCelular()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacli.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacli.getRowCount();
        }
    }

    private class GrillaConyuge extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloconyu.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloconyu.removeRow(0);
            }

            clienteDAO DAOcl = new clienteDAO();
            try {
                for (cliente cl : DAOcl.todos()) {
                    String Datos[] = {String.valueOf(cl.getCodigo()), cl.getNombre(), cl.getRuc(), formatoFecha.format(cl.getFechanacimiento()), cl.getDireccion(), cl.getTelefono(), cl.getCelular()};
                    modeloconyu.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaconyuge.setRowSorter(new TableRowSorter(modeloconyu));
            int cantFilas = tablaconyuge.getRowCount();
        }
    }

    private class GrillaEdificio extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloedificio.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloedificio.removeRow(0);
            }

            edificioDAO DAOed = new edificioDAO();
            try {
                for (edificio ed : DAOed.Todos()) {
                    String Datos[] = {String.valueOf(ed.getIdunidad()), ed.getInmueble().getNomedif(), ed.getCtactral(), ed.getMedande(), ed.getMedcorpo(), ed.getNir(), ed.getNis(), ed.getTelunid(), formatea.format(ed.getAlquiler()), formatea.format(ed.getDepgtia())};
                    modeloedificio.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaedificio.setRowSorter(new TableRowSorter(modeloedificio));
            int cantFilas = tablaedificio.getRowCount();
        }
    }

    private class GenerarLibro extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cIdsolicitud", idControl.getText().trim());

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/solicitud_locacion.jasper");
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

    private class GrillaBuscarJuridica extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelojuri.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelojuri.removeRow(0);
            }
            clienteDAO clDAO = new clienteDAO();
            try {
                for (cliente cl : clDAO.todos()) {
                    String Datos[] = {String.valueOf(cl.getCodigo()), cl.getNombre()};
                    modelojuri.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablajuri.setRowSorter(new TableRowSorter(modelojuri));
            int cantFilas = tablajuri.getRowCount();
        }

    }

    private class GrillaBuscarCodeudor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocode.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocode.removeRow(0);
            }
            clienteDAO clDAO = new clienteDAO();
            try {
                for (cliente cl : clDAO.todos()) {
                    String Datos[] = {String.valueOf(cl.getCodigo()), cl.getNombre()};
                    modelocode.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacode.setRowSorter(new TableRowSorter(modelocode));
            int cantFilas = tablacode.getRowCount();
        }

    }

    private class GrillaBuscarPropietario extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopropietario1.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopropietario1.removeRow(0);
            }
            clienteDAO clDAO = new clienteDAO();
            try {
                for (cliente cl : clDAO.todos()) {
                    String Datos[] = {String.valueOf(cl.getCodigo()), cl.getNombre()};
                    modelopropietario1.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablapropietario1.setRowSorter(new TableRowSorter(modelopropietario1));
            int cantFilas = tablapropietario1.getRowCount();
        }

    }

    private class GrillaCodeudor extends Thread {

        public void run() {

            int cantidadRegistro = modelocodeudor2.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocodeudor2.removeRow(0);
            }
            solicitud_locacion_codeudorDAO slcDAO = new solicitud_locacion_codeudorDAO();
            try {
                for (solicitud_locacion_codeudor slc : slcDAO.MostrarDetalle(Integer.valueOf(idsolicitud.getText()))) {
                    String Detalle[] = {String.valueOf(slc.getIdcliente()), slc.getCliente().getNombre()};
                    modelocodeudor2.addRow(Detalle);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            tablacodeudor2.setRowSorter(new TableRowSorter(modelocodeudor2));
            int cantFilas = tablacodeudor2.getRowCount();
            if (cantFilas > 0) {
                GenerarCodeudor.setEnabled(true);
                DeleteCodeudor.setEnabled(true);
            } else {
                GenerarCodeudor.setEnabled(true);
                DeleteCodeudor.setEnabled(false);
            }
        }
    }

    private class GrillaJuridica extends Thread {

        public void run() {

            int cantidadRegistro = modelojuridica.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelojuridica.removeRow(0);
            }
            solicitud_locacion_juridicaDAO sljDAO = new solicitud_locacion_juridicaDAO();
            try {
                for (solicitud_locacion_juridica slj : sljDAO.MostrarxJuridica(Integer.valueOf(idsolicitud.getText()))) {
                    String Detalle[] = {String.valueOf(slj.getIdcliente()), slj.getCliente().getNombre()};
                    modelojuridica.addRow(Detalle);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            tablajuridica.setRowSorter(new TableRowSorter(modelojuridica));
            int cantFilas = tablajuridica.getRowCount();
            if (cantFilas > 0) {
                GenerarJuridica.setEnabled(true);
                DeleteJuridica.setEnabled(true);
            } else {
                GenerarJuridica.setEnabled(true);
                DeleteJuridica.setEnabled(false);
            }
        }
    }

}

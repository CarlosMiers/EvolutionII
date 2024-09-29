/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.PanelCamara;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.cargoDAO;
import DAO.departamento_laboralDAO;
import DAO.ficha_empleadoDAO;
import DAO.giraduriaDAO;
import DAO.hijosDAO;
import DAO.localidadDAO;
import DAO.paisDAO;
import DAO.postulanteDAO;
import DAO.profesionDAO;
import DAO.seccionDAO;
import DAO.sucursalDAO;
import Modelo.Tablas;
import Modelo.albumfoto_edificio;
import Modelo.cargo;
import Modelo.departamento_laboral;
import Modelo.ficha_empleado;
import Modelo.giraduria;
import Modelo.hijos;
import Modelo.localidad;
import Modelo.pais;
import Modelo.postulante;
import Modelo.profesion;
import Modelo.seccion;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

public class ficha_de_empleados extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelotipo = new Tablas();
    Tablas modelonacionalidad = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modeloprofesion = new Tablas();
    Tablas modelocargo = new Tablas();
    Tablas modelodepartamento = new Tablas();
    Tablas modeloseccion = new Tablas();
    Tablas modelohijo = new Tablas();
    Tablas modelolocalidad = new Tablas();
    Tablas modelobanco = new Tablas();
    Tablas modelobarrio = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelopro = new Tablas();
    public PanelCamara panelCamara = new PanelCamara();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltrobanco, trsfiltro, trsfiltrobarrio, trsfiltronacionalidad, trsfiltrosucursal, trsfiltroprofesion, trsfiltrocargo, trsfiltrodepartamento, trsfiltroseccion, trsfiltrolocalidad, trsfiltrogiraduria;
    int nProp = 0;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String ruta, cNombre = null;
    String cAgua, cEnergiaelec, cRecolebasu = "0";
    int contador = 0;
    ArrayList<albumfoto_edificio> imagenes;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");

    public ficha_de_empleados() {
        fotoFuncionario = panelCamara;

        initComponents();
        this.Grabar.setIcon(iconograbar);
        this.Salir.setIcon(iconosalir);
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.botonsalir.setIcon(iconosalir);
        this.buscarlocalidad.setIcon(iconobuscar);
        this.buscarpostulante.setIcon(iconobuscar);
        this.buscarnacionalidad.setIcon(iconobuscar);
        this.buscarsucursal.setIcon(iconobuscar);
        this.buscarprofesion.setIcon(iconobuscar);
        this.buscarcargo.setIcon(iconobuscar);
        this.buscardepartamento.setIcon(iconobuscar);
        this.buscarseccion.setIcon(iconobuscar);
        this.buscargiraduria.setIcon(iconobuscar);
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.codigo.setText("0");
        this.idControl.setText("0");
        this.idControl.setVisible(false);
//        this.etiqueta.setSize(372, 259);
        this.cargarTitulo();
        this.TitNacionalidad();
        this.TituloHijo();
        this.TitSucursal();
        this.TitProfesion();
        this.TitCargo();
        this.TitDepartamento();
        this.TitSeccion();
        this.TitLocalidad();
        this.TitGiraduria();
        this.TitBarrios();
        this.TituloBanco();
        ///deshabilitar boton
        //sacarFoto.setVisible(false);
        GrillaFicha GrillaOC = new GrillaFicha();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        GrillaSeccion grillaSecc = new GrillaSeccion();
        Thread hilosecc = new Thread(grillaSecc);
        hilosecc.start();

        GrillaGiraduria grillaGira = new GrillaGiraduria();
        Thread hiloGira = new Thread(grillaGira);
        hiloGira.start();

        GrillaDepartamento grillaDep = new GrillaDepartamento();
        Thread hilodep = new Thread(grillaDep);
        hilodep.start();

        GrillaCargo grillaCar = new GrillaCargo();
        Thread hilocar = new Thread(grillaCar);
        hilocar.start();

        GrillaProfesion grillaPro = new GrillaProfesion();
        Thread hiloPro = new Thread(grillaPro);
        hiloPro.start();

        GrillaSucursal grillaSuc = new GrillaSucursal();
        Thread hiloSuc = new Thread(grillaSuc);
        hiloSuc.start();

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

        detalle_ficha = new javax.swing.JDialog();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        direccion = new javax.swing.JTextField();
        Ubicación = new javax.swing.JLabel();
        apellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        estadocivil = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        sexo = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        nacimiento = new com.toedter.calendar.JDateChooser();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        estado = new javax.swing.JCheckBox();
        nacionalidad = new javax.swing.JTextField();
        buscarnacionalidad = new javax.swing.JButton();
        nombrenacionalidad = new javax.swing.JTextField();
        conyugue = new javax.swing.JTextField();
        nrocedula = new javax.swing.JTextField();
        localidad = new javax.swing.JTextField();
        buscarlocalidad = new javax.swing.JButton();
        nombrelocalidad = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        celular = new javax.swing.JTextField();
        telefonourge = new javax.swing.JTextField();
        correo = new javax.swing.JTextField();
        fotoFuncionario = new JPanelWebCam.JPanelWebCam();
        sacarFoto = new javax.swing.JButton();
        postulante = new javax.swing.JTextField();
        codigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        buscarpostulante = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Ubicación1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        fechaingreso = new com.toedter.calendar.JDateChooser();
        bonificacion = new javax.swing.JCheckBox();
        salario = new javax.swing.JFormattedTextField();
        jPanel13 = new javax.swing.JPanel();
        espanol = new javax.swing.JCheckBox();
        guarani = new javax.swing.JCheckBox();
        portugues = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        Ubicación2 = new javax.swing.JLabel();
        nivelacademico = new javax.swing.JComboBox<>();
        Ubicación3 = new javax.swing.JLabel();
        otroidioma = new javax.swing.JCheckBox();
        aleman = new javax.swing.JCheckBox();
        adicionalxformacion = new javax.swing.JFormattedTextField();
        sistemaxcobro = new javax.swing.JComboBox<>();
        tiposalario = new javax.swing.JComboBox<>();
        ips = new javax.swing.JCheckBox();
        jLabel34 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarsucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        profesion = new javax.swing.JTextField();
        buscarprofesion = new javax.swing.JButton();
        nombreprofesion = new javax.swing.JTextField();
        giraduria = new javax.swing.JTextField();
        buscargiraduria = new javax.swing.JButton();
        nombregiraduria = new javax.swing.JTextField();
        cargo = new javax.swing.JTextField();
        buscarcargo = new javax.swing.JButton();
        nombrecargo = new javax.swing.JTextField();
        departamento = new javax.swing.JTextField();
        buscardepartamento = new javax.swing.JButton();
        nombredepartamento = new javax.swing.JTextField();
        seccion = new javax.swing.JTextField();
        buscarseccion = new javax.swing.JButton();
        nombreseccion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nroseguroips = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nrohijos = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        preparacion_academica = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        objetivos_laborales = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        experiencia_laboral = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tablahijo = new javax.swing.JTable();
        jPanel32 = new javax.swing.JPanel();
        AgregarItem = new javax.swing.JButton();
        EditarItem = new javax.swing.JButton();
        BorrarItem = new javax.swing.JButton();
        Bbarrio = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combobarrio = new javax.swing.JComboBox();
        jTBuscarBarrio = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablabarrio = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarCasa = new javax.swing.JButton();
        SalirCasa = new javax.swing.JButton();
        BNacionalidad = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combonacionalidad = new javax.swing.JComboBox();
        jTBuscarNacionalidad = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablanacionalidad = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarNac = new javax.swing.JButton();
        SalirNac = new javax.swing.JButton();
        BSucursal = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BProfesion = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        comboprofesion = new javax.swing.JComboBox();
        jTBuscaProfesion = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaprofesion = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        AceptarPro = new javax.swing.JButton();
        SalirPro = new javax.swing.JButton();
        BGiraduria = new javax.swing.JDialog();
        jPanel24 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        jTBuscarGiraduria = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablagiraduria = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        AceptarGira = new javax.swing.JButton();
        SalirGira = new javax.swing.JButton();
        BLocalidad = new javax.swing.JDialog();
        jPanel26 = new javax.swing.JPanel();
        combolocalidad = new javax.swing.JComboBox();
        jTBuscarLocalidad = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablalocalidad = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        AceptarLoc = new javax.swing.JButton();
        SalirLoc = new javax.swing.JButton();
        BCargo = new javax.swing.JDialog();
        jPanel28 = new javax.swing.JPanel();
        combocargo = new javax.swing.JComboBox();
        jTBuscarCargo = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablacargo = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        AceptarCar = new javax.swing.JButton();
        SalirCar = new javax.swing.JButton();
        BDepartamento_laboral = new javax.swing.JDialog();
        jPanel30 = new javax.swing.JPanel();
        combodepartamento = new javax.swing.JComboBox();
        jTBuscarDepartamento = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        tabladepartamento = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        AceptarDep = new javax.swing.JButton();
        SalirDep = new javax.swing.JButton();
        BSeccion = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        comboseccion = new javax.swing.JComboBox();
        jTBuscarSeccion = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaseccion = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarSecc = new javax.swing.JButton();
        SalirSecc = new javax.swing.JButton();
        BPostulante = new javax.swing.JDialog();
        jPanel42 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        jTBuscarbanco = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel43 = new javax.swing.JPanel();
        AceptarCasa1 = new javax.swing.JButton();
        SalirCasa1 = new javax.swing.JButton();
        itemCuentas = new javax.swing.JDialog();
        jPanel33 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        nombrehijo = new javax.swing.JTextField();
        nacimientohijo = new com.toedter.calendar.JDateChooser();
        sexohijo = new javax.swing.JComboBox<>();
        cobrabonificacion = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        idItem = new javax.swing.JFormattedTextField();
        jPanel34 = new javax.swing.JPanel();
        GrabarItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        Unidades = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        buscapersona = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        botonsalir = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        generarcontrato = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        detalle_ficha.setMinimumSize(new java.awt.Dimension(726, 665));
        detalle_ficha.setModal(true);
        detalle_ficha.setResizable(false);
        detalle_ficha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_fichaFocusGained(evt);
            }
        });
        detalle_ficha.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_fichaWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_ficha.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_fichaWindowActivated(evt);
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

        Salir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salir.setText("Salir");
        Salir.setToolTipText("Salir sin Guardar");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1091, 674));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1091, 674));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(1086, 652));
        jPanel1.setMinimumSize(new java.awt.Dimension(1086, 652));

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setMaximumSize(new java.awt.Dimension(731, 506));
        jPanel4.setMinimumSize(new java.awt.Dimension(731, 506));

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setMaximumSize(new java.awt.Dimension(651, 480));
        jPanel11.setMinimumSize(new java.awt.Dimension(651, 480));

        jLabel12.setText("Código");

        jLabel15.setText("Nombres");

        direccion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        direccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                direccionFocusGained(evt);
            }
        });
        direccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionActionPerformed(evt);
            }
        });
        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                direccionKeyReleased(evt);
            }
        });

        Ubicación.setText("Apellidos");

        apellido.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        apellido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                apellidoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                apellidoFocusLost(evt);
            }
        });
        apellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoActionPerformed(evt);
            }
        });
        apellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                apellidoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                apellidoKeyReleased(evt);
            }
        });

        jLabel5.setText("Sexo");

        jLabel16.setText("Nacionalidad");

        jLabel14.setText("Conyugue");

        jLabel17.setText("Localidad");

        estadocivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CASADO/A", "SOLTERO/A", "SEPARADO/A", "DIVORCIADO/A", "VIUDO/A" }));
        estadocivil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                estadocivilKeyPressed(evt);
            }
        });

        jLabel13.setText("N° de Cedula");

        jLabel25.setText("Nacimiento");

        jLabel27.setText("Estado Civil");

        sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MASCULINO", "FEMENINO" }));
        sexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sexoKeyPressed(evt);
            }
        });

        jLabel29.setText("Dirección");

        nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacimientoKeyPressed(evt);
            }
        });

        jLabel30.setText("Teléfono");

        jLabel31.setText("Celular");

        jLabel32.setText("Tel. Urgencia");

        jLabel33.setText("Correo Electronico");

        estado.setText("Estado");
        estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoActionPerformed(evt);
            }
        });

        nacionalidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nacionalidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nacionalidadFocusGained(evt);
            }
        });
        nacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nacionalidadActionPerformed(evt);
            }
        });
        nacionalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacionalidadKeyPressed(evt);
            }
        });

        buscarnacionalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarnacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarnacionalidadActionPerformed(evt);
            }
        });

        nombrenacionalidad.setEnabled(false);
        nombrenacionalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrenacionalidadKeyPressed(evt);
            }
        });

        conyugue.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        conyugue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                conyugueFocusGained(evt);
            }
        });
        conyugue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conyugueActionPerformed(evt);
            }
        });
        conyugue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                conyugueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                conyugueKeyReleased(evt);
            }
        });

        nrocedula.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nrocedula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nrocedulaFocusGained(evt);
            }
        });
        nrocedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nrocedulaActionPerformed(evt);
            }
        });
        nrocedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrocedulaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nrocedulaKeyReleased(evt);
            }
        });

        localidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        localidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                localidadFocusGained(evt);
            }
        });
        localidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localidadActionPerformed(evt);
            }
        });
        localidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                localidadKeyPressed(evt);
            }
        });

        buscarlocalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarlocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarlocalidadActionPerformed(evt);
            }
        });

        nombrelocalidad.setEnabled(false);
        nombrelocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrelocalidadKeyPressed(evt);
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

        telefono.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        telefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                telefonoFocusGained(evt);
            }
        });
        telefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonoActionPerformed(evt);
            }
        });
        telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                telefonoKeyReleased(evt);
            }
        });

        celular.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        celular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                celularFocusGained(evt);
            }
        });
        celular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celularActionPerformed(evt);
            }
        });
        celular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                celularKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                celularKeyReleased(evt);
            }
        });

        telefonourge.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        telefonourge.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                telefonourgeFocusGained(evt);
            }
        });
        telefonourge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonourgeActionPerformed(evt);
            }
        });
        telefonourge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonourgeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                telefonourgeKeyReleased(evt);
            }
        });

        correo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        correo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                correoFocusGained(evt);
            }
        });
        correo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correoActionPerformed(evt);
            }
        });
        correo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                correoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                correoKeyReleased(evt);
            }
        });

        fotoFuncionario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fotoFuncionario.setToolTipText("Click para Encender Cámara");
        fotoFuncionario.setFONDO(false);
        fotoFuncionario.setEnabled(false);

        javax.swing.GroupLayout fotoFuncionarioLayout = new javax.swing.GroupLayout(fotoFuncionario);
        fotoFuncionario.setLayout(fotoFuncionarioLayout);
        fotoFuncionarioLayout.setHorizontalGroup(
            fotoFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        fotoFuncionarioLayout.setVerticalGroup(
            fotoFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
        );

        sacarFoto.setText("Examinar..");
        sacarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sacarFotoActionPerformed(evt);
            }
        });

        postulante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        postulante.setEnabled(false);
        postulante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                postulanteKeyReleased(evt);
            }
        });

        codigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codigo.setEnabled(false);

        jLabel7.setText("Postulante");

        buscarpostulante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarpostulante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarpostulanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Ubicación)
                    .addComponent(jLabel13)
                    .addComponent(jLabel5)
                    .addComponent(jLabel25)
                    .addComponent(jLabel16)
                    .addComponent(jLabel27)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(estado)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(estadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nrocedula, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(conyugue)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                    .addComponent(localidad, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buscarlocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nombrelocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(telefonourge, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(apellido)
                                .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addComponent(nacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buscarnacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nombrenacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(postulante, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                    .addComponent(codigo, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(7, 7, 7)
                                .addComponent(buscarpostulante, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fotoFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sacarFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(correo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                            .addComponent(direccion, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(postulante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Ubicación, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(nrocedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16))
                                    .addComponent(buscarnacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombrenacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(estadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(conyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(localidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(buscarlocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombrelocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(buscarpostulante, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(fotoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sacarFoto)))
                .addGap(3, 3, 3)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(telefonourge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(estado)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1070, 1070, 1070)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Personales", jPanel1);

        jPanel5.setMaximumSize(new java.awt.Dimension(701, 542));
        jPanel5.setMinimumSize(new java.awt.Dimension(701, 542));
        jPanel5.setName(""); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(701, 500));

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setMaximumSize(new java.awt.Dimension(676, 481));
        jPanel7.setMinimumSize(new java.awt.Dimension(676, 481));

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setMaximumSize(new java.awt.Dimension(657, 455));
        jPanel12.setMinimumSize(new java.awt.Dimension(657, 455));

        jLabel18.setText("Sucursal Destino");

        jLabel19.setText("Profesión");

        Ubicación1.setText("Función/Cargo");

        jLabel6.setText("Departamento");

        jLabel20.setText("Salario");

        jLabel22.setText("Sistema de Cobro");

        jLabel23.setText("Tipo Salario");

        jLabel24.setText("Sección");

        jLabel26.setText("Fecha Ingreso");

        jLabel28.setText("Adicional por Formación");

        fechaingreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaingresoKeyPressed(evt);
            }
        });

        bonificacion.setText("Bonificación Familiar");
        bonificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bonificacionActionPerformed(evt);
            }
        });

        salario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        salario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        salario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                salarioFocusGained(evt);
            }
        });
        salario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                salarioKeyPressed(evt);
            }
        });

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setMaximumSize(new java.awt.Dimension(234, 259));
        jPanel13.setMinimumSize(new java.awt.Dimension(234, 259));

        espanol.setText("Español");

        guarani.setText("Guarani");
        guarani.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guaraniActionPerformed(evt);
            }
        });

        portugues.setText("Portugués");
        portugues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portuguesActionPerformed(evt);
            }
        });

        jLabel1.setText("Estudios /Formación");

        Ubicación2.setText("Nivel Académico");

        nivelacademico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ESCOLAR", "SECUNDARIA", "UNIVERSITARIA", "MASTERADO/DOCTORADO" }));
        nivelacademico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nivelacademicoKeyPressed(evt);
            }
        });

        Ubicación3.setText("Lenguas");

        otroidioma.setText("Otros Idiomas");
        otroidioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otroidiomaActionPerformed(evt);
            }
        });

        aleman.setText("Alemán");
        aleman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alemanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(Ubicación2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nivelacademico, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Ubicación3)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(guarani)
                            .addComponent(espanol)
                            .addComponent(portugues)
                            .addComponent(otroidioma)
                            .addComponent(aleman))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ubicación2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nivelacademico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Ubicación3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(espanol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(guarani)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(portugues)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(aleman)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(otroidioma)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        adicionalxformacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        adicionalxformacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        adicionalxformacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                adicionalxformacionFocusGained(evt);
            }
        });
        adicionalxformacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                adicionalxformacionKeyPressed(evt);
            }
        });

        sistemaxcobro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EFECTIVO", "CHEQUE", "TARJETA", "TRANSFERENCIAS" }));
        sistemaxcobro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sistemaxcobroKeyPressed(evt);
            }
        });

        tiposalario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HORA", "DIARIO", "QUINCENAL", "MENSUAL" }));
        tiposalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tiposalarioKeyPressed(evt);
            }
        });

        ips.setText("IPS");
        ips.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipsActionPerformed(evt);
            }
        });

        jLabel34.setText("Giraduria");

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

        buscarsucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarsucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarsucursalActionPerformed(evt);
            }
        });

        nombresucursal.setEnabled(false);
        nombresucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombresucursalKeyPressed(evt);
            }
        });

        profesion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        profesion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                profesionFocusGained(evt);
            }
        });
        profesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profesionActionPerformed(evt);
            }
        });
        profesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                profesionKeyPressed(evt);
            }
        });

        buscarprofesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarprofesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarprofesionActionPerformed(evt);
            }
        });

        nombreprofesion.setEnabled(false);
        nombreprofesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreprofesionKeyPressed(evt);
            }
        });

        giraduria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giraduria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                giraduriaFocusGained(evt);
            }
        });
        giraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giraduriaActionPerformed(evt);
            }
        });
        giraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                giraduriaKeyPressed(evt);
            }
        });

        buscargiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscargiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscargiraduriaActionPerformed(evt);
            }
        });

        nombregiraduria.setEnabled(false);
        nombregiraduria.setMaximumSize(new java.awt.Dimension(6, 22));
        nombregiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombregiraduriaKeyPressed(evt);
            }
        });

        cargo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cargo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cargoFocusGained(evt);
            }
        });
        cargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargoActionPerformed(evt);
            }
        });
        cargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cargoKeyPressed(evt);
            }
        });

        buscarcargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarcargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcargoActionPerformed(evt);
            }
        });

        nombrecargo.setEnabled(false);
        nombrecargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrecargoKeyPressed(evt);
            }
        });

        departamento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        departamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                departamentoFocusGained(evt);
            }
        });
        departamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departamentoActionPerformed(evt);
            }
        });
        departamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                departamentoKeyPressed(evt);
            }
        });

        buscardepartamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscardepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscardepartamentoActionPerformed(evt);
            }
        });

        nombredepartamento.setEnabled(false);
        nombredepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombredepartamentoKeyPressed(evt);
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

        buscarseccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarseccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarseccionActionPerformed(evt);
            }
        });

        nombreseccion.setEnabled(false);
        nombreseccion.setMaximumSize(new java.awt.Dimension(6, 22));
        nombreseccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreseccionKeyPressed(evt);
            }
        });

        jLabel2.setText("N° Seguro IPS");

        jLabel3.setText("Hijos");

        nrohijos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        nrohijos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrohijos.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel4.setText("Para gestión de Bonificación solamente");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel19)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Ubicación1, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel34)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(profesion)
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(seccion)
                                .addComponent(departamento)
                                .addComponent(cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(giraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buscarsucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarprofesion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(buscarcargo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(buscardepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(buscarseccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nombreseccion, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nombrecargo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nombredepartamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nombreprofesion, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(buscargiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 433, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(530, 530, 530))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(sistemaxcobro, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel26))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fechaingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(salario, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(adicionalxformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tiposalario, 0, 122, Short.MAX_VALUE)
                                        .addComponent(nroseguroips))
                                    .addComponent(bonificacion)
                                    .addComponent(ips))))
                        .addGap(65, 65, 65)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nrohijos, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombreprofesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(nombrecargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombredepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombreseccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(buscarsucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarprofesion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Ubicación1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarcargo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscardepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarseccion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(buscargiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(profesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addComponent(cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(departamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(seccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(giraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28)
                            .addComponent(adicionalxformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sistemaxcobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tiposalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nroseguroips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ips)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bonificacion)
                    .addComponent(jLabel3)
                    .addComponent(nrohijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(169, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1086, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 645, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Datos Laborales", jPanel5);

        preparacion_academica.setColumns(20);
        preparacion_academica.setRows(5);
        jScrollPane2.setViewportView(preparacion_academica);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Formación Académica", jPanel3);

        objetivos_laborales.setColumns(20);
        objetivos_laborales.setRows(5);
        jScrollPane3.setViewportView(objetivos_laborales);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Objetivos Laborales", jPanel8);

        experiencia_laboral.setColumns(20);
        experiencia_laboral.setRows(5);
        jScrollPane4.setViewportView(experiencia_laboral);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Experiencia Laboral", jPanel9);

        tablahijo.setModel(modelohijo       );
        jScrollPane15.setViewportView(tablahijo);

        jPanel32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AgregarItem.setText("Agregar");
        AgregarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItemActionPerformed(evt);
            }
        });

        EditarItem.setText("Editar");
        EditarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EditarItem.setEnabled(false);
        EditarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarItemActionPerformed(evt);
            }
        });

        BorrarItem.setText("Borrar");
        BorrarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarItem.setEnabled(false);
        BorrarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EditarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(BorrarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarItem)
                    .addComponent(BorrarItem)
                    .addComponent(EditarItem))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hijos/as", jPanel10);

        javax.swing.GroupLayout detalle_fichaLayout = new javax.swing.GroupLayout(detalle_ficha.getContentPane());
        detalle_ficha.getContentPane().setLayout(detalle_fichaLayout);
        detalle_fichaLayout.setHorizontalGroup(
            detalle_fichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_fichaLayout.createSequentialGroup()
                .addGap(0, 409, Short.MAX_VALUE)
                .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(detalle_fichaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        detalle_fichaLayout.setVerticalGroup(
            detalle_fichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_fichaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(detalle_fichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grabar)
                    .addComponent(Salir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Bbarrio.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Bbarrio.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobarrio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobarrio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobarrio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobarrioActionPerformed(evt);
            }
        });

        jTBuscarBarrio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarBarrio.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarBarrioActionPerformed(evt);
            }
        });
        jTBuscarBarrio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarBarrioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combobarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablabarrio.setModel(modelobarrio);
        tablabarrio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablabarrioMouseClicked(evt);
            }
        });
        tablabarrio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablabarrioKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablabarrio);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
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

        javax.swing.GroupLayout BbarrioLayout = new javax.swing.GroupLayout(Bbarrio.getContentPane());
        Bbarrio.getContentPane().setLayout(BbarrioLayout);
        BbarrioLayout.setHorizontalGroup(
            BbarrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BbarrioLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BbarrioLayout.setVerticalGroup(
            BbarrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BbarrioLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BNacionalidad.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BNacionalidad.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combonacionalidad.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combonacionalidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combonacionalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combonacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combonacionalidadActionPerformed(evt);
            }
        });

        jTBuscarNacionalidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarNacionalidad.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarNacionalidadActionPerformed(evt);
            }
        });
        jTBuscarNacionalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarNacionalidadKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combonacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combonacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablanacionalidad.setModel(modelonacionalidad    );
        tablanacionalidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablanacionalidadMouseClicked(evt);
            }
        });
        tablanacionalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablanacionalidadKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablanacionalidad);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarNac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarNac.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarNac.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarNac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarNacActionPerformed(evt);
            }
        });

        SalirNac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirNac.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirNac.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirNac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirNacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarNac, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirNac, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarNac)
                    .addComponent(SalirNac))
                .addContainerGap())
        );

        javax.swing.GroupLayout BNacionalidadLayout = new javax.swing.GroupLayout(BNacionalidad.getContentPane());
        BNacionalidad.getContentPane().setLayout(BNacionalidadLayout);
        BNacionalidadLayout.setHorizontalGroup(
            BNacionalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BNacionalidadLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BNacionalidadLayout.setVerticalGroup(
            BNacionalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BNacionalidadLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablasucursal.setModel(modelosucursal    );
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
        jScrollPane8.setViewportView(tablasucursal);

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BProfesion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProfesion.setTitle("null");

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboprofesion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboprofesion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboprofesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboprofesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboprofesionActionPerformed(evt);
            }
        });

        jTBuscaProfesion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscaProfesion.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscaProfesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscaProfesionActionPerformed(evt);
            }
        });
        jTBuscaProfesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscaProfesionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(comboprofesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscaProfesion, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboprofesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscaProfesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaprofesion.setModel(modeloprofesion    );
        tablaprofesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaprofesionMouseClicked(evt);
            }
        });
        tablaprofesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaprofesionKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablaprofesion);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarPro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarPro.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProActionPerformed(evt);
            }
        });

        SalirPro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirPro.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirPro, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPro)
                    .addComponent(SalirPro))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProfesionLayout = new javax.swing.GroupLayout(BProfesion.getContentPane());
        BProfesion.getContentPane().setLayout(BProfesionLayout);
        BProfesionLayout.setHorizontalGroup(
            BProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProfesionLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProfesionLayout.setVerticalGroup(
            BProfesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProfesionLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BGiraduria.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BGiraduria.setTitle("null");

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combogiraduria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combogiraduria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combogiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combogiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combogiraduriaActionPerformed(evt);
            }
        });

        jTBuscarGiraduria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarGiraduria.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablagiraduria.setModel(modelogiraduria    );
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
        jScrollPane10.setViewportView(tablagiraduria);

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGira.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGira.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGira.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGira.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGiraActionPerformed(evt);
            }
        });

        SalirGira.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGira.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirGira.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGira.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGiraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGira, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGira, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGira)
                    .addComponent(SalirGira))
                .addContainerGap())
        );

        javax.swing.GroupLayout BGiraduriaLayout = new javax.swing.GroupLayout(BGiraduria.getContentPane());
        BGiraduria.getContentPane().setLayout(BGiraduriaLayout);
        BGiraduriaLayout.setHorizontalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BGiraduriaLayout.setVerticalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BLocalidad.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BLocalidad.setTitle("null");

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combolocalidad.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combolocalidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combolocalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combolocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combolocalidadActionPerformed(evt);
            }
        });

        jTBuscarLocalidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarLocalidad.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarLocalidadActionPerformed(evt);
            }
        });
        jTBuscarLocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarLocalidadKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(combolocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combolocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablalocalidad.setModel(modelolocalidad    );
        tablalocalidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablalocalidadMouseClicked(evt);
            }
        });
        tablalocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablalocalidadKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(tablalocalidad);

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarLoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarLoc.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarLoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarLocActionPerformed(evt);
            }
        });

        SalirLoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirLoc.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirLoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarLoc)
                    .addComponent(SalirLoc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BLocalidadLayout = new javax.swing.GroupLayout(BLocalidad.getContentPane());
        BLocalidad.getContentPane().setLayout(BLocalidadLayout);
        BLocalidadLayout.setHorizontalGroup(
            BLocalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BLocalidadLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BLocalidadLayout.setVerticalGroup(
            BLocalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BLocalidadLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCargo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCargo.setTitle("null");

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocargo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocargoActionPerformed(evt);
            }
        });

        jTBuscarCargo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCargo.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCargoActionPerformed(evt);
            }
        });
        jTBuscarCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCargoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(combocargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacargo.setModel(modelocargo   );
        tablacargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacargoMouseClicked(evt);
            }
        });
        tablacargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacargoKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(tablacargo);

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCar.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCarActionPerformed(evt);
            }
        });

        SalirCar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCar.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCar)
                    .addComponent(SalirCar))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCargoLayout = new javax.swing.GroupLayout(BCargo.getContentPane());
        BCargo.getContentPane().setLayout(BCargoLayout);
        BCargoLayout.setHorizontalGroup(
            BCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCargoLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCargoLayout.setVerticalGroup(
            BCargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCargoLayout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BDepartamento_laboral.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BDepartamento_laboral.setTitle("null");

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combodepartamento.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combodepartamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combodepartamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combodepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combodepartamentoActionPerformed(evt);
            }
        });

        jTBuscarDepartamento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarDepartamento.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarDepartamentoActionPerformed(evt);
            }
        });
        jTBuscarDepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarDepartamentoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(combodepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combodepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tabladepartamento.setModel(modelodepartamento    );
        tabladepartamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabladepartamentoMouseClicked(evt);
            }
        });
        tabladepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabladepartamentoKeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(tabladepartamento);

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarDep.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarDep.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarDep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarDepActionPerformed(evt);
            }
        });

        SalirDep.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirDep.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirDep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirDepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarDep, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirDep, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarDep)
                    .addComponent(SalirDep))
                .addContainerGap())
        );

        javax.swing.GroupLayout BDepartamento_laboralLayout = new javax.swing.GroupLayout(BDepartamento_laboral.getContentPane());
        BDepartamento_laboral.getContentPane().setLayout(BDepartamento_laboralLayout);
        BDepartamento_laboralLayout.setHorizontalGroup(
            BDepartamento_laboralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BDepartamento_laboralLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BDepartamento_laboralLayout.setVerticalGroup(
            BDepartamento_laboralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BDepartamento_laboralLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BSeccion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSeccion.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboseccion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboseccion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboseccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboseccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboseccionActionPerformed(evt);
            }
        });

        jTBuscarSeccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSeccion.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(comboseccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane6.setViewportView(tablaseccion);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSecc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSecc.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSecc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSecc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSeccActionPerformed(evt);
            }
        });

        SalirSecc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSecc.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSecc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSecc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSeccActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSecc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSecc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSecc)
                    .addComponent(SalirSecc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSeccionLayout = new javax.swing.GroupLayout(BSeccion.getContentPane());
        BSeccion.getContentPane().setLayout(BSeccionLayout);
        BSeccionLayout.setHorizontalGroup(
            BSeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSeccionLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSeccionLayout.setVerticalGroup(
            BSeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSeccionLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BPostulante.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BPostulante.setTitle("null");

        jPanel42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarbanco.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane14.setViewportView(tablabanco);

        jPanel43.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa1.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasa1ActionPerformed(evt);
            }
        });

        SalirCasa1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa1.setText(org.openide.util.NbBundle.getMessage(ficha_de_empleados.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCasa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCasa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCasa1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa1)
                    .addComponent(SalirCasa1))
                .addContainerGap())
        );

        javax.swing.GroupLayout BPostulanteLayout = new javax.swing.GroupLayout(BPostulante.getContentPane());
        BPostulante.getContentPane().setLayout(BPostulanteLayout);
        BPostulanteLayout.setHorizontalGroup(
            BPostulanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPostulanteLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BPostulanteLayout.setVerticalGroup(
            BPostulanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPostulanteLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setText("Nombres y Apellidos");

        jLabel10.setText("Nacimiento");

        nombrehijo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrehijoKeyPressed(evt);
            }
        });

        nacimientohijo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacimientohijoKeyPressed(evt);
            }
        });

        sexohijo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MASCULINO", "FEMIENINO" }));

        cobrabonificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jLabel9.setText("Sexo");

        jLabel11.setText("Bonificación");

        idItem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addGap(32, 32, 32)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(nombrehijo, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(75, Short.MAX_VALUE))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nacimientohijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel33Layout.createSequentialGroup()
                                .addComponent(sexohijo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(idItem, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cobrabonificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(nombrehijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)))
                .addGap(18, 18, 18)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nacimientohijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sexohijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(idItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cobrabonificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItem.setText("Grabar");
        GrabarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemActionPerformed(evt);
            }
        });

        SalirItem.setText("Salir");
        SalirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(GrabarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(SalirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarItem)
                    .addComponent(SalirItem))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout itemCuentasLayout = new javax.swing.GroupLayout(itemCuentas.getContentPane());
        itemCuentas.getContentPane().setLayout(itemCuentasLayout);
        itemCuentasLayout.setHorizontalGroup(
            itemCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemCuentasLayout.setVerticalGroup(
            itemCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemCuentasLayout.createSequentialGroup()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ficha de Empleados");
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

        Unidades.setBackground(new java.awt.Color(255, 255, 255));
        Unidades.setText("Empleados");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Nombre", "Cédula" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        buscapersona.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buscapersona.setSelectionColor(new java.awt.Color(0, 63, 62));
        buscapersona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscapersonaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(Unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscapersona, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buscapersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        generarcontrato.setBackground(new java.awt.Color(255, 255, 255));
        generarcontrato.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        generarcontrato.setText("Generar Contrato");
        generarcontrato.setToolTipText("");
        generarcontrato.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generarcontrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarcontratoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(botonsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(generarcontrato, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Agregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Modificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Listar)
                .addGap(4, 4, 4)
                .addComponent(generarcontrato)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonsalir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(376, Short.MAX_VALUE))
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

        jMenu1.setText("Opciones");

        jMenuItem1.setText("Listado General");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void botonsalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_botonsalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_botonsalirActionPerformed

    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        this.limpiar();
        this.buscarpostulante.setEnabled(true);
        this.salario.setText("0");
        this.adicionalxformacion.setText("0");
        detalle_ficha.setModal(true);
        //                        (Ancho,Alto)
        detalle_ficha.setSize(700, 635);
        //Establecemos un título para el jDialog
        detalle_ficha.setTitle("Agregar Nueva Ficha de Empleado");
        detalle_ficha.setLocationRelativeTo(null);
        detalle_ficha.setVisible(true);


          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        nombre.requestFocus();
        this.codigo.setText("0");
        this.nombre.setText("");
        this.postulante.setText("0");
        this.apellido.setText("");
        this.nrocedula.setText("0");
        this.nacionalidad.setText("0");
        this.nombrenacionalidad.setText("");
        this.conyugue.setText("");
        this.localidad.setText("0");
        this.nombrelocalidad.setText("");
        this.direccion.setText("");
        this.telefono.setText("");
        this.celular.setText("");
        this.telefonourge.setText("");
        this.correo.setText("");
        this.sucursal.setText("0");
        this.nombresucursal.setText("");
        this.profesion.setText("0");
        this.nombreprofesion.setText("");
        this.cargo.setText("0");
        this.nombrecargo.setText("");
        this.departamento.setText("0");
        this.nombredepartamento.setText("");
        this.seccion.setText("0");
        this.adicionalxformacion.setText("0");
        this.salario.setText("0");
        this.nombreseccion.setText("");
        this.giraduria.setText("0");
        this.nombregiraduria.setText("");
        this.salario.setText("");
        this.adicionalxformacion.setText("");
        this.nacimiento.setCalendar(c2);
        this.fechaingreso.setCalendar(c2);
        this.ips.setSelected(false);
        this.estado.setSelected(false);
        this.estado.setSelected(false);
        this.bonificacion.setSelected(false);
        this.espanol.setSelected(false);
        this.portugues.setSelected(false);
        this.guarani.setSelected(false);
        this.aleman.setSelected(false);
        this.otroidioma.setSelected(false);
        this.nrohijos.setText("0");
        fotoFuncionario.setImagen("");
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

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        this.limpiar();
        this.buscarpostulante.setEnabled(false);
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nFila = this.jTable1.getSelectedRow();
            int nestado, nips, nboni, nespanol, nguarani, nportugues, naleman, notros = 0;
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.jTable1.requestFocus();
                return;
            } else {
                this.codigo.setText(this.jTable1.getValueAt(nFila, 0).toString());
            }
            this.codigo.setEnabled(false);
            ficha_empleadoDAO fiDAO = new ficha_empleadoDAO();
            ficha_empleado fi = null;
            try {
                fi = fiDAO.buscarId(Integer.valueOf(this.codigo.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (fi != null) {
                codigo.setText(String.valueOf(fi.getCodigo()));
                nacionalidad.setText(String.valueOf(fi.getNacionalidad().getCodigo()));
                nombrenacionalidad.setText(fi.getNacionalidad().getNombre());
                localidad.setText(String.valueOf(fi.getLocalidad().getCodigo()));
                nombrelocalidad.setText(fi.getLocalidad().getNombre());
                sucursal.setText(String.valueOf(fi.getSucursal().getCodigo()));
                nombresucursal.setText(fi.getSucursal().getNombre());
                profesion.setText(String.valueOf(fi.getProfesion().getCodigo()));
                nombreprofesion.setText(fi.getProfesion().getNombre());
                cargo.setText(String.valueOf(fi.getCargo().getCodigo()));
                nombrecargo.setText(fi.getCargo().getNombre());
                departamento.setText(String.valueOf(fi.getDepartamento().getCodigo()));
                nombredepartamento.setText(fi.getDepartamento().getNombre());
                seccion.setText(String.valueOf(fi.getSeccion().getCodigo()));
                nombreseccion.setText(fi.getSeccion().getNombre());
                giraduria.setText(String.valueOf(fi.getGiraduria().getCodigo()));
                nombregiraduria.setText(fi.getGiraduria().getNombre());
                nroseguroips.setText(fi.getNroseguroips());
                nrohijos.setText(formatea.format(fi.getNrohijos()));
                nombre.setText(fi.getNombres());
                apellido.setText(fi.getApellidos());
                sexo.setSelectedIndex(fi.getSexo() - 1);
                nrocedula.setText(String.valueOf(fi.getCedula()));
                nacimiento.setDate(fi.getFechanacimiento());
                estadocivil.setSelectedItem(fi.getEstado_civil());
                conyugue.setText(fi.getConyugue());
                direccion.setText(fi.getDireccion());
                telefono.setText(fi.getTelefono());
                celular.setText(fi.getCelular());
                telefonourge.setText(fi.getTelefono_urgencia());
                correo.setText(fi.getE_mail());
                postulante.setText(String.valueOf(fi.getPostulante()));
                fotoFuncionario.setImagen(fi.getFoto());
                nestado = fi.getEstado();
                if (nestado == 1) {
                    estado.setSelected(true);
                }

                fechaingreso.setDate(fi.getFecha_ingreso());
                salario.setText(formatea.format(fi.getSalario()));
                adicionalxformacion.setText(formatea.format(fi.getAdicionalxformacion()));
                sistemaxcobro.setSelectedIndex(fi.getSistema_cobro() - 1);
                tiposalario.setSelectedIndex(fi.getTipo_salario() - 1);

                nips = fi.getIps();
                if (nips == 1) {
                    ips.setSelected(true);
                }

                nboni = fi.getBonificacion();
                if (nboni == 1) {
                    bonificacion.setSelected(true);
                }

                nivelacademico.setSelectedIndex(fi.getAcademia() - 1);

                nespanol = fi.getEspanol();
                if (nespanol == 1) {
                    espanol.setSelected(true);
                }

                nguarani = fi.getGuarani();
                if (nguarani == 1) {
                    guarani.setSelected(true);
                }

                nportugues = fi.getPortugues();
                if (nportugues == 1) {
                    portugues.setSelected(true);
                }

                naleman = fi.getAleman();
                if (naleman == 1) {
                    aleman.setSelected(true);
                }

                notros = fi.getOtros();
                if (notros == 1) {
                    otroidioma.setSelected(true);
                }
                hijosDAO hDAO = new hijosDAO();
                int cantidadRegistro;
                cantidadRegistro = modelohijo.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelohijo.removeRow(0);
                }
                try {

                    for (hijos h : hDAO.MostrarxReferencia(Integer.valueOf(codigo.getText()))) {
                        String cSexo = "";
                        String cBonificacion = "";
                        if (h.getSexo() == 1) {
                            cSexo = "MASCULINO";
                        } else {
                            cSexo = "FEMENINO";
                        }
                        if (h.getBonificacion() == 1) {
                            cBonificacion = "SI";
                        } else {
                            cBonificacion = "NO";
                        }
                        String Datos[] = {String.valueOf(h.getIditem()), h.getNombrehijo(), formatoFecha.format(h.getFecha_nacimiento()), formatea.format(h.getEdad()), cSexo, cBonificacion};
                        modelohijo.addRow(Datos);
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                int tFilas = tablahijo.getRowCount();
                if (tFilas > 0) {
                    EditarItem.setEnabled(true);
                    BorrarItem.setEnabled(true);
                } else {
                    EditarItem.setEnabled(false);
                    BorrarItem.setEnabled(false);
                }

                detalle_ficha.setModal(true);
                //(Ancho,Alto)
                detalle_ficha.setSize(700, 635);
                //Establecemos un título para el jDialog
                detalle_ficha.setTitle("Modificar Datos de Ficha de Empleado");
                detalle_ficha.setLocationRelativeTo(null);
                detalle_ficha.setVisible(true);

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
        int nFila = jTable1.getSelectedRow();
        String num = jTable1.getValueAt(nFila, 0).toString();
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                ficha_empleadoDAO fiDAO = new ficha_empleadoDAO();
                try {
                    ficha_empleado fi = fiDAO.buscarId(Integer.valueOf(num));
                    if (fi == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        fiDAO.borrarFicha(Integer.valueOf(num));
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
                GrillaFicha GrillaFi = new GrillaFicha();
                Thread HiloGrilla = new Thread(GrillaFi);
                HiloGrilla.start();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
    }//GEN-LAST:event_EliminarActionPerformed

    private void jTable1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nFila = jTable1.getSelectedRow();

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("Emp", Integer.valueOf(jTable1.getValueAt(nFila, 0).toString()));

            JasperReport jr = null;
            //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
            URL url = getClass().getClassLoader().getResource("Reports/ficha_empleado.jasper");
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

    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_ficha.setVisible(false);
        detalle_ficha.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito

        if (this.adicionalxformacion.getText().isEmpty()) {
            this.adicionalxformacion.setText("0");
        }

        if (this.salario.getText().isEmpty()) {
            this.salario.setText("0");
        }

        if (this.nrohijos.getText().isEmpty()) {
            this.nrohijos.setText("0");
        }

        if (this.nacionalidad.getText().isEmpty() || this.nacionalidad.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Nacionalidad");
            this.nacionalidad.requestFocus();
            return;
        }

        if (this.nrocedula.getText().isEmpty() || this.nrocedula.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "No puede dejar vacío el N° de Cédula");
            this.nrocedula.requestFocus();
            return;
        }

        if (this.localidad.getText().isEmpty() || this.localidad.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "No puede dejar vacío Localidad");
            this.localidad.requestFocus();
            return;
        }

        if (this.sucursal.getText().isEmpty() || this.sucursal.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor");
            this.sucursal.requestFocus();
            return;
        }
        if (this.profesion.getText().isEmpty() || this.profesion.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor");
            this.profesion.requestFocus();
            return;
        }
        if (this.cargo.getText().isEmpty() || this.cargo.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor");
            this.cargo.requestFocus();
            return;
        }
        if (this.departamento.getText().isEmpty() || this.departamento.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor");
            this.departamento.requestFocus();
            return;
        }
        if (this.seccion.getText().isEmpty() || this.seccion.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor");
            this.seccion.requestFocus();
            return;
        }

        if (this.giraduria.getText().isEmpty() || this.giraduria.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor");
            this.giraduria.requestFocus();
            return;
        }

    /*    if (this.postulante.getText().isEmpty() || this.postulante.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione un postulante");
            this.nombre.requestFocus();
            return;
        }*/

        Boolean isFoto = false;
        ficha_empleadoDAO grabar = new ficha_empleadoDAO();
        ficha_empleado fi = new ficha_empleado();

        paisDAO paDAO = new paisDAO();
        pais pa = null;
        localidadDAO locDAO = new localidadDAO();
        localidad loc = null;
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = null;
        profesionDAO proDAO = new profesionDAO();
        profesion pro = new profesion();
        cargoDAO carDAO = new cargoDAO();
        cargo car = null;
        departamento_laboralDAO depDAO = new departamento_laboralDAO();
        departamento_laboral dep = null;
        seccionDAO seccDAO = new seccionDAO();
        seccion secc = null;
        giraduriaDAO giraDAO = new giraduriaDAO();
        giraduria gira = null;
        try {
            pa = paDAO.buscarId(Integer.valueOf(this.nacionalidad.getText()));
            loc = locDAO.buscarLocalidad(Integer.valueOf(this.localidad.getText()));
            suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            pro = proDAO.buscarId(Integer.valueOf(this.profesion.getText()));
            car = carDAO.buscarId(Integer.valueOf(this.cargo.getText()));
            dep = depDAO.buscarId(Integer.valueOf(this.departamento.getText()));
            secc = seccDAO.buscarId(Integer.valueOf(this.seccion.getText()));
            gira = giraDAO.buscarId(Integer.valueOf(this.giraduria.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (Integer.valueOf(codigo.getText()) > 0) {
            fi.setCodigo(Integer.valueOf(codigo.getText()));
        }    //CAPTURAMOS LOS DATOS DE LA CABECERA
        Date dNacimiento = ODate.de_java_a_sql(nacimiento.getDate());
        Date dFechaIngreso = ODate.de_java_a_sql(fechaingreso.getDate());

        fi.setNacionalidad(pa);
        fi.setLocalidad(loc);
        fi.setSucursal(suc);
        fi.setProfesion(pro);
        fi.setCargo(car);
        fi.setDepartamento(dep);
        fi.setSeccion(secc);
        fi.setGiraduria(gira);
        fi.setPostulante(Integer.valueOf(postulante.getText()));

        fi.setNombres(this.nombre.getText());
        fi.setApellidos(this.apellido.getText());
        if (sexo.getSelectedIndex()
                == 0) {
            fi.setSexo(1);
        } else {
            fi.setSexo(2);
        }
        fi.setCedula(Integer.valueOf(this.nrocedula.getText()));
        fi.setEstado_civil(estadocivil.getSelectedItem().toString());
        fi.setConyugue(this.conyugue.getText());
        fi.setDireccion(this.direccion.getText());
        fi.setTelefono(this.telefono.getText());
        fi.setCelular(this.celular.getText());
        fi.setTelefono_urgencia(this.telefonourge.getText());
        fi.setE_mail(this.correo.getText());
        if (fotoFuncionario.getBytes() != null) {
            fi.setFoto(fotoFuncionario.getBytes());
            /**
             * set foto*
             */
            isFoto = true;
        }

        if (estado.isSelected()) {
            fi.setEstado(1);
        } else {
            fi.setEstado(0);
        }

        fi.setFechanacimiento(dNacimiento);
        fi.setFecha_ingreso(dFechaIngreso);

        ///////////////////////EMPEZAMOS A CAPTURAR DATOS NUMERICOS///////////////
        String cSalario = this.salario.getText();
        cSalario = cSalario.replace(".", "").replace(",", ".");
        BigDecimal sal = new BigDecimal(cSalario);
        fi.setSalario(sal);
        String cAdicional = this.adicionalxformacion.getText();
        cAdicional = cAdicional.replace(".", "").replace(",", ".");
        BigDecimal adic = new BigDecimal(cAdicional);

        fi.setNrohijos(Integer.valueOf(nrohijos.getText()));
        fi.setAdicionalxformacion(adic);
        fi.setSistema_cobro(sistemaxcobro.getSelectedIndex() + 1);
        fi.setTipo_salario(tiposalario.getSelectedIndex() + 1);
        fi.setNroseguroips(nroseguroips.getText());
        if (ips.isSelected()) {
            fi.setIps(1);
        } else {
            fi.setIps(0);
        }

        if (bonificacion.isSelected()) {
            fi.setBonificacion(1);
        } else {
            fi.setBonificacion(0);
        }
        fi.setAcademia(nivelacademico.getSelectedIndex() + 1);
        if (espanol.isSelected()) {
            fi.setEspanol(1);
        } else {
            fi.setEspanol(0);
        }

        if (guarani.isSelected()) {
            fi.setGuarani(1);
        } else {
            fi.setGuarani(0);
        }

        if (portugues.isSelected()) {
            fi.setPortugues(1);
        } else {
            fi.setPortugues(0);
        }

        if (aleman.isSelected()) {
            fi.setAleman(1);
        } else {
            fi.setAleman(0);
        }

        if (otroidioma.isSelected()) {
            fi.setOtros(1);
        } else {
            fi.setOtros(0);
        }

        fi.setPreparacion_academica(preparacion_academica.getText());
        fi.setObjetivos_laborales(objetivos_laborales.getText());
        fi.setExperiencia_laboral(experiencia_laboral.getText());

        if (Integer.valueOf(this.codigo.getText()) == 0) {
            try {
                grabar.insertarficha(fi, isFoto);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                this.nombre.requestFocus();
                return;
            }
        } else {
            try {
                grabar.actualizarFicha(fi, isFoto);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                this.nombre.requestFocus();
                return;
            }

        }

        detalle_ficha.setVisible(false);
        this.detalle_ficha.setModal(false);
        GrillaFicha GrillaFi = new GrillaFicha();
        Thread HiloGrilla = new Thread(GrillaFi);

        HiloGrilla.start();

    }//GEN-LAST:event_GrabarActionPerformed

    private void detalle_fichaFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_fichaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_fichaFocusGained

    private void detalle_fichaWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_fichaWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_fichaWindowGainedFocus

    private void detalle_fichaWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_fichaWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_fichaWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange


    private void combobarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobarrioActionPerformed

    private void jTBuscarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarBarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarBarrioActionPerformed

    private void jTBuscarBarrioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarBarrioKeyPressed
        this.jTBuscarBarrio.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarBarrio.getText()).toUpperCase();
                jTBuscarBarrio.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combobarrio.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrobarrio(indiceColumnaTabla);
            }
        });
        trsfiltrobarrio = new TableRowSorter(tablabarrio.getModel());
        tablabarrio.setRowSorter(trsfiltrobarrio);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarBarrioKeyPressed

    private void tablabarrioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabarrioMouseClicked
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabarrioMouseClicked

    private void tablabarrioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabarrioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabarrioKeyPressed

    private void AceptarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.Bbarrio.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    public void filtronacionalidad(int nNumeroColumna) {
        trsfiltronacionalidad.setRowFilter(RowFilter.regexFilter(jTBuscarNacionalidad.getText(), nNumeroColumna));
    }

    public void filtrosucursal(int nNumeroColumna) {
        trsfiltrosucursal.setRowFilter(RowFilter.regexFilter(jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtroprofesion(int nNumeroColumna) {
        trsfiltroprofesion.setRowFilter(RowFilter.regexFilter(jTBuscaProfesion.getText(), nNumeroColumna));
    }

    public void filtrocargo(int nNumeroColumna) {
        trsfiltrocargo.setRowFilter(RowFilter.regexFilter(jTBuscarCargo.getText(), nNumeroColumna));
    }

    public void filtrodepartamento(int nNumeroColumna) {
        trsfiltrodepartamento.setRowFilter(RowFilter.regexFilter(jTBuscarDepartamento.getText(), nNumeroColumna));
    }

    public void filtroseccion(int nNumeroColumna) {
        trsfiltroseccion.setRowFilter(RowFilter.regexFilter(jTBuscarSeccion.getText(), nNumeroColumna));
    }

    public void filtrolocalidad(int nNumeroColumna) {
        trsfiltrolocalidad.setRowFilter(RowFilter.regexFilter(jTBuscarLocalidad.getText(), nNumeroColumna));
    }

    public void filtrogiraduria(int nNumeroColumna) {
        trsfiltrogiraduria.setRowFilter(RowFilter.regexFilter(jTBuscarGiraduria.getText(), nNumeroColumna));
    }
    private void fechaingresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaingresoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaingresoKeyPressed

    private void bonificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bonificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bonificacionActionPerformed

    private void salarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_salarioFocusGained
        salario.selectAll();
    }//GEN-LAST:event_salarioFocusGained

    private void salarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salarioKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!salario.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                adicionalxformacion.setEnabled(true);
                adicionalxformacion.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Salario", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_salarioKeyPressed

    private void guaraniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guaraniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guaraniActionPerformed

    private void portuguesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portuguesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portuguesActionPerformed

    private void nivelacademicoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nivelacademicoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nivelacademicoKeyPressed

    private void otroidiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otroidiomaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otroidiomaActionPerformed

    private void alemanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alemanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alemanActionPerformed

    private void adicionalxformacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_adicionalxformacionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_adicionalxformacionFocusGained

    private void adicionalxformacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_adicionalxformacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!adicionalxformacion.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos

            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Adicional por Cobro", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_adicionalxformacionKeyPressed

    private void sistemaxcobroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sistemaxcobroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sistemaxcobroKeyPressed

    private void tiposalarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tiposalarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tiposalarioKeyPressed

    private void ipsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipsActionPerformed

    private void combonacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combonacionalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combonacionalidadActionPerformed

    private void jTBuscarNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarNacionalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarNacionalidadActionPerformed

    private void jTBuscarNacionalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarNacionalidadKeyPressed
        this.jTBuscarNacionalidad.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarNacionalidad.getText()).toUpperCase();
                jTBuscarNacionalidad.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combonacionalidad.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 2:
                }
                repaint();
                filtronacionalidad(indiceColumnaTabla);
            }
        });
        trsfiltronacionalidad = new TableRowSorter(tablanacionalidad.getModel());
        tablanacionalidad.setRowSorter(trsfiltronacionalidad);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarNacionalidadKeyPressed

    private void tablanacionalidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablanacionalidadMouseClicked
        this.AceptarNac.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanacionalidadMouseClicked

    private void tablanacionalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablanacionalidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarNac.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanacionalidadKeyPressed

    private void AceptarNacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarNacActionPerformed
        int nFila = this.tablanacionalidad.getSelectedRow();
        this.nacionalidad.setText(this.tablanacionalidad.getValueAt(nFila, 0).toString());
        this.nombrenacionalidad.setText(this.tablanacionalidad.getValueAt(nFila, 1).toString());

        this.BNacionalidad.setVisible(false);
        this.jTBuscarNacionalidad.setText("");
        //this.tipinmueble.requestFocus();
    }//GEN-LAST:event_AceptarNacActionPerformed

    private void SalirNacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirNacActionPerformed
        this.BNacionalidad.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirNacActionPerformed

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarsucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void sucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!sucursal.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                profesion.setEnabled(true);
                profesion.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Sucursal", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_sucursalKeyPressed

    private void buscarsucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarsucursalActionPerformed
        sucursalDAO suDAO = new sucursalDAO();
        sucursal su = null;
        try {
            su = suDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (su.getCodigo() == 0) {
                BSucursal.setModal(true);
                BSucursal.setSize(500, 575);
                BSucursal.setLocationRelativeTo(null);
                BSucursal.setTitle("Buscar Sucursal");
                BSucursal.setVisible(true);
                //                giraduria.requestFocus();
                BSucursal.setModal(false);
            } else {
                nombresucursal.setText(su.getNombre());
                //Establecemos un título para el jDialog
            }
            profesion.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarsucursalActionPerformed

    private void nombresucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombresucursalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombresucursalKeyPressed

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
                }
                repaint();
                filtrosucursal(indiceColumnaTabla);
            }
        });
        trsfiltrosucursal = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltrosucursal);
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
        this.profesion.requestFocus();
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void profesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profesionActionPerformed
        this.buscarprofesion.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_profesionActionPerformed

    private void profesionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_profesionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!profesion.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                cargo.setEnabled(true);
                cargo.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Profesión", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_profesionKeyPressed

    private void buscarprofesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarprofesionActionPerformed
        profesionDAO proDAO = new profesionDAO();
        profesion pro = null;
        try {
            pro = proDAO.buscarId(Integer.valueOf(this.profesion.getText()));
            if (pro.getCodigo() == 0) {
                BProfesion.setModal(true);
                BProfesion.setSize(500, 575);
                BProfesion.setLocationRelativeTo(null);
                BProfesion.setTitle("Buscar Profesion");
                BProfesion.setVisible(true);
                //                giraduria.requestFocus();
                BProfesion.setModal(false);
            } else {
                nombreprofesion.setText(pro.getNombre());
                //Establecemos un título para el jDialog
            }
            cargo.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarprofesionActionPerformed

    private void nombreprofesionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreprofesionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreprofesionKeyPressed

    private void comboprofesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboprofesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboprofesionActionPerformed

    private void jTBuscaProfesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscaProfesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscaProfesionActionPerformed

    private void jTBuscaProfesionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscaProfesionKeyPressed
        this.jTBuscaProfesion.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscaProfesion.getText()).toUpperCase();
                jTBuscaProfesion.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboprofesion.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 2:

                }
                repaint();
                filtroprofesion(indiceColumnaTabla);
            }
        });
        trsfiltroprofesion = new TableRowSorter(tablaprofesion.getModel());
        tablaprofesion.setRowSorter(trsfiltroprofesion);
        // TODO add your handling code here:*/
    }//GEN-LAST:event_jTBuscaProfesionKeyPressed

    private void tablaprofesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaprofesionMouseClicked
        this.AceptarPro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprofesionMouseClicked

    private void tablaprofesionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaprofesionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPro.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprofesionKeyPressed

    private void AceptarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProActionPerformed
        int nFila = this.tablaprofesion.getSelectedRow();
        this.profesion.setText(this.tablaprofesion.getValueAt(nFila, 0).toString());
        this.nombreprofesion.setText(this.tablaprofesion.getValueAt(nFila, 1).toString());

        this.BProfesion.setVisible(false);
        this.jTBuscaProfesion.setText("");
        // this.tipinmueble.requestFocus();
    }//GEN-LAST:event_AceptarProActionPerformed

    private void SalirProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProActionPerformed
        this.BProfesion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProActionPerformed

    private void giraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giraduriaActionPerformed
        this.buscargiraduria.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_giraduriaActionPerformed

    private void giraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giraduriaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!giraduria.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos

            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Giraduria", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_giraduriaKeyPressed

    private void buscargiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscargiraduriaActionPerformed
        giraduriaDAO giraDAO = new giraduriaDAO();
        giraduria gira = null;
        try {
            gira = giraDAO.buscarId(Integer.valueOf(this.giraduria.getText()));
            if (gira.getCodigo() == 0) {
                BGiraduria.setModal(true);
                BGiraduria.setSize(500, 575);
                BGiraduria.setLocationRelativeTo(null);
                BGiraduria.setTitle("Buscar Giraduria");
                BGiraduria.setVisible(true);
                BGiraduria.setModal(false);
            } else {
                nombregiraduria.setText(gira.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscargiraduriaActionPerformed

    private void nombregiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombregiraduriaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombregiraduriaKeyPressed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    private void jTBuscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaActionPerformed

    private void jTBuscarGiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaKeyPressed
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
                    case 2:
                }
                repaint();
                filtrogiraduria(indiceColumnaTabla);
            }
        });
        trsfiltrogiraduria = new TableRowSorter(tablagiraduria.getModel());
        tablagiraduria.setRowSorter(trsfiltrogiraduria);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaKeyPressed

    private void tablagiraduriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablagiraduriaMouseClicked
        this.AceptarGira.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaMouseClicked

    private void tablagiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablagiraduriaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGira.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaKeyPressed

    private void AceptarGiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGiraActionPerformed
        int nFila = this.tablagiraduria.getSelectedRow();
        this.giraduria.setText(this.tablagiraduria.getValueAt(nFila, 0).toString());
        this.nombregiraduria.setText(this.tablagiraduria.getValueAt(nFila, 1).toString());

        this.BGiraduria.setVisible(false);
        this.jTBuscarGiraduria.setText("");
        //this.tipinmueble.requestFocus();
    }//GEN-LAST:event_AceptarGiraActionPerformed

    private void SalirGiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGiraActionPerformed
        this.BGiraduria.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGiraActionPerformed

    private void combolocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combolocalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combolocalidadActionPerformed

    private void jTBuscarLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarLocalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarLocalidadActionPerformed

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

                }
                repaint();
                filtrolocalidad(indiceColumnaTabla);
            }
        });
        trsfiltrolocalidad = new TableRowSorter(tablalocalidad.getModel());
        tablalocalidad.setRowSorter(trsfiltrolocalidad);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarLocalidadKeyPressed

    private void tablalocalidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablalocalidadMouseClicked
        this.AceptarLoc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablalocalidadMouseClicked

    private void tablalocalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablalocalidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarLoc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablalocalidadKeyPressed

    private void AceptarLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarLocActionPerformed
        int nFila = this.tablalocalidad.getSelectedRow();
        this.localidad.setText(this.tablalocalidad.getValueAt(nFila, 0).toString());
        this.nombrelocalidad.setText(this.tablalocalidad.getValueAt(nFila, 1).toString());

        this.BLocalidad.setVisible(false);
        this.jTBuscarLocalidad.setText("");
        this.direccion.requestFocus();
    }//GEN-LAST:event_AceptarLocActionPerformed

    private void SalirLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirLocActionPerformed
        this.BLocalidad.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirLocActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();
    }//GEN-LAST:event_sucursalFocusGained

    private void profesionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_profesionFocusGained
        profesion.selectAll();
    }//GEN-LAST:event_profesionFocusGained

    private void giraduriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_giraduriaFocusGained
        giraduria.selectAll();
    }//GEN-LAST:event_giraduriaFocusGained

    private void cargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargoActionPerformed
        this.buscarcargo.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cargoActionPerformed

    private void cargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cargoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!cargo.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                departamento.setEnabled(true);
                departamento.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Cargo", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_cargoKeyPressed

    private void buscarcargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcargoActionPerformed
        cargoDAO carDAO = new cargoDAO();
        cargo car = null;
        try {
            car = carDAO.buscarId(Integer.valueOf(this.cargo.getText()));
            if (car.getCodigo() == 0) {
                BCargo.setModal(true);
                BCargo.setSize(500, 575);
                BCargo.setLocationRelativeTo(null);
                BCargo.setTitle("Buscar Cargo");
                BCargo.setVisible(true);
                //                giraduria.requestFocus();
                BCargo.setModal(false);
            } else {
                nombrecargo.setText(car.getNombre());
                //Establecemos un título para el jDialog
            }
            departamento.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarcargoActionPerformed

    private void nombrecargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrecargoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecargoKeyPressed

    private void combocargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocargoActionPerformed

    private void jTBuscarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCargoActionPerformed

    private void jTBuscarCargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCargoKeyPressed
        this.jTBuscarCargo.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCargo.getText()).toUpperCase();
                jTBuscarCargo.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocargo.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo

                }
                repaint();
                filtrocargo(indiceColumnaTabla);
            }
        });
        trsfiltrocargo = new TableRowSorter(tablacargo.getModel());
        tablacargo.setRowSorter(trsfiltrocargo);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCargoKeyPressed

    private void tablacargoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacargoMouseClicked
        this.AceptarCar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacargoMouseClicked

    private void tablacargoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacargoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacargoKeyPressed

    private void AceptarCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCarActionPerformed
        int nFila = this.tablacargo.getSelectedRow();
        this.cargo.setText(this.tablacargo.getValueAt(nFila, 0).toString());
        this.nombrecargo.setText(this.tablacargo.getValueAt(nFila, 1).toString());

        this.BCargo.setVisible(false);
        this.jTBuscarCargo.setText("");
        this.departamento.requestFocus();
    }//GEN-LAST:event_AceptarCarActionPerformed

    private void SalirCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCarActionPerformed
        this.BCargo.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCarActionPerformed

    private void cargoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cargoFocusGained
        cargo.selectAll();
    }//GEN-LAST:event_cargoFocusGained

    private void departamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departamentoActionPerformed
        this.buscardepartamento.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_departamentoActionPerformed

    private void departamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_departamentoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!departamento.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                seccion.setEnabled(true);
                seccion.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Departamento", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_departamentoKeyPressed

    private void buscardepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscardepartamentoActionPerformed
        departamento_laboralDAO dpDAO = new departamento_laboralDAO();
        departamento_laboral dp = null;
        try {
            dp = dpDAO.buscarId(Integer.valueOf(this.departamento.getText()));
            if (dp.getCodigo() == 0) {
                BDepartamento_laboral.setModal(true);
                BDepartamento_laboral.setSize(500, 575);
                BDepartamento_laboral.setLocationRelativeTo(null);
                BDepartamento_laboral.setTitle("Buscar Departamento Laboral");
                BDepartamento_laboral.setVisible(true);
                //                giraduria.requestFocus();
                BDepartamento_laboral.setModal(false);
            } else {
                nombredepartamento.setText(dp.getNombre());
                //Establecemos un título para el jDialog
            }
            seccion.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscardepartamentoActionPerformed

    private void nombredepartamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombredepartamentoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombredepartamentoKeyPressed

    private void combodepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combodepartamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combodepartamentoActionPerformed

    private void jTBuscarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarDepartamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarDepartamentoActionPerformed

    private void jTBuscarDepartamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarDepartamentoKeyPressed
        this.jTBuscarDepartamento.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarDepartamento.getText()).toUpperCase();
                jTBuscarDepartamento.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combodepartamento.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrodepartamento(indiceColumnaTabla);
            }
        });
        trsfiltrodepartamento = new TableRowSorter(tabladepartamento.getModel());
        tabladepartamento.setRowSorter(trsfiltrodepartamento);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarDepartamentoKeyPressed

    private void tabladepartamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladepartamentoMouseClicked
        this.AceptarDep.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladepartamentoMouseClicked

    private void tabladepartamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabladepartamentoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarDep.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladepartamentoKeyPressed

    private void AceptarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarDepActionPerformed
        int nFila = this.tabladepartamento.getSelectedRow();
        this.departamento.setText(this.tabladepartamento.getValueAt(nFila, 0).toString());
        this.nombredepartamento.setText(this.tabladepartamento.getValueAt(nFila, 1).toString());

        this.BDepartamento_laboral.setVisible(false);
        this.jTBuscarDepartamento.setText("");
        this.seccion.requestFocus();
    }//GEN-LAST:event_AceptarDepActionPerformed

    private void SalirDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirDepActionPerformed
        this.BDepartamento_laboral.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirDepActionPerformed

    private void departamentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_departamentoFocusGained
        departamento.selectAll();
    }//GEN-LAST:event_departamentoFocusGained

    private void seccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seccionActionPerformed
        this.buscarseccion.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_seccionActionPerformed

    private void seccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_seccionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!seccion.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                giraduria.setEnabled(true);
                giraduria.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Sección", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_seccionKeyPressed

    private void buscarseccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarseccionActionPerformed
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
            giraduria.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarseccionActionPerformed

    private void nombreseccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreseccionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreseccionKeyPressed

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
        this.giraduria.requestFocus();
    }//GEN-LAST:event_AceptarSeccActionPerformed

    private void SalirSeccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSeccActionPerformed
        this.BSeccion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSeccActionPerformed

    private void seccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_seccionFocusGained
        seccion.selectAll();
    }//GEN-LAST:event_seccionFocusGained

    private void correoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_correoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_correoKeyReleased

    private void correoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_correoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!telefonourge.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos

            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Correo Electronico", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_correoKeyPressed

    private void correoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correoActionPerformed

    private void correoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_correoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_correoFocusGained

    private void telefonourgeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonourgeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonourgeKeyReleased

    private void telefonourgeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonourgeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!telefonourge.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                correo.setEnabled(true);
                correo.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el N° de Teléfono", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_telefonourgeKeyPressed

    private void telefonourgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonourgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonourgeActionPerformed

    private void telefonourgeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonourgeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonourgeFocusGained

    private void celularKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_celularKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_celularKeyReleased

    private void celularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_celularKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!celular.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                telefonourge.setEnabled(true);
                telefonourge.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el N° de Celular", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_celularKeyPressed

    private void celularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celularActionPerformed

    private void celularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_celularFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_celularFocusGained

    private void telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoKeyReleased

    private void telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!telefono.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                celular.setEnabled(true);
                celular.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el N° de Teléfono", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_telefonoKeyPressed

    private void telefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoActionPerformed

    private void telefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoFocusGained

    private void nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.apellido.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.postulante.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyReleased

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed

    }//GEN-LAST:event_nombreKeyPressed

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed

    private void nombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusGained
        if (Integer.valueOf(codigo.getText()) == 0) {
            postulanteDAO fiDAO = new postulanteDAO();
            postulante fi = null;
            try {
                fi = fiDAO.buscarId(Integer.valueOf(this.postulante.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (fi != null) {
                nombre.setText(fi.getNombres());
                apellido.setText(fi.getApellidos());
                sexo.setSelectedIndex(fi.getSexo() - 1);
                nrocedula.setText(String.valueOf(fi.getCedula()));
                nacimiento.setDate(fi.getFechanacimiento());
                estadocivil.setSelectedItem(fi.getEstado_civil());
                conyugue.setText(fi.getConyugue());
                direccion.setText(fi.getDireccion());
                telefono.setText(fi.getTelefono());
                estado.isSelected();
                experiencia_laboral.setText(fi.getExperiencia_laboral());
                preparacion_academica.setText(fi.getPreparacion_academica());
                objetivos_laborales.setText(fi.getObjetivos_laborales());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusGained

    private void nombrelocalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrelocalidadKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrelocalidadKeyPressed

    private void buscarlocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarlocalidadActionPerformed
        localidadDAO locDAO = new localidadDAO();
        localidad loc = null;
        try {
            loc = locDAO.buscarLocalidad(Integer.valueOf(this.localidad.getText()));
            if (loc.getCodigo() == 0) {
                GrillaLocalidad grillaloc = new GrillaLocalidad();
                Thread hiloloc = new Thread(grillaloc);
                hiloloc.start();
                BLocalidad.setModal(true);
                BLocalidad.setSize(500, 575);
                BLocalidad.setLocationRelativeTo(null);
                BLocalidad.setTitle("Buscar Localidad");
                BLocalidad.setVisible(true);
                //                giraduria.requestFocus();
                BLocalidad.setModal(false);
            } else {
                nombrelocalidad.setText(loc.getNombre());
                //Establecemos un título para el jDialog
            }
            direccion.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarlocalidadActionPerformed

    private void localidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_localidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!localidad.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                direccion.setEnabled(true);
                direccion.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "IPor Favor Ingrese la Localidad", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_localidadKeyPressed

    private void localidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localidadActionPerformed
        this.buscarlocalidad.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_localidadActionPerformed

    private void localidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_localidadFocusGained
        localidad.selectAll();
    }//GEN-LAST:event_localidadFocusGained

    private void nrocedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrocedulaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nrocedulaKeyReleased

    private void nrocedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrocedulaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!nrocedula.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                nacionalidad.setEnabled(true);
                nacionalidad.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el N° de Cedula", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_nrocedulaKeyPressed

    private void nrocedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nrocedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nrocedulaActionPerformed

    private void nrocedulaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nrocedulaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nrocedulaFocusGained

    private void conyugueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_conyugueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_conyugueKeyReleased

    private void conyugueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_conyugueKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!conyugue.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                localidad.setEnabled(true);
                localidad.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese Nombre del Conyugue", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_conyugueKeyPressed

    private void conyugueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conyugueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conyugueActionPerformed

    private void conyugueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_conyugueFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_conyugueFocusGained

    private void nombrenacionalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrenacionalidadKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrenacionalidadKeyPressed

    private void buscarnacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarnacionalidadActionPerformed
        paisDAO paDAO = new paisDAO();
        pais pa = null;
        try {
            pa = paDAO.buscarpaisId(String.valueOf(this.nacionalidad.getText()));
            if (pa.getCodigo() == 0) {
                GrillaNacionalidad grillaNac = new GrillaNacionalidad();
                Thread hiloNac = new Thread(grillaNac);
                hiloNac.start();
                BNacionalidad.setModal(true);
                BNacionalidad.setSize(500, 575);
                BNacionalidad.setLocationRelativeTo(null);
                BNacionalidad.setTitle("Buscar Nacionalidad");
                BNacionalidad.setVisible(true);
                //                giraduria.requestFocus();
                BNacionalidad.setModal(false);
            } else {
                nombrenacionalidad.setText(pa.getNombre());
                //Establecemos un título para el jDialog
            }
            nombrenacionalidad.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarnacionalidadActionPerformed

    private void nacionalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacionalidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!nacionalidad.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                conyugue.setEnabled(true);
                conyugue.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Nacionalidad", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_nacionalidadKeyPressed

    private void nacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nacionalidadActionPerformed
        this.buscarnacionalidad.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_nacionalidadActionPerformed

    private void nacionalidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nacionalidadFocusGained
        nacionalidad.selectAll();
    }//GEN-LAST:event_nacionalidadFocusGained

    private void estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estadoActionPerformed

    private void nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoKeyPressed

    private void sexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sexoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sexoKeyPressed

    private void estadocivilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_estadocivilKeyPressed

    }//GEN-LAST:event_estadocivilKeyPressed

    private void apellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoKeyReleased
        String letras = ConvertirMayusculas.cadena(direccion);
        direccion.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoKeyReleased

    private void apellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!apellido.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                nrocedula.setEnabled(true);
                nrocedula.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Apellido", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_apellidoKeyPressed

    private void apellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoActionPerformed

    private void apellidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_apellidoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoFocusGained

    private void direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyReleased
        String letras = ConvertirMayusculas.cadena(direccion);
        direccion.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionKeyReleased

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!direccion.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                telefono.setEnabled(true);
                telefono.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Dirección", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_direccionKeyPressed

    private void direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionActionPerformed

    private void direccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionFocusGained

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void buscapersonaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscapersonaKeyPressed
        this.buscapersona.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscapersona.getText()).toUpperCase();
                buscapersona.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 0;
                        break;//por factura
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por sucursal
                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por nombre del cliente
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscapersonaKeyPressed

    private void sacarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sacarFotoActionPerformed
        //fotoFuncionario.getBytes() para obtener foto
        //Creamos filtro
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes", "jpg", "png", "gif", "jpeg");
        //Creacion del FileChooser
        JFileChooser filechooser = new JFileChooser();
        //se adjunta el filtro
        filechooser.setFileFilter(filter);
        //titulo de la ventana
        filechooser.setDialogTitle("Seleccionar Imagen..");
        //Se crea carpeta predeterminada
        File archivo = new File(System.getProperty("user.home") + "/Pictures"); //para que abra la carpeta mis imagenes
        //se lo establece al filechooser
        filechooser.setCurrentDirectory(archivo);
        //Se establece la apertura de la ventana Abrir
        int result = filechooser.showOpenDialog(null);
        //Condicionante para obtener la ruta del archivo seleccionado
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            try {
                fotoFuncionario.setImagen(ImageIO.read(file));
                this.repaint();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }//GEN-LAST:event_sacarFotoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nFila = jTable1.getSelectedRow();

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);

            JasperReport jr = null;
            //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
            URL url = getClass().getClassLoader().getResource("Reports/listadoempleados.jasper");
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
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void generarcontratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarcontratoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generarcontratoActionPerformed

    private void nombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusLost
        String letras = ConvertirMayusculas.cadena(nombre);
        nombre.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusLost

    private void apellidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_apellidoFocusLost
        String letras = ConvertirMayusculas.cadena(apellido);
        apellido.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoFocusLost

    private void postulanteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_postulanteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_postulanteKeyReleased

    private void buscarpostulanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarpostulanteActionPerformed
        GrillaPostulante grillabco = new GrillaPostulante();
        Thread hilobco = new Thread(grillabco);
        hilobco.start();
        BPostulante.setModal(true);
        BPostulante.setSize(482, 575);
        BPostulante.setLocationRelativeTo(null);
        BPostulante.setVisible(true);
        BPostulante.setModal(true);        // TODO add your handling code here:
    }//GEN-LAST:event_buscarpostulanteActionPerformed

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
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarCasa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasa1ActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.postulante.setText(this.tablabanco.getValueAt(nFila, 0).toString());

        this.BPostulante.setVisible(false);
        this.jTBuscarbanco.setText("");
        this.nombre.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasa1ActionPerformed

    private void TituloBanco() {
        modelobanco.addColumn("Código");
        modelobanco.addColumn("Nombres y Apellidos");

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

    private void SalirCasa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasa1ActionPerformed
        this.BPostulante.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasa1ActionPerformed

    private void AgregarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarItemActionPerformed
        LimpiarItem();

        itemCuentas.setModal(true);
        itemCuentas.setSize(533, 310);
        //Establecemos un título para el jDialog
        itemCuentas.setLocationRelativeTo(null);
        itemCuentas.setVisible(true);
        nombrehijo.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarItemActionPerformed

    private void EditarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarItemActionPerformed
        int nFila = this.tablahijo.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        String cNumero = tablahijo.getValueAt(nFila, 0).toString();
        idItem.setText(cNumero);

        hijosDAO hDAO = new hijosDAO();
        hijos h = null;

        try {
            h = hDAO.buscarId(Double.valueOf(idItem.getText()));
            nombrehijo.setText(h.getNombrehijo());
            this.nacimientohijo.setDate(h.getFecha_nacimiento());
            sexohijo.setSelectedIndex(h.getSexo() - 1);
            cobrabonificacion.setSelectedIndex(h.getBonificacion() - 1);

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        itemCuentas.setModal(true);
        itemCuentas.setSize(533, 310);
        //Establecemos un título para el jDialog
        itemCuentas.setLocationRelativeTo(null);
        itemCuentas.setVisible(true);
        nombrehijo.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_EditarItemActionPerformed

    private void BorrarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarItemActionPerformed
        int nFila = this.tablahijo.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        String cNumero = tablahijo.getValueAt(nFila, 0).toString();
        idItem.setText(cNumero);

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Borrar este Item ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            hijosDAO dcDAO = new hijosDAO();
            try {
                dcDAO.EliminarItem(Double.valueOf(idItem.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.actualizarhijo();

        }   // TODO add your handling code here:
    }//GEN-LAST:event_BorrarItemActionPerformed

    private void nombrehijoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrehijoKeyPressed
    }//GEN-LAST:event_nombrehijoKeyPressed

    private void GrabarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Detalle? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            hijos dc = new hijos();
            hijosDAO dcDAO = new hijosDAO();

            if (this.nombrehijo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese Descripción");
                this.nombrehijo.requestFocus();
                return;
            }
            Date dNacimiento = ODate.de_java_a_sql(nacimientohijo.getDate());

            ficha_empleadoDAO plDAO = new ficha_empleadoDAO();
            ficha_empleado pl = null;
            try {
                pl = plDAO.buscarId(Integer.valueOf(this.codigo.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            dc.setId_empleado(pl);
            dc.setNombrehijo(nombrehijo.getText());
            dc.setFecha_nacimiento(dNacimiento);
            dc.setSexo(sexohijo.getSelectedIndex() + 1);
            dc.setBonificacion(sexohijo.getSelectedIndex() + 1);
            dc.setIditem(Double.valueOf(idItem.getText()));

            try {
                if (dc.getIditem() == 0) {
                    dcDAO.insertarItem(dc);
                    this.LimpiarItem();
                } else {
                    dcDAO.actualizarItem(dc);
                    this.actualizarhijo();
                    this.SalirItem.doClick();
                }
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.actualizarhijo();
            this.nombrehijo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemActionPerformed

    private void LimpiarItem() {
        nombrehijo.setText("");
        sexohijo.setSelectedIndex(0);
        cobrabonificacion.setSelectedIndex(0);
        idItem.setText("0");
    }

    private void actualizarhijo() {
        hijosDAO hDAO = new hijosDAO();
        int cantidadRegistro;
        cantidadRegistro = modelohijo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelohijo.removeRow(0);
        }
        try {

            for (hijos h : hDAO.MostrarxReferencia(Integer.valueOf(codigo.getText()))) {
                String cSexo = "";
                String cBonificacion = "";
                if (h.getSexo() == 1) {
                    cSexo = "MASCULINO";
                } else {
                    cSexo = "FEMENINO";
                }
                if (h.getBonificacion() == 1) {
                    cBonificacion = "SI";
                } else {
                    cBonificacion = "NO";
                }
                String Datos[] = {String.valueOf(h.getIditem()), h.getNombrehijo(), formatoFecha.format(h.getFecha_nacimiento()), formatea.format(h.getEdad()), cSexo, cBonificacion};
                modelohijo.addRow(Datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

        int tFilas = tablahijo.getRowCount();
        if (tFilas > 0) {
            EditarItem.setEnabled(true);
            BorrarItem.setEnabled(true);
        } else {
            EditarItem.setEnabled(false);
            BorrarItem.setEnabled(false);
        }

    }

    private void SalirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        this.itemCuentas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void nacimientohijoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientohijoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientohijoKeyPressed
    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscapersona.getText(), nNumeroColumna));
    }

    public void filtrobarrio(int nNumeroColumna) {
        trsfiltrobarrio.setRowFilter(RowFilter.regexFilter(this.jTBuscarBarrio.getText(), nNumeroColumna));
    }

    private void TituloHijo() {
        modelohijo.addColumn("id");
        modelohijo.addColumn("Nombres y Apellidos");
        modelohijo.addColumn("Nacimiento");
        modelohijo.addColumn("Edad");
        modelohijo.addColumn("Sexo");
        modelohijo.addColumn("Bonificacion");

        int[] anchos = {10, 200, 100, 100, 100, 120};
        for (int i = 0; i < modelohijo.getColumnCount(); i++) {
            tablahijo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablahijo.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablahijo.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablahijo.setFont(font);
        this.tablahijo.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablahijo.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablahijo.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablahijo.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.tablahijo.getColumnModel().getColumn(3).setCellRenderer(AlinearCentro);
        this.tablahijo.getColumnModel().getColumn(4).setCellRenderer(AlinearCentro);
        this.tablahijo.getColumnModel().getColumn(5).setCellRenderer(AlinearCentro);

    }

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cédula");
        modelo.addColumn("Nacimiento");
        modelo.addColumn("Nacionalidad");
        modelo.addColumn("Dirección");
        modelo.addColumn("Télefono");
        modelo.addColumn("E-mail");
        modelo.addColumn("Profesión");

        int[] anchos = {80, 120, 120, 120, 120, 120, 120, 200, 180};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.jTable1.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.jTable1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);

    }

    private void TitNacionalidad() {
        modelonacionalidad.addColumn("Código");
        modelonacionalidad.addColumn("Nombre");

        int[] anchos = {40, 50};
        for (int i = 0; i < modelonacionalidad.getColumnCount(); i++) {
            tablanacionalidad.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablanacionalidad.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablanacionalidad.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablanacionalidad.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablanacionalidad.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitSucursal() {
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

    private void TitProfesion() {
        modeloprofesion.addColumn("Código");
        modeloprofesion.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloprofesion.getColumnCount(); i++) {
            tablaprofesion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaprofesion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaprofesion.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaprofesion.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaprofesion.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitCargo() {
        modelocargo.addColumn("Código");
        modelocargo.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelocargo.getColumnCount(); i++) {
            tablacargo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacargo.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacargo.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacargo.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacargo.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitDepartamento() {
        modelodepartamento.addColumn("Código");
        modelodepartamento.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelodepartamento.getColumnCount(); i++) {
            tabladepartamento.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladepartamento.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladepartamento.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tabladepartamento.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tabladepartamento.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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

    private void TitLocalidad() {
        modelolocalidad.addColumn("Código");
        modelolocalidad.addColumn("Nombre");

        int[] anchos = {40, 50};
        for (int i = 0; i < modelolocalidad.getColumnCount(); i++) {
            tablalocalidad.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablalocalidad.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablalocalidad.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablalocalidad.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablalocalidad.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitGiraduria() {
        modelogiraduria.addColumn("Código");
        modelogiraduria.addColumn("Nombre");

        int[] anchos = {40, 50};
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

    private void TitBarrios() {
        modelobarrio.addColumn("Código");
        modelobarrio.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelobarrio.getColumnCount(); i++) {
            tablabarrio.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablabarrio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablabarrio.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablabarrio.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear
        this.tablabarrio.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);

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
                new ficha_de_empleados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCar;
    private javax.swing.JButton AceptarCasa;
    private javax.swing.JButton AceptarCasa1;
    private javax.swing.JButton AceptarDep;
    private javax.swing.JButton AceptarGira;
    private javax.swing.JButton AceptarLoc;
    private javax.swing.JButton AceptarNac;
    private javax.swing.JButton AceptarPro;
    private javax.swing.JButton AceptarSecc;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton Agregar;
    private javax.swing.JButton AgregarItem;
    private javax.swing.JDialog BCargo;
    private javax.swing.JDialog BDepartamento_laboral;
    private javax.swing.JDialog BGiraduria;
    private javax.swing.JDialog BLocalidad;
    private javax.swing.JDialog BNacionalidad;
    private javax.swing.JDialog BPostulante;
    private javax.swing.JDialog BProfesion;
    private javax.swing.JDialog BSeccion;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JDialog Bbarrio;
    private javax.swing.JButton BorrarItem;
    private javax.swing.JButton EditarItem;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton GrabarItem;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCar;
    private javax.swing.JButton SalirCasa;
    private javax.swing.JButton SalirCasa1;
    private javax.swing.JButton SalirDep;
    private javax.swing.JButton SalirGira;
    private javax.swing.JButton SalirItem;
    private javax.swing.JButton SalirLoc;
    private javax.swing.JButton SalirNac;
    private javax.swing.JButton SalirPro;
    private javax.swing.JButton SalirSecc;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JLabel Ubicación;
    private javax.swing.JLabel Ubicación1;
    private javax.swing.JLabel Ubicación2;
    private javax.swing.JLabel Ubicación3;
    private org.edisoncor.gui.label.LabelMetric Unidades;
    private javax.swing.JFormattedTextField adicionalxformacion;
    private javax.swing.JCheckBox aleman;
    private javax.swing.JTextField apellido;
    private javax.swing.JCheckBox bonificacion;
    private javax.swing.JButton botonsalir;
    private javax.swing.JTextField buscapersona;
    private javax.swing.JButton buscarcargo;
    private javax.swing.JButton buscardepartamento;
    private javax.swing.JButton buscargiraduria;
    private javax.swing.JButton buscarlocalidad;
    private javax.swing.JButton buscarnacionalidad;
    private javax.swing.JButton buscarpostulante;
    private javax.swing.JButton buscarprofesion;
    private javax.swing.JButton buscarseccion;
    private javax.swing.JButton buscarsucursal;
    private javax.swing.JTextField cargo;
    private javax.swing.JTextField celular;
    private javax.swing.JComboBox<String> cobrabonificacion;
    private javax.swing.JTextField codigo;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox combobarrio;
    private javax.swing.JComboBox combocargo;
    private javax.swing.JComboBox combodepartamento;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JComboBox combolocalidad;
    private javax.swing.JComboBox combonacionalidad;
    private javax.swing.JComboBox comboprofesion;
    private javax.swing.JComboBox comboseccion;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JTextField conyugue;
    private javax.swing.JTextField correo;
    private javax.swing.JTextField departamento;
    private javax.swing.JDialog detalle_ficha;
    private javax.swing.JTextField direccion;
    private javax.swing.JCheckBox espanol;
    private javax.swing.JCheckBox estado;
    private javax.swing.JComboBox<String> estadocivil;
    private javax.swing.JTextArea experiencia_laboral;
    private com.toedter.calendar.JDateChooser fechaingreso;
    private JPanelWebCam.JPanelWebCam fotoFuncionario;
    private javax.swing.JButton generarcontrato;
    private javax.swing.JTextField giraduria;
    private javax.swing.JCheckBox guarani;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField idItem;
    private javax.swing.JCheckBox ips;
    private javax.swing.JDialog itemCuentas;
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
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscaProfesion;
    private javax.swing.JTextField jTBuscarBarrio;
    private javax.swing.JTextField jTBuscarCargo;
    private javax.swing.JTextField jTBuscarDepartamento;
    private javax.swing.JTextField jTBuscarGiraduria;
    private javax.swing.JTextField jTBuscarLocalidad;
    private javax.swing.JTextField jTBuscarNacionalidad;
    private javax.swing.JTextField jTBuscarSeccion;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTBuscarbanco;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField localidad;
    private com.toedter.calendar.JDateChooser nacimiento;
    private com.toedter.calendar.JDateChooser nacimientohijo;
    private javax.swing.JTextField nacionalidad;
    private javax.swing.JComboBox<String> nivelacademico;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombrecargo;
    private javax.swing.JTextField nombredepartamento;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nombrehijo;
    private javax.swing.JTextField nombrelocalidad;
    private javax.swing.JTextField nombrenacionalidad;
    private javax.swing.JTextField nombreprofesion;
    private javax.swing.JTextField nombreseccion;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nrocedula;
    private javax.swing.JFormattedTextField nrohijos;
    private javax.swing.JTextField nroseguroips;
    private javax.swing.JTextArea objetivos_laborales;
    private javax.swing.JCheckBox otroidioma;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JCheckBox portugues;
    private javax.swing.JTextField postulante;
    private javax.swing.JTextArea preparacion_academica;
    private javax.swing.JTextField profesion;
    private javax.swing.JButton sacarFoto;
    private javax.swing.JFormattedTextField salario;
    private javax.swing.JTextField seccion;
    private javax.swing.JComboBox<String> sexo;
    private javax.swing.JComboBox<String> sexohijo;
    private javax.swing.JComboBox<String> sistemaxcobro;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablabanco;
    private javax.swing.JTable tablabarrio;
    private javax.swing.JTable tablacargo;
    private javax.swing.JTable tabladepartamento;
    private javax.swing.JTable tablagiraduria;
    private javax.swing.JTable tablahijo;
    private javax.swing.JTable tablalocalidad;
    private javax.swing.JTable tablanacionalidad;
    private javax.swing.JTable tablaprofesion;
    private javax.swing.JTable tablaseccion;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTextField telefono;
    private javax.swing.JTextField telefonourge;
    private javax.swing.JComboBox<String> tiposalario;
    // End of variables declaration//GEN-END:variables

    private class GrillaFicha extends Thread {

        public void run() {
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            ficha_empleadoDAO fiDAO = new ficha_empleadoDAO();
            try {
                for (ficha_empleado fi : fiDAO.Todos()) {
                    String Datos[] = {String.valueOf(fi.getCodigo()), fi.getNombres(), String.valueOf(fi.getCedula()), formatoFecha.format(fi.getFechanacimiento()), fi.getNacionalidad().getNombre(), fi.getDireccion(), fi.getTelefono(), fi.getE_mail(), fi.getProfesion().getNombre()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            jTable1.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = jTable1.getRowCount();
            if (cantFilas > 0) {
                Modificar.setEnabled(true);
                Eliminar.setEnabled(true);
                Listar.setEnabled(true);
                generarcontrato.setEnabled(true);
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                Listar.setEnabled(false);
                generarcontrato.setEnabled(false);
            }
        }
    }

    private class GrillaNacionalidad extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelonacionalidad.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelonacionalidad.removeRow(0);
            }

            paisDAO DAOpa = new paisDAO();
            try {
                for (pais pa : DAOpa.todos()) {
                    String Datos[] = {String.valueOf(pa.getCodigo()), String.valueOf(pa.getNombre())};
                    modelonacionalidad.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablanacionalidad.setRowSorter(new TableRowSorter(modelonacionalidad));
            int cantFilas = tablanacionalidad.getRowCount();
        }
    }

    private class GrillaSucursal extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelosucursal.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelosucursal.removeRow(0);
            }

            sucursalDAO DAOsu = new sucursalDAO();
            try {
                for (sucursal su : DAOsu.todos()) {
                    String Datos[] = {String.valueOf(su.getCodigo()), String.valueOf(su.getNombre())};
                    modelosucursal.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablasucursal.setRowSorter(new TableRowSorter(modelosucursal));
            int cantFilas = tablasucursal.getRowCount();
        }
    }

    private class GrillaProfesion extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloprofesion.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloprofesion.removeRow(0);
            }

            profesionDAO DAOpro = new profesionDAO();
            try {
                for (profesion pro : DAOpro.Todos()) {
                    String Datos[] = {String.valueOf(pro.getCodigo()), String.valueOf(pro.getNombre())};
                    modeloprofesion.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaprofesion.setRowSorter(new TableRowSorter(modeloprofesion));
            int cantFilas = tablaprofesion.getRowCount();
        }
    }

    private class GrillaCargo extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocargo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocargo.removeRow(0);
            }

            cargoDAO DAOcar = new cargoDAO();
            try {
                for (cargo car : DAOcar.Todos()) {
                    String Datos[] = {String.valueOf(car.getCodigo()), String.valueOf(car.getNombre())};
                    modelocargo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacargo.setRowSorter(new TableRowSorter(modelocargo));
            int cantFilas = tablacargo.getRowCount();
        }
    }

    private class GrillaDepartamento extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelodepartamento.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodepartamento.removeRow(0);
            }

            departamento_laboralDAO DAOdp = new departamento_laboralDAO();
            try {
                for (departamento_laboral dp : DAOdp.Todos()) {
                    String Datos[] = {String.valueOf(dp.getCodigo()), String.valueOf(dp.getNombre())};
                    modelodepartamento.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tabladepartamento.setRowSorter(new TableRowSorter(modelodepartamento));
            int cantFilas = tabladepartamento.getRowCount();
        }
    }

    private class GrillaSeccion extends Thread {

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

    private class GrillaGiraduria extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelogiraduria.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelogiraduria.removeRow(0);
            }

            giraduriaDAO DAOgira = new giraduriaDAO();
            try {
                for (giraduria gira : DAOgira.todos()) {
                    String Datos[] = {String.valueOf(gira.getCodigo()), String.valueOf(gira.getNombre())};
                    modelogiraduria.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablagiraduria.setRowSorter(new TableRowSorter(modelogiraduria));
            int cantFilas = tablagiraduria.getRowCount();
        }
    }

    private class GrillaLocalidad extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelolocalidad.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelolocalidad.removeRow(0);
            }

            localidadDAO DAOloc = new localidadDAO();
            try {
                for (localidad loc : DAOloc.todos()) {
                    String Datos[] = {String.valueOf(loc.getCodigo()), String.valueOf(loc.getNombre())};
                    modelolocalidad.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablalocalidad.setRowSorter(new TableRowSorter(modelolocalidad));
            int cantFilas = tablalocalidad.getRowCount();
        }
    }

    private class GrillaPostulante extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            postulanteDAO poDAO = new postulanteDAO();
            try {
                for (postulante po : poDAO.ListaAprobados()) {
                    String Datos[] = {String.valueOf(po.getCodigo()), po.getNombres().trim() + "" + po.getApellidos().trim()};
                    modelobanco.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablabanco.setRowSorter(new TableRowSorter(modelobanco));
            int cantFilas = tablabanco.getRowCount();
        }
    }

}

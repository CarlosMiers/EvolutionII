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
import DAO.cabecera_certificado_estudiosDAO;
import DAO.carreraDAO;
import DAO.certificado_estudioDAO;
import DAO.clienteDAO;
import DAO.configuracionDAO;
import DAO.ficha_empleadoDAO;
import DAO.materiasDAO;
import DAO.periodo_lectivoDAO;
import DAO.semestresDAO;
import DAO.sucursalDAO;
import DAO.tipo_examenDAO;
import Modelo.Tablas;
import Modelo.cabecera_certificado_estudio;
import Modelo.carrera;
import Modelo.certificado_estudio;
import Modelo.cliente;
import Modelo.configuracion;
import Modelo.ficha_empleado;
import Modelo.materias;
import Modelo.periodo_lectivo;
import Modelo.semestres;
import Modelo.sucursal;
import Modelo.tipo_examen;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class carga_calificaciones extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modeloalumno = new Tablas();
    Tablas modelocarrera = new Tablas();
    Tablas modeloexamen = new Tablas();
    Tablas modelomateria = new Tablas();
    Tablas modelodocente = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelonota = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltromateria, trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltroalumno, trsfiltrodocente, trsfiltrocarrera, trsfiltroexamen;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("###");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String cTasa, referencia = null;
    int counter = 0;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon iconorefrescar = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");

    public carga_calificaciones() {
        initComponents();
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.Salir.setIcon(iconosalir);
        this.Grabar.setIcon(iconograbar);
        this.refrescar.setIcon(iconorefrescar);
        this.SalirCompleto.setIcon(iconosalir);
        this.buscarCarrera.setIcon(iconobuscar);
        this.buscarprofesor.setIcon(iconobuscar);
        this.buscarTipoExamen.setIcon(iconobuscar);
        this.buscaralumno.setIcon(iconobuscar);
        this.buscarMateria.setIcon(iconobuscar);
        this.buscarSede.setIcon(iconobuscar);
        this.refrescar.setIcon(icorefresh);
        this.item.setVisible(false);
        this.idreferencia.setVisible(false);

        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        fecha.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    codcarrera.requestFocus();
                }
            }
        });

        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setVisible(false);
        this.idControl.setText("0");
        this.Inicializar();
        this.cargarTitulo();
        this.TitSuc();
        this.TitClie();
        this.TitCarrera();
        this.TitExamen();
        this.TitMateria();
        this.TituloNota();
        this.TitDocente();
        GrillaCertificado GrillaCer = new GrillaCertificado();
        Thread HiloGrillaCer = new Thread(GrillaCer);
        HiloGrillaCer.start();

        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();

        GrillaCarrera grillaca = new GrillaCarrera();
        Thread hilocar = new Thread(grillaca);
        hilocar.start();

        GrillaExamen grillaex = new GrillaExamen();
        Thread hiloexa = new Thread(grillaex);
        hiloexa.start();

        GrillaDocente grilladoc = new GrillaDocente();
        Thread hilodoc = new Thread(grilladoc);
        hilodoc.start();

        GrillaCliente grillacli= new GrillaCliente();
        Thread hilocli = new Thread(grillacli);
        hilocli.start();

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

        crearacta = new javax.swing.JDialog();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        codcarrera = new javax.swing.JTextField();
        buscarCarrera = new javax.swing.JButton();
        nombrecarrera = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tipoexamen = new javax.swing.JTextField();
        buscarTipoExamen = new javax.swing.JButton();
        nombreexamen = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarSede = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        idcertificado = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        materia = new javax.swing.JTextField();
        buscarMateria = new javax.swing.JButton();
        nombremateria = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        periodo = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        turno = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        acta = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        codprofesor = new javax.swing.JFormattedTextField();
        buscarprofesor = new javax.swing.JButton();
        nombreprofesor = new javax.swing.JTextField();
        semestre = new javax.swing.JComboBox<>();
        BSede = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BDocente = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combodocente = new javax.swing.JComboBox();
        jTBuscarDocente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabladocente = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarDocente = new javax.swing.JButton();
        SalirDocente = new javax.swing.JButton();
        BMateria = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combomateria = new javax.swing.JComboBox();
        jTBuscarMateria = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablamateria = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarMateria = new javax.swing.JButton();
        SalirMateria = new javax.swing.JButton();
        BCarrera = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combocarrera = new javax.swing.JComboBox();
        jTBuscarCarrera = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablacarrera = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarCarrera = new javax.swing.JButton();
        SalirCarrera = new javax.swing.JButton();
        BTipoExamen = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        comboexamen = new javax.swing.JComboBox();
        jTBuscarExamen = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaexamen = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        AceptarExamen = new javax.swing.JButton();
        SalirExamen = new javax.swing.JButton();
        calificaciones = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablanotas = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        item = new javax.swing.JTextField();
        nombrealumno = new javax.swing.JTextField();
        nota = new javax.swing.JFormattedTextField();
        codalumno = new javax.swing.JFormattedTextField();
        BotonGrabarNota = new javax.swing.JButton();
        BotonBorrarNota = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        buscaralumno = new javax.swing.JButton();
        estado = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        idreferencia = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        BotonSalirNota = new javax.swing.JButton();
        BotonLimpiar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        nombrecarreranota = new javax.swing.JLabel();
        nombrematerianota = new javax.swing.JLabel();
        nombredocentenota = new javax.swing.JLabel();
        actanota = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        BCliente = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarAlumno = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
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
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();

        crearacta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                crearactaFocusGained(evt);
            }
        });
        crearacta.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                crearactaWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        crearacta.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                crearactaWindowActivated(evt);
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

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("N° Referencia");

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

        jLabel2.setText("Fecha Examen");

        jLabel4.setText("Carrera");

        codcarrera.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codcarrera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codcarreraFocusGained(evt);
            }
        });
        codcarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codcarreraActionPerformed(evt);
            }
        });
        codcarrera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codcarreraKeyPressed(evt);
            }
        });

        buscarCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCarreraActionPerformed(evt);
            }
        });

        nombrecarrera.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecarrera.setEnabled(false);

        jLabel5.setText("Semestre");

        jLabel6.setText("Tipo Exámen");

        tipoexamen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tipoexamen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tipoexamenFocusGained(evt);
            }
        });
        tipoexamen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoexamenActionPerformed(evt);
            }
        });
        tipoexamen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipoexamenKeyPressed(evt);
            }
        });

        buscarTipoExamen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarTipoExamen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarTipoExamenActionPerformed(evt);
            }
        });

        nombreexamen.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreexamen.setEnabled(false);

        jLabel12.setText("Sede");

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

        buscarSede.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarSede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSedeActionPerformed(evt);
            }
        });

        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        idcertificado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        idcertificado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idcertificado.setEnabled(false);

        jLabel9.setText("Materia");

        materia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        materia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                materiaFocusGained(evt);
            }
        });
        materia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materiaActionPerformed(evt);
            }
        });
        materia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                materiaKeyPressed(evt);
            }
        });

        buscarMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarMateriaActionPerformed(evt);
            }
        });

        nombremateria.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombremateria.setEnabled(false);

        jLabel14.setText("Período Lectivo");

        periodo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        periodo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel7.setText("N° Acta");

        turno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "T", "N" }));

        jLabel10.setText("Turno");

        acta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        acta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel11.setText("Docente");

        codprofesor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        codprofesor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codprofesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codprofesorActionPerformed(evt);
            }
        });

        buscarprofesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarprofesorActionPerformed(evt);
            }
        });

        nombreprofesor.setEnabled(false);

        semestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        semestre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(idcertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(materia, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tipoexamen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(periodo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                                                .addComponent(codcarrera, javax.swing.GroupLayout.Alignment.LEADING)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(codprofesor)
                                        .addGap(62, 62, 62)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buscarTipoExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarSede, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(buscarprofesor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buscarMateria, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(nombreprofesor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nombremateria, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nombreexamen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                                    .addComponent(nombrecarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(semestre, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(turno, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(acta, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(435, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarSede, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idcertificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(periodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(codcarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrecarrera, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(semestre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tipoexamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(nombreexamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarTipoExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(materia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombremateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(codprofesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(acta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarprofesor, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreprofesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(turno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout crearactaLayout = new javax.swing.GroupLayout(crearacta.getContentPane());
        crearacta.getContentPane().setLayout(crearactaLayout);
        crearactaLayout.setHorizontalGroup(
            crearactaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crearactaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crearactaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        crearactaLayout.setVerticalGroup(
            crearactaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crearactaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(crearactaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Salir)
                    .addComponent(Grabar))
                .addContainerGap())
        );

        BSede.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSede.setTitle("null");

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
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.SalirCliente.text")); // NOI18N
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

        javax.swing.GroupLayout BSedeLayout = new javax.swing.GroupLayout(BSede.getContentPane());
        BSede.getContentPane().setLayout(BSedeLayout);
        BSedeLayout.setHorizontalGroup(
            BSedeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSedeLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSedeLayout.setVerticalGroup(
            BSedeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSedeLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BDocente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BDocente.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combodocente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combodocente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combodocente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combodocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combodocenteActionPerformed(evt);
            }
        });

        jTBuscarDocente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarDocente.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarDocenteActionPerformed(evt);
            }
        });
        jTBuscarDocente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarDocenteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combodocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combodocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tabladocente.setModel(modelodocente       );
        tabladocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabladocenteMouseClicked(evt);
            }
        });
        tabladocente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabladocenteKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tabladocente);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarDocente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarDocente.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarDocente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarDocenteActionPerformed(evt);
            }
        });

        SalirDocente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirDocente.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirDocente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirDocenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarDocente)
                    .addComponent(SalirDocente))
                .addContainerGap())
        );

        javax.swing.GroupLayout BDocenteLayout = new javax.swing.GroupLayout(BDocente.getContentPane());
        BDocente.getContentPane().setLayout(BDocenteLayout);
        BDocenteLayout.setHorizontalGroup(
            BDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BDocenteLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BDocenteLayout.setVerticalGroup(
            BDocenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BDocenteLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMateria.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMateria.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomateria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomateria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomateria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomateriaActionPerformed(evt);
            }
        });

        jTBuscarMateria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMateria.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarMateriaActionPerformed(evt);
            }
        });
        jTBuscarMateria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarMateriaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combomateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablamateria.setModel(modelomateria        );
        tablamateria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablamateriaMouseClicked(evt);
            }
        });
        tablamateria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablamateriaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablamateria);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMateria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMateria.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMateriaActionPerformed(evt);
            }
        });

        SalirMateria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMateria.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMateria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMateriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMateria)
                    .addComponent(SalirMateria))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMateriaLayout = new javax.swing.GroupLayout(BMateria.getContentPane());
        BMateria.getContentPane().setLayout(BMateriaLayout);
        BMateriaLayout.setHorizontalGroup(
            BMateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMateriaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMateriaLayout.setVerticalGroup(
            BMateriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMateriaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCarrera.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCarrera.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocarrera.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocarrera.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocarreraActionPerformed(evt);
            }
        });

        jTBuscarCarrera.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCarrera.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCarreraActionPerformed(evt);
            }
        });
        jTBuscarCarrera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCarreraKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combocarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacarrera.setModel(modelocarrera);
        tablacarrera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacarreraMouseClicked(evt);
            }
        });
        tablacarrera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacarreraKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablacarrera);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCarrera.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCarrera.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCarreraActionPerformed(evt);
            }
        });

        SalirCarrera.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCarrera.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCarrera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCarreraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCarrera)
                    .addComponent(SalirCarrera))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCarreraLayout = new javax.swing.GroupLayout(BCarrera.getContentPane());
        BCarrera.getContentPane().setLayout(BCarreraLayout);
        BCarreraLayout.setHorizontalGroup(
            BCarreraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCarreraLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCarreraLayout.setVerticalGroup(
            BCarreraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCarreraLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BTipoExamen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BTipoExamen.setTitle("null");

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboexamen.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboexamen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboexamen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboexamen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboexamenActionPerformed(evt);
            }
        });

        jTBuscarExamen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarExamen.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarExamen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarExamenActionPerformed(evt);
            }
        });
        jTBuscarExamen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarExamenKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(comboexamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboexamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarExamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaexamen.setModel(modeloexamen);
        tablaexamen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaexamenMouseClicked(evt);
            }
        });
        tablaexamen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaexamenKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(tablaexamen);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarExamen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarExamen.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarExamen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarExamen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarExamenActionPerformed(evt);
            }
        });

        SalirExamen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirExamen.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirExamen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirExamen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirExamenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarExamen)
                    .addComponent(SalirExamen))
                .addContainerGap())
        );

        javax.swing.GroupLayout BTipoExamenLayout = new javax.swing.GroupLayout(BTipoExamen.getContentPane());
        BTipoExamen.getContentPane().setLayout(BTipoExamenLayout);
        BTipoExamenLayout.setHorizontalGroup(
            BTipoExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BTipoExamenLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BTipoExamenLayout.setVerticalGroup(
            BTipoExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BTipoExamenLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablanotas.setModel(modelonota);
        tablanotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablanotasMousePressed(evt);
            }
        });
        tablanotas.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablanotasPropertyChange(evt);
            }
        });
        tablanotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablanotasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablanotas);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        item.setEditable(false);
        item.setEnabled(false);

        nombrealumno.setEditable(false);
        nombrealumno.setEnabled(false);
        nombrealumno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombrealumnoFocusLost(evt);
            }
        });

        nota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        nota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        codalumno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        codalumno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codalumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codalumnoActionPerformed(evt);
            }
        });

        BotonGrabarNota.setText("Grabar");
        BotonGrabarNota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonGrabarNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGrabarNotaActionPerformed(evt);
            }
        });

        BotonBorrarNota.setText("Borrar");
        BotonBorrarNota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonBorrarNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBorrarNotaActionPerformed(evt);
            }
        });

        jLabel13.setText("Matrícula");

        jLabel15.setText("Nombre del Alumno");

        jLabel16.setText("Nota");

        buscaralumno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscaralumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscaralumnoActionPerformed(evt);
            }
        });

        estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "P", "A" }));

        jLabel18.setText("Estado");

        idreferencia.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(codalumno, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscaralumno, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(nombrealumno, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(43, 43, 43)
                        .addComponent(item, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(idreferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(nota, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(29, 29, 29)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonGrabarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonBorrarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotonGrabarNota)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel15)
                                    .addComponent(item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18)
                                    .addComponent(idreferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(codalumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buscaralumno, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nombrealumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonBorrarNota)))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonSalirNota.setText("Volver a Principal");
        BotonSalirNota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSalirNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirNotaActionPerformed(evt);
            }
        });

        BotonLimpiar.setText("Limpiar");
        BotonLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(BotonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BotonSalirNota, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonSalirNota)
                    .addComponent(BotonLimpiar))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        nombrecarreranota.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombrecarreranota.setText("jLabel4");

        nombrematerianota.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombrematerianota.setText("jLabel4");

        nombredocentenota.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombredocentenota.setText("jLabel4");

        actanota.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        actanota.setText("jLabel4");

        jLabel17.setText("Carrera");

        jLabel19.setText("Materia");

        jLabel20.setText("Docente");

        jLabel21.setText("Acta");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(nombrematerianota, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(nombrecarreranota, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(actanota, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombredocentenota, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecarreranota)
                    .addComponent(nombredocentenota)
                    .addComponent(jLabel17)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrematerianota)
                    .addComponent(actanota)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout calificacionesLayout = new javax.swing.GroupLayout(calificaciones.getContentPane());
        calificaciones.getContentPane().setLayout(calificacionesLayout);
        calificacionesLayout.setHorizontalGroup(
            calificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        calificacionesLayout.setVerticalGroup(
            calificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calificacionesLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarAlumno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarAlumno.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarAlumnoActionPerformed(evt);
            }
        });
        jTBuscarAlumno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarAlumnoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacliente.setModel(modeloalumno        );
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
        jScrollPane8.setViewportView(tablacliente);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        javax.swing.GroupLayout BClienteLayout = new javax.swing.GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Carga Calificaciones");
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
        etiquetacredito.setText("Acta Calificaciones");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Nro de Orden" }));
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
                .addContainerGap(351, Short.MAX_VALUE))
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
        Modificar.setText("Actualizar Notas");
        Modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        Agregar.setBackground(new java.awt.Color(255, 255, 255));
        Agregar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Agregar.setText("Crear Acta");
        Agregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Eliminar.setBackground(new java.awt.Color(255, 255, 255));
        Eliminar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Eliminar.setText("Anular Acta");
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(carga_calificaciones.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

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
                .addContainerGap(85, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
    }

    private void TitClie() {
        modeloalumno.addColumn("Código");
        modeloalumno.addColumn("Nombre");

        int[] anchos = {90, 150};
        for (int i = 0; i < modeloalumno.getColumnCount(); i++) {
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
                        indiceColumnaTabla = 3;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
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

    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        idControl.setText("0");
        this.limpiar();
        crearacta.setModal(true);
        crearacta.setSize(690, 480);
        //Establecemos un título para el jDialog
        crearacta.setTitle("Agregar Nueva Acta de Calificaciones");
        crearacta.setLocationRelativeTo(null);
        crearacta.setVisible(true);
        sucursal.requestFocus();
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.sucursal.setText("0");
        this.idcertificado.setText("0");
        this.nombresucursal.setText("");
        this.fecha.setCalendar(c2);
        this.codcarrera.setText("0");
        this.nombrecarrera.setText("");
        this.semestre.setSelectedIndex(0);
        this.periodo.setText("0");
        this.tipoexamen.setText("");
        this.nombreexamen.setText("");
        this.codprofesor.setText("0");
        this.nombreprofesor.setText("");
        this.materia.setText("0");
        this.nombremateria.setText("");
        this.acta.setText("0");
        this.turno.setSelectedIndex(0);
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
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            this.limpiar();
            this.LimpiarNota();
            int nFila = this.jTable1.getSelectedRow();
            this.idreferencia.setText(this.jTable1.getValueAt(nFila, 0).toString());

            cabecera_certificado_estudiosDAO crDAO = new cabecera_certificado_estudiosDAO();
            cabecera_certificado_estudio cer = new cabecera_certificado_estudio();

            try {
                cer = crDAO.BuscarId(Double.valueOf(this.idreferencia.getText()));
                if (cer != null) {
                    nombrecarreranota.setText(cer.getCodcarrera().getNombre());
                    nombrematerianota.setText(cer.getMateria().getNombre());
                    nombredocentenota.setText(cer.getCodprofesor().getNombres());
                    actanota.setText(formatosinpunto.format(cer.getActa()));

                    this.CargarNota(Double.valueOf(idreferencia.getText()));
                    calificaciones.setModal(true);
                    calificaciones.setSize(586, 450);
                    //Establecemos un título para el jDialog
                    calificaciones.setTitle("Actualizar Acta de Calificaciones N° " + cer.getIdcertificado());
                    calificaciones.setLocationRelativeTo(null);
                    calificaciones.setVisible(true);
                    LimpiarNota();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
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
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Anular esta Orden ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                cabecera_certificado_estudiosDAO crDAO = new cabecera_certificado_estudiosDAO();
                cabecera_certificado_estudio cer = new cabecera_certificado_estudio();
                try {
                    cer = crDAO.BuscarId(Double.valueOf(num));
                    if (cer == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        crDAO.EliminarActa(Double.valueOf(num));
                        //pl.borrarDetalleOrdenCompra(p.getIdordencompra());
                        JOptionPane.showMessageDialog(null, "Orden Anulada Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
        }
        this.refrescar.doClick();
    }//GEN-LAST:event_EliminarActionPerformed

    private void jTable1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        String cEstado = this.jTable1.getValueAt(nFila, 7).toString();
        if (cEstado.equals("ANULADO")) {
            JOptionPane.showMessageDialog(null, "La Orden de Compra ya fue Anulada ");
            return;
        }
//      EmitirOrden EmitirOrden = new EmitirOrden();
//      Thread HiloOrden = new Thread(EmitirOrden);
//      HiloOrden.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        crearacta.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed
        //Se inicia Proceso de Grabado de Registro
        if (this.materia.getText().isEmpty() || this.materia.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Materia");
            this.materia.requestFocus();
            return;
        }

        if (this.tipoexamen.getText().isEmpty() || this.tipoexamen.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Tipo de Examen");
            this.tipoexamen.requestFocus();
            return;
        }

        if (this.codcarrera.getText().isEmpty() || this.codcarrera.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cerrera");
            this.codcarrera.requestFocus();
            return;
        }

        if (this.codprofesor.getText().isEmpty() || this.codprofesor.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Docente");
            this.codprofesor.requestFocus();
            return;
        }

        if (this.periodo.getText().isEmpty() || this.periodo.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Período");
            this.periodo.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            cabecera_certificado_estudiosDAO crDAO = new cabecera_certificado_estudiosDAO();
            cabecera_certificado_estudio cer = new cabecera_certificado_estudio();
            try {
                cer = crDAO.GenerarActa();
                acta.setText(String.valueOf(cer.getActa()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal sc = null;

            materiasDAO matDAO = new materiasDAO();
            materias ma = null;

            carreraDAO caDAO = new carreraDAO();
            carrera ca = null;

            ficha_empleadoDAO fiDAO = new ficha_empleadoDAO();
            ficha_empleado fi = null;

            periodo_lectivoDAO peDAO = new periodo_lectivoDAO();
            periodo_lectivo pe = null;

            semestresDAO seDAO = new semestresDAO();
            semestres se = null;

            tipo_examenDAO exDAO = new tipo_examenDAO();
            tipo_examen exa = null;

            try {
                sc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                ca = caDAO.buscarId(Integer.valueOf(this.codcarrera.getText()));
                fi = fiDAO.buscarIdActivo(Integer.valueOf(this.codprofesor.getText()));
                pe = peDAO.buscarId(Integer.valueOf(this.periodo.getText()));
                se = seDAO.buscarId(Integer.valueOf(this.semestre.getSelectedIndex() + 1));
                exa = exDAO.buscarId(this.tipoexamen.getText());
                ma = matDAO.BuscarxCarreraxSemestre(Integer.valueOf(this.materia.getText()), Integer.valueOf(this.codcarrera.getText()), Integer.valueOf(this.semestre.getSelectedIndex() + 1));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            //CAPTURAMOS LOS DATOS DE LA CABECERA
            //sede,periodo,semestre,codturno,codcarrera,
            //codprofesor,tipoexamen,fechaexamen,acta,materia
            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            cer.setIdcertificado(Double.valueOf(this.idcertificado.getText()));
            cer.setSede(sc);
            cer.setPeriodo(pe);
            cer.setSemestre(se);
            cer.setCodturno(this.turno.getSelectedItem().toString());
            cer.setCodcarrera(ca);
            cer.setCodprofesor(fi);
            cer.setTipoexamen(exa);
            cer.setFechaexamen(FechaProceso);
            cer.setActa(Integer.valueOf(acta.getText()));
            cer.setMateria(ma);
            if (Integer.valueOf(this.idcertificado.getText()) == 0) {
                try {
                    crDAO.InsertarActa(cer);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            } else {
                try {
                    crDAO.ActualizarActa(cer);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            }
            this.refrescar.doClick();
            crearacta.setVisible(false);
            this.crearacta.setModal(false);
            this.crearacta.setVisible(false);
        }
    }//GEN-LAST:event_GrabarActionPerformed

    private void crearactaFocusGained(FocusEvent evt) {//GEN-FIRST:event_crearactaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_crearactaFocusGained

    private void crearactaWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_crearactaWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_crearactaWindowGainedFocus

    private void crearactaWindowActivated(WindowEvent evt) {//GEN-FIRST:event_crearactaWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_crearactaWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void refrescarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaCertificado GrillaCer = new GrillaCertificado();
        Thread HiloGrillaCer = new Thread(GrillaCer);
        HiloGrillaCer.start();
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
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.codcarrera.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void codcarreraKeyPressed(KeyEvent evt) {//GEN-FIRST:event_codcarreraKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tipoexamen.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_codcarreraKeyPressed

    private void tipoexamenKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tipoexamenKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.materia.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.semestre.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_tipoexamenKeyPressed

    private void buscarSedeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarSedeActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (sucu.getCodigo() == 0) {
                BSede.setModal(true);
                BSede.setSize(482, 575);
                BSede.setLocationRelativeTo(null);
                BSede.setTitle("Buscar Sucursal");
                BSede.setVisible(true);
                BSede.setModal(false);
            } else {
                nombresucursal.setText(sucu.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSedeActionPerformed

    private void sucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSede.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void combodocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combodocenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combodocenteActionPerformed

    private void jTBuscarDocenteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarDocenteKeyPressed
        this.jTBuscarDocente.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarDocente.getText()).toUpperCase();
                jTBuscarDocente.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combodocente.getSelectedIndex()) {
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
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltrocli = new TableRowSorter(tabladocente.getModel());
        tabladocente.setRowSorter(trsfiltrocli);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarDocenteKeyPressed

    private void tabladocenteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabladocenteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarDocente.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladocenteKeyPressed

    private void AceptarDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarDocenteActionPerformed
        int nFila = this.tabladocente.getSelectedRow();
        this.codprofesor.setText(this.tabladocente.getValueAt(nFila, 0).toString());
        this.nombreprofesor.setText(this.tabladocente.getValueAt(nFila, 1).toString());
        this.BDocente.setVisible(false);
        this.jTBuscarDocente.setText("");
        this.acta.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarDocenteActionPerformed

    private void SalirDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirDocenteActionPerformed
        this.BDocente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirDocenteActionPerformed

    private void buscarCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCarreraActionPerformed

        carreraDAO girDAO = new carreraDAO();
        carrera gi = null;
        try {
            gi = girDAO.buscarIdSimple(Integer.valueOf(this.codcarrera.getText()));
            if (gi.getCodigo() == 0) {
                BCarrera.setModal(true);
                BCarrera.setSize(482, 575);
                BCarrera.setLocationRelativeTo(null);
                BCarrera.setVisible(true);
                BCarrera.setTitle("Buscar Carrera");
                BCarrera.setModal(false);
            } else {
                nombrecarrera.setText(gi.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarCarreraActionPerformed

    private void codcarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codcarreraActionPerformed
        this.buscarCarrera.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codcarreraActionPerformed

    private void jTBuscarDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarDocenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarDocenteActionPerformed

    private void tabladocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladocenteMouseClicked
        this.AceptarDocente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladocenteMouseClicked

    private void combomateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combomateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomateriaActionPerformed

    private void jTBuscarMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMateriaActionPerformed

    private void jTBuscarMateriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarMateriaKeyPressed
        this.jTBuscarMateria.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarMateria.getText()).toUpperCase();
                jTBuscarMateria.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combomateria.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtromateria(indiceColumnaTabla);
            }
        });
        trsfiltromateria = new TableRowSorter(tablamateria.getModel());
        tablamateria.setRowSorter(trsfiltromateria);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMateriaKeyPressed

    private void tablamateriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamateriaMouseClicked
        this.AceptarMateria.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamateriaMouseClicked

    private void tablamateriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamateriaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarMateria.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamateriaKeyPressed

    private void AceptarMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarMateriaActionPerformed
        int nFila = this.tablamateria.getSelectedRow();
        this.materia.setText(this.tablamateria.getValueAt(nFila, 0).toString());
        this.nombremateria.setText(this.tablamateria.getValueAt(nFila, 1).toString());

        this.BMateria.setVisible(false);
        this.jTBuscarMateria.setText("");
        this.codprofesor.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMateriaActionPerformed

    private void SalirMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMateriaActionPerformed
        this.BMateria.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMateriaActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSede.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BSede.setVisible(false);
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

    private void tipoexamenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoexamenActionPerformed
        this.buscarTipoExamen.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoexamenActionPerformed

    private void buscarTipoExamenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarTipoExamenActionPerformed
        tipo_examenDAO exaDAO = new tipo_examenDAO();
        tipo_examen exa = null;
        try {
            exa = exaDAO.buscarId(this.tipoexamen.getText());
            if (exa.getCodigo() == "") {
                BTipoExamen.setModal(true);
                BTipoExamen.setSize(482, 575);
                BTipoExamen.setLocationRelativeTo(null);
                BTipoExamen.setVisible(true);
                BTipoExamen.setModal(false);
            } else {
                nombreexamen.setText(exa.getNombre());
                //Establecemos un título para el jDialog
                materia.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarTipoExamenActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void codcarreraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codcarreraFocusGained
        codcarrera.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_codcarreraFocusGained

    private void tipoexamenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tipoexamenFocusGained
        tipoexamen.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoexamenFocusGained

    private void materiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_materiaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_materiaFocusGained

    private void materiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materiaActionPerformed
        this.buscarMateria.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_materiaActionPerformed

    private void materiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_materiaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
//          this.importe.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipoexamen.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_materiaKeyPressed

    private void buscarMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarMateriaActionPerformed
        materiasDAO matDAO = new materiasDAO();
        materias mat = null;
        try {
            mat = matDAO.BuscarxCarreraxSemestre(Integer.valueOf(this.materia.getText()), Integer.valueOf(this.codcarrera.getText()), this.semestre.getSelectedIndex() + 1);
            if (mat.getIdmateria() == 0) {
                GrillaMateria grillamat = new GrillaMateria();
                Thread hilomat = new Thread(grillamat);
                hilomat.start();

                BMateria.setModal(true);
                BMateria.setSize(482, 575);
                BMateria.setLocationRelativeTo(null);
                BMateria.setVisible(true);
                BMateria.setModal(false);
            } else {
                nombremateria.setText(mat.getNombre());
                //Establecemos un título para el jDialog
                codprofesor.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarMateriaActionPerformed

    private void combocarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocarreraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocarreraActionPerformed

    private void jTBuscarCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCarreraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCarreraActionPerformed

    private void jTBuscarCarreraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCarreraKeyPressed
        this.jTBuscarCarrera.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCarrera.getText()).toUpperCase();
                jTBuscarCarrera.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocarrera.getSelectedIndex()) {
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
        trsfiltrocarrera = new TableRowSorter(tablacarrera.getModel());
        tablacarrera.setRowSorter(trsfiltrocarrera);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCarreraKeyPressed

    private void tablacarreraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacarreraMouseClicked
        this.AceptarCarrera.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacarreraMouseClicked

    private void tablacarreraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacarreraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCarrera.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablacarreraKeyPressed

    private void AceptarCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCarreraActionPerformed
        int nFila = this.tablacarrera.getSelectedRow();
        this.codcarrera.setText(this.tablacarrera.getValueAt(nFila, 0).toString());
        this.nombrecarrera.setText(this.tablacarrera.getValueAt(nFila, 1).toString());
        this.semestre.requestFocus();
        this.BCarrera.setVisible(false);
        this.jTBuscarCarrera.setText("");
        this.semestre.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCarreraActionPerformed

    private void SalirCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCarreraActionPerformed
        this.BCarrera.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCarreraActionPerformed

    private void buscarprofesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarprofesorActionPerformed
        ficha_empleadoDAO docDAO = new ficha_empleadoDAO();
        ficha_empleado doc = null;
        try {
            doc = docDAO.buscarIdActivo(Integer.valueOf(this.codprofesor.getText()));
            if (doc.getCodigo() == 0) {
                BDocente.setModal(true);
                BDocente.setSize(482, 575);
                BDocente.setLocationRelativeTo(null);
                BDocente.setVisible(true);
                semestre.requestFocus();
                BDocente.setModal(false);
            } else {
                nombreprofesor.setText(doc.getNombreempleado());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        acta.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarprofesorActionPerformed

    private void comboexamenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboexamenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboexamenActionPerformed

    private void jTBuscarExamenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarExamenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarExamenActionPerformed

    private void jTBuscarExamenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarExamenKeyPressed
        this.jTBuscarExamen.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarExamen.getText()).toUpperCase();
                jTBuscarExamen.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboexamen.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtroexamen(indiceColumnaTabla);
            }
        });
        trsfiltroexamen = new TableRowSorter(tablaexamen.getModel());
        tablaexamen.setRowSorter(trsfiltroexamen);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarExamenKeyPressed

    private void tablaexamenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaexamenMouseClicked
        this.AceptarExamen.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaexamenMouseClicked

    public void filtroexamen(int nNumeroColumna) {
        trsfiltroexamen.setRowFilter(RowFilter.regexFilter(this.jTBuscarExamen.getText(), nNumeroColumna));
    }

    private void tablaexamenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaexamenKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarExamen.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaexamenKeyPressed

    private void AceptarExamenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarExamenActionPerformed
        int nFila = this.tablaexamen.getSelectedRow();
        this.tipoexamen.setText(this.tablaexamen.getValueAt(nFila, 0).toString());
        this.nombreexamen.setText(this.tablaexamen.getValueAt(nFila, 1).toString());
        this.BTipoExamen.setVisible(false);
        this.BTipoExamen.setModal(false);
        this.materia.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarExamenActionPerformed

    private void SalirExamenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirExamenActionPerformed
        this.BTipoExamen.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirExamenActionPerformed

    private void codprofesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codprofesorActionPerformed
        this.buscarprofesor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codprofesorActionPerformed

    private void tablanotasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablanotasMousePressed
        int nFila = this.tablanotas.getSelectedRow();
        this.item.setText(this.tablanotas.getValueAt(nFila, 0).toString());
        this.codalumno.setText(this.tablanotas.getValueAt(nFila, 1).toString());
        this.nombrealumno.setText(this.tablanotas.getValueAt(nFila, 2).toString());
        this.nota.setText(this.tablanotas.getValueAt(nFila, 3).toString());
        this.estado.setSelectedItem(this.tablanotas.getValueAt(nFila, 4).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotasMousePressed

    private void tablanotasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablanotasPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotasPropertyChange

    private void tablanotasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablanotasKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            int nFila = this.tablanotas.getSelectedRow();
            this.item.setText(this.tablanotas.getValueAt(nFila, 0).toString());
            this.codalumno.setText(this.tablanotas.getValueAt(nFila, 1).toString());
            this.nombrealumno.setText(this.tablanotas.getValueAt(nFila, 2).toString());
            this.nota.setText(this.tablanotas.getValueAt(nFila, 3).toString());
            this.estado.setSelectedItem(this.tablanotas.getValueAt(nFila, 4).toString());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablanotasKeyReleased

    private void nombrealumnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombrealumnoFocusLost
        String letras = ConvertirMayusculas.cadena(nombremateria);
        nombremateria.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrealumnoFocusLost

    private void BotonGrabarNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGrabarNotaActionPerformed
        if (this.codalumno.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Matrícula del Alumno");
            this.codalumno.requestFocus();
            return;
        }

        certificado_estudioDAO grabar = new certificado_estudioDAO();
        certificado_estudio mat = new certificado_estudio();
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;

        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codalumno.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        //Clase de Cliente porque tiene que hacer referencia al cliente
        mat.setCodalumno(cl);

        String cItem = item.getText();
        cItem = cItem.replace(",", ".");

        String cIdnota = idreferencia.getText();
        cIdnota = cIdnota.replace(",", ".");

        mat.setItem(Integer.valueOf(cItem));
        mat.setIdnota(Double.valueOf(cIdnota));
        mat.setNota(Integer.valueOf(nota.getText()));
        mat.setEstado(estado.getSelectedItem().toString());
        try {
            if (mat.getItem() == 0) {
                grabar.InsertarNota(mat);
            } else {
                grabar.actualizarNota(mat);
            }
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.CargarNota(Double.valueOf(this.idreferencia.getText()));
        LimpiarNota();
        this.codalumno.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonGrabarNotaActionPerformed

    private void CargarNota(Double nnumero) {
        int cantidadRegistro = modelonota.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelonota.removeRow(0);
        }
        certificado_estudioDAO cerDAO = new certificado_estudioDAO();
        try {
            for (certificado_estudio detvta : cerDAO.MostrarxActa(nnumero)) {
                String Detalle[] = {formatosinpunto.format(detvta.getItem()), formatosinpunto.format(detvta.getCodalumno().getCodigo()), detvta.getCodalumno().getNombre(), formatosinpunto.format(detvta.getNota()), detvta.getEstado()};
                modelonota.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
        int cantFilas = tablanotas.getRowCount();
        if (cantFilas > 0) {
            BotonGrabarNota.setEnabled(true);
            BotonBorrarNota.setEnabled(true);
        } else {
            BotonGrabarNota.setEnabled(true);
            BotonBorrarNota.setEnabled(false);
        }

    }

    private void TituloNota() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelonota.addColumn("Item");
        modelonota.addColumn("Matrícula");
        modelonota.addColumn("Nombre del Alumno");
        modelonota.addColumn("Nota");
        modelonota.addColumn("Estado");
        int[] anchos = {1, 100, 160, 100, 100};
        for (int i = 0; i < modelonota.getColumnCount(); i++) {
            tablanotas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablanotas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablanotas.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tablanotas.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        //Se usa para poner invisible una determinada celda
        tablanotas.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tablamateria.setFont(font);

        this.tablanotas.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablanotas.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablanotas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablanotas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
    }


    private void BotonBorrarNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBorrarNotaActionPerformed
        int a = this.tablanotas.getSelectedRow();
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            certificado_estudioDAO cerDAO = new certificado_estudioDAO();
            try {
                cerDAO.eliminarNota(Integer.valueOf(item.getText()));
                JOptionPane.showMessageDialog(null, "Registro Eliminado Exitósamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.LimpiarNota();
            this.CargarNota(Double.valueOf(idreferencia.getText()));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonBorrarNotaActionPerformed

    private void BotonSalirNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirNotaActionPerformed
        this.calificaciones.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSalirNotaActionPerformed

    private void BotonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonLimpiarActionPerformed
        LimpiarNota();
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonLimpiarActionPerformed

    private void comboclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

    private void jTBuscarAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarAlumnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarAlumnoActionPerformed

    private void jTBuscarAlumnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarAlumnoKeyPressed
        this.jTBuscarAlumno.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarAlumno.getText()).toUpperCase();
                jTBuscarAlumno.setText(cadena);
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
                filtroalumno(indiceColumnaTabla);
            }
        });
        trsfiltroalumno = new TableRowSorter(tablacliente.getModel());
        tablacliente.setRowSorter(trsfiltroalumno);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarAlumnoKeyPressed
    
    public void filtroalumno(int nNumeroColumna) {
        trsfiltroalumno.setRowFilter(RowFilter.regexFilter(this.jTBuscarAlumno.getText(), nNumeroColumna));
    }

    
    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.AceptarCli.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void tablaclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCli.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteKeyPressed

    private void AceptarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCliActionPerformed
        int nFila = this.tablacliente.getSelectedRow();
        this.codalumno.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombrealumno.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.BCliente.setVisible(false);
        this.jTBuscarDocente.setText("");
        this.estado.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void buscaralumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscaralumnoActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codalumno.getText()));
            if (cl.getCodigo() == 0) {
                BCliente.setModal(true);
                BCliente.setSize(482, 575);
                BCliente.setLocationRelativeTo(null);
                BCliente.setTitle("Buscar Alumno");
                BCliente.setVisible(true);
                BCliente.setModal(false);
            } else {
                nombrealumno.setText(cl.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        estado.requestFocus();
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_buscaralumnoActionPerformed

    private void codalumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codalumnoActionPerformed
        buscaralumno.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codalumnoActionPerformed
    private void LimpiarNota() {
        item.setText("0");
        codalumno.setText("0");
        nombrealumno.setText("");
        estado.setSelectedIndex(0);
        nota.setText("0");
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }



    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarDocente.getText(), nNumeroColumna));
    }

    public void filtromateria(int nNumeroColumna) {
        trsfiltromateria.setRowFilter(RowFilter.regexFilter(jTBuscarMateria.getText(), nNumeroColumna));
    }

    public void filtrogira(int nNumeroColumna) {
        trsfiltrocarrera.setRowFilter(RowFilter.regexFilter(this.jTBuscarCarrera.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("N° ");
        modelo.addColumn("Fecha");
        modelo.addColumn("Carrera");
        modelo.addColumn("Materia");
        modelo.addColumn("Tipo");
        modelo.addColumn("Docente");
        modelo.addColumn("Período");
        modelo.addColumn("Semestre");
        modelo.addColumn("Turno");
        modelo.addColumn("Acta");

        int[] anchos = {100, 90, 150, 150, 120, 150, 100, 100, 90, 100};
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
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
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

    private void TitExamen() {
        modeloexamen.addColumn("Código");
        modeloexamen.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloexamen.getColumnCount(); i++) {
            tablaexamen.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaexamen.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaexamen.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaexamen.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaexamen.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitMateria() {
        modelomateria.addColumn("Código");
        modelomateria.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelomateria.getColumnCount(); i++) {
            tablamateria.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamateria.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamateria.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablamateria.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablamateria.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitDocente() {
        modelodocente.addColumn("Código");
        modelodocente.addColumn("Nombre");
        int[] anchos = {90, 150};
        for (int i = 0; i < modelodocente.getColumnCount(); i++) {
            tabladocente.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladocente.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladocente.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tabladocente.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tabladocente.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitCarrera() {
        modelocarrera.addColumn("Código");
        modelocarrera.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelocarrera.getColumnCount(); i++) {
            tablacarrera.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacarrera.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacarrera.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacarrera.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacarrera.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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
                new carga_calificaciones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCarrera;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarDocente;
    private javax.swing.JButton AceptarExamen;
    private javax.swing.JButton AceptarMateria;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BCarrera;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BDocente;
    private javax.swing.JDialog BMateria;
    private javax.swing.JDialog BSede;
    private javax.swing.JDialog BTipoExamen;
    private javax.swing.JButton BotonBorrarNota;
    private javax.swing.JButton BotonGrabarNota;
    private javax.swing.JButton BotonLimpiar;
    private javax.swing.JButton BotonSalirNota;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCarrera;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirDocente;
    private javax.swing.JButton SalirExamen;
    private javax.swing.JButton SalirMateria;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JFormattedTextField acta;
    private javax.swing.JLabel actanota;
    private javax.swing.JButton buscarCarrera;
    private javax.swing.JButton buscarMateria;
    private javax.swing.JButton buscarSede;
    private javax.swing.JButton buscarTipoExamen;
    private javax.swing.JButton buscaralumno;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JButton buscarprofesor;
    private javax.swing.JDialog calificaciones;
    private javax.swing.JFormattedTextField codalumno;
    private javax.swing.JTextField codcarrera;
    private javax.swing.JFormattedTextField codprofesor;
    private javax.swing.JComboBox combocarrera;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combodocente;
    private javax.swing.JComboBox comboexamen;
    private javax.swing.JComboBox combomateria;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JDialog crearacta;
    private com.toedter.calendar.JDateChooser dFinal;
    private com.toedter.calendar.JDateChooser dInicial;
    private javax.swing.JComboBox<String> estado;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField idcertificado;
    private javax.swing.JTextField idreferencia;
    private javax.swing.JTextField item;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField jTBuscarAlumno;
    private javax.swing.JTextField jTBuscarCarrera;
    private javax.swing.JTextField jTBuscarDocente;
    private javax.swing.JTextField jTBuscarExamen;
    private javax.swing.JTextField jTBuscarMateria;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField materia;
    private javax.swing.JTextField nombrealumno;
    private javax.swing.JTextField nombrecarrera;
    private javax.swing.JLabel nombrecarreranota;
    private javax.swing.JLabel nombredocentenota;
    private javax.swing.JTextField nombreexamen;
    private javax.swing.JTextField nombremateria;
    private javax.swing.JLabel nombrematerianota;
    private javax.swing.JTextField nombreprofesor;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JFormattedTextField nota;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JFormattedTextField periodo;
    private javax.swing.JButton refrescar;
    private javax.swing.JComboBox<String> semestre;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablacarrera;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tabladocente;
    private javax.swing.JTable tablaexamen;
    private javax.swing.JTable tablamateria;
    private javax.swing.JTable tablanotas;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTextField tipoexamen;
    private javax.swing.JComboBox<String> turno;
    // End of variables declaration//GEN-END:variables

    private class GenerarReporte extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nCliente", idControl.getText().trim());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/extracto_cuenta_clientes.jasper");
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

    private class GrillaCertificado extends Thread {

        public void run() {

            Date dFechaInicio = ODate.de_java_a_sql(dInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(dFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            cabecera_certificado_estudiosDAO DAO = new cabecera_certificado_estudiosDAO();
            try {
                for (cabecera_certificado_estudio orden : DAO.MostrarxFecha(dFechaInicio, dFechaFinal)) {
                    String Datos[] = {formatosinpunto.format(orden.getIdcertificado()), formatoFecha.format(orden.getFechaexamen()), orden.getCodcarrera().getNombre(), orden.getMateria().getNombre(), orden.getTipoexamen().getNombre(), orden.getCodprofesor().getNombres(), String.valueOf(orden.getPeriodo().getCodigo()), String.valueOf(orden.getSemestre().getSemestre()), orden.getCodturno(), String.valueOf(orden.getActa())};
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

    private class GrillaAlumno extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todosimple()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tabladocente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tabladocente.getRowCount();
        }
    }

    private class GrillaCarrera extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocarrera.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocarrera.removeRow(0);
            }
            carreraDAO DAOCAR = new carreraDAO();
            try {
                for (carrera gi : DAOCAR.lista()) {
                    String Datos[] = {String.valueOf(gi.getCodigo()), gi.getNombre()};
                    modelocarrera.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacarrera.setRowSorter(new TableRowSorter(modelocarrera));
            int cantFilas = tablacarrera.getRowCount();
        }
    }

    private class GrillaExamen extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloexamen.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloexamen.removeRow(0);
            }
            tipo_examenDAO DAOEXA = new tipo_examenDAO();
            try {
                for (tipo_examen ex : DAOEXA.todos()) {
                    String Datos[] = {ex.getCodigo(), ex.getNombre()};
                    modeloexamen.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaexamen.setRowSorter(new TableRowSorter(modeloexamen));
            int cantFilas = tablaexamen.getRowCount();
        }
    }

    private class GrillaMateria extends Thread {

        public void run() {
            int ncarrera = Integer.valueOf(codcarrera.getText());
            int nsemestre = semestre.getSelectedIndex() + 1;
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomateria.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomateria.removeRow(0);
            }
            materiasDAO MATDAO = new materiasDAO();
            try {
                for (materias mat : MATDAO.todosxCarreraxSemestre(ncarrera, nsemestre)) {
                    String Datos[] = {String.valueOf(mat.getIdmateria()), mat.getNombre()};
                    modelomateria.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamateria.setRowSorter(new TableRowSorter(modelomateria));
            int cantFilas = tablamateria.getRowCount();
        }
    }

    private class GrillaDocente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelodocente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodocente.removeRow(0);
            }

            ficha_empleadoDAO docDAO = new ficha_empleadoDAO();
            try {
                for (ficha_empleado doc : docDAO.TodosActivo()) {
                    String Datos[] = {String.valueOf(doc.getCodigo()), doc.getNombreempleado()};
                    modelodocente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tabladocente.setRowSorter(new TableRowSorter(modelodocente));
            int cantFilas = tabladocente.getRowCount();
        }
    }

    private class GrillaCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloalumno.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloalumno.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todosCarrerasimple()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getDireccion(), cli.getRuc(), String.valueOf(cli.getCarrera().getCodigo()), cli.getCarrera().getNombre()};
                    modeloalumno.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablacliente.setRowSorter(new TableRowSorter(modeloalumno));
            int cantFilas = tablacliente.getRowCount();
        }
    }

}

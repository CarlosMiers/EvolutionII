/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.CalcularRuc;
import Clases.Config;
import Clases.ControlGrabado;
import Clases.ConvertirMayusculas;
import Clases.CrearDigitoRuc;
import Conexion.BDConexion;
import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Conexion.ObtenerFecha;
import Conexion.ObtenerNumero;
import DAO.albumfotoDAO;
import DAO.clienteDAO;
import DAO.giraduriaDAO;
import DAO.referencia_comercialDAO;
import DAO.referencia_laboralDAO;
import Modelo.Tablas;
import Modelo.albumfoto;
import Modelo.cliente;
import Modelo.giraduria;
import Modelo.referencia_comercial;
import Modelo.referencia_laboral;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import org.openide.util.Exceptions;

/**
 *
 * @author MEC
 */
public class detalle_alumnos extends javax.swing.JFrame {

    //Inicializamos las variables de conexion a la base de Datos
    Conexion con = null;
    Statement stm = null;
    BDConexion BD = new BDConexion();
    String Operacion = null;
    Tablas comercial = new Tablas();
    Tablas laboral = new Tablas();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    ArrayList<albumfoto> imagenes;
    int contador = 0;

    public detalle_alumnos(String Opcion) throws ParseException {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
        //Verificamos el parametro y determinamos si quiero un registro nuevo
        //o va actualizar uno existente
        this.jTextControl.setVisible(false);
        this.limpiarCombos(); //Se limpian e inicializan los combos asociados a otra tabla
        this.jTextControl.setText(Opcion);
        this.codigo.setHorizontalAlignment(this.codigo.RIGHT);
        this.Salario.setHorizontalAlignment(this.Salario.RIGHT);
        this.SalarioConyugue.setHorizontalAlignment(this.SalarioConyugue.RIGHT);
        this.otrosIngresos.setHorizontalAlignment(this.otrosIngresos.RIGHT);
        this.LimiteCredito.setHorizontalAlignment(this.LimiteCredito.RIGHT);
        this.jTextnombre.selectAll();
        if (Opcion == "new") {
            this.codigo.setEnabled(true);
            this.codigo.setEditable(true);

            this.LimiteCredito.setText("0");
            this.Salario.setText("0");
            this.SalarioConyugue.setText("0");
            this.otrosIngresos.setText("0");

            this.codigo.setText("0");
            this.plazocredito.setText("0");
            this.codigo.requestFocus();
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            Calendar c2 = new GregorianCalendar();
            this.jDateNacimiento.setCalendar(c2);
            this.jDateFechaIngreso.setCalendar(c2);
        } else {
            this.codigo.setEnabled(false);
            this.codigo.setText(Opcion);
            this.consultarTabla(this.codigo.getText());
            this.jTextnombre.requestFocus();
            GrillaComercial GrillaC = new GrillaComercial();
            Thread Hilo1 = new Thread(GrillaC);
            Hilo1.start();

            GrillaLaboral GrillaL = new GrillaLaboral();
            Thread Hilo2 = new Thread(GrillaL);
            Hilo2.start();

        }
        //Asignamos el formato a los campos tipo date
        this.refrescarCarrusel(0);
        this.CargarReferenciaLaboral();
        this.CargarReferenciaComercial();
        this.jDateFechaIngreso.setDateFormatString("dd/MM/yyyy");
        this.jDateNacimiento.setDateFormatString("dd/MM/yyyy");
        // damos formato de seleccion completo a estos objetos
        this.Salario.selectAll();
        this.otrosIngresos.selectAll();
        this.SalarioConyugue.selectAll();

     /*   if (Config.cNivelUsuario.equals("1")) {
            this.observaciones.setEditable(true);
        } else {
            this.observaciones.setEditable(false);
        }*/
    }

    ObtenerFecha ODate = new ObtenerFecha();
    ObtenerNumero on = new ObtenerNumero();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Panel1Clientes = new javax.swing.JTabbedPane();
        DatosGenerales = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        jTextnombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboCategoria = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ruc = new javax.swing.JTextField();
        jTextControl = new javax.swing.JTextField();
        jComboEstadocivil = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        cedula = new javax.swing.JTextField();
        jTextConyugue = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jDateNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboLocalidades = new javax.swing.JComboBox();
        jLabel42 = new javax.swing.JLabel();
        barrio = new javax.swing.JComboBox();
        jTextmail = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextDireccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextCelular = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jComboEstado = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        sexo = new javax.swing.JComboBox();
        DatosLaborales = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextProfesion = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextLugarTrabajo = new javax.swing.JTextField();
        jTextCargoqueOcupa = new javax.swing.JTextField();
        jDateFechaIngreso = new com.toedter.calendar.JDateChooser();
        jTextDireccionLaboral = new javax.swing.JTextField();
        jTextTelefonoLaboral = new javax.swing.JTextField();
        jTextFaxLaboral = new javax.swing.JTextField();
        jTextMailLaboral = new javax.swing.JTextField();
        Salario = new org.jdesktop.swingx.JXFormattedTextField();
        SalarioConyugue = new org.jdesktop.swingx.JXFormattedTextField();
        otrosIngresos = new org.jdesktop.swingx.JXFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jComboAsesor = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        LimiteCredito = new org.jdesktop.swingx.JXFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jComboCasaPropia = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jComboAuto = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jComboInfor = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        plazocredito = new javax.swing.JTextField();
        Referencias = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablalaboral = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        referencialugartrabajo = new javax.swing.JTextField();
        referenciafonolaboral = new javax.swing.JTextField();
        etiqueta1 = new javax.swing.JLabel();
        etiqueta2 = new javax.swing.JLabel();
        GrabarRefLaboral = new javax.swing.JButton();
        DelRefLaboral = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablacomercial = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        referenciacomercio = new javax.swing.JTextField();
        referenciafonocomercial = new javax.swing.JTextField();
        GrabarRefComercial = new javax.swing.JButton();
        BorrarRefComercial = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        PanelImagenes = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        BotonAbriArchivo = new javax.swing.JButton();
        nombrearchivo = new javax.swing.JTextField();
        etiqueta = new javax.swing.JLabel();
        GuardarArchivo = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frame_detalle_clientes"); // NOI18N
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setForeground(new java.awt.Color(255, 255, 255));
        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel11.text")); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        DatosGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel7.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel1.text")); // NOI18N

        codigo.setEditable(false);
        codigo.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextCodigo.text")); // NOI18N
        codigo.setEnabled(false);
        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoFocusLost(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
        });

        jTextnombre.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextnombre.text")); // NOI18N
        jTextnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextnombreFocusLost(evt);
            }
        });
        jTextnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextnombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextnombreKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel2.text")); // NOI18N

        jComboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCategoriaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel25.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel25.text")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel3.text")); // NOI18N

        ruc.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextcedula.text")); // NOI18N
        ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rucKeyPressed(evt);
            }
        });

        jTextControl.setEditable(false);
        jTextControl.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextControl.text")); // NOI18N
        jTextControl.setEnabled(false);

        jComboEstadocivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Casado/a", "Soltero/a", "Separado/a", "Divorciado/a", "Viudo/a" }));
        jComboEstadocivil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboEstadocivilKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel4.text")); // NOI18N

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel44.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel44.text_1")); // NOI18N

        cedula.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.cedula.text")); // NOI18N
        cedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cedulaKeyPressed(evt);
            }
        });

        jTextConyugue.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextConyugue.text")); // NOI18N
        jTextConyugue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextConyugueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextConyugueKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel5.text")); // NOI18N

        jDateNacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateNacimientoKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel6.text")); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel24.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel24.text")); // NOI18N

        jComboLocalidades.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboLocalidades.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboLocalidadesKeyPressed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel42.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel42.text")); // NOI18N

        barrio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        barrio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                barrioKeyPressed(evt);
            }
        });

        jTextmail.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextmail.text")); // NOI18N
        jTextmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextmailKeyPressed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel45.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel45.text_1")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel8.text")); // NOI18N

        jTextDireccion.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextDireccion.text")); // NOI18N
        jTextDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDireccionKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel9.text")); // NOI18N

        jTextFono.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextFono.text")); // NOI18N
        jTextFono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFonoKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel10.text")); // NOI18N

        jTextCelular.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jTextCelular.text")); // NOI18N
        jTextCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCelularActionPerformed(evt);
            }
        });
        jTextCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextCelularKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel12.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jLabel12.text")); // NOI18N

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inactivo", "Activo" }));

        jLabel30.setForeground(new java.awt.Color(0, 51, 255));
        jLabel30.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel30.text")); // NOI18N

        jLabel32.setForeground(new java.awt.Color(0, 51, 255));
        jLabel32.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel32.text")); // NOI18N

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel35.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel35.text")); // NOI18N

        sexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMENINO" }));
        sexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sexoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10)))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFono, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jTextDireccion)
                                    .addComponent(jTextmail)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(58, 58, 58)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboLocalidades, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(barrio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextConyugue)))
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel25)
                                        .addComponent(jLabel44)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel35))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jLabel30))
                                                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jLabel32))
                                                .addComponent(jDateNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(11, 11, 11)
                                            .addComponent(jComboEstadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(190, 190, 190)
                        .addComponent(jTextControl, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(76, 76, 76)
                        .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jComboEstadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboLocalidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel42)
                                    .addComponent(barrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel45))
                            .addComponent(jTextmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout DatosGeneralesLayout = new javax.swing.GroupLayout(DatosGenerales);
        DatosGenerales.setLayout(DatosGeneralesLayout);
        DatosGeneralesLayout.setHorizontalGroup(
            DatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosGeneralesLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(DatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        DatosGeneralesLayout.setVerticalGroup(
            DatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jPanel1.TabConstraints.tabTitle"), DatosGenerales); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jPanel4.border.title"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel13.text")); // NOI18N

        jTextProfesion.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextProfesion.text")); // NOI18N
        jTextProfesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextProfesionActionPerformed(evt);
            }
        });
        jTextProfesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextProfesionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextProfesionKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel14.text")); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel15.text")); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel16.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel16.text")); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel17.text")); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel18.text")); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel19.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel19.text")); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel20.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel20.text")); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel21.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel21.text")); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel22.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel22.text")); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel23.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel23.text")); // NOI18N

        jTextLugarTrabajo.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextLugarTrabajo.text")); // NOI18N
        jTextLugarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextLugarTrabajoActionPerformed(evt);
            }
        });
        jTextLugarTrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextLugarTrabajoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextLugarTrabajoKeyReleased(evt);
            }
        });

        jTextCargoqueOcupa.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextCargoqueOcupa.text")); // NOI18N
        jTextCargoqueOcupa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextCargoqueOcupaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextCargoqueOcupaKeyReleased(evt);
            }
        });

        jDateFechaIngreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateFechaIngresoKeyPressed(evt);
            }
        });

        jTextDireccionLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextDireccionLaboral.text")); // NOI18N
        jTextDireccionLaboral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDireccionLaboralKeyPressed(evt);
            }
        });

        jTextTelefonoLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextTelefonoLaboral.text")); // NOI18N
        jTextTelefonoLaboral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextTelefonoLaboralKeyPressed(evt);
            }
        });

        jTextFaxLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextFaxLaboral.text")); // NOI18N
        jTextFaxLaboral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFaxLaboralActionPerformed(evt);
            }
        });
        jTextFaxLaboral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFaxLaboralKeyPressed(evt);
            }
        });

        jTextMailLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jTextMailLaboral.text")); // NOI18N
        jTextMailLaboral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMailLaboralActionPerformed(evt);
            }
        });

        Salario.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.Salario.text")); // NOI18N
        Salario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));

        SalarioConyugue.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.SalarioConyugue.text")); // NOI18N
        SalarioConyugue.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        otrosIngresos.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.otrosIngresos.text")); // NOI18N
        otrosIngresos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextMailLaboral)
                            .addComponent(jTextDireccionLaboral)
                            .addComponent(jTextCargoqueOcupa)
                            .addComponent(jTextLugarTrabajo)
                            .addComponent(jTextProfesion)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jDateFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 150, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel21))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SalarioConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Salario, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(otrosIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextTelefonoLaboral, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                    .addComponent(jTextFaxLaboral))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextProfesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextLugarTrabajo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextCargoqueOcupa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateFechaIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextDireccionLaboral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextTelefonoLaboral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFaxLaboral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextMailLaboral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(Salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(SalarioConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(otrosIngresos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jPanel5.border.title"))); // NOI18N

        jComboAsesor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel26.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel26.text")); // NOI18N

        LimiteCredito.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.LimiteCredito.text")); // NOI18N
        LimiteCredito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        LimiteCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LimiteCreditoKeyPressed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel27.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel27.text")); // NOI18N

        jComboCasaPropia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Alquiler", "Casa Propia", "Con Parientes" }));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel28.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel28.text")); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel29.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel29.text")); // NOI18N

        jComboAuto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel31.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel31.text")); // NOI18N

        jComboInfor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO", "SI" }));
        jComboInfor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboInforActionPerformed(evt);
            }
        });

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane1.setViewportView(observaciones);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel33.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel33.text")); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel34.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel34.text")); // NOI18N

        plazocredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        plazocredito.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.plazocredito.text")); // NOI18N
        plazocredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plazocreditoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(plazocredito, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel33)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboAuto, javax.swing.GroupLayout.Alignment.LEADING, 0, 52, Short.MAX_VALUE)
                                .addComponent(jComboInfor, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(LimiteCredito, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboCasaPropia, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jComboAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 180, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(jComboAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plazocredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboCasaPropia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jComboAuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboInfor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DatosLaboralesLayout = new javax.swing.GroupLayout(DatosLaborales);
        DatosLaborales.setLayout(DatosLaboralesLayout);
        DatosLaboralesLayout.setHorizontalGroup(
            DatosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosLaboralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        DatosLaboralesLayout.setVerticalGroup(
            DatosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosLaboralesLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(DatosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jPanel4.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jPanel4.AccessibleContext.accessibleName")); // NOI18N

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jPanel2.TabConstraints.tabTitle"), DatosLaborales); // NOI18N

        Referencias.setToolTipText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.Referencias.toolTipText")); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jPanel6.border.title"))); // NOI18N

        tablalaboral.setModel(laboral);
        jScrollPane2.setViewportView(tablalaboral);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        referencialugartrabajo.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.referencialugartrabajo.text")); // NOI18N
        referencialugartrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                referencialugartrabajoKeyReleased(evt);
            }
        });

        referenciafonolaboral.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.referenciafonolaboral.text")); // NOI18N

        etiqueta1.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.etiqueta1.text")); // NOI18N

        etiqueta2.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.etiqueta2.text")); // NOI18N

        GrabarRefLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.GrabarRefLaboral.text")); // NOI18N
        GrabarRefLaboral.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarRefLaboral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarRefLaboralActionPerformed(evt);
            }
        });

        DelRefLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.DelRefLaboral.text")); // NOI18N
        DelRefLaboral.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DelRefLaboral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelRefLaboralActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(etiqueta1)
                        .addGap(0, 122, Short.MAX_VALUE))
                    .addComponent(referencialugartrabajo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(etiqueta2)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(referenciafonolaboral, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(DelRefLaboral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GrabarRefLaboral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiqueta1)
                    .addComponent(etiqueta2)
                    .addComponent(GrabarRefLaboral))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(referencialugartrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(DelRefLaboral)
                        .addComponent(referenciafonolaboral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jPanel7.border.title"))); // NOI18N

        tablacomercial.setModel(comercial);
        jScrollPane3.setViewportView(tablacomercial);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        referenciacomercio.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.referenciacomercio.text")); // NOI18N
        referenciacomercio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                referenciacomercioKeyReleased(evt);
            }
        });

        referenciafonocomercial.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.referenciafonocomercial.text")); // NOI18N

        GrabarRefComercial.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.GrabarRefComercial.text")); // NOI18N
        GrabarRefComercial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarRefComercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarRefComercialActionPerformed(evt);
            }
        });

        BorrarRefComercial.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.BorrarRefComercial.text")); // NOI18N
        BorrarRefComercial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarRefComercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarRefComercialActionPerformed(evt);
            }
        });

        jLabel46.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel46.text")); // NOI18N

        jLabel47.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jLabel47.text")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(referenciacomercio)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(53, 53, 53)
                        .addComponent(GrabarRefComercial))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(referenciafonocomercial, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BorrarRefComercial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrabarRefComercial, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel46)
                        .addComponent(jLabel47)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenciacomercio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referenciafonocomercial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BorrarRefComercial))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout ReferenciasLayout = new javax.swing.GroupLayout(Referencias);
        Referencias.setLayout(ReferenciasLayout);
        ReferenciasLayout.setHorizontalGroup(
            ReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReferenciasLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        ReferenciasLayout.setVerticalGroup(
            ReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReferenciasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.Referencias.TabConstraints.tabTitle"), Referencias); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonAbriArchivo.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.BotonAbriArchivo.text")); // NOI18N
        BotonAbriArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAbriArchivoActionPerformed(evt);
            }
        });

        etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.etiqueta.text")); // NOI18N

        GuardarArchivo.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.GuardarArchivo.text")); // NOI18N
        GuardarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarArchivoActionPerformed(evt);
            }
        });

        jButton3.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.jButton4.text")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addGap(141, 141, 141)
                            .addComponent(jButton4))
                        .addComponent(etiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonAbriArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GuardarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAbriArchivo)
                    .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(GuardarArchivo)))
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelImagenesLayout = new javax.swing.GroupLayout(PanelImagenes);
        PanelImagenes.setLayout(PanelImagenesLayout);
        PanelImagenesLayout.setHorizontalGroup(
            PanelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelImagenesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelImagenesLayout.setVerticalGroup(
            PanelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelImagenesLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_alumnos.PanelImagenes.TabConstraints.tabTitle"), PanelImagenes); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(Panel1Clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(Panel1Clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton1.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jButton1.text")); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(org.openide.util.NbBundle.getMessage(detalle_alumnos.class, "detalle_asesores.jButton2.text")); // NOI18N
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(354, 354, 354))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refrescarCarrusel(int p) {
        etiqueta.setText("");
        etiqueta.setIcon(null);
        albumfotoDAO imag = new albumfotoDAO();
        if (Integer.valueOf(this.codigo.getText()) > 0) {
            imagenes = imag.getImagenes(Integer.valueOf(this.codigo.getText()));
            if (p < imagenes.size()) {
                if (imagenes.size() > 0) {
                    ImageIcon icon = new ImageIcon(imagenes.get(p).getFoto().getScaledInstance(this.etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_DEFAULT));
                    etiqueta.setText("");
                    etiqueta.setIcon(icon);
                    nombrearchivo.setText(imagenes.get(p).getNombre());
                }
            } else {
                contador--;
            }
        }
    }

    private void CargarReferenciaLaboral() {
        laboral.addColumn("Id");
        laboral.addColumn("Lugar de Trabajo");
        laboral.addColumn("Fono");
        int[] anchos = {70, 200, 100};
        for (int i = 0; i < laboral.getColumnCount(); i++) {
            tablalaboral.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablalaboral.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablalaboral.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablalaboral.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablalaboral.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void CargarReferenciaComercial() {
        comercial.addColumn("Id");
        comercial.addColumn("Denominación");
        comercial.addColumn("Fono");
        int[] anchos = {70, 200, 100};
        for (int i = 0; i < comercial.getColumnCount(); i++) {
            tablacomercial.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacomercial.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacomercial.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacomercial.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablacomercial.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void consultarTabla(String campoclave) {

        con = new Conexion();
        stm = con.conectar();

        //Inicializo Variables
        int nAsesor = 0;
        int nCategoria = 0;
        int nLocalidad = 0;
        int nBarrio = 0;
        String numero;
        int num;
        String nCodCategoria;
        String cCodLocalidad;
        String cAsesor;
        String cBarrio;
        ResultSet results = null;

        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = cliDAO.buscarId(Integer.valueOf(this.codigo.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (cl != null) {
            nCategoria = cl.getCategoria();
            nLocalidad = cl.getLocalidad().getCodigo();
            nAsesor = cl.getAsesor();
            nBarrio = cl.getBarrio().getCodigo();
            this.jTextnombre.setText(cl.getNombre());
            this.ruc.setText(cl.getRuc());
            this.cedula.setText(cl.getCedula());
            this.jComboEstadocivil.setSelectedItem(cl.getEstadocivil());
            this.jTextConyugue.setText(cl.getConyugue());
            this.jDateNacimiento.setDate(cl.getFechanacimiento());
            this.jTextDireccion.setText(cl.getDireccion());
            this.jTextFono.setText(cl.getTelefono());
            this.jTextCelular.setText(cl.getCelular());
            this.jTextmail.setText(cl.getMail());
            this.jComboEstado.setSelectedIndex(cl.getEstado());
            this.jTextProfesion.setText(cl.getProfesion());
            this.jTextLugarTrabajo.setText(cl.getLugartrabajo());
            this.jTextCargoqueOcupa.setText(cl.getCargolaboral());
            this.jDateFechaIngreso.setDate(cl.getFechaingreso());
            this.jTextDireccionLaboral.setText(cl.getDireccionlaboral());
            this.jTextTelefonoLaboral.setText(cl.getTelefonolaboral());
            this.jTextFaxLaboral.setText(cl.getFaxlaboral());
            this.jTextMailLaboral.setText(cl.getMaillaboral());
            this.Salario.setText(formato.format(cl.getSalario()));
            this.SalarioConyugue.setText(formato.format(cl.getSalarioconyugue()));
            this.otrosIngresos.setText(formato.format(cl.getOtrosingresos()));
            this.LimiteCredito.setText(formato.format(cl.getLimitecredito()));
            this.jComboCasaPropia.setSelectedIndex(cl.getCasapropia());
            this.jComboAuto.setSelectedIndex(cl.getAutopropio());
            this.jComboInfor.setSelectedIndex(cl.getInformconf());
            this.observaciones.setText(cl.getNotas());
            this.plazocredito.setText(String.valueOf(cl.getPlazocredito()));
            //Con esta función mostramos los valores numericos con los puntos correspondientes
            nCodCategoria = selectCombo(jComboCategoria, "select * from categoria_clientes where codigo='" + nCategoria + "'");
            cCodLocalidad = selectCombo(jComboLocalidades, "select codigo,nombre from localidades where codigo='" + nLocalidad + "'");
            cAsesor = selectCombo(jComboAsesor, "select codigo,nombre from vendedores where estado=1 and codigo='" + nAsesor + "'");
            cBarrio = selectCombo(barrio, "select codigo,nombre from barrios where codigo='" + nBarrio + "'");
        }
        //
    }

    public void limpiarCombos() {
        jComboCategoria.removeAllItems();
        BD.cargarCombo("select * from categoria_clientes", jComboCategoria);
        jComboCategoria.setSelectedIndex(0);

        jComboLocalidades.removeAllItems();;
        BD.cargarCombo("select codigo,nombre from localidades", jComboLocalidades);
        jComboLocalidades.setSelectedIndex(0);

        jComboAsesor.removeAllItems();
        BD.cargarCombo("select codigo,nombre from vendedores where estado=1", jComboAsesor);

        barrio.removeAllItems();
        BD.cargarCombo("select codigo,nombre from barrios ", barrio);

    }

    public String selectCombo(JComboBox combo, String sql) {
        ResultSet codi = null;
        ComboBoxModel modelo = combo.getModel();
        int longitud = combo.getItemCount();
        int cod = 0;
        int c = 0;
        String codigo = "";
        Object ob = null;
        codi = BD.Select(sql);
        try {
            codi.first();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        while (c < longitud) {
            combo.setSelectedIndex(c);
            ob = combo.getSelectedItem();
            cod = Integer.valueOf(((String[]) ob)[0]).intValue();
            try {
                if (cod == codi.getInt(1)) {
                    codigo = Integer.valueOf(cod).toString();
                    c = longitud;
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            c++;
        }
        return codigo;
    }

    private void jTextnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextnombreKeyPressed
        //Pasar el foco a otro objeto con la tecla Enter o cursor
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboCategoria.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextnombreKeyPressed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboEstadocivil.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cedula.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rucKeyPressed

    private void jTextConyugueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextConyugueKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jDateNacimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboEstadocivil.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextConyugueKeyPressed

    private void jDateNacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateNacimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboLocalidades.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextConyugue.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateNacimientoKeyPressed

    private void jTextDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDireccionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextFono.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboLocalidades.requestFocus();
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jTextDireccionKeyPressed

    private void jTextFonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFonoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextCelular.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextDireccion.requestFocus();
        }   // TODO add your handling code here:
    }//GEN-LAST:event_jTextFonoKeyPressed

    private void jTextCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCelularActionPerformed

    private void jTextCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCelularKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextmail.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextFono.requestFocus();
        }   // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCelularKeyPressed

    private void jTextmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextmailKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboEstado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextCelular.requestFocus();
        }   // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextmailKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "";
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (this.jTextnombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nombre o Denominación del Cliente");
            this.jTextnombre.requestFocus();
            return;
        }
        if (this.ruc.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el RUC O CI del Cliente");
            this.ruc.requestFocus();
            return;
        }
        if (this.jTextDireccion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Dirección Particular del Cliente");
            this.jTextDireccion.requestFocus();
            return;
        }

        if (this.jTextCelular.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Celular");
            this.jTextCelular.requestFocus();
            return;
        }
        if (this.jTextFono.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Línea Baja");
            this.jTextFono.requestFocus();
            return;
        }

        if (this.jTextLugarTrabajo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Lugar de Trabajo");
            this.jTextLugarTrabajo.requestFocus();
            return;
        }

        if (this.jTextProfesion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Profesión");
            this.jTextProfesion.requestFocus();
            return;
        }

        if (this.jTextCargoqueOcupa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Cargo que Ocupa");
            this.jTextCargoqueOcupa.requestFocus();
            return;
        }

        if (this.jTextDireccionLaboral.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Dirección Laboral");
            this.jTextDireccionLaboral.requestFocus();
            return;
        }

        if (this.jTextTelefonoLaboral.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Teléfono Laboral");
            this.jTextTelefonoLaboral.requestFocus();
            return;
        }

        ControlGrabado.REGISTRO_GRABADO = "SI";
        BDConexion BD = new BDConexion();

        Date FechaNac = ODate.de_java_a_sql(this.jDateNacimiento.getDate()); // Formateamos los campos de fecha
        Date dFechaIngreso = ODate.de_java_a_sql(this.jDateFechaIngreso.getDate());

        Object objCategoria = this.jComboCategoria.getSelectedItem();
        Object objLocalidad = this.jComboLocalidades.getSelectedItem();
        Object objAsesor = this.jComboAsesor.getSelectedItem();
        Object objBarrio = this.barrio.getSelectedItem();

        String barrio = ((String[]) objBarrio)[0];
        String Categoria = ((String[]) objCategoria)[0];
        String Localidad = ((String[]) objLocalidad)[0];
        String asesor = ((String[]) objAsesor)[0];

        // Formateamos el campo de salario para enviarlo a la base de datos
        String salario = this.Salario.getText();
        // con el metodo replace borramos los puntos y remplazamos la coma decimal
        // por el punto para su correcta captura en la base de datos
        if (this.Salario.getText().trim().length() > 0) {
            salario = salario.replace(".", "");
            salario = salario.replace(",", ".");
        } else {
            salario = "0";
        }

        // Formateamos el campo de salario del conyugye para enviarlo a la base de datos
        String salarioconyugue = this.SalarioConyugue.getText();
        // con el metodo replace borramos los puntos y remplazamos la coma decimal
        // por el punto para su correcta captura en la base de datos
        if (this.SalarioConyugue.getText().trim().length() > 0) {
            salarioconyugue = salarioconyugue.replace(".", "");
            salarioconyugue = salarioconyugue.replace(",", ".");
        } else {
            salarioconyugue = "0";
        }

        String otrosingresos = this.otrosIngresos.getText();
        if (this.otrosIngresos.getText().trim().length() > 0) {
            otrosingresos = otrosingresos.replace(".", "");
            otrosingresos = otrosingresos.replace(",", ".");
        } else {
            otrosingresos = "0";
        }

        String limitecredito = this.LimiteCredito.getText();
        if (limitecredito.trim().length() > 0) {
            limitecredito = limitecredito.replace(".", "");
            limitecredito = limitecredito.replace(",", ".");
        } else {
            limitecredito = "0";
        }

        String cParametro = this.jTextControl.getText();
        String cLetra = "new";
        if (cParametro.equals(cLetra)) {
            if (this.codigo.getText().trim().length() <= 1) {
                BD.insertarRegistro("clientes", "nombre,cedula,ruc,fechanacimiento,estadocivil,conyugue,direccion,telefono,celular,mail,estado,categoria,localidad,salario,salarioconyugue,otrosingresos,profesion,lugartrabajo,cargolaboral,direccionlaboral,fechaingreso,telefonolaboral,faxlaboral,maillaboral,limitecredito,casapropia,autopropio,informconf,asesor,notas,barrio,plazocredito", "'" + this.jTextnombre.getText() + "','" + this.cedula.getText() + "','" + this.ruc.getText() + "','" + FechaNac + "','" + this.jComboEstadocivil.getSelectedItem() + "','" + this.jTextConyugue.getText() + "','" + this.jTextDireccion.getText() + "','" + this.jTextFono.getText() + "','" + this.jTextCelular.getText() + "','" + this.jTextMailLaboral.getText() + "','" + this.jComboEstado.getSelectedIndex() + "','" + Categoria + "','" + Localidad + "','" + salario + "','" + salarioconyugue + "','" + otrosingresos + "','" + this.jTextProfesion.getText() + "','" + this.jTextLugarTrabajo.getText() + "','" + this.jTextCargoqueOcupa.getText() + "','" + this.jTextDireccionLaboral.getText() + "','" + dFechaIngreso + "','" + this.jTextTelefonoLaboral.getText() + "','" + this.jTextFaxLaboral.getText() + "','" + this.jTextMailLaboral.getText() + "','" + limitecredito + "','" + this.jComboCasaPropia.getSelectedIndex() + "','" + this.jComboAuto.getSelectedIndex() + "','" + this.jComboInfor.getSelectedIndex() + "','" + asesor + "','" + this.observaciones.getText().trim() + "','" + barrio + "','" + this.plazocredito.getText() + "'");
            } else {
                BD.insertarRegistro("clientes", "codigo,nombre,ruc,fechanacimiento,estadocivil,conyugue,direccion,telefono,celular,mail,estado,categoria,localidad,salario,salarioconyugue,otrosingresos,profesion,lugartrabajo,cargolaboral,direccionlaboral,fechaingreso,telefonolaboral,faxlaboral,maillaboral,limitecredito,casapropia,autopropio,informconf,asesor,notas,barrio,plazocredito", "'" + this.codigo.getText() + "','" + this.jTextnombre.getText() + "','" + this.ruc.getText() + "','" + FechaNac + "','" + this.jComboEstadocivil.getSelectedItem() + "','" + this.jTextConyugue.getText() + "','" + this.jTextDireccion.getText() + "','" + this.jTextFono.getText() + "','" + this.jTextCelular.getText() + "','" + this.jTextMailLaboral.getText() + "','" + this.jComboEstado.getSelectedIndex() + "','" + Categoria + "','" + Localidad + "','" + salario + "','" + salarioconyugue + "','" + otrosingresos + "','" + this.jTextProfesion.getText() + "','" + this.jTextLugarTrabajo.getText() + "','" + this.jTextCargoqueOcupa.getText() + "','" + this.jTextDireccionLaboral.getText() + "','" + dFechaIngreso + "','" + this.jTextTelefonoLaboral.getText() + "','" + this.jTextFaxLaboral.getText() + "','" + this.jTextMailLaboral.getText() + "','" + limitecredito + "','" + this.jComboCasaPropia.getSelectedIndex() + "','" + this.jComboAuto.getSelectedIndex() + "','" + this.jComboInfor.getSelectedIndex() + "','" + asesor + "','" + this.observaciones.getText().trim() + "','" + barrio + "','" + this.plazocredito.getText() + "'");
            }
        } else {
            BD.actualizarRegistro("clientes", " nombre='" + this.jTextnombre.getText() + "',cedula='" + this.cedula.getText() + "',plazocredito='" + plazocredito.getText() + "',ruc='" + this.ruc.getText() + "',fechanacimiento='" + FechaNac + "',estadocivil='" + this.jComboEstadocivil.getSelectedItem() + "',conyugue='" + this.jTextConyugue.getText() + "',direccion='" + this.jTextDireccion.getText().trim() + "',telefono='" + this.jTextFono.getText().trim() + "',celular='" + this.jTextCelular.getText() + "',mail='" + this.jTextMailLaboral.getText() + "',estado ='" + this.jComboEstado.getSelectedIndex() + "',localidad ='" + Localidad + "',categoria ='" + Categoria + "',salario ='" + salario + "',salarioconyugue ='" + salarioconyugue + "',otrosingresos ='" + otrosingresos + "',profesion= '" + this.jTextProfesion.getText() + "',lugartrabajo='" + this.jTextLugarTrabajo.getText() + "',cargolaboral='" + this.jTextCargoqueOcupa.getText() + "',direccionlaboral='" + this.jTextDireccionLaboral.getText() + "',fechaingreso='" + dFechaIngreso + "',telefonolaboral='" + this.jTextTelefonoLaboral.getText() + "',faxlaboral= '" + this.jTextFaxLaboral.getText() + "',maillaboral='" + this.jTextMailLaboral.getText() + "',limitecredito='" + limitecredito + "',casapropia='" + this.jComboCasaPropia.getSelectedIndex() + "',autopropio='" + this.jComboAuto.getSelectedIndex() + "',informconf='" + this.jComboInfor.getSelectedIndex() + "',asesor='" + asesor + "',barrio='" + barrio + "',notas ='" + this.observaciones.getText().trim() + "'", "codigo= " + this.codigo.getText());
        }
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboEstadocivilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboEstadocivilKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextConyugue.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sexo.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboEstadocivilKeyPressed

    private void jComboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboCategoriaActionPerformed

    private void jTextMailLaboralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMailLaboralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextMailLaboralActionPerformed

    private void jTextFaxLaboralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFaxLaboralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFaxLaboralActionPerformed

    private void jTextLugarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextLugarTrabajoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLugarTrabajoActionPerformed

    private void jTextProfesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextProfesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProfesionActionPerformed

    private void jComboInforActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboInforActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboInforActionPerformed

    private void jComboLocalidadesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboLocalidadesKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextDireccion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jDateNacimiento.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jComboLocalidadesKeyPressed

    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextnombre.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyPressed

    private void jTextProfesionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextProfesionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextLugarTrabajo.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProfesionKeyPressed

    private void jTextLugarTrabajoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextLugarTrabajoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextCargoqueOcupa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextProfesion.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLugarTrabajoKeyPressed

    private void jTextCargoqueOcupaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCargoqueOcupaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jDateFechaIngreso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextLugarTrabajo.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:

    }//GEN-LAST:event_jTextCargoqueOcupaKeyPressed

    private void jDateFechaIngresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateFechaIngresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextDireccionLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextCargoqueOcupa.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jDateFechaIngresoKeyPressed

    private void jTextDireccionLaboralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDireccionLaboralKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextTelefonoLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jDateFechaIngreso.requestFocus();
        }   // TODO add your handling code here:       
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDireccionLaboralKeyPressed

    private void jTextTelefonoLaboralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTelefonoLaboralKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextFaxLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextDireccionLaboral.requestFocus();
        }   // TODO add your handling code here:               // TODO add your handling code here:
    }//GEN-LAST:event_jTextTelefonoLaboralKeyPressed

    private void jTextFaxLaboralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFaxLaboralKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextMailLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextFaxLaboral.requestFocus();
        }   // TODO add your handling code here:           // TODO add your handling code here:
    }//GEN-LAST:event_jTextFaxLaboralKeyPressed

    private void jTextnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextnombreKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextnombreKeyReleased

    private void jTextConyugueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextConyugueKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextConyugue);
        jTextConyugue.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextConyugueKeyReleased

    private void jTextProfesionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextProfesionKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextProfesion);
        jTextProfesion.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProfesionKeyReleased

    private void jTextLugarTrabajoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextLugarTrabajoKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextLugarTrabajo);
        jTextLugarTrabajo.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLugarTrabajoKeyReleased

    private void jTextCargoqueOcupaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCargoqueOcupaKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextCargoqueOcupa);
        jTextCargoqueOcupa.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCargoqueOcupaKeyReleased

    private void barrioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barrioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_barrioKeyPressed

    private void DelRefLaboralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelRefLaboralActionPerformed
        int a = this.tablalaboral.getSelectedRow();
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            referencia_laboralDAO borrarL = new referencia_laboralDAO();
            String cItemBorrar = this.tablalaboral.getValueAt(a, 0).toString();
            try {
                borrarL.borrarRefLaboral(Integer.valueOf(cItemBorrar));
                JOptionPane.showMessageDialog(null, "Registro Eliminado Exitósamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            GrillaLaboral GrillaL = new GrillaLaboral();
            Thread Hilo2 = new Thread(GrillaL);
            Hilo2.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_DelRefLaboralActionPerformed

    private void GrabarRefLaboralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarRefLaboralActionPerformed
        if (this.referencialugartrabajo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Denominación del Lugar de Trabajo");
            this.referencialugartrabajo.requestFocus();
            return;
        }
        if (this.referenciafonolaboral.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese un Nro. de Teléfono");
            this.referenciafonolaboral.requestFocus();
            return;
        }

        referencia_laboralDAO grabarL = new referencia_laboralDAO();
        referencia_laboral L = new referencia_laboral();
        //Clase de Cliente porque tiene que hacer referencia al cliente
        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = cliDAO.buscarId(Integer.valueOf(this.codigo.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        L.setIdcliente(cl);
        L.setDescripcion(this.referencialugartrabajo.getText());
        L.setTelefono(this.referenciafonolaboral.getText());
        try {
            grabarL.insertarRefLaboral(L);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        GrillaLaboral GrillaL = new GrillaLaboral();
        Thread Hilo2 = new Thread(GrillaL);
        Hilo2.start();
        this.referencialugartrabajo.setText("");
        this.referenciafonolaboral.setText("");
        this.referencialugartrabajo.requestFocus();
    }//GEN-LAST:event_GrabarRefLaboralActionPerformed

    private void referencialugartrabajoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_referencialugartrabajoKeyReleased
        String letras = ConvertirMayusculas.cadena(referencialugartrabajo);
        referencialugartrabajo.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_referencialugartrabajoKeyReleased

    private void referenciacomercioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_referenciacomercioKeyReleased
        String letras = ConvertirMayusculas.cadena(referenciacomercio);
        referenciacomercio.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_referenciacomercioKeyReleased

    private void GrabarRefComercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarRefComercialActionPerformed
        if (this.referenciacomercio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Denominación del Comercio");
            this.referenciacomercio.requestFocus();
            return;
        }
        if (this.referenciafonocomercial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese un Nro. de Teléfono");
            this.referenciafonocomercial.requestFocus();
            return;
        }

        referencia_comercialDAO grabarC = new referencia_comercialDAO();
        referencia_comercial C = new referencia_comercial();
        //Clase de Cliente porque tiene que hacer referencia al cliente
        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = cliDAO.buscarId(Integer.valueOf(this.codigo.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        C.setIdcliente(cl);
        C.setDescripcion(this.referenciacomercio.getText());
        C.setTelefono(this.referenciafonocomercial.getText());
        try {
            grabarC.insertarRefComercial(C);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        GrillaComercial GrillaC = new GrillaComercial();
        Thread Hilo1 = new Thread(GrillaC);
        Hilo1.start();
        this.referenciacomercio.setText("");
        this.referenciafonocomercial.setText("");
        this.referenciacomercio.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarRefComercialActionPerformed

    private void BorrarRefComercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarRefComercialActionPerformed
        int a = this.tablacomercial.getSelectedRow();
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            referencia_comercialDAO borrarC = new referencia_comercialDAO();
            String cItemBorrar = this.tablacomercial.getValueAt(a, 0).toString();
            try {
                borrarC.borrarRefComercial(Integer.valueOf(cItemBorrar));
                JOptionPane.showMessageDialog(null, "Registro Eliminado Exitósamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            GrillaComercial GrillaC = new GrillaComercial();
            Thread Hilo1 = new Thread(GrillaC);
            Hilo1.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarRefComercialActionPerformed

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
                etiqueta.setText("");
                ImageIcon icon = new ImageIcon(preview.getScaledInstance(etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_DEFAULT));
                etiqueta.setIcon(icon);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BotonAbriArchivoActionPerformed

    private void GuardarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarArchivoActionPerformed
        albumfotoDAO GrabarImagen = new albumfotoDAO();
        albumfoto alb = new albumfoto();
        clienteDAO clie = new clienteDAO();
        cliente cl = null;
        try {
            cl = clie.buscarId(Integer.valueOf(codigo.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        alb.setCodigo(cl);
        alb.setNombre(nombrearchivo.getText().toString());
        try {
            GrabarImagen.insertarimagen(alb, nombrearchivo.getText().toString());
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            nombrearchivo.setText("");
            etiqueta.setIcon(null);
            refrescarCarrusel(0);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarArchivoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if ((contador - 1) >= 0) {
            contador--;
            refrescarCarrusel(contador);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        contador++;
        refrescarCarrusel(contador);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cedulaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ruc.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboCategoria.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cedulaKeyPressed

    private void jTextnombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextnombreFocusLost
        String letras = ConvertirMayusculas.cadena(jTextnombre);
        jTextnombre.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextnombreFocusLost

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
        if (Double.valueOf(this.codigo.getText()) != 0) {
            CalcularRuc rucDigito = new CalcularRuc();
            String cCodigo = this.codigo.getText();
            int base = 11;
            int digito = rucDigito.CalcularDigito(cCodigo, base);
            ruc.setText(cCodigo.toString() + '-' + String.valueOf(digito));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoFocusLost

    private void LimiteCreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LimiteCreditoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.plazocredito.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboAsesor.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_LimiteCreditoKeyPressed

    private void plazocreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plazocreditoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboCasaPropia.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.LimiteCredito.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:

    }//GEN-LAST:event_plazocreditoKeyPressed

    private void sexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sexoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboEstadocivil.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ruc.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_sexoKeyPressed

    public void MostrarDatos(String Operacion) {
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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //    new detalle_asesores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BorrarRefComercial;
    private javax.swing.JButton BotonAbriArchivo;
    private javax.swing.JPanel DatosGenerales;
    private javax.swing.JPanel DatosLaborales;
    private javax.swing.JButton DelRefLaboral;
    private javax.swing.JButton GrabarRefComercial;
    private javax.swing.JButton GrabarRefLaboral;
    private javax.swing.JButton GuardarArchivo;
    private org.jdesktop.swingx.JXFormattedTextField LimiteCredito;
    private javax.swing.JTabbedPane Panel1Clientes;
    private javax.swing.JPanel PanelImagenes;
    private javax.swing.JPanel Referencias;
    private org.jdesktop.swingx.JXFormattedTextField Salario;
    private org.jdesktop.swingx.JXFormattedTextField SalarioConyugue;
    private javax.swing.JComboBox barrio;
    private javax.swing.JTextField cedula;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel etiqueta;
    private javax.swing.JLabel etiqueta1;
    private javax.swing.JLabel etiqueta2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboAsesor;
    private javax.swing.JComboBox jComboAuto;
    private javax.swing.JComboBox jComboCasaPropia;
    private javax.swing.JComboBox jComboCategoria;
    private javax.swing.JComboBox jComboEstado;
    private javax.swing.JComboBox jComboEstadocivil;
    private javax.swing.JComboBox jComboInfor;
    private javax.swing.JComboBox jComboLocalidades;
    private com.toedter.calendar.JDateChooser jDateFechaIngreso;
    private com.toedter.calendar.JDateChooser jDateNacimiento;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JTextField jTextCargoqueOcupa;
    private javax.swing.JTextField jTextCelular;
    private javax.swing.JTextField jTextControl;
    private javax.swing.JTextField jTextConyugue;
    private javax.swing.JTextField jTextDireccion;
    private javax.swing.JTextField jTextDireccionLaboral;
    private javax.swing.JTextField jTextFaxLaboral;
    private javax.swing.JTextField jTextFono;
    private javax.swing.JTextField jTextLugarTrabajo;
    private javax.swing.JTextField jTextMailLaboral;
    private javax.swing.JTextField jTextProfesion;
    private javax.swing.JTextField jTextTelefonoLaboral;
    private javax.swing.JTextField jTextmail;
    private javax.swing.JTextField jTextnombre;
    private javax.swing.JTextField nombrearchivo;
    private javax.swing.JTextArea observaciones;
    private org.jdesktop.swingx.JXFormattedTextField otrosIngresos;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTextField plazocredito;
    private javax.swing.JTextField referenciacomercio;
    private javax.swing.JTextField referenciafonocomercial;
    private javax.swing.JTextField referenciafonolaboral;
    private javax.swing.JTextField referencialugartrabajo;
    private javax.swing.JTextField ruc;
    private javax.swing.JComboBox sexo;
    private javax.swing.JTable tablacomercial;
    private javax.swing.JTable tablalaboral;
    // End of variables declaration//GEN-END:variables

    private class GrillaLaboral extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = tablalaboral.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                laboral.removeRow(0);
            }
            referencia_laboralDAO LabDAO = new referencia_laboralDAO();
            try {
                for (referencia_laboral l : LabDAO.muestrarefxcliente(Integer.valueOf(codigo.getText()))) {
                    String Datos[] = {String.valueOf(l.getItem()), l.getDescripcion(), l.getTelefono()};
                    laboral.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablalaboral.setRowSorter(new TableRowSorter(laboral));
            int cantFilas = tablalaboral.getRowCount();
            if (cantFilas > 0) {
                GrabarRefLaboral.setEnabled(true);
                DelRefLaboral.setEnabled(true);
            } else {
                GrabarRefLaboral.setEnabled(true);
                DelRefLaboral.setEnabled(false);
            }
        }
    }

    private class GrillaComercial extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = tablacomercial.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                comercial.removeRow(0);
            }
            referencia_comercialDAO ComDAO = new referencia_comercialDAO();
            try {
                for (referencia_comercial c : ComDAO.muestrarefxcliente(Integer.valueOf(codigo.getText()))) {
                    String Datos[] = {String.valueOf(c.getItem()), c.getDescripcion(), c.getTelefono()};
                    comercial.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacomercial.setRowSorter(new TableRowSorter(comercial));
            int cantFilas = tablacomercial.getRowCount();
            if (cantFilas > 0) {
                GrabarRefComercial.setEnabled(true);
                BorrarRefComercial.setEnabled(true);
            } else {
                GrabarRefComercial.setEnabled(true);
                BorrarRefComercial.setEnabled(false);
            }
        }
    }

}

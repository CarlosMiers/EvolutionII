/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import static Clases.Config.cNombreFactura;
import Clases.ConvertirMayusculas;
import Clases.PanelCamara;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.cargoDAO;
import DAO.concursoDAO;
import DAO.departamento_laboralDAO;
import DAO.postulanteDAO;
import DAO.giraduriaDAO;
import DAO.localidadDAO;
import DAO.concursoDAO;
import DAO.profesionDAO;
import DAO.seccionDAO;
import DAO.sucursalDAO;
import DAO.vacanciasDAO;
import Modelo.Tablas;
import Modelo.albumfoto_edificio;
import Modelo.cargo;
import Modelo.concurso;
import Modelo.departamento_laboral;
import Modelo.postulante;
import Modelo.giraduria;
import Modelo.localidad;
import Modelo.pais;
import Modelo.profesion;
import Modelo.seccion;
import Modelo.sucursal;
import Modelo.vacancias;
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

public class postulantes extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelotipo = new Tablas();
    Tablas modeloconcurso = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modeloprofesion = new Tablas();
    Tablas modelocargo = new Tablas();
    Tablas modelodepartamento = new Tablas();
    Tablas modeloseccion = new Tablas();
    Tablas modelolocalidad = new Tablas();
    Tablas modelobarrio = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelopro = new Tablas();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrobarrio, trsfiltronacionalidad, trsfiltrosucursal, trsfiltroprofesion, trsfiltrocargo, trsfiltrodepartamento, trsfiltroseccion, trsfiltrolocalidad, trsfiltrogiraduria;
    int nProp = 0;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("###");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String ruta, cNombre = null;
    String cAgua, cEnergiaelec, cRecolebasu = "0";
    int contador = 0;

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

    public postulantes() {

        initComponents();
        this.fechaproceso.setCalendar(c2);
        this.fechaproceso.setVisible(false);
        this.Salir.setIcon(iconosalir);
        this.Grabar.setIcon(iconograbar);
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.botonsalir.setIcon(iconosalir);
        this.buscarconcurso.setIcon(iconobuscar);
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
        this.TitConcurso();
        ///deshabilitar boton
        //sacarFoto.setVisible(false);
        GrillaPostulantes GrillaOC = new GrillaPostulantes();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        GrillaConcurso grillacon = new GrillaConcurso();
        Thread hilocon = new Thread(grillacon);
        hilocon.start();
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

        BConcurso = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combonacionalidad = new javax.swing.JComboBox();
        jTBuscarNacionalidad = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaconcurso = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarNac = new javax.swing.JButton();
        SalirNac = new javax.swing.JButton();
        detalle_postulante = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        direccion = new javax.swing.JTextField();
        Ubicación = new javax.swing.JLabel();
        apellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        estadocivil = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        sexo = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        nacimiento = new com.toedter.calendar.JDateChooser();
        jLabel30 = new javax.swing.JLabel();
        concurso = new javax.swing.JTextField();
        buscarconcurso = new javax.swing.JButton();
        nombreconcurso = new javax.swing.JTextField();
        conyugue = new javax.swing.JTextField();
        nrocedula = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        estado = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        preparacion_academica = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        objetivos_laborales = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        experiencia_laboral = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        Postulantes = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        buscapersona = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        botonsalir = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        fechaproceso = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        BConcurso.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BConcurso.setTitle("null");

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
        jTBuscarNacionalidad.setText(org.openide.util.NbBundle.getMessage(postulantes.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        tablaconcurso.setModel(modeloconcurso  );
        tablaconcurso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaconcursoMouseClicked(evt);
            }
        });
        tablaconcurso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaconcursoKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablaconcurso);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarNac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarNac.setText(org.openide.util.NbBundle.getMessage(postulantes.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarNac.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarNac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarNacActionPerformed(evt);
            }
        });

        SalirNac.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirNac.setText(org.openide.util.NbBundle.getMessage(postulantes.class, "ventas.SalirCliente.text")); // NOI18N
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

        javax.swing.GroupLayout BConcursoLayout = new javax.swing.GroupLayout(BConcurso.getContentPane());
        BConcurso.getContentPane().setLayout(BConcursoLayout);
        BConcursoLayout.setHorizontalGroup(
            BConcursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BConcursoLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BConcursoLayout.setVerticalGroup(
            BConcursoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BConcursoLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setMaximumSize(new java.awt.Dimension(651, 480));
        jPanel11.setMinimumSize(new java.awt.Dimension(651, 480));

        jLabel12.setText("Código");

        codigo.setEditable(false);
        codigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codigo.setEnabled(false);
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

        jLabel16.setText("Concurso");

        jLabel14.setText("Conyugue");

        estadocivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CASADO/A", "SOLTERO/A", "SEPARADO/A", "DIVORCIADO/A", "VIUDO/A" }));
        estadocivil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                estadocivilKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                estadocivilKeyReleased(evt);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nacimientoKeyReleased(evt);
            }
        });

        jLabel30.setText("Teléfono");

        concurso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        concurso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                concursoFocusGained(evt);
            }
        });
        concurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concursoActionPerformed(evt);
            }
        });
        concurso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                concursoKeyPressed(evt);
            }
        });

        buscarconcurso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarconcurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarconcursoActionPerformed(evt);
            }
        });

        nombreconcurso.setEnabled(false);
        nombreconcurso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreconcursoKeyPressed(evt);
            }
        });

        conyugue.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        conyugue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                conyugueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                conyugueFocusLost(evt);
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

        nrocedula.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "En Proceso", "Rechazado", "Archivar", "Aceptado" }));

        jLabel1.setText("Estado");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(Ubicación)
                    .addComponent(jLabel13)
                    .addComponent(jLabel25)
                    .addComponent(jLabel14)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel5)
                    .addComponent(jLabel16)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccion, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(concurso, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarconcurso, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreconcurso, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(conyugue)
                    .addComponent(apellido)
                    .addComponent(nombre)
                    .addComponent(nrocedula, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 143, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Ubicación, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jLabel27)
                    .addComponent(estadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarconcurso, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(concurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombreconcurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Datos Personales", jPanel11);

        preparacion_academica.setColumns(20);
        preparacion_academica.setRows(5);
        jScrollPane2.setViewportView(preparacion_academica);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Formación Académica", jPanel1);

        objetivos_laborales.setColumns(20);
        objetivos_laborales.setRows(5);
        jScrollPane3.setViewportView(objetivos_laborales);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Objetivos Laborales", jPanel3);

        experiencia_laboral.setColumns(20);
        experiencia_laboral.setRows(5);
        jScrollPane4.setViewportView(experiencia_laboral);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Experiencia Laboral", jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Grabar.setText("Grabar");
        Grabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarActionPerformed(evt);
            }
        });

        Salir.setText("Salir");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grabar)
                    .addComponent(Salir))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout detalle_postulanteLayout = new javax.swing.GroupLayout(detalle_postulante.getContentPane());
        detalle_postulante.getContentPane().setLayout(detalle_postulanteLayout);
        detalle_postulanteLayout.setHorizontalGroup(
            detalle_postulanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_postulanteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(detalle_postulanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        detalle_postulanteLayout.setVerticalGroup(
            detalle_postulanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_postulanteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Postulantes");
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

        Postulantes.setBackground(new java.awt.Color(255, 255, 255));
        Postulantes.setText("Postulantes");

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
                .addComponent(Postulantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
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
                    .addComponent(Postulantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
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

        fechaproceso.setEnabled(false);
        fechaproceso.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        fechaproceso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaprocesoFocusGained(evt);
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
                        .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonsalir)
                .addGap(26, 26, 26)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
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
        this.estado.setEnabled(false);
        detalle_postulante.setModal(true);
        detalle_postulante.setSize(628, 500);
        //Establecemos un título para el jDialog
        detalle_postulante.setTitle("Agregar Nuevo Postulante");
        detalle_postulante.setLocationRelativeTo(null);
        detalle_postulante.setVisible(true);

          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        nombre.requestFocus();
        this.codigo.setText("0");
        this.nombre.setText("");
        this.apellido.setText("");
        this.nrocedula.setText("0");
        this.concurso.setText("0");
        this.nombreconcurso.setText("");
        this.conyugue.setText("");
        this.direccion.setText("");
        this.telefono.setText("");
        this.experiencia_laboral.setText("");
        this.preparacion_academica.setText("");
        this.objetivos_laborales.setText("");
        this.nacimiento.setCalendar(c2);
        this.sexo.setSelectedIndex(0);
        this.estado.setSelectedIndex(0);
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
        //int cNivelUsuario=2;
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nFila = this.jTable1.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.jTable1.requestFocus();
                return;
            } else {
                this.codigo.setText(this.jTable1.getValueAt(nFila, 0).toString());
            }
            this.codigo.setEnabled(false);
            postulanteDAO fiDAO = new postulanteDAO();
            postulante fi = null;
            try {
                fi = fiDAO.buscarId(Integer.valueOf(this.codigo.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (fi != null) {
                codigo.setText(String.valueOf(fi.getCodigo()));
                concurso.setText(formatosinpunto.format(fi.getConcurso().getIdconcurso()));
                nombreconcurso.setText(fi.getVacancia().getNombrepuesto());
                nombre.setText(fi.getNombres());
                apellido.setText(fi.getApellidos());
                sexo.setSelectedIndex(fi.getSexo() - 1);
                nrocedula.setText(String.valueOf(fi.getCedula()));
                nacimiento.setDate(fi.getFechanacimiento());
                estadocivil.setSelectedItem(fi.getEstado_civil());
                conyugue.setText(fi.getConyugue());
                direccion.setText(fi.getDireccion());
                telefono.setText(fi.getTelefono());
                estado.setSelectedIndex(fi.getEstado() - 1);
                this.experiencia_laboral.setText(fi.getExperiencia_laboral());
                this.preparacion_academica.setText(fi.getPreparacion_academica());
                this.objetivos_laborales.setText(fi.getObjetivos_laborales());

                this.estado.setEnabled(true);
                detalle_postulante.setModal(true);
                //(Ancho,Alto)
                detalle_postulante.setSize(628, 500);
                //Establecemos un título para el jDialog
                detalle_postulante.setTitle("Modificar Datos del Postulante");
                detalle_postulante.setLocationRelativeTo(null);
                detalle_postulante.setVisible(true);

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
                postulanteDAO fiDAO = new postulanteDAO();
                try {
                    postulante fi = fiDAO.buscarId(Integer.valueOf(num));
                    if (fi == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        fiDAO.borrarFicha(Integer.valueOf(num));
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
                GrillaPostulantes GrillaFi = new GrillaPostulantes();
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
            URL url = getClass().getClassLoader().getResource("Reports/postulante.jasper");
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

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    public void filtronacionalidad(int nNumeroColumna) {
        trsfiltronacionalidad.setRowFilter(RowFilter.regexFilter(jTBuscarNacionalidad.getText(), nNumeroColumna));
    }

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
        trsfiltronacionalidad = new TableRowSorter(tablaconcurso.getModel());
        tablaconcurso.setRowSorter(trsfiltronacionalidad);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarNacionalidadKeyPressed

    private void tablaconcursoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaconcursoMouseClicked
        this.AceptarNac.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaconcursoMouseClicked

    private void tablaconcursoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaconcursoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarNac.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaconcursoKeyPressed

    private void AceptarNacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarNacActionPerformed
        int nFila = this.tablaconcurso.getSelectedRow();
        this.concurso.setText(this.tablaconcurso.getValueAt(nFila, 0).toString());
        this.nombreconcurso.setText(this.tablaconcurso.getValueAt(nFila, 1).toString());
        this.BConcurso.setVisible(false);
        this.jTBuscarNacionalidad.setText("");
        this.estado.requestFocus();

        //this.tipinmueble.requestFocus();
    }//GEN-LAST:event_AceptarNacActionPerformed

    private void SalirNacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirNacActionPerformed
        this.BConcurso.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirNacActionPerformed

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

    private void telefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonoKeyReleased

    private void telefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.sexo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.direccion.requestFocus();
        }   // TODO add your handling code 

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
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyReleased

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed
    }//GEN-LAST:event_nombreKeyPressed

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed

    private void nombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusGained

    private void nrocedulaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrocedulaKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nacimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nacimiento.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_nrocedulaKeyReleased

    private void nrocedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrocedulaKeyPressed
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
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.direccion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.estadocivil.requestFocus();
        }   // TODO add your handling code 

    }//GEN-LAST:event_conyugueKeyPressed

    private void conyugueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conyugueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conyugueActionPerformed

    private void conyugueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_conyugueFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_conyugueFocusGained

    private void nombreconcursoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreconcursoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreconcursoKeyPressed

    private void buscarconcursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarconcursoActionPerformed
        Date dFechaProceso = ODate.de_java_a_sql(fechaproceso.getDate());
        concursoDAO paDAO = new concursoDAO();
        concurso pa = null;
        try {
            pa = paDAO.buscarId(Double.valueOf(this.concurso.getText()), dFechaProceso);
            if (pa.getIdconcurso() == null) {
                BConcurso.setModal(true);
                BConcurso.setSize(500, 575);
                BConcurso.setLocationRelativeTo(null);
                BConcurso.setTitle("Buscar Concurso");
                BConcurso.setVisible(true);
                //                giraduria.requestFocus();
                BConcurso.setModal(false);
            } else {
                nombreconcurso.setText(pa.getIdvacancia().getNombrepuesto());
                //Establecemos un título para el jDialog
            }
            estado.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarconcursoActionPerformed

    private void concursoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_concursoKeyPressed
    }//GEN-LAST:event_concursoKeyPressed

    private void concursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_concursoActionPerformed
        this.buscarconcurso.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_concursoActionPerformed

    private void concursoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_concursoFocusGained
        concurso.selectAll();
    }//GEN-LAST:event_concursoFocusGained

    private void nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoKeyPressed

    private void sexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sexoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sexoKeyPressed

    private void estadocivilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_estadocivilKeyPressed

    }//GEN-LAST:event_estadocivilKeyPressed

    private void apellidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nrocedula.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombre.requestFocus();
        }   // TODO add your handling code 

        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoKeyReleased

    private void apellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoKeyPressed
    }//GEN-LAST:event_apellidoKeyPressed

    private void apellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoActionPerformed

    private void apellidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_apellidoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoFocusGained

    private void direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_direccionKeyReleased

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.telefono.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.conyugue.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_direccionKeyPressed

    private void direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionActionPerformed

    private void direccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionFocusGained

    private void codigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyReleased
        String letras = ConvertirMayusculas.cadena(codigo);
        codigo.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyReleased

    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyPressed

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed

    }//GEN-LAST:event_codigoActionPerformed

    private void codigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusGained
        codigo.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_codigoFocusGained

    private void GrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed

        if (Integer.valueOf(codigo.getText()) != 0) {
            if (Integer.valueOf(Config.cNivelUsuario) > 1) {
                if (this.estado.getSelectedIndex() + 1 == 4) {
                    JOptionPane.showMessageDialog(null, "No puede aprobar Contrataciones");
                    this.nombre.requestFocus();
                    return;
                }
            } else {
                concursoDAO coDAO = new concursoDAO();
                concurso co = null;
                try {
                    co = coDAO.ValidarCantidad(Double.valueOf(concurso.getText()), Double.valueOf(codigo.getText()));
                    int disponible = co.getIdvacancia().getDisponible();
                    int usados = co.getIdvacancia().getCupos();
                    
                    if (disponible == usados) {
                        JOptionPane.showMessageDialog(null, "Sólo tiene "+disponible+" Cargo/s Disponible/s");
                        this.nombre.requestFocus();
                        return;

                    }
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        if (this.nombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Ingresar el nombre del Postulante");
            this.nombre.requestFocus();
            return;
        }
        if (this.apellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Ingresar el Apellido del Postulante");
            this.apellido.requestFocus();
            return;
        }

        if (this.concurso.getText().isEmpty() || this.concurso.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Concurso");
            this.concurso.requestFocus();
            return;
        }

        if (this.nrocedula.getText().isEmpty() || this.nrocedula.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "No puede dejar vacío el N° de Cédula");
            this.nrocedula.requestFocus();
            return;
        }

        postulanteDAO grabar = new postulanteDAO();
        postulante fi = new postulante();
        Date dFechaProceso = ODate.de_java_a_sql(fechaproceso.getDate());

        concursoDAO coDAO = new concursoDAO();
        concurso co = null;
        vacanciasDAO vacDAO = new vacanciasDAO();
        vacancias vac = null;
        try {
            co = coDAO.buscarId(Integer.valueOf(this.concurso.getText()), dFechaProceso);
            vac = vacDAO.buscarId(co.getIdvacancia().getNumero());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (Integer.valueOf(codigo.getText()) > 0) {
            fi.setCodigo(Integer.valueOf(codigo.getText()));
        }    //CAPTURAMOS LOS DATOS DE LA CABECERA
        Date dNacimiento = ODate.de_java_a_sql(nacimiento.getDate());

        fi.setConcurso(co);

        fi.setNombres(this.nombre.getText());
        fi.setApellidos(this.apellido.getText());
        fi.setSexo(sexo.getSelectedIndex() + 1);
        fi.setCedula(Integer.valueOf(this.nrocedula.getText()));
        fi.setEstado_civil(estadocivil.getSelectedItem().toString());
        fi.setConyugue(this.conyugue.getText());
        fi.setDireccion(this.direccion.getText());
        fi.setTelefono(this.telefono.getText());
        fi.setEstado(estado.getSelectedIndex() + 1);
        fi.setFechanacimiento(dNacimiento);
        fi.setVacancia(vac);
        fi.setPreparacion_academica(preparacion_academica.getText());
        fi.setObjetivos_laborales(objetivos_laborales.getText());
        fi.setExperiencia_laboral(experiencia_laboral.getText());
        if (Integer.valueOf(this.codigo.getText()) == 0) {
            try {
                grabar.insertarPostulante(fi);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                this.nombre.requestFocus();
                return;
            }
        } else {
            try {
                grabar.actualizarPostulante(fi);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                this.nombre.requestFocus();
                return;
            }

        }

        detalle_postulante.setVisible(false);
        this.detalle_postulante.setModal(false);
        GrillaPostulantes GrillaFi = new GrillaPostulantes();
        Thread HiloGrilla = new Thread(GrillaFi);

        HiloGrilla.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarActionPerformed

    private void fechaprocesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaprocesoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprocesoFocusGained

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.detalle_postulante.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

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

    private void conyugueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_conyugueFocusLost
        String letras = ConvertirMayusculas.cadena(conyugue);
        conyugue.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_conyugueFocusLost

    private void nacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientoKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.estadocivil.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocedula.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoKeyReleased

    private void estadocivilKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_estadocivilKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.conyugue.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nacimiento.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_estadocivilKeyReleased
    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscapersona.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Nombres y Apellidos");
        modelo.addColumn("Cédula");
        modelo.addColumn("Nacimiento");
        modelo.addColumn("Dirección");
        modelo.addColumn("Télefono");

        int[] anchos = {100, 200, 150, 120, 250, 150};
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

    }

    private void TitConcurso() {
        modeloconcurso.addColumn("Código");
        modeloconcurso.addColumn("Nombre");

        int[] anchos = {40, 50};
        for (int i = 0; i < modeloconcurso.getColumnCount(); i++) {
            tablaconcurso.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaconcurso.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaconcurso.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaconcurso.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaconcurso.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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
                new postulantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarNac;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BConcurso;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private org.edisoncor.gui.label.LabelMetric Postulantes;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirNac;
    private javax.swing.JLabel Ubicación;
    private javax.swing.JTextField apellido;
    private javax.swing.JButton botonsalir;
    private javax.swing.JTextField buscapersona;
    private javax.swing.JButton buscarconcurso;
    private javax.swing.JTextField codigo;
    private javax.swing.JComboBox combonacionalidad;
    private javax.swing.JTextField concurso;
    private javax.swing.JTextField conyugue;
    private javax.swing.JDialog detalle_postulante;
    private javax.swing.JTextField direccion;
    private javax.swing.JComboBox<String> estado;
    private javax.swing.JComboBox<String> estadocivil;
    private javax.swing.JTextArea experiencia_laboral;
    private com.toedter.calendar.JDateChooser fechaproceso;
    private javax.swing.JTextField idControl;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTBuscarNacionalidad;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JDateChooser nacimiento;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombreconcurso;
    private javax.swing.JTextField nrocedula;
    private javax.swing.JTextArea objetivos_laborales;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTextArea preparacion_academica;
    private javax.swing.JComboBox<String> sexo;
    private javax.swing.JTable tablaconcurso;
    private javax.swing.JTextField telefono;
    // End of variables declaration//GEN-END:variables

    private class GrillaPostulantes extends Thread {

        public void run() {
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            postulanteDAO fiDAO = new postulanteDAO();
            try {
                for (postulante fi : fiDAO.Todos()) {
                    String Datos[] = {String.valueOf(fi.getCodigo()), fi.getNombres().trim() + ", " + fi.getApellidos().trim(), String.valueOf(fi.getCedula()), formatoFecha.format(fi.getFechanacimiento()), fi.getDireccion(), fi.getTelefono()};
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
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                Listar.setEnabled(false);
            }
        }
    }

    private class GrillaConcurso extends Thread {

        Date dFechaProceso = ODate.de_java_a_sql(fechaproceso.getDate());

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloconcurso.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloconcurso.removeRow(0);
            }

            concursoDAO pDAO = new concursoDAO();
            try {
                for (concurso pa : pDAO.todosActivo(dFechaProceso)) {
                    String Datos[] = {formatosinpunto.format(pa.getIdconcurso()), pa.getIdvacancia().getNombrepuesto()};
                    modeloconcurso.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaconcurso.setRowSorter(new TableRowSorter(modeloconcurso));
            int cantFilas = tablaconcurso.getRowCount();
        }
    }
}

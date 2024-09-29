/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.ficha_empleadoDAO;
import DAO.vacacionDAO;
import Modelo.Tablas;
import Modelo.ficha_empleado;
import Modelo.vacacion;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class llamadas_a_concurso extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modeloempleado = new Tablas();
    Tablas modelovacacion = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltroempleado, trsfiltrovacacion;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    int counter = 0;

    public llamadas_a_concurso() {
        initComponents();
        fecha.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    inicio.requestFocus();
                }
            }
        });

        inicio.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    fin.requestFocus();
                }
            }
        });

        fin.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    empleado.requestFocus();
                }
            }
        });
        
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.Salir.setIcon(iconosalir);
        this.SalirCompleto.setIcon(iconosalir);
        this.buscarEmpleado.setIcon(iconobuscar);
        this.Grabar.setIcon(iconograbar);

        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.salario.setVisible(false);
        this.tipo_salario.setVisible(false);
        this.idControl.setVisible(false);
        this.idControl.setText("0");
        this.cargarTitulo();
        this.Inicializar();
        this.TitEmpleado();

        GrillaEmpleado grillaemple = new GrillaEmpleado();
        Thread hiloemple = new Thread(grillaemple);
        hiloemple.start();

        GrillaVacaciones GrillaAus = new GrillaVacaciones();
        Thread HiloGrilla = new Thread(GrillaAus);
        HiloGrilla.start();
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

        detalle_llamadas = new javax.swing.JDialog();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        Socio = new javax.swing.JLabel();
        empleado = new javax.swing.JTextField();
        buscarEmpleado = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        numero = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        fecha = new com.toedter.calendar.JDateChooser();
        tiempo = new javax.swing.JTextField();
        nombreempleado = new javax.swing.JTextField();
        dia = new javax.swing.JRadioButton();
        hora = new javax.swing.JRadioButton();
        salario = new javax.swing.JTextField();
        tipo_salario = new javax.swing.JTextField();
        inicio = new com.toedter.calendar.JDateChooser();
        fin = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BEmpleado = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        comboempleado = new javax.swing.JComboBox();
        jTBuscarEmpleado = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaempleado = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarEmpleado = new javax.swing.JButton();
        SalirEmpleado = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetavacaciones = new org.edisoncor.gui.label.LabelMetric();
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
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        detalle_llamadas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_llamadasFocusGained(evt);
            }
        });
        detalle_llamadas.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_llamadasWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_llamadas.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_llamadasWindowActivated(evt);
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
        Grabar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GrabarKeyPressed(evt);
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

        Socio.setText("Funcionario");

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

        jLabel2.setText("Número");

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

        jLabel12.setText("Importe");

        jLabel14.setText("Tiempo");

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

        tiempo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tiempo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tiempoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tiempoFocusLost(evt);
            }
        });
        tiempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiempoActionPerformed(evt);
            }
        });
        tiempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tiempoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tiempoKeyReleased(evt);
            }
        });

        nombreempleado.setEnabled(false);
        nombreempleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreempleadoKeyPressed(evt);
            }
        });

        dia.setText("Día");
        dia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dia.setName(""); // NOI18N
        dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaActionPerformed(evt);
            }
        });

        hora.setText("Horas");
        hora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horaActionPerformed(evt);
            }
        });

        inicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inicioFocusGained(evt);
            }
        });
        inicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inicioKeyPressed(evt);
            }
        });

        fin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                finFocusGained(evt);
            }
        });
        fin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                finKeyPressed(evt);
            }
        });

        jLabel1.setText("Fecha");

        jLabel4.setText("Desde el");

        jLabel5.setText("Hasta el");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(salario, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tipo_salario, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(101, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(Socio)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nombreempleado))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hora)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tipo_salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Socio))
                    .addComponent(nombreempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dia)
                        .addComponent(hora)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout detalle_llamadasLayout = new javax.swing.GroupLayout(detalle_llamadas.getContentPane());
        detalle_llamadas.getContentPane().setLayout(detalle_llamadasLayout);
        detalle_llamadasLayout.setHorizontalGroup(
            detalle_llamadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_llamadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_llamadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_llamadasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        detalle_llamadasLayout.setVerticalGroup(
            detalle_llamadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_llamadasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(detalle_llamadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grabar)
                    .addComponent(Salir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BEmpleado.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BEmpleado.setTitle("null");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboempleado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboempleado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboempleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboempleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboempleadoActionPerformed(evt);
            }
        });

        jTBuscarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarEmpleado.setText(org.openide.util.NbBundle.getMessage(llamadas_a_concurso.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(comboempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane4.setViewportView(tablaempleado);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarEmpleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarEmpleado.setText(org.openide.util.NbBundle.getMessage(llamadas_a_concurso.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarEmpleadoActionPerformed(evt);
            }
        });

        SalirEmpleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirEmpleado.setText(org.openide.util.NbBundle.getMessage(llamadas_a_concurso.class, "ventas.SalirCliente.text")); // NOI18N
        SalirEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarEmpleado)
                    .addComponent(SalirEmpleado))
                .addContainerGap())
        );

        javax.swing.GroupLayout BEmpleadoLayout = new javax.swing.GroupLayout(BEmpleado.getContentPane());
        BEmpleado.getContentPane().setLayout(BEmpleadoLayout);
        BEmpleadoLayout.setHorizontalGroup(
            BEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BEmpleadoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BEmpleadoLayout.setVerticalGroup(
            BEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BEmpleadoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Llamadas a Concurso");
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

        etiquetavacaciones.setBackground(new java.awt.Color(255, 255, 255));
        etiquetavacaciones.setText("Llamadas a Concurso");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "N°" }));
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
                .addComponent(etiquetavacaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetavacaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(llamadas_a_concurso.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(llamadas_a_concurso.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(llamadas_a_concurso.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

        refrescar.setText("Refrescar");
        refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(refrescar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(35, 35, 35))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(SalirCompleto)
                .addGap(26, 26, 26)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializar() {
        this.fecha.setCalendar(c2);
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
        this.inicio.setCalendar(c2);
        this.fin.setCalendar(c2);
    }

    private void jComboBox1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void CalcularxHora(String cSalario, int nTipoSalario, double nTiempo, int TipoTiempo) {
        double salarioxhora = 0.00;
        cSalario = cSalario.replace(".", "").replace(",", ".");
        // instrucción switch con tipo de datos int
        switch (nTipoSalario) {
            //SI EL SALARIO ES POR HORAS
            case 1:
                salarioxhora = Math.round(Double.valueOf(cSalario));
                break;
            //SI EL SALARIO ES POR DIA         
            case 2:
                salarioxhora = Math.round((Double.valueOf(cSalario) / 8));
                break;
            //SI EL SALARIO ES QUINCENAL         
            case 3:
                salarioxhora = Math.round((Double.valueOf(cSalario) * 2 / 30 / 8));
                break;
            //SI EL SALARIO ES MENSUAL
            case 4:
                salarioxhora = Math.round((Double.valueOf(cSalario) / 30 / 8));
                break;
        }

        if (TipoTiempo == 1) {
            this.importe.setText(formatea.format(Math.round(salarioxhora * nTiempo * 8)));
        } else {
            this.importe.setText(formatea.format(Math.round(salarioxhora * nTiempo)));
        }
    }


    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText()).toUpperCase();
                buscarcadena.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 2;
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

    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void SalirCompletoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        idControl.setText("0");
        this.limpiar();
        detalle_llamadas.setModal(true);
        detalle_llamadas.setSize(500, 340);
        //Establecemos un título para el jDialog
        detalle_llamadas.setTitle("Agregar Vacaciones");
        detalle_llamadas.setLocationRelativeTo(null);
        detalle_llamadas.setVisible(true);
        empleado.requestFocus();

          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.numero.setText("0");
        this.empleado.setText("0");
        this.nombreempleado.setText("");
        this.tiempo.setText("");
        this.importe.setText("");
        this.dia.setSelected(true);
    }
    private void jTable1KeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            Agregar.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Modificar.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            Eliminar.doClick();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            refrescar.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        idControl.setText("2");
        // int nunidad = 0;
        this.limpiar();
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = this.jTable1.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.jTable1.requestFocus();
                return;
            }

            this.numero.setText(this.jTable1.getValueAt(nFila, 0).toString());
            this.numero.setEnabled(false);
            vacacionDAO vacDAO = new vacacionDAO();
            vacacion vac = null;
            try {
                vac = vacDAO.buscarId(Integer.valueOf(this.numero.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (vac != null) {
                fecha.setDate(vac.getFecha());
                inicio.setDate(vac.getInicio());
                fin.setDate(vac.getFin());
                empleado.setText(String.valueOf(vac.getFicha_empleado().getCodigo()));
                nombreempleado.setText(vac.getFicha_empleado().getNombreempleado());
                salario.setText(formatea.format(vac.getFicha_empleado().getSalario()));
                tipo_salario.setText(String.valueOf(vac.getFicha_empleado().getTipo_salario()));
                tiempo.setText(String.valueOf(vac.getHoras()));
                importe.setText(formatea.format(vac.getImporte()));

                if (vac.getUnidmed().equals("Dias")) {
                    dia.setSelected(true);
                    hora.setSelected(false);
                }
                if (vac.getUnidmed().equals("Horas")) {
                    hora.setSelected(true);
                    dia.setSelected(false);
                }
                detalle_llamadas.setModal(true);
                //                        (Ancho,Alto)
                detalle_llamadas.setSize(500, 340);
                //Establecemos un título para el jDialog
                detalle_llamadas.setTitle("Modificar Vacaciones");
                detalle_llamadas.setLocationRelativeTo(null);
                detalle_llamadas.setVisible(true);
                empleado.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "USUARIO NO AUTORIZADO");
            }
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
                vacacionDAO vacDAO = new vacacionDAO();
                try {
                    vacacion vac = vacDAO.buscarId(Integer.valueOf(num));
                    if (vac == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        vacDAO.eliminarvacacion(Integer.valueOf(num));
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "USUARIO NO AUTORIZADO");
        }

        this.refrescar.doClick();
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
        detalle_llamadas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed

        if (this.empleado.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código de Empleado");
            this.empleado.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            // Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            Date Fecha = ODate.de_java_a_sql(fecha.getDate());
            Date Inicio = ODate.de_java_a_sql(inicio.getDate());
            Date Fin = ODate.de_java_a_sql(fin.getDate());

            vacacionDAO grabar = new vacacionDAO();
            vacacion vac = new vacacion();

            ficha_empleadoDAO fichaDAO = new ficha_empleadoDAO();
            ficha_empleado ficha = null;
            try {
                ficha = fichaDAO.buscarId(Integer.valueOf(this.empleado.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            //CAPTURAMOS LOS DATOS DE LA CABECERA
            vac.setNumero(Double.valueOf(this.numero.getText()));
            vac.setFecha(Fecha);
            vac.setInicio(Inicio);
            vac.setFin(Fin);
            vac.setFicha_empleado(ficha);
            vac.setHoras(Integer.valueOf(tiempo.getText()));

            if (dia.isSelected()) {
                vac.setUnidmed("Días");
            }

            if (hora.isSelected()) {
                vac.setUnidmed("Horas");
            }

            String cImporte = importe.getText();
            cImporte = cImporte.replace(".", "").replace(",", ".");
            BigDecimal nImporte = new BigDecimal(cImporte);
            vac.setImporte(nImporte);

            //EMPEZAMOS A CAPTURAR DATOS NUMERICOS
            if (Integer.valueOf(this.numero.getText()) == 0) {
                try {
                    grabar.insertarvacacion(vac);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            } else {
                //Actualizar 
                try {
                    vac.setNumero(Double.valueOf(this.numero.getText()));
                    grabar.actualizarvacacion(vac);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            }
            detalle_llamadas.setVisible(false);
            this.detalle_llamadas.setModal(false);
            this.refrescar.doClick();
        }
    }//GEN-LAST:event_GrabarActionPerformed

    private void detalle_llamadasFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_llamadasFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_llamadasFocusGained

    private void detalle_llamadasWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_llamadasWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_llamadasWindowGainedFocus

    private void detalle_llamadasWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_llamadasWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_llamadasWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void SalirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirEmpleadoActionPerformed
        this.BEmpleado.setModal(true);
        this.BEmpleado.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirEmpleadoActionPerformed

    private void AceptarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarEmpleadoActionPerformed
        int nFila = this.tablaempleado.getSelectedRow();
        this.empleado.setText(this.tablaempleado.getValueAt(nFila, 0).toString());
        this.nombreempleado.setText(this.tablaempleado.getValueAt(nFila, 1).toString());
        this.tipo_salario.setText(this.tablaempleado.getValueAt(nFila, 2).toString());
        this.salario.setText(this.tablaempleado.getValueAt(nFila, 3).toString());
        this.BEmpleado.setVisible(false);
        this.jTBuscarEmpleado.setText("");
        this.tiempo.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarEmpleadoActionPerformed

    private void tablaempleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaempleadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarEmpleado.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaempleadoKeyPressed

    private void tablaempleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaempleadoMouseClicked
        this.AceptarEmpleado.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaempleadoMouseClicked

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

    private void jTBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarEmpleadoActionPerformed

    private void comboempleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboempleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboempleadoActionPerformed


    private void horaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horaActionPerformed
        if (hora.isSelected()) {
            dia.setSelected(false);
            this.CalcularxHora(salario.getText().toString(), Integer.valueOf(tipo_salario.getText()), Double.valueOf(tiempo.getText()), 2);
        } else {
            dia.setSelected(true);
            this.CalcularxHora(salario.getText().toString(), Integer.valueOf(tipo_salario.getText()), Double.valueOf(tiempo.getText()), 1);
        }
    }//GEN-LAST:event_horaActionPerformed

    private void diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaActionPerformed
        if (dia.isSelected()) {
            hora.setSelected(false);
            this.CalcularxHora(salario.getText().toString(), Integer.valueOf(tipo_salario.getText()), Double.valueOf(tiempo.getText()), 1);
        } else {
            hora.setSelected(true);
            this.CalcularxHora(salario.getText().toString(), Integer.valueOf(tipo_salario.getText()), Double.valueOf(tiempo.getText()), 2);
        }
    }//GEN-LAST:event_diaActionPerformed

    private void nombreempleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreempleadoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreempleadoKeyPressed

    private void tiempoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tiempoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tiempoKeyReleased

    private void tiempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tiempoKeyPressed
         if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importe.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.empleado.requestFocus();
        }   // TODO add your handling code 

    }//GEN-LAST:event_tiempoKeyPressed

    private void tiempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiempoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tiempoActionPerformed

    private void tiempoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tiempoFocusLost
        if (dia.isSelected()) {
            this.CalcularxHora(salario.getText().toString(), Integer.valueOf(tipo_salario.getText()), Double.valueOf(tiempo.getText()), 1);
        }
        if (hora.isSelected()) {
            this.CalcularxHora(salario.getText().toString(), Integer.valueOf(tipo_salario.getText()), Double.valueOf(tiempo.getText()), 2);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tiempoFocusLost

    private void tiempoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tiempoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tiempoFocusGained

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.inicio.requestFocus();
        }
    }//GEN-LAST:event_fechaKeyPressed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void importeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.Grabar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tiempo.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_importeKeyPressed

    private void importeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_importeFocusGained

    private void numeroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyReleased

    private void numeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyPressed

    private void numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroActionPerformed

    }//GEN-LAST:event_numeroActionPerformed

    private void numeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numeroFocusGained
        numero.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroFocusGained

    private void buscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarEmpleadoActionPerformed
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
                tiempo.requestFocus();
                BEmpleado.setModal(false);
            } else {
                nombreempleado.setText(ficha.getNombreempleado());
                salario.setText(formatea.format(ficha.getSalario()));
                tipo_salario.setText(String.valueOf(ficha.getTipo_salario()));
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        tiempo.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarEmpleadoActionPerformed

    private void empleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empleadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fin.requestFocus();
        }   // TODO add your handling code 

        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoKeyPressed

    private void empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empleadoActionPerformed
        this.buscarEmpleado.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoActionPerformed

    private void empleadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_empleadoFocusGained
        Date fechaIn = ODate.de_java_a_sql(inicio.getDate());
        Date fechaFi = ODate.de_java_a_sql(fin.getDate());
        int dias = (int) ((fechaFi.getTime() - fechaIn.getTime()) / 86400000);
        this.tiempo.setText(String.valueOf(dias));

        empleado.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoFocusGained

    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaVacaciones GrillaAus = new GrillaVacaciones();
        Thread HiloGrilla = new Thread(GrillaAus);
        HiloGrilla.start();
    }//GEN-LAST:event_refrescarActionPerformed

    private void inicioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inicioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_inicioFocusGained

    private void inicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inicioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.fin.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_inicioKeyPressed

    private void finFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_finFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_finFocusGained

    private void finKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_finKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.empleado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.inicio.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_finKeyPressed

    private void GrabarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GrabarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importe.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarKeyPressed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtroempleado(int nNumeroColumna) {
        trsfiltroempleado.setRowFilter(RowFilter.regexFilter(this.jTBuscarEmpleado.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Número");
        modelo.addColumn("Fecha");
        modelo.addColumn("Vacancia");
        modelo.addColumn("Perfil de Cargo");

        int[] anchos = {100, 100, 300, 150};
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
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
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
                new llamadas_a_concurso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarEmpleado;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BEmpleado;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirEmpleado;
    private javax.swing.JLabel Socio;
    private javax.swing.JButton buscarEmpleado;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JComboBox comboempleado;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JDialog detalle_llamadas;
    private javax.swing.JRadioButton dia;
    private javax.swing.JTextField empleado;
    private org.edisoncor.gui.label.LabelMetric etiquetavacaciones;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fin;
    private javax.swing.JRadioButton hora;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField importe;
    private com.toedter.calendar.JDateChooser inicio;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTBuscarEmpleado;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nombreempleado;
    private javax.swing.JTextField numero;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField salario;
    private javax.swing.JTable tablaempleado;
    private javax.swing.JTextField tiempo;
    private javax.swing.JTextField tipo_salario;
    // End of variables declaration//GEN-END:variables

    private class GrillaVacaciones extends Thread {

        public void run() {
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            vacacionDAO DAO = new vacacionDAO();
            try {
                for (vacacion vac : DAO.Todos(FechaI, FechaF)) {
                    String Datos[] = {String.valueOf(vac.getNumero()), formatoFecha.format(vac.getFecha()), vac.getFicha_empleado().getNombreempleado(), String.valueOf(vac.getHoras()), formatea.format(vac.getImporte())};
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

    private class GrillaEmpleado extends Thread {

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
}

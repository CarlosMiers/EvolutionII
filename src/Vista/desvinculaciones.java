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
import DAO.configuracionDAO;
import DAO.ficha_empleadoDAO;
import DAO.desvinculacionDAO;
import Modelo.Tablas;
import Modelo.configuracion;
import Modelo.ficha_empleado;
import Modelo.desvinculacion;
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
public class desvinculaciones extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modeloempleado = new Tablas();
    Tablas modelodesvinculacion = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltroempleado, trsfiltrodesvinculacion;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("###");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    double nAporteIps = 0;

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

    public desvinculaciones() {
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

        this.tabla.setShowGrid(false);
        this.tabla.setOpaque(true);
        this.tabla.setBackground(new Color(204, 204, 255));
        this.tabla.setForeground(Color.BLACK);
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

        GrillaSalidas GrillaAus = new GrillaSalidas();
        Thread HiloGrilla = new Thread(GrillaAus);
        HiloGrilla.start();

        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = new configuracion();
        config = configDAO.consultar();
        nAporteIps = config.getPorcentaje_ips();
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

        detalle_desvinculacion = new javax.swing.JDialog();
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
        salario_ordinario = new javax.swing.JFormattedTextField();
        fecha = new com.toedter.calendar.JDateChooser();
        nombreempleado = new javax.swing.JTextField();
        salario = new javax.swing.JTextField();
        tipo_salario = new javax.swing.JTextField();
        inicio = new com.toedter.calendar.JDateChooser();
        fin = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        diaspreaviso = new javax.swing.JTextField();
        vacaciones = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        aguinaldo = new javax.swing.JFormattedTextField();
        preaviso = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        antiguedad = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        indemnizacion = new javax.swing.JFormattedTextField();
        ips = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        tipo_desvinculacion = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
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
        tabla = new javax.swing.JTable();

        detalle_desvinculacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_desvinculacionFocusGained(evt);
            }
        });
        detalle_desvinculacion.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_desvinculacionWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_desvinculacion.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_desvinculacionWindowActivated(evt);
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

        jLabel12.setText("Salario Ordinario");

        jLabel14.setText("Antiguedad");

        salario_ordinario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        salario_ordinario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        salario_ordinario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                salario_ordinarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                salario_ordinarioFocusLost(evt);
            }
        });
        salario_ordinario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                salario_ordinarioKeyPressed(evt);
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

        nombreempleado.setEnabled(false);
        nombreempleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreempleadoKeyPressed(evt);
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

        jLabel4.setText("Ingresó");

        jLabel5.setText("Hasta el");

        jLabel15.setText("Días Preaviso");

        diaspreaviso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        diaspreaviso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                diaspreavisoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                diaspreavisoFocusLost(evt);
            }
        });
        diaspreaviso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaspreavisoActionPerformed(evt);
            }
        });
        diaspreaviso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diaspreavisoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                diaspreavisoKeyReleased(evt);
            }
        });

        vacaciones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        vacaciones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vacaciones.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vacacionesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                vacacionesFocusLost(evt);
            }
        });
        vacaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vacacionesKeyPressed(evt);
            }
        });

        jLabel13.setText("Vacaciones");

        jLabel16.setText("Aguinaldo");

        aguinaldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        aguinaldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aguinaldo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                aguinaldoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                aguinaldoFocusLost(evt);
            }
        });
        aguinaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                aguinaldoKeyPressed(evt);
            }
        });

        preaviso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        preaviso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        preaviso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                preavisoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                preavisoFocusLost(evt);
            }
        });
        preaviso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                preavisoKeyPressed(evt);
            }
        });

        jLabel17.setText("PreaAviso");

        antiguedad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        antiguedad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        antiguedad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                antiguedadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                antiguedadFocusLost(evt);
            }
        });
        antiguedad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                antiguedadKeyPressed(evt);
            }
        });

        jLabel18.setText("Indemnización");

        indemnizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        indemnizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        indemnizacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                indemnizacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                indemnizacionFocusLost(evt);
            }
        });
        indemnizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                indemnizacionKeyPressed(evt);
            }
        });

        ips.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        ips.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ips.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ipsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ipsFocusLost(evt);
            }
        });
        ips.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ipsKeyPressed(evt);
            }
        });

        jLabel19.setText("IPS");

        tipo_desvinculacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Finalización Contrato", "Renuncia", "Despido Justificado", "Despido Injustificado", "Jubilación" }));
        tipo_desvinculacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipo_desvinculacionKeyPressed(evt);
            }
        });

        jLabel6.setText("Tipo Desvinculación");

        jLabel7.setText("Años/Meses");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)))
                .addGap(413, 413, 413))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(salario, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tipo_salario, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Socio, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel19))
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addGap(21, 21, 21)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(antiguedad, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(diaspreaviso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(empleado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nombreempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tipo_desvinculacion, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ips, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                .addComponent(preaviso, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(aguinaldo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                .addComponent(vacaciones, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(salario_ordinario, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(indemnizacion, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipo_desvinculacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(empleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Socio))
                            .addComponent(nombreempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(antiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(diaspreaviso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salario_ordinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vacaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aguinaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(preaviso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(indemnizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout detalle_desvinculacionLayout = new javax.swing.GroupLayout(detalle_desvinculacion.getContentPane());
        detalle_desvinculacion.getContentPane().setLayout(detalle_desvinculacionLayout);
        detalle_desvinculacionLayout.setHorizontalGroup(
            detalle_desvinculacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_desvinculacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_desvinculacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_desvinculacionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        detalle_desvinculacionLayout.setVerticalGroup(
            detalle_desvinculacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_desvinculacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(detalle_desvinculacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grabar)
                    .addComponent(Salir))
                .addGap(9, 9, 9))
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
        jTBuscarEmpleado.setText(org.openide.util.NbBundle.getMessage(desvinculaciones.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarEmpleado.setText(org.openide.util.NbBundle.getMessage(desvinculaciones.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarEmpleadoActionPerformed(evt);
            }
        });

        SalirEmpleado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirEmpleado.setText(org.openide.util.NbBundle.getMessage(desvinculaciones.class, "ventas.SalirCliente.text")); // NOI18N
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
        setTitle("Desvinculaciones");
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
        etiquetavacaciones.setText("Desvinculaciones");

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
                .addGap(43, 43, 43)
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(desvinculaciones.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(desvinculaciones.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(desvinculaciones.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

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

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tabla.setModel(modelo);
        tabla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabla.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tabla.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tabla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablaFocusLost(evt);
            }
        });
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        tabla.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaPropertyChange(evt);
            }
        });
        tabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

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
            this.salario_ordinario.setText(formatea.format(Math.round(salarioxhora * nTiempo * 8)));
        } else {
            this.salario_ordinario.setText(formatea.format(Math.round(salarioxhora * nTiempo)));
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
        trsfiltro = new TableRowSorter(tabla.getModel());
        tabla.setRowSorter(trsfiltro);

    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void SalirCompletoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        idControl.setText("0");
        this.limpiar();
        detalle_desvinculacion.setModal(true);
        detalle_desvinculacion.setSize(560, 520);
        //Establecemos un título para el jDialog
        detalle_desvinculacion.setTitle("Agregar Desvinculaciones");
        detalle_desvinculacion.setLocationRelativeTo(null);
        detalle_desvinculacion.setVisible(true);
        empleado.requestFocus();

          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.numero.setText("0");
        this.empleado.setText("0");
        this.fecha.setCalendar(c2);
        this.inicio.setCalendar(c2);
        this.fin.setCalendar(c2);
        this.nombreempleado.setText("");
        this.antiguedad.setText("0");
        this.diaspreaviso.setText("0");
        this.salario_ordinario.setText("0");
        this.indemnizacion.setText("0");
        this.ips.setText("0");
        this.preaviso.setText("0");
        this.aguinaldo.setText("0");
        this.vacaciones.setText("0");
        this.tipo_desvinculacion.setSelectedIndex(0);

    }
    private void tablaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablaKeyPressed
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
    }//GEN-LAST:event_tablaKeyPressed

    private void tablaMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        int nFila = this.tabla.getSelectedRow();
        this.idControl.setText(this.tabla.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaMouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        idControl.setText("2");
        // int nunidad = 0;
        this.limpiar();
        int nnn = 1;
        //if (Integer.valueOf(Config.cNivelUsuario) == 1) {
        if (nnn == 1) {
            int nFila = this.tabla.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tabla.requestFocus();
                return;
            }

            this.numero.setText(this.tabla.getValueAt(nFila, 0).toString());
            this.numero.setEnabled(false);
            desvinculacionDAO vacDAO = new desvinculacionDAO();
            desvinculacion vac = null;
            try {
                vac = vacDAO.buscarId(Integer.valueOf(this.numero.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (vac != null) {
                fecha.setDate(vac.getFecha());
                inicio.setDate(vac.getInicio());
                fin.setDate(vac.getFin());
                empleado.setText(String.valueOf(vac.getFuncionario().getCodigo()));
                nombreempleado.setText(vac.getFuncionario().getNombreempleado());
                tipo_salario.setText(String.valueOf(vac.getFuncionario().getTipo_salario()));
                antiguedad.setText(formatea.format(vac.getAntiguedad()));
                diaspreaviso.setText(formatea.format(vac.getDias_preaviso()));
                tipo_desvinculacion.setSelectedIndex(vac.getTipo_desvinculacion() - 1);
                salario_ordinario.setText(formatea.format(vac.getSalario_ordinario()));
                vacaciones.setText(formatea.format(vac.getVacaciones()));
                indemnizacion.setText(formatea.format(vac.getIndemnizacion()));
                preaviso.setText(formatea.format(vac.getPreaviso()));
                ips.setText(formatea.format(vac.getIps()));
                aguinaldo.setText(formatea.format(vac.getAguinaldo()));

                tipo_desvinculacion.setSelectedIndex(vac.getTipo_desvinculacion() - 1);

                detalle_desvinculacion.setModal(true);
                //                        (Ancho,Alto)
                detalle_desvinculacion.setSize(560, 520);
                //Establecemos un título para el jDialog
                detalle_desvinculacion.setTitle("Modificar Desvinculaciones");
                detalle_desvinculacion.setLocationRelativeTo(null);
                detalle_desvinculacion.setVisible(true);
                empleado.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "USUARIO NO AUTORIZADO");
            }
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void tablaFocusGained(FocusEvent evt) {//GEN-FIRST:event_tablaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaFocusGained

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
        int nFila = tabla.getSelectedRow();
        String num = tabla.getValueAt(nFila, 0).toString();

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                desvinculacionDAO vacDAO = new desvinculacionDAO();
                ficha_empleadoDAO fichaDAO = new ficha_empleadoDAO();
            
                try {
                    desvinculacion vac = vacDAO.buscarId(Integer.valueOf(num));
                    if (vac == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        vacDAO.eliminardesvinculacion(Integer.valueOf(num));
                        fichaDAO.ActivarFicha(vac.getFuncionario().getCodigo());
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

    private void tablaFocusLost(FocusEvent evt) {//GEN-FIRST:event_tablaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaFocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_desvinculacion.setVisible(false);
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

            desvinculacionDAO grabar = new desvinculacionDAO();
            desvinculacion vac = new desvinculacion();

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
            vac.setFuncionario(ficha);
            vac.setIdsucursal(ficha.getGiraduria().getCodigo());
            vac.setDias_preaviso(Integer.valueOf(diaspreaviso.getText()));
            vac.setTipo_desvinculacion(tipo_desvinculacion.getSelectedIndex() + 1);

            String cAntiguedad = antiguedad.getText();
            cAntiguedad = cAntiguedad.replace(".", "").replace(",", ".");
            vac.setAntiguedad(Double.valueOf(cAntiguedad));

            String cSalario = salario_ordinario.getText();
            cSalario = cSalario.replace(".", "").replace(",", ".");
            vac.setSalario_ordinario(Double.valueOf(cSalario));

            String cVacaciones = vacaciones.getText();
            cVacaciones = cVacaciones.replace(".", "").replace(",", ".");
            vac.setVacaciones(Double.valueOf(cVacaciones));

            String cAgui = aguinaldo.getText();
            cAgui = cAgui.replace(".", "").replace(",", ".");
            vac.setAguinaldo(Double.valueOf(cAgui));

            String cInd = indemnizacion.getText();
            cInd = cInd.replace(".", "").replace(",", ".");
            vac.setIndemnizacion(Double.valueOf(cInd));

            String cPre = preaviso.getText();
            cPre = cPre.replace(".", "").replace(",", ".");
            vac.setPreaviso(Double.valueOf(cPre));

            String cIps = ips.getText();
            cIps = cIps.replace(".", "").replace(",", ".");
            vac.setIps(Double.valueOf(cIps));

            //EMPEZAMOS A CAPTURAR DATOS NUMERICOS
            if (Integer.valueOf(this.numero.getText()) == 0) {
                try {
                    fichaDAO.DesactivarFicha(Integer.valueOf(this.empleado.getText()));
                    grabar.insertardesvinculacion(vac);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            } else {
                //Actualizar 
                try {
                    fichaDAO.DesactivarFicha(Integer.valueOf(this.empleado.getText()));
                    vac.setNumero(Double.valueOf(this.numero.getText()));
                    grabar.actualizardesvinculacion(vac);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            }
            detalle_desvinculacion.setVisible(false);
            this.detalle_desvinculacion.setModal(false);
            this.refrescar.doClick();
        }
    }//GEN-LAST:event_GrabarActionPerformed

    private void CalcularIps() {
        String cSalario = salario_ordinario.getText();
        cSalario = cSalario.replace(".", "").replace(",", ".");

        String cVacaciones = vacaciones.getText();
        cVacaciones = cVacaciones.replace(".", "").replace(",", ".");

        String cAgui = aguinaldo.getText();
        cAgui = cAgui.replace(".", "").replace(",", ".");

        String cInd = indemnizacion.getText();
        cInd = cInd.replace(".", "").replace(",", ".");

        String cPre = preaviso.getText();
        cPre = cPre.replace(".", "").replace(",", ".");

        double nTotal = Double.valueOf(cSalario) + Double.valueOf(cVacaciones) + Double.valueOf(cInd) + Double.valueOf(cPre);
        double nTotalAporte = Math.round(nTotal * nAporteIps / 100);
        ips.setText(formatea.format(nTotalAporte));
    }

    private void detalle_desvinculacionFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_desvinculacionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_desvinculacionFocusGained

    private void detalle_desvinculacionWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_desvinculacionWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_desvinculacionWindowGainedFocus

    private void detalle_desvinculacionWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_desvinculacionWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_desvinculacionWindowActivated

    private void tablaPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_tablaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaPropertyChange

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

        this.antiguedad.requestFocus();

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


    private void nombreempleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreempleadoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreempleadoKeyPressed

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.inicio.requestFocus();
        }
    }//GEN-LAST:event_fechaKeyPressed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void salario_ordinarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salario_ordinarioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.vacaciones.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.diaspreaviso.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_salario_ordinarioKeyPressed

    private void salario_ordinarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_salario_ordinarioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_salario_ordinarioFocusGained

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
                antiguedad.requestFocus();
                BEmpleado.setModal(false);
            } else {
                nombreempleado.setText(ficha.getNombreempleado());
                salario.setText(formatea.format(ficha.getSalario()));
                tipo_salario.setText(String.valueOf(ficha.getTipo_salario()));
                this.inicio.setDate(ficha.getFecha_ingreso());

                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        antiguedad.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarEmpleadoActionPerformed

    private void empleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empleadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipo_desvinculacion.requestFocus();
        }   // TODO add your handling code 

        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoKeyPressed

    private void empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empleadoActionPerformed
        this.buscarEmpleado.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoActionPerformed

    private void empleadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_empleadoFocusGained
        empleado.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_empleadoFocusGained


    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaSalidas GrillaAus = new GrillaSalidas();
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
            this.salario_ordinario.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarKeyPressed

    private void diaspreavisoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_diaspreavisoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_diaspreavisoFocusGained

    private void diaspreavisoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_diaspreavisoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_diaspreavisoFocusLost

    private void diaspreavisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaspreavisoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diaspreavisoActionPerformed

    private void diaspreavisoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diaspreavisoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.salario_ordinario.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.antiguedad.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_diaspreavisoKeyPressed

    private void diaspreavisoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diaspreavisoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_diaspreavisoKeyReleased

    private void vacacionesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vacacionesFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_vacacionesFocusGained

    private void vacacionesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vacacionesKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.aguinaldo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.salario_ordinario.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_vacacionesKeyPressed

    private void aguinaldoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_aguinaldoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_aguinaldoFocusGained

    private void aguinaldoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_aguinaldoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.preaviso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.vacaciones.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_aguinaldoKeyPressed

    private void preavisoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preavisoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_preavisoFocusGained

    private void preavisoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preavisoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.indemnizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.aguinaldo.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_preavisoKeyPressed

    private void antiguedadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_antiguedadFocusGained
        ficha_empleadoDAO fichaDAO = new ficha_empleadoDAO();
        ficha_empleado ficha = null;
        try {
            ficha = fichaDAO.buscarId(Integer.valueOf(this.empleado.getText()));
            if (ficha.getCodigo() != 0) {
                this.inicio.setDate(ficha.getFecha_ingreso());
                Date fechaIn = ODate.de_java_a_sql(inicio.getDate());
                Date fechaFi = ODate.de_java_a_sql(fin.getDate());
                int dias = (int) ((fechaFi.getTime() - fechaIn.getTime()) / 86400000);
                double anual = dias / 365;
                this.antiguedad.setText(formatea.format(anual));
            }

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_antiguedadFocusGained

    private void antiguedadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_antiguedadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.diaspreaviso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.empleado.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_antiguedadKeyPressed

    private void indemnizacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_indemnizacionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_indemnizacionFocusGained

    private void indemnizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_indemnizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ips.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.preaviso.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_indemnizacionKeyPressed

    private void ipsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ipsFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_ipsFocusGained

    private void ipsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ipsKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.Grabar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.indemnizacion.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_ipsKeyPressed

    private void antiguedadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_antiguedadFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_antiguedadFocusLost

    private void tipo_desvinculacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipo_desvinculacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.empleado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fin.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_tipo_desvinculacionKeyPressed

    private void salario_ordinarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_salario_ordinarioFocusLost
        this.CalcularIps();
        // TODO add your handling code here:
    }//GEN-LAST:event_salario_ordinarioFocusLost

    private void vacacionesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vacacionesFocusLost
        this.CalcularIps();
        // TODO add your handling code here:
    }//GEN-LAST:event_vacacionesFocusLost

    private void aguinaldoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_aguinaldoFocusLost
        this.CalcularIps();
        // TODO add your handling code here:
    }//GEN-LAST:event_aguinaldoFocusLost

    private void preavisoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_preavisoFocusLost
        this.CalcularIps();
        // TODO add your handling code here:
    }//GEN-LAST:event_preavisoFocusLost

    private void indemnizacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_indemnizacionFocusLost
        this.CalcularIps();
        // TODO add your handling code here:
    }//GEN-LAST:event_indemnizacionFocusLost

    private void ipsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ipsFocusLost
        this.CalcularIps();
        // TODO add your handling code here:
    }//GEN-LAST:event_ipsFocusLost

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtroempleado(int nNumeroColumna) {
        trsfiltroempleado.setRowFilter(RowFilter.regexFilter(this.jTBuscarEmpleado.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Numero");
        modelo.addColumn("Fecha");
        modelo.addColumn("Inicio");
        modelo.addColumn("Fin");
        modelo.addColumn("Nombre de Funcionario");
        modelo.addColumn("Antiguedad");
        modelo.addColumn("Salario");

        int[] anchos = {100, 100, 100, 100, 300, 150, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabla.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tabla.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();

        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.tabla.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tabla.getColumnModel().getColumn(1).setCellRenderer(AlinearCentro);
        this.tabla.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.tabla.getColumnModel().getColumn(3).setCellRenderer(AlinearCentro);
        this.tabla.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
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
                new desvinculaciones().setVisible(true);
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
    private javax.swing.JFormattedTextField aguinaldo;
    private javax.swing.JFormattedTextField antiguedad;
    private javax.swing.JButton buscarEmpleado;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JComboBox comboempleado;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JDialog detalle_desvinculacion;
    private javax.swing.JTextField diaspreaviso;
    private javax.swing.JTextField empleado;
    private org.edisoncor.gui.label.LabelMetric etiquetavacaciones;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fin;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField indemnizacion;
    private com.toedter.calendar.JDateChooser inicio;
    private javax.swing.JFormattedTextField ips;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTBuscarEmpleado;
    private javax.swing.JTextField nombreempleado;
    private javax.swing.JTextField numero;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JFormattedTextField preaviso;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField salario;
    private javax.swing.JFormattedTextField salario_ordinario;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tablaempleado;
    private javax.swing.JComboBox<String> tipo_desvinculacion;
    private javax.swing.JTextField tipo_salario;
    private javax.swing.JFormattedTextField vacaciones;
    // End of variables declaration//GEN-END:variables

    private class GrillaSalidas extends Thread {

        public void run() {
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            desvinculacionDAO DAO = new desvinculacionDAO();
            try {
                for (desvinculacion vac : DAO.Todos(FechaI, FechaF)) {
                    String Datos[] = {formatosinpunto.format(vac.getNumero()), formatoFecha.format(vac.getFecha()), formatoFecha.format(vac.getInicio()), formatoFecha.format(vac.getFin()), vac.getFuncionario().getNombreempleado(), formatea.format(vac.getAntiguedad()), formatea.format(vac.getSalario_ordinario())};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tabla.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = tabla.getRowCount();
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
                int nFila = tabla.getSelectedRow();
                String num = tabla.getValueAt(nFila, 0).toString();
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cNumero", num);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/liquidacion_haberes.jasper");
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

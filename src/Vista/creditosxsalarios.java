/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/
package Vista;

import Clases.Config;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.concepto_salarioDAO;
import DAO.credito_salarioDAO;
import DAO.ficha_empleadoDAO;
import Modelo.Tablas;
import Modelo.concepto_salario;
import Modelo.credito_salario;
import Modelo.ficha_empleado;
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
public class creditosxsalarios extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modeloconcepto = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocasa = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modeloficha = new Tablas();
    Tablas modelocomprobante = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltroconcepto, trsfiltroficha;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat sinformato = new DecimalFormat("###,###");
    DecimalFormat formatosinpunto = new DecimalFormat("###");

    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String cTasa, referencia = null;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    int counter = 0;

    public creditosxsalarios() {
        initComponents();
        fechaemision.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    concepto.requestFocus();
                }
            }
        });
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.Salir.setIcon(iconosalir);
        this.BuscarConcepto.setIcon(iconobuscar);
        this.BuscarFuncionario.setIcon(iconobuscar);

        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setVisible(false);
        this.fechagrabado.setVisible(false);
        this.idControl.setText("0");
        this.Inicializar();
        this.cargarTitulo();
        this.TitFuncionario();
        this.TitConcepto();

        GrillaConcepto grillacs = new GrillaConcepto();
        Thread hilocs = new Thread(grillacs);
        hilocs.start();

        GrillaFicha grillafi = new GrillaFicha();
        Thread hilofi = new Thread(grillafi);
        hilofi.start();

        GrillaCredito GrillaCR = new GrillaCredito();
        Thread HiloGrilla = new Thread(GrillaCR);
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

        detalle_creditos = new javax.swing.JDialog();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        fechaemision = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        fechagrabado = new com.toedter.calendar.JDateChooser();
        concepto = new javax.swing.JTextField();
        BuscarConcepto = new javax.swing.JButton();
        nombreconcepto = new javax.swing.JTextField();
        barrioetiqueta = new javax.swing.JLabel();
        funcionario = new javax.swing.JTextField();
        BuscarFuncionario = new javax.swing.JButton();
        idcontrol = new javax.swing.JTextField();
        direccionfuncionario = new javax.swing.JTextField();
        tipoconcepto = new javax.swing.JTextField();
        nombrefuncionario = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        barrioetiqueta1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        aprobarcredito = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        grabaroc = new javax.swing.JButton();
        saliropcion = new javax.swing.JButton();
        numeroc = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        fechac = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        nombreclientec = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        importec = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        descuentoc = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        neto = new javax.swing.JFormattedTextField();
        BConcepto = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        comboconcepto = new javax.swing.JComboBox();
        jTBuscarConcepto = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaconcepto = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarConcepto = new javax.swing.JButton();
        SalirConcepto = new javax.swing.JButton();
        BFuncionario = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        combofuncionario = new javax.swing.JComboBox();
        jTBuscarFicha = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaficha = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        AceptarFuncionario = new javax.swing.JButton();
        SalirFuncionario = new javax.swing.JButton();
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
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        detalle_creditos.setTitle("Actualizar Contrato");
        detalle_creditos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_creditosFocusGained(evt);
            }
        });
        detalle_creditos.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_creditosWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_creditos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_creditosWindowActivated(evt);
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

        jLabel2.setText("Fecha Emisión");

        jLabel11.setText("Importe");

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

        fechagrabado.setEnabled(false);

        concepto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        concepto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                conceptoFocusGained(evt);
            }
        });
        concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conceptoActionPerformed(evt);
            }
        });
        concepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                conceptoKeyPressed(evt);
            }
        });

        BuscarConcepto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarConceptoActionPerformed(evt);
            }
        });

        nombreconcepto.setEnabled(false);
        nombreconcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreconceptoKeyPressed(evt);
            }
        });

        barrioetiqueta.setText("Funcionario");

        funcionario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        funcionario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                funcionarioFocusGained(evt);
            }
        });
        funcionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcionarioActionPerformed(evt);
            }
        });
        funcionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                funcionarioKeyPressed(evt);
            }
        });

        BuscarFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarFuncionarioActionPerformed(evt);
            }
        });

        idcontrol.setEnabled(false);
        idcontrol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idcontrolKeyPressed(evt);
            }
        });

        direccionfuncionario.setEnabled(false);
        direccionfuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionfuncionarioKeyPressed(evt);
            }
        });

        tipoconcepto.setEnabled(false);
        tipoconcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipoconceptoKeyPressed(evt);
            }
        });

        nombrefuncionario.setEnabled(false);
        nombrefuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrefuncionarioKeyPressed(evt);
            }
        });

        jLabel14.setText("Número");

        jLabel13.setText("Concepto");

        barrioetiqueta1.setText("Dirección");

        jLabel15.setText("Tipo");

        observacion.setColumns(20);
        observacion.setRows(5);
        observacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                observacionKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(observacion);

        jLabel1.setText("Observaciones");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fechagrabado, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel11)
                        .addComponent(barrioetiqueta)
                        .addComponent(barrioetiqueta1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel13)
                        .addComponent(jLabel15)
                        .addComponent(jLabel14))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(idcontrol, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(direccionfuncionario, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BuscarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nombrefuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tipoconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idcontrol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(barrioetiqueta))
                            .addComponent(nombrefuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(direccionfuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barrioetiqueta1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(fechaemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nombreconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13)))
                                    .addComponent(BuscarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tipoconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addComponent(jLabel2)))
                    .addComponent(fechagrabado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout detalle_creditosLayout = new javax.swing.GroupLayout(detalle_creditos.getContentPane());
        detalle_creditos.getContentPane().setLayout(detalle_creditosLayout);
        detalle_creditosLayout.setHorizontalGroup(
            detalle_creditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_creditosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_creditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detalle_creditosLayout.createSequentialGroup()
                        .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        detalle_creditosLayout.setVerticalGroup(
            detalle_creditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_creditosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(detalle_creditosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grabar)
                    .addComponent(Salir))
                .addGap(4, 4, 4))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("N° Orden Crédito");

        grabaroc.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabaroc.setText("Aceptar");
        grabaroc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarocActionPerformed(evt);
            }
        });

        saliropcion.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        saliropcion.setText("Salir");
        saliropcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saliropcionActionPerformed(evt);
            }
        });

        numeroc.setEditable(false);
        numeroc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel17.setText("Fecha");

        fechac.setBackground(new java.awt.Color(255, 255, 255));
        fechac.setForeground(new java.awt.Color(255, 255, 255));
        fechac.setEnabled(false);
        fechac.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel18.setText("Nombre del Socio");

        nombreclientec.setEditable(false);
        nombreclientec.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel20.setText("Importe Solicitado");

        importec.setEditable(false);
        importec.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel22.setText("Descuentos");

        descuentoc.setEditable(false);
        descuentoc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel23.setText("Monto a Entregar");

        neto.setEditable(false);
        neto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addGap(58, 58, 58)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreclientec)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(descuentoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                        .addComponent(importec, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(numeroc, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fechac, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                                .addGap(0, 146, Short.MAX_VALUE)))
                        .addGap(88, 88, 88))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(neto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(grabaroc)
                .addGap(18, 18, 18)
                .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(numeroc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(fechac, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreclientec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(descuentoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grabaroc, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout aprobarcreditoLayout = new javax.swing.GroupLayout(aprobarcredito.getContentPane());
        aprobarcredito.getContentPane().setLayout(aprobarcreditoLayout);
        aprobarcreditoLayout.setHorizontalGroup(
            aprobarcreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        aprobarcreditoLayout.setVerticalGroup(
            aprobarcreditoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        BConcepto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BConcepto.setTitle("null");

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboconcepto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboconcepto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboconcepto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboconcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboconceptoActionPerformed(evt);
            }
        });

        jTBuscarConcepto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarConcepto.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarConceptoActionPerformed(evt);
            }
        });
        jTBuscarConcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarConceptoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(comboconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboconcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaconcepto.setModel(modeloconcepto);
        tablaconcepto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaconceptoMouseClicked(evt);
            }
        });
        tablaconcepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaconceptoKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablaconcepto);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarConcepto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarConcepto.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarConcepto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarConceptoActionPerformed(evt);
            }
        });

        SalirConcepto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirConcepto.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "ventas.SalirCliente.text")); // NOI18N
        SalirConcepto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirConceptoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarConcepto)
                    .addComponent(SalirConcepto))
                .addContainerGap())
        );

        javax.swing.GroupLayout BConceptoLayout = new javax.swing.GroupLayout(BConcepto.getContentPane());
        BConcepto.getContentPane().setLayout(BConceptoLayout);
        BConceptoLayout.setHorizontalGroup(
            BConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BConceptoLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BConceptoLayout.setVerticalGroup(
            BConceptoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BConceptoLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BFuncionario.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BFuncionario.setTitle("null");

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combofuncionario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combofuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combofuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combofuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combofuncionarioActionPerformed(evt);
            }
        });

        jTBuscarFicha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarFicha.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarFichaActionPerformed(evt);
            }
        });
        jTBuscarFicha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarFichaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(combofuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combofuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarFicha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaficha.setModel(modeloficha);
        tablaficha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablafichaMouseClicked(evt);
            }
        });
        tablaficha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablafichaKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tablaficha);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarFuncionario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarFuncionario.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarFuncionarioActionPerformed(evt);
            }
        });

        SalirFuncionario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirFuncionario.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "ventas.SalirCliente.text")); // NOI18N
        SalirFuncionario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarFuncionario)
                    .addComponent(SalirFuncionario))
                .addContainerGap())
        );

        javax.swing.GroupLayout BFuncionarioLayout = new javax.swing.GroupLayout(BFuncionario.getContentPane());
        BFuncionario.getContentPane().setLayout(BFuncionarioLayout);
        BFuncionarioLayout.setHorizontalGroup(
            BFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFuncionarioLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BFuncionarioLayout.setVerticalGroup(
            BFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFuncionarioLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Créditos - Premios - Comisiones");
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
        etiquetacredito.setText("Créditos Salariales");

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
                .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(creditosxsalarios.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(35, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(refrescar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(SalirCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addGap(18, 18, 18)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializar() {
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
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
        detalle_creditos.setModal(true);
        detalle_creditos.setSize(450, 390);
        //Establecemos un título para el jDialog
        detalle_creditos.setTitle("Agregar Nuevo Crédito Salarial");
        detalle_creditos.setLocationRelativeTo(null);
        detalle_creditos.setVisible(true);
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.idcontrol.setText("0");
        this.funcionario.setText("0");
        this.nombrefuncionario.setText("");
        this.direccionfuncionario.setText("");
        this.fechaemision.setCalendar(c2);
        this.concepto.setText("0");
        this.nombreconcepto.setText("");
        this.tipoconcepto.setText("");
        this.fechagrabado.setCalendar(c2);
        this.importe.setText("0");

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
        this.limpiar();
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nFila = this.jTable1.getSelectedRow();
            //int nagua, nenergiaelec, nrecolebasu, nivasi, nivano = 0;
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.jTable1.requestFocus();
                return;
            } else {
                this.idcontrol.setText(this.jTable1.getValueAt(nFila, 0).toString());
            }
            this.idcontrol.setEnabled(false);
            credito_salarioDAO csDAO = new credito_salarioDAO();
            credito_salario cs = null;
            try {
                cs = csDAO.buscarId(Integer.valueOf(this.idcontrol.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (cs != null) {
                idcontrol.setText(sinformato.format(cs.getIdcontrol()));
                funcionario.setText(String.valueOf(cs.getFuncionario().getCodigo()));
                nombrefuncionario.setText(String.valueOf(cs.getFuncionario().getNombreempleado()));
                direccionfuncionario.setText(String.valueOf(cs.getFuncionario().getDireccion()));
                fechaemision.setDate(cs.getFecha());
                concepto.setText(String.valueOf(cs.getConcepto().getCodigo()));
                nombreconcepto.setText(String.valueOf(cs.getConcepto().getNombre()));
                tipoconcepto.setText(String.valueOf(cs.getConcepto().getTipo()));
                importe.setText(formatea.format(cs.getImporte()));
                observacion.setText(cs.getObservacion());
                detalle_creditos.setModal(true);
                //(Ancho,Alto)
                detalle_creditos.setSize(450, 390);
                //Establecemos un título para el jDialog
                detalle_creditos.setTitle("Modificar Datos de Crédito Salarial");
                detalle_creditos.setLocationRelativeTo(null);
                detalle_creditos.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
            }
        } else {
            JOptionPane.showMessageDialog(null, "USUARIO NO ACTUALIZADO");
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
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                credito_salarioDAO csDAO = new credito_salarioDAO();
                try {
                    credito_salario cs = csDAO.buscarId(Integer.valueOf(num));
                    if (cs == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        csDAO.EliminarCredito(cs.getIdcontrol());
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
        detalle_creditos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed

        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar la Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            credito_salarioDAO grabar = new credito_salarioDAO();
            credito_salario crs = new credito_salario();

            ficha_empleadoDAO fiDAO = new ficha_empleadoDAO();
            ficha_empleado fi = null;

            concepto_salarioDAO csDAO = new concepto_salarioDAO();
            concepto_salario cs = null;

            try {
                fi = fiDAO.buscarId(Integer.valueOf(this.funcionario.getText()));
                cs = csDAO.buscarId(Integer.valueOf(this.concepto.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (Integer.valueOf(idcontrol.getText()) > 0) {
                crs.setIdcontrol(Integer.valueOf(idcontrol.getText()));
            }    //CAPTURAMOS LOS DATOS DE LA CABECERA
            Date dFecha = ODate.de_java_a_sql(fechaemision.getDate());
            Date dFechaGrabado = ODate.de_java_a_sql(fechagrabado.getDate());

            crs.setFecha(dFecha);
            crs.setFechagrabado(dFechaGrabado);

            crs.setFuncionario(fi);
            crs.setGiraduria(fi.getGiraduria().getCodigo());
            crs.setConcepto(cs);
            crs.setObservacion(observacion.getText());
            ///////////////////////EMPEZAMOS A CAPTURAR DATOS NUMERICOS///////////////
            String cImporte = this.importe.getText();
            cImporte = cImporte.replace(".", "").replace(",", ".");
            BigDecimal impor = new BigDecimal(cImporte);

            crs.setImporte(impor);
            crs.setIdusuario(Integer.valueOf(Config.CodUsuario));

            if (Integer.valueOf(this.idcontrol.getText()) == 0) {
                try {
                    grabar.InsertarCredito(crs);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            } else {
                try {
                    grabar.ActualizarCredito(crs);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }

            }

            detalle_creditos.setVisible(false);
            this.detalle_creditos.setModal(false);
            GrillaCredito GrillaGB = new GrillaCredito();
            Thread HiloGrilla = new Thread(GrillaGB);

            HiloGrilla.start();
        }

        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito
        /*   if (this.sucursal.getText().isEmpty() || this.sucursal.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Sucursal");
            this.sucursal.requestFocus();
            return;
        }

        if (this.cliente.getText().isEmpty() || this.cliente.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cuenta del Socio");
            this.cliente.requestFocus();
            return;
        }

        if (this.giraduria.getText().isEmpty() || this.giraduria.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Giraduría");
            this.giraduria.requestFocus();
            return;
        }

        if (this.comercio.getText().isEmpty() || this.comercio.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Casa Comercial");
            this.comercio.requestFocus();
            return;
        }

        if (this.tipo.getText().isEmpty() || this.tipo.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Tipo de Servicio");
            this.tipo.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            contratoDAO grabarOCR = new contratoDAO();
            contrato cr = new contrato();

            giraduriaDAO giraDAO = new giraduriaDAO();
            giraduria gi = null;
            casaDAO caDAO = new casaDAO();
            casa ca = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cl = null;
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal sc = null;
            comprobanteDAO tipoDAO = new comprobanteDAO();
            comprobante ti = null;

            try {
                gi = giraDAO.buscarId(Integer.valueOf(this.giraduria.getText()));
                ca = caDAO.buscarId(Integer.valueOf(this.comercio.getText()));
                cl = cliDAO.buscarId(Integer.valueOf(this.cliente.getText()));
                sc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                ti = tipoDAO.buscarIdxtipo(Integer.valueOf(this.tipo.getText()), 11);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            if (this.creferencia.getText().isEmpty()) {
                referencia = UUID.crearUUID();
                referencia = referencia.substring(1, 25);
            } else {
                referencia = this.creferencia.getText();
                cr.setNumero(Integer.valueOf(this.numero.getText()));
            }

            //CAPTURAMOS LOS DATOS DE LA CABECERA
            cr.setIdordencompra(referencia);
            cr.setSucursal(sc);
            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            cr.setFecha(FechaProceso);
            cr.setGiraduria(gi);
            cr.setComercio(ca);
            cr.setSocio(cl);
            cr.setTipo(ti);
            cr.setPlazo(Integer.valueOf(this.plazo.getText()));
            cr.setObservacion(observacion.getText());
            String cImporte = this.importe.getText();
            cImporte = cImporte.replace(".", "").replace(",", ".");
            BigDecimal nimporte = new BigDecimal(cImporte);
            cr.setImporte(nimporte);

            String cMontoCuota = this.montocuota.getText();
            cMontoCuota = cMontoCuota.replace(".", "").replace(",", ".");

            BigDecimal nMontoCuota = new BigDecimal(cMontoCuota);
            cr.setMonto_cuota(nMontoCuota);

            Date PrimerVence = ODate.de_java_a_sql(primervence.getDate());
            cr.setPrimer_vence(PrimerVence);
            cr.setUsuarioalta(Integer.valueOf(Config.CodUsuario));

            //AHORA CAPTURAMOS LOS DATOS DE LOS DETALLES DE LA CUOTA
            //INICIAMOS EL CICLO FOR PARA EL MODELADO
            String iddocumento = null;
            Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
            calendar.setTime(this.primervence.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
            double nInteres, nMonto, nAmortiza = 0.00;
            nMonto = Double.valueOf(cImporte);
            String detalle = "[";
            for (int i = 1; i <= Integer.valueOf(this.plazo.getText()); i++) {
                iddocumento = UUID.crearUUID();
                iddocumento = iddocumento.substring(1, 25);
                this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                Date VenceCuota = ODate.de_java_a_sql(vencimientos.getDate());

                String linea = "{iddocumento : " + iddocumento + ","
                        + "creferencia : " + referencia + ","
                        + "documento : " + this.numero.getText() + ","
                        + "fecha : " + FechaProceso + ","
                        + "vencimiento : " + VenceCuota + ","
                        + "cliente : " + this.cliente.getText() + ","
                        + "sucursal : " + this.sucursal.getText() + ","
                        + "importe : " + cMontoCuota + ","
                        + "comprobante : " + ti.getCodigo() + ","
                        + "giraduria : " + this.giraduria.getText() + ","
                        + "numerocuota : " + this.plazo.getText() + ","
                        + "cuota : " + i + ","
                        + "saldo : " + cMontoCuota + ","
                        + "comercial : " + this.comercio.getText()
                        + "},";
                detalle += linea;
                calendar.setTime(this.vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
                this.venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior
                System.out.println(detalle);
                // Se capturan el día y el mes para saber si hay que aumentar los días en el siguiente mes
                // esto se hace en caso que el mes sea febrero
                int mes = this.venceanterior.getCalendar().get(Calendar.MONTH) + 1;
                int dia = this.venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
                calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0

                if (mes == 2 && dia == 28) {
                    calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días 
                    //el vencimiento
                }
                this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
                nMonto = nMonto - nAmortiza;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            if (Integer.valueOf(this.numero.getText()) == 0) {
                try {
                    grabarOCR.insertarContrato(cr, detalle);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } else {
                //Actualizar Orden de Credito
                try {
                    grabarOCR.borrarDetalleContrato(this.creferencia.getText());
                    grabarOCR.ActualizarContrato(cr, detalle);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            }
            detalle_debitos.setVisible(false);
            this.detalle_debitos.setModal(false);
            this.detalle_debitos.setVisible(false);
            GrillaCredito GrillaOC = new GrillaCredito();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();
        }*/
    }//GEN-LAST:event_GrabarActionPerformed

    private void detalle_creditosFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_creditosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_creditosFocusGained

    private void detalle_creditosWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_creditosWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_creditosWindowGainedFocus

    private void detalle_creditosWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_creditosWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_creditosWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void refrescarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaCredito GrillaOC = new GrillaCredito();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();        // TODO add your handling code here:
    }//GEN-LAST:event_refrescarActionPerformed

    private void fechaemisionFocusGained(FocusEvent evt) {//GEN-FIRST:event_fechaemisionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaemisionFocusGained

    private void fechaemisionKeyPressed(KeyEvent evt) {//GEN-FIRST:event_fechaemisionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.concepto.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.funcionario.requestFocus();
        }   // TO        // TODO add your handling code here:

    }//GEN-LAST:event_fechaemisionKeyPressed

    private void importeKeyPressed(KeyEvent evt) {//GEN-FIRST:event_importeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.observacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.concepto.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_importeKeyPressed

    private void importeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importeFocusGained
        importe.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importeFocusGained

    private void grabarocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarocActionPerformed
        int nFila = this.jTable1.getSelectedRow();
//        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            String cAprobar = "APROBADO";
            int ret = JOptionPane.showOptionDialog(null, "Desea Aprobar el Préstamo Seleccionado? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Aprobar un Préstamo");
            return;
        }
        this.aprobarcredito.setVisible(false);
    }//GEN-LAST:event_grabarocActionPerformed

    private void saliropcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saliropcionActionPerformed
        aprobarcredito.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_saliropcionActionPerformed

    private void conceptoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_conceptoFocusGained
        concepto.selectAll();
    }//GEN-LAST:event_conceptoFocusGained

    private void conceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conceptoActionPerformed
        BuscarConcepto.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoActionPerformed

    private void conceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_conceptoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechaemision.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_conceptoKeyPressed

    private void BuscarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarConceptoActionPerformed
        concepto_salarioDAO csDAO = new concepto_salarioDAO();
        concepto_salario cs = null;
        try {
            cs = csDAO.buscarIdxTipo(Integer.valueOf(this.concepto.getText()), "INGRESOS");
            if (cs.getCodigo() == 0) {
                BConcepto.setModal(true);
                BConcepto.setSize(482, 575);
                BConcepto.setLocationRelativeTo(null);
                BConcepto.setVisible(true);
                BConcepto.setTitle("Buscar Concepto");
                importe.requestFocus();
                BConcepto.setModal(false);
            } else {
                nombreconcepto.setText(cs.getNombre());
                tipoconcepto.setText(cs.getTipo());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        importe.requestFocus();
        // TODO add your handling code here:

    }//GEN-LAST:event_BuscarConceptoActionPerformed

    private void nombreconceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreconceptoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreconceptoKeyPressed

    private void comboconceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboconceptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboconceptoActionPerformed

    private void jTBuscarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarConceptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarConceptoActionPerformed

    private void jTBuscarConceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarConceptoKeyPressed
        this.jTBuscarConcepto.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarConcepto.getText()).toUpperCase();
                jTBuscarConcepto.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboconcepto.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtroconcepto(indiceColumnaTabla);
            }
        });
        trsfiltroconcepto = new TableRowSorter(tablaconcepto.getModel());
        tablaconcepto.setRowSorter(trsfiltroconcepto);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarConceptoKeyPressed

    private void tablaconceptoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaconceptoMouseClicked
        this.AceptarConcepto.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaconceptoMouseClicked

    private void tablaconceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaconceptoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarConcepto.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaconceptoKeyPressed

    private void AceptarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarConceptoActionPerformed
        int nFila = this.tablaconcepto.getSelectedRow();
        this.concepto.setText(this.tablaconcepto.getValueAt(nFila, 0).toString());
        this.nombreconcepto.setText(this.tablaconcepto.getValueAt(nFila, 1).toString());
        this.tipoconcepto.setText(this.tablaconcepto.getValueAt(nFila, 2).toString());

        this.BConcepto.setVisible(false);
        this.jTBuscarConcepto.setText("");
        this.Grabar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarConceptoActionPerformed

    private void SalirConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirConceptoActionPerformed
        this.BConcepto.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirConceptoActionPerformed

    private void funcionarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_funcionarioFocusGained
        funcionario.selectAll();
    }//GEN-LAST:event_funcionarioFocusGained

    private void funcionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funcionarioActionPerformed
        BuscarFuncionario.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_funcionarioActionPerformed

    private void funcionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_funcionarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!funcionario.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                concepto.setEnabled(true);
                concepto.requestFocus();
            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese Código de Funcionario", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_funcionarioKeyPressed

    private void BuscarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarFuncionarioActionPerformed
        ficha_empleadoDAO fiDAO = new ficha_empleadoDAO();
        ficha_empleado fi = null;
        try {
            fi = fiDAO.buscarIdActivo(Integer.valueOf(this.funcionario.getText()));
            if (fi.getCodigo() == 0) {
                BFuncionario.setModal(true);
                BFuncionario.setSize(482, 575);
                BFuncionario.setLocationRelativeTo(null);
                BFuncionario.setVisible(true);
                BFuncionario.setTitle("Buscar Funcionario");
                concepto.requestFocus();
                BFuncionario.setModal(false);
            } else {
                nombrefuncionario.setText(fi.getNombreempleado());
                direccionfuncionario.setText(fi.getDireccion());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        fechaemision.requestFocus();

        // TODO add your handling code here:

    }//GEN-LAST:event_BuscarFuncionarioActionPerformed

    private void idcontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idcontrolKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idcontrolKeyPressed

    private void direccionfuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionfuncionarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionfuncionarioKeyPressed

    private void combofuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combofuncionarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combofuncionarioActionPerformed

    private void jTBuscarFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarFichaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFichaActionPerformed

    private void jTBuscarFichaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarFichaKeyPressed
        this.jTBuscarFicha.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarFicha.getText()).toUpperCase();
                jTBuscarFicha.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combofuncionario.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;

                }
                repaint();
                filtrofi(indiceColumnaTabla);
            }
        });
        trsfiltroficha = new TableRowSorter(tablaficha.getModel());
        tablaficha.setRowSorter(trsfiltroficha);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFichaKeyPressed

    private void tablafichaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablafichaMouseClicked
        this.AceptarFuncionario.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablafichaMouseClicked

    private void tablafichaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablafichaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarFuncionario.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablafichaKeyPressed

    private void AceptarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarFuncionarioActionPerformed
        int nFila = this.tablaficha.getSelectedRow();
        this.funcionario.setText(this.tablaficha.getValueAt(nFila, 0).toString());
        this.nombrefuncionario.setText(this.tablaficha.getValueAt(nFila, 1).toString());
        this.direccionfuncionario.setText(this.tablaficha.getValueAt(nFila, 2).toString());
        this.BFuncionario.setVisible(false);
        this.jTBuscarFicha.setText("");
        this.concepto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarFuncionarioActionPerformed

    private void SalirFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFuncionarioActionPerformed
        this.BFuncionario.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFuncionarioActionPerformed

    private void tipoconceptoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipoconceptoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoconceptoKeyPressed

    private void nombrefuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrefuncionarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrefuncionarioKeyPressed

    private void GrabarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GrabarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importe.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarKeyPressed

    private void observacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_observacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.Grabar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importe.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_observacionKeyPressed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtrofi(int nNumeroColumna) {
        trsfiltroficha.setRowFilter(RowFilter.regexFilter(this.jTBuscarFicha.getText(), nNumeroColumna));
    }

    public void filtroconcepto(int nNumeroColumna) {
        trsfiltroconcepto.setRowFilter(RowFilter.regexFilter(this.jTBuscarConcepto.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Nro.");
        modelo.addColumn("Fecha");
        modelo.addColumn("Nombre del Funcionario");
        modelo.addColumn("Motivo del Crédito");
        modelo.addColumn("Importe");
        modelo.addColumn("Fecha Proceso");
        modelo.addColumn("Usuario");

        int[] anchos = {90, 90, 200, 200, 100, 150, 100};
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
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(AlinearCentro);

        this.jTable1.getColumnModel().getColumn(6).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(6).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
    }

    private void TitFuncionario() {
        modeloficha.addColumn("Código");
        modeloficha.addColumn("Nombre");
        modeloficha.addColumn("Dirección");

        int[] anchos = {90, 200, 250};
        for (int i = 0; i < modeloficha.getColumnCount(); i++) {
            tablaficha.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaficha.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaficha.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaficha.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaficha.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitConcepto() {
        modeloconcepto.addColumn("Código");
        modeloconcepto.addColumn("Nombre");
        modeloconcepto.addColumn("Tipo");

        int[] anchos = {90, 200, 200};
        for (int i = 0; i < modeloconcepto.getColumnCount(); i++) {
            tablaconcepto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaconcepto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaconcepto.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaconcepto.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaconcepto.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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
                new creditosxsalarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarConcepto;
    private javax.swing.JButton AceptarFuncionario;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BConcepto;
    private javax.swing.JDialog BFuncionario;
    private javax.swing.JButton BuscarConcepto;
    private javax.swing.JButton BuscarFuncionario;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirConcepto;
    private javax.swing.JButton SalirFuncionario;
    private javax.swing.JDialog aprobarcredito;
    private javax.swing.JLabel barrioetiqueta;
    private javax.swing.JLabel barrioetiqueta1;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JComboBox comboconcepto;
    private javax.swing.JComboBox combofuncionario;
    private javax.swing.JTextField concepto;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JFormattedTextField descuentoc;
    private javax.swing.JDialog detalle_creditos;
    private javax.swing.JTextField direccionfuncionario;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private com.toedter.calendar.JDateChooser fechac;
    private com.toedter.calendar.JDateChooser fechaemision;
    private com.toedter.calendar.JDateChooser fechagrabado;
    private javax.swing.JTextField funcionario;
    private javax.swing.JButton grabaroc;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idcontrol;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JFormattedTextField importec;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarConcepto;
    private javax.swing.JTextField jTBuscarFicha;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField neto;
    private javax.swing.JTextField nombreclientec;
    private javax.swing.JTextField nombreconcepto;
    private javax.swing.JTextField nombrefuncionario;
    private javax.swing.JTextField numeroc;
    private javax.swing.JTextArea observacion;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JButton refrescar;
    private javax.swing.JButton saliropcion;
    private javax.swing.JTable tablaconcepto;
    private javax.swing.JTable tablaficha;
    private javax.swing.JTextField tipoconcepto;
    // End of variables declaration//GEN-END:variables

    private class GenerarReporte extends Thread {

        public void run() {
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("FechaI", FechaI);
                parameters.put("FechaF", FechaF);
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/credito_salarios.jasper");
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

    private class GrillaCredito extends Thread {

        public void run() {
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            credito_salarioDAO csDAO = new credito_salarioDAO();
            try {
                for (credito_salario cs : csDAO.MostrarxFecha(FechaI, FechaF)) {
                    String Datos[] = {formatosinpunto.format(cs.getIdcontrol()), formatoFecha.format(cs.getFecha()), cs.getFuncionario().getNombreempleado(), cs.getConcepto().getNombre(), formatea.format(cs.getImporte()), formatoFecha.format(cs.getFechagrabado()), String.valueOf(cs.getIdusuario())};
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

    private class GrillaConcepto extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloconcepto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloconcepto.removeRow(0);
            }

            concepto_salarioDAO DAOCS = new concepto_salarioDAO();
            try {
                for (concepto_salario cs : DAOCS.TodoxTipo("INGRESOS")) {
                    String Datos[] = {String.valueOf(cs.getCodigo()), cs.getNombre(), cs.getTipo()};
                    modeloconcepto.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaconcepto.setRowSorter(new TableRowSorter(modeloconcepto));
            int cantFilas = tablaconcepto.getRowCount();
        }
    }

    private class GrillaFicha extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloficha.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloficha.removeRow(0);
            }

            ficha_empleadoDAO DAOFI = new ficha_empleadoDAO();
            try {
                for (ficha_empleado fi : DAOFI.TodosActivo()) {
                    String Datos[] = {String.valueOf(fi.getCodigo()), fi.getNombreempleado(), fi.getDireccion()};
                    modeloficha.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaficha.setRowSorter(new TableRowSorter(modeloficha));
            int cantFilas = tablaficha.getRowCount();
        }
    }

}

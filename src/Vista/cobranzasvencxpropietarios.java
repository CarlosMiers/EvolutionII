/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Conexion.Conexion;
import Conexion.ObtenerFecha;
import Modelo.Tablas;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import Clases.Config;
import DAO.cuenta_clienteDAO;
import DAO.propietarioDAO;
import Modelo.cuenta_clientes;
import Modelo.propietario;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.UIManager;

/**
 *
 * @author hp
 */
public class cobranzasvencxpropietarios extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelopro = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocliente = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltropro, trsfiltromoneda;
    int nProp = 0;
    ObtenerFecha ODate = new ObtenerFecha();
    String cSql, cCliente = null;

    Config configuracion = new Config();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    double nCapital = 0.00;
    double nMora = 0.00;
    double nInteres = 0.00;
    int nDiaGraciaMora = 0;
    double nImporteGastos, nDiasGracia, nDiasGraciaGastos = 0.00;
    int natraso = 0;
    String catraso = null;
    String cAmortizacion;
    double nAmortizacion;
    double nPunitorio = 0.00;
    double nTasaPunitoria = 0.00;

    /**
     * Creates new form libroventaconsolidado
     */
    public cobranzasvencxpropietarios() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.codpro.setText("0");
        this.idControl.setText("0");
        this.listar.setEnabled(false);
        this.nombrepropietario.setText("");
        this.idControl.setVisible(false);
        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.TitProp();
        this.Inicializar();
        codpro.requestFocus();
    }

    private void cargarTitulo() {

        modelo.addColumn("Ref");
        modelo.addColumn("Documento");
        modelo.addColumn("Vencimiento");
        modelo.addColumn("Código");
        modelo.addColumn("Cédula/Ruc");
        modelo.addColumn("Locatario");
        modelo.addColumn("Edificio");
        modelo.addColumn("Alquiler");
        modelo.addColumn("Garage");
        modelo.addColumn("Expensa");
        modelo.addColumn("Comisión");
        modelo.addColumn("Multa");
        modelo.addColumn("Garantía");
        modelo.addColumn("Fondo");
        modelo.addColumn("Llave");
        modelo.addColumn("Otros");
        modelo.addColumn("Saldo");
        modelo.addColumn("Inmueble");

        int[] anchos = {0,100,100, 90,100,150,150, 110, 110, 110, 110, 110,110,110,110,110,110,0};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.jTable1.setFont(font);

        DefaultTableCellRenderer TablaDerecho = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaIzquierdo = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaDerecho.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear
        TablaIzquierdo.setHorizontalAlignment(SwingConstants.LEFT);
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(17).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(17).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaIzquierdo);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaIzquierdo);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaIzquierdo);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(11).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(12).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(13).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(14).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(15).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(16).setCellRenderer(TablaDerecho);
    }

    private void TitProp() {
        modelopro.addColumn("Código");
        modelopro.addColumn("Cédula/Ruc");
        modelopro.addColumn("Nombre de Propietario");

        int[] anchos = {100, 100, 200};
        for (int i = 0; i < modelopro.getColumnCount(); i++) {
            tablapro.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapro.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapro.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapro.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablapro.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
        this.codpro.setText("0");
        this.nombrepropietario.setText("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BPropietario = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combopropietario = new javax.swing.JComboBox();
        jTBuscarPropietario = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablapro = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarPro = new javax.swing.JButton();
        SalirPro = new javax.swing.JButton();
        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        etiquetasaldos = new org.edisoncor.gui.label.LabelMetric();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nombrepropietario = new javax.swing.JTextField();
        codpro = new javax.swing.JTextField();
        BuscarResponsable = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        listar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        BPropietario.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BPropietario.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combopropietario.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combopropietario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Código", "Buscar por Cédula/Ruc", "Buscar por Nombre" }));
        combopropietario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combopropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combopropietarioActionPerformed(evt);
            }
        });

        jTBuscarPropietario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPropietario.setText(org.openide.util.NbBundle.getMessage(cobranzasvencxpropietarios.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarPropietarioActionPerformed(evt);
            }
        });
        jTBuscarPropietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPropietarioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combopropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combopropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tablapro.setModel(modelopro       );
        tablapro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproMouseClicked(evt);
            }
        });
        tablapro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaproKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablapro);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarPro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarPro.setText(org.openide.util.NbBundle.getMessage(cobranzasvencxpropietarios.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProActionPerformed(evt);
            }
        });

        SalirPro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirPro.setText(org.openide.util.NbBundle.getMessage(cobranzasvencxpropietarios.class, "ventas.SalirCliente.text")); // NOI18N
        SalirPro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarPro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirPro, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPro)
                    .addComponent(SalirPro))
                .addContainerGap())
        );

        javax.swing.GroupLayout BPropietarioLayout = new javax.swing.GroupLayout(BPropietario.getContentPane());
        BPropietario.getContentPane().setLayout(BPropietarioLayout);
        BPropietarioLayout.setHorizontalGroup(
            BPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPropietarioLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BPropietarioLayout.setVerticalGroup(
            BPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPropietarioLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vencimientos de Alquileres por Propietarios");

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetasaldos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setForeground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setText("Vencimientos de Alquileres por Propietarios");
        etiquetasaldos.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1041, Short.MAX_VALUE))
        );
        panelTranslucido1Layout.setVerticalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        panelTranslucido2.setBackground(new java.awt.Color(204, 204, 204));
        panelTranslucido2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTranslucido2.setColorPrimario(new java.awt.Color(204, 204, 204));
        panelTranslucido2.setColorSecundario(new java.awt.Color(153, 153, 153));

        generar.setText("Generar");
        generar.setToolTipText("Filtrar Datos");
        generar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        salir.setText("Salir");
        salir.setToolTipText("Salir de esta Opción");
        salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        jLabel1.setText("Propietarios");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Rango de Vencimiento"));

        dFechaInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dFechaInicialFocusGained(evt);
            }
        });

        jLabel2.setText("Desde el");

        jLabel3.setText("Hasta el");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        nombrepropietario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nombrepropietario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrepropietario.setEnabled(false);

        codpro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codpro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codproFocusGained(evt);
            }
        });
        codpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codproActionPerformed(evt);
            }
        });
        codpro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codproKeyPressed(evt);
            }
        });

        BuscarResponsable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarResponsable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarResponsableMouseClicked(evt);
            }
        });
        BuscarResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarResponsableActionPerformed(evt);
            }
        });

        idControl.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrepropietario)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(codpro, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 11, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(codpro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(nombrepropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        listar.setText("Vista Previa");
        listar.setToolTipText("Vista Previa");
        listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTranslucido2Layout = new javax.swing.GroupLayout(panelTranslucido2);
        panelTranslucido2.setLayout(panelTranslucido2Layout);
        panelTranslucido2Layout.setHorizontalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTranslucido2Layout.setVerticalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(generar)
                .addGap(3, 3, 3)
                .addComponent(listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salir)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(modelo);
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panelTranslucido1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTranslucido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        if (this.codpro.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Propietario");
            this.codpro.requestFocus();
            return;
        }
        GenerarConsultaCuenta consulta1 = new GenerarConsultaCuenta();
        Thread HiloConsulta1 = new Thread(consulta1);
        HiloConsulta1.start();        // TODO add your handling code here:
    }//GEN-LAST:event_generarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirActionPerformed

    private void dFechaInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dFechaInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialFocusGained

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_listarActionPerformed

    private void codproFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codproFocusGained
        codpro.selectAll();
    }//GEN-LAST:event_codproFocusGained

    private void codproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codproActionPerformed
        this.BuscarResponsable.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codproActionPerformed

    private void codproKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codproKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!codpro.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos

            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Propietario ", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_codproKeyPressed

    private void BuscarResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarResponsableActionPerformed
        if (this.codpro.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código del Propietario");
            this.codpro.requestFocus();
            return;
        }

        if (codpro.getText().equals("0")) {
            codpro.setText("0");
            nombrepropietario.setText("Todos los Propietarios");
        } else {

            propietarioDAO paDAO = new propietarioDAO();
            propietario pro = null;
            try {
                pro = paDAO.buscarId(Integer.valueOf(this.codpro.getText()));
                if (pro.getCodpro() == 0) {
                    nProp = 1;
                    GrillaBuscarPropietario grillapro = new GrillaBuscarPropietario();
                    Thread hilopro = new Thread(grillapro);
                    hilopro.start();
                    BPropietario.setModal(true);
                    BPropietario.setSize(482, 575);
                    BPropietario.setLocationRelativeTo(null);
                    BPropietario.setVisible(true);
                    BPropietario.setTitle("Buscar Propietario");
                    BPropietario.setModal(false);
                } else {
                    nombrepropietario.setText(pro.getNombreprop());
                    //Establecemos un título para el jDialog
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarResponsableActionPerformed

    private void combopropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combopropietarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combopropietarioActionPerformed

    private void jTBuscarPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarPropietarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPropietarioActionPerformed

    private void jTBuscarPropietarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPropietarioKeyPressed
        this.jTBuscarPropietario.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarPropietario.getText()).toUpperCase();
                jTBuscarPropietario.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combopropietario.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 0;
                        break;//por código
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por cedula

                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por nombre
                }
                repaint();
                filtropro(indiceColumnaTabla);
            }
        });
        trsfiltropro = new TableRowSorter(tablapro.getModel());
        tablapro.setRowSorter(trsfiltropro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPropietarioKeyPressed

    private void tablaproMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproMouseClicked
        this.AceptarPro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproMouseClicked

    private void tablaproKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPro.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproKeyPressed

    private void AceptarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProActionPerformed
        int nFila = this.tablapro.getSelectedRow();
        this.codpro.setText(this.tablapro.getValueAt(nFila, 0).toString());
        this.nombrepropietario.setText(this.tablapro.getValueAt(nFila, 2).toString());

        this.BPropietario.setVisible(false);
        this.jTBuscarPropietario.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProActionPerformed

    private void SalirProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProActionPerformed
        this.BPropietario.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void BuscarResponsableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarResponsableMouseClicked

    }//GEN-LAST:event_BuscarResponsableMouseClicked

    public void filtropro(int nNumeroColumna) {
        trsfiltropro.setRowFilter(RowFilter.regexFilter(this.jTBuscarPropietario.getText(), nNumeroColumna));
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
                new cobranzasvencxpropietarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarPro;
    private javax.swing.JDialog BPropietario;
    private javax.swing.JButton BuscarResponsable;
    private javax.swing.JButton SalirPro;
    private javax.swing.JTextField codpro;
    private javax.swing.JComboBox combopropietario;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private org.edisoncor.gui.label.LabelMetric etiquetasaldos;
    private javax.swing.JButton generar;
    private javax.swing.JTextField idControl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTBuscarPropietario;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton listar;
    private javax.swing.JTextField nombrepropietario;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JButton salir;
    private javax.swing.JTable tablapro;
    // End of variables declaration//GEN-END:variables

    private class GenerarConsultaCuenta extends Thread {

        public void run() {
            int nPropietario = Integer.valueOf(codpro.getText());
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            cuenta_clienteDAO DAO = new cuenta_clienteDAO();
            try {
                for (cuenta_clientes cuenta : DAO.Cobranzasvencxpropietarios(FechaI, FechaF, nPropietario)) {
                    String Datos[] = {cuenta.getCreferencia(), cuenta.getDocumento(), formatoFecha.format(cuenta.getVencimiento()), String.valueOf(cuenta.getCliente().getCodigo()),cuenta.getCliente().getRuc(),cuenta.getCliente().getNombre(), String.valueOf(cuenta.getInmueble().getNomedif()), formato.format(cuenta.getAlquiler()), formato.format(cuenta.getGarage()), formato.format(cuenta.getExpensa()), formato.format(cuenta.getComision()), formato.format(cuenta.getMulta()),formato.format(cuenta.getGarantia()),formato.format(cuenta.getFondo()),formato.format(cuenta.getLlave()),formato.format(cuenta.getOtros()),formato.format(cuenta.getSaldo()),String.valueOf(cuenta.getInmueble().getIdinmueble()),};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            jTable1.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = jTable1.getRowCount();
            if (cantFilas > 0) {
                listar.setEnabled(true);
            } else {
                listar.setEnabled(false);
            }
        }
    }

    private class GrillaBuscarPropietario extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopro.removeRow(0);
            }
            propietarioDAO proDAO = new propietarioDAO();
            try {
                for (propietario pr : proDAO.todos()) {
                    String Datos[] = {String.valueOf(pr.getCodpro()), pr.getCedula(), pr.getNombreprop()};
                    modelopro.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            int cantFilas = tablapro.getRowCount();
        }
    }

    private class GenerarReporte extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            Date FechaInicio = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaFinal = ODate.de_java_a_sql(dFechaFinal.getDate());

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cReferencia", idControl.getText().trim());
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
                parameters.put("nPropietario", codpro.getText());
                parameters.put("FechaInicio", FechaInicio);
                parameters.put("FechaFinal", FechaFinal);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/cobranzavencxpropietario.jasper");
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

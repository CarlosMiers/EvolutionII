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
import java.sql.ResultSet;
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
import DAO.cabecera_compraDAO;
import DAO.monedaDAO;
import DAO.rubroDAO;
import DAO.sucursalDAO;
import Modelo.Resumencomprasxrubro;
import Modelo.cabecera_compra;

import Modelo.moneda;
import Modelo.rubro;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author hp
 */
public class resumen_comprasxrubro extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelorubro = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocliente = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltrorubro, trsfiltromoneda, trsfiltro, trsfiltro2, trsfiltrocli;
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
    public resumen_comprasxrubro() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.rubro.setText("0");
        this.nombremoneda.setText("");
        this.moneda.setText("0");
        this.listar.setEnabled(false);
        this.nombrerubro.setText("");
        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.TitRubro();
        this.TitMoneda();
        this.Inicializar();
        rubro.requestFocus();
    }

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Promedio");
        modelo.addColumn("Total");
        modelo.addColumn("Rubro");
        modelo.addColumn("Descripción");
        modelo.addColumn("Fecha");

        int[] anchos = {80, 300, 80, 80, 150, 100, 100, 100, 80};
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
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(AlinearCentro);

    }

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

    private void TitRubro() {
        modelorubro.addColumn("Código");
        modelorubro.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelorubro.getColumnCount(); i++) {
            tablarubro.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablarubro.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablarubro.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablarubro.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablarubro.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitMoneda() {
        modelomoneda.addColumn("Código");
        modelomoneda.addColumn("Nombre");
        modelomoneda.addColumn("Cotización");

        int[] anchos = {90, 100, 90};
        for (int i = 0; i < modelomoneda.getColumnCount(); i++) {
            tablamoneda.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamoneda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamoneda.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablamoneda.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablamoneda.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablamoneda.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
        this.rubro.setText("0");
        this.nombrerubro.setText("");
        this.moneda.setText("0");
        this.nombremoneda.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BRubro = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        comborubro = new javax.swing.JComboBox();
        jTBuscarRubro = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablarubro = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarRubro = new javax.swing.JButton();
        SalirRubro = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
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
        jLabel4 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        nombremoneda = new javax.swing.JTextField();
        rubro = new javax.swing.JTextField();
        nombrerubro = new javax.swing.JTextField();
        buscarRubro = new javax.swing.JButton();
        buscarMoneda = new javax.swing.JButton();
        listar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        BRubro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BRubro.setTitle("null");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comborubro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comborubro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comborubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comborubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comborubroActionPerformed(evt);
            }
        });

        jTBuscarRubro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarRubro.setText(org.openide.util.NbBundle.getMessage(resumen_comprasxrubro.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarRubro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscarRubroFocusGained(evt);
            }
        });
        jTBuscarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarRubroActionPerformed(evt);
            }
        });
        jTBuscarRubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarRubroKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablarubro.setModel(modelorubro);
        tablarubro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablarubroMouseClicked(evt);
            }
        });
        tablarubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablarubroKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablarubro);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarRubro.setText(org.openide.util.NbBundle.getMessage(resumen_comprasxrubro.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarRubroActionPerformed(evt);
            }
        });

        SalirRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirRubro.setText(org.openide.util.NbBundle.getMessage(resumen_comprasxrubro.class, "ventas.SalirCliente.text")); // NOI18N
        SalirRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarRubro)
                    .addComponent(SalirRubro))
                .addContainerGap())
        );

        javax.swing.GroupLayout BRubroLayout = new javax.swing.GroupLayout(BRubro.getContentPane());
        BRubro.getContentPane().setLayout(BRubroLayout);
        BRubroLayout.setHorizontalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BRubroLayout.setVerticalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.setText(org.openide.util.NbBundle.getMessage(resumen_comprasxrubro.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarMonedaActionPerformed(evt);
            }
        });
        jTBuscarMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarMonedaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablamoneda.setModel(modelomoneda);
        tablamoneda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablamonedaMouseClicked(evt);
            }
        });
        tablamoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablamonedaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablamoneda);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText(org.openide.util.NbBundle.getMessage(resumen_comprasxrubro.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(org.openide.util.NbBundle.getMessage(resumen_comprasxrubro.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Resumen de Compras por Rubros");

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetasaldos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setForeground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setText("Resumen de Compras por Rubros");
        etiquetasaldos.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1114, Short.MAX_VALUE))
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

        jLabel1.setText("Rubro");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Rango de Fechas"));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel4.setText("Moneda");

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        moneda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                monedaFocusGained(evt);
            }
        });
        moneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monedaActionPerformed(evt);
            }
        });

        nombremoneda.setEnabled(false);

        rubro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rubro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rubroFocusGained(evt);
            }
        });
        rubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rubroActionPerformed(evt);
            }
        });
        rubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rubroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rubroKeyReleased(evt);
            }
        });

        nombrerubro.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrerubro.setEnabled(false);

        buscarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarRubro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buscarRubroMouseClicked(evt);
            }
        });
        buscarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarRubroActionPerformed(evt);
            }
        });

        buscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(rubro, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nombremoneda, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrerubro))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombrerubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 100, Short.MAX_VALUE))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        panelTranslucido2Layout.setVerticalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generar)
                .addGap(3, 3, 3)
                .addComponent(listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salir)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(modelo);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addComponent(panelTranslucido1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        if (this.rubro.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Vendedor");
            this.rubro.requestFocus();
            return;
        }
        GenerarConsultaCompra consulta1 = new GenerarConsultaCompra();
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

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        this.buscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void comborubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comborubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comborubroActionPerformed

    private void jTBuscarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarRubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarRubroActionPerformed

    private void jTBuscarRubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarRubroKeyPressed
        this.jTBuscarRubro.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarRubro.getText()).toUpperCase();
                jTBuscarRubro.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comborubro.getSelectedIndex()) {
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
                filtrorubro(indiceColumnaTabla);
            }
        });
        trsfiltrorubro = new TableRowSorter(tablarubro.getModel());
        tablarubro.setRowSorter(trsfiltrorubro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarRubroKeyPressed

    private void tablarubroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablarubroMouseClicked
        this.AceptarRubro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablarubroMouseClicked

    private void tablarubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablarubroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarRubro.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablarubroKeyPressed

    private void AceptarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarRubroActionPerformed
        int nFila = this.tablarubro.getSelectedRow();
        this.rubro.setText(this.tablarubro.getValueAt(nFila, 0).toString());
        this.nombrerubro.setText(this.tablarubro.getValueAt(nFila, 1).toString());

        this.BRubro.setVisible(false);
        this.jTBuscarRubro.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarRubroActionPerformed

    private void SalirRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirRubroActionPerformed
        this.BRubro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirRubroActionPerformed

    private void rubroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rubroFocusGained
        rubro.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_rubroFocusGained

    private void rubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubroActionPerformed
        this.buscarRubro.doClick();
        // jTBuscarSucursal.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroActionPerformed

    private void rubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rubroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTBuscarRubro.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroKeyPressed

    private void rubroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rubroKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroKeyReleased

    private void combomonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combomonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomonedaActionPerformed

    private void jTBuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMonedaActionPerformed

    private void jTBuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarMonedaKeyPressed
        this.jTBuscarMoneda.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarMoneda.getText()).toUpperCase();
                jTBuscarMoneda.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combomoneda.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtromoneda(indiceColumnaTabla);
            }
        });
        trsfiltromoneda = new TableRowSorter(tablamoneda.getModel());
        tablamoneda.setRowSorter(trsfiltromoneda);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMonedaKeyPressed

    private void tablamonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamonedaMouseClicked
        this.AceptarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaMouseClicked

    private void tablamonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamonedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarMoneda.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaKeyPressed

    private void AceptarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarMonedaActionPerformed
        int nFila = this.tablamoneda.getSelectedRow();
        this.moneda.setText(this.tablamoneda.getValueAt(nFila, 0).toString());
        this.nombremoneda.setText(this.tablamoneda.getValueAt(nFila, 1).toString());

        this.BMoneda.setVisible(false);
        this.jTBuscarMoneda.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void buscarRubroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarRubroMouseClicked
        jTBuscarRubro.requestFocus();

    }//GEN-LAST:event_buscarRubroMouseClicked

    private void buscarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarRubroActionPerformed
        if (rubro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No puede dejar en Blanco");
            rubro.requestFocus();
            return;
        }
        if (rubro.getText().equals("0")) {
            rubro.setText("0");
            nombrerubro.setText("Todos los Rubros");
            moneda.requestFocus();
        } else {
            rubroDAO ruDAO = new rubroDAO();
            rubro ru = null;
            try {
                ru = ruDAO.buscarId(Integer.valueOf(this.rubro.getText()));
                if (ru.getCodigo() == 0) {
                    GrillaRubro grillaru = new GrillaRubro();
                    Thread hiloru = new Thread(grillaru);
                    hiloru.start();
                    BRubro.setModal(true);
                    BRubro.setSize(500, 575);
                    BRubro.setLocationRelativeTo(null);
                    BRubro.setVisible(true);
                    BRubro.setTitle("Rubro");
                    BRubro.setModal(false);
                } else {
                    nombrerubro.setText(ru.getNombre());
                    //Establecemos un título para el jDialog
                }
                moneda.requestFocus();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarRubroActionPerformed

    private void buscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarMonedaActionPerformed
        monedaDAO casDAO = new monedaDAO();
        moneda mn = null;
        try {
            mn = casDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mn.getCodigo() == 0) {
                GrillaCasa grillaca = new GrillaCasa();
                Thread hiloca = new Thread(grillaca);
                hiloca.start();
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mn.getNombre());

                //Establecemos un título para el jDialog
            }
            //cuotas.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarMonedaActionPerformed

    private void jTBuscarRubroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscarRubroFocusGained
        rubro.selectAll();
    }//GEN-LAST:event_jTBuscarRubroFocusGained

    private void monedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_monedaFocusGained
        moneda.selectAll();
    }//GEN-LAST:event_monedaFocusGained

    public void filtrorubro(int nNumeroColumna) {
        trsfiltrorubro.setRowFilter(RowFilter.regexFilter(this.jTBuscarRubro.getText(), nNumeroColumna));
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
                new resumen_comprasxrubro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton AceptarRubro;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BRubro;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirRubro;
    private javax.swing.JButton buscarMoneda;
    private javax.swing.JButton buscarRubro;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox comborubro;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private org.edisoncor.gui.label.LabelMetric etiquetasaldos;
    private javax.swing.JButton generar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarRubro;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton listar;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombrerubro;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JTextField rubro;
    private javax.swing.JButton salir;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablarubro;
    // End of variables declaration//GEN-END:variables

    private class GenerarConsultaCompra extends Thread {

        public void run() {
            int nRubro = Integer.valueOf(rubro.getText());
            int nMoneda = Integer.valueOf(moneda.getText());
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            cabecera_compraDAO DAO = new cabecera_compraDAO();
            try {
                for (cabecera_compra orden : DAO.ResumenCompraxRubro(FechaI, FechaF, nRubro, nRubro, nMoneda)) {
                    String Datos[] = {orden.getCodprodcompra(), orden.getNombreproductocompra(), formato.format(orden.getCantidadcompra()), formato.format(orden.getPreciocompra()), formato.format(orden.getPromedio()), formato.format(orden.getTotalneto()), String.valueOf(orden.getCodrubrocompra()), orden.getNombrerubrocompra(), formatoFecha.format(orden.getFecha())};
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

    private class GrillaRubro extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelorubro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelorubro.removeRow(0);
            }

            rubroDAO DAOru = new rubroDAO();
            try {
                for (rubro suc : DAOru.todos()) {
                    String Datos[] = {String.valueOf(suc.getCodigo()), suc.getNombre()};
                    modelorubro.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablarubro.setRowSorter(new TableRowSorter(modelorubro));
            int cantFilas = tablarubro.getRowCount();
        }
    }

    private class GrillaCasa extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }
            monedaDAO DAOCASA = new monedaDAO();
            try {
                for (moneda ca : DAOCASA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre()};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private class GenerarReporte extends Thread {

        public void run() {
            int nFila = 0;
            List Resultados = new ArrayList();
            Resumencomprasxrubro tipo;
            String cCantidad = null;
            String cPrecio = null;
            String cTotal = null;
            String cPromedio = null;
            Date FechaInicio = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaFinal = ODate.de_java_a_sql(dFechaFinal.getDate());

            for (nFila = 0; nFila < jTable1.getRowCount(); nFila++) {
                cCantidad = jTable1.getValueAt(nFila, 2).toString();
                cCantidad = cCantidad.replace(".", "").replace(",", ".");

                cPrecio = jTable1.getValueAt(nFila, 3).toString();
                cPrecio = cPrecio.replace(".", "").replace(",", ".");

                cPromedio = jTable1.getValueAt(nFila, 4).toString();
                cPromedio = cPromedio.replace(".", "").replace(",", ".");

                cTotal = jTable1.getValueAt(nFila, 5).toString();
                cTotal = cTotal.replace(".", "").replace(",", ".");

                tipo = new Resumencomprasxrubro(jTable1.getValueAt(nFila, 0).toString(), jTable1.getValueAt(nFila, 1).toString(), Double.valueOf(cCantidad), Double.valueOf(cPrecio), Double.valueOf(cPromedio), Double.valueOf(cTotal), Integer.valueOf(jTable1.getValueAt(nFila, 6).toString()), jTable1.getValueAt(nFila, 7).toString());
                Resultados.add(tipo);

                System.out.println(tipo.getNombreproducto());
                System.out.println(tipo.getCantidad());
                System.out.println(tipo.getPrecio());
                System.out.println(tipo.getTotal());
                System.out.println(tipo.getRubro());
                System.out.println(tipo.getNombreproducto());

            }

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
                parameters.put("FechaInicio", FechaInicio);
                parameters.put("FechaFinal", FechaFinal);
                parameters.put("cMoneda", nombremoneda.getText());
                JasperReport jr = null;

                URL url = getClass().getClassLoader().getResource("Reports/resumen_comprasxrubro.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(Resultados));
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e, "Error", 1);
                System.out.println(e);
            }        // TODO add your handling code here:
        }
    }
}

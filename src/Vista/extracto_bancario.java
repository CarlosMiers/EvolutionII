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
import Clases.Parametros;
import DAO.bancosDAO;
import DAO.extraccionDAO;
import Modelo.ExtractoReporte;
import Modelo.banco;
import Modelo.extraccion;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.Date;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class extracto_bancario extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelobanco = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltro2;
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
    public extracto_bancario() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.listar.setEnabled(false);
        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.TitGir();
        this.Inicializar();
    }

    private void cargarTitulo() {
        modelo.addColumn("Fecha");
        modelo.addColumn("Documento");
        modelo.addColumn("N° Cheque");
        modelo.addColumn("Detalle");
        modelo.addColumn("DEBE");
        modelo.addColumn("HABER");

        int[] anchos = {90, 130, 130, 130, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.jTable1.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
    }

    private void TitGir() {
        modelobanco.addColumn("Código");
        modelobanco.addColumn("Nombre");

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

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wBuscarBanco = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        BuscarGiraduria = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        etiquetasaldos = new org.edisoncor.gui.label.LabelMetric();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        codbanco = new javax.swing.JFormattedTextField();
        nombrebanco = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        listar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        wBuscarBanco.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        wBuscarBanco.setTitle(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.wBuscarBanco.title")); // NOI18N

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combogiraduria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combogiraduria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combogiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combogiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combogiraduriaActionPerformed(evt);
            }
        });

        BuscarGiraduria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BuscarGiraduria.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.BuscarGiraduria.text")); // NOI18N
        BuscarGiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarGiraduriaActionPerformed(evt);
            }
        });
        BuscarGiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarGiraduriaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablabanco.setModel(modelobanco);
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
        jScrollPane6.setViewportView(tablabanco);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.AceptarGir.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.SalirGir.text")); // NOI18N
        SalirGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout wBuscarBancoLayout = new javax.swing.GroupLayout(wBuscarBanco.getContentPane());
        wBuscarBanco.getContentPane().setLayout(wBuscarBancoLayout);
        wBuscarBancoLayout.setHorizontalGroup(
            wBuscarBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wBuscarBancoLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        wBuscarBancoLayout.setVerticalGroup(
            wBuscarBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wBuscarBancoLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.title")); // NOI18N

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetasaldos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setForeground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.etiquetasaldos.text")); // NOI18N
        etiquetasaldos.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        generar.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.generar.text")); // NOI18N
        generar.setToolTipText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.generar.toolTipText")); // NOI18N
        generar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        salir.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.salir.text")); // NOI18N
        salir.setToolTipText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.salir.toolTipText")); // NOI18N
        salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.jPanel2.border.title"))); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.jLabel1.text")); // NOI18N

        codbanco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        codbanco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codbanco.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.codbanco.text")); // NOI18N
        codbanco.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        codbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codbancoActionPerformed(evt);
            }
        });

        nombrebanco.setEditable(false);
        nombrebanco.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        nombrebanco.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.nombrebanco.text")); // NOI18N
        nombrebanco.setEnabled(false);

        Buscar.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.Buscar.text")); // NOI18N
        Buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.jPanel4.border.title"))); // NOI18N

        dFechaInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dFechaInicialFocusGained(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.jLabel3.text")); // NOI18N

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
                .addContainerGap(32, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrebanco)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(codbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
        );

        listar.setText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.listar.text")); // NOI18N
        listar.setToolTipText(org.openide.util.NbBundle.getMessage(extracto_bancario.class, "extracto_bancario.listar.toolTipText")); // NOI18N
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
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        panelTranslucido2Layout.setVerticalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(generar)
                .addGap(3, 3, 3)
                .addComponent(listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salir)
                .addGap(31, 31, 31)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(modelo);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1155, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTranslucido1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTranslucido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        if (this.codbanco.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cuenta");
            this.codbanco.requestFocus();
            return;
        }
        generarconsulta consulta = new generarconsulta();
        Thread HiloConsulta = new Thread(consulta);
        HiloConsulta.start();        // TODO add your handling code here:
    }//GEN-LAST:event_generarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirActionPerformed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    private void BuscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarGiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarGiraduriaActionPerformed

    private void BuscarGiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarGiraduriaKeyPressed
        this.BuscarGiraduria.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (BuscarGiraduria.getText()).toUpperCase();
                BuscarGiraduria.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combogiraduria.getSelectedIndex()) {
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
        trsfiltro2 = new TableRowSorter(tablabanco.getModel());
        tablabanco.setRowSorter(trsfiltro2);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarGiraduriaKeyPressed

    private void tablabancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.codbanco.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.wBuscarBanco.setVisible(false);
        wBuscarBanco.setModal(true);
        this.dFechaInicial.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.wBuscarBanco.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        if (this.codbanco.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Banco");
            this.codbanco.requestFocus();
            return;
        }
        bancosDAO bcoDAO = new bancosDAO();
        banco ba = null;
        try {
            ba = bcoDAO.buscarId(Integer.valueOf(this.codbanco.getText()));
            if (ba.getCodigo() == 0) {
                GrillaBanco grillagi = new GrillaBanco();
                Thread hilogi = new Thread(grillagi);
                hilogi.start();
                wBuscarBanco.setModal(true);
                wBuscarBanco.setSize(482, 575);
                wBuscarBanco.setLocationRelativeTo(null);
                wBuscarBanco.setVisible(true);
                wBuscarBanco.setTitle("Buscar Giraduria");
                this.dFechaInicial.requestFocus();
                wBuscarBanco.setModal(false);
            } else {
                nombrebanco.setText(ba.getNombre());
                //Establecemos un título para el jDialog
                dFechaInicial.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarActionPerformed

    private void codbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codbancoActionPerformed
        this.Buscar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codbancoActionPerformed

    private void dFechaInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dFechaInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialFocusGained

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_listarActionPerformed
    public void filtrogira(int nNumeroColumna) {
        trsfiltro2.setRowFilter(RowFilter.regexFilter(this.BuscarGiraduria.getText(), nNumeroColumna));
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
                new extracto_bancario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton Buscar;
    private javax.swing.JTextField BuscarGiraduria;
    private javax.swing.JButton SalirGir;
    private javax.swing.JFormattedTextField codbanco;
    private javax.swing.JComboBox combogiraduria;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private org.edisoncor.gui.label.LabelMetric etiquetasaldos;
    private javax.swing.JButton generar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton listar;
    private javax.swing.JTextField nombrebanco;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JButton salir;
    private javax.swing.JTable tablabanco;
    private javax.swing.JDialog wBuscarBanco;
    // End of variables declaration//GEN-END:variables

    private class generarconsulta extends Thread {

        public void run() {
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            int nBanco = Integer.valueOf(codbanco.getText());
            String cDocumento = "";
            String cCheque="";
            String cConcepto="SD";
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            extraccionDAO exDAO = new extraccionDAO();
            try {
                for (extraccion ex : exDAO.SaldAnterior(nBanco, FechaI)) {
                    if (ex.getCredito() != 0) {
                        String Datos[] = {formatoFecha.format(ex.getFecha()), "", "", "SALDO ANTERIOR", formato.format(ex.getCredito()),"0"};
                        modelo.addRow(Datos);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            try {
                for (extraccion ct : exDAO.ExtractoxFecha(nBanco, FechaI, FechaF)) {
                    if(ct.getDocumento()!=null){
                        cDocumento=ct.getDocumento();
                    }else{
                        cDocumento="SD";
                    }
                    if(ct.getChequenro()!=null){
                        cCheque=ct.getChequenro();
                    }else{
                        cCheque="";
                    }
                    if(ct.getObservaciones()==null){
                        cConcepto="SD";
                    }else{
                        cConcepto=ct.getObservaciones();
                    }


                    String Datos[] = {formatoFecha.format(ct.getFecha()), cDocumento, cCheque, cConcepto, formato.format(ct.getCredito()), formato.format(ct.getDebito())};
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

    private class GrillaBanco extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            bancosDAO DAOBAN = new bancosDAO();
            try {
                for (banco b : DAOBAN.todos()) {
                    String Datos[] = {String.valueOf(b.getCodigo()), b.getNombre()};
                    modelobanco.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablabanco.setRowSorter(new TableRowSorter(modelobanco));
            int cantFilas = tablabanco.getRowCount();
        }
    }

    private class GenerarReporte extends Thread {

        public void run() {
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            int nFila = 0;
            List Resultados = new ArrayList();
            ExtractoReporte tipo;
            String cDebe ="0";
            String cHaber ="0";

            for (nFila = 0; nFila < jTable1.getRowCount(); nFila++) {
                cDebe = jTable1.getValueAt(nFila, 4).toString();
                cDebe = cDebe.replace(".", "").replace(",", ".");

                cHaber= jTable1.getValueAt(nFila, 5).toString();
                cHaber= cHaber.replace(".", "").replace(",", ".");

                tipo = new ExtractoReporte(jTable1.getValueAt(nFila, 0).toString().trim(), jTable1.getValueAt(nFila, 1).toString().trim(), jTable1.getValueAt(nFila, 2).toString().trim(), jTable1.getValueAt(nFila, 3).toString().trim(), Double.valueOf(cDebe),Double.valueOf(cHaber));
                Resultados.add(tipo);
            }

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte   
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nBanco",codbanco.getText());
                parameters.put("cNombreBanco",nombrebanco.getText());
                parameters.put("FechaI", FechaI);
                parameters.put("FechaF", FechaI);
                JasperReport jr = null;

                URL url = getClass().getClassLoader().getResource("Reports/extracto_bancario.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, new JRBeanCollectionDataSource(Resultados));
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

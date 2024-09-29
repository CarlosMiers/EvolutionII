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
import DAO.clienteDAO;
import DAO.cuenta_clienteDAO;
import Modelo.alquilerlocatario;
import Modelo.cliente;
import Modelo.cuenta_clientes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class alquilerxlocatario extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelopro = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocliente = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltrocli;
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
    public alquilerxlocatario() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.cliente.setText("0");
        this.idControl.setText("0");
        this.listar.setEnabled(false);
        this.clientenombre.setText("");
        this.idControl.setVisible(false);
        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.TitLocatario();
        this.Inicializar();
        cliente.requestFocus();
    }

    private void cargarTitulo() {

        modelo.addColumn("Ref");
        modelo.addColumn("N° de Documento");
        modelo.addColumn("Vencimiento");
        modelo.addColumn("Cédula/Ruc");
        modelo.addColumn("Locatario");
        modelo.addColumn("Edificio");
        modelo.addColumn("Alquiler");
        modelo.addColumn("Garage");
        modelo.addColumn("Expensa");
        modelo.addColumn("Comisión");
        modelo.addColumn("Multa");

        modelo.addColumn("código");
        modelo.addColumn("locatarioDirec");
        modelo.addColumn("locatarioTelef");
        modelo.addColumn("nomedif");
        modelo.addColumn("diredif");
        modelo.addColumn("edifctactral");
        modelo.addColumn("idinmueble");
        modelo.addColumn("numerocuota");
        modelo.addColumn("cuota");
        modelo.addColumn("codpro");
        modelo.addColumn("cedulaprop");
        modelo.addColumn("nombreprop");
        modelo.addColumn("direprop");
        modelo.addColumn("telefprop");
        int[] anchos = {0, 230, 180, 180, 350, 300, 150, 100, 120, 150, 150, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 11));

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

        this.jTable1.getColumnModel().getColumn(11).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(11).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(12).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(12).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(12).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(13).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(13).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(14).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(14).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(14).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(15).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(15).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(15).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(16).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(16).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(17).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(17).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(18).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(18).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(18).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(18).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(19).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(19).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(19).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(19).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(20).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(20).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(20).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(20).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(21).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(21).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(22).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(22).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(22).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(22).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(23).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(23).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(23).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(23).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(24).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(24).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(24).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(24).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaIzquierdo);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaDerecho);
        this.jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaDerecho);

    }

    private void TitLocatario() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("RUC");
        modelocliente.addColumn("Nombre");

        int[] anchos = {90, 80, 150};
        for (int i = 0; i < modelocliente.getColumnCount(); i++) {
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
        this.tablacliente.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.cliente.setText("0");
        this.clientenombre.setText("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BCliente = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        etiquetasaldos = new org.edisoncor.gui.label.LabelMetric();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        BuscarCliente = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        clienteruc = new javax.swing.JTextField();
        clientenombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        vencimiento = new javax.swing.JCheckBox();
        listar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Código", "Buscar por RUC", "Buscar por Nombre" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(alquilerxlocatario.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarClienteActionPerformed(evt);
            }
        });
        jTBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarClienteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacliente.setModel(modelocliente        );
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
        jScrollPane5.setViewportView(tablacliente);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(alquilerxlocatario.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(alquilerxlocatario.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        javax.swing.GroupLayout BClienteLayout = new javax.swing.GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(" Alquileres Pendiente por Locatario");

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetasaldos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setForeground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setText(" Alquileres Pendiente por Locatario");
        etiquetasaldos.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1103, Short.MAX_VALUE))
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

        jLabel1.setText("Locatario");

        cliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                clienteFocusGained(evt);
            }
        });
        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });
        cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clienteKeyPressed(evt);
            }
        });

        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        idControl.setEditable(false);

        clienteruc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        clienteruc.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        clienteruc.setEnabled(false);

        clientenombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        clientenombre.setEnabled(false);

        jLabel4.setText("Ruc");

        jLabel5.setText("Nombre");

        vencimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        vencimiento.setText("Sólo Vencidos");
        vencimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vencimientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(clientenombre)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(clienteruc))
                            .addComponent(jLabel5)
                            .addComponent(vencimiento))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clienteruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clientenombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(vencimiento)
                .addContainerGap(166, Short.MAX_VALUE))
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
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(modelo);
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
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(panelTranslucido1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        if (this.cliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Locatario");
            this.cliente.requestFocus();
            return;
        }
        GenerarConsultaAlquiler consulta1 = new GenerarConsultaAlquiler();
        Thread HiloConsulta1 = new Thread(consulta1);
        HiloConsulta1.start();        // TODO add your handling code here:
    }//GEN-LAST:event_generarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirActionPerformed

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_listarActionPerformed

    private void clienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clienteFocusGained
        cliente.selectAll();
    }//GEN-LAST:event_clienteFocusGained

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        this.BuscarCliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //valida que el usuario enter le da enter se quede en un lugar
            if (!cliente.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos

            } else {

                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Propietario ", "ATENCION", JOptionPane.CLOSED_OPTION);

            }

        }
    }//GEN-LAST:event_clienteKeyPressed

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        if (cliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No puede dejar en Blanco");
            cliente.requestFocus();
            return;
        }

        if (cliente.getText().equals("0")) {
            cliente.setText("0");
            clienteruc.setText("0");
            clientenombre.setText("Todas los Locatarios");

        } else {
            clienteDAO clDAO = new clienteDAO();
            cliente cl = null;
            try {
                cl = clDAO.buscarId(Integer.valueOf(this.cliente.getText()));
                if (cl.getCodigo() == 0) {
                    GrillaCliente grillacl = new GrillaCliente();
                    Thread hilocl = new Thread(grillacl);
                    hilocl.start();
                    BCliente.setModal(true);
                    BCliente.setSize(500, 575);
                    BCliente.setLocationRelativeTo(null);
                    BCliente.setTitle("Buscar Cliente");
                    BCliente.setVisible(true);
                    BCliente.setModal(false);
                } else {
                    clienteruc.setText(cl.getRuc());
                    clientenombre.setText(cl.getNombre());
                    //Establecemos un título para el jDialog
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void comboclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

    private void jTBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }
    private void jTBuscarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarClienteKeyPressed
        this.jTBuscarCliente.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCliente.getText()).toUpperCase();
                jTBuscarCliente.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocliente.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por ruc
                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por nombre
                }
                repaint();
                filtrocli(indiceColumnaTabla);
            }
        });
        trsfiltrocli = new TableRowSorter(tablacliente.getModel());
        tablacliente.setRowSorter(trsfiltrocli);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteKeyPressed

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
        this.cliente.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.clienteruc.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.clientenombre.setText(this.tablacliente.getValueAt(nFila, 2).toString());
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        //        this.giraduria.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void vencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vencimientoActionPerformed
        /*  if (this.vencimiento.isSelected()) {
            this.deposito.setSelected(false);
        } else {
            this.deposito.setSelected(true);
        }*/
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoActionPerformed

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
                new alquilerxlocatario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCli;
    private javax.swing.JDialog BCliente;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton SalirCli;
    private javax.swing.JTextField cliente;
    private javax.swing.JTextField clientenombre;
    private javax.swing.JTextField clienteruc;
    private javax.swing.JComboBox combocliente;
    private org.edisoncor.gui.label.LabelMetric etiquetasaldos;
    private javax.swing.JButton generar;
    private javax.swing.JTextField idControl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton listar;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JButton salir;
    private javax.swing.JTable tablacliente;
    private javax.swing.JCheckBox vencimiento;
    // End of variables declaration//GEN-END:variables

    private class GenerarConsultaAlquiler extends Thread {

        public void run() {

            Integer cVencidos = 0;
            if (vencimiento.isSelected()) {
                cVencidos = 1;
            }
            int nCliente = Integer.valueOf(cliente.getText());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            cuenta_clienteDAO DAO = new cuenta_clienteDAO();
            try {
                for (cuenta_clientes cuenta : DAO.alquilerxlocatario(nCliente, nCliente, cVencidos)) {
                    String Datos[] = {cuenta.getCreferencia(), cuenta.getDocumento(), formatoFecha.format(cuenta.getVencimiento()), String.valueOf(cuenta.getCliente().getRuc()), String.valueOf(cuenta.getCliente().getNombre()), String.valueOf(cuenta.getInmueble().getNomedif()), formato.format(cuenta.getAlquiler()), formato.format(cuenta.getGarage()), formato.format(cuenta.getExpensa()), formato.format(cuenta.getComision()), formato.format(cuenta.getMulta()), String.valueOf(cuenta.getCliente().getCodigo()), String.valueOf(cuenta.getCliente().getDireccion()), String.valueOf(cuenta.getCliente().getTelefono()), String.valueOf(cuenta.getInmueble().getNomedif()), String.valueOf(cuenta.getInmueble().getDiredif()), String.valueOf(cuenta.getInmueble().getCtactral()), String.valueOf(cuenta.getInmueble().getIdinmueble()), String.valueOf(cuenta.getNumerocuota()), String.valueOf(cuenta.getCuota()), String.valueOf(cuenta.getPropietario().getCodpro()), String.valueOf(cuenta.getPropietario().getCedula()), String.valueOf(cuenta.getPropietario().getNombreprop()), String.valueOf(cuenta.getPropietario().getDirparticular()), String.valueOf(cuenta.getPropietario().getTeleparticular())};
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

    private class GrillaCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todos()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getRuc(), cli.getNombre()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class GenerarReporte extends Thread {

        public void run() {
            int nFila = 0;
            List Resultados = new ArrayList();
            alquilerlocatario tipo;
            String cAlquiler = null;
            String cGarage = null;
            String cExpensa = null;
            String cComision = null;
            String cMulta = null;

            for (nFila = 0; nFila < jTable1.getRowCount(); nFila++) {
                cAlquiler = jTable1.getValueAt(nFila, 6).toString();
                cAlquiler = cAlquiler.replace(".", "").replace(",", ".");

                cGarage = jTable1.getValueAt(nFila, 7).toString();
                cGarage = cGarage.replace(".", "").replace(",", ".");

                cExpensa = jTable1.getValueAt(nFila, 8).toString();
                cExpensa = cExpensa.replace(".", "").replace(",", ".");

                cComision = jTable1.getValueAt(nFila, 9).toString();
                cComision = cComision.replace(".", "").replace(",", ".");
                
                cMulta = jTable1.getValueAt(nFila, 10).toString();
                cMulta = cMulta.replace(".", "").replace(",", ".");

                tipo = new alquilerlocatario(Integer.valueOf(jTable1.getValueAt(nFila, 11).toString()), jTable1.getValueAt(nFila, 4).toString(), jTable1.getValueAt(nFila, 3).toString(), jTable1.getValueAt(nFila, 12).toString(), jTable1.getValueAt(nFila, 13).toString(), Integer.valueOf(jTable1.getValueAt(nFila, 20).toString()), jTable1.getValueAt(nFila, 21).toString(), jTable1.getValueAt(nFila, 22).toString(), jTable1.getValueAt(nFila, 23).toString(), jTable1.getValueAt(nFila, 24).toString(), jTable1.getValueAt(nFila, 1).toString(), jTable1.getValueAt(nFila, 2).toString(), Integer.valueOf(jTable1.getValueAt(nFila, 17).toString()), jTable1.getValueAt(nFila, 14).toString(), jTable1.getValueAt(nFila, 16).toString(),Integer.valueOf(jTable1.getValueAt(nFila, 18).toString()),Integer.valueOf(jTable1.getValueAt(nFila, 19).toString()), Double.valueOf(cAlquiler), Double.valueOf(cGarage), Double.valueOf(cExpensa), Double.valueOf(cComision), Double.valueOf(cMulta));
                Resultados.add(tipo);

                System.out.println(tipo.getCodigo());
                System.out.println(tipo.getNombrelocatario());
                System.out.println(tipo.getRuclocatario());
                System.out.println(tipo.getLocatarioDirec());
                System.out.println(tipo.getLocatarioTelef());
                System.out.println(tipo.getCodpro());
                System.out.println(tipo.getCedulaprop());
                System.out.println(tipo.getNombreprop());
                System.out.println(tipo.getDireprop());
                System.out.println(tipo.getTelefprop());
                System.out.println(tipo.getDocumento());
                System.out.println(tipo.getVencimiento());
                System.out.println(tipo.getIdinmueble());
                System.out.println(tipo.getNomedif());
                System.out.println(tipo.getInmctactral());
                System.out.println(tipo.getNumerocuota());
                System.out.println(tipo.getCuota());
                System.out.println(tipo.getAlquiler());
                System.out.println(tipo.getGarage());
                System.out.println(tipo.getExpensa());
                System.out.println(tipo.getComision());
                System.out.println(tipo.getMulta());
                

            }

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());

                JasperReport jr = null;

                URL url = getClass().getClassLoader().getResource("Reports/alquilerxlocatario.jasper");
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

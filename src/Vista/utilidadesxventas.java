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
import DAO.productoDAO;
import DAO.rubroDAO;
import DAO.sucursalDAO;
import DAO.ventaDAO;
import Modelo.Detalleventaxproducto;
import Modelo.producto;
import Modelo.rubro;
import Modelo.sucursal;
import Modelo.venta;
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
public class utilidadesxventas extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelorubro = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocliente = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltrorubro, trsfiltrosucursal;
    ObtenerFecha ODate = new ObtenerFecha();
    String cSql, cCliente = null;

    Config configuracion = new Config();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formato = new DecimalFormat("#,###.##");

    /**
     * Creates new form libroventaconsolidado
     */
    public utilidadesxventas() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.TitRubro();
        this.TitSucursal();
        this.Inicializar();
        sucursal.requestFocus();
        GrillaRubros grillaru = new GrillaRubros();
        Thread hiloru = new Thread(grillaru);
        hiloru.start();

    }

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Nombre Producto");
        modelo.addColumn("Vendidas");
        modelo.addColumn("Ult. Costo");
        modelo.addColumn("Ult. Precio Vta");
        modelo.addColumn("Total Costo");
        modelo.addColumn("Total Venta");
        modelo.addColumn("Utilidades");

        int[] anchos = {100, 200, 120, 120, 150, 150, 150, 150};
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
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
    }

    public void filtrosucursal(int nNumeroColumna) {
        trsfiltrosucursal.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
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

    private void TitSucursal() {
        modelosucursal.addColumn("Código");
        modelosucursal.addColumn("Nombre");

        int[] anchos = {90, 100};
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
        this.tablasucursal.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
        this.rubro.setText("0");
        this.nombrerubro.setText("");
        this.sucursal.setText("0");
        this.nombresucursal.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BSucursal = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BRubro = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        comborubro = new javax.swing.JComboBox();
        jTBuscarRubro = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablarubro = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarRubro = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        etiquetasaldos = new org.edisoncor.gui.label.LabelMetric();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        listar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rubro = new javax.swing.JTextField();
        nombrerubro = new javax.swing.JTextField();
        buscarRubro = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(utilidadesxventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarSucursal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscarSucursalFocusGained(evt);
            }
        });
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

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane5.setViewportView(tablasucursal);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(utilidadesxventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(utilidadesxventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BRubro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BRubro.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comborubro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comborubro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comborubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comborubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comborubroActionPerformed(evt);
            }
        });

        jTBuscarRubro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarRubro.setText(org.openide.util.NbBundle.getMessage(utilidadesxventas.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane6.setViewportView(tablarubro);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarRubro.setText(org.openide.util.NbBundle.getMessage(utilidadesxventas.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarRubroActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(utilidadesxventas.class, "ventas.SalirCliente.text")); // NOI18N
        SalirVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarRubro)
                    .addComponent(SalirVendedor))
                .addContainerGap())
        );

        javax.swing.GroupLayout BRubroLayout = new javax.swing.GroupLayout(BRubro.getContentPane());
        BRubro.getContentPane().setLayout(BRubroLayout);
        BRubroLayout.setHorizontalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BRubroLayout.setVerticalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Utilidades por Ventas");

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetasaldos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setForeground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setText("Utilidades por Ventas");
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

        listar.setText("Vista Previa");
        listar.setToolTipText("Vista Previa");
        listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarActionPerformed(evt);
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
                .addContainerGap(33, Short.MAX_VALUE))
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

        jLabel5.setText("Sucursal");

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sucursalKeyReleased(evt);
            }
        });

        buscarSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buscarSucursalMouseClicked(evt);
            }
        });
        buscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nombrerubro, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombresucursal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(rubro, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombrerubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTranslucido2Layout = new javax.swing.GroupLayout(panelTranslucido2);
        panelTranslucido2.setLayout(panelTranslucido2Layout);
        panelTranslucido2Layout.setHorizontalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        if (this.rubro.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Producto");
            this.rubro.requestFocus();
            return;
        }
        GenerarConsultaVenta consulta1 = new GenerarConsultaVenta();
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
            this.dFechaInicial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroKeyPressed

    private void rubroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rubroKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroKeyReleased

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
            dFechaInicial.requestFocus();
        } else {
            rubroDAO ruDAO = new rubroDAO();
            rubro ru = null;
            try {
                ru = ruDAO.buscarId(Integer.valueOf(this.rubro.getText()));
                if (ru.getCodigo() == 0) {
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
                dFechaInicial.requestFocus();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarRubroActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // jTBuscarSucursal.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void sucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTBuscarSucursal.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void sucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyReleased

    private void buscarSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarSucursalMouseClicked
        jTBuscarSucursal.requestFocus();
    }//GEN-LAST:event_buscarSucursalMouseClicked

    private void buscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
        if (sucursal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No puede dejar en Blanco");
            sucursal.requestFocus();
            return;
        }
        if (sucursal.getText().equals("0")) {
            sucursal.setText("0");
            nombresucursal.setText("Todas las Sucursales");
            rubro.requestFocus();
        } else {
            sucursalDAO suDAO = new sucursalDAO();
            sucursal suc = null;
            try {
                suc = suDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                if (suc.getCodigo() == 0) {
                    GrillaSucursal grillasu = new GrillaSucursal();
                    Thread hilogi = new Thread(grillasu);
                    hilogi.start();
                    BSucursal.setModal(true);
                    BSucursal.setSize(500, 575);
                    BSucursal.setLocationRelativeTo(null);
                    BSucursal.setVisible(true);
                    BSucursal.setTitle("Buscar Sucursal");
                    BSucursal.setModal(false);
                } else {
                    nombresucursal.setText(suc.getNombre());
                    //Establecemos un título para el jDialog
                }
                rubro.requestFocus();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

    private void jTBuscarSucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscarSucursalFocusGained
        sucursal.selectAll();
    }//GEN-LAST:event_jTBuscarSucursalFocusGained

    private void jTBuscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalActionPerformed

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

                }
                repaint();
                filtrosucursal(indiceColumnaTabla);
            }
        });
        trsfiltrosucursal = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltrosucursal);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalKeyPressed

    private void tablasucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasucursalMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalMouseClicked

    private void tablasucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalKeyPressed

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void comborubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comborubroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comborubroActionPerformed

    private void jTBuscarRubroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscarRubroFocusGained
        rubro.selectAll();
    }//GEN-LAST:event_jTBuscarRubroFocusGained

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
                filtrosrubro(indiceColumnaTabla);
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

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BRubro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    public void filtrosrubro(int nNumeroColumna) {
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
                new utilidadesxventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarRubro;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JDialog BRubro;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JButton buscarRubro;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JComboBox comborubro;
    private javax.swing.JComboBox combosucursal;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private org.edisoncor.gui.label.LabelMetric etiquetasaldos;
    private javax.swing.JButton generar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTBuscarRubro;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton listar;
    private javax.swing.JTextField nombrerubro;
    private javax.swing.JTextField nombresucursal;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JTextField rubro;
    private javax.swing.JButton salir;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablarubro;
    private javax.swing.JTable tablasucursal;
    // End of variables declaration//GEN-END:variables

    private class GenerarConsultaVenta extends Thread {

        public void run() {
            int nSucursal = Integer.valueOf(sucursal.getText());
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            ventaDAO DAO = new ventaDAO();
            try {
                for (venta orden : DAO.UtilidadesxVentas(FechaI, FechaF, Integer.valueOf(rubro.getText()), nSucursal)) {
                    String Datos[] = {orden.getCodprodventa(),
                        orden.getNombreproductoventa(),
                        formato.format(orden.getCantidadventa()),
                        formato.format(orden.getCosto()),
                        formato.format(orden.getPrecioventa()),
                        formato.format(orden.getCostomercaderia()),
                        formato.format(orden.getVentasnetas()),
                        formato.format(orden.getUtilidades())};
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

    private class GrillaRubros extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelorubro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelorubro.removeRow(0);
            }

            rubroDAO DAORU = new rubroDAO();
            try {
                for (rubro ru : DAORU.todos()) {
                    String Datos[] = {String.valueOf(ru.getCodigo()), ru.getNombre()};
                    modelorubro.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablarubro.setRowSorter(new TableRowSorter(modelorubro));
            int cantFilas = tablarubro.getRowCount();
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

    private class GenerarReporte extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            Date FechaInicio = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaFinal = ODate.de_java_a_sql(dFechaFinal.getDate());

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
                parameters.put("FechaInicio", FechaInicio);
                parameters.put("FechaFinal", FechaFinal);
                parameters.put("cSucursal",sucursal.getText());
                parameters.put("cRubro", rubro.getText());

                JasperReport jr = null;

                URL url = getClass().getClassLoader().getResource("Reports/utilidadesxventas.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
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

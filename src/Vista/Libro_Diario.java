package Vista;

import Conexion.Conexion;
import Conexion.ObtenerFecha;
import Modelo.Tablas;
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
import DAO.cabecera_asientoDAO;
import DAO.sucursalDAO;
import Modelo.cabecera_asientos;
import Modelo.libro_diario_report;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class Libro_Diario extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modeloregistro = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltrosuc, trsfiltroasiento;
    ObtenerFecha ODate = new ObtenerFecha();
    Tablas modelosucursal = new Tablas();
    Tablas modeloasiento = new Tablas();
    Config configuracion = new Config();
    String cSql = null;

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formatea = new DecimalFormat("###,###.##");

    public Libro_Diario() throws SQLException {
        initComponents();
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.sucursal.setText("0");
        this.nombresucursal.setText("");
        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.cargarTituloAsiento();
        this.TitSuc();
        this.TitAsiento();
        this.Inicializar();
        this.Limpiar();
        this.listar.setEnabled(false);
        sucursal.requestFocus();

    }

    private void Limpiar() throws SQLException {
        cabecera_asientoDAO CADAO = new cabecera_asientoDAO();
        cabecera_asientos ca = null;
        ca = CADAO.consultar();

        this.numeroinicio.setText(formatosinpunto.format(ca.getNuminicio()));
        this.numerofinal.setText(formatosinpunto.format(ca.getNumfinal()));
        this.dFechaInicial.setDate(ca.getFechainicio());
        this.dFechaFinal.setDate(ca.getFechafinal());

    }

    private void cargarTitulo() {

        modeloregistro.addColumn("Asiento");
        modeloregistro.addColumn("Registro");
        modeloregistro.addColumn("Fecha");
        modeloregistro.addColumn("Documento");
        modeloregistro.addColumn("Cuenta");
        modeloregistro.addColumn("Descripción");
        modeloregistro.addColumn("Detalle");
        modeloregistro.addColumn("DEBITO");
        modeloregistro.addColumn("CREDITO");
        modeloregistro.addColumn("Sucursal");
        int[] anchos = {140, 140, 150, 200, 150, 200, 420, 150, 200, 350};
        for (int i = 0; i < modeloregistro.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.jTable1.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        TablaRendererizquierdo.setHorizontalAlignment(SwingConstants.LEFT);
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.jTable1.getColumnModel().getColumn(9).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(9).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(9).setMinWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        //this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);

    }

    private void cargarTituloAsiento() {
        modeloasiento.addColumn("Registro");
        modeloasiento.addColumn("Asiento");

        int[] anchos = {140, 140};
        for (int i = 0; i < modeloasiento.getColumnCount(); i++) {
            this.tablaasiento.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        ((DefaultTableCellRenderer) tablaasiento.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaasiento.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablaasiento.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaRendererizquierdo = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        TablaRendererizquierdo.setHorizontalAlignment(SwingConstants.LEFT);
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.tablaasiento.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablaasiento.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);

    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtroasiento(int nNumeroColumna) {
        trsfiltroasiento.setRowFilter(RowFilter.regexFilter(this.jTBuscarAsiento.getText(), nNumeroColumna));
    }

    private void TitSuc() {
        modelosucursal.addColumn("Código");
        modelosucursal.addColumn("Nombre");

        int[] anchos = {90, 200};
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
    }

    private void TitAsiento() {
        modelosucursal.addColumn("Registro");
        modelosucursal.addColumn("Asiento");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloasiento.getColumnCount(); i++) {
            tablaasiento.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaasiento.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaasiento.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaasiento.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaasiento.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {

        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
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
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BAsiento = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        comboasiento = new javax.swing.JComboBox();
        jTBuscarAsiento = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaasiento = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarAsiento = new javax.swing.JButton();
        SalirAsiento = new javax.swing.JButton();
        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jPanel1 = new javax.swing.JPanel();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        numeroinicio = new javax.swing.JTextField();
        numerofinal = new javax.swing.JTextField();
        listar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane4.setViewportView(tablasucursal);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BAsiento.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BAsiento.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboasiento.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboasiento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Asiento" }));
        comboasiento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboasiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboasientoActionPerformed(evt);
            }
        });

        jTBuscarAsiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarAsiento.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarAsiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscarAsientoFocusGained(evt);
            }
        });
        jTBuscarAsiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarAsientoActionPerformed(evt);
            }
        });
        jTBuscarAsiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarAsientoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(comboasiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboasiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaasiento.setModel(modeloasiento);
        tablaasiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaasientoMouseClicked(evt);
            }
        });
        tablaasiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaasientoKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablaasiento);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarAsiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarAsiento.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarAsiento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarAsiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarAsientoActionPerformed(evt);
            }
        });

        SalirAsiento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirAsiento.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "ventas.SalirCliente.text")); // NOI18N
        SalirAsiento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirAsiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirAsientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirAsiento, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarAsiento)
                    .addComponent(SalirAsiento))
                .addContainerGap())
        );

        javax.swing.GroupLayout BAsientoLayout = new javax.swing.GroupLayout(BAsiento.getContentPane());
        BAsiento.getContentPane().setLayout(BAsientoLayout);
        BAsientoLayout.setHorizontalGroup(
            BAsientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BAsientoLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BAsientoLayout.setVerticalGroup(
            BAsientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BAsientoLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Libro Asiento Diario");

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setBackground(new java.awt.Color(0, 0, 0));
        labelMetric1.setForeground(new java.awt.Color(0, 0, 0));
        labelMetric1.setText("Libro Diario");
        labelMetric1.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTranslucido1Layout.setVerticalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTranslucido2.setBackground(new java.awt.Color(204, 204, 204));
        panelTranslucido2.setColorPrimario(new java.awt.Color(204, 204, 204));
        panelTranslucido2.setColorSecundario(new java.awt.Color(153, 153, 153));

        generar.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "libroventaconsolidado.jButton1.text")); // NOI18N
        generar.setToolTipText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "libroventaconsolidado.jButton1.toolTipText")); // NOI18N
        generar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        jButton3.setText("Salir");
        jButton3.setToolTipText("Salir de esta Opción");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

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
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dFechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(dFechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Sucursal");

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

        nombresucursal.setEditable(false);
        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombresucursalActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "N° de Asiento"));

        jLabel4.setText("N°  Inicio");

        jLabel5.setText("N° Final");

        numeroinicio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numeroinicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroinicioActionPerformed(evt);
            }
        });
        numeroinicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numeroinicioKeyPressed(evt);
            }
        });

        numerofinal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(numerofinal)
                    .addComponent(numeroinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numeroinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numerofinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        listar.setText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "libroventaconsolidado.jButton2.text")); // NOI18N
        listar.setToolTipText(org.openide.util.NbBundle.getMessage(Libro_Diario.class, "libroventaconsolidado.jButton2.toolTipText")); // NOI18N
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
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelTranslucido2Layout.setVerticalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(generar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(listar)
                .addGap(11, 11, 11)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTable1.setModel(modeloregistro);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelTranslucido1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        //REGISTROS

        GenerarConsulta consulta1 = new GenerarConsulta();
        Thread HiloConsulta1 = new Thread(consulta1);
        HiloConsulta1.start();

// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_generarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void dFechaInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dFechaInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialFocusGained

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
        } else {

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal sucu = null;
            try {
                sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                if (sucu.getCodigo() == 0) {
                    GrillaSucursal grillasu = new GrillaSucursal();
                    Thread hilosuc = new Thread(grillasu);
                    hilosuc.start();
                    BSucursal.setModal(true);
                    BSucursal.setSize(500, 575);
                    BSucursal.setLocationRelativeTo(null);
                    BSucursal.setTitle("Buscar Sucursal");
                    BSucursal.setVisible(true);
                    BSucursal.setModal(false);
                } else {
                    nombresucursal.setText(sucu.getNombre().trim());
                }
                numeroinicio.requestFocus();
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
                        indiceColumnaTabla = 2;
                        break;//por RUC
                }
                repaint();
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltrosuc = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltrosuc);
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
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString().trim());

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void numeroinicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroinicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeroinicioActionPerformed

    private void numeroinicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroinicioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.numerofinal.requestFocus();
        }
    }//GEN-LAST:event_numeroinicioKeyPressed

    private void comboasientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboasientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboasientoActionPerformed

    private void jTBuscarAsientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscarAsientoFocusGained
        sucursal.selectAll();
    }//GEN-LAST:event_jTBuscarAsientoFocusGained

    private void jTBuscarAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarAsientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarAsientoActionPerformed

    private void jTBuscarAsientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarAsientoKeyPressed
        this.jTBuscarAsiento.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarAsiento.getText()).toUpperCase();
                jTBuscarAsiento.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboasiento.getSelectedIndex()) {
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
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltrosuc = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltrosuc);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarAsientoKeyPressed

    private void tablaasientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaasientoMouseClicked
        this.AceptarAsiento.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaasientoMouseClicked

    private void tablaasientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaasientoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarAsiento.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaasientoKeyPressed

    private void AceptarAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarAsientoActionPerformed
        /* int nFila = this.tablaasiento.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");*/

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarAsientoActionPerformed

    private void SalirAsientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirAsientoActionPerformed
        this.BAsiento.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirAsientoActionPerformed

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        GenerarLibro Generar = new GenerarLibro();
        Thread HiloReporte = new Thread(Generar);
        HiloReporte.start();
        // TODO add your handling code here:*/
    }//GEN-LAST:event_listarActionPerformed

    private void nombresucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombresucursalActionPerformed
        BAsiento.setModal(true);
        BAsiento.setSize(500, 575);
        BAsiento.setLocationRelativeTo(null);
        BAsiento.setTitle("Buscar Sucursal");
        BAsiento.setVisible(true);
        BAsiento.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombresucursalActionPerformed

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
                try {
                    new Libro_Diario().setVisible(true);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarAsiento;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JDialog BAsiento;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton SalirAsiento;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JComboBox comboasiento;
    private javax.swing.JComboBox combosucursal;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JButton generar;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTBuscarAsiento;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTable jTable1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private javax.swing.JButton listar;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField numerofinal;
    private javax.swing.JTextField numeroinicio;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablaasiento;
    private javax.swing.JTable tablasucursal;
    // End of variables declaration//GEN-END:variables

    private class GenerarConsulta extends Thread {

        public void run() {
            int nSucursal = Integer.valueOf(sucursal.getText());
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            String nInicio = String.valueOf(numeroinicio.getText());
            String nFinal = String.valueOf(numerofinal.getText());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloregistro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloregistro.removeRow(0);
            }
            cabecera_asientoDAO DAO = new cabecera_asientoDAO();

            try {
                for (cabecera_asientos ca : DAO.consultalibrodiario(FechaI, FechaF, nSucursal, nSucursal, nInicio, nFinal)) {
                    String Datos[] = {formatosinpunto.format(ca.getRownum()), formatosinpunto.format(ca.getNumero()), formatoFecha.format(ca.getFecha()), String.valueOf(ca.getAsi_numero()), ca.getPlan().getCodigo(), ca.getPlan().getNombre(), ca.getAsi_descri(), formatea.format(ca.getImpdebe()), formatea.format(ca.getImphaber()), ca.getSucursal().getNombre()};
                    modeloregistro.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            jTable1.setRowSorter(new TableRowSorter(modeloregistro));
            int cantFilas = jTable1.getRowCount();
            if (cantFilas > 0) {
                listar.setEnabled(true);
                int nasiento = 1;
                int totalRow = modeloregistro.getRowCount();
                totalRow -= 1;
                String casiento = jTable1.getValueAt(0, 1).toString();
                for (int i = 0; i <= (totalRow); i++) {
                    if (casiento.equals(jTable1.getValueAt(i, 1).toString())) {
                        jTable1.setValueAt(nasiento, i, 0);
                    } else {
                        casiento = jTable1.getValueAt(i, 1).toString();
                        nasiento++;
                        jTable1.setValueAt(nasiento, i, 0);
                    }
                }
            } else {
                listar.setEnabled(false);
            }
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

    private class GenerarLibro extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            Date FechaInicio = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaFinal = ODate.de_java_a_sql(dFechaFinal.getDate());
            BigDecimal nInicio = new BigDecimal(numeroinicio.getText());
            BigDecimal nFinal = new BigDecimal(numerofinal.getText());

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                parameters.put("cNombreEmpresa",Config.cNombreEmpresa.trim());
                parameters.put("nInicio", nInicio);
                parameters.put("nFinal", nFinal);
                parameters.put("FechaInicio", FechaInicio);
                parameters.put("FechaFinal", FechaFinal);
                parameters.put("cSucursal", Integer.valueOf(sucursal.getText()));
                parameters.put("cNombreSucursal", nombresucursal.getText());

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/libro_diario.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());

                //  if (Config.cDestinoImpresion == "") {
                //Enviar a Vista Previa
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
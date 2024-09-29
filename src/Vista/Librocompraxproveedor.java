package Vista;

import Conexion.Conexion;
import Conexion.ObtenerFecha;
import Modelo.Tablas;
import java.net.URL;
import java.sql.Date;
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
import javax.swing.JFrame;
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
import DAO.clienteDAO;
import DAO.proveedorDAO;
import DAO.sucursalDAO;
import DAO.vendedorDAO;
import DAO.ventaDAO;
import Modelo.RptLibroCompraConsolidado;
import Modelo.RptLibroVentaConsolidado;
import Modelo.cabecera_compra;
import Modelo.cliente;
import Modelo.proveedor;
import Modelo.sucursal;
import Modelo.vendedor;
import Modelo.venta;
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
public class Librocompraxproveedor extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelolibro = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltropro;
    ObtenerFecha ODate = new ObtenerFecha();
    Tablas modeloproveedor = new Tablas();
    Config configuracion = new Config();
    String cSql = null;

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formatea = new DecimalFormat("###,###.##");

    public Librocompraxproveedor() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.proveedor.setText("0");
        this.nombreproveedor.setText("");
        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.TitProveedor();
        this.Inicializar();
        this.listar.setEnabled(false);
        proveedor.requestFocus();

    }

    private void cargarTitulo() {
        modelolibro.addColumn("N° de Factura");
        modelolibro.addColumn("Fecha");
        modelolibro.addColumn("Nombre de Proveedor");
        modelolibro.addColumn("Ruc");
        modelolibro.addColumn("Gravadas 5%");
        modelolibro.addColumn("Gravadas 10%");
        modelolibro.addColumn("Exentas");
        modelolibro.addColumn("Total");
        modelolibro.addColumn("Comprobante");
        modelolibro.addColumn("Cod Comp");
        modelolibro.addColumn("Timbrado");
        int[] anchos = {200, 140, 350, 150, 200, 200, 150, 200, 200, 100, 100};
        for (int i = 0; i < modelolibro.getColumnCount(); i++) {
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

        this.jTable1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRendererizquierdo);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(9).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(9).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(9).setMinWidth(0);
    }

    public void filtropro(int nNumeroColumna) {
        trsfiltropro.setRowFilter(RowFilter.regexFilter(this.jTBuscarProveedor.getText(), nNumeroColumna));
    }

    private void TitProveedor() {
        modeloproveedor.addColumn("Código");
        modeloproveedor.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloproveedor.getColumnCount(); i++) {
            tablaproveedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproveedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproveedor.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaproveedor.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaproveedor.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
        this.proveedor.setText("0");
        this.nombreproveedor.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BProveedor = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        comboproveedor = new javax.swing.JComboBox();
        jTBuscarProveedor = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaproveedor = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        Aceptarprov = new javax.swing.JButton();
        Salirprov = new javax.swing.JButton();
        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panelTranslucido3 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        listar = new javax.swing.JButton();
        jButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        proveedor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        buscarproveedor = new javax.swing.JButton();
        nombreproveedor = new javax.swing.JTextField();

        BProveedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProveedor.setTitle("null");

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproveedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproveedorActionPerformed(evt);
            }
        });

        jTBuscarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarProveedor.setText(org.openide.util.NbBundle.getMessage(Librocompraxproveedor.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarProveedorActionPerformed(evt);
            }
        });
        jTBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarProveedorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaproveedor.setModel(modeloproveedor     );
        tablaproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproveedorMouseClicked(evt);
            }
        });
        tablaproveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaproveedorKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablaproveedor);

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptarprov.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptarprov.setText(org.openide.util.NbBundle.getMessage(Librocompraxproveedor.class, "ventas.Aceptarcliente.text")); // NOI18N
        Aceptarprov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptarprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarprovActionPerformed(evt);
            }
        });

        Salirprov.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salirprov.setText(org.openide.util.NbBundle.getMessage(Librocompraxproveedor.class, "ventas.SalirCliente.text")); // NOI18N
        Salirprov.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salirprov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirprovActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptarprov, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(Salirprov, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptarprov)
                    .addComponent(Salirprov))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProveedorLayout = new javax.swing.GroupLayout(BProveedor.getContentPane());
        BProveedor.getContentPane().setLayout(BProveedorLayout);
        BProveedorLayout.setHorizontalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProveedorLayout.setVerticalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Libro Compra Proveedor");

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setBackground(new java.awt.Color(0, 0, 0));
        labelMetric1.setForeground(new java.awt.Color(0, 0, 0));
        labelMetric1.setText("Libro IVA Compras Por Proveedor");
        labelMetric1.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1116, Short.MAX_VALUE))
        );
        panelTranslucido1Layout.setVerticalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTable1.setModel(modelolibro);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        panelTranslucido3.setBackground(new java.awt.Color(204, 204, 204));
        panelTranslucido3.setColorPrimario(new java.awt.Color(204, 204, 204));
        panelTranslucido3.setColorSecundario(new java.awt.Color(153, 153, 153));

        generar.setText(org.openide.util.NbBundle.getMessage(Librocompraxproveedor.class, "libroventaconsolidado.jButton1.text")); // NOI18N
        generar.setToolTipText(org.openide.util.NbBundle.getMessage(Librocompraxproveedor.class, "libroventaconsolidado.jButton1.toolTipText")); // NOI18N
        generar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        listar.setText(org.openide.util.NbBundle.getMessage(Librocompraxproveedor.class, "libroventaconsolidado.jButton2.text")); // NOI18N
        listar.setToolTipText(org.openide.util.NbBundle.getMessage(Librocompraxproveedor.class, "libroventaconsolidado.jButton2.toolTipText")); // NOI18N
        listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarActionPerformed(evt);
            }
        });

        jButton.setText("Salir");
        jButton.setToolTipText("Salir de esta Opción");
        jButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Rango de Fechas"));

        dFechaInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dFechaInicialFocusGained(evt);
            }
        });

        jLabel4.setText("Desde el");

        jLabel5.setText("Hasta el");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        proveedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        proveedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                proveedorFocusGained(evt);
            }
        });
        proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proveedorActionPerformed(evt);
            }
        });
        proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                proveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                proveedorKeyReleased(evt);
            }
        });

        jLabel1.setText("Proveedor");

        buscarproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarproveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buscarproveedorMouseClicked(evt);
            }
        });
        buscarproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarproveedorActionPerformed(evt);
            }
        });

        nombreproveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreproveedor.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 156, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTranslucido3Layout = new javax.swing.GroupLayout(panelTranslucido3);
        panelTranslucido3.setLayout(panelTranslucido3Layout);
        panelTranslucido3Layout.setHorizontalGroup(
            panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido3Layout.createSequentialGroup()
                .addGroup(panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTranslucido3Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelTranslucido3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        panelTranslucido3Layout.setVerticalGroup(
            panelTranslucido3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(generar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTranslucido3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(panelTranslucido3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        GenerarConsulta consulta1 = new GenerarConsulta();
        Thread HiloConsulta1 = new Thread(consulta1);
        HiloConsulta1.start();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_generarActionPerformed

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        GenerarLibro Generar = new GenerarLibro();
        Thread HiloReporte = new Thread(Generar);
        HiloReporte.start();
        // TODO add your handling code here:*/
    }//GEN-LAST:event_listarActionPerformed

    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonActionPerformed

    private void dFechaInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dFechaInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialFocusGained

    private void buscarproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarproveedorActionPerformed
        proveedorDAO proDAO = new proveedorDAO();
        proveedor pro = null;
        try {
            pro = proDAO.buscarId(Integer.valueOf(this.proveedor.getText()));
            if (pro.getCodigo() == 0) {
                GrillaProveedor grillapro = new GrillaProveedor();
                Thread hilopro = new Thread(grillapro);
                hilopro.start();
                BProveedor.setModal(true);
                BProveedor.setSize(500, 575);
                BProveedor.setLocationRelativeTo(null);
                BProveedor.setTitle("Buscar Proveedor");
                BProveedor.setVisible(true);
                BProveedor.setModal(false);
            } else {
                nombreproveedor.setText(pro.getNombre());
            }
            generar.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarproveedorActionPerformed

    private void buscarproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarproveedorMouseClicked
        jTBuscarProveedor.requestFocus();
    }//GEN-LAST:event_buscarproveedorMouseClicked

    private void proveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedorKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorKeyReleased

    private void proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTBuscarProveedor.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorKeyPressed

    private void proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorActionPerformed
        this.buscarproveedor.doClick();
        // jTBuscarSucursal.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorActionPerformed

    private void proveedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_proveedorFocusGained
        proveedor.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_proveedorFocusGained

    private void comboproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproveedorActionPerformed

    private void jTBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProveedorActionPerformed

    private void jTBuscarProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarProveedorKeyPressed
        this.jTBuscarProveedor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarProveedor.getText()).toUpperCase();
                jTBuscarProveedor.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboproveedor.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 3;
                        break;//por RUC
                }
                repaint();
                filtropro(indiceColumnaTabla);
            }
        });
        trsfiltropro = new TableRowSorter(tablaproveedor.getModel());
        tablaproveedor.setRowSorter(trsfiltropro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProveedorKeyPressed

    private void tablaproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproveedorMouseClicked
        this.Aceptarprov.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorMouseClicked

    private void tablaproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptarprov.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorKeyPressed

    private void AceptarprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarprovActionPerformed
        int nFila = this.tablaproveedor.getSelectedRow();
        this.proveedor.setText(this.tablaproveedor.getValueAt(nFila, 0).toString());
        this.nombreproveedor.setText(this.tablaproveedor.getValueAt(nFila, 1).toString());
        this.BProveedor.setVisible(false);
        this.jTBuscarProveedor.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarprovActionPerformed

    private void SalirprovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirprovActionPerformed
        this.BProveedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirprovActionPerformed

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
                new Librocompraxproveedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptarprov;
    private javax.swing.JDialog BProveedor;
    private javax.swing.JButton Salirprov;
    private javax.swing.JButton buscarproveedor;
    private javax.swing.JComboBox comboproveedor;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JButton generar;
    private javax.swing.JButton jButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTBuscarProveedor;
    private javax.swing.JTable jTable1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private javax.swing.JButton listar;
    private javax.swing.JTextField nombreproveedor;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido3;
    private javax.swing.JTextField proveedor;
    private javax.swing.JTable tablaproveedor;
    // End of variables declaration//GEN-END:variables

    private class GenerarConsulta extends Thread {

        public void run() {
            int Pro = Integer.valueOf(proveedor.getText());
            Date FechaIni = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaFin = ODate.de_java_a_sql(dFechaFinal.getDate());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelolibro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelolibro.removeRow(0);
            }
            cabecera_compraDAO DAO = new cabecera_compraDAO();

            try {
                for (cabecera_compra com : DAO.librocompraxproveedor(Pro, FechaIni, FechaFin, Pro, FechaIni, FechaFin)) {
                    String Datos[] = {com.getFormatofactura(),
                        formatoFecha.format(com.getFecha()),
                        com.getProveedor().getNombre(),
                        com.getProveedor().getRuc(),
                        formatea.format(com.getGravadas5()),
                        formatea.format(com.getGravadas10()),
                        formatea.format(com.getExentas()), 
                        formatea.format(com.getTotalneto()),
                        com.getComprobante().getNombre(),
                        String.valueOf(com.getComprobante().getCodigo()),
                        String.valueOf(com.getTimbrado())};
                    modelolibro.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            jTable1.setRowSorter(new TableRowSorter(modelolibro));
            int cantFilas = jTable1.getRowCount();
            if (cantFilas > 0) {
                listar.setEnabled(true);
            } else {
                listar.setEnabled(false);
            }
        }
    }

    private class GrillaProveedor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproveedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproveedor.removeRow(0);
            }

            proveedorDAO DAOpro = new proveedorDAO();
            try {
                for (proveedor pro : DAOpro.todos()) {
                    String Datos[] = {String.valueOf(pro.getCodigo()), pro.getNombre()};
                    modeloproveedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproveedor.setRowSorter(new TableRowSorter(modeloproveedor));
            int cantFilas = tablaproveedor.getRowCount();
        }
    }

    private class GenerarLibro extends Thread {

        public void run() {

            int nFila = 0;
            List Resultados = new ArrayList();
            RptLibroCompraConsolidado compra;
            String cGravada5 = null;
            String cIva5 = "0";
            String cGravada10 = null;
            String cIva10 = "0";
            String cExenta = null;
            String cTotal = null;

            Date FechaInicio = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaFinal = ODate.de_java_a_sql(dFechaFinal.getDate());

            for (nFila = 0; nFila < jTable1.getRowCount(); nFila++) {
                cGravada5 = jTable1.getValueAt(nFila, 4).toString();
                cGravada5 = cGravada5.replace(".", "").replace(",", ".");

                cGravada10 = jTable1.getValueAt(nFila, 5).toString();
                cGravada10 = cGravada10.replace(".", "").replace(",", ".");

                cExenta = jTable1.getValueAt(nFila, 6).toString();
                cExenta = cExenta.replace(".", "").replace(",", ".");

                if (Double.valueOf(cGravada10) != 0) {
                    cIva10 = String.valueOf(Math.round(Double.valueOf(cGravada10) / 11));
                }
                if (Double.valueOf(cGravada5) != 0) {
                    cIva5 = String.valueOf(Math.round(Double.valueOf(cGravada5) / 21));
                }

                cTotal = jTable1.getValueAt(nFila, 7).toString();
                cTotal = cTotal.replace(".", "").replace(",", ".");

                compra = new RptLibroCompraConsolidado(Double.valueOf(cIva5),
                        Double.valueOf(cIva10),
                        jTable1.getValueAt(nFila, 0).toString(),
                        jTable1.getValueAt(nFila, 1).toString(),
                        jTable1.getValueAt(nFila, 2).toString(),
                        jTable1.getValueAt(nFila, 3).toString(),
                        Double.valueOf(cGravada5) - Double.valueOf(cIva5),
                        Double.valueOf(cGravada10) - Double.valueOf(cIva10),
                        Double.valueOf(cExenta), Double.valueOf(cTotal),
                        jTable1.getValueAt(nFila, 8).toString(),
                        Integer.valueOf(jTable1.getValueAt(nFila, 9).toString()),
                        Integer.valueOf(jTable1.getValueAt(nFila, 10).toString()));
                Resultados.add(compra);
                cIva10 = "0";
                cIva5 = "0";
            }

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
                parameters.put("cNombreProveedor", nombreproveedor.getText());
                parameters.put("FechaInicio", FechaInicio);
                parameters.put("FechaFinal", FechaFinal);

                JasperReport jr = null;

                URL url = getClass().getClassLoader().getResource("Reports/Libro_Iva_Compra_Proveedor.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);// Libro_Iva_Venta.jasper
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

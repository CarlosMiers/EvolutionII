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
import Clases.UUID;
import DAO.bancosDAO;
import DAO.cartera_clientesDAO;
import DAO.config_contableDAO;
import DAO.emisorDAO;
import DAO.extraccionDAO;
import DAO.monedaDAO;
import DAO.pagoDAO;
import DAO.proveedorDAO;
import DAO.sucursalDAO;
import Modelo.banco;
import Modelo.cartera_clientes;
import Modelo.config_contable;
import Modelo.emisor;
import Modelo.extraccion;
import Modelo.moneda;
import Modelo.pago;
import Modelo.proveedor;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class resumen_vencimiento_cartera_emisor extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modeloemisor = new Tablas();
    Tablas modelomoneda = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltro2, trsfiltrocli;
    ObtenerFecha ODate = new ObtenerFecha();
    String cSql, cCliente = null;

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    DecimalFormat formatoSinPunto = new DecimalFormat("####");
    String cTotalPago, cAmortiza, cMinteres, cGastos, cMora, cPunitorio = null;

    /**
     * Creates new form libroventaconsolidado
     */
    public resumen_vencimiento_cartera_emisor() {
        initComponents();
        this.cartera.setShowGrid(false);
        this.cartera.setOpaque(true);
        this.cartera.setBackground(new Color(102, 204, 255));
        this.cartera.setForeground(Color.BLACK);
        this.emisor.setText("0");
        this.listar.setEnabled(false);
        this.nombreemisor.setText("");
        this.setLocationRelativeTo(null);
        this.cargarTituloTabla();
        this.TitGir();
        this.TitClie();
        this.Inicializar();

        GrillaCaja grillagi = new GrillaCaja();
        Thread hilogi = new Thread(grillagi);
        hilogi.start();

    }

    private void TitClie() {
        modelomoneda.addColumn("Código");
        modelomoneda.addColumn("Nombre");

        int[] anchos = {90, 150};
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
    }

    private void cargarTituloTabla() {
        modelo.addColumn("Número"); //0
        modelo.addColumn("Fecha Cierre"); //1
        modelo.addColumn("Emision");//2
        modelo.addColumn("Vence");//3
        modelo.addColumn("Comprador");//4
        modelo.addColumn("Comitente");//5
        modelo.addColumn("Nombre del Cliente");//6
        modelo.addColumn("Emisor");//7
        modelo.addColumn("Moneda");//8
        modelo.addColumn("Título");//9
        modelo.addColumn("Valor Nominal");//10
        modelo.addColumn("Valor Inversión");//11
        modelo.addColumn("Precio");//12
        modelo.addColumn("Tasa");//13
        modelo.addColumn("Plazo/días");//14
        modelo.addColumn("Tipo");//15
        modelo.addColumn("Banco");//16
        modelo.addColumn("Cuenta");//17
        modelo.addColumn("N° Pago");//18
        modelo.addColumn("Cód. Banco Plaza");//19
        modelo.addColumn("Correo");//20
        modelo.addColumn("RUC/Cédula");//21
        // 0    1   2     3    4    5   6   7    8    9    10   11  12   13   14   15     16   17   18  19  20  21
        int[] anchos = {100, 100, 100, 100, 100, 100, 150, 100, 100, 100, 100, 100, 100, 100, 100, 100, 150, 100, 100, 200, 200, 150};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            cartera.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) cartera.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        cartera.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);
        this.cartera.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(1).setCellRenderer(AlinearCentro);
        this.cartera.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.cartera.getColumnModel().getColumn(3).setCellRenderer(AlinearCentro);
        this.cartera.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(13).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        this.cartera.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

    private void TitGir() {
        modeloemisor.addColumn("Código");
        modeloemisor.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloemisor.getColumnCount(); i++) {
            tablaemisor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaemisor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaemisor.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaemisor.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaemisor.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
        this.emisor.setText("0");
        this.nombreemisor.setText("");
        this.moneda.setText("0");
        this.nombremoneda.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BuscarEmisor = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combocaja = new javax.swing.JComboBox();
        JTBuscarCaja = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaemisor = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        Aceptarcaja = new javax.swing.JButton();
        Salircaja = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        etiquetasaldos = new org.edisoncor.gui.label.LabelMetric();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cartera = new javax.swing.JTable();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        emisor = new javax.swing.JFormattedTextField();
        Buscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nombreemisor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        BuscarMoneda = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        tipotitulo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cestado = new javax.swing.JCheckBox();
        listar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        BuscarEmisor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BuscarEmisor.setTitle("Buscar Emisor");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocaja.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocaja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocajaActionPerformed(evt);
            }
        });

        JTBuscarCaja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        JTBuscarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTBuscarCajaActionPerformed(evt);
            }
        });
        JTBuscarCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTBuscarCajaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaemisor.setModel(modeloemisor        );
        tablaemisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaemisorMouseClicked(evt);
            }
        });
        tablaemisor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaemisorKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablaemisor);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptarcaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptarcaja.setText("Aceptar");
        Aceptarcaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptarcaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarcajaActionPerformed(evt);
            }
        });

        Salircaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Salircaja.setText("Salir");
        Salircaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salircaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalircajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptarcaja, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(Salircaja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptarcaja)
                    .addComponent(Salircaja))
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscarEmisorLayout = new javax.swing.GroupLayout(BuscarEmisor.getContentPane());
        BuscarEmisor.getContentPane().setLayout(BuscarEmisorLayout);
        BuscarEmisorLayout.setHorizontalGroup(
            BuscarEmisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarEmisorLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscarEmisorLayout.setVerticalGroup(
            BuscarEmisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarEmisorLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("Buscar Moneda");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablamoneda.setModel(modelomoneda        );
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
        jScrollPane5.setViewportView(tablamoneda);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText("Aceptar");
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText("Salir");
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Resumen de Vencimientos por Emisor");

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetasaldos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setForeground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setText("Resumen de Vencimientos por Emisor");
        etiquetasaldos.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1070, Short.MAX_VALUE))
        );
        panelTranslucido1Layout.setVerticalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cartera.setModel(modelo);
        cartera.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(cartera);

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

        jLabel1.setText("Emisor");

        emisor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        emisor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        emisor.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        emisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emisorActionPerformed(evt);
            }
        });

        Buscar.setText(".....");
        Buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Rango de Vencimientos"));

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
                .addContainerGap(67, Short.MAX_VALUE))
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

        nombreemisor.setEnabled(false);

        jLabel4.setText("Moneda");

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        moneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monedaActionPerformed(evt);
            }
        });

        BuscarMoneda.setText(".....");
        BuscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarMonedaActionPerformed(evt);
            }
        });

        nombremoneda.setEnabled(false);

        tipotitulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Principales", "Cupones" }));
        tipotitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipotituloActionPerformed(evt);
            }
        });

        jLabel5.setText("Seleccione");

        cestado.setText("Solo Activos");

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
                            .addComponent(nombreemisor)
                            .addComponent(nombremoneda)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(emisor, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cestado)
                            .addComponent(tipotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarMoneda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cestado)
                .addContainerGap(32, Short.MAX_VALUE))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salir)
                .addGap(60, 60, 60)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(panelTranslucido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("Opciones");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Exportar Excel Compensación");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Generar Pagos");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelTranslucido1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        if (this.emisor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Emisor");
            this.emisor.requestFocus();
            return;
        }
        ConsultaVenceEmisor consulta1 = new ConsultaVenceEmisor();
        Thread HiloConsulta1 = new Thread(consulta1);
        HiloConsulta1.start();        // TODO add your handling code here:
    }//GEN-LAST:event_generarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirActionPerformed

    private void combocajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocajaActionPerformed

    private void JTBuscarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTBuscarCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTBuscarCajaActionPerformed

    private void JTBuscarCajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTBuscarCajaKeyPressed
        this.JTBuscarCaja.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (JTBuscarCaja.getText()).toUpperCase();
                JTBuscarCaja.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocaja.getSelectedIndex()) {
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
        trsfiltro2 = new TableRowSorter(tablaemisor.getModel());
        tablaemisor.setRowSorter(trsfiltro2);
        // TODO add your handling code here:
    }//GEN-LAST:event_JTBuscarCajaKeyPressed

    private void tablaemisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaemisorMouseClicked
        this.Aceptarcaja.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaemisorMouseClicked

    private void tablaemisorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaemisorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptarcaja.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaemisorKeyPressed

    private void AceptarcajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarcajaActionPerformed
        int nFila = this.tablaemisor.getSelectedRow();
        this.emisor.setText(this.tablaemisor.getValueAt(nFila, 0).toString());
        this.nombreemisor.setText(this.tablaemisor.getValueAt(nFila, 1).toString());

        this.BuscarEmisor.setVisible(false);
        this.JTBuscarCaja.setText("");
        this.dFechaInicial.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarcajaActionPerformed

    private void SalircajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalircajaActionPerformed
        this.BuscarEmisor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalircajaActionPerformed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        BuscarEmisor.setModal(true);
        BuscarEmisor.setSize(482, 575);
        BuscarEmisor.setLocationRelativeTo(null);
        BuscarEmisor.setVisible(true);
        BuscarEmisor.setTitle("Buscar Emisor");
        BuscarEmisor.setModal(false);
        this.moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarActionPerformed

    private void emisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emisorActionPerformed

        if (emisor.getText().equals("0")) {
            emisor.setText("0");
            nombreemisor.setText("Todos los Emisores");
            moneda.requestFocus();
        } else {
            emisorDAO giDAO = new emisorDAO();
            emisor ve = null;
            try {
                ve = giDAO.buscarId(Integer.valueOf(this.emisor.getText()));
                if (ve.getCodigo() == 0) {
                    BuscarEmisor.setModal(true);
                    BuscarEmisor.setSize(482, 575);
                    BuscarEmisor.setLocationRelativeTo(null);
                    BuscarEmisor.setVisible(true);
                    BuscarEmisor.setTitle("Buscar Emisor");
                    BuscarEmisor.setModal(false);
                    this.moneda.requestFocus();
                } else {
                    nombreemisor.setText(ve.getNombre());
                    //Establecemos un título para el jDialog
                }
                moneda.requestFocus();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_emisorActionPerformed

    private void dFechaInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dFechaInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialFocusGained

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        GenerarReporteCartera GenerarReporte = new GenerarReporteCartera();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_listarActionPerformed

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        if (moneda.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No puede dejar en Blanco");
            moneda.requestFocus();
            return;
        }

        if (moneda.getText().equals("0")) {
            nombremoneda.setText("Todas las Monedas");
            dFechaInicial.requestFocus();
        } else {
            monedaDAO clDAO = new monedaDAO();
            moneda mn = null;
            try {
                mn = clDAO.buscarId(Integer.valueOf(this.moneda.getText()));
                if (mn.getCodigo() == 0) {
                    GrillaMoneda grillacl = new GrillaMoneda();
                    Thread hilocl = new Thread(grillacl);
                    hilocl.start();
                    BMoneda.setModal(true);
                    BMoneda.setSize(482, 575);
                    BMoneda.setLocationRelativeTo(null);
                    BMoneda.setTitle("Buscar Socio");
                    BMoneda.setVisible(true);
                    emisor.requestFocus();
                    BMoneda.setModal(false);
                } else {
                    nombremoneda.setText(mn.getNombre());
                    //Establecemos un título para el jDialog
                }
                this.dFechaInicial.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaActionPerformed

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
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 3;
                        break;//por RUC
                }
                repaint();
                filtrocli(indiceColumnaTabla);
            }
        });
        trsfiltrocli = new TableRowSorter(tablamoneda.getModel());
        tablamoneda.setRowSorter(trsfiltrocli);
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
        this.dFechaInicial.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        this.BuscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void tipotituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipotituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipotituloActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String nombrearchivo = "";
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            nombrearchivo = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(nombrearchivo);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("Excel Sheet");
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Nombre Proveedor"); //INT
            rowhead.createCell((short) 1).setCellValue("Importe"); //INT
            rowhead.createCell((short) 2).setCellValue("Email");//String
            rowhead.createCell((short) 3).setCellValue("Referencia Crédito");//String
            rowhead.createCell((short) 4).setCellValue("Número cuenta crédito GNB");//String
            rowhead.createCell((short) 5).setCellValue("Sub-Operación GNB");//String
            rowhead.createCell((short) 6).setCellValue("Código banco destino plaza");//String
            rowhead.createCell((short) 7).setCellValue("Número cuenta crédito plaza");//String
            rowhead.createCell((short) 8).setCellValue("Pais del documento beneficiario");//String
            rowhead.createCell((short) 9).setCellValue("Tipo de documento beneficiario");//String
            rowhead.createCell((short) 10).setCellValue("Número del documento beneficiario");//String
            rowhead.createCell((short) 11).setCellValue("Medio de operación");//String
            rowhead.createCell((short) 12).setCellValue("Cuenta");//String

            int index = modelo.getRowCount();
            index -= 1;

            for (int i = 0; i <= (index); i++) {
                HSSFRow row = sheet.createRow((short) i + 1);
                row.createCell((short) 0).setCellValue(modelo.getValueAt(i, 6).toString());
                String cImporte = modelo.getValueAt(i, 10).toString();
                cImporte = cImporte.replace(".", "").replace(",", ".");
                row.createCell((short) 1).setCellValue(Double.valueOf(cImporte));
                row.createCell((short) 2).setCellValue(modelo.getValueAt(i, 20).toString());
                row.createCell((short) 3).setCellValue("PAGO VENCIMIENTO " + modelo.getValueAt(i, 7).toString());
                row.createCell((short) 4).setCellValue("                       ");
                row.createCell((short) 5).setCellValue("                       ");
                row.createCell((short) 6).setCellValue(modelo.getValueAt(i, 19).toString());
                row.createCell((short) 7).setCellValue(modelo.getValueAt(i, 17).toString());
                row.createCell((short) 8).setCellValue("586");
                row.createCell((short) 9).setCellValue("1");
                row.createCell((short) 10).setCellValue(modelo.getValueAt(i, 21).toString());
                row.createCell((short) 11).setCellValue("ACH");
                row.createCell((short) 12).setCellValue(modelo.getValueAt(i, 4).toString());

//              row.createCell((short) 8).setCellValue(res.getDouble(9));
            }
            FileOutputStream fileOut = new FileOutputStream(nombrearchivo);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        UUID id = new UUID();
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sc = null;
        monedaDAO monDAO = new monedaDAO();
        moneda mo = null;
        extraccionDAO extracDAO = new extraccionDAO();
        extraccion pag = new extraccion();
        bancosDAO baDAO = new bancosDAO();
        banco bco = new banco();
        config_contableDAO coDAO = new config_contableDAO();
        config_contable conta = new config_contable();
        conta = coDAO.consultarCbsa();
        
        int index = modelo.getRowCount();
        index -= 1;

        for (int i = 0; i <= (index); i++) {

            String referencia = UUID.crearUUID();
            referencia = referencia.substring(1, 25);

            int nMoneda = 0;
            int nBanco = 0;
            String cCuenta = "";
            if (modelo.getValueAt(i, 8).toString().equals("GUARANIES")) {
                nMoneda = 1;
                nBanco = 10;
                cCuenta = conta.getCompensacion_gs();
            } else {
                nMoneda = 2;
                nBanco = 11;
                cCuenta = conta.getCompensacion_usd();
            }
            Date FechaProceso = ODate.de_java_a_sql(this.dFechaInicial.getDate());

            try {
                sc = sucDAO.buscarId(1);
                mo = monDAO.buscarId(nMoneda);
                bco = baDAO.buscarId(nBanco);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            try {
                FechaProceso = ODate.de_java_a_sql(formatoFecha.parse(modelo.getValueAt(i,3).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
            pag.setIdmovimiento(referencia);
            pag.setDocumento(modelo.getValueAt(i, 0).toString());
            pag.setFecha(FechaProceso);
            pag.setProveedor(0);
            pag.setMoneda(mo);
            pag.setBanco(bco);
            pag.setCotizacion(mo.getVenta());
            cTotalPago = modelo.getValueAt(i, 11).toString();
            cTotalPago = cTotalPago.replace(".", "").replace(",", ".");
            pag.setImporte(new BigDecimal(cTotalPago));
            pag.setObservaciones("PAGO VENCIMIENTO " + modelo.getValueAt(i, 7).toString() + " " + modelo.getValueAt(i, 6).toString());
            pag.setSucursal(sc);
            pag.setVencimiento(FechaProceso);
            pag.setChequenro("1");
            pag.setTipo("D");
            pag.setIdcta(cCuenta);
            try {
                extracDAO.insertarMovBancoCbsa(pag);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    public void filtrogira(int nNumeroColumna) {
        trsfiltro2.setRowFilter(RowFilter.regexFilter(this.JTBuscarCaja.getText(), nNumeroColumna));
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
                new resumen_vencimiento_cartera_emisor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton Aceptarcaja;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JButton Buscar;
    private javax.swing.JDialog BuscarEmisor;
    private javax.swing.JButton BuscarMoneda;
    private javax.swing.JTextField JTBuscarCaja;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton Salircaja;
    private javax.swing.JTable cartera;
    private javax.swing.JCheckBox cestado;
    private javax.swing.JComboBox combocaja;
    private javax.swing.JComboBox combomoneda;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JFormattedTextField emisor;
    private org.edisoncor.gui.label.LabelMetric etiquetasaldos;
    private javax.swing.JButton generar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
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
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JButton listar;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField nombreemisor;
    private javax.swing.JTextField nombremoneda;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JButton salir;
    private javax.swing.JTable tablaemisor;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JComboBox<String> tipotitulo;
    // End of variables declaration//GEN-END:variables

    private class ConsultaVenceEmisor extends Thread {

        public void run() {
            int nestado = 0;
            if (cestado.isSelected()) {
                nestado = 1;
            } else {
                nestado = 0;
            }
            int nEmisor = Integer.valueOf(emisor.getText());
            int nMoneda = Integer.valueOf(moneda.getText());
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            cartera_clientesDAO DAO = new cartera_clientesDAO();

            if (tipotitulo.getSelectedIndex() == 0) {
                try {
                    for (cartera_clientes cart : DAO.MostrarxVencimientoEmisorPrincipalCupones(nEmisor, nMoneda, FechaI, FechaF, nestado)) {
                        String Datos[] = {formatoSinPunto.format(cart.getNumero()), formatoFecha.format(cart.getFechacierre()),
                            formatoFecha.format(cart.getFechaemision()), formatoFecha.format(cart.getVencimiento()),
                            formato.format(cart.getComprador().getCodigo()),
                            formato.format(cart.getComprador().getComitente()),
                            cart.getComprador().getNombre(),
                            cart.getEmisor().getNombre(),
                            cart.getMoneda().getNombre(),
                            cart.getTitulo().getNomalias(),
                            formato.format(cart.getValor_nominal()),
                            formato.format(cart.getValor_inversion()),
                            formato.format(cart.getPrecio()),
                            formato.format(cart.getTasa()),
                            formato.format(cart.getPlazo()),
                            cart.getNombredocumento(),
                            cart.getBancopago().getNombre(),
                            cart.getCuentapago(),
                            String.valueOf(cart.getNcupon()) + "/" + String.valueOf(cart.getNcantidad()),
                            cart.getBancopago().getReporte(),
                            cart.getComprador().getMail(),
                            cart.getComprador().getRuc()};
                        modelo.addRow(Datos);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            } else if (tipotitulo.getSelectedIndex() == 1) {
                try {
                    for (cartera_clientes cart : DAO.MostrarxVencimientoEmisorPrincipal(nEmisor, nEmisor, nMoneda, nMoneda, FechaI, FechaF, nestado)) {
                        String Datos[] = {formatoSinPunto.format(cart.getNumero()), formatoFecha.format(cart.getFechacierre()),
                            formatoFecha.format(cart.getFechaemision()), formatoFecha.format(cart.getVencimiento()),
                            formato.format(cart.getComprador().getCodigo()),
                            formato.format(cart.getComprador().getComitente()),
                            cart.getComprador().getNombre(),
                            cart.getEmisor().getNombre(),
                            cart.getMoneda().getNombre(),
                            cart.getTitulo().getNomalias(),
                            formato.format(cart.getValor_nominal()),
                            formato.format(cart.getValor_inversion()),
                            formato.format(cart.getPrecio()),
                            formato.format(cart.getTasa()),
                            formato.format(cart.getPlazo()),
                            cart.getNombredocumento(),
                            cart.getBancopago().getNombre(),
                            cart.getCuentapago(),
                            String.valueOf(cart.getNcupon()) + "/" + String.valueOf(cart.getNcantidad()),
                            cart.getBancopago().getReporte(),
                            cart.getComprador().getMail(),
                            cart.getComprador().getRuc()};
                        modelo.addRow(Datos);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            } else if (tipotitulo.getSelectedIndex() == 2) {
                try {
                    for (cartera_clientes cart : DAO.MostrarxVencimientoEmisorCupones(nEmisor, nEmisor, nMoneda, nMoneda, FechaI, FechaF, nestado)) {
                        String Datos[] = {formatoSinPunto.format(cart.getNumero()), formatoFecha.format(cart.getFechacierre()),
                            formatoFecha.format(cart.getFechaemision()), formatoFecha.format(cart.getVencimiento()),
                            formato.format(cart.getComprador().getCodigo()),
                            formato.format(cart.getComprador().getComitente()),
                            cart.getComprador().getNombre(),
                            cart.getEmisor().getNombre(),
                            cart.getMoneda().getNombre(),
                            cart.getTitulo().getNomalias(),
                            formato.format(cart.getValor_nominal()),
                            formato.format(cart.getValor_inversion()),
                            formato.format(cart.getPrecio()),
                            formato.format(cart.getTasa()),
                            formato.format(cart.getPlazo()),
                            cart.getNombredocumento(),
                            cart.getBancopago().getNombre(),
                            cart.getCuentapago(),
                            String.valueOf(cart.getNcupon()) + "/" + String.valueOf(cart.getNcantidad()),
                            cart.getBancopago().getReporte(),
                            cart.getComprador().getMail(),
                            cart.getComprador().getRuc()};

                        modelo.addRow(Datos);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }

            cartera.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = cartera.getRowCount();
            if (cantFilas > 0) {
                listar.setEnabled(true);
            } else {
                listar.setEnabled(false);
            }
        }
    }

    private class GrillaCaja extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloemisor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloemisor.removeRow(0);
            }
            emisorDAO DAOCA = new emisorDAO();
            try {
                for (emisor ca : DAOCA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre()};
                    modeloemisor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaemisor.setRowSorter(new TableRowSorter(modeloemisor));
            int cantFilas = tablaemisor.getRowCount();
        }
    }

    private class GrillaMoneda extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }

            monedaDAO DAOMONE = new monedaDAO();
            try {
                for (moneda mon : DAOMONE.todos()) {
                    String Datos[] = {String.valueOf(mon.getCodigo()), mon.getNombre()};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private class GenerarReporteCartera extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            Date FechaInicio = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaFinal = ODate.de_java_a_sql(dFechaFinal.getDate());
            int nestado = 0;
            if (cestado.isSelected()) {
                nestado = 1;
            } else {
                nestado = 0;
            }

            String cReporte = "";
            if (tipotitulo.getSelectedIndex() == 0) {
                cReporte = "resumen_vencimiento_cartera_emisor00.jasper";
            } else if (tipotitulo.getSelectedIndex() == 1) {
                cReporte = "resumen_vencimiento_cartera_emisor01.jasper";
            } else {
                cReporte = "resumen_vencimiento_cartera_emisor02.jasper";
            }
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
                parameters.put("nestado", nestado);
                parameters.put("FechaInicio", FechaInicio);
                parameters.put("FechaFinal", FechaFinal);
                parameters.put("cMoneda", moneda.getText());
                parameters.put("cEmisor", emisor.getText());
                parameters.put("nAsesor", Integer.valueOf(Config.cCodigoAsesor));

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cReporte.toString());
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

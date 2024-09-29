/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ControlGrabado;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.configuracionDAO;
import DAO.giraduriaDAO;
import DAO.prestamoDAO;
import DAO.cuenta_clienteDAO;
import DAO.sucursalDAO;
import Modelo.Tablas;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.giraduria;
import Modelo.prestamo;
import Modelo.sucursal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JTextField;
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
public class prestamos_aso extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelogiraduria = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrogira;
    ObtenerFecha ODate = new ObtenerFecha();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    String cTotalImprimir = null;
    String cCliente, cReferencia, cSql = null;
    String cCodigoGastos, idPrestamo = null;
    Calendar c2 = new GregorianCalendar();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // FORMATO DE FECHA
    DecimalFormat formatea = new DecimalFormat("###,###.##");

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");

    public prestamos_aso() {
        initComponents();
        this.nrotimbrado1.setVisible(false);
        this.jButton2.setIcon(icononuevo);
        this.jButton1.setIcon(iconoeditar);
        this.jButton3.setIcon(iconoborrar);
        this.jButton5.setIcon(iconosalir);
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.importe.setHorizontalAlignment(JTextField.RIGHT);
        this.montoaprobado.setHorizontalAlignment(JTextField.RIGHT);
        this.interes.setHorizontalAlignment(JTextField.RIGHT);
        this.neto.setHorizontalAlignment(JTextField.RIGHT);
        this.imprimirfactura1.setVisible(false);
        this.numero.setVisible(false);
        this.creferencia.setVisible(false);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.Opciones.setVisible(false);
        this.cargarTitulo();
        this.TitGir();
        this.Inicializar();
        this.cargarTabla();
    }

    Control hand = new Control();

    private void TitGir() {
        modelogiraduria.addColumn("Código");
        modelogiraduria.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelogiraduria.getColumnCount(); i++) {
            tablagiraduria.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablagiraduria.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablagiraduria.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablagiraduria.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablagiraduria.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aprobar = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grabar = new javax.swing.JButton();
        saliropcion = new javax.swing.JButton();
        nroprestamo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fechaprestamo = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        nombrecliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        interes = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        montoaprobado = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tipoprestamo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        oficialcuenta = new javax.swing.JTextField();
        neto = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel30 = new javax.swing.JLabel();
        totalprestamo = new javax.swing.JFormattedTextField();
        jLabel31 = new javax.swing.JLabel();
        rechazar = new javax.swing.JButton();
        importecuota = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        condicionar = new javax.swing.JButton();
        genfacturacapital = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        salirfactura1 = new javax.swing.JButton();
        nroprestamo1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        fechaprestamo1 = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        grabarfactura1 = new javax.swing.JButton();
        nombrecliente1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        moneda1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        intordinario = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        nrofactura1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        observacion1 = new javax.swing.JTextArea();
        imprimirfactura1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        importegasto = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        comisiones = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        seguro = new javax.swing.JFormattedTextField();
        genfacturaotros = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        salirfactura2 = new javax.swing.JButton();
        nroprestamo2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        fechaprestamo2 = new com.toedter.calendar.JDateChooser();
        grabarfactura2 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        nombrecliente2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        moneda2 = new javax.swing.JTextField();
        lblgastos = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        nrofactura2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacion2 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        interesordinario = new javax.swing.JFormattedTextField();
        capitalafacturar = new javax.swing.JFormattedTextField();
        lblgastos1 = new javax.swing.JLabel();
        gastosadministrativos = new javax.swing.JFormattedTextField();
        lblgastos2 = new javax.swing.JLabel();
        nrotimbrado = new javax.swing.JTextField();
        vencetimbrado = new com.toedter.calendar.JDateChooser();
        importefactura = new javax.swing.JFormattedTextField();
        nrotimbrado1 = new javax.swing.JTextField();
        debito = new javax.swing.JDialog();
        labelTask1 = new org.edisoncor.gui.label.LabelTask();
        jPanel7 = new javax.swing.JPanel();
        codgiraduria = new javax.swing.JTextField();
        nombregiraduria = new javax.swing.JTextField();
        nrocuenta = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        buscarcodgiraduria = new javax.swing.JButton();
        BotonConfirma = new javax.swing.JButton();
        SalirDebito = new javax.swing.JButton();
        numero = new javax.swing.JTextField();
        creferencia = new javax.swing.JTextField();
        autorizacion = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        BGiraduria = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        jTBuscarGiraduria = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablagiraduria = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        Opciones = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        ordenpago = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        facturacapital = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        facturainteresprestamo = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("N° Préstamo");

        grabar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabar.setText("Aprobar");
        grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarActionPerformed(evt);
            }
        });

        saliropcion.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        saliropcion.setText("Salir");
        saliropcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saliropcionActionPerformed(evt);
            }
        });

        nroprestamo.setEditable(false);
        nroprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel3.setText("Fecha");

        fechaprestamo.setBackground(new java.awt.Color(255, 255, 255));
        fechaprestamo.setForeground(new java.awt.Color(255, 255, 255));
        fechaprestamo.setEnabled(false);
        fechaprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel4.setText("Nombre Cliente");

        nombrecliente.setEditable(false);
        nombrecliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        nombrecliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nombrecliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nombreclienteMouseClicked(evt);
            }
        });

        jLabel5.setText("Moneda");

        moneda.setEditable(false);
        moneda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel6.setText("Capital");

        importe.setEditable(false);
        importe.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel7.setText("Interés Ordinario");

        interes.setEditable(false);
        interes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel8.setText("Total Préstamo");

        montoaprobado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        montoaprobado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        montoaprobado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        montoaprobado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                montoaprobadoActionPerformed(evt);
            }
        });

        jLabel9.setText("Monto a Entregar");

        jLabel10.setText("Tipo Préstamo");

        tipoprestamo.setEditable(false);
        tipoprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel11.setText("Oficial de Cuenta");

        oficialcuenta.setEditable(false);
        oficialcuenta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        neto.setEditable(false);
        neto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane4.setViewportView(observaciones);

        jLabel30.setText("Importe Aprobado");

        totalprestamo.setEditable(false);
        totalprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalprestamo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel31.setText("Observaciones");

        rechazar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        rechazar.setText("Rechazar");
        rechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechazarActionPerformed(evt);
            }
        });

        importecuota.setEditable(false);
        importecuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importecuota.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel32.setText("Cuota Préstamo");

        condicionar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        condicionar.setText("Condicionar");
        condicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                condicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(grabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(condicionar)
                        .addGap(18, 18, 18)
                        .addComponent(rechazar)
                        .addGap(26, 26, 26)
                        .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel7)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombrecliente)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(interes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                                .addComponent(importe, javax.swing.GroupLayout.Alignment.LEADING))
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(nroprestamo, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(fechaprestamo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                                        .addGap(0, 117, Short.MAX_VALUE)))
                                .addGap(88, 88, 88))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(oficialcuenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                        .addComponent(tipoprestamo, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(neto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(montoaprobado, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(importecuota, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(totalprestamo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(26, 26, 26))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(fechaprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(interes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(totalprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(neto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(oficialcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(montoaprobado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saliropcion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rechazar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(condicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout aprobarLayout = new javax.swing.GroupLayout(aprobar.getContentPane());
        aprobar.getContentPane().setLayout(aprobarLayout);
        aprobarLayout.setHorizontalGroup(
            aprobarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        aprobarLayout.setVerticalGroup(
            aprobarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setText("N° Préstamo");

        salirfactura1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        salirfactura1.setText("Salir");
        salirfactura1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirfactura1ActionPerformed(evt);
            }
        });

        nroprestamo1.setEditable(false);
        nroprestamo1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel13.setText("Fecha");

        fechaprestamo1.setBackground(new java.awt.Color(255, 255, 255));
        fechaprestamo1.setForeground(new java.awt.Color(255, 255, 255));
        fechaprestamo1.setEnabled(false);

        jLabel14.setText("Nombre Cliente");

        grabarfactura1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabarfactura1.setText("Grabar Factura");
        grabarfactura1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarfactura1ActionPerformed(evt);
            }
        });

        nombrecliente1.setEditable(false);
        nombrecliente1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel15.setText("Moneda");

        moneda1.setEditable(false);
        moneda1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel16.setText("Interés Ordinario");

        intordinario.setEditable(false);
        intordinario.setForeground(new java.awt.Color(255, 0, 0));
        intordinario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel17.setText("Nº Factura");

        observacion1.setColumns(20);
        observacion1.setRows(5);
        jScrollPane2.setViewportView(observacion1);

        imprimirfactura1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        imprimirfactura1.setText("Imprimir Factura");
        imprimirfactura1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirfactura1ActionPerformed(evt);
            }
        });

        jLabel18.setText("Observaciones al píe del Comprobante");

        jLabel23.setText("Gastos Administrativos");

        importegasto.setEditable(false);
        importegasto.setForeground(new java.awt.Color(255, 0, 0));
        importegasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel26.setText("Comisiones Varias");

        comisiones.setEditable(false);
        comisiones.setForeground(new java.awt.Color(255, 0, 0));
        comisiones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel27.setText("Seguro de Vida");

        seguro.setEditable(false);
        seguro.setForeground(new java.awt.Color(255, 0, 0));
        seguro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(grabarfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(imprimirfactura1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(salirfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombrecliente1)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(moneda1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                            .addComponent(intordinario, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(88, 88, 88))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13))
                                        .addGap(82, 82, 82)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nroprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fechaprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(88, 88, 88)
                                            .addComponent(nrofactura1))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel26)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comisiones, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel23)
                                            .addGap(31, 31, 31)
                                            .addComponent(importegasto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel27)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(seguro, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(237, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jScrollPane2)
                                .addContainerGap())))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(139, 139, 139))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel12))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nroprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(nombrecliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(moneda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(intordinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(importegasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(comisiones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(seguro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nrofactura1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(52, 52, 52)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salirfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grabarfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imprimirfactura1))
                .addContainerGap())
        );

        javax.swing.GroupLayout genfacturacapitalLayout = new javax.swing.GroupLayout(genfacturacapital.getContentPane());
        genfacturacapital.getContentPane().setLayout(genfacturacapitalLayout);
        genfacturacapitalLayout.setHorizontalGroup(
            genfacturacapitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, genfacturacapitalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        genfacturacapitalLayout.setVerticalGroup(
            genfacturacapitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(genfacturacapitalLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setText("N° Préstamo");

        salirfactura2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        salirfactura2.setText("Salir");
        salirfactura2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirfactura2ActionPerformed(evt);
            }
        });

        nroprestamo2.setEditable(false);
        nroprestamo2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel20.setText("Fecha");

        fechaprestamo2.setBackground(new java.awt.Color(255, 255, 255));
        fechaprestamo2.setForeground(new java.awt.Color(255, 255, 255));
        fechaprestamo2.setEnabled(false);

        grabarfactura2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabarfactura2.setText("Grabar Factura");
        grabarfactura2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarfactura2ActionPerformed(evt);
            }
        });

        jLabel21.setText("Nombre Cliente");

        nombrecliente2.setEditable(false);
        nombrecliente2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel22.setText("Moneda");

        moneda2.setEditable(false);
        moneda2.setForeground(new java.awt.Color(255, 0, 0));

        lblgastos.setText("Interés Ordinario");

        jLabel24.setText("Nº Factura");

        nrofactura2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        observacion2.setColumns(20);
        observacion2.setRows(5);
        jScrollPane3.setViewportView(observacion2);

        jButton7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton7.setText("Imprimir Factura");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel25.setText("Observaciones al píe del Comprobante");

        interesordinario.setEditable(false);
        interesordinario.setForeground(new java.awt.Color(255, 0, 0));
        interesordinario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        interesordinario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        capitalafacturar.setEditable(false);
        capitalafacturar.setForeground(new java.awt.Color(255, 0, 0));
        capitalafacturar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        capitalafacturar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lblgastos1.setText("Capital");

        gastosadministrativos.setEditable(false);
        gastosadministrativos.setForeground(new java.awt.Color(255, 0, 0));
        gastosadministrativos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gastosadministrativos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lblgastos2.setText("Gastos Administrativos");

        nrotimbrado.setEditable(false);
        nrotimbrado.setEnabled(false);

        vencetimbrado.setBackground(new java.awt.Color(255, 255, 255));
        vencetimbrado.setForeground(new java.awt.Color(255, 255, 255));
        vencetimbrado.setEnabled(false);

        importefactura.setEditable(false);
        importefactura.setForeground(new java.awt.Color(255, 0, 0));
        importefactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        importefactura.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        nrotimbrado1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(grabarfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(salirfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblgastos1)
                                .addComponent(jLabel22))
                            .addComponent(capitalafacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(fechaprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(moneda2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(lblgastos))
                                            .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(gastosadministrativos, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblgastos2)
                                                    .addComponent(importefactura, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(nrotimbrado1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(nombrecliente2))
                                .addGap(88, 88, 88))))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nrotimbrado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneda2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblgastos2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblgastos1)
                        .addComponent(lblgastos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capitalafacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gastosadministrativos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(importefactura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salirfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grabarfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        javax.swing.GroupLayout genfacturaotrosLayout = new javax.swing.GroupLayout(genfacturaotros.getContentPane());
        genfacturaotros.getContentPane().setLayout(genfacturaotrosLayout);
        genfacturaotrosLayout.setHorizontalGroup(
            genfacturaotrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        genfacturaotrosLayout.setVerticalGroup(
            genfacturaotrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        labelTask1.setText(org.openide.util.NbBundle.getMessage(prestamos_aso.class, "actualizar_web.labelTask1.text")); // NOI18N
        labelTask1.setDescription(org.openide.util.NbBundle.getMessage(prestamos_aso.class, "actualizar_web.labelTask1.description")); // NOI18N

        codgiraduria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codgiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codgiraduriaActionPerformed(evt);
            }
        });

        nombregiraduria.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        nrocuenta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        nrocuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrocuentaKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Giraduría");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Descripción");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("N° Cuenta");

        buscarcodgiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcodgiraduriaActionPerformed(evt);
            }
        });

        BotonConfirma.setText("Confirmar Débito Automático");
        BotonConfirma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonConfirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonConfirmaActionPerformed(evt);
            }
        });

        SalirDebito.setText("Salir");
        SalirDebito.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirDebitoActionPerformed(evt);
            }
        });

        numero.setEnabled(false);

        creferencia.setEnabled(false);

        autorizacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        autorizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                autorizacionKeyPressed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("N° Autorización");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(BotonConfirma)
                        .addGap(68, 68, 68)
                        .addComponent(SalirDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel1)
                            .addComponent(jLabel29)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(autorizacion)
                                .addGap(31, 31, 31)
                                .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(codgiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(buscarcodgiraduria))
                                .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(numero, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nrocuenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codgiraduria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarcodgiraduria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nrocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(19, 19, 19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(autorizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonConfirma)
                    .addComponent(SalirDebito))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout debitoLayout = new javax.swing.GroupLayout(debito.getContentPane());
        debito.getContentPane().setLayout(debitoLayout);
        debitoLayout.setHorizontalGroup(
            debitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTask1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        debitoLayout.setVerticalGroup(
            debitoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(debitoLayout.createSequentialGroup()
                .addComponent(labelTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BGiraduria.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BGiraduria.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combogiraduria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combogiraduria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combogiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combogiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combogiraduriaActionPerformed(evt);
            }
        });

        jTBuscarGiraduria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarGiraduria.setText(org.openide.util.NbBundle.getMessage(prestamos_aso.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarGiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarGiraduriaActionPerformed(evt);
            }
        });
        jTBuscarGiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarGiraduriaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablagiraduria.setModel(modelogiraduria);
        tablagiraduria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablagiraduriaMouseClicked(evt);
            }
        });
        tablagiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablagiraduriaKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablagiraduria);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(prestamos_aso.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(prestamos_aso.class, "ventas.SalirCliente.text")); // NOI18N
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

        javax.swing.GroupLayout BGiraduriaLayout = new javax.swing.GroupLayout(BGiraduria.getContentPane());
        BGiraduria.getContentPane().setLayout(BGiraduriaLayout);
        BGiraduriaLayout.setHorizontalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BGiraduriaLayout.setVerticalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton1.setText("Editar Registro");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton2.setText(" Agregar Registro");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton3.setText("Anular Préstamo");
        jButton3.setToolTipText("");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton5.setText("     Salir");
        jButton5.setToolTipText("");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        Opciones.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar entre los Días"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(FechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        jButton6.setText("Refrescar");
        jButton6.setActionCommand("Filtrar");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(Opciones, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(53, 53, 53)
                .addComponent(Opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setText("Préstamos");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del Cliente", "N° de Operación", "Estado" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setSelectionColor(new java.awt.Color(0, 63, 62));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jMenu1.setText("Menú Préstamos");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem3.setText("Solicitud de Crédito");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator5);

        jMenuItem4.setText("Aprobar Préstamo");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem4MouseClicked(evt);
            }
        });
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator4);

        jMenuItem7.setText("Datos del Cliente");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);
        jMenu1.add(jSeparator10);

        jMenuItem2.setText("Liquidación del Préstamo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator1);

        jMenuItem8.setText("Contrato");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);
        jMenu1.add(jSeparator3);

        jMenuItem1.setText("Emitir Pagaré");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator2);

        ordenpago.setText("Orden de Pago");
        ordenpago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordenpagoActionPerformed(evt);
            }
        });
        jMenu1.add(ordenpago);
        jMenu1.add(jSeparator6);

        facturacapital.setText("Facturar  Comisiones-Interés-Gastos");
        facturacapital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturacapitalActionPerformed(evt);
            }
        });
        jMenu1.add(facturacapital);
        jMenu1.add(jSeparator7);

        facturainteresprestamo.setText("Facturar Capital-Interés-Gastos");
        facturainteresprestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturainteresprestamoActionPerformed(evt);
            }
        });
        jMenu1.add(facturainteresprestamo);
        jMenu1.add(jSeparator9);

        jMenuItem5.setText("Débito Automático");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator8);

        jMenuItem6.setText("Tarifario de Gastos");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        this.jTextField1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTextField1.getText()).toUpperCase();
                jTextField1.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 3;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 12;
                        break;//por Estado
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "";
        String cOpcion = "new";
        try {
            new detalle_prestamos_asociaciones(cOpcion).setVisible(true);//FAPASA
            //        new detalle_prestamos_asolac(cOpcion).setVisible(true);
            //          new detalle_prestamos_sinatram(cOpcion).setVisible(true);
            //        new detalle_prestamos_carlyle2(cOpcion).setVisible(true);//CARLYLE
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "";
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        String cEstado = this.jTable1.getValueAt(nFila, 12).toString();

        if (Integer.valueOf(Config.cNivelUsuario) != 1) {
            if (cEstado.equals("DESEMBOLSADO")) {
                JOptionPane.showMessageDialog(null, "El Préstamo ya fue Desembolsado ");
                return;
            }
            if (cEstado.equals("RECHAZADO")) {
                JOptionPane.showMessageDialog(null, "El Préstamo fue Rechazado ");
                return;
            }
        }

        String cOpcion = this.Opciones.getText();
        try {
            //         new detalle_prestamos_sinatram(cOpcion).setVisible(true);

            new detalle_prestamos_asociaciones(cOpcion).setVisible(true);//FAPASA
            //         new detalle_prestamos_asolac(cOpcion).setVisible(true);
            //       new detalle_prestamos_carlyle2(cOpcion).setVisible(true);//FAPASA
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusGained

    private void jScrollPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if (ControlGrabado.REGISTRO_GRABADO == "SI") {
            this.cargarTabla();
            ControlGrabado.REGISTRO_GRABADO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        String cNumero = this.jTable1.getValueAt(nFila, 1).toString();
        String cReferencia = this.jTable1.getValueAt(nFila, 0).toString();
        if (Config.cNivelUsuario.equals("1")) {
            if (!this.Opciones.getText().isEmpty()) {
                BDConexion BD = new BDConexion();
                BD.borrarRegistro("detalle_prestamo", "nprestamo=" + cNumero);
                BD.BorrarDetalles("prestamos", "numero=" + cNumero);
                this.cargarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar un Préstamo");
            return;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.cargarTabla();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GenerarPagare GenerarPagare = new GenerarPagare();
        Thread HiloPagare = new Thread(GenerarPagare);
        HiloPagare.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        GenerarLiquidacion GenerarLiquidacion = new GenerarLiquidacion();
        Thread HiloLiquidacion = new Thread(GenerarLiquidacion);
        HiloLiquidacion.start();        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void ordenpagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordenpagoActionPerformed
        GenerarOp OrdenPago = new GenerarOp();
        Thread HiloOrdenPago = new Thread(OrdenPago);
        HiloOrdenPago.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_ordenpagoActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        this.aprobar.setSize(623, 630);
        int nFila = this.jTable1.getSelectedRow();
        String cEstado = this.jTable1.getValueAt(nFila, 12).toString();
        if (cEstado.equals("APROBADO") || cEstado.equals("DESEMBOLSADO") || cEstado.equals("RECHAZADO")) {
            JOptionPane.showMessageDialog(null, "El Préstamo ya fue Aprobado o Desembolsado");
            return;
        }
        this.nroprestamo.setText(this.jTable1.getValueAt(nFila, 1).toString());
        try {
            this.fechaprestamo.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.nombrecliente.setText(this.jTable1.getValueAt(nFila, 3).toString().trim());
        this.moneda.setText(this.jTable1.getValueAt(nFila, 4).toString().trim());
        this.importe.setText(this.jTable1.getValueAt(nFila, 5).toString());
        this.interes.setText(this.jTable1.getValueAt(nFila, 6).toString());
        this.importecuota.setText(this.jTable1.getValueAt(nFila, 20).toString());
        this.totalprestamo.setText(this.jTable1.getValueAt(nFila, 7).toString());
        this.montoaprobado.setText("0");
        this.neto.setText(this.jTable1.getValueAt(nFila, 8).toString());
        this.tipoprestamo.setText(this.jTable1.getValueAt(nFila, 10).toString().trim());
        this.oficialcuenta.setText(this.jTable1.getValueAt(nFila, 11).toString().trim());
        this.grabar.setText("Aprobar");
        aprobar.setTitle("Aprobar Préstamo");
        aprobar.setLocationRelativeTo(null);
        aprobar.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void saliropcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saliropcionActionPerformed
        aprobar.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_saliropcionActionPerformed

    private void grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            String cAprobar = "APROBADO";
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Aprobar el Préstamo Seleccionado? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                con = new Conexion();
                stm = con.conectar();
                ResultSet results = null;
                String cMontoAprobado = this.montoaprobado.getText();
                cMontoAprobado = cMontoAprobado.replace(".", "");
                cMontoAprobado = cMontoAprobado.replace(",", ".");
                String cSqlAprobar = "UPDATE prestamos  SET estado='" + cAprobar + "',aprobadopor='" + Config.CodUsuario + "',montoaprobado='" + cMontoAprobado + "',observaciones='" + this.observaciones.getText();
                cSqlAprobar += "' WHERE numero=" + this.Opciones.getText();
                try {
                    stm.executeUpdate(cSqlAprobar);
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Aprobar un Préstamo");
            return;
        }
        this.aprobar.setVisible(false);
    }//GEN-LAST:event_grabarActionPerformed

    private void facturainteresprestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturainteresprestamoActionPerformed

        GenFacturaGastos GenFacGastos = new GenFacturaGastos();
        Thread HiloGastos = new Thread(GenFacGastos);
        HiloGastos.start();
        int nsucu = 1;
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(1);
            String cBoca = sucu.getExpedicion().trim();
            Double nFactura = sucu.getFactura()+1;
            int n = (int) nFactura.doubleValue();
            String formatString = String.format("%%0%dd", 7);
            String formattedString = String.format(formatString, n);
            this.nrofactura2.setText(cBoca + "-" + formattedString);
            vencetimbrado.setDate(sucu.getVencetimbrado());
            nrotimbrado1.setText(sucu.getNrotimbrado());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        this.genfacturaotros.setSize(554, 467);
        int nFila = this.jTable1.getSelectedRow();
        cReferencia = this.jTable1.getValueAt(nFila, 0).toString();

        this.vencetimbrado.setVisible(false);
        this.nrotimbrado.setVisible(false);
        this.nroprestamo2.setText(this.jTable1.getValueAt(nFila, 1).toString());
        cCliente = this.jTable1.getValueAt(nFila, 13).toString();
        cCodigoGastos = this.jTable1.getValueAt(nFila, 16).toString();
        try {
            this.fechaprestamo2.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.nombrecliente2.setText(this.jTable1.getValueAt(nFila, 3).toString().trim());
        this.moneda2.setText(this.jTable1.getValueAt(nFila, 4).toString().trim());
        this.capitalafacturar.setText(this.jTable1.getValueAt(nFila, 5).toString());
        this.interesordinario.setText(this.jTable1.getValueAt(nFila, 6).toString());
        this.gastosadministrativos.setText(this.jTable1.getValueAt(nFila, 15).toString());
        genfacturaotros.setTitle("Generar Factura");
        genfacturaotros.setLocationRelativeTo(null);
        genfacturaotros.setVisible(true);

        // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_facturainteresprestamoActionPerformed

    private void grabarfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarfactura1ActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Factura del Capital? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GrabarDetalleCapital GrabarDet = new GrabarDetalleCapital();
            Thread HiloGrabarDet = new Thread(GrabarDet);
            HiloGrabarDet.start();
            JOptionPane.showMessageDialog(null, "Factura Generada con Éxito");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_grabarfactura1ActionPerformed

    private void salirfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfactura1ActionPerformed
        genfacturacapital.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirfactura1ActionPerformed

    private void facturacapitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturacapitalActionPerformed
        this.genfacturacapital.setSize(563, 550);
        int nFila = this.jTable1.getSelectedRow();
        cReferencia = this.jTable1.getValueAt(nFila, 0).toString();

        GenFacturaCapital GenFacCapital = new GenFacturaCapital();
        Thread HiloCapital = new Thread(GenFacCapital);
        HiloCapital.start();

        this.nroprestamo1.setText(this.jTable1.getValueAt(nFila, 1).toString());
        cCliente = this.jTable1.getValueAt(nFila, 13).toString();
        cCodigoGastos = this.jTable1.getValueAt(nFila, 16).toString();

        try {
            this.fechaprestamo1.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.nombrecliente1.setText(this.jTable1.getValueAt(nFila, 3).toString().trim());
        this.moneda1.setText(this.jTable1.getValueAt(nFila, 4).toString().trim());
        this.intordinario.setText(this.jTable1.getValueAt(nFila, 6).toString());
        this.importegasto.setText(this.jTable1.getValueAt(nFila, 15).toString());
        this.comisiones.setText(this.jTable1.getValueAt(nFila, 18).toString());
        this.seguro.setText(this.jTable1.getValueAt(nFila, 19).toString());

        genfacturacapital.setTitle("Generar Factura");
        genfacturacapital.setLocationRelativeTo(null);
        genfacturacapital.setVisible(true);

// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_facturacapitalActionPerformed

    private void imprimirfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirfactura1ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        String cSqlControl = "SELECT creferencia,totalneto FROM cabecera_ventas WHERE creferencia='" + this.Opciones.getText() + "'";
        try {
            results = stm.executeQuery(cSqlControl);
            while (results.next()) {
                cReferencia = results.getString("creferencia");
                cTotalImprimir = results.getString("totalneto");
            }
            results.close();
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (cReferencia.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La Factura aún no fue Generada");
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Factura? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            ImprimirFacturaCapital GenerarReporte1 = new ImprimirFacturaCapital();
            Thread HiloReporte = new Thread(GenerarReporte1);
            HiloReporte.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirfactura1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        cReferencia = "";
        cTotalImprimir = "";
        int nFila = this.jTable1.getSelectedRow();
        cReferencia = (this.jTable1.getValueAt(nFila, 0).toString());
        String cSqlControl2 = "SELECT creferencia,totalneto FROM cabecera_ventas WHERE creferencia='" + cReferencia.toString() + "'";
        try {
            results = stm.executeQuery(cSqlControl2);
            while (results.next()) {
                cTotalImprimir = results.getString("totalneto");
                importefactura.setText(results.getString("totalneto"));
            }
            results.close();
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        System.out.println("Total Imprimir " + cTotalImprimir);
        if (cTotalImprimir == null) {
            JOptionPane.showMessageDialog(null, "La Factura aún no fue Generada");
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Factura? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            ImprimirFactura GenerarFactura = new ImprimirFactura();
            Thread HiloReporte = new Thread(GenerarFactura);
            HiloReporte.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void grabarfactura2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarfactura2ActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Factura por Gastos? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
//            GrabarDetalleOtros GrabarGastos = new GrabarDetalleOtros();
            GrabarDetalleAsolac GrabarGastos = new GrabarDetalleAsolac();
            Thread HiloGrabarGastos = new Thread(GrabarGastos);
            HiloGrabarGastos.start();
            JOptionPane.showMessageDialog(null, "Factura Generada con Éxito");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_grabarfactura2ActionPerformed

    private void salirfactura2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfactura2ActionPerformed
        this.genfacturaotros.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirfactura2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        ImprimirSolicitud ImpSolicitud = new ImprimirSolicitud();
        Thread HiloSolicitud = new Thread(ImpSolicitud);
        HiloSolicitud.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cReferencia", cReferencia);
            JasperReport jr = null;
            // URL url = getClass().getClassLoader().getResource("Reports/liquidacion_prestamos_iva.jasper");
            URL url = getClass().getClassLoader().getResource("Reports/gastosxmora.jasper");
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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        this.debito.setSize(525, 347);
        this.debito.setModal(true);
        int nFila = this.jTable1.getSelectedRow();
        this.creferencia.setText(this.jTable1.getValueAt(nFila, 0).toString());
        this.numero.setText(this.jTable1.getValueAt(nFila, 1).toString());
        String idpre = this.jTable1.getValueAt(nFila, 1).toString();
        prestamoDAO pr1 = new prestamoDAO();
        prestamo p = null;
        try {
            p = pr1.buscarId(Integer.valueOf(idpre));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (p.getNrocuenta() != null) {
            codgiraduria.setText(String.valueOf(p.getGiraduria().getCodigo()));
            nombregiraduria.setText(p.getGiraduria().getNombre());
            nrocuenta.setText(p.getNrocuenta());
        } else {
            codgiraduria.setText("0");
            nombregiraduria.setText("");
            nrocuenta.setText("");
        }
        debito.setTitle("Aprobar Préstamo");
        debito.setLocationRelativeTo(null);
        debito.setVisible(true);        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void SalirDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirDebitoActionPerformed
        debito.setModal(false);
        debito.setVisible(false);
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirDebitoActionPerformed

    private void buscarcodgiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcodgiraduriaActionPerformed
        giraduriaDAO girDAO = new giraduriaDAO();
        giraduria gi = null;
        try {
            gi = girDAO.buscarId(Integer.valueOf(this.codgiraduria.getText()));
            if (gi.getCodigo() == 0) {
                GrillaBGiraduria grillagi = new GrillaBGiraduria();
                Thread hilogi = new Thread(grillagi);
                hilogi.start();
                BGiraduria.setModal(true);
                BGiraduria.setSize(482, 575);
                BGiraduria.setLocationRelativeTo(null);
                BGiraduria.setVisible(true);
                BGiraduria.setTitle("Buscar Giraduria");
                nrocuenta.requestFocus();
                BGiraduria.setModal(false);
            } else {
                nombregiraduria.setText(gi.getNombre());
                //Establecemos un título para el jDialog
                nrocuenta.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcodgiraduriaActionPerformed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    public void filtrogira(int nNumeroColumna) {
        trsfiltrogira.setRowFilter(RowFilter.regexFilter(this.jTBuscarGiraduria.getText(), nNumeroColumna));
    }


    private void jTBuscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaActionPerformed
        this.jTBuscarGiraduria.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarGiraduria.getText()).toUpperCase();
                jTBuscarGiraduria.setText(cadena);
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
        trsfiltrogira = new TableRowSorter(tablagiraduria.getModel());
        tablagiraduria.setRowSorter(trsfiltrogira);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaActionPerformed

    private void jTBuscarGiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaKeyPressed

    private void tablagiraduriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablagiraduriaMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaMouseClicked

    private void tablagiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablagiraduriaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablagiraduria.getSelectedRow();
        this.codgiraduria.setText(this.tablagiraduria.getValueAt(nFila, 0).toString());
        this.nombregiraduria.setText(this.tablagiraduria.getValueAt(nFila, 1).toString());

        this.BGiraduria.setVisible(false);
        this.jTBuscarGiraduria.setText("");
        this.nrocuenta.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BGiraduria.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void BotonConfirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonConfirmaActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            prestamoDAO grabarPRE = new prestamoDAO();
            prestamo pr = new prestamo();
            giraduriaDAO giraDAO = new giraduriaDAO();
            giraduria gi = null;
            try {
                gi = giraDAO.buscarId(Integer.valueOf(this.codgiraduria.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            pr.setGiraduria(gi);
            pr.setNrocuenta(nrocuenta.getText());
            pr.setNumero(Integer.valueOf(numero.getText()));

            cuenta_clientes cta = new cuenta_clientes();
            cta.setCreferencia(creferencia.getText());
            cta.setGiraduria(gi);
            cta.setNrocuenta(nrocuenta.getText());
            cta.setAutorizacion(autorizacion.getText());
            cuenta_clienteDAO GrabarCta = new cuenta_clienteDAO();
            try {
                grabarPRE.ActualizarPrestamoDebitoAutomatico(pr);
                GrabarCta.ActualizarDebitoAutomaticoCuenta(cta);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");

        }
        debito.setModal(false);
        debito.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonConfirmaActionPerformed

    private void codgiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codgiraduriaActionPerformed
        this.buscarcodgiraduria.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codgiraduriaActionPerformed

    private void rechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechazarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            String cAprobar = "RECHAZADO";
            int ret = JOptionPane.showOptionDialog(null, "Se rechazará el Préstamo Seleccionado? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                con = new Conexion();
                stm = con.conectar();
                ResultSet results = null;
                String cSqlAprobar = "UPDATE Prestamos  SET estado='" + cAprobar + "',aprobadopor='" + Config.CodUsuario + "',observaciones='" + this.observaciones.getText();
                cSqlAprobar += "' WHERE numero=" + this.Opciones.getText();
                try {
                    stm.executeUpdate(cSqlAprobar);
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Aprobar un Préstamo");
            return;
        }
        this.aprobar.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_rechazarActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        String cOpcion = this.jTable1.getValueAt(nFila, 13).toString();
        try {
            new detalle_clientes(cOpcion).setVisible(true);
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem4MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        String cOpcion = this.jTable1.getValueAt(nFila, 13).toString();
        try {
            new detalle_clientes(cOpcion).setVisible(true);
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4MouseClicked

    private void nombreclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombreclienteMouseClicked
        int nFila = this.jTable1.getSelectedRow();
        String cOpcion = this.jTable1.getValueAt(nFila, 13).toString();
        try {
            new detalle_clientes(cOpcion).setVisible(true);
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_nombreclienteMouseClicked

    private void nrocuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrocuentaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.autorizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codgiraduria.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nrocuentaKeyPressed

    private void autorizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_autorizacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocuenta.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_autorizacionKeyPressed

    private void condicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_condicionarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.Opciones.setText(this.jTable1.getValueAt(nFila, 1).toString());
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            String cAprobar = "CONDICIONAL";
            int ret = JOptionPane.showOptionDialog(null, "La Solicitud estará sujeta a otras condiciones ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                con = new Conexion();
                stm = con.conectar();
                ResultSet results = null;
                String cSqlAprobar = "UPDATE Prestamos  SET estado='" + cAprobar + "',aprobadopor='" + Config.CodUsuario + "',observaciones='" + this.observaciones.getText();
                cSqlAprobar += "' WHERE numero=" + this.Opciones.getText();
                try {
                    stm.executeUpdate(cSqlAprobar);
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lo siento, no está Autorizado a Condicionar un Préstamo");
            return;
        }
        this.aprobar.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_condicionarActionPerformed

    private void montoaprobadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoaprobadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_montoaprobadoActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        GenerarContrato GenerarContrato = new GenerarContrato();
        Thread HiloContrato = new Thread(GenerarContrato);
        HiloContrato.start();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Operacion");
        modelo.addColumn("Fecha");
        modelo.addColumn("Denominación Cliente");
        modelo.addColumn("Moneda");
        modelo.addColumn("Capital"); //COLUMNA 5 CAPITAL
        modelo.addColumn("Interés"); //COLUMNA 6 INTERES
        modelo.addColumn("Total Préstamo");
        modelo.addColumn("Total Desembolso");
        modelo.addColumn("Tasa");
        modelo.addColumn("Tipo Préstamo");
        modelo.addColumn("Nombre Asesor");
        modelo.addColumn("Estado");
        modelo.addColumn("Cuenta");
        modelo.addColumn("IVA S/Interés");
        modelo.addColumn("Gastos");  //COLUMNA 15 GASTOS ADMINISTRATIVOS
        modelo.addColumn("Tipo");
        modelo.addColumn("IdFactura");
        modelo.addColumn("Comisiones");
        modelo.addColumn("Seguro de Vida");
        modelo.addColumn("Importe Cuota");
    }

    public void cargarTabla() {
        //Uso la Clase SimpleDateFormat para darle formato al campo fecha
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        cSql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.socio,prestamos.tasa,";
        cSql = cSql + "clientes.nombre AS nombrecliente,vendedores.nombre AS nombreasesor,prestamos.totalprestamo,monto_cuota,";
        cSql = cSql + "importe,monedas.nombre AS nombremoneda,comprobantes.nombre AS nombreprestamo, ";
        cSql = cSql + "prestamos.interes,prestamos.monto_entregar,prestamos.estado,prestamos.ivainteres,prestamos.comision_deudor,prestamos.tipo,prestamos.idfactura,";
        cSql = cSql + "prestamos.gastos_escritura,prestamos.segurovida ";
        cSql = cSql + "FROM prestamos ";
        cSql = cSql + "LEFT JOIN comprobantes ";
        cSql = cSql + "ON comprobantes.codigo=prestamos.tipo ";
        cSql = cSql + "LEFT JOIN vendedores ";
        cSql = cSql + "ON vendedores.codigo=prestamos.asesor ";
        cSql = cSql + "LEFT JOIN monedas ";
        cSql = cSql + "ON monedas.codigo=prestamos.moneda ";
        cSql = cSql + "LEFT JOIN clientes ";
        cSql = cSql + "ON clientes.codigo=prestamos.socio ";
        cSql = cSql + "WHERE prestamos.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
        cSql = cSql + " ORDER BY prestamos.fecha";

        //Instanciamos esta clase para alinear las celdas numericas a la derecha
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        //Llamo a la clase conexion para conectarme a la base de datos
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
        int[] anchos = {3, 120, 90, 350, 100, 100, 100, 100, 100, 90, 200, 200, 100, 90, 100, 100, 90, 100, 100, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(17).setMaxWidth(5);
        this.jTable1.getColumnModel().getColumn(17).setMinWidth(5);
        this.jTable1.getColumnModel().getColumn(17).setPreferredWidth(5);
        this.jTable1.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(19).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(20).setCellRenderer(TablaRenderer);

        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        try {
            results = stm.executeQuery(cSql);
            while (results.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.

                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[21]; // Cantidad de columnas en la tabla
                fila[0] = results.getString("idprestamos");
                fila[1] = results.getString("numero");
                fila[2] = formatoFecha.format(results.getDate("fecha"));
                fila[3] = results.getString("nombrecliente");
                fila[4] = results.getString("nombremoneda");
                fila[5] = formatea.format(results.getDouble("importe"));
                fila[6] = formatea.format(results.getDouble("interes"));
                fila[7] = formatea.format(results.getDouble("totalprestamo"));
                fila[8] = formatea.format(results.getDouble("monto_entregar"));
                fila[9] = formatea.format(results.getDouble("tasa"));
                fila[10] = results.getString("nombreprestamo");
                fila[11] = results.getString("nombreasesor");
                fila[12] = results.getString("estado");
                fila[13] = results.getString("socio");
                fila[14] = formatea.format(results.getDouble("ivainteres"));
                fila[15] = formatea.format(results.getDouble("comision_deudor"));
                fila[16] = results.getString("tipo");
                fila[17] = results.getString("idfactura");
                fila[18] = formatea.format(results.getDouble("gastos_escritura"));
                fila[19] = formatea.format(results.getDouble("segurovida"));
                fila[20] = formatea.format(results.getDouble("monto_cuota"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = this.jTable1.getRowCount();
            if (cantFilas > 0) {
                this.jButton1.setEnabled(true);
                this.jButton3.setEnabled(true);
            } else {
                this.jButton1.setEnabled(false);
                this.jButton3.setEnabled(false);
            }
            stm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new prestamos_aso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarGir;
    private javax.swing.JDialog BGiraduria;
    private javax.swing.JButton BotonConfirma;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JTextField Opciones;
    private javax.swing.JButton SalirDebito;
    private javax.swing.JButton SalirGir;
    private javax.swing.JDialog aprobar;
    private javax.swing.JTextField autorizacion;
    private javax.swing.JButton buscarcodgiraduria;
    private javax.swing.JFormattedTextField capitalafacturar;
    private javax.swing.JTextField codgiraduria;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JFormattedTextField comisiones;
    private javax.swing.JButton condicionar;
    private javax.swing.JTextField creferencia;
    private javax.swing.JDialog debito;
    private javax.swing.JMenuItem facturacapital;
    private javax.swing.JMenuItem facturainteresprestamo;
    private com.toedter.calendar.JDateChooser fechaprestamo;
    private com.toedter.calendar.JDateChooser fechaprestamo1;
    private com.toedter.calendar.JDateChooser fechaprestamo2;
    private javax.swing.JFormattedTextField gastosadministrativos;
    private javax.swing.JDialog genfacturacapital;
    private javax.swing.JDialog genfacturaotros;
    private javax.swing.JButton grabar;
    private javax.swing.JButton grabarfactura1;
    private javax.swing.JButton grabarfactura2;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JFormattedTextField importecuota;
    private javax.swing.JFormattedTextField importefactura;
    private javax.swing.JFormattedTextField importegasto;
    private javax.swing.JButton imprimirfactura1;
    private javax.swing.JFormattedTextField interes;
    private javax.swing.JFormattedTextField interesordinario;
    private javax.swing.JFormattedTextField intordinario;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTextField jTBuscarGiraduria;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private org.edisoncor.gui.label.LabelTask labelTask1;
    private javax.swing.JLabel lblgastos;
    private javax.swing.JLabel lblgastos1;
    private javax.swing.JLabel lblgastos2;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField moneda1;
    private javax.swing.JTextField moneda2;
    private javax.swing.JFormattedTextField montoaprobado;
    private javax.swing.JFormattedTextField neto;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombrecliente1;
    private javax.swing.JTextField nombrecliente2;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nrocuenta;
    private javax.swing.JTextField nrofactura1;
    private javax.swing.JTextField nrofactura2;
    private javax.swing.JTextField nroprestamo;
    private javax.swing.JTextField nroprestamo1;
    private javax.swing.JTextField nroprestamo2;
    private javax.swing.JTextField nrotimbrado;
    private javax.swing.JTextField nrotimbrado1;
    private javax.swing.JTextField numero;
    private javax.swing.JTextArea observacion1;
    private javax.swing.JTextArea observacion2;
    private javax.swing.JTextArea observaciones;
    private javax.swing.JTextField oficialcuenta;
    private javax.swing.JMenuItem ordenpago;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JButton rechazar;
    private javax.swing.JButton salirfactura1;
    private javax.swing.JButton salirfactura2;
    private javax.swing.JButton saliropcion;
    private javax.swing.JFormattedTextField seguro;
    private javax.swing.JTable tablagiraduria;
    private javax.swing.JTextField tipoprestamo;
    private javax.swing.JFormattedTextField totalprestamo;
    private com.toedter.calendar.JDateChooser vencetimbrado;
    // End of variables declaration//GEN-END:variables

    private class GenerarOp extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = jTable1.getValueAt(nFila, 8).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Referencia", jTable1.getValueAt(nFila, 0).toString());
                parameters.put("Letra", numero.Convertir(num, true, 1));
                JasperReport jr = null;
                //OTRAS EMPRESAS
                URL url = getClass().getClassLoader().getResource("Reports/orden_pago_prestamos.jasper");
                //PRECIOS BAJOS    
                //URL url = getClass().getClassLoader().getResource("Reports/orden_pago_preciosbajos.jasper");
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

    private class GenerarPagare extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombrePagare = config.getNombrepagare();

            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = jTable1.getValueAt(nFila, 7).toString();

                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = jTable1.getValueAt(nFila, 6).toString();
                cpInteres = cpInteres.replace(".", "");
                System.out.println("Interes " + cpInteres);

                BigDecimal n1 = new BigDecimal(cpImporte);
//                BigDecimal n2 = new BigDecimal(cpInteres);
                //              n1 = n1.add(n2); // Se suma los valores 
                //double nTotal=n1;

                String num = String.valueOf(n1);
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                // parameters.put("cNombreEmpresa", "EMPRESA DE PRUEBA");
                parameters.put("nNumeroPrestamo", Opciones.getText().trim());
                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/pagares.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombrePagare.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
                stm.close();
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
    }

    private class GenerarLiquidacion extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreLiquidacion = config.getNombreliquidacion();
            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", jTable1.getValueAt(nFila, 1).toString());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacion.toString());
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

    private class GenFacturaCapital extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            String cSql = "SELECT factura+1  AS nRegistro FROM sucursales";
            try {
                results = stm.executeQuery(cSql);
                if (results.next()) {
                    nrofactura1.setText(results.getString("nRegistro"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class GrabarDetalleCapital extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha

            con = new Conexion();
            stm = con.conectar();
            Connection conn = null;
            Date FechaProceso = ODate.de_java_a_sql(fechaprestamo1.getDate());
            Date PrimerVence = ODate.de_java_a_sql(fechaprestamo1.getDate());
            //Obteniendo Datos de los Combodatos
            String cmoneda = Config.cMonedaDefecto;
            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();
            String cFactura = nrofactura1.getText();
            String cOrdinario = intordinario.getText();
            cOrdinario = cOrdinario.replace(".", "");
            cOrdinario = cOrdinario.replace(",", ".");
            double nOrdi = Double.valueOf(cOrdinario);

            String cGastos = importegasto.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");

            String cComi = comisiones.getText();
            cComi = cComi.replace(".", "");
            cComi = cComi.replace(",", ".");

            String cSegu = seguro.getText();
            cSegu = cSegu.replace(".", "");
            cSegu = cSegu.replace(",", ".");

            double nGasto = Double.valueOf(cGastos) + Math.round(Double.valueOf(cGastos) * Config.nArancelIva / 100);
            cGastos = String.valueOf(nGasto);
            double nComi = Double.valueOf(cComi) + Math.round(Double.valueOf(cComi) * Config.nArancelIva / 100);
            cComi = String.valueOf(nComi);
            double nSegu = Double.valueOf(cSegu) + Math.round(Double.valueOf(cSegu) * Config.nArancelIva / 100);
            cSegu = String.valueOf(nSegu);

            double TotalNeto = Double.valueOf(cOrdinario) + nGasto + nComi + nSegu;
            String cTotalNeto = String.valueOf(TotalNeto);
            double montoiva10 = Math.round(Double.valueOf(Double.valueOf(cOrdinario) + nGasto + nComi + nSegu / 11));
            String cImpiva = String.valueOf(montoiva10);
            String cIva = String.valueOf(Config.nArancelIva);
            String cCotizacion = "1";
            String cIvaDetalle = "";
            String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,";
            cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idusuario)";
            cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cFactura + "','" + PrimerVence + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
            cSqlCab += "2" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + "0" + "','" + cTotalNeto + "','" + "0" + "','" + cTotalNeto + "','" + observacion1.getText().toString() + "','" + Config.CodUsuario + "')";
            try {
                conn = stm.getConnection();
                conn.setAutoCommit(false);

                try {
                    stm.executeUpdate(cSqlCab);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cCantidad = "1";

                if (Integer.valueOf(Config.cInteresPrestamo) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nOrdi / 11));
                    String cSqlDetalle1 = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle1 += "VALUES ('" + cReferencia + "','" + Config.cInteresPrestamo + "','" + cCantidad + "','" + '0' + "','" + cOrdinario + "','" + cOrdinario + "','" + cIvaDetalle + "','" + cIva + "')";

                    try {
                        stm.executeUpdate(cSqlDetalle1);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                if (Integer.valueOf(Config.cCodColocacion) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nGasto / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodColocacion + "','" + cCantidad + "','" + '0' + "','" + cGastos + "','" + cGastos + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                if (Integer.valueOf(Config.cCodComision) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nComi / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodComision + "','" + cCantidad + "','" + '0' + "','" + cComi + "','" + cComi + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                if (Integer.valueOf(Config.cCodSeguroVida) > 0) {
                    cIvaDetalle = String.valueOf(Math.round(nSegu / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodSeguroVida + "','" + cCantidad + "','" + '0' + "','" + cSegu + "','" + cSegu + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                String cSqlFactura = "UPDATE sucursales SET factura= '" + cFactura + "'";
                try {
                    stm.executeUpdate(cSqlFactura);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cSqlControlFactura = "INSERT INTO facturaprestamos (idprestamo,idfactura,opcion) VALUES('" + cReferencia + "','" + cReferencia + "','" + '1' + "')";
                try {
                    stm.executeUpdate(cSqlControlFactura);
                    conn.commit();
                    stm.close();
                } catch (SQLException ex) {
                    conn.rollback();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "La Factura ya fue Generada, Verifique");
                    Exceptions.printStackTrace(ex);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class ImprimirFacturaCapital extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte

                String num = cTotalImprimir;
                System.out.println(num);
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.toString());
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

    private class GenFacturaGastos extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            String cSql = "SELECT factura+1  AS nRegistro,vencetimbrado,nrotimbrado FROM sucursales";
            try {
                results = stm.executeQuery(cSql);
                if (results.next()) {
                    nrofactura2.setText(results.getString("nRegistro"));
                    nrotimbrado.setText(results.getString("nrotimbrado"));
                    vencetimbrado.setDate(results.getDate("vencetimbrado"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class GrabarDetalleOtros extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha

            Date FechaProceso = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date PrimerVence = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date VenceTimbrado = ODate.de_java_a_sql(vencetimbrado.getDate());
            //Obteniendo Datos de los Combodatos
            String cmoneda = Config.cMonedaDefecto;
            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();
            String cFactura = nrofactura2.getText();

            //SE CAPTURA CAPITAL
            String cCapital = capitalafacturar.getText();
            cCapital = cCapital.replace(".", "");
            cCapital = cCapital.replace(",", ".");

            //SE CAPTURA GASTOS ADMINISTRATIVOS
            String cGastos = gastosadministrativos.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");

            //SE CAPTURA LOS INTERESES ORDINARIOS EN CASO DE QUERER FACTURAR
            String cInteresOrdinario = interesordinario.getText();
            cInteresOrdinario = cInteresOrdinario.replace(".", "");
            cInteresOrdinario = cInteresOrdinario.replace(",", ".");
            // SE CALCULA EL TOTAL

            double TotalNeto = 0.0;
            double montoiva10 = 0.0;
            if (Config.cInteresPrestamo.isEmpty()) {
                cInteresOrdinario = "0";
            }
            if (Config.cCodCapitalPrestamo.isEmpty()) {
                cCapital = "0";
            }
            if (Config.cCodColocacion.isEmpty()) {
                cGastos = "0";
            }

            TotalNeto = Double.valueOf(cGastos) + Double.valueOf(cInteresOrdinario) + Double.valueOf(cCapital);
            montoiva10 = Math.round(Double.valueOf(Double.valueOf(cGastos) + Double.valueOf(cInteresOrdinario) / 11));

            String cTotalNeto = String.valueOf(TotalNeto);
            //SE CALCULA EL IVA 10% SOLO SOBRE GASTOS E INTERESES
            String cImpiva = String.valueOf(montoiva10);

            String cExentas = cCapital;

            String cIva = String.valueOf(Config.nArancelIva);
            String cCotizacion = "1";
            String cIvaDetalle = "";

            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            Connection conn = null;

            try {
                conn = stm.getConnection();
                conn.setAutoCommit(false);

                String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,";
                cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idprestamo,idusuario,vencimientotimbrado,nrotimbrado)";
                cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cFactura + "','" + PrimerVence + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
                cSqlCab += "2" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + cExentas + "','" + cImpiva + "','" + "0" + "','" + cTotalNeto + "','" + observacion2.getText().toString() + "','" + nroprestamo2.getText() + "','" + Config.CodUsuario + "','" + VenceTimbrado + "','" + nrotimbrado.getText() + "')";
                try {
                    stm.executeUpdate(cSqlCab);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cCantidad = "1";
                // SE GRABA DETALLE DE CAPITAL
                if (Config.cCodCapitalPrestamo != null && !Config.cCodCapitalPrestamo.equals("")) {
                    cIvaDetalle = "0";
                    String cSqlDetalle1 = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle1 += "VALUES ('" + cReferencia + "','" + Config.cCodCapitalPrestamo + "','" + cCantidad + "','" + '0' + "','" + cCapital + "','" + cCapital + "','" + cIvaDetalle + "','" + "0" + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle1);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                //SE GRABA DETALLE DE 
                if (Config.cCodColocacion != null && !Config.cCodColocacion.equals("")) {
                    cIvaDetalle = String.valueOf(Math.round(Double.valueOf(cGastos) / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodColocacion + "','" + cCantidad + "','" + '0' + "','" + cGastos + "','" + cGastos + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                if (Config.cInteresPrestamo != null && !Config.cInteresPrestamo.equals("")) {
                    cIvaDetalle = String.valueOf(Math.round(Double.valueOf(cInteresOrdinario) / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cInteresPrestamo + "','" + cCantidad + "','" + '0' + "','" + cInteresOrdinario + "','" + cInteresOrdinario + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                String cSqlFactura = "UPDATE sucursales SET factura= '" + cFactura + "'";
                try {
                    stm.executeUpdate(cSqlFactura);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                String cSqlControlFactura2 = "INSERT INTO facturaprestamos (idprestamo,idfactura,opcion) VALUES('" + cReferencia + "','" + cReferencia + "','" + '2' + "')";
                try {
                    stm.executeUpdate(cSqlControlFactura2);
                    conn.commit();
                    stm.close();
                } catch (SQLException ex) {
                    conn.rollback();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "La Factura ya fue Generada, Verifique");
                    Exceptions.printStackTrace(ex);
                }
                conn.close();
                stm.close();

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class ImprimirFacturaGastos extends Thread {

        public void run() {
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = jTable1.getSelectedRow();
                String num = jTable1.getValueAt(nFila, 6).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class ImprimirSolicitud extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreSolicitud = config.getNombresolicitud();
            int nFila = jTable1.getSelectedRow();
            cReferencia = jTable1.getValueAt(nFila, 1).toString();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("nNumeroPrestamo", cReferencia);
                System.out.println(cReferencia);
                JasperReport jr = null;
                //Ficha de Cliente de PUERTO SEGURO
                //   URL url = getClass().getClassLoader().getResource("Reports/fichacliente.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreSolicitud.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class GrillaBGiraduria extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelogiraduria.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelogiraduria.removeRow(0);
            }
            giraduriaDAO DAOGIR = new giraduriaDAO();
            try {
                for (giraduria gi : DAOGIR.todos()) {
                    String Datos[] = {String.valueOf(gi.getCodigo()), gi.getNombre()};
                    modelogiraduria.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablagiraduria.setRowSorter(new TableRowSorter(modelogiraduria));
            int cantFilas = tablagiraduria.getRowCount();
        }
    }

    private class ImprimirFactura extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = jTable1.getSelectedRow();
                String num = importefactura.getText();
                numero_a_letras numero = new numero_a_letras();
                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cRuc", Config.cRucEmpresa);
                parameters.put("cTelefono", Config.cTelefono);
                parameters.put("cDireccion", Config.cDireccion);
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia.trim());

                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
                stm.close();
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }

    }

    private class GrabarDetalleAsolac extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha

            Date FechaProceso = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date PrimerVence = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date VenceTimbrado = ODate.de_java_a_sql(vencetimbrado.getDate());
            //Obteniendo Datos de los Combodatos
            String cmoneda = Config.cMonedaDefecto;
            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();
            
            String cNumeroFactura = nrofactura2.getText();
            cNumeroFactura = cNumeroFactura.replace("-", "");
            String cContadorFactura = cNumeroFactura.substring(6, 13);
            
            String cFactura = nrofactura2.getText();

            
            //SE CAPTURA CAPITAL
            String cCapital = capitalafacturar.getText();
            cCapital = cCapital.replace(".", "");
            cCapital = cCapital.replace(",", ".");

            //SE CAPTURA GASTOS ADMINISTRATIVOS
            String cGastos = gastosadministrativos.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");

            //SE CAPTURA LOS INTERESES ORDINARIOS EN CASO DE QUERER FACTURAR
            String cInteresOrdinario = interesordinario.getText();
            cInteresOrdinario = cInteresOrdinario.replace(".", "");
            cInteresOrdinario = cInteresOrdinario.replace(",", ".");
            // SE CALCULA EL TOTAL

            double TotalNeto = 0.0;
            double montoiva10 = 0.0;

            if (Config.cInteresPrestamo.isEmpty()) {
                cInteresOrdinario = "0";
            }
            if (Config.cCodColocacion.isEmpty()) {
                cGastos = "0";
            }

            TotalNeto = Double.valueOf(cGastos) + Double.valueOf(cInteresOrdinario);
            montoiva10 = TotalNeto;

            String cTotalNeto = String.valueOf(TotalNeto);
            //SE CALCULA EL IVA 10% SOLO SOBRE GASTOS E INTERESES
            String cImpiva = String.valueOf(montoiva10);

            String cExentas = "0";

            String cIva = String.valueOf(Config.nArancelIva);
            String cCotizacion = "1";
            String cIvaDetalle = "";

            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            Connection conn = null;

            try {
                conn = stm.getConnection();
                conn.setAutoCommit(false);

                String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,formatofactura,vencimiento,cliente,sucursal,moneda,giraduria,";
                cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idprestamo,idusuario,vencimientotimbrado,nrotimbrado)";
                cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cNumeroFactura+"','"+cFactura + "','" + PrimerVence + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
                cSqlCab += "64" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + cExentas + "','" + cImpiva + "','" + "0" + "','" + cTotalNeto + "','" + observacion2.getText().toString() + "','" + nroprestamo2.getText() + "','" + Config.CodUsuario + "','" + VenceTimbrado + "','" + nrotimbrado1.getText() + "')";
                try {
                    stm.executeUpdate(cSqlCab);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cCantidad = "1";
                //SE GRABA DETALLE DE 
                if (Config.cCodColocacion != null && !Config.cCodColocacion.equals("")) {
                    cIvaDetalle = String.valueOf(Math.round(Double.valueOf(cGastos) / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodColocacion + "','" + cCantidad + "','" + '0' + "','" + cGastos + "','" + cGastos + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                if (Config.cInteresPrestamo != null && !Config.cInteresPrestamo.equals("")) {
                    cIvaDetalle = String.valueOf(Math.round(Double.valueOf(cInteresOrdinario) / 11));
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cInteresPrestamo + "','" + cCantidad + "','" + '0' + "','" + cInteresOrdinario + "','" + cInteresOrdinario + "','" + cIvaDetalle + "','" + cIva + "')";
                    try {
                        stm.executeUpdate(cSqlDetalle);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

                String cSqlFactura = "UPDATE sucursales SET factura= " +Double.valueOf(cContadorFactura)+1;
                try {
                    stm.executeUpdate(cSqlFactura);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cSqlControlFactura2 = "INSERT INTO facturaprestamos (idprestamo,idfactura,opcion) VALUES('" + cReferencia + "','" + cReferencia + "','" + '2' + "')";
                try {
                    stm.executeUpdate(cSqlControlFactura2);
                    conn.commit();
                    stm.close();
                } catch (SQLException ex) {
                    conn.rollback();
                    stm.close();
                    JOptionPane.showMessageDialog(null, "La Factura ya fue Generada, Verifique");
                    Exceptions.printStackTrace(ex);
                }
                conn.close();
                stm.close();

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

        private class GenerarContrato extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cContrato1 = config.getNombrecontrato1();

            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                String cMoneda = jTable1.getValueAt(nFila, 4).toString();
                String cNumeroPrestamo = jTable1.getValueAt(nFila, 1).toString();

                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = jTable1.getValueAt(nFila, 7).toString();
                String cMonto_Entregar = jTable1.getValueAt(nFila, 7).toString();
                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = jTable1.getValueAt(nFila, 6).toString();
                cpInteres = cpInteres.replace(".", "");
                System.out.println("Interes " + cpInteres);

                BigDecimal n1 = new BigDecimal(cpImporte);
//                BigDecimal n2 = new BigDecimal(cpInteres);
                //              n1 = n1.add(n2); // Se suma los valores 
                //double nTotal=n1;

                String num = String.valueOf(n1);
                numero_a_letras numero = new numero_a_letras();
                if (cMoneda.equals("GS.")) {
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                } else {
                    parameters.put("Letra", numero.Convertir(num, true, 2));
                }
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", cNumeroPrestamo.toString());
                parameters.put("cMonto_Entregar", cMonto_Entregar.toString());
                System.out.println(num);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cContrato1.toString());
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

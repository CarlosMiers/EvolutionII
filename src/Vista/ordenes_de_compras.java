/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.casaDAO;
import DAO.clienteDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.cuenta_clienteDAO;
import DAO.giraduriaDAO;
import DAO.ordencompraDAO;
import DAO.sucursalDAO;
import Modelo.Tablas;
import Modelo.casa;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.giraduria;
import Modelo.ordencompra;
import Modelo.sucursal;
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
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class ordenes_de_compras extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocasa = new Tablas();
    Tablas modelocliente = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltrogira, trsfiltrocasa;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String cTasa, referencia = null;
    int counter = 0;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon iconorefrescar = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");

    public ordenes_de_compras() {
        initComponents();
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.Salir.setIcon(iconosalir);
        this.Grabar.setIcon(iconograbar);
        this.refrescar.setIcon(iconorefrescar);
        this.SalirCompleto.setIcon(iconosalir);
        this.buscarCliente.setIcon(iconobuscar);
        this.buscarComercio.setIcon(iconobuscar);
        this.buscarGarante.setIcon(iconobuscar);
        this.buscarGiraduria.setIcon(iconobuscar);
        this.buscarSucursal.setIcon(iconobuscar);
        this.refrescar.setIcon(icorefresh);

        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        fecha.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    cliente.requestFocus();
                }
            }
        });

        primervence.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    Grabar.requestFocus();
                }
            }
        });

        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setVisible(false);
        this.venceanterior.setVisible(false);
        this.vencimientos.setVisible(false);
        this.fechaalta.setVisible(false);
        this.creferencia.setVisible(false);
        this.idControl.setText("0");
        this.Inicializar();
        this.cargarTitulo();
        this.TitSuc();
        this.TitCasa();
        this.TitGir();
        this.TitClie();
        GrillaOCredito GrillaOC = new GrillaOCredito();
        Thread HiloGrilla = new Thread(GrillaOC);
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

        detalle_orden_compra = new javax.swing.JDialog();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        buscarCliente = new javax.swing.JButton();
        nombrecliente = new javax.swing.JTextField();
        direccioncliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        giraduria = new javax.swing.JTextField();
        buscarGiraduria = new javax.swing.JButton();
        nombregiraduria = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comercio = new javax.swing.JTextField();
        buscarComercio = new javax.swing.JButton();
        nombrecomercio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        plazo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        montocuota = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        primervence = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        vencimientos = new com.toedter.calendar.JDateChooser();
        venceanterior = new com.toedter.calendar.JDateChooser();
        creferencia = new javax.swing.JTextField();
        numero = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        garante = new javax.swing.JTextField();
        buscarGarante = new javax.swing.JButton();
        nombregarante = new javax.swing.JTextField();
        fechaalta = new com.toedter.calendar.JDateChooser();
        BSucursal = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BCliente = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BGiraduria = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        jTBuscarGiraduria = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablagiraduria = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        BCasas = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combocasa = new javax.swing.JComboBox();
        jTBuscarCasa = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablacasa = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarCasa = new javax.swing.JButton();
        SalirCasa = new javax.swing.JButton();
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
        dInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();

        detalle_orden_compra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_orden_compraFocusGained(evt);
            }
        });
        detalle_orden_compra.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_orden_compraWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_orden_compra.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_orden_compraWindowActivated(evt);
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

        jLabel1.setText("N° Orden");

        fecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaFocusGained(evt);
            }
        });
        fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaKeyPressed(evt);
            }
        });

        jLabel2.setText("Fecha Emisión");

        jLabel4.setText("Socio");

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

        buscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarClienteActionPerformed(evt);
            }
        });

        nombrecliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecliente.setEnabled(false);

        direccioncliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        direccioncliente.setEnabled(false);

        jLabel5.setText("Giraduria");

        giraduria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giraduria.setEnabled(false);
        giraduria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                giraduriaFocusGained(evt);
            }
        });
        giraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giraduriaActionPerformed(evt);
            }
        });
        giraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                giraduriaKeyPressed(evt);
            }
        });

        buscarGiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarGiraduria.setEnabled(false);
        buscarGiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarGiraduriaActionPerformed(evt);
            }
        });

        nombregiraduria.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombregiraduria.setEnabled(false);

        jLabel6.setText("Comercio");

        comercio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comercio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comercioFocusGained(evt);
            }
        });
        comercio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comercioActionPerformed(evt);
            }
        });
        comercio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comercioKeyPressed(evt);
            }
        });

        buscarComercio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarComercio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarComercioActionPerformed(evt);
            }
        });

        nombrecomercio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecomercio.setEnabled(false);

        jLabel7.setText("Importe Solicitado");

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

        jLabel10.setText("Plazo");

        plazo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        plazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                plazoFocusGained(evt);
            }
        });
        plazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plazoActionPerformed(evt);
            }
        });
        plazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plazoKeyPressed(evt);
            }
        });

        jLabel11.setText("Importe Cuota");

        montocuota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        montocuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        montocuota.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                montocuotaFocusGained(evt);
            }
        });
        montocuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                montocuotaKeyPressed(evt);
            }
        });

        jLabel12.setText("Sucursal");

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
        });

        buscarSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        primervence.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primervenceKeyPressed(evt);
            }
        });

        jLabel13.setText("Primer Vencimiento");

        vencimientos.setEnabled(false);

        venceanterior.setEnabled(false);

        creferencia.setEnabled(false);

        numero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numero.setEnabled(false);

        jLabel9.setText("Garante");

        garante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        garante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                garanteFocusGained(evt);
            }
        });
        garante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                garanteActionPerformed(evt);
            }
        });
        garante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                garanteKeyPressed(evt);
            }
        });

        buscarGarante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarGarante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarGaranteActionPerformed(evt);
            }
        });

        nombregarante.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombregarante.setEnabled(false);

        fechaalta.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(giraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comercio, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(garante, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montocuota, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(primervence, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarGarante, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(nombregarante, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nombrecomercio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(giraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(comercio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(nombrecomercio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarComercio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(garante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombregarante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fechaalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buscarGarante, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(montocuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(primervence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detalle_orden_compraLayout = new javax.swing.GroupLayout(detalle_orden_compra.getContentPane());
        detalle_orden_compra.getContentPane().setLayout(detalle_orden_compraLayout);
        detalle_orden_compraLayout.setHorizontalGroup(
            detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_orden_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_compraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        detalle_orden_compraLayout.setVerticalGroup(
            detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_orden_compraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(detalle_orden_compraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Salir)
                    .addComponent(Grabar))
                .addContainerGap())
        );

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
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.SalirCliente.text")); // NOI18N
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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.SalirCliente.text")); // NOI18N
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
        jTBuscarGiraduria.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.SalirCliente.text")); // NOI18N
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

        BCasas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCasas.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocasa.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocasa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocasaActionPerformed(evt);
            }
        });

        jTBuscarCasa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCasa.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCasaActionPerformed(evt);
            }
        });
        jTBuscarCasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCasaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combocasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacasa.setModel(modelocasa);
        tablacasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacasaMouseClicked(evt);
            }
        });
        tablacasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacasaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablacasa);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa)
                    .addComponent(SalirCasa))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCasasLayout = new javax.swing.GroupLayout(BCasas.getContentPane());
        BCasas.getContentPane().setLayout(BCasasLayout);
        BCasasLayout.setHorizontalGroup(
            BCasasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCasasLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCasasLayout.setVerticalGroup(
            BCasasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCasasLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetacredito.setBackground(new java.awt.Color(255, 255, 255));
        etiquetacredito.setText("Ordenes de Compra");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Nro de Orden" }));
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
                .addGap(40, 40, 40)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(342, Short.MAX_VALUE))
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
        Eliminar.setText("Anular Orden");
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(ordenes_de_compras.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

        refrescar.setText("Refrescar");
        refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(refrescar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(SalirCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Eliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
                .addGap(26, 26, 26)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
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

        jMenu1.setText("Menú Ordenes de Compra");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem2.setText("Liquidación ");
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator2);

        jMenuItem3.setText("Orden de Compra");
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
        this.fechaalta.setCalendar(c2);
    }

    public void limpiarCombos() {

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
        detalle_orden_compra.setModal(true);
        detalle_orden_compra.setSize(690, 445);
        //Establecemos un título para el jDialog
        detalle_orden_compra.setTitle("Agregar Nueva Orden de Compra");
        detalle_orden_compra.setLocationRelativeTo(null);
        detalle_orden_compra.setVisible(true);
        sucursal.requestFocus();
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.sucursal.setText("0");
        this.numero.setText("0");
        this.nombresucursal.setText("");
        this.fecha.setCalendar(c2);
        this.primervence.setCalendar(c2);
        this.cliente.setText("0");
        this.nombrecliente.setText("");
        this.direccioncliente.setText("");
        this.giraduria.setText("0");
        this.nombregiraduria.setText("");
        this.comercio.setText("0");
        this.nombrecomercio.setText("");
        this.importe.setText("0");
        this.montocuota.setText("0");
        this.plazo.setText("0");
        this.garante.setText("0");
        this.nombregarante.setText("");
    }
    private void jTable1KeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            this.limpiar();
            int nFila = this.jTable1.getSelectedRow();
            this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
            String cEstado = this.jTable1.getValueAt(nFila, 7).toString();

            String cReferencia = this.jTable1.getValueAt(nFila, 8).toString();
            cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
            cuenta_clientes saldo = null;
            try {
                saldo = saldoDAO.SaldoMovimiento(cReferencia);
                if (saldo.getDocumento() != null) {
                    JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                    return;
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            if (cEstado.equals("ANULADO")) {
                JOptionPane.showMessageDialog(null, "La Orden de Compra ya fue Anulada ");
                return;
            }

            ordencompraDAO ocDAO = new ordencompraDAO();
            ordencompra oc = null;

            try {
                oc = ocDAO.buscarId(Integer.valueOf(this.idControl.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (oc != null) {
                creferencia.setText(oc.getIdordencompra());
                sucursal.setText(String.valueOf(oc.getSucursal().getCodigo()));
                nombresucursal.setText(oc.getSucursal().getNombre());
                numero.setText(idControl.getText());
                fecha.setDate(oc.getFecha());
                cliente.setText(String.valueOf(oc.getCliente().getCodigo()));
                nombrecliente.setText(oc.getCliente().getNombre());
                direccioncliente.setText(oc.getCliente().getDireccion());
                giraduria.setText(String.valueOf(oc.getGiraduria().getCodigo()));
                nombregiraduria.setText(oc.getGiraduria().getNombre());
                comercio.setText(String.valueOf(oc.getCasas().getCodigo()));
                nombrecomercio.setText(oc.getCasas().getNombre());
                garante.setText(String.valueOf(oc.getGarante()));
                nombregarante.setText(oc.getNombregarante());
                importe.setText(formatea.format(oc.getImporte()));
                plazo.setText(String.valueOf(oc.getPlazo()));
                montocuota.setText(formatea.format(oc.getMonto_cuota()));
                primervence.setDate(oc.getPrimer_vence());
                if (oc.getEstado() != "ACTIVO") {
                    detalle_orden_compra.setModal(true);
                    detalle_orden_compra.setSize(690, 445);
                    //Establecemos un título para el jDialog
                    detalle_orden_compra.setTitle("Modificar Orden de Compra");
                    detalle_orden_compra.setLocationRelativeTo(null);
                    detalle_orden_compra.setVisible(true);
                    sucursal.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
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
        String cEstado = this.jTable1.getValueAt(nFila, 7).toString();

        String cReferencia = this.jTable1.getValueAt(nFila, 8).toString();
        cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
        cuenta_clientes saldo = null;
        try {
            saldo = saldoDAO.SaldoMovimiento(cReferencia);
            if (saldo.getDocumento() != null) {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Eliminarse");
                 return;
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (cEstado.equals("ANULADO")) {
            JOptionPane.showMessageDialog(null, "La Orden de Compra ya fue Anulada ");
            return;
        }
        if (Config.cNivelUsuario.equals("1")) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Anular esta Orden ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                ordencompraDAO pl = new ordencompraDAO();
                try {
                    ordencompra p = pl.buscarId(Integer.valueOf(num));
                    p.setUsuarionulo(Integer.valueOf(Config.CodUsuario));
                    p.setEstado("ANULADO");
                    if (p == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        pl.AnularOrdenCompra(p, Integer.valueOf(num));
                        pl.borrarDetalleOrdenCompra(p.getIdordencompra());
                        JOptionPane.showMessageDialog(null, "Orden Anulada Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
        }
        GrillaOCredito GrillaOC = new GrillaOCredito();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();
    }//GEN-LAST:event_EliminarActionPerformed

    private void jTable1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        String cEstado = this.jTable1.getValueAt(nFila, 7).toString();
        if (cEstado.equals("ANULADO")) {
            JOptionPane.showMessageDialog(null, "La Orden de Compra ya fue Anulada ");
            return;
        }
        EmitirOrden EmitirOrden = new EmitirOrden();
        Thread HiloOrden = new Thread(EmitirOrden);
        HiloOrden.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_orden_compra.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed
        //Se inicia Proceso de Grabado de Registro
        //Se instancian las clases necesarias asociadas al modelado de Orden de Credito
        if (this.garante.getText().isEmpty()) {
            this.garante.setText("0");
        }

        if (this.giraduria.getText().isEmpty() || this.giraduria.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Giraduría");
            this.giraduria.requestFocus();
            return;
        }

        if (this.comercio.getText().isEmpty() || this.comercio.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Comercio");
            this.comercio.requestFocus();
            return;
        }

        if (this.cliente.getText().isEmpty() || this.cliente.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Cliente");
            this.cliente.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            ordencompraDAO grabarOCR = new ordencompraDAO();
            ordencompra cr = new ordencompra();
            giraduriaDAO giraDAO = new giraduriaDAO();
            giraduria gi = null;
            casaDAO caDAO = new casaDAO();
            casa ca = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cl = null;
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal sc = null;
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = null;
            config = configDAO.consultar();
            comprobanteDAO cmDAO = new comprobanteDAO();
            comprobante cm = null;

            try {
                gi = giraDAO.buscarId(Integer.valueOf(this.giraduria.getText()));
                ca = caDAO.buscarId(Integer.valueOf(this.comercio.getText()));
                cl = cliDAO.buscarId(Integer.valueOf(this.cliente.getText()));
                sc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
                cm = cmDAO.buscarId(config.getCodigoordencompra());
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
            Date Fechaalta = ODate.de_java_a_sql(fechaalta.getDate());
            cr.setFecha(FechaProceso);
            cr.setFechaalta(Fechaalta);
            cr.setGiraduria(gi);
            cr.setCasas(ca);
            cr.setCliente(cl);
            cr.setTipo(cm);
            cr.setGarante(Integer.valueOf(this.garante.getText()));
            cr.setPlazo(Integer.valueOf(this.plazo.getText()));

            String cImporte = this.importe.getText();
            cImporte = cImporte.replace(".", "");
            cImporte = cImporte.replace(",", ".");
            BigDecimal nimporte = new BigDecimal(cImporte);
            cr.setImporte(nimporte);

            String cMontoCuota = this.montocuota.getText();
            cMontoCuota = cMontoCuota.replace(".", "");
            cMontoCuota = cMontoCuota.replace(",", ".");

            String cMontoCuotaAnterior = cMontoCuota;
            BigDecimal nMontoCuota = new BigDecimal(cMontoCuota);
            cr.setMonto_cuota(nMontoCuota);

            Date PrimerVence = ODate.de_java_a_sql(primervence.getDate());
            cr.setPrimer_vence(PrimerVence);
            //cr.setUsuarioalta(Integer.valueOf(Config.CodUsuario));
            cr.setUsuarioalta(Integer.valueOf(Config.CodUsuario));

            //ESTA FUNCION USAMOS PARA REDONDEAR LAS CUOTAS
            double nImportecuotas = Double.valueOf(cMontoCuota) * Integer.valueOf(this.plazo.getText());

            double nResto = Double.valueOf(cImporte) - nImportecuotas;
            double nPrimeraCuota = Double.valueOf(cMontoCuota);
            if (nResto != 0) {
                if (nResto < 0) {
                    nPrimeraCuota = nPrimeraCuota - (Math.abs(nResto));
                } else {
                    nPrimeraCuota = nPrimeraCuota + nResto;
                }
            }

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
                if (i == 1) {
                    cMontoCuota = String.valueOf(nPrimeraCuota);
                }
                String linea = "{iddocumento : " + iddocumento + ","
                        + "creferencia : " + referencia + ","
                        + "documento : " + this.numero.getText() + ","
                        + "fecha : " + FechaProceso + ","
                        + "vencimiento : " + VenceCuota + ","
                        + "cliente : " + this.cliente.getText() + ","
                        + "sucursal : " + this.sucursal.getText() + ","
                        + "moneda : " + 1 + ","
                        + "importe : " + cMontoCuota + ","
                        + "comprobante : " + cm.getCodigo() + ","
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
                cMontoCuota = cMontoCuotaAnterior;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            if (Integer.valueOf(this.numero.getText()) == 0) {

                try {
                    grabarOCR.insertarOrdenCompra(Config.cToken, cr, detalle);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } else {
                //Actualizar Orden de Credito
                try {
                    grabarOCR.borrarDetalleOrdenCompra(this.creferencia.getText());
                    grabarOCR.ActualizarOrdenCompra(Config.cToken, cr, detalle);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            }
            detalle_orden_compra.setVisible(false);
            this.detalle_orden_compra.setModal(false);
            this.detalle_orden_compra.setVisible(false);
            GrillaOCredito GrillaOC = new GrillaOCredito();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();
    }//GEN-LAST:event_GrabarActionPerformed
    }
    private void detalle_orden_compraFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_orden_compraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_compraFocusGained

    private void detalle_orden_compraWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_orden_compraWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_compraWindowGainedFocus

    private void detalle_orden_compraWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_orden_compraWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_orden_compraWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void plazoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_plazoActionPerformed
        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.fecha.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        calendar.add(Calendar.DAY_OF_YEAR, 30);  // numero de días a añadir, o restar en caso de días<0
        this.primervence.setDate(calendar.getTime()); //Y cargamos finalmente en el 
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoActionPerformed

    private void refrescarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaOCredito GrillaOC = new GrillaOCredito();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();        // TODO add your handling code here:
    }//GEN-LAST:event_refrescarActionPerformed

    private void sucursalKeyPressed(KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fecha.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void fechaFocusGained(FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void fechaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cliente.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void clienteKeyPressed(KeyEvent evt) {//GEN-FIRST:event_clienteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.comercio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }   // TODO add your handling code 
    }//GEN-LAST:event_clienteKeyPressed

    private void giraduriaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_giraduriaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.comercio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cliente.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_giraduriaKeyPressed

    private void comercioKeyPressed(KeyEvent evt) {//GEN-FIRST:event_comercioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.garante.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.giraduria.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_comercioKeyPressed

    private void importeKeyPressed(KeyEvent evt) {//GEN-FIRST:event_importeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.plazo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.garante.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_importeKeyPressed

    private void plazoKeyPressed(KeyEvent evt) {//GEN-FIRST:event_plazoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.montocuota.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.plazo.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_plazoKeyPressed

    private void montocuotaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_montocuotaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.primervence.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.plazo.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_montocuotaKeyPressed

    private void primervenceKeyPressed(KeyEvent evt) {//GEN-FIRST:event_primervenceKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.Grabar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.montocuota.requestFocus();
        }   // TO        // TODO add your handling code here:
    }//GEN-LAST:event_primervenceKeyPressed

    private void buscarSucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (sucu.getCodigo() == 0) {
                GrillaSucursal grillasu = new GrillaSucursal();
                Thread hilosuc = new Thread(grillasu);
                hilosuc.start();
                BSucursal.setModal(true);
                BSucursal.setSize(482, 575);
                BSucursal.setLocationRelativeTo(null);
                BSucursal.setTitle("Buscar Sucursal");
                BSucursal.setVisible(true);
                BSucursal.setModal(false);
            } else {
                nombresucursal.setText(sucu.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void sucursalActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void comboclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

    private void jTBuscarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarClienteKeyPressed
        this.jTBuscarCliente.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCliente.getText()).toUpperCase();
                jTBuscarCliente.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocliente.getSelectedIndex()) {
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
        trsfiltrocli = new TableRowSorter(tablacliente.getModel());
        tablacliente.setRowSorter(trsfiltrocli);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteKeyPressed

    private void tablaclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCli.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteKeyPressed

    private void AceptarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCliActionPerformed
        int nFila = this.tablacliente.getSelectedRow();
        this.cliente.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombrecliente.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.direccioncliente.setText(this.tablacliente.getValueAt(nFila, 2).toString());
        this.giraduria.setText(this.tablacliente.getValueAt(nFila, 4).toString());
        this.nombregiraduria.setText(this.tablacliente.getValueAt(nFila, 5).toString());

        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        this.giraduria.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void buscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarClienteActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarIdSimple(Integer.valueOf(this.cliente.getText()));
            if (cl.getCodigo() == 0) {
                GrillaCliente grillacl = new GrillaCliente();
                Thread hilocl = new Thread(grillacl);
                hilocl.start();
                BCliente.setModal(true);
                BCliente.setSize(482, 575);
                BCliente.setLocationRelativeTo(null);
                BCliente.setTitle("Buscar Socio");
                BCliente.setVisible(true);
                giraduria.requestFocus();
                BCliente.setModal(false);
            } else {
                nombrecliente.setText(cl.getNombre());
                direccioncliente.setText(cl.getDireccion());
                giraduria.setText(String.valueOf(cl.getGiraduria().getCodigo()));
                nombregiraduria.setText(cl.getGiraduria().getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        comercio.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarClienteActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        this.buscarCliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    private void jTBuscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaActionPerformed

    private void jTBuscarGiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaKeyPressed
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
    }//GEN-LAST:event_jTBuscarGiraduriaKeyPressed

    private void tablagiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablagiraduriaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablagiraduria.getSelectedRow();
        this.giraduria.setText(this.tablagiraduria.getValueAt(nFila, 0).toString());
        this.nombregiraduria.setText(this.tablagiraduria.getValueAt(nFila, 1).toString());

        this.BGiraduria.setVisible(false);
        this.jTBuscarGiraduria.setText("");
        this.comercio.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BGiraduria.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void buscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarGiraduriaActionPerformed
        giraduriaDAO girDAO = new giraduriaDAO();
        giraduria gi = null;
        try {
            gi = girDAO.buscarId(Integer.valueOf(this.giraduria.getText()));
            if (gi.getCodigo() == 0) {
                GrillaGiraduria grillagi = new GrillaGiraduria();
                Thread hilogi = new Thread(grillagi);
                hilogi.start();
                BGiraduria.setModal(true);
                BGiraduria.setSize(482, 575);
                BGiraduria.setLocationRelativeTo(null);
                BGiraduria.setVisible(true);
                BGiraduria.setTitle("Buscar Giraduria");
                comercio.requestFocus();
                BGiraduria.setModal(false);
            } else {
                nombregiraduria.setText(gi.getNombre());
                //Establecemos un título para el jDialog
                comercio.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarGiraduriaActionPerformed

    private void jTBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.AceptarCli.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void tablagiraduriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablagiraduriaMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaMouseClicked

    private void giraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giraduriaActionPerformed
        this.buscarGiraduria.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_giraduriaActionPerformed

    private void combocasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocasaActionPerformed

    private void jTBuscarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCasaActionPerformed

    private void jTBuscarCasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCasaKeyPressed
        this.jTBuscarCasa.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCasa.getText()).toUpperCase();
                jTBuscarCasa.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocasa.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrocasa(indiceColumnaTabla);
            }
        });
        trsfiltrocasa = new TableRowSorter(tablacasa.getModel());
        tablacasa.setRowSorter(trsfiltrocasa);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCasaKeyPressed

    private void tablacasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacasaMouseClicked
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacasaMouseClicked

    private void tablacasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacasaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacasaKeyPressed

    private void AceptarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed
        int nFila = this.tablacasa.getSelectedRow();
        this.comercio.setText(this.tablacasa.getValueAt(nFila, 0).toString());
        this.nombrecomercio.setText(this.tablacasa.getValueAt(nFila, 1).toString());

        this.BCasas.setVisible(false);
        this.jTBuscarCasa.setText("");
        this.importe.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.BCasas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");
        this.fecha.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void tablasucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalKeyPressed

    private void tablasucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasucursalMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalMouseClicked

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
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalKeyPressed

    private void jTBuscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalActionPerformed

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

    private void comercioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comercioActionPerformed
        this.buscarComercio.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_comercioActionPerformed

    private void buscarComercioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarComercioActionPerformed
        casaDAO casDAO = new casaDAO();
        casa ca = null;
        try {
            ca = casDAO.buscarId(Integer.valueOf(this.comercio.getText()));
            if (ca.getCodigo() == 0) {
                GrillaCasa grillaca = new GrillaCasa();
                Thread hiloca = new Thread(grillaca);
                hiloca.start();
                BCasas.setModal(true);
                BCasas.setSize(482, 575);
                BCasas.setLocationRelativeTo(null);
                BCasas.setVisible(true);
                BCasas.setTitle("Buscar Comercio");
                comercio.requestFocus();
                BCasas.setModal(false);
            } else {
                nombrecomercio.setText(ca.getNombre());
                //Establecemos un título para el jDialog
                garante.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarComercioActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void clienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clienteFocusGained
        cliente.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_clienteFocusGained

    private void giraduriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_giraduriaFocusGained
        giraduria.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_giraduriaFocusGained

    private void comercioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comercioFocusGained
        comercio.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_comercioFocusGained

    private void importeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importeFocusGained
        importe.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importeFocusGained

    private void plazoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_plazoFocusGained
        plazo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoFocusGained

    private void montocuotaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_montocuotaFocusGained
        montocuota.selectAll();
        String cImporte = this.importe.getText();
        cImporte = cImporte.replace(".", "");
        double nMontoCuota = Math.round(Double.valueOf(cImporte) / Double.valueOf(plazo.getText()));
        montocuota.setText(formatea.format(nMontoCuota));
        // TODO add your handling code here:
    }//GEN-LAST:event_montocuotaFocusGained

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

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void garanteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_garanteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_garanteFocusGained

    private void garanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_garanteActionPerformed
        if (Integer.valueOf(this.garante.getText()) > 0) {
            this.buscarGarante.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_garanteActionPerformed

    private void garanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_garanteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importe.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comercio.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_garanteKeyPressed

    private void buscarGaranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarGaranteActionPerformed
        clienteDAO gaDAO = new clienteDAO();
        cliente ga = null;
        try {
            ga = gaDAO.buscarId(Integer.valueOf(this.garante.getText()));
            if (ga.getCodigo() == 0) {
                GrillaCliente grillacl = new GrillaCliente();
                Thread hilocl = new Thread(grillacl);
                hilocl.start();
                BCliente.setModal(true);
                BCliente.setSize(482, 575);
                BCliente.setLocationRelativeTo(null);
                BCliente.setTitle("Buscar Cliente");
                BCliente.setVisible(true);
                giraduria.requestFocus();
                BCliente.setModal(false);
            } else {
                nombregarante.setText(ga.getNombre());
                //Establecemos un título para el jDialog
                importe.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarGaranteActionPerformed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    public void filtrocasa(int nNumeroColumna) {
        trsfiltrocasa.setRowFilter(RowFilter.regexFilter(jTBuscarCasa.getText(), nNumeroColumna));
    }

    public void filtrogira(int nNumeroColumna) {
        trsfiltrogira.setRowFilter(RowFilter.regexFilter(this.jTBuscarGiraduria.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Nro. Orden");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cliente");
        modelo.addColumn("Nombre del Cliente");
        modelo.addColumn("Importe");
        modelo.addColumn("Cuotas");
        modelo.addColumn("Monto Cuota");
        modelo.addColumn("Estado");
        modelo.addColumn("id");

        int[] anchos = {120, 90, 90, 250, 100, 100, 100, 100, 10};
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
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(AlinearCentro);
    
        this.jTable1.getColumnModel().getColumn(8).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(8).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
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

    private void TitCasa() {
        modelocasa.addColumn("Código");
        modelocasa.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelocasa.getColumnCount(); i++) {
            tablacasa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacasa.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacasa.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacasa.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacasa.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");
        modelocliente.addColumn("Dirección");
        modelocliente.addColumn("RUC");
        modelocliente.addColumn("Giraduría");
        modelocliente.addColumn("Nombre G.");

        int[] anchos = {90, 150, 100, 100, 5, 5};
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
        this.tablacliente.getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacliente.getColumnModel().getColumn(4).setMinWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
        this.tablacliente.getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablacliente.getColumnModel().getColumn(5).setMinWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);

    }

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
                new ordenes_de_compras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCasa;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BCasas;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BGiraduria;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCasa;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JDialog aprobarcredito;
    private javax.swing.JButton buscarCliente;
    private javax.swing.JButton buscarComercio;
    private javax.swing.JButton buscarGarante;
    private javax.swing.JButton buscarGiraduria;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JTextField cliente;
    private javax.swing.JComboBox combocasa;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JTextField comercio;
    private javax.swing.JTextField creferencia;
    private com.toedter.calendar.JDateChooser dFinal;
    private com.toedter.calendar.JDateChooser dInicial;
    private javax.swing.JFormattedTextField descuentoc;
    private javax.swing.JDialog detalle_orden_compra;
    private javax.swing.JTextField direccioncliente;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechaalta;
    private com.toedter.calendar.JDateChooser fechac;
    private javax.swing.JTextField garante;
    private javax.swing.JTextField giraduria;
    private javax.swing.JButton grabaroc;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JFormattedTextField importec;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextField jTBuscarCasa;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarGiraduria;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField montocuota;
    private javax.swing.JFormattedTextField neto;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreclientec;
    private javax.swing.JTextField nombrecomercio;
    private javax.swing.JTextField nombregarante;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JFormattedTextField numero;
    private javax.swing.JTextField numeroc;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTextField plazo;
    private com.toedter.calendar.JDateChooser primervence;
    private javax.swing.JButton refrescar;
    private javax.swing.JButton saliropcion;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablacasa;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablagiraduria;
    private javax.swing.JTable tablasucursal;
    private com.toedter.calendar.JDateChooser venceanterior;
    private com.toedter.calendar.JDateChooser vencimientos;
    // End of variables declaration//GEN-END:variables

    private class GenerarReporte extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nCliente", idControl.getText().trim());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/extracto_cuenta_clientes.jasper");
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

    private class EmitirOrden extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            int nFila = jTable1.getSelectedRow();
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte

                String cNumero = jTable1.getValueAt(nFila, 0).toString();
                String num = jTable1.getValueAt(nFila, 4).toString();
                String ImporteTotal = jTable1.getValueAt(nFila, 4).toString();
                num = num.replace(".", "").replace(",", ".");
                numero_a_letras numero = new numero_a_letras();
                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("nNumero", cNumero);
                parameters.put("ImporteTotal", ImporteTotal);
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cRuc", Config.cRucEmpresa);

                JasperReport jr, jr2 = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                //AFUMA
                URL url = getClass().getClassLoader().getResource("Reports/ordencompra1.jasper");
                URL url2 = getClass().getClassLoader().getResource("Reports/ordencompra2.jasper");
                //SINATRAM
//                URL url = getClass().getClassLoader().getResource("Reports/ordencompra_aso1.jasper");
                //              URL url2 = getClass().getClassLoader().getResource("Reports/ordencompra_aso2.jasper");

//                URL url = getClass().getClassLoader().getResource("Reports/ordencompracoop.jasper");
//                URL url2 = getClass().getClassLoader().getResource("Reports/ordencompra_aso2.jasper");
                //+ Config.cNombreFactura);
                jr = (JasperReport) JRLoader.loadObject(url);
                jr2 = (JasperReport) JRLoader.loadObject(url2);
                JasperPrint masterPrint = null;
                JasperPrint masterPrint2 = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                masterPrint2 = JasperFillManager.fillReport(jr2, parameters, stm.getConnection());

                //  if (Config.cDestinoImpresion == "") {
                //Enviar a Vista Previa
                //JasperViewer ventana = new JasperViewer(masterPrint, false);
                //ventana.setTitle("Vista Previa");
                //ventana.setVisible(true);
                //} else {
                //Enviar a Impresora 
                JasperPrintManager.printReport(masterPrint, false);
                JasperPrintManager.printReport(masterPrint2, false);
                // }
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class GrillaOCredito extends Thread {

        public void run() {

            Date dFechaInicio = ODate.de_java_a_sql(dInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(dFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            ordencompraDAO DAO = new ordencompraDAO();
            try {
                for (ordencompra orden : DAO.MostrarxFecha(dFechaInicio, dFechaFinal)) {
                    String Datos[] = {String.valueOf(orden.getNumero()), formatoFecha.format(orden.getFecha()), String.valueOf(orden.getCliente().getCodigo()), orden.getCliente().getNombre(), formatea.format(orden.getImporte()), String.valueOf(orden.getPlazo()), formatea.format(orden.getMonto_cuota()), orden.getEstado(), orden.getIdordencompra()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
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

    private class GrillaCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todosimple()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getDireccion(), cli.getRuc(), String.valueOf(cli.getGiraduria().getCodigo()), cli.getGiraduria().getNombre()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class GrillaGiraduria extends Thread {

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

    private class GrillaCasa extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocasa.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocasa.removeRow(0);
            }
            casaDAO DAOCASA = new casaDAO();
            try {
                for (casa ca : DAOCASA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre()};
                    modelocasa.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacasa.setRowSorter(new TableRowSorter(modelocasa));
            int cantFilas = tablacasa.getRowCount();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.GenerarAsientosBancosDAO;
import DAO.bancosDAO;
import DAO.cabecera_asientoDAO;
import DAO.rendicion_gastosDAO;
import DAO.comprobanteDAO;
import DAO.config_contableDAO;
import DAO.configuracionDAO;
import DAO.extraccionDAO;
import DAO.gastos_comprasDAO;
import DAO.monedaDAO;
import DAO.proveedorDAO;
import DAO.rubro_compraDAO;
import DAO.sucursalDAO;
import Modelo.Tablas;
import Modelo.banco;
import Modelo.rendicion_gastos;
import Modelo.comprobante;
import Modelo.config_contable;
import Modelo.configuracion;
import Modelo.edificio;
import Modelo.extraccion;
import Modelo.gasto_unidad;
import Modelo.gastos_compras;
import Modelo.moneda;
import Modelo.proveedor;
import Modelo.rubro_compra;
import Modelo.sucursal;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class rendicion_de_fondos extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modeloproveedor = new Tablas();
    Tablas modelobanco = new Tablas();
    Tablas modelorubro = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelocomprobante = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modelounidad = new Tablas();
    Tablas modelosucursal = new Tablas();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrounidad, trsfiltromoneda,
            trsfiltrobanco, trsfiltrodetalle, trsfiltrosuc, trsfiltrocompr,
            trsfiltrorubro, trsfiltroproveedor;
    int nProp = 0;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("####");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String ruta, cNombre = null;
    String cModo = null;
    int nFila = -1;

    /**
     * Creates new form Template
     */
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconoitemnuevo = new ImageIcon("src/Iconos/pencil_add.png");
    ImageIcon iconoitemupdate = new ImageIcon("src/Iconos/pencil.png");
    ImageIcon iconoitemdelete = new ImageIcon("src/Iconos/pencil_delete.png");

    /**
     * ******************************
     */
    double nExentas = 0.0D;
    double nGravadas10 = 0.0D;
    double nGravadas5 = 0.0D;
    double nTotal = 0.0D;
    double nIva10 = 0.0D;
    double nIva5 = 0.0D;
    double nCotizacion = 0.0D;
    String cCotizacion = null;

    public rendicion_de_fondos() {
        initComponents();
        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.botonsalir.setIcon(iconosalir);
        this.buscarbanco.setIcon(iconobuscar);
        this.refrescar.setIcon(icorefresh);
        this.nuevoitem.setIcon(iconoitemnuevo);
        this.editaritem.setIcon(iconoitemupdate);
        this.delitem.setIcon(iconoitemdelete);
        this.creferencia.setVisible(false);
        this.buscarSucursal.setIcon(iconobuscar);
        this.buscarbanco.setIcon(iconobuscar);
        this.BuscarRubro.setIcon(iconobuscar);
        this.BuscarProveedor.setIcon(iconobuscar);
        this.BuscarComprobante.setIcon(iconobuscar);
        this.BuscarMoneda.setIcon(iconobuscar);

        this.Salir.setIcon(iconosalir);
        this.Grabar.setIcon(iconograbar);
        //this.jTable1.setShowHorizontalLines(false);
        //  this.setAlwaysOnTop(true); Convierte en Modal un jFrame
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setText("0");
        this.idControl.setVisible(false);
        //this.etiqueta.setSize(372, 259);
        this.Inicializar();
        this.cargarTitulo();
        this.TituloDetalle();
        this.TitBanco();
        this.TitSuc();
        this.TitRubro();
        this.TitCompr();
        this.TitMoneda();
        this.TitProveedor();

        this.limpiaritems();

        GrillaMoneda grillamon = new GrillaMoneda();
        Thread hiloca = new Thread(grillamon);
        hiloca.start();

        GrillaGastos GrillaOC = new GrillaGastos();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();
        fechasKeyListeners();
        ///////////////////////////////////////////
        //Config.cNivelUsuario="1";
        //Config.CodUsuario="1";
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

        detalle_gastos = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        numero = new javax.swing.JTextField();
        bancoeti = new javax.swing.JLabel();
        banco = new javax.swing.JTextField();
        buscarbanco = new javax.swing.JButton();
        nombrebanco = new javax.swing.JTextField();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        creferencia = new javax.swing.JTextField();
        nrocheque = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        importe = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabladetalle = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        nuevoitem = new javax.swing.JButton();
        editaritem = new javax.swing.JButton();
        delitem = new javax.swing.JButton();
        Bbanco = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        jTBuscarBanco = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        AceptarBanco = new javax.swing.JButton();
        SalirBanco = new javax.swing.JButton();
        itemgastos = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        GrabarItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        fechafactura = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        exentas = new javax.swing.JFormattedTextField();
        gravadas10 = new javax.swing.JFormattedTextField();
        iva10 = new javax.swing.JFormattedTextField();
        gravadas5 = new javax.swing.JFormattedTextField();
        iva5 = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Totalneto = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        proveedor = new javax.swing.JTextField();
        nombreproveedor = new javax.swing.JTextField();
        timbrado = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        vencimientotimbrado = new com.toedter.calendar.JDateChooser();
        BuscarProveedor = new javax.swing.JButton();
        moneda = new javax.swing.JTextField();
        BuscarMoneda = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        comprobante = new javax.swing.JTextField();
        BuscarComprobante = new javax.swing.JButton();
        nombrecomprobante = new javax.swing.JTextField();
        rubro = new javax.swing.JTextField();
        BuscarRubro = new javax.swing.JButton();
        nombrerubro = new javax.swing.JTextField();
        nombreinmuebleeti = new javax.swing.JLabel();
        nrofactura = new javax.swing.JTextField();
        fondo = new javax.swing.JLabel();
        BSucursal = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BRubro = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        comborubro = new javax.swing.JComboBox();
        jTBuscarRubro = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablarubro = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarRubro = new javax.swing.JButton();
        SalirRubro = new javax.swing.JButton();
        BComprobantes = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        comboComprobantes = new javax.swing.JComboBox();
        jTBuscarComprobantes = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablacomprobante = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarComprobantes = new javax.swing.JButton();
        SalirComprobantes = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        BProveedor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        comboproveedor = new javax.swing.JComboBox();
        jTBuscarProveedor = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaproveedor = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarProveedor = new javax.swing.JButton();
        SalirProveedor = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        Unidades = new org.edisoncor.gui.label.LabelMetric();
        jComboBoxBusqueda = new javax.swing.JComboBox();
        buscarcadena = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        Modificar = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        botonsalir = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        detalle_gastos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_gastosFocusGained(evt);
            }
        });
        detalle_gastos.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_gastosWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_gastos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_gastosWindowActivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Fecha");

        fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaKeyPressed(evt);
            }
        });

        jLabel15.setText("N° Cheque");

        jLabel12.setText("Número");

        numero.setEditable(false);
        numero.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numero.setEnabled(false);
        numero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numeroFocusGained(evt);
            }
        });
        numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroActionPerformed(evt);
            }
        });
        numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numeroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numeroKeyReleased(evt);
            }
        });

        bancoeti.setText("Caja/Banco");

        banco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        banco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bancoFocusGained(evt);
            }
        });
        banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bancoActionPerformed(evt);
            }
        });
        banco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bancoKeyPressed(evt);
            }
        });

        buscarbanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarbanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buscarbancoMouseClicked(evt);
            }
        });
        buscarbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarbancoActionPerformed(evt);
            }
        });
        buscarbanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscarbancoKeyPressed(evt);
            }
        });

        nombrebanco.setEnabled(false);
        nombrebanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrebancoKeyPressed(evt);
            }
        });

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

        jLabel3.setText("Sucursal");

        creferencia.setEditable(false);
        creferencia.setEnabled(false);

        nrocheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        nrocheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrocheque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nrochequeFocusGained(evt);
            }
        });
        nrocheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nrochequeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel2)))
                        .addGap(42, 42, 42)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bancoeti)
                    .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel15))
                    .addComponent(creferencia, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(nrocheque))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel2)
                            .addComponent(bancoeti)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buscarSucursal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setToolTipText("");
        jPanel3.setName("Concepto"); // NOI18N

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

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane2.setViewportView(observaciones);

        importe.setEditable(false);
        importe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        importe.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        importe.setEnabled(false);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(importe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Grabar)
                            .addComponent(Salir))
                        .addGap(1, 1, 1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabladetalle.setModel(modelodetalle);
        tabladetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane4.setViewportView(tabladetalle);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nuevoitem.setText("Agregar");
        nuevoitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoitemActionPerformed(evt);
            }
        });

        editaritem.setText("Editar");
        editaritem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editaritemActionPerformed(evt);
            }
        });

        delitem.setText("Eliminar");
        delitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delitemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editaritem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nuevoitem, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nuevoitem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editaritem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delitem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout detalle_gastosLayout = new javax.swing.GroupLayout(detalle_gastos.getContentPane());
        detalle_gastos.getContentPane().setLayout(detalle_gastosLayout);
        detalle_gastosLayout.setHorizontalGroup(
            detalle_gastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_gastosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_gastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        detalle_gastosLayout.setVerticalGroup(
            detalle_gastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, detalle_gastosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        Bbanco.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Bbanco.setTitle("null");

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarBanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarBanco.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarBancoActionPerformed(evt);
            }
        });
        jTBuscarBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarBancoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablabanco.setModel(modelobanco       );
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
        jScrollPane8.setViewportView(tablabanco);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarBanco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarBanco.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarBancoActionPerformed(evt);
            }
        });

        SalirBanco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirBanco.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirBancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarBanco)
                    .addComponent(SalirBanco))
                .addContainerGap())
        );

        javax.swing.GroupLayout BbancoLayout = new javax.swing.GroupLayout(Bbanco.getContentPane());
        Bbanco.getContentPane().setLayout(BbancoLayout);
        BbancoLayout.setHorizontalGroup(
            BbancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BbancoLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BbancoLayout.setVerticalGroup(
            BbancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BbancoLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        itemgastos.setMinimumSize(new java.awt.Dimension(800, 590));
        itemgastos.setResizable(false);
        itemgastos.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItem.setText("Grabar");
        GrabarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemActionPerformed(evt);
            }
        });

        SalirItem.setText("Salir");
        SalirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(504, 504, 504)
                .addComponent(GrabarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SalirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarItem)
                    .addComponent(SalirItem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        itemgastos.getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 730, 50));

        panel2.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel2.setColorSecundario(new java.awt.Color(0, 204, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        itemgastos.getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel1.text")); // NOI18N
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, 20));
        jPanel7.add(fechafactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 100, 22));

        jLabel13.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel2.text")); // NOI18N
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 50, 20));
        jPanel7.add(vencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 100, 22));

        jLabel14.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel3.text")); // NOI18N
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, 20));

        jLabel17.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel5.text")); // NOI18N
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 70, 20));

        jLabel19.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel8.text")); // NOI18N
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 60, 20));

        cotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion.setText("1");
        cotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });
        jPanel7.add(cotizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 80, -1));

        jLabel20.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel9.text")); // NOI18N
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 50, 20));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jPanel1.border.title"))); // NOI18N

        exentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        exentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        exentas.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.exentas.text")); // NOI18N
        exentas.setToolTipText("");
        exentas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                exentasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                exentasFocusLost(evt);
            }
        });
        exentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                exentasKeyPressed(evt);
            }
        });

        gravadas10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gravadas10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas10.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.exentas.text")); // NOI18N
        gravadas10.setToolTipText("");
        gravadas10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gravadas10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gravadas10FocusLost(evt);
            }
        });
        gravadas10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gravadas10KeyPressed(evt);
            }
        });

        iva10.setEditable(false);
        iva10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        iva10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        iva10.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.exentas.text")); // NOI18N
        iva10.setToolTipText("");
        iva10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                iva10FocusGained(evt);
            }
        });
        iva10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                iva10KeyPressed(evt);
            }
        });

        gravadas5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gravadas5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas5.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.exentas.text")); // NOI18N
        gravadas5.setToolTipText("");
        gravadas5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                gravadas5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                gravadas5FocusLost(evt);
            }
        });
        gravadas5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gravadas5KeyPressed(evt);
            }
        });

        iva5.setEditable(false);
        iva5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        iva5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        iva5.setToolTipText("");
        iva5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                iva5FocusGained(evt);
            }
        });
        iva5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                iva5KeyPressed(evt);
            }
        });

        jLabel21.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel10.text")); // NOI18N

        jLabel22.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel11.text")); // NOI18N

        jLabel23.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel12.text")); // NOI18N

        jLabel24.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel13.text")); // NOI18N

        jLabel25.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel14.text")); // NOI18N

        Totalneto.setEditable(false);
        Totalneto.setBackground(new java.awt.Color(255, 51, 51));
        Totalneto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        Totalneto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Totalneto.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.exentas.text")); // NOI18N
        Totalneto.setToolTipText("");
        Totalneto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Totalneto.setEnabled(false);
        Totalneto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalnetoActionPerformed(evt);
            }
        });

        jLabel26.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel18.text")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Totalneto)
                    .addComponent(exentas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gravadas10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iva10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gravadas5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iva5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gravadas10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iva10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iva5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 280, 240));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jPanel4.border.title"))); // NOI18N

        observacion.setColumns(20);
        observacion.setRows(5);
        observacion.setText("Observaciones");
        jScrollPane9.setViewportView(observacion);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 440, 240));

        proveedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        proveedor.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.proveedor.text")); // NOI18N
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
        });
        jPanel7.add(proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 40, -1));

        nombreproveedor.setEditable(false);
        nombreproveedor.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.nombreproveedor.text")); // NOI18N
        nombreproveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreproveedor.setEnabled(false);
        jPanel7.add(nombreproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 220, -1));

        timbrado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        timbrado.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.timbrado.text")); // NOI18N
        timbrado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                timbradoFocusGained(evt);
            }
        });
        timbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                timbradoKeyPressed(evt);
            }
        });
        jPanel7.add(timbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 90, -1));

        jLabel27.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel15.text")); // NOI18N
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 20));

        jLabel28.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel16.text")); // NOI18N
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, 20));

        jLabel29.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.jLabel17.text")); // NOI18N
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 110, 20));
        jPanel7.add(vencimientotimbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 100, 22));

        BuscarProveedor.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "detalle_gastos.BuscarProveedor.text")); // NOI18N
        BuscarProveedor.setBorder(null);
        BuscarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarProveedorActionPerformed(evt);
            }
        });
        jPanel7.add(BuscarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 20, 22));

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        moneda.setText("0");
        moneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                monedaKeyPressed(evt);
            }
        });
        jPanel7.add(moneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 40, -1));

        BuscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarMoneda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarMonedaMouseClicked(evt);
            }
        });
        BuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarMonedaActionPerformed(evt);
            }
        });
        BuscarMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarMonedaKeyPressed(evt);
            }
        });
        jPanel7.add(BuscarMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 22, 22));

        nombremoneda.setEnabled(false);
        nombremoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombremonedaKeyPressed(evt);
            }
        });
        jPanel7.add(nombremoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 130, -1));

        comprobante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comprobante.setText("0");
        comprobante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comprobanteFocusGained(evt);
            }
        });
        comprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprobanteActionPerformed(evt);
            }
        });
        comprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comprobanteKeyPressed(evt);
            }
        });
        jPanel7.add(comprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 40, -1));

        BuscarComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarComprobanteMouseClicked(evt);
            }
        });
        BuscarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarComprobanteActionPerformed(evt);
            }
        });
        BuscarComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarComprobanteKeyPressed(evt);
            }
        });
        jPanel7.add(BuscarComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 22, 22));

        nombrecomprobante.setEnabled(false);
        nombrecomprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrecomprobanteKeyPressed(evt);
            }
        });
        jPanel7.add(nombrecomprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 130, -1));

        rubro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rubro.setText("0");
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
        });
        jPanel7.add(rubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 32, -1));

        BuscarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarRubro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarRubroMouseClicked(evt);
            }
        });
        BuscarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarRubroActionPerformed(evt);
            }
        });
        BuscarRubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarRubroKeyPressed(evt);
            }
        });
        jPanel7.add(BuscarRubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 22, 22));

        nombrerubro.setEnabled(false);
        nombrerubro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombrerubroKeyPressed(evt);
            }
        });
        jPanel7.add(nombrerubro, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 150, -1));

        nombreinmuebleeti.setText("Rubro");
        jPanel7.add(nombreinmuebleeti, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, -1, -1));

        nrofactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrofactura.setToolTipText("Format Ejemplo 001-001-0000712");
        nrofactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrofacturaKeyPressed(evt);
            }
        });
        jPanel7.add(nrofactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 120, -1));

        itemgastos.getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 776, 420));
        itemgastos.getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, -4, 800, 570));

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
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        jScrollPane5.setViewportView(tablasucursal);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.SalirCliente.text")); // NOI18N
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
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BRubro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BRubro.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comborubro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comborubro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comborubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comborubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comborubroActionPerformed(evt);
            }
        });

        jTBuscarRubro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarRubro.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comborubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablarubro.setModel(modelorubro       );
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
        jScrollPane7.setViewportView(tablarubro);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarRubro.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarRubroActionPerformed(evt);
            }
        });

        SalirRubro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirRubro.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirRubro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarRubro)
                    .addComponent(SalirRubro))
                .addContainerGap())
        );

        javax.swing.GroupLayout BRubroLayout = new javax.swing.GroupLayout(BRubro.getContentPane());
        BRubro.getContentPane().setLayout(BRubroLayout);
        BRubroLayout.setHorizontalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BRubroLayout.setVerticalGroup(
            BRubroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BRubroLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BComprobantes.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BComprobantes.setTitle("null");
        BComprobantes.setMinimumSize(new java.awt.Dimension(490, 595));

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboComprobantes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboComprobantes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboComprobantesActionPerformed(evt);
            }
        });

        jTBuscarComprobantes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarComprobantes.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarComprobantesActionPerformed(evt);
            }
        });
        jTBuscarComprobantes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarComprobantesKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(comboComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTBuscarComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacomprobante.setModel(modelocomprobante);
        tablacomprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacomprobanteMouseClicked(evt);
            }
        });
        tablacomprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacomprobanteKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(tablacomprobante);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarComprobantes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarComprobantes.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarComprobantesActionPerformed(evt);
            }
        });

        SalirComprobantes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirComprobantes.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirComprobantes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirComprobantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirComprobantesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirComprobantes, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarComprobantes)
                    .addComponent(SalirComprobantes))
                .addContainerGap())
        );

        javax.swing.GroupLayout BComprobantesLayout = new javax.swing.GroupLayout(BComprobantes.getContentPane());
        BComprobantes.getContentPane().setLayout(BComprobantesLayout);
        BComprobantesLayout.setHorizontalGroup(
            BComprobantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane10)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BComprobantesLayout.setVerticalGroup(
            BComprobantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BComprobantesLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane11.setViewportView(tablamoneda);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BProveedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProveedor.setTitle("null");

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproveedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproveedorActionPerformed(evt);
            }
        });

        jTBuscarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarProveedor.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarProveedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscarProveedorFocusGained(evt);
            }
        });
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

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaproveedor.setModel(modeloproveedor);
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
        jScrollPane12.setViewportView(tablaproveedor);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarProveedor.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProveedorActionPerformed(evt);
            }
        });

        SalirProveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirProveedor.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarProveedor)
                    .addComponent(SalirProveedor))
                .addContainerGap())
        );

        javax.swing.GroupLayout BProveedorLayout = new javax.swing.GroupLayout(BProveedor.getContentPane());
        BProveedor.getContentPane().setLayout(BProveedorLayout);
        BProveedorLayout.setHorizontalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProveedorLayout.setVerticalGroup(
            BProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProveedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
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

        Unidades.setBackground(new java.awt.Color(255, 255, 255));
        Unidades.setText("Rendición de Gastos");

        jComboBoxBusqueda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBoxBusqueda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Número" }));
        jComboBoxBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBusquedaActionPerformed(evt);
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
                .addComponent(Unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        Eliminar.setText("Eliminar Registro");
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

        botonsalir.setBackground(new java.awt.Color(255, 255, 255));
        botonsalir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        botonsalir.setText("     Salir");
        botonsalir.setToolTipText("");
        botonsalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonsalirActionPerformed(evt);
            }
        });

        idControl.setEditable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(rendicion_de_fondos.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

        refrescar.setText("Refrescar");
        refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
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
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(botonsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))))
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
                .addComponent(botonsalir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fechasKeyListeners() {
        fechafactura.getDateEditor().getUiComponent().addKeyListener( //fecha factura
                new KeyListener() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (fechafactura.getDate() != null) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                        vencimiento.getDateEditor().getUiComponent().requestFocusInWindow();
                    } else {
                        JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Fecha de Factura", "ATENCION", JOptionPane.CLOSED_OPTION);
                    }
                }
                if (evt.getKeyCode() == KeyEvent.VK_UP) {
                    nrofactura.requestFocus();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        }
        );

        vencimiento.getDateEditor().getUiComponent().addKeyListener( //fecha factura
                new KeyListener() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (vencimiento.getDate() != null) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                        comprobante.requestFocus();
                    } else {
                        JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Fecha de Vencimiento de Factura", "ATENCION", JOptionPane.CLOSED_OPTION);
                    }
                }
                if (evt.getKeyCode() == KeyEvent.VK_UP) {
                    fechafactura.requestFocus();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        }
        );

        vencimientotimbrado.getDateEditor().getUiComponent().addKeyListener( //fecha factura
                new KeyListener() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (vencimientotimbrado.getDate() != null) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                        exentas.requestFocus();
                    } else {
                        JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Fecha de Vencimiento de Timbrado", "ATENCION", JOptionPane.CLOSED_OPTION);
                    }
                }
                if (evt.getKeyCode() == KeyEvent.VK_UP) {
                    timbrado.requestFocus();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        }
        );
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

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

    private void TitProveedor() {
        modeloproveedor.addColumn("Código");
        modeloproveedor.addColumn("Nombre");
        modeloproveedor.addColumn("Ruc");
        modeloproveedor.addColumn("Nro. Timbrado");
        modeloproveedor.addColumn("Vencimiento");

        int[] anchos = {90, 200, 90, 90, 90};
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

    public void filtroproveedor(int nNumeroColumna) {
        trsfiltroproveedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarProveedor.getText(), nNumeroColumna));
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

    private void TitRubro() {
        modelorubro.addColumn("Código");
        modelorubro.addColumn("Nombre");

        int[] anchos = {60, 120};
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

    private void TitCompr() {
        modelocomprobante.addColumn("Código");
        modelocomprobante.addColumn("Nombre");

        int[] anchos = {80, 210};
        for (int i = 0; i < modelocomprobante.getColumnCount(); i++) {
            tablacomprobante.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacomprobante.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacomprobante.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacomprobante.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacomprobante.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void sumatoria() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA
        double sumdebe, sumhaber = 0.00;
        double sumdebetotal = 0.00;
        double sumhabertotal = 0.00;
        double sumdiferencia = 0.00;
        String cValorDebe = "";
        String cValorHaber = "";
        int totalRow = this.tabladetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //Primero capturamos el porcentaje del IVA
            cValorDebe = String.valueOf(this.tabladetalle.getValueAt(i, 19));

            cValorDebe = cValorDebe.replace(".", "").replace(",", ".");

            sumdebe = Double.valueOf(cValorDebe);
            sumdebetotal += sumdebe;

        }
        tabladetalle.setRowSorter(new TableRowSorter(modelodetalle));
        int cantFilas = tabladetalle.getRowCount();
        if (cantFilas > 0) {
            delitem.setEnabled(true);
            editaritem.setEnabled(true);
        } else {
            delitem.setEnabled(false);
            editaritem.setEnabled(false);
        }
        this.importe.setText(formatea.format(sumdebetotal));
    }

    private void limpiaritems() {

        this.nrofactura.setText("");
        this.fechafactura.setCalendar(c2);
        this.vencimiento.setCalendar(c2);
        this.comprobante.setText("0");
        this.nombrecomprobante.setText("");
        this.rubro.setText("0");
        this.nombrerubro.setText("");
        this.moneda.setText("1");
        this.nombremoneda.setText("GUARANIES");
        this.cotizacion.setText("1");
        this.timbrado.setText("0");
        this.vencimientotimbrado.setCalendar(c2);
        this.proveedor.setText("0");
        this.nombreproveedor.setText("");
        this.exentas.setText("0");
        this.gravadas10.setText("0");
        this.iva10.setText("0");
        this.gravadas5.setText("0");
        this.iva5.setText("0");
        this.Totalneto.setText("0");
        this.observacion.setText("Observaciones de ");
    }

    private void Inicializar() {
        this.dInicial.setCalendar(c2);
        this.dFinal.setCalendar(c2);
        this.fecha.setCalendar(c2);
    }


    private void jComboBoxBusquedaActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBoxBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxBusquedaActionPerformed

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText()).toUpperCase();
                buscarcadena.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBoxBusqueda.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 0;
                        break;//por Numero
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);

    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void botonsalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_botonsalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_botonsalirActionPerformed

    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        this.limpiar();
        //tabla
        modelodetalle.setRowCount(0);
        detalle_gastos.setModal(true);
        //                        (Ancho,Alto)
        detalle_gastos.setSize(850, 530);
        //Establecemos un título para el jDialog
        detalle_gastos.setTitle("Agregar Gastos");
        detalle_gastos.setLocationRelativeTo(null);
        detalle_gastos.setVisible(true);
        sucursal.requestFocus();
          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = null;
        config = configDAO.consultar();
        this.sucursal.setText(String.valueOf(config.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(config.getSucursaldefecto().getNombre());
        this.numero.setText("0");
        this.fecha.setCalendar(c2);
        this.banco.setText("0");
        this.nombrebanco.setText("");
        this.importe.setText("0");
        this.nrocheque.setText("0");
        this.editaritem.setEnabled(false);
        this.delitem.setEnabled(false);
    }

    private void jTable1KeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idControl.setText(this.jTable1.getValueAt(nFila, 0).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        this.limpiar();
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nFila = this.jTable1.getSelectedRow();
            rendicion_gastosDAO caDAO = new rendicion_gastosDAO();
            rendicion_gastos ca = null;
            this.numero.setText(this.jTable1.getValueAt(nFila, 0).toString());
            try {
                ca = caDAO.buscarId(Double.valueOf(this.numero.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (ca != null) {
                //SE CARGAN LOS DATOS DE LA CABECERA
                creferencia.setText(ca.getCreferencia());
                sucursal.setText(String.valueOf(ca.getSucursal().getCodigo()));
                nombresucursal.setText(ca.getSucursal().getNombre());
                fecha.setDate(ca.getFecha());
                importe.setText(formatea.format(ca.getImporte()));
                banco.setText(String.valueOf(ca.getBanco().getCodigo()));
                nombrebanco.setText(ca.getBanco().getNombre());
                nrocheque.setText(ca.getNrocheque());
                observaciones.setText(ca.getObservacion());
                // SE CARGAN LOS DETALLES
                int cantidadRegistro = modelodetalle.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelodetalle.removeRow(0);
                }

                gastos_comprasDAO detDAO = new gastos_comprasDAO();
                try {
                    for (gastos_compras detasi : detDAO.MostrarDetalleGastos(Double.valueOf(numero.getText()))) {
                        Object Detalle[] = {
                            detasi.getFormatofactura(), //0
                            formatoFecha.format(detasi.getFechafactura()), //1
                            formatoFecha.format(detasi.getVencimiento()), //2
                            detasi.getComprobante().getCodigo(), //4
                            detasi.getComprobante().getNombre(), //5
                            detasi.getConcepto().getCodigo(), //6
                            detasi.getConcepto().getNombre(), //7
                            detasi.getMoneda().getCodigo(), //8
                            detasi.getMoneda().getNombre(), //9
                            formatea.format(detasi.getCotizacion()),
                            detasi.getTimbrado(), //14
                            formatoFecha.format(detasi.getVencimientotimbrado()), //15
                            detasi.getProveedor().getCodigo(), //16
                            detasi.getProveedor().getNombre(), //17
                            formatea.format(detasi.getExentas()),//18
                            formatea.format(detasi.getGravadas10()),//19
                            formatea.format(detasi.getIva10()),//20
                            formatea.format(detasi.getGravadas5()),//21
                            formatea.format(detasi.getIva5()),//22
                            formatea.format(detasi.getTotalneto()), //23
                            detasi.getObservacion()};
                        modelodetalle.addRow(Detalle);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                int cantFilas = tabladetalle.getRowCount();
                if (cantFilas > 0) {
                    editaritem.setEnabled(true);
                    delitem.setEnabled(true);
                } else {
                    editaritem.setEnabled(false);
                    delitem.setEnabled(false);
                }
                detalle_gastos.setModal(true);
                detalle_gastos.setSize(850, 530);
                //Establecemos un título para el jDialog
                detalle_gastos.setTitle("Modificar Gastos");
                detalle_gastos.setLocationRelativeTo(null);
                detalle_gastos.setVisible(true);
                sucursal.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
            }
            this.refrescar.doClick();
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

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = jTable1.getSelectedRow();
            double num = Double.valueOf(jTable1.getValueAt(nFila, 0).toString());
            double ncontrol = Double.valueOf(jTable1.getValueAt(nFila, 6).toString());

            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                rendicion_gastosDAO gastoDAO = new rendicion_gastosDAO();
                extraccionDAO extDAO = new extraccionDAO();
                cabecera_asientoDAO cabDAO = new cabecera_asientoDAO();
                try {
                    rendicion_gastos gasto = gastoDAO.buscarId(num);
                    if (gasto == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        gastoDAO.borrardetalle(num);
                        gastoDAO.eliminarItemGastos(num);
                        extDAO.borrarExtraccion(gasto.getCreferencia());
                        cabDAO.eliminarAsiento(ncontrol);
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
            GrillaGastos GrillaAs = new GrillaGastos();
            Thread HiloGrilla = new Thread(GrillaAs);
            HiloGrilla.start();
        } else {

            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }

    }//GEN-LAST:event_EliminarActionPerformed

    private void jTable1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_gastos.setVisible(false);
        detalle_gastos.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed

        if (this.sucursal.getText().isEmpty() || this.sucursal.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Sucursal");
            this.sucursal.requestFocus();
            return;
        }
        if (this.banco.getText().isEmpty() || this.banco.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Origen de Pagos");
            this.banco.requestFocus();
            return;
        }
        if (this.nrocheque.getText().isEmpty() || this.nrocheque.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Cheque");
            this.nrocheque.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            String cReferencia = null;
            if (Double.valueOf(numero.getText()) == 0) {
                cReferencia = UUID.crearUUID();
                cReferencia = cReferencia.substring(1, 30);
            } else {
                cReferencia = creferencia.getText();
            }
            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            bancosDAO bcoDAO = new bancosDAO();
            banco bco = null;

            monedaDAO monDAO = new monedaDAO();
            moneda mon = null;

            try {
                suc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
                bco = bcoDAO.buscarId(Integer.valueOf(banco.getText()));
                mon = monDAO.buscarId(1);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            //RENDICIONES
            rendicion_gastos c = new rendicion_gastos();
            rendicion_gastosDAO grabargasto = new rendicion_gastosDAO();
            //Capturamos los Valores BigDecimal
            ///BANCOS
            extraccionDAO extraDAO = new extraccionDAO();
            extraccion ext = new extraccion();

            String cImporte = importe.getText();
            //////REPOSICIONES
            cImporte = cImporte.replace(".", "").replace(",", ".");
            c.setCreferencia(cReferencia);
            c.setFecha(FechaProceso);
            c.setSucursal(suc);
            c.setImporte(Double.valueOf(cImporte));
            c.setBanco(bco);
            c.setNumero(Double.valueOf(numero.getText()));
            c.setNrocheque(nrocheque.getText());
            c.setObservacion(observaciones.getText());
            c.setUsuarioalta(Integer.valueOf(Config.CodUsuario));
            c.setUsuarioalta(1);
            c.setCotizacion(1);
            c.setMoneda(mon);
            ////EXTRACCION BANCARIA
            ext.setIdmovimiento(cReferencia);
            ext.setFecha(FechaProceso);
            ext.setVencimiento(FechaProceso);
            ext.setBanco(bco);
            ext.setSucursal(suc);
            ext.setMoneda(mon);
            ext.setCotizacion(new BigDecimal(1));
            ext.setDocumento(nrocheque.getText());
            ext.setImporte(new BigDecimal(cImporte));
            ext.setObservaciones(observaciones.getText());
            ext.setTipo("D");

            String iddoc = null;
            int nItem = 0;
            int totalRow = modelodetalle.getRowCount();
            totalRow -= 1;

            //CAPTURAMOS LOS DETALLES DE GASTOS
            String detalle = "[";
            for (int i = 0; i <= (totalRow); i++) {
                iddoc = UUID.crearUUID();
                iddoc = iddoc.substring(1, 30);

                //Capturo y valido Producto
                nItem = nItem + 1;
                //Porcentaje
                //
                String cExenta = modelodetalle.getValueAt(i, 14).toString().replace(".", "").replace(",", ".");
                String cGravada10 = modelodetalle.getValueAt(i, 15).toString().replace(".", "").replace(",", ".");
                String cIva10 = modelodetalle.getValueAt(i, 16).toString().replace(".", "").replace(",", ".");
                String cGravada5 = modelodetalle.getValueAt(i, 17).toString().replace(".", "").replace(",", ".");
                String cIva5 = modelodetalle.getValueAt(i, 18).toString().replace(".", "").replace(",", ".");
                cImporte = modelodetalle.getValueAt(i, 19).toString().replace(".", "").replace(",", ".");
                String cCotizacion = modelodetalle.getValueAt(i, 9).toString().replace(".", "").replace(",", ".");
                //LE CAMBIAMOS EL FORMATO A LA FECHA DEL JTABLEDETALLE
                JDateChooser fechaAux = new JDateChooser();
                JDateChooser vencAux = new JDateChooser();
                JDateChooser vencTimAux = new JDateChooser();

                try {
                    fechaAux.setDate(formatoFecha.parse(tabladetalle.getValueAt(i, 1).toString()));
                    vencAux.setDate(formatoFecha.parse(tabladetalle.getValueAt(i, 2).toString()));
                    vencTimAux.setDate(formatoFecha.parse(tabladetalle.getValueAt(i, 11).toString()));
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cObservacion = modelodetalle.getValueAt(i, 20).toString();
                if (cObservacion.equals("")) {
                    cObservacion = "SIN DATOS";
                }
                String cFactura = modelodetalle.getValueAt(i, 0).toString();
                cFactura = cFactura.replace("-", "");
                String linea = "{fondofijo : " + numero.getText() + ","
                        + "formatofactura : " + modelodetalle.getValueAt(i, 0).toString() + ","
                        + "nrofactura : " + cFactura + ","
                        + "fecha : " + ODate.de_java_a_sql(fechaAux.getDate()) + ","
                        + "primer_vence: " + ODate.de_java_a_sql(vencAux.getDate()) + ","
                        + "comprobante : " + modelodetalle.getValueAt(i, 3).toString() + ","
                        + "concepto : " + modelodetalle.getValueAt(i, 5).toString() + ","
                        + "moneda : " + modelodetalle.getValueAt(i, 7).toString() + ","//8
                        + "timbrado : " + modelodetalle.getValueAt(i, 10).toString() + ","
                        + "vencetimbrado : " + ODate.de_java_a_sql(vencTimAux.getDate()) + ","
                        + "proveedor : " + modelodetalle.getValueAt(i, 12).toString() + ","
                        + "exentas : " + cExenta + ","//13
                        + "gravadas10 : " + cGravada10 + ","
                        + "iva10 : " + cIva10 + ","
                        + "gravadas5 : " + cGravada5 + ","
                        + "iva5 : " + cIva5 + ","
                        + "totalneto : " + cImporte + ","//18
                        + "observacion : '" + cObservacion + "',"
                        + "cotizacion : " + cCotizacion + ","
                        + "sucursal : " + sucursal.getText() + ","
                        + "creferencia : '" + iddoc + "'},";
                detalle += linea;
            }

            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";

            //CAPTURAMOS EL BANCO O LA CAJA
            if (Double.valueOf(numero.getText()) == 0) {
                try {
                    grabargasto.AgregarGastos(c, detalle);
                    extraDAO.insertarMovBanco(ext);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                    this.banco.requestFocus();
                    return;
                }
            } else {
                try {
                    extraDAO.borrarExtraccion(cReferencia);
                    grabargasto.ActualizarGastos(c, detalle);
                    extraDAO.insertarMovBanco(ext);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                    this.banco.requestFocus();
                    return;
                }
            }
            config_contableDAO contableDAO = new config_contableDAO();
            config_contable contable = null;
            contable = contableDAO.consultar();
            GenerarAsientosBancosDAO genDAO = new GenerarAsientosBancosDAO();
            if (contable.getFondofijo() == 1) {
                genDAO.generarRendicionFondoFijoItem(cReferencia);
            }
            refrescar.doClick();
            detalle_gastos.setModal(false);
            detalle_gastos.setVisible(false);
        }

    }//GEN-LAST:event_GrabarActionPerformed

    private void detalle_gastosFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_gastosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_gastosFocusGained

    private void detalle_gastosWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_gastosWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_gastosWindowGainedFocus

    private void detalle_gastosWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_gastosWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_gastosWindowActivated

    private void jTable1PropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange


    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void importeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importeFocusGained
        importe.selectAll();
    }//GEN-LAST:event_importeFocusGained

    private void importeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeKeyPressed

    }//GEN-LAST:event_importeKeyPressed

    private void numeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numeroFocusGained
        numero.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroFocusGained

    private void numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroActionPerformed
        /*        propietarioDAO pdDAO = new propietarioDAO();
        propietario pd = null;
        try {
            pd = pdDAO.BuscarProducto(this.codpro.getText());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (pd.getCodigo() != null) {
            JOptionPane.showMessageDialog(null, "Este Producto ya Existe");
            this.codpro.requestFocus();
            return;
        }*/
    }//GEN-LAST:event_numeroActionPerformed

    private void numeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyPressed

    private void numeroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyReleased
        String letras = ConvertirMayusculas.cadena(numero);
        numero.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyReleased

    private void bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bancoActionPerformed
        this.buscarbanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_bancoActionPerformed

    private void bancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bancoKeyPressed

    }//GEN-LAST:event_bancoKeyPressed

    private void buscarbancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buscarbancoMouseClicked
        jTBuscarBanco.requestFocus();
    }//GEN-LAST:event_buscarbancoMouseClicked

    private void buscarbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarbancoActionPerformed
        bancosDAO banDAO = new bancosDAO();
        banco ban = null;
        try {
            ban = banDAO.buscarId(Integer.valueOf(this.banco.getText()));
            if (ban.getCodigo() == 0) {
                GrillaBanco grillapa = new GrillaBanco();
                Thread hilopa = new Thread(grillapa);
                hilopa.start();
                Bbanco.setModal(true);
                Bbanco.setSize(482, 575);
                Bbanco.setLocationRelativeTo(null);
                Bbanco.setVisible(true);
                Bbanco.setTitle("Buscar Banco");
                nrocheque.requestFocus();
                Bbanco.setModal(false);
            } else {
                nombrebanco.setText(ban.getNombre());
                //Establecemos un título para el jDialog
                nrocheque.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_buscarbancoActionPerformed

    private void buscarbancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarbancoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarbancoKeyPressed

    private void nombrebancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrebancoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrebancoKeyPressed

    private void combobancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobancoActionPerformed

    private void jTBuscarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarBancoActionPerformed

    private void jTBuscarBancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarBancoKeyPressed
        this.jTBuscarBanco.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarBanco.getText()).toUpperCase();
                jTBuscarBanco.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combobanco.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrobanco(indiceColumnaTabla);
            }
        });
        trsfiltrobanco = new TableRowSorter(tablabanco.getModel());
        tablabanco.setRowSorter(trsfiltrobanco);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarBancoKeyPressed

    private void tablabancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.AceptarBanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarBanco.doClick();
        }
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarBancoActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.banco.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.Bbanco.setVisible(false);
        this.jTBuscarBanco.setText("");
        this.nrocheque.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarBancoActionPerformed

    private void SalirBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirBancoActionPerformed
        this.Bbanco.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirBancoActionPerformed

    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaGastos GrillaAs = new GrillaGastos();
        Thread HiloGrilla = new Thread(GrillaAs);
        HiloGrilla.start();        // TODO add your handling code here:*/
    }//GEN-LAST:event_refrescarActionPerformed

    private void nuevoitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoitemActionPerformed
        this.limpiaritems();
        cModo = "1";
        itemgastos.setSize(520, 410);
        itemgastos.setLocationRelativeTo(null);
//        this.limpiaritems();
        this.GrabarItem.setText("Agregar");
        //      this.cModo.setText("");
        itemgastos.setModal(true);
        itemgastos.setVisible(true);
        nrofactura.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevoitemActionPerformed

    private void editaritemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editaritemActionPerformed
        cModo = "2";
        nFila = this.tabladetalle.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        itemgastos.setSize(520, 360);
        itemgastos.setLocationRelativeTo(null);

        nrofactura.setText(tabladetalle.getValueAt(nFila, 0).toString());
        try {
            fechafactura.setDate(formatoFecha.parse(tabladetalle.getValueAt(nFila, 1).toString()));
            vencimiento.setDate(formatoFecha.parse(tabladetalle.getValueAt(nFila, 2).toString()));
            vencimientotimbrado.setDate(formatoFecha.parse(tabladetalle.getValueAt(nFila, 11).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        this.comprobante.setText(tabladetalle.getValueAt(nFila, 3).toString());
        this.nombrecomprobante.setText(tabladetalle.getValueAt(nFila, 4).toString());
        this.rubro.setText(tabladetalle.getValueAt(nFila, 5).toString());
        this.nombrerubro.setText(tabladetalle.getValueAt(nFila, 6).toString());
        this.moneda.setText(tabladetalle.getValueAt(nFila, 7).toString());
        this.nombremoneda.setText(tabladetalle.getValueAt(nFila, 8).toString());
        this.cotizacion.setText(tabladetalle.getValueAt(nFila, 9).toString());
        this.timbrado.setText(tabladetalle.getValueAt(nFila, 10).toString());
        //this.vencimientotimbrado.setDate(c2);
        this.proveedor.setText(tabladetalle.getValueAt(nFila, 12).toString());
        this.nombreproveedor.setText(tabladetalle.getValueAt(nFila, 13).toString());
        this.exentas.setText(tabladetalle.getValueAt(nFila, 14).toString());
        this.gravadas10.setText(tabladetalle.getValueAt(nFila, 15).toString());
        this.iva10.setText(tabladetalle.getValueAt(nFila, 16).toString());
        this.gravadas5.setText(tabladetalle.getValueAt(nFila, 17).toString());
        this.iva5.setText(tabladetalle.getValueAt(nFila, 18).toString());
        this.Totalneto.setText(tabladetalle.getValueAt(nFila, 19).toString());
        this.observacion.setText(tabladetalle.getValueAt(nFila, 20).toString());
        this.GrabarItem.setText("Actualizar");
        itemgastos.setModal(true);
        itemgastos.setVisible(true);
        nrofactura.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_editaritemActionPerformed

    private void delitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delitemActionPerformed
        int a = this.tabladetalle.getSelectedRow();
        if (a < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelodetalle.removeRow(a);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                this.sumatoria();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_delitemActionPerformed

    private void SalirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        itemgastos.setModal(false);
        itemgastos.setVisible(false);
        this.detalle_gastos.setModal(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void GrabarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemActionPerformed
        int nCaracter = nrofactura.getText().indexOf("-");
        String cNumeroFactura = nrofactura.getText();
        if (nCaracter >= 0) {
            cNumeroFactura = cNumeroFactura.replace("-", "");
            boolean isNumeric = cNumeroFactura.matches("[+-]?\\d*(\\.\\d+)?");
            if (isNumeric == false) {
                JOptionPane.showMessageDialog(null, "Formato de Número de Factura no Corresponde");
                this.nrofactura.getText();
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de Número de Factura no Corresponde");
            this.nrofactura.getText();
            return;
        }

        Object[] fila = new Object[21]; //para cada elemento a guardar?
        fila[0] = nrofactura.getText();
        fila[1] = formatoFecha.format(fechafactura.getDate());
        fila[2] = formatoFecha.format(vencimiento.getDate());
        fila[3] = comprobante.getText();
        fila[4] = nombrecomprobante.getText();
        fila[5] = rubro.getText();
        fila[6] = nombrerubro.getText();
        fila[7] = moneda.getText();
        fila[8] = nombremoneda.getText();
        fila[9] = cotizacion.getText();
        fila[10] = timbrado.getText();
        fila[11] = formatoFecha.format(vencimientotimbrado.getDate());
        fila[12] = proveedor.getText();
        fila[13] = nombreproveedor.getText();
        fila[14] = exentas.getText();
        fila[15] = gravadas10.getText();
        fila[16] = iva10.getText();
        fila[17] = gravadas5.getText();
        fila[18] = iva5.getText();
        fila[19] = Totalneto.getText();
        fila[20] = observacion.getText();

        if (cModo.equals("1")) { //agregar?}
            modelodetalle.addRow(fila);
            this.nrofactura.requestFocus();
        } else { //editar?
            this.tabladetalle.setValueAt(fila[0], nFila, 0);
            this.tabladetalle.setValueAt(fila[1], nFila, 1);
            this.tabladetalle.setValueAt(fila[2], nFila, 2);
            this.tabladetalle.setValueAt(fila[3], nFila, 3);
            this.tabladetalle.setValueAt(fila[4], nFila, 4);
            this.tabladetalle.setValueAt(fila[5], nFila, 5);
            this.tabladetalle.setValueAt(fila[6], nFila, 6);
            this.tabladetalle.setValueAt(fila[7], nFila, 7);
            this.tabladetalle.setValueAt(fila[8], nFila, 8);
            this.tabladetalle.setValueAt(fila[9], nFila, 9);
            this.tabladetalle.setValueAt(fila[10], nFila, 10);
            this.tabladetalle.setValueAt(fila[11], nFila, 11);
            this.tabladetalle.setValueAt(fila[12], nFila, 12);
            this.tabladetalle.setValueAt(fila[13], nFila, 13);
            this.tabladetalle.setValueAt(fila[14], nFila, 14);
            this.tabladetalle.setValueAt(fila[15], nFila, 15);
            this.tabladetalle.setValueAt(fila[16], nFila, 16);
            this.tabladetalle.setValueAt(fila[17], nFila, 17);
            this.tabladetalle.setValueAt(fila[18], nFila, 18);
            this.tabladetalle.setValueAt(fila[19], nFila, 19);
            this.tabladetalle.setValueAt(fila[20], nFila, 20);
            nFila = -1;
            this.SalirItem.doClick();
        }
        this.limpiaritems();
        this.sumatoria();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemActionPerformed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void sucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fecha.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void buscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
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
                nombresucursal.setText(sucu.getNombre());
            }
            banco.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

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
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalKeyPressed
    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrorubro(int nNumeroColumna) {
        trsfiltrorubro.setRowFilter(RowFilter.regexFilter(this.jTBuscarRubro.getText(), nNumeroColumna));
    }

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
        this.banco.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void rubroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rubroFocusGained
        rubro.selectAll();
    }//GEN-LAST:event_rubroFocusGained

    private void rubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubroActionPerformed
        this.BuscarRubro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_rubroActionPerformed

    private void rubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rubroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!rubro.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                BuscarRubro.doClick();
                this.moneda.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Rubro", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comprobante.requestFocus();
        }
    }//GEN-LAST:event_rubroKeyPressed

    private void BuscarRubroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarRubroMouseClicked
        jTBuscarRubro.requestFocus();
    }//GEN-LAST:event_BuscarRubroMouseClicked

    private void BuscarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarRubroActionPerformed
        rubro_compraDAO ruDAO = new rubro_compraDAO();
        rubro_compra ru = null;
        try {
            ru = ruDAO.buscarRubroBanco(Integer.valueOf(this.rubro.getText()));
            if (ru.getCodigo() == 0) {
                GrillaRubroCompra grillaru = new GrillaRubroCompra();
                Thread hiloru = new Thread(grillaru);
                hiloru.start();
                BRubro.setModal(true);
                BRubro.setSize(482, 575);
                BRubro.setLocationRelativeTo(null);
                BRubro.setVisible(true);
                BRubro.setTitle("Buscar Tipo de Inmueble");
                BRubro.setModal(false);
            } else {
                nombrerubro.setText(ru.getNombre());
                //Establecemos un título para el jDialog
                moneda.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_BuscarRubroActionPerformed

    private void BuscarRubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarRubroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarRubroKeyPressed

    private void nombrerubroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrerubroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrerubroKeyPressed

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

    }//GEN-LAST:event_tablarubroKeyPressed

    private void AceptarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarRubroActionPerformed
        int nFila = this.tablarubro.getSelectedRow();
        this.rubro.setText(this.tablarubro.getValueAt(nFila, 0).toString());
        this.nombrerubro.setText(this.tablarubro.getValueAt(nFila, 1).toString());

        this.BRubro.setVisible(false);
        this.jTBuscarRubro.setText("");
        moneda.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarRubroActionPerformed

    private void SalirRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirRubroActionPerformed
        this.BRubro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirRubroActionPerformed

    private void bancoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bancoFocusGained
        banco.selectAll();
    }//GEN-LAST:event_bancoFocusGained

    private void cotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!cotizacion.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                this.proveedor.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Cotización", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }
    }//GEN-LAST:event_cotizacionKeyPressed

    private void Calcular() {
        //Hacemos el Formateo de Numeros para los Valores Exentos
        if (this.exentas.getText().trim().length() > 0) {
            String cExentas = this.exentas.getText();
            cExentas = cExentas.replace(".", "").replace(",", ".");
            nExentas = Double.parseDouble(cExentas);
        }

        if (this.gravadas10.getText().trim().length() > 0) {
            String cGravadas10 = this.gravadas10.getText();
            cGravadas10 = cGravadas10.replace(".", "");
            cGravadas10 = cGravadas10.replace(",", ".");
            nGravadas10 = Double.parseDouble(cGravadas10);
        }

        if (this.gravadas5.getText().trim().length() > 0) {
            String cGravadas5 = this.gravadas5.getText();
            cGravadas5 = cGravadas5.replace(".", "");
            cGravadas5 = cGravadas5.replace(",", ".");
            nGravadas5 = Double.parseDouble(cGravadas5);
        }

        nTotal = nExentas + nGravadas10 + nGravadas5;
        String Numero = String.valueOf(nTotal);
        Numero = Numero.replace(",", ".");
        // BigDecimal bd1 = new BigDecimal(Numero);

        double Total = Double.parseDouble(Numero);
        this.Totalneto.setText(formatea.format(Total));
    }


    private void exentasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exentasKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (exentas.getText().matches("")) {
                exentas.setText("0");
            }
            this.gravadas10.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            proveedor.requestFocus();
        }
    }//GEN-LAST:event_exentasKeyPressed

    private void gravadas10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gravadas10KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (gravadas10.getText().matches("")) {
                gravadas10.setText("0");
            }
            this.iva10.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            exentas.requestFocus();
        }
    }//GEN-LAST:event_gravadas10KeyPressed

    private void iva10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iva10KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (iva10.getText().matches("")) {
                iva10.setText("0");
            }
            this.gravadas5.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            gravadas10.requestFocus();
        }
    }//GEN-LAST:event_iva10KeyPressed

    private void gravadas5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gravadas5KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (gravadas5.getText().matches("")) {
                gravadas5.setText("0");
            }
            this.iva5.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            iva10.requestFocus();
        }
    }//GEN-LAST:event_gravadas5KeyPressed

    private void iva5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iva5KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (iva5.getText().matches("")) {
                iva5.setText("0");
            }
            this.observacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            gravadas5.requestFocus();
        }
    }//GEN-LAST:event_iva5KeyPressed

    private void TotalnetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalnetoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalnetoActionPerformed

    private void proveedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_proveedorFocusGained
        proveedor.selectAll();
    }//GEN-LAST:event_proveedorFocusGained

    private void proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proveedorActionPerformed
        BuscarProveedor.doClick();
    }//GEN-LAST:event_proveedorActionPerformed

    private void proveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proveedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!proveedor.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                this.exentas.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Proveedor", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            vencimientotimbrado.requestFocus();
        }
    }//GEN-LAST:event_proveedorKeyPressed

    private void timbradoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_timbradoFocusGained

    }//GEN-LAST:event_timbradoFocusGained

    private void timbradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_timbradoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!timbrado.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                this.vencimientotimbrado.getDateEditor().getUiComponent().requestFocusInWindow();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Timbrado", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cotizacion.requestFocus();
        }
    }//GEN-LAST:event_timbradoKeyPressed

    private void BuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarProveedorActionPerformed

        proveedorDAO proDAO = new proveedorDAO();
        proveedor pro = null;
        try {
            pro = proDAO.buscarId(Integer.valueOf(this.proveedor.getText()));
            if (pro.getCodigo() == 0) {
                GrillaProveedor grillapro = new GrillaProveedor();
                Thread hilopro = new Thread(grillapro);
                hilopro.start();
                BProveedor.setModal(true);
                BProveedor.setSize(500, 585);
                BProveedor.setLocationRelativeTo(null);
                BProveedor.setVisible(true);
                BProveedor.setTitle("Proveedores");
                BProveedor.setModal(false);
            } else {
                nombreproveedor.setText(pro.getNombre());
                timbrado.setText(pro.getTimbrado());
                vencimientotimbrado.setDate(pro.getVencimiento());
                //Establecemos un título para el jDialog
            }
            exentas.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());

        }

    }//GEN-LAST:event_BuscarProveedorActionPerformed



    private void comprobanteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comprobanteFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteFocusGained

    private void comprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteActionPerformed

    private void comprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comprobanteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!comprobante.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                BuscarComprobante.doClick();
                if (rubro.isEnabled()) {
                    this.rubro.requestFocus();
                } else {
                    moneda.requestFocus();
                }
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese el Comprobante", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            rubro.requestFocus();
        }
    }//GEN-LAST:event_comprobanteKeyPressed

    private void BuscarComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarComprobanteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarComprobanteMouseClicked

    private void BuscarComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarComprobanteActionPerformed
        comprobanteDAO compDAO = new comprobanteDAO();
        comprobante compr = null;
        try {
            compr = compDAO.buscarId(Integer.valueOf(this.comprobante.getText()));
            //System.out.println("codigo: "+compr.getCodigo());
            if (compr.getCodigo() == 0) {
                GrillaComprobante grillacomp = new GrillaComprobante();
                Thread hilocompr = new Thread(grillacomp);
                hilocompr.start();
                BComprobantes.setModal(true);
                BComprobantes.setSize(500, 575);
                BComprobantes.setLocationRelativeTo(null);
                BComprobantes.setTitle("Buscar Comprobantes");
                BComprobantes.setVisible(true);
                BComprobantes.setModal(false);
            } else {
                nombrecomprobante.setText(compr.getNombre());
            }
            rubro.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
    }//GEN-LAST:event_BuscarComprobanteActionPerformed

    private void BuscarComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarComprobanteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarComprobanteKeyPressed

    private void nombrecomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombrecomprobanteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombrecomprobanteKeyPressed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!moneda.getText().matches("")) { //if es negacion si no esta vacio este cuadro de texto pasa al siguiente condicion mantches este es el cuadro de texto en la que estamos
                BuscarMoneda.doClick();
                this.cotizacion.requestFocus();
            } else {
                JOptionPane.showConfirmDialog(null, "Por Favor Ingrese la Moneda", "ATENCION", JOptionPane.CLOSED_OPTION);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (rubro.isEnabled()) {
                this.rubro.requestFocus();
            } else {
                comprobante.requestFocus();
            }
        }
    }//GEN-LAST:event_monedaKeyPressed

    private void BuscarMonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarMonedaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaMouseClicked

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        monedaDAO casDAO = new monedaDAO();
        moneda mn = null;
        try {
            mn = casDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mn.getCodigo() == 0) {
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mn.getNombre());
                this.cotizacion.setText(formatea.format(mn.getVenta()));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        moneda.requestFocus();
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private class GrillaMoneda extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }
            monedaDAO DAOMONEDA = new monedaDAO();
            try {
                for (moneda ca : DAOMONEDA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre(), formatea.format(ca.getVenta())};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private void BuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarMonedaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaKeyPressed

    private void nombremonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombremonedaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombremonedaKeyPressed

    private void comboComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboComprobantesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboComprobantesActionPerformed

    private void jTBuscarComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarComprobantesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobantesActionPerformed

    private void jTBuscarComprobantesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarComprobantesKeyPressed
        this.jTBuscarComprobantes.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarComprobantes.getText()).toUpperCase();
                jTBuscarComprobantes.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboComprobantes.getSelectedIndex()) {
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
                filtrocompr(indiceColumnaTabla);
            }
        });
        trsfiltrocompr = new TableRowSorter(tablacomprobante.getModel());
        tablacomprobante.setRowSorter(trsfiltrocompr);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobantesKeyPressed

    public void filtrocompr(int nNumeroColumna) {
        trsfiltrocompr.setRowFilter(RowFilter.regexFilter(this.jTBuscarComprobantes.getText(), nNumeroColumna));
    }

    private void tablacomprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacomprobanteMouseClicked
        this.AceptarComprobantes.doClick();
    }//GEN-LAST:event_tablacomprobanteMouseClicked

    private void tablacomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacomprobanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarComprobantes.doClick();
        }
    }//GEN-LAST:event_tablacomprobanteKeyPressed

    private void AceptarComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarComprobantesActionPerformed
        int nFila = this.tablacomprobante.getSelectedRow();
        this.comprobante.setText(this.tablacomprobante.getValueAt(nFila, 0).toString());
        this.nombrecomprobante.setText(this.tablacomprobante.getValueAt(nFila, 1).toString());

        this.BComprobantes.setVisible(false);
        this.jTBuscarComprobantes.setText("");
        this.rubro.requestFocus();
    }//GEN-LAST:event_AceptarComprobantesActionPerformed

    private void SalirComprobantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirComprobantesActionPerformed
        this.BComprobantes.setVisible(false);
    }//GEN-LAST:event_SalirComprobantesActionPerformed

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
        this.cotizacion.setText(this.tablamoneda.getValueAt(nFila, 2).toString());
        this.BMoneda.setVisible(false);
        this.jTBuscarMoneda.setText("");
        this.cotizacion.requestFocus();
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void comboproveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproveedorActionPerformed

    private void jTBuscarProveedorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscarProveedorFocusGained
        proveedor.selectAll();
    }//GEN-LAST:event_jTBuscarProveedorFocusGained

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
                        indiceColumnaTabla = 2;
                        break;//por RUC
                }
                repaint();
                filtroproveedor(indiceColumnaTabla);
            }
        });
        trsfiltroproveedor = new TableRowSorter(tablaproveedor.getModel());
        tablaproveedor.setRowSorter(trsfiltroproveedor);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarProveedorKeyPressed

    private void tablaproveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproveedorMouseClicked
        this.AceptarProveedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorMouseClicked

    private void tablaproveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarProveedor.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproveedorKeyPressed

    private void AceptarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProveedorActionPerformed
        int nFila = this.tablaproveedor.getSelectedRow();
        this.proveedor.setText(this.tablaproveedor.getValueAt(nFila, 0).toString());
        this.nombreproveedor.setText(this.tablaproveedor.getValueAt(nFila, 1).toString());
        this.timbrado.setText(this.tablaproveedor.getValueAt(nFila, 3).toString());
        try {
            vencimientotimbrado.setDate(formatoFecha.parse(this.tablaproveedor.getValueAt(nFila, 4).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        timbrado.requestFocus();
        this.BProveedor.setVisible(false);
        this.jTBuscarProveedor.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProveedorActionPerformed

    private void SalirProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProveedorActionPerformed
        this.BProveedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProveedorActionPerformed

    private void ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nFila = jTable1.getSelectedRow();

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("nNumero", Double.valueOf(jTable1.getValueAt(nFila, 0).toString()));

            JasperReport jr = null;
            //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
            URL url = getClass().getClassLoader().getResource("Reports/reposicion_fondo_fijo.jasper");
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
        // TODO add your handling code here:
    }//GEN-LAST:event_ListarActionPerformed

    private void exentasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_exentasFocusLost
        if (this.exentas.getText().trim().length() > 0) {
            this.Calcular();
            String cExentas = this.exentas.getText();
            cExentas = cExentas.replace(",", ".");
            double nExentas = Double.parseDouble(cExentas);
        }
    }//GEN-LAST:event_exentasFocusLost

    private void gravadas10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas10FocusLost
        if (this.gravadas10.getText().trim().length() > 0) {
            this.iva10.setText("");
            this.Calcular();
            String cGravadas10 = this.gravadas10.getText();

            cGravadas10 = cGravadas10.replace(",", ".");
            cGravadas10 = cGravadas10.replace(".", "");

            double nGravadas10 = Double.parseDouble(cGravadas10);

            if (Double.valueOf(moneda.getText()) == 1) {
                nIva10 = Math.round(nGravadas10 / 11);
            } else {
                nIva10 = nGravadas10 / 11;
            }
            this.iva10.setText(formatea.format(nIva10));
        }
    }//GEN-LAST:event_gravadas10FocusLost

    private void gravadas5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas5FocusLost
        if (this.gravadas5.getText().trim().length() > 0) {
            this.iva5.setText("");
            this.Calcular();
            String cGravadas5 = this.gravadas5.getText();

            cGravadas5 = cGravadas5.replace(",", ".");
            cGravadas5 = cGravadas5.replace(".", "");

            double nGravadas5 = Double.parseDouble(cGravadas5);

            if (Double.valueOf(moneda.getText()) == 1) {
                nIva5 = Math.round(nGravadas5 / 21);
            } else {
                nIva5 = nGravadas5 / 21;
            }
            this.iva5.setText(formatea.format(nIva5));
        }
    }//GEN-LAST:event_gravadas5FocusLost

    private void exentasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_exentasFocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_exentasFocusGained

    private void gravadas10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas10FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_gravadas10FocusGained

    private void gravadas5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gravadas5FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_gravadas5FocusGained

    private void iva10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_iva10FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_iva10FocusGained

    private void iva5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_iva5FocusGained
        String m = moneda.getText().replace(" ", "");
        if (m.equals("") || m.equals("0")) {
            moneda.requestFocus();
            JOptionPane.showMessageDialog(this.itemgastos, "Primero Ingrese Moneda", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_iva5FocusGained

    private void nrochequeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nrochequeFocusGained
        nrocheque.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeFocusGained

    private void nrochequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nrochequeActionPerformed
        this.nuevoitem.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeActionPerformed

    private void nrofacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrofacturaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechafactura.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaKeyPressed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.jTBuscarBanco.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("N°");
        modelo.addColumn("Fecha");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Banco");
        modelo.addColumn("Monto");
        modelo.addColumn("Cheque N°");
        modelo.addColumn("N° Asiento");

        int[] anchos = {120, 120, 150, 150, 200, 120, 120};
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
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
    }

    private void TitBanco() {
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

    private void TituloDetalle() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        modelodetalle.addColumn("N° Factura");		//00	nrofactura
        modelodetalle.addColumn("Fecha");		//01	fechafactura
        modelodetalle.addColumn("Vencimiento");		//02	vencimiento
        modelodetalle.addColumn("Tipo");	//03	comprobante
        modelodetalle.addColumn("Comprobante");		//04	nombrecomprobante
        modelodetalle.addColumn("Rubro");		//05	rubro
        modelodetalle.addColumn("Descripción Rubro");   //06	nombrerubro
        modelodetalle.addColumn("Tipo Moneda");		//07	moneda
        modelodetalle.addColumn("Descripción Moneda");	//08 	nombremoneda
        modelodetalle.addColumn("Cotización");		//09 	cotizacion
        modelodetalle.addColumn("Timbrado");		//10	timbrado
        modelodetalle.addColumn("Ven. Timb.");		//11	vencimientotimbrado
        modelodetalle.addColumn("Cta. Proveedor");	//12	proveedor
        modelodetalle.addColumn("Descripción Proveedor");//13	nombreproveedor
        modelodetalle.addColumn("Exentas");		//14	exentas
        modelodetalle.addColumn("Gravadas 10%");        //15	gravadas10
        modelodetalle.addColumn("Iva 10%");		//16	iva10
        modelodetalle.addColumn("Gravadas 5%");		//17	gravadas5
        modelodetalle.addColumn("Iva 5%");		//18	iva5
        modelodetalle.addColumn("Neto");		//19	Totalneto
        modelodetalle.addColumn("Observacion");		//20	observacion

        tabladetalle.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(1).setCellRenderer(AlinearCentro);
        tabladetalle.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        tabladetalle.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(11).setCellRenderer(AlinearCentro);
        tabladetalle.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(16).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(17).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(19).setCellRenderer(TablaRenderer);

        int[] anchos = {
            100,//01	nrofactura          0
            100,//01	fechafactura        1
            100,//00	vencimiento         2
            90,//00	comprobante         3
            100,//00	nombrecomprobante   4  
            90,//00	rubro               5
            100,//01	nombrerubro         6
            90,//00	moneda              7
            100,//00 	nombremoneda        8
            90,//00 	cotizacion          9
            100,//01	timbrado            10
            100,//00	vencimientotimbrado 11
            90,//00	proveedor           12
            100,//01	nombreproveedor     13
            100,//01	exentas             14
            100,//00	gravadas10          15
            100,//01	iva10               16
            100,//00	gravadas5           17
            100,//01	iva5                18
            100,//01	Totalneto           19
            100//00	observacion         20
        };
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            tabladetalle.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalle.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladetalle.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 10);
        tabladetalle.setFont(font);
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
                new rendicion_de_fondos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarBanco;
    private javax.swing.JButton AceptarComprobantes;
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton AceptarProveedor;
    private javax.swing.JButton AceptarRubro;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BComprobantes;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BProveedor;
    private javax.swing.JDialog BRubro;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JDialog Bbanco;
    private javax.swing.JButton BuscarComprobante;
    private javax.swing.JButton BuscarMoneda;
    private javax.swing.JButton BuscarProveedor;
    private javax.swing.JButton BuscarRubro;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton GrabarItem;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirBanco;
    private javax.swing.JButton SalirComprobantes;
    private javax.swing.JButton SalirItem;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirProveedor;
    private javax.swing.JButton SalirRubro;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JFormattedTextField Totalneto;
    private org.edisoncor.gui.label.LabelMetric Unidades;
    private javax.swing.JTextField banco;
    private javax.swing.JLabel bancoeti;
    private javax.swing.JButton botonsalir;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JButton buscarbanco;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JComboBox comboComprobantes;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox comboproveedor;
    private javax.swing.JComboBox comborubro;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JTextField comprobante;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JTextField creferencia;
    private com.toedter.calendar.JDateChooser dFinal;
    private com.toedter.calendar.JDateChooser dInicial;
    private javax.swing.JButton delitem;
    private javax.swing.JDialog detalle_gastos;
    private javax.swing.JButton editaritem;
    private javax.swing.JFormattedTextField exentas;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechafactura;
    private javax.swing.JLabel fondo;
    private javax.swing.JFormattedTextField gravadas10;
    private javax.swing.JFormattedTextField gravadas5;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JDialog itemgastos;
    private javax.swing.JFormattedTextField iva10;
    private javax.swing.JFormattedTextField iva5;
    private javax.swing.JComboBox jComboBoxBusqueda;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarBanco;
    private javax.swing.JTextField jTBuscarComprobantes;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarProveedor;
    private javax.swing.JTextField jTBuscarRubro;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField nombrebanco;
    private javax.swing.JTextField nombrecomprobante;
    private javax.swing.JLabel nombreinmuebleeti;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombreproveedor;
    private javax.swing.JTextField nombrerubro;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JFormattedTextField nrocheque;
    private javax.swing.JTextField nrofactura;
    private javax.swing.JButton nuevoitem;
    private javax.swing.JTextField numero;
    private javax.swing.JTextArea observacion;
    private javax.swing.JTextArea observaciones;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel2;
    private javax.swing.JTextField proveedor;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField rubro;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablabanco;
    private javax.swing.JTable tablacomprobante;
    private javax.swing.JTable tabladetalle;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablaproveedor;
    private javax.swing.JTable tablarubro;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTextField timbrado;
    private com.toedter.calendar.JDateChooser vencimiento;
    private com.toedter.calendar.JDateChooser vencimientotimbrado;
    // End of variables declaration//GEN-END:variables

    private class GrillaGastos extends Thread {

        public void run() {
            Date dFechaInicio = ODate.de_java_a_sql(dInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(dFinal.getDate());

            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            rendicion_gastosDAO guDAO = new rendicion_gastosDAO();
            try {
                for (rendicion_gastos gu : guDAO.MostrarxFecha(dFechaInicio, dFechaFinal)) {
                    String Datos[] = {formatosinpunto.format(gu.getNumero()), formatoFecha.format(gu.getFecha()), gu.getSucursal().getNombre(), gu.getBanco().getNombre(), formatea.format(gu.getImporte()), gu.getNrocheque(), formatosinpunto.format(gu.getAsiento())};
                    modelo.addRow(Datos);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
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

    private class GrillaBanco extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            bancosDAO baDAO = new bancosDAO();
            try {
                for (banco ba : baDAO.todos()) {
                    String Datos[] = {String.valueOf(ba.getCodigo()), ba.getNombre()};
                    modelobanco.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablabanco.setRowSorter(new TableRowSorter(modelobanco));
            int cantFilas = tablabanco.getRowCount();
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

    private class GrillaComprobante extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocomprobante.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocomprobante.removeRow(0);
            }

            comprobanteDAO DAOCOMP = new comprobanteDAO();
            try {
                for (comprobante compr : DAOCOMP.todosxtipo(1)) {
                    String Datos[] = {String.valueOf(compr.getCodigo()), compr.getNombre()};
                    modelocomprobante.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablacomprobante.setModel(modelocomprobante);
            tablacomprobante.setRowSorter(new TableRowSorter(modelocomprobante));
            int cantFilas = tablacomprobante.getRowCount();
            System.out.println("agregado modelo: " + modelocomprobante.getRowCount());
            System.out.println("agregado tabla: " + tablacomprobante.getRowCount());
        }
    }

    private class GrillaRubroCompra extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelorubro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelorubro.removeRow(0);
            }
            rubro_compraDAO ruDAO = new rubro_compraDAO();
            try {
                for (Modelo.rubro_compra ru : ruDAO.todos()) {
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

    private class GrillaProveedor extends Thread {
        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproveedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproveedor.removeRow(0);
            }

            proveedorDAO DAOpro = new proveedorDAO();
            try {
                for (proveedor prov : DAOpro.todos()) {
                    String Datos[] = {String.valueOf(prov.getCodigo()), prov.getNombre(), prov.getRuc(), prov.getTimbrado(), formatoFecha.format(prov.getVencimiento())};
                    modeloproveedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproveedor.setRowSorter(new TableRowSorter(modeloproveedor));
            int cantFilas = tablaproveedor.getRowCount();
        }
    }

}

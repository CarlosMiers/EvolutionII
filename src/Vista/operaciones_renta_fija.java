/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.UUID;
import Clases.cambiarFecha;
import Clases.numero_a_letras;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.bancoplazaDAO;
import DAO.bancosDAO;
import DAO.cartera_clientesDAO;
import DAO.clienteDAO;
import DAO.comisionDAO;
import DAO.configuracionDAO;
import DAO.ctacteclienteDAO;
import DAO.cuponesDAO;
import DAO.desgloseDAO;
import DAO.detalle_cupones_titulosDAO;
import DAO.emisorDAO;
import DAO.monedaDAO;
import DAO.precierre_operacionDAO;
import DAO.tituloDAO;
import DAO.vendedorDAO;
import Modelo.Tablas;
import Modelo.banco;
import Modelo.bancoplaza;
import Modelo.cartera_clientes;
import Modelo.cliente;
import Modelo.comision;
import Modelo.configuracion;
import Modelo.ctactecliente;
import Modelo.cupones;
import Modelo.desglose;
import Modelo.detalle_cupones_titulos;
import Modelo.emisor;
import Modelo.moneda;
import Modelo.precierre_operacion;
import Modelo.titulo;
import Modelo.vendedor;
import com.toedter.calendar.JDateChooser;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class operaciones_renta_fija extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelobanco = new Tablas();
    Tablas modelodisponibilidad = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelovendedor = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelotitulo = new Tablas();
    Tablas modelocupon = new Tablas();
    Tablas modelodesglose = new Tablas();
    Tablas modelocomision = new Tablas();

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrotitulo, trsfiltrobanco,
            trsfiltrocliente, trsfiltromoneda,
            trsfiltrovendedor;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatodecimal = new DecimalFormat("###,###.####");
    DecimalFormat formatosinpunto = new DecimalFormat("###");
    String cSql = null;
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    String referencia = null;
    JDateChooser dateChooser = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
    Date dEmision;
    Date dVence;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconocertificado = new ImageIcon("src/Images/programas.png");

    int counter = 0;
    int nBuscarCliente = 0;
    int nBuscarAsesor = 0;

    public operaciones_renta_fija() {
        initComponents();
        fecha.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    fecha.requestFocus();
                }
            }
        });

        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Eliminar.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.ListarCertificado.setIcon(iconocertificado);
        this.Salir.setIcon(iconosalir);
        this.SalirCompleto.setIcon(iconosalir);
        this.buscartitulo.setIcon(iconobuscar);
        this.buscarcuenta.setIcon(iconobuscar);
        this.Grabar.setIcon(iconograbar);
        this.refrescar.setIcon(icorefresh);
        this.buscarmoneda.setIcon(iconobuscar);
        this.buscarclientecomprador.setIcon(iconobuscar);
        this.buscarclientevendedor.setIcon(iconobuscar);
        this.buscarasesorvendedor.setIcon(iconobuscar);
        this.buscarasesorcomprador.setIcon(iconobuscar);
        this.creferencia.setVisible(false);
        this.banco.setVisible(false);
        this.emisor.setVisible(false);
        this.cupones.setVisible(true);
        this.base.setVisible(false);
        this.tablaprincipal.setShowGrid(false);
        this.tablaprincipal.setOpaque(true);
        this.tablaprincipal.setBackground(new Color(204, 204, 255));
        this.tablaprincipal.setForeground(Color.BLACK);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idControl.setVisible(false);
        this.idControl.setText("0");
        vencecupon.setVisible(false);
        this.iditem.setVisible(false);
        this.cargarTitulo();
        this.Inicializar();
        this.Titbancos();
        this.TitTitulo();
        this.TituloComision();
        this.TitClie();
        this.TitMoneda();
        this.TitVendedor();
        this.TituloCupon();
        this.TituloDesglose();

        GrillaRentaFija GrillaAus = new GrillaRentaFija();
        Thread HiloGrilla = new Thread(GrillaAus);
        HiloGrilla.start();

        GrillaCliente grillacl = new GrillaCliente();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();

        GrillaMoneda grillaca = new GrillaMoneda();
        Thread hiloca = new Thread(grillaca);
        hiloca.start();

        GrillaTitulos grillati = new GrillaTitulos();
        Thread hiloti = new Thread(grillati);
        hiloti.start();

        GrillaVendedor grillave = new GrillaVendedor();
        Thread hilove = new Thread(grillave);
        hilove.start();

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

        detalle_operacion = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        Grabar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        tipooperacion = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        negociado = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        mercado = new javax.swing.JComboBox<>();
        Socio1 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        buscarmoneda = new javax.swing.JButton();
        titulo = new javax.swing.JTextField();
        buscartitulo = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        valor_nominal = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        cantidad = new javax.swing.JFormattedTextField();
        precio = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        tasa = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        fechaemision = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        plazo = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tipoplazo = new javax.swing.JComboBox<>();
        periodopago = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        emisor = new javax.swing.JTextField();
        nombretitulo = new javax.swing.JLabel();
        nombremoneda = new javax.swing.JLabel();
        creferencia = new javax.swing.JTextField();
        base = new javax.swing.JTextField();
        nombreemisor = new javax.swing.JTextField();
        cupones = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        banco = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        clientecomprador = new javax.swing.JTextField();
        buscarclientecomprador = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        clientevendedor = new javax.swing.JTextField();
        buscarclientevendedor = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        asesorcomprador = new javax.swing.JTextField();
        buscarasesorcomprador = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        asesorvendedor = new javax.swing.JTextField();
        buscarasesorvendedor = new javax.swing.JButton();
        nombreclientecomprador = new javax.swing.JTextField();
        nombreasesorvendedor = new javax.swing.JTextField();
        nombreasesorcomprador = new javax.swing.JTextField();
        nombreclientevendedor = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        nombrebanco = new javax.swing.JTextField();
        cuentapago = new javax.swing.JTextField();
        buscarcuenta = new javax.swing.JButton();
        arancelcompra = new javax.swing.JCheckBox();
        arancelventa = new javax.swing.JCheckBox();
        comcomprador = new javax.swing.JFormattedTextField();
        comvendedor = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablacupon = new javax.swing.JTable();
        GenerarCupones = new javax.swing.JButton();
        vencecupon = new com.toedter.calendar.JDateChooser();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabladesglose = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        cantidadTitulo = new javax.swing.JFormattedTextField();
        importeTitulo = new javax.swing.JFormattedTextField();
        serie = new javax.swing.JTextField();
        nrotitulo = new javax.swing.JTextField();
        desde_acci = new javax.swing.JFormattedTextField();
        hasta_acci = new javax.swing.JFormattedTextField();
        GrabarDesglose = new javax.swing.JButton();
        BorrarDesglose = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        iditem = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablacomision = new javax.swing.JTable();
        GenerarComisiones = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        numero = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        numerobolsa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fechacierre = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        BBancos = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        txtbuscarbanco = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablabancos = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarBanco = new javax.swing.JButton();
        SalirBanco = new javax.swing.JButton();
        BCliente = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        BTitulos = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        combotitulo = new javax.swing.JComboBox();
        txtbuscartitulo = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablatitulo = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        AceptarCasa = new javax.swing.JButton();
        SalirCasa = new javax.swing.JButton();
        BVendedor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combovendedor = new javax.swing.JComboBox();
        jTBuscarVendedor = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablavendedor = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        compensacion = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        numprecierre = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        codcomprador = new javax.swing.JTextField();
        nomcomprador = new javax.swing.JTextField();
        codbanco = new javax.swing.JTextField();
        nombanco = new javax.swing.JTextField();
        buscarbco = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        GrabarCompensacion = new javax.swing.JButton();
        Salircompensacion = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetavacaciones = new org.edisoncor.gui.label.LabelMetric();
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
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        ListarCertificado = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaprincipal = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        detalle_operacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_operacionFocusGained(evt);
            }
        });
        detalle_operacion.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_operacionWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_operacion.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_operacionWindowActivated(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Grabar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Grabar.setText("Grabar");
        Grabar.setToolTipText("Guardar los Cambios");
        Grabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarActionPerformed(evt);
            }
        });
        Grabar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GrabarKeyPressed(evt);
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Grabar)
                    .addComponent(Salir))
                .addContainerGap())
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tipooperacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Compra", "Venta", "Compra-Venta" }));
        tipooperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipooperacionActionPerformed(evt);
            }
        });
        tipooperacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipooperacionKeyPressed(evt);
            }
        });

        jLabel10.setText("Tipo");

        jLabel11.setText("Negociado");

        negociado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BVPASA", "Mesa de Dinero", "Fondos", "Transferencias" }));
        negociado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        negociado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                negociadoKeyPressed(evt);
            }
        });

        jLabel13.setText("Mercado");

        mercado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Primario", "Secundario" }));
        mercado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mercado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mercadoKeyPressed(evt);
            }
        });

        Socio1.setText("Moneda");

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
        moneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                monedaKeyPressed(evt);
            }
        });

        buscarmoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarmoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarmonedaActionPerformed(evt);
            }
        });

        titulo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        titulo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tituloFocusGained(evt);
            }
        });
        titulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tituloActionPerformed(evt);
            }
        });
        titulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tituloKeyPressed(evt);
            }
        });

        buscartitulo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscartitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscartituloActionPerformed(evt);
            }
        });

        jLabel14.setText("Título");

        jLabel12.setText("Valor Nominal");

        valor_nominal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        valor_nominal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valor_nominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                valor_nominalKeyPressed(evt);
            }
        });

        jLabel15.setText("Cantidad");

        cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadKeyPressed(evt);
            }
        });

        precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                precioKeyPressed(evt);
            }
        });

        jLabel16.setText("Precio");

        tasa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        tasa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tasaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tasaKeyReleased(evt);
            }
        });

        jLabel17.setText("Tasa");

        fechaemision.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaemisionFocusGained(evt);
            }
        });
        fechaemision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaemisionKeyPressed(evt);
            }
        });

        jLabel9.setText("Emisión");

        vencimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                vencimientoFocusGained(evt);
            }
        });
        vencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vencimientoKeyPressed(evt);
            }
        });

        jLabel18.setText("Vence");

        plazo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        plazo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        plazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                plazoFocusGained(evt);
            }
        });
        plazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plazoKeyPressed(evt);
            }
        });

        jLabel19.setText("Plazo");

        jLabel20.setText("Plazo en");

        tipoplazo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Días", "Meses", "Años" }));
        tipoplazo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tipoplazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipoplazoKeyPressed(evt);
            }
        });

        periodopago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MENSUAL", "BIMESTRAL", "TRIMESTRAL", "CUATRIMESTRAL", "SEMESTRAL", "ANUAL", "VENCIMIENTO" }));
        periodopago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        periodopago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                periodopagoKeyPressed(evt);
            }
        });

        jLabel21.setText("Pago Int.");

        emisor.setEnabled(false);

        nombretitulo.setText("jLabel4");

        nombremoneda.setText("jLabel26");

        creferencia.setEnabled(false);

        nombreemisor.setEditable(false);
        nombreemisor.setDisabledTextColor(new java.awt.Color(0, 0, 255));

        cupones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cupones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cuponesKeyPressed(evt);
            }
        });

        jLabel30.setText("Cupones");

        banco.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(nombreemisor, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Socio1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cupones, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tasa, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(precio, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cantidad)
                            .addComponent(tipoplazo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(valor_nominal, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(moneda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mercado, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(negociado, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tipooperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel21))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(periodopago, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)
                                        .addComponent(vencimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fechaemision, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(buscarmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscartitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombretitulo)
                                    .addComponent(nombremoneda)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(base, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(creferencia, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                        .addComponent(emisor, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)))))
                        .addGap(111, 111, 111))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tipooperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(negociado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mercado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Socio1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(emisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel13)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buscartitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombretitulo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nombremoneda)
                            .addComponent(buscarmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valor_nominal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tipoplazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fechaemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(periodopago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cupones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nombreemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        clientecomprador.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        clientecomprador.setText(" ");
        clientecomprador.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        clientecomprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientecompradorActionPerformed(evt);
            }
        });
        clientecomprador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clientecompradorKeyPressed(evt);
            }
        });

        buscarclientecomprador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarclientecomprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarclientecompradorActionPerformed(evt);
            }
        });

        jLabel6.setText("Cliente Comprador");

        jLabel23.setText("Cliente Vendedor");

        clientevendedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        clientevendedor.setText(" ");
        clientevendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientevendedorActionPerformed(evt);
            }
        });
        clientevendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clientevendedorKeyPressed(evt);
            }
        });

        buscarclientevendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarclientevendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarclientevendedorActionPerformed(evt);
            }
        });

        jLabel24.setText("Asesor Comprador");

        asesorcomprador.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        asesorcomprador.setText(" ");
        asesorcomprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asesorcompradorActionPerformed(evt);
            }
        });
        asesorcomprador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                asesorcompradorKeyPressed(evt);
            }
        });

        buscarasesorcomprador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarasesorcomprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarasesorcompradorActionPerformed(evt);
            }
        });

        jLabel25.setText("Asesor Vendedor");

        asesorvendedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        asesorvendedor.setText(" ");
        asesorvendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asesorvendedorActionPerformed(evt);
            }
        });
        asesorvendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                asesorvendedorKeyPressed(evt);
            }
        });

        buscarasesorvendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarasesorvendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarasesorvendedorActionPerformed(evt);
            }
        });

        nombreclientecomprador.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombreclientecomprador.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreclientecomprador.setEnabled(false);

        nombreasesorvendedor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombreasesorvendedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreasesorvendedor.setEnabled(false);

        nombreasesorcomprador.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombreasesorcomprador.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreasesorcomprador.setEnabled(false);
        nombreasesorcomprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreasesorcompradorActionPerformed(evt);
            }
        });

        nombreclientevendedor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombreclientevendedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreclientevendedor.setEnabled(false);

        jLabel31.setText("Cuenta Cte.");

        nombrebanco.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrebanco.setEnabled(false);

        cuentapago.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cuentapago.setEnabled(false);

        buscarcuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarcuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcuentaActionPerformed(evt);
            }
        });

        arancelcompra.setText("Arancel P.C.");

        arancelventa.setText("Arancel P.V.");

        comcomprador.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.0000"))));
        comcomprador.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        comvendedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        comvendedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel32.setText("% Com.  CM.");

        jLabel33.setText("% Com. PV.");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrebanco, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nombreclientecomprador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addComponent(nombreclientevendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addComponent(nombreasesorvendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addComponent(nombreasesorcomprador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(clientecomprador, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buscarclientecomprador, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addGap(18, 18, 18)
                            .addComponent(clientevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buscarclientevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel25)
                            .addGap(18, 18, 18)
                            .addComponent(asesorvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(buscarasesorvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(asesorcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buscarasesorcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cuentapago, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel32)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comcomprador)
                            .addComponent(arancelcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(arancelventa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(comvendedor)))))
                .addContainerGap())
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {nombreasesorcomprador, nombreasesorvendedor, nombreclientecomprador, nombreclientevendedor});

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buscarclientecomprador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(clientecomprador)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreclientecomprador, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buscarasesorcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(asesorcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreasesorcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(buscarclientevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clientevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreclientevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel25)
                        .addComponent(asesorvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarasesorvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreasesorvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarcuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel31)
                        .addComponent(cuentapago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arancelcompra)
                    .addComponent(arancelventa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addGap(5, 5, 5)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Operación", jPanel10);

        tablacupon.setModel(modelocupon);
        tablacupon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablacuponKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablacupon);

        GenerarCupones.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        GenerarCupones.setText("Generar Cupones");
        GenerarCupones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GenerarCupones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarCuponesActionPerformed(evt);
            }
        });

        vencecupon.setFocusCycleRoot(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vencecupon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(GenerarCupones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)))
                .addGap(51, 51, 51))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(vencecupon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(GenerarCupones)
                .addGap(21, 21, 21))
        );

        jTabbedPane1.addTab("Cupones", jPanel5);

        tabladesglose.setModel(modelodesglose);
        tabladesglose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabladesgloseMousePressed(evt);
            }
        });
        tabladesglose.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabladesgloseKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tabladesglose);

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cantidadTitulo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        importeTitulo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importeTitulo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        desde_acci.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        desde_acci.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        hasta_acci.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        hasta_acci.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        GrabarDesglose.setText("Grabar");
        GrabarDesglose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarDesglose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarDesgloseActionPerformed(evt);
            }
        });

        BorrarDesglose.setText("Borrar");
        BorrarDesglose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarDesglose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarDesgloseActionPerformed(evt);
            }
        });

        jLabel4.setText("Cantidad");

        jLabel22.setText("Importe");

        jLabel26.setText("Serie");

        jLabel27.setText("N° Título");

        jLabel28.setText("Desde");

        jLabel29.setText("Hasta");

        iditem.setEnabled(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cantidadTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importeTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel22)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(serie, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26)
                        .addGap(59, 59, 59)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nrotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel27)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(desde_acci, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hasta_acci, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel28)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel29)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GrabarDesglose, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(BorrarDesglose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(GrabarDesglose)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(serie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nrotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(desde_acci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hasta_acci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BorrarDesglose)
                            .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cantidadTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(importeTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Limpiar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(6, 6, 6))
        );

        jTabbedPane1.addTab("Desglose", jPanel6);

        tablacomision.setModel(modelocomision);
        jScrollPane6.setViewportView(tablacomision);

        GenerarComisiones.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        GenerarComisiones.setText("Generar Comisiones a Pagar");
        GenerarComisiones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GenerarComisiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarComisionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(395, Short.MAX_VALUE)
                .addComponent(GenerarComisiones, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(358, Short.MAX_VALUE)
                .addComponent(GenerarComisiones)
                .addGap(21, 21, 21))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(56, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(53, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Comisiones a Pagar", jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        jLabel2.setText("Número");

        jLabel1.setText("Fecha Orden");

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

        numerobolsa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numerobolsa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numerobolsaFocusGained(evt);
            }
        });
        numerobolsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numerobolsaActionPerformed(evt);
            }
        });
        numerobolsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numerobolsaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numerobolsaKeyReleased(evt);
            }
        });

        jLabel5.setText("Número BVPASA");

        fechacierre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechacierreFocusGained(evt);
            }
        });
        fechacierre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechacierreKeyPressed(evt);
            }
        });

        jLabel7.setText("Fecha Cierre");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numerobolsa, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechacierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fechacierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(numerobolsa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(3, 3, 3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detalle_operacionLayout = new javax.swing.GroupLayout(detalle_operacion.getContentPane());
        detalle_operacion.getContentPane().setLayout(detalle_operacionLayout);
        detalle_operacionLayout.setHorizontalGroup(
            detalle_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_operacionLayout.createSequentialGroup()
                .addGroup(detalle_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        detalle_operacionLayout.setVerticalGroup(
            detalle_operacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_operacionLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        BBancos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BBancos.setTitle("null");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        txtbuscarbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscarbanco.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.jTBuscarClientes.text")); // NOI18N
        txtbuscarbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarbancoActionPerformed(evt);
            }
        });
        txtbuscarbanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscarbancoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtbuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablabancos.setModel(modelobanco     );
        tablabancos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablabancosMouseClicked(evt);
            }
        });
        tablabancos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablabancosKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablabancos);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarBanco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarBanco.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarBancoActionPerformed(evt);
            }
        });

        SalirBanco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirBanco.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.SalirCliente.text")); // NOI18N
        SalirBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirBancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarBanco)
                    .addComponent(SalirBanco))
                .addContainerGap())
        );

        javax.swing.GroupLayout BBancosLayout = new javax.swing.GroupLayout(BBancos.getContentPane());
        BBancos.getContentPane().setLayout(BBancosLayout);
        BBancosLayout.setHorizontalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBancosLayout.setVerticalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
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
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.SalirCliente.text")); // NOI18N
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
        jTBuscarMoneda.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarMoneda.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.SalirCliente.text")); // NOI18N
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

        BTitulos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BTitulos.setTitle("null");

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combotitulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combotitulo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combotitulo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combotitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combotituloActionPerformed(evt);
            }
        });

        txtbuscartitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscartitulo.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.jTBuscarClientes.text")); // NOI18N
        txtbuscartitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscartituloActionPerformed(evt);
            }
        });
        txtbuscartitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscartituloKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(combotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtbuscartitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscartitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablatitulo.setModel(modelotitulo        );
        tablatitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablatituloMouseClicked(evt);
            }
        });
        tablatitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablatituloKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tablatitulo);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa)
                    .addComponent(SalirCasa))
                .addContainerGap())
        );

        javax.swing.GroupLayout BTitulosLayout = new javax.swing.GroupLayout(BTitulos.getContentPane());
        BTitulos.getContentPane().setLayout(BTitulosLayout);
        BTitulosLayout.setHorizontalGroup(
            BTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BTitulosLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BTitulosLayout.setVerticalGroup(
            BTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BTitulosLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BVendedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BVendedor.setTitle("null");

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combovendedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combovendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combovendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combovendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combovendedorActionPerformed(evt);
            }
        });

        jTBuscarVendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarVendedor.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarVendedorActionPerformed(evt);
            }
        });
        jTBuscarVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarVendedorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(combovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablavendedor.setModel(modelovendedor);
        tablavendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablavendedorMouseClicked(evt);
            }
        });
        tablavendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablavendedorKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablavendedor);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "ventas.SalirCliente.text")); // NOI18N
        SalirVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarVendedor)
                    .addComponent(SalirVendedor))
                .addContainerGap())
        );

        javax.swing.GroupLayout BVendedorLayout = new javax.swing.GroupLayout(BVendedor.getContentPane());
        BVendedor.getContentPane().setLayout(BVendedorLayout);
        BVendedorLayout.setHorizontalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BVendedorLayout.setVerticalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Actualizar Cuenta Compensación");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel35.setText("Precierre");

        jLabel36.setText("Cuenta");

        buscarbco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel37.setText("Nombre");

        jLabel38.setText("Banco");

        jLabel39.setText("Denominación");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(jLabel35))
                .addGap(26, 26, 26)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(codbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarbco, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nomcomprador)
                    .addComponent(nombanco, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(numprecierre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                        .addComponent(codcomprador, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(numprecierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(codcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomcomprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(codbanco)
                        .addComponent(jLabel38))
                    .addComponent(buscarbco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCompensacion.setText("Grabar");

        Salircompensacion.setText("Salir");
        Salircompensacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalircompensacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(GrabarCompensacion, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(Salircompensacion, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCompensacion)
                    .addComponent(Salircompensacion))
                .addContainerGap())
        );

        javax.swing.GroupLayout compensacionLayout = new javax.swing.GroupLayout(compensacion.getContentPane());
        compensacion.getContentPane().setLayout(compensacionLayout);
        compensacionLayout.setHorizontalGroup(
            compensacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(compensacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(compensacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        compensacionLayout.setVerticalGroup(
            compensacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(compensacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Renta Fija");
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

        etiquetavacaciones.setBackground(new java.awt.Color(255, 255, 255));
        etiquetavacaciones.setText("Renta Fija");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N° Operación", "Emisor", "Título", "Moneda", "Comprador", "Vendedor", "Asesor Compra", "Asesor Venta" }));
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
                .addComponent(etiquetavacaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarcadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetavacaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "libroventaconsolidado.jPanel2.border.title"))); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "libroventaconsolidado.jLabel1.text")); // NOI18N

        dFechaInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dFechaInicialKeyPressed(evt);
            }
        });

        jLabel8.setText(org.openide.util.NbBundle.getMessage(operaciones_renta_fija.class, "libroventaconsolidado.jLabel2.text")); // NOI18N

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(refrescar)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        ListarCertificado.setBackground(new java.awt.Color(255, 255, 255));
        ListarCertificado.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ListarCertificado.setText("Certificado");
        ListarCertificado.setToolTipText("");
        ListarCertificado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ListarCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListarCertificadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ListarCertificado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(SalirCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Modificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Eliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27))
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
                .addComponent(ListarCertificado)
                .addGap(13, 13, 13)
                .addComponent(SalirCompleto)
                .addGap(34, 34, 34)
                .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablaprincipal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablaprincipal.setModel(modelo);
        tablaprincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaprincipal.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablaprincipal.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablaprincipal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablaprincipalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablaprincipalFocusLost(evt);
            }
        });
        tablaprincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaprincipalMouseClicked(evt);
            }
        });
        tablaprincipal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaprincipalPropertyChange(evt);
            }
        });
        tablaprincipal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaprincipalKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaprincipal);

        jMenu1.setText("Opciones");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Actualizar Cuenta Compensación");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Resumen de Operaciones");
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
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TituloDesglose() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelodesglose.addColumn("Cantidad");
        modelodesglose.addColumn("Importe");
        modelodesglose.addColumn("Serie");
        modelodesglose.addColumn("N° Título");
        modelodesglose.addColumn("Desde");
        modelodesglose.addColumn("Hasta");
        modelodesglose.addColumn("id");
        int[] anchos = {100, 100, 100, 100, 100, 100, 1};
        for (int i = 0; i < modelodesglose.getColumnCount(); i++) {
            tabladesglose.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladesglose.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladesglose.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tabladesglose.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        tabladesglose.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        //Se usa para poner invisible una determinada celda
        tabladesglose.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tabladesglose.setFont(font);

        this.tabladesglose.getColumnModel().getColumn(6).setMaxWidth(0);
        this.tabladesglose.getColumnModel().getColumn(6).setMinWidth(0);
        this.tabladesglose.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        this.tabladesglose.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
    }

    private void ImprimirDocumentos(String reporte, int punta, String ctipo) throws SQLException {
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();

            int nFila = tablaprincipal.getSelectedRow();
            String num = tablaprincipal.getValueAt(nFila, 9).toString();
            String cnum = tablaprincipal.getValueAt(nFila, 20).toString();

            String cValorNominal = tablaprincipal.getValueAt(nFila, 9).toString();

            System.out.println(cValorNominal);
            num = num.replace(".", "").replace(",", ".");
            numero_a_letras numero = new numero_a_letras();

            String cvalorinversion = tablaprincipal.getValueAt(nFila, 19).toString();
            cvalorinversion = cvalorinversion.replace(".", "").replace(",", ".");

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("Letra", numero.Convertir(num, true, Integer.valueOf(cvalorinversion)));
            parameters.put("LetraNominal", numero.Convertir(cnum, true, Integer.valueOf(tablaprincipal.getValueAt(nFila, 19).toString())));
            parameters.put("nNumero", tablaprincipal.getValueAt(nFila, 1).toString());
            parameters.put("nPunta", punta);
            parameters.put("cValorNominal", cValorNominal);
            parameters.put("cTipo", ctipo);

            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            JasperReport jr = null;

            URL url = getClass().getClassLoader().getResource("Reports/" + reporte.trim());
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos

            masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
            JasperPrintManager.printReport(masterPrint, false);

        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(null, "No puede emitirse el Reporte");
            System.out.println(e);
        }        // TODO add your handling code here:
    }

    private void CargarDesglose() {
        int cantidadRegistro = modelodesglose.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodesglose.removeRow(0);
        }
        desgloseDAO desDAO = new desgloseDAO();
        try {
            for (desglose detdes : desDAO.todosxCierre(creferencia.getText())) {
                String Detalle[] = {formatea.format(detdes.getCantidad()),
                    formatea.format(detdes.getImporte()),
                    detdes.getSerie(),
                    detdes.getNro_titulo(),
                    formatosinpunto.format(detdes.getDesde_acci()),
                    formatosinpunto.format(detdes.getHasta_acci()),
                    formatosinpunto.format(detdes.getIditem())};
                modelodesglose.addRow(Detalle);
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        int cantFilas = tabladesglose.getRowCount();
        if (cantFilas > 0) {
            BorrarDesglose.setEnabled(true);
        } else {
            BorrarDesglose.setEnabled(false);
        }
    }

    private void GenerarCartera() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        for (int i = 0; i <= tabladesglose.getRowCount(); i++) {
            //SUMA EL TOTAL A PAGAR
            if (i == tabladesglose.getRowCount()) {
                int nCantidad = Integer.valueOf(this.tabladesglose.getValueAt(i - 1, 0).toString());
                String cImporte = this.tabladesglose.getValueAt(i - 1, 1).toString();
                cImporte = cImporte.replace(".", "").replace(",", ".");
                String cSerie = this.tabladesglose.getValueAt(i - 1, 2).toString();
                String cNrotitulo = this.tabladesglose.getValueAt(i - 1, 3).toString();
                int nDesdeAcci = Integer.valueOf(this.tabladesglose.getValueAt(i - 1, 4).toString());
                int nHastaAcci = Integer.valueOf(this.tabladesglose.getValueAt(i - 1, 5).toString());
                double nIditem = Double.valueOf(this.tabladesglose.getValueAt(i - 1, 6).toString());

                GrabarCartera(nCantidad, cImporte, cSerie, cNrotitulo, nDesdeAcci, nHastaAcci, nIditem);
                System.out.println("Cantidad de Registros" + tabladesglose.getRowCount());
                System.out.println("Se genera cartera" + i);
            }
        }
    }

    private void GrabarCartera(int cant, String cImporte, String cSerie, String cNroTitulo, int nDesde, int nHasta, double idItem) {

        cartera_clientesDAO caDAO = new cartera_clientesDAO();
        cartera_clientes ca = new cartera_clientes();
        try {
            caDAO.borrarCartera(idItem);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        tituloDAO titDAO = new tituloDAO();
        titulo ti = null;
        emisorDAO emDAO = new emisorDAO();
        emisor em = null;
        monedaDAO mnDAO = new monedaDAO();
        moneda mn = null;
        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        vendedorDAO veDAO = new vendedorDAO();
        vendedor ve = null;
        bancoplazaDAO baDAO = new bancoplazaDAO();
        bancoplaza ba = null;
        try {
            ti = titDAO.buscarId(Integer.valueOf(this.titulo.getText()));
            em = emDAO.buscarId(Integer.valueOf(this.emisor.getText()));
            mn = mnDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            cl = cliDAO.buscarId(Integer.valueOf(this.clientecomprador.getText()));
            ve = veDAO.buscarId(Integer.valueOf(this.asesorcomprador.getText()));
            ba = baDAO.buscarId(Integer.valueOf(this.banco.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        Date Fecha = ODate.de_java_a_sql(fecha.getDate());
        Date FechaCierre = ODate.de_java_a_sql(fechacierre.getDate());
        Date FechaEmision = ODate.de_java_a_sql(fechaemision.getDate());
        Date FechaVencimiento = ODate.de_java_a_sql(vencimiento.getDate());

        UUID id = new UUID();
        String idcartera = UUID.crearUUID();
        idcartera = idcartera.substring(1, 25);

        String cPlazo = plazo.getText();
        cPlazo = cPlazo.replace(".", "").replace(",", ".");

        String cPrecio = precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");

        String cTasa = tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");

        ca.setCreferencia(this.creferencia.getText());
        ca.setIdcartera(idcartera);
        ca.setNumero(Double.valueOf(this.numero.getText()));
        ca.setFecha(Fecha);
        ca.setComprador(cl);
        ca.setAsesorcompra(ve);
        ca.setOperacion(1);
        ca.setTipooperacion(tipooperacion.getSelectedIndex() + 1);
        ca.setMercado(mercado.getSelectedIndex() + 1);
        ca.setMoneda(mn);
        ca.setEmisor(em);
        ca.setTitulo(ti);
        ca.setValor_nominal(new BigDecimal(cImporte));
        ca.setCantidad(new BigDecimal(cant));
        ca.setValor_inversion(ca.getValor_nominal().multiply(ca.getCantidad()));
        ca.setTasa(new BigDecimal(cTasa));
        ca.setPlazo(Integer.valueOf(plazo.getText()));
        ca.setTipoplazo(tipoplazo.getSelectedIndex() + 1);
        ca.setCustodia(0);
        ca.setPrecio(new BigDecimal(cPrecio));
        ca.setFechacierre(FechaCierre);
        ca.setNegociado(negociado.getSelectedIndex());
        ca.setBase(Integer.valueOf(base.getText()));
        ca.setPeriodopago(periodopago.getSelectedIndex() + 1);
        ca.setNumerobolsa(numerobolsa.getText());
        ca.setFechaemision(FechaEmision);
        ca.setVencimiento(FechaVencimiento);
        ca.setSerie(cSerie);
        ca.setNro_titulo(cNroTitulo);
        ca.setDesde_acci(nDesde);
        ca.setHasta_acci(nHasta);
        ca.setEstado(1);
        ca.setItemdesglose(idItem);
        ca.setBancopago(ba);
        ca.setCuentapago(cuentapago.getText());
        try {
            caDAO.insertarRentaFija(ca);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void CargarCupon(String creferencia) {
        int cantidadRegistro = modelocupon.getRowCount();
        String cNombreDia = "SD";
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }
        cuponesDAO detDAO = new cuponesDAO();
        try {
            for (cupones detvta : detDAO.MostrarDetalle(creferencia)) {
                LocalDate currenDate = LocalDate.parse(detvta.getFechavencimiento().toString());
                System.out.println("DIA " + detvta.getFechavencimiento().toString());
                int diaSemana = currenDate.getDayOfWeek().getValue() + 1;
                switch (diaSemana) {
                    case 1:
                        cNombreDia = "Domingo";
                        break; // break es opcional
                    case 2:
                        cNombreDia = "Lunes";
                        break; // break es opcional
                    case 3:
                        cNombreDia = "Martes";
                        break; // break es opcional
                    case 4:
                        cNombreDia = "Míercoles";
                        break; // break es opcional
                    case 5:
                        cNombreDia = "Jueves";
                        break; // break es opcional
                    case 6:
                        cNombreDia = "Viernes";
                        break; // break es opcional
                    case 7:
                        cNombreDia = "Sábado";
                        break; // break es opcional
                    default:
                        cNombreDia = "SD";
                }
                String Detalle[] = {String.valueOf(detvta.getNcupon()) + "/" + String.valueOf(detvta.getNcantidad()), formatea.format(detvta.getPlazocupon()), formatoFecha.format(detvta.getFechainicial()), formatoFecha.format(detvta.getFechavencimiento()), formatea.format(detvta.getValorfuturo()), cNombreDia};
                modelocupon.addRow(Detalle);
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        int cantFilas = tablacupon.getRowCount();
        if (cantFilas > 0) {
            //  BotonGrabarMateria.setEnabled(true);
            //  BotonBorrarMateria.setEnabled(true);
        } else {
            // BotonGrabarMateria.setEnabled(true);
            //  BotonBorrarMateria.setEnabled(false);
        }

    }

    private void TituloCupon() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        modelocupon.addColumn("N° Cupón");
        modelocupon.addColumn("Plazo");
        modelocupon.addColumn("Fecha Inicio");
        modelocupon.addColumn("Vencimiento");
        modelocupon.addColumn("Valor Cupón");
        modelocupon.addColumn("Día Venc.");
        int[] anchos = {120, 150, 150, 150, 150, 150};
        for (int i = 0; i < modelocupon.getColumnCount(); i++) {
            tablacupon.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacupon.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacupon.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tablacupon.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        tablacupon.getColumnModel().getColumn(2).setCellRenderer(TablaCentro);
        tablacupon.getColumnModel().getColumn(3).setCellRenderer(TablaCentro);
        tablacupon.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        //Se usa para poner invisible una determinada celda
        tablacupon.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tablacupon.setFont(font);

    }

    private void TituloComision() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        modelocomision.addColumn("Asesor");
        modelocomision.addColumn("Punta");
        modelocomision.addColumn("% Comisión");
        modelocomision.addColumn("Monto");
        modelocomision.addColumn("IVA");
        modelocomision.addColumn("Total");
        int[] anchos = {150, 90, 100, 150, 150, 150};
        for (int i = 0; i < modelocupon.getColumnCount(); i++) {
            tablacupon.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacomision.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacomision.getColumnModel().getColumn(2).setCellRenderer(TablaCentro);
        tablacomision.getColumnModel().getColumn(3).setCellRenderer(TablaCentro);
        tablacomision.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        tablacomision.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        //Se usa para poner invisible una determinada celda
        tablacomision.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tablacomision.setFont(font);
    }

    private void CargarComision(String creferencia) {
        int cantidadRegistro = modelocomision.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocomision.removeRow(0);
        }
        comisionDAO detDAO = new comisionDAO();
        try {
            for (comision detvta : detDAO.MostrarComisiones(creferencia)) {
                String Detalle[] = {String.valueOf(detvta.getAsesor().getNombre()),
                    detvta.getObservacion(),
                    formatea.format(detvta.getComision()),
                    formatea.format(detvta.getMonto()),
                    formatea.format(detvta.getMontoiva()),
                    formatea.format(detvta.getTotales())};
                modelocomision.addRow(Detalle);
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        int cantFilas = tablacomision.getRowCount();
        if (cantFilas > 0) {
            //  BotonGrabarMateria.setEnabled(true);
            //  BotonBorrarMateria.setEnabled(true);
        } else {
            // BotonGrabarMateria.setEnabled(true);
            //  BotonBorrarMateria.setEnabled(false);
        }

    }

    private void Inicializar() {
        this.fecha.setCalendar(c2);
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocliente.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

    private void jComboBox1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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

    private void TitTitulo() {
        modelotitulo.addColumn("Código");
        modelotitulo.addColumn("Nombre");
        modelotitulo.addColumn("Emisor");

        int[] anchos = {90, 100, 150};
        for (int i = 0; i < modelotitulo.getColumnCount(); i++) {
            tablatitulo.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablatitulo.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablatitulo.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablatitulo.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablatitulo.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitVendedor() {
        modelovendedor.addColumn("Código");
        modelovendedor.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelovendedor.getColumnCount(); i++) {
            tablavendedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablavendedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablavendedor.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablavendedor.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablavendedor.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtrovendedor(int nNumeroColumna) {
        trsfiltrovendedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarVendedor.getText(), nNumeroColumna));
    }

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Denominación del Cliente");

        int[] anchos = {90, 250};
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

    }

    private void buscarcadenaKeyPressed(KeyEvent evt) {//GEN-FIRST:event_buscarcadenaKeyPressed
        this.buscarcadena.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (buscarcadena.getText()).toUpperCase();
                buscarcadena.setText(cadena);

                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 5;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 6;
                        break;//por codigo
                    case 3:
                        indiceColumnaTabla = 7;
                        break;//por codigo
                    case 4:
                        indiceColumnaTabla = 15;
                        break;//por codigo
                    case 5:
                        indiceColumnaTabla = 16;
                        break;//por codigo
                    case 6:
                        indiceColumnaTabla = 17;
                        break;//por codigo
                    case 7:
                        indiceColumnaTabla = 18;
                        break;//por codigo
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablaprincipal.getModel());
        tablaprincipal.setRowSorter(trsfiltro);

    }//GEN-LAST:event_buscarcadenaKeyPressed

    private void SalirCompletoActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirCompletoActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCompletoActionPerformed

    private void AgregarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        int cantidadRegistro = modelocupon.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }

        cantidadRegistro = modelodesglose.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodesglose.removeRow(0);
        }

        cantidadRegistro = modelocomision.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocomision.removeRow(0);
        }

        idControl.setText("0");
        this.limpiar();
        detalle_operacion.setModal(true);
        detalle_operacion.setSize(750, 600);//(ancho,alto
        //Establecemos un título para el jDialog
        detalle_operacion.setTitle("Agregar Operación");
        detalle_operacion.setLocationRelativeTo(null);
        detalle_operacion.setVisible(true);
        fecha.requestFocus();

          }//GEN-LAST:event_AgregarActionPerformed

    public void limpiar() {
        this.numero.setText("0");
        this.numerobolsa.setText("0");
        this.fecha.setCalendar(c2);
        this.fechacierre.setCalendar(c2);
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = null;
        config = configDAO.consultar();

        this.titulo.setText("0");
        this.nombretitulo.setText("");
        this.clientecomprador.setText("0");
        this.nombreclientecomprador.setText("");
        this.moneda.setText(String.valueOf(config.getMonedadefecto().getCodigo()));
        this.nombremoneda.setText(config.getMonedadefecto().getNombre());
        this.moneda.setText("1");
        this.nombremoneda.setText("GUARANIES");
        this.valor_nominal.setText("0");
        this.cantidad.setText("0");
        this.precio.setText("0");
        this.tasa.setText("0");
        this.tipooperacion.setSelectedIndex(0);
        this.negociado.setSelectedIndex(0);
        this.mercado.setSelectedIndex(0);
        this.tipoplazo.setSelectedIndex(0);
        this.periodopago.setSelectedIndex(0);
//        this.formapago.setSelectedIndex(0);
        this.clientecomprador.setText("0");
        this.nombreclientecomprador.setText("");
        this.clientevendedor.setText("0");
        this.nombreclientevendedor.setText("");
        this.asesorcomprador.setText("0");
        this.nombreasesorcomprador.setText("");
        this.asesorvendedor.setText("0");
        this.nombreasesorvendedor.setText("");
        this.emisor.setText("0");
        this.nombreemisor.setText("");
        this.cupones.setText("0");
        this.banco.setText("0");
        this.nombrebanco.setText("");
        this.cuentapago.setText("");
        this.arancelcompra.setSelected(false);
        this.arancelventa.setSelected(false);
        this.comcomprador.setText("0");
        this.comvendedor.setText("0");
    }

    private void tablaprincipalKeyPressed(KeyEvent evt) {//GEN-FIRST:event_tablaprincipalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_INSERT) {
            Agregar.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Modificar.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            Eliminar.doClick();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            refrescar.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalKeyPressed

    private void tablaprincipalMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tablaprincipalMouseClicked
        int nFila = this.tablaprincipal.getSelectedRow();
        this.idControl.setText(this.tablaprincipal.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalMouseClicked

    private void ModificarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        idControl.setText("2");
        int nunidad = 1;
        this.limpiar();
        this.LimpiarDesglose();
        //if (Integer.valueOf(Config.cNivelUsuario) == 1) {
        if (nunidad == 1) {
            int nFila = this.tablaprincipal.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaprincipal.requestFocus();
                return;
            }

            this.numero.setText(this.tablaprincipal.getValueAt(nFila, 1).toString());
            this.numero.setEnabled(false);
            precierre_operacionDAO preDAO = new precierre_operacionDAO();
            precierre_operacion pre = null;
            try {
                pre = preDAO.BuscarxOperacion(Double.valueOf(this.numero.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (pre != null) {
                fecha.setDate(pre.getFecha());
                creferencia.setText(pre.getCreferencia());
                fechacierre.setDate(pre.getFechacierre());
                fechaemision.setDate(pre.getFechaemision());
                vencimiento.setDate(pre.getVencimiento());
                numerobolsa.setText(pre.getNumerobolsa());
                emisor.setText(String.valueOf(pre.getEmisor().getCodigo()));
                nombreemisor.setText(pre.getEmisor().getNombre());
                tipooperacion.setSelectedIndex(pre.getTipooperacion() - 1);
                nombretitulo.setText(pre.getTitulo().getNomalias());
                negociado.setSelectedIndex(pre.getNegociado());
                plazo.setText(String.valueOf(pre.getPlazo()));
                mercado.setSelectedIndex(pre.getMercado() - 1);
                tipoplazo.setSelectedIndex(pre.getTipoplazo() - 1);
                titulo.setText(String.valueOf(pre.getTitulo().getCodigo()));
                periodopago.setSelectedIndex(pre.getPeriodopago() - 1);
                valor_nominal.setText(formatea.format(pre.getValor_nominal()));
                cantidad.setText(formatea.format(pre.getCantidad()));
                precio.setText(formatodecimal.format(pre.getPrecio()));
                tasa.setText(formatodecimal.format(pre.getTasa()));
                clientecomprador.setText(String.valueOf(pre.getComprador()));
                clientevendedor.setText(String.valueOf(pre.getVendedor()));
                nombreclientecomprador.setText(pre.getNombrecomprador());
                nombreclientevendedor.setText(pre.getNombrevendedor());
                cupones.setText(String.valueOf(pre.getCupones()));

                asesorvendedor.setText(String.valueOf(pre.getAsesorventa()));
                nombreasesorvendedor.setText(pre.getNombre_asesor_venta());
                asesorcomprador.setText(String.valueOf(pre.getAsesorcompra()));
                nombreasesorcomprador.setText(pre.getNombre_asesor_compra());
                base.setText(String.valueOf(pre.getBase()));

                moneda.setText(String.valueOf(pre.getMoneda().getCodigo()));
                nombremoneda.setText(pre.getMoneda().getNombre());

                banco.setText(formatosinpunto.format(pre.getBancopago()));
                nombrebanco.setText(pre.getNombrebanco());
                cuentapago.setText(pre.getCuentapago());
                comcomprador.setText(formatodecimal.format(pre.getComcomprador()));
                comvendedor.setText(formatodecimal.format(pre.getComvendedor()));

                if (pre.getArancelventa() == 1) {
                    arancelventa.setSelected(true);
                } else {
                    arancelventa.setSelected(false);
                }

                if (pre.getArancelcompra() == 1) {
                    arancelcompra.setSelected(true);
                } else {
                    arancelcompra.setSelected(false);
                }
                this.CargarCupon(creferencia.getText());
                this.CargarDesglose();
                this.CargarComision(creferencia.getText());
                detalle_operacion.setModal(true);

                detalle_operacion.setSize(750, 600);//(ancho,alto
                //Establecemos un título para el jDialog
                detalle_operacion.setTitle("Editar Operación");
                detalle_operacion.setLocationRelativeTo(null);
                detalle_operacion.setVisible(true);
                fecha.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "USUARIO NO AUTORIZADO");
            }
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void tablaprincipalFocusGained(FocusEvent evt) {//GEN-FIRST:event_tablaprincipalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalFocusGained

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
        int nFila = tablaprincipal.getSelectedRow();
        String num = tablaprincipal.getValueAt(nFila, 1).toString();

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                precierre_operacionDAO preDAO = new precierre_operacionDAO();
                cartera_clientesDAO caDAO = new cartera_clientesDAO();
                cuponesDAO cuDAO = new cuponesDAO();
                desgloseDAO deDAO = new desgloseDAO();

                try {
                    precierre_operacion pre = preDAO.BuscarxOperacion(Double.valueOf(num));
                    if (pre == null) {
                        JOptionPane.showMessageDialog(null, "Registro no Existe");
                    } else {
                        preDAO.borrarPrecierre(Double.valueOf(num));
                        caDAO.borrarCarteraTotal(pre.getCreferencia());
                        cuDAO.borrarDetallecupones(pre.getCreferencia());
                        deDAO.eliminarDesglosexOperacion(pre.getCreferencia());
                        JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "USUARIO NO AUTORIZADO");
        }
        this.refrescar.doClick();
    }//GEN-LAST:event_EliminarActionPerformed

    private void tablaprincipalFocusLost(FocusEvent evt) {//GEN-FIRST:event_tablaprincipalFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalFocusLost

    private void ListarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        int Fila = tablaprincipal.getSelectedRow();
        String cVendedor = tablaprincipal.getValueAt(Fila, 14).toString();
        String cComprador = tablaprincipal.getValueAt(Fila, 15).toString();

        try {
            if (!cComprador.isEmpty()) {
                this.ImprimirDocumentos("orden_operacion_renta_fija.jasper", 1, "SV");
            }
            if (!cVendedor.isEmpty()) {
                this.ImprimirDocumentos("orden_operacion_renta_fija.jasper", 2, "SV");
            }
            this.ImprimirDocumentos("anexoiv.jasper", 0, "SV");
            this.ImprimirDocumentos("carta_declaracion_jurada.jasper", 0, "SV");

            String cExpresion = "";
            for (int i = 1; i <= 3; i++) {
                if (i == 1) {
                    cExpresion = "Original: Comitente";
                } else if (i == 2) {
                    cExpresion = "Duplicado: Representante de Obligacionista";
                } else {
                    cExpresion = "Triplicado: Casa de Bolsa";
                }
                this.ImprimirDocumentos("certificado_de_operacion.jasper", 1, cExpresion);
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_ListarActionPerformed

    private void SalirActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        detalle_operacion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GrabarActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed

        if (Double.valueOf(numero.getText()) == 0) {
            UUID id = new UUID();
            referencia = UUID.crearUUID();
            referencia = referencia.substring(1, 25);
            creferencia.setText(referencia);
        }

        if (this.titulo.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese Código del Título");
            this.titulo.requestFocus();
            return;
        }

        if (this.moneda.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese Moneda");
            this.moneda.requestFocus();
            return;
        }

        String cPlazo = plazo.getText();
        cPlazo = cPlazo.replace(".", "").replace(",", ".");
        if (Double.valueOf(cPlazo) == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Plazo");
            this.plazo.requestFocus();
            return;
        }

        String cValorNominal = valor_nominal.getText();
        cValorNominal = cValorNominal.replace(".", "").replace(",", ".");

        if (Double.valueOf(cValorNominal) == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Valor Nominal");
            this.valor_nominal.requestFocus();
            return;
        }

        String cCantidad = cantidad.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCantidad) == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cantidad");
            this.cantidad.requestFocus();
            return;
        }

        String cPrecio = precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");

        if (Double.valueOf(cPrecio) == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Precio");
            this.precio.requestFocus();
            return;
        }

        String cTasa = tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");

        if (Double.valueOf(cTasa) == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese la Tasa");
            this.tasa.requestFocus();
            return;
        }

        double nValorInversion = 0.00;

        if (Integer.valueOf(moneda.getText()) == 1) {
            nValorInversion = Math.round(Double.valueOf(cPrecio) * Double.valueOf(cValorNominal) / 100);
        } else {
            nValorInversion = Double.valueOf(cPrecio) * Double.valueOf(cValorNominal) / 100;
        }

        String cComcomprador = comcomprador.getText();
        cComcomprador = cComcomprador.replace(".", "").replace(",", ".");

        String cComvendedor = comvendedor.getText();
        cComvendedor = cComvendedor.replace(".", "").replace(",", ".");

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            // Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            Date Fecha = ODate.de_java_a_sql(fecha.getDate());
            Date FechaCierre = ODate.de_java_a_sql(fechacierre.getDate());
            Date FechaEmision = ODate.de_java_a_sql(fechaemision.getDate());
            Date FechaVencimiento = ODate.de_java_a_sql(vencimiento.getDate());

            precierre_operacionDAO grabar = new precierre_operacionDAO();
            precierre_operacion pre = new precierre_operacion();

            tituloDAO titDAO = new tituloDAO();
            titulo ti = null;
            emisorDAO emDAO = new emisorDAO();
            emisor em = null;
            monedaDAO mnDAO = new monedaDAO();
            moneda mn = null;

            try {
                ti = titDAO.buscarId(Integer.valueOf(this.titulo.getText()));
                em = emDAO.buscarId(Integer.valueOf(this.emisor.getText()));
                mn = mnDAO.buscarId(Integer.valueOf(this.moneda.getText()));

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            //CAPTURAMOS LOS DATOS DE LA CABECERA
            pre.setCreferencia(this.creferencia.getText());
            pre.setNumerobolsa(numerobolsa.getText());
            pre.setFecha(Fecha);
            pre.setFechacierre(FechaCierre);
            pre.setFechaemision(FechaEmision);
            pre.setVencimiento(FechaVencimiento);
            pre.setTitulo(ti);
            pre.setMoneda(mn);
            pre.setEmisor(em);
            pre.setComprador(Integer.valueOf(clientecomprador.getText()));
            pre.setVendedor(Integer.valueOf(clientevendedor.getText()));
            pre.setAsesorcompra(Integer.valueOf(asesorcomprador.getText()));
            pre.setAsesorventa(Integer.valueOf(asesorvendedor.getText()));
            pre.setMercado(mercado.getSelectedIndex() + 1);
            pre.setPeriodopago(periodopago.getSelectedIndex() + 1);
            pre.setTipoplazo(tipoplazo.getSelectedIndex() + 1);
            pre.setTipooperacion(tipooperacion.getSelectedIndex() + 1);
            pre.setBase(Integer.valueOf(base.getText()));
            pre.setPlazo(Integer.valueOf(cPlazo));
            pre.setPrecio(new BigDecimal(cPrecio));
            pre.setValor_nominal(new BigDecimal(cValorNominal));
            pre.setCantidad(new BigDecimal(cCantidad));
            pre.setTasa(new BigDecimal(cTasa));
            pre.setValor_inversion(new BigDecimal(nValorInversion));
            pre.setOperacion(1);
            pre.setCupones(Integer.valueOf(cupones.getText()));
            pre.setNegociado(negociado.getSelectedIndex());
            pre.setBancopago(Integer.valueOf(banco.getText()));
            pre.setCuentapago(cuentapago.getText());
            pre.setComcomprador(new BigDecimal(Double.valueOf(cComcomprador)));
            pre.setComvendedor(new BigDecimal(Double.valueOf(cComvendedor)));
            if (arancelventa.isSelected()) {
                pre.setArancelventa(1);
            } else {
                pre.setArancelventa(0);
            }
            if (arancelcompra.isSelected()) {
                pre.setArancelcompra(1);
            } else {
                pre.setArancelcompra(0);
            }
            String detalle = "[";
            int Items = tablacupon.getRowCount();
            String iddoc = null;
            for (int i = 0; i < Items; i++) {
                try {
                    dEmision = ODate.de_java_a_sql(formatoFecha.parse(tablacupon.getValueAt(i, 2).toString()));
                    dVence = ODate.de_java_a_sql(formatoFecha.parse(tablacupon.getValueAt(i, 3).toString()));
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }

                iddoc = UUID.crearUUID();
                iddoc = iddoc.substring(1, 25);

                String cValorCupon = tablacupon.getValueAt(i, 4).toString();
                cValorCupon = cValorCupon.replace(".", "").replace(",", ".");

                String linea = "{idprecierre :'" + this.creferencia.getText().trim() + "',"
                        + "idcupon :'" + iddoc.trim() + "',"
                        + "numeroprecierre :" + this.numero.getText() + ","
                        + "iddesglose :'" + iddoc.trim() + "',"
                        + "ncantidad :" + cupones.getText() + ","
                        + "ncupon :" + tablacupon.getValueAt(i, 0).toString() + ","
                        + "diaemision :'" + FechaEmision + "',"
                        + "fechainicial :'" + dEmision + "',"
                        + "plazocupon :" + tablacupon.getValueAt(i, 1).toString().trim() + ","
                        + "base :" + this.base.getText() + ","
                        + "interes :" + cTasa + ","
                        + "fechavencimiento :'" + dVence + "',"
                        + "valorfuturo :" + cValorCupon + ","
                        + "montocapital:" + cValorNominal + ","
                        + "estado :" + 1 + "},";
                //CARGAMOS EL JSON CON LOS DATOS DE CUPONES   
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            System.out.println(detalle);
            cuponesDAO cupDAO = new cuponesDAO();

            if (Double.valueOf(this.numero.getText()) == 0) {
                try {
                    pre.setUsuarioalta(Integer.valueOf(Config.CodUsuario));
                    grabar.insertarOperacion(pre, detalle);
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                }
            } else {
                //Actualizar 
                pre.setNumero(Double.valueOf(this.numero.getText()));
                pre.setUsuarioupdate(Integer.valueOf(Config.CodUsuario));
                try {
                    grabar.ActualizarPrecierre(pre, detalle);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            }
        }
        detalle_operacion.setVisible(false);
        this.detalle_operacion.setModal(false);
        this.refrescar.doClick();

    }//GEN-LAST:event_GrabarActionPerformed

    private void detalle_operacionFocusGained(FocusEvent evt) {//GEN-FIRST:event_detalle_operacionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_operacionFocusGained

    private void detalle_operacionWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_detalle_operacionWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_operacionWindowGainedFocus

    private void detalle_operacionWindowActivated(WindowEvent evt) {//GEN-FIRST:event_detalle_operacionWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_operacionWindowActivated

    private void tablaprincipalPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_tablaprincipalPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalPropertyChange

    private void SalirBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirBancoActionPerformed
        this.BBancos.setModal(true);
        this.BBancos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirBancoActionPerformed

    private void AceptarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarBancoActionPerformed
        int nFila = this.tablabancos.getSelectedRow();
        this.banco.setText(this.tablabancos.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablabancos.getValueAt(nFila, 1).toString());
        this.cuentapago.setText(this.tablabancos.getValueAt(nFila, 2).toString());
        this.SalirBanco.doClick();
        this.moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarBancoActionPerformed

    private void tablabancosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarBanco.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancosKeyPressed

    private void tablabancosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancosMouseClicked
        this.AceptarBanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancosMouseClicked

    private void txtbuscarbancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarbancoKeyPressed
        this.txtbuscarbanco.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtbuscarbanco.getText()).toUpperCase();
                txtbuscarbanco.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combobanco.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrobanco(indiceColumnaTabla);
            }
        });
        trsfiltrobanco = new TableRowSorter(tablabancos.getModel());
        tablabancos.setRowSorter(trsfiltrobanco);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarbancoKeyPressed

    private void txtbuscarbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarbancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarbancoActionPerformed

    private void combobancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobancoActionPerformed


    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechacierre.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.numerobolsa.requestFocus();
        }   // TODO add your handling code */
    }//GEN-LAST:event_fechaKeyPressed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void numeroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyReleased

    private void numeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroKeyPressed

    private void numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroActionPerformed

    }//GEN-LAST:event_numeroActionPerformed

    private void numeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numeroFocusGained
        numero.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_numeroFocusGained

    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaRentaFija GrillaAus = new GrillaRentaFija();
        Thread HiloGrilla = new Thread(GrillaAus);
        HiloGrilla.start();
    }//GEN-LAST:event_refrescarActionPerformed

    private void GrabarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GrabarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
//          this.observaciones.requestFocus();
        }   // TODO add your handling code 
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarKeyPressed

    private void comboclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

    private void jTBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

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
        trsfiltrocliente = new TableRowSorter(tablacliente.getModel());
        tablacliente.setRowSorter(trsfiltrocliente);
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
        if (nBuscarCliente == 1) {
            this.clientecomprador.setText(this.tablacliente.getValueAt(nFila, 0).toString());
            this.nombreclientecomprador.setText(this.tablacliente.getValueAt(nFila, 1).toString());
            this.clientevendedor.requestFocus();
        } else {
            this.clientevendedor.setText(this.tablacliente.getValueAt(nFila, 0).toString());
            this.nombreclientevendedor.setText(this.tablacliente.getValueAt(nFila, 1).toString());
            this.asesorcomprador.requestFocus();
        }
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        //        this.giraduria.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

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
//        this.cotizacion.setText(this.tablamoneda.getValueAt(nFila, 2).toString());
        this.BMoneda.setVisible(false);
        this.jTBuscarMoneda.setText("");
        this.valor_nominal.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void valor_nominalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_valor_nominalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cantidad.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_valor_nominalKeyPressed

    private void buscarmonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarmonedaActionPerformed
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
                //Establecemos un título para el jDialog
            }
            valor_nominal.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarmonedaActionPerformed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.valor_nominal.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.titulo.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        this.buscarmoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void monedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_monedaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaFocusGained

    private void buscarclientecompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarclientecompradorActionPerformed
        nBuscarCliente = 1;
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Cliente");
        BCliente.setVisible(true);
        BCliente.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarclientecompradorActionPerformed

    private void clientecompradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientecompradorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.asesorcomprador.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.plazo.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_clientecompradorKeyPressed

    private void clientecompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientecompradorActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.clientecomprador.getText().trim()));
            if (cl.getCodigo() == 0) {
                buscarclientecomprador.doClick();
            } else {
                nombreasesorcomprador.setText(cl.getNombre());
                asesorcomprador.requestFocus();
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_clientecompradorActionPerformed

    private void buscartituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscartituloActionPerformed
        BTitulos.setModal(true);
        BTitulos.setSize(482, 575);
        BTitulos.setLocationRelativeTo(null);
        BTitulos.setTitle("Buscar Título");
        BTitulos.setVisible(true);
        moneda.requestFocus();
        BTitulos.setModal(false);
        moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscartituloActionPerformed

    private void tituloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tituloKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }   // TODO add your handling code */

        // TODO add your handling code here:
    }//GEN-LAST:event_tituloKeyPressed

    private void tituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tituloActionPerformed
        tituloDAO titDAO = new tituloDAO();
        titulo tit = null;
        try {
            tit = titDAO.buscarIdOperacion(Integer.valueOf(this.titulo.getText()), 1);
            if (tit.getCodigo() == 0) {
                BTitulos.setModal(true);
                BTitulos.setSize(482, 575);
                BTitulos.setLocationRelativeTo(null);
                BTitulos.setTitle("Buscar Título");
                BTitulos.setVisible(true);
                moneda.requestFocus();
                BTitulos.setModal(false);
            } else {
                emisor.setText(String.valueOf(tit.getEmpresa().getCodigo()));
                nombreemisor.setText(tit.getEmpresa().getNombre().trim());
                nombretitulo.setText(tit.getNomalias().trim());
                moneda.setText(String.valueOf(tit.getMoneda().getCodigo()));
                nombremoneda.setText(tit.getMoneda().getNombre());
                tasa.setText(formatea.format(tit.getTasa()));
                base.setText(formatea.format(tit.getBase()));
                fechaemision.setDate(tit.getFechaemision());
                vencimiento.setDate(tit.getVencimiento());
                cupones.setText(String.valueOf(tit.getCupones()));
                String cPeriodoPago = tit.getPagointeres().toString();

                //MENSUAL, BIMESTRAL, TRIMESTRAL, CUATRIMESTRAL, SEMESTRAL, ANUAL, VENCIMIENTO                
                if (cPeriodoPago.toUpperCase().equals("MENSUAL")) {
                    periodopago.setSelectedIndex(0);
                } else if (cPeriodoPago.toUpperCase().equals("MENSUAL")) {
                    periodopago.setSelectedIndex(1);
                } else if (cPeriodoPago.toUpperCase().equals("TRIMESTRAL")) {
                    periodopago.setSelectedIndex(2);
                } else if (cPeriodoPago.toUpperCase().equals("CUATRIMESTRAL")) {
                    periodopago.setSelectedIndex(3);
                } else if (cPeriodoPago.toUpperCase().equals("SEMESTRAL")) {
                    periodopago.setSelectedIndex(4);
                } else if (cPeriodoPago.toUpperCase().equals("ANUAL")) {
                    periodopago.setSelectedIndex(5);
                } else if (cPeriodoPago.toUpperCase().equals("VENCIMIENTO")) {
                    periodopago.setSelectedIndex(6);
                }
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_tituloActionPerformed

    private void tituloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tituloFocusGained

        titulo.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_tituloFocusGained

    private void numerobolsaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numerobolsaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_numerobolsaFocusGained

    private void numerobolsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numerobolsaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numerobolsaActionPerformed

    private void numerobolsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numerobolsaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fecha.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_numerobolsaKeyPressed

    private void numerobolsaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numerobolsaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_numerobolsaKeyReleased

    private void fechacierreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechacierreFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechacierreFocusGained

    private void fechacierreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechacierreKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tipooperacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_fechacierreKeyPressed

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.precio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.valor_nominal.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tasa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_precioKeyPressed

    private void tasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tasaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tipoplazo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.precio.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaKeyPressed

    private void fechaemisionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaemisionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaemisionFocusGained

    private void fechaemisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaemisionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.vencimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipoplazo.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaemisionKeyPressed

    private void vencimientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vencimientoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoFocusGained

    private void vencimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.plazo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechaemision.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoKeyPressed

    private void plazoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plazoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.periodopago.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.vencimiento.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoKeyPressed

    private void clientevendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientevendedorActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.clientevendedor.getText().trim()));
            if (cl.getCodigo() == 0) {
                buscarclientevendedor.doClick();
            } else {
                nombreclientevendedor.setText(cl.getNombre());
                asesorcomprador.requestFocus();
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_clientevendedorActionPerformed

    private void clientevendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientevendedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.asesorvendedor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.asesorcomprador.requestFocus();
        }   // TODO add your handling code */

        // TODO add your handling code here:
    }//GEN-LAST:event_clientevendedorKeyPressed

    private void buscarclientevendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarclientevendedorActionPerformed
        nBuscarCliente = 2;
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Cliente");
        BCliente.setVisible(true);
        BCliente.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarclientevendedorActionPerformed

    private void asesorcompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asesorcompradorActionPerformed
        vendedorDAO veDAO = new vendedorDAO();
        vendedor vn = null;
        try {
            vn = veDAO.buscarId(Integer.valueOf(this.asesorcomprador.getText()));
            if (vn.getCodigo() == 0) {
                buscarasesorcomprador.doClick();
            } else {
                nombreasesorcomprador.setText(vn.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorcompradorActionPerformed

    private void asesorcompradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_asesorcompradorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.clientecomprador.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.clientevendedor.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorcompradorKeyPressed

    private void buscarasesorcompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarasesorcompradorActionPerformed
        nBuscarAsesor = 1;
        BVendedor.setModal(true);
        BVendedor.setSize(500, 575);
        BVendedor.setLocationRelativeTo(null);
        BVendedor.setVisible(true);
        BVendedor.setTitle("Buscar Asesor");
        BVendedor.setModal(false);
        //Establecemos un título para el jDialog
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarasesorcompradorActionPerformed

    private void asesorvendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asesorvendedorActionPerformed
        vendedorDAO veDAO = new vendedorDAO();
        vendedor vn = null;
        try {
            vn = veDAO.buscarId(Integer.valueOf(this.asesorvendedor.getText()));
            if (vn.getCodigo() == 0) {
                buscarasesorvendedor.doClick();
            } else {
                nombreasesorvendedor.setText(vn.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorvendedorActionPerformed

    private void asesorvendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_asesorvendedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.asesorcomprador.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorvendedorKeyPressed

    private void buscarasesorvendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarasesorvendedorActionPerformed
        nBuscarAsesor = 2;
        BVendedor.setModal(true);
        BVendedor.setSize(500, 575);
        BVendedor.setLocationRelativeTo(null);
        BVendedor.setVisible(true);
        BVendedor.setTitle("Buscar Asesor");
        BVendedor.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarasesorvendedorActionPerformed

    private void combotituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combotituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combotituloActionPerformed

    private void txtbuscartituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscartituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscartituloActionPerformed

    private void txtbuscartituloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscartituloKeyPressed
        this.txtbuscartitulo.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtbuscartitulo.getText()).toUpperCase();
                txtbuscartitulo.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combotitulo.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrotitulo(indiceColumnaTabla);
            }
        });
        trsfiltrotitulo = new TableRowSorter(tablatitulo.getModel());
        tablatitulo.setRowSorter(trsfiltrotitulo);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscartituloKeyPressed

    private void tablatituloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablatituloMouseClicked
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablatituloMouseClicked

    private void tablatituloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablatituloKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablatituloKeyPressed

    private void AceptarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed
        int nFila = this.tablatitulo.getSelectedRow();
        this.titulo.setText(this.tablatitulo.getValueAt(nFila, 0).toString());
        this.nombretitulo.setText(this.tablatitulo.getValueAt(nFila, 1).toString());

        tituloDAO titDAO = new tituloDAO();
        titulo tit = null;
        try {
            tit = titDAO.buscarIdOperacion(Integer.valueOf(this.titulo.getText()), 1);
            emisor.setText(String.valueOf(tit.getEmpresa().getCodigo()));
            nombreemisor.setText(tit.getEmpresa().getNombre().trim());
            moneda.setText(String.valueOf(tit.getMoneda().getCodigo()));
            nombremoneda.setText(tit.getMoneda().getNombre());
            tasa.setText(formatea.format(tit.getTasa()));
            base.setText(formatea.format(tit.getBase()));
            fechaemision.setDate(tit.getFechaemision());
            vencimiento.setDate(tit.getVencimiento());
            cupones.setText(String.valueOf(tit.getCupones()));
            String cPeriodoPago = tit.getPagointeres().toString();

            if (cPeriodoPago.toUpperCase().equals("MENSUAL")) {
                periodopago.setSelectedIndex(0);
            } else if (cPeriodoPago.toUpperCase().equals("MENSUAL")) {
                periodopago.setSelectedIndex(1);
            } else if (cPeriodoPago.toUpperCase().equals("TRIMESTRAL")) {
                periodopago.setSelectedIndex(2);
            } else if (cPeriodoPago.toUpperCase().equals("CUATRIMESTRAL")) {
                periodopago.setSelectedIndex(3);
            } else if (cPeriodoPago.toUpperCase().equals("SEMESTRAL")) {
                periodopago.setSelectedIndex(4);
            } else if (cPeriodoPago.toUpperCase().equals("ANUAL")) {
                periodopago.setSelectedIndex(5);
            } else if (cPeriodoPago.toUpperCase().equals("VENCIMIENTO")) {
                periodopago.setSelectedIndex(6);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        this.BTitulos.setVisible(false);
        this.txtbuscartitulo.setText("");
        this.moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.BTitulos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    private void nombreasesorcompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreasesorcompradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreasesorcompradorActionPerformed

    private void combovendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combovendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combovendedorActionPerformed

    private void jTBuscarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorActionPerformed

    private void jTBuscarVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarVendedorKeyPressed
        this.jTBuscarVendedor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarVendedor.getText()).toUpperCase();
                jTBuscarVendedor.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combovendedor.getSelectedIndex()) {
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
                filtrovendedor(indiceColumnaTabla);
            }
        });
        trsfiltrovendedor = new TableRowSorter(tablavendedor.getModel());
        tablavendedor.setRowSorter(trsfiltrovendedor);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorKeyPressed

    private void tablavendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablavendedorMouseClicked
        this.AceptarVendedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorMouseClicked

    private void tablavendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablavendedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarVendedor.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorKeyPressed

    private void AceptarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarVendedorActionPerformed
        int nFila = this.tablavendedor.getSelectedRow();
        if (nBuscarAsesor == 1) {
            this.asesorcomprador.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
            this.nombreasesorcomprador.setText(this.tablavendedor.getValueAt(nFila, 1).toString());
            this.asesorvendedor.requestFocus();
        } else {
            this.asesorvendedor.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
            this.nombreasesorvendedor.setText(this.tablavendedor.getValueAt(nFila, 1).toString());
            this.Grabar.requestFocus();
        }
        this.BVendedor.setVisible(false);
        this.jTBuscarVendedor.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void tipoplazoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipoplazoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cupones.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tasa.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoplazoKeyPressed

    private void periodopagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_periodopagoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.clientecomprador.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.plazo.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_periodopagoKeyPressed

    private void tipooperacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipooperacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.negociado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechacierre.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_tipooperacionKeyPressed

    private void negociadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_negociadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.mercado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipooperacion.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_negociadoKeyPressed

    private void mercadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mercadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.titulo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.negociado.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_mercadoKeyPressed

    private void plazoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_plazoFocusGained
        java.util.Date fecha1, fecha2;
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        fecha1 = this.fechacierre.getDate();
        fecha2 = this.vencimiento.getDate();
        long diferencia = (fecha2.getTime() - fecha1.getTime()) / MILLSECS_PER_DAY;
        this.plazo.setText(Long.toString(diferencia));
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoFocusGained

    private void tipooperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipooperacionActionPerformed
        if (tipooperacion.getSelectedIndex() == 0) {
            this.clientecomprador.setEnabled(true);
            this.asesorcomprador.setEnabled(true);
            this.clientevendedor.setText("0");
            this.nombreclientevendedor.setText("");
            this.clientevendedor.setEnabled(false);
            this.nombreclientevendedor.setText("");
            this.asesorvendedor.setEnabled(false);
            this.asesorvendedor.setText("0");
            this.nombreasesorvendedor.setText("");
        } else if (tipooperacion.getSelectedIndex() == 1) {
            this.clientevendedor.setEnabled(true);
            this.asesorvendedor.setEnabled(true);
            this.clientecomprador.setText("0");
            this.nombreclientecomprador.setText("");
            this.clientecomprador.setEnabled(false);
            this.nombreclientecomprador.setText("");
            this.asesorcomprador.setEnabled(false);
            this.asesorcomprador.setText("0");
            this.nombreasesorcomprador.setText("");
        } else if (tipooperacion.getSelectedIndex() == 2) {
            this.clientevendedor.setEnabled(true);
            this.asesorvendedor.setEnabled(true);
            this.clientecomprador.setEnabled(true);
            this.asesorcomprador.setEnabled(true);
        }
    }//GEN-LAST:event_tipooperacionActionPerformed

    private void tablacuponKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacuponKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            CalcularPlazoInteres(1); //SUMAR UN DIA
            ActualizarCupones(1);
        }
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            CalcularPlazoInteres(2);//RESTAR UN DIA
            ActualizarCupones(2);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacuponKeyReleased

    private void CalcularPlazoInteres(int operacion) {
        String cNombreDia = "";
        int nFila = this.tablacupon.getSelectedRow();

        String cPrecio = precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");

        String cValorNominal = valor_nominal.getText();
        cValorNominal = cValorNominal.replace(".", "").replace(",", ".");

        String cTasa = tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");
        double nTasa = Double.valueOf(cTasa);
        int nbase = 0;

        double nValorInversion = 0.00;
        try {
            vencecupon.setDate(formatoFecha.parse(this.tablacupon.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        int nplazo = 0;
        if (operacion == 1) {
            nplazo = Integer.valueOf(this.tablacupon.getValueAt(this.tablacupon.getSelectedRow(), 1).toString()) + 1;
        } else {
            nplazo = Integer.valueOf(this.tablacupon.getValueAt(this.tablacupon.getSelectedRow(), 1).toString()) - 1;
        }

        //CALCULAMOS EL NUEVO VENCIMIENTO
        this.calcularVencimiento(nplazo);
        this.tablacupon.setValueAt(String.valueOf(nplazo), this.tablacupon.getSelectedRow(), 1);
        this.tablacupon.setValueAt(formatoFecha.format(this.vencecupon.getDate()), this.tablacupon.getSelectedRow(), 3);
        this.tablacupon.setValueAt(String.valueOf(nplazo), this.tablacupon.getSelectedRow(), 1);

        //CALCULAMOS EL INTERES
        if (Integer.valueOf(base.getText()) == 1) {
            nbase = 360;
        } else {
            nbase = 365;
        }
        if (Integer.valueOf(moneda.getText()) == 1) {
            nValorInversion = Math.round(Double.valueOf(cPrecio) * Double.valueOf(cValorNominal) / 100);
            this.tablacupon.setValueAt(formatea.format(Math.round((nValorInversion * (nTasa / 100) * nplazo) / nbase)), this.tablacupon.getSelectedRow(), 4);
        } else {
            nValorInversion = Double.valueOf(cPrecio) * Double.valueOf(cValorNominal) / 100;
            this.tablacupon.setValueAt(formatea.format((nValorInversion * (nTasa / 100) * nplazo) / nbase), this.tablacupon.getSelectedRow(), 4);
        }
        //	REPLACE valorfuturo  WITH ROUND((m.importe*(interes/100)*plazocupon)/nAnual,0)
        String cdia = String.valueOf(this.vencecupon.getCalendar().get(Calendar.DAY_OF_MONTH));
        String cmes = String.valueOf(this.vencecupon.getCalendar().get(Calendar.MONTH) + 1);
        String cano = String.valueOf(this.vencecupon.getCalendar().get(Calendar.YEAR));
        if (Integer.valueOf(cmes) < 10) {
            cmes = "0" + cmes;
        }
        if (Integer.valueOf(cdia) < 10) {
            cdia = "0" + cdia;
        }

        LocalDate currenDate = LocalDate.parse(cano + "-" + cmes + "-" + cdia);
        System.out.println("DIA " + cano + "-" + cmes + "-" + cdia);
        int diaSemana = currenDate.getDayOfWeek().getValue() + 1;
        switch (diaSemana) {
            case 1:
                cNombreDia = "Domingo";
                break; // break es opcional
            case 2:
                cNombreDia = "Lunes";
                break; // break es opcional
            case 3:
                cNombreDia = "Martes";
                break; // break es opcional
            case 4:
                cNombreDia = "Míercoles";
                break; // break es opcional
            case 5:
                cNombreDia = "Jueves";
                break; // break es opcional
            case 6:
                cNombreDia = "Viernes";
                break; // break es opcional
            case 7:
                cNombreDia = "Sábado";
                break; // break es opcional
        }
        this.tablacupon.setValueAt(cNombreDia, this.tablacupon.getSelectedRow(), 5);
    }

    private void ActualizarCupones(int operacion) {
        String cPrecio = precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");

        String cValorNominal = valor_nominal.getText();
        cValorNominal = cValorNominal.replace(".", "").replace(",", ".");

        String cTasa = tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");
        double nTasa = Double.valueOf(cTasa);
        int nbase = 0;

        double nValorInversion = 0.00;
        if (Integer.valueOf(base.getText()) == 1) {
            nbase = 360;
        } else {
            nbase = 365;
        }
        int nplazo = 0;
        switch (this.periodopago.getSelectedIndex()) {
            case 0:
                nplazo = 30;
                break; // break es opcional
            case 1:
                nplazo = 60;
                break; // break es opcional
            case 2:
                nplazo = 90;
                break; // break es opcional
            case 3:
                nplazo = 120;
                break; // break es opcional
            case 4:
                nplazo = 180;
                break; // break es opcional
            case 5:
                nplazo = 365;
                break; // break es opcional
            case 6:
                nplazo = Integer.valueOf(plazo.getText());
                break; // break es opcional
        }
//      vencecupon.setDate(fechaemision.getDate());

        int cantidadRegistro = modelocupon.getRowCount();
        int nposicion = tablacupon.getSelectedRow();

        try {
            vencecupon.setDate(formatoFecha.parse(this.tablacupon.getValueAt(tablacupon.getSelectedRow(), 3).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        nposicion = nposicion + 1;

        for (int i = nposicion; i <= (cantidadRegistro); i++) {
            this.tablacupon.setValueAt(formatoFecha.format(this.vencecupon.getDate()), i, 2);
            if (operacion == 1) {
                nplazo = Integer.valueOf(this.tablacupon.getValueAt(i, 1).toString()) + 1;
            } else {
                nplazo = Integer.valueOf(this.tablacupon.getValueAt(i, 1).toString()) - 1;
            }

            //CALCULAMOS EL NUEVO VENCIMIENTO
            this.calcularVencimiento(nplazo);
            this.tablacupon.setValueAt(String.valueOf(nplazo), i, 1);
            this.tablacupon.setValueAt(formatoFecha.format(this.vencecupon.getDate()), i, 3);
            this.tablacupon.setValueAt(String.valueOf(nplazo), i, 1);

            //CALCULAMOS EL INTERES
            if (Integer.valueOf(base.getText()) == 1) {
                nbase = 360;
            } else {
                nbase = 365;
            }
            if (Integer.valueOf(moneda.getText()) == 1) {
                nValorInversion = Math.round(Double.valueOf(cPrecio) * Double.valueOf(cValorNominal) / 100);
                this.tablacupon.setValueAt(formatea.format(Math.round((nValorInversion * (nTasa / 100) * nplazo) / nbase)), i, 4);
            } else {
                nValorInversion = Double.valueOf(cPrecio) * Double.valueOf(cValorNominal) / 100;
                this.tablacupon.setValueAt(formatea.format((nValorInversion * (nTasa / 100) * nplazo) / nbase), i, 4);
            }
            //	REPLACE valorfuturo  WITH ROUND((m.importe*(interes/100)*plazocupon)/nAnual,0)
            String cdia = String.valueOf(this.vencecupon.getCalendar().get(Calendar.DAY_OF_MONTH));
            String cmes = String.valueOf(this.vencecupon.getCalendar().get(Calendar.MONTH) + 1);
            String cano = String.valueOf(this.vencecupon.getCalendar().get(Calendar.YEAR));
            if (Integer.valueOf(cmes) < 10) {
                cmes = "0" + cmes;
            }
            if (Integer.valueOf(cdia) < 10) {
                cdia = "0" + cdia;
            }
            String cNombreDia = "";
            LocalDate currenDate = LocalDate.parse(cano + "-" + cmes + "-" + cdia);
            System.out.println("DIA " + cano + "-" + cmes + "-" + cdia);
            int diaSemana = currenDate.getDayOfWeek().getValue() + 1;
            switch (diaSemana) {
                case 1:
                    cNombreDia = "Domingo";
                    break; // break es opcional
                case 2:
                    cNombreDia = "Lunes";
                    break; // break es opcional
                case 3:
                    cNombreDia = "Martes";
                    break; // break es opcional
                case 4:
                    cNombreDia = "Míercoles";
                    break; // break es opcional
                case 5:
                    cNombreDia = "Jueves";
                    break; // break es opcional
                case 6:
                    cNombreDia = "Viernes";
                    break; // break es opcional
                case 7:
                    cNombreDia = "Sábado";
                    break; // break es opcional
            }
            this.tablacupon.setValueAt(cNombreDia, i, 5);

            try {
                vencecupon.setDate(formatoFecha.parse(this.tablacupon.getValueAt(i, 3).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }


    private void GenerarCuponesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarCuponesActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Generar Cupones Predefinidos? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GenerarCuponAutomaticamente();
        } else {
            GenerarCuponPagos();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_GenerarCuponesActionPerformed
    private void LimpiarDesglose() {
        this.cantidadTitulo.setText("0");
        this.importeTitulo.setText("0");
        this.serie.setText("0");
        this.nrotitulo.setText("0");
        this.desde_acci.setText("0");
        this.hasta_acci.setText("0");
        this.iditem.setText("0");
    }

    private void GrabarDesgloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarDesgloseActionPerformed
        if (this.cantidadTitulo.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cantidad");
            this.cantidadTitulo.requestFocus();
            return;
        }
        String cImporteTitulo = importeTitulo.getText();
        cImporteTitulo = cImporteTitulo.replace(".", "").replace(",", ".");
        if (Double.valueOf(cImporteTitulo) == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Importe");
            this.importeTitulo.requestFocus();
            return;
        }
        if (this.serie.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Serie");
            this.serie.requestFocus();
            return;
        }
        if (this.nrotitulo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Título");
            this.nrotitulo.requestFocus();
            return;
        }
        if (this.creferencia.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Aún no puede Cargar Desglose");
            this.cantidadTitulo.requestFocus();
            return;
        }
        if (this.desde_acci.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° Inicial");
            this.desde_acci.requestFocus();
            return;
        }

        if (this.hasta_acci.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° Final");
            this.hasta_acci.requestFocus();
            return;
        }

        desgloseDAO grabar = new desgloseDAO();
        desglose des = new desglose();

        des.setCantidad(Integer.valueOf(cantidadTitulo.getText()));
        des.setImporte(Double.valueOf(cImporteTitulo));
        des.setSerie(this.serie.getText());
        des.setNro_titulo(this.nrotitulo.getText());

        String cDesdeAcci = desde_acci.getText();
        cDesdeAcci = cDesdeAcci.replace(".", "");
        des.setDesde_acci(Integer.valueOf(cDesdeAcci));

        String cHastaAcci = hasta_acci.getText();
        cHastaAcci = cHastaAcci.replace(".", "");
        des.setHasta_acci(Integer.valueOf(cHastaAcci));

        des.setCod_compra(Integer.valueOf(clientecomprador.getText()));
        des.setCod_vende(Integer.valueOf(clientevendedor.getText()));
        des.setIddesglose(creferencia.getText());
        try {
            if (Integer.valueOf(this.iditem.getText()) == 0) {
                grabar.insertarDesglose(des);
            } else {
                des.setIditem(Double.valueOf(iditem.getText()));
                grabar.actualizarDesglose(des);
            }
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        CargarDesglose();
        GenerarCartera();
        LimpiarDesglose();
        this.cantidadTitulo.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarDesgloseActionPerformed

    private void tabladesgloseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabladesgloseKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            int nFila = this.tabladesglose.getSelectedRow();
            this.cantidadTitulo.setText(this.tabladesglose.getValueAt(nFila, 0).toString());
            this.importeTitulo.setText(this.tabladesglose.getValueAt(nFila, 1).toString());
            this.serie.setText(this.tabladesglose.getValueAt(nFila, 2).toString());
            this.nrotitulo.setText(this.tabladesglose.getValueAt(nFila, 3).toString());
            this.desde_acci.setText(this.tabladesglose.getValueAt(nFila, 4).toString());
            this.hasta_acci.setText(this.tabladesglose.getValueAt(nFila, 5).toString());
            this.iditem.setText(this.tabladesglose.getValueAt(nFila, 6).toString());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladesgloseKeyReleased

    private void tabladesgloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladesgloseMousePressed
        int nFila = this.tabladesglose.getSelectedRow();
        this.cantidadTitulo.setText(this.tabladesglose.getValueAt(nFila, 0).toString());
        this.importeTitulo.setText(this.tabladesglose.getValueAt(nFila, 1).toString());
        this.serie.setText(this.tabladesglose.getValueAt(nFila, 2).toString());
        this.nrotitulo.setText(this.tabladesglose.getValueAt(nFila, 3).toString());
        this.desde_acci.setText(this.tabladesglose.getValueAt(nFila, 4).toString());
        this.hasta_acci.setText(this.tabladesglose.getValueAt(nFila, 5).toString());
        this.iditem.setText(this.tabladesglose.getValueAt(nFila, 6).toString());        // TODO add your handling code here:
    }//GEN-LAST:event_tabladesgloseMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.LimpiarDesglose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void BorrarDesgloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarDesgloseActionPerformed
        int a = this.tabladesglose.getSelectedRow();
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            desgloseDAO borrar = new desgloseDAO();
            cartera_clientesDAO borrarCartera = new cartera_clientesDAO();
            String cItemBorrar = this.tabladesglose.getValueAt(a, 6).toString();
            try {
                borrar.eliminarDesglose(Double.valueOf(cItemBorrar));
                borrarCartera.borrarCartera(Double.valueOf(cItemBorrar));
                JOptionPane.showMessageDialog(null, "Registro Eliminado Exitósamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            CargarDesglose();
            LimpiarDesglose();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarDesgloseActionPerformed

    private void ListarCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarCertificadoActionPerformed

        int nFila = tablaprincipal.getSelectedRow();
        cartera_clientesDAO caDAO = new cartera_clientesDAO();
        cartera_clientes ca = null;

        try {
            ca = caDAO.VerificarCertificado(Double.valueOf(tablaprincipal.getValueAt(nFila, 1).toString()));
            if (ca.getNrocertificado() == 0) {
                caDAO.CrearNumeroCertificado(Double.valueOf(tablaprincipal.getValueAt(nFila, 1).toString()));
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();

            String num = tablaprincipal.getValueAt(nFila, 9).toString();

            String cValorNominal = tablaprincipal.getValueAt(nFila, 9).toString();
            num = num.replace(".", "").replace(",", ".");
            numero_a_letras numero = new numero_a_letras();

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("Letra", numero.Convertir(num, true, Integer.valueOf(tablaprincipal.getValueAt(nFila, 19).toString())));
            parameters.put("nNumero", tablaprincipal.getValueAt(nFila, 1).toString());
            parameters.put("cValorNominal", cValorNominal);

            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            JasperReport jr = null;

            URL url = getClass().getClassLoader().getResource("Reports/certificado_liquidacion.jasper");
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos

            masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
            JasperPrintManager.printReport(masterPrint, false);

        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(null, "No puede emitirse el Reporte");
            System.out.println(e);
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_ListarCertificadoActionPerformed

    private void buscarcuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcuentaActionPerformed
        GrillaBancos grillabanco = new GrillaBancos();
        Thread hilobanco = new Thread(grillabanco);
        hilobanco.start();
        BBancos.setModal(true);
        BBancos.setSize(500, 575);
        BBancos.setLocationRelativeTo(null);
        BBancos.setVisible(true);
        BBancos.setTitle("Buscar Cuenta");
        BBancos.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcuentaActionPerformed

    private void tasaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tasaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaKeyReleased

    private void cuponesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cuponesKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.clientecomprador.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipoplazo.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_cuponesKeyPressed

    private void SalircompensacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalircompensacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SalircompensacionActionPerformed

    private void dFechaInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dFechaInicialKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialKeyPressed

    private void GenerarComisionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarComisionesActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Generar Comisiones? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            comisionDAO comDAO = new comisionDAO();
            try {
                comDAO.eliminarComision(this.creferencia.getText());
                if (Integer.valueOf(moneda.getText()) == 1) {
                    //ENVIAMOS LOS PARAMETROS PARA CALCULAR LA COMISION DE LA PUNTA COMPRADORA
                    comDAO.CalcularComision(this.creferencia.getText(), 1, Integer.valueOf(moneda.getText()), Integer.valueOf(asesorcomprador.getText()));
                } else {
                    //ENVIAMOS LOS PARAMETROS PARA CALCULAR LA COMISION DE LA PUNTA VENDEDORA
                    comDAO.CalcularComision(this.creferencia.getText(), 2, Integer.valueOf(moneda.getText()), Integer.valueOf(asesorvendedor.getText()));
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.CargarComision(creferencia.getText());

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GenerarComisionesActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Date dFechaInicio = ODate.de_java_a_sql(this.dFechaInicial.getDate());
        Date dFechaFinal = ODate.de_java_a_sql(this.dFechaFinal.getDate());
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("FechaInicio", dFechaInicio);
            parameters.put("FechaFinal", dFechaFinal);
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/resumen_operaciones_renta_fija.jasper");
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed


        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    public void GenerarCuponAutomaticamente() {
        int cantidadRegistro = modelocupon.getRowCount();
        Date FechaVence = ODate.de_java_a_sql(fechacierre.getDate());

        String cNombreDia = "SD";
        String cPrecio = precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");

        String cValorNominal = valor_nominal.getText();
        cValorNominal = cValorNominal.replace(".", "").replace(",", ".");

        String cTasa = tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");
        double nTasa = Double.valueOf(cTasa);
        double nValorCupon = 0.00;
        int nbase = 0;

        double nValorInversion = 0.00;
        if (Integer.valueOf(base.getText()) == 1) {
            nbase = 360;
        } else {
            nbase = 365;
        }

        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }
        detalle_cupones_titulosDAO detDAO = new detalle_cupones_titulosDAO();
        try {
            for (detalle_cupones_titulos detvta : detDAO.MostrarxTituloActivos(Integer.valueOf(titulo.getText()), FechaVence)) {
                LocalDate currenDate = LocalDate.parse(detvta.getFechavencimiento().toString());
                System.out.println("DIA " + detvta.getFechavencimiento().toString());
                int diaSemana = currenDate.getDayOfWeek().getValue() + 1;
                switch (diaSemana) {
                    case 1:
                        cNombreDia = "Domingo";
                        break; // break es opcional
                    case 2:
                        cNombreDia = "Lunes";
                        break; // break es opcional
                    case 3:
                        cNombreDia = "Martes";
                        break; // break es opcional
                    case 4:
                        cNombreDia = "Míercoles";
                        break; // break es opcional
                    case 5:
                        cNombreDia = "Jueves";
                        break; // break es opcional
                    case 6:
                        cNombreDia = "Viernes";
                        break; // break es opcional
                    case 7:
                        cNombreDia = "Sábado";
                        break; // break es opcional
                }

                if (Integer.valueOf(moneda.getText()) == 1) {
                    nValorInversion = Math.round(Double.valueOf(cValorNominal));
                    nValorCupon = Math.round((nValorInversion * (nTasa / 100) * detvta.getPlazo()) / nbase);
                } else {
                    nValorInversion = Double.valueOf(cValorNominal);
                    nValorCupon = (nValorInversion * (nTasa / 100) * detvta.getPlazo()) / nbase;
                }
                String Detalle[] = {String.valueOf(detvta.getNumerocupon()), formatea.format(detvta.getPlazo()), formatoFecha.format(detvta.getFechainicio()), formatoFecha.format(detvta.getFechavencimiento()), formatea.format(nValorCupon), cNombreDia};
                modelocupon.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

    }

    private void GenerarCuponPagos() {
        int cantidadRegistro = modelocupon.getRowCount();
        int nCantidadCupones = Integer.valueOf(cupones.getText());
        String cNombreDia = "SD";
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }

        String cPrecio = precio.getText();
        cPrecio = cPrecio.replace(".", "").replace(",", ".");

        String cValorNominal = valor_nominal.getText();
        cValorNominal = cValorNominal.replace(".", "").replace(",", ".");

        String cTasa = tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");
        double nTasa = Double.valueOf(cTasa);
        int nbase = 0;

        double nValorInversion = 0.00;
        if (Integer.valueOf(base.getText()) == 1) {
            nbase = 360;
        } else {
            nbase = 365;
        }
        int nplazo = 0;
        switch (this.periodopago.getSelectedIndex()) {
            case 0:
                nplazo = 30;
                break; // break es opcional
            case 1:
                nplazo = 60;
                break; // break es opcional
            case 2:
                nplazo = 90;
                break; // break es opcional
            case 3:
                nplazo = 120;
                break; // break es opcional
            case 4:
                nplazo = 180;
                break; // break es opcional
            case 5:
                nplazo = 365;
                break; // break es opcional
            case 6:
                nplazo = Integer.valueOf(plazo.getText());
                break; // break es opcional
        }
        vencecupon.setDate(fechaemision.getDate());
        for (int i = 1; i <= (nCantidadCupones); i++) {
            Object[] fila = new Object[6]; // Hay 8   columnas en la tabla
            fila[0] = i;
            fila[1] = formatea.format(nplazo);
            fila[2] = formatoFecha.format(vencecupon.getDate());
            this.calcularVencimiento(nplazo);
            fila[3] = formatoFecha.format(vencecupon.getDate());
            String cdia = String.valueOf(this.vencecupon.getCalendar().get(Calendar.DAY_OF_MONTH));
            String cmes = String.valueOf(this.vencecupon.getCalendar().get(Calendar.MONTH) + 1);
            String cano = String.valueOf(this.vencecupon.getCalendar().get(Calendar.YEAR));
            if (Integer.valueOf(cmes) < 10) {
                cmes = "0" + cmes;
            }
            if (Integer.valueOf(cdia) < 10) {
                cdia = "0" + cdia;
            }
            LocalDate currenDate = LocalDate.parse(cano + "-" + cmes + "-" + cdia);
            int diaSemana = currenDate.getDayOfWeek().getValue() + 1;
            switch (diaSemana) {
                case 1:
                    cNombreDia = "Domingo";
                    break; // break es opcional
                case 2:
                    cNombreDia = "Lunes";
                    break; // break es opcional
                case 3:
                    cNombreDia = "Martes";
                    break; // break es opcional
                case 4:
                    cNombreDia = "Míercoles";
                    break; // break es opcional
                case 5:
                    cNombreDia = "Jueves";
                    break; // break es opcional
                case 6:
                    cNombreDia = "Viernes";
                    break; // break es opcional
                case 7:
                    cNombreDia = "Sábado";
                    break; // break es opcional
            }

            if (Integer.valueOf(moneda.getText()) == 1) {
                nValorInversion = Math.round(Double.valueOf(cValorNominal));
                fila[4] = formatea.format(Math.round((nValorInversion * (nTasa / 100) * nplazo) / nbase));
            } else {
                nValorInversion = Double.valueOf(cValorNominal);
                fila[4] = formatea.format((nValorInversion * (nTasa / 100) * nplazo) / nbase);
            }
            fila[5] = cNombreDia;
            modelocupon.addRow(fila);
        }

    }

    private void calcularVencimiento(int dias) {
        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.vencecupon.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        this.vencecupon.setDate(calendar.getTime()); //Y cargamos
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(buscarcadena.getText(), nNumeroColumna));
    }

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.txtbuscarbanco.getText(), nNumeroColumna));
    }

    public void filtrotitulo(int nNumeroColumna) {
        trsfiltrotitulo.setRowFilter(RowFilter.regexFilter(this.txtbuscartitulo.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Ref");//0
        modelo.addColumn("Número");//01
        modelo.addColumn("Fecha");//02
        modelo.addColumn("Emisión");//03
        modelo.addColumn("Vence");//04
        modelo.addColumn("Emisor");//05
        modelo.addColumn("Título");//06
        modelo.addColumn("Moneda");//07
        modelo.addColumn("Mercado");//08
        modelo.addColumn("Valor Nominal");//09
        modelo.addColumn("Cantidad");//10
        modelo.addColumn("Precio");//11
        modelo.addColumn("Valor Inversión");//12
        modelo.addColumn("Tasa");//13
        modelo.addColumn("Plazo");//14
        modelo.addColumn("Cliente Comprador");//15
        modelo.addColumn("Cliente Vendedor");//16
        modelo.addColumn("Asesor Comprador");//17
        modelo.addColumn("Asesor Vendedor");//18
        modelo.addColumn("Moneda");//19
        modelo.addColumn("Nominal");//20

        int[] anchos = {1, 100, 90, 90, 90, 150, 150, 150, 150, 150, 90, 90, 150, 50, 50, 200, 200, 200, 200, 1, 1};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tablaprincipal.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaprincipal.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaprincipal.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablaprincipal.setFont(font);

        this.tablaprincipal.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaprincipal.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablaprincipal.getColumnModel().getColumn(19).setMaxWidth(0);
        this.tablaprincipal.getColumnModel().getColumn(19).setMinWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(19).setMaxWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(19).setMinWidth(0);

        this.tablaprincipal.getColumnModel().getColumn(20).setMaxWidth(0);
        this.tablaprincipal.getColumnModel().getColumn(20).setMinWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(20).setMaxWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(20).setMinWidth(0);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();

        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.tablaprincipal.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.tablaprincipal.getColumnModel().getColumn(3).setCellRenderer(AlinearCentro);
        this.tablaprincipal.getColumnModel().getColumn(4).setCellRenderer(AlinearCentro);
        this.tablaprincipal.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(13).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
    }

    private void Titbancos() {
        modelobanco.addColumn("Código");
        modelobanco.addColumn("Nombre Banco");
        modelobanco.addColumn("N° de Cuenta");

        int[] anchos = {90, 150, 100};
        for (int i = 0; i < modelobanco.getColumnCount(); i++) {
            tablabancos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablabancos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablabancos.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablabancos.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablabancos.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);

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
                new operaciones_renta_fija().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarBanco;
    private javax.swing.JButton AceptarCasa;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JButton Agregar;
    private javax.swing.JDialog BBancos;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BTitulos;
    private javax.swing.JDialog BVendedor;
    private javax.swing.JButton BorrarDesglose;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton GenerarComisiones;
    private javax.swing.JButton GenerarCupones;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton GrabarCompensacion;
    private javax.swing.JButton GrabarDesglose;
    private javax.swing.JButton Listar;
    private javax.swing.JButton ListarCertificado;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirBanco;
    private javax.swing.JButton SalirCasa;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCompleto;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JButton Salircompensacion;
    private javax.swing.JLabel Socio1;
    private javax.swing.JCheckBox arancelcompra;
    private javax.swing.JCheckBox arancelventa;
    private javax.swing.JTextField asesorcomprador;
    private javax.swing.JTextField asesorvendedor;
    private javax.swing.JTextField banco;
    private javax.swing.JTextField base;
    private javax.swing.JButton buscarasesorcomprador;
    private javax.swing.JButton buscarasesorvendedor;
    private javax.swing.JButton buscarbco;
    private javax.swing.JTextField buscarcadena;
    private javax.swing.JButton buscarclientecomprador;
    private javax.swing.JButton buscarclientevendedor;
    private javax.swing.JButton buscarcuenta;
    private javax.swing.JButton buscarmoneda;
    private javax.swing.JButton buscartitulo;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JFormattedTextField cantidadTitulo;
    private javax.swing.JTextField clientecomprador;
    private javax.swing.JTextField clientevendedor;
    private javax.swing.JTextField codbanco;
    private javax.swing.JTextField codcomprador;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox combotitulo;
    private javax.swing.JComboBox combovendedor;
    private javax.swing.JFormattedTextField comcomprador;
    private javax.swing.JDialog compensacion;
    private javax.swing.JFormattedTextField comvendedor;
    private javax.swing.JTextField creferencia;
    private javax.swing.JTextField cuentapago;
    private javax.swing.JTextField cupones;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JFormattedTextField desde_acci;
    private javax.swing.JDialog detalle_operacion;
    private javax.swing.JTextField emisor;
    private org.edisoncor.gui.label.LabelMetric etiquetavacaciones;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechacierre;
    private com.toedter.calendar.JDateChooser fechaemision;
    private javax.swing.JFormattedTextField hasta_acci;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField iditem;
    private javax.swing.JFormattedTextField importeTitulo;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarVendedor;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> mercado;
    private javax.swing.JTextField moneda;
    private javax.swing.JComboBox<String> negociado;
    private javax.swing.JTextField nombanco;
    private javax.swing.JTextField nombreasesorcomprador;
    private javax.swing.JTextField nombreasesorvendedor;
    private javax.swing.JTextField nombrebanco;
    private javax.swing.JTextField nombreclientecomprador;
    private javax.swing.JTextField nombreclientevendedor;
    private javax.swing.JTextField nombreemisor;
    private javax.swing.JLabel nombremoneda;
    private javax.swing.JLabel nombretitulo;
    private javax.swing.JTextField nomcomprador;
    private javax.swing.JTextField nrotitulo;
    private javax.swing.JTextField numero;
    private javax.swing.JTextField numerobolsa;
    private javax.swing.JTextField numprecierre;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JComboBox<String> periodopago;
    private javax.swing.JFormattedTextField plazo;
    private javax.swing.JFormattedTextField precio;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField serie;
    private javax.swing.JTable tablabancos;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacomision;
    private javax.swing.JTable tablacupon;
    private javax.swing.JTable tabladesglose;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablaprincipal;
    private javax.swing.JTable tablatitulo;
    private javax.swing.JTable tablavendedor;
    private javax.swing.JFormattedTextField tasa;
    private javax.swing.JComboBox<String> tipooperacion;
    private javax.swing.JComboBox<String> tipoplazo;
    private javax.swing.JTextField titulo;
    private javax.swing.JTextField txtbuscarbanco;
    private javax.swing.JTextField txtbuscartitulo;
    private javax.swing.JFormattedTextField valor_nominal;
    private com.toedter.calendar.JDateChooser vencecupon;
    private com.toedter.calendar.JDateChooser vencimiento;
    // End of variables declaration//GEN-END:variables

    private class GrillaRentaFija extends Thread {

        public void run() {
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            Date FechaF = ODate.de_java_a_sql(dFechaFinal.getDate());
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            precierre_operacionDAO DAO = new precierre_operacionDAO();
            try {
                String cMercado = "";
                for (precierre_operacion vac : DAO.MostrarxFechaRentaFija(FechaI, FechaF)) {
                    if (vac.getMercado() == 1) {
                        cMercado = "PRIMARIO";
                    } else {
                        cMercado = "SECUNDARIO";
                    }
                    String Datos[] = {vac.getCreferencia(),
                        formatosinpunto.format(vac.getNumero()),
                        formatoFecha.format(vac.getFechacierre()),
                        formatoFecha.format(vac.getFechaemision()),
                        formatoFecha.format(vac.getVencimiento()),
                        vac.getEmisor().getNombre(),
                        vac.getTitulo().getNombre(),
                        vac.getMoneda().getNombre(),
                        cMercado.toString(),
                        formatea.format(vac.getValor_nominal()),
                        formatea.format(vac.getCantidad()),
                        formatodecimal.format(vac.getPrecio()),
                        formatea.format(vac.getValor_inversion()),
                        formatea.format(vac.getTasa()),
                        formatea.format(vac.getPlazo()),
                        vac.getNombrecomprador(),
                        vac.getNombrevendedor(),
                        vac.getNombre_asesor_compra(),
                        vac.getNombre_asesor_venta(),
                        String.valueOf(vac.getMoneda().getCodigo()),
                        formatosinpunto.format(vac.getTitulo().getNominal())};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablaprincipal.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = tablaprincipal.getRowCount();
            if (cantFilas > 0) {
                Modificar.setEnabled(true);
                Eliminar.setEnabled(true);
                Listar.setEnabled(true);
                ListarCertificado.setEnabled(true);
            } else {
                Modificar.setEnabled(false);
                Eliminar.setEnabled(false);
                Listar.setEnabled(false);
                ListarCertificado.setEnabled(false);
            }
        }
    }

    private class GrillaBancos extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int nmoneda = Integer.valueOf(moneda.getText());
            int ncliente = Integer.valueOf(clientecomprador.getText());
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }

            ctacteclienteDAO DAOFICHA = new ctacteclienteDAO();
            try {
                for (ctactecliente ficha : DAOFICHA.todosxClienteMoneda(ncliente, nmoneda)) {
                    String Datos[] = {formatosinpunto.format(ficha.getBancos().getCodigo()), ficha.getBancos().getNombre(), ficha.getNrocuenta()};
                    modelobanco.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablabancos.setRowSorter(new TableRowSorter(modelobanco));
            int cantFilas = tablabancos.getRowCount();
        }
    }

    private class GrillaTitulos extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelotitulo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelotitulo.removeRow(0);
            }

            tituloDAO titDAO = new tituloDAO();
            try {
                for (titulo tit : titDAO.todoOperaciones(1)) {
                    String Datos[] = {String.valueOf(tit.getCodigo()), tit.getNomalias(), tit.getEmpresa().getNombre()};
                    modelotitulo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablatitulo.setRowSorter(new TableRowSorter(modelotitulo));
            int cantFilas = tablatitulo.getRowCount();
        }
    }

    private class GrillaMoneda extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }
            monedaDAO DAOCASA = new monedaDAO();
            try {
                for (moneda ca : DAOCASA.todos()) {
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
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class GrillaVendedor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelovendedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelovendedor.removeRow(0);
            }
            vendedorDAO DAOve = new vendedorDAO();
            try {
                for (vendedor ve : DAOve.todosActivos()) {
                    String Datos[] = {String.valueOf(ve.getCodigo()), ve.getNombre()};
                    modelovendedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablavendedor.setRowSorter(new TableRowSorter(modelovendedor));
            int cantFilas = tablavendedor.getRowCount();
        }
    }

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
}

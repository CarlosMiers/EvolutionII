package Vista;

import Clases.Config;
import Clases.ConvertirMayusculas;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.detalle_cupones_titulosDAO;
import DAO.emisorDAO;
import DAO.instrumentoDAO;
import DAO.monedaDAO;
import DAO.paisDAO;
import DAO.rubro_emisorDAO;
import DAO.tituloDAO;
import Modelo.Tablas;
import Modelo.detalle_cupones_titulos;
import Modelo.emisor;
import Modelo.instrumento;
import Modelo.moneda;
import Modelo.pais;
import Modelo.rubro_emisor;
import Modelo.titulo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 * @author FedeXavier
 */
public class titulos extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelopaises = new Tablas();
    Tablas modelorubros = new Tablas();
    Tablas modelocupon = new Tablas();
    Tablas modelosucursal = new Tablas();
    Date dEmision;
    Date dVence;

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltropais, trsfiltroemisor,
            trsfiltromoneda, trsfiltrosuc;
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ObtenerFecha ODate = new ObtenerFecha();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("###");

    /**
     * Creates new form Template
     */
    public titulos() {
        initComponents();
        this.GrabarCupon.setIcon(iconograbar);
        this.SalirCupon.setIcon(iconosalir);
        this.BotonAgregar.setIcon(icononuevo);
        this.BotonEditar.setIcon(iconoeditar);
        this.BuscarEmisor.setIcon(iconobuscar);
        this.BuscarNacionalidad.setIcon(iconobuscar);
        this.BuscarMoneda.setIcon(iconobuscar);
        this.BuscarIntrumento.setIcon(iconobuscar);
        this.BotonDelete.setIcon(iconoborrar);
        this.BotonPrint.setIcon(iconoprint);
        this.BotonClose.setIcon(iconosalir);
        this.BotonGrabar.setIcon(iconograbar);
        this.BotonSalir.setIcon(iconosalir);
        this.tablatitulos.setShowGrid(false);
        this.tablatitulos.setOpaque(true);
        this.tablatitulos.setBackground(new Color(102, 204, 255));
        this.tablatitulos.setForeground(Color.BLACK);
        this.vencecupon.setVisible(false);
        this.codtitulo.setVisible(false);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.jTextOpciones1.setVisible(false);
        this.Inicializar();
        this.cargarTitulo();
        this.TitPaises();
        this.TitRubros();
        this.TitMoneda();
        this.TituloCupon();
        this.TitInstrumento();

        GrillaTitulos GrillaOC = new GrillaTitulos();
        Thread HiloGrilla = new Thread(GrillaOC);
        HiloGrilla.start();

        GrillaPaises grillapa = new GrillaPaises();
        Thread hilodo = new Thread(grillapa);
        hilodo.start();

        GrillaRubros grillaru = new GrillaRubros();
        Thread hiloru = new Thread(grillaru);
        hiloru.start();

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

        detalle_titulos = new javax.swing.JDialog();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        codigo = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        nomalias = new javax.swing.JTextField();
        programa = new javax.swing.JTextField();
        resbvpasa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pais = new javax.swing.JTextField();
        BuscarNacionalidad = new javax.swing.JButton();
        nombrenacionalidad = new javax.swing.JTextField();
        estado = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        rescnv = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        emisor = new javax.swing.JTextField();
        BuscarEmisor = new javax.swing.JButton();
        nombreemisor = new javax.swing.JTextField();
        tipo = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        negociable = new javax.swing.JComboBox<>();
        fechacnv = new com.toedter.calendar.JDateChooser();
        fechabvpasa = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        BuscarMoneda = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        instrumento = new javax.swing.JTextField();
        BuscarIntrumento = new javax.swing.JButton();
        nombreinstrumento = new javax.swing.JTextField();
        panel3 = new org.edisoncor.gui.panel.Panel();
        BotonGrabar = new javax.swing.JButton();
        BotonSalir = new javax.swing.JButton();
        Bpaises = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combopais = new javax.swing.JComboBox<>();
        jTBuscarPais = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablapaises = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarPais = new javax.swing.JButton();
        SalirPais = new javax.swing.JButton();
        BEmisor = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        comboemisor = new javax.swing.JComboBox<>();
        jTBuscarEmisor = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaemisor = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        AceptarRubro = new javax.swing.JButton();
        SalirRubro = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        BInstrumento = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        cupones = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        monto_emision = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        corte_minimo = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        emision = new com.toedter.calendar.JDateChooser();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tasa = new javax.swing.JFormattedTextField();
        base = new javax.swing.JComboBox<>();
        pagointeres = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cantidadcupones = new javax.swing.JFormattedTextField();
        codtitulo = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        SalirCupon = new javax.swing.JButton();
        GrabarCupon = new javax.swing.JButton();
        vencecupon = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablacupon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        BotonEditar = new javax.swing.JButton();
        BotonAgregar = new javax.swing.JButton();
        BotonDelete = new javax.swing.JButton();
        BotonPrint = new javax.swing.JButton();
        BotonClose = new javax.swing.JButton();
        jTextOpciones1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablatitulos = new javax.swing.JTable();
        panel2 = new org.edisoncor.gui.panel.Panel();
        etiquetasucursal = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        detalle_titulos.setTitle("Actualizar Título");
        detalle_titulos.setName("detalle_titulos"); // NOI18N
        detalle_titulos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                detalle_titulosFocusGained(evt);
            }
        });
        detalle_titulos.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                detalle_titulosWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        detalle_titulos.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                detalle_titulosWindowActivated(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setForeground(new java.awt.Color(255, 0, 0));
        panel1.setToolTipText("");
        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));
        panel1.setName("panel1"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ingresar Título");
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(250, 250, 250))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setName("jPanel1"); // NOI18N

        codigo.setEditable(false);
        codigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codigo.setEnabled(false);
        codigo.setName("codigo"); // NOI18N

        nombre.setName("nombre"); // NOI18N
        nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombreFocusLost(evt);
            }
        });
        nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreKeyPressed(evt);
            }
        });

        nomalias.setName("nomalias"); // NOI18N
        nomalias.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nomaliasFocusLost(evt);
            }
        });
        nomalias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nomaliasKeyPressed(evt);
            }
        });

        programa.setName("programa"); // NOI18N
        programa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                programaKeyPressed(evt);
            }
        });

        resbvpasa.setName("resbvpasa"); // NOI18N
        resbvpasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                resbvpasaKeyPressed(evt);
            }
        });

        jLabel7.setText("Código");
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel9.setText("Nombre");
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setText("Serie");
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel11.setText("Programa");
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setText("Res. BVPASA");
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel3.setText("País de Origen");
        jLabel3.setName("jLabel3"); // NOI18N

        pais.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pais.setName("pais"); // NOI18N
        pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paisActionPerformed(evt);
            }
        });
        pais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                paisKeyPressed(evt);
            }
        });

        BuscarNacionalidad.setName("BuscarNacionalidad"); // NOI18N
        BuscarNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarNacionalidadActionPerformed(evt);
            }
        });

        nombrenacionalidad.setEditable(false);
        nombrenacionalidad.setName("nombrenacionalidad"); // NOI18N

        estado.setText("Estado");
        estado.setName("estado"); // NOI18N

        jLabel4.setText("Res. CNV");
        jLabel4.setName("jLabel4"); // NOI18N

        rescnv.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rescnv.setName("rescnv"); // NOI18N
        rescnv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rescnvKeyPressed(evt);
            }
        });

        jLabel6.setText("Fecha Res, CNV");
        jLabel6.setName("jLabel6"); // NOI18N

        emisor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        emisor.setName("emisor"); // NOI18N
        emisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emisorActionPerformed(evt);
            }
        });
        emisor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emisorKeyPressed(evt);
            }
        });

        BuscarEmisor.setName("BuscarEmisor"); // NOI18N
        BuscarEmisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarEmisorActionPerformed(evt);
            }
        });

        nombreemisor.setEditable(false);
        nombreemisor.setName("nombreemisor"); // NOI18N

        tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RENTA FIJA", "RENTA VARIABLE", "FONDOS DE INVERSION" }));
        tipo.setName("tipo"); // NOI18N
        tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoActionPerformed(evt);
            }
        });
        tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipoKeyPressed(evt);
            }
        });

        jLabel14.setText("Rentabilidad");
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText("Negociable");
        jLabel15.setName("jLabel15"); // NOI18N

        negociable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BVPASA", "NO BVPASA" }));
        negociable.setName("negociable"); // NOI18N
        negociable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negociableActionPerformed(evt);
            }
        });
        negociable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                negociableKeyPressed(evt);
            }
        });

        fechacnv.setName("fechacnv"); // NOI18N
        fechacnv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechacnvKeyPressed(evt);
            }
        });

        fechabvpasa.setName("fechabvpasa"); // NOI18N
        fechabvpasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechabvpasaKeyPressed(evt);
            }
        });

        jLabel16.setText("Fecha Res. BVPASA");
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setText("Sociedad Emisora");
        jLabel17.setName("jLabel17"); // NOI18N

        jLabel8.setText("Moneda");
        jLabel8.setName("jLabel8"); // NOI18N

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        moneda.setName("moneda"); // NOI18N
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

        BuscarMoneda.setName("BuscarMoneda"); // NOI18N
        BuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarMonedaActionPerformed(evt);
            }
        });

        nombremoneda.setEditable(false);
        nombremoneda.setName("nombremoneda"); // NOI18N

        jLabel18.setText("Tipo Instrumento");
        jLabel18.setName("jLabel18"); // NOI18N

        instrumento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        instrumento.setName("instrumento"); // NOI18N
        instrumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instrumentoActionPerformed(evt);
            }
        });
        instrumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                instrumentoKeyPressed(evt);
            }
        });

        BuscarIntrumento.setName("BuscarIntrumento"); // NOI18N
        BuscarIntrumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarIntrumentoActionPerformed(evt);
            }
        });

        nombreinstrumento.setEditable(false);
        nombreinstrumento.setName("nombreinstrumento"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(instrumento, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarIntrumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreinstrumento, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fechacnv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(emisor, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BuscarEmisor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreemisor, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fechabvpasa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pais, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarNacionalidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarMoneda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrenacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resbvpasa, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(rescnv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                        .addComponent(programa, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nomalias, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(estado)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(negociable, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nomalias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(programa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(rescnv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fechacnv, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resbvpasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechabvpasa, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(emisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17))
                    .addComponent(BuscarEmisor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreemisor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrenacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(instrumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BuscarIntrumento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombreinstrumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(negociable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(estado)
                .addGap(86, 86, 86))
        );

        panel3.setBackground(new java.awt.Color(255, 255, 255));
        panel3.setForeground(new java.awt.Color(255, 0, 0));
        panel3.setToolTipText("");
        panel3.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel3.setColorSecundario(new java.awt.Color(0, 204, 255));
        panel3.setName("panel3"); // NOI18N

        BotonGrabar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BotonGrabar.setText("Grabar");
        BotonGrabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonGrabar.setName("BotonGrabar"); // NOI18N
        BotonGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGrabarActionPerformed(evt);
            }
        });

        BotonSalir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BotonSalir.setText("Salir");
        BotonSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSalir.setName("BotonSalir"); // NOI18N
        BotonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(BotonGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addComponent(BotonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonGrabar)
                    .addComponent(BotonSalir))
                .addContainerGap())
        );

        javax.swing.GroupLayout detalle_titulosLayout = new javax.swing.GroupLayout(detalle_titulos.getContentPane());
        detalle_titulos.getContentPane().setLayout(detalle_titulosLayout);
        detalle_titulosLayout.setHorizontalGroup(
            detalle_titulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(detalle_titulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detalle_titulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        detalle_titulosLayout.setVerticalGroup(
            detalle_titulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_titulosLayout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Bpaises.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Bpaises.setTitle("Buscar Pais");
        Bpaises.setName("Bpaises"); // NOI18N

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel19.setName("jPanel19"); // NOI18N

        combopais.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combopais.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combopais.setName("combopais"); // NOI18N

        jTBuscarPais.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTBuscarPais.setName("jTBuscarPais"); // NOI18N
        jTBuscarPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPaisKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combopais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jTBuscarPais, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combopais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        tablapaises.setModel(modelopaises);
        tablapaises.setName("tablapaises"); // NOI18N
        tablapaises.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablapaisesMouseClicked(evt);
            }
        });
        tablapaises.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablapaisesKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablapaises);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel20.setName("jPanel20"); // NOI18N

        AceptarPais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AceptarPais.setText("Aceptar");
        AceptarPais.setName("AceptarPais"); // NOI18N
        AceptarPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarPaisActionPerformed(evt);
            }
        });

        SalirPais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SalirPais.setText("Salir");
        SalirPais.setName("SalirPais"); // NOI18N
        SalirPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(AceptarPais, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SalirPais, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPais)
                    .addComponent(SalirPais))
                .addContainerGap())
        );

        javax.swing.GroupLayout BpaisesLayout = new javax.swing.GroupLayout(Bpaises.getContentPane());
        Bpaises.getContentPane().setLayout(BpaisesLayout);
        BpaisesLayout.setHorizontalGroup(
            BpaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BpaisesLayout.setVerticalGroup(
            BpaisesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BpaisesLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BEmisor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BEmisor.setTitle("Buscar Emisor");
        BEmisor.setName("BEmisor"); // NOI18N

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel21.setName("jPanel21"); // NOI18N

        comboemisor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboemisor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboemisor.setName("comboemisor"); // NOI18N

        jTBuscarEmisor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTBuscarEmisor.setName("jTBuscarEmisor"); // NOI18N
        jTBuscarEmisor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarEmisorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jTBuscarEmisor, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarEmisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        tablaemisor.setModel(modelorubros        );
        tablaemisor.setName("tablaemisor"); // NOI18N
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
        jScrollPane8.setViewportView(tablaemisor);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel22.setName("jPanel22"); // NOI18N

        AceptarRubro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AceptarRubro.setText("Aceptar");
        AceptarRubro.setName("AceptarRubro"); // NOI18N
        AceptarRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarRubroActionPerformed(evt);
            }
        });

        SalirRubro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SalirRubro.setText("Salir");
        SalirRubro.setName("SalirRubro"); // NOI18N
        SalirRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(AceptarRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SalirRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarRubro)
                    .addComponent(SalirRubro))
                .addContainerGap())
        );

        javax.swing.GroupLayout BEmisorLayout = new javax.swing.GroupLayout(BEmisor.getContentPane());
        BEmisor.getContentPane().setLayout(BEmisorLayout);
        BEmisorLayout.setHorizontalGroup(
            BEmisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BEmisorLayout.setVerticalGroup(
            BEmisorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BEmisorLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");
        BMoneda.setName("BMoneda"); // NOI18N

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel23.setName("jPanel23"); // NOI18N

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.setName("combomoneda"); // NOI18N
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.setText(org.openide.util.NbBundle.getMessage(titulos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarMoneda.setName("jTBuscarMoneda"); // NOI18N
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

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        tablamoneda.setModel(modelomoneda);
        tablamoneda.setName("tablamoneda"); // NOI18N
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
        jScrollPane9.setViewportView(tablamoneda);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel24.setName("jPanel24"); // NOI18N

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText(org.openide.util.NbBundle.getMessage(titulos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.setName("AceptarMoneda"); // NOI18N
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(org.openide.util.NbBundle.getMessage(titulos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.setName("SalirMoneda"); // NOI18N
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
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BInstrumento.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BInstrumento.setTitle("null");
        BInstrumento.setName("BInstrumento"); // NOI18N

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel13.setName("jPanel13"); // NOI18N

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.setName("combosucursal"); // NOI18N
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(titulos.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarSucursal.setName("jTBuscarSucursal"); // NOI18N
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

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        tablasucursal.setModel(modelosucursal);
        tablasucursal.setName("tablasucursal"); // NOI18N
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
        jPanel15.setName("jPanel15"); // NOI18N

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(titulos.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.setName("AceptarSuc"); // NOI18N
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(titulos.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.setName("SalirSuc"); // NOI18N
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

        javax.swing.GroupLayout BInstrumentoLayout = new javax.swing.GroupLayout(BInstrumento.getContentPane());
        BInstrumento.getContentPane().setLayout(BInstrumentoLayout);
        BInstrumentoLayout.setHorizontalGroup(
            BInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BInstrumentoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BInstrumentoLayout.setVerticalGroup(
            BInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BInstrumentoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cupones.setName("cupones"); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel2.setText("Monto Emisión");
        jLabel2.setName("jLabel2"); // NOI18N

        monto_emision.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        monto_emision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        monto_emision.setName("monto_emision"); // NOI18N
        monto_emision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monto_emisionActionPerformed(evt);
            }
        });
        monto_emision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                monto_emisionKeyReleased(evt);
            }
        });

        jLabel5.setText("Corte Mínimo");
        jLabel5.setName("jLabel5"); // NOI18N

        corte_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        corte_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        corte_minimo.setName("corte_minimo"); // NOI18N
        corte_minimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                corte_minimoKeyReleased(evt);
            }
        });

        jLabel13.setText("Emisión");
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel19.setText("Vencimiento");
        jLabel19.setName("jLabel19"); // NOI18N

        emision.setName("emision"); // NOI18N
        emision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emisionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                emisionKeyReleased(evt);
            }
        });

        vencimiento.setName("vencimiento"); // NOI18N
        vencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vencimientoKeyPressed(evt);
            }
        });

        jLabel20.setText("Tasa");
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText("Base");
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel22.setText("Período Pagos");
        jLabel22.setName("jLabel22"); // NOI18N

        tasa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        tasa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tasa.setName("tasa"); // NOI18N
        tasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tasaActionPerformed(evt);
            }
        });
        tasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tasaKeyPressed(evt);
            }
        });

        base.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "360", "365" }));
        base.setName("base"); // NOI18N
        base.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                baseKeyReleased(evt);
            }
        });

        pagointeres.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mensual", "Bimestral", "Trimestral", "Cuatrimestral", "Semestral", "Anual", "Vencimiento" }));
        pagointeres.setName("pagointeres"); // NOI18N
        pagointeres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagointeresActionPerformed(evt);
            }
        });
        pagointeres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pagointeresKeyReleased(evt);
            }
        });

        jLabel23.setText("Cupones");
        jLabel23.setName("jLabel23"); // NOI18N

        cantidadcupones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        cantidadcupones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidadcupones.setName("cantidadcupones"); // NOI18N
        cantidadcupones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadcuponesActionPerformed(evt);
            }
        });

        codtitulo.setEditable(false);
        codtitulo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        codtitulo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codtitulo.setEnabled(false);
        codtitulo.setName("codtitulo"); // NOI18N
        codtitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codtituloActionPerformed(evt);
            }
        });
        codtitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codtituloKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(emision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monto_emision, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(corte_minimo))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(codtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(13, 13, 13)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pagointeres, 0, 1, Short.MAX_VALUE)
                            .addComponent(base, 0, 99, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cantidadcupones, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(monto_emision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel21)
                        .addComponent(base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(corte_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(codtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(emision, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pagointeres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(cantidadcupones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setName("jPanel4"); // NOI18N

        SalirCupon.setText("Salir");
        SalirCupon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCupon.setName("SalirCupon"); // NOI18N
        SalirCupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCuponActionPerformed(evt);
            }
        });

        GrabarCupon.setText("Grabar");
        GrabarCupon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCupon.setName("GrabarCupon"); // NOI18N
        GrabarCupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCuponActionPerformed(evt);
            }
        });

        vencecupon.setName("vencecupon"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(GrabarCupon, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SalirCupon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(vencecupon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SalirCupon)
                        .addComponent(GrabarCupon))
                    .addComponent(vencecupon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tablacupon.setModel(modelocupon        );
        tablacupon.setName("tablacupon"); // NOI18N
        tablacupon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablacuponKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablacupon);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(282, 282, 282))
        );

        javax.swing.GroupLayout cuponesLayout = new javax.swing.GroupLayout(cupones.getContentPane());
        cupones.getContentPane().setLayout(cuponesLayout);
        cuponesLayout.setHorizontalGroup(
            cuponesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cuponesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cuponesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cuponesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cuponesLayout.setVerticalGroup(
            cuponesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cuponesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Títulos");
        setName("Form"); // NOI18N
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

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setName("jPanel2"); // NOI18N

        BotonEditar.setBackground(new java.awt.Color(255, 255, 255));
        BotonEditar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonEditar.setText("Editar Registro");
        BotonEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonEditar.setName("BotonEditar"); // NOI18N
        BotonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEditarActionPerformed(evt);
            }
        });

        BotonAgregar.setBackground(new java.awt.Color(255, 255, 255));
        BotonAgregar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonAgregar.setText(" Agregar Registro");
        BotonAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonAgregar.setName("BotonAgregar"); // NOI18N
        BotonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAgregarActionPerformed(evt);
            }
        });

        BotonDelete.setBackground(new java.awt.Color(255, 255, 255));
        BotonDelete.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonDelete.setText("Eliminar Registro");
        BotonDelete.setToolTipText("");
        BotonDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonDelete.setName("BotonDelete"); // NOI18N
        BotonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonDeleteActionPerformed(evt);
            }
        });

        BotonPrint.setBackground(new java.awt.Color(255, 255, 255));
        BotonPrint.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonPrint.setText("Listar/Imprimir");
        BotonPrint.setToolTipText("");
        BotonPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonPrint.setName("BotonPrint"); // NOI18N
        BotonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonPrintActionPerformed(evt);
            }
        });

        BotonClose.setBackground(new java.awt.Color(255, 255, 255));
        BotonClose.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonClose.setText("     Salir");
        BotonClose.setToolTipText("");
        BotonClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonClose.setName("BotonClose"); // NOI18N
        BotonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCloseActionPerformed(evt);
            }
        });

        jTextOpciones1.setEditable(false);
        jTextOpciones1.setName("jTextOpciones1"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(BotonClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonPrint, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(BotonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(72, Short.MAX_VALUE)
                    .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(BotonAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BotonEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BotonDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BotonPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BotonClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(331, Short.MAX_VALUE)
                    .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(245, 245, 245)))
        );

        tablatitulos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablatitulos.setModel(modelo);
        tablatitulos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablatitulos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablatitulos.setName("tablatitulos"); // NOI18N
        tablatitulos.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablatitulos.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablatitulos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablatitulosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablatitulosFocusLost(evt);
            }
        });
        tablatitulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablatitulosMouseClicked(evt);
            }
        });
        tablatitulos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablatitulosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablatitulos);

        panel2.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel2.setColorSecundario(new java.awt.Color(0, 204, 255));
        panel2.setName("panel2"); // NOI18N

        etiquetasucursal.setText("Títulos");
        etiquetasucursal.setName("etiquetasucursal"); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Código", "Emisor", "Res. CNV", "Serie" }));
        jComboBox1.setName("jComboBox1"); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N
        jTextField1.setSelectionColor(new java.awt.Color(0, 63, 62));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(etiquetasucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetasucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText("Configurar");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Personalizar Cupones");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void TituloCupon() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 
        modelocupon.addColumn("N°");
        modelocupon.addColumn("Plazo");
        modelocupon.addColumn("Inicio");
        modelocupon.addColumn("Vence");
        modelocupon.addColumn("Día");
        int[] anchos = {120, 150, 150, 150, 150};
        for (int i = 0; i < modelocupon.getColumnCount(); i++) {
            tablacupon.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacupon.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacupon.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tablacupon.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        tablacupon.getColumnModel().getColumn(2).setCellRenderer(TablaCentro);
        tablacupon.getColumnModel().getColumn(3).setCellRenderer(TablaCentro);
        //Se usa para poner invisible una determinada celda
        tablacupon.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tablacupon.setFont(font);

    }

    private void TitInstrumento() {
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


    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        this.jTextField1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTextField1.getText()).toUpperCase();
                jTextField1.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por emisor
                    case 3:
                        indiceColumnaTabla = 5;
                        break;//por cnv
                    case 4:
                        indiceColumnaTabla = 6;
                        break;//por serie
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablatitulos.getModel());
        tablatitulos.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    public void filtropais(int nNumeroColumna) {
        trsfiltropais.setRowFilter(RowFilter.regexFilter(this.jTBuscarPais.getText(), nNumeroColumna));
    }

    private void TitPaises() {
        modelopaises.addColumn("Código");
        modelopaises.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelopaises.getColumnCount(); i++) {
            tablapaises.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapaises.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapaises.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapaises.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablapaises.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtroemisor(int nNumeroColumna) {
        trsfiltroemisor.setRowFilter(RowFilter.regexFilter(this.jTBuscarEmisor.getText(), nNumeroColumna));
    }

    private void TitRubros() {
        modelorubros.addColumn("Código");
        modelorubros.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelorubros.getColumnCount(); i++) {
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

    private void BotonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCloseActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonCloseActionPerformed

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
    }

    private void limpiar() {
        Calendar c2 = new GregorianCalendar();
        codigo.setText("0");
        nombre.setText("");
        nomalias.setText("");
        programa.setText("");
        rescnv.setText("");
        fechacnv.setCalendar(c2);
        resbvpasa.setText("");

        fechabvpasa.setCalendar(c2);

        emisor.setText("0");
        nombreemisor.setText("");

        pais.setText("0");
        nombrenacionalidad.setText("");
        moneda.setText("0");
        nombremoneda.setText("");

        instrumento.setText("0");
        nombreinstrumento.setText("");

        tipo.setSelectedIndex(0);
        negociable.setSelectedIndex(0);
        estado.setSelected(true);
    }


    private void BotonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAgregarActionPerformed
        codigo.setEnabled(true);
        this.limpiar();
        detalle_titulos.setModal(true);
        detalle_titulos.setSize(670, 612);
        //Establecemos un título para el jDialog
        detalle_titulos.setTitle("Agregar nuevo Título");
        detalle_titulos.setLocationRelativeTo(null);
        detalle_titulos.setVisible(true);
        nombre.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_BotonAgregarActionPerformed

    private void tablatitulosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablatitulosKeyPressed
        int nFila = this.tablatitulos.getSelectedRow();
        this.jTextOpciones1.setText(this.tablatitulos.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablatitulosKeyPressed

    private void tablatitulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablatitulosMouseClicked
        int nFila = this.tablatitulos.getSelectedRow();
        this.jTextOpciones1.setText(this.tablatitulos.getValueAt(nFila, 0).toString());

        // TODO add your handling code here:
    }//GEN-LAST:event_tablatitulosMouseClicked

    private void BotonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEditarActionPerformed
        this.limpiar();
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = this.tablatitulos.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablatitulos.requestFocus();
                return;
            }

            this.codigo.setText(this.tablatitulos.getValueAt(nFila, 0).toString());
            tituloDAO emDAO = new tituloDAO();
            titulo em = null;
            try {
                em = emDAO.buscarId(Integer.valueOf(this.codigo.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (em != null) {

                nombre.setText(em.getNombre());
                nomalias.setText(em.getNomalias());
                programa.setText(em.getNombreprograma());
                rescnv.setText(em.getRescnv());
                fechacnv.setDate(em.getFechacnv());
                resbvpasa.setText(em.getResbvpasa());
                fechabvpasa.setDate(em.getFechabvpasa());

                emisor.setText(String.valueOf(em.getEmpresa().getCodigo()));
                nombreemisor.setText(em.getEmpresa().getNombre());

                pais.setText(String.valueOf(em.getPais().getCodigo()));
                nombrenacionalidad.setText(em.getPais().getNombre().trim());
                moneda.setText(String.valueOf(em.getMoneda().getCodigo()));
                nombremoneda.setText(em.getMoneda().getNombre());

                instrumento.setText(String.valueOf(em.getInstrumento().getCodigo()));
                nombreinstrumento.setText(em.getInstrumento().getNombre());

                tipo.setSelectedIndex(em.getTipo() - 1);
                negociable.setSelectedIndex(em.getNegociable() - 1);

                if (em.getEstado() == 0) {
                    this.estado.setSelected(false);
                } else {
                    this.estado.setSelected(true);
                }

                detalle_titulos.setModal(true);
                detalle_titulos.setSize(670, 612);
                //Establecemos un título para el jDialog
                detalle_titulos.setTitle("Modificar Datos del Título");
                detalle_titulos.setLocationRelativeTo(null);
                detalle_titulos.setVisible(true);
                nombre.requestFocus();

            } else {
                JOptionPane.showMessageDialog(null, "USUARIO NO AUTORIZADO");
            }
        }

        //Envio como parametro el Codigo para saber si voy a modificar
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonEditarActionPerformed

    private void tablatitulosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablatitulosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablatitulosFocusGained

    private void jScrollPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void BotonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonDeleteActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = tablatitulos.getSelectedRow();
            String num = tablatitulos.getValueAt(nFila, 0).toString();
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                tituloDAO emDAO = new tituloDAO();
                try {
                    emDAO.eliminarTitulo(Integer.valueOf(num));
                    JOptionPane.showMessageDialog(null, "Registro Eliminado Exitosamente");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
            }
            GrillaTitulos GrillaOC = new GrillaTitulos();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonDeleteActionPerformed

    private void tablatitulosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablatitulosFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablatitulosFocusLost

    private void BotonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonPrintActionPerformed
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/titulos.jasper");
            //+ Config.cNombreFactura);
//              jr = (JasperReport) JRLoader.loadObject(url);
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(masterReport, parameters, stm.getConnection());
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(true);
        } catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
        }

    }//GEN-LAST:event_BotonPrintActionPerformed

    private void detalle_titulosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_detalle_titulosFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_titulosFocusGained

    private void detalle_titulosWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_titulosWindowGainedFocus

        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_titulosWindowGainedFocus

    private void BotonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirActionPerformed
        this.detalle_titulos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSalirActionPerformed

    private void BotonGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGrabarActionPerformed

        if (this.rescnv.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta Resolución CNV");
            this.rescnv.requestFocus();
            return;
        }

        if (this.resbvpasa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Falta Resolución BVPASA");
            this.resbvpasa.requestFocus();
            return;
        }

        if (this.nombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nombre");
            this.nombre.requestFocus();
            return;
        }

        if (this.nomalias.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Serie");
            this.nomalias.requestFocus();
            return;
        }

        if (this.moneda.getText().isEmpty() || this.moneda.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Moneda");
            this.moneda.requestFocus();
            return;
        }

        if (this.emisor.getText().isEmpty() || this.emisor.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Emisor");
            this.emisor.requestFocus();
            return;
        }

        if (this.instrumento.getText().isEmpty() || this.instrumento.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Instrumento");
            this.instrumento.requestFocus();
            return;
        }

        if (this.pais.getText().isEmpty() || this.pais.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el País");
            this.pais.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar los Datos? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            Date FechaResCnv = ODate.de_java_a_sql(fechacnv.getDate());
            Date FechaBVPASA = ODate.de_java_a_sql(fechabvpasa.getDate());

            tituloDAO grabar = new tituloDAO();
            titulo ti = new titulo();

            paisDAO pDAO = new paisDAO();
            pais p = null;

            emisorDAO emiDAO = new emisorDAO();
            emisor em = null;

            monedaDAO mnDAO = new monedaDAO();
            moneda mon = null;

            instrumentoDAO instruDAO = new instrumentoDAO();
            instrumento instru = null;

            try {
                p = pDAO.buscarId(Integer.valueOf(pais.getText()));
                em = emiDAO.buscarId(Integer.valueOf(emisor.getText()));
                mon = mnDAO.buscarId(Integer.valueOf(moneda.getText()));
                instru = instruDAO.buscarId(Integer.valueOf(instrumento.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            int nestado = 0;
            if (this.estado.isSelected()) {
                nestado = 1;
            }
            ti.setCodigo(Integer.valueOf(codigo.getText()));
            ti.setNombre(nombre.getText());
            ti.setNomalias(nomalias.getText());
            ti.setNombreprograma(programa.getText());
            ti.setRescnv(rescnv.getText());
            ti.setFechacnv(FechaResCnv);
            ti.setResbvpasa(resbvpasa.getText().trim());
            ti.setFechabvpasa(FechaBVPASA);
            ti.setEmpresa(em);
            ti.setPais(p);
            ti.setMoneda(mon);
            ti.setInstrumento(instru);
            ti.setTipo(tipo.getSelectedIndex() + 1);
            ti.setNegociable(negociable.getSelectedIndex() + 1);
            ti.setEstado(nestado);
            try {
                if (Integer.valueOf(codigo.getText()) == 0) {
                    grabar.insertarTitulos(ti);
                } else {
                    grabar.actualizarTitulos(ti);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            detalle_titulos.setVisible(false);
            detalle_titulos.setModal(false);

            GrillaTitulos GrillaOC = new GrillaTitulos();
            Thread HiloGrilla = new Thread(GrillaOC);
            HiloGrilla.start();
        }
    }//GEN-LAST:event_BotonGrabarActionPerformed

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.nomalias.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyPressed

    private void nomaliasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomaliasKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.programa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombre.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nomaliasKeyPressed

    private void programaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_programaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.rescnv.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nomalias.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_programaKeyPressed

    private void resbvpasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resbvpasaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.fechabvpasa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechacnv.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_resbvpasaKeyPressed

    private void nombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusLost
        String letras = ConvertirMayusculas.cadena(nombre);
        nombre.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusLost

    private void tablapaisesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablapaisesMouseClicked
        this.AceptarPais.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapaisesMouseClicked

    private void tablapaisesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablapaisesKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPais.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapaisesKeyPressed

    private void jTBuscarPaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPaisKeyPressed
        this.jTBuscarPais.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarPais.getText()).toUpperCase();
                jTBuscarPais.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combopais.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtropais(indiceColumnaTabla);
            }
        });
        trsfiltropais = new TableRowSorter(tablapaises.getModel());
        tablapaises.setRowSorter(trsfiltropais);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPaisKeyPressed

    private void AceptarPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarPaisActionPerformed
        int nFila = this.tablapaises.getSelectedRow();
        this.pais.setText(this.tablapaises.getValueAt(nFila, 0).toString());
        this.nombrenacionalidad.setText(this.tablapaises.getValueAt(nFila, 1).toString().trim());
        this.Bpaises.setVisible(false);
        this.jTBuscarPais.setText("");
        moneda.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarPaisActionPerformed

    private void SalirPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPaisActionPerformed
        this.Bpaises.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPaisActionPerformed

    private void BuscarNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarNacionalidadActionPerformed
        paisDAO baDAO = new paisDAO();
        pais ba = null;
        try {
            ba = baDAO.buscarId(Integer.valueOf(this.pais.getText()));
            if (ba.getCodigo() == 0) {
                Bpaises.setModal(true);
                Bpaises.setSize(482, 575);
                Bpaises.setLocationRelativeTo(null);
                Bpaises.setVisible(true);
                Bpaises.setTitle("Buscar Paises");
            } else {
                nombrenacionalidad.setText(ba.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarNacionalidadActionPerformed

    private void paisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paisActionPerformed
        this.BuscarNacionalidad.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_paisActionPerformed

    private void paisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paisKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.moneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.emisor.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_paisKeyPressed

    private void rescnvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rescnvKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.fechacnv.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.programa.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rescnvKeyPressed

    private void emisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emisorActionPerformed
        this.BuscarEmisor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_emisorActionPerformed

    private void emisorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emisorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.pais.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechabvpasa.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_emisorKeyPressed

    private void BuscarEmisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarEmisorActionPerformed
        emisorDAO ruDAO = new emisorDAO();
        emisor ru = null;
        try {
            ru = ruDAO.buscarId(Integer.valueOf(this.emisor.getText()));
            if (ru.getCodigo() == 0) {
                BEmisor.setModal(true);
                BEmisor.setSize(482, 575);
                BEmisor.setLocationRelativeTo(null);
                BEmisor.setVisible(true);
            } else {
                nombreemisor.setText(ru.getNombre().trim());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
//        tiposociedad.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarEmisorActionPerformed

    private void tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoActionPerformed

    private void tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.negociable.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.instrumento.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoKeyPressed

    private void negociableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negociableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_negociableActionPerformed

    private void negociableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_negociableKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.estado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_negociableKeyPressed

    private void jTBuscarEmisorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarEmisorKeyPressed
        this.jTBuscarEmisor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarEmisor.getText()).toUpperCase();
                jTBuscarEmisor.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboemisor.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtroemisor(indiceColumnaTabla);

            }
        });
        trsfiltroemisor = new TableRowSorter(tablaemisor.getModel());
        tablaemisor.setRowSorter(trsfiltroemisor);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarEmisorKeyPressed

    private void tablaemisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaemisorMouseClicked
        this.AceptarRubro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaemisorMouseClicked

    private void tablaemisorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaemisorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarRubro.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaemisorKeyPressed

    private void AceptarRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarRubroActionPerformed
        int nFila = this.tablaemisor.getSelectedRow();
        this.emisor.setText(this.tablaemisor.getValueAt(nFila, 0).toString());
        this.nombreemisor.setText(this.tablaemisor.getValueAt(nFila, 1).toString());
        this.BEmisor.setVisible(false);
        this.jTBuscarEmisor.setText("");
        pais.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarRubroActionPerformed

    private void SalirRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirRubroActionPerformed
        this.BEmisor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirRubroActionPerformed

    private void fechacnvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechacnvKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.resbvpasa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.rescnv.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fechacnvKeyPressed

    private void fechabvpasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechabvpasaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.emisor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.resbvpasa.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_fechabvpasaKeyPressed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        this.BuscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.instrumento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.pais.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        monedaDAO casDAO = new monedaDAO();
        moneda mn = null;
        try {
            mn = casDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mn.getCodigo() == 0) {
                GrillaMoneda grillaca = new GrillaMoneda();
                Thread hiloca = new Thread(grillaca);
                hiloca.start();
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
            instrumento.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private void instrumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instrumentoActionPerformed
        this.BuscarIntrumento.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_instrumentoActionPerformed

    private void instrumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_instrumentoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.tipo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_instrumentoKeyPressed

    private void BuscarIntrumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarIntrumentoActionPerformed
        instrumentoDAO instDAO = new instrumentoDAO();
        instrumento instru = null;
        try {
            instru = instDAO.buscarId(Integer.valueOf(this.instrumento.getText()));
            if (instru.getCodigo() == 0) {
                GrillaInstrumento grillasu = new GrillaInstrumento();
                Thread hilosuc = new Thread(grillasu);
                hilosuc.start();
                BInstrumento.setModal(true);
                BInstrumento.setSize(500, 575);
                BInstrumento.setLocationRelativeTo(null);
                BInstrumento.setTitle("Buscar Instrumento");
                BInstrumento.setVisible(true);
                BInstrumento.setModal(false);
            } else {
                nombreinstrumento.setText(instru.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarIntrumentoActionPerformed

    private void nomaliasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nomaliasFocusLost
        String letras = ConvertirMayusculas.cadena(nomalias);
        nomalias.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nomaliasFocusLost

    private void detalle_titulosWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_detalle_titulosWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_detalle_titulosWindowActivated

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

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }


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
        this.instrumento.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

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
        this.instrumento.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombreinstrumento.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BInstrumento.setVisible(false);
        this.jTBuscarSucursal.setText("");
        this.tipo.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BInstrumento.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void emisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emisionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_emisionKeyPressed

    private void vencimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.tasa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.emision.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoKeyPressed

    private void pagointeresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagointeresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagointeresActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int cantidadRegistro = modelocupon.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }

        int nFila = this.tablatitulos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablatitulos.requestFocus();
            return;
        }

        this.codigo.setText(this.tablatitulos.getValueAt(nFila, 0).toString());
        tituloDAO emDAO = new tituloDAO();
        titulo em = null;
        try {
            em = emDAO.buscarId(Integer.valueOf(this.codigo.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (em != null) {
            if (em.getTipo() == 1) {
                this.codtitulo.setText(formato.format(em.getCodigo()));
                this.monto_emision.setText(formato.format(em.getMonto_emision()));
                this.corte_minimo.setText(formato.format(em.getCorte_minimo()));
                this.emision.setDate(em.getFechaemision());
                this.vencimiento.setDate(em.getVencimiento());
                this.tasa.setText(formato.format(em.getTasa()));
                this.base.setSelectedIndex(em.getBase() - 1);
                this.pagointeres.setSelectedItem(em.getPagointeres());
                this.cantidadcupones.setText(formato.format(em.getCupones()));
                this.CargarCupon(em.getCodigo());
                cupones.setModal(true);
                cupones.setSize(476, 500);
                //Establecemos un título para el jDialog
                cupones.setTitle("Actualizar Cupones del Título " + em.getNombre().trim() + "/" + em.getNomalias().trim());
                cupones.setLocationRelativeTo(null);
                cupones.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Título no es de Renta Fija");
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void CargarCupon(int titulo) {
        int cantidadRegistro = modelocupon.getRowCount();
        String cNombreDia = "SD";
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }
        detalle_cupones_titulosDAO detDAO = new detalle_cupones_titulosDAO();
        try {
            for (detalle_cupones_titulos detvta : detDAO.MostrarxTitulo(titulo)) {
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
                String Detalle[] = {String.valueOf(detvta.getNumerocupon()), formato.format(detvta.getPlazo()), formatoFecha.format(detvta.getFechainicio()), formatoFecha.format(detvta.getFechavencimiento()), cNombreDia};
                modelocupon.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }

    }


    private void monto_emisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monto_emisionActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_monto_emisionActionPerformed

    private void monto_emisionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monto_emisionKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.corte_minimo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_monto_emisionKeyReleased

    private void corte_minimoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_corte_minimoKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.emision.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.monto_emision.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_corte_minimoKeyReleased

    private void emisionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emisionKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.vencimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.corte_minimo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_emisionKeyReleased

    private void tasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaActionPerformed

    private void baseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_baseKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.pagointeres.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tasa.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_baseKeyReleased

    private void pagointeresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pagointeresKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) { //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.GrabarCupon.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.base.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_pagointeresKeyReleased

    private void tasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaKeyPressed

    private void SalirCuponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCuponActionPerformed
        this.cupones.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCuponActionPerformed

    private void cantidadcuponesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadcuponesActionPerformed
        GenerarCupones();
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadcuponesActionPerformed

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

    private void GrabarCuponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCuponActionPerformed
        if (this.cantidadcupones.getText().isEmpty() || this.cantidadcupones.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cantidad de Cupones");
            this.cantidadcupones.requestFocus();
            return;
        }
        String cvalor_emision = this.monto_emision.getText();
        cvalor_emision = cvalor_emision.replace(".", "").replace(",", ".");
        if (cvalor_emision.isEmpty() || cvalor_emision.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Monto de la Emisión");
            monto_emision.requestFocus();
            return;
        }        // TODO add your handling code here:

        String ccorte = this.corte_minimo.getText();
        ccorte = ccorte.replace(".", "").replace(",", ".");
        if (ccorte.isEmpty() || ccorte.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Corte de los Títulos");
            corte_minimo.requestFocus();
            return;
        }        // TODO add your handling code here:

        String ctasa = this.tasa.getText();
        ctasa = ctasa.replace(".", "").replace(",", ".");
        if (ctasa.isEmpty() || ctasa.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Tasa de Emisión");
            tasa.requestFocus();
            return;
        }        // TODO add your handling code here:

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar las Modificaciones ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            titulo tit = new titulo();
            tituloDAO tiDAO = new tituloDAO();

            tit.setCodigo(Integer.valueOf(codtitulo.getText()));
            Date FechaEmision = ODate.de_java_a_sql(emision.getDate());
            Date FechaVence = ODate.de_java_a_sql(vencimiento.getDate());
            tit.setMonto_emision(new BigDecimal(cvalor_emision));
            tit.setFechaemision(FechaEmision);
            tit.setVencimiento(FechaVence);
            tit.setTasa(new BigDecimal(ctasa));
            tit.setCorte_minimo(new BigDecimal(ccorte));
            tit.setNominal(new BigDecimal(ccorte));
            tit.setBase(base.getSelectedIndex() + 1);
            tit.setPagointeres(pagointeres.getSelectedItem().toString());
            tit.setCupones(Integer.valueOf(cantidadcupones.getText()));

            String detalle = "[";
            int Items = tablacupon.getRowCount();
            for (int i = 0; i < Items; i++) {
                try {
                    dEmision = ODate.de_java_a_sql(formatoFecha.parse(tablacupon.getValueAt(i, 2).toString()));
                    dVence = ODate.de_java_a_sql(formatoFecha.parse(tablacupon.getValueAt(i, 3).toString()));
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }

                //CARGAMOS EL JSON CON LOS DATOS DE CUPONES   
                String linea = "{titulo : " + codtitulo.getText() + ","
                        + "numerocupon : " + tablacupon.getValueAt(i, 0).toString().trim() + ","
                        + "fechavencimiento : " + dVence + ","
                        + "plazo : " + tablacupon.getValueAt(i, 1).toString().trim() + ","
                        + "fechainicio : " + dEmision + ","
                        + "estadocupon :" + 1 + "},";
                detalle += linea;
            }
            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";
            System.out.println(detalle);

            try {
                tiDAO.actualizarTitulosRentaFija(tit, detalle);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        this.SalirCupon.doClick();
    }//GEN-LAST:event_GrabarCuponActionPerformed

    private void codtituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codtituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codtituloActionPerformed

    private void codtituloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codtituloKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_codtituloKeyPressed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    public void GenerarCupones() {
        int cantidadRegistro = modelocupon.getRowCount();
        int nCantidadCupones = Integer.valueOf(cantidadcupones.getText());
        String cNombreDia = "SD";
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }
        int nbase = 0;
        if (this.base.getSelectedIndex() == 0) {
            nbase = 360;
        } else {
            nbase = 365;
        }
        int nplazo = 0;
        switch (this.pagointeres.getSelectedIndex()) {
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
                nplazo = 365;
                break; // break es opcional
        }
        vencecupon.setDate(emision.getDate());
        for (int i = 1; i <= (nCantidadCupones); i++) {
            Object[] fila = new Object[5]; // Hay 8   columnas en la tabla
            fila[0] = i;
            fila[1] = formato.format(nplazo);
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
                default:
                    cNombreDia = "SD";
            }
            fila[4] = cNombreDia;
            modelocupon.addRow(fila);
        }
    }

    private void calcularVencimiento(int plazo) {
        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.vencecupon.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        calendar.add(Calendar.DAY_OF_YEAR, plazo);  // numero de días a añadir, o restar en caso de días<0
        this.vencecupon.setDate(calendar.getTime()); //Y cargamos
    }

    private void CalcularPlazoInteres(int operacion) {
        String cNombreDia = "";
        int nFila = this.tablacupon.getSelectedRow();

        int nbase = 0;
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
            default:
                cNombreDia = "SD";
        }
        this.tablacupon.setValueAt(cNombreDia, this.tablacupon.getSelectedRow(), 4);
        try {
            vencecupon.setDate(formatoFecha.parse(this.tablacupon.getValueAt(this.tablacupon.getSelectedRow(), 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void ActualizarCupones(int operacion) {
        int nplazo = 0;
        switch (this.pagointeres.getSelectedIndex()) {
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
                nplazo = 365;
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
                default:
                    cNombreDia = "SD";
            }
            this.tablacupon.setValueAt(cNombreDia, i, 4);
            try {
                vencecupon.setDate(formatoFecha.parse(this.tablacupon.getValueAt(i, 3).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Sociedad Emisora");
        modelo.addColumn("País");
        modelo.addColumn("Moneda");
        modelo.addColumn("RES. CNV");
        modelo.addColumn("Serie");

        int[] anchos = {90, 150, 200, 100, 100, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tablatitulos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablatitulos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablatitulos.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablatitulos.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);
        this.tablatitulos.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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
                new titulos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton AceptarPais;
    private javax.swing.JButton AceptarRubro;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JDialog BEmisor;
    private javax.swing.JDialog BInstrumento;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JButton BotonAgregar;
    private javax.swing.JButton BotonClose;
    private javax.swing.JButton BotonDelete;
    private javax.swing.JButton BotonEditar;
    private javax.swing.JButton BotonGrabar;
    private javax.swing.JButton BotonPrint;
    private javax.swing.JButton BotonSalir;
    private javax.swing.JDialog Bpaises;
    private javax.swing.JButton BuscarEmisor;
    private javax.swing.JButton BuscarIntrumento;
    private javax.swing.JButton BuscarMoneda;
    private javax.swing.JButton BuscarNacionalidad;
    private javax.swing.JButton GrabarCupon;
    private javax.swing.JButton SalirCupon;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirPais;
    private javax.swing.JButton SalirRubro;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JComboBox<String> base;
    private javax.swing.JFormattedTextField cantidadcupones;
    private javax.swing.JTextField codigo;
    private javax.swing.JFormattedTextField codtitulo;
    private javax.swing.JComboBox<String> comboemisor;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox<String> combopais;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JFormattedTextField corte_minimo;
    private javax.swing.JDialog cupones;
    private javax.swing.JDialog detalle_titulos;
    private com.toedter.calendar.JDateChooser emision;
    private javax.swing.JTextField emisor;
    private javax.swing.JCheckBox estado;
    private org.edisoncor.gui.label.LabelMetric etiquetasucursal;
    private com.toedter.calendar.JDateChooser fechabvpasa;
    private com.toedter.calendar.JDateChooser fechacnv;
    private javax.swing.JTextField instrumento;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarEmisor;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarPais;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextOpciones1;
    private javax.swing.JTextField moneda;
    private javax.swing.JFormattedTextField monto_emision;
    private javax.swing.JComboBox<String> negociable;
    private javax.swing.JTextField nomalias;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombreemisor;
    private javax.swing.JTextField nombreinstrumento;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombrenacionalidad;
    private javax.swing.JComboBox<String> pagointeres;
    private javax.swing.JTextField pais;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel2;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JTextField programa;
    private javax.swing.JTextField resbvpasa;
    private javax.swing.JTextField rescnv;
    private javax.swing.JTable tablacupon;
    private javax.swing.JTable tablaemisor;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablapaises;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTable tablatitulos;
    private javax.swing.JFormattedTextField tasa;
    private javax.swing.JComboBox<String> tipo;
    private com.toedter.calendar.JDateChooser vencecupon;
    private com.toedter.calendar.JDateChooser vencimiento;
    // End of variables declaration//GEN-END:variables

    private class GrillaTitulos extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            tituloDAO DAO = new tituloDAO();
            try {
                for (titulo em : DAO.todos()) {
                    String Datos[] = {String.valueOf(em.getCodigo()), em.getNombre(), em.getEmpresa().getNombre(), em.getPais().getNombre(), em.getMoneda().getNombre(), em.getRescnv(), em.getNomalias()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablatitulos.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = tablatitulos.getRowCount();
            if (cantFilas > 0) {
                BotonEditar.setEnabled(true);
                BotonDelete.setEnabled(true);
                BotonPrint.setEnabled(true);
            } else {
                BotonEditar.setEnabled(false);
                BotonDelete.setEnabled(false);
                BotonPrint.setEnabled(false);
            }
        }
    }

    private class GrillaPaises extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopaises.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopaises.removeRow(0);
            }
            paisDAO bcDAO = new paisDAO();
            try {
                for (pais ba : bcDAO.todos()) {
                    String Datos[] = {String.valueOf(ba.getCodigo()), ba.getNombre()};
                    modelopaises.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablapaises.setRowSorter(new TableRowSorter(modelopaises));
            int cantFilas = tablapaises.getRowCount();
        }
    }

    private class GrillaRubros extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelorubros.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelorubros.removeRow(0);
            }
            emisorDAO ruDAO = new emisorDAO();
            try {
                for (emisor ru : ruDAO.todos()) {
                    String Datos[] = {String.valueOf(ru.getCodigo()), ru.getNombre()};
                    modelorubros.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaemisor.setRowSorter(new TableRowSorter(modelorubros));
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
            monedaDAO DAOCASA = new monedaDAO();
            try {
                for (moneda ca : DAOCASA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre(), formato.format(ca.getVenta())};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private class GrillaInstrumento extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelosucursal.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelosucursal.removeRow(0);
            }

            instrumentoDAO DAOSUC = new instrumentoDAO();
            try {
                for (instrumento suc : DAOSUC.todos()) {
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

}

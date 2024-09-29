/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.CalcularRuc;
import Clases.Config;
import Clases.ControlGrabado;
import Clases.ConvertirMayusculas;
import Clases.CrearDigitoRuc;
import Conexion.BDConexion;
import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Conexion.ObtenerFecha;
import Conexion.ObtenerNumero;
import javax.swing.RowFilter;
import DAO.albumfotoDAO;
import DAO.bancoplazaDAO;
import DAO.barrioDAO;
import DAO.clienteDAO;
import DAO.ctacteclienteDAO;
import DAO.giraduriaDAO;
import DAO.localidadDAO;
import DAO.monedaDAO;
import DAO.referencia_comercialDAO;
import DAO.referencia_laboralDAO;
import Modelo.Tablas;
import Modelo.albumfoto;
import Modelo.bancoplaza;
import Modelo.barrio;
import Modelo.cliente;
import Modelo.ctactecliente;
import Modelo.giraduria;
import Modelo.localidad;
import Modelo.moneda;
import Modelo.referencia_comercial;
import Modelo.referencia_laboral;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import org.openide.util.Exceptions;

/**
 *
 * @author MEC
 */
public class detalle_clientes_fais extends javax.swing.JFrame {

    //Inicializamos las variables de conexion a la base de Datos
    Conexion con = null;
    Statement stm = null;
    BDConexion BD = new BDConexion();
    String Operacion = null;
    Tablas comercial = new Tablas();
    Tablas modelocuenta = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelobanco = new Tablas();
    Tablas modelolocalidad = new Tablas();
    Tablas modelobarrio = new Tablas();
    private TableRowSorter trsfiltromoneda, trsfiltrobanco, trsfiltrolocalidad, trsfiltrobarrio;
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("####");
    ArrayList<albumfoto> imagenes;
    int contador = 0;

    public detalle_clientes_fais(String Opcion) throws ParseException {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
        //Verificamos el parametro y determinamos si quiero un registro nuevo
        //o va actualizar uno existente
        this.jTextControl.setVisible(false);
        this.BuscarLocalidad.setIcon(iconobuscar);
        this.BuscarBarrio.setIcon(iconobuscar);
        this.limpiarCombos(); //Se limpian e inicializan los combos asociados a otra tabla

        this.jTextControl.setText(Opcion);
        this.codigo.setHorizontalAlignment(this.codigo.RIGHT);
        this.Salario.setHorizontalAlignment(this.Salario.RIGHT);
        this.SalarioConyugue.setHorizontalAlignment(this.SalarioConyugue.RIGHT);
        this.otrosIngresos.setHorizontalAlignment(this.otrosIngresos.RIGHT);
        this.LimiteCredito.setHorizontalAlignment(this.LimiteCredito.RIGHT);
        this.nombre.selectAll();
        this.iditem.setVisible(false);

        if (Opcion == "new") {
            this.codigo.setEnabled(true);
            this.codigo.setEditable(true);
            this.LimiteCredito.setText("0");
            this.Salario.setText("0");
            this.SalarioConyugue.setText("0");
            this.otrosIngresos.setText("0");
            this.LimiteCredito.setText("0");
            this.codigo.setText("0");
            this.plazocredito.setText("0");
            this.comitente.setText("0");
            this.codigo.requestFocus();
            int cantidadRegistro = modelocuenta.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocuenta.removeRow(0);
            }
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            Calendar c2 = new GregorianCalendar();
            this.jDateNacimiento.setCalendar(c2);
            this.fechaingresofuncionario.setCalendar(c2);
            this.fechaingreso.setCalendar(c2);
            this.facturar_a_nombre.setText("");
            this.direccionfactura.setText("");
            this.telefonofactura.setText("");
            this.rucfactura.setText("");
            imprimirtitular.setSelected(false);
            this.clientepep.setSelectedItem(0);
            this.apoderado.setText("");
            this.objetoempresa.setText("");
            this.directorio.setText("");
            this.localidad.setText("0");
            this.nombrelocalidad.setText("");
            this.barrio.setText("0");
            this.nombrebarrio.setText("");
            this.AceptarCasa.setText("0");
        } else {
            this.codigo.setEnabled(false);
            this.codigo.setText(Opcion);
            this.consultarTabla(this.codigo.getText());
            this.nombre.requestFocus();
            GrillaComercial GrillaC = new GrillaComercial();
            Thread Hilo1 = new Thread(GrillaC);
            Hilo1.start();
        }
        //Asignamos el formato a los campos tipo date

        this.refrescarCarrusel(0);
        this.TituloCuenta();
        this.TitMoneda();
        this.TituloBanco();
        this.TitLocalidad();
        this.TitBarrio();
        this.LimpiarCta();
        this.CargarReferenciaComercial();
        this.fechaingresofuncionario.setDateFormatString("dd/MM/yyyy");
        this.jDateNacimiento.setDateFormatString("dd/MM/yyyy");
        // damos formato de seleccion completo a estos objetos
        this.Salario.selectAll();
        this.otrosIngresos.selectAll();
        this.SalarioConyugue.selectAll();
        this.Buscar.setIcon(iconobuscar);
        this.buscarbanco.setIcon(iconobuscar);

        GrillaMonedaCta grillamo = new GrillaMonedaCta();
        Thread hiloca = new Thread(grillamo);
        hiloca.start();

        GrillaBancoPlaza grillaba = new GrillaBancoPlaza();
        Thread hiloba = new Thread(grillaba);
        hiloba.start();

        GrillaLocalidad grillaloc = new GrillaLocalidad();
        Thread hilolo = new Thread(grillaloc);
        hilolo.start();


        if (Integer.valueOf(this.codigo.getText()) > 0) {
            this.CargarCta(Integer.valueOf(codigo.getText()));
        }

        /*   if (Config.cNivelUsuario.equals("1")) {
            this.observaciones.setEditable(true);
        } else {
            this.observaciones.setEditable(false);
        }*/
    }

    ObtenerFecha ODate = new ObtenerFecha();
    ObtenerNumero on = new ObtenerNumero();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BMoneda = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        BuscarMoneda = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        BBancos = new javax.swing.JDialog();
        jPanel42 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        jTBuscarbanco = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel43 = new javax.swing.JPanel();
        AceptarCasa = new javax.swing.JButton();
        SalirCasa = new javax.swing.JButton();
        BLocalidad = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combolocalidad = new javax.swing.JComboBox();
        txtbuscarlocalidad = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablalocalidad = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        BBarrio = new javax.swing.JDialog();
        jPanel18 = new javax.swing.JPanel();
        combobarrio = new javax.swing.JComboBox();
        txtbuscarbarrio = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablabarrio = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        AceptarBarrio = new javax.swing.JButton();
        SalirBarrio = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Panel1Clientes = new javax.swing.JTabbedPane();
        DatosGenerales = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        codigo = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboCategoria = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ruc = new javax.swing.JTextField();
        jTextControl = new javax.swing.JTextField();
        jComboEstadocivil = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        cedula = new javax.swing.JTextField();
        jTextConyugue = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jDateNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        mail = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        direccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextCelular = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jComboEstado = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        comitente = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        codfais = new javax.swing.JFormattedTextField();
        fechaingreso = new com.toedter.calendar.JDateChooser();
        jLabel43 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        localidad = new javax.swing.JFormattedTextField();
        barrio = new javax.swing.JFormattedTextField();
        BuscarLocalidad = new javax.swing.JButton();
        BuscarBarrio = new javax.swing.JButton();
        nombrelocalidad = new javax.swing.JTextField();
        nombrebarrio = new javax.swing.JTextField();
        Referencias = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablacuentas = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        nrocuenta = new javax.swing.JTextField();
        etiqueta2 = new javax.swing.JLabel();
        BotonGrabarCta = new javax.swing.JButton();
        BotonBorrarCta = new javax.swing.JButton();
        iditem = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        banco = new javax.swing.JTextField();
        buscarbanco = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        nombrebanco = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablacomercial = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        referenciacomercio = new javax.swing.JTextField();
        referenciafonocomercial = new javax.swing.JTextField();
        GrabarRefComercial = new javax.swing.JButton();
        BorrarRefComercial = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        PanelJuridico = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        objetoempresa = new javax.swing.JTextField();
        apoderado = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        directorio = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        observacion_cliente = new javax.swing.JTextArea();
        Utiles = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        imprimirtitular = new javax.swing.JCheckBox();
        facturar_a_nombre = new javax.swing.JTextField();
        direccionfactura = new javax.swing.JTextField();
        telefonofactura = new javax.swing.JTextField();
        rucfactura = new javax.swing.JTextField();
        PanelImagenes = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        BotonAbriArchivo = new javax.swing.JButton();
        nombrearchivo = new javax.swing.JTextField();
        etiqueta = new javax.swing.JLabel();
        GuardarArchivo = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        DatosLaborales = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextProfesion = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextLugarTrabajo = new javax.swing.JTextField();
        jTextCargoqueOcupa = new javax.swing.JTextField();
        fechaingresofuncionario = new com.toedter.calendar.JDateChooser();
        jTextDireccionLaboral = new javax.swing.JTextField();
        jTextTelefonoLaboral = new javax.swing.JTextField();
        jTextFaxLaboral = new javax.swing.JTextField();
        jTextMailLaboral = new javax.swing.JTextField();
        Salario = new org.jdesktop.swingx.JXFormattedTextField();
        SalarioConyugue = new org.jdesktop.swingx.JXFormattedTextField();
        otrosIngresos = new org.jdesktop.swingx.JXFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jComboAsesor = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        LimiteCredito = new org.jdesktop.swingx.JXFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jComboCasaPropia = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jComboAuto = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jComboInfor = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        plazocredito = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        clientepep = new javax.swing.JComboBox<>();
        perfilinterno = new javax.swing.JComboBox<>();
        inversorcalificado = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BMoneda.title")); // NOI18N

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        BuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jScrollPane6.setViewportView(tablamoneda);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.AceptarGir.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.SalirGir.text")); // NOI18N
        SalirGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BMonedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BBancos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BBancos.setTitle(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BBancos.title")); // NOI18N

        jPanel42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarbanco.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTBuscarbanco.text")); // NOI18N
        jTBuscarbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarbancoActionPerformed(evt);
            }
        });
        jTBuscarbanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarbancoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jScrollPane12.setViewportView(tablabanco);

        jPanel43.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.AceptarCasa.text")); // NOI18N
        AceptarCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.SalirCasa.text")); // NOI18N
        SalirCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa)
                    .addComponent(SalirCasa))
                .addContainerGap())
        );

        javax.swing.GroupLayout BBancosLayout = new javax.swing.GroupLayout(BBancos.getContentPane());
        BBancos.getContentPane().setLayout(BBancosLayout);
        BBancosLayout.setHorizontalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBancosLayout.setVerticalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BLocalidad.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BLocalidad.setTitle(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BLocalidad.title")); // NOI18N

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combolocalidad.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combolocalidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combolocalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combolocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combolocalidadActionPerformed(evt);
            }
        });

        txtbuscarlocalidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscarlocalidad.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.txtbuscarlocalidad.text")); // NOI18N
        txtbuscarlocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarlocalidadActionPerformed(evt);
            }
        });
        txtbuscarlocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscarlocalidadKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(combolocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtbuscarlocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combolocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscarlocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablalocalidad.setModel(modelolocalidad    );
        tablalocalidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablalocalidadMouseClicked(evt);
            }
        });
        tablalocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablalocalidadKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablalocalidad);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.AceptarVendedor.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.SalirVendedor.text")); // NOI18N
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
                .addGap(75, 75, 75)
                .addComponent(SalirVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarVendedor)
                    .addComponent(SalirVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout BLocalidadLayout = new javax.swing.GroupLayout(BLocalidad.getContentPane());
        BLocalidad.getContentPane().setLayout(BLocalidadLayout);
        BLocalidadLayout.setHorizontalGroup(
            BLocalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BLocalidadLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BLocalidadLayout.setVerticalGroup(
            BLocalidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BLocalidadLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BBarrio.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BBarrio.setTitle(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BBarrio.title")); // NOI18N

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobarrio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobarrio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobarrio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobarrioActionPerformed(evt);
            }
        });

        txtbuscarbarrio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscarbarrio.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.txtbuscarbarrio.text")); // NOI18N
        txtbuscarbarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarbarrioActionPerformed(evt);
            }
        });
        txtbuscarbarrio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscarbarrioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(combobarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtbuscarbarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbuscarbarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablabarrio.setModel(modelobarrio);
        tablabarrio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablabarrioMouseClicked(evt);
            }
        });
        tablabarrio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablabarrioKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablabarrio);

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarBarrio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarBarrio.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.AceptarBarrio.text")); // NOI18N
        AceptarBarrio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarBarrioActionPerformed(evt);
            }
        });

        SalirBarrio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirBarrio.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.SalirBarrio.text")); // NOI18N
        SalirBarrio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirBarrioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarBarrio)
                    .addComponent(SalirBarrio))
                .addContainerGap())
        );

        javax.swing.GroupLayout BBarrioLayout = new javax.swing.GroupLayout(BBarrio.getContentPane());
        BBarrio.getContentPane().setLayout(BBarrioLayout);
        BBarrioLayout.setHorizontalGroup(
            BBarrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBarrioLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBarrioLayout.setVerticalGroup(
            BBarrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBarrioLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("frame_detalle_clientes"); // NOI18N
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setForeground(new java.awt.Color(255, 255, 255));
        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel11.text")); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        DatosGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel7.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        codigo.setEditable(false);
        codigo.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextCodigo.text")); // NOI18N
        codigo.setEnabled(false);
        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoFocusLost(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
        });

        nombre.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextnombre.text")); // NOI18N
        nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombreFocusLost(evt);
            }
        });
        nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombreKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel2.text")); // NOI18N

        jComboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCategoriaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel25.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel25.text")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel3.text")); // NOI18N

        ruc.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextcedula.text")); // NOI18N
        ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rucKeyPressed(evt);
            }
        });

        jTextControl.setEditable(false);
        jTextControl.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextControl.text")); // NOI18N
        jTextControl.setEnabled(false);

        jComboEstadocivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Casado/a", "Soltero/a", "Separado/a", "Divorciado/a", "Viudo/a" }));
        jComboEstadocivil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboEstadocivil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboEstadocivilKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel4.text")); // NOI18N

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel44.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel44.text_1")); // NOI18N

        cedula.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.cedula.text")); // NOI18N
        cedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cedulaKeyPressed(evt);
            }
        });

        jTextConyugue.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextConyugue.text")); // NOI18N
        jTextConyugue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextConyugueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextConyugueKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel5.text")); // NOI18N

        jDateNacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateNacimientoKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel6.text")); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel24.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel24.text")); // NOI18N

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel42.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel42.text")); // NOI18N

        mail.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextmail.text")); // NOI18N
        mail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mailKeyPressed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel45.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel45.text_1")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel8.text")); // NOI18N

        direccion.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextDireccion.text")); // NOI18N
        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel9.text")); // NOI18N

        jTextFono.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextFono.text")); // NOI18N
        jTextFono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFonoKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel10.text")); // NOI18N

        jTextCelular.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jTextCelular.text")); // NOI18N
        jTextCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCelularActionPerformed(evt);
            }
        });
        jTextCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextCelularKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel12.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jLabel12.text")); // NOI18N

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inactivo", "Activo" }));
        jComboEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel35.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel35.text")); // NOI18N

        comitente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        comitente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comitente.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.comitente.text")); // NOI18N
        comitente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comitenteKeyPressed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel38.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel38.text")); // NOI18N

        codfais.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        codfais.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codfais.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.codfais.text")); // NOI18N
        codfais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codfaisKeyPressed(evt);
            }
        });

        fechaingreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaingresoKeyPressed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel43.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel43.text")); // NOI18N

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel52.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel52.text")); // NOI18N

        localidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        localidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        localidad.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.localidad.text")); // NOI18N
        localidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localidadActionPerformed(evt);
            }
        });

        barrio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        barrio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        barrio.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.barrio.text")); // NOI18N
        barrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barrioActionPerformed(evt);
            }
        });

        BuscarLocalidad.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BuscarLocalidad.text")); // NOI18N
        BuscarLocalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarLocalidadActionPerformed(evt);
            }
        });

        BuscarBarrio.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BuscarBarrio.text")); // NOI18N
        BuscarBarrio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarBarrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarBarrioActionPerformed(evt);
            }
        });

        nombrelocalidad.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.nombrelocalidad.text")); // NOI18N
        nombrelocalidad.setEnabled(false);

        nombrebarrio.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.nombrebarrio.text")); // NOI18N
        nombrebarrio.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(barrio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                                    .addComponent(localidad, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(BuscarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(nombrebarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(BuscarLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(nombrelocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextConyugue)
                                .addGap(18, 18, 18))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel52))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(26, 26, 26)
                                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(codfais, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(comitente, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jComboEstadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel12)))
                                .addGap(48, 48, 48)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFono, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 355, Short.MAX_VALUE))
                                    .addComponent(direccion)
                                    .addComponent(mail))))
                        .addGap(18, 18, 18)))
                .addComponent(jLabel43)
                .addGap(18, 18, 18)
                .addComponent(fechaingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jTextControl, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codfais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(fechaingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboEstadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(localidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel42)
                                .addComponent(BuscarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(barrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nombrelocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrebarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel45))
                    .addComponent(mail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout DatosGeneralesLayout = new javax.swing.GroupLayout(DatosGenerales);
        DatosGenerales.setLayout(DatosGeneralesLayout);
        DatosGeneralesLayout.setHorizontalGroup(
            DatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosGeneralesLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(681, Short.MAX_VALUE))
            .addGroup(DatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        DatosGeneralesLayout.setVerticalGroup(
            DatosGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jPanel1.TabConstraints.tabTitle"), DatosGenerales); // NOI18N

        Referencias.setToolTipText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.Referencias.toolTipText")); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel6.border.title"))); // NOI18N

        tablacuentas.setModel(modelocuenta);
        tablacuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablacuentasMousePressed(evt);
            }
        });
        tablacuentas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablacuentasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablacuentas);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nrocuenta.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.nrocuenta.text")); // NOI18N
        nrocuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrocuentaKeyPressed(evt);
            }
        });

        etiqueta2.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.etiqueta2.text")); // NOI18N

        BotonGrabarCta.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BotonGrabarCta.text")); // NOI18N
        BotonGrabarCta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonGrabarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGrabarCtaActionPerformed(evt);
            }
        });

        BotonBorrarCta.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BotonBorrarCta.text")); // NOI18N
        BotonBorrarCta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonBorrarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBorrarCtaActionPerformed(evt);
            }
        });

        iditem.setEditable(false);
        iditem.setEnabled(false);

        jLabel36.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel36.text")); // NOI18N

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        moneda.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.moneda.text")); // NOI18N
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

        Buscar.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.Buscar.text")); // NOI18N
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        nombremoneda.setEditable(false);
        nombremoneda.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.nombremoneda.text")); // NOI18N
        nombremoneda.setEnabled(false);
        nombremoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombremonedaKeyReleased(evt);
            }
        });

        banco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        banco.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.banco.text")); // NOI18N
        banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bancoActionPerformed(evt);
            }
        });

        buscarbanco.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.buscarbanco.text")); // NOI18N
        buscarbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarbancoActionPerformed(evt);
            }
        });

        jLabel37.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel37.text")); // NOI18N

        nombrebanco.setEditable(false);
        nombrebanco.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.nombrebanco.text")); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiqueta2)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombremoneda)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nrocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(BotonBorrarCta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BotonGrabarCta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(banco, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37)
                        .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarbanco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonGrabarCta)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(etiqueta2)
                        .addComponent(nrocuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(BotonBorrarCta)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(iditem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel7.border.title"))); // NOI18N

        tablacomercial.setModel(comercial);
        jScrollPane3.setViewportView(tablacomercial);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        referenciacomercio.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.referenciacomercio.text")); // NOI18N
        referenciacomercio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                referenciacomercioKeyReleased(evt);
            }
        });

        referenciafonocomercial.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.referenciafonocomercial.text")); // NOI18N

        GrabarRefComercial.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.GrabarRefComercial.text")); // NOI18N
        GrabarRefComercial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarRefComercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarRefComercialActionPerformed(evt);
            }
        });

        BorrarRefComercial.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BorrarRefComercial.text")); // NOI18N
        BorrarRefComercial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarRefComercial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarRefComercialActionPerformed(evt);
            }
        });

        jLabel46.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel46.text")); // NOI18N

        jLabel47.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel47.text")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(referenciacomercio)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(53, 53, 53)
                        .addComponent(GrabarRefComercial))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(referenciafonocomercial, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BorrarRefComercial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrabarRefComercial, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel46)
                        .addComponent(jLabel47)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenciacomercio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referenciafonocomercial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BorrarRefComercial))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout ReferenciasLayout = new javax.swing.GroupLayout(Referencias);
        Referencias.setLayout(ReferenciasLayout);
        ReferenciasLayout.setHorizontalGroup(
            ReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReferenciasLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        ReferenciasLayout.setVerticalGroup(
            ReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReferenciasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ReferenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.Referencias.TabConstraints.tabTitle"), Referencias); // NOI18N

        jLabel40.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel40.text")); // NOI18N

        jLabel41.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel41.text")); // NOI18N

        objetoempresa.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.objetoempresa.text")); // NOI18N
        objetoempresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                objetoempresaKeyPressed(evt);
            }
        });

        apoderado.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.apoderado.text")); // NOI18N
        apoderado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                apoderadoKeyPressed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

        directorio.setColumns(20);
        directorio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        directorio.setRows(5);
        jScrollPane4.setViewportView(directorio);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel12.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N

        observacion_cliente.setColumns(20);
        observacion_cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        observacion_cliente.setRows(5);
        jScrollPane5.setViewportView(observacion_cliente);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelJuridicoLayout = new javax.swing.GroupLayout(PanelJuridico);
        PanelJuridico.setLayout(PanelJuridicoLayout);
        PanelJuridicoLayout.setHorizontalGroup(
            PanelJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJuridicoLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(PanelJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelJuridicoLayout.createSequentialGroup()
                        .addGroup(PanelJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(apoderado, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                            .addComponent(objetoempresa))))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        PanelJuridicoLayout.setVerticalGroup(
            PanelJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJuridicoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(PanelJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(objetoempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelJuridicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(apoderado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.PanelJuridico.TabConstraints.tabTitle"), PanelJuridico); // NOI18N

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel11.border.title"))); // NOI18N

        jLabel48.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel48.text")); // NOI18N

        jLabel49.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel49.text")); // NOI18N

        jLabel50.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel50.text")); // NOI18N

        jLabel51.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel51.text")); // NOI18N

        imprimirtitular.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.imprimirtitular.text")); // NOI18N

        facturar_a_nombre.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.facturar_a_nombre.text")); // NOI18N
        facturar_a_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                facturar_a_nombreKeyPressed(evt);
            }
        });

        direccionfactura.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.direccionfactura.text")); // NOI18N
        direccionfactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionfacturaKeyPressed(evt);
            }
        });

        telefonofactura.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.telefonofactura.text")); // NOI18N
        telefonofactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                telefonofacturaKeyPressed(evt);
            }
        });

        rucfactura.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.rucfactura.text")); // NOI18N
        rucfactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rucfacturaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imprimirtitular, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(facturar_a_nombre)
                            .addComponent(direccionfactura, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                            .addComponent(telefonofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rucfactura, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(facturar_a_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(direccionfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(telefonofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(rucfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(imprimirtitular)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout UtilesLayout = new javax.swing.GroupLayout(Utiles);
        Utiles.setLayout(UtilesLayout);
        UtilesLayout.setHorizontalGroup(
            UtilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UtilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(406, Short.MAX_VALUE))
        );
        UtilesLayout.setVerticalGroup(
            UtilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UtilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.Utiles.TabConstraints.tabTitle"), Utiles); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonAbriArchivo.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.BotonAbriArchivo.text")); // NOI18N
        BotonAbriArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAbriArchivoActionPerformed(evt);
            }
        });

        etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqueta.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.etiqueta.text")); // NOI18N

        GuardarArchivo.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.GuardarArchivo.text")); // NOI18N
        GuardarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarArchivoActionPerformed(evt);
            }
        });

        jButton3.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jButton4.text")); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addGap(141, 141, 141)
                            .addComponent(jButton4))
                        .addComponent(etiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonAbriArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GuardarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonAbriArchivo)
                    .addComponent(nombrearchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(GuardarArchivo)))
                .addGap(33, 33, 33)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelImagenesLayout = new javax.swing.GroupLayout(PanelImagenes);
        PanelImagenes.setLayout(PanelImagenesLayout);
        PanelImagenesLayout.setHorizontalGroup(
            PanelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelImagenesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelImagenesLayout.setVerticalGroup(
            PanelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelImagenesLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.PanelImagenes.TabConstraints.tabTitle"), PanelImagenes); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel4.border.title"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel13.text")); // NOI18N

        jTextProfesion.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextProfesion.text")); // NOI18N
        jTextProfesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextProfesionActionPerformed(evt);
            }
        });
        jTextProfesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextProfesionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextProfesionKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel14.text")); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel15.text")); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel16.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel16.text")); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel17.text")); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel18.text")); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel19.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel19.text")); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel20.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel20.text")); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel21.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel21.text")); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel22.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel22.text")); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel23.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel23.text")); // NOI18N

        jTextLugarTrabajo.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextLugarTrabajo.text")); // NOI18N
        jTextLugarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextLugarTrabajoActionPerformed(evt);
            }
        });
        jTextLugarTrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextLugarTrabajoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextLugarTrabajoKeyReleased(evt);
            }
        });

        jTextCargoqueOcupa.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextCargoqueOcupa.text")); // NOI18N
        jTextCargoqueOcupa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextCargoqueOcupaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextCargoqueOcupaKeyReleased(evt);
            }
        });

        fechaingresofuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaingresofuncionarioKeyPressed(evt);
            }
        });

        jTextDireccionLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextDireccionLaboral.text")); // NOI18N
        jTextDireccionLaboral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDireccionLaboralKeyPressed(evt);
            }
        });

        jTextTelefonoLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextTelefonoLaboral.text")); // NOI18N
        jTextTelefonoLaboral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextTelefonoLaboralKeyPressed(evt);
            }
        });

        jTextFaxLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextFaxLaboral.text")); // NOI18N
        jTextFaxLaboral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFaxLaboralActionPerformed(evt);
            }
        });
        jTextFaxLaboral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFaxLaboralKeyPressed(evt);
            }
        });

        jTextMailLaboral.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jTextMailLaboral.text")); // NOI18N
        jTextMailLaboral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextMailLaboralActionPerformed(evt);
            }
        });

        Salario.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.Salario.text")); // NOI18N
        Salario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));

        SalarioConyugue.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.SalarioConyugue.text")); // NOI18N
        SalarioConyugue.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        otrosIngresos.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.otrosIngresos.text")); // NOI18N
        otrosIngresos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextDireccionLaboral)
                            .addComponent(jTextCargoqueOcupa)
                            .addComponent(jTextLugarTrabajo)
                            .addComponent(jTextProfesion)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(fechaingresofuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(90, 90, 90))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextTelefonoLaboral)
                        .addComponent(jTextFaxLaboral, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextMailLaboral, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addComponent(SalarioConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Salario, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(otrosIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextProfesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextLugarTrabajo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextCargoqueOcupa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fechaingresofuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextDireccionLaboral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextTelefonoLaboral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFaxLaboral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextMailLaboral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(Salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(SalarioConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(otrosIngresos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel5.border.title"))); // NOI18N

        jComboAsesor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboAsesor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel26.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel26.text")); // NOI18N

        LimiteCredito.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.LimiteCredito.text")); // NOI18N
        LimiteCredito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        LimiteCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LimiteCreditoKeyPressed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel27.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel27.text")); // NOI18N

        jComboCasaPropia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Alquiler", "Casa Propia", "Con Parientes" }));
        jComboCasaPropia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel28.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel28.text")); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel29.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel29.text")); // NOI18N

        jComboAuto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        jComboAuto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel31.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel31.text")); // NOI18N

        jComboInfor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NO", "SI" }));
        jComboInfor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboInfor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboInforActionPerformed(evt);
            }
        });

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane1.setViewportView(observaciones);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel33.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel33.text")); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel34.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel34.text")); // NOI18N

        plazocredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        plazocredito.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.plazocredito.text")); // NOI18N
        plazocredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plazocreditoKeyPressed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel39.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel39.text")); // NOI18N

        clientepep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO", "SI" }));
        clientepep.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        perfilinterno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Preservación del Capital", "Conservador", "Moderado", "Agresivo" }));

        inversorcalificado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel53.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel53.text")); // NOI18N

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel54.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jLabel54.text")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(plazocredito, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jComboAuto, javax.swing.GroupLayout.Alignment.LEADING, 0, 52, Short.MAX_VALUE)
                                    .addComponent(jComboInfor, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(53, 53, 53)
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)
                                .addComponent(clientepep, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(LimiteCredito, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboCasaPropia, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(inversorcalificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(perfilinterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(jComboAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plazocredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboCasaPropia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jComboAuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboInfor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39)
                    .addComponent(clientepep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(perfilinterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inversorcalificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DatosLaboralesLayout = new javax.swing.GroupLayout(DatosLaborales);
        DatosLaborales.setLayout(DatosLaboralesLayout);
        DatosLaboralesLayout.setHorizontalGroup(
            DatosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosLaboralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        DatosLaboralesLayout.setVerticalGroup(
            DatosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosLaboralesLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(DatosLaboralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jPanel4.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_clientes_fais.jPanel4.AccessibleContext.accessibleName")); // NOI18N

        Panel1Clientes.addTab(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jPanel2.TabConstraints.tabTitle"), DatosLaborales); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel1Clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 971, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(Panel1Clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton1.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jButton1.text")); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(org.openide.util.NbBundle.getMessage(detalle_clientes_fais.class, "detalle_asesores.jButton2.text")); // NOI18N
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(354, 354, 354))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LimpiarCta() {
        this.iditem.setText("0");
        this.banco.setText("0");
        this.nombrebanco.setText("");
        this.nrocuenta.setText("");
        this.moneda.setText("1");
        this.nombremoneda.setText("Guaraníes");
    }

    private void TitLocalidad() {
        modelolocalidad.addColumn("Código");
        modelolocalidad.addColumn("Nombre");

        int[] anchos = {90, 100};
        for (int i = 0; i < modelolocalidad.getColumnCount(); i++) {
            tablalocalidad.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamoneda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablalocalidad.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablalocalidad.setFont(font);
    }

    private void TitBarrio() {
        modelobarrio.addColumn("Código");
        modelobarrio.addColumn("Nombre");

        int[] anchos = {90, 100};
        for (int i = 0; i < modelobarrio.getColumnCount(); i++) {
            tablabarrio.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablabarrio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablabarrio.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablabarrio.setFont(font);
    }

    public void filtrobarrio(int nNumeroColumna) {
        trsfiltrobarrio.setRowFilter(RowFilter.regexFilter(this.txtbuscarbarrio.getText(), nNumeroColumna));
    }

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.BuscarMoneda.getText(), nNumeroColumna));
    }

    private void TitMoneda() {
        modelomoneda.addColumn("Código");
        modelomoneda.addColumn("Nombre");

        int[] anchos = {90, 100};
        for (int i = 0; i < modelomoneda.getColumnCount(); i++) {
            tablamoneda.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamoneda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamoneda.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablamoneda.setFont(font);
    }

    private void refrescarCarrusel(int p) {
        etiqueta.setText("");
        etiqueta.setIcon(null);
        albumfotoDAO imag = new albumfotoDAO();
        if (Integer.valueOf(this.codigo.getText()) > 0) {
            imagenes = imag.getImagenes(Integer.valueOf(this.codigo.getText()));
            if (p < imagenes.size()) {
                if (imagenes.size() > 0) {
                    ImageIcon icon = new ImageIcon(imagenes.get(p).getFoto().getScaledInstance(this.etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_DEFAULT));
                    etiqueta.setText("");
                    etiqueta.setIcon(icon);
                    nombrearchivo.setText(imagenes.get(p).getNombre());
                }
            } else {
                contador--;
            }
        }
    }

    private void CargarReferenciaComercial() {
        comercial.addColumn("Id");
        comercial.addColumn("Denominación");
        comercial.addColumn("Fono");
        int[] anchos = {70, 200, 100};
        for (int i = 0; i < comercial.getColumnCount(); i++) {
            tablacomercial.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacomercial.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacomercial.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacomercial.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.LEFT); // aqui defines donde alinear 
        this.tablacomercial.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void consultarTabla(String campoclave) {

        con = new Conexion();
        stm = con.conectar();

        //Inicializo Variables
        int nAsesor = 0;
        int nCategoria = 0;
        int nLocalidad = 0;
        int nBarrio = 0;
        String numero;
        int num;
        String nCodCategoria;
        String cCodLocalidad;
        String cAsesor;
        String cBarrio;
        ResultSet results = null;

        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = cliDAO.buscarIdCasaBolsa(Integer.valueOf(this.codigo.getText()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        if (cl != null) {
            nCategoria = cl.getCategoria();
            nLocalidad = cl.getLocalidad().getCodigo();
            nAsesor = cl.getAsesor();
            nBarrio = cl.getBarrio().getCodigo();
            this.nombre.setText(cl.getNombre());
            this.ruc.setText(cl.getRuc());
            this.cedula.setText(cl.getCedula());
            this.localidad.setText(String.valueOf(cl.getLocalidad().getCodigo()));
            this.nombrelocalidad.setText(cl.getLocalidad().getNombre());
            this.barrio.setText(String.valueOf(cl.getBarrio().getCodigo()));
            this.nombrebarrio.setText(cl.getBarrio().getNombre());
            this.jComboEstadocivil.setSelectedItem(cl.getEstadocivil());
            this.jTextConyugue.setText(cl.getConyugue());
            this.jDateNacimiento.setDate(cl.getFechanacimiento());
            this.direccion.setText(cl.getDireccion());
            this.jTextFono.setText(cl.getTelefono());
            this.jTextCelular.setText(cl.getCelular());
            this.mail.setText(cl.getMail());
            this.jComboEstado.setSelectedIndex(cl.getEstado());
            this.jTextProfesion.setText(cl.getProfesion());
            this.jTextLugarTrabajo.setText(cl.getLugartrabajo());
            this.jTextCargoqueOcupa.setText(cl.getCargolaboral());
            this.fechaingresofuncionario.setDate(cl.getFechaingresofuncionario());
            this.fechaingreso.setDate(cl.getFechaingreso());
            this.jTextDireccionLaboral.setText(cl.getDireccionlaboral());
            this.jTextTelefonoLaboral.setText(cl.getTelefonolaboral());
            this.jTextFaxLaboral.setText(cl.getFaxlaboral());
            this.jTextMailLaboral.setText(cl.getMaillaboral());
            this.Salario.setText(formato.format(cl.getSalario()));
            this.SalarioConyugue.setText(formato.format(cl.getSalarioconyugue()));
            this.otrosIngresos.setText(formato.format(cl.getOtrosingresos()));
            this.LimiteCredito.setText(formato.format(cl.getLimitecredito()));
            this.jComboCasaPropia.setSelectedIndex(cl.getCasapropia());
            this.jComboAuto.setSelectedIndex(cl.getAutopropio());
            this.jComboInfor.setSelectedIndex(cl.getInformconf());
            this.observaciones.setText(cl.getNotas());
            this.plazocredito.setText(String.valueOf(cl.getPlazocredito()));
            this.comitente.setText(String.valueOf(cl.getComitente()));
            this.codfais.setText(String.valueOf(cl.getCodfais()));
            this.clientepep.setSelectedItem(cl.getPep());
            this.apoderado.setText(cl.getApoderado());
            this.objetoempresa.setText(cl.getObjeto());
            this.directorio.setText(cl.getDirectorio());
            this.facturar_a_nombre.setText(cl.getFacturar_a_nombre());
            this.direccionfactura.setText(cl.getDireccionfactura());
            this.telefonofactura.setText(cl.getTelefonofactura());
            this.rucfactura.setText(cl.getRucfactura());
            this.observacion_cliente.setText(cl.getObservacion_cliente());
            this.perfilinterno.setSelectedItem(cl.getPerfilinterno());
            this.inversorcalificado.setSelectedItem(cl.getInversorcalificado());

            if (cl.getImprimirtitular() == 1) {
                imprimirtitular.setSelected(true);
            } else {
                imprimirtitular.setSelected(false);
            }

            //Con esta función mostramos los valores numericos con los puntos correspondientes
            nCodCategoria = selectCombo(jComboCategoria, "select * from categoria_clientes where codigo='" + nCategoria + "'");
            cAsesor = selectCombo(jComboAsesor, "select codigo,nombre from vendedores where estado=1 and codigo='" + nAsesor + "'");
        }
        //
    }

    public void limpiarCombos() {
        jComboCategoria.removeAllItems();
        BD.cargarCombo("select * from categoria_clientes", jComboCategoria);
        jComboCategoria.setSelectedIndex(0);

        jComboAsesor.removeAllItems();
        BD.cargarCombo("select codigo,nombre from vendedores where estado=1", jComboAsesor);

    }

    public String selectCombo(JComboBox combo, String sql) {
        ResultSet codi = null;
        ComboBoxModel modelo = combo.getModel();
        int longitud = combo.getItemCount();
        int cod = 0;
        int c = 0;
        String codigo = "";
        Object ob = null;
        codi = BD.Select(sql);
        try {
            codi.first();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        while (c < longitud) {
            combo.setSelectedIndex(c);
            ob = combo.getSelectedItem();
            cod = Integer.valueOf(((String[]) ob)[0]).intValue();
            try {
                if (cod == codi.getInt(1)) {
                    codigo = Integer.valueOf(cod).toString();
                    c = longitud;
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            c++;
        }
        return codigo;
    }

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "";
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (this.nombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nombre o Denominación del Cliente");
            this.nombre.requestFocus();
            return;
        }
        if (this.ruc.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el RUC O CI del Cliente");
            this.ruc.requestFocus();
            return;
        }
        if (this.direccion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Dirección Particular del Cliente");
            this.direccion.requestFocus();
            return;
        }

        if (this.jTextCelular.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Celular");
            this.jTextCelular.requestFocus();
            return;
        }
        if (this.jTextFono.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Línea Baja");
            this.jTextFono.requestFocus();
            return;
        }

        if (this.jTextLugarTrabajo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Lugar de Trabajo");
            this.jTextLugarTrabajo.requestFocus();
            return;
        }

        if (this.jTextProfesion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Profesión");
            this.jTextProfesion.requestFocus();
            return;
        }

        if (this.jTextCargoqueOcupa.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Cargo que Ocupa");
            this.jTextCargoqueOcupa.requestFocus();
            return;
        }

        if (this.jTextDireccionLaboral.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Dirección Laboral");
            this.jTextDireccionLaboral.requestFocus();
            return;
        }

        if (this.jTextTelefonoLaboral.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Teléfono Laboral");
            this.jTextTelefonoLaboral.requestFocus();
            return;
        }

        ControlGrabado.REGISTRO_GRABADO = "SI";
        BDConexion BD = new BDConexion();

        Date FechaNac = ODate.de_java_a_sql(this.jDateNacimiento.getDate()); // Formateamos los campos de fecha
        Date dFechaIngresoFuncionario = ODate.de_java_a_sql(this.fechaingresofuncionario.getDate());
        Date dFechaIngreso = ODate.de_java_a_sql(this.fechaingreso.getDate());

        Object objCategoria = this.jComboCategoria.getSelectedItem();
        Object objAsesor = this.jComboAsesor.getSelectedItem();

        String barrio = this.barrio.getText();
        String Categoria = ((String[]) objCategoria)[0];
        String Localidad = this.localidad.getText();
        String asesor = ((String[]) objAsesor)[0];

        // Formateamos el campo de salario para enviarlo a la base de datos
        String salario = this.Salario.getText();
        // con el metodo replace borramos los puntos y remplazamos la coma decimal
        // por el punto para su correcta captura en la base de datos
        if (this.Salario.getText().trim().length() > 0) {
            salario = salario.replace(".", "");
            salario = salario.replace(",", ".");
        } else {
            salario = "0";
        }

        // Formateamos el campo de salario del conyugye para enviarlo a la base de datos
        String salarioconyugue = this.SalarioConyugue.getText();
        // con el metodo replace borramos los puntos y remplazamos la coma decimal
        // por el punto para su correcta captura en la base de datos
        if (this.SalarioConyugue.getText().trim().length() > 0) {
            salarioconyugue = salarioconyugue.replace(".", "");
            salarioconyugue = salarioconyugue.replace(",", ".");
        } else {
            salarioconyugue = "0";
        }

        String otrosingresos = this.otrosIngresos.getText();
        if (this.otrosIngresos.getText().trim().length() > 0) {
            otrosingresos = otrosingresos.replace(".", "");
            otrosingresos = otrosingresos.replace(",", ".");
        } else {
            otrosingresos = "0";
        }

        String limitecredito = this.LimiteCredito.getText();
        if (limitecredito.trim().length() > 0) {
            limitecredito = limitecredito.replace(".", "");
            limitecredito = limitecredito.replace(",", ".");
        } else {
            limitecredito = "0";
        }

        int nimprimirtitular = 0;
        if (imprimirtitular.isSelected()) {
            nimprimirtitular = 1;
        } else {
            nimprimirtitular = 0;
        }

        String cParametro = this.jTextControl.getText();
        String cLetra = "new";
        if (cParametro.equals(cLetra)) {
            if (this.codigo.getText().trim().length() <= 1) {
                BD.insertarRegistro("clientes", "nombre,cedula,ruc,fechanacimiento,estadocivil,"
                        + "conyugue,direccion,telefono,celular,mail,estado,"
                        + "categoria,localidad,salario,salarioconyugue,"
                        + "otrosingresos,profesion,lugartrabajo,cargolaboral,"
                        + "direccionlaboral,fechaingresofuncionario,fechaingreso,telefonolaboral,"
                        + "faxlaboral,maillaboral,limitecredito,casapropia,"
                        + "autopropio,informconf,asesor,notas,barrio,"
                        + "plazocredito,comitente,codfais,pep,"
                        + "objeto,apoderado,directorio,facturar_a_nombre,direccionfactura,"
                        + "telefonofactura,rucfactura,imprimirtitular,observacion_cliente,perfilinterno,inversorcalificado", "'"
                        + this.nombre.getText() + "','" + this.cedula.getText()
                        + "','" + this.ruc.getText() + "','" + FechaNac
                        + "','" + this.jComboEstadocivil.getSelectedItem()
                        + "','" + this.jTextConyugue.getText()
                        + "','" + this.direccion.getText()
                        + "','" + this.jTextFono.getText()
                        + "','" + this.jTextCelular.getText()
                        + "','" + this.mail.getText()
                        + "','" + this.jComboEstado.getSelectedIndex()
                        + "','" + Categoria + "','" + Localidad
                        + "','" + salario + "','" + salarioconyugue
                        + "','" + otrosingresos + "','" + this.jTextProfesion.getText()
                        + "','" + this.jTextLugarTrabajo.getText() + "','"
                        + this.jTextCargoqueOcupa.getText() + "','"
                        + this.jTextDireccionLaboral.getText() + "','"
                        + dFechaIngresoFuncionario + "','" + dFechaIngreso
                        + "','" + this.jTextTelefonoLaboral.getText()
                        + "','" + this.jTextFaxLaboral.getText() + "','"
                        + this.jTextMailLaboral.getText() + "','"
                        + limitecredito + "','" + this.jComboCasaPropia.getSelectedIndex()
                        + "','" + this.jComboAuto.getSelectedIndex() + "','"
                        + this.jComboInfor.getSelectedIndex() + "','" + asesor + "','"
                        + this.observaciones.getText().trim() + "','" + barrio + "','"
                        + this.plazocredito.getText() + "','" + this.comitente.getText()
                        + "','" + this.codfais.getText()
                        + "','" + this.clientepep.getSelectedItem().toString()
                        + "','" + objetoempresa.getText()
                        + "','" + apoderado.getText()
                        + "','" + directorio.getText()
                        + "','" + facturar_a_nombre.getText()
                        + "','" + direccionfactura.getText()
                        + "','" + telefonofactura.getText()
                        + "','" + rucfactura.getText()
                        + "','" + nimprimirtitular
                        + "','" + observacion_cliente.getText()
                        + "','" + this.perfilinterno.getSelectedItem().toString()
                        + "','" + this.inversorcalificado.getSelectedItem().toString()
                        + "'");
            } else {
                BD.insertarRegistro("clientes", "codigo,nombre,ruc,fechanacimiento,"
                        + "estadocivil,conyugue,direccion,telefono,celular,mail,"
                        + "estado,categoria,localidad,salario,"
                        + "salarioconyugue,otrosingresos,profesion,"
                        + "lugartrabajo,cargolaboral,direccionlaboral,"
                        + "fechaingreso,telefonolaboral,faxlaboral,"
                        + "maillaboral,limitecredito,casapropia,autopropio,"
                        + "informconf,asesor,notas,barrio,plazocredito,"
                        + "comitente,codfais,pep,objeto,apoderado,directorio,"
                        + "facturar_a_nombre,direccionfactura,"
                        + "telefonofactura,rucfactura,imprimirtitular,observacion_cliente,perfilinterno,inversorcalificado", "'"
                        + this.codigo.getText() + "','" + this.nombre.getText()
                        + "','" + this.ruc.getText() + "','" + FechaNac
                        + "','" + this.jComboEstadocivil.getSelectedItem()
                        + "','" + this.jTextConyugue.getText()
                        + "','" + this.direccion.getText()
                        + "','" + this.jTextFono.getText()
                        + "','" + this.jTextCelular.getText()
                        + "','" + this.mail.getText()
                        + "','" + this.jComboEstado.getSelectedIndex()
                        + "','" + Categoria + "','" + Localidad
                        + "','" + salario + "','" + salarioconyugue
                        + "','" + otrosingresos + "','" + this.jTextProfesion.getText()
                        + "','" + this.jTextLugarTrabajo.getText()
                        + "','" + this.jTextCargoqueOcupa.getText()
                        + "','" + this.jTextDireccionLaboral.getText()
                        + dFechaIngresoFuncionario + "','" + dFechaIngreso
                        + "','" + this.jTextTelefonoLaboral.getText()
                        + "','" + this.jTextFaxLaboral.getText() + "','" + this.jTextMailLaboral.getText()
                        + "','" + limitecredito + "','" + this.jComboCasaPropia.getSelectedIndex()
                        + "','" + this.jComboAuto.getSelectedIndex()
                        + "','" + this.jComboInfor.getSelectedIndex()
                        + "','" + asesor + "','" + this.observaciones.getText().trim()
                        + "','" + barrio + "','" + this.plazocredito.getText()
                        + "','" + this.comitente.getText() + "','" + this.codfais.getText()
                        + "','" + this.clientepep.getSelectedItem().toString()
                        + "','" + objetoempresa.getText()
                        + "','" + apoderado.getText()
                        + "','" + directorio.getText()
                        + "','" + facturar_a_nombre.getText()
                        + "','" + direccionfactura.getText()
                        + "','" + telefonofactura.getText()
                        + "','" + rucfactura.getText()
                        + "','" + nimprimirtitular
                        + "','" + observacion_cliente.getText()
                        + "','" + this.perfilinterno.getSelectedItem().toString()
                        + "','" + this.inversorcalificado.getSelectedItem().toString()
                        + "'");
            }
        } else {
            BD.actualizarRegistro("clientes", " nombre='" + this.nombre.getText() + "',cedula='"
                    + this.cedula.getText() + "',comitente='"
                    + comitente.getText() + "',codfais='"
                    + codfais.getText() + "',plazocredito='"
                    + plazocredito.getText() + "',ruc='"
                    + this.ruc.getText() + "',fechanacimiento='" + FechaNac
                    + "',estadocivil='" + this.jComboEstadocivil.getSelectedItem()
                    + "',pep='" + this.clientepep.getSelectedItem().toString()
                    + "',objeto='" + this.objetoempresa.getText()
                    + "',apoderado='" + this.apoderado.getText()
                    + "',directorio='" + this.directorio.getText()
                    + "',facturar_a_nombre='" + this.facturar_a_nombre.getText()
                    + "',direccionfactura='" + this.direccionfactura.getText()
                    + "',telefonofactura='" + this.telefonofactura.getText()
                    + "',rucfactura='" + this.rucfactura.getText()
                    + "',imprimirtitular='" + nimprimirtitular
                    + "',conyugue='" + this.jTextConyugue.getText() + "',direccion='"
                    + this.direccion.getText().trim()
                    + "',telefono='" + this.jTextFono.getText().trim()
                    + "',celular='" + this.jTextCelular.getText() + "',mail='"
                    + this.mail.getText() + "',estado ='"
                    + this.jComboEstado.getSelectedIndex() + "',localidad ='"
                    + Localidad + "',categoria ='" + Categoria + "',salario ='"
                    + salario + "',salarioconyugue ='" + salarioconyugue
                    + "',otrosingresos ='" + otrosingresos + "',profesion= '"
                    + this.jTextProfesion.getText() + "',lugartrabajo='"
                    + this.jTextLugarTrabajo.getText() + "',cargolaboral='"
                    + this.jTextCargoqueOcupa.getText() + "',direccionlaboral='"
                    + this.jTextDireccionLaboral.getText() + "',fechaingresofuncionario='"
                    + dFechaIngresoFuncionario + "',fechaingreso='" + dFechaIngreso
                    + "',telefonolaboral='" + this.jTextTelefonoLaboral.getText()
                    + "',faxlaboral= '" + this.jTextFaxLaboral.getText()
                    + "',maillaboral='" + this.jTextMailLaboral.getText()
                    + "',limitecredito='" + limitecredito + "',casapropia='"
                    + this.jComboCasaPropia.getSelectedIndex() + "',autopropio='"
                    + this.jComboAuto.getSelectedIndex() + "',informconf='"
                    + this.jComboInfor.getSelectedIndex() + "',asesor='"
                    + asesor + "',barrio='" + barrio + "',notas ='"
                    + this.observaciones.getText().trim()
                    + "',perfilinterno='" + this.perfilinterno.getSelectedItem().toString()
                    + "',inversorcalificado='" + this.inversorcalificado.getSelectedItem().toString()
                    + "',observacion_cliente ='" + this.observacion_cliente.getText().trim() + "'",
                    "codigo= " + this.codigo.getText());
        }
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextMailLaboralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextMailLaboralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextMailLaboralActionPerformed

    private void jTextFaxLaboralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFaxLaboralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFaxLaboralActionPerformed

    private void jTextLugarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextLugarTrabajoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLugarTrabajoActionPerformed

    private void jTextProfesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextProfesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProfesionActionPerformed

    private void jComboInforActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboInforActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboInforActionPerformed

    private void jTextProfesionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextProfesionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextLugarTrabajo.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProfesionKeyPressed

    private void jTextLugarTrabajoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextLugarTrabajoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextCargoqueOcupa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextProfesion.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLugarTrabajoKeyPressed

    private void jTextCargoqueOcupaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCargoqueOcupaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechaingresofuncionario.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextLugarTrabajo.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:

    }//GEN-LAST:event_jTextCargoqueOcupaKeyPressed

    private void fechaingresofuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaingresofuncionarioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextDireccionLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextCargoqueOcupa.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_fechaingresofuncionarioKeyPressed

    private void jTextDireccionLaboralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDireccionLaboralKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextTelefonoLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechaingresofuncionario.requestFocus();
        }   // TODO add your handling code here:       
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDireccionLaboralKeyPressed

    private void jTextTelefonoLaboralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTelefonoLaboralKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextFaxLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextDireccionLaboral.requestFocus();
        }   // TODO add your handling code here:               // TODO add your handling code here:
    }//GEN-LAST:event_jTextTelefonoLaboralKeyPressed

    private void jTextFaxLaboralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFaxLaboralKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextMailLaboral.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextFaxLaboral.requestFocus();
        }   // TODO add your handling code here:           // TODO add your handling code here:
    }//GEN-LAST:event_jTextFaxLaboralKeyPressed

    private void jTextProfesionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextProfesionKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextProfesion);
        jTextProfesion.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextProfesionKeyReleased

    private void jTextLugarTrabajoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextLugarTrabajoKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextLugarTrabajo);
        jTextLugarTrabajo.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLugarTrabajoKeyReleased

    private void jTextCargoqueOcupaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCargoqueOcupaKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextCargoqueOcupa);
        jTextCargoqueOcupa.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCargoqueOcupaKeyReleased

    private void BotonBorrarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBorrarCtaActionPerformed
        int a = this.tablacuentas.getSelectedRow();
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            ctacteclienteDAO borrar = new ctacteclienteDAO();
            try {
                borrar.eliminarMateria(Integer.valueOf(iditem.getText()));
                JOptionPane.showMessageDialog(null, "Registro Eliminado Exitósamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.CargarCta(Integer.valueOf(codigo.getText()));
            this.LimpiarCta();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonBorrarCtaActionPerformed

    private void BotonGrabarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGrabarCtaActionPerformed
        if (this.banco.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Entidad Bancaria");
            this.banco.requestFocus();
            return;
        }
        if (this.nrocuenta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de Cuenta");
            this.nrocuenta.requestFocus();
            return;
        }

        if (this.moneda.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Moneda");
            this.moneda.requestFocus();
            return;
        }

        ctacteclienteDAO grabar = new ctacteclienteDAO();
        ctactecliente mat = new ctactecliente();
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        monedaDAO mnDAO = new monedaDAO();
        moneda mn = null;
        bancoplazaDAO baDAO = new bancoplazaDAO();
        bancoplaza ba = null;

        try {
            cl = clDAO.buscarIdSimple(Integer.valueOf(codigo.getText()));
            mn = mnDAO.buscarId(Integer.valueOf(moneda.getText()));
            ba = baDAO.buscarId(Integer.valueOf(banco.getText()));

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        //Clase de Cliente porque tiene que hacer referencia al cliente
        mat.setIditem(Integer.valueOf(this.iditem.getText()));
        mat.setBancos(ba);
        mat.setNrocuenta(nrocuenta.getText());
        mat.setCliente(cl);
        mat.setMoneda(mn);
        try {
            if (mat.getIditem() == 0) {
                grabar.insertarCta(mat);
            } else {
                grabar.actualizarCta(mat);
            }
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.LimpiarCta();
        this.nombrebanco.requestFocus();
        this.CargarCta(Integer.valueOf(codigo.getText()));

    }//GEN-LAST:event_BotonGrabarCtaActionPerformed

    private void TituloBanco() {
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

    private void CargarCta(int cliente) {
        int cantidadRegistro = modelocuenta.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocuenta.removeRow(0);
        }
        ctacteclienteDAO detDAO = new ctacteclienteDAO();
        try {
            for (ctactecliente detvta : detDAO.todosxCliente(cliente)) {
                String Detalle[] = {detvta.getBancos().getNombre(), detvta.getNrocuenta(),
                    detvta.getMoneda().getNombre(), formatosinpunto.format(detvta.getIditem()),
                    formatosinpunto.format(detvta.getMoneda().getCodigo()),
                    formatosinpunto.format(detvta.getBancos().getCodigo())};
                modelocuenta.addRow(Detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
        int cantFilas = tablacuentas.getRowCount();
        if (cantFilas > 0) {
            BotonGrabarCta.setEnabled(true);
            BotonBorrarCta.setEnabled(true);
        } else {
            BotonGrabarCta.setEnabled(true);
            BotonBorrarCta.setEnabled(false);
        }

    }


    private void referenciacomercioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_referenciacomercioKeyReleased
        String letras = ConvertirMayusculas.cadena(referenciacomercio);
        referenciacomercio.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_referenciacomercioKeyReleased

    private void GrabarRefComercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarRefComercialActionPerformed
        if (this.referenciacomercio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Denominación del Comercio");
            this.referenciacomercio.requestFocus();
            return;
        }
        if (this.referenciafonocomercial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese un Nro. de Teléfono");
            this.referenciafonocomercial.requestFocus();
            return;
        }

        referencia_comercialDAO grabarC = new referencia_comercialDAO();
        referencia_comercial C = new referencia_comercial();
        //Clase de Cliente porque tiene que hacer referencia al cliente
        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = cliDAO.buscarId(Integer.valueOf(this.codigo.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        C.setIdcliente(cl);
        C.setDescripcion(this.referenciacomercio.getText());
        C.setTelefono(this.referenciafonocomercial.getText());
        try {
            grabarC.insertarRefComercial(C);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        GrillaComercial GrillaC = new GrillaComercial();
        Thread Hilo1 = new Thread(GrillaC);
        Hilo1.start();
        this.referenciacomercio.setText("");
        this.referenciafonocomercial.setText("");
        this.referenciacomercio.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarRefComercialActionPerformed

    private void BorrarRefComercialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarRefComercialActionPerformed
        int a = this.tablacomercial.getSelectedRow();
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar el Registro ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            referencia_comercialDAO borrarC = new referencia_comercialDAO();
            String cItemBorrar = this.tablacomercial.getValueAt(a, 0).toString();
            try {
                borrarC.borrarRefComercial(Integer.valueOf(cItemBorrar));
                JOptionPane.showMessageDialog(null, "Registro Eliminado Exitósamente");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            GrillaComercial GrillaC = new GrillaComercial();
            Thread Hilo1 = new Thread(GrillaC);
            Hilo1.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarRefComercialActionPerformed

    private void BotonAbriArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAbriArchivoActionPerformed
        final JFileChooser elegirImagen = new JFileChooser();
        elegirImagen.setMultiSelectionEnabled(false);
        int o = elegirImagen.showOpenDialog(this);
        if (o == JFileChooser.APPROVE_OPTION) {
            String ruta = elegirImagen.getSelectedFile().getAbsolutePath();
            String cNombre = elegirImagen.getSelectedFile().getName();
            nombrearchivo.setText(ruta);
            Image preview = Toolkit.getDefaultToolkit().getImage(ruta);
            if (preview != null) {
                etiqueta.setText("");
                ImageIcon icon = new ImageIcon(preview.getScaledInstance(etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_DEFAULT));
                etiqueta.setIcon(icon);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BotonAbriArchivoActionPerformed

    private void GuardarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarArchivoActionPerformed
        albumfotoDAO GrabarImagen = new albumfotoDAO();
        albumfoto alb = new albumfoto();
        clienteDAO clie = new clienteDAO();
        cliente cl = null;
        try {
            cl = clie.buscarId(Integer.valueOf(codigo.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        alb.setCodigo(cl);
        alb.setNombre(nombrearchivo.getText().toString());
        try {
            GrabarImagen.insertarimagen(alb, nombrearchivo.getText().toString());
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            nombrearchivo.setText("");
            etiqueta.setIcon(null);
            refrescarCarrusel(0);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarArchivoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if ((contador - 1) >= 0) {
            contador--;
            refrescarCarrusel(contador);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        contador++;
        refrescarCarrusel(contador);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void LimiteCreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LimiteCreditoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.plazocredito.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboAsesor.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_LimiteCreditoKeyPressed

    private void plazocreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plazocreditoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboCasaPropia.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.LimiteCredito.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:

    }//GEN-LAST:event_plazocreditoKeyPressed

    private void tablacuentasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacuentasKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_UP) {
            int nFila = this.tablacuentas.getSelectedRow();
            this.iditem.setText(this.tablacuentas.getValueAt(nFila, 3).toString());
            this.nombrebanco.setText(this.tablacuentas.getValueAt(nFila, 0).toString());
            this.nrocuenta.setText(this.tablacuentas.getValueAt(nFila, 1).toString());
            this.moneda.setText(this.tablacuentas.getValueAt(nFila, 4).toString());
            this.nombremoneda.setText(this.tablacuentas.getValueAt(nFila, 2).toString());
            this.banco.setText(this.tablacuentas.getValueAt(nFila, 5).toString());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacuentasKeyReleased

    private void tablacuentasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacuentasMousePressed
        int nFila = this.tablacuentas.getSelectedRow();
        this.iditem.setText(this.tablacuentas.getValueAt(nFila, 3).toString());
        this.nombrebanco.setText(this.tablacuentas.getValueAt(nFila, 0).toString());
        this.nrocuenta.setText(this.tablacuentas.getValueAt(nFila, 1).toString());
        this.moneda.setText(this.tablacuentas.getValueAt(nFila, 4).toString());
        this.nombremoneda.setText(this.tablacuentas.getValueAt(nFila, 2).toString());
        this.banco.setText(this.tablacuentas.getValueAt(nFila, 5).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacuentasMousePressed

    private void nombremonedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombremonedaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nombremonedaKeyReleased

    private void combomonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combomonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomonedaActionPerformed

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private void BuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarMonedaKeyPressed
        this.BuscarMoneda.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (BuscarMoneda.getText()).toUpperCase();
                BuscarMoneda.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combomoneda.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtromoneda(indiceColumnaTabla);
            }
        });
        trsfiltromoneda = new TableRowSorter(tablamoneda.getModel());
        tablamoneda.setRowSorter(trsfiltromoneda);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaKeyPressed

    private void tablamonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamonedaMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaMouseClicked

    private void tablamonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamonedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablamoneda.getSelectedRow();
        this.moneda.setText(this.tablamoneda.getValueAt(nFila, 0).toString());
        this.nombremoneda.setText(this.tablamoneda.getValueAt(nFila, 1).toString());

        this.BMoneda.setVisible(false);
        BMoneda.setModal(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        monedaDAO monDAO = new monedaDAO();
        moneda mo = null;
        try {
            mo = monDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mo.getCodigo() == 0) {
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mo.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        BotonGrabarCta.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarActionPerformed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        Buscar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void nrocuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrocuentaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombrebanco.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nrocuentaKeyPressed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.BotonGrabarCta.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocuenta.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void combobancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobancoActionPerformed

    private void jTBuscarbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarbancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarbancoActionPerformed

    private void jTBuscarbancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarbancoKeyPressed
        this.jTBuscarbanco.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarbanco.getText()).toUpperCase();
                jTBuscarbanco.setText(cadena);
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
        trsfiltrobanco = new TableRowSorter(tablabanco.getModel());
        tablabanco.setRowSorter(trsfiltrobanco);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarbancoKeyPressed

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.jTBuscarbanco.getText(), nNumeroColumna));
    }

    private void tablabancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.banco.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.BBancos.setVisible(false);
        this.jTBuscarbanco.setText("");
        this.nrocuenta.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.BBancos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    private void buscarbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarbancoActionPerformed
        bancoplazaDAO bDAO = new bancoplazaDAO();
        bancoplaza bco = null;
        try {
            bco = bDAO.buscarId(Integer.valueOf(this.banco.getText()));
            if (bco.getCodigo() == 0) {
                BBancos.setModal(true);
                BBancos.setSize(482, 575);
                BBancos.setLocationRelativeTo(null);
                BBancos.setVisible(true);
                BBancos.setModal(true);
            } else {
                nombrebanco.setText(bco.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }        // TODO add your handling code here:
        // TODO add your handling code here:
        nrocuenta.requestFocus();

    }//GEN-LAST:event_buscarbancoActionPerformed

    private void bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bancoActionPerformed
        buscarbanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_bancoActionPerformed

    private void objetoempresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_objetoempresaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.apoderado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.directorio.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_objetoempresaKeyPressed

    private void apoderadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apoderadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.directorio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.objetoempresa.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_apoderadoKeyPressed

    private void facturar_a_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_facturar_a_nombreKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.direccionfactura.requestFocus();
        }
    }//GEN-LAST:event_facturar_a_nombreKeyPressed

    private void direccionfacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionfacturaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.telefonofactura.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.facturar_a_nombre.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionfacturaKeyPressed

    private void telefonofacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonofacturaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.rucfactura.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.direccionfactura.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonofacturaKeyPressed

    private void rucfacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucfacturaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.imprimirtitular.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.telefonofactura.requestFocus();
        }   // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_rucfacturaKeyPressed

    private void fechaingresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaingresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nombre.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comitente.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_fechaingresoKeyPressed

    private void codfaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codfaisKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.comitente.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codfaisKeyPressed

    private void comitenteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comitenteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechaingreso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codfais.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_comitenteKeyPressed

    private void jTextCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCelularKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.mail.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextFono.requestFocus();
        }   // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCelularKeyPressed

    private void jTextCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCelularActionPerformed

    private void jTextFonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFonoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextCelular.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.direccion.requestFocus();
        }   // TODO add your handling code here:
    }//GEN-LAST:event_jTextFonoKeyPressed

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextFono.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.localidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionKeyPressed

    private void mailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mailKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboEstado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextCelular.requestFocus();
        }   // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_mailKeyPressed

    private void jDateNacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateNacimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.localidad.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextConyugue.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateNacimientoKeyPressed

    private void jTextConyugueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextConyugueKeyReleased
        String letras = ConvertirMayusculas.cadena(jTextConyugue);
        jTextConyugue.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextConyugueKeyReleased

    private void jTextConyugueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextConyugueKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jDateNacimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboEstadocivil.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextConyugueKeyPressed

    private void cedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cedulaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ruc.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboCategoria.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cedulaKeyPressed

    private void jComboEstadocivilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboEstadocivilKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextConyugue.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ruc.requestFocus();
        }   // TODO add your handling code
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboEstadocivilKeyPressed

    private void rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboEstadocivil.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cedula.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_rucKeyPressed

    private void jComboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboCategoriaActionPerformed

    private void nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyReleased

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed
        //Pasar el foco a otro objeto con la tecla Enter o cursor
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboCategoria.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comitente.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyPressed

    private void nombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreFocusLost
        String letras = ConvertirMayusculas.cadena(nombre);
        nombre.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreFocusLost

    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nombre.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyPressed

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
        if (Double.valueOf(this.codigo.getText()) != 0) {
            CalcularRuc rucDigito = new CalcularRuc();
            String cCodigo = this.codigo.getText();
            int base = 11;
            int digito = rucDigito.CalcularDigito(cCodigo, base);
            ruc.setText(cCodigo.toString() + '-' + String.valueOf(digito));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoFocusLost

    private void combolocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combolocalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combolocalidadActionPerformed

    private void txtbuscarlocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarlocalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarlocalidadActionPerformed

    private void txtbuscarlocalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarlocalidadKeyPressed
        this.txtbuscarlocalidad.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtbuscarlocalidad.getText()).toUpperCase();
                txtbuscarlocalidad.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combolocalidad.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrolocalidad(indiceColumnaTabla);
            }
        });
        trsfiltrolocalidad = new TableRowSorter(tablalocalidad.getModel());
        tablalocalidad.setRowSorter(trsfiltrolocalidad);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarlocalidadKeyPressed

    public void filtrolocalidad(int nNumeroColumna) {
        trsfiltrolocalidad.setRowFilter(RowFilter.regexFilter(this.txtbuscarlocalidad.getText(), nNumeroColumna));
    }


    private void tablalocalidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablalocalidadMouseClicked
        this.AceptarVendedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablalocalidadMouseClicked

    private void tablalocalidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablalocalidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarVendedor.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablalocalidadKeyPressed

    private void AceptarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarVendedorActionPerformed
        int nFila = this.tablalocalidad.getSelectedRow();
        this.localidad.setText(this.tablalocalidad.getValueAt(nFila, 0).toString());
        this.nombrelocalidad.setText(this.tablalocalidad.getValueAt(nFila, 1).toString());

        this.BLocalidad.setVisible(false);
        this.txtbuscarlocalidad.setText("");
        this.barrio.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BLocalidad.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void BuscarLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarLocalidadActionPerformed
        BLocalidad.setModal(true);
        BLocalidad.setSize(482, 575);
        BLocalidad.setLocationRelativeTo(null);
        BLocalidad.setVisible(true);
        BLocalidad.setTitle("Buscar Localidad");
        BLocalidad.setModal(false);
        this.barrio.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarLocalidadActionPerformed

    private void localidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localidadActionPerformed

        localidadDAO loDAO = new localidadDAO();
        localidad ve = null;
        try {
            ve = loDAO.buscarLocalidad(Integer.valueOf(this.localidad.getText()));
            if (ve.getCodigo() == 0) {
                this.BuscarLocalidad.doClick();
                this.barrio.requestFocus();
            } else {
                nombrelocalidad.setText(ve.getNombre());
            }
            barrio.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_localidadActionPerformed

    private void combobarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobarrioActionPerformed

    private void txtbuscarbarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarbarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarbarrioActionPerformed

    private void txtbuscarbarrioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarbarrioKeyPressed
        this.txtbuscarbarrio.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtbuscarbarrio.getText()).toUpperCase();
                txtbuscarbarrio.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combobarrio.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrobarrio(indiceColumnaTabla);
            }
        });
        trsfiltrobarrio = new TableRowSorter(tablabarrio.getModel());
        tablabarrio.setRowSorter(trsfiltrobarrio);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbuscarbarrioKeyPressed

    private void tablabarrioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabarrioMouseClicked
        this.AceptarBarrio.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabarrioMouseClicked

    private void tablabarrioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabarrioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.AceptarBarrio.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabarrioKeyPressed

    private void AceptarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarBarrioActionPerformed
        int nFila = this.tablabarrio.getSelectedRow();
        this.barrio.setText(this.tablabarrio.getValueAt(nFila, 0).toString());
        this.nombrebarrio.setText(this.tablabarrio.getValueAt(nFila, 1).toString());
        this.BBarrio.setVisible(false);
        this.BBarrio.setModal(false);
        this.direccion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarBarrioActionPerformed

    private void SalirBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirBarrioActionPerformed
        this.BBarrio.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirBarrioActionPerformed

    private void BuscarBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarBarrioActionPerformed
        GrillaBarrio grillabarrio = new GrillaBarrio();
        Thread hilobarrio = new Thread(grillabarrio);
        hilobarrio.start();
        BBarrio.setModal(true);
        BBarrio.setSize(482, 575);
        BBarrio.setLocationRelativeTo(null);
        BBarrio.setVisible(true);
        BBarrio.setTitle("Buscar Localidad");
        BBarrio.setModal(false);
        this.direccion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarBarrioActionPerformed

    private void barrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barrioActionPerformed
        barrioDAO baDAO = new barrioDAO();
        barrio ba = null;
        try {
            ba = baDAO.buscarIdxLocalidad(Integer.valueOf(this.barrio.getText()),Integer.valueOf(this.localidad.getText()));
            if (ba.getCodigo() == 0) {
                this.BuscarBarrio.doClick();
                this.direccion.requestFocus();
            } else {
                nombrebarrio.setText(ba.getNombre());
            }
            direccion.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_barrioActionPerformed

    public void MostrarDatos(String Operacion) {
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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detalle_asesores.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //    new detalle_asesores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarBarrio;
    private javax.swing.JButton AceptarCasa;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JDialog BBancos;
    private javax.swing.JDialog BBarrio;
    private javax.swing.JDialog BLocalidad;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JButton BorrarRefComercial;
    private javax.swing.JButton BotonAbriArchivo;
    private javax.swing.JButton BotonBorrarCta;
    private javax.swing.JButton BotonGrabarCta;
    private javax.swing.JButton Buscar;
    private javax.swing.JButton BuscarBarrio;
    private javax.swing.JButton BuscarLocalidad;
    private javax.swing.JTextField BuscarMoneda;
    private javax.swing.JPanel DatosGenerales;
    private javax.swing.JPanel DatosLaborales;
    private javax.swing.JButton GrabarRefComercial;
    private javax.swing.JButton GuardarArchivo;
    private org.jdesktop.swingx.JXFormattedTextField LimiteCredito;
    private javax.swing.JTabbedPane Panel1Clientes;
    private javax.swing.JPanel PanelImagenes;
    private javax.swing.JPanel PanelJuridico;
    private javax.swing.JPanel Referencias;
    private org.jdesktop.swingx.JXFormattedTextField Salario;
    private org.jdesktop.swingx.JXFormattedTextField SalarioConyugue;
    private javax.swing.JButton SalirBarrio;
    private javax.swing.JButton SalirCasa;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JPanel Utiles;
    private javax.swing.JTextField apoderado;
    private javax.swing.JTextField banco;
    private javax.swing.JFormattedTextField barrio;
    private javax.swing.JButton buscarbanco;
    private javax.swing.JTextField cedula;
    private javax.swing.JComboBox<String> clientepep;
    private javax.swing.JFormattedTextField codfais;
    private javax.swing.JTextField codigo;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox combobarrio;
    private javax.swing.JComboBox combolocalidad;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JFormattedTextField comitente;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField direccionfactura;
    private javax.swing.JTextArea directorio;
    private javax.swing.JLabel etiqueta;
    private javax.swing.JLabel etiqueta2;
    private javax.swing.JTextField facturar_a_nombre;
    private com.toedter.calendar.JDateChooser fechaingreso;
    private com.toedter.calendar.JDateChooser fechaingresofuncionario;
    private javax.swing.JTextField iditem;
    private javax.swing.JCheckBox imprimirtitular;
    private javax.swing.JComboBox<String> inversorcalificado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboAsesor;
    private javax.swing.JComboBox jComboAuto;
    private javax.swing.JComboBox jComboCasaPropia;
    private javax.swing.JComboBox jComboCategoria;
    private javax.swing.JComboBox jComboEstado;
    private javax.swing.JComboBox jComboEstadocivil;
    private javax.swing.JComboBox jComboInfor;
    private com.toedter.calendar.JDateChooser jDateNacimiento;
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
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarbanco;
    private javax.swing.JTextField jTextCargoqueOcupa;
    private javax.swing.JTextField jTextCelular;
    private javax.swing.JTextField jTextControl;
    private javax.swing.JTextField jTextConyugue;
    private javax.swing.JTextField jTextDireccionLaboral;
    private javax.swing.JTextField jTextFaxLaboral;
    private javax.swing.JTextField jTextFono;
    private javax.swing.JTextField jTextLugarTrabajo;
    private javax.swing.JTextField jTextMailLaboral;
    private javax.swing.JTextField jTextProfesion;
    private javax.swing.JTextField jTextTelefonoLaboral;
    private javax.swing.JFormattedTextField localidad;
    private javax.swing.JTextField mail;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombrearchivo;
    private javax.swing.JTextField nombrebanco;
    private javax.swing.JTextField nombrebarrio;
    private javax.swing.JTextField nombrelocalidad;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nrocuenta;
    private javax.swing.JTextField objetoempresa;
    private javax.swing.JTextArea observacion_cliente;
    private javax.swing.JTextArea observaciones;
    private org.jdesktop.swingx.JXFormattedTextField otrosIngresos;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JComboBox<String> perfilinterno;
    private javax.swing.JTextField plazocredito;
    private javax.swing.JTextField referenciacomercio;
    private javax.swing.JTextField referenciafonocomercial;
    private javax.swing.JTextField ruc;
    private javax.swing.JTextField rucfactura;
    private javax.swing.JTable tablabanco;
    private javax.swing.JTable tablabarrio;
    private javax.swing.JTable tablacomercial;
    private javax.swing.JTable tablacuentas;
    private javax.swing.JTable tablalocalidad;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTextField telefonofactura;
    private javax.swing.JTextField txtbuscarbarrio;
    private javax.swing.JTextField txtbuscarlocalidad;
    // End of variables declaration//GEN-END:variables

    private class GrillaLaboral extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = tablacuentas.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocuenta.removeRow(0);
            }
            referencia_laboralDAO LabDAO = new referencia_laboralDAO();
            try {
                for (referencia_laboral l : LabDAO.muestrarefxcliente(Integer.valueOf(codigo.getText()))) {
                    String Datos[] = {String.valueOf(l.getItem()), l.getDescripcion(), l.getTelefono()};
                    modelocuenta.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacuentas.setRowSorter(new TableRowSorter(modelocuenta));
            int cantFilas = tablacuentas.getRowCount();
            if (cantFilas > 0) {
                BotonGrabarCta.setEnabled(true);
                BotonBorrarCta.setEnabled(true);
            } else {
                BotonGrabarCta.setEnabled(true);
                BotonBorrarCta.setEnabled(false);
            }
        }
    }

    private void TituloCuenta() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelocuenta.addColumn("Nombre Banco");
        modelocuenta.addColumn("N° de Cuenta");
        modelocuenta.addColumn("Moneda");
        modelocuenta.addColumn("id");
        modelocuenta.addColumn("idMoneda");
        modelocuenta.addColumn("idBanco");
        int[] anchos = {100, 100, 50, 5, 5, 5};
        for (int i = 0; i < modelocuenta.getColumnCount(); i++) {
            tablacuentas.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacuentas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        //Se usa para poner invisible una determinada celda
        tablacuentas.getTableHeader().setFont(new Font("Arial Black", 1, 10));
        Font font = new Font("Arial", Font.BOLD, 10);
        tablacuentas.setFont(font);
        this.tablacuentas.getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablacuentas.getColumnModel().getColumn(3).setMinWidth(0);
        this.tablacuentas.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablacuentas.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);

        this.tablacuentas.getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacuentas.getColumnModel().getColumn(4).setMinWidth(0);
        this.tablacuentas.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacuentas.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);

        this.tablacuentas.getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablacuentas.getColumnModel().getColumn(5).setMinWidth(0);
        this.tablacuentas.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablacuentas.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);

    }

    private class GrillaComercial extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = tablacomercial.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                comercial.removeRow(0);
            }
            referencia_comercialDAO ComDAO = new referencia_comercialDAO();
            try {
                for (referencia_comercial c : ComDAO.muestrarefxcliente(Integer.valueOf(codigo.getText()))) {
                    String Datos[] = {String.valueOf(c.getItem()), c.getDescripcion(), c.getTelefono()};
                    comercial.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacomercial.setRowSorter(new TableRowSorter(comercial));
            int cantFilas = tablacomercial.getRowCount();
            if (cantFilas > 0) {
                GrabarRefComercial.setEnabled(true);
                BorrarRefComercial.setEnabled(true);
            } else {
                GrabarRefComercial.setEnabled(true);
                BorrarRefComercial.setEnabled(false);
            }
        }
    }

    private class GrillaMonedaCta extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }
            monedaDAO DAOMON = new monedaDAO();
            try {
                for (moneda mo : DAOMON.todos()) {
                    String Datos[] = {String.valueOf(mo.getCodigo()), mo.getNombre()};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private class GrillaBancoPlaza extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            bancoplazaDAO bancoDAO = new bancoplazaDAO();
            try {
                for (bancoplaza ba : bancoDAO.todos()) {
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

    private class GrillaLocalidad extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelolocalidad.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelolocalidad.removeRow(0);
            }
            localidadDAO locDAO = new localidadDAO();
            try {
                for (localidad loc : locDAO.todos()) {
                    String Datos[] = {String.valueOf(loc.getCodigo()), loc.getNombre()};
                    modelolocalidad.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablalocalidad.setRowSorter(new TableRowSorter(modelolocalidad));
            int cantFilas = tablalocalidad.getRowCount();
        }
    }

    private class GrillaBarrio extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobarrio.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobarrio.removeRow(0);
            }
            barrioDAO baDAO = new barrioDAO();
            try {
                for (barrio ba : baDAO.todosxlocalidad(Integer.valueOf(localidad.getText()))) {
                    String Datos[] = {String.valueOf(ba.getCodigo()), ba.getNombre()};
                    modelobarrio.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablabarrio.setRowSorter(new TableRowSorter(modelobarrio));
            int cantFilas = tablabarrio.getRowCount();
        }
    }

}

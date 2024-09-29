/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.ControlGrabado;
import Clases.clsExportarExcel;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.abogadoDAO;
import DAO.clienteDAO;
import DAO.giraduriaDAO;
import DAO.planDAO;
import DAO.situacionDAO;
import Modelo.Tablas;
import Modelo.abogado;
import Modelo.cliente;
import Modelo.giraduria;
import Modelo.plan;
import Modelo.situacion;
import com.lowagie.text.Cell;
import com.lowagie.text.Row;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import jxl.Sheet;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class Clientes extends javax.swing.JFrame {

    clsExportarExcel obj;
    ObtenerFecha ODate = new ObtenerFecha();
    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modeloabogado = new Tablas();
    Tablas modelosituacion = new Tablas();
    Tablas modeloplan = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrogira, trsfiltroplan, trsfiltroabog, trsfiltrosit;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    String cSql = null;
    DecimalFormat formatea = new DecimalFormat("###,###");
    DecimalFormat formatoSinpunto = new DecimalFormat("###");

    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");

    public Clientes() {
        initComponents();
        this.jButton2.setIcon(icononuevo);
        this.jButton1.setIcon(iconoeditar);
        this.jButton3.setIcon(iconoborrar);
        this.jButton4.setIcon(iconoprint);
        this.jButton5.setIcon(iconosalir);
        this.BtnBuscarAbogado.setIcon(iconobuscar);
        this.BtnBuscarSituacion.setIcon(iconobuscar);
        this.dFechaInicial.setVisible(false);

        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(204, 204, 255));
        this.jTable1.setForeground(Color.BLACK);

        this.setLocationRelativeTo(null); //Centramos el formulario
        this.jTextOpciones1.setVisible(false);
        this.cargarTitulo();
        this.TitGir();
        this.TitPlan();
        this.TitAbogado();
        this.TitSituacion();

        GrillaCliente Grilla = new GrillaCliente();
        Thread HiloClientes = new Thread(Grilla);
        HiloClientes.start();
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);

        GrillaAbogado grillab = new GrillaAbogado();
        Thread hiloab = new Thread(grillab);
        hiloab.start();

        GrillaSituacion grillasi = new GrillaSituacion();
        Thread hilosi = new Thread(grillasi);
        hilosi.start();
        
        GrillaPlanCliente grillapl = new GrillaPlanCliente();
        Thread hilopl = new Thread(grillapl);
        hilopl.start();

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

        DatosSocios = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cuentasocio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nombresocio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        rucsocio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        legajosocio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ingresofuncionario = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        ingresosocio = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        giraduriasocio = new javax.swing.JTextField();
        BtnBuscarGiraduria = new javax.swing.JButton();
        nombregiraduria = new javax.swing.JTextField();
        habilitardescuento = new javax.swing.JCheckBox();
        lugartrabajo = new javax.swing.JTextField();
        turno = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        credito = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        BtnGrabarSocios = new javax.swing.JButton();
        BtnSalirSocios = new javax.swing.JButton();
        BGiraduria = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        jTBuscarGiraduria = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablagiraduria = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        cuentas = new javax.swing.JDialog();
        jPanel34 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        codcuenta = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        descripcion = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        idcta = new javax.swing.JTextField();
        nombrecuenta = new javax.swing.JTextField();
        BuscarCta = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        GrabaEnlace = new javax.swing.JButton();
        SalirEnlace = new javax.swing.JButton();
        BCuenta = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        comboplan = new javax.swing.JComboBox();
        jTBuscarPlan = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablaplan = new javax.swing.JTable();
        jPanel37 = new javax.swing.JPanel();
        AceptarCuenta = new javax.swing.JButton();
        SalirCuenta = new javax.swing.JButton();
        res90 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        codcliente = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        nombreclienteres90 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        nombreferencia = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tipores90 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        GrabaEnlace1 = new javax.swing.JButton();
        SalirEnlace1 = new javax.swing.JButton();
        situacionCliente = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cuentacliente = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        nombrecliente = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        ruccliente = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        abogado = new javax.swing.JTextField();
        BtnBuscarAbogado = new javax.swing.JButton();
        nombreabogado = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        situacion = new javax.swing.JFormattedTextField();
        BtnBuscarSituacion = new javax.swing.JButton();
        nombresituacion = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        motivo = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        BtnGrabarSocios1 = new javax.swing.JButton();
        BtnSalirSocios1 = new javax.swing.JButton();
        BAbogado = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        comboabogado = new javax.swing.JComboBox();
        jTBuscarAbogado = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaabogado = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        BSituacion = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        combosituacion = new javax.swing.JComboBox();
        jTBuscarSituacion = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablasituacion = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        AceptarSituacion = new javax.swing.JButton();
        SalirSituacion = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextOpciones1 = new javax.swing.JTextField();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código");

        cuentasocio.setEditable(false);
        cuentasocio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cuentasocio.setEnabled(false);

        jLabel2.setText("Nombre del Socio");

        nombresocio.setEditable(false);
        nombresocio.setEnabled(false);

        jLabel3.setText("RUC/CIP");

        rucsocio.setEditable(false);
        rucsocio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rucsocio.setEnabled(false);

        jLabel4.setText("N° Legajo");

        legajosocio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        legajosocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                legajosocioKeyPressed(evt);
            }
        });

        jLabel5.setText("Ingreso c/Funcionario");

        ingresofuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ingresofuncionarioKeyPressed(evt);
            }
        });

        jLabel6.setText("Ingreso c/Socio");

        ingresosocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ingresosocioKeyPressed(evt);
            }
        });

        jLabel7.setText("Descontar Salario en");

        giraduriasocio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giraduriasocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giraduriasocioActionPerformed(evt);
            }
        });
        giraduriasocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                giraduriasocioKeyPressed(evt);
            }
        });

        BtnBuscarGiraduria.setText("...");
        BtnBuscarGiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnBuscarGiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarGiraduriaActionPerformed(evt);
            }
        });

        nombregiraduria.setEditable(false);
        nombregiraduria.setEnabled(false);

        habilitardescuento.setText("Descontar Aportes - Cuota Social - Rifas");

        lugartrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lugartrabajoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lugartrabajoKeyReleased(evt);
            }
        });

        turno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DIURNO", "MIXTO", "NOCTURNO" }));

        jLabel8.setText("Ubicación");

        jLabel9.setText("Turno");

        jLabel14.setText("Aporte");

        credito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        credito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        credito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                creditoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(giraduriasocio, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtnBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombregiraduria))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ingresosocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ingresofuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombresocio, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuentasocio, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(turno, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lugartrabajo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(legajosocio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                                        .addComponent(rucsocio, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(83, 83, 83))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(credito, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(habilitardescuento)
                .addGap(197, 197, 197))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cuentasocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombresocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rucsocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(legajosocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lugartrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(ingresofuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(ingresosocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(giraduriasocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscarGiraduria)
                    .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(habilitardescuento)
                .addGap(17, 17, 17))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BtnGrabarSocios.setText("Actualizar");
        BtnGrabarSocios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnGrabarSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGrabarSociosActionPerformed(evt);
            }
        });

        BtnSalirSocios.setText("Salir");
        BtnSalirSocios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnSalirSocios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirSociosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnGrabarSocios)
                .addGap(26, 26, 26)
                .addComponent(BtnSalirSocios, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGrabarSocios)
                    .addComponent(BtnSalirSocios))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DatosSociosLayout = new javax.swing.GroupLayout(DatosSocios.getContentPane());
        DatosSocios.getContentPane().setLayout(DatosSociosLayout);
        DatosSociosLayout.setHorizontalGroup(
            DatosSociosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DatosSociosLayout.setVerticalGroup(
            DatosSociosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DatosSociosLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jTBuscarGiraduria.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.SalirCliente.text")); // NOI18N
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

        cuentas.setTitle("Configurar Cuenta de Enlace");

        jPanel34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel39.setText("Código");

        codcuenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codcuenta.setEnabled(false);

        jLabel40.setText("Denominación");

        descripcion.setEnabled(false);

        jLabel41.setText("Cuenta Contable");

        idcta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idcta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idctaActionPerformed(evt);
            }
        });

        nombrecuenta.setEnabled(false);

        BuscarCta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCtaActionPerformed(evt);
            }
        });

        jLabel42.setText("Denominación Cuenta");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(idcta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BuscarCta, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombrecuenta)
                    .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(codcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarCta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41)
                        .addComponent(idcta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabaEnlace.setText("Grabar");
        GrabaEnlace.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabaEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabaEnlaceActionPerformed(evt);
            }
        });

        SalirEnlace.setText("Salir");
        SalirEnlace.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirEnlaceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabaEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(SalirEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabaEnlace)
                    .addComponent(SalirEnlace))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout cuentasLayout = new javax.swing.GroupLayout(cuentas.getContentPane());
        cuentas.getContentPane().setLayout(cuentasLayout);
        cuentasLayout.setHorizontalGroup(
            cuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        cuentasLayout.setVerticalGroup(
            cuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cuentasLayout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCuenta.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCuenta.setTitle("null");

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboplan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboplan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboplan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboplan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboplanActionPerformed(evt);
            }
        });

        jTBuscarPlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPlan.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarPlanActionPerformed(evt);
            }
        });
        jTBuscarPlan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPlanKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(comboplan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboplan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaplan.setModel(modeloplan);
        tablaplan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaplanMouseClicked(evt);
            }
        });
        tablaplan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaplanKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(tablaplan);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCuenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCuenta.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCuentaActionPerformed(evt);
            }
        });

        SalirCuenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCuenta.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCuentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCuenta)
                    .addComponent(SalirCuenta))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCuentaLayout = new javax.swing.GroupLayout(BCuenta.getContentPane());
        BCuenta.getContentPane().setLayout(BCuentaLayout);
        BCuentaLayout.setHorizontalGroup(
            BCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCuentaLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCuentaLayout.setVerticalGroup(
            BCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCuentaLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setText("Código");

        codcliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codcliente.setEnabled(false);

        jLabel11.setText("Descripción");

        nombreclienteres90.setEnabled(false);

        jLabel12.setText("Tipo ");

        nombreferencia.setEnabled(false);

        jLabel13.setText("Referencia");

        tipores90.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "11", "12", "13", "14", "15", "16", "17" }));
        tipores90.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tipores90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipores90ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nombreferencia)
                        .addComponent(nombreclienteres90, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tipores90, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(codcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(nombreclienteres90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tipores90, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabaEnlace1.setText("Grabar");
        GrabaEnlace1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabaEnlace1ActionPerformed(evt);
            }
        });

        SalirEnlace1.setText("Salir");
        SalirEnlace1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirEnlace1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addComponent(GrabaEnlace1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(SalirEnlace1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabaEnlace1)
                    .addComponent(SalirEnlace1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout res90Layout = new javax.swing.GroupLayout(res90.getContentPane());
        res90.getContentPane().setLayout(res90Layout);
        res90Layout.setHorizontalGroup(
            res90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        res90Layout.setVerticalGroup(
            res90Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(res90Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setText("Código");

        cuentacliente.setEditable(false);
        cuentacliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cuentacliente.setEnabled(false);

        jLabel16.setText("Nombre del Cliente");

        nombrecliente.setEditable(false);
        nombrecliente.setEnabled(false);

        jLabel17.setText("CIP");

        ruccliente.setEditable(false);
        ruccliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ruccliente.setEnabled(false);

        jLabel21.setText("Abogado");

        abogado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        abogado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abogadoActionPerformed(evt);
            }
        });
        abogado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                abogadoKeyPressed(evt);
            }
        });

        BtnBuscarAbogado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnBuscarAbogado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarAbogadoActionPerformed(evt);
            }
        });

        nombreabogado.setEditable(false);
        nombreabogado.setEnabled(false);

        jLabel18.setText("Situación");

        situacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        situacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        situacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                situacionActionPerformed(evt);
            }
        });

        BtnBuscarSituacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnBuscarSituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarSituacionActionPerformed(evt);
            }
        });

        nombresituacion.setEditable(false);
        nombresituacion.setEnabled(false);

        jLabel19.setText("Motivo");

        motivo.setColumns(20);
        motivo.setRows(5);
        jScrollPane2.setViewportView(motivo);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(jLabel21)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addComponent(cuentacliente, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ruccliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(situacion, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnBuscarSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nombresituacion))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(abogado, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnBuscarAbogado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nombreabogado))
                    .addComponent(jScrollPane2))
                .addGap(0, 115, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cuentacliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(ruccliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(abogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(BtnBuscarAbogado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreabogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(situacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(BtnBuscarSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresituacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BtnGrabarSocios1.setText("Actualizar");
        BtnGrabarSocios1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnGrabarSocios1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGrabarSocios1ActionPerformed(evt);
            }
        });

        BtnSalirSocios1.setText("Salir");
        BtnSalirSocios1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnSalirSocios1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirSocios1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnGrabarSocios1)
                .addGap(26, 26, 26)
                .addComponent(BtnSalirSocios1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGrabarSocios1)
                    .addComponent(BtnSalirSocios1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout situacionClienteLayout = new javax.swing.GroupLayout(situacionCliente.getContentPane());
        situacionCliente.getContentPane().setLayout(situacionClienteLayout);
        situacionClienteLayout.setHorizontalGroup(
            situacionClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        situacionClienteLayout.setVerticalGroup(
            situacionClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(situacionClienteLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BAbogado.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BAbogado.setTitle("Buscar Abogado");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboabogado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboabogado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboabogado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboabogado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboabogadoActionPerformed(evt);
            }
        });

        jTBuscarAbogado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarAbogado.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarAbogado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarAbogadoActionPerformed(evt);
            }
        });
        jTBuscarAbogado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarAbogadoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(comboabogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarAbogado, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboabogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarAbogado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaabogado.setModel(modeloabogado        );
        tablaabogado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaabogadoMouseClicked(evt);
            }
        });
        tablaabogado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaabogadoKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablaabogado);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.SalirCliente.text")); // NOI18N
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

        javax.swing.GroupLayout BAbogadoLayout = new javax.swing.GroupLayout(BAbogado.getContentPane());
        BAbogado.getContentPane().setLayout(BAbogadoLayout);
        BAbogadoLayout.setHorizontalGroup(
            BAbogadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BAbogadoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BAbogadoLayout.setVerticalGroup(
            BAbogadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BAbogadoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BSituacion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSituacion.setTitle("null");

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosituacion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosituacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosituacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosituacionActionPerformed(evt);
            }
        });

        jTBuscarSituacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSituacion.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarSituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarSituacionActionPerformed(evt);
            }
        });
        jTBuscarSituacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarSituacionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(combosituacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combosituacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablasituacion.setModel(modelosituacion);
        tablasituacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablasituacionMouseClicked(evt);
            }
        });
        tablasituacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablasituacionKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablasituacion);

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSituacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSituacion.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSituacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSituacionActionPerformed(evt);
            }
        });

        SalirSituacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSituacion.setText(org.openide.util.NbBundle.getMessage(Clientes.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSituacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSituacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSituacion)
                    .addComponent(SalirSituacion))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSituacionLayout = new javax.swing.GroupLayout(BSituacion.getContentPane());
        BSituacion.getContentPane().setLayout(BSituacionLayout);
        BSituacionLayout.setHorizontalGroup(
            BSituacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSituacionLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSituacionLayout.setVerticalGroup(
            BSituacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSituacionLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        labelMetric1.setText("Ficha de Clientes");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RUC", "Nombre", "Código", "Teléfono" }));
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
                .addGap(25, 25, 25)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
        jButton3.setText("Eliminar Registro");
        jButton3.setToolTipText("");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton4.setText("Listar/Imprimir");
        jButton4.setToolTipText("");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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

        jTextOpciones1.setEditable(false);

        dFechaInicial.setEnabled(false);
        dFechaInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dFechaInicialFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(41, Short.MAX_VALUE)
                    .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(51, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(302, Short.MAX_VALUE)
                    .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(245, 245, 245)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(283, 283, 283)
                    .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(264, Short.MAX_VALUE)))
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

        jMenu1.setText("Menú Clientes/Socios");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Solicitud Formato 1");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Datos de Asociados");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator3);

        jMenuItem4.setText("Declaración Jurada");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator2);

        jMenuItem3.setText("Exportar Tabla a Excel");
        jMenuItem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator4);

        jMenuItem5.setText("Cuenta Contable");
        jMenuItem5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jSeparator5.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jSeparator5AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jMenu1.add(jSeparator5);

        jMenuItem6.setText("Tipo Documento RES/90");
        jMenuItem6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator6);

        jMenuItem7.setText("Situación Comercial del Cliente");
        jMenuItem7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

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
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(1, 1, 1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 603, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TitPlan() {
        modeloplan.addColumn("Código");
        modeloplan.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloplan.getColumnCount(); i++) {
            tablaplan.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaplan.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaplan.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaplan.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaplan.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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

    private void TitAbogado() {
        modeloabogado.addColumn("Código");
        modeloabogado.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloabogado.getColumnCount(); i++) {
            tablaabogado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaabogado.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaabogado.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaabogado.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaabogado.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitSituacion() {
        modelosituacion.addColumn("Código");
        modelosituacion.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelosituacion.getColumnCount(); i++) {
            tablasituacion.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablasituacion.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablasituacion.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablasituacion.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablasituacion.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtrogira(int nNumeroColumna) {
        trsfiltrogira.setRowFilter(RowFilter.regexFilter(this.jTBuscarGiraduria.getText(), nNumeroColumna));
    }

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
                        break;//por ruc
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 2:
                        indiceColumnaTabla = 0;
                        break;//por código
                    case 3:
                        indiceColumnaTabla = 5;
                        break;//por cedula
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
            new detalle_clientes(cOpcion).setVisible(true);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        int nFila = this.jTable1.getSelectedRow();
        this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 0).toString());

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "";
        String cOpcion = this.jTextOpciones1.getText();
        try {
            new detalle_clientes(cOpcion).setVisible(true);
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
            GrillaCliente Grilla = new GrillaCliente();
            Thread HiloClientes = new Thread(Grilla);
            HiloClientes.start();
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
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            if (!this.jTextOpciones1.getText().isEmpty()) {
                BDConexion BD = new BDConexion();
                BD.borrarRegistro("clientes", "codigo=" + this.jTextOpciones1.getText().trim());
                GrillaCliente Grilla = new GrillaCliente();
                Thread HiloClientes = new Thread(Grilla);
                HiloClientes.start();
            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        try {
            obj = new clsExportarExcel();
            obj.exportarExcel(jTable1);
        } catch (IOException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GenerarSolicitud GenerarSolicitud = new GenerarSolicitud();
        Thread HiloReporte = new Thread(GenerarSolicitud);
        HiloReporte.start();

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void dFechaInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dFechaInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialFocusGained

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 0).toString());
        if (Integer.parseInt(this.jTextOpciones1.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar");
        } else {
            clienteDAO clDAO = new clienteDAO();
            cliente cl = null;

            try {
                cl = clDAO.buscarId(Integer.valueOf(this.jTextOpciones1.getText()));
                if (cl != null) {
                    this.cuentasocio.setText(String.valueOf(cl.getCodigo()));
                    this.nombresocio.setText(cl.getNombre());
                    this.rucsocio.setText(cl.getRuc());
                    this.legajosocio.setText(String.valueOf(cl.getLegajo()));
                    this.ingresosocio.setDate(cl.getFechaingreso());
                    this.credito.setText(formatea.format(cl.getCredito()));
                    this.lugartrabajo.setText(cl.getLugartrabajo());
                    this.turno.setSelectedItem(cl.getTurno());
                    this.ingresofuncionario.setDate(cl.getFechaingresofuncionario());
                    this.giraduriasocio.setText(String.valueOf(cl.getGiraduria().getCodigo()));
                    this.nombregiraduria.setText(cl.getGiraduria().getNombre());
                    if (cl.getDescuentos_aportes() == 1) {
                        habilitardescuento.setSelected(true);
                    } else {
                        habilitardescuento.setSelected(false);
                    }
                    DatosSocios.setModal(true);
                    DatosSocios.setSize(625, 400);
                    legajosocio.requestFocus();
                    //Establecemos un título para el jDialog
                    DatosSocios.setTitle("Actualización");
                    DatosSocios.setLocationRelativeTo(null);
                    DatosSocios.setVisible(true);
                }

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void BtnSalirSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirSociosActionPerformed
        DatosSocios.setModal(false);
        DatosSocios.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSalirSociosActionPerformed

    private void BtnGrabarSociosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGrabarSociosActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            Date FechaIngreso = ODate.de_java_a_sql(this.ingresosocio.getDate());
            Date FechaIngresoFuncionario = ODate.de_java_a_sql(this.ingresofuncionario.getDate());

            clienteDAO actualDAO = new clienteDAO();
            cliente cli = new cliente();
            giraduriaDAO giDAO = new giraduriaDAO();
            giraduria g = null;

            String cAporte = this.credito.getText();
            cAporte = cAporte.replace(".", "");
            cli.setFechaingreso(FechaIngreso);
            cli.setFechaingresofuncionario(FechaIngresoFuncionario);
            cli.setLegajo(Integer.valueOf(this.legajosocio.getText()));
            cli.setTurno(turno.getSelectedItem().toString());
            cli.setLugartrabajo(lugartrabajo.getText());
            cli.setCredito(Double.valueOf(cAporte));
            if (habilitardescuento.isSelected()) {
                cli.setDescuentos_aportes(1);
            } else {
                cli.setDescuentos_aportes(0);
            }
            cli.setCodigo(Integer.valueOf(this.cuentasocio.getText()));
            try {
                g = giDAO.buscarId(Integer.valueOf(this.giraduriasocio.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (g != null) {
                cli.setGiraduria(g);
            } else {
                JOptionPane.showConfirmDialog(null, "Seleccione la Giraduria");
                this.giraduriasocio.requestFocus();
                return;
            }

            try {
                actualDAO.ActualizarSociosAso(cli);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.DatosSocios.setModal(false);
            this.DatosSocios.setVisible(false);
        }
    }//GEN-LAST:event_BtnGrabarSociosActionPerformed

    private void legajosocioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_legajosocioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.lugartrabajo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_legajosocioKeyPressed

    private void ingresosocioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingresosocioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.giraduriasocio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ingresosocio.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_ingresosocioKeyPressed

    private void ingresofuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingresofuncionarioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ingresosocio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.legajosocio.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ingresofuncionarioKeyPressed

    private void giraduriasocioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giraduriasocioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.BtnBuscarGiraduria.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ingresosocio.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_giraduriasocioKeyPressed

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
        this.giraduriasocio.setText(this.tablagiraduria.getValueAt(nFila, 0).toString());
        this.nombregiraduria.setText(this.tablagiraduria.getValueAt(nFila, 1).toString());

        this.BGiraduria.setVisible(false);
        this.jTBuscarGiraduria.setText("");
        this.habilitardescuento.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BGiraduria.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void BtnBuscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarGiraduriaActionPerformed
        giraduriaDAO girDAO = new giraduriaDAO();
        giraduria gi = null;
        try {
            gi = girDAO.buscarId(Integer.valueOf(this.giraduriasocio.getText()));
            if (gi.getCodigo() == 0) {
                GrillaGiraduria grillagi = new GrillaGiraduria();
                Thread hilogi = new Thread(grillagi);
                hilogi.start();
                BGiraduria.setModal(true);
                BGiraduria.setSize(482, 575);
                BGiraduria.setLocationRelativeTo(null);
                BGiraduria.setVisible(true);
                BGiraduria.setTitle("Buscar Giraduria");
                BGiraduria.setModal(false);
            } else {
                nombregiraduria.setText(gi.getNombre());
                //Establecemos un título para el jDialog
            }
            this.habilitardescuento.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBuscarGiraduriaActionPerformed

    private void giraduriasocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giraduriasocioActionPerformed
        this.BtnBuscarGiraduria.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_giraduriasocioActionPerformed

    private void lugartrabajoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lugartrabajoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.credito.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_lugartrabajoKeyPressed

    private void lugartrabajoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lugartrabajoKeyReleased
        String letras = ConvertirMayusculas.cadena(lugartrabajo);
        lugartrabajo.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_lugartrabajoKeyReleased

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            obj = new clsExportarExcel();
            obj.exportarExcel(jTable1);
        } catch (IOException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        con = new Conexion();
        stm = con.conectar();
        Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("cCliente", jTextOpciones1.getText().trim());
            parameters.put("FechaI", FechaI);
            parameters.put("cUsuario", Config.cNombreUsuario);

            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/declaracion_jurada.jasper");
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
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void idctaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idctaActionPerformed
        BuscarCta.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_idctaActionPerformed

    private void BuscarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCtaActionPerformed
        planDAO plaDAO = new planDAO();
        plan pl = null;
        try {
            pl = plaDAO.buscarId(this.idcta.getText());
            if (pl.getCodigo() == null) {
                BCuenta.setModal(true);
                BCuenta.setSize(482, 575);
                BCuenta.setLocationRelativeTo(null);
                BCuenta.setVisible(true);
                BCuenta.setTitle("Buscar Cuenta");
                BCuenta.setModal(false);
            } else {
                nombrecuenta.setText(pl.getNombre());
                //Establecemos un título para el jDialog
            }
            this.GrabaEnlace.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCtaActionPerformed

    private void GrabaEnlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabaEnlaceActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Actualizar el Enlace? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            clienteDAO grabarDAO = new clienteDAO();
            cliente p = new cliente();
            planDAO pDAO = new planDAO();
            plan plan = null;

            try {
                plan = pDAO.buscarId(idcta.getText());
                p.setIdcta(plan);
                p.setCodigo(Integer.valueOf(this.codcuenta.getText()));;
                grabarDAO.ActualizarCuentaContable(p);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            cuentas.setVisible(false);
            cuentas.setModal(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabaEnlaceActionPerformed

    private void SalirEnlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirEnlaceActionPerformed
        cuentas.setVisible(false);
        cuentas.setModal(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirEnlaceActionPerformed

    private void comboplanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboplanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboplanActionPerformed

    private void jTBuscarPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarPlanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPlanActionPerformed

    private void jTBuscarPlanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPlanKeyPressed
        this.jTBuscarPlan.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarPlan.getText()).toUpperCase();
                jTBuscarPlan.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboplan.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtroplan(indiceColumnaTabla);
            }
        });
        trsfiltroplan = new TableRowSorter(tablaplan.getModel());
        tablaplan.setRowSorter(trsfiltroplan);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPlanKeyPressed

    public void filtroplan(int nNumeroColumna) {
        trsfiltroplan.setRowFilter(RowFilter.regexFilter(jTBuscarPlan.getText(), nNumeroColumna));
    }


    private void tablaplanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaplanMouseClicked
        this.AceptarCuenta.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaplanMouseClicked

    private void tablaplanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaplanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCuenta.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaplanKeyPressed

    private void AceptarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCuentaActionPerformed
        int nFila = this.tablaplan.getSelectedRow();
        this.idcta.setText(this.tablaplan.getValueAt(nFila, 0).toString());
        this.nombrecuenta.setText(this.tablaplan.getValueAt(nFila, 1).toString());
        this.BCuenta.setVisible(false);
        this.BCuenta.setModal(false);
        this.GrabaEnlace.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCuentaActionPerformed

    private void SalirCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCuentaActionPerformed
        this.BCuenta.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCuentaActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.jTable1.requestFocus();
            return;
        } else {
            this.codcuenta.setText(this.jTable1.getValueAt(nFila, 0).toString());
            this.descripcion.setText(this.jTable1.getValueAt(nFila, 1).toString());
        }
        clienteDAO pvDAO = new clienteDAO();
        cliente pro = null;
        try {
            pro = pvDAO.BuscarCuentaCliente(Integer.valueOf(this.codcuenta.getText()));
            if (pro != null) {
                idcta.setText(pro.getIdcta().getCodigo());
                nombrecuenta.setText(pro.getIdcta().getNombre());
                cuentas.setTitle("Modificar Enlace Contable");
                cuentas.setModal(true);

                //(Ancho,Alto)
                cuentas.setSize(450, 271);
                //Establecemos un título para el jDialog
                cuentas.setLocationRelativeTo(null);
                cuentas.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "La Operación  no puede Modificarse");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void GrabaEnlace1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabaEnlace1ActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Actualizar ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            clienteDAO grabar = new clienteDAO();
            cliente c = new cliente();
            c.setCodigo(Integer.valueOf(this.codcliente.getText()));;
            c.setRes90(Integer.valueOf(tipores90.getSelectedItem().toString()));
            try {
                grabar.ActualizarRes90(c);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            res90.setVisible(false);
            res90.setModal(false);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_GrabaEnlace1ActionPerformed

    private void describirRes() {
        int num = tipores90.getSelectedIndex();
        // instrucción switch con tipo de datos int
        switch (num) {
            case 0:
                nombreferencia.setText("RUC");
                break;
            case 1:
                nombreferencia.setText("CÉDULA DE IDENTIDAD");
                break;
            case 2:
                nombreferencia.setText("PASAPORTE");
                break;
            case 3:
                nombreferencia.setText("CÉDULA EXTRANJERA");
                break;
            case 4:
                nombreferencia.setText("SIN NOMBRE");
                break;
            case 5:
                nombreferencia.setText("DIPLOMATICO");
                break;
            case 6:
                nombreferencia.setText("IDENTIFICACIÓN TRIBUTARIA");
                break;
            case 7:
                nombreferencia.setText("CLIENTE DEL EXTERIOR");
                break;
        }
    }

    private void SalirEnlace1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirEnlace1ActionPerformed
        res90.setVisible(false);
        res90.setModal(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirEnlace1ActionPerformed

    private void tipores90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipores90ActionPerformed
        this.describirRes();
        // TODO add your handling code here:
    }//GEN-LAST:event_tipores90ActionPerformed

    private void jSeparator5AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jSeparator5AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jSeparator5AncestorAdded

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.jTable1.requestFocus();
            return;
        } else {
            this.codcliente.setText(this.jTable1.getValueAt(nFila, 0).toString());
            this.nombreclienteres90.setText(this.jTable1.getValueAt(nFila, 1).toString());
        }
        clienteDAO pvDAO = new clienteDAO();
        cliente pro = null;
        try {
            pro = pvDAO.BuscarRes90Cliente(Integer.valueOf(this.codcliente.getText()));
            if (pro != null) {
                String cRes90 = String.valueOf(pro.getRes90());
                tipores90.setSelectedItem(cRes90);
                this.describirRes();
                res90.setTitle("Actualizar Tipo de Documento");
                res90.setModal(true);

                //(Ancho,Alto)
                res90.setSize(450, 271);
                //Establecemos un título para el jDialog
                res90.setLocationRelativeTo(null);
                res90.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "La Operación  no puede Modificarse");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void creditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_creditoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.turno.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_creditoKeyPressed

    private void abogadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abogadoActionPerformed
        this.BtnBuscarAbogado.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_abogadoActionPerformed

    private void abogadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_abogadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.BtnBuscarGiraduria.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ingresosocio.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_abogadoKeyPressed

    private void BtnBuscarAbogadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarAbogadoActionPerformed
        abogadoDAO aboDAO = new abogadoDAO();
        abogado abo = null;
        try {
            abo = aboDAO.buscarId(Integer.valueOf(this.abogado.getText()));
            if (abo.getCodigo() == 0) {
                BAbogado.setModal(true);
                BAbogado.setSize(482, 575);
                BAbogado.setLocationRelativeTo(null);
                BAbogado.setVisible(true);
                BAbogado.setTitle("Buscar Abogado");
                BAbogado.setModal(false);
            } else {
                nombreabogado.setText(abo.getNombre());
                //Establecemos un título para el jDialog
            }
            this.situacion.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBuscarAbogadoActionPerformed

    private void BtnGrabarSocios1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGrabarSocios1ActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            clienteDAO actualDAO = new clienteDAO();
            cliente cli = new cliente();
            abogadoDAO abDAO = new abogadoDAO();
            abogado ab = null;
            situacionDAO siDAO = new situacionDAO();
            situacion sit = null;

            try {
                ab = abDAO.buscarId(Integer.valueOf(this.abogado.getText()));
                sit = siDAO.buscarId(Integer.valueOf(this.situacion.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            cli.setAbogado(ab);
            cli.setSituacion(sit);
            cli.setMotivosituacion(motivo.getText());
            cli.setCodigo(Integer.valueOf(this.cuentacliente.getText()));

            try {
                actualDAO.actualizarSituacionCliente(cli);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.situacionCliente.setModal(false);
            this.situacionCliente.setVisible(false);
        }
    }//GEN-LAST:event_BtnGrabarSocios1ActionPerformed

    private void BtnSalirSocios1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirSocios1ActionPerformed
        situacionCliente.setModal(false);
        situacionCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSalirSocios1ActionPerformed

    private void BtnBuscarSituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarSituacionActionPerformed
        situacionDAO aboDAO = new situacionDAO();
        situacion abo = null;
        try {
            abo = aboDAO.buscarId(Integer.valueOf(this.situacion.getText()));
            if (abo.getCodigo() == 0) {
                BSituacion.setModal(true);
                BSituacion.setSize(482, 575);
                BSituacion.setLocationRelativeTo(null);
                BSituacion.setVisible(true);
                BSituacion.setTitle("Buscar Situación");
                BSituacion.setModal(false);
            } else {
                nombresituacion.setText(abo.getNombre());
                //Establecemos un título para el jDialog
            }
            this.motivo.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnBuscarSituacionActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        int nFila = this.jTable1.getSelectedRow();
        this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 0).toString());
        if (Integer.parseInt(this.jTextOpciones1.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar");
        } else {
            clienteDAO clDAO = new clienteDAO();
            cliente cl = null;

            try {
                cl = clDAO.buscarIdSituacion(Integer.valueOf(this.jTextOpciones1.getText()));
                if (cl != null) {
                    this.cuentacliente.setText(String.valueOf(cl.getCodigo()));
                    this.nombrecliente.setText(cl.getNombre());
                    this.ruccliente.setText(cl.getRuc());
                    this.abogado.setText(String.valueOf(cl.getAbogado().getCodigo()));
                    this.nombreabogado.setText(cl.getAbogado().getNombre());
                    this.situacion.setText(String.valueOf(cl.getSituacion().getCodigo()));
                    this.nombresituacion.setText(cl.getSituacion().getNombre());

                    situacionCliente.setModal(true);
                    situacionCliente.setSize(625, 400);
                    abogado.requestFocus();
                    //Establecemos un título para el jDialog
                    situacionCliente.setTitle("Actualización");
                    situacionCliente.setLocationRelativeTo(null);
                    situacionCliente.setVisible(true);
                }

            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void comboabogadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboabogadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboabogadoActionPerformed

    private void jTBuscarAbogadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarAbogadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarAbogadoActionPerformed

    private void jTBuscarAbogadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarAbogadoKeyPressed
        this.jTBuscarAbogado.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarAbogado.getText()).toUpperCase();
                jTBuscarAbogado.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboabogado.getSelectedIndex()) {
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
                filtroabog(indiceColumnaTabla);
            }
        });
        trsfiltroabog = new TableRowSorter(tablaabogado.getModel());
        tablaabogado.setRowSorter(trsfiltroabog);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarAbogadoKeyPressed

    public void filtroabog(int nNumeroColumna) {
        trsfiltroabog.setRowFilter(RowFilter.regexFilter(this.jTBuscarAbogado.getText(), nNumeroColumna));
    }


    private void tablaabogadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaabogadoMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaabogadoMouseClicked

    private void tablaabogadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaabogadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaabogadoKeyPressed

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablaabogado.getSelectedRow();
        this.abogado.setText(this.tablaabogado.getValueAt(nFila, 0).toString());
        this.nombreabogado.setText(this.tablaabogado.getValueAt(nFila, 1).toString());

        this.BAbogado.setVisible(false);
        this.jTBuscarAbogado.setText("");
        this.situacion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BAbogado.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void combosituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosituacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosituacionActionPerformed

    private void jTBuscarSituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarSituacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSituacionActionPerformed

    private void jTBuscarSituacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarSituacionKeyPressed
        this.jTBuscarSituacion.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarSituacion.getText()).toUpperCase();
                jTBuscarSituacion.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combosituacion.getSelectedIndex()) {
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
                filtrosit(indiceColumnaTabla);
            }
        });
        trsfiltrosit = new TableRowSorter(tablasituacion.getModel());
        tablasituacion.setRowSorter(trsfiltrosit);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSituacionKeyPressed

    public void filtrosit(int nNumeroColumna) {
        trsfiltrosit.setRowFilter(RowFilter.regexFilter(this.jTBuscarSituacion.getText(), nNumeroColumna));
    }


    private void tablasituacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasituacionMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasituacionMouseClicked

    private void tablasituacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasituacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasituacionKeyPressed

    private void AceptarSituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSituacionActionPerformed
        int nFila = this.tablasituacion.getSelectedRow();
        this.situacion.setText(this.tablasituacion.getValueAt(nFila, 0).toString());
        this.nombresituacion.setText(this.tablasituacion.getValueAt(nFila, 1).toString());

        this.BSituacion.setVisible(false);
        this.jTBuscarSituacion.setText("");
        this.motivo.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSituacionActionPerformed

    private void SalirSituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSituacionActionPerformed
        this.BSituacion.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSituacionActionPerformed

    private void situacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_situacionActionPerformed
        this.BtnBuscarSituacion.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_situacionActionPerformed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Cuenta");
        modelo.addColumn("Nombres y Apellidos/Denominación");
        modelo.addColumn("Nac/Const.");
        modelo.addColumn("CIP/RUC");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Fax");
        modelo.addColumn("Mail");
        modelo.addColumn("Profesión");
        modelo.addColumn("Lugar de Trabajo");
        modelo.addColumn("Cargo que Ocupa");
        modelo.addColumn("Ingreso el");
        modelo.addColumn("Dirección Laboral");
        modelo.addColumn("Teléf. Laboral");

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.jTable1.setFont(font);
        int[] anchos = {70, 300, 100, 100, 250, 100, 100, 250, 200, 200, 100, 200, 250, 200};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCuenta;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarSituacion;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JDialog BAbogado;
    private javax.swing.JDialog BCuenta;
    private javax.swing.JDialog BGiraduria;
    private javax.swing.JDialog BSituacion;
    private javax.swing.JButton BtnBuscarAbogado;
    private javax.swing.JButton BtnBuscarGiraduria;
    private javax.swing.JButton BtnBuscarSituacion;
    private javax.swing.JButton BtnGrabarSocios;
    private javax.swing.JButton BtnGrabarSocios1;
    private javax.swing.JButton BtnSalirSocios;
    private javax.swing.JButton BtnSalirSocios1;
    private javax.swing.JButton BuscarCta;
    private javax.swing.JDialog DatosSocios;
    private javax.swing.JButton GrabaEnlace;
    private javax.swing.JButton GrabaEnlace1;
    private javax.swing.JButton SalirCuenta;
    private javax.swing.JButton SalirEnlace;
    private javax.swing.JButton SalirEnlace1;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirSituacion;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JTextField abogado;
    private javax.swing.JTextField codcliente;
    private javax.swing.JTextField codcuenta;
    private javax.swing.JComboBox comboabogado;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JComboBox comboplan;
    private javax.swing.JComboBox combosituacion;
    private javax.swing.JFormattedTextField credito;
    private javax.swing.JTextField cuentacliente;
    private javax.swing.JDialog cuentas;
    private javax.swing.JTextField cuentasocio;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JTextField descripcion;
    private javax.swing.JTextField giraduriasocio;
    private javax.swing.JCheckBox habilitardescuento;
    private javax.swing.JTextField idcta;
    private com.toedter.calendar.JDateChooser ingresofuncionario;
    private com.toedter.calendar.JDateChooser ingresosocio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTextField jTBuscarAbogado;
    private javax.swing.JTextField jTBuscarGiraduria;
    private javax.swing.JTextField jTBuscarPlan;
    private javax.swing.JTextField jTBuscarSituacion;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextOpciones1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private javax.swing.JTextField legajosocio;
    private javax.swing.JTextField lugartrabajo;
    private javax.swing.JTextArea motivo;
    private javax.swing.JTextField nombreabogado;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreclienteres90;
    private javax.swing.JTextField nombrecuenta;
    private javax.swing.JTextField nombreferencia;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nombresituacion;
    private javax.swing.JTextField nombresocio;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JDialog res90;
    private javax.swing.JTextField ruccliente;
    private javax.swing.JTextField rucsocio;
    private javax.swing.JFormattedTextField situacion;
    private javax.swing.JDialog situacionCliente;
    private javax.swing.JTable tablaabogado;
    private javax.swing.JTable tablagiraduria;
    private javax.swing.JTable tablaplan;
    private javax.swing.JTable tablasituacion;
    private javax.swing.JComboBox<String> tipores90;
    private javax.swing.JComboBox<String> turno;
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
                parameters.put("nCliente", jTextOpciones1.getText().trim());
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

    private class GrillaCliente extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            cSql = "select * from clientes order by codigo";

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    // Se crea un array que será una de las filas de la tabla.
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    Object[] fila = new Object[14]; // Hay 14 columnas en la tabla
                    fila[0] = results.getString("codigo");
                    fila[1] = results.getString("nombre");
                    fila[2] = formatoFecha.format(results.getDate("fechanacimiento"));
                    fila[3] = results.getString("ruc");
                    fila[4] = results.getString("direccion");
                    fila[5] = results.getString("telefono");
                    fila[6] = results.getString("fax");
                    fila[7] = results.getString("mail");
                    fila[8] = results.getString("profesion");
                    fila[9] = results.getString("lugartrabajo");
                    fila[10] = results.getString("cargolaboral");
                    fila[11] = formatoFecha.format(results.getDate("fechaingreso"));
                    fila[12] = results.getString("direccionlaboral");
                    fila[13] = results.getString("telefonolaboral");
                    modelo.addRow(fila);          // Se añade al modelo la fila completa.
                }
                jTable1.setRowSorter(new TableRowSorter(modelo));
                jTable1.updateUI();
                int cantFilas = jTable1.getRowCount();
                if (cantFilas > 0) {
                    jButton1.setEnabled(true);
                    jButton3.setEnabled(true);
                    jButton4.setEnabled(true);
                } else {
                    jButton1.setEnabled(false);
                    jButton3.setEnabled(false);
                    jButton4.setEnabled(true);
                }
                stm.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Ingresar a los Datos",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex);
            }
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

    private class GenerarSolicitud extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            Date FechaI = ODate.de_java_a_sql(dFechaInicial.getDate());
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cCliente", jTextOpciones1.getText().trim());
                parameters.put("FechaI", FechaI);
                parameters.put("cUsuario", Config.cNombreUsuario);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/solicitud_afuma.jasper");
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

    private class GrillaPlanCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadReg = modeloplan.getRowCount();
            for (int i = 1; i <= cantidadReg; i++) {
                modeloplan.removeRow(0);
            }
            planDAO DAOPLA = new planDAO();
            try {
                for (plan pl : DAOPLA.TodoAsentables()) {
                    String DatosPlan[] = {pl.getCodigo(), pl.getNombre()};
                    System.out.println(pl.getCodigo());
                    System.out.println(pl.getNombre());

                    modeloplan.addRow(DatosPlan);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaplan.setRowSorter(new TableRowSorter(modeloplan));
            int cantF = tablaplan.getRowCount();
        }
    }

    private class GrillaAbogado extends Thread {
        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadReg = modeloplan.getRowCount();
            for (int i = 1; i <= cantidadReg; i++) {
                modeloabogado.removeRow(0);
            }
            abogadoDAO abDAO = new abogadoDAO();
            try {
                for (abogado ab : abDAO.Todos()) {
                    String DatosPlan[] = {formatoSinpunto.format(ab.getCodigo()), ab.getNombre()};
                    modeloabogado.addRow(DatosPlan);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablaabogado.setRowSorter(new TableRowSorter(modeloabogado));
            int cantF = tablaabogado.getRowCount();
        }
    }

    private class GrillaSituacion extends Thread {
        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadReg = modelosituacion.getRowCount();
            for (int i = 1; i <= cantidadReg; i++) {
                modelosituacion.removeRow(0);
            }
            situacionDAO abDAO = new situacionDAO();
            try {
                for (situacion ab : abDAO.Todos()) {
                    String DatosPlan[] = {formatoSinpunto.format(ab.getCodigo()), ab.getNombre()};
                    modelosituacion.addRow(DatosPlan);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablasituacion.setRowSorter(new TableRowSorter(modelosituacion));
            int cantF = tablasituacion.getRowCount();
        }
    }

}

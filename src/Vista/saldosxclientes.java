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
import Clases.Parametros;
import Clases.morasxcliente;
import java.awt.Color;
import java.awt.print.PrinterException;
import java.sql.Date;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class saldosxclientes extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro;
    ObtenerFecha ODate = new ObtenerFecha();
    String cSql, cCliente = null;

    Config configuracion = new Config();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    double nCapital = 0.00;
    double nMora = 0.00;
    double nInteres = 0.00;
    int nDiaGraciaMora = 0;
    double nImporteGastos, nDiasGracia, nDiasGraciaGastos = 0.00;
    int natraso = 0;
    String catraso = null;
    String cAmortizacion;
    double nAmortizacion;
    double nPunitorio = 0.00;
    double nTasaPunitoria = 0.00;

    /**
     * Creates new form libroventaconsolidado
     */
    public saldosxclientes() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);

        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.Inicializar();
        this.cliente.setText("0");
        this.moneda.setHorizontalAlignment(JTextField.RIGHT);
        this.moneda.setText("1");
        this.nombremoneda.setText("GUARANIES");
        this.comprobante.setText("0");
        this.nombrecomprobante.setText("INGRESE TIPO CUENTA");
        this.listar.setEnabled(false);
        this.nombremoneda.setEnabled(false);
    }

    private void cargarTitulo() {
        modelo.addColumn("N° Operación");
        modelo.addColumn("Fecha");
        modelo.addColumn("Vence");
        modelo.addColumn("Ult. Pago");
        modelo.addColumn("Concepto");
        modelo.addColumn("Nro.");
        modelo.addColumn("Cuotas");
        modelo.addColumn("Amortización");
        modelo.addColumn("Int. Ordinario");
        modelo.addColumn("Int. Moratorio");
        modelo.addColumn("Gastos");
        modelo.addColumn("Saldo Cuota");
        modelo.addColumn("Int. Punitorio");
        modelo.addColumn("Dias");

        int[] anchos = {90, 130, 130, 130, 120, 70, 120, 120, 120, 120, 120, 120, 120, 120};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public void sumar() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 

        double nDiaConteo = 0.00;
        double nMora, tMora = 0.00;
        double nTotalPago, tTotalPago = 0.00;
        double nTotalAmortiza, tTotalAmortiza = 0.00;
        String cTotalAmortizacion = null;

        double nTotalMoratorio, tTotalMoratorio = 0.00;
        String cTotalMoratorio = null;

        double nTotalInteres, tTotalInteres = 0.00;
        String cTotalInteres = null;

        double nGasto, tGasto = 0.00;
        String cGasto = null;

        double nTotalPunitorio, tTotalPunitorio = 0.00;
        String cTotalPunitorio = null;

        String cTotalPago = null;
        String cinteresvencido = "";
        String cinteresavencer = "";
        String cDiaConteo = "";
        String cMora = "";

        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CHEQUES
            cDiaConteo = String.valueOf(modelo.getValueAt(i, 13));
            cDiaConteo = cDiaConteo.replace(".", "");
            cDiaConteo = cDiaConteo.replace(",", ".");
            nDiaConteo = Double.valueOf(cDiaConteo);
            //Calcular Importe en Mora
            if (nDiaConteo > 0) {
                cMora = String.valueOf(modelo.getValueAt(i, 11));
                cMora = cMora.replace(".", "");
                cMora = cMora.replace(",", ".");
                nMora = Double.valueOf(cMora);
                tMora += nMora;
            }
            //Calcular Total a Pagar
            cTotalPago = String.valueOf(modelo.getValueAt(i, 11));
            cTotalPago = cTotalPago.replace(".", "");
            cTotalPago = cTotalPago.replace(",", ".");
            nTotalPago = Double.valueOf(cTotalPago);
            tTotalPago += nTotalPago;

            //Calcular Amortizacion
            cTotalAmortizacion = String.valueOf(modelo.getValueAt(i, 7));
            cTotalAmortizacion = cTotalAmortizacion.replace(".", "");
            cTotalAmortizacion = cTotalAmortizacion.replace(",", ".");
            nTotalAmortiza = Double.valueOf(cTotalAmortizacion);
            tTotalAmortiza += nTotalAmortiza;

            //Calcular Interés Ordinario
            cTotalInteres = String.valueOf(modelo.getValueAt(i, 8));
            cTotalInteres = cTotalInteres.replace(".", "");
            cTotalInteres = cTotalInteres.replace(",", ".");
            nTotalInteres = Double.valueOf(cTotalInteres);
            tTotalInteres += nTotalInteres;

            //Calcular Interés Moratorio
            cTotalMoratorio = String.valueOf(modelo.getValueAt(i, 9));
            if (cTotalMoratorio.trim().length() > 0) {
                cTotalMoratorio = cTotalMoratorio.replace(".", "");
                cTotalMoratorio = cTotalMoratorio.replace(",", ".");
            } else {
                cTotalMoratorio = "0";
            }
            nTotalMoratorio = Double.valueOf(cTotalMoratorio);
            tTotalMoratorio += nTotalMoratorio;

            //Calcular Gastos Administrativos por Cobranzas
            cGasto = String.valueOf(modelo.getValueAt(i, 10));
            if (Double.valueOf(cGasto) > 0) {
                cGasto = cGasto.replace(".", "");
                cGasto = cGasto.replace(",", ".");
                nGasto = Double.valueOf(cGasto);
            } else {
                cGasto = "0";
                nGasto = 0;
            }
            tGasto += nGasto;

            //Calcular Interés Punitorio
            cTotalPunitorio = String.valueOf(modelo.getValueAt(i, 12));
            cTotalPunitorio = cTotalPunitorio.replace(".", "");
            cTotalPunitorio = cTotalPunitorio.replace(",", ".");
            nTotalPunitorio = Double.valueOf(cTotalPunitorio);
            tTotalPunitorio += nTotalPunitorio;
        }
        //LUEGO MOSTRAMOS LOS TOTALES
        this.totalenmora.setText(formato.format(tMora));
        this.totalpago.setText(formato.format(tTotalPago));
        this.totalamortizacion.setText(formato.format(tTotalAmortiza));
        this.totalinteresordinario.setText(formato.format(tTotalInteres));
        this.totalinteresmora.setText(formato.format(tTotalMoratorio));
        this.totalinterespunitorio.setText(formato.format(tTotalPunitorio));
        this.totalgastos.setText(formato.format(tGasto));
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
//        this.dFechaInicial.setCalendar(c2);
        //       this.dFechaFinal.setCalendar(c2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        etiquetasaldos = new org.edisoncor.gui.label.LabelMetric();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        generar = new javax.swing.JButton();
        listar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        moneda = new javax.swing.JTextField();
        nombremoneda = new javax.swing.JTextField();
        lblmoneda = new javax.swing.JLabel();
        buscarcliente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        nombrecliente = new javax.swing.JTextArea();
        lblmoneda2 = new javax.swing.JLabel();
        comprobante = new javax.swing.JTextField();
        nombrecomprobante = new javax.swing.JTextField();
        buscarcomprobante = new javax.swing.JButton();
        opcionconsulta = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        totalpago = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        totalamortizacion = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        totalinteresordinario = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        totalinteresmora = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        totalgastos = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        totalinterespunitorio = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        totalenmora = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.title")); // NOI18N

        panelTranslucido1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucido1.setColorSecundario(new java.awt.Color(0, 204, 255));

        etiquetasaldos.setBackground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setForeground(new java.awt.Color(0, 0, 0));
        etiquetasaldos.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.etiquetasaldos.text")); // NOI18N
        etiquetasaldos.setColorDeSombra(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTranslucido1Layout.setVerticalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(etiquetasaldos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        panelTranslucido2.setBackground(new java.awt.Color(204, 204, 204));
        panelTranslucido2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTranslucido2.setColorPrimario(new java.awt.Color(204, 204, 204));
        panelTranslucido2.setColorSecundario(new java.awt.Color(153, 153, 153));

        generar.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.generar.text")); // NOI18N
        generar.setToolTipText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.generar.toolTipText")); // NOI18N
        generar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        listar.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.listar.text")); // NOI18N
        listar.setToolTipText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.listar.toolTipText")); // NOI18N
        listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarActionPerformed(evt);
            }
        });

        salir.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.salir.text")); // NOI18N
        salir.setToolTipText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.salir.toolTipText")); // NOI18N
        salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jPanel2.border.title"))); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel2.text")); // NOI18N

        cliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cliente.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.cliente.text")); // NOI18N
        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });

        moneda.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.moneda.text")); // NOI18N
        moneda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                monedaFocusGained(evt);
            }
        });

        nombremoneda.setEditable(false);
        nombremoneda.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.nombremoneda.text")); // NOI18N

        lblmoneda.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.lblmoneda.text")); // NOI18N

        buscarcliente.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.buscarcliente.text")); // NOI18N
        buscarcliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarclienteActionPerformed(evt);
            }
        });

        nombrecliente.setColumns(20);
        nombrecliente.setRows(5);
        jScrollPane2.setViewportView(nombrecliente);

        lblmoneda2.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.lblmoneda2.text")); // NOI18N

        comprobante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comprobante.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.comprobante.text")); // NOI18N
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

        nombrecomprobante.setEditable(false);
        nombrecomprobante.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.nombrecomprobante.text")); // NOI18N

        buscarcomprobante.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.buscarcomprobante.text")); // NOI18N
        buscarcomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcomprobanteActionPerformed(evt);
            }
        });

        opcionconsulta.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.opcionconsulta.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombrecomprobante, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nombremoneda, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblmoneda2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarcomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblmoneda)
                            .addComponent(opcionconsulta))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblmoneda2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarcomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombrecomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblmoneda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(opcionconsulta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelTranslucido2Layout = new javax.swing.GroupLayout(panelTranslucido2);
        panelTranslucido2.setLayout(panelTranslucido2Layout);
        panelTranslucido2Layout.setHorizontalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTranslucido2Layout.setVerticalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(generar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salir)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(modelo);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1040, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jPanel3.border.title"))); // NOI18N

        totalpago.setEditable(false);
        totalpago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        totalpago.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalpago.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.totalpago.text")); // NOI18N
        totalpago.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel3.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel3.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel4.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel4.text")); // NOI18N

        totalamortizacion.setEditable(false);
        totalamortizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        totalamortizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalamortizacion.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.totalamortizacion.text")); // NOI18N
        totalamortizacion.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel5.text")); // NOI18N

        totalinteresordinario.setEditable(false);
        totalinteresordinario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        totalinteresordinario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalinteresordinario.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.totalinteresordinario.text")); // NOI18N
        totalinteresordinario.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel6.text")); // NOI18N

        totalinteresmora.setEditable(false);
        totalinteresmora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        totalinteresmora.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalinteresmora.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.totalinteresmora.text")); // NOI18N
        totalinteresmora.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel7.text")); // NOI18N

        totalgastos.setEditable(false);
        totalgastos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        totalgastos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalgastos.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.totalgastos.text")); // NOI18N
        totalgastos.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel8.text")); // NOI18N

        totalinterespunitorio.setEditable(false);
        totalinterespunitorio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        totalinterespunitorio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalinterespunitorio.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.totalinterespunitorio.text")); // NOI18N
        totalinterespunitorio.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel9.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.jLabel9.text")); // NOI18N

        totalenmora.setEditable(false);
        totalenmora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        totalenmora.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalenmora.setText(org.openide.util.NbBundle.getMessage(saldosxclientes.class, "saldosxclientes.totalenmora.text")); // NOI18N
        totalenmora.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalinteresmora, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalamortizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalenmora, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalinteresordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalinterespunitorio, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalgastos, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalgastos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalinterespunitorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(33, 33, 33))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(totalamortizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(totalenmora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(totalinteresordinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalinteresmora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTranslucido1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelTranslucido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        generarconsulta consulta = new generarconsulta();
        Thread HiloConsulta = new Thread(consulta);
        HiloConsulta.start();        // TODO add your handling code here:
    }//GEN-LAST:event_generarActionPerformed

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        //   MessageFormat headerFormat = new MessageFormat("Tutorial");
        //    MessageFormat footerFormat = new MessageFormat("Fito");
        //    try {
        //       jTable1.print(JTable.PrintMode.NORMAL, footerFormat, footerFormat);
        //  } catch (PrinterException ex) {
        //       Exceptions.printStackTrace(ex);
        //   }
        GenerarReporte GenerarReporte = new GenerarReporte();
        Thread HiloReporte = new Thread(GenerarReporte);
        HiloReporte.start();

    }//GEN-LAST:event_listarActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        showcliente mostrarcliente = new showcliente();
        mostrarcliente.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void buscarclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarclienteActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaClientes2 cu = new Vista.ConsultaClientes2();
        cu.setVisible(true);
        this.comprobante.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_buscarclienteActionPerformed

    private void monedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_monedaFocusGained
        this.comprobante.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
        this.nombrecomprobante.setText(Parametros.NOMBRE_ELEGIDO.trim());
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaFocusGained

    private void comprobanteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comprobanteFocusGained
        if (Parametros.CODIGO_ELEGIDO > 0) {
            this.cliente.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombrecliente.setText(Parametros.NOMBRE_ELEGIDO.trim());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteFocusGained

    private void buscarcomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcomprobanteActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("comprobantes");
        cu.setVisible(true);
        this.moneda.requestFocus();         // TODO add your handling code here:
    }//GEN-LAST:event_buscarcomprobanteActionPerformed

    private void comprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprobanteActionPerformed
        showcomprobante mostrarcomprobante = new showcomprobante();
        mostrarcomprobante.start();

        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteActionPerformed

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
                new saldosxclientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarcliente;
    private javax.swing.JButton buscarcomprobante;
    private javax.swing.JTextField cliente;
    private javax.swing.JTextField comprobante;
    private org.edisoncor.gui.label.LabelMetric etiquetasaldos;
    private javax.swing.JButton generar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblmoneda;
    private javax.swing.JLabel lblmoneda2;
    private javax.swing.JButton listar;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextArea nombrecliente;
    private javax.swing.JTextField nombrecomprobante;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JCheckBox opcionconsulta;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JButton salir;
    private javax.swing.JFormattedTextField totalamortizacion;
    private javax.swing.JFormattedTextField totalenmora;
    private javax.swing.JFormattedTextField totalgastos;
    private javax.swing.JFormattedTextField totalinteresmora;
    private javax.swing.JFormattedTextField totalinteresordinario;
    private javax.swing.JFormattedTextField totalinterespunitorio;
    private javax.swing.JFormattedTextField totalpago;
    // End of variables declaration//GEN-END:variables

    private class generarconsulta extends Thread {

        public void run() {

            //Dando formato a los datos tipo Fecha
            cSql = "SELECT cuenta_clientes.creferencia,cuenta_clientes.iddocumento,cuenta_clientes.documento,cuenta_clientes.fecha,";
            cSql = cSql + "cuenta_clientes.vencimiento,cuenta_clientes.giraduria,cuenta_clientes.comprobante,cuenta_clientes.amortiza,cuenta_clientes.minteres,";
            cSql = cSql + "cuenta_clientes.importe,cuenta_clientes.cliente,cuenta_clientes.saldo,cuenta_clientes.tasaoperativa,";
            cSql = cSql + "cuenta_clientes.autorizacion,cuenta_clientes.nrocuenta,cuenta_clientes.cuota,cuenta_clientes.numerocuota,";
            cSql = cSql + "clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,cuenta_clientes.fecha_pago,comprobantes.nomalias,DATEDIFF(CURDATE(),fecha_pago) AS di,";
            cSql = cSql + "comprobantes.diasgracia_gastos,comprobantes.diasgracia,comprobantes.interesmora,comprobantes.gastoscobros,comprobantes.interespunitorio ";
            cSql = cSql + "FROM cuenta_clientes ";
            cSql = cSql + "INNER JOIN CLIENTES ";
            cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + "INNER JOIN comprobantes ";
            cSql = cSql + "ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + "WHERE cuenta_clientes.saldo>0 ";
            cSql = cSql + " and cuenta_clientes.moneda= " + moneda.getText();
            if (cliente.getText() != "0") {
                cSql = cSql + " and cuenta_clientes.cliente= " + cliente.getText();
            }
            if (opcionconsulta.isSelected()) {
                cSql = cSql + " and cuenta_clientes.vencimiento<=curdate() ";
            }
            cSql = cSql + " and cuenta_clientes.comprobante= " + comprobante.getText();
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            System.out.println(cSql);

            //Uso la Clase SimpleDateFormat para darle formato al campo fecha
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            //Instanciamos esta clase para alinear las celdas numericas a la derecha
            DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
            TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

            //Llamo a la clase conexion para conectarme a la base de datos
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;

            jTable1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);

//              jTable1.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            try {

                results = stm.executeQuery(cSql);
                while (results.next()) {
                    // Se crea un array que será una de las filas de la tabla.
                    Object[] fila = new Object[14]; // Hay 13 columnas en la tabla
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    //Verificamos si tiene fecha de pago cargado
                    //Si tiene fecha de pago
                    //usamos como fecha de ultimo pago
                    //de lo contrario la fecha de vencimiento
                    int ndiamora = results.getInt("atraso");
                    nMora = 0.00;
                    int nComprobante = results.getInt("comprobante");
                    nImporteGastos = 0.00;
                    nPunitorio = 0.00;
                    nCapital = results.getDouble("saldo");
                    nDiasGraciaGastos = results.getInt("diasgracia_gastos");
                    catraso = results.getString("di");
                    natraso = results.getInt("di");
                    nTasaPunitoria = results.getDouble("interespunitorio");
                    nDiaGraciaMora = results.getInt("diasgracia");

                    if (ndiamora > 0 && catraso != null) {
                        if (natraso <= ndiamora) {
                            natraso = results.getInt("di");
                        } else {
                            natraso = ndiamora;
                        }
                    } else {
                        natraso = ndiamora;
                    }

                    if (natraso > nDiaGraciaMora) {
                        nInteres = results.getDouble("tasaoperativa");
                        nMora = Math.round(nCapital * ((nInteres / 100) / 360 * natraso));
                        if (Config.nIvaIncluido == 1) {
                            nMora = Math.round(nMora + (nMora * Config.porcentajeiva / 100));
                        }
                        fila[9] = formato.format(nMora);
                    } else {
                        fila[9] = formato.format(0);
                    }

                    fila[10] = formato.format(0);
                    if (Config.nIvaIncluido == 1) {
                        if (natraso > nDiasGraciaGastos) {
                            nImporteGastos = results.getDouble("gastoscobros");
                            fila[10] = formato.format(nImporteGastos);
                        }
                    } else {
                        nCapital = results.getDouble("importe");
                        if (natraso > nDiasGraciaGastos) {
                            if (nComprobante == 8) {
                                if (natraso <= 30) {
                                    nImporteGastos = Math.round(nCapital * 5 / 100);
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = Math.round(nCapital * 10 / 100);
                                } else if (natraso >= 61) {
                                    nImporteGastos = Math.round(nCapital * 20 / 100);
                                }
                            }
                            if (nComprobante == 9) {
                                if (natraso <= 30) {
                                    nImporteGastos = Math.round(nCapital * 5 / 100);
                                } else if (natraso > 31 && natraso <= 60) {
                                    nImporteGastos = Math.round(nCapital * 10 / 100);
                                } else if (natraso >= 61 && natraso <= 360) {
                                    nImporteGastos = Math.round(nCapital * 20 / 100);
                                } else if (natraso >= 361 && natraso <= 540) {
                                    nImporteGastos = Math.round(nCapital * 25 / 100);
                                } else if (natraso >= 541 && natraso <= 720) {
                                    nImporteGastos = Math.round(nCapital * 30 / 100);
                                } else if (natraso >= 721 && natraso <= 900) {
                                    nImporteGastos = Math.round(nCapital * 40 / 100);
                                } else if (natraso >= 901) {
                                    nImporteGastos = Math.round(nCapital * 50 / 100);
                                }
                            }

                            if (nComprobante == 10) {
                                if (natraso <= 30) {
                                    nImporteGastos = Math.round(nCapital * 3 / 100);
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = Math.round(nCapital * 6 / 100);
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = Math.round(nCapital * 10 / 100);
                                } else if (natraso >= 90) {
                                    nImporteGastos = Math.round(nCapital * 15 / 100);
                                }
                            }
                            fila[10] = formato.format(nImporteGastos);
                        }
                    }
                    //Se calcula el interes punitorio en caso que tenga la tasa estipulada
                    if (nTasaPunitoria > 0 && nMora > 0) {
                        nPunitorio = Math.round(nMora * nTasaPunitoria / 100);
                    }

                    fila[0] = results.getString("documento");
                    fila[1] = formatoFecha.format(results.getDate("fecha"));
                    fila[2] = formatoFecha.format(results.getDate("vencimiento"));
                    if (results.getDate("fecha_pago") == null) {
                        fila[3] = "";
                    } else {
                        fila[3] = formatoFecha.format(results.getDate("fecha_pago"));
                    }
                    fila[4] = results.getString("nomalias");
                    fila[5] = formato.format(results.getDouble("cuota"));
                    fila[6] = formato.format(results.getInt("numerocuota"));
                    fila[7] = formato.format(results.getDouble("amortiza"));
                    fila[8] = formato.format(results.getDouble("minteres"));
                    fila[11] = formato.format(nCapital + nMora + nImporteGastos + nPunitorio);
                    fila[12] = formato.format(nPunitorio);
                    fila[13] = formato.format(natraso);

                    modelo.addRow(fila);
                    jTable1.setRowSorter(new TableRowSorter(modelo));
                    //    jTable1.updateUI();
                    int cantFilas = jTable1.getRowCount();
                    if (cantFilas > 0) {
                        listar.setEnabled(true);
                    } else {
                        listar.setEnabled(false);
                    }
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            try {
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            sumar();
        }
    }

    private class GenerarReporte extends Thread {

        public void run() {
            //            dFecha = ODate.de_java_a_sql(formatoFecha.parse(jTable1.getValueAt(i, 1).toString()));
            int nFila = 0;
            List Resultados = new ArrayList();
            morasxcliente tipo;

            for (nFila = 0; nFila < jTable1.getRowCount(); nFila++) {
                tipo = new morasxcliente(jTable1.getValueAt(nFila, 0).toString(), jTable1.getValueAt(nFila, 1).toString(), jTable1.getValueAt(nFila, 2).toString(), jTable1.getValueAt(nFila, 3).toString(), jTable1.getValueAt(nFila, 4).toString(), jTable1.getValueAt(nFila, 5).toString(), jTable1.getValueAt(nFila, 6).toString(), jTable1.getValueAt(nFila, 7).toString(), jTable1.getValueAt(nFila, 8).toString(), jTable1.getValueAt(nFila, 9).toString(), jTable1.getValueAt(nFila, 10).toString(), jTable1.getValueAt(nFila, 11).toString(), jTable1.getValueAt(nFila, 12).toString(), jTable1.getValueAt(nFila, 13).toString());
                Resultados.add(tipo);
            }

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("nombremoneda", nombremoneda.getText());
                parameters.put("cCliente", cliente.getText());
                parameters.put("cNombreCliente", nombrecliente.getText());
                parameters.put("cMoneda", moneda.getText());
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
                parameters.put("totalpago", totalpago.getText());
                parameters.put("totalenmora", totalenmora.getText());
                parameters.put("totalamortizacion", totalamortizacion.getText());
                parameters.put("totalinteresordinario", totalinteresordinario.getText());
                parameters.put("totalinteresmora", totalinteresmora.getText());
                parameters.put("totalgastos", totalgastos.getText());
                parameters.put("totalinterespunitorio", totalinterespunitorio.getText());

                JasperReport jr = null;

                URL url = getClass().getClassLoader().getResource("Reports/morasxcliente.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
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

    private class showcliente extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT nombre from clientes where codigo=" + cliente.getText());
                while (results.next()) {
                    nombrecliente.setText(results.getString("nombre"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }
        }
    }

    private class showcomprobante extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT codigo,nombre from comprobantes where codigo=" + comprobante.getText());
                while (results.next()) {
                    nombrecomprobante.setText(results.getString("nombre"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.Parametros;
import Clases.TablaCobros;
import Clases.UUID;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import Modelo.Tablas;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class detalle_cobranzas extends javax.swing.JFrame {

    /**
     * Creates new form cobranzas
     */
    String cDescuentos, cValorActual = null;
    double nDescuentos, sumatoria, sumtotal = 0.00;

    Conexion con = null;
    Statement stm = null;
    TablaCobros modelo = new TablaCobros();
    Tablas modelo2 = new Tablas();

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    private TableRowSorter trsfiltro;

    JScrollPane scroll = new JScrollPane();
    ResultSet results = null;
    BDConexion BD = new BDConexion();
    String Operacion = null;
    ObtenerFecha ODate = new ObtenerFecha();
    String Sucursal = null;
    String Cobrador = null;
    String Moneda = null;
    int cCuota = 1;
    int nFila = 0;

    public detalle_cobranzas(String cOpcion) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.totalamortizacion.setHorizontalAlignment(JTextField.RIGHT);
        this.interesordinario.setHorizontalAlignment(JTextField.RIGHT);
        this.interesvencido.setHorizontalAlignment(JTextField.RIGHT);
        this.interesavencer.setHorizontalAlignment(JTextField.RIGHT);
        this.totalbruto.setHorizontalAlignment(JTextField.RIGHT);
        this.descuentoxpagos.setHorizontalAlignment(JTextField.RIGHT);
        this.gastosxcobrador.setHorizontalAlignment(JTextField.RIGHT);
        this.totalpago.setHorizontalAlignment(JTextField.RIGHT);
        this.cliente.setHorizontalAlignment(JTextField.RIGHT);
        this.cotizacion.setHorizontalAlignment(JTextField.RIGHT);
        this.totalamortizacion.setHorizontalAlignment(JTextField.RIGHT);
        this.Modo.setVisible(false);
        this.idControl.setVisible(false);
        Calendar c2 = new GregorianCalendar();
        this.sucursal.requestFocus();
        if (cOpcion == "new") {
            this.Modo.setText(cOpcion);
            this.limpiarCombos();
            this.idControl.setText("");
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            this.fecha.setCalendar(c2);
            this.emisioncheque.setCalendar(c2);
            this.emisiontarjeta.setCalendar(c2);

            this.totalamortizacion.setText("0");
            this.interesordinario.setText("0");
            this.interesvencido.setText("0");
            this.interesavencer.setText("0");
            this.totalbruto.setText("0");
            this.descuentoxpagos.setText("0");
            this.gastosxcobrador.setText("0");
            this.totalpago.setText("0");
            this.pagotarjeta.setText("0");

            this.cobroefectivo.setText("0");
            this.cobrocheque.setText("0");
            this.cobroefectivo.setText("0");
            this.totalbrutoapagar.setText("0");

            this.cotizacion.setText(formato.format(1));
            numerorecibo.start();
        } else {
            this.idControl.setText(cOpcion);
            //this.consultarTabla();
            //this.VerDetalleCuotas();
            //this.consultarPagos();
            //    mostraracreedor mostraracreedor = new mostraracreedor();
            //    Thread HiloAcreedor = new Thread(mostraracreedor);
            //     HiloAcreedor.start();
            // this.sumatoria();
        }

        this.Titulo();
    }

    NumRecibo numerorecibo = new NumRecibo();
    GuardarCobranza guardacobro = new GuardarCobranza();

    private void Titulo() {
        modelo.addColumn("Ref.");
        modelo.addColumn("N° Op.");
        modelo.addColumn("Emisión");
        modelo.addColumn("Vence");
        modelo.addColumn("Tipo");
        modelo.addColumn("Nº");
        modelo.addColumn("Amortización");
        modelo.addColumn("Int. Ord.");
        modelo.addColumn("Saldo Cuota");
        modelo.addColumn("Int. Mora");
        modelo.addColumn("Gastos");
        modelo.addColumn("Total a Pagar");
        modelo.addColumn("Su Pago");
        modelo.addColumn("Capital");
        modelo.addColumn("Inversor");
        modelo.addColumn("Cuotas");
        modelo.addColumn("Cód.");
        modelo.addColumn("Mora");
        modelo.addColumn("IVA Int.");
        modelo.addColumn("Int. Punitorio");
        int[] anchos = {5, 60, 120, 120, 40, 40, 80, 80, 80, 80, 80, 80, 80, 80, 80, 50, 30, 50, 80, 80};
        //           1  2    3    4    5  6    7   8  9   10  11  12  13  14  15  16  17  18  19  20   
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public void sumarcobros() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 

        double amortizacion = 0.00;
        double supago = 0.00;
        double sumpago = 0.00;
        double sumamortizacion = 0.00;
        double totalinteresordinario = 0.00;
        double suminteresordinario = 0.00;
        double interesmoratorio = 0.00;
        double gastos = 0.00;
        double totalcobro = 0.00;
        double ninteresvencido = 0.00;
        double ninteresavencer = 0.00;
        double nDiaConteo = 0.00;
        String cinteresvencido = "";
        String cinteresavencer = "";
        String cDiaConteo = "";

        String camortizacion = "";
        String csupago = "";
        String cinteresordinario = "";
        String cinteresmoratorio = "";
        String cgastos = "";
        String ctotalcobro = "";
        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CHEQUES
            cDiaConteo = String.valueOf(modelo.getValueAt(i, 17));
            cDiaConteo = cDiaConteo.replace(".", "");
            cDiaConteo = cDiaConteo.replace(",", ".");
            nDiaConteo = Double.parseDouble(String.valueOf(cDiaConteo));

            csupago = String.valueOf(modelo.getValueAt(i, 12));
            csupago = csupago.replace(".", "");
            csupago = csupago.replace(",", ".");
            supago = Double.parseDouble(String.valueOf(csupago));
            sumpago += supago;

            if (supago != 0) {
                camortizacion = String.valueOf(modelo.getValueAt(i, 6));
                camortizacion = camortizacion.replace(".", "");
                camortizacion = camortizacion.replace(",", ".");
                amortizacion = Double.parseDouble(String.valueOf(camortizacion));
                sumamortizacion += amortizacion;
                //SUMA EL TOTAL DE DESCUENTOS

                cinteresordinario = String.valueOf(modelo.getValueAt(i, 7));
                cinteresordinario = cinteresordinario.replace(".", "");
                cinteresordinario = cinteresordinario.replace(",", ".");
                totalinteresordinario = Double.parseDouble(String.valueOf(cinteresordinario));
                suminteresordinario += totalinteresordinario;

                if (nDiaConteo >= 0) {
                    ninteresvencido += totalinteresordinario;
                }
                if (nDiaConteo < 0) {
                    ninteresavencer += totalinteresordinario;
                }
            }
        }
        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.interesavencer.setText(formato.format(ninteresavencer));
        this.interesvencido.setText(formato.format(ninteresvencido));
        this.totalamortizacion.setText(formato.format(sumamortizacion));
        this.interesordinario.setText(formato.format(suminteresordinario));
        this.totalbruto.setText(formato.format(sumpago));
        this.totalpago.setText(formato.format(sumpago));
        //formato.format(sumatoria1);
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

    public void limpiarCombos() {
        this.sucursal.removeAllItems();
        BD.cargarCombo("select codigo,nombre from sucursales", this.sucursal);
        this.sucursal.setSelectedIndex(0);

        this.moneda.removeAllItems();;
        BD.cargarCombo("select codigo,nombre from monedas order by codigo", this.moneda);
        this.moneda.setSelectedIndex(0);

        this.cobrador.removeAllItems();
        BD.cargarCombo("select codigo,nombre from cobradores where estado=1  order by nombre", this.cobrador);
        this.cobrador.setSelectedIndex(0);

        this.cargobanco.removeAllItems();
        BD.cargarCombo("select codigo,nombre from bancos_plaza  order by nombre", this.cargobanco);
        this.cargobanco.setSelectedIndex(0);

        this.cargoemisor.removeAllItems();
        BD.cargarCombo("select codigo,nombre from bancos_plaza  order by nombre", this.cargoemisor);
        this.cargoemisor.setSelectedIndex(0);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PagoParcial = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        nrodocumento = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        concepto = new javax.swing.JTextField();
        importe_a_pagar = new javax.swing.JFormattedTextField();
        importe_pagado = new javax.swing.JFormattedTextField();
        nrocuota = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        GrabarPagoParcial = new javax.swing.JButton();
        SalirPagoParcial = new javax.swing.JButton();
        ingresar_cobros = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        totalbrutoapagar = new javax.swing.JFormattedTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        cobroefectivo = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        cobrocheque = new javax.swing.JFormattedTextField();
        cargobanco = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        nrocheque = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        emisioncheque = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        pagotarjeta = new javax.swing.JFormattedTextField();
        cargoemisor = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        nrotarjeta = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        emisiontarjeta = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        AceptarCobro = new javax.swing.JButton();
        SalirFormaCobro = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        moneda = new javax.swing.JComboBox();
        cotizacion = new javax.swing.JFormattedTextField();
        sucursal = new javax.swing.JComboBox();
        cliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        direccion = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cobrador = new javax.swing.JComboBox();
        nombrecliente = new javax.swing.JTextField();
        ruc = new javax.swing.JTextField();
        Modo = new javax.swing.JTextField();
        idControl = new javax.swing.JTextField();
        BuscarCliente = new javax.swing.JButton();
        MostrarCuentas = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        GrabarCobro = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        totalamortizacion = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        interesordinario = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        interesvencido = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        interesavencer = new javax.swing.JFormattedTextField();
        boxdescuentos = new javax.swing.JCheckBox();
        boxcuotas = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        totalbruto = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        descuentoxpagos = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        gastosxcobrador = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        totalpago = new javax.swing.JFormattedTextField();
        boxgastos = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nrorecibo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();

        PagoParcial.setTitle(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.PagoParcial.title")); // NOI18N

        jPanel7.setBackground(new java.awt.Color(102, 255, 204));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(102, 102, 255)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel21.text")); // NOI18N

        nrodocumento.setEditable(false);
        nrodocumento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrodocumento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrodocumento.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.nrodocumento.text")); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel22.text")); // NOI18N

        concepto.setEditable(false);
        concepto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        concepto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        concepto.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.concepto.text")); // NOI18N
        concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conceptoActionPerformed(evt);
            }
        });

        importe_a_pagar.setEditable(false);
        importe_a_pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_a_pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_a_pagar.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.importe_a_pagar.text")); // NOI18N
        importe_a_pagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        importe_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_pagado.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.importe_pagado.text")); // NOI18N
        importe_pagado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        importe_pagado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                importe_pagadoFocusGained(evt);
            }
        });
        importe_pagado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importe_pagadoKeyPressed(evt);
            }
        });

        nrocuota.setEditable(false);
        nrocuota.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrocuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrocuota.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.nrocuota.text")); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel23.text")); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel24.text")); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel25.text")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(importe_a_pagar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(nrodocumento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importe_pagado)
                    .addComponent(nrocuota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(concepto, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(nrodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nrocuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importe_a_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importe_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(153, 153, 255));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarPagoParcial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarPagoParcial.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.GrabarPagoParcial.text")); // NOI18N
        GrabarPagoParcial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarPagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarPagoParcialActionPerformed(evt);
            }
        });

        SalirPagoParcial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirPagoParcial.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.SalirPagoParcial.text")); // NOI18N
        SalirPagoParcial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirPagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPagoParcialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(GrabarPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(SalirPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarPagoParcial)
                    .addComponent(SalirPagoParcial))
                .addContainerGap())
        );

        javax.swing.GroupLayout PagoParcialLayout = new javax.swing.GroupLayout(PagoParcial.getContentPane());
        PagoParcial.getContentPane().setLayout(PagoParcialLayout);
        PagoParcialLayout.setHorizontalGroup(
            PagoParcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PagoParcialLayout.setVerticalGroup(
            PagoParcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagoParcialLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel20.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel20.text")); // NOI18N

        totalbrutoapagar.setEditable(false);
        totalbrutoapagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalbrutoapagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalbrutoapagar.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.totalbrutoapagar.text")); // NOI18N
        totalbrutoapagar.setDisabledTextColor(new java.awt.Color(255, 51, 51));
        totalbrutoapagar.setEnabled(false);
        totalbrutoapagar.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalbrutoapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalbrutoapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel26.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel26.text")); // NOI18N

        cobroefectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cobroefectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobroefectivo.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.cobroefectivo.text")); // NOI18N
        cobroefectivo.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        cobroefectivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cobroefectivoFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cobroefectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(cobroefectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jPanel11.border.title"))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel27.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel27.text")); // NOI18N

        cobrocheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cobrocheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobrocheque.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.cobrocheque.text")); // NOI18N
        cobrocheque.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        cargobanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel28.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel28.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel28.text")); // NOI18N

        nrocheque.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.nrocheque.text")); // NOI18N

        jLabel29.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel29.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel29.text")); // NOI18N

        jLabel33.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel33.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel33.text")); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel27))
                    .addComponent(cargobanco, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(cobrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel28)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33)
                        .addGap(30, 30, 30))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cargobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cobrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jPanel12.border.title"))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel30.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel30.text")); // NOI18N

        pagotarjeta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        pagotarjeta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pagotarjeta.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.pagotarjeta.text")); // NOI18N
        pagotarjeta.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        cargoemisor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel31.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel31.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel31.text")); // NOI18N

        nrotarjeta.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.nrotarjeta.text")); // NOI18N

        jLabel32.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel32.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel32.text")); // NOI18N

        jLabel34.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel34.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel34.text")); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel30))
                    .addComponent(cargoemisor, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel31)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel32)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(11, 11, 11)))
                .addGap(17, 17, 17))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(14, 14, 14)
                        .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cargoemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        AceptarCobro.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.AceptarCobro.text")); // NOI18N
        AceptarCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCobroActionPerformed(evt);
            }
        });

        SalirFormaCobro.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.SalirFormaCobro.text")); // NOI18N
        SalirFormaCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFormaCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFormaCobroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AceptarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(SalirFormaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCobro)
                    .addComponent(SalirFormaCobro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ingresar_cobrosLayout = new javax.swing.GroupLayout(ingresar_cobros.getContentPane());
        ingresar_cobros.getContentPane().setLayout(ingresar_cobrosLayout);
        ingresar_cobrosLayout.setHorizontalGroup(
            ingresar_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ingresar_cobrosLayout.setVerticalGroup(
            ingresar_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingresar_cobrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel2.text")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel3.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel4.text")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel5.text")); // NOI18N

        fecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        moneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        moneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        moneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monedaActionPerformed(evt);
            }
        });

        cotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.cotizacion.text")); // NOI18N
        cotizacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        sucursal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cliente.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.cliente.text")); // NOI18N
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

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel6.text")); // NOI18N

        direccion.setEditable(false);
        direccion.setBackground(new java.awt.Color(204, 204, 255));
        direccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        direccion.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.direccion.text")); // NOI18N
        direccion.setBorder(null);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel14.text")); // NOI18N

        cobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cobrador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        nombrecliente.setEditable(false);
        nombrecliente.setBackground(new java.awt.Color(204, 204, 255));
        nombrecliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nombrecliente.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.nombrecliente.text")); // NOI18N
        nombrecliente.setBorder(null);

        ruc.setEditable(false);
        ruc.setBackground(new java.awt.Color(204, 204, 255));
        ruc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ruc.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.ruc.text")); // NOI18N
        ruc.setBorder(null);

        Modo.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.Modo.text")); // NOI18N

        idControl.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.idControl.text")); // NOI18N

        BuscarCliente.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.BuscarCliente.text")); // NOI18N
        BuscarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        MostrarCuentas.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.MostrarCuentas.text")); // NOI18N
        MostrarCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarCuentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(11, 11, 11))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(moneda, 0, 162, Short.MAX_VALUE)
                                        .addComponent(sucursal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(MostrarCuentas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel6)
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(MostrarCuentas)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(modelo);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCobro.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.GrabarCobro.text")); // NOI18N
        GrabarCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCobroActionPerformed(evt);
            }
        });

        Salir.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.Salir.text")); // NOI18N
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel9.text")); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel17.text")); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel18.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCobro)
                    .addComponent(Salir)
                    .addComponent(jLabel9)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel7.text")); // NOI18N

        totalamortizacion.setEditable(false);
        totalamortizacion.setBackground(new java.awt.Color(204, 204, 255));
        totalamortizacion.setBorder(null);
        totalamortizacion.setForeground(new java.awt.Color(255, 0, 0));
        totalamortizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalamortizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalamortizacion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel8.text")); // NOI18N

        interesordinario.setEditable(false);
        interesordinario.setBackground(new java.awt.Color(204, 204, 255));
        interesordinario.setBorder(null);
        interesordinario.setForeground(new java.awt.Color(255, 0, 0));
        interesordinario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        interesordinario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel10.text")); // NOI18N

        interesvencido.setEditable(false);
        interesvencido.setBackground(new java.awt.Color(204, 204, 255));
        interesvencido.setBorder(null);
        interesvencido.setForeground(new java.awt.Color(255, 0, 0));
        interesvencido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        interesvencido.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        interesvencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interesvencidoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel11.text")); // NOI18N

        interesavencer.setEditable(false);
        interesavencer.setBackground(new java.awt.Color(204, 204, 255));
        interesavencer.setBorder(null);
        interesavencer.setForeground(new java.awt.Color(255, 0, 0));
        interesavencer.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        interesavencer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        interesavencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interesavencerActionPerformed(evt);
            }
        });

        boxdescuentos.setBackground(new java.awt.Color(204, 204, 255));
        boxdescuentos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxdescuentos.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.boxdescuentos.text")); // NOI18N
        boxdescuentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxdescuentosActionPerformed(evt);
            }
        });

        boxcuotas.setBackground(new java.awt.Color(204, 204, 255));
        boxcuotas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxcuotas.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.boxcuotas.text")); // NOI18N
        boxcuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxcuotasActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel12.text")); // NOI18N

        totalbruto.setEditable(false);
        totalbruto.setBackground(new java.awt.Color(204, 204, 255));
        totalbruto.setBorder(null);
        totalbruto.setForeground(new java.awt.Color(255, 0, 0));
        totalbruto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalbruto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel13.text")); // NOI18N

        descuentoxpagos.setEditable(false);
        descuentoxpagos.setBackground(new java.awt.Color(204, 204, 255));
        descuentoxpagos.setBorder(null);
        descuentoxpagos.setForeground(new java.awt.Color(255, 0, 0));
        descuentoxpagos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        descuentoxpagos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel15.text")); // NOI18N

        gastosxcobrador.setEditable(false);
        gastosxcobrador.setBackground(new java.awt.Color(204, 204, 255));
        gastosxcobrador.setBorder(null);
        gastosxcobrador.setForeground(new java.awt.Color(255, 0, 0));
        gastosxcobrador.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gastosxcobrador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        gastosxcobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gastosxcobradorActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel16.text")); // NOI18N

        totalpago.setEditable(false);
        totalpago.setBackground(new java.awt.Color(204, 204, 255));
        totalpago.setBorder(null);
        totalpago.setForeground(new java.awt.Color(255, 0, 0));
        totalpago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalpago.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        boxgastos.setBackground(new java.awt.Color(204, 204, 255));
        boxgastos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxgastos.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.boxgastos.text")); // NOI18N
        boxgastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxgastosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(interesvencido, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalamortizacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(interesavencer))
                .addGap(136, 136, 136)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxcuotas)
                    .addComponent(boxdescuentos)
                    .addComponent(boxgastos))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(descuentoxpagos)
                    .addComponent(totalbruto)
                    .addComponent(gastosxcobrador)
                    .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(totalamortizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalbruto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(boxdescuentos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descuentoxpagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(boxcuotas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(interesvencido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(boxgastos))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gastosxcobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(interesavencer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel16)
                    .addComponent(totalpago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.jLabel1.text")); // NOI18N

        nrorecibo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        nrorecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nrorecibo.setText(org.openide.util.NbBundle.getMessage(detalle_cobranzas.class, "detalle_cobranzas.nrorecibo.text")); // NOI18N
        nrorecibo.setBorder(null);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(nrorecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nrorecibo)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setForeground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void interesvencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interesvencidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_interesvencidoActionPerformed

    private void interesavencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interesavencerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_interesavencerActionPerformed

    private void boxdescuentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxdescuentosActionPerformed
        if (this.boxdescuentos.isSelected()) {
            cDescuentos = this.interesavencer.getText();
            cDescuentos = cDescuentos.replace(".", "").replace(",", ".");
            nDescuentos = Double.parseDouble(cDescuentos);
            nDescuentos = Math.round(nDescuentos * Config.limitedescuento / 100);
            this.descuentoxpagos.setText(formato.format(nDescuentos));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(nBruto - nDescuentos));

        } else {
            this.descuentoxpagos.setText(formato.format(0));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(cBruto));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_boxdescuentosActionPerformed

    private void boxcuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxcuotasActionPerformed
        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        sumatoria = 0.00;
        sumtotal = 0.00;
        if (boxcuotas.isSelected()) { // Si hemos dado clic en el jCheckBox
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(this.jTable1.getValueAt(i, 11), i, 12);
                cValorActual = String.valueOf(modelo.getValueAt(i, 12));
                cValorActual = cValorActual.replace(".", "");
                cValorActual = cValorActual.replace(",", ".");
                sumatoria = Double.parseDouble(cValorActual);
                sumtotal += sumatoria;
            }
        } else {
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(0, i, 12);
            }
        }
        this.sumarcobros();
    }//GEN-LAST:event_boxcuotasActionPerformed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Parametros.RUC_ELEGIDO = "";
        Vista.ConsultaClientes2 cu = new Vista.ConsultaClientes2();
        cu.setVisible(true);
        this.fecha.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clienteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fecha.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            this.BuscarCliente.doClick();
        }
    }//GEN-LAST:event_clienteKeyPressed

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        BusClientes buscliente = new BusClientes();
        buscliente.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.cliente.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombrecliente.setText(Parametros.NOMBRE_ELEGIDO.trim());
            this.ruc.setText(Parametros.RUC_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
            Parametros.RUC_ELEGIDO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            this.jTable1.setValueAt(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 11), this.jTable1.getSelectedRow(), 12);
            this.sumarcobros();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            nFila = this.jTable1.getSelectedRow();
            this.nrodocumento.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1).toString());
            this.concepto.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 4).toString());
            this.nrocuota.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 5).toString());
            this.importe_a_pagar.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 11).toString());
            this.importe_pagado.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 12).toString());
            PagoParcial.setSize(448, 360);
            PagoParcial.setLocationRelativeTo(null);
            PagoParcial.setVisible(true);
            this.importe_pagado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            totalbrutoapagar.setText(totalbruto.getText());
            ingresar_cobros.setSize(650, 420);
            ingresar_cobros.setLocationRelativeTo(null);
            ingresar_cobros.setVisible(true);
            this.cobroefectivo.requestFocus();
        }

    }//GEN-LAST:event_jTable1KeyPressed

    private void MostrarCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarCuentasActionPerformed
        CargarSaldos cargarsaldo = new CargarSaldos();
        cargarsaldo.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_MostrarCuentasActionPerformed

    private void boxgastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxgastosActionPerformed
        if (this.boxgastos.isSelected()) {
            cDescuentos = this.descuentoxpagos.getText();
            cDescuentos = cDescuentos.replace(".", "").replace(",", ".");
            nDescuentos = Double.parseDouble(cDescuentos);
            this.gastosxcobrador.setText(formato.format(Config.nCobradorEnCasa));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(nBruto - nDescuentos + Config.nCobradorEnCasa));

        } else {
            cDescuentos = this.descuentoxpagos.getText();
            cDescuentos = cDescuentos.replace(".", "").replace(",", ".");
            nDescuentos = Double.parseDouble(cDescuentos);
            this.gastosxcobrador.setText(formato.format(0));
            String cBruto = this.totalbruto.getText();
            cBruto = cBruto.replace(".", "");
            cBruto = cBruto.replace(",", ".");
            double nBruto = Double.valueOf(cBruto);
            this.totalpago.setText(formato.format(nBruto - nDescuentos));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_boxgastosActionPerformed

    private void conceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conceptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoActionPerformed

    private void SalirPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPagoParcialActionPerformed
        PagoParcial.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPagoParcialActionPerformed

    private void importe_pagadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importe_pagadoFocusGained
        this.importe_a_pagar.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoFocusGained

    private void GrabarPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarPagoParcialActionPerformed
        this.jTable1.setValueAt(this.importe_pagado.getText(), nFila, 12);
        nFila = 0;
        PagoParcial.setVisible(false);
        this.sumarcobros();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarPagoParcialActionPerformed

    private void importe_pagadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importe_pagadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarPagoParcial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoKeyPressed

    private void SalirFormaCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFormaCobroActionPerformed
        this.GrabarCobro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFormaCobroActionPerformed

    private void cobroefectivoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cobroefectivoFocusGained
        this.cobroefectivo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoFocusGained

    private void AceptarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobroActionPerformed
        this.ingresar_cobros.setVisible(false);
        this.GrabarCobro.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCobroActionPerformed

    private void GrabarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCobroActionPerformed
        if (this.totalpago.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "No existen Pagos Seleccionados");
            this.totalpago.requestFocus();
            return;
        }

        Object[] opciones = {"   Grabar   ", "   Salir   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            guardacobro.start();
            this.Salir.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCobroActionPerformed

    private void gastosxcobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gastosxcobradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gastosxcobradorActionPerformed

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
//                new detalle_cobranzas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCobro;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton GrabarCobro;
    private javax.swing.JButton GrabarPagoParcial;
    private javax.swing.JTextField Modo;
    private javax.swing.JButton MostrarCuentas;
    private javax.swing.JDialog PagoParcial;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirFormaCobro;
    private javax.swing.JButton SalirPagoParcial;
    private javax.swing.JCheckBox boxcuotas;
    private javax.swing.JCheckBox boxdescuentos;
    private javax.swing.JCheckBox boxgastos;
    private javax.swing.JComboBox<String> cargobanco;
    private javax.swing.JComboBox<String> cargoemisor;
    private javax.swing.JTextField cliente;
    private javax.swing.JComboBox cobrador;
    private javax.swing.JFormattedTextField cobrocheque;
    private javax.swing.JFormattedTextField cobroefectivo;
    private javax.swing.JTextField concepto;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JFormattedTextField descuentoxpagos;
    private javax.swing.JTextField direccion;
    private com.toedter.calendar.JDateChooser emisioncheque;
    private com.toedter.calendar.JDateChooser emisiontarjeta;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JFormattedTextField gastosxcobrador;
    private javax.swing.JTextField idControl;
    private javax.swing.JFormattedTextField importe_a_pagar;
    private javax.swing.JFormattedTextField importe_pagado;
    private javax.swing.JDialog ingresar_cobros;
    private javax.swing.JFormattedTextField interesavencer;
    private javax.swing.JFormattedTextField interesordinario;
    private javax.swing.JFormattedTextField interesvencido;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox moneda;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nrocheque;
    private javax.swing.JTextField nrocuota;
    private javax.swing.JTextField nrodocumento;
    private javax.swing.JTextField nrorecibo;
    private javax.swing.JTextField nrotarjeta;
    private javax.swing.JFormattedTextField pagotarjeta;
    private javax.swing.JTextField ruc;
    private javax.swing.JComboBox sucursal;
    private javax.swing.JFormattedTextField totalamortizacion;
    private javax.swing.JFormattedTextField totalbruto;
    private javax.swing.JFormattedTextField totalbrutoapagar;
    private javax.swing.JFormattedTextField totalpago;
    // End of variables declaration//GEN-END:variables

    public class GuardarCobranza extends Thread {

        public void run() {
            UUID id = new UUID();
            String idunico = UUID.crearUUID();
            idunico = idunico.substring(1, 25);
            idControl.setText(idunico);
            String cAsiento = "0";
            double nsupago = 0.00;

            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            Object objSucursal = sucursal.getSelectedItem();
            Object objCobrador = cobrador.getSelectedItem();
            Object objMoneda = moneda.getSelectedItem();

            Sucursal = ((String[]) objSucursal)[0];
            Cobrador = ((String[]) objCobrador)[0];
            Moneda = ((String[]) objMoneda)[0];
            String cTotalPago = totalpago.getText();
            if (cTotalPago.trim().length() > 0) {
                cTotalPago = cTotalPago.replace(".", "");
                cTotalPago = cTotalPago.replace(",", ".");
            } else {
                cTotalPago = "0";
            }

            String cGastosCobrador = gastosxcobrador.getText();
            if (cGastosCobrador.trim().length() > 0) {
                cGastosCobrador = cGastosCobrador.replace(".", "");
                cGastosCobrador = cGastosCobrador.replace(",", ".");
            } else {
                cGastosCobrador = "0";
            }

            String cTotalBruto = totalbruto.getText();
            if (cTotalBruto.trim().length() > 0) {
                cTotalBruto = cTotalBruto.replace(".", "");
                cTotalBruto = cTotalBruto.replace(",", ".");
            } else {
                cTotalBruto = "0";
            }

            String cDescuentos = descuentoxpagos.getText();
            if (cDescuentos.trim().length() > 0) {
                cDescuentos = cDescuentos.replace(".", "");
                cDescuentos = cDescuentos.replace(",", ".");
            } else {
                cDescuentos = "0";
            }

            String cGastosEnvio = gastosxcobrador.getText();
            if (cGastosEnvio.trim().length() > 0) {
                cGastosEnvio = cGastosEnvio.replace(".", "");
                cGastosEnvio = cGastosEnvio.replace(",", ".");
            } else {
                cGastosEnvio = "0";
            }

            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            String cSqlcab = "INSERT INTO cobranzas  (idpagos,numero,fecha,cliente,moneda,totalpago,asiento,observacion,valores,cobrador,sucursal,cotizacionmoneda,descuentos,enviocobrador,codusuario)";
            cSqlcab += " VALUES('" + idControl.getText() + "','" + nrorecibo.getText() + "','" + FechaProceso + "','" + cliente.getText() + "','" + Moneda + "','" + cTotalPago + "','" + cAsiento + "','" + "OBSERVACION" + "','" + cTotalPago + "','" + Cobrador + "','" + Sucursal + "','" + cotizacion.getText() + "','" + cDescuentos + "','" + cGastosEnvio + "','" + Config.CodUsuario + "')";
            System.out.println(cSqlcab);
            try {
                stm.executeUpdate(cSqlcab);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            int Items = jTable1.getRowCount();
            for (int i = 0; i < Items; i++) {

                String supago = jTable1.getValueAt(i, 12).toString();
                supago = supago.replace(".", "");
                supago = supago.replace(",", ".");
                nsupago = Double.valueOf(supago);

                if (nsupago != 0) {
                    try {
                        String idFactura = (jTable1.getValueAt(i, 0).toString());
                        String cFactura = (jTable1.getValueAt(i, 1).toString());
                        Date dEmision = ODate.de_java_a_sql(formatoFecha.parse(jTable1.getValueAt(i, 2).toString()));
                        Date dVence = ODate.de_java_a_sql(formatoFecha.parse(jTable1.getValueAt(i, 3).toString()));

                        String cCuota = (jTable1.getValueAt(i, 5).toString());

                        String cAmortiza = (jTable1.getValueAt(i, 6).toString());
                        cAmortiza = cAmortiza.replace(".", "");
                        cAmortiza = cAmortiza.replace(",", ".");

                        String cInteres = (jTable1.getValueAt(i, 7).toString());
                        cInteres = cInteres.replace(".", "");
                        cInteres = cInteres.replace(",", ".");

                        String cSaldo = (jTable1.getValueAt(i, 8).toString());
                        cSaldo = cSaldo.replace(".", "");
                        cSaldo = cSaldo.replace(",", ".");

                        String cMora = (jTable1.getValueAt(i, 9).toString());
                        if (cMora.trim().length() > 0) {
                            cMora = cMora.replace(".", "");
                            cMora = cMora.replace(",", ".");
                        } else {
                            cMora = "0";
                        }

                        String cGastos = (jTable1.getValueAt(i, 10).toString());
                        if (cGastos.trim().length() > 0) {
                            cGastos = cGastos.replace(".", "");
                            cGastos = cGastos.replace(",", ".");
                        } else {
                            cGastos = "0";
                        }

                        String cPagoCuota = (jTable1.getValueAt(i, 11).toString());
                        cPagoCuota = cPagoCuota.replace(".", "");
                        cPagoCuota = cPagoCuota.replace(",", ".");

                        String cCapital = (jTable1.getValueAt(i, 13).toString());
                        cCapital = cCapital.replace(".", "");
                        cCapital = cCapital.replace(",", ".");

                        String cInversionista = (jTable1.getValueAt(i, 14).toString());
                        String cNumerocuota = (jTable1.getValueAt(i, 15).toString());
                        String cComprobante = (jTable1.getValueAt(i, 16).toString());
                        String cAtraso = (jTable1.getValueAt(i, 17).toString());

                        String cIvaInteres = (jTable1.getValueAt(i, 18).toString());
                        if (cIvaInteres.trim().length() > 0) {
                            cIvaInteres = cIvaInteres.replace(".", "");
                            cIvaInteres = cIvaInteres.replace(",", ".");
                        } else {
                            cIvaInteres = "0";
                        }

                        String cPunitorio = (jTable1.getValueAt(i, 19).toString());
                        if (cPunitorio.trim().length() > 0) {
                            cPunitorio = cPunitorio.replace(".", "");
                            cPunitorio = cPunitorio.replace(",", ".");
                        } else {
                            cPunitorio = "0";
                        }
                        //En caso que el pago sea menor al monto a pagar   
                        if (nsupago < Double.valueOf(cPagoCuota)) {
                            double nresto = nsupago;
                            if (Double.valueOf(cGastos) > 0) {
                                if (nresto <= Double.valueOf(cGastos)) {
                                    cGastos = String.valueOf(nresto);
                                    nresto = 0;
                                } else {
                                    nresto = nresto - Double.valueOf(cGastos);
                                }
                            }

                            if (Double.valueOf(cPunitorio) > 0 && nresto > 0) {
                                if (nresto <= Double.valueOf(cPunitorio)) {
                                    cPunitorio = String.valueOf(nresto);
                                    nresto = 0;
                                } else {
                                    nresto = nresto - Double.valueOf(cPunitorio);
                                }
                            } else {
                                cPunitorio = "0";
                            }

                            if (Double.valueOf(cMora) > 0 && nresto > 0) {
                                if (nresto <= Double.valueOf(cMora)) {
                                    cMora = String.valueOf(nresto);
                                    nresto = 0;
                                } else {
                                    nresto = nresto - Double.valueOf(cMora);
                                }
                            } else {
                                cMora = "0";
                            }

                            if (Double.valueOf(cInteres) > 0 && nresto > 0) {
                                if (nresto <= Double.valueOf(cInteres)) {
                                    cInteres = String.valueOf(nresto);
                                    nresto = 0;
                                } else {
                                    nresto = nresto - Double.valueOf(cInteres);
                                }
                            } else {
                                cInteres = "0";
                            }
                            cAmortiza = String.valueOf(nresto);
                        }

                        String cSqlDet = "INSERT INTO detalle_cobranzas(iddetalle,idfactura,nrofactura,emision,comprobante,pago,capital,diamora,mora,gastos_cobranzas,moneda,amortiza,minteres,vence,acreedor,cuota,numerocuota,importe_iva,punitorio)";
                        cSqlDet += " VALUES ('" + idControl.getText() + "','" + idFactura + "','" + cFactura + "','";
                        cSqlDet += dEmision + "','" + cComprobante + "','" + supago + "','" + cCapital + "','" + cAtraso + "','" + cMora + "','" + cGastos + "','" + Moneda + "','" + cAmortiza + "','" + cInteres + "','" + dVence + "','" + cInversionista + "','" + cCuota + "','" + cNumerocuota + "','" + cIvaInteres + "','" + cPunitorio + "')";
                        try {
                            stm.executeUpdate(cSqlDet);

                        } catch (SQLException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                        System.out.println(cSqlDet);
                    } catch (ParseException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

            }
            try {
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    public class NumRecibo extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT MAX(numero)+1 AS nRegistro FROM cobranzas");
                if (results.next()) {
                    nrorecibo.setText(results.getString("nRegistro"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }
        }
    }

    public class BusClientes extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("select codigo, nombre,ruc  from clientes where codigo=" + cliente.getText());
                if (results.next()) {
                    nombrecliente.setText(results.getString("nombre").trim());
                    ruc.setText(results.getString("ruc"));
                } else {
                    BuscarCliente.doClick();
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }

        }
    }

    public class CargarSaldos extends Thread {

        public void run() {
            Object objMoneda = moneda.getSelectedItem();
            String cMoneda = ((String[]) objMoneda)[0];
            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            String cCotizacionMoneda = cotizacion.getText();
            cCotizacionMoneda = cCotizacionMoneda.replace(".", "");
            cCotizacionMoneda = cCotizacionMoneda.replace(",", ".");
            double nCotizacionMoneda = Double.valueOf(cCotizacionMoneda);

            String cSql = "SELECT cuenta_clientes.*,clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,DATEDIFF('" + FechaProceso + "',cuenta_clientes.vencimiento) AS atraso,";
            cSql = cSql + "clientes.celular, clientes.direccion, clientes.mail,comprobantes.nomalias,DATEDIFF('" + FechaProceso + "',cuenta_clientes.fecha_pago) AS di,";
            cSql = cSql + "comprobantes.interespunitorio,comprobantes.diasgracia_gastos,comprobantes.diasgracia,cuenta_clientes.tasaoperativa as interesmora,comprobantes.gastoscobros,cuenta_clientes.importe_iva,cuenta_clientes.punitorio, ";
            cSql = cSql + "(SELECT SUM(mora)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) morapagada, ";
            cSql = cSql + "(SELECT SUM(punitorio)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) punitoriocobrado, ";
            cSql = cSql + "(SELECT SUM(gastos_cobranzas)";
            cSql = cSql + " FROM detalle_cobranzas ";
            cSql = cSql + " WHERE cuenta_clientes.iddocumento = detalle_cobranzas.idfactura ";
            cSql = cSql + " GROUP BY idfactura) gastoscobrado ";
            cSql = cSql + " FROM cuenta_clientes ";
            cSql = cSql + " INNER JOIN CLIENTES ";
            cSql = cSql + " ON clientes.codigo=cuenta_clientes.cliente ";
            cSql = cSql + " INNER JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=cuenta_clientes.comprobante ";
            cSql = cSql + " WHERE cuenta_clientes.saldo<>0 ";
            cSql = cSql + " and cuenta_clientes.cliente= " + cliente.getText();
            cSql = cSql + " and cuenta_clientes.moneda= " + cMoneda;
            cSql = cSql + " ORDER by cuenta_clientes.vencimiento";

            System.out.println(cSql);
            double nCapital = 0.00;
            double nMora = 0.00;
            double nInteres = 0.00;
            double nPunitorio = 0.00;
            double nTasaPunitoria = 0.00;
            int nDiaGraciaMora = 0;
            int nDiaCompletoMora = 0;
            int nComprobante = 0;
            double nImporteGastos, nDiasGracia, nDiasGraciaGastos = 0.00;
            double nMoraPagada, nPunitorioPagado, nGastosPagados = 0.00;
            double nMoraTotalPagar = 0.00;
            int natraso, ndiasmora = 0;
            String catraso = null;

            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;

            DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
            TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
            jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(13).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(16).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(17).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
            jTable1.getColumnModel().getColumn(19).setCellRenderer(TablaRenderer);

            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    // Se crea un array que será una de las filas de la tabla.
                    Object[] fila = new Object[20]; // Hay 8 columnas en la tabla
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    //Verificamos si tiene fecha de pago cargado
                    //Si tiene fecha de pago
                    //usamos como fecha de ultimo pago
                    //de lo contrario la fecha de vencimiento
                    nPunitorio = 0.00;
                    nMora = 0.00;
                    nImporteGastos = 0.00;
                    nMoraPagada = results.getDouble("morapagada");
                    nPunitorioPagado = results.getDouble("punitoriocobrado");
                    nGastosPagados = results.getDouble("gastoscobrado");
                    nCapital = results.getDouble("saldo");
                    catraso = results.getString("di");
                    nDiasGraciaGastos = results.getInt("diasgracia_gastos");
                    int ndiamora = results.getInt("atraso");
                    natraso = results.getInt("di");
                    nTasaPunitoria = results.getDouble("interespunitorio");
                    nDiaGraciaMora = results.getInt("diasgracia");
                    nComprobante = results.getInt("comprobante");
                    nInteres = results.getDouble("interesmora");
                    nDiaCompletoMora = ndiamora - natraso;

                    if (ndiamora > 0 && catraso != null) {
                        if (natraso <= ndiamora) {
                            natraso = results.getInt("di");
                        } else {
                            natraso = ndiamora;
                        }
                    } else {
                        natraso = ndiamora;
                    }

                    //Calculamos el total de mora por todo el atraso para ver si hizo algun pago
                    //anterior a cuenta de la misma
                    //Aqui calculamos la mora anterior
                    if (natraso > nDiaGraciaMora) {
                        nMora = Math.round(nCapital * ((nInteres / 100) / 360 * natraso));
                        if (Config.nIvaIncluido == 1) {
                            nMora = Math.round(nMora + (nMora * Config.porcentajeiva / 100));
                        }
                    }
                    fila[9] = formato.format(nMora);
                    fila[10] = formato.format(0);

                    if (Config.nIvaIncluido == 1) {
                        if (natraso > nDiasGraciaGastos) {
                            nImporteGastos = results.getDouble("gastoscobros");
                            nImporteGastos = nImporteGastos / nCotizacionMoneda;
                            fila[10] = formato.format(nImporteGastos);
                        }
                    } else {
                        if (natraso > nDiasGraciaGastos) {
                            if (nComprobante == 8) {
                                if (natraso <= 30) {
                                    nImporteGastos = Math.round(nCapital * 5 / 100);
                                } else if (natraso >= 31 && natraso <= 60) {
                                    nImporteGastos = Math.round(nCapital * 10 / 100);
                                } else if (natraso >= 60) {
                                    nImporteGastos = Math.round(nCapital * 20 / 100);
                                }
                            }
                            if (nComprobante == 9) {
                                if (natraso <= 30) {
                                    nImporteGastos = Math.round(nCapital * 5 / 100);
                                } else if (natraso >= 31 && natraso <= 60) {
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
                                } else if (natraso > 31 && natraso <= 60) {
                                    nImporteGastos = Math.round(nCapital * 6 / 100);
                                } else if (natraso >= 61 && natraso <= 90) {
                                    nImporteGastos = Math.round(nCapital * 10 / 100);
                                } else if (natraso >= 91) {
                                    nImporteGastos = Math.round(nCapital * 15 / 100);
                                }
                            }
                            nImporteGastos = nImporteGastos / nCotizacionMoneda;
                            fila[10] = formato.format(nImporteGastos);
                        }
                    }
                    //Se calcula el interes punitorio en caso que tenga la tasa estipulada
                    if (nTasaPunitoria > 0 && nMora > 0) {
                        nPunitorio = Math.round(nMora * nTasaPunitoria / 100);
                    }
                    fila[0] = results.getString("iddocumento");
                    fila[1] = results.getString("documento");
                    fila[2] = formatoFecha.format(results.getDate("fecha"));
                    fila[3] = formatoFecha.format(results.getDate("vencimiento"));
                    fila[4] = results.getString("nomalias");
                    fila[5] = results.getInt("cuota");
                    fila[6] = formato.format(results.getDouble("amortiza"));
                    fila[7] = formato.format(results.getDouble("minteres"));
                    fila[8] = formato.format(results.getDouble("saldo"));
                    fila[11] = formato.format(nCapital + nMora + nImporteGastos + nPunitorio);
                    fila[12] = formato.format(0);
                    fila[13] = formato.format(results.getDouble("capital"));
                    fila[14] = results.getDouble("inversionista");
                    fila[15] = results.getInt("numerocuota");
                    fila[16] = results.getInt("comprobante");
                    fila[17] = formato.format(ndiasmora);
                    fila[18] = formato.format(results.getDouble("importe_iva"));
                    fila[19] = formato.format(nPunitorio);

                    modelo.addRow(fila);
                }
                jTable1.setRowSorter(new TableRowSorter(modelo));
                jTable1.updateUI();
                stm.close();
            } catch (SQLException ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Ingresar a la Consulta de Cuotas",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex2);
            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Conectarse a la Base de Datos",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex2);
            }
        }
    }
}

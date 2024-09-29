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
import java.sql.Date;
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
import javax.swing.JFrame;
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
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class vencimiento_cuotas_clientes extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro;
    ObtenerFecha ODate = new ObtenerFecha();
    String cSql = null;
    Config configuracion = new Config();

    /**
     * Creates new form libroventaconsolidado
     */
    public vencimiento_cuotas_clientes() {
        initComponents();
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);

        this.setLocationRelativeTo(null);
        this.cargarTitulo();
        this.Inicializar();
        this.tipocomprobante.setHorizontalAlignment(JTextField.RIGHT);
        this.moneda.setHorizontalAlignment(JTextField.RIGHT);
        this.moneda.setText("1");
        this.nombremoneda.setText("GUARANIES");
        this.nombrecliente.setEnabled(false);
        this.jButton2.setEnabled(false);
        this.nombremoneda.setEnabled(false);
        this.nombreOperacion.setEnabled(false);
    }

    private void cargarTitulo() {
        modelo.addColumn("N° Cuenta");
        modelo.addColumn("Denominación Cliente");
        modelo.addColumn("N° Operación");
        modelo.addColumn("Emisión");
        modelo.addColumn("Vence");
        modelo.addColumn("Días Mora");
        modelo.addColumn("Cuota");
        modelo.addColumn("Saldo");

        int[] anchos = {150, 250, 150, 150, 150, 150, 200, 200};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.dFechaInicial.setCalendar(c2);
        this.dFechaFinal.setCalendar(c2);
    }

    private void GenerarConsulta() {

        //Dando formato a los datos tipo Fecha
        Date dFechaInicio = ODate.de_java_a_sql(this.dFechaInicial.getDate());
        Date dFechaFinal = ODate.de_java_a_sql(this.dFechaFinal.getDate());

        cSql = "SELECT cuenta_clientes.*,clientes.categoria,clientes.asesor,clientes.nombre as nombrecliente,vendedores.nombre as nombreasesor,DATEDIFF(curdate(),cuenta_clientes.vencimiento) AS atraso ";
        cSql = cSql + "FROM cuenta_clientes ";
        cSql = cSql + "INNER JOIN CLIENTES ";
        cSql = cSql + "ON clientes.codigo=cuenta_clientes.cliente ";
        cSql = cSql + "INNER JOIN VENDEDORES ";
        cSql = cSql + "ON vendedores.codigo=clientes.asesor ";
        cSql = cSql + "WHERE and cuenta_clientes.saldo>0 ";
        cSql = cSql + "and cuenta_clientes.cliente = " + this.cliente.getText();
        cSql = cSql + " and cuenta_clientes.moneda = " + this.moneda.getText();
        cSql = cSql + " and cuenta_clientes.comprobante = " + this.tipocomprobante.getText();
        cSql = cSql + " and cuenta_clientes.vencimiento BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
        cSql = cSql + " ORDER BY cuenta_clientes.vencimiento";

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

        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);

        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        try {
            results = stm.executeQuery(cSql);
            while (results.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
                DecimalFormat formatea = new DecimalFormat("###,###.##");
                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[8]; // Cantidad de columnas en la tabla
                fila[0] = results.getString("cliente");
                fila[1] = results.getString("nombrecliente");
                fila[2] = results.getString("documento");
                fila[3] = formatoFecha.format(results.getDate("fecha"));
                fila[4] = formatoFecha.format(results.getDate("vencimiento"));
                fila[5] = results.getString("atraso");
                fila[6] = formatea.format(results.getInt("importe"));
                fila[7] = formatea.format(results.getInt("saldo"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            this.jTable1.updateUI();
            int cantFilas = this.jTable1.getRowCount();
            if (cantFilas > 0) {
                this.jButton2.setEnabled(true);
            } else {
                this.jButton2.setEnabled(false);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTranslucido1 = new org.edisoncor.gui.panel.PanelTranslucido();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        panelTranslucido2 = new org.edisoncor.gui.panel.PanelTranslucido();
        dFechaInicial = new com.toedter.calendar.JDateChooser();
        dFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tipocomprobante = new javax.swing.JTextField();
        nombreOperacion = new javax.swing.JTextField();
        BuscarOperacion = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        moneda = new javax.swing.JTextField();
        BuscarMoneda = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        BuscarCliente = new javax.swing.JButton();
        nombrecliente = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.title")); // NOI18N

        panelTranslucido1.setColorPrimario(new java.awt.Color(255, 51, 51));
        panelTranslucido1.setColorSecundario(new java.awt.Color(204, 0, 0));

        jButton1.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jButton1.text")); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jButton2.text")); // NOI18N
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jButton3.text")); // NOI18N
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTranslucido1Layout = new javax.swing.GroupLayout(panelTranslucido1);
        panelTranslucido1.setLayout(panelTranslucido1Layout);
        panelTranslucido1Layout.setHorizontalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido1Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelTranslucido1Layout.setVerticalGroup(
            panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelTranslucido1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTranslucido2.setColorPrimario(new java.awt.Color(204, 204, 255));
        panelTranslucido2.setColorSecundario(new java.awt.Color(153, 153, 255));
        panelTranslucido2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                panelTranslucido2AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        dFechaInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dFechaInicialFocusGained(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jLabel2.text")); // NOI18N

        jLabel5.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jLabel5.text")); // NOI18N

        tipocomprobante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tipocomprobanteFocusGained(evt);
            }
        });
        tipocomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipocomprobanteActionPerformed(evt);
            }
        });
        tipocomprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipocomprobanteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tipocomprobanteKeyTyped(evt);
            }
        });

        nombreOperacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreOperacionActionPerformed(evt);
            }
        });

        BuscarOperacion.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.BuscarOperacion.text")); // NOI18N
        BuscarOperacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarOperacionActionPerformed(evt);
            }
        });

        jLabel6.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jLabel6.text")); // NOI18N

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                monedaKeyTyped(evt);
            }
        });

        BuscarMoneda.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.BuscarMoneda.text")); // NOI18N
        BuscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarMonedaActionPerformed(evt);
            }
        });

        nombremoneda.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel7.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.jLabel7.text")); // NOI18N

        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });
        cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                clienteKeyTyped(evt);
            }
        });

        BuscarCliente.setText(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.BuscarCliente.text")); // NOI18N
        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        nombrecliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelTranslucido2Layout = new javax.swing.GroupLayout(panelTranslucido2);
        panelTranslucido2.setLayout(panelTranslucido2Layout);
        panelTranslucido2Layout.setHorizontalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(34, 34, 34)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tipocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dFechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dFechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(318, Short.MAX_VALUE))
        );
        panelTranslucido2Layout.setVerticalGroup(
            panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(nombreOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)))
                        .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTranslucido2Layout.createSequentialGroup()
                                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel5))
                                    .addGroup(panelTranslucido2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tipocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTranslucido2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucido2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );

        jTable1.setModel(modelo);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTranslucido1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTranslucido2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTranslucido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTranslucido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(vencimiento_cuotas_clientes.class, "vencimiento_cuotas_clientes.AccessibleContext.accessibleName")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.GenerarConsulta();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Date dFechaInicio = ODate.de_java_a_sql(this.dFechaInicial.getDate());
        Date dFechaFinal = ODate.de_java_a_sql(this.dFechaFinal.getDate());
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            parameters.put("nCliente", this.cliente.getText());
            parameters.put("nombreoperacion", this.nombreOperacion.getText());
            parameters.put("nombremoneda", this.nombremoneda.getText());
            parameters.put("nMoneda", this.moneda.getText());
            parameters.put("tipooperacion", this.tipocomprobante.getText());
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa.trim());
            parameters.put("FechaInicio", dFechaInicio);
            parameters.put("FechaFinal", dFechaFinal);
            JasperReport jr = null;

            URL url = getClass().getClassLoader().getResource("Reports/vencimiento_cuotas_cliente.jasper");
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(true);
        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(this, e, "Error", 1);
            System.out.println(e);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tipocomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipocomprobanteActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre  from comprobantes where codigo=" + this.tipocomprobante.getText());
            if (results.next()) {
                this.nombreOperacion.setText(results.getString("nombre").trim());
            } else {
                this.BuscarOperacion.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tipocomprobanteActionPerformed

    private void tipocomprobanteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipocomprobanteKeyTyped
        char c = evt.getKeyChar();

        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tipocomprobanteKeyTyped

    private void monedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyTyped

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre  from monedas where codigo=" + this.moneda.getText());
            if (results.next()) {
                this.nombremoneda.setText(results.getString("nombre").trim());
            } else {
                this.BuscarMoneda.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void tipocomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipocomprobanteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moneda.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tipocomprobanteKeyPressed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.dFechaInicial.requestFocus();
        }
        // RETROCEDER AL OBJETO ANTERIOR
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipocomprobante.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void BuscarOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarOperacionActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("comprobantes");
        cu.setVisible(true);
        this.moneda.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarOperacionActionPerformed

    private void monedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_monedaFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.tipocomprobante.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombreOperacion.setText(Parametros.NOMBRE_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaFocusGained

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("monedas");
        cu.setVisible(true);
        this.dFechaInicial.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private void dFechaInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dFechaInicialFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.moneda.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombremoneda.setText(Parametros.NOMBRE_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_dFechaInicialFocusGained

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo, nombre  from clientes where codigo=" + this.cliente.getText());
            if (results.next()) {
                this.nombrecliente.setText(results.getString("nombre").trim());
            } else {
                this.BuscarCliente.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteKeyPressed

    private void clienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clienteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteKeyTyped

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("clientes");
        cu.setVisible(true);
        this.tipocomprobante.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void nombreOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreOperacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreOperacionActionPerformed

    private void panelTranslucido2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_panelTranslucido2AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_panelTranslucido2AncestorAdded

    private void tipocomprobanteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tipocomprobanteFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.cliente.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombrecliente.setText(Parametros.NOMBRE_ELEGIDO);
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tipocomprobanteFocusGained

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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vencimiento_cuotas_clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton BuscarMoneda;
    private javax.swing.JButton BuscarOperacion;
    private javax.swing.JTextField cliente;
    private com.toedter.calendar.JDateChooser dFechaFinal;
    private com.toedter.calendar.JDateChooser dFechaInicial;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField nombreOperacion;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombremoneda;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido1;
    private org.edisoncor.gui.panel.PanelTranslucido panelTranslucido2;
    private javax.swing.JTextField tipocomprobante;
    // End of variables declaration//GEN-END:variables
}

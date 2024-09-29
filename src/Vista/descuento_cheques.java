/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ControlGrabado;
import Clases.numero_a_letras;

import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.configuracionDAO;
import Modelo.Tablas;
import Modelo.configuracion;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
 */
public class descuento_cheques extends javax.swing.JFrame {

    int nFila = 0;
    String cSql, cCliente, cCodigoGastos = null;
    String cReferencia = null;
    Conexion con = null;
    Statement stm, stm2 = null;
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelo2 = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro;
    ObtenerFecha ODate = new ObtenerFecha();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");

    public descuento_cheques() {
        initComponents();
        this.jButton2.setIcon(icononuevo);
        this.jButton1.setIcon(iconoeditar);
        this.jButton3.setIcon(iconoborrar);
        this.jButton5.setIcon(iconosalir);
        this.Refrescar.setIcon(icorefresh);
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);

        this.setLocationRelativeTo(null); //Centramos el formulario
        this.jTextOpciones1.setVisible(false);
        this.cargarTitulo();
        this.CargarTitulo2();
        this.Inicializar();
        this.cargarTabla();
    }

    Control hand = new Control();

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
    }

    public void QueryPagos() {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        String query = null;
        int nFila = this.jTable1.getSelectedRow();
        String cReferenciaPago = (this.jTable1.getValueAt(nFila, 0).toString());
        query = "SELECT forma,codmoneda,confirmacion,importepago,";
        query = query + "formaspago.nombre as nombreformapago,";
        query = query + "netocobrado,nrocheque,banco,";
        query = query + "bancos.nombre AS nombrebanco ";
        query = query + "FROM detalle_forma_pago ";
        query = query + "INNER JOIN bancos ";
        query = query + "ON bancos.codigo=detalle_forma_pago.banco ";
        query = query + "INNER JOIN formaspago ";
        query = query + "ON formaspago.codigo=detalle_forma_pago.forma ";
        query = query + "WHERE detalle_forma_pago.idmovimiento ='" + cReferenciaPago + "'";

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.jTable2.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable2.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable2.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable2.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        int cantidadRegistro = modelo2.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo2.removeRow(0);
        }
        try {
            results = stm.executeQuery(query);
            while (results.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila2 = new Object[6]; // Hay 8   columnas en la tabla
                fila2[0] = results.getInt("forma");
                fila2[1] = results.getString("nombreformapago");
                fila2[2] = results.getInt("banco");
                fila2[3] = results.getString("nombrebanco");
                fila2[4] = formatoFecha.format(results.getDate("confirmacion"));
                fila2[5] = formato.format(results.getDouble("netocobrado"));
                modelo2.addRow(fila2);
            }
            this.jTable2.setRowSorter(new TableRowSorter(modelo2));
            this.jTable2.updateUI();
            int cantFilas = this.jTable2.getRowCount();
            if (cantFilas > 0) {
                this.AddPagoDD.setEnabled(true);
                this.DelPagoDD.setEnabled(true);
            } else {
                this.AddPagoDD.setEnabled(true);
                this.DelPagoDD.setEnabled(false);
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

        ordenpago = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        DelPagoDD = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        CtaCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        NombreCliente = new javax.swing.JTextField();
        AddPagoDD = new javax.swing.JButton();
        genfacturacheques = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
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
        importecapital = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        nrofactura1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacion1 = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        importegasto = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextOpciones1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        Refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        etiquetacredito = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();

        jTable2.setModel(modelo2);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
        );

        jLabel3.setText("Cuenta");

        jLabel4.setText("Denominación");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(CtaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DelPagoDD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AddPagoDD, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CtaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DelPagoDD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddPagoDD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        javax.swing.GroupLayout ordenpagoLayout = new javax.swing.GroupLayout(ordenpago.getContentPane());
        ordenpago.getContentPane().setLayout(ordenpagoLayout);
        ordenpagoLayout.setHorizontalGroup(
            ordenpagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ordenpagoLayout.setVerticalGroup(
            ordenpagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ordenpagoLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel12.setText("Nro Operación");

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

        jLabel16.setText("Interés");

        importecapital.setEditable(false);
        importecapital.setForeground(new java.awt.Color(255, 0, 0));
        importecapital.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel17.setText("Nº Factura");

        observacion1.setColumns(20);
        observacion1.setRows(5);
        jScrollPane3.setViewportView(observacion1);

        jLabel18.setText("Observaciones al píe del Comprobante");

        jLabel23.setText("Gastos Administrativos");

        importegasto.setEditable(false);
        importegasto.setForeground(new java.awt.Color(255, 0, 0));
        importegasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(138, 138, 138))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(88, 88, 88)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrecliente1)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nrofactura1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(moneda1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                    .addComponent(importecapital, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(88, 88, 88))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addGap(71, 71, 71)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fechaprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nroprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(31, 31, 31)
                                .addComponent(importegasto, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(grabarfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(salirfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(nroprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel13))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaprestamo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(nombrecliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(moneda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(importecapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(importegasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nrofactura1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salirfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grabarfactura1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout genfacturachequesLayout = new javax.swing.GroupLayout(genfacturacheques.getContentPane());
        genfacturacheques.getContentPane().setLayout(genfacturachequesLayout);
        genfacturachequesLayout.setHorizontalGroup(
            genfacturachequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, genfacturachequesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        genfacturachequesLayout.setVerticalGroup(
            genfacturachequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar entre los Días"));

        Refrescar.setText("Refrescar");
        Refrescar.setActionCommand("Filtrar");
        Refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Refrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(FechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Refrescar))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        etiquetacredito.setBackground(new java.awt.Color(255, 255, 255));
        etiquetacredito.setText(org.openide.util.NbBundle.getMessage(descuento_cheques.class, "ventas.etiquetacredito.text")); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del Cliente", "N° de Operación" }));
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
                .addGap(26, 26, 26)
                .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(286, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(etiquetacredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jMenu1.setText("Opciones  D.D.");
        jMenu1.add(jSeparator4);

        jMenuItem1.setText("Liquidación");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator2);

        jMenuItem2.setText("Contrato");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator3);

        jMenuItem3.setText("Orden de Pago");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator1);

        jMenuItem6.setText("Pagaré por Cuotas");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator6);

        jMenuItem4.setText("Emisión de Factura");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
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
            //       new detalle_descuento_cheques(cOpcion).setVisible(true);
            new detalle_descuento_cheques_siniva(cOpcion).setVisible(true);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        int nFila = this.jTable1.getSelectedRow();
        this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            ControlGrabado.REGISTRO_GRABADO = "";
            int nFila = this.jTable1.getSelectedRow();
            this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 1).toString());
            String cOpcion = this.jTextOpciones1.getText();
            try {
              //  new detalle_descuento_cheques(cOpcion).setVisible(true);
                new detalle_descuento_cheques_siniva(cOpcion).setVisible(true);
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
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
            dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
            dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
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
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            if (!this.jTextOpciones1.getText().isEmpty()) {
                Object[] opciones = {"   Eliminar   ", "   Salir   "};
                int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar esta Operación ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
                if (ret == 0) {
                    BDConexion BD = new BDConexion();
                    BD.BorrarDetalles("cabecera_descuento_documentos", "numero=" + this.jTextOpciones1.getText().trim());
                } else {
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
            return;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void RefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarActionPerformed
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        this.cargarTabla();
        // TODO add your handling code here:
    }//GEN-LAST:event_RefrescarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        LiquidaCheques LiquidarCheques = new LiquidaCheques();
        Thread HiloCheques = new Thread(LiquidarCheques);
        HiloCheques.start();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        GenerarContrato GenerarContrato = new GenerarContrato();
        Thread HiloContrato = new Thread(GenerarContrato);
        HiloContrato.start();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void salirfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfactura1ActionPerformed
        genfacturacheques.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirfactura1ActionPerformed

    private void grabarfactura1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarfactura1ActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Factura del Capital? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GrabarFacturaCheques GrabarDet = new GrabarFacturaCheques();
            Thread HiloGrabarDet = new Thread(GrabarDet);
            HiloGrabarDet.start();
            JOptionPane.showMessageDialog(null, "Factura Generada con Éxito");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_grabarfactura1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        this.genfacturacheques.setSize(671, 506);
        nFila = this.jTable1.getSelectedRow();
        cReferencia = this.jTable1.getValueAt(nFila, 0).toString();

        GenFacturaCheques GenFacCapital = new GenFacturaCheques();
        Thread HiloCapital = new Thread(GenFacCapital);
        HiloCapital.start();

        this.nroprestamo1.setText(this.jTable1.getValueAt(nFila, 1).toString());
        cCliente = this.jTable1.getValueAt(nFila, 3).toString();

        try {
            this.fechaprestamo1.setDate(formatoFecha.parse(this.jTable1.getValueAt(nFila, 2).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.nombrecliente1.setText(this.jTable1.getValueAt(nFila, 4).toString().trim());
        this.moneda1.setText(this.jTable1.getValueAt(nFila, 5).toString().trim());
        this.importecapital.setText(this.jTable1.getValueAt(nFila, 9).toString());
        this.importegasto.setText(this.jTable1.getValueAt(nFila, 8).toString());
        genfacturacheques.setTitle("Generar Factura");
        genfacturacheques.setLocationRelativeTo(null);
        genfacturacheques.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        PagareCuota2 PagareCuotas = new PagareCuota2();
        Thread HiloPagare = new Thread(PagareCuotas);
        HiloPagare.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        OrdenPagoCheques OpCheques = new OrdenPagoCheques();
        Thread HiloOpCheques = new Thread(OpCheques);
        HiloOpCheques.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Operacion");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Denominación Cliente");
        modelo.addColumn("Moneda");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Importe");
        modelo.addColumn("Gastos");
        modelo.addColumn("Descuentos");
        modelo.addColumn("Desembolso");

        int[] anchos = {3, 90, 90, 90, 200, 100, 100, 100, 100, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);

    }

    public void CargarTitulo2() {
        modelo2.addColumn("Forma");
        modelo2.addColumn("Descripción");
        modelo2.addColumn("Cód. Cta.");
        modelo2.addColumn("Banco");
        modelo2.addColumn("Fecha");
        modelo2.addColumn("Importe");
        int[] anchos = {110, 100, 150, 150, 150, 150};
        for (int i = 0; i < modelo2.getColumnCount(); i++) {
            this.jTable2.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public void cargarTabla() {
        //Uso la Clase SimpleDateFormat para darle formato al campo fecha

        cSql = "SELECT cabecera_descuento_documentos.idprestamos,cabecera_descuento_documentos.numero,cabecera_descuento_documentos.fecha,";
        cSql = cSql + "clientes.nombre AS nombrecliente,";
        cSql = cSql + "cabecera_descuento_documentos.comision_deudor,cabecera_descuento_documentos.socio, ";
        cSql = cSql + "sucursales.nombre AS nombresucursal,monedas.nombre AS nombremoneda,";
        cSql = cSql + "cabecera_descuento_documentos.importe,cabecera_descuento_documentos.totaldescuento,";
        cSql = cSql + "cabecera_descuento_documentos.totalactual ";
        cSql = cSql + "FROM cabecera_descuento_documentos ";
        cSql = cSql + "INNER JOIN clientes ON clientes.codigo=cabecera_descuento_documentos.socio ";
        cSql = cSql + "INNER JOIN sucursales ON sucursales.codigo=cabecera_descuento_documentos.sucursal ";
        cSql = cSql + "INNER JOIN monedas ON monedas.codigo=cabecera_descuento_documentos.moneda ";
        cSql = cSql + "WHERE cabecera_descuento_documentos.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
        cSql = cSql + " ORDER BY cabecera_descuento_documentos.numero";

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        //Instanciamos esta clase para alinear las celdas numericas a la derecha
        //Llamo a la clase conexion para conectarme a la base de datos
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje

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
                Object[] fila = new Object[11]; // Hay 9 columnas en la tabla
                fila[0] = results.getString("idprestamos");
                fila[1] = results.getString("numero");
                fila[2] = formatoFecha.format(results.getDate("fecha"));
                fila[3] = results.getString("socio");
                fila[4] = results.getString("nombrecliente");
                fila[5] = results.getString("nombremoneda");
                fila[6] = results.getString("nombresucursal");
                fila[7] = formatea.format(results.getDouble("importe"));
                fila[8] = formatea.format(results.getDouble("comision_deudor"));
                fila[9] = formatea.format(results.getDouble("totaldescuento"));
                fila[10] = formatea.format(results.getDouble("totalactual"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            this.jTable1.updateUI();
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
                new descuento_cheques().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPagoDD;
    private javax.swing.JTextField CtaCliente;
    private javax.swing.JButton DelPagoDD;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JTextField NombreCliente;
    private javax.swing.JButton Refrescar;
    private org.edisoncor.gui.label.LabelMetric etiquetacredito;
    private com.toedter.calendar.JDateChooser fechaprestamo1;
    private javax.swing.JDialog genfacturacheques;
    private javax.swing.JButton grabarfactura1;
    private javax.swing.JFormattedTextField importecapital;
    private javax.swing.JFormattedTextField importegasto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextOpciones1;
    private javax.swing.JTextField moneda1;
    private javax.swing.JTextField nombrecliente1;
    private javax.swing.JTextField nrofactura1;
    private javax.swing.JTextField nroprestamo1;
    private javax.swing.JTextArea observacion1;
    private javax.swing.JDialog ordenpago;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JButton salirfactura1;
    // End of variables declaration//GEN-END:variables

    private class GenerarContrato extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cContrato1 = config.getNombrecontrato1();
            String cContrato2 = config.getNombrecontrato2();
            String cContrato3 = config.getNombrecontrato3();
            String cContrato4 = config.getNombrecontrato4();

            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                String cMonto1 = jTable1.getValueAt(nFila, 7).toString();
                cMonto1 = cMonto1.replace(".", "").replace(",", ".");
                numero_a_letras numero = new numero_a_letras();
                String cFecha = jTable1.getValueAt(nFila, 2).toString().trim();
                cFecha=cFecha.substring(6, 10);
                System.out.println("Anual "+cFecha);
                
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte

                parameters.put("Letra", numero.Convertir(cMonto1, true, 1));
                parameters.put("cLetraAnual", numero.Convertir(cFecha, true, 1));
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cRucEmpresa", Config.cRucEmpresa);
                parameters.put("cNumero", jTable1.getValueAt(nFila, 1).toString());
                parameters.put("cMonto_Entregar", jTable1.getValueAt(nFila, 10).toString());
                JasperReport jr = null;
                URL url = null;
                if (!cContrato1.isEmpty()) {
                    url = getClass().getClassLoader().getResource("Reports/" + cContrato1.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                    JasperPrint masterPrint = null;
                    //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                    masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                    JasperViewer ventana = new JasperViewer(masterPrint, false);
                    ventana.setTitle("Vista Previa");
                    ventana.setVisible(true);
                }
                if (!cContrato2.isEmpty()) {
                    url = getClass().getClassLoader().getResource("Reports/" + cContrato2.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                    JasperPrint masterPrint = null;
                    //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                    masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                    JasperViewer ventana = new JasperViewer(masterPrint, false);
                    ventana.setTitle("Vista Previa");
                    ventana.setVisible(true);
                }
                if (!cContrato3.isEmpty()) {
                    url = getClass().getClassLoader().getResource("Reports/" + cContrato3.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                    JasperPrint masterPrint = null;
                    //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                    masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                    JasperViewer ventana = new JasperViewer(masterPrint, false);
                    ventana.setTitle("Vista Previa");
                    ventana.setVisible(true);
                }
                if (!cContrato4.isEmpty()) {
                    url = getClass().getClassLoader().getResource("Reports/" + cContrato4.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                    JasperPrint masterPrint = null;
                    //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                    masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                    JasperViewer ventana = new JasperViewer(masterPrint, false);
                    ventana.setTitle("Vista Previa");
                    ventana.setVisible(true);
                }
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }
        }
    }

    private class LiquidaCheques extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreLiquidacionCheques = config.getNombreliquidacioncheques();

            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("dNumero", jTable1.getValueAt(nFila, 1).toString());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacionCheques.toString());
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

    private class OrdenPagoCheques extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                int nFila = jTable1.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = jTable1.getValueAt(nFila, 10).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("dNumero", jTable1.getValueAt(nFila, 0).toString());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/ordenpago_descuento_cheques.jasper");
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

    private class GrabarFacturaCheques extends Thread {

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
            String cExentas = importecapital.getText();
            cExentas = cExentas.replace(".", "");
            cExentas = cExentas.replace(",", ".");
            String cGastos = importegasto.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");

            double TotalNeto = Double.valueOf(cGastos) + Double.valueOf(cExentas);
            String cTotalNeto = String.valueOf(TotalNeto);
            double montoiva10 = Math.round(Double.valueOf(cTotalNeto) / 11);
            String cImpiva = String.valueOf(montoiva10);
            String cIva = "10";
            String cCotizacion = "1";
            String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,";
            cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idusuario)";
            cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cFactura + "','" + PrimerVence + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
            cSqlCab += "1" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + "0" + "','" + cTotalNeto + "','" + "0" + "','" + cTotalNeto + "','" + observacion1.getText().toString() + "','" + Config.CodUsuario + "')";
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
                    cImpiva = String.valueOf(Math.round(Double.valueOf(cExentas) / 11));

                    String cSqlDetalle1 = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle1 += "VALUES ('" + cReferencia + "','" + Config.cInteresPrestamo + "','" + cCantidad + "','" + '0' + "','" + cExentas + "','" + cExentas + "','" + cImpiva + "','" + cIva + "')";

                    try {
                        stm.executeUpdate(cSqlDetalle1);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                if (Integer.valueOf(Config.cCodGastos) > 0) {
                    String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                    cSqlDetalle += "VALUES ('" + cReferencia + "','" + Config.cCodGastos + "','" + cCantidad + "','" + '0' + "','" + cGastos + "','" + cGastos + "','" + cImpiva + "','" + cIva + "')";
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

                String cSqlControlFactura = "INSERT INTO facturacheques (idcheques,idfactura,opcion) VALUES('" + cReferencia + "','" + cReferencia + "','" + '1' + "')";
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

    private class ImprimirFacturaCheques extends Thread {

        public void run() {
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = jTable1.getSelectedRow();
                String cMonto1 = jTable1.getValueAt(nFila, 8).toString();
                String cMonto2 = jTable1.getValueAt(nFila, 10).toString();
                cMonto1 = cMonto1.replace(".", "");
                cMonto1 = cMonto1.replace(",", ".");

                cMonto2 = cMonto2.replace(".", "");
                cMonto2 = cMonto2.replace(",", ".");

                double nTotalFactura = Double.valueOf(cMonto1) + Double.valueOf(cMonto2);

                String num = String.valueOf(nTotalFactura);
                System.out.println(num);
//                num = num.replace(".", "");
//                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + Config.cNombreFactura.toString());
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

    private class GenFacturaCheques extends Thread {

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

    private class PagareCuota2 extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            stm2 = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombrePagare = config.getNombrepagarecuota();
            int nFila = jTable1.getSelectedRow();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nOperacion = Integer.valueOf(jTable1.getValueAt(nFila, 1).toString());

            String cSql = "SELECT emision,nrodocumento,monto_documento FROM detalle_descuento_documentos WHERE ndescuento=" + nOperacion + " ORDER BY emision ";
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    //double cMonto_total = results.getDouble("monto_documento");
                    String cMonto = formato.format(results.getDouble("monto_documento"));
                    String cNroDocumento = results.getString("nrodocumento");
                    System.out.println(cMonto);
                    System.out.println(Config.cNombreEmpresa.toString());
                    System.out.println(nOperacion);
                    System.out.println(cNroDocumento);
                    
                    try {
                        Map parameters = new HashMap();
                        //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                        //en el reporte
                        // double n =
                        String num = cMonto;
                        num = num.replace(".", "").replace(",", ".");
                        numero_a_letras numero = new numero_a_letras();
                        parameters.put("Letra", numero.Convertir(num, true, 1));
                        parameters.put("cNombreEmpresa", Config.cNombreEmpresa.toString());
                        parameters.put("cNroDescuento", nOperacion);
                        parameters.put("cNroDocumento", cNroDocumento);
                        parameters.put("cMonto", cMonto);
                        JasperReport jr = null;
                        URL url = getClass().getClassLoader().getResource("Reports/" + cNombrePagare.toString());
                        jr = (JasperReport) JRLoader.loadObject(url);
                        JasperPrint masterPrint = null;
                        //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                        masterPrint = JasperFillManager.fillReport(jr, parameters, stm2.getConnection());
                        JasperViewer ventana = new JasperViewer(masterPrint, false);
                        ventana.setTitle("Vista Previa");
                        ventana.setVisible(true);
                        //Enviar Directo a Impresora
                        // JasperPrintManager.printReport(masterPrint, false);
                    } catch (Exception e) {
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
                    }
                }
                results.close();
                stm.close();
                stm2.close();
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ControlGrabado;
import Clases.Parametros;
import Conexion.BDConexion;
import Clases.Detalle_cheques;
import Clases.UUID;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import Modelo.Tablas;
import Clases.ConvertirMayusculas;
import java.awt.Point;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class detalle_facturas extends javax.swing.JFrame {

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro;
    Tablas modelo = new Tablas();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    Conexion con = null;
    Statement stm = null;
    ResultSet results = null;
    BDConexion BD = new BDConexion();
    String Operacion = null;
    ObtenerFecha ODate = new ObtenerFecha();
    String Sucursal = null;
    String Asesor = null;
    String Moneda = null;
    String Comprobante = null;
    String cSql = null;
    double ntotal,nIva= 0;
    
    int cCuota = 1;
    int nFila = 0;

    public detalle_facturas(String Opcion) throws ParseException {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.Modo.setVisible(false);
        this.cModo.setVisible(false);
        this.item_iva.setVisible(false);
        this.CargarTitulo();
        this.limpiarCombos();
        this.idfila.setVisible(false);
        this.creferencia.setVisible(false);
        this.Modo.setVisible(false);
        this.iva10.setVisible(false);
        this.iva5.setVisible(false);
        this.Modo.setVisible(false);
        this.UpdateItem.setVisible(false);
        this.Modo.setText(Opcion);
        this.nombreproducto.setEnabled(false);
        this.totalitem.setEnabled(false);
        this.codprod.setHorizontalAlignment(JTextField.RIGHT);
        this.nrofactura.setHorizontalAlignment(JTextField.RIGHT);
        this.cotizacion.setHorizontalAlignment(JTextField.RIGHT);
        this.cuotas.setHorizontalAlignment(JTextField.RIGHT);
        this.cantidad.setHorizontalAlignment(JTextField.RIGHT);
        this.preciounitario.setHorizontalAlignment(JTextField.RIGHT);
        this.totalitem.setHorizontalAlignment(JTextField.RIGHT);
        this.exentas.setHorizontalAlignment(JTextField.RIGHT);
        this.gravadas10.setHorizontalAlignment(JTextField.RIGHT);
        this.iva10.setHorizontalAlignment(JTextField.RIGHT);
        this.totalneto.setHorizontalAlignment(JTextField.RIGHT);
        this.gravadas5.setHorizontalAlignment(JTextField.RIGHT);
        this.iva5.setHorizontalAlignment(JTextField.RIGHT);
        this.nrofactura.setVisible(true);
        this.sucursal.requestFocus();
        Calendar c2 = new GregorianCalendar();
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);

        if (Opcion == "new") {
            this.nrofactura.setText("");
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            this.fecha.setCalendar(c2);
            this.vencimiento.setCalendar(c2);
        } else {
            this.creferencia.setText(Opcion);
            this.consultarTabla();
            // this.sumatoria();
        }
    }

    public void sumatoria() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA
        double nPorcentajeIva = 0.00;
        double sumexentas = 0.00;
        double sumgravadas10 = 0.00;
        double sumgravadas5 = 0.00;
        double sumtotal = 0.00;
        double nmontoiva10 = 0.00;
        double nmontoiva5 = 0.00;
        double sumatoria = 0.00;
        String cValorImporte = "";
        int totalRow = this.jTable1.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //Primero capturamos el porcentaje del IVA
            nPorcentajeIva = Double.parseDouble(String.valueOf(this.jTable1.getValueAt(i, 4)));
            //Luego capturamos el importe de la celda total del item
            cValorImporte = String.valueOf(this.jTable1.getValueAt(i, 6));
            cValorImporte = cValorImporte.replace(".", "");
            cValorImporte = cValorImporte.replace(",", ".");
            //Calculamos el total
            sumatoria = Double.parseDouble(String.valueOf(cValorImporte));
            sumtotal += sumatoria;
            //Calculamos el total de exentos
            if (nPorcentajeIva == 0) {
                sumatoria = Double.parseDouble(String.valueOf(cValorImporte));
                sumexentas += sumatoria;
            }
            //Calculamos el total del 5%
            if (nPorcentajeIva == 5) {
                sumatoria = Double.parseDouble(String.valueOf(cValorImporte));
                sumgravadas5 += sumatoria;
            }

            if (nPorcentajeIva == 10) {
                sumatoria = Double.parseDouble(String.valueOf(cValorImporte));
                sumgravadas10 += sumatoria;
            }
        }
        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.gravadas5.setText(formato.format(sumgravadas5));
        this.iva10.setText(formato.format(nmontoiva10));
        this.exentas.setText(formato.format(sumexentas));
        this.gravadas10.setText(formato.format(sumgravadas10));
        this.totalneto.setText(formato.format(sumtotal));
        //formato.format(sumatoria1);
    }

    public void CargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("IVA %");
        modelo.addColumn("Importe IVA");
        modelo.addColumn("Total");
        int[] anchos = {90, 250, 100, 150, 50, 150, 150};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public void consultarTabla() throws ParseException {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        int nComprobante = 0;
        int nSucursal = 0;
        int nAsesor = 0;
        int nMoneda = 0;
        String query = null;
        query = "SELECT creferencia,factura,fecha,moneda,sucursal,vencimiento,cotizacion,";
        query = query + "cliente,comprobante,nombrecomprobante,nombrecliente,clientes.direccion,";
        query = query + "exentas,gravadas10,gravadas5,totalneto,registro,vista_ventas.vendedor,";
        query = query + "observacion,cuotas";
        query = query + " FROM vista_ventas";
        query = query + " INNER JOIN clientes";
        query = query + " ON clientes.codigo=vista_ventas.cliente";
        query = query + " WHERE vista_ventas.creferencia ='" + this.creferencia.getText() + "'";

        try {
            results = stm.executeQuery(query);
            if (results.next()) {
                nSucursal = results.getInt("sucursal");
                nAsesor = results.getInt("vendedor");
                nMoneda = results.getInt("moneda");
                nComprobante=results.getInt("comprobante");
                this.creferencia.setText(results.getString("creferencia"));
                this.nrofactura.setText(results.getString("factura"));
                this.observacion.setText(results.getString("observacion").trim());
                this.fecha.setDate(results.getDate("fecha"));
                this.vencimiento.setDate(results.getDate("vencimiento"));
                this.cotizacion.setText(formato.format(results.getDouble("cotizacion")));
                this.cuotas.setText(formato.format(results.getDouble("cuotas")));
                this.Cliente.setText(results.getString("cliente"));
                this.NombreCliente.setText(results.getString("nombrecliente").trim());
                this.Direccion.setText(results.getString("direccion").trim());
                this.exentas.setText(formato.format(results.getDouble("exentas")));
                this.gravadas10.setText(formato.format(results.getDouble("gravadas10")));
                //this.iva10.setText(formato.format(results.getInt("iva10")));
                this.gravadas5.setText(formato.format(results.getDouble("gravadas5")));
                // this.iva5.setText(formato.format(results.getInt("iva5")));
                this.totalneto.setText(formato.format(results.getDouble("totalneto")));
            }
            String cSucursal = selectCombo(sucursal, "select codigo,nombre from sucursales where codigo='" + nSucursal + "'");
            String cAsesor = selectCombo(vendedor, "select codigo,nombre from vendedores where estado=1 and codigo='" + nAsesor + "'");
            String cMoneda = selectCombo(codmoneda, "select * from monedas where codigo='" + nMoneda + "'");
            String cComprobante = selectCombo(comprobante, "select codigo,nombre from comprobantes where tipo=2 and libros=1 and codigo='"+nComprobante+"'");

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        // Se construye el JFormattedTextField pasándole la máscara
        // Se da un valor inicial válido para evitar problemas
        //Inicializo Variables
        //Consultamos el Detalle  de la Operacion y Mostramos en el JTable
        query = "";
        query = "SELECT codprod,cantidad,precio,monto,impiva,porcentaje,";
        query = query + "productos.nombre";
        query = query + " FROM detalle_ventas";
        query = query + " INNER JOIN productos";
        query = query + " ON productos.codigo=detalle_ventas.codprod";
        query = query + " WHERE detalle_ventas.dreferencia ='" + this.creferencia.getText() + "'";

        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        try {
            results = stm.executeQuery(query);
            while (results.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[7]; // Hay 7   columnas en la tabla
                fila[0] = results.getString("codprod");
                fila[1] = results.getString("nombre");
                fila[2] = formato.format(results.getDouble("cantidad"));
                fila[3] = formato.format(results.getDouble("precio"));
                fila[4] = results.getDouble("porcentaje");
                fila[5] = formato.format(results.getDouble("impiva"));
                fila[6] = formato.format(results.getDouble("monto"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            this.jTable1.updateUI();
            int cantFilas = this.jTable1.getRowCount();
            if (cantFilas > 0) {
                this.NuevoItem.setEnabled(true);
                this.SalirItem.setEnabled(true);
            } else {
                this.NuevoItem.setEnabled(true);
                this.SalirItem.setEnabled(false);
            }
            stm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
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

        this.codmoneda.removeAllItems();;
        BD.cargarCombo("select codigo,nombre from monedas order by codigo", this.codmoneda);
        this.codmoneda.setSelectedIndex(0);

        this.vendedor.removeAllItems();
        BD.cargarCombo("select codigo,nombre from vendedores where estado=1 order by codigo", this.vendedor);
        this.vendedor.setSelectedIndex(0);

        this.comprobante.removeAllItems();
        BD.cargarCombo("select codigo,nombre from comprobantes where tipo=2 and libros=1", this.comprobante);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        itemfacturas = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        codprod = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        BuscarProducto = new javax.swing.JButton();
        nombreproducto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cantidad = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        preciounitario = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        totalitem = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        cModo = new javax.swing.JTextField();
        item_iva = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        NuevoItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sucursal = new javax.swing.JComboBox();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        codmoneda = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        Cliente = new javax.swing.JTextField();
        NombreCliente = new javax.swing.JTextField();
        Direccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        vendedor = new javax.swing.JComboBox();
        BuscarCliente = new javax.swing.JButton();
        nrofactura = new javax.swing.JTextField();
        Modo = new javax.swing.JTextField();
        creferencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comprobante = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        cuotas = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observacion = new javax.swing.JTextArea();
        AgregarItem = new javax.swing.JButton();
        UpdateItem = new javax.swing.JButton();
        DeleteItem = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        Exentas = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        exentas = new javax.swing.JFormattedTextField();
        gravadas10 = new javax.swing.JFormattedTextField();
        iva10 = new javax.swing.JFormattedTextField();
        gravadas5 = new javax.swing.JFormattedTextField();
        iva5 = new javax.swing.JFormattedTextField();
        totalneto = new javax.swing.JFormattedTextField();
        idfila = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        codprod.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.codprod.text")); // NOI18N
        codprod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codprodFocusGained(evt);
            }
        });
        codprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codprodActionPerformed(evt);
            }
        });
        codprod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codprodKeyPressed(evt);
            }
        });

        jLabel14.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel14.text")); // NOI18N

        BuscarProducto.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.BuscarProducto.text")); // NOI18N
        BuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarProductoActionPerformed(evt);
            }
        });

        nombreproducto.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.nombreproducto.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel15.text")); // NOI18N

        cantidad.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.cantidad.text")); // NOI18N
        cantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cantidadFocusGained(evt);
            }
        });
        cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadKeyPressed(evt);
            }
        });

        jLabel16.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel16.text")); // NOI18N

        preciounitario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        preciounitario.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.preciounitario.text")); // NOI18N
        preciounitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preciounitarioActionPerformed(evt);
            }
        });
        preciounitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                preciounitarioKeyPressed(evt);
            }
        });

        jLabel17.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel17.text")); // NOI18N

        totalitem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalitem.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.totalitem.text")); // NOI18N

        jLabel19.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel19.text")); // NOI18N

        cModo.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.cModo.text")); // NOI18N

        item_iva.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.item_iva.text")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel16)
                                .addComponent(jLabel15)
                                .addComponent(jLabel14))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel17))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel19)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(item_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(totalitem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addComponent(preciounitario, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(codprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(item_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(preciounitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        NuevoItem.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.NuevoItem.text")); // NOI18N
        NuevoItem.setBorder(null);
        NuevoItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NuevoItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NuevoItemFocusGained(evt);
            }
        });
        NuevoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoItemActionPerformed(evt);
            }
        });

        SalirItem.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.SalirItem.text")); // NOI18N
        SalirItem.setBorder(null);
        SalirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(NuevoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(SalirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NuevoItem, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                    .addComponent(SalirItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout itemfacturasLayout = new javax.swing.GroupLayout(itemfacturas.getContentPane());
        itemfacturas.getContentPane().setLayout(itemfacturasLayout);
        itemfacturasLayout.setHorizontalGroup(
            itemfacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemfacturasLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemfacturasLayout.setVerticalGroup(
            itemfacturasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemfacturasLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.title")); // NOI18N
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(204, 204, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel1.text")); // NOI18N

        sucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        sucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucursalActionPerformed(evt);
            }
        });

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

        jLabel2.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel3.text")); // NOI18N

        codmoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        codmoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codmonedaActionPerformed(evt);
            }
        });
        codmoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codmonedaKeyPressed(evt);
            }
        });

        jLabel4.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel4.text")); // NOI18N

        Cliente.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.Cliente.text")); // NOI18N
        Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClienteActionPerformed(evt);
            }
        });
        Cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ClienteKeyPressed(evt);
            }
        });

        NombreCliente.setBackground(new java.awt.Color(204, 204, 255));
        NombreCliente.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.NombreCliente.text")); // NOI18N
        NombreCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NombreCliente.setEnabled(false);

        Direccion.setBackground(new java.awt.Color(204, 204, 255));
        Direccion.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.Direccion.text")); // NOI18N
        Direccion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Direccion.setEnabled(false);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel5.text")); // NOI18N

        vendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        vendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendedorActionPerformed(evt);
            }
        });
        vendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vendedorKeyPressed(evt);
            }
        });

        BuscarCliente.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.BuscarCliente.text")); // NOI18N
        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        nrofactura.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.nrofactura.text")); // NOI18N
        nrofactura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nrofacturaFocusGained(evt);
            }
        });
        nrofactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nrofacturaMouseClicked(evt);
            }
        });
        nrofactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nrofacturaActionPerformed(evt);
            }
        });
        nrofactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrofacturaKeyPressed(evt);
            }
        });

        Modo.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.Modo.text")); // NOI18N
        Modo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModoActionPerformed(evt);
            }
        });

        creferencia.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.creferencia.text")); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel6.text")); // NOI18N

        comprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VENTA CONTADO", "VENTA CREDITO" }));
        comprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comprobanteKeyPressed(evt);
            }
        });

        jLabel8.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel8.text")); // NOI18N

        cotizacion.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.cotizacion.text")); // NOI18N
        cotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });

        jLabel10.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel10.text")); // NOI18N

        vencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vencimientoKeyPressed(evt);
            }
        });

        jLabel12.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel12.text")); // NOI18N

        cuotas.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.cuotas.text")); // NOI18N
        cuotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cuotasKeyPressed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel13.text")); // NOI18N

        observacion.setColumns(20);
        observacion.setRows(5);
        jScrollPane1.setViewportView(observacion);

        AgregarItem.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.AgregarItem.text")); // NOI18N
        AgregarItem.setToolTipText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.AgregarItem.toolTipText")); // NOI18N
        AgregarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItemActionPerformed(evt);
            }
        });

        UpdateItem.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.UpdateItem.text")); // NOI18N
        UpdateItem.setToolTipText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.UpdateItem.toolTipText")); // NOI18N
        UpdateItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateItemActionPerformed(evt);
            }
        });

        DeleteItem.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.DeleteItem.text")); // NOI18N
        DeleteItem.setToolTipText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.DeleteItem.toolTipText")); // NOI18N
        DeleteItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteItemActionPerformed(evt);
            }
        });

        jLabel18.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel18.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(155, 155, 155))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(jLabel5)
                            .addGap(12, 12, 12)
                            .addComponent(vencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(69, 69, 69)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Modo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel13)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cuotas, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(151, 151, 151)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(codmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(110, 110, 110)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(2, 2, 2)
                                                .addComponent(Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(22, 22, 22)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(UpdateItem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(codmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel13))
                    .addComponent(cuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        jPanel4.setBackground(new java.awt.Color(51, 204, 255));
        jPanel4.setForeground(new java.awt.Color(51, 204, 255));
        jPanel4.setOpaque(false);

        Exentas.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.Exentas.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel7.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel9.text")); // NOI18N

        jLabel11.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jLabel11.text")); // NOI18N

        exentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        exentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        exentas.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.exentas.text")); // NOI18N
        exentas.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        exentas.setEnabled(false);
        exentas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        gravadas10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gravadas10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gravadas10.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.gravadas10.text")); // NOI18N
        gravadas10.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        gravadas10.setEnabled(false);
        gravadas10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        iva10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        iva10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        iva10.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.iva10.text")); // NOI18N
        iva10.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        iva10.setEnabled(false);
        iva10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        gravadas5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gravadas5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gravadas5.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.gravadas5.text")); // NOI18N
        gravadas5.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        gravadas5.setEnabled(false);
        gravadas5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        iva5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        iva5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        iva5.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        iva5.setEnabled(false);
        iva5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        totalneto.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalneto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalneto.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        totalneto.setEnabled(false);
        totalneto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        idfila.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.idfila.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iva10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iva5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(idfila, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Exentas, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(exentas, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gravadas5, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                            .addComponent(gravadas10)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {exentas, gravadas10, gravadas5, totalneto});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Exentas)
                            .addComponent(iva10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(gravadas10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addComponent(iva5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(idfila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(8, 8, 8))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setForeground(new java.awt.Color(204, 204, 255));

        jButton1.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jButton1.text")); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jButton2.text")); // NOI18N
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_facturas.class, "detalle_facturas.jPanel5.border.title"))); // NOI18N

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jTable1.setModel(modelo);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "NO";
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaClientes2 cu = new Vista.ConsultaClientes2();
        cu.setVisible(true);
        this.fecha.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void NuevoItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NuevoItemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoItemFocusGained

    private void ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClienteActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre,telefono,direccion  from clientes where codigo=" + this.Cliente.getText());
            if (results.next()) {
                this.NombreCliente.setText(results.getString("nombre"));
                this.Direccion.setText(results.getString("direccion"));
//                this.autorizado.requestFocus();
            } else {
                this.BuscarCliente.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ClienteActionPerformed

    private void NuevoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoItemActionPerformed
        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[7];
            fila[0] = this.codprod.getText().toString();
            fila[1] = this.nombreproducto.getText().toString();
            fila[2] = this.cantidad.getText();
            fila[3] = this.preciounitario.getText();
            fila[4] = Config.porcentajeiva;
            fila[5] = this.item_iva.getText();
            fila[6] = this.totalitem.getText();
            modelo.addRow(fila);
            this.codprod.requestFocus();
        } else {
            this.jTable1.setValueAt(this.codprod.getText(), nFila, 0);
            this.jTable1.setValueAt(this.nombreproducto.getText(), nFila, 1);
            this.jTable1.setValueAt(this.cantidad.getText(), nFila, 2);
            this.jTable1.setValueAt(this.preciounitario.getText(), nFila, 3);
            this.jTable1.setValueAt(Config.porcentajeiva, nFila, 4);
            this.jTable1.setValueAt(this.item_iva.getText(), nFila, 5);
            this.jTable1.setValueAt(this.totalitem.getText(), nFila, 6);
            nFila = 0;
            this.SalirItem.doClick();
        }
        this.LimpiarItem();
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoItemActionPerformed

    private void LimpiarItem() {
        this.codprod.setText("");
        this.nombreproducto.setText("");
        this.cantidad.setText("");
        this.preciounitario.setText("");
        this.totalitem.setText("");
        this.item_iva.setText("");
        this.sumatoria();
    }

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // AGREGAMOS O MODIFICAMOS AL JTABLE EL REGISTRO CORRESPONDIENTE QUE NOS DEVUELVE
        // EL FORM ITEM_DESCUENTO_CHEQUES
        if (ControlGrabado.REGISTRO_GRABADO == "SI") {
            String cadenacontrol = this.idfila.getText();
            if (cadenacontrol.isEmpty()) {
                Object[] fila = new Object[18];
                // Hay 18   columnas en la tabla
                // fila[0] = cheques.demision;
                //   modelo.addRow(fila);
                // Se añade al modelo la fila completa.
            } else {
                //cheques.nFilaCheque = Integer.parseInt(this.idfila.getText());
                //this.jTable1.setValueAt(cheques.demision, cheques.nFilaCheque, 0);
            }
            this.idfila.setText("");
            ControlGrabado.REGISTRO_GRABADO = "NO";
            this.sumatoria();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void SalirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        itemfacturas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            if (this.nrofactura.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debe Ingresar el Nro. de Factura");
                this.nrofactura.requestFocus();
                return;
            }

            if (this.Cliente.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese el Código del Cliente");
                this.Cliente.requestFocus();
                return;
            }

            if (this.cotizacion.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese la Cotización de Moneda");
                this.cotizacion.requestFocus();
                return;
            }

            ControlGrabado.REGISTRO_GRABADO = "SI";
            con = new Conexion();
            stm = con.conectar();
            String Operacion = this.Modo.getText();

            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha
            Date dProceso = ODate.de_java_a_sql(this.fecha.getDate());
            Date dVencimiento = ODate.de_java_a_sql(this.vencimiento.getDate());
            //Obteniendo Datos de los Combodatos
            Object objSucursal = this.sucursal.getSelectedItem();
            Object objAsesor = this.vendedor.getSelectedItem();
            Object objMoneda = this.codmoneda.getSelectedItem();
            Object objComprobante = this.comprobante.getSelectedItem();

            Sucursal = ((String[]) objSucursal)[0];
            Asesor = ((String[]) objAsesor)[0];
            Moneda = ((String[]) objMoneda)[0];
            Comprobante = ((String[]) objComprobante)[0];

            //SE CAPTURAN LOS DATOS NUMERICOS
            String cExentas = this.exentas.getText();
            if (cExentas.trim().length() > 0) {
                cExentas = cExentas.replace(".", "");
                cExentas = cExentas.replace(",", ".");
            } else {
                cExentas = "0";
            }

            String cGravadas10 = this.gravadas10.getText();
            if (cGravadas10.trim().length() > 0) {
                cGravadas10 = cGravadas10.replace(".", "");
                cGravadas10 = cGravadas10.replace(",", ".");
            } else {
                cGravadas10 = "0";
            }

            String cIva10 = this.iva10.getText();
            if (cIva10.trim().length() > 0) {
                cIva10 = cIva10.replace(".", "");
                cIva10 = cIva10.replace(",", ".");
            } else {
                cIva10 = "0";
            }

            String cGravadas5 = this.gravadas5.getText();
            if (cGravadas5.trim().length() > 0) {
                cGravadas5 = cGravadas5.replace(".", "");
                cGravadas5 = cGravadas5.replace(",", ".");
            } else {
                cGravadas5 = "0";
            }
            String cIva5 = this.iva5.getText();
            if (cIva5.trim().length() > 0) {
                cIva5 = cIva5.replace(".", "");
                cIva5 = cIva5.replace(",", ".");
            } else {
                cIva5 = "0";
            }

            String cTotal = this.totalneto.getText();
            if (cTotal.trim().length() > 0) {
                cTotal = cTotal.replace(".", "");
                cTotal = cTotal.replace(",", ".");
            } else {
                cTotal = "0";
            }

            //PROCESO DE GRABACION
            if (Operacion.equals("new")) {
                UUID id = new UUID();
                String idunico = UUID.crearUUID();
                idunico = idunico.substring(1, 25);
                this.creferencia.setText(idunico);
                insertarRegistro("cabecera_ventas", "creferencia,factura,sucursal,vendedor,fecha,cliente,exentas,gravadas10,gravadas5,totalneto,moneda,cotizacion,cuotas,vencimiento,comprobante,observacion", "'" + this.creferencia.getText() + "','" + this.nrofactura.getText() + "','" + Sucursal + "','" + Asesor + "','" + dProceso + "','" + this.Cliente.getText() + "','" + cExentas + "','" + cGravadas10 + "','" + cGravadas5 + "','" + cTotal + "','" + Moneda + "','" + this.cotizacion.getText() + "','" + this.cuotas.getText() + "','" + dVencimiento + "','" + Comprobante + "','" + this.observacion.getText() + "'");
                try {
                    GuardarDetalle();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }

            } else {
                //BORRO LOS REGISTROS DE CUENTA DE CLIENTES Y DETALLE DE MOVIMIENTOS
                BorrarDetalles("cuenta_clientes", " creferencia='" + this.creferencia.getText() + "'");
                BorrarDetalles("detalle_ventas", " dreferencia='" + this.creferencia.getText() + "'");
                actualizarRegistro("cabecera_ventas", " factura='" + this.nrofactura.getText() + "',sucursal='" + Sucursal + "',vendedor='" + Asesor + "',cliente='" + this.Cliente.getText() + "',cotizacion='" + this.cotizacion.getText() + "',fecha='" + dProceso + "',exentas='" + cExentas + "',gravadas10='" + cGravadas10 + "',gravadas5='" + cGravadas5 + "',totalneto='" + cTotal + "',moneda='" + Moneda + "',cuotas='" + this.cuotas.getText()  + "',observacion='" +this.observacion.getText() + "',comprobante='"+Comprobante + "',vencimiento='" + dVencimiento + "'", " creferencia= '" + this.creferencia.getText() + "'");
                try {
                    this.GuardarDetalle();
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            this.dispose();
        // TODO add your handling code here:

            // TODO add your handling code here:
            // TODO add your handling code here:
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ModoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ModoActionPerformed

    private void codmonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codmonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codmonedaActionPerformed

    private void nrofacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nrofacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaActionPerformed

    private void preciounitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preciounitarioActionPerformed
        String cCantidad = this.cantidad.getText();
        cCantidad = cCantidad.replace(".", "");
        cCantidad = cCantidad.replace(",", ".");

        String cPrecio = this.preciounitario.getText();
        cPrecio = cPrecio.replace(".", "");
        cPrecio = cPrecio.replace(",", ".");

        double nCantidad = Double.parseDouble(cCantidad);//CANTIDAD
        double nPrecio = Double.parseDouble(cPrecio);//PRECIO
        ntotal = nCantidad * nPrecio;
        if(Double.valueOf(cotizacion.getText())==1){
            nIva = Math.floor(ntotal / 11);
        }else{
            nIva = ntotal / 11;
        }
            
        this.item_iva.setText(formato.format(nIva));
        this.totalitem.setText(formato.format(ntotal));
        this.NuevoItem.doClick();
        this.preciounitario.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_preciounitarioActionPerformed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            this.Cliente.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.NombreCliente.setText(Parametros.NOMBRE_ELEGIDO.trim());
            this.Direccion.setText(Parametros.DIRECCION_ELEGIDA.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
            Parametros.DIRECCION_ELEGIDA = "";
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void vendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorActionPerformed

    private void nrofacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrofacturaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.Cliente.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaKeyPressed

    private void ClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ClienteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.fecha.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrofactura.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_ClienteKeyPressed

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.comprobante.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.Cliente.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void comprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comprobanteKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.vencimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_comprobanteKeyPressed

    private void vencimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.codmoneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comprobante.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientoKeyPressed

    private void codmonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codmonedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.cuotas.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.vencimiento.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codmonedaKeyPressed

    private void cuotasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cuotasKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.cotizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codmoneda.requestFocus();
        }
        // TO        // TODO add your handling code here:
    }//GEN-LAST:event_cuotasKeyPressed

    private void cotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.vendedor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cuotas.requestFocus();
        }
        // TO        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionKeyPressed

    private void vendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vendedorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.codprod.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cotizacion.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_vendedorKeyPressed

    private void codprodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codprodKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.cantidad.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.vendedor.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodKeyPressed

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.preciounitario.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codprod.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void preciounitarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preciounitarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_preciounitarioKeyPressed

    private void codprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codprodActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre  from productos where estado=1 and codigo=" + this.codprod.getText());
            if (results.next()) {
                this.nombreproducto.setText(results.getString("nombre").trim());
            } else {
                this.BuscarProducto.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }         // TODO add your handling code here:
    }//GEN-LAST:event_codprodActionPerformed

    private void BuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarProductoActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("productos");
        cu.setVisible(true);
        this.cantidad.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarProductoActionPerformed

    private void cantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.codprod.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombreproducto.setText(Parametros.NOMBRE_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }         // TODO add your handling code here:
    }//GEN-LAST:event_cantidadFocusGained

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void AgregarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarItemActionPerformed
        itemfacturas.setSize(434, 345);
        itemfacturas.setLocationRelativeTo(null);
        this.limpiar();
        this.NuevoItem.setText("Agregar");
        this.cModo.setText("");
        itemfacturas.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarItemActionPerformed
    private void limpiar() {
        this.codprod.setText("");
        this.nombreproducto.setText("");
        this.cantidad.setText("");
        this.preciounitario.setText("");
        this.totalneto.setText("");
        this.sumatoria();
    }

    private void DeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteItemActionPerformed
        int a = this.jTable1.getSelectedRow();
        if (a < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelo.removeRow(a);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                this.sumatoria();
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteItemActionPerformed

    private void UpdateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateItemActionPerformed
        nFila = this.jTable1.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        this.cModo.setText(String.valueOf(nFila));
        itemfacturas.setSize(434, 345);
        itemfacturas.setLocationRelativeTo(null);
        this.NuevoItem.setText("Modificar");
        itemfacturas.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateItemActionPerformed

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if (evt.getClickCount() > 1) {
            Point point = evt.getPoint();
            nFila = this.jTable1.getSelectedRow();
            this.codprod.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 0).toString());
            this.nombreproducto.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 1).toString());
            this.cantidad.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 2).toString());
            this.preciounitario.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 3).toString());
            this.item_iva.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 5).toString());
            this.totalitem.setText(this.jTable1.getValueAt(this.jTable1.getSelectedRow(), 6).toString());
            this.UpdateItem.doClick();
            this.sumatoria();
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jTable1MousePressed

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed

     }//GEN-LAST:event_sucursalActionPerformed

    private void nrofacturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nrofacturaFocusGained

        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaFocusGained

    private void nrofacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nrofacturaMouseClicked
        GenFactura GenerarFactura = new GenFactura();
        Thread HiloFactura = new Thread(GenerarFactura);
        HiloFactura.start();        // TODO add your handling code here:
    }//GEN-LAST:event_nrofacturaMouseClicked

    private void codprodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codprodFocusGained
        this.preciounitario.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_codprodFocusGained
    public void GuardarDetalle() throws SQLException, ParseException {

        int Items = modelo.getRowCount();
        for (int i = 0; i < Items; i++) {
            String cProducto = (this.jTable1.getValueAt(i, 0).toString());
            String cCantidad = (this.jTable1.getValueAt(i, 2).toString());
            String cPrecio = (this.jTable1.getValueAt(i, 3).toString());
            String cTotalItem = (this.jTable1.getValueAt(i, 6).toString());
            String cImporteIva = (this.jTable1.getValueAt(i, 5).toString());
            String cPorcentajeIva = (this.jTable1.getValueAt(i, 4).toString());

            if (cCantidad.trim().length() > 0) {
                cCantidad = cCantidad.replace(".", "");
                cCantidad = cCantidad.replace(",", ".");
            } else {
                cCantidad = "0";
            }
            if (cPrecio.trim().length() > 0) {
                cPrecio = cPrecio.replace(".", "");
                cPrecio = cPrecio.replace(",", ".");
            } else {
                cPrecio = "0";
            }
            if (cTotalItem.trim().length() > 0) {
                cTotalItem = cTotalItem.replace(".", "");
                cTotalItem = cTotalItem.replace(",", ".");
            } else {
                cTotalItem = "0";
            }
            if (cImporteIva.trim().length() > 0) {
                cImporteIva = cImporteIva.replace(".", "");
                cImporteIva = cImporteIva.replace(",", ".");
            } else {
                cImporteIva = "0";
            }

            //         if (cPorcentajeIva.trim().length() > 0) {
            //             cPorcentajeIva = cPorcentajeIva.replace(".", "");
            //             cPorcentajeIva = cPorcentajeIva.replace(",", ".");
            //         } else {
            //             cPorcentajeIva = "0";
            //         }
            //PRIMERO  GRABAMOS EL DETALLE DE DETALLE DE VENTAS 
            InsertarRegistroDetalle("detalle_ventas", "dreferencia,codprod,cantidad,precio,monto,impiva,porcentaje", "'" + this.creferencia.getText() + "','" + cProducto + "','" + cCantidad + "','" + cPrecio + "','" + cTotalItem + "','" + cImporteIva + "','" + cPorcentajeIva + "'");
            //LUEGO ACTUALIZAMOS LA TABLA DE DEUDAS DE CLIENTES
            //EN LA TABLA cuenta_clientes
            //    InsertarRegistroDetalle("cuenta_clientes", "iddocumento,creferencia,documento,fecha,vencimiento,cliente,sucursal,moneda,comprobante,importe,numerocuota,cuota,saldo,inversionista,tasaoperativa", "'" + iddocumento + "','" + this.creferencia.getText() + "','" + cNrodocumento + "','" + dEmision + "','" + dVence + "','" + this.Cliente.getText() + "','" + Sucursal + "','" + Moneda + "','" + cComprobante + "','" + cMonto + "','" + cCuota + "','" + cCuota + "','" + cMonto + "','" + cAcreedor + "','" + cTasa + "'");
        }
    }

    private void insertarRegistro(String tabla, String campos, String valores) {
        try {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                int i = stm.executeUpdate("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            }
            // System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
        } catch (Exception e) {
            int resultado;
            JOptionPane.showMessageDialog(null, "No se pudo realizar la insercion en la base de datos\nVerifique que se hayan completado todos los campos necesarios\nVuelva a intentar...\n" + e.getMessage(), "Atencion", 1);
        }
    }

    private void InsertarRegistroDetalle(String tabla, String campos, String valores) {
        try {
            stm.executeUpdate("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
        } catch (Exception e) {
            int resultado;
            JOptionPane.showMessageDialog(null, "No se pudo realizar la insercion en la base de datos\nVerifique que se hayan completado todos los campos necesarios\nVuelva a intentar...\n" + e.getMessage(), "Atencion", 1);
        }
    }

    private void BorrarDetalles(String tabla, String condicion) {
        try {
            stm.executeUpdate("delete from " + tabla + " where " + condicion);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void actualizarRegistro(String tabla, String campos, String criterio) {
        try {
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                int i = stm.executeUpdate("update " + tabla + " set " + campos + " where " + criterio);
            }
        } catch (Exception e) {
            int resultado;
            JOptionPane.showMessageDialog(null, "No se pudieron actualizar los datos\nEs posible que los datos se\nesten usando en otra interfaz..." + e.getMessage(), "Atencion", 1);
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
        }       //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new detalle_descuento_cheques().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarItem;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton BuscarProducto;
    private javax.swing.JTextField Cliente;
    private javax.swing.JButton DeleteItem;
    private javax.swing.JTextField Direccion;
    private javax.swing.JLabel Exentas;
    private javax.swing.JTextField Modo;
    private javax.swing.JTextField NombreCliente;
    private javax.swing.JButton NuevoItem;
    private javax.swing.JButton SalirItem;
    private javax.swing.JButton UpdateItem;
    private javax.swing.JTextField cModo;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JComboBox codmoneda;
    private javax.swing.JTextField codprod;
    private javax.swing.JComboBox comprobante;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JTextField creferencia;
    private javax.swing.JFormattedTextField cuotas;
    private javax.swing.JFormattedTextField exentas;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JFormattedTextField gravadas10;
    private javax.swing.JFormattedTextField gravadas5;
    private javax.swing.JTextField idfila;
    private javax.swing.JFormattedTextField item_iva;
    private javax.swing.JDialog itemfacturas;
    private javax.swing.JFormattedTextField iva10;
    private javax.swing.JFormattedTextField iva5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField nrofactura;
    private javax.swing.JTextArea observacion;
    private javax.swing.JFormattedTextField preciounitario;
    private javax.swing.JComboBox sucursal;
    private javax.swing.JFormattedTextField totalitem;
    private javax.swing.JFormattedTextField totalneto;
    private com.toedter.calendar.JDateChooser vencimiento;
    private javax.swing.JComboBox vendedor;
    // End of variables declaration//GEN-END:variables

    private class GenFactura extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            cSql = "SELECT factura+1  AS nRegistro FROM sucursales";
            try {
                results = stm.executeQuery(cSql);
                if (results.next()) {
                    nrofactura.setText(results.getString("nRegistro"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ControlGrabado;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.cabecera_asientoDAO;
import DAO.centro_costoDAO;
import DAO.detalle_centro_costoDAO;
import DAO.detalle_compra_gastosDAO;
import DAO.planDAO;
import DAO.saldo_proveedoresDAO;
import Modelo.Tablas;
import Modelo.centro_costo;
import Modelo.detalle_centro_costo;
import Modelo.detalle_compra_gastos;
import Modelo.plan;
import Modelo.saldo_proveedores;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class gastos extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelocentro = new Tablas();
    Tablas modelocentrocosto = new Tablas();
    Tablas modelo = new Tablas();
    Tablas modeloasiento = new Tablas();
    Tablas modeloplan = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrocentro;
    ObtenerFecha ODate = new ObtenerFecha();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    private TableRowSorter trsfiltroplan;

    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");

    public gastos() {
        initComponents();
        this.idItemcentro.setVisible(false);
        this.creferencia1.setVisible(false);
        this.creferencia.setVisible(false);
        this.jButton2.setIcon(icononuevo);
        this.jButton1.setIcon(iconoeditar);
        this.jButton3.setIcon(iconoborrar);
        this.Refrescar.setIcon(icorefresh);
        this.Listar.setIcon(iconoprint);
        this.jButton5.setIcon(iconosalir);
        this.BuscarCuenta.setIcon(iconobuscar);
        this.idItem.setVisible(false);
        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);
        this.CargarTituloAsiento();
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.jTextOpciones1.setVisible(false);
        this.cargarTitulo();
        this.Inicializar();
        this.TitPlan();
        this.CargarTituloCentro();
        this.TitCentro();

        GrillaCentroCosto grillaCe = new GrillaCentroCosto();
        Thread hiloCe = new Thread(grillaCe);
        hiloCe.start();

        GrillaCuenta grillagi = new GrillaCuenta();
        Thread hilogi = new Thread(grillagi);
        hilogi.start();

        String cSql = "";
        cSql = "SELECT creferencia,formatofactura,nrofactura,gastos_compras.fecha,comprobantes.nombre AS nombrecomprobante,proveedores.nombre AS nombreproveedor,exentas,";
        cSql = cSql + "gravadas10,gravadas5,iva10,iva5,totalneto,monedas.nombre AS nombremoneda,cotizacion,gastos_compras.timbrado,vencetimbrado,asiento ";
        cSql = cSql + " FROM gastos_compras ";
        cSql = cSql + " INNER JOIN comprobantes ";
        cSql = cSql + " ON comprobantes.codigo=gastos_compras.comprobante";
        cSql = cSql + " INNER JOIN monedas";
        cSql = cSql + " ON monedas.codigo=gastos_compras.moneda";
        cSql = cSql + " INNER JOIN proveedores";
        cSql = cSql + " ON proveedores.codigo=gastos_compras.proveedor ";
        cSql = cSql + "WHERE gastos_compras.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
        cSql = cSql + " AND gastos_compras.fondofijo=0 ";
        cSql = cSql + " ORDER BY gastos_compras.fecha";
        this.cargarTabla(cSql);
    }

    Control hand = new Control();

    private void TitCentro() {
        modelocentrocosto.addColumn("Código");
        modelocentrocosto.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelocentrocosto.getColumnCount(); i++) {
            tablacentrocosto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacentrocosto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacentrocosto.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacentrocosto.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
    }

    public void CargarTituloCentro() {
        modelocentro.addColumn("Descripción");
        modelocentro.addColumn("Cantidad");
        modelocentro.addColumn("Precio");
        modelocentro.addColumn("IVA");
        modelocentro.addColumn("Total");
        modelocentro.addColumn("Imp. IVA");
        modelocentro.addColumn("Centro");
        modelocentro.addColumn("Denominación");
        modelocentro.addColumn("IdItem");
        int[] anchos = {150, 100, 100, 100, 100, 100, 90, 120, 50};
        for (int i = 0; i < modelocentro.getColumnCount(); i++) {
            this.tablacentro.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacentro.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacentro.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.tablacentro.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablacentro.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablacentro.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablacentro.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.tablacentro.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablacentro.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);

        this.tablacentro.getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablacentro.getColumnModel().getColumn(8).setMinWidth(0);
        this.tablacentro.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablacentro.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);

    }

    private void LimpiarCentro() {
        descripcioncentro.setText("");
        cantidadcentro.setText("0");
        precio1.setText("0");
        porcentajeivacentro.setText("0");
        totalitemcentro.setText("0");
        importeivacentro.setText("0");
        idcentro.setText("0");
        nombrecentro.setText("");
        idItemcentro.setText("0");
    }

    public void sumarCentro() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double ntotal = 0.00;
        double sumtotal = 0.00;
        String ctotal = "";

        int totalRow = tablacentro.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL A PAGAR
            ctotal = String.valueOf(tablacentro.getValueAt(i, 4));
            ctotal = ctotal.replace(".", "").replace(",", ".");
            ntotal = Double.valueOf(ctotal);
            sumtotal += ntotal;
        }
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalcentro.setText(formatea.format(sumtotal));
    }

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
    }

    private void Inicializar() {
        Calendar c2 = new GregorianCalendar();
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
    }

    public void filtroplan(int nNumeroColumna) {
        trsfiltroplan.setRowFilter(RowFilter.regexFilter(jTBuscarPlan.getText(), nNumeroColumna));
    }

    public void sumarAsiento() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double ntotal = 0.00;
        double sumtotal = 0.00;
        String ctotal = "";

        int totalRow = tablaasiento.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL A PAGAR
            ctotal = String.valueOf(tablaasiento.getValueAt(i, 4));
            ctotal = ctotal.replace(".", "").replace(",", ".");
            ntotal = Double.valueOf(ctotal);
            sumtotal += ntotal;
        }
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalcuentas.setText(formatea.format(sumtotal));
    }

    public void CargarTituloAsiento() {
        modeloasiento.addColumn("Descripción");
        modeloasiento.addColumn("Cantidad");
        modeloasiento.addColumn("Precio");
        modeloasiento.addColumn("IVA");
        modeloasiento.addColumn("Total");
        modeloasiento.addColumn("Imp. IVA");
        modeloasiento.addColumn("Cuenta");
        modeloasiento.addColumn("Denominación");
        modeloasiento.addColumn("IdItem");
        int[] anchos = {150, 100, 100, 100, 100, 100, 90, 120, 50};
        for (int i = 0; i < modeloasiento.getColumnCount(); i++) {
            this.tablaasiento.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaasiento.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaasiento.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.tablaasiento.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablaasiento.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaasiento.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablaasiento.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.tablaasiento.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablaasiento.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);

        this.tablaasiento.getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablaasiento.getColumnModel().getColumn(8).setMinWidth(0);
        this.tablaasiento.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablaasiento.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detalles = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nrofactura = new javax.swing.JTextField();
        nombreproveedor = new javax.swing.JTextField();
        totalfactura = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comprobante = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        creferencia = new javax.swing.JTextField();
        fechaemision = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaasiento = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        AgregarItem = new javax.swing.JButton();
        EditarItem = new javax.swing.JButton();
        BorrarItem = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        totalcuentas = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        itemCuentas = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        descripcion = new javax.swing.JTextField();
        cantidad = new javax.swing.JFormattedTextField();
        precio = new javax.swing.JFormattedTextField();
        porcentajeiva = new javax.swing.JFormattedTextField();
        totalitem = new javax.swing.JFormattedTextField();
        importeiva = new javax.swing.JFormattedTextField();
        nombrecuenta = new javax.swing.JTextField();
        idItem = new javax.swing.JTextField();
        cuenta = new javax.swing.JTextField();
        BuscarCuenta = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        GrabarItem = new javax.swing.JButton();
        SalirItem = new javax.swing.JButton();
        BCuenta = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        comboplan = new javax.swing.JComboBox();
        jTBuscarPlan = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaplan = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarCta = new javax.swing.JButton();
        SalirCta = new javax.swing.JButton();
        itemCentro = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        descripcioncentro = new javax.swing.JTextField();
        cantidadcentro = new javax.swing.JFormattedTextField();
        precio1 = new javax.swing.JFormattedTextField();
        porcentajeivacentro = new javax.swing.JFormattedTextField();
        totalitemcentro = new javax.swing.JFormattedTextField();
        importeivacentro = new javax.swing.JFormattedTextField();
        nombrecentro = new javax.swing.JTextField();
        idItemcentro = new javax.swing.JTextField();
        idcentro = new javax.swing.JTextField();
        BuscarCuenta1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        GrabarItemCentro = new javax.swing.JButton();
        SalirCentro = new javax.swing.JButton();
        BCentro = new javax.swing.JDialog();
        jPanel55 = new javax.swing.JPanel();
        comboplan1 = new javax.swing.JComboBox();
        jTBuscarPlan1 = new javax.swing.JTextField();
        jScrollPane17 = new javax.swing.JScrollPane();
        tablacentrocosto = new javax.swing.JTable();
        jPanel56 = new javax.swing.JPanel();
        AceptarCta1 = new javax.swing.JButton();
        SalirCta1 = new javax.swing.JButton();
        detalle_centro = new javax.swing.JDialog();
        jPanel57 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        nrofactura1 = new javax.swing.JTextField();
        nombreclientecentro = new javax.swing.JTextField();
        totalfacturacentro = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        comprobante1 = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        creferencia1 = new javax.swing.JTextField();
        fechaemisioncentro = new javax.swing.JTextField();
        jPanel58 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tablacentro = new javax.swing.JTable();
        jPanel59 = new javax.swing.JPanel();
        AgregarItemCentro = new javax.swing.JButton();
        EditarItemCentro = new javax.swing.JButton();
        BorrarItemCentro = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        totalcentro = new javax.swing.JFormattedTextField();
        jLabel71 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextOpciones1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        Refrescar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        tituloproveedor = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        detalles.setTitle("Detalle");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Factura");

        jLabel2.setText("Total Factura");

        jLabel3.setText("Proveedor");

        nrofactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrofactura.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        nrofactura.setEnabled(false);

        nombreproveedor.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        nombreproveedor.setEnabled(false);

        totalfactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalfactura.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        totalfactura.setEnabled(false);

        jLabel4.setText("Fecha Emisión");

        comprobante.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        comprobante.setEnabled(false);

        jLabel5.setText("Comprobante");

        creferencia.setEnabled(false);

        fechaemision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fechaemision.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        fechaemision.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nrofactura)
                    .addComponent(totalfactura, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fechaemision, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(fechaemision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(totalfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nombreproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablaasiento.setModel(modeloasiento);
        jScrollPane2.setViewportView(tablaasiento);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AgregarItem.setText("Agregar");
        AgregarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItemActionPerformed(evt);
            }
        });

        EditarItem.setText("Editar");
        EditarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EditarItem.setEnabled(false);
        EditarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarItemActionPerformed(evt);
            }
        });

        BorrarItem.setText("Borrar");
        BorrarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarItem.setEnabled(false);
        BorrarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarItemActionPerformed(evt);
            }
        });

        jButton7.setText("Salir");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        totalcuentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        totalcuentas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalcuentas.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        totalcuentas.setEnabled(false);
        totalcuentas.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Total");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BorrarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalcuentas, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarItem)
                    .addComponent(EditarItem)
                    .addComponent(BorrarItem)
                    .addComponent(jButton7)
                    .addComponent(totalcuentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detallesLayout = new javax.swing.GroupLayout(detalles.getContentPane());
        detalles.getContentPane().setLayout(detallesLayout);
        detallesLayout.setHorizontalGroup(
            detallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(detallesLayout.createSequentialGroup()
                .addGroup(detallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        detallesLayout.setVerticalGroup(
            detallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detallesLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        itemCuentas.setTitle("Item de Cuentas");

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Descripción");

        jLabel7.setText("Cantidad");

        jLabel8.setText("Precio");

        jLabel9.setText("% IVA");

        jLabel10.setText("Total");

        jLabel11.setText("Importe IVA");

        jLabel12.setText("Cuenta");

        descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                descripcionKeyPressed(evt);
            }
        });

        cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadKeyPressed(evt);
            }
        });

        precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                precioFocusLost(evt);
            }
        });
        precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                precioKeyPressed(evt);
            }
        });

        porcentajeiva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        porcentajeiva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        porcentajeiva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                porcentajeivaFocusLost(evt);
            }
        });
        porcentajeiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                porcentajeivaKeyPressed(evt);
            }
        });

        totalitem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalitem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalitem.setEnabled(false);
        totalitem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                totalitemKeyPressed(evt);
            }
        });

        importeiva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importeiva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importeiva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importeivaKeyPressed(evt);
            }
        });

        nombrecuenta.setEnabled(false);

        idItem.setEnabled(false);

        cuenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuentaActionPerformed(evt);
            }
        });
        cuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cuentaKeyPressed(evt);
            }
        });

        BuscarCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCuentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cuenta)
                            .addComponent(importeiva)
                            .addComponent(totalitem))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(idItem, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(BuscarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nombrecuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(porcentajeiva, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(42, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(porcentajeiva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(totalitem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addComponent(importeiva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(nombrecuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItem.setText("Grabar");
        GrabarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemActionPerformed(evt);
            }
        });

        SalirItem.setText("Salir");
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
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(GrabarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(SalirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarItem)
                    .addComponent(SalirItem))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout itemCuentasLayout = new javax.swing.GroupLayout(itemCuentas.getContentPane());
        itemCuentas.getContentPane().setLayout(itemCuentasLayout);
        itemCuentasLayout.setHorizontalGroup(
            itemCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemCuentasLayout.setVerticalGroup(
            itemCuentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemCuentasLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCuenta.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCuenta.setTitle(org.openide.util.NbBundle.getMessage(gastos.class, "detalle_gastos_cuenta.BCuenta.title")); // NOI18N

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboplan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboplan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboplan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboplan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboplanActionPerformed(evt);
            }
        });

        jTBuscarPlan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPlan.setText(org.openide.util.NbBundle.getMessage(gastos.class, "detalle_gastos_cuenta.jTBuscarPlan.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(comboplan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane6.setViewportView(tablaplan);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCta.setText(org.openide.util.NbBundle.getMessage(gastos.class, "detalle_gastos_cuenta.AceptarGir.text")); // NOI18N
        AceptarCta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCtaActionPerformed(evt);
            }
        });

        SalirCta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCta.setText(org.openide.util.NbBundle.getMessage(gastos.class, "detalle_gastos_cuenta.SalirGir.text")); // NOI18N
        SalirCta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCtaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCta, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCta)
                    .addComponent(SalirCta))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCuentaLayout = new javax.swing.GroupLayout(BCuenta.getContentPane());
        BCuenta.getContentPane().setLayout(BCuentaLayout);
        BCuentaLayout.setHorizontalGroup(
            BCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCuentaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCuentaLayout.setVerticalGroup(
            BCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCuentaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        descripcioncentro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                descripcioncentroKeyPressed(evt);
            }
        });

        cantidadcentro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cantidadcentro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidadcentro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadcentroKeyPressed(evt);
            }
        });

        precio1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        precio1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        precio1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                precio1FocusLost(evt);
            }
        });
        precio1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                precio1KeyPressed(evt);
            }
        });

        porcentajeivacentro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        porcentajeivacentro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        porcentajeivacentro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                porcentajeivacentroFocusLost(evt);
            }
        });
        porcentajeivacentro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                porcentajeivacentroKeyPressed(evt);
            }
        });

        totalitemcentro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalitemcentro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalitemcentro.setEnabled(false);
        totalitemcentro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                totalitemcentroKeyPressed(evt);
            }
        });

        importeivacentro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importeivacentro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importeivacentro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importeivacentroKeyPressed(evt);
            }
        });

        nombrecentro.setEnabled(false);

        idItemcentro.setEnabled(false);

        idcentro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        idcentro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idcentroActionPerformed(evt);
            }
        });
        idcentro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idcentroKeyPressed(evt);
            }
        });

        BuscarCuenta1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCuenta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCuenta1ActionPerformed(evt);
            }
        });

        jLabel16.setText("Descripción Centro");

        jLabel17.setText("Cantidad");

        jLabel18.setText("Precio");

        jLabel19.setText("IVA");

        jLabel20.setText("Total");

        jLabel21.setText("Importe IVA");

        jLabel22.setText("Centro de Costo");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel18)
                    .addComponent(jLabel22))
                .addGap(147, 147, 147)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idcentro)
                            .addComponent(importeivacentro)
                            .addComponent(totalitemcentro))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                                .addComponent(idItemcentro, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                                .addComponent(BuscarCuenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nombrecentro, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descripcioncentro, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cantidadcentro, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(precio1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(porcentajeivacentro, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(36, Short.MAX_VALUE))))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(descripcioncentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidadcentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(precio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(porcentajeivacentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(totalitemcentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(idItemcentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(importeivacentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)))
                .addGap(18, 18, 18)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(idcentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22))
                    .addComponent(BuscarCuenta1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel54.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarItemCentro.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.GrabarItemCentro.text")); // NOI18N
        GrabarItemCentro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarItemCentro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarItemCentroActionPerformed(evt);
            }
        });

        SalirCentro.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.SalirCentro.text")); // NOI18N
        SalirCentro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCentro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCentroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(GrabarItemCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(SalirCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarItemCentro)
                    .addComponent(SalirCentro))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout itemCentroLayout = new javax.swing.GroupLayout(itemCentro.getContentPane());
        itemCentro.getContentPane().setLayout(itemCentroLayout);
        itemCentroLayout.setHorizontalGroup(
            itemCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemCentroLayout.setVerticalGroup(
            itemCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemCentroLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCentro.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCentro.setTitle(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.BCentro.title")); // NOI18N

        jPanel55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboplan1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboplan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboplan1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboplan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboplan1ActionPerformed(evt);
            }
        });

        jTBuscarPlan1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPlan1.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jTBuscarPlan.text")); // NOI18N
        jTBuscarPlan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarPlan1ActionPerformed(evt);
            }
        });
        jTBuscarPlan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPlan1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addComponent(comboplan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPlan1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboplan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPlan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacentrocosto.setModel(modelocentrocosto);
        tablacentrocosto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacentrocostoMouseClicked(evt);
            }
        });
        tablacentrocosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacentrocostoKeyPressed(evt);
            }
        });
        jScrollPane17.setViewportView(tablacentrocosto);

        jPanel56.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCta1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCta1.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.AceptarCta.text")); // NOI18N
        AceptarCta1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCta1ActionPerformed(evt);
            }
        });

        SalirCta1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCta1.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.SalirCta.text")); // NOI18N
        SalirCta1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCta1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCta1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCta1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel56Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCta1)
                    .addComponent(SalirCta1))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCentroLayout = new javax.swing.GroupLayout(BCentro.getContentPane());
        BCentro.getContentPane().setLayout(BCentroLayout);
        BCentroLayout.setHorizontalGroup(
            BCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCentroLayout.createSequentialGroup()
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane17, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCentroLayout.setVerticalGroup(
            BCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCentroLayout.createSequentialGroup()
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel57.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel66.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jLabel66.text")); // NOI18N

        jLabel67.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jLabel67.text")); // NOI18N

        jLabel68.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jLabel68.text")); // NOI18N

        nrofactura1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrofactura1.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        nrofactura1.setEnabled(false);

        nombreclientecentro.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        nombreclientecentro.setEnabled(false);

        totalfacturacentro.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalfacturacentro.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        totalfacturacentro.setEnabled(false);

        jLabel69.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jLabel69.text")); // NOI18N

        comprobante1.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        comprobante1.setEnabled(false);

        jLabel70.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jLabel70.text")); // NOI18N

        creferencia1.setEnabled(false);

        fechaemisioncentro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fechaemisioncentro.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        fechaemisioncentro.setEnabled(false);

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel67)
                    .addComponent(jLabel66))
                .addGap(18, 18, 18)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nrofactura1)
                    .addComponent(totalfacturacentro, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel69)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel57Layout.createSequentialGroup()
                        .addComponent(comprobante1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(creferencia1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel57Layout.createSequentialGroup()
                        .addComponent(fechaemisioncentro, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreclientecentro, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel57Layout.createSequentialGroup()
                        .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(nrofactura1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69)
                            .addComponent(fechaemisioncentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68)
                            .addComponent(nombreclientecentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67)
                            .addComponent(totalfacturacentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel70)))
                    .addGroup(jPanel57Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comprobante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(creferencia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel58.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablacentro.setModel(modelocentro);
        jScrollPane18.setViewportView(tablacentro);

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );

        jPanel59.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AgregarItemCentro.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.AgregarItemCentro.text")); // NOI18N
        AgregarItemCentro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarItemCentro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItemCentroActionPerformed(evt);
            }
        });

        EditarItemCentro.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.EditarItemCentro.text")); // NOI18N
        EditarItemCentro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EditarItemCentro.setEnabled(false);
        EditarItemCentro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarItemCentroActionPerformed(evt);
            }
        });

        BorrarItemCentro.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.BorrarItemCentro.text")); // NOI18N
        BorrarItemCentro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarItemCentro.setEnabled(false);
        BorrarItemCentro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarItemCentroActionPerformed(evt);
            }
        });

        jButton8.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jButton7.text")); // NOI18N
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        totalcentro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        totalcentro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalcentro.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        totalcentro.setEnabled(false);
        totalcentro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel71.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel71.setText(org.openide.util.NbBundle.getMessage(gastos.class, "ventas_mercaderias.jLabel71.text")); // NOI18N

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AgregarItemCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditarItemCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BorrarItemCentro, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalcentro, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarItemCentro)
                    .addComponent(EditarItemCentro)
                    .addComponent(BorrarItemCentro)
                    .addComponent(jButton8)
                    .addComponent(totalcentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout detalle_centroLayout = new javax.swing.GroupLayout(detalle_centro.getContentPane());
        detalle_centro.getContentPane().setLayout(detalle_centroLayout);
        detalle_centroLayout.setHorizontalGroup(
            detalle_centroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(detalle_centroLayout.createSequentialGroup()
                .addGroup(detalle_centroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        detalle_centroLayout.setVerticalGroup(
            detalle_centroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_centroLayout.createSequentialGroup()
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(FechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        Refrescar.setText("Refrescar");
        Refrescar.setActionCommand("Filtrar");
        Refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(Listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextOpciones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(Refrescar)
                .addContainerGap(175, Short.MAX_VALUE))
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        tituloproveedor.setText("Registro de Gastos ");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Factura", "Proveedor", "Importe" }));
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

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 255));
        jLabel14.setText("F2 = Detalle Contable");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 255));
        jLabel15.setText("F3 = Editar ");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(tituloproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tituloproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 5;

                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 11;
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
            if (Config.nCentroCosto == 0) {
                new detalle_gastos(cOpcion).setVisible(true);
            } else {
                new detalle_gastos_cuenta(cOpcion).setVisible(true);
            }
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
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            ControlGrabado.REGISTRO_GRABADO = "";
            int nFila = this.jTable1.getSelectedRow();
            this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 0).toString());

            saldo_proveedoresDAO saldoDAO = new saldo_proveedoresDAO();
            saldo_proveedores saldo = null;
            try {
                saldo = saldoDAO.SaldoMovimiento(this.jTextOpciones1.getText());
                if (saldo.getNrofactura() != null) {
                    JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                    return;
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            String cOpcion = this.jTextOpciones1.getText();
            try {
                if (Config.nCentroCosto == 0) {
                    new detalle_gastos(cOpcion).setVisible(true);
                } else {
                    new detalle_gastos_cuenta(cOpcion).setVisible(true);
                }
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
            String cSql = "";
            cSql = "SELECT creferencia,formatofactura,gastos_compras.fecha,comprobantes.nombre AS nombrecomprobante,proveedores.nombre AS nombreproveedor,exentas,";
            cSql = cSql + "gravadas10,gravadas5,iva10,iva5,totalneto,monedas.nombre AS nombremoneda,cotizacion,gastos_compras.timbrado,vencetimbrado,asiento ";
            cSql = cSql + "FROM gastos_compras";
            cSql = cSql + " INNER JOIN comprobantes ";
            cSql = cSql + " ON comprobantes.codigo=gastos_compras.comprobante";
            cSql = cSql + " INNER JOIN monedas";
            cSql = cSql + " ON monedas.codigo=gastos_compras.moneda";
            cSql = cSql + " INNER JOIN proveedores";
            cSql = cSql + " ON proveedores.codigo=gastos_compras.proveedor ";
            cSql = cSql + "WHERE gastos_compras.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
            cSql = cSql + " AND gastos_compras.fondofijo=0 ";
            cSql = cSql + " ORDER BY gastos_compras.fecha";
            this.cargarTabla(cSql);
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
            int nFila = jTable1.getSelectedRow();
            String cAsiento = jTable1.getValueAt(nFila, 12).toString();
            this.jTextOpciones1.setText(jTable1.getValueAt(nFila, 0).toString());
            saldo_proveedoresDAO saldoDAO = new saldo_proveedoresDAO();
            saldo_proveedores saldo = null;
            try {
                saldo = saldoDAO.SaldoMovimiento(this.jTextOpciones1.getText());
                if (saldo.getNrofactura() != null) {
                    JOptionPane.showMessageDialog(null, "La Operación ya no puede Eliminarse");
                    return;
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            if (!this.jTextOpciones1.getText().isEmpty()) {
                BDConexion BD = new BDConexion();
                BD.borrarRegistro("gastos_compras", "creferencia= '" + this.jTextOpciones1.getText().trim() + "'");
                BD.BorrarDetalles("cuenta_proveedores", "idreferencia= '" + this.jTextOpciones1.getText().trim() + "'");
                dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
                dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
                String cSql = "";
                cSql = "SELECT creferencia,formatofactura,nrofactura,gastos_compras.fecha,comprobantes.nombre AS nombrecomprobante,proveedores.nombre AS nombreproveedor,exentas,";
                cSql = cSql + "gravadas10,gravadas5,iva10,iva5,totalneto,monedas.nombre AS nombremoneda,cotizacion,gastos_compras.timbrado,vencetimbrado,asiento ";
                cSql = cSql + "FROM gastos_compras";
                cSql = cSql + " INNER JOIN comprobantes ";
                cSql = cSql + " ON comprobantes.codigo=gastos_compras.comprobante";
                cSql = cSql + " INNER JOIN monedas";
                cSql = cSql + " ON monedas.codigo=gastos_compras.moneda";
                cSql = cSql + " INNER JOIN proveedores";
                cSql = cSql + " ON proveedores.codigo=gastos_compras.proveedor ";
                cSql = cSql + "WHERE gastos_compras.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
                cSql = cSql + " AND gastos_compras.fondofijo=0 ";
                cSql = cSql + " ORDER BY gastos_compras.fecha";
                this.cargarTabla(cSql);

                if (Double.valueOf(cAsiento) != 0) {
                    cabecera_asientoDAO cabDAO = new cabecera_asientoDAO();
                    try {
                        cabDAO.eliminarAsiento(Double.valueOf(cAsiento));
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }

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

    private void ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();
            parameters.put("", new String(""));
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/clientes.jasper");
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            masterPrint = JasperFillManager.fillReport(jr, null, stm.getConnection());
            JasperViewer ventana = new JasperViewer(masterPrint, false);
            ventana.setTitle("Vista Previa");
            ventana.setVisible(true);
        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", 1);
        }
    }//GEN-LAST:event_ListarActionPerformed

    private void RefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarActionPerformed
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        String cSql = "";
        cSql = "SELECT creferencia,formatofactura,nrofactura,gastos_compras.fecha,comprobantes.nombre AS nombrecomprobante,proveedores.nombre AS nombreproveedor,exentas,";
        cSql = cSql + "gravadas10,gravadas5,iva10,iva5,totalneto,monedas.nombre AS nombremoneda,cotizacion,gastos_compras.timbrado,vencetimbrado,asiento ";
        cSql = cSql + "FROM gastos_compras";
        cSql = cSql + " INNER JOIN comprobantes ";
        cSql = cSql + " ON comprobantes.codigo=gastos_compras.comprobante";
        cSql = cSql + " INNER JOIN monedas";
        cSql = cSql + " ON monedas.codigo=gastos_compras.moneda";
        cSql = cSql + " INNER JOIN proveedores";
        cSql = cSql + " ON proveedores.codigo=gastos_compras.proveedor ";
        cSql = cSql + "WHERE gastos_compras.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
        cSql = cSql + " AND gastos_compras.fondofijo=0 ";
        cSql = cSql + " ORDER BY gastos_compras.fecha";
        this.cargarTabla(cSql);
        // TODO add your handling code here:
    }//GEN-LAST:event_RefrescarActionPerformed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            int nFila = this.jTable1.getSelectedRow();
            GrillaAsiento GrillaP = new GrillaAsiento();
            Thread Hilo2 = new Thread(GrillaP);
            Hilo2.start();
            creferencia.setText(this.jTable1.getValueAt(nFila, 0).toString());
            nrofactura.setText(this.jTable1.getValueAt(nFila, 1).toString());
            fechaemision.setText(this.jTable1.getValueAt(nFila, 2).toString());
            comprobante.setText(this.jTable1.getValueAt(nFila, 3).toString());
            nombreproveedor.setText(this.jTable1.getValueAt(nFila, 5).toString());
            totalfactura.setText(this.jTable1.getValueAt(nFila, 11).toString());
            totalcuentas.setText("0");

            detalles.setModal(true);
            detalles.setSize(710, 370);
            //Establecemos un título para el jDialog
            detalles.setLocationRelativeTo(null);
            detalles.setVisible(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            ControlGrabado.REGISTRO_GRABADO = "";
            int nFila = this.jTable1.getSelectedRow();
            this.jTextOpciones1.setText(this.jTable1.getValueAt(nFila, 0).toString());
            String cOpcion = this.jTextOpciones1.getText();
            try {
                new detalle_gastos_ajustes(cOpcion).setVisible(true);
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            int nFila = this.jTable1.getSelectedRow();
            GrillaCentroCostoGastos GrillaP = new GrillaCentroCostoGastos();
            Thread Hilo2 = new Thread(GrillaP);
            Hilo2.start();
            creferencia1.setText(this.jTable1.getValueAt(nFila, 0).toString());
            nrofactura1.setText(this.jTable1.getValueAt(nFila, 1).toString());
            fechaemisioncentro.setText(this.jTable1.getValueAt(nFila, 2).toString());
            comprobante1.setText(this.jTable1.getValueAt(nFila, 3).toString());
            nombreclientecentro.setText(this.jTable1.getValueAt(nFila, 5).toString());
            totalfacturacentro.setText(this.jTable1.getValueAt(nFila, 11).toString());
            totalcentro.setText("0");
            detalle_centro.setModal(true);
            detalle_centro.setSize(710, 370);
            //Establecemos un título para el jDialog
            detalle_centro.setLocationRelativeTo(null);
            detalle_centro.setVisible(true);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyReleased

    private void AgregarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarItemActionPerformed
        LimpiarItem();

        itemCuentas.setModal(true);
        itemCuentas.setSize(533, 390);
        //Establecemos un título para el jDialog
        itemCuentas.setLocationRelativeTo(null);
        itemCuentas.setVisible(true);
        descripcion.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarItemActionPerformed

    private void SalirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirItemActionPerformed
        this.itemCuentas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirItemActionPerformed

    private void GrabarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Detalle? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            detalle_compra_gastos dc = new detalle_compra_gastos();
            detalle_compra_gastosDAO dcDAO = new detalle_compra_gastosDAO();

            if (this.descripcion.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese Descripción");
                this.descripcion.requestFocus();
                return;
            }

            planDAO plDAO = new planDAO();
            plan pl = null;
            try {
                pl = plDAO.buscarId(this.cuenta.getText());
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            String cCantidad = cantidad.getText();
            cCantidad = cCantidad.replace(".", "").replace(",", ".");

            String cPrecio = precio.getText();
            cPrecio = cPrecio.replace(".", "").replace(",", ".");

            String cIva = porcentajeiva.getText();
            cIva = cIva.replace(",", ".");

            String cTotalItem = totalitem.getText();
            cTotalItem = cTotalItem.replace(".", "").replace(",", ".");

            String cIvaImporte = importeiva.getText();
            cIvaImporte = cIvaImporte.replace(".", "").replace(",", ".");
            System.out.println(descripcion.getText());

            dc.setDreferencia(creferencia.getText());
            dc.setDescripcion(descripcion.getText());
            dc.setCantidad(Double.valueOf(cCantidad));
            dc.setPrcosto(Double.valueOf(cPrecio));
            dc.setPorcentaje(Double.valueOf(cIva));
            dc.setMonto(Double.valueOf(cTotalItem));
            dc.setImpiva(Double.valueOf(cIvaImporte));
            dc.setIdcta(pl);

            dc.setItemid(Double.valueOf(this.idItem.getText()));

            try {
                if (dc.getItemid() == 0) {
                    dcDAO.insertarItem(dc);
                } else {
                    dcDAO.actualizarItem(dc);
                    GrillaAsiento GrillaP = new GrillaAsiento();
                    Thread Hilo2 = new Thread(GrillaP);
                    Hilo2.start();
                    this.SalirItem.doClick();
                }
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            GrillaAsiento GrillaP = new GrillaAsiento();
            Thread Hilo2 = new Thread(GrillaP);
            Hilo2.start();
            this.LimpiarItem();
            this.descripcion.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.detalles.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void descripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descripcionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cantidad.requestFocus();
        }
    }//GEN-LAST:event_descripcionKeyPressed

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.precio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.descripcion.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.porcentajeiva.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_precioKeyPressed

    private void porcentajeivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcentajeivaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importeiva.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.precio.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeivaKeyPressed

    private void totalitemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalitemKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importeiva.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.porcentajeiva.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_totalitemKeyPressed

    private void importeivaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeivaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cuenta.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.porcentajeiva.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_importeivaKeyPressed

    private void precioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precioFocusLost
        String cCantidad = cantidad.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");
        String cCosto = precio.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCantidad) > 0 && Double.valueOf(cCosto) > 0) {
            totalitem.setText(formatea.format(Double.valueOf(cCantidad) * Double.valueOf(cCosto)));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_precioFocusLost

    private void porcentajeivaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentajeivaFocusLost
        String cTotal = totalitem.getText();
        cTotal = cTotal.replace(".", "").replace(",", ".");
        String cIva = porcentajeiva.getText();
        cIva = cIva.replace(",", ".");

        if (Double.valueOf(cIva) == 0) {
            this.importeiva.setText("0");
        } else if (Double.valueOf(cIva) == 5) {;
            this.importeiva.setText(formatea.format(Math.round(Double.valueOf(cTotal) / 21)));

        } else if (Double.valueOf(cIva) == 10) {;
            this.importeiva.setText(formatea.format(Math.round(Double.valueOf(cTotal) / 11)));
        } else {
            JOptionPane.showMessageDialog(null, "Porcentaje IVA no corresponde");
            this.porcentajeiva.requestFocus();
            return;
        }

        double ntotal = Double.valueOf(cTotal);

        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeivaFocusLost

    private void EditarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarItemActionPerformed
        int nFila = this.tablaasiento.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        descripcion.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 0).toString());
        cantidad.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 1).toString());
        precio.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 2).toString());
        porcentajeiva.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 3).toString());
        totalitem.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 4).toString());
        importeiva.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 5).toString());
        cuenta.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 6).toString());
        nombrecuenta.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 7).toString());
        idItem.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 8).toString());
        itemCuentas.setModal(true);
        itemCuentas.setSize(533, 390);
        //Establecemos un título para el jDialog
        itemCuentas.setLocationRelativeTo(null);
        itemCuentas.setVisible(true);
        descripcion.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_EditarItemActionPerformed

    private void BorrarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarItemActionPerformed
        int nFila = this.tablaasiento.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        idItem.setText(tablaasiento.getValueAt(tablaasiento.getSelectedRow(), 8).toString());
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Borrar este Item ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            detalle_compra_gastosDAO dcDAO = new detalle_compra_gastosDAO();
            try {
                dcDAO.EliminarItem(Double.valueOf(idItem.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            GrillaAsiento GrillaP = new GrillaAsiento();
            Thread Hilo2 = new Thread(GrillaP);
            Hilo2.start();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarItemActionPerformed

    private void cuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuentaActionPerformed
        this.BuscarCuenta.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cuentaActionPerformed

    private void cuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cuentaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarItem.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importeiva.requestFocus();
        }        // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cuentaKeyPressed

    private void BuscarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCuentaActionPerformed
        planDAO plaDAO = new planDAO();
        plan pl = null;
        try {
            pl = plaDAO.buscarId(this.cuenta.getText());
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
            this.GrabarItem.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCuentaActionPerformed

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

    private void tablaplanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaplanMouseClicked
        this.AceptarCta.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaplanMouseClicked

    private void tablaplanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaplanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCta.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaplanKeyPressed

    private void AceptarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCtaActionPerformed
        int nFila = this.tablaplan.getSelectedRow();
        this.cuenta.setText(this.tablaplan.getValueAt(nFila, 0).toString());
        this.nombrecuenta.setText(this.tablaplan.getValueAt(nFila, 1).toString());
        this.BCuenta.setVisible(false);
        this.BCuenta.setModal(false);
        this.GrabarItem.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCtaActionPerformed

    private void SalirCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCtaActionPerformed
        this.BCuenta.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCtaActionPerformed

    private void descripcioncentroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_descripcioncentroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cantidadcentro.requestFocus();
        }
    }//GEN-LAST:event_descripcioncentroKeyPressed

    private void cantidadcentroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadcentroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.precio1.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.descripcioncentro.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadcentroKeyPressed

    private void precio1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_precio1FocusLost
        String cCantidad = cantidadcentro.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");
        String cCosto = precio1.getText();
        cCosto = cCosto.replace(".", "").replace(",", ".");
        if (Double.valueOf(cCantidad) > 0 && Double.valueOf(cCosto) > 0) {
            totalitemcentro.setText(formatea.format(Double.valueOf(cCantidad) * Double.valueOf(cCosto)));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_precio1FocusLost

    private void precio1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precio1KeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.porcentajeivacentro.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidadcentro.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_precio1KeyPressed

    private void porcentajeivacentroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porcentajeivacentroFocusLost
        String cTotal = totalitemcentro.getText();
        cTotal = cTotal.replace(".", "").replace(",", ".");
        String cIva = porcentajeivacentro.getText();
        cIva = cIva.replace(",", ".");

        if (Double.valueOf(cIva) == 0) {
            this.importeivacentro.setText("0");
        } else if (Double.valueOf(cIva) == 5) {;
            this.importeivacentro.setText(formatea.format(Math.round(Double.valueOf(cTotal) / 21)));

        } else if (Double.valueOf(cIva) == 10) {;
            this.importeivacentro.setText(formatea.format(Math.round(Double.valueOf(cTotal) / 11)));
        } else {
            JOptionPane.showMessageDialog(null, "Porcentaje IVA no corresponde");
            this.porcentajeivacentro.requestFocus();
            return;
        }

        double ntotal = Double.valueOf(cTotal);

        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeivacentroFocusLost

    private void porcentajeivacentroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcentajeivacentroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importeivacentro.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.precio1.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeivacentroKeyPressed

    private void totalitemcentroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalitemcentroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importeivacentro.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.porcentajeivacentro.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_totalitemcentroKeyPressed

    private void importeivacentroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeivacentroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.idcentro.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.porcentajeivacentro.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_importeivacentroKeyPressed

    private void idcentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idcentroActionPerformed
        this.BuscarCuenta1.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_idcentroActionPerformed

    private void idcentroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idcentroKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarItemCentro.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importeivacentro.requestFocus();
        }        // TODO add your handling code here:        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_idcentroKeyPressed

    private void BuscarCuenta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCuenta1ActionPerformed
        centro_costoDAO ceDAO = new centro_costoDAO();
        centro_costo pl = null;
        try {
            pl = ceDAO.buscarId(Integer.valueOf(this.idcentro.getText()));
            if (pl.getCodigo() == 0) {
                BCentro.setModal(true);
                BCentro.setSize(482, 575);
                BCentro.setLocationRelativeTo(null);
                BCentro.setVisible(true);
                BCentro.setTitle("Buscar Centro");
                BCentro.setModal(false);
            } else {
                nombrecentro.setText(pl.getNombre());
                //Establecemos un título para el jDialog
            }
            this.GrabarItemCentro.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCuenta1ActionPerformed

    private void GrabarItemCentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarItemCentroActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Detalle? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            detalle_centro_costo dc = new detalle_centro_costo();
            detalle_centro_costoDAO dcDAO = new detalle_centro_costoDAO();

            if (this.descripcioncentro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese Descripción");
                this.descripcioncentro.requestFocus();
                return;
            }

            centro_costoDAO plDAO = new centro_costoDAO();
            centro_costo pl = null;
            try {
                pl = plDAO.buscarId(Integer.valueOf(this.idcentro.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            String cCantidad = cantidadcentro.getText();
            cCantidad = cCantidad.replace(".", "").replace(",", ".");

            String cPrecio = precio1.getText();
            cPrecio = cPrecio.replace(".", "").replace(",", ".");

            String cIva = porcentajeivacentro.getText();
            cIva = cIva.replace(",", ".");

            String cTotalItem = totalitemcentro.getText();
            cTotalItem = cTotalItem.replace(".", "").replace(",", ".");

            String cIvaImporte = importeivacentro.getText();
            cIvaImporte = cIvaImporte.replace(".", "").replace(",", ".");
            System.out.println(descripcioncentro.getText());

            dc.setDreferencia(creferencia1.getText());
            dc.setDescripcion(descripcioncentro.getText());
            dc.setCantidad(Double.valueOf(cCantidad));
            dc.setPrcosto(Double.valueOf(cPrecio));
            dc.setPorcentaje(Double.valueOf(cIva));
            dc.setMonto(Double.valueOf(cTotalItem));
            dc.setImpiva(Double.valueOf(cIvaImporte));
            dc.setIdcentro(pl);
            dc.setTipo(1);
            dc.setItemid(Double.valueOf(this.idItemcentro.getText()));

            try {
                if (dc.getItemid() == 0) {
                    dcDAO.insertarItem(dc);
                } else {
                    dcDAO.actualizarItem(dc);
                    GrillaCentroCostoGastos GrillaP = new GrillaCentroCostoGastos();
                    Thread Hilo2 = new Thread(GrillaP);
                    Hilo2.start();
                    this.SalirItem.doClick();
                }
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            GrillaCentroCostoGastos GrillaP = new GrillaCentroCostoGastos();
            Thread Hilo2 = new Thread(GrillaP);
            Hilo2.start();
            this.LimpiarCentro();
            this.descripcioncentro.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarItemCentroActionPerformed

    private void SalirCentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCentroActionPerformed
        this.itemCentro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCentroActionPerformed

    private void comboplan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboplan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboplan1ActionPerformed

    private void jTBuscarPlan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarPlan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPlan1ActionPerformed

    private void jTBuscarPlan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPlan1KeyPressed
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
                filtrocentro(indiceColumnaTabla);
            }
        });
        trsfiltrocentro = new TableRowSorter(tablacentrocosto.getModel());
        tablacentrocosto.setRowSorter(trsfiltrocentro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPlan1KeyPressed
    public void filtrocentro(int nNumeroColumna) {
        trsfiltrocentro.setRowFilter(RowFilter.regexFilter(jTBuscarPlan.getText(), nNumeroColumna));
    }


    private void tablacentrocostoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacentrocostoMouseClicked
        this.AceptarCta.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacentrocostoMouseClicked

    private void tablacentrocostoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacentrocostoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCta.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablacentrocostoKeyPressed

    private void AceptarCta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCta1ActionPerformed
        int nFila = this.tablacentrocosto.getSelectedRow();
        this.idcentro.setText(this.tablacentrocosto.getValueAt(nFila, 0).toString());
        this.nombrecentro.setText(this.tablacentrocosto.getValueAt(nFila, 1).toString());
        this.BCentro.setVisible(false);
        this.BCentro.setModal(false);
        this.GrabarItem.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCta1ActionPerformed

    private void SalirCta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCta1ActionPerformed
        this.BCentro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCta1ActionPerformed

    private void AgregarItemCentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarItemCentroActionPerformed
        LimpiarCentro();

        itemCentro.setModal(true);
        itemCentro.setSize(533, 390);
        //Establecemos un título para el jDialog
        itemCentro.setLocationRelativeTo(null);
        itemCentro.setVisible(true);
        descripcioncentro.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarItemCentroActionPerformed

    private void EditarItemCentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarItemCentroActionPerformed
        int nFila = this.tablacentro.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        descripcioncentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 0).toString());
        cantidadcentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 1).toString());
        precio1.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 2).toString());
        porcentajeivacentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 3).toString());
        totalitem.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 4).toString());
        importeivacentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 5).toString());
        idcentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 6).toString());
        nombrecentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 7).toString());
        idItemcentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 8).toString());
        itemCentro.setModal(true);
        itemCentro.setSize(533, 390);
        //Establecemos un título para el jDialog
        itemCentro.setLocationRelativeTo(null);
        itemCentro.setVisible(true);
        descripcioncentro.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_EditarItemCentroActionPerformed

    private void BorrarItemCentroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarItemCentroActionPerformed
        int nFila = this.tablacentro.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
            return;
        }
        idItemcentro.setText(tablacentro.getValueAt(tablacentro.getSelectedRow(), 8).toString());
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Borrar este Item ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            detalle_centro_costoDAO dcDAO = new detalle_centro_costoDAO();
            try {
                dcDAO.EliminarItem(Double.valueOf(idItemcentro.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            GrillaCentroCosto GrillaP = new GrillaCentroCosto();
            Thread Hilo2 = new Thread(GrillaP);
            Hilo2.start();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarItemCentroActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.detalle_centro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void LimpiarItem() {
        descripcion.setText("");
        cantidad.setText("0");
        precio.setText("0");
        porcentajeiva.setText("0");
        totalitem.setText("0");
        importeiva.setText("0");
        cuenta.setText("");
        nombrecuenta.setText("");
        idItem.setText("0");
    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° de Factura");
        modelo.addColumn("Fecha");
        modelo.addColumn("Comprobante");
        modelo.addColumn("Moneda");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Exentas");
        modelo.addColumn("Gravadas10");
        modelo.addColumn("IVA10");
        modelo.addColumn("Gravadas5");
        modelo.addColumn("IVA5");
        modelo.addColumn("Total");
        modelo.addColumn("Asiento");

        int[] anchos = {3, 120, 90, 150, 150, 200, 100, 100, 100, 100, 100, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);

        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

    }

    public void cargarTabla(String sql) {
        //Uso la Clase SimpleDateFormat para darle formato al campo fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        //Instanciamos esta clase para alinear las celdas numericas a la derecha
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

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
            results = stm.executeQuery(sql);
            while (results.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
                DecimalFormat formatea = new DecimalFormat("###,###.##");
                DecimalFormat formatosinpunto = new DecimalFormat("######");
                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[13]; // Hay 14 columnas en la tabla
                fila[0] = results.getString("creferencia");
                fila[1] = results.getString("formatofactura");
                fila[2] = formatoFecha.format(results.getDate("fecha"));
                fila[3] = results.getString("nombrecomprobante");
                fila[4] = results.getString("nombremoneda");
                fila[5] = results.getString("nombreproveedor");
                fila[6] = formatea.format(results.getDouble("exentas"));
                fila[7] = formatea.format(results.getDouble("gravadas10"));
                fila[8] = formatea.format(results.getDouble("iva10"));
                fila[9] = formatea.format(results.getDouble("gravadas5"));
                fila[10] = formatea.format(results.getDouble("iva5"));
                fila[11] = formatea.format(results.getDouble("totalneto"));
                fila[12] = formatosinpunto.format(results.getInt("asiento"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            this.jTable1.updateUI();
            int cantFilas = this.jTable1.getRowCount();
            if (cantFilas > 0) {
                this.jButton1.setEnabled(true);
                this.jButton3.setEnabled(true);
                this.Listar.setEnabled(true);
            } else {
                this.jButton1.setEnabled(false);
                this.jButton3.setEnabled(false);
                this.Listar.setEnabled(false);
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
                new gastos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCta;
    private javax.swing.JButton AceptarCta1;
    private javax.swing.JButton AgregarItem;
    private javax.swing.JButton AgregarItemCentro;
    private javax.swing.JDialog BCentro;
    private javax.swing.JDialog BCuenta;
    private javax.swing.JButton BorrarItem;
    private javax.swing.JButton BorrarItemCentro;
    private javax.swing.JButton BuscarCuenta;
    private javax.swing.JButton BuscarCuenta1;
    private javax.swing.JButton EditarItem;
    private javax.swing.JButton EditarItemCentro;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JButton GrabarItem;
    private javax.swing.JButton GrabarItemCentro;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Refrescar;
    private javax.swing.JButton SalirCentro;
    private javax.swing.JButton SalirCta;
    private javax.swing.JButton SalirCta1;
    private javax.swing.JButton SalirItem;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JFormattedTextField cantidadcentro;
    private javax.swing.JComboBox comboplan;
    private javax.swing.JComboBox comboplan1;
    private javax.swing.JTextField comprobante;
    private javax.swing.JTextField comprobante1;
    private javax.swing.JTextField creferencia;
    private javax.swing.JTextField creferencia1;
    private javax.swing.JTextField cuenta;
    private javax.swing.JTextField descripcion;
    private javax.swing.JTextField descripcioncentro;
    private javax.swing.JDialog detalle_centro;
    private javax.swing.JDialog detalles;
    private javax.swing.JTextField fechaemision;
    private javax.swing.JTextField fechaemisioncentro;
    private javax.swing.JTextField idItem;
    private javax.swing.JTextField idItemcentro;
    private javax.swing.JTextField idcentro;
    private javax.swing.JFormattedTextField importeiva;
    private javax.swing.JFormattedTextField importeivacentro;
    private javax.swing.JDialog itemCentro;
    private javax.swing.JDialog itemCuentas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTBuscarPlan;
    private javax.swing.JTextField jTBuscarPlan1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextOpciones1;
    private javax.swing.JTextField nombrecentro;
    private javax.swing.JTextField nombreclientecentro;
    private javax.swing.JTextField nombrecuenta;
    private javax.swing.JTextField nombreproveedor;
    private javax.swing.JTextField nrofactura;
    private javax.swing.JTextField nrofactura1;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JFormattedTextField porcentajeiva;
    private javax.swing.JFormattedTextField porcentajeivacentro;
    private javax.swing.JFormattedTextField precio;
    private javax.swing.JFormattedTextField precio1;
    private javax.swing.JTable tablaasiento;
    private javax.swing.JTable tablacentro;
    private javax.swing.JTable tablacentrocosto;
    private javax.swing.JTable tablaplan;
    private org.edisoncor.gui.label.LabelMetric tituloproveedor;
    private javax.swing.JFormattedTextField totalcentro;
    private javax.swing.JFormattedTextField totalcuentas;
    private javax.swing.JTextField totalfactura;
    private javax.swing.JTextField totalfacturacentro;
    private javax.swing.JFormattedTextField totalitem;
    private javax.swing.JFormattedTextField totalitemcentro;
    // End of variables declaration//GEN-END:variables

    private class GrillaAsiento extends Thread {

        public void run() {
            int cantidadRegistro = modeloasiento.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloasiento.removeRow(0);
            }
            detalle_compra_gastosDAO dtDAO = new detalle_compra_gastosDAO();
            try {
                for (detalle_compra_gastos dt : dtDAO.MostrarxReferencia(creferencia.getText())) {
                    String Detalle[] = {dt.getDescripcion(), formatosinpunto.format(dt.getCantidad()), formatea.format(dt.getPrcosto()), formatea.format(dt.getPorcentaje()), formatea.format(dt.getMonto()), formatea.format(dt.getImpiva()), dt.getIdcta().getCodigo(), dt.getIdcta().getNombre(), formatosinpunto.format(dt.getItemid())};
                    modeloasiento.addRow(Detalle);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            int cantFilas = tablaasiento.getRowCount();
            if (cantFilas > 0) {
                EditarItem.setEnabled(true);
                BorrarItem.setEnabled(true);
            } else {
                EditarItem.setEnabled(false);
                BorrarItem.setEnabled(false);
            }
            sumarAsiento();
        }
    }

    private class GrillaCuenta extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloplan.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloplan.removeRow(0);
            }
            planDAO DAOPLA = new planDAO();
            try {
                for (plan pl : DAOPLA.TodoAsentables()) {
                    String Datos[] = {String.valueOf(pl.getCodigo()), pl.getNombre()};
                    modeloplan.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaplan.setRowSorter(new TableRowSorter(modeloplan));
            int cantFilas = tablaplan.getRowCount();
        }
    }

    private class GrillaCentroCostoGastos extends Thread {

        public void run() {
            int cantidadRegistro = modelocentro.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocentro.removeRow(0);
            }
            detalle_centro_costoDAO dtDAO = new detalle_centro_costoDAO();
            try {
                for (detalle_centro_costo dt : dtDAO.MostrarxReferencia(creferencia1.getText())) {
                    String Detalle[] = {dt.getDescripcion(),
                        formatosinpunto.format(dt.getCantidad()),
                        formatea.format(dt.getPrcosto()),
                        formatea.format(dt.getPorcentaje()),
                        formatea.format(dt.getMonto()),
                        formatea.format(dt.getImpiva()),
                        formatosinpunto.format(dt.getIdcentro().getCodigo()),
                        dt.getIdcentro().getNombre(),
                        formatosinpunto.format(dt.getItemid())};
                    modelocentro.addRow(Detalle);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            int cantFilas = tablacentro.getRowCount();
            if (cantFilas > 0) {
                EditarItemCentro.setEnabled(true);
                BorrarItemCentro.setEnabled(true);
            } else {
                EditarItemCentro.setEnabled(false);
                BorrarItemCentro.setEnabled(false);
            }
            sumarCentro();
        }
    }

    private class GrillaCentroCosto extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocentrocosto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocentrocosto.removeRow(0);
            }
            centro_costoDAO DAOPLA = new centro_costoDAO();
            try {
                for (centro_costo pl : DAOPLA.Todos()) {
                    String Datos[] = {String.valueOf(pl.getCodigo()), pl.getNombre()};
                    modelocentrocosto.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacentrocosto.setRowSorter(new TableRowSorter(modelocentrocosto));
            int cantFilas = tablacentrocosto.getRowCount();
        }
    }

}

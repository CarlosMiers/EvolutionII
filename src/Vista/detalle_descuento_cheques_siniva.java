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
import Clases.Descuentos;
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
import javax.swing.ImageIcon;
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
public class detalle_descuento_cheques_siniva extends javax.swing.JFrame {

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro;
    Tablas modelo = new Tablas();
    Tablas modeloPagos = new Tablas();

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");
    Conexion con = null;
    Statement stm = null;
    ResultSet results = null;
    BDConexion BD = new BDConexion();
    String Operacion = null;
    Detalle_cheques cheques = new Detalle_cheques();
    ObtenerFecha ODate = new ObtenerFecha();
    String Sucursal = null;
    String Asesor = null;
    String Moneda = null;
    int cCuota = 1;

    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconoitemnuevo = new ImageIcon("src/Iconos/pencil_add.png");
    ImageIcon iconoitemupdate = new ImageIcon("src/Iconos/pencil.png");
    ImageIcon iconoitemdelete = new ImageIcon("src/Iconos/pencil_delete.png");
 
    public detalle_descuento_cheques_siniva(String Opcion) throws ParseException {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
    this.NuevoItem.setIcon(icononuevo);
        this.EditarItem.setIcon(iconoeditar);
        this.DelItem.setIcon(iconoborrar);
        this.GrabarOp.setIcon(iconograbar);
        this.SalirOp.setIcon(iconosalir);
        this.BuscarCliente.setIcon(iconobuscar);
        this.buscarbdesembolso.setIcon(iconobuscar);
    
        this.Modo.setVisible(false);
        this.CargarTitulo();
        this.CargarTituloPagos();
        this.limpiarCombos();
        this.idfila.setVisible(false);
        this.creferencia.setVisible(false);
        this.idControl.setEditable(false);
        this.idControl.setHorizontalAlignment(JTextField.RIGHT);
        this.importe.setHorizontalAlignment(JTextField.RIGHT);
        this.importe_descuentos.setHorizontalAlignment(JTextField.RIGHT);
        this.neto_a_entregar.setHorizontalAlignment(JTextField.RIGHT);
        this.comision_deudor.setHorizontalAlignment(JTextField.RIGHT);
        this.deducciones.setHorizontalAlignment(JTextField.RIGHT);

        this.importe.setText("0");
        this.importe_descuentos.setText("0");
        this.neto_a_entregar.setText("0");
        this.comision_deudor.setText("0");

        this.deducciones.setText("0");
        this.idControl.setVisible(true);
        this.idControl.setText(Opcion);
        Calendar c2 = new GregorianCalendar();
        if (Opcion == "new") {
            this.idControl.setText("");
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            this.fechaproceso.setCalendar(c2);
        } else {
            this.idControl.setText(Opcion);
            this.consultarTabla();
            this.PagosCheques();
            this.sumatoria();
        }
    }

    public void CargarTituloPagos() {
        modeloPagos.addColumn("Forma");
        modeloPagos.addColumn("Descripción");
        modeloPagos.addColumn("Cód. Cta.");
        modeloPagos.addColumn("Banco");
        modeloPagos.addColumn("Fecha");
        modeloPagos.addColumn("Importe");
        int[] anchos = {110, 100, 150, 150, 150, 150};
        for (int i = 0; i < modeloPagos.getColumnCount(); i++) {
            this.JTablePagos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public void sumatoria() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 

        double sumdeducciones = 0.00;
        double sumgastos = 0.00;
        double sumtotal = 0.00;
        double sumdescuentos = 0.00;
        double sumvaloractual = 0.00;
        double nmontoiva = 0.00;
        String cDeducciones = "";
        String cValorGastos = "";
        String cValorCheques = "";
        String cValorDescuento = "";
        String cValorActual = "";
        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        double sumatoriagastos = 0.00;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CHEQUES
            cValorCheques = String.valueOf(modelo.getValueAt(i, 10));
            cValorCheques = cValorCheques.replace(".", "");
            cValorCheques = cValorCheques.replace(",", ".");
            double sumatoria = Double.parseDouble(String.valueOf(cValorCheques));
            sumtotal += sumatoria;
            //SUMA EL TOTAL DE DESCUENTOS
            cValorDescuento = String.valueOf(modelo.getValueAt(i, 13));
            cValorDescuento = cValorDescuento.replace(".", "");
            cValorDescuento = cValorDescuento.replace(",", ".");
            double sumardescuento = Double.parseDouble(String.valueOf(cValorDescuento));
            sumdescuentos += sumardescuento;
            //SUMA EL IMPORTE DE LOS GASTOS ADMINISTRATIVOS POR CADA ITEM

            cValorGastos = String.valueOf(modelo.getValueAt(i, 17));
            cValorGastos = cValorGastos.replace(".", "");
            cValorGastos = cValorGastos.replace(",", ".");
            if (Double.valueOf(cValorGastos) > 0) {
                sumatoriagastos = Double.valueOf(cValorGastos);
                sumgastos += sumatoriagastos;
            }
            //SUMA EL VALOR ACTUAL DE LOS CHEQUES
            cValorActual = String.valueOf(modelo.getValueAt(i, 14));
            cValorActual = cValorActual.replace(".", "");
            cValorActual = cValorActual.replace(",", ".");
            double sumarvalor = Double.parseDouble(cValorActual);
            sumvaloractual += sumarvalor;
        }
        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        if (this.deducciones.getText() == "0") {
            sumdeducciones = 0;
        } else {
            cDeducciones = this.deducciones.getText();
            cDeducciones = cDeducciones.replace(".", "");
            cDeducciones = cDeducciones.replace(",", ".");
            sumdeducciones = Double.parseDouble(cDeducciones);
        }
        sumvaloractual = Math.round(sumtotal - (sumdescuentos + sumgastos + sumdeducciones));
        //  double neto =
        this.importe.setText(formato.format(sumtotal));

        this.comision_deudor.setText(formato.format(sumgastos));
        this.importe_descuentos.setText(formato.format(sumdescuentos));

        this.neto_a_entregar.setText(formato.format(sumvaloractual));
        //formato.format(sumatoria1);
    }

    public void CargarTitulo() {
        modelo.addColumn("Fecha Emisión");
        modelo.addColumn("Fecha Vence");
        modelo.addColumn("Fecha Compra");
        modelo.addColumn("N° de Documento");
        modelo.addColumn("N° de Cuenta");
        modelo.addColumn("Cód. Bco.");
        modelo.addColumn("Denominación Banco");
        modelo.addColumn("Cód. Operación");
        modelo.addColumn("Descripción Operación");
        modelo.addColumn("Denominación Librador");
        modelo.addColumn("Importe");
        modelo.addColumn("Tasa");
        modelo.addColumn("Plazo");
        modelo.addColumn("Valor Descuento");
        modelo.addColumn("Valor del Cheque");
        modelo.addColumn("Cta. Inversionista");
        modelo.addColumn("Denominación Inversionista");
        modelo.addColumn("Gastos");
        int[] anchos = {120, 120, 120, 150, 150, 120, 200, 120, 200, 200, 150, 90, 90, 150, 150, 150, 200, 150};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public void consultarTabla() throws ParseException {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        int nSucursal = 0;
        int nAsesor = 0;
        int nMoneda = 0;
        String query = null;
        query = "SELECT idprestamos,numero,fecha,moneda,sucursal,";
        query = query + "socio,clientes.nombre,clientes.direccion,";
        query = query + "comision_deudor,deducciones,importe,montoiva,";
        query = query + "totaldescuento,totalactual,autorizado_por,comision_asesor,cabecera_descuento_documentos.asesor";
        query = query + " FROM cabecera_descuento_documentos";
        query = query + " INNER JOIN clientes";
        query = query + " ON clientes.codigo=cabecera_descuento_documentos.socio";
        query = query + " WHERE cabecera_descuento_documentos.numero =" + this.idControl.getText();

        try {
            results = stm.executeQuery(query);
            if (results.next()) {
                nSucursal = results.getInt("sucursal");
                nAsesor = results.getInt("asesor");
                nMoneda = results.getInt("moneda");
                this.creferencia.setText(results.getString("idprestamos"));
                this.fechaproceso.setDate(results.getDate("fecha"));
                this.Cliente.setText(results.getString("socio"));
                this.NombreCliente.setText(results.getString("nombre"));
                this.Direccion.setText(results.getString("direccion"));
                this.autorizado.setText(results.getString("autorizado_por"));
                this.importe.setText(formato.format(results.getDouble("importe")));
                this.importe_descuentos.setText(formato.format(results.getDouble("totaldescuento")));
                this.comision_deudor.setText(formato.format(results.getDouble("comision_deudor")));
                this.deducciones.setText(formato.format(results.getDouble("deducciones")));
                this.neto_a_entregar.setText(formato.format(results.getDouble("totalactual")));
            }
            String cSucursal = selectCombo(sucursal, "select codigo,nombre from sucursales where codigo='" + nSucursal + "'");
            String cAsesor = selectCombo(asesor, "select codigo,nombre from vendedores where estado=1 and codigo='" + nAsesor + "'");
            String cMoneda = selectCombo(this.codmoneda, "select * from monedas where codigo='" + nMoneda + "'");
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        // Se construye el JFormattedTextField pasándole la máscara
        // Se da un valor inicial válido para evitar problemas
        //Inicializo Variables
        //Consultamos el Detalle  de la Operacion y Mostramos en el JTable
        query = "";
        query = "SELECT emision,nrodocumento,comprobante,comprobantes.nombre AS nombrecomprobante,";
        query = query + "cargobanco,bancos_plaza.nombre AS nombrebanco,monto_documento,librador,vencimiento,tasa,plazo,";
        query = query + "compra,descuento,valor_actual,acreedor,nrocuentacte,clientes.nombre as nombrecliente,detalle_descuento_documentos.gastos";
        query = query + " FROM detalle_descuento_documentos";
        query = query + " INNER JOIN comprobantes";
        query = query + " ON comprobantes.codigo=detalle_descuento_documentos.comprobante";
        query = query + " INNER JOIN bancos_plaza";
        query = query + " ON bancos_plaza.codigo=detalle_descuento_documentos.cargobanco";
        query = query + " INNER JOIN clientes";
        query = query + " ON clientes.codigo=detalle_descuento_documentos.acreedor";
        query = query + " WHERE detalle_descuento_documentos.ndescuento =" + this.idControl.getText();

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(13).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(17).setHeaderRenderer(TablaRenderer);

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
                Object[] fila = new Object[18]; // Hay 17   columnas en la tabla
                fila[0] = formatoFecha.format(results.getDate("emision"));
                fila[1] = formatoFecha.format(results.getDate("vencimiento"));
                fila[2] = formatoFecha.format(results.getDate("compra"));
                fila[3] = results.getString("nrodocumento");
                fila[4] = results.getString("nrocuentacte");
                fila[5] = results.getString("cargobanco");
                fila[6] = results.getString("nombrebanco");
                fila[7] = results.getInt("comprobante");
                fila[8] = results.getString("nombrecomprobante");
                fila[9] = results.getString("librador");
                fila[10] = formato.format(results.getDouble("monto_documento"));
                fila[11] = formato.format(results.getDouble("tasa"));
                fila[12] = formato.format(results.getDouble("plazo"));
                fila[13] = formato.format(results.getDouble("descuento"));
                fila[14] = formato.format(results.getDouble("valor_actual"));
                fila[15] = results.getString("acreedor");
                fila[16] = results.getString("nombrecliente");
                fila[17] = formato.format(results.getDouble("gastos"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            this.jTable1.updateUI();
            int cantFilas = this.jTable1.getRowCount();
            if (cantFilas > 0) {
                this.NuevoItem.setEnabled(true);
                this.EditarItem.setEnabled(true);
                this.DelItem.setEnabled(true);
            } else {
                this.NuevoItem.setEnabled(true);
                this.EditarItem.setEnabled(false);
                this.DelItem.setEnabled(false);
            }
            stm.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }

    public void PagosCheques() {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        String query = null;
        query = "SELECT forma,codmoneda,confirmacion,importepago,";
        query = query + "formaspago.nombre as nombreformapago,";
        query = query + "netocobrado,nrocheque,banco,";
        query = query + "bancos.nombre AS nombrebanco ";
        query = query + "FROM detalle_forma_pago ";
        query = query + "INNER JOIN bancos ";
        query = query + "ON bancos.codigo=detalle_forma_pago.banco ";
        query = query + "INNER JOIN formaspago ";
        query = query + "ON formaspago.codigo=detalle_forma_pago.forma ";
        query = query + "WHERE detalle_forma_pago.idmovimiento ='" + this.creferencia.getText() + "'";

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.JTablePagos.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.JTablePagos.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.JTablePagos.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.JTablePagos.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        int cantidadRegistro = modeloPagos.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloPagos.removeRow(0);
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
                fila2[5] = formato.format(results.getInt("netocobrado"));
                modeloPagos.addRow(fila2);
            }
            this.JTablePagos.setRowSorter(new TableRowSorter(modeloPagos));
            this.JTablePagos.updateUI();
            int cantFilas = this.JTablePagos.getRowCount();
            if (cantFilas > 0) {
                this.AgregarItem.setEnabled(true);
                this.BorrarItem.setEnabled(true);
            } else {
                this.AgregarItem.setEnabled(true);
                this.BorrarItem.setEnabled(false);
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

        this.asesor.removeAllItems();
        BD.cargarCombo("select codigo,nombre from vendedores order by codigo", this.asesor);
        this.asesor.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DesembolsoCheques = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        operacionDesembolso = new javax.swing.JTextField();
        cuentaDesembolso = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        nombreDesembolso = new javax.swing.JTextField();
        AgregarItem = new javax.swing.JButton();
        BorrarItem = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTablePagos = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        montoDesembolso = new javax.swing.JFormattedTextField();
        itemdesembolsocheques = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        Cuenta = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        opdesembolso = new javax.swing.JTextField();
        idctadesembolso = new javax.swing.JTextField();
        idformadesembolso = new javax.swing.JTextField();
        idbancodesembolso = new javax.swing.JTextField();
        nrochequedd = new javax.swing.JTextField();
        fechadesembolsodd = new com.toedter.calendar.JDateChooser();
        netodesembolsodd = new javax.swing.JFormattedTextField();
        buscarbdesembolso = new javax.swing.JButton();
        buscarfdesembolso = new javax.swing.JButton();
        nombrecliente = new javax.swing.JTextField();
        nombreformadesembolso = new javax.swing.JTextField();
        banconombredesembolso = new javax.swing.JTextField();
        GrabarDesembolso = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sucursal = new javax.swing.JComboBox();
        fechaproceso = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        codmoneda = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        Cliente = new javax.swing.JTextField();
        NombreCliente = new javax.swing.JTextField();
        Direccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        asesor = new javax.swing.JComboBox();
        BuscarCliente = new javax.swing.JButton();
        NuevoItem = new javax.swing.JButton();
        EditarItem = new javax.swing.JButton();
        DelItem = new javax.swing.JButton();
        idControl = new javax.swing.JTextField();
        Modo = new javax.swing.JTextField();
        creferencia = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        autorizado = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        importe_descuentos = new javax.swing.JFormattedTextField();
        comision_deudor = new javax.swing.JFormattedTextField();
        deducciones = new javax.swing.JFormattedTextField();
        neto_a_entregar = new javax.swing.JFormattedTextField();
        idfila = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        GrabarOp = new javax.swing.JButton();
        SalirOp = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        OpcionesDD = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        DesembolsoCheques.setAutoRequestFocus(false);

        jLabel14.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel14.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel15.text")); // NOI18N

        operacionDesembolso.setEditable(false);
        operacionDesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.operacionDesembolso.text")); // NOI18N
        operacionDesembolso.setBorder(null);

        cuentaDesembolso.setEditable(false);
        cuentaDesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.cuentaDesembolso.text")); // NOI18N
        cuentaDesembolso.setBorder(null);
        cuentaDesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuentaDesembolsoActionPerformed(evt);
            }
        });

        jLabel16.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel16.text")); // NOI18N

        nombreDesembolso.setEditable(false);
        nombreDesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.nombreDesembolso.text")); // NOI18N
        nombreDesembolso.setBorder(null);

        AgregarItem.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.AgregarItem.text")); // NOI18N
        AgregarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItemActionPerformed(evt);
            }
        });

        BorrarItem.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.BorrarItem.text")); // NOI18N
        BorrarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(operacionDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cuentaDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BorrarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(operacionDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(nombreDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cuentaDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(AgregarItem, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(BorrarItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        JTablePagos.setModel(modeloPagos);
        jScrollPane1.setViewportView(JTablePagos);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel17.text")); // NOI18N

        montoDesembolso.setEditable(false);
        montoDesembolso.setBorder(null);
        montoDesembolso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        montoDesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.montoDesembolso.text")); // NOI18N
        montoDesembolso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(montoDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(montoDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DesembolsoChequesLayout = new javax.swing.GroupLayout(DesembolsoCheques.getContentPane());
        DesembolsoCheques.getContentPane().setLayout(DesembolsoChequesLayout);
        DesembolsoChequesLayout.setHorizontalGroup(
            DesembolsoChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DesembolsoChequesLayout.setVerticalGroup(
            DesembolsoChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesembolsoChequesLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel29.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel29.text")); // NOI18N

        Cuenta.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.Cuenta.text")); // NOI18N

        jLabel30.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel30.text")); // NOI18N

        jLabel31.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel31.text")); // NOI18N

        jLabel28.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel28.text")); // NOI18N

        jLabel32.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel32.text")); // NOI18N

        jLabel33.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel33.text")); // NOI18N

        opdesembolso.setEditable(false);
        opdesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.opdesembolso.text")); // NOI18N

        idctadesembolso.setEditable(false);
        idctadesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.idctadesembolso.text")); // NOI18N
        idctadesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idctadesembolsoActionPerformed(evt);
            }
        });

        idformadesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.idformadesembolso.text")); // NOI18N
        idformadesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idformadesembolsoActionPerformed(evt);
            }
        });
        idformadesembolso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idformadesembolsoKeyPressed(evt);
            }
        });

        idbancodesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.idbancodesembolso.text")); // NOI18N
        idbancodesembolso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idbancodesembolsoFocusGained(evt);
            }
        });
        idbancodesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idbancodesembolsoActionPerformed(evt);
            }
        });
        idbancodesembolso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idbancodesembolsoKeyPressed(evt);
            }
        });

        nrochequedd.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.nrochequedd.text")); // NOI18N
        nrochequedd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nrochequeddFocusGained(evt);
            }
        });
        nrochequedd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrochequeddKeyPressed(evt);
            }
        });

        netodesembolsodd.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        netodesembolsodd.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.netodesembolsodd.text")); // NOI18N

        buscarbdesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.buscarbdesembolso.text")); // NOI18N
        buscarbdesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarbdesembolsoActionPerformed(evt);
            }
        });

        buscarfdesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.buscarfdesembolso.text")); // NOI18N
        buscarfdesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarfdesembolsoActionPerformed(evt);
            }
        });

        nombrecliente.setEditable(false);
        nombrecliente.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.nombrecliente.text")); // NOI18N

        nombreformadesembolso.setEditable(false);
        nombreformadesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.nombreformadesembolso.text")); // NOI18N

        banconombredesembolso.setEditable(false);
        banconombredesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.banconombredesembolso.text")); // NOI18N

        GrabarDesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.GrabarDesembolso.text")); // NOI18N
        GrabarDesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarDesembolsoActionPerformed(evt);
            }
        });

        jButton5.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jButton5.text")); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30)
                    .addComponent(Cuenta)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addGap(44, 44, 44)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(opdesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(220, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(idctadesembolso, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(idformadesembolso, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(idbancodesembolso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(buscarfdesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(buscarbdesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(nrochequedd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(banconombredesembolso)
                                    .addComponent(nombrecliente)
                                    .addComponent(nombreformadesembolso))
                                .addGap(31, 31, 31))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(netodesembolsodd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fechadesembolsodd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(Cuenta)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(opdesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(idctadesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(idformadesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarfdesembolso)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nombreformadesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(idbancodesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarbdesembolso)
                            .addComponent(banconombredesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nrochequedd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(fechadesembolsodd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(netodesembolsodd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout itemdesembolsochequesLayout = new javax.swing.GroupLayout(itemdesembolsocheques.getContentPane());
        itemdesembolsocheques.getContentPane().setLayout(itemdesembolsochequesLayout);
        itemdesembolsochequesLayout.setHorizontalGroup(
            itemdesembolsochequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        itemdesembolsochequesLayout.setVerticalGroup(
            itemdesembolsochequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.title")); // NOI18N
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(255, 51, 51));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel1.text")); // NOI18N

        sucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel2.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel3.text")); // NOI18N

        codmoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel4.text")); // NOI18N

        Cliente.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.Cliente.text")); // NOI18N
        Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClienteActionPerformed(evt);
            }
        });

        NombreCliente.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.NombreCliente.text")); // NOI18N
        NombreCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NombreCliente.setEnabled(false);

        Direccion.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.Direccion.text")); // NOI18N
        Direccion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Direccion.setEnabled(false);

        jLabel5.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel5.text")); // NOI18N

        asesor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BuscarCliente.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.BuscarCliente.text")); // NOI18N
        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        NuevoItem.setBackground(new java.awt.Color(204, 204, 204));
        NuevoItem.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.NuevoItem.text")); // NOI18N
        NuevoItem.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        EditarItem.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.EditarItem.text")); // NOI18N
        EditarItem.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EditarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EditarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarItemActionPerformed(evt);
            }
        });

        DelItem.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.DelItem.text")); // NOI18N
        DelItem.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DelItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DelItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelItemActionPerformed(evt);
            }
        });

        idControl.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.idControl.text")); // NOI18N

        Modo.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.Modo.text")); // NOI18N

        creferencia.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.creferencia.text")); // NOI18N

        jLabel12.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel12.text")); // NOI18N

        autorizado.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.autorizado.text")); // NOI18N
        autorizado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                autorizadoFocusGained(evt);
            }
        });
        autorizado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                autorizadoKeyReleased(evt);
            }
        });

        jLabel13.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel13.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1)
                        .addGap(180, 180, 180)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(32, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(57, 57, 57)
                                        .addComponent(jLabel12))
                                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(autorizado, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)))
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(codmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(asesor, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(139, 139, 139)
                        .addComponent(NuevoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EditarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DelItem, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(codmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(asesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(autorizado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(22, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(EditarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(NuevoItem)
                                    .addComponent(DelItem))
                                .addGap(14, 14, 14))))))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jPanel4.border.title"))); // NOI18N
        jPanel4.setOpaque(false);

        jLabel6.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel6.text")); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel7.text")); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel9.text")); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel10.text")); // NOI18N

        jLabel11.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jLabel11.text")); // NOI18N

        importe.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        importe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.importe.text")); // NOI18N
        importe.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        importe.setEnabled(false);
        importe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        importe_descuentos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        importe_descuentos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_descuentos.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.importe_descuentos.text")); // NOI18N
        importe_descuentos.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        importe_descuentos.setEnabled(false);
        importe_descuentos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        comision_deudor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        comision_deudor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        comision_deudor.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.comision_deudor.text")); // NOI18N
        comision_deudor.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        comision_deudor.setEnabled(false);
        comision_deudor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        deducciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        deducciones.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        deducciones.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        deducciones.setEnabled(false);
        deducciones.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        neto_a_entregar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        neto_a_entregar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        neto_a_entregar.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        neto_a_entregar.setEnabled(false);
        neto_a_entregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        idfila.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.idfila.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(importe_descuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(comision_deudor, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(deducciones, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(neto_a_entregar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(111, 111, 111))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(idfila, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importe_descuentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comision_deudor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deducciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(neto_a_entregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(idfila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));
        jPanel3.setForeground(new java.awt.Color(255, 51, 51));

        GrabarOp.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.GrabarOp.text")); // NOI18N
        GrabarOp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarOpActionPerformed(evt);
            }
        });

        SalirOp.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.SalirOp.text")); // NOI18N
        SalirOp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirOpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarOp, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SalirOp, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarOp)
                    .addComponent(SalirOp))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jPanel5.border.title"))); // NOI18N

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jTable1.setModel(modelo);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        OpcionesDD.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.OpcionesDD.text")); // NOI18N
        OpcionesDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpcionesDDActionPerformed(evt);
            }
        });

        jMenuItem1.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jMenuItem1.text")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        OpcionesDD.add(jMenuItem1);
        OpcionesDD.add(jSeparator1);

        jMenuItem2.setText(org.openide.util.NbBundle.getMessage(detalle_descuento_cheques_siniva.class, "detalle_descuento_cheques_siniva.jMenuItem2.text")); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        OpcionesDD.add(jMenuItem2);

        jMenuBar1.add(OpcionesDD);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "NO";
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaClientes cu = new Vista.ConsultaClientes();
        cu.setVisible(true);
        this.autorizado.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void NuevoItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NuevoItemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoItemFocusGained

    private void SalirOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirOpActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_SalirOpActionPerformed

    private void ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClienteActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre,telefono,direccion  from clientes where codigo=" + this.Cliente.getText());
            if (results.next()) {
                this.NombreCliente.setText(results.getString("nombre"));
                this.Direccion.setText(results.getString("direccion"));
                this.autorizado.requestFocus();
            } else {
                this.BuscarCliente.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ClienteActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void NuevoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoItemActionPerformed
        cheques.nFilaCheque = 0;
        this.idfila.setText("");
        ControlGrabado.REGISTRO_GRABADO = "NO";
        new item_descuento_documentos("new").setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_NuevoItemActionPerformed

    private void EditarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarItemActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "NO";
        cheques.nFilaCheque = this.jTable1.getSelectedRow();
        this.idfila.setText(String.valueOf(this.jTable1.getSelectedRow()));
        cheques.demision = (this.jTable1.getValueAt(cheques.nFilaCheque, 0).toString());
        cheques.dvencimiento = (this.jTable1.getValueAt(cheques.nFilaCheque, 1).toString());
        cheques.dcompra = (this.jTable1.getValueAt(cheques.nFilaCheque, 2).toString());
        cheques.cnrodocumento = (this.jTable1.getValueAt(cheques.nFilaCheque, 3).toString());
        cheques.cnrocuentacte = (this.jTable1.getValueAt(cheques.nFilaCheque, 4).toString());
        cheques.ccargobanco = (this.jTable1.getValueAt(cheques.nFilaCheque, 5).toString());
        cheques.cnombrebanco = (this.jTable1.getValueAt(cheques.nFilaCheque, 6).toString());
        cheques.ccomprobante = (this.jTable1.getValueAt(cheques.nFilaCheque, 7).toString());
        cheques.cnombrecomprobante = (this.jTable1.getValueAt(cheques.nFilaCheque, 8).toString());
        cheques.clibrador = (this.jTable1.getValueAt(cheques.nFilaCheque, 9).toString());
        cheques.nmonto_documento = (this.jTable1.getValueAt(cheques.nFilaCheque, 10).toString());
        cheques.ntasa = (this.jTable1.getValueAt(cheques.nFilaCheque, 11).toString());
        cheques.nplazo = (this.jTable1.getValueAt(cheques.nFilaCheque, 12).toString());
        cheques.ndescuento = (this.jTable1.getValueAt(cheques.nFilaCheque, 13).toString());
        cheques.nvalor_actual = (this.jTable1.getValueAt(cheques.nFilaCheque, 14).toString());
        cheques.nacreedor = (this.jTable1.getValueAt(cheques.nFilaCheque, 15).toString());
        cheques.cnombrecliente = (this.jTable1.getValueAt(cheques.nFilaCheque, 16).toString());
        cheques.cGastos = (this.jTable1.getValueAt(cheques.nFilaCheque, 17).toString());
        new item_descuento_documentos("").setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_EditarItemActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // AGREGAMOS O MODIFICAMOS AL JTABLE EL REGISTRO CORRESPONDIENTE QUE NOS DEVUELVE
        // EL FORM ITEM_DESCUENTO_CHEQUES
        double nneto = 0.00;
        double ndescuento = 0.00;
        String cneto = "";
        String cdescuento = "";
        if (ControlGrabado.REGISTRO_GRABADO == "SI") {
            String cadenacontrol = this.idfila.getText();
            if (cadenacontrol.isEmpty()) {
                Object[] fila = new Object[18]; // Hay 18   columnas en la tabla
                fila[0] = cheques.demision;
                fila[1] = cheques.dvencimiento;
                fila[2] = cheques.dcompra;
                fila[3] = cheques.cnrodocumento;
                fila[4] = cheques.cnrocuentacte;
                fila[5] = cheques.ccargobanco;
                fila[6] = cheques.cnombrebanco;
                fila[7] = cheques.ccomprobante;
                fila[8] = cheques.cnombrecomprobante;
                fila[9] = cheques.clibrador;
                fila[10] = cheques.nmonto_documento;
                fila[11] = cheques.ntasa;
                fila[12] = cheques.nplazo;
                fila[13] = cheques.ndescuento;
                fila[14] = cheques.nvalor_actual;
                fila[15] = cheques.nacreedor;
                fila[16] = cheques.cnombrecliente;
                fila[17] = cheques.cGastos;
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            } else {
                cheques.nFilaCheque = Integer.parseInt(this.idfila.getText());
                this.jTable1.setValueAt(cheques.demision, cheques.nFilaCheque, 0);
                this.jTable1.setValueAt(cheques.dvencimiento, cheques.nFilaCheque, 1);
                this.jTable1.setValueAt(cheques.dcompra, cheques.nFilaCheque, 2);
                this.jTable1.setValueAt(cheques.cnrodocumento, cheques.nFilaCheque, 3);
                this.jTable1.setValueAt(cheques.cnrocuentacte, cheques.nFilaCheque, 4);
                this.jTable1.setValueAt(cheques.ccargobanco, cheques.nFilaCheque, 5);
                this.jTable1.setValueAt(cheques.cnombrebanco, cheques.nFilaCheque, 6);
                this.jTable1.setValueAt(cheques.ccomprobante, cheques.nFilaCheque, 7);
                this.jTable1.setValueAt(cheques.cnombrecomprobante, cheques.nFilaCheque, 8);
                this.jTable1.setValueAt(cheques.clibrador, cheques.nFilaCheque, 9);
                this.jTable1.setValueAt(cheques.nmonto_documento, cheques.nFilaCheque, 10);
                this.jTable1.setValueAt(cheques.ntasa, cheques.nFilaCheque, 11);
                this.jTable1.setValueAt(cheques.nplazo, cheques.nFilaCheque, 12);
                this.jTable1.setValueAt(cheques.ndescuento, cheques.nFilaCheque, 13);
                this.jTable1.setValueAt(cheques.nvalor_actual, cheques.nFilaCheque, 14);
                this.jTable1.setValueAt(cheques.nacreedor, cheques.nFilaCheque, 15);
                this.jTable1.setValueAt(cheques.cnombrecliente, cheques.nFilaCheque, 16);
                this.jTable1.setValueAt(cheques.cGastos, cheques.nFilaCheque, 17);
            }
            this.idfila.setText("");
            cheques.nFilaCheque = 0;
            ControlGrabado.REGISTRO_GRABADO = "NO";
        }

        if (this.idControl.getText().length() > 1) {
            SumarDeducciones SumarD = new SumarDeducciones();
            Thread HiloSumD = new Thread(SumarD);
            HiloSumD.start();
        }
        this.sumatoria();
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void DelItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelItemActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "NO";
        cheques.nFilaCheque = 0;
        JOptionPane optionPane = new JOptionPane();
        Object[] opciones = {"   Si   ", "   No   "};
        int NumeroFila = this.jTable1.getSelectedRow();

        int ret = JOptionPane.showOptionDialog(null, "Desea Borrar? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);

        if (ret == 0) {
            modelo.removeRow(NumeroFila);
            this.sumatoria();
            // TODO add your handling code here:
        }

    }//GEN-LAST:event_DelItemActionPerformed

    private void GrabarOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarOpActionPerformed

        ControlGrabado.REGISTRO_GRABADO = "SI";
        con = new Conexion();
        stm = con.conectar();
        String Operacion = this.idControl.getText();

        //SE CAPTURA LOS DATOS DE LA CABECERA
        //Dando formato a los datos tipo Fecha
        Date dProceso = ODate.de_java_a_sql(this.fechaproceso.getDate());
        //Obteniendo Datos de los Combodatos
        Object objSucursal = this.sucursal.getSelectedItem();
        Object objAsesor = this.asesor.getSelectedItem();
        Object objMoneda = this.codmoneda.getSelectedItem();

        Sucursal = ((String[]) objSucursal)[0];
        Asesor = ((String[]) objAsesor)[0];
        Moneda = ((String[]) objMoneda)[0];

        //SE CAPTURAN LOS DATOS NUMERICOS
        String cImporte = this.importe.getText();
        cImporte = cImporte.replace(".", "");
        cImporte = cImporte.replace(",", ".");
        if (Double.valueOf(cImporte) <= 0) {
            cImporte = "0";
        }

        String cDescuentos = this.importe_descuentos.getText();
        cDescuentos = cDescuentos.replace(".", "");
        cDescuentos = cDescuentos.replace(",", ".");
        if (Double.valueOf(cDescuentos) <= 0) {
            cDescuentos = "0";
        }

        String cComision = this.comision_deudor.getText();
        cComision = cComision.replace(".", "");
        cComision = cComision.replace(",", ".");
        if (Double.valueOf(cComision) <= 0) {
            cComision = "0";
        }

        String cDeducciones = this.deducciones.getText();
        cDeducciones = cDeducciones.replace(".", "");
        cDeducciones = cDeducciones.replace(",", ".");
        if (Double.valueOf(cDeducciones) <= 0) {
            cDeducciones = "0";
        }

        String cTotal = this.neto_a_entregar.getText();
        cTotal = cTotal.replace(".", "");
        cTotal = cTotal.replace(",", ".");
        if (Double.valueOf(cTotal) <= 0) {
            cTotal = "0";
        }
         String cIva10="0";
        //PROCESO DE GRABACION
        if (Operacion.isEmpty()) {
            UUID id = new UUID();
            String idunico = UUID.crearUUID();
            idunico = idunico.substring(1, 25);
            this.creferencia.setText(idunico);
            String cSql = "SELECT MAX(numero)+1 AS nRegistro FROM cabecera_descuento_documentos";
            try {
                results = stm.executeQuery(cSql);
                if (results.next()) {
                    this.idControl.setText(results.getString("nRegistro"));
                    if (this.idControl.getText() == null) {
                        this.idControl.setText("1");
                    }
                }
                String cSqlCab = "INSERT INTO cabecera_descuento_documentos (idprestamos,fecha,moneda,sucursal,socio,comision_deudor,deducciones,";
                cSqlCab += "importe,montoiva,totaldescuento,totalactual,asesor,oficialcta,autorizado_por)";
                cSqlCab += "VALUES ('" + idunico + "','" + dProceso + "','" + Moneda + "','" + Sucursal + "','" + this.Cliente.getText() + "','" + cComision + "','" + cDeducciones + "','" + cImporte + "','";
                cSqlCab += cIva10 + "','" + cDescuentos + "','" + cTotal + "','" + Asesor + "','" + Asesor + "','" + this.autorizado.getText() + "')";
                stm.executeUpdate(cSqlCab, Statement.RETURN_GENERATED_KEYS);
                int idNumero = -1;
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idNumero = rs.getInt(1);
                }
                idControl.setText(String.valueOf(idNumero));

         //     insertarRegistro("cabecera_descuento_documentos", "idprestamos,numero,fecha,moneda,sucursal,socio,comision_deudor,deducciones,importe,montoiva,totaldescuento,totalactual,asesor,oficialcta,autorizado_por", "'" + idunico + "','" + this.idControl.getText() + "','" + dProceso + "','" + Moneda + "','" + Sucursal + "','" + this.Cliente.getText() + "','" + cComision + "','" + cDeducciones + "','" + cImporte + "','" + cIva10 + "','" + cDescuentos + "','" + cTotal + "','" + Asesor + "','" + Asesor + "','" + this.autorizado.getText() + "'");
                GuardarDetalle();
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            //BORRO LOS REGISTROS DE CUENTA DE CLIENTES Y DETALLE DE MOVIMIENTOS    
            BorrarDetalles("cuenta_clientes", " creferencia='" + this.creferencia.getText() + "'");
            BorrarDetalles("detalle_descuento_documentos", "ndescuento=" + this.idControl.getText());
            //ACTUALIZAMOS LA CABECERA DE DESCUENTOS DE DOCUMENTOS PARA LUEGO ACTUALIZAR LOS DETALLES
            actualizarRegistro("cabecera_descuento_documentos", " fecha='" + dProceso + "',moneda='" + Moneda + "',sucursal='" + Sucursal + "',socio='" + this.Cliente.getText() + "',comision_deudor='" + cComision + "',deducciones='" + cDeducciones + "',importe='" + cImporte + "',montoiva='" + cIva10 + "',totaldescuento='" + cDescuentos + "',totalactual='" + cTotal + "',asesor='" + Asesor + "',oficialcta='" + Asesor + "',autorizado_por='" + this.autorizado.getText() + "'", " numero=" + this.idControl.getText());
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
    }//GEN-LAST:event_GrabarOpActionPerformed

    private void autorizadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_autorizadoKeyReleased
        String letras = ConvertirMayusculas.cadena(autorizado);
        autorizado.setText(letras);

        // TODO add your handling code here:
    }//GEN-LAST:event_autorizadoKeyReleased

    private void autorizadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_autorizadoFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            this.Cliente.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.NombreCliente.setText(Parametros.NOMBRE_ELEGIDO.trim());
            this.Direccion.setText(Parametros.DIRECCION_ELEGIDA.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
            Parametros.DIRECCION_ELEGIDA = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_autorizadoFocusGained

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        String cEstadoAprobado = this.idControl.getText();
        if (this.idControl.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Recién al Grabar la Operación podrá hacer Deducciones");
            return;
        }

        Object objMoneda = codmoneda.getSelectedItem();
        Moneda = ((String[]) objMoneda)[0];
        Descuentos.totaldescuentos = "";
        new deducciones_dd(this.Cliente.getText(), Moneda, this.idControl.getText()).setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void cuentaDesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuentaDesembolsoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cuentaDesembolsoActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (Integer.parseInt(this.idControl.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "No tiene N° de Operación");
        } else {
            String cImporteDesembolso = this.neto_a_entregar.getText();
            cImporteDesembolso = cImporteDesembolso.replace(".", "");
            cImporteDesembolso = cImporteDesembolso.replace(",", ".");
            double nImportedesembolso = Double.parseDouble(cImporteDesembolso);
            DesembolsoCheques.setSize(700, 390);
            cuentaDesembolso.setText(this.Cliente.getText());
            nombreDesembolso.setText(this.NombreCliente.getText());
            operacionDesembolso.setText(this.idControl.getText());
            montoDesembolso.setText(formato.format(nImportedesembolso));
            //Establecemos un título para el jDialog
            DesembolsoCheques.setTitle("Desembolso Descuentos");
            DesembolsoCheques.setLocationRelativeTo(null);
            DesembolsoCheques.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void OpcionesDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpcionesDDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OpcionesDDActionPerformed

    private void idformadesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idformadesembolsoActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre from formaspago where codigo=" + this.idformadesembolso.getText());
            if (results.next()) {
                this.nombreformadesembolso.setText(results.getString("nombre").trim());
            } else {
                this.buscarfdesembolso.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_idformadesembolsoActionPerformed

    private void idformadesembolsoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idformadesembolsoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.idbancodesembolso.requestFocus();
        }
    }//GEN-LAST:event_idformadesembolsoKeyPressed

    private void idbancodesembolsoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idbancodesembolsoFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.idformadesembolso.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombreformadesembolso.setText(Parametros.NOMBRE_ELEGIDO);
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }        // TODO add your handling code here:
    }//GEN-LAST:event_idbancodesembolsoFocusGained

    private void idbancodesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idbancodesembolsoActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre from bancos where estado=1 and codigo=" + this.idbancodesembolso.getText());
            if (results.next()) {
                this.banconombredesembolso.setText(results.getString("nombre").trim());
            } else {
                this.buscarbdesembolso.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling co        // TODO add your handling code here:
    }//GEN-LAST:event_idbancodesembolsoActionPerformed

    private void idbancodesembolsoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idbancodesembolsoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nrochequedd.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.idformadesembolso.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_idbancodesembolsoKeyPressed

    private void nrochequeddFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nrochequeddFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.idbancodesembolso.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.banconombredesembolso.setText(Parametros.NOMBRE_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeddFocusGained

    private void nrochequeddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrochequeddKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechadesembolsodd.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrochequedd.requestFocus();
        }        // TODO add your handling code here        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeddKeyPressed

    private void buscarbdesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarbdesembolsoActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("bancos");
        cu.setVisible(true);
        this.nrochequedd.requestFocus();             // TODO add your handling code here:
    }//GEN-LAST:event_buscarbdesembolsoActionPerformed

    private void buscarfdesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarfdesembolsoActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("formaspago");
        cu.setVisible(true);
        this.idbancodesembolso.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarfdesembolsoActionPerformed

    private void idctadesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idctadesembolsoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idctadesembolsoActionPerformed

    private void GrabarDesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarDesembolsoActionPerformed

        Date dFechaConfirmacion = ODate.de_java_a_sql(fechadesembolsodd.getDate());
        //Obteniendo Datos de los Combodatos
        Object objMoneda = codmoneda.getSelectedItem();
        String cCodigoMoneda = ((String[]) objMoneda)[0];

        String cNetoDesembolso = this.netodesembolsodd.getText();
        cNetoDesembolso = cNetoDesembolso.replace(".", "");
        cNetoDesembolso = cNetoDesembolso.replace(",", ".");
        double NetoDesembolso = Double.parseDouble(cNetoDesembolso);
        Object[] fila2 = new Object[6]; // Hay 8   columnas en la tabla
        BDConexion BD = new BDConexion();
        BD.insertarRegistro("detalle_forma_pago", "idmovimiento,forma,codmoneda,banco,nrocheque,confirmacion,netocobrado", "'" + this.creferencia.getText() + "','" + this.idformadesembolso.getText() + "','" + cCodigoMoneda + "','" + this.idbancodesembolso.getText() + "','" + this.nrochequedd.getText() + "','" + dFechaConfirmacion + "','" + cNetoDesembolso + "'");

        fila2[0] = this.idformadesembolso.getText();
        fila2[1] = this.nombreformadesembolso.getText();
        fila2[2] = this.idbancodesembolso.getText();
        fila2[3] = this.banconombredesembolso.getText();
        fila2[4] = formatoFecha.format(dFechaConfirmacion);
        fila2[5] = formato.format(NetoDesembolso);
        modeloPagos.addRow(fila2);
        // EN ESTE HILO GUARDAMOS LA EXTRACCION DEL BANCO
        itemdesembolsocheques.setVisible(false);
        grabarExtraccionDD GuardarExtraccionDD = new grabarExtraccionDD();
        Thread HiloExtraccionDD = new Thread(GuardarExtraccionDD);
        HiloExtraccionDD.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarDesembolsoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.itemdesembolsocheques.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void AgregarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarItemActionPerformed
        /*        String cEstadoAprobado = this.estado.getText();
         if (cEstadoAprobado.equals("EN PROCESO")) {
         JOptionPane.showMessageDialog(null, "Préstamo aún no fue Aprobado, Verifique");
         return;
         }*/

        if (Integer.parseInt(this.idControl.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "No tiene N° de Operación");
        } else {
            String cImporteDesembolso = this.neto_a_entregar.getText();
            cImporteDesembolso = cImporteDesembolso.replace(".", "");
            cImporteDesembolso = cImporteDesembolso.replace(",", ".");
            double nImportedesembolso = Double.parseDouble(cImporteDesembolso);
            itemdesembolsocheques.setSize(506, 332);
            idctadesembolso.setText(this.Cliente.getText());
            nombrecliente.setText(this.NombreCliente.getText());
            opdesembolso.setText(this.idControl.getText());
            netodesembolsodd.setText(formato.format(nImportedesembolso));
            //Establecemos un título para el jDialog
            itemdesembolsocheques.setTitle("Desembolso Cheques");
            itemdesembolsocheques.setLocationRelativeTo(null);
            itemdesembolsocheques.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarItemActionPerformed

    private void BorrarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarItemActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "NO";
        JOptionPane optionPane = new JOptionPane();
        Object[] opciones = {"   Si   ", "   No   "};
        int NumeroFila = this.JTablePagos.getSelectedRow();
        int ret = JOptionPane.showOptionDialog(null, "Desea Borrar? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            modeloPagos.removeRow(NumeroFila);
            con = new Conexion();
            stm = con.conectar();
            String cSqlDelExtracto = "DELETE FROM extracciones WHERE idmovimiento='" + creferencia.getText() + "'";
            String cSqlDelFormaPago = "DELETE FROM detalle_forma_pago WHERE idmovimiento='" + creferencia.getText() + "'";
            try {
                stm.executeUpdate(cSqlDelExtracto);
                stm.executeUpdate(cSqlDelFormaPago);
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarItemActionPerformed
    public void GuardarDetalle() throws SQLException, ParseException {

        int Items = modelo.getRowCount();
        for (int i = 0; i < Items; i++) {
            Date dEmision = ODate.de_java_a_sql(formatoFecha.parse(this.jTable1.getValueAt(i, 0).toString()));
            Date dVence = ODate.de_java_a_sql(formatoFecha.parse(this.jTable1.getValueAt(i, 1).toString()));
            Date dCompra = ODate.de_java_a_sql(formatoFecha.parse(this.jTable1.getValueAt(i, 2).toString()));;
            String cNrodocumento = (this.jTable1.getValueAt(i, 3).toString());
            String cNrocuentacte = (this.jTable1.getValueAt(i, 4).toString());
            String cCargobanco = (this.jTable1.getValueAt(i, 5).toString());
            String cComprobante = (this.jTable1.getValueAt(i, 7).toString());
            String cLibrador = (this.jTable1.getValueAt(i, 9).toString());
            String cMonto = (this.jTable1.getValueAt(i, 10).toString());
            String cTasa = (this.jTable1.getValueAt(i, 11).toString());
            String cPlazo = (this.jTable1.getValueAt(i, 12).toString());
            String cDescuentoItem = (this.jTable1.getValueAt(i, 13).toString());
            String cValor_actual = (this.jTable1.getValueAt(i, 14).toString());
            String cAcreedor = (this.jTable1.getValueAt(i, 15).toString());
            String cGastosA = (this.jTable1.getValueAt(i, 17).toString());

            if (cMonto.trim().length() > 0) {
                cMonto = cMonto.replace(".", "");
                cMonto = cMonto.replace(",", ".");
            } else {
                cMonto = "0";
            }
            if (cTasa.trim().length() > 0) {
                cTasa = cTasa.replace(".", "");
                cTasa = cTasa.replace(",", ".");
            } else {
                cTasa = "0";
            }
            if (cPlazo.trim().length() > 0) {
                cPlazo = cPlazo.replace(".", "");
                cPlazo = cPlazo.replace(",", ".");
            } else {
                cPlazo = "0";
            }
            if (cDescuentoItem.trim().length() > 0) {
                cDescuentoItem = cDescuentoItem.replace(".", "");
                cDescuentoItem = cDescuentoItem.replace(",", ".");
            } else {
                cDescuentoItem = "0";
            }

            if (cValor_actual.trim().length() > 0) {
                cValor_actual = cValor_actual.replace(".", "");
                cValor_actual = cValor_actual.replace(",", ".");
            } else {
                cValor_actual = "0";
            }

            if (cGastosA.trim().length() > 0) {
                cGastosA = cGastosA.replace(".", "");
                cGastosA = cGastosA.replace(",", ".");
            } else {
                cGastosA = "0";
            }
            double nAmortizacion = Double.valueOf(cValor_actual) - Double.valueOf(cGastosA);
            String cAmorti = String.valueOf(nAmortizacion);

            //CREAMOS EL ID UNICO PARA EL DOCUMENTO
            String iddocumento = UUID.crearUUID();
            iddocumento = iddocumento.substring(1, 25);

            //PRIMERO EL GRABAMOS EL DETALLE DE CHEQUES EN 
            //LA TABLA detalle_descuento_documentos
            InsertarRegistroDetalle("detalle_descuento_documentos", "ndescuento,emision,nrodocumento,comprobante,cargobanco,monto_documento,librador,vencimiento,tasa,plazo,compra,descuento,valor_actual,acreedor,nrocuentacte,gastos", "'" + this.idControl.getText() + "','" + dEmision + "','" + cNrodocumento + "','" + cComprobante + "','" + cCargobanco + "','" + cMonto + "','" + cLibrador + "','" + dVence + "','" + cTasa + "','" + cPlazo + "','" + dCompra + "','" + cDescuentoItem + "','" + cValor_actual + "','" + cAcreedor + "','" + cNrocuentacte + "','" + cGastosA + "'");
            //LUEGO ACTUALIZAMOS LA TABLA DE DEUDAS DE CLIENTES
            //EN LA TABLA cuenta_clientes
            InsertarRegistroDetalle("cuenta_clientes", "iddocumento,creferencia,documento,fecha,vencimiento,cliente,sucursal,moneda,comprobante,importe,numerocuota,cuota,saldo,inversionista,tasaoperativa,capital,amortiza,minteres", "'" + iddocumento + "','" + this.creferencia.getText() + "','" + cNrodocumento + "','" + dEmision + "','" + dVence + "','" + this.Cliente.getText() + "','" + Sucursal + "','" + Moneda + "','" + cComprobante + "','" + cMonto + "','" + cCuota + "','" + cCuota + "','" + cMonto + "','" + cAcreedor + "','" + cTasa + "','" + cMonto + "','" + cAmorti + "','" + cDescuentoItem + "'");
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
            System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            JOptionPane.showMessageDialog(null, "No se pudo realizar la insercion en la base de datos\nVerifique que se hayan completado todos los campos necesarios\nVuelva a intentar...\n" + e.getMessage(), "Atencion", 1);
        }
    }

    private void InsertarRegistroDetalle(String tabla, String campos, String valores) {
        try {
            stm.executeUpdate("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
        } catch (Exception e) {
            int resultado;
            System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
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

            System.out.println("update " + tabla + " set " + campos + " where " + criterio);
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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("FormattedTextField.background", new ColorUIResource(255, 255, 255));
                    UIManager.put("control", new ColorUIResource(247, 247, 247));//fondo
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Portada.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Portada.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Portada.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Portada.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

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
    private javax.swing.JButton BorrarItem;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JTextField Cliente;
    private javax.swing.JLabel Cuenta;
    private javax.swing.JButton DelItem;
    private javax.swing.JDialog DesembolsoCheques;
    private javax.swing.JTextField Direccion;
    private javax.swing.JButton EditarItem;
    private javax.swing.JButton GrabarDesembolso;
    private javax.swing.JButton GrabarOp;
    private javax.swing.JTable JTablePagos;
    private javax.swing.JTextField Modo;
    private javax.swing.JTextField NombreCliente;
    private javax.swing.JButton NuevoItem;
    private javax.swing.JMenu OpcionesDD;
    private javax.swing.JButton SalirOp;
    private javax.swing.JComboBox asesor;
    private javax.swing.JTextField autorizado;
    private javax.swing.JTextField banconombredesembolso;
    private javax.swing.JButton buscarbdesembolso;
    private javax.swing.JButton buscarfdesembolso;
    private javax.swing.JComboBox codmoneda;
    private javax.swing.JFormattedTextField comision_deudor;
    private javax.swing.JTextField creferencia;
    private javax.swing.JTextField cuentaDesembolso;
    private javax.swing.JFormattedTextField deducciones;
    private com.toedter.calendar.JDateChooser fechadesembolsodd;
    private com.toedter.calendar.JDateChooser fechaproceso;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idbancodesembolso;
    private javax.swing.JTextField idctadesembolso;
    private javax.swing.JTextField idfila;
    private javax.swing.JTextField idformadesembolso;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JFormattedTextField importe_descuentos;
    private javax.swing.JDialog itemdesembolsocheques;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField montoDesembolso;
    private javax.swing.JFormattedTextField neto_a_entregar;
    private javax.swing.JFormattedTextField netodesembolsodd;
    private javax.swing.JTextField nombreDesembolso;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreformadesembolso;
    private javax.swing.JTextField nrochequedd;
    private javax.swing.JTextField opdesembolso;
    private javax.swing.JTextField operacionDesembolso;
    private javax.swing.JComboBox sucursal;
    // End of variables declaration//GEN-END:variables

    private class SumarDeducciones extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT SUM(supago) AS totaldeducciones FROM deducciones_descuentos WHERE ndescuento=" + idControl.getText());
                while (results.next()) {
                    deducciones.setText(formato.format(results.getDouble("totaldeducciones")));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }
        }
    }

    private class grabarExtraccionDD extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            String cSqlBorrarBanco = "DELETE FROM extracciones WHERE idmovimiento='" + creferencia.getText() + "'";
            String cSqlAgregarBanco = " INSERT INTO extracciones (idmovimiento,documento,fecha,sucursal,";
            cSqlAgregarBanco += "banco,moneda,observaciones,chequenro,vencimiento,importe,cierre,proveedor)";
            cSqlAgregarBanco += " SELECT cabecera_descuento_documentos.idprestamos,cabecera_descuento_documentos.numero,cabecera_descuento_documentos.fecha,cabecera_descuento_documentos.sucursal,";
            cSqlAgregarBanco += "detalle_forma_pago.banco,detalle_forma_pago.codmoneda,'DESEMBOLSO POR DESCUENTO DE DOCUMENTOS',";
            cSqlAgregarBanco += "detalle_forma_pago.nrocheque,detalle_forma_pago.confirmacion,detalle_forma_pago.netocobrado,1 AS cierre,1 ";
            cSqlAgregarBanco += " FROM cabecera_descuento_documentos ";
            cSqlAgregarBanco += " INNER JOIN detalle_forma_pago ";
            cSqlAgregarBanco += " ON cabecera_descuento_documentos.idprestamos=detalle_forma_pago.idmovimiento ";
            cSqlAgregarBanco += "WHERE cabecera_descuento_documentos.idprestamos='" + creferencia.getText() + "'";
            System.out.println(cSqlAgregarBanco);

            try {
                stm.executeUpdate(cSqlBorrarBanco);
                stm.executeUpdate(cSqlAgregarBanco);
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}

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
import java.awt.event.KeyEvent;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class detalle_proformas extends javax.swing.JFrame {

    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro;
    Tablas modelo = new Tablas();
    Tablas modelo2 = new Tablas();
    Tablas modelo3 = new Tablas();
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
    String Tipoprestamo = null;
    String Destinoprestamo = null;
    String Garantiaprestamo = null;
    int cCuota = 1;

    public detalle_proformas(String Opcion) throws ParseException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.Modo.setVisible(false);
        this.creferencia.setVisible(false);
        this.vencimientos.setVisible(false);
        this.venceanterior.setVisible(false);
        this.incluiriva.setVisible(true);
        this.auxiliar.setVisible(false);
        this.calcularneto.setVisible(false);
        this.estado.setVisible(false);
        this.incluiriva.setText("0");
        this.CargarTitulo1();
        this.limpiarCombos();
        this.idfila.setVisible(false);
        this.creferencia.setVisible(false);
        this.idControl.setEditable(false);
        this.tasa.setEditable(true);
        this.buscarbdesembolso.setVisible(false);
        this.buscarfdesembolso.setVisible(false);
        this.NroPrestamo.setHorizontalAlignment(JTextField.RIGHT);
        this.CtaTitular.setHorizontalAlignment(JTextField.RIGHT);
        this.descuento_intereses.setHorizontalAlignment(JTextField.RIGHT);
        this.idControl.setHorizontalAlignment(JTextField.RIGHT);
        this.descuentos.setHorizontalAlignment(JTextField.RIGHT);
        this.Cliente.setHorizontalAlignment(JTextField.RIGHT);
        this.montoiva.setHorizontalAlignment(JTextField.RIGHT);
        this.interes.setHorizontalAlignment(JTextField.RIGHT);
        this.totalprestamo.setHorizontalAlignment(JTextField.RIGHT);
        this.neto_a_entregar.setHorizontalAlignment(JTextField.RIGHT);
        this.gastos_administrativos.setHorizontalAlignment(JTextField.RIGHT);
        this.comision.setHorizontalAlignment(JTextField.RIGHT);
        this.importecapital.setHorizontalAlignment(JTextField.RIGHT);
        this.tasa.setHorizontalAlignment(JTextField.RIGHT);
        this.netodesembolso.setHorizontalAlignment(JTextField.RIGHT);
        this.plazo.setHorizontalAlignment(JTextField.RIGHT);
        this.importecuota.setHorizontalAlignment(JTextField.RIGHT);
        this.formadesembolso.setHorizontalAlignment(JTextField.RIGHT);
        this.bancodesembolso.setHorizontalAlignment(JTextField.RIGHT);
        this.idControl.setVisible(true);
        this.idControl.setText(Opcion);

        Calendar c2 = new GregorianCalendar();
        if (Opcion == "new") {
            this.Modo.setText(Opcion);
            this.idControl.setText("0");
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            this.fechaproceso.setCalendar(c2);
            this.descuento_intereses.setText("0");
            this.montoiva.setText("0");
            this.interes.setText("0");
            this.totalprestamo.setText("0");
            this.gastos_administrativos.setText("0");
            this.neto_a_entregar.setText("0");
            this.descuentos.setText("0");
        } else {
            this.idControl.setText(Opcion);
            this.consultarTabla();
            this.VerDetalleCuotas();
            // this.sumatoria();
        }
    }

    private void generarcuotas() {

        java.util.Date fecha1, fecha2;
        String cdiferencia = null;
        //Como siempre capturamos la moneda para poder hacer con decimales dependiendo
        // del tipo de moneda
        Object objMoneda = this.codmoneda.getSelectedItem();
        String cMoneda = ((String[]) objMoneda)[0];

        //Capturamos la tasa
        String cTasa = this.tasa.getText();
        cTasa = cTasa.replace(".", "");
        cTasa = cTasa.replace(",", ".");
        double nTotalInteres = 0.00;
        double nTotalPrestamo = 0.00;
        double nTotalIva = 0.00;
        double nTasa = Double.parseDouble(cTasa);
        double InteresAdicional = 0.00;
        double nImporteIva = 0.00;
        //Capturamos el importe del prestamo
        String cMonto = this.importecapital.getText();
        cMonto = cMonto.replace(".", "");
        cMonto = cMonto.replace(",", ".");
        String cMontoCuota = this.importecuota.getText();
        cMontoCuota = cMontoCuota.replace(".", "");
        cMontoCuota = cMontoCuota.replace(",", ".");
        double nCuotaAnterior = 0.00;
        double nMonto = 0.00;
        double nMontoCuota = 0.00;
        double nMontoCapital = 0.00;
        if (cMoneda.equals("1")) {
            nMonto = Math.round(Double.parseDouble(cMonto));
            nMontoCuota = Double.parseDouble(cMontoCuota);
            nMontoCapital = nMonto;
        } else {
            nMonto = Double.parseDouble(cMonto);
            nMontoCuota = Double.parseDouble(cMontoCuota);
            nMontoCapital = nMonto;
        }
        nCuotaAnterior = nMontoCuota;
        double nInteres = 0.00;
        double nAmortiza = 0.00;
        String cCuota = this.importecuota.getText();
        cCuota = cCuota.replace(".", "");
        cCuota = cCuota.replace(",", ".");
        int nCantidad = Integer.parseInt(this.plazo.getText());
  
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.jTable1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.primer_vencimiento.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }

        for (int i = 1; i <= nCantidad; i++) {
            //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
            // Se crea un array que será una de las filas de la tabla.
            // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
            Object[] fila = new Object[8]; // Hay 8   columnas en la tabla

            if (i == 1) {
                final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
                fecha1 = this.fechaproceso.getDate();
                fecha2 = this.vencimientos.getDate();
                long diferencia = (fecha2.getTime() - fecha1.getTime()) / MILLSECS_PER_DAY;
                cdiferencia = Long.toString(diferencia);
            }
            fila[0] = i;
            fila[1] = cdiferencia;
            fila[2] = formatoFecha.format(this.vencimientos.getDate());
            fila[3] = formato.format(nMonto);
            //Calculamos los valores de acuerdo al tipo de Moneda
            //Moneda Local sin decimales
            if (Integer.parseInt(cMoneda) == 1) {
                nInteres = Math.round(nMonto * (nTasa / 12) / 100);
                nAmortiza = Math.round(nMontoCuota - nInteres);
            } else {
                nInteres = nMonto * (nTasa / 12) / 100;
                nAmortiza = nMontoCuota - nInteres;
            }
            // SI LA CUOTA ES LA PRIMERA, SE VERIFICA SI LA CANTIDAD DE DIAS
            // ES MAYOR A 31 DIAS, ENTONCES SE COBRA INTERES ADICIONAL
            if (i == 1 && Integer.parseInt(cdiferencia) > 31) {
                InteresAdicional = Math.round(nMonto * (nTasa / 100)) / 360 * (Integer.parseInt(cdiferencia));
                nInteres = InteresAdicional;
                nMontoCuota = nAmortiza + nInteres;
            } else {
                nMontoCuota = nCuotaAnterior;
            }
            if (Integer.parseInt(this.incluiriva.getText()) > 0) {
                if (Integer.parseInt(cMoneda) == 1) {
                    nImporteIva = Math.round(nInteres * (Config.porcentajeiva / 100));
                } else {
                    nImporteIva = nInteres * (Config.porcentajeiva / 100);
                }
                nMontoCuota = nAmortiza + nInteres + nImporteIva;
                nAmortiza = nMontoCuota - nInteres;
            }
            fila[4] = formato.format(nAmortiza);
            fila[5] = formato.format(nInteres);
            fila[6] = formato.format(nImporteIva);
            fila[7] = formato.format(nMontoCuota);
            modelo.addRow(fila);// Se añade al modelo la fila completa.
            calendar.setTime(this.vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
            this.venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior 
            calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0
            this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
            nMonto = nMonto - nAmortiza;
            //Acumulamos los importes para los totales
            //Total Interes, Total Iva, Total Préstamo
            nTotalInteres = nTotalInteres + nInteres;
            nTotalIva = nTotalIva + nImporteIva;
            nTotalPrestamo = nTotalPrestamo + nMontoCuota;
            final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
            fecha1 = this.venceanterior.getDate();
            fecha2 = this.vencimientos.getDate();
            long diferencia = (fecha2.getTime() - fecha1.getTime()) / MILLSECS_PER_DAY;
            cdiferencia = Long.toString(diferencia);

            //Capturamos los gastos para restarlos al importe del prestamo
            //junto al IVA
            String cGastos = this.gastos_administrativos.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");
            double nGastos = Double.parseDouble(cGastos);

            this.interes.setText(formato.format(nTotalInteres));
            this.totalprestamo.setText(formato.format(nTotalPrestamo));
            System.out.println("nTotalInteres " + nTotalInteres);
            if (Integer.parseInt(cMoneda) == 1) {
                nTotalIva = Math.round(nTotalInteres * Config.porcentajeiva / 100);
            } else {
                nTotalIva = nTotalInteres * Config.porcentajeiva / 100;
            }
            this.montoiva.setText(formato.format(nTotalIva));
            if (Integer.parseInt(this.incluiriva.getText()) == 0) {
                this.neto_a_entregar.setText(formato.format(nMontoCapital - (nTotalIva + nGastos)));
            } else {
                this.neto_a_entregar.setText(formato.format(nMontoCapital - nGastos));
            }
        }
        this.jTable1.setRowSorter(new TableRowSorter(modelo));
        this.jTable1.updateUI();
        int cantFilas = this.jTable1.getRowCount();
        if (cantFilas > 0) {
            this.GuardarOperacion.setEnabled(true);
            this.Salir.setEnabled(true);
        } else {
            this.GuardarOperacion.setEnabled(true);
            this.Salir.setEnabled(false);
        }
    }

    public void CargarTitulo1() {
        modelo.addColumn("N°");
        modelo.addColumn("Días");
        modelo.addColumn("Vence");
        modelo.addColumn("Capital");
        modelo.addColumn("Amortización");
        modelo.addColumn("Interés");
        modelo.addColumn("IVA");
        modelo.addColumn("Monto Cuota");
        int[] anchos = {110, 100, 150, 150, 150, 150, 150, 150};
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
        int nDestino = 0;
        int nGarantia = 0;
        int nComprobante = 0;
        String query = null;

        query = "SELECT proformas.idprestamos,proformas.numero,proformas.fecha,proformas.moneda,proformas.estado,";
        query = query + "proformas.sucursal,proformas.acreedor,proformas.solicitud,proformas.socio,";
        query = query + "proformas.monto_entregar,proformas.porcentaje_deudor,proformas.comision_deudor,proformas.plazo,";
        query = query + "proformas.deducciones,proformas.importe,proformas.monto_cuota,proformas.primer_vence,";
        query = query + "proformas.tipo,proformas.tasa,proformas.interes,proformas.ivainteres,proformas.totalamortizacion,";
        query = query + "proformas.totalprestamo,proformas.garante,proformas.gastos_escritura,proformas.garante2,";
        query = query + "proformas.comision_asesor,proformas.codusuario,proformas.generariva,proformas.asesor,";
        query = query + "proformas.acreedor,proformas.interesnocobrado,proformas.oficial,proformas.destino,proformas.garantia,";
        query = query + "clientes.nombre AS nombrecliente,clientes.direccion ";
        query = query + "FROM proformas ";
        query = query + "INNER JOIN clientes ";
        query = query + "ON clientes.codigo=proformas.socio";
        query = query + " WHERE proformas.numero =" + this.idControl.getText();
        System.out.println(query);

        try {
            results = stm.executeQuery(query);
            if (results.next()) {
                nSucursal = results.getInt("sucursal");
                nAsesor = results.getInt("asesor");
                nMoneda = results.getInt("moneda");
                nComprobante = results.getInt("tipo");
                nDestino = results.getInt("destino");
                nGarantia = results.getInt("garantia");
                this.creferencia.setText(results.getString("idprestamos"));
                this.fechaproceso.setDate(results.getDate("fecha"));
                this.primer_vencimiento.setDate(results.getDate("primer_vence"));
                this.Cliente.setText(results.getString("socio"));
                this.NombreCliente.setText(results.getString("nombrecliente"));
                this.Direccion.setText(results.getString("direccion"));
                this.importecapital.setText(formato.format(results.getInt("importe")));
                this.tasa.setText(formato.format(results.getInt("tasa")));
                this.plazo.setText(formato.format(results.getInt("plazo")));
                this.importecuota.setText(formato.format(results.getInt("monto_cuota")));
                this.montoiva.setText(formato.format(results.getInt("ivainteres")));
                this.gastos_administrativos.setText(formato.format(results.getInt("comision_deudor")));
                this.comision.setText(formato.format(results.getInt("deducciones")));
                this.interes.setText(formato.format(results.getInt("interes")));
                this.totalprestamo.setText(formato.format(results.getInt("totalprestamo")));
                this.neto_a_entregar.setText(formato.format(results.getInt("monto_entregar")));
                this.estado.setText(results.getString("estado"));
            }
            String cSucursal = selectCombo(this.sucursal, "select codigo,nombre from sucursales where codigo='" + nSucursal + "'");
            String cAsesor = selectCombo(this.asesor, "select codigo,nombre from vendedores where estado =1 and codigo='" + nAsesor + "'");
            String cMoneda = selectCombo(this.codmoneda, "select * from monedas where codigo='" + nMoneda + "'");
            String cDestino = selectCombo(this.destinoprestamo, "select * from destino where codigo='" + nDestino + "'");
            String cGarantia = selectCombo(this.tipogarantia, "select * from garantias where codigo='" + nGarantia + "'");
            String cComprobante = selectCombo(this.tipoprestamo, "select * from comprobantes where tipo=3 and codigo='" + nComprobante + "'");
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void VerDetalleCuotas() {
        // Se construye el JFormattedTextField pasándole la máscara
        // Se da un valor inicial válido para evitar problemas
        //Inicializo Variables
        //Consultamos el Detalle  de la Operacion y Mostramos en el JTable
        con = new Conexion();
        stm = con.conectar();
        ResultSet rsdet = null;

        String querydet = "SELECT nrocuota,capital,dias,amortiza,";
        querydet = querydet + "minteres,vence,monto,ivaxinteres ";
        querydet = querydet + "FROM detalle_proformas ";
        querydet = querydet + " WHERE detalle_proformas.nprestamo =" + this.idControl.getText();
        System.out.println(querydet);
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.jTable1.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        try {
            rsdet = stm.executeQuery(querydet);
            while (rsdet.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[8]; // Hay 8   columnas en la tabla
                fila[0] = rsdet.getInt("nrocuota");
                fila[1] = rsdet.getInt("dias");
                fila[2] = formatoFecha.format(rsdet.getDate("vence"));
                fila[3] = formato.format(rsdet.getInt("capital"));
                fila[4] = formato.format(rsdet.getInt("amortiza"));
                fila[5] = formato.format(rsdet.getInt("minteres"));
                fila[6] = formato.format(rsdet.getInt("ivaxinteres"));
                fila[7] = formato.format(rsdet.getInt("monto"));
                modelo.addRow(fila);// Se añade al modelo la fila completa.
            }
            stm.close();
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            this.jTable1.updateUI();
            int cantFilas = this.jTable1.getRowCount();
            if (cantFilas > 0) {
                this.GuardarOperacion.setEnabled(true);
                this.Salir.setEnabled(true);
            } else {
                this.GuardarOperacion.setEnabled(true);
                this.Salir.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Cargar los Detalles",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
    }

    public void VerGarantes() {
        // Se construye el JFormattedTextField pasándole la máscara
        // Se da un valor inicial válido para evitar problemas
        //Inicializo Variables
        //Consultamos el Detalle  de la Operacion y Mostramos en el JTable
        con = new Conexion();
        stm = con.conectar();
        ResultSet rsgarantes = null;

        String querygarantes = "SELECT codcliente,clientes.nombre ";
        querygarantes = querygarantes + "FROM garantia_aval ";
        querygarantes = querygarantes + "INNER JOIN clientes ";
        querygarantes = querygarantes + "ON clientes.codigo=garantia_aval.codcliente  ";
        querygarantes = querygarantes + " WHERE garantia_aval.idprestamo =" + this.idControl.getText();
        int cantidadRegistro = modelo3.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo3.removeRow(0);
        }
        try {
            rsgarantes = stm.executeQuery(querygarantes);
            while (rsgarantes.next()) {
                //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[2]; // Hay 2   columnas en la tabla
                fila[0] = rsgarantes.getInt("codcliente");
                fila[1] = rsgarantes.getString("nombre");
                modelo3.addRow(fila);// Se añade al modelo la fila completa.
            }
            stm.close();
            this.TablaGarantes.setRowSorter(new TableRowSorter(modelo3));
            this.TablaGarantes.updateUI();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Cargar los Detalles",
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

        this.destinoprestamo.removeAllItems();
        BD.cargarCombo("select codigo,nombre from destino order by codigo", this.destinoprestamo);
        this.destinoprestamo.setSelectedIndex(0);

        this.tipogarantia.removeAllItems();
        BD.cargarCombo("select codigo,nombre from garantias order by codigo", this.tipogarantia);
        this.tipogarantia.setSelectedIndex(0);

        this.tipoprestamo.removeAllItems();
        BD.cargarCombo("select codigo,nombre from comprobantes where tipo=3 order by codigo", this.tipoprestamo);
        this.tipoprestamo.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Garantes = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaGarantes = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        CtaTitular = new javax.swing.JTextField();
        NombreTitular = new javax.swing.JTextField();
        NroPrestamo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        AdItemGarante = new javax.swing.JButton();
        DelItemGarante = new javax.swing.JButton();
        codgarante = new javax.swing.JTextField();
        BuscarGarante = new javax.swing.JButton();
        nombregarante = new javax.swing.JTextField();
        DesembolsoPrestamo = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        Cuenta = new javax.swing.JLabel();
        ctaclientedesembolso = new javax.swing.JTextField();
        nombrecliente = new javax.swing.JTextField();
        nprestamodesembolso = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        formadesembolso = new javax.swing.JTextField();
        nombreformadesembolso = new javax.swing.JTextField();
        bancodesembolso = new javax.swing.JTextField();
        banconombredesembolso = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        nrocheque = new javax.swing.JTextField();
        GrabarDesembolso = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        fechadesembolso = new com.toedter.calendar.JDateChooser();
        netodesembolso = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        buscarfdesembolso = new javax.swing.JButton();
        buscarbdesembolso = new javax.swing.JButton();
        referencias_personales = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        rfpnroprestamo = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        rfpcuenta = new javax.swing.JTextField();
        rfpnombrecuenta = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        GuardarOperacion = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        importecapital = new javax.swing.JFormattedTextField();
        tipoprestamo = new javax.swing.JComboBox();
        destinoprestamo = new javax.swing.JComboBox();
        tipogarantia = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tasa = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        importecuota = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        primer_vencimiento = new com.toedter.calendar.JDateChooser();
        Modo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        plazo = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        creferencia = new javax.swing.JTextField();
        incluiriva = new javax.swing.JTextField();
        entregarneto = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        Cliente = new javax.swing.JTextField();
        NombreCliente = new javax.swing.JTextField();
        Direccion = new javax.swing.JTextField();
        BuscarCliente = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        neto_a_entregar = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        comision = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        gastos_administrativos = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        descuentos = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        montoiva = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        descuento_intereses = new javax.swing.JFormattedTextField();
        idfila = new javax.swing.JTextField();
        vencimientos = new com.toedter.calendar.JDateChooser();
        venceanterior = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        interes = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        totalprestamo = new javax.swing.JFormattedTextField();
        calcularneto = new javax.swing.JButton();
        auxiliar = new javax.swing.JFormattedTextField();
        estado = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        fechaproceso = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        idControl = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        codmoneda = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        asesor = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        sucursal = new javax.swing.JComboBox();

        Garantes.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Garantes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                GarantesFocusGained(evt);
            }
        });
        Garantes.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                GarantesWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        Garantes.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                GarantesWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                GarantesWindowOpened(evt);
            }
        });

        TablaGarantes.setModel(modelo3);
        jScrollPane3.setViewportView(TablaGarantes);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        CtaTitular.setEditable(false);
        CtaTitular.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.CtaTitular.text")); // NOI18N
        CtaTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CtaTitularActionPerformed(evt);
            }
        });

        NombreTitular.setEditable(false);
        NombreTitular.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.NombreTitular.text")); // NOI18N

        NroPrestamo.setEditable(false);
        NroPrestamo.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.NroPrestamo.text")); // NOI18N

        jLabel25.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel25.text")); // NOI18N

        jLabel26.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel26.text")); // NOI18N

        jLabel27.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel27.text")); // NOI18N

        AdItemGarante.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\001_06.png")); // NOI18N
        AdItemGarante.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.AdItemGarante.text")); // NOI18N
        AdItemGarante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AdItemGaranteFocusGained(evt);
            }
        });
        AdItemGarante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdItemGaranteActionPerformed(evt);
            }
        });

        DelItemGarante.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\001_05.png")); // NOI18N
        DelItemGarante.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.DelItemGarante.text")); // NOI18N
        DelItemGarante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelItemGaranteActionPerformed(evt);
            }
        });

        codgarante.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.codgarante.text")); // NOI18N
        codgarante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codgaranteActionPerformed(evt);
            }
        });

        BuscarGarante.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\buscar.png")); // NOI18N
        BuscarGarante.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.BuscarGarante.text")); // NOI18N
        BuscarGarante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarGaranteActionPerformed(evt);
            }
        });

        nombregarante.setEditable(false);
        nombregarante.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.nombregarante.text")); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(NroPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codgarante, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarGarante, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CtaTitular, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(NombreTitular, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(nombregarante)))
                .addGap(42, 42, 42)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DelItemGarante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AdItemGarante, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AdItemGarante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DelItemGarante))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NroPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CtaTitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NombreTitular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombregarante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarGarante, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codgarante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout GarantesLayout = new javax.swing.GroupLayout(Garantes.getContentPane());
        Garantes.getContentPane().setLayout(GarantesLayout);
        GarantesLayout.setHorizontalGroup(
            GarantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        GarantesLayout.setVerticalGroup(
            GarantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        DesembolsoPrestamo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Cuenta.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.Cuenta.text")); // NOI18N

        ctaclientedesembolso.setEditable(false);
        ctaclientedesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.ctaclientedesembolso.text")); // NOI18N

        nombrecliente.setEditable(false);
        nombrecliente.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.nombrecliente.text")); // NOI18N

        nprestamodesembolso.setEditable(false);
        nprestamodesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.nprestamodesembolso.text")); // NOI18N

        jLabel29.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel29.text")); // NOI18N

        jLabel30.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel30.text")); // NOI18N

        formadesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.formadesembolso.text")); // NOI18N
        formadesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formadesembolsoActionPerformed(evt);
            }
        });
        formadesembolso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formadesembolsoKeyPressed(evt);
            }
        });

        nombreformadesembolso.setEditable(false);
        nombreformadesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.nombreformadesembolso.text")); // NOI18N

        bancodesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.bancodesembolso.text")); // NOI18N
        bancodesembolso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bancodesembolsoFocusGained(evt);
            }
        });
        bancodesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bancodesembolsoActionPerformed(evt);
            }
        });
        bancodesembolso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bancodesembolsoKeyPressed(evt);
            }
        });

        banconombredesembolso.setEditable(false);
        banconombredesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.banconombredesembolso.text")); // NOI18N

        jLabel31.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel31.text")); // NOI18N

        jLabel28.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel28.text")); // NOI18N

        nrocheque.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.nrocheque.text")); // NOI18N
        nrocheque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nrochequeFocusGained(evt);
            }
        });
        nrocheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrochequeKeyPressed(evt);
            }
        });

        GrabarDesembolso.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\001_06.png")); // NOI18N
        GrabarDesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.GrabarDesembolso.text")); // NOI18N
        GrabarDesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarDesembolsoActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\001_05.png")); // NOI18N
        jButton2.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel32.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel32.text")); // NOI18N

        netodesembolso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        netodesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.netodesembolso.text")); // NOI18N

        jLabel33.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel33.text")); // NOI18N

        buscarfdesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.buscarfdesembolso.text")); // NOI18N
        buscarfdesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarfdesembolsoActionPerformed(evt);
            }
        });

        buscarbdesembolso.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.buscarbdesembolso.text")); // NOI18N
        buscarbdesembolso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarbdesembolsoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(nprestamodesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30)
                    .addComponent(Cuenta)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ctaclientedesembolso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                    .addComponent(formadesembolso, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bancodesembolso))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buscarfdesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarbdesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(banconombredesembolso)
                                    .addComponent(nombrecliente)
                                    .addComponent(nombreformadesembolso))
                                .addGap(28, 28, 28))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(fechadesembolso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(netodesembolso))
                                    .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(182, 246, Short.MAX_VALUE))))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(GrabarDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nprestamodesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cuenta)
                            .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(nombreformadesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(banconombredesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buscarfdesembolso)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(ctaclientedesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(formadesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bancodesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buscarbdesembolso))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechadesembolso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(netodesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarDesembolso, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DesembolsoPrestamoLayout = new javax.swing.GroupLayout(DesembolsoPrestamo.getContentPane());
        DesembolsoPrestamo.getContentPane().setLayout(DesembolsoPrestamoLayout);
        DesembolsoPrestamoLayout.setHorizontalGroup(
            DesembolsoPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        DesembolsoPrestamoLayout.setVerticalGroup(
            DesembolsoPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesembolsoPrestamoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel36.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel36.text")); // NOI18N

        rfpnroprestamo.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.rfpnroprestamo.text")); // NOI18N

        jLabel37.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel37.text")); // NOI18N

        rfpcuenta.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.rfpcuenta.text")); // NOI18N

        rfpnombrecuenta.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.rfpnombrecuenta.text")); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rfpnroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rfpcuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rfpnombrecuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37)
                        .addComponent(rfpcuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rfpnombrecuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(rfpnroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jButton1.text")); // NOI18N

        jButton9.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jButton9.text")); // NOI18N

        jLabel38.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel38.text")); // NOI18N

        jLabel39.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel39.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jTextField1.text")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jTextField2.text")); // NOI18N

        jLabel40.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel40.text")); // NOI18N

        jTextField3.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jTextField3.text")); // NOI18N

        jLabel41.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel41.text")); // NOI18N

        jTextField4.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jTextField4.text")); // NOI18N

        jTextField5.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jTextField5.text")); // NOI18N

        jLabel42.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel42.text")); // NOI18N

        jLabel43.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel43.text")); // NOI18N

        jTextField6.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jTextField6.text")); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(54, 54, 54)
                .addComponent(jButton9)
                .addGap(40, 40, 40))
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout referencias_personalesLayout = new javax.swing.GroupLayout(referencias_personales.getContentPane());
        referencias_personales.getContentPane().setLayout(referencias_personalesLayout);
        referencias_personalesLayout.setHorizontalGroup(
            referencias_personalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        referencias_personalesLayout.setVerticalGroup(
            referencias_personalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.title")); // NOI18N
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jTable1.setModel(modelo);
        jScrollPane1.setViewportView(jTable1);

        GuardarOperacion.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\aceptar.png")); // NOI18N
        GuardarOperacion.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.GuardarOperacion.text")); // NOI18N
        GuardarOperacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GuardarOperacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                GuardarOperacionFocusGained(evt);
            }
        });
        GuardarOperacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarOperacionActionPerformed(evt);
            }
        });

        Salir.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\salir.png")); // NOI18N
        Salir.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.Salir.text")); // NOI18N
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(886, Short.MAX_VALUE)
                .addComponent(GuardarOperacion, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Salir)
                    .addComponent(GuardarOperacion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(204, 255, 255));

        importecapital.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importecapital.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.importecapital.text")); // NOI18N
        importecapital.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                importecapitalFocusLost(evt);
            }
        });
        importecapital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importecapitalActionPerformed(evt);
            }
        });
        importecapital.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importecapitalKeyPressed(evt);
            }
        });

        tipoprestamo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tipoprestamo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tipoprestamoItemStateChanged(evt);
            }
        });
        tipoprestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoprestamoActionPerformed(evt);
            }
        });
        tipoprestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipoprestamoKeyPressed(evt);
            }
        });

        destinoprestamo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        destinoprestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                destinoprestamoKeyPressed(evt);
            }
        });

        tipogarantia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tipogarantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tipogarantiaKeyPressed(evt);
            }
        });

        jLabel12.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel12.text")); // NOI18N

        jLabel14.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel14.text")); // NOI18N

        jLabel15.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel15.text")); // NOI18N

        jLabel16.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel16.text")); // NOI18N

        tasa.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.tasa.text")); // NOI18N
        tasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tasaKeyPressed(evt);
            }
        });

        jLabel17.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel17.text")); // NOI18N

        jLabel18.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel18.text")); // NOI18N

        importecuota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importecuota.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.importecuota.text")); // NOI18N
        importecuota.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                importecuotaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                importecuotaFocusLost(evt);
            }
        });
        importecuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importecuotaActionPerformed(evt);
            }
        });
        importecuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importecuotaKeyPressed(evt);
            }
        });

        jLabel19.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel19.text")); // NOI18N

        primer_vencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primer_vencimientoKeyPressed(evt);
            }
        });

        Modo.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.Modo.text")); // NOI18N

        jLabel21.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel21.text")); // NOI18N

        plazo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        plazo.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.plazo.text")); // NOI18N
        plazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                plazoFocusLost(evt);
            }
        });
        plazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plazoActionPerformed(evt);
            }
        });
        plazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                plazoKeyPressed(evt);
            }
        });

        jLabel22.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel22.text")); // NOI18N

        creferencia.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.creferencia.text")); // NOI18N
        creferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creferenciaActionPerformed(evt);
            }
        });

        incluiriva.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.incluiriva.text")); // NOI18N

        entregarneto.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.entregarneto.text")); // NOI18N
        entregarneto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entregarnetoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel12))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importecapital, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22))
                    .addComponent(tipogarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(destinoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(primer_vencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entregarneto))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(incluiriva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(destinoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tipogarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importecapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(primer_vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(entregarneto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(jLabel22))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(incluiriva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(204, 255, 255));

        jLabel4.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel4.text")); // NOI18N

        Cliente.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.Cliente.text")); // NOI18N
        Cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClienteActionPerformed(evt);
            }
        });

        NombreCliente.setBackground(new java.awt.Color(204, 204, 255));
        NombreCliente.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.NombreCliente.text")); // NOI18N
        NombreCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        NombreCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NombreCliente.setEnabled(false);

        Direccion.setBackground(new java.awt.Color(204, 204, 255));
        Direccion.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.Direccion.text")); // NOI18N
        Direccion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Direccion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Direccion.setEnabled(false);

        BuscarCliente.setIcon(new javax.swing.ImageIcon("C:\\Sistemas\\EvolutionII\\EvolutionII\\src\\Images\\buscar.png")); // NOI18N
        BuscarCliente.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.BuscarCliente.text")); // NOI18N
        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 0));
        jLabel35.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel35.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addGap(9, 9, 9)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Direccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(281, 281, 281))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel35))
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setForeground(new java.awt.Color(204, 255, 255));

        jLabel11.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel11.text")); // NOI18N

        neto_a_entregar.setEditable(false);
        neto_a_entregar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        neto_a_entregar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        neto_a_entregar.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        neto_a_entregar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel10.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel10.text")); // NOI18N

        comision.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        comision.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        comision.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        comision.setEnabled(false);
        comision.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel9.text")); // NOI18N

        gastos_administrativos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gastos_administrativos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        gastos_administrativos.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.gastos_administrativos.text")); // NOI18N
        gastos_administrativos.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        gastos_administrativos.setEnabled(false);
        gastos_administrativos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel7.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel7.text")); // NOI18N

        descuentos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        descuentos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        descuentos.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.descuentos.text")); // NOI18N
        descuentos.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        descuentos.setEnabled(false);
        descuentos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel8.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel8.text")); // NOI18N

        montoiva.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        montoiva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        montoiva.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.montoiva.text")); // NOI18N
        montoiva.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        montoiva.setEnabled(false);
        montoiva.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel6.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel6.text")); // NOI18N

        descuento_intereses.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        descuento_intereses.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        descuento_intereses.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        descuento_intereses.setEnabled(false);
        descuento_intereses.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        idfila.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.idfila.text")); // NOI18N

        vencimientos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vencimientosKeyPressed(evt);
            }
        });

        venceanterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                venceanteriorKeyPressed(evt);
            }
        });

        jLabel23.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel23.text")); // NOI18N

        interes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        interes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        interes.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        interes.setEnabled(false);
        interes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel24.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel24.text")); // NOI18N

        totalprestamo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        totalprestamo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalprestamo.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        totalprestamo.setEnabled(false);
        totalprestamo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        calcularneto.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.calcularneto.text")); // NOI18N
        calcularneto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularnetoActionPerformed(evt);
            }
        });

        auxiliar.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.auxiliar.text")); // NOI18N

        estado.setEditable(false);
        estado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        estado.setForeground(new java.awt.Color(255, 0, 0));
        estado.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.estado.text")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel10)
                        .addComponent(jLabel7)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(jLabel9)))
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(descuentos, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(montoiva)
                    .addComponent(gastos_administrativos)
                    .addComponent(comision)
                    .addComponent(descuento_intereses))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(idfila, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(auxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel23)
                                .addGap(15, 15, 15)
                                .addComponent(interes, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel24)
                                .addGap(15, 15, 15)
                                .addComponent(totalprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(15, 15, 15)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(calcularneto)
                                    .addComponent(neto_a_entregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(estado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(interes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(auxiliar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(neto_a_entregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(descuentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(idfila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(montoiva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addComponent(vencimientos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(gastos_administrativos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(descuento_intereses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcularneto)
                        .addContainerGap())))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setForeground(new java.awt.Color(204, 255, 255));

        fechaproceso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaprocesoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fechaprocesoFocusLost(evt);
            }
        });
        fechaproceso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaprocesoKeyPressed(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel2.text")); // NOI18N

        jLabel13.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel13.text")); // NOI18N

        idControl.setEditable(false);
        idControl.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.idControl.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel3.text")); // NOI18N

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

        jLabel5.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel5.text")); // NOI18N

        asesor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        asesor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                asesorKeyPressed(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(detalle_proformas.class, "detalle_proformas.jLabel1.text")); // NOI18N

        sucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        sucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucursalActionPerformed(evt);
            }
        });
        sucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sucursalKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(asesor, 0, 103, Short.MAX_VALUE)
                    .addComponent(sucursal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel1)
                            .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(asesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(codmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // AGREGAMOS O MODIFICAMOS AL JTABLE EL REGISTRO CORRESPONDIENTE QUE NOS DEVUELVE
        // EL FORM ITEM_DESCUENTO_CHEQUES
        if (ControlGrabado.REGISTRO_GRABADO == "SI") {
            //     String cadenacontrol = this.idfila.getText();
            //     if (cadenacontrol.isEmpty()) {
            Object[] fila = new Object[18]; // Hay 18   columnas en la tabla
            modelo.addRow(fila);          // Se añade al modelo la fila completa.
            //   } else {
            //              this.jTable1.setValueAt(cheques.demision, cheques.nFilaCheque, 0);
            //              this.jTable1.setValueAt(cheques.dvencimiento, cheques.nFilaCheque, 1);
            //   }
            //       this.idfila.setText("");
            //           cheques.nFilaCheque = 0;
            ControlGrabado.REGISTRO_GRABADO = "NO";
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void codmonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codmonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codmonedaActionPerformed

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "NO";
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaClientes cu = new Vista.ConsultaClientes();
        cu.setVisible(true);
        this.fechaproceso.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void ClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClienteActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre,telefono,direccion  from clientes where codigo=" + this.Cliente.getText());
            if (results.next()) {
                this.NombreCliente.setText(results.getString("nombre").trim());
                this.Direccion.setText(results.getString("direccion").trim());
                this.fechaproceso.requestFocus();
            } else {
                this.BuscarCliente.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ClienteActionPerformed

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void fechaprocesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaprocesoFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.Cliente.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.NombreCliente.setText(Parametros.NOMBRE_ELEGIDO.trim());
            this.Direccion.setText(Parametros.DIRECCION_ELEGIDA.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
            Parametros.DIRECCION_ELEGIDA = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprocesoFocusGained

    private void fechaprocesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaprocesoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.sucursal.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.Cliente.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprocesoKeyPressed

    private void sucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.codmoneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void codmonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codmonedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.asesor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codmonedaKeyPressed

    private void asesorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_asesorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tipoprestamo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codmoneda.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorKeyPressed

    private void tipoprestamoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipoprestamoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.destinoprestamo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.asesor.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoprestamoKeyPressed

    private void destinoprestamoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_destinoprestamoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tipogarantia.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipoprestamo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_destinoprestamoKeyPressed

    private void tipogarantiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tipogarantiaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importecapital.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.destinoprestamo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tipogarantiaKeyPressed

    private void importecapitalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importecapitalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.plazo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipogarantia.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importecapitalKeyPressed

    private void plazoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plazoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.primer_vencimiento.requestFocus();
            calculartasa calculartasa = new calculartasa();
            Thread hilo1 = new Thread(calculartasa);
            hilo1.start();

           // calculargastos calculargastos = new calculargastos();
           // Thread hilo2 = new Thread(calculargastos);
          //  hilo2.start();

            inclusioniva inclusioniva = new inclusioniva();
            Thread hilo3 = new Thread(inclusioniva);
            hilo3.start();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importecapital.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoKeyPressed

    private void tasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tasaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
            calendar.setTime(this.fechaproceso.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
            calendar.add(Calendar.DAY_OF_YEAR, 30);  // numero de días a añadir, o restar en caso de días<0
            this.primer_vencimiento.setDate(calendar.getTime()); //Y cargamos finalmente en el 
            this.primer_vencimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
            calendar.setTime(this.fechaproceso.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
            calendar.add(Calendar.DAY_OF_YEAR, 30);  // numero de días a añadir, o restar en caso de días<0
            this.primer_vencimiento.setDate(calendar.getTime()); //Y cargamos finalmente en el 
            this.plazo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaKeyPressed

    private void primer_vencimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primer_vencimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importecuota.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tasa.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_primer_vencimientoKeyPressed

    private void importecuotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importecuotaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.primer_vencimiento.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importecuotaKeyPressed

    private void fechaprocesoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaprocesoFocusLost

        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.fechaproceso.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        calendar.add(Calendar.DAY_OF_YEAR, 30);  // numero de días a añadir, o restar en caso de días<0
        this.primer_vencimiento.setDate(calendar.getTime()); //Y cargamos finalmente en el 
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprocesoFocusLost

    private void importecuotaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importecuotaFocusGained

        //Calculamos la Cuota//usamos Sistema Frances
        String cCapital = String.valueOf(this.importecapital.getText());
        cCapital = cCapital.replace(".", "");
        cCapital = cCapital.replace(",", ".");
        double Monto = Double.parseDouble(String.valueOf(cCapital));

        String cTasa = this.tasa.getText();
        cTasa = cTasa.replace(".", "");
        cTasa = cTasa.replace(",", ".");

        int Plazos = Integer.parseInt(this.plazo.getText());
        double tasa = Double.parseDouble(String.valueOf(cTasa));
        double taza = tasa / 12 / 100;

        //A = 1-(1+taza)^-plazos
        int p = Plazos * -1;
        double b = (1 + taza);

        //A = 1-(1+taza)^-plazos
        double A = (1 - Math.pow(b, p)) / taza;

        //Cuota Fija = Monto / A;
        //Necesitamos capturar el tipo de Moneda para poder 
        Object objMoneda = this.codmoneda.getSelectedItem();
        String cMoneda = ((String[]) objMoneda)[0];

        System.out.println(this.codmoneda.getSelectedItem());
        double Cuota_F = 0.00;
        if (cMoneda.equals("1")) {
            Cuota_F = Math.round(Monto / A);
        } else {
            Cuota_F = Monto / A;
        }
        this.importecuota.setText(formato.format(Cuota_F));
        // TODO add your handling code here:

    }//GEN-LAST:event_importecuotaFocusGained

    private void vencimientosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vencimientosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_vencimientosKeyPressed

    private void venceanteriorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_venceanteriorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_venceanteriorKeyPressed

    private void plazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plazoActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_plazoActionPerformed

    private void importecapitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importecapitalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_importecapitalActionPerformed

    private void creferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creferenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creferenciaActionPerformed

    private void tipoprestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoprestamoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoprestamoActionPerformed

    private void tipoprestamoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tipoprestamoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoprestamoItemStateChanged

    private void importecuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importecuotaActionPerformed
        if (Integer.parseInt(this.idControl.getText()) == 0) {
            grabarcabecera grabarcabecera = new grabarcabecera();
            Thread proceso1 = new Thread(grabarcabecera);
            proceso1.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importecuotaActionPerformed

    private void CtaTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CtaTitularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CtaTitularActionPerformed

    private void codgaranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codgaranteActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre,telefono,direccion  from clientes where codigo=" + this.Cliente.getText());
            if (results.next()) {
                this.nombregarante.setText(results.getString("nombre").trim());
            } else {
                this.BuscarGarante.doClick();
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println(ex);
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_codgaranteActionPerformed

    private void BuscarGaranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarGaranteActionPerformed
        ControlGrabado.REGISTRO_GRABADO = "NO";
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaClientes2 cu = new Vista.ConsultaClientes2();
        cu.setVisible(true);
        this.AdItemGarante.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarGaranteActionPerformed

    private void AdItemGaranteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AdItemGaranteFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.codgarante.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombregarante.setText(Parametros.NOMBRE_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AdItemGaranteFocusGained

    private void AdItemGaranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdItemGaranteActionPerformed
        Object[] fila = new Object[2];
        BDConexion BD = new BDConexion();
        BD.insertarRegistro("garantia_aval", "idprestamo,codcliente", "'" + this.NroPrestamo.getText() + "','" + this.codgarante.getText() + "'");
        fila[0] = this.codgarante.getText().toString();
        fila[1] = this.nombregarante.getText().toString();
        modelo3.addRow(fila);
        this.LimpiarItem();
        this.codgarante.requestFocus();
        // TODO add your handling code here:
    }

    private void LimpiarItem() {
        this.codgarante.setText("");
        this.nombregarante.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AdItemGaranteActionPerformed

    private void DelItemGaranteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelItemGaranteActionPerformed
        int a = this.TablaGarantes.getSelectedRow();
        if (a < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                con = new Conexion();
                stm = con.conectar();
                String cQueryDeleteGarante = "DELETE FROM garantia_aval WHERE idprestamo=";
                cQueryDeleteGarante += this.NroPrestamo.getText() + " AND codcliente=";
                cQueryDeleteGarante += this.TablaGarantes.getValueAt(a, 0).toString();
                System.out.println(cQueryDeleteGarante);
                try {
                    stm.executeUpdate(cQueryDeleteGarante);
                    modelo3.removeRow(a);
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }         // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_DelItemGaranteActionPerformed

    private void GarantesWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_GarantesWindowGainedFocus
        this.VerGarantes();

        // TODO add your handling code here:
    }//GEN-LAST:event_GarantesWindowGainedFocus

    private void GarantesWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_GarantesWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_GarantesWindowActivated

    private void GarantesWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_GarantesWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_GarantesWindowOpened

    private void GarantesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_GarantesFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_GarantesFocusGained

    private void formadesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formadesembolsoActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre from formaspago where codigo=" + this.formadesembolso.getText());
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
    }//GEN-LAST:event_formadesembolsoActionPerformed

    private void bancodesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bancodesembolsoActionPerformed
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select codigo,nombre from bancos where estado=1 and codigo=" + this.bancodesembolso.getText());
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
    }//GEN-LAST:event_bancodesembolsoActionPerformed

    private void buscarfdesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarfdesembolsoActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("formaspago");
        cu.setVisible(true);
        this.bancodesembolso.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarfdesembolsoActionPerformed

    private void bancodesembolsoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bancodesembolsoFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.formadesembolso.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.nombrecliente.setText(Parametros.NOMBRE_ELEGIDO);
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }        // TODO add your handling code here:
    }//GEN-LAST:event_bancodesembolsoFocusGained

    private void formadesembolsoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formadesembolsoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.bancodesembolso.requestFocus();
        }
    }//GEN-LAST:event_formadesembolsoKeyPressed

    private void bancodesembolsoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bancodesembolsoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nrocheque.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.formadesembolso.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_bancodesembolsoKeyPressed

    private void nrochequeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrochequeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechadesembolso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocheque.requestFocus();
        }        // TODO add your handling code here        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeKeyPressed

    private void buscarbdesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarbdesembolsoActionPerformed
        Parametros.CODIGO_ELEGIDO = 0;
        Parametros.NOMBRE_ELEGIDO = "";
        Vista.ConsultaTablas cu = new Vista.ConsultaTablas("bancos");
        cu.setVisible(true);
        this.nrocheque.requestFocus();             // TODO add your handling code here:
    }//GEN-LAST:event_buscarbdesembolsoActionPerformed

    private void nrochequeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nrochequeFocusGained
        if (Parametros.CODIGO_ELEGIDO != 0) {
            //Date dato = formatoFecha.parse(textoFecha);
            this.bancodesembolso.setText(Integer.toString(Parametros.CODIGO_ELEGIDO));
            this.banconombredesembolso.setText(Parametros.NOMBRE_ELEGIDO.trim());
            Parametros.CODIGO_ELEGIDO = 0;
            Parametros.NOMBRE_ELEGIDO = "";
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.DesembolsoPrestamo.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void GrabarDesembolsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarDesembolsoActionPerformed

        Date dFechaConfirmacion = ODate.de_java_a_sql(fechadesembolso.getDate());
        //Obteniendo Datos de los Combodatos
        Object objMoneda = codmoneda.getSelectedItem();
        String cCodigoMoneda = ((String[]) objMoneda)[0];

        String cNetoDesembolso = this.netodesembolso.getText();
        cNetoDesembolso = cNetoDesembolso.replace(".", "");
        cNetoDesembolso = cNetoDesembolso.replace(",", ".");
        double NetoDesembolso = Double.parseDouble(cNetoDesembolso);
        Object[] fila2 = new Object[6]; // Hay 8   columnas en la tabla
        BDConexion BD = new BDConexion();
        BD.insertarRegistro("detalle_forma_pago", "idmovimiento,forma,codmoneda,banco,nrocheque,confirmacion,netocobrado", "'" + this.creferencia.getText() + "','" + this.formadesembolso.getText() + "','" + cCodigoMoneda + "','" + this.bancodesembolso.getText() + "','" + this.nrocheque.getText() + "','" + dFechaConfirmacion + "','" + cNetoDesembolso + "'");

        fila2[0] = this.formadesembolso.getText();
        fila2[1] = this.nombreformadesembolso.getText();
        fila2[2] = this.bancodesembolso.getText();
        fila2[3] = this.banconombredesembolso.getText();
        fila2[4] = formatoFecha.format(dFechaConfirmacion);
        fila2[5] = formato.format(NetoDesembolso);
        modelo2.addRow(fila2);
        // EN ESTE HILO GUARDAMOS LA EXTRACCION DEL BANCO
        DesembolsoPrestamo.setVisible(false);
        grabarbanco grabarbanco = new grabarbanco();
        Thread HiloBanco = new Thread(grabarbanco);
        HiloBanco.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarDesembolsoActionPerformed

    private void calcularnetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularnetoActionPerformed
        calculardiferencia diferencia = new calculardiferencia();
        Thread hilodiferencia = new Thread(diferencia);
        hilodiferencia.start();
    }//GEN-LAST:event_calcularnetoActionPerformed

    private void importecapitalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importecapitalFocusLost
      //  calculargastos calculargastos = new calculargastos();
      //  Thread hilo2 = new Thread(calculargastos);
      //  hilo2.start();

        inclusioniva inclusioniva = new inclusioniva();
        Thread hilo3 = new Thread(inclusioniva);
        hilo3.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_importecapitalFocusLost

    private void plazoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_plazoFocusLost

        // TODO add your handling code here:
    }//GEN-LAST:event_plazoFocusLost

    private void importecuotaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importecuotaFocusLost
        this.generarcuotas();
        // TODO add your handling code here:
    }//GEN-LAST:event_importecuotaFocusLost

    private void entregarnetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entregarnetoActionPerformed
        if (this.entregarneto.isSelected()) { // Si hemos dado clic en el jCheckBox
            String cCapital = this.importecapital.getText();
            cCapital = cCapital.replace(".", "");
            cCapital = cCapital.replace(",", ".");
            this.auxiliar.setText(formato.format(Double.parseDouble(cCapital)));
            this.calcularneto.doClick();
        }
// TODO add your handling code here:
    }//GEN-LAST:event_entregarnetoActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void GuardarOperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarOperacionActionPerformed
        if (this.Cliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cuenta del Cliente");
            this.Cliente.requestFocus();
            return;
        }

        ControlGrabado.REGISTRO_GRABADO = "SI";
        String Operacion = this.idControl.getText();
        //SE CAPTURA LOS DATOS DE LA CABECERA
        //Dando formato a los datos tipo Fecha
        Date FechaProceso = ODate.de_java_a_sql(fechaproceso.getDate());
        Date PrimerVence = ODate.de_java_a_sql(primer_vencimiento.getDate());
        //Obteniendo Datos de los Combodatos
        Object objPrestamo = tipoprestamo.getSelectedItem();
        Object objDestino = destinoprestamo.getSelectedItem();
        Object objGarantia = tipogarantia.getSelectedItem();
        Object objSucursal = sucursal.getSelectedItem();
        Object objAsesor = asesor.getSelectedItem();
        Object objMoneda = codmoneda.getSelectedItem();

        Tipoprestamo = ((String[]) objPrestamo)[0];
        Destinoprestamo = ((String[]) objDestino)[0];
        Garantiaprestamo = ((String[]) objGarantia)[0];
        Sucursal = ((String[]) objSucursal)[0];
        Asesor = ((String[]) objAsesor)[0];
        Moneda = ((String[]) objMoneda)[0];

        //SE CAPTURAN LOS DATOS NUMERICOS
        //     String cImporte = this.importe.getText();
        String cNetoEntregar = neto_a_entregar.getText();
        cNetoEntregar = cNetoEntregar.replace(".", "");
        cNetoEntregar = cNetoEntregar.replace(",", ".");

        String cDescuentos = descuentos.getText();
        if (cDescuentos.trim().length() > 0) {
            cDescuentos = cDescuentos.replace(".", "");
            cDescuentos = cDescuentos.replace(",", ".");
        } else {
            cDescuentos = "0";
        }

        String cCapital = importecapital.getText();
        cCapital = cCapital.replace(".", "");
        cCapital = cCapital.replace(",", ".");

        String cCuota = importecuota.getText();
        cCuota = cCuota.replace(".", "");
        cCuota = cCuota.replace(",", ".");

        String cTasa = tasa.getText();
        cTasa = cTasa.replace(".", "");
        cTasa = cTasa.replace(",", ".");

        String cGastos = gastos_administrativos.getText();
        cGastos = cGastos.replace(".", "");
        cGastos = cGastos.replace(",", ".");

        String cInteres = interes.getText();
        cInteres = cInteres.replace(".", "");
        cInteres = cInteres.replace(",", ".");

        String cMontoIva = montoiva.getText();
        if (cMontoIva.trim().length() > 0) {
            cMontoIva = cMontoIva.replace(".", "");
            cMontoIva = cMontoIva.replace(",", ".");
        } else {
            cMontoIva = "0";
        }

        String cTotal = totalprestamo.getText();
        cTotal = cTotal.replace(".", "");
        cTotal = cTotal.replace(",", ".");
        String cEtiquetaMoneda = "PYG";
        String cEstado = "PE";
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            try {
                Connection conn = stm.getConnection();
                conn.setAutoCommit(false);
                try {
                    String cSqlCab = "UPDATE proformas  SET fecha='" + FechaProceso + "',moneda='" + Moneda;
                    cSqlCab += "',sucursal='" + Sucursal + "',oficial='" + Asesor;
                    cSqlCab += "',socio='" + this.Cliente.getText() + "',monto_entregar='" + cNetoEntregar + "',plazo='" + this.plazo.getText();
                    cSqlCab += "',deducciones='" + cDescuentos + "',importe='" + cCapital + "',monto_cuota='" + cCuota;
                    cSqlCab += "',primer_vence='" + PrimerVence + "',tipo='" + Tipoprestamo + "',tasa='" + cTasa + "',interes='" + cInteres;
                    cSqlCab += "',ivainteres='" + cMontoIva + "',totalamortizacion='" + cCapital + "',totalprestamo='" + cTotal + "',comision_deudor='" + cGastos;
                    cSqlCab += "',asesor='" + Asesor + "',codusuario='" + Config.CodUsuario;
                    cSqlCab += "' WHERE numero=" + this.idControl.getText();
                    System.out.println(cSqlCab);
                    stm.executeUpdate(cSqlCab);

                    stm.executeUpdate("DELETE FROM detalle_proformas WHERE nprestamo=" + this.idControl.getText());

                    int Items = jTable1.getRowCount();
                    for (int i = 0; i < Items; i++) {
                        String cNrocuota = (jTable1.getValueAt(i, 0).toString());
                        String cNroDias = (jTable1.getValueAt(i, 1).toString());
                        Date dVence = ODate.de_java_a_sql(formatoFecha.parse(jTable1.getValueAt(i, 2).toString()));
                        cCapital = (jTable1.getValueAt(i, 3).toString());
                        String cAmortiza = (jTable1.getValueAt(i, 4).toString());
                        cInteres = (jTable1.getValueAt(i, 5).toString());
                        cMontoIva = (jTable1.getValueAt(i, 6).toString());
                        cCuota = (jTable1.getValueAt(i, 7).toString());

                        cCapital = cCapital.replace(".", "");
                        cCapital = cCapital.replace(",", ".");

                        cAmortiza = cAmortiza.replace(".", "");
                        cAmortiza = cAmortiza.replace(",", ".");

                        cInteres = cInteres.replace(".", "");
                        cInteres = cInteres.replace(",", ".");

                        if (cMontoIva.trim().length() > 0) {
                            cMontoIva = cMontoIva.replace(".", "");
                            cMontoIva = cMontoIva.replace(",", ".");
                        } else {
                            cMontoIva = "0";
                        }

                        cCuota = cCuota.replace(".", "");
                        cCuota = cCuota.replace(",", ".");
                        // SE GRABAN LOS DETALLES
                        String cSqlDet = "INSERT INTO detalle_proformas (nprestamo,nrocuota,dias,capital,amortiza,minteres,vence,monto,ivaxinteres)";
                        cSqlDet += "VALUES ('" + idControl.getText() + "','" + cNrocuota + "','" + cNroDias + "','";
                        cSqlDet += cCapital + "','" + cAmortiza + "','" + cInteres + "','" + dVence + "','" + cCuota + "','" + cMontoIva + "')";
                        stm.executeUpdate(cSqlDet);
                    }
                    //PRIMERO EL GRABAMOS EL DETALLE DE CHEQUES EN
                    //LA TABLA detalle_descuento_documentos
                    conn.commit();
                    stm.close();
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                    conn.rollback();
                    stm.close();
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.dispose();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GuardarOperacionActionPerformed

    private void GuardarOperacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_GuardarOperacionFocusGained

    }//GEN-LAST:event_GuardarOperacionFocusGained
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
                cGastosA = cGastosA.replace(",", ".");
            } else {
                cGastosA = "0";
            }

            //CREAMOS EL ID UNICO PARA EL DOCUMENTO
            String iddocumento = UUID.crearUUID();
            iddocumento = iddocumento.substring(1, 25);

            //PRIMERO EL GRABAMOS EL DETALLE DE CHEQUES EN 
            //LA TABLA detalle_descuento_documentos
            InsertarRegistroDetalle("detalle_descuento_documentos", "ndescuento,emision,nrodocumento,comprobante,cargobanco,monto_documento,librador,vencimiento,tasa,plazo,compra,descuento,valor_actual,acreedor,nrocuentacte,gastos", "'" + this.idControl.getText() + "','" + dEmision + "','" + cNrodocumento + "','" + cComprobante + "','" + cCargobanco + "','" + cMonto + "','" + cLibrador + "','" + dVence + "','" + cTasa + "','" + cPlazo + "','" + dCompra + "','" + cDescuentoItem + "','" + cValor_actual + "','" + cAcreedor + "','" + cNrocuentacte + "','" + cGastosA + "'");
            //LUEGO ACTUALIZAMOS LA TABLA DE DEUDAS DE CLIENTES
            //EN LA TABLA cuenta_clientes
            InsertarRegistroDetalle("cuenta_clientes", "iddocumento,creferencia,documento,fecha,vencimiento,cliente,sucursal,moneda,comprobante,importe,numerocuota,cuota,saldo,inversionista,tasaoperativa", "'" + iddocumento + "','" + this.creferencia.getText() + "','" + cNrodocumento + "','" + dEmision + "','" + dVence + "','" + this.Cliente.getText() + "','" + Sucursal + "','" + Moneda + "','" + cComprobante + "','" + cMonto + "','" + cCuota + "','" + cCuota + "','" + cMonto + "','" + cAcreedor + "','" + cTasa + "'");
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
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new detalle_descuento_cheques().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AdItemGarante;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton BuscarGarante;
    private javax.swing.JTextField Cliente;
    private javax.swing.JTextField CtaTitular;
    private javax.swing.JLabel Cuenta;
    private javax.swing.JButton DelItemGarante;
    private javax.swing.JDialog DesembolsoPrestamo;
    private javax.swing.JTextField Direccion;
    private javax.swing.JDialog Garantes;
    private javax.swing.JButton GrabarDesembolso;
    private javax.swing.JButton GuardarOperacion;
    private javax.swing.JTextField Modo;
    private javax.swing.JTextField NombreCliente;
    private javax.swing.JTextField NombreTitular;
    private javax.swing.JTextField NroPrestamo;
    private javax.swing.JButton Salir;
    private javax.swing.JTable TablaGarantes;
    private javax.swing.JComboBox asesor;
    private javax.swing.JFormattedTextField auxiliar;
    private javax.swing.JTextField bancodesembolso;
    private javax.swing.JTextField banconombredesembolso;
    private javax.swing.JButton buscarbdesembolso;
    private javax.swing.JButton buscarfdesembolso;
    private javax.swing.JButton calcularneto;
    private javax.swing.JTextField codgarante;
    private javax.swing.JComboBox codmoneda;
    private javax.swing.JFormattedTextField comision;
    private javax.swing.JTextField creferencia;
    private javax.swing.JTextField ctaclientedesembolso;
    private javax.swing.JFormattedTextField descuento_intereses;
    private javax.swing.JFormattedTextField descuentos;
    private javax.swing.JComboBox destinoprestamo;
    private javax.swing.JCheckBox entregarneto;
    private javax.swing.JTextField estado;
    private com.toedter.calendar.JDateChooser fechadesembolso;
    private com.toedter.calendar.JDateChooser fechaproceso;
    private javax.swing.JTextField formadesembolso;
    private javax.swing.JFormattedTextField gastos_administrativos;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idfila;
    private javax.swing.JFormattedTextField importecapital;
    private javax.swing.JFormattedTextField importecuota;
    private javax.swing.JTextField incluiriva;
    private javax.swing.JFormattedTextField interes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JFormattedTextField montoiva;
    private javax.swing.JFormattedTextField neto_a_entregar;
    private javax.swing.JFormattedTextField netodesembolso;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreformadesembolso;
    private javax.swing.JTextField nombregarante;
    private javax.swing.JTextField nprestamodesembolso;
    private javax.swing.JTextField nrocheque;
    private javax.swing.JFormattedTextField plazo;
    private com.toedter.calendar.JDateChooser primer_vencimiento;
    private javax.swing.JDialog referencias_personales;
    private javax.swing.JTextField rfpcuenta;
    private javax.swing.JTextField rfpnombrecuenta;
    private javax.swing.JTextField rfpnroprestamo;
    private javax.swing.JComboBox sucursal;
    private javax.swing.JFormattedTextField tasa;
    private javax.swing.JComboBox tipogarantia;
    private javax.swing.JComboBox tipoprestamo;
    private javax.swing.JFormattedTextField totalprestamo;
    private com.toedter.calendar.JDateChooser venceanterior;
    private com.toedter.calendar.JDateChooser vencimientos;
    // End of variables declaration//GEN-END:variables

    private class calculardiferencia extends Thread {

        public void run() {
            String cNetoActual = neto_a_entregar.getText();
            cNetoActual = cNetoActual.replace(".", "");
            cNetoActual = cNetoActual.replace(",", ".");
            double nNetoActual = Double.parseDouble(cNetoActual);

            String cNetoEntregar = auxiliar.getText();
            cNetoEntregar = cNetoEntregar.replace(".", "");
            cNetoEntregar = cNetoEntregar.replace(",", ".");

            double nNetoEntregar = Double.parseDouble(cNetoEntregar);
            double nAcumulador = nNetoEntregar;
            double nResto = 0.00;
            nNetoEntregar = Double.parseDouble(cNetoEntregar);
            while (nNetoActual <= nNetoEntregar) {
                cNetoActual = neto_a_entregar.getText();
                cNetoActual = cNetoActual.replace(".", "");
                cNetoActual = cNetoActual.replace(",", ".");
                nNetoActual = Double.parseDouble(cNetoActual);

                nResto = nNetoEntregar - nNetoActual;
                if (nResto > 100) {
                    nAcumulador = nAcumulador + nResto;
                } else {
                    nAcumulador = nAcumulador + 1;
                }
                importecapital.setText(formato.format(nAcumulador));
                ////////////////////////////////////////
                String cCapital = String.valueOf(importecapital.getText());
                cCapital = cCapital.replace(".", "");
                cCapital = cCapital.replace(",", ".");
                double Monto = Double.parseDouble(String.valueOf(cCapital));

                String cTasa = tasa.getText();
                cTasa = cTasa.replace(".", "");
                cTasa = cTasa.replace(",", ".");

                int Plazos = Integer.parseInt(plazo.getText());
                double tasa = Double.parseDouble(String.valueOf(cTasa));
                double taza = tasa / 12 / 100;

                //A = 1-(1+taza)^-plazos
                int p = Plazos * -1;
                double b = (1 + taza);

                //A = 1-(1+taza)^-plazos
                double A = (1 - Math.pow(b, p)) / taza;

                //Cuota Fija = Monto / A;
                //Necesitamos capturar el tipo de Moneda para poder 
                Object objMoneda = codmoneda.getSelectedItem();
                String cMoneda = ((String[]) objMoneda)[0];

                double Cuota_F = 0.00;
                if (cMoneda.equals("1")) {
                    Cuota_F = Math.round(Monto / A);
                } else {
                    Cuota_F = Monto / A;
                }
                importecuota.setText(formato.format(Cuota_F));
                generarcuotas();
                //update(getGraphics());
            }
            nResto = nNetoActual - nNetoEntregar;
            if (nResto > 0) {
                nAcumulador = nAcumulador - (nResto + 1);
                importecapital.setText(formato.format(nAcumulador));
                neto_a_entregar.setText(formato.format(nNetoEntregar));
            }
        }
    }

    private class calculartasa extends Thread {

        public void run() {
            Object objTipo = tipoprestamo.getSelectedItem();
            String cTipo = ((String[]) objTipo)[0];

            Object objMoneda = codmoneda.getSelectedItem();
            String cMoneda = ((String[]) objMoneda)[0];

            String cPlazo = plazo.getText();
            cPlazo = cPlazo.replace(".", "");
            cPlazo = cPlazo.replace(",", ".");

            double nPlazo = Double.parseDouble(cPlazo);//PLAZO

            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT plazof,porcentaje FROM config_premios WHERE comprobante=" + cTipo + " and moneda=" + cMoneda);
                while (results.next()) {
                    String cPl = results.getString("plazof");
                    Double nPl = Double.parseDouble(cPl);
                    if (nPlazo <= nPl) {
                        tasa.setText(formato.format(results.getDouble("porcentaje")));
                        break;
                    }
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }
        }

    }

    private class calculargastos extends Thread {

        public void run() {
            Object objTipo = tipoprestamo.getSelectedItem();
            String cTipo = ((String[]) objTipo)[0];

            Object objMoneda = codmoneda.getSelectedItem();
            String cMoneda = ((String[]) objMoneda)[0];

            String cImporteCapital = importecapital.getText();
            cImporteCapital = cImporteCapital.replace(".", "");
            cImporteCapital = cImporteCapital.replace(",", ".");

            int nPlazoP = Integer.parseInt(plazo.getText());
            double nImporteCapital = Double.parseDouble(cImporteCapital);//MONTO O CAPITAL SOLICITADO
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT importei,importef,importegastos,18meses,24meses,30meses,36meses FROM config_gastos WHERE comprobante=" + cTipo + " and moneda=" + cMoneda + " and importei<=" + nImporteCapital);

                while (results.next()) {
                    //  String cImpCap = results.getString("importef");
                    //  Double nImpCap = Double.parseDouble(cImpCap);
                    if (results.last()) {
                        if (nPlazoP <= 12) {
                            gastos_administrativos.setText(formato.format(results.getInt("importegastos")));
                        } else if (nPlazoP > 12 && nPlazoP <= 18) {
                            gastos_administrativos.setText(formato.format(results.getInt("18meses")));
                        } else if (nPlazoP > 18 && nPlazoP <= 24) {
                            gastos_administrativos.setText(formato.format(results.getInt("24meses")));
                        } else if (nPlazoP > 24 && nPlazoP <= 30) {
                            gastos_administrativos.setText(formato.format(results.getInt("30meses")));
                        } else if (nPlazoP > 35) {
                            gastos_administrativos.setText(formato.format(results.getInt("36meses")));
                        }
                    }
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }

        }
    }

    private class inclusioniva extends Thread {

        public void run() {
            Object objTipo = tipoprestamo.getSelectedItem();
            String cTipo = ((String[]) objTipo)[0];
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            try {
                results = stm.executeQuery("SELECT incluiriva from comprobantes where codigo=" + cTipo);
                while (results.next()) {
                    incluiriva.setText(results.getString("incluiriva"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println(ex);
            }
        }
    }

    private class grabarbanco extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            String cSqlBorrarBanco = "DELETE FROM extracciones WHERE idmovimiento='" + creferencia.getText() + "'";
            String cSqlAgregarBanco = " INSERT INTO extracciones (idmovimiento,documento,fecha,sucursal,";
            cSqlAgregarBanco += "banco,moneda,observaciones,chequenro,vencimiento,importe,cierre,proveedor)";
            cSqlAgregarBanco += " SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.sucursal,";
            cSqlAgregarBanco += "detalle_forma_pago.banco,detalle_forma_pago.codmoneda,'PAGO DE PRESTAMOS',";
            cSqlAgregarBanco += "detalle_forma_pago.nrocheque,detalle_forma_pago.confirmacion,detalle_forma_pago.netocobrado,1 AS cierre,1 ";
            cSqlAgregarBanco += "FROM prestamos ";
            cSqlAgregarBanco += "INNER JOIN detalle_forma_pago ";
            cSqlAgregarBanco += "ON prestamos.idprestamos=detalle_forma_pago.idmovimiento ";
            cSqlAgregarBanco += "WHERE prestamos.idprestamos='" + creferencia.getText() + "'";
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

    private class grabarcabecera extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha
            Date FechaProceso = ODate.de_java_a_sql(fechaproceso.getDate());
            Date PrimerVence = ODate.de_java_a_sql(primer_vencimiento.getDate());
            //Obteniendo Datos de los Combodatos
            Object objPrestamo = tipoprestamo.getSelectedItem();
            Object objDestino = destinoprestamo.getSelectedItem();
            Object objGarantia = tipogarantia.getSelectedItem();
            Object objSucursal = sucursal.getSelectedItem();
            Object objAsesor = asesor.getSelectedItem();
            Object objMoneda = codmoneda.getSelectedItem();

            Tipoprestamo = ((String[]) objPrestamo)[0];
            Destinoprestamo = ((String[]) objDestino)[0];
            Garantiaprestamo = ((String[]) objGarantia)[0];
            Sucursal = ((String[]) objSucursal)[0];
            Asesor = ((String[]) objAsesor)[0];
            Moneda = ((String[]) objMoneda)[0];

            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();
            String cNetoEntregar = neto_a_entregar.getText();
            cNetoEntregar = cNetoEntregar.replace(".", "");
            cNetoEntregar = cNetoEntregar.replace(",", ".");

            String cDescuentos = descuentos.getText();
            if (cDescuentos.trim().length() > 0) {
                cDescuentos = cDescuentos.replace(".", "");
                cDescuentos = cDescuentos.replace(",", ".");
            } else {
                cDescuentos = "0";
            }

            String cCapital = importecapital.getText();
            cCapital = cCapital.replace(".", "");
            cCapital = cCapital.replace(",", ".");

            String cCuota = importecuota.getText();
            cCuota = cCuota.replace(".", "");
            cCuota = cCuota.replace(",", ".");

            String cTasa = tasa.getText();
            cTasa = cTasa.replace(".", "");
            cTasa = cTasa.replace(",", ".");

            String cInteres = interes.getText();
            cInteres = cInteres.replace(".", "");
            cInteres = cInteres.replace(",", ".");

            String cMontoIva = montoiva.getText();
            if (cMontoIva.trim().length() > 0) {
                cMontoIva = cMontoIva.replace(".", "");
                cMontoIva = cMontoIva.replace(",", ".");
            } else {
                cMontoIva = "0";
            }

            String cGastos = gastos_administrativos.getText();
            cGastos = cGastos.replace(".", "");
            cGastos = cGastos.replace(",", ".");

            String cTotal = totalprestamo.getText();
            cTotal = cTotal.replace(".", "");
            cTotal = cTotal.replace(",", ".");

            UUID id = new UUID();
            String idunico = UUID.crearUUID();
            idunico = idunico.substring(1, 25);
            creferencia.setText(idunico);
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            String cSql = "SELECT MAX(numero)+1 AS nRegistro FROM proformas";
            try {
                results = stm.executeQuery(cSql);
                if (results.next()) {
                    idControl.setText(results.getString("nRegistro"));
                    if (idControl.getText().isEmpty()) {
                        idControl.setText("1");
                    }
                }

                String cSqlCab = "INSERT INTO proformas (idprestamos,numero,fecha,moneda,sucursal,oficial,socio,";
                cSqlCab += "monto_entregar,plazo,deducciones,comision_deudor,importe,monto_cuota,primer_vence,tipo,tasa,interes,ivainteres,";
                cSqlCab += "totalamortizacion,totalprestamo,asesor,codusuario)";
                cSqlCab += "VALUES ('" + creferencia.getText() + "','" + idControl.getText() + "','" + FechaProceso + "','" + Moneda + "','" + Sucursal + "','";
                cSqlCab += Asesor + "','" + Cliente.getText() + "','" + cNetoEntregar + "','" + plazo.getText() + "','" + cDescuentos + "','" + cGastos + "','";
                cSqlCab += cCapital + "','" + cCuota + "','" + PrimerVence + "','" + Tipoprestamo + "','" + cTasa + "','" + cInteres + "','";
                cSqlCab += cMontoIva + "','" + cCapital + "','" + cTotal + "','" + Asesor + "','" + Config.CodUsuario + "')";
                System.out.println(cSqlCab);
                stm.executeUpdate(cSqlCab);

                int Items = jTable1.getRowCount();
                for (int i = 0; i < Items; i++) {
                    String cNrocuota = (jTable1.getValueAt(i, 0).toString());
                    String cNroDias = (jTable1.getValueAt(i, 1).toString());
                    Date dVence = ODate.de_java_a_sql(formatoFecha.parse(jTable1.getValueAt(i, 2).toString()));
                    cCapital = (jTable1.getValueAt(i, 3).toString());
                    String cAmortiza = (jTable1.getValueAt(i, 4).toString());
                    cInteres = (jTable1.getValueAt(i, 5).toString());
                    cMontoIva = (jTable1.getValueAt(i, 6).toString());
                    cCuota = (jTable1.getValueAt(i, 7).toString());

                    cCapital = cCapital.replace(".", "");
                    cCapital = cCapital.replace(",", ".");

                    cAmortiza = cAmortiza.replace(".", "");
                    cAmortiza = cAmortiza.replace(",", ".");

                    cInteres = cInteres.replace(".", "");
                    cInteres = cInteres.replace(",", ".");

                    if (cMontoIva.trim().length() > 0) {
                        cMontoIva = cMontoIva.replace(".", "");
                        cMontoIva = cMontoIva.replace(",", ".");
                    } else {
                        cMontoIva = "0";
                    }

                    cCuota = cCuota.replace(".", "");
                    cCuota = cCuota.replace(",", ".");

                    String cSqlDet = "INSERT INTO detalle_proformas (nprestamo,nrocuota,dias,capital,amortiza,minteres,vence,monto,ivaxinteres)";
                    cSqlDet += "VALUES ('" + idControl.getText() + "','" + cNrocuota + "','" + cNroDias + "','";
                    cSqlDet += cCapital + "','" + cAmortiza + "','" + cInteres + "','" + dVence + "','" + cCuota + "','" + cMontoIva + "')";
                    stm.executeUpdate(cSqlDet);
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

            //GuardarDetalle();
        }
    }
}

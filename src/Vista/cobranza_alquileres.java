/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.ControlGrabado;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import DAO.cajaDAO;
import DAO.clienteDAO;
import DAO.cobradorDAO;
import DAO.cobranzaDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.cuenta_clienteDAO;
import DAO.detalle_forma_cobroDAO;
import DAO.giraduriaDAO;
import Modelo.cuenta_clientes;
import DAO.monedaDAO;
import DAO.productoDAO;
import DAO.sucursalDAO;
import DAO.usuarioDAO;
import DAO.vendedorDAO;
import DAO.ventaDAO;
import Modelo.Tablas;
import Modelo.caja;
import Modelo.cliente;
import Modelo.cobrador;
import Modelo.cobranza;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.giraduria;
import Modelo.moneda;
import Modelo.producto;
import Modelo.sucursal;
import Modelo.usuario;
import Modelo.vendedor;
import Modelo.venta;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 */
public class cobranza_alquileres extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm, stm2 = null;
    BDConexion BD = new BDConexion();
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelo2 = new Tablas();
    Tablas modelodetalle = new Tablas();
    JScrollPane scroll = new JScrollPane();
    ObtenerFecha ODate = new ObtenerFecha();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    String cSql = null;
    String cReferencia = null;
    Double nGastos, nOrdinario, tGastos, Punitorio, tOrdinario = 0.00;
    String cPunitorio, cGastos, cOrdinario = null;
    int nPeriodo = 0;
    int nFila = 0;
    Tablas modelosucursal = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelocaja = new Tablas();
    Tablas modelocobrador = new Tablas();
    Date dEmision;
    Date dVence;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c2 = new GregorianCalendar();
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltromoneda, trsfiltrocaja, trsfiltrocobrador;

    DecimalFormat formatea = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("######");
    /**
     * Creates new form Template
     */
    ImageIcon icononuevo = new ImageIcon("src/Iconos/nuevo.png");
    ImageIcon iconoeditar = new ImageIcon("src/Iconos/editar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconoprint = new ImageIcon("src/Iconos/impresora.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon icorefresh = new ImageIcon("src/Iconos/refrescar.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");

    public cobranza_alquileres() {
        initComponents();
        this.idControl.setVisible(false);
        this.Modo.setVisible(false);
        this.Agregar.setIcon(icononuevo);
        this.Anular.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.Salir.setIcon(iconosalir);
        this.refrescar.setIcon(icorefresh);

        this.buscarCaja.setIcon(iconobuscar);
        this.buscarSucursal.setIcon(iconobuscar);
        this.buscarCobrador.setIcon(iconobuscar);
        this.buscarMoneda.setIcon(iconobuscar);
        this.buscarSucursal.setIcon(iconobuscar);
        this.BuscarCliente.setIcon(iconobuscar);

        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);

        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idcontrol.setVisible(false);
        this.cargarTitulo();
        this.CargarTituloDetalle();
        this.Inicializar();
        this.TitClie();
        this.TitCaja();
        this.TitCobrador();
        this.TitMoneda();
        this.TitSuc();
        this.limpiarCombos();

        GrillaMoneda grillamo = new GrillaMoneda();
        Thread hiloca = new Thread(grillamo);
        hiloca.start();

        GrillaCobrador grillaco = new GrillaCobrador();
        Thread hilocobr = new Thread(grillaco);
        hilocobr.start();

        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();

        GrillaCliente grillacl = new GrillaCliente();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();

        this.refrescar.doClick();
    }

    Control hand = new Control();

    private void imprimirFactura() {
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreFactura = config.getNombrefactura();

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            String num = totalcobro.getText();
            num = num.replace(".", "");
            num = num.replace(",", ".");
            numero_a_letras numero = new numero_a_letras();

            parameters.put("Letra", numero.Convertir(num, true, 1));
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("cReferencia", this.idControl.getText());
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.trim());
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
            JasperPrintManager.printReport(masterPrint, false);
            JasperPrintManager.printReport(masterPrint, false);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
        // TODO add your handling code here:
    }

    private void reimprimirfactura() {
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreFactura = config.getNombrefactura();

        try {
            Map parameters = new HashMap();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nFila = jTable1.getSelectedRow();
            String num = jTable1.getValueAt(nFila, 8).toString();
            num = num.replace(".", "").replace(",", ".");
            numero_a_letras numero = new numero_a_letras();

            parameters.put("Letra", numero.Convertir(num, true, 1));
            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("cReferencia", jTable1.getValueAt(nFila, 0).toString());
            JasperReport jr = null;
            URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.trim());
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos
            masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
            JasperPrintManager.printReport(masterPrint, false);
            JasperPrintManager.printReport(masterPrint, false);
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }
        // TODO add your handling code here:
    }

    public void GuardoCobranza() {
        UUID id = new UUID();
        String idunico = UUID.crearUUID();
        idunico = idunico.substring(1, 25);
        this.idControl.setText(idunico.trim());
        double nsupago = 0.00;

        Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
        Date FechaCheque = ODate.de_java_a_sql(emisioncheque.getDate());
        Date FechaTarjeta = ODate.de_java_a_sql(emisiontarjeta.getDate());

        String cTotalPago = totalcobro.getText();
        if (cTotalPago.trim().length() > 0) {
            cTotalPago = cTotalPago.replace(".", "").replace(",", ".");
        } else {
            cTotalPago = "0";
        }

        String cCotizacion = cotizacion.getText();
        cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
        BigDecimal cotizacionmoneda = new BigDecimal(cCotizacion);
        cobranzaDAO cobDAO = new cobranzaDAO();
        cobranza cob = new cobranza();
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sc = null;
        clienteDAO cliDAO = new clienteDAO();
        cliente cl = null;
        monedaDAO monDAO = new monedaDAO();
        moneda mo = null;
        cobradorDAO cobroDAO = new cobradorDAO();
        cobrador co = null;
        usuarioDAO usuDAO = new usuarioDAO();
        usuario usu = null;
        cajaDAO caDAO = new cajaDAO();
        caja ca = null;

        try {
            cl = cliDAO.buscarId(Integer.valueOf(cliente.getText()));
            sc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
            co = cobroDAO.buscarId(Integer.valueOf(cobrador.getText()));
            usu = usuDAO.buscarId(Integer.valueOf(Config.CodUsuario));
            ca = caDAO.buscarId(Integer.valueOf(caja.getText()));
            mo = monDAO.buscarId(Integer.valueOf(moneda.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        cob.setIdpagos(idControl.getText().trim());
        String cNroRecibo = nrorecibo.getText();
        cNroRecibo = cNroRecibo.replace("-", "");
        BigDecimal nrec = new BigDecimal(cNroRecibo);
        cob.setFormatofactura(nrorecibo.getText());
        cob.setNumero(nrec);
        cob.setFecha(FechaProceso);
        cob.setCliente(cl);
        cob.setMoneda(mo);
        BigDecimal TotalPago = new BigDecimal(cTotalPago);
        cob.setTotalpago(TotalPago);
        cob.setObservacion("Cobranzas Servicios Administración N° Documento " + nrorecibo.getText().trim());
        cob.setValores(TotalPago);
        cob.setCobrador(co);
        cob.setSucursal(sc);
        cob.setCotizacionmoneda(cotizacionmoneda);
        cob.setCodusuario(usu);
        cob.setCaja(ca);
        cob.setTipo(1);

        String detalle = "[";
        int Items = tablaalquiler.getRowCount();
        for (int i = 0; i < Items; i++) {
            String supago = tablaalquiler.getValueAt(i, 6).toString();
            supago = supago.replace(".", "").replace(",", ".");
            nsupago = Double.valueOf(supago);

            String idFactura = (tablaalquiler.getValueAt(i, 0).toString().trim());
            String cFactura = (tablaalquiler.getValueAt(i, 21).toString().trim());
            try {
                dEmision = ODate.de_java_a_sql(formatoFecha.parse(tablaalquiler.getValueAt(i, 4).toString()));
                dVence = ODate.de_java_a_sql(formatoFecha.parse(tablaalquiler.getValueAt(i, 4).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

            String cSaldo = (tablaalquiler.getValueAt(i, 5).toString());
            cSaldo = cSaldo.replace(".", "").replace(",", ".");

            String cMulta = (tablaalquiler.getValueAt(i, 10).toString());
            cMulta = cMulta.replace(".", "").replace(",", ".");

            String cAlquiler = (tablaalquiler.getValueAt(i, 7).toString());
            cAlquiler = cAlquiler.replace(".", "").replace(",", ".");

            String cGarage = (tablaalquiler.getValueAt(i, 8).toString());
            cGarage = cGarage.replace(".", "").replace(",", ".");

            String cExpensa = (tablaalquiler.getValueAt(i, 9).toString());
            cExpensa = cExpensa.replace(".", "").replace(",", ".");

            String cComision = (tablaalquiler.getValueAt(i, 11).toString());
            cComision = cComision.replace(".", "").replace(",", ".");

            ///////////////
            String cIvaAlquiler = (tablaalquiler.getValueAt(i, 16).toString());
            cIvaAlquiler = cIvaAlquiler.replace(".", "").replace(",", ".");

            String cIvaMulta = (tablaalquiler.getValueAt(i, 17).toString());
            cIvaMulta = cIvaMulta.replace(".", "").replace(",", ".");

            String cIvaComision = (tablaalquiler.getValueAt(i, 18).toString());
            cIvaComision = cIvaComision.replace(".", "").replace(",", ".");

            String cIvaGarage = (tablaalquiler.getValueAt(i, 19).toString());
            cIvaGarage = cIvaGarage.replace(".", "").replace(",", ".");

            String cIvaExpensa = (tablaalquiler.getValueAt(i, 20).toString());
            cIvaExpensa = cIvaExpensa.replace(".", "").replace(",", ".");

            String cNumerocuota = (tablaalquiler.getValueAt(i, 23).toString());
            String cCuota = (tablaalquiler.getValueAt(i, 24).toString());
            String cComprobante = (tablaalquiler.getValueAt(i, 25).toString());

            String cGarantia = (tablaalquiler.getValueAt(i, 12).toString());
            cGarantia = cGarantia.replace(".", "").replace(",", ".");

            String cFondo = (tablaalquiler.getValueAt(i, 13).toString());
            cFondo = cFondo.replace(".", "").replace(",", ".");

            String cLlave = (tablaalquiler.getValueAt(i, 14).toString());
            cLlave = cLlave.replace(".", "").replace(",", ".");

            String cOtros = (tablaalquiler.getValueAt(i, 15).toString());
            cOtros = cOtros.replace(".", "").replace(",", ".");

            //En caso que el pago sea menor al monto a pagar   
            if (Double.valueOf(supago) > 0) {
                String linea = "{iddetalle : " + cob.getIdpagos().trim() + ","
                        + "idfactura : " + idFactura + ","
                        + "nrofactura : " + cFactura + ","
                        + "emision : " + dEmision + ","
                        + "comprobante : " + cComprobante + ","
                        + "pago : " + supago + ","
                        + "alquiler: " + cAlquiler + ","
                        + "multa: " + cMulta + ","
                        + "expensa : " + cExpensa + ","
                        + "moneda : " + moneda.getText() + ","
                        + "saldo : " + cSaldo + ","
                        + "comision : " + cComision + ","
                        + "vence : " + dVence + ","
                        + "garage : " + cGarage + ","
                        + "cuota : " + cCuota + ","
                        + "numerocuota : " + cNumerocuota + ","
                        + "ivaalquiler: " + cIvaAlquiler + ","
                        + "ivamulta: " + cIvaMulta + ","
                        + "ivagarage: " + cIvaGarage + ","
                        + "ivacomision: " + cIvaComision + ","
                        + "ivaexpensa: " + cIvaExpensa + ","
                        + "garantia: " + cGarantia + ","
                        + "fondo: " + cFondo + ","
                        + "llave: " + cLlave + ","
                        + "otros: " + cOtros + ","
                        + "idunidad: " + tablaalquiler.getValueAt(i, 22).toString() + ","
                        + "fechacobro: " + FechaProceso + "},";
                detalle += linea;
            }
        }
        if (!detalle.equals("[")) {
            detalle = detalle.substring(0, detalle.length() - 1);
        }
        detalle += "]";

        //INGRESAMOS LA FORMA DE PAGO
        //INGRESAMOS LA FORMA DE PAGO
        String detalleformapago = "[";
        String cEfectivo = totalbrutoapagar.getText();
        cEfectivo = cEfectivo.replace(".", "").replace(",", ".");
        String cCobroTarjeta = pagotarjeta.getText();
        String cCobroCheque = cobrocheque.getText();
        cEfectivo = cEfectivo.replace(".", "").replace(",", ".");
        cCobroTarjeta = cCobroTarjeta.replace(".", "").replace(",", ".");
        cCobroCheque = cCobroCheque.replace(".", "").replace(",", ".");
        String linea = null;

        if (Double.valueOf(cEfectivo) > 0) {
            linea = "{idmovimiento : '" + cob.getIdpagos().trim() + "',"
                    + "forma : " + "1" + ","
                    + "banco : " + "0" + ","
                    + "nrocheque : " + "XX" + ","
                    + "confirmacion: " + FechaProceso + ","
                    + "netocobrado : " + cEfectivo + "},";
            detalleformapago += linea;
        }
        if (Double.valueOf(cCobroCheque) > 0) {
            Object objBancocheque = cargobanco.getSelectedItem();
            String cBanco = ((String[]) objBancocheque)[0];
            linea = "{idmovimiento : '" + cob.getIdpagos().trim() + "',"
                    + "forma : " + "2" + ","
                    + "banco : " + cBanco + ","
                    + "nrocheque : " + nrocheque.getText() + ","
                    + "confirmacion: " + FechaCheque + ","
                    + "netocobrado : " + cCobroCheque + "},";
            detalleformapago += linea;
        }

        if (Double.valueOf(cCobroTarjeta) > 0) {
            Object objBancoEmisor = cargoemisor.getSelectedItem();
            String cBancoEmisor = ((String[]) objBancoEmisor)[0];
            linea = "{idmovimiento : '" + cob.getIdpagos().trim() + "',"
                    + "forma : " + "3" + ","
                    + "banco : " + cBancoEmisor + ","
                    + "nrocheque : " + nrotarjeta.getText() + ","
                    + "confirmacion: " + FechaTarjeta + ","
                    + "netocobrado : " + cCobroTarjeta + "},";
            detalleformapago += linea;
        }

        if (!detalleformapago.equals("[")) {
            detalleformapago = detalleformapago.substring(0, detalleformapago.length() - 1);
        }
        detalleformapago += "]";
        System.out.println(detalleformapago);

        try {
            cobDAO.insertarCobrosInmobiliaria(cob, detalle);
            JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            if (Double.valueOf(cEfectivo) > 0) {
                detalle_forma_cobroDAO detallecobroDAO = new detalle_forma_cobroDAO();
                detallecobroDAO.guardarFormaCobranza(cob.getIdpagos().trim(),detalleformapago);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
        }

    }

    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    public void filtrocobrador(int nNumeroColumna) {
        trsfiltrocobrador.setRowFilter(RowFilter.regexFilter(this.jTBuscarCobrador.getText(), nNumeroColumna));
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    public void filtrocaja(int nNumeroColumna) {
        trsfiltrocaja.setRowFilter(RowFilter.regexFilter(this.jTBuscarCaja.getText(), nNumeroColumna));
    }

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.BuscarMoneda.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    private void Inicializar() {
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        this.emisioncheque.setCalendar(c2);
        this.emisiontarjeta.setCalendar(c2);
    }

    public void limpiarCombos() {
        cargobanco.removeAllItems();
        BD.cargarCombo("SELECT codigo,nombre FROM bancos_plaza", cargobanco);
        cargobanco.setSelectedIndex(0);

        cargoemisor.removeAllItems();;
        BD.cargarCombo("SELECT codigo,nombre FROM bancos_plaza", cargoemisor);
        cargoemisor.setSelectedIndex(0);

    }

    private void GrabarFactura() {
        Runtime rt = Runtime.getRuntime();
        Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
        Date FechaVence = ODate.de_java_a_sql(fecha.getDate());
        Date FechaCheque = ODate.de_java_a_sql(emisioncheque.getDate());
        Date FechaTarjeta = ODate.de_java_a_sql(emisiontarjeta.getDate());

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = null;
        clienteDAO cliDAO = new clienteDAO();
        cliente cli = null;
        comprobanteDAO coDAO = new comprobanteDAO();
        comprobante com = null;
        monedaDAO mnDAO = new monedaDAO();
        moneda mn = null;
        giraduriaDAO giDAO = new giraduriaDAO();
        giraduria gi = null;
        vendedorDAO veDAO = new vendedorDAO();
        vendedor ve = null;
        cajaDAO caDAO = new cajaDAO();
        caja ca = null;

        try {
            suc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
            cli = cliDAO.buscarId(Integer.valueOf(cliente.getText()));
            com = coDAO.buscarId(1);
            mn = mnDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            gi = giDAO.buscarId(1); //verificar y tener en cuenta para ingreso por teclado o asociar a Cliente
            ve = veDAO.buscarId(1);
            ca = caDAO.buscarId(Integer.valueOf(this.caja.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        venta v = new venta();
        ventaDAO grabarventa = new ventaDAO();
        //Capturamos los Valores BigDecimal
        String cExentas = totalexentas.getText();
        cExentas = cExentas.replace(".", "").replace(",", ".");
        BigDecimal nExentas = new BigDecimal(cExentas);

        String cGravadas10 = gravadas10.getText();
        cGravadas10 = cGravadas10.replace(".", "").replace(",", ".");
        BigDecimal nGravadas10 = new BigDecimal(cGravadas10);

        String cGravadas5 = gravadas5.getText();
        cGravadas5 = cGravadas5.replace(".", "").replace(",", ".");
        BigDecimal nGravadas5 = new BigDecimal(cGravadas5);

        String cTotalValores = totalcobro.getText();
        cTotalValores = cTotalValores.replace(".", "").replace(",", ".");
        BigDecimal nSupago = new BigDecimal(cTotalValores);

        String cTotalNeto = totalcobro.getText();
        cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
        BigDecimal nTotalNeto = new BigDecimal(cTotalNeto);

        String cCotizacion = cotizacion.getText();;
        cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
        String cNumeroFactura = nrorecibo.getText();
        cNumeroFactura = cNumeroFactura.replace("-", "");
        BigDecimal nCotizacion = new BigDecimal(cCotizacion);
        //SE CAPTURA SOLO LA PARTE DE LA NUMERACION DE LA FACTURA PARA GUARDARLO EN CAJA
        String cContadorFactura = cNumeroFactura.substring(6, 13);

        v.setCreferencia(idControl.getText());
        v.setFecha(FechaProceso);
        v.setFormatofactura(nrorecibo.getText());
        v.setFactura(Double.valueOf(cNumeroFactura));
        v.setVencimiento(FechaProceso);
        v.setCliente(cli);
        v.setMoneda(mn);
        v.setGiraduria(gi);
        v.setComprobante(com);
        v.setSucursal(suc);
        v.setCotizacion(nCotizacion);
        v.setVendedor(ve);
        v.setCaja(ca);
        v.setNrotimbrado(ca.getTimbrado());
        v.setVencimientotimbrado(ca.getVencetimbrado());
        v.setExentas(nExentas);
        v.setGravadas10(nGravadas10);
        v.setGravadas5(nGravadas5);
        v.setTotalneto(nTotalNeto);
        v.setCuotas(0);
        v.setFinanciado(nTotalNeto.subtract(nSupago));
        v.setObservacion("COBRANZA ALQUILER");
        v.setSupago(nSupago);
        v.setIdusuario(Integer.valueOf(Config.CodUsuario));
        v.setPreventa(0);
        v.setTurno(1);

        productoDAO producto = new productoDAO();
        producto p = null;
        int comprobante = 0;
        String cProducto = null;
        String cCosto = null;
        String cCantidad = null;
        String cPrecio = null;
        String cMonto = null;
        String civa = null;
        String cIvaItem = null;

        BigDecimal totalitem = null;
        int totalRow = modelodetalle.getRowCount();
        totalRow -= 1;
        String detalle = "[";
        for (int i = 0; i <= (totalRow); i++) {
            //Capturo y valido Producto
            cProducto = String.valueOf(modelodetalle.getValueAt(i, 1));
            try {
                p = producto.BuscarProductoBasico("001");
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            //Capturo cantidad    
            cCantidad = String.valueOf("1");
            //Porcentaje

            String cpagado = tablaalquiler.getValueAt(i, 6).toString();
            cpagado = cpagado.replace(".", "").replace(",", ".");
            double npagado = Double.valueOf(cpagado);
            try {
                dEmision = ODate.de_java_a_sql(formatoFecha.parse(tablaalquiler.getValueAt(i, 4).toString()));
                dVence = ODate.de_java_a_sql(formatoFecha.parse(tablaalquiler.getValueAt(i, 4).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

            String cMulta = (tablaalquiler.getValueAt(i, 10).toString());
            cMulta = cMulta.replace(".", "").replace(",", ".");

            String cAlquiler = (tablaalquiler.getValueAt(i, 7).toString());
            cAlquiler = cAlquiler.replace(".", "").replace(",", ".");

            String cGarage = (tablaalquiler.getValueAt(i, 8).toString());
            cGarage = cGarage.replace(".", "").replace(",", ".");

            String cExpensa = (tablaalquiler.getValueAt(i, 9).toString());
            cExpensa = cExpensa.replace(".", "").replace(",", ".");

            String cComision = (tablaalquiler.getValueAt(i, 11).toString());
            cComision = cComision.replace(".", "").replace(",", ".");

            ///////////////
            String cIvaAlquiler = (tablaalquiler.getValueAt(i, 16).toString());
            cIvaAlquiler = cIvaAlquiler.replace(".", "").replace(",", ".");

            String cIvaMulta = (tablaalquiler.getValueAt(i, 17).toString());
            cIvaMulta = cIvaMulta.replace(".", "").replace(",", ".");

            String cIvaComision = (tablaalquiler.getValueAt(i, 18).toString());
            cIvaComision = cIvaComision.replace(".", "").replace(",", ".");

            String cIvaGarage = (tablaalquiler.getValueAt(i, 19).toString());
            cIvaGarage = cIvaGarage.replace(".", "").replace(",", ".");

            String cIvaExpensa = (tablaalquiler.getValueAt(i, 20).toString());
            cIvaExpensa = cIvaExpensa.replace(".", "").replace(",", ".");

            String cNumerocuota = (tablaalquiler.getValueAt(i, 23).toString());
            String cCuota = (tablaalquiler.getValueAt(i, 24).toString());
            String cComprobante = (tablaalquiler.getValueAt(i, 25).toString());

            String cGarantia = (tablaalquiler.getValueAt(i, 12).toString());
            cGarantia = cGarantia.replace(".", "").replace(",", ".");

            String cFondo = (tablaalquiler.getValueAt(i, 13).toString());
            cFondo = cFondo.replace(".", "").replace(",", ".");

            String cLlave = (tablaalquiler.getValueAt(i, 14).toString());
            cLlave = cLlave.replace(".", "").replace(",", ".");

            String cOtros = (tablaalquiler.getValueAt(i, 15).toString());
            cOtros = cOtros.replace(".", "").replace(",", ".");

            String cTipoUnidad = (tablaalquiler.getValueAt(i, 26).toString());

            String cPorcentaje = "0";
            String linea = null;
            if (Double.valueOf(cpagado) > 0) {
                //SE GUARDA DETALLE DE ALQUILER
                if (Double.valueOf(cAlquiler) > 0) {
                    switch (Integer.valueOf(cTipoUnidad)) {
                        case 1:
                            cPorcentaje = "10";
                            break;
                        case 2:
                            cPorcentaje = "5";
                            break;
                    }

                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "001" + "',"
                            + "prcosto : " + cAlquiler + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cAlquiler + ","
                            + "monto : " + String.valueOf(cAlquiler) + ","
                            + "impiva: " + cIvaAlquiler + ","
                            + "porcentaje : " + cPorcentaje + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                ///SE GUARDA DETALLE DE EXPENSAS
                if (Double.valueOf(cExpensa) > 0) {
                    if (Double.valueOf(cIvaExpensa) > 0) {
                        switch (Integer.valueOf(cTipoUnidad)) {
                            case 1:
                                cPorcentaje = "10";
                                break;
                            case 2:
                                cPorcentaje = "5";
                                break;
                        }
                    } else {
                        cPorcentaje = "0";
                        cIvaExpensa = "0";
                    }
                    double nTotalItem = Double.valueOf(cExpensa);
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "002" + "',"
                            + "prcosto : " + cExpensa + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cExpensa + ","
                            + "monto : " + cExpensa + ","
                            + "impiva: " + cIvaExpensa + ","
                            + "porcentaje : " + cPorcentaje + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                ///SE GUARDA DETALLE DE MULTA
                if (Double.valueOf(cMulta) > 0) {
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "003" + "',"
                            + "prcosto : " + cMulta + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cMulta + ","
                            + "monto : " + cMulta + ","
                            + "impiva: " + cIvaMulta + ","
                            + "porcentaje : " + "10" + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                //SE GUARDA DETALLE DE GARAGE
                if (Double.valueOf(cGarage) > 0) {
                    switch (Integer.valueOf(cTipoUnidad)) {
                        case 1:
                            cPorcentaje = "10";
                            break;
                        case 2:
                            cPorcentaje = "5";
                            break;
                    }
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "004" + "',"
                            + "prcosto : " + cGarage + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cGarage + ","
                            + "monto : " + cGarage + ","
                            + "impiva: " + cIvaGarage + ","
                            + "porcentaje : " + cPorcentaje + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence.toString() + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                if (Double.valueOf(cGarantia) > 0) {
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "005" + "',"
                            + "prcosto : " + cGarantia + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cGarantia + ","
                            + "monto : " + cGarantia + ","
                            + "impiva: " + "0" + ","
                            + "porcentaje : " + "0" + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence.toString() + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                if (Double.valueOf(cComision) > 0) {
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "006" + "',"
                            + "prcosto : " + cComision + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cComision + ","
                            + "monto : " + cComision + ","
                            + "impiva: " + "0" + ","
                            + "porcentaje : " + "0" + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence.toString() + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                if (Double.valueOf(cFondo) > 0) {
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "007" + "',"
                            + "prcosto : " + cFondo + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cFondo + ","
                            + "monto : " + cFondo + ","
                            + "impiva: " + "0" + ","
                            + "porcentaje : " + "0" + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence.toString() + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                if (Double.valueOf(cLlave) > 0) {
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "008" + "',"
                            + "prcosto : " + cLlave + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cLlave + ","
                            + "monto : " + cLlave + ","
                            + "impiva: " + "0" + ","
                            + "porcentaje : " + "0" + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence.toString() + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

                if (Double.valueOf(cOtros) > 0) {
                    linea = "{dreferencia : '" + idControl.getText() + "',"
                            + "codprod : '" + "009" + "',"
                            + "prcosto : " + cOtros + ","
                            + "cantidad : " + cCantidad + ","
                            + "precio : " + cOtros + ","
                            + "monto : " + cOtros + ","
                            + "impiva: " + "0" + ","
                            + "porcentaje : " + "0" + ","
                            + "edificio : " + tablaalquiler.getValueAt(i, 22).toString() + ","
                            + "vence : " + dVence.toString() + ","
                            + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
                    detalle += linea;
                }

            }
        }
        if (!detalle.equals("[")) {
            detalle = detalle.substring(0, detalle.length() - 1);
        }
        detalle += "]";
        System.out.println(detalle);

        //SE CREA EL JSON PARA LA VENTA CONTADO
        String cEfectivo = totalbrutoapagar.getText();
        String cCobroTarjeta = pagotarjeta.getText();
        String cCobroCheque = cobrocheque.getText();
        cEfectivo = cEfectivo.replace(".", "").replace(",", ".");
        cCobroTarjeta = cCobroTarjeta.replace(".", "").replace(",", ".");
        cCobroCheque = cCobroCheque.replace(".", "").replace(",", ".");
        String lineacobro = null;
        String detalleformapagoventa = "[";
        if (Double.valueOf(cEfectivo) > 0) {
            lineacobro = "{idmovimiento : " + idControl.getText() + ","
                    + "forma : " + "1" + ","
                    + "banco : " + "1" + ","
                    + "nrocheque : " + "XX" + ","
                    + "confirmacion: " + FechaProceso + ","
                    + "netocobrado : " + cEfectivo + "},";
            detalleformapagoventa += lineacobro;
        }

        if (Double.valueOf(cCobroCheque) > 0) {
            Object objBancocheque = cargobanco.getSelectedItem();
            String cBanco = ((String[]) objBancocheque)[0];
            lineacobro = "{idmovimiento : " + idControl.getText() + ","
                    + "forma : " + "2" + ","
                    + "banco : " + cBanco + ","
                    + "nrocheque : " + nrocheque.getText() + ","
                    + "confirmacion: " + FechaCheque + ","
                    + "netocobrado : " + cCobroCheque + "},";
            detalleformapagoventa += lineacobro;
        }

        if (Double.valueOf(cCobroTarjeta) > 0) {
            Object objBancoEmisor = cargoemisor.getSelectedItem();
            String cBancoEmisor = ((String[]) objBancoEmisor)[0];
            lineacobro = "{idmovimiento : " + idControl.getText() + ","
                    + "forma : " + "3" + ","
                    + "banco : " + cBancoEmisor + ","
                    + "nrocheque : " + nrotarjeta.getText() + ","
                    + "confirmacion: " + FechaTarjeta + ","
                    + "netocobrado : " + cCobroTarjeta + "},";
            detalleformapagoventa += lineacobro;
        }

        if (!detalleformapagoventa.equals("[")) {
            detalleformapagoventa = detalleformapagoventa.substring(0, detalleformapagoventa.length() - 1);
        }
        detalleformapagoventa += "]";
        System.out.println();
        try {
            //SE GRABA LA VENTA EN CABECERA DE VENTAS Y DETALLES DE VENTAS    
            grabarventa.AgregarFacturaInmo(v, detalle);
            //O SE GUARDA LA FORMA DE COBRO EN CASO QUE SEA VENTA AL CONTADO
            detalle_forma_cobroDAO cobDAO = new detalle_forma_cobroDAO();
            cobDAO.guardarFormaPago(detalleformapagoventa);
            //SE SUMA UNO MAS AL NUMERO DE FACTURA EN LA CAJA CORRESPONDIENTE
            caDAO.ActualizarFacturaCaja(Integer.valueOf(caja.getText()), Double.valueOf(cContadorFactura) + 1);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
//        this.imprimirpos.doClick();
        rt.gc();
    }

    public void sumarcobros() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 

        double supago = 0.00;
        double sumpago = 0.00;

        double exentas = 0.00;
        double texentas = 0.00;
        double gravadas10 = 0.00;
        double tgravadas10 = 0.00;
        double gravadas5 = 0.00;
        double tgravadas5 = 0.00;

        String csupago = "";
        String cExentas = "";
        String cGravadas10 = "";
        String cGravadas5 = "";

        int totalRow = modelodetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CUOTAS COBRADAS

            csupago = String.valueOf(modelodetalle.getValueAt(i, 6));
            csupago = csupago.replace(".", "").replace(",", ".");
            supago = Double.valueOf(csupago);
            if (supago > 0) {
                String cMulta = (tablaalquiler.getValueAt(i, 10).toString());
                cMulta = cMulta.replace(".", "").replace(",", ".");

                String cAlquiler = (tablaalquiler.getValueAt(i, 7).toString());
                cAlquiler = cAlquiler.replace(".", "").replace(",", ".");

                String cGarage = (tablaalquiler.getValueAt(i, 8).toString());
                cGarage = cGarage.replace(".", "").replace(",", ".");

                String cExpensa = (tablaalquiler.getValueAt(i, 9).toString());
                cExpensa = cExpensa.replace(".", "").replace(",", ".");

                String cComision = (tablaalquiler.getValueAt(i, 11).toString());
                cComision = cComision.replace(".", "").replace(",", ".");

                String cIvaAlquiler = (tablaalquiler.getValueAt(i, 16).toString());
                cIvaAlquiler = cIvaAlquiler.replace(".", "").replace(",", ".");

                String cIvaMulta = (tablaalquiler.getValueAt(i, 17).toString());
                cIvaMulta = cIvaMulta.replace(".", "").replace(",", ".");

                String cIvaComision = (tablaalquiler.getValueAt(i, 18).toString());
                cIvaComision = cIvaComision.replace(".", "").replace(",", ".");

                String cIvaGarage = (tablaalquiler.getValueAt(i, 19).toString());
                cIvaGarage = cIvaGarage.replace(".", "").replace(",", ".");

                String cIvaExpensa = (tablaalquiler.getValueAt(i, 20).toString());
                cIvaExpensa = cIvaExpensa.replace(".", "").replace(",", ".");

                String cGarantia = (tablaalquiler.getValueAt(i, 12).toString());
                cGarantia = cGarantia.replace(".", "").replace(",", ".");

                String cFondo = (tablaalquiler.getValueAt(i, 13).toString());
                cFondo = cFondo.replace(".", "").replace(",", ".");

                String cLlave = (tablaalquiler.getValueAt(i, 14).toString());
                cLlave = cLlave.replace(".", "").replace(",", ".");

                String cOtros = (tablaalquiler.getValueAt(i, 15).toString());
                cOtros = cOtros.replace(".", "").replace(",", ".");
                String cTipoUnidad = (tablaalquiler.getValueAt(i, 26).toString());

                if (Integer.valueOf(cTipoUnidad) == 1) {
                    gravadas10 = Double.valueOf(cAlquiler) + Double.valueOf(cGarage);
                } else {
                    gravadas5 = Double.valueOf(cAlquiler) + Double.valueOf(cGarage);
                }

                if (Double.valueOf(cIvaExpensa) > 0) {
                    gravadas10 = gravadas10 + Double.valueOf(cExpensa);
                } else {
                    exentas = Double.valueOf(cExpensa);
                }

                exentas = exentas + Double.valueOf(cGarantia) + Double.valueOf(cFondo) + Double.valueOf(cOtros) + Double.valueOf(cLlave);
                texentas += exentas;
                gravadas10 = gravadas10 + Double.valueOf(cMulta) + Double.valueOf(cComision);
                tgravadas10 += gravadas10;

                tgravadas5 += gravadas5;

                sumpago += supago;
            }
        }
        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.gravadas10.setText(formatea.format(tgravadas10));
        this.gravadas5.setText(formatea.format(tgravadas5));
        this.totalexentas.setText(formatea.format(texentas));
        this.totalcobro.setText(formatea.format(sumpago));
        //formato.format(sumatoria1);
    }

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");
        modelocliente.addColumn("Dirección");
        modelocliente.addColumn("RUC");

        int[] anchos = {90, 150, 100, 100};
        for (int i = 0; i < modelocliente.getColumnCount(); i++) {
            tablacliente.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacliente.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacliente.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacliente.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacliente.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitSuc() {
        modelosucursal.addColumn("Código");
        modelosucursal.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelosucursal.getColumnCount(); i++) {
            tablasucursal.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablasucursal.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablasucursal.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablasucursal.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablasucursal.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitMoneda() {
        modelomoneda.addColumn("Código");
        modelomoneda.addColumn("Nombre");
        modelomoneda.addColumn("Cotización");

        int[] anchos = {90, 100, 90};
        for (int i = 0; i < modelomoneda.getColumnCount(); i++) {
            tablamoneda.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablamoneda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablamoneda.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablamoneda.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablamoneda.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablamoneda.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
    }

    private void TitCobrador() {
        modelocobrador.addColumn("Código");
        modelocobrador.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelocobrador.getColumnCount(); i++) {
            tablacobrador.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacobrador.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacobrador.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacobrador.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacobrador.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TitCaja() {
        modelocaja.addColumn("Código");
        modelocaja.addColumn("Nombre");
        modelocaja.addColumn("Nro. Factura");
        modelocaja.addColumn("Expedicion");
        modelocaja.addColumn("N° Timbrado");
        modelocaja.addColumn("Fecha Inicio");
        modelocaja.addColumn("Vence Timbrado");

        int[] anchos = {90, 100, 90, 90, 90, 90, 90};
        for (int i = 0; i < modelocaja.getColumnCount(); i++) {
            tablacaja.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacaja.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacaja.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacaja.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacaja.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablacaja.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);

        this.tablacaja.getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablacaja.getColumnModel().getColumn(3).setMinWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);

        this.tablacaja.getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacaja.getColumnModel().getColumn(4).setMinWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);

        this.tablacaja.getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablacaja.getColumnModel().getColumn(5).setMinWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);

        this.tablacaja.getColumnModel().getColumn(6).setMaxWidth(0);
        this.tablacaja.getColumnModel().getColumn(6).setMinWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
    }

    public void limpiar() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = null;
        config = configDAO.consultar();
        this.sucursal.setText(String.valueOf(config.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(config.getSucursaldefecto().getNombre());
        this.caja.setText(String.valueOf(config.getCajadefecto().getCodigo()));
        this.nombrecaja.setText(config.getCajadefecto().getNombre());
        this.nrorecibo.setText("0");
        this.sucambio.setText("0");
        this.cliente.setText("0");
        this.nombrecliente.setText("Ingrese la Cta. del Inquilino");
        //ASIGNAMOS FORMATO DE FACTURA

        String cBoca = config.getCajadefecto().getExpedicion().trim();
        Double nFactura = config.getCajadefecto().getFactura();
        int n = (int) nFactura.doubleValue();
        String formatString = String.format("%%0%dd", 7);
        String formattedString = String.format(formatString, n);
        this.nrorecibo.setText(cBoca + "-" + formattedString);

        this.cobrador.setText("1");
        this.nombrecobrador.setText("MOSTRADOR");

        this.moneda.setText(String.valueOf(config.getMonedadefecto().getCodigo()));
        this.nombremoneda.setText(config.getMonedadefecto().getNombre());
        this.cotizacion.setText(formatea.format(config.getMonedadefecto().getVenta()));

        this.cliente.setText(String.valueOf(config.getClientedefecto().getCodigo()));
        this.nombrecliente.setText(config.getClientedefecto().getNombre());
        this.direccion.setText(config.getClientedefecto().getDireccion());

        this.fecha.setCalendar(c2);
        this.cobrocheque.setText("0");
        this.pagotarjeta.setText("0");
        this.nrocheque.setText("");

        this.totalcobro.setText("0");
        this.totalexentas.setText("0");
        this.gravadas10.setText("0");
        this.gravadas5.setText("0");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detalle_cobro_alquiler = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        nombrecliente = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        Modo = new javax.swing.JTextField();
        idControl = new javax.swing.JTextField();
        ruc = new javax.swing.JTextField();
        MostrarCuentas = new javax.swing.JButton();
        BuscarCliente = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JFormattedTextField();
        sucursal = new javax.swing.JTextField();
        buscarSucursal = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        moneda = new javax.swing.JTextField();
        buscarMoneda = new javax.swing.JButton();
        nombremoneda = new javax.swing.JTextField();
        cobrador = new javax.swing.JTextField();
        buscarCobrador = new javax.swing.JButton();
        nombrecobrador = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        nombrecaja = new javax.swing.JTextField();
        buscarCaja = new javax.swing.JButton();
        caja = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nrorecibo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        sucambio = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaalquiler = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        SalirCobranza = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        totalcobro = new javax.swing.JFormattedTextField();
        jPanel28 = new javax.swing.JPanel();
        boxcuotas = new javax.swing.JCheckBox();
        descuentogastos = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        totalexentas = new javax.swing.JFormattedTextField();
        gravadas10 = new javax.swing.JFormattedTextField();
        gravadas5 = new javax.swing.JFormattedTextField();
        ingresar_cobros = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        totalbrutoapagar = new javax.swing.JFormattedTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        cobroefectivo = new javax.swing.JFormattedTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        cobrocheque = new javax.swing.JFormattedTextField();
        cargobanco = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        nrocheque = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        emisioncheque = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        pagotarjeta = new javax.swing.JFormattedTextField();
        cargoemisor = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        nrotarjeta = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        emisiontarjeta = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        AceptarCobro = new javax.swing.JButton();
        SalirFormaCobro = new javax.swing.JButton();
        BSucursal = new javax.swing.JDialog();
        jPanel18 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        PagoParcial = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        importe_pagado = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        alquiler = new javax.swing.JFormattedTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        garage = new javax.swing.JFormattedTextField();
        expensa = new javax.swing.JFormattedTextField();
        comision = new javax.swing.JFormattedTextField();
        multa = new javax.swing.JFormattedTextField();
        fondo = new javax.swing.JFormattedTextField();
        llave = new javax.swing.JFormattedTextField();
        otros = new javax.swing.JFormattedTextField();
        propietario = new javax.swing.JTextField();
        direccionpropietario = new javax.swing.JTextField();
        catastro = new javax.swing.JTextField();
        vencimiento = new com.toedter.calendar.JDateChooser();
        garantia = new javax.swing.JFormattedTextField();
        totalapagar = new javax.swing.JFormattedTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        GrabarPagoParcial = new javax.swing.JButton();
        SalirPagoParcial = new javax.swing.JButton();
        BCliente = new javax.swing.JDialog();
        jPanel20 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BMoneda = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        BuscarMoneda = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        BCaja = new javax.swing.JDialog();
        jPanel24 = new javax.swing.JPanel();
        combocaja = new javax.swing.JComboBox();
        jTBuscarCaja = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablacaja = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        AceptarCaja = new javax.swing.JButton();
        SalirCaja = new javax.swing.JButton();
        BCobrador = new javax.swing.JDialog();
        jPanel26 = new javax.swing.JPanel();
        combocobrador = new javax.swing.JComboBox();
        jTBuscarCobrador = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablacobrador = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        AceptarCobrador = new javax.swing.JButton();
        SalirCobrador = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Agregar = new javax.swing.JButton();
        Anular = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        idcontrol = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        refrescar = new javax.swing.JButton();
        Listar = new javax.swing.JButton();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Cuenta");

        cliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        nombrecliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombrecliente.setEnabled(false);

        direccion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        direccion.setEnabled(false);

        Modo.setText(" ");

        ruc.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ruc.setEnabled(false);

        MostrarCuentas.setText("Mostrar Cuentas");
        MostrarCuentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        MostrarCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarCuentasActionPerformed(evt);
            }
        });

        BuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        jLabel7.setText("Inquilino");

        jLabel8.setText("RUC");

        jLabel9.setText("Dirección");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(direccion)
                        .addComponent(nombrecliente, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MostrarCuentas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Modo, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(idControl))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MostrarCuentas)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Modo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Sucursal");

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

        jLabel3.setText("Fecha");

        jLabel14.setText("Cobrador");

        jLabel4.setText("Moneda");

        jLabel5.setText("Cotización");

        cotizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionKeyPressed(evt);
            }
        });

        sucursal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucursal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sucursalFocusGained(evt);
            }
        });
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

        buscarSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarSucursalActionPerformed(evt);
            }
        });

        nombresucursal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        moneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        buscarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarMonedaActionPerformed(evt);
            }
        });

        nombremoneda.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombremoneda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombremoneda.setEnabled(false);

        cobrador.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobradorActionPerformed(evt);
            }
        });
        cobrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cobradorKeyPressed(evt);
            }
        });

        buscarCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCobradorActionPerformed(evt);
            }
        });

        nombrecobrador.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombrecobrador.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecobrador.setEnabled(false);

        jLabel36.setText("Caja");

        nombrecaja.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombrecaja.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecaja.setEnabled(false);

        buscarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarCajaActionPerformed(evt);
            }
        });

        caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaActionPerformed(evt);
            }
        });
        caja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cajaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(13, 13, 13)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(buscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(buscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel36)
                                .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14))
                            .addComponent(buscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(moneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombremoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(buscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("N° Factura");

        nrorecibo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrorecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nrorecibo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nrorecibo.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("SU CAMBIO");

        sucambio.setEditable(false);
        sucambio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sucambio.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nrorecibo))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel1))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(sucambio, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nrorecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(sucambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablaalquiler.setModel(modelodetalle);
        tablaalquiler.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaalquiler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaalquilerKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaalquiler);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirCobranza.setText("Salir");
        SalirCobranza.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCobranza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCobranzaActionPerformed(evt);
            }
        });

        jButton2.setText("Ingrese el Pago");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Total a Cobrar");

        totalcobro.setEditable(false);
        totalcobro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalcobro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SalirCobranza, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel10))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(totalcobro, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(totalcobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalirCobranza)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        boxcuotas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxcuotas.setText("Cobrar todas las Cuotas");
        boxcuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxcuotasActionPerformed(evt);
            }
        });

        descuentogastos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        descuentogastos.setText("Aplicar Descuentos x Mora");
        descuentogastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descuentogastosActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("[F1] Seleccionar Pago");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("[F2] Ingresar Pago Parcial");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("[F3] Cobrar");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("[F4] Descontar esta Mora");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("[F5] Borrar Cobro");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(27, 27, 27)
                                .addComponent(descuentogastos)))
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addGroup(jPanel28Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(53, 53, 53)
                                .addComponent(boxcuotas))
                            .addComponent(jLabel18))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(boxcuotas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(descuentogastos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Resumen de Impuestos");

        jLabel15.setText("Exentas");

        jLabel16.setText("Gravadas 10%");

        jLabel23.setText("Gravadas 5%");

        totalexentas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        totalexentas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalexentas.setEnabled(false);

        gravadas10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        gravadas10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas10.setEnabled(false);

        gravadas5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        gravadas5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        gravadas5.setEnabled(false);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel15))
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gravadas10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalexentas, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel13)
                        .addContainerGap(72, Short.MAX_VALUE))))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(totalexentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(gravadas10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout detalle_cobro_alquilerLayout = new javax.swing.GroupLayout(detalle_cobro_alquiler.getContentPane());
        detalle_cobro_alquiler.getContentPane().setLayout(detalle_cobro_alquilerLayout);
        detalle_cobro_alquilerLayout.setHorizontalGroup(
            detalle_cobro_alquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        detalle_cobro_alquilerLayout.setVerticalGroup(
            detalle_cobro_alquilerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel9.setBackground(new java.awt.Color(240, 224, 121));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel20.setText("Total a Cobrar");

        totalbrutoapagar.setEditable(false);
        totalbrutoapagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalbrutoapagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(240, 224, 121));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel26.setText("Efectivo");

        cobroefectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        cobroefectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobroefectivo.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        cobroefectivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cobroefectivoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cobroefectivoFocusLost(evt);
            }
        });
        cobroefectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobroefectivoActionPerformed(evt);
            }
        });
        cobroefectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cobroefectivoKeyPressed(evt);
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

        jPanel12.setBackground(new java.awt.Color(240, 224, 121));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Pago en Cheque"));

        jLabel27.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel27.setText("Cargo Banco");

        cobrocheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cobrocheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobrocheque.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        cargobanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel28.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel28.setText("Nº Cheque");

        jLabel29.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel29.setText("Emisión");

        jLabel33.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel33.setText("Importe Cheque");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel27))
                    .addComponent(cargobanco, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(cobrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel28)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33)
                        .addGap(30, 30, 30))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emisioncheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cargobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cobrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(240, 224, 121));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Pago con Tarjetas"));

        jLabel30.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel30.setText("Banco Emisor");

        pagotarjeta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        pagotarjeta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pagotarjeta.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N

        cargoemisor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel31.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel31.setText("Nº Tarjeta");

        jLabel32.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel32.setText("Emisión");

        jLabel34.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        jLabel34.setText("Importe Tarjeta");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel30))
                    .addComponent(cargoemisor, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel31)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel32)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(11, 11, 11)))
                .addGap(17, 17, 17))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(14, 14, 14)
                        .addComponent(pagotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emisiontarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cargoemisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nrotarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(240, 224, 121));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCobro.setText("Aceptar");
        AceptarCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCobroActionPerformed(evt);
            }
        });

        SalirFormaCobro.setText("Salir");
        SalirFormaCobro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFormaCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFormaCobroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AceptarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(SalirFormaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ingresar_cobrosLayout.setVerticalGroup(
            ingresar_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingresar_cobrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarSucursalActionPerformed(evt);
            }
        });
        jTBuscarSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarSucursalKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablasucursal.setModel(modelosucursal);
        tablasucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablasucursalMouseClicked(evt);
            }
        });
        tablasucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablasucursalKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablasucursal);

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText("Aceptar");
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText("Salir");
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        PagoParcial.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        PagoParcial.setTitle("Pagar Cuotas");

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(102, 102, 255)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Propietario");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Vence");

        importe_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importe_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Alquiler");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Su Pago");

        alquiler.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        alquiler.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        alquiler.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                alquilerKeyPressed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Garage");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Expensa");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Comisión");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Multa");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("Fondo");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Llave");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText("Otros");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText("Edificio");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setText("Ubicación");

        garage.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        garage.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        garage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                garageKeyPressed(evt);
            }
        });

        expensa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        expensa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        expensa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                expensaKeyPressed(evt);
            }
        });

        comision.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        comision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                comisionKeyPressed(evt);
            }
        });

        multa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        multa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        multa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                multaKeyPressed(evt);
            }
        });

        fondo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        fondo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        fondo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fondoKeyPressed(evt);
            }
        });

        llave.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        llave.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        llave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                llaveKeyPressed(evt);
            }
        });

        otros.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        otros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        otros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                otrosKeyPressed(evt);
            }
        });

        propietario.setEnabled(false);
        propietario.setFocusable(false);

        direccionpropietario.setEnabled(false);
        direccionpropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionpropietarioActionPerformed(evt);
            }
        });

        catastro.setEnabled(false);

        vencimiento.setEnabled(false);

        garantia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        garantia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        garantia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                garantiaKeyPressed(evt);
            }
        });

        totalapagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        totalapagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalapagar.setEnabled(false);

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setText("Garantía");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Total a Pagar");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comision, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                .addComponent(expensa, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                .addComponent(multa, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jLabel43)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                        .addComponent(llave, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                        .addComponent(otros, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel42)))
                            .addComponent(garantia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addGap(64, 64, 64)
                                .addComponent(jLabel25)
                                .addGap(0, 32, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(totalapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(importe_pagado))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40)
                            .addComponent(jLabel24)
                            .addComponent(jLabel46)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(propietario)
                                .addComponent(direccionpropietario, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(catastro, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(garage, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(57, 115, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(propietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(direccionpropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(catastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alquiler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(garage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(expensa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(multa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(garantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(llave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(jLabel47)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(totalapagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importe_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarPagoParcial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarPagoParcial.setText("Aceptar");
        GrabarPagoParcial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarPagoParcial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarPagoParcialActionPerformed(evt);
            }
        });

        SalirPagoParcial.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirPagoParcial.setText("Salir");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(SalirPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
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
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarClienteActionPerformed(evt);
            }
        });
        jTBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarClienteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacliente.setModel(modelocliente        );
        tablacliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaclienteMouseClicked(evt);
            }
        });
        tablacliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaclienteKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tablacliente);

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText("Aceptar");
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText("Salir");
        SalirCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        javax.swing.GroupLayout BClienteLayout = new javax.swing.GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("Buscar Giraduría");

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combogiraduria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combogiraduria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combogiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combogiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combogiraduriaActionPerformed(evt);
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
                .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        AceptarGir.setText("Aceptar");
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText("Salir");
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

        BCaja.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCaja.setTitle("null");

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocaja.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocaja.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocajaActionPerformed(evt);
            }
        });

        jTBuscarCaja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCajaActionPerformed(evt);
            }
        });
        jTBuscarCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCajaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacaja.setModel(modelocaja);
        tablacaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacajaMouseClicked(evt);
            }
        });
        tablacaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacajaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablacaja);

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCaja.setText("Aceptar");
        AceptarCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCajaActionPerformed(evt);
            }
        });

        SalirCaja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCaja.setText("Salir");
        SalirCaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCajaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCaja)
                    .addComponent(SalirCaja))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCajaLayout = new javax.swing.GroupLayout(BCaja.getContentPane());
        BCaja.getContentPane().setLayout(BCajaLayout);
        BCajaLayout.setHorizontalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCajaLayout.setVerticalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCobrador.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCobrador.setTitle("null");

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocobrador.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocobrador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocobradorActionPerformed(evt);
            }
        });

        jTBuscarCobrador.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCobrador.setText(org.openide.util.NbBundle.getMessage(cobranza_alquileres.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarCobradorActionPerformed(evt);
            }
        });
        jTBuscarCobrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarCobradorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(combocobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacobrador.setModel(modelocobrador);
        tablacobrador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacobradorMouseClicked(evt);
            }
        });
        tablacobrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacobradorKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablacobrador);

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCobrador.setText(org.openide.util.NbBundle.getMessage(cobranza_alquileres.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCobradorActionPerformed(evt);
            }
        });

        SalirCobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCobrador.setText(org.openide.util.NbBundle.getMessage(cobranza_alquileres.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCobradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCobrador)
                    .addComponent(SalirCobrador))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCobradorLayout = new javax.swing.GroupLayout(BCobrador.getContentPane());
        BCobrador.getContentPane().setLayout(BCobradorLayout);
        BCobradorLayout.setHorizontalGroup(
            BCobradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCobradorLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCobradorLayout.setVerticalGroup(
            BCobradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCobradorLayout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        Agregar.setBackground(new java.awt.Color(255, 255, 255));
        Agregar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Agregar.setText(" Agregar Registro");
        Agregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        Anular.setBackground(new java.awt.Color(255, 255, 255));
        Anular.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Anular.setText("Anular Cobranza");
        Anular.setToolTipText("");
        Anular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Anular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnularActionPerformed(evt);
            }
        });

        Salir.setBackground(new java.awt.Color(255, 255, 255));
        Salir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Salir.setText("     Salir");
        Salir.setToolTipText("");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        idcontrol.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar entre los Días"));

        refrescar.setText("Refrescar");
        refrescar.setActionCommand("Filtrar");
        refrescar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(refrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(FechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refrescar))
        );

        Listar.setBackground(new java.awt.Color(255, 255, 255));
        Listar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Listar.setText(org.openide.util.NbBundle.getMessage(cobranza_alquileres.class, "ventas.Listar.text")); // NOI18N
        Listar.setToolTipText(org.openide.util.NbBundle.getMessage(cobranza_alquileres.class, "ventas.Listar.toolTipText")); // NOI18N
        Listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idcontrol, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(Salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Anular, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(Agregar)
                .addGap(18, 18, 18)
                .addComponent(Anular)
                .addGap(18, 18, 18)
                .addComponent(Listar)
                .addGap(16, 16, 16)
                .addComponent(Salir)
                .addGap(29, 29, 29)
                .addComponent(idcontrol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setText("Cobranzas de Alquileres");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del Cliente", "N° de Recibo" }));
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
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 40, Short.MAX_VALUE))
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
                        indiceColumnaTabla = 4;
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

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        idControl.setText("0");
        //  modo.setText("1");
        this.limpiar();
        //  this.limpiaritems();
        //Establecemos un título para el jDialog

        int cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }

        cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }
        detalle_cobro_alquiler.setModal(true);
        detalle_cobro_alquiler.setSize(1100, 710);
        detalle_cobro_alquiler.setTitle("Generar Nueva Cobranza");
        detalle_cobro_alquiler.setLocationRelativeTo(null);
        detalle_cobro_alquiler.setVisible(true);
        sucursal.requestFocus();
        this.refrescar.doClick();

    }//GEN-LAST:event_AgregarActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        int nFila = this.jTable1.getSelectedRow();
        this.idcontrol.setText(this.jTable1.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int nFila = this.jTable1.getSelectedRow();
        this.idcontrol.setText(this.jTable1.getValueAt(nFila, 0).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusGained

    private void jScrollPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void AnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnularActionPerformed
        if (Config.cNivelUsuario.equals("1")) {
            if (!this.idcontrol.getText().isEmpty()) {
                Object[] opciones = {"   Si   ", "   No   "};
                int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar esta Cobranza ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
                if (ret == 0) {
                    BDConexion BD = new BDConexion();
                    BD.BorrarDetalles("cabecera_ventas", "creferencia='" + this.idcontrol.getText().trim() + "'");
                    BD.BorrarDetalles("facturacobranzas", "idcobranzas='" + this.idcontrol.getText().trim() + "'");
                    BD.BorrarDetalles("detalle_forma_cobranzas", "idmovimiento='" + this.idcontrol.getText().trim() + "'");
                    BD.BorrarDetalles("detalle_cobranzas", "iddetalle='" + this.idcontrol.getText().trim() + "'");
                    BD.BorrarDetalles("cobranzas", "idpagos='" + this.idcontrol.getText() + "'");
                } else {
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar una Cobranza");
            return;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AnularActionPerformed

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusLost

    private void refrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrescarActionPerformed
        GrillaCobroAlquiler GrillaCC = new GrillaCobroAlquiler();
        Thread HiloGrilla = new Thread(GrillaCC);
        HiloGrilla.start();
    }//GEN-LAST:event_refrescarActionPerformed

    private void ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        this.reimprimirfactura();
    }//GEN-LAST:event_ListarActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        this.BuscarCliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

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

    private void MostrarCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarCuentasActionPerformed
        GrillaCuenta cargarsaldo = new GrillaCuenta();
        cargarsaldo.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_MostrarCuentasActionPerformed

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.cliente.getText()));
            if (cl.getCodigo() == 0) {
                BCliente.setModal(true);
                BCliente.setSize(500, 575);
                BCliente.setLocationRelativeTo(null);
                BCliente.setTitle("Buscar Cliente");
                BCliente.setVisible(true);
                MostrarCuentas.requestFocus();
                BCliente.setModal(false);
            } else {
                nombrecliente.setText(cl.getNombre());
                direccion.setText(cl.getDireccion());
                ruc.setText(cl.getRuc());
                //Establecemos un título para el jDialog
                MostrarCuentas.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void fechaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaFocusGained

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.caja.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.sucursal.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void cotizacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cliente.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.moneda.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionKeyPressed

    private void sucursalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucursalFocusGained
        sucursal.selectAll();

        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalFocusGained

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
        this.buscarSucursal.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalActionPerformed

    private void sucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucursalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void buscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (sucu.getCodigo() == 0) {
                BSucursal.setModal(true);
                BSucursal.setSize(500, 575);
                BSucursal.setLocationRelativeTo(null);
                BSucursal.setTitle("Buscar Sucursal");
                BSucursal.setVisible(true);
                BSucursal.setModal(false);
                fecha.requestFocus();
            } else {
                nombresucursal.setText(sucu.getNombre());
                //Establecemos un título para el jDialog
                fecha.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarSucursalActionPerformed

    private void monedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaActionPerformed
        buscarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaActionPerformed

    private void monedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cotizacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cobrador.requestFocus();
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_monedaKeyPressed

    private void buscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarMonedaActionPerformed
        monedaDAO monDAO = new monedaDAO();
        moneda mo = null;
        try {
            mo = monDAO.buscarId(Integer.valueOf(this.moneda.getText()));
            if (mo.getCodigo() == 0) {
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                BMoneda.setTitle("Buscar Moneda");
                cotizacion.requestFocus();
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mo.getNombre());
                cotizacion.setText(formatea.format(mo.getVenta()));
                //Establecemos un título para el jDialog
                cotizacion.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarMonedaActionPerformed

    private void cobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobradorActionPerformed
        this.buscarCobrador.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cobradorActionPerformed

    private void cobradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cobradorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.moneda.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.caja.requestFocus();
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_cobradorKeyPressed

    private void buscarCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCobradorActionPerformed
        cobradorDAO coDAO = new cobradorDAO();
        cobrador cob = null;
        try {
            cob = coDAO.buscarId(Integer.valueOf(this.cobrador.getText()));
            if (cob.getCodigo() == 0) {
                BCobrador.setModal(true);
                BCobrador.setSize(500, 575);
                BCobrador.setLocationRelativeTo(null);
                BCobrador.setVisible(true);
                BCobrador.setTitle("Buscar Cobrador");
                BCobrador.setModal(false);
            } else {
                nombrecobrador.setText(cob.getNombre());
                //Establecemos un título para el jDialog
            }
            moneda.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarCobradorActionPerformed

    private void buscarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarCajaActionPerformed
        cajaDAO cajDAO = new cajaDAO();
        caja ca = null;
        try {
            ca = cajDAO.buscarId(Integer.valueOf(this.caja.getText()));
            if (ca.getCodigo() == 0) {
                GrillaCaja grillaca = new GrillaCaja();
                Thread hilocaja = new Thread(grillaca);
                hilocaja.start();
                BCaja.setModal(true);
                BCaja.setSize(500, 575);
                BCaja.setLocationRelativeTo(null);
                BCaja.setVisible(true);
                BCaja.setTitle("Buscar Caja");
                BCaja.setModal(false);
            } else {
                String cBoca = ca.getExpedicion().trim();
                Double nFactura = ca.getFactura();
                int n = (int) nFactura.doubleValue();
                String formatString = String.format("%%0%dd", 7);
                String formattedString = String.format(formatString, n);
                this.nrorecibo.setText(cBoca + "-" + formattedString);
                nombrecaja.setText(ca.getNombre());
                //Establecemos un título para el jDialog
            }
            cobrador.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarCajaActionPerformed

    private void cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaActionPerformed
        this.buscarCaja.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaActionPerformed

    private void cajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cobrador.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaKeyPressed

    private void cobroefectivoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cobroefectivoFocusGained
        this.cobroefectivo.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoFocusGained

    private void AceptarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobroActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Confirmar el Cobro de las Cuotas ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            this.GuardoCobranza();
            this.GrabarFactura();
            this.imprimirFactura();
        }
        this.SalirFormaCobro.doClick();
        this.SalirCobranza.doClick();
        this.refrescar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCobroActionPerformed

    private void SalirFormaCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFormaCobroActionPerformed
        ingresar_cobros.setVisible(false);
        detalle_cobro_alquiler.setModal(true);
        //this.GrabarCobro.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFormaCobroActionPerformed

    private void combosucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combosucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combosucursalActionPerformed

    private void jTBuscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalActionPerformed

    private void jTBuscarSucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarSucursalKeyPressed
        this.jTBuscarSucursal.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarSucursal.getText()).toUpperCase();
                jTBuscarSucursal.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combosucursal.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltrosuc = new TableRowSorter(tablasucursal.getModel());
        tablasucursal.setRowSorter(trsfiltrosuc);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarSucursalKeyPressed

    private void tablasucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablasucursalMouseClicked
        this.AceptarSuc.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalMouseClicked

    private void tablasucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablasucursalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarSuc.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablasucursalKeyPressed

    private void AceptarSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSucActionPerformed
        int nFila = this.tablasucursal.getSelectedRow();
        this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
        this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());

        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");
        this.fecha.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setModal(true);
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void importe_pagadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importe_pagadoFocusGained
        this.importe_pagado.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoFocusGained

    private void importe_pagadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importe_pagadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarPagoParcial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoKeyPressed

    private void GrabarPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarPagoParcialActionPerformed
        this.jTable1.setValueAt(this.importe_pagado.getText(), nFila, 12);
        nFila = 0;
        PagoParcial.setVisible(false);
//      this.sumarcobros();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarPagoParcialActionPerformed

    private void SalirPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPagoParcialActionPerformed
        PagoParcial.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPagoParcialActionPerformed

    private void comboclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboclienteActionPerformed

    private void jTBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteActionPerformed

    private void jTBuscarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarClienteKeyPressed
        this.jTBuscarCliente.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCliente.getText()).toUpperCase();
                jTBuscarCliente.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocliente.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 3;
                        break;//por RUC
                }
                repaint();
                filtrocli(indiceColumnaTabla);
            }
        });
        trsfiltrocli = new TableRowSorter(tablacliente.getModel());
        tablacliente.setRowSorter(trsfiltrocli);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarClienteKeyPressed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
        this.AceptarCli.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void tablaclienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCli.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaclienteKeyPressed

    private void AceptarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCliActionPerformed
        int nFila = this.tablacliente.getSelectedRow();
        this.cliente.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombrecliente.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.direccion.setText(this.tablacliente.getValueAt(nFila, 2).toString());
        this.ruc.setText(this.tablacliente.getValueAt(nFila, 3).toString());
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        MostrarCuentas.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    private void BuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMonedaActionPerformed

    private void BuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarMonedaKeyPressed
        this.BuscarMoneda.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (BuscarMoneda.getText()).toUpperCase();
                BuscarMoneda.setText(cadena);
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
        this.cotizacion.setText(this.tablamoneda.getValueAt(nFila, 2).toString());

        this.BMoneda.setVisible(false);
        BMoneda.setModal(true);
        this.cotizacion.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void combocajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocajaActionPerformed

    private void jTBuscarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCajaActionPerformed

    private void jTBuscarCajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCajaKeyPressed
        this.jTBuscarCaja.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCaja.getText()).toUpperCase();
                jTBuscarCaja.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocaja.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltrocaja = new TableRowSorter(tablacaja.getModel());
        tablacaja.setRowSorter(trsfiltrocaja);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCajaKeyPressed

    private void tablacajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacajaMouseClicked
        this.AceptarCaja.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacajaMouseClicked

    private void tablacajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacajaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCaja.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacajaKeyPressed

    private void AceptarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCajaActionPerformed
        int nFila = this.tablacaja.getSelectedRow();
        this.caja.setText(this.tablacaja.getValueAt(nFila, 0).toString());
        this.nombrecaja.setText(this.tablacaja.getValueAt(nFila, 1).toString());
        this.nrorecibo.setText(tablacaja.getValueAt(nFila, 2).toString());
        this.BCaja.setVisible(false);
        this.jTBuscarCaja.setText("");
        this.cobrador.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCajaActionPerformed

    private void SalirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCajaActionPerformed
        this.BCaja.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCajaActionPerformed

    private void combocobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocobradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocobradorActionPerformed

    private void jTBuscarCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarCobradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCobradorActionPerformed

    private void jTBuscarCobradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarCobradorKeyPressed
        this.jTBuscarCobrador.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarCobrador.getText()).toUpperCase();
                jTBuscarCobrador.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocobrador.getSelectedIndex()) {
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
                filtrocobrador(indiceColumnaTabla);
            }
        });
        trsfiltrocobrador = new TableRowSorter(tablacobrador.getModel());
        tablacobrador.setRowSorter(trsfiltrocobrador);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCobradorKeyPressed

    private void tablacobradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacobradorMouseClicked
        this.AceptarCobrador.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacobradorMouseClicked

    private void tablacobradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacobradorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCobrador.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacobradorKeyPressed

    private void AceptarCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobradorActionPerformed
        int nFila = this.tablacobrador.getSelectedRow();
        this.cobrador.setText(this.tablacobrador.getValueAt(nFila, 0).toString());
        this.nombrecobrador.setText(this.tablacobrador.getValueAt(nFila, 1).toString());

        this.BCobrador.setVisible(false);
        this.jTBuscarCobrador.setText("");
        this.moneda.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCobradorActionPerformed

    private void SalirCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCobradorActionPerformed
        this.BCobrador.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCobradorActionPerformed

    private void SalirCobranzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCobranzaActionPerformed
        detalle_cobro_alquiler.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCobranzaActionPerformed

    private void boxcuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxcuotasActionPerformed
        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        // sumatoria = 0.00;
        // sumtotal = 0.00;
        if (boxcuotas.isSelected()) { // Si hemos dado clic en el jCheckBox
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(this.jTable1.getValueAt(i, 11), i, 12);
                //      cValorActual = String.valueOf(modelo.getValueAt(i, 12));
                //       cValorActual = cValorActual.replace(".", "");
                //     cValorActual = cValorActual.replace(",", ".");
                //    sumatoria = Double.parseDouble(cValorActual);
                //   sumtotal += sumatoria;
            }
        } else {
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(0, i, 12);
            }
        }
        //this.sumarcobros();
    }//GEN-LAST:event_boxcuotasActionPerformed

    private void tablaalquilerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaalquilerKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            //SE REEMPLAZA LA CELDA DE PAGO CON EL VALOR A PAGAR
            this.tablaalquiler.setValueAt(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 5), this.tablaalquiler.getSelectedRow(), 6);
            this.sumarcobros();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            nFila = this.tablaalquiler.getSelectedRow();
            this.propietario.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 1).toString());
            this.direccionpropietario.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 2).toString());
            this.catastro.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 3).toString());

            try {
                vencimiento.setDate(formatoFecha.parse(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 4).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

            this.alquiler.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 7).toString());
            this.garage.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 8).toString());
            this.expensa.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 9).toString());
            this.multa.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 10).toString());
            this.comision.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 11).toString());
            this.garantia.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 12).toString());
            this.fondo.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 13).toString());
            this.llave.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 14).toString());
            this.otros.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 15).toString());
            this.totalapagar.setText(this.tablaalquiler.getValueAt(this.tablaalquiler.getSelectedRow(), 5).toString());
            this.importe_pagado.setText("0");
            PagoParcial.setSize(610, 480);
            PagoParcial.setModal(true);
            PagoParcial.setLocationRelativeTo(null);
            PagoParcial.setVisible(true);
            this.alquiler.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            totalbrutoapagar.setText(totalcobro.getText());
            ingresar_cobros.setSize(650, 420);
            ingresar_cobros.setLocationRelativeTo(null);
            ingresar_cobros.setVisible(true);
            this.cobroefectivo.requestFocus();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            nFila = this.tablaalquiler.getSelectedRow();
            this.tablaalquiler.setValueAt(0, nFila, 9); //COLUMNA INTERES MORATORIO
            this.tablaalquiler.setValueAt(0, nFila, 19); //COLUMNA INTERES PUNITORIO

            String cSaldo = this.tablaalquiler.getValueAt(nFila, 8).toString(); // COLUMNA SALDO
            String cGasto = this.tablaalquiler.getValueAt(nFila, 10).toString();// COLUMNA GASTO

            cSaldo = cSaldo.replace(".", "");
            cSaldo = cSaldo.replace(",", ".");

            cGasto = cGasto.replace(".", "");
            cGasto = cGasto.replace(",", ".");
            double nItemTotal = Double.valueOf(cGasto) + Double.valueOf(cSaldo);

            this.tablaalquiler.setValueAt(formatea.format(nItemTotal), nFila, 11);

            //this.jTable1.setValueAt(0, i, 10); // 
            //formato.format(sumatoria1);
            this.sumarcobros();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            this.tablaalquiler.setValueAt("0", this.tablaalquiler.getSelectedRow(), 10);
            this.sumarcobros();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaalquilerKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (this.moneda.getText().isEmpty() || this.moneda.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione la Moneda e Ingrese la Cotización");
            this.moneda.requestFocus();
            return;
        }
        if (this.cobrador.getText().isEmpty() || this.cobrador.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Cobrador");
            this.cobrador.requestFocus();
            return;
        }
        if (this.cobrador.getText().isEmpty() || this.cobrador.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Cobrador");
            this.cobrador.requestFocus();
            return;
        }
        if (this.cotizacion.getText().isEmpty() || this.cotizacion.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cotización");
            this.cotizacion.requestFocus();
            return;
        }
        if (this.caja.getText().isEmpty() || this.caja.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Caja");
            this.caja.requestFocus();
            return;
        }

        if (this.cliente.getText().isEmpty() || this.cliente.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Cliente");
            this.cliente.requestFocus();
            return;
        }
        String ctotalcobro = this.totalcobro.getText();
        ctotalcobro = ctotalcobro.replace(".", "").replace(",", ".");
        if (ctotalcobro.isEmpty() || ctotalcobro.equals("0")) {
            JOptionPane.showMessageDialog(null, "No tiene Cuentas Seleccionadas");
            caja.requestFocus();
            return;
        }

        totalbrutoapagar.setText(totalcobro.getText());

        ingresar_cobros.setModal(true);
        ingresar_cobros.setSize(673, 465);
        ingresar_cobros.setTitle("Ingrese Formas de Pago");
        ingresar_cobros.setLocationRelativeTo(null);
        ingresar_cobros.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cobroefectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cobroefectivoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.AceptarCobro.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoKeyPressed

    private void cobroefectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobroefectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoActionPerformed

    private void cobroefectivoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cobroefectivoFocusLost
        String ccobroefectivo = cobroefectivo.getText();
        String ctotalapagar = totalbrutoapagar.getText();
        ccobroefectivo = ccobroefectivo.replace(".", "").replace(",", ".");
        ctotalapagar = ctotalapagar.replace(".", "").replace(",", ".");
        if (Double.valueOf(ccobroefectivo) > Double.valueOf(ctotalapagar)) {
            sucambio.setText(formatea.format(Double.valueOf(ccobroefectivo) - Double.valueOf(ctotalapagar)));
        } else {
            sucambio.setText("0");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoFocusLost

    private void direccionpropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionpropietarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionpropietarioActionPerformed

    private void alquilerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alquilerKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.garage.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_alquilerKeyPressed

    private void garageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_garageKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.expensa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.alquiler.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_garageKeyPressed

    private void expensaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_expensaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.comision.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.garage.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_expensaKeyPressed

    private void comisionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comisionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.multa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.expensa.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_comisionKeyPressed

    private void multaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_multaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.garantia.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.comision.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_multaKeyPressed

    private void garantiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_garantiaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.fondo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.multa.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_garantiaKeyPressed

    private void fondoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fondoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.llave.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.garantia.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_fondoKeyPressed

    private void llaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_llaveKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.otros.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fondo.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_llaveKeyPressed

    private void otrosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_otrosKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            //Si pulso enter o cursor abajo obtiene el foco el sgte objeto
            this.importe_pagado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.llave.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_otrosKeyPressed

    private void descuentogastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descuentogastosActionPerformed
        if (this.descuentogastos.isSelected()) {
            int totalRow = modelo.getRowCount();
            totalRow -= 1;
            for (int i = 0; i <= (totalRow); i++) {
                this.jTable1.setValueAt(0, i, 10); //COLUMNA GASTOS

                //     this.jTable1.setValueAt(0, i, 9); //COLUMNA INTERES MORATORIO
                //     this.jTable1.setValueAt(0, i, 19); //COLUMNA INTERES PUNITORIO
                String cSaldo = this.jTable1.getValueAt(i, 8).toString(); // COLUMNA SALDO
                String cIntMora = this.jTable1.getValueAt(i, 9).toString();// INTERES MORATORIO
                String cIntPuni = this.jTable1.getValueAt(i, 19).toString();// INTERES PUNITORIO

                cSaldo = cSaldo.replace(".", "");
                cSaldo = cSaldo.replace(",", ".");

                cIntMora = cIntMora.replace(".", "");
                cIntMora = cIntMora.replace(",", ".");

                cIntPuni = cIntPuni.replace(".", "");
                cIntPuni = cIntPuni.replace(",", ".");

                double nItemT = Double.valueOf(cSaldo) + Double.valueOf(cIntMora) + Double.valueOf(cIntPuni);

                //  this.jTable1.setValueAt(formato.format(nItemT), i, 11);
            }
            //this.sumarcobros();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_descuentogastosActionPerformed

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Factura");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Denominación del Inquilino");
        modelo.addColumn("Moneda");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Cotización");
        modelo.addColumn("Total Cobro");
        modelo.addColumn("Asiento");
        modelo.addColumn("Cobrador");
        modelo.addColumn("Usuario");

        int[] anchos = {3, 100, 90, 90, 200, 100, 100, 100, 100, 90, 200, 200};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        jTable1.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        Font font = new Font("Arial", Font.BOLD, 10);
        this.jTable1.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.jTable1.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.jTable1.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
    }

    //TITULO DETALLE
    public void CargarTituloDetalle() {
        modelodetalle.addColumn("Id.");      //0
        modelodetalle.addColumn("Propietario");//1
        modelodetalle.addColumn("Edificio");//2
        modelodetalle.addColumn("Ubicación");//3
        modelodetalle.addColumn("Vence");//4
        modelodetalle.addColumn("Total a Pagar");//5
        modelodetalle.addColumn("Su Pago");//6
        modelodetalle.addColumn("Alquiler");//7
        modelodetalle.addColumn("Garaje");//8
        modelodetalle.addColumn("Expensas");//9
        modelodetalle.addColumn("Multa");//10
        modelodetalle.addColumn("Comisión");//11
        modelodetalle.addColumn("Garantia");//12
        modelodetalle.addColumn("Fondo");//13
        modelodetalle.addColumn("Llave");//14
        modelodetalle.addColumn("Otros");//15
        modelodetalle.addColumn("IVA Alquiler");//16
        modelodetalle.addColumn("IVA Mora");//17
        modelodetalle.addColumn("IVA Comisión");//18
        modelodetalle.addColumn("IVA Garage");//19
        modelodetalle.addColumn("IVA Expensa");//20
        modelodetalle.addColumn("DOCUMENTO");//21
        modelodetalle.addColumn("Edificio");//22
        modelodetalle.addColumn("NUMEROCUOTA");//23
        modelodetalle.addColumn("CUOTA");//24
        modelodetalle.addColumn("COMPROBANTE");//25
        modelodetalle.addColumn("TIPO UNIDAD");//26

        int[] anchos = {0, 150, 150, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1};
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            this.tablaalquiler.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaalquiler.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaalquiler.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.tablaalquiler.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaalquiler.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablaalquiler.getColumnModel().getColumn(21).setMaxWidth(0);
        this.tablaalquiler.getColumnModel().getColumn(21).setMinWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);

        this.tablaalquiler.getColumnModel().getColumn(22).setMaxWidth(0);
        this.tablaalquiler.getColumnModel().getColumn(22).setMinWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(22).setMaxWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(22).setMinWidth(0);

        this.tablaalquiler.getColumnModel().getColumn(23).setMaxWidth(0);
        this.tablaalquiler.getColumnModel().getColumn(23).setMinWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(23).setMaxWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(23).setMinWidth(0);

        this.tablaalquiler.getColumnModel().getColumn(24).setMaxWidth(0);
        this.tablaalquiler.getColumnModel().getColumn(24).setMinWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(24).setMaxWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(24).setMinWidth(0);

        this.tablaalquiler.getColumnModel().getColumn(25).setMaxWidth(0);
        this.tablaalquiler.getColumnModel().getColumn(25).setMinWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(25).setMaxWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(25).setMinWidth(0);

        this.tablaalquiler.getColumnModel().getColumn(26).setMaxWidth(0);
        this.tablaalquiler.getColumnModel().getColumn(26).setMinWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(26).setMaxWidth(0);
        this.tablaalquiler.getTableHeader().getColumnModel().getColumn(26).setMinWidth(0);

        this.tablaalquiler.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(11).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(12).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(13).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(16).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(17).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(19).setCellRenderer(TablaRenderer);
        this.tablaalquiler.getColumnModel().getColumn(20).setCellRenderer(TablaRenderer);

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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cobranza_alquileres().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCaja;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarCobrador;
    private javax.swing.JButton AceptarCobro;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Anular;
    private javax.swing.JDialog BCaja;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BCobrador;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JTextField BuscarMoneda;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JButton GrabarPagoParcial;
    private javax.swing.JButton Listar;
    private javax.swing.JTextField Modo;
    private javax.swing.JButton MostrarCuentas;
    private javax.swing.JDialog PagoParcial;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCaja;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCobrador;
    private javax.swing.JButton SalirCobranza;
    private javax.swing.JButton SalirFormaCobro;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirPagoParcial;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JFormattedTextField alquiler;
    private javax.swing.JCheckBox boxcuotas;
    private javax.swing.JButton buscarCaja;
    private javax.swing.JButton buscarCobrador;
    private javax.swing.JButton buscarMoneda;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JTextField caja;
    private javax.swing.JComboBox<String> cargobanco;
    private javax.swing.JComboBox<String> cargoemisor;
    private javax.swing.JTextField catastro;
    private javax.swing.JTextField cliente;
    private javax.swing.JTextField cobrador;
    private javax.swing.JFormattedTextField cobrocheque;
    private javax.swing.JFormattedTextField cobroefectivo;
    private javax.swing.JComboBox combocaja;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combocobrador;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JFormattedTextField comision;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JCheckBox descuentogastos;
    private javax.swing.JDialog detalle_cobro_alquiler;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField direccionpropietario;
    private com.toedter.calendar.JDateChooser emisioncheque;
    private com.toedter.calendar.JDateChooser emisiontarjeta;
    private javax.swing.JFormattedTextField expensa;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JFormattedTextField fondo;
    private javax.swing.JFormattedTextField garage;
    private javax.swing.JFormattedTextField garantia;
    private javax.swing.JFormattedTextField gravadas10;
    private javax.swing.JFormattedTextField gravadas5;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idcontrol;
    private javax.swing.JFormattedTextField importe_pagado;
    private javax.swing.JDialog ingresar_cobros;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCaja;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarCobrador;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private javax.swing.JFormattedTextField llave;
    private javax.swing.JTextField moneda;
    private javax.swing.JFormattedTextField multa;
    private javax.swing.JTextField nombrecaja;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombrecobrador;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nrocheque;
    private javax.swing.JTextField nrorecibo;
    private javax.swing.JTextField nrotarjeta;
    private javax.swing.JFormattedTextField otros;
    private javax.swing.JFormattedTextField pagotarjeta;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JTextField propietario;
    private javax.swing.JButton refrescar;
    private javax.swing.JTextField ruc;
    private javax.swing.JFormattedTextField sucambio;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTable tablaalquiler;
    private javax.swing.JTable tablacaja;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacobrador;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JFormattedTextField totalapagar;
    private javax.swing.JFormattedTextField totalbrutoapagar;
    private javax.swing.JFormattedTextField totalcobro;
    private javax.swing.JFormattedTextField totalexentas;
    private com.toedter.calendar.JDateChooser vencimiento;
    // End of variables declaration//GEN-END:variables

    private class GrillaCobroAlquiler extends Thread {

        public void run() {

            Date dFechaInicio = ODate.de_java_a_sql(FechaInicial.getDate());
            Date dFechaFinal = ODate.de_java_a_sql(FechaFinal.getDate());

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelo.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelo.removeRow(0);
            }

            cobranzaDAO DAO = new cobranzaDAO();
            try {
                for (cobranza orden : DAO.MostrarxDiaCobro(dFechaInicio, dFechaFinal, 1)) {
                    String Datos[] = {orden.getIdpagos(), orden.getFormatofactura(), formatoFecha.format(orden.getFecha()), String.valueOf(orden.getCliente().getCodigo()), orden.getCliente().getNombre(), orden.getMoneda().getEtiqueta(), orden.getSucursal().getNombre(), formatea.format(orden.getCotizacionmoneda()), formatea.format(orden.getTotalpago()), String.valueOf(orden.getAsiento()), orden.getCodusuario().getLast_name()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            jTable1.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = jTable1.getRowCount();
            if (cantFilas > 0) {
                Anular.setEnabled(true);
                Listar.setEnabled(true);
            } else {
                Anular.setEnabled(false);
                Listar.setEnabled(false);
            }
        }
    }

    private class GrillaSucursal extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelosucursal.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelosucursal.removeRow(0);
            }

            sucursalDAO DAOSUC = new sucursalDAO();
            try {
                for (sucursal suc : DAOSUC.todos()) {
                    String Datos[] = {String.valueOf(suc.getCodigo()), suc.getNombre()};
                    modelosucursal.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablasucursal.setRowSorter(new TableRowSorter(modelosucursal));
            int cantFilas = tablasucursal.getRowCount();
        }
    }

    private class GrillaCliente extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todos()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getDireccion(), cli.getRuc()};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class GrillaMoneda extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelomoneda.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelomoneda.removeRow(0);
            }
            monedaDAO DAOMON = new monedaDAO();
            try {
                for (moneda mo : DAOMON.todos()) {
                    String Datos[] = {String.valueOf(mo.getCodigo()), mo.getNombre(), formatea.format(mo.getVenta())};
                    modelomoneda.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablamoneda.setRowSorter(new TableRowSorter(modelomoneda));
            int cantFilas = tablamoneda.getRowCount();
        }
    }

    private class GrillaCaja extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocaja.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocaja.removeRow(0);
            }
            cajaDAO DAOCAJA = new cajaDAO();
            try {
                for (caja ca : DAOCAJA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre(), formatosinpunto.format(ca.getFactura()), ca.getExpedicion(), formatosinpunto.format(ca.getTimbrado()), formatoFecha.format(ca.getIniciotimbrado()), formatoFecha.format(ca.getVencetimbrado())};
                    modelocaja.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacaja.setRowSorter(new TableRowSorter(modelocaja));
            int cantFilas = tablacaja.getRowCount();
        }
    }

    private class GrillaCobrador extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocobrador.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocobrador.removeRow(0);
            }
            cobradorDAO COBCAJA = new cobradorDAO();
            try {
                for (cobrador co : COBCAJA.todos()) {
                    String Datos[] = {String.valueOf(co.getCodigo()), co.getNombre()};
                    modelocobrador.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacobrador.setRowSorter(new TableRowSorter(modelocobrador));
            int cantFilas = tablacobrador.getRowCount();
        }
    }

    private class GrillaCuenta extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            Date dFecha = ODate.de_java_a_sql(fecha.getDate());

            int cantidadRegistro = modelodetalle.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodetalle.removeRow(0);
            }
            cuenta_clienteDAO COBCAJA = new cuenta_clienteDAO();
            try {
                for (cuenta_clientes co : COBCAJA.CuentasInmobiliarias(Integer.valueOf(cliente.getText()), Integer.valueOf(cliente.getText()), dFecha)) {

                    String Datos[] = {co.getIddocumento(),
                        co.getEdificio().getInmueble().getNombrepropietario(),
                        co.getEdificio().getInmueble().getNomedif(),
                        co.getEdificio().getUbicacion(),
                        formatoFecha.format(co.getVencimiento()),
                        formatea.format(co.getSaldo()),
                        "0",
                        formatea.format(co.getAlquiler()),
                        formatea.format(co.getGarage()),
                        formatea.format(co.getExpensa()),
                        formatea.format(co.getMora()),
                        formatea.format(co.getComision()),
                        formatea.format(co.getGarantia()),
                        formatea.format(co.getFondo()),
                        formatea.format(co.getLlave()),
                        formatea.format(co.getOtros()),
                        formatea.format(co.getIvaalquiler()),
                        formatea.format(co.getIvamulta()),
                        formatea.format(co.getIvacomision()),
                        formatea.format(co.getIvagarage()),
                        formatea.format(co.getIvaexpensa()),
                        String.valueOf(co.getDocumento()),
                        String.valueOf(co.getEdificio().getIdunidad()),
                        String.valueOf(co.getNumerocuota()),
                        String.valueOf(co.getCuota()),
                        String.valueOf(co.getComprobante().getCodigo()),
                        String.valueOf(co.getEdificio().getTipunid())};
                    modelodetalle.addRow(Datos);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            tablaalquiler.setRowSorter(new TableRowSorter(modelodetalle));
            int cantFilas = tablaalquiler.getRowCount();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.BuscadorImpresora;
import Clases.CalcularRuc;
import Clases.Config;
import Clases.ConvertirMayusculas;
import Clases.FormatoTabla;
import Clases.FormatoTablaStock;
import Clases.UUID;
import Clases.numero_a_letras;
import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import DAO.albumfoto_productoDAO;
import DAO.aperturaDAO;
import DAO.bancosDAO;
import DAO.cajaDAO;
import DAO.clienteDAO;
import DAO.cobradorDAO;
import DAO.cobranzaDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.cuenta_clienteDAO;
import DAO.detalle_forma_cobroDAO;
import DAO.detalle_preventaDAO;
import DAO.divisaDAO;
import DAO.extraccionDAO;
import DAO.formapagoDAO;
import DAO.giraduriaDAO;
import DAO.localidadDAO;
import DAO.monedaDAO;
import DAO.obraDAO;
import DAO.preventaDAO;
import DAO.productoDAO;
import DAO.stockDAO;
import DAO.sucursalDAO;
import DAO.usuarioDAO;
import DAO.vendedorDAO;
import DAO.ventaDAO;
import Modelo.Tablas;
import Modelo.albumfoto_producto;
import Modelo.apertura;
import Modelo.banco;
import Modelo.caja;
import Modelo.cliente;
import Modelo.cobrador;
import Modelo.cobranza;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.detalle_preventa;
import Modelo.divisa;
import Modelo.extraccion;
import Modelo.formapago;
import Modelo.giraduria;
import Modelo.localidad;
import Modelo.moneda;
import Modelo.obra;
import Modelo.preventa;
import Modelo.producto;
import Modelo.stock;
import Modelo.sucursal;
import Modelo.usuario;
import Modelo.vendedor;
import Modelo.venta;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
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
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import jssc.SerialPortException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class punto_venta_multicaja_ferremax extends javax.swing.JFrame {

    String cValorCantidad, cValorPrecio, cValorCosto, Sucursal, Caja, cTotalNeto, cIva = null;
    String cSql = null;
    Conexion con = null;
    Statement stm, stmprod, stmcli, smphisto = null;
    Date dEmision;
    Date dVence;
    Date dConfirma;

    Tablas modelo = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modelocaja = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelocomprobante = new Tablas();
    Tablas modeloproducto = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelovendedor = new Tablas();
    Tablas modeloformapago = new Tablas();
    Tablas modelopreventa = new Tablas();
    Tablas modelobanco = new Tablas();
    Tablas modelostock = new Tablas();
    Tablas modelopago = new Tablas();

    BDConexion BD = new BDConexion();
    JScrollPane scroll = new JScrollPane();

    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltrocomprobante, trsfiltromoneda, trsfiltroproducto, trsfiltrovendedor, trsfiltroformapago, trsfiltrobanco, trsfiltrocaja, trsfiltropreventa, trsfiltropago;

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formatea = new DecimalFormat("###,###");
    DecimalFormat formatoextranjero = new DecimalFormat("###,###.##");
    DecimalFormat formatosinpunto = new DecimalFormat("############");
    DecimalFormat formatcantidad = new DecimalFormat("######.###");
    ObtenerFecha ODate = new ObtenerFecha();
    int nFila, Registros = 0;
    String cCotizacionDolar, cCotizacionReal, cCotizacionPeso = null;
    Object[] fila = new Object[6];
    String cCosto, cCantidad, cPrecio, cImpiva, cMonto, cProducto = null;
    double nMonto, nImpiva;
    double nPorcentajeIVA = 0.00;
    int indiceTabla = 0;
    int nPosicionPrecioMinorista = -1;
    int nPosicionPrecioCosto = -1;
    int nListaPrecio;
    ImageIcon imagenfondo = new ImageIcon("src/Iconos/producto.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");
    ImageIcon iconoborrar = new ImageIcon("src/Iconos/eliminar.png");
    ImageIcon iconocancelar = new ImageIcon("src/Iconos/cancelar.png");
    ImageIcon iconoaceptar = new ImageIcon("src/Iconos/aceptar.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon imagen_dolar = new ImageIcon("src/Iconos/icono_dolar.jpg");
    ImageIcon imagen_real = new ImageIcon("src/Iconos/icono_real.jpg");
    ImageIcon imagen_peso = new ImageIcon("src/Iconos/icono_peso.jpg");
    Calendar c2 = new GregorianCalendar();
    int nGestorBusqueda = 0;
    int nItemProductos = 0;
    int nTipoImpresora = 0;
    int nTipoBuscarCliente = 0;
    int nSupervisorCaja = 0;

    /**
     * Creates new form ingreso_punto_venta
     */
    public punto_venta_multicaja_ferremax() {
        initComponents();
        this.imagendolar.setIcon(imagen_dolar);
        this.imagenreal.setIcon(imagen_real);
        this.imagenpeso.setIcon(imagen_peso);
        this.BuscarCliente.setIcon(iconobuscar);
        this.buscarbanco.setIcon(iconobuscar);
        this.buscarmonedaorigen.setIcon(iconobuscar);
        this.buscarmonedadestino.setIcon(iconobuscar);
        this.BuscarClienteDivisa.setIcon(iconobuscar);
        this.limitecredito.setVisible(false);
        this.estacompra.setVisible(false);
        this.disponible.setVisible(false);
        this.totalcompleto.setVisible(false);
        this.precio.setVisible(true);
        this.control.setVisible(false);
        this.preventa.setText("0");
        this.imprimirpos.setVisible(false);
        this.reimpresion.setVisible(false);
        this.ingresopos.setIcon(iconoaceptar);
        this.AceptarSupervisor.setIcon(iconoaceptar);
        this.SalirSupervisor.setIcon(iconosalir);
        this.salirpos.setIcon(iconosalir);
        this.salirpuntoventa.setIcon(iconosalir);
        this.SalirCliente.setIcon(iconosalir);
        this.GrabarCliente.setIcon(iconograbar);
        this.BusCodCli.setIcon(iconobuscar);
        this.GrabarCobroPV.setIcon(iconograbar);
        this.SalirCobroPV.setIcon(iconosalir);
        this.CancelarVenta.setIcon(iconocancelar);
        this.GrabarVenta.setIcon(iconograbar);
        this.AceptarPreventa.setIcon(iconoaceptar);
        this.SalirPreventa.setIcon(iconosalir);
        this.BuscarVendedor.setIcon(iconobuscar);
        this.buscarCliente.setIcon(iconobuscar);
        this.buscarcomprobante.setIcon(iconobuscar);
        this.buscarpreventa.setIcon(iconobuscar);
        this.buscarproducto.setIcon(iconobuscar);
        this.buscarsucursal.setIcon(iconobuscar);
        this.buscarobra.setIcon(iconobuscar);
        this.buscarcaja.setIcon(iconobuscar);
        this.BuscarVendedorAcceso.setIcon(iconobuscar);
        this.BuscarCajaAcceso.setIcon(iconobuscar);
        this.BuscarSucAcceso.setIcon(iconobuscar);
        this.BorrarItemVenta.setIcon(iconoborrar);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.setLocationRelativeTo(null);
        this.vencetimbrado.setCalendar(c2);
        this.fecha.setCalendar(c2);
        this.fechaingreso.setCalendar(c2);
        this.nacimiento.setCalendar(c2);
        this.creferencia.setVisible(false);
        this.referencia.setVisible(false);
        this.iniciotimbrado.setVisible(false);
        this.vencetimbrado.setVisible(false);
        this.nrotimbrado.setVisible(false);
        this.precio.setVisible(false);
        this.iva.setVisible(false);
        this.exentas.setVisible(false);
        this.gravadas10.setVisible(false);
        this.gravadas5.setVisible(false);
        this.AgregarItem.setVisible(false);
        this.CargarTituloCobranza();
        this.TituloComprobante();
        this.TitCaja();
        this.TitClie();
        this.TitSuc();
        this.TituloProductos();
        this.TituloPreventas();
        this.TitVendedor();
        this.TituloPOS();
        this.TituloBanco();
        this.TituloFormaPago();
        this.limpiarobjetos();
        this.TitStock();

        //CONFIGURACION PARA CONTROL DE ITEMS POR FACTURA
        configuracionDAO configDAO = new configuracionDAO();
        configuracion configbase = null;
        configbase = configDAO.consultar();
        nItemProductos = configbase.getItemfacturas();
        nTipoImpresora = configbase.getTipo_factura_impresion();
        nListaPrecio = configbase.getLista_precio();

        ///PRODUCTOS
        //CLIENTES
        GrillaCliente grillacl = new GrillaCliente();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();
        //COTIZACION DE MONEDAS
        CargarCotizacion cotizar = new CargarCotizacion();
        Thread hilocotizar = new Thread(cotizar);
        hilocotizar.start();

        GrillaCaja grillaca = new GrillaCaja();
        Thread hilocaja = new Thread(grillaca);
        hilocaja.start();

        GrillaFormaPago grillapa = new GrillaFormaPago();
        Thread hilopago = new Thread(grillapa);
        hilopago.start();
    }

    private void TitStock() {
        modelostock.addColumn("Sucursal");
        modelostock.addColumn("Stock");

        int[] anchos = {200, 90};
        for (int i = 0; i < modelostock.getColumnCount(); i++) {
            tablastock.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablastock.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablastock.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablastock.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablastock.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
    }

    private void TituloBanco() {
        modelobanco.addColumn("Código");
        modelobanco.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelobanco.getColumnCount(); i++) {
            tablabanco.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablabanco.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablabanco.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablabanco.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablabanco.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TituloFormaPago() {
        modelopago.addColumn("Código");
        modelopago.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelopago.getColumnCount(); i++) {
            tablapago.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapago.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapago.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapago.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablapago.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void CargaPreventa() {
        int cantidadRegistro = tabladetalle.getRowCount();
        double nTotalItem = 0.00;
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        detalle_preventaDAO detDAO = new detalle_preventaDAO();
        try {
            for (detalle_preventa detpre : detDAO.MostrarDetalle(Integer.valueOf(preventa.getText()))) {
                int nItem = tabladetalle.getRowCount() + 1;
                nTotalItem = detpre.getCantidad() * detpre.getPrecio();
                if (nTotalItem != detpre.getMonto()) {
                    JOptionPane.showMessageDialog(null, "En el Item "+nItem+ " hay Diferencia, Verifique");
                }
                String Datos[] = {String.valueOf(String.valueOf(nItem)),
                    detpre.getCodprod().getCodigo(),
                    detpre.getCodprod().getNombre(),
                    formatcantidad.format(detpre.getCantidad()),
                    formatea.format(detpre.getPrecio()),
                    formatea.format(detpre.getMonto()),
                    formatea.format(detpre.getPorcentaje())};
                modelo.addRow(Datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
        sumatoria();
        tabladetalle.setRowSorter(new TableRowSorter(modelo));
        int cantFilas = tabladetalle.getRowCount();
    }

    private void limpiarmedidas() {
        ancho.setText("0");
        espesor.setText("0");
        longitud.setText("0");
        subtotal.setText("0");
        total.setText("0");
        piezas.setText("0");
    }

    private void limpiarproductos() {
        Config.cCodProducto = "";
        Config.cNombreProducto = "";
        Config.nPrecio = 0;
        Config.nIvaProducto = 0;
    }

    public void CargarTituloCobranza() {
        modelodetalle.addColumn("Id.");
        modelodetalle.addColumn("N° Documento");
        modelodetalle.addColumn("Emisión");
        modelodetalle.addColumn("Vence");
        modelodetalle.addColumn("Comprobante");
        modelodetalle.addColumn("Cuota");
        modelodetalle.addColumn("Saldo");
        modelodetalle.addColumn("Su Pago");
        modelodetalle.addColumn("CodCom");
        modelodetalle.addColumn("NumCuota");
        modelodetalle.addColumn("nCuota");
        modelodetalle.addColumn("nMoneda");

        int[] anchos = {0, 90, 90, 90, 100, 90, 100, 100, 1, 1, 1, 1};
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            this.tablacobranza.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacobranza.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacobranza.getTableHeader().setFont(new Font("Arial Black", 1, 8));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer TablaCenter = new DefaultTableCellRenderer();
        TablaCenter.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        this.tablacobranza.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablacobranza.getColumnModel().getColumn(2).setCellRenderer(TablaCenter);
        this.tablacobranza.getColumnModel().getColumn(3).setCellRenderer(TablaCenter);
        this.tablacobranza.getColumnModel().getColumn(5).setCellRenderer(TablaCenter);
        this.tablacobranza.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.tablacobranza.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablacobranza.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);

        this.tablacobranza.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacobranza.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablacobranza.getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablacobranza.getColumnModel().getColumn(8).setMinWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);

        this.tablacobranza.getColumnModel().getColumn(9).setMaxWidth(0);
        this.tablacobranza.getColumnModel().getColumn(9).setMinWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(9).setMinWidth(0);

        this.tablacobranza.getColumnModel().getColumn(10).setMaxWidth(0);
        this.tablacobranza.getColumnModel().getColumn(10).setMinWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(10).setMinWidth(0);

        this.tablacobranza.getColumnModel().getColumn(11).setMaxWidth(0);
        this.tablacobranza.getColumnModel().getColumn(11).setMinWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);
    }

    private void GrabarVenta() {
        Runtime rt = Runtime.getRuntime();
        Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
        Date FechaVenceTimbrado = ODate.de_java_a_sql(vencetimbrado.getDate());
        Date FechaVence = ODate.de_java_a_sql(fecha.getDate());

        //CAMBIAMOS EL VENCIMIENTO SI LA VENTA ES CREDITO
        if (nombrecomprobante.getText().equals("VENTA CREDITO")) {
            Calendar calendar = Calendar.getInstance();
            int mes = fecha.getCalendar().get(Calendar.MONTH) + 1;
            int dia = fecha.getCalendar().get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0
            if (mes == 2 && dia == 28) {
                calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días                            //el vencimiento
            }
            FechaVence = ODate.de_java_a_sql(calendar.getTime());
        }

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

        preventaDAO prevDAO = new preventaDAO();

        try {
            suc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
            cli = cliDAO.buscarId(Integer.valueOf(cliente.getText()));
            com = coDAO.buscarId(Integer.valueOf(comprobante.getText()));
            mn = mnDAO.buscarId(1);
            gi = giDAO.buscarId(1); //verificar y tener en cuenta para ingreso por teclado o asociar a Cliente
            ve = veDAO.buscarId(Integer.valueOf(vendedor.getText()));
            ca = caDAO.buscarId(Integer.valueOf(caja.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        venta v = new venta();
        ventaDAO grabarventa = new ventaDAO();
        //Capturamos los Valores BigDecimal
        String cExentas = exentas.getText();
        cExentas = cExentas.replace(".", "").replace(",", ".");
        BigDecimal nExentas = new BigDecimal(cExentas);

        String cGravadas10 = gravadas10.getText();
        cGravadas10 = cGravadas10.replace(".", "").replace(",", ".");
        BigDecimal nGravadas10 = new BigDecimal(cGravadas10);

        String cGravadas5 = gravadas5.getText();
        cGravadas5 = cGravadas5.replace(".", "").replace(",", ".");
        BigDecimal nGravadas5 = new BigDecimal(cGravadas5);

        String cTotalValores = totalneto.getText();
        cTotalValores = cTotalValores.replace(".", "").replace(",", ".");
        BigDecimal nSupago = new BigDecimal(cTotalValores);

        cTotalNeto = totalneto.getText();
        cTotalNeto = cTotalNeto.replace(".", "").replace(",", ".");
        BigDecimal nTotalNeto = new BigDecimal(cTotalNeto);

        String cSucambio = sucambio.getText();
        cSucambio = cSucambio.replace(".", "").replace(",", ".");
        BigDecimal nSucambio = new BigDecimal(cSucambio);

        String cCotizacion = "1";
        String cNumeroFactura = factura.getText();
        cNumeroFactura = cNumeroFactura.replace("-", "");
        BigDecimal nCotizacion = new BigDecimal(cCotizacion);
        //SE CAPTURA SOLO LA PARTE DE LA NUMERACION DE LA FACTURA PARA GUARDARLO EN CAJA
        String cContadorFactura = cNumeroFactura.substring(6, 13);

        v.setCreferencia(referencia.getText());
        v.setCentro(Integer.valueOf(obra.getText()));
        v.setFecha(FechaProceso);
        v.setFormatofactura(factura.getText());
        v.setFactura(Double.valueOf(cNumeroFactura));
        v.setVencimiento(FechaVence);
        v.setCliente(cli);
        v.setMoneda(mn);
        v.setGiraduria(gi);
        v.setComprobante(com);
        v.setSucursal(suc);
        v.setCotizacion(nCotizacion);
        v.setVendedor(ve);
        v.setCaja(ca);
        v.setExentas(nExentas);
        v.setGravadas10(nGravadas10);
        v.setGravadas5(nGravadas5);
        v.setTotalneto(nTotalNeto);
        v.setCuotas(0);
        v.setFinanciado(nTotalNeto.subtract(nSupago));
        v.setObservacion(this.observacion.getText());
        v.setSupago(nSupago);
        v.setVencimientotimbrado(FechaVenceTimbrado);
        v.setNrotimbrado(ca.getTimbrado());
        v.setIdusuario(Integer.valueOf(Config.CodUsuario));
        v.setPreventa(Integer.valueOf(preventa.getText()));
        v.setSucambio(nSucambio);
        v.setTurno(this.turno.getSelectedIndex() + 1);
        v.setCentro(Integer.valueOf(obra.getText()));

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
        int totalRow = modelo.getRowCount();
        totalRow -= 1;
        String detalleformapago = "[";
        String detalle = "[";
        for (int i = 0; i <= (totalRow); i++) {
            //Capturo y valido Producto
            cProducto = String.valueOf(modelo.getValueAt(i, 1));
            try {
                p = producto.BuscarProductoBasico(cProducto);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            //Capturo cantidad    
            cCantidad = String.valueOf(modelo.getValueAt(i, 3));
            cCantidad = cCantidad.replace(".", "").replace(",", ".");
            //Porcentaje
            civa = String.valueOf(modelo.getValueAt(i, 6));
            civa = civa.replace(".", "").replace(",", ".");
            //Precio
            cPrecio = String.valueOf(modelo.getValueAt(i, 4));
            cPrecio = cPrecio.replace(".", "").replace(",", ".");
            //Total Item
            cMonto = String.valueOf(modelo.getValueAt(i, 5));
            cMonto = cMonto.replace(".", "").replace(",", ".");
            cCosto = String.valueOf(p.getCosto());
            switch (p.getIvaporcentaje().intValue()) {
                case 10:
                    cIvaItem = String.valueOf(Math.round(Double.valueOf(cMonto) / 11));
                    break;
                case 5:
                    cIvaItem = String.valueOf(Math.round(Double.valueOf(cMonto) / 21));
                    break;
                case 0:
                    cIvaItem = "0";
                    break;
            }
            String linea = "{dreferencia : '" + referencia.getText() + "',"
                    + "codprod : '" + cProducto + "',"
                    + "prcosto : " + cCosto + ","
                    + "cantidad : " + cCantidad + ","
                    + "precio : " + cPrecio + ","
                    + "monto : " + cMonto + ","
                    + "impiva: " + cIvaItem + ","
                    + "porcentaje : " + p.getIvaporcentaje().toString() + ","
                    + "suc : " + Integer.valueOf(sucursal.getText()) + "},";
            detalle += linea;
        }
        if (!detalle.equals("[")) {
            detalle = detalle.substring(0, detalle.length() - 1);
        }
        detalle += "]";
        System.out.println(detalle);
        String detacuota = "[";
        //SI LA VENTA FUERA A CREDITO SE GENERA CUENTA A COBRAR
        String iddoc = null;
        String cImporteCuota = null;
        String lineacuota = null;

        if (Integer.valueOf(this.comprobante.getText()) == 2) {
            cImporteCuota = String.valueOf(Double.valueOf(cTotalNeto));
            detacuota = "[";
            iddoc = UUID.crearUUID();
            iddoc = iddoc.substring(1, 25);
            lineacuota = "{iddocumento : '" + iddoc + "',"
                    + "creferencia : '" + referencia.getText() + "',"
                    + "documento : " + cNumeroFactura + ","
                    + "fecha : " + FechaProceso + ","
                    + "vencimiento : " + FechaVence + ","
                    + "cliente : " + cliente.getText() + ","
                    + "sucursal: " + sucursal.getText() + ","
                    + "moneda : " + v.getMoneda().getCodigo() + ","
                    + "comprobante : " + v.getComprobante().getCodigo() + ","
                    + "vendedor : " + vendedor.getText() + ","
                    + "idedificio : " + obra.getText() + ","
                    + "importe : " + cTotalNeto + ","
                    + "numerocuota : " + 1 + ","
                    + "cuota : " + 1 + ","
                    + "saldo : " + cTotalNeto
                    + "},";
            detacuota += lineacuota;
            if (!detacuota.equals("[")) {
                detacuota = detacuota.substring(0, detacuota.length() - 1);
            }
            detacuota += "]";
        }
        if (Integer.valueOf(this.comprobante.getText()) == 3 && Integer.valueOf(this.cliente.getText()) != 1) {
            cImporteCuota = String.valueOf(Double.valueOf(cTotalNeto));

            detacuota = "[";
            iddoc = UUID.crearUUID();
            iddoc = iddoc.substring(1, 25);
            lineacuota = "{iddocumento : '" + iddoc + "',"
                    + "creferencia : '" + referencia.getText() + "',"
                    + "documento : " + cNumeroFactura + ","
                    + "fecha : " + FechaProceso + ","
                    + "vencimiento : " + FechaVence + ","
                    + "cliente : " + cliente.getText() + ","
                    + "sucursal: " + sucursal.getText() + ","
                    + "moneda : " + v.getMoneda().getCodigo() + ","
                    + "comprobante : " + v.getComprobante().getCodigo() + ","
                    + "vendedor : " + vendedor.getText() + ","
                    + "idedificio : " + obra.getText() + ","
                    + "importe : " + cTotalNeto + ","
                    + "numerocuota : " + 1 + ","
                    + "cuota : " + 1 + ","
                    + "saldo : " + cTotalNeto
                    + "},";
            detacuota += lineacuota;
            if (!detacuota.equals("[")) {
                detacuota = detacuota.substring(0, detacuota.length() - 1);
            }
            detacuota += "]";
            System.out.println(detacuota);
        }

        if (Integer.valueOf(this.comprobante.getText()) == 1) {
            //SE CREA EL JSON PARA LA VENTA CONTADO
            String cBruto = totalbrutoapagar.getText();
            cBruto = cBruto.replace(".", "").replace(",", ".");
            String cCobroEfectivo = this.cobroefectivo.getText();
            cCobroEfectivo = cCobroEfectivo.replace(".", "").replace(",", ".");
            if (Double.valueOf(cCobroEfectivo) > Double.valueOf(cBruto)) {
                cCobroEfectivo = cBruto;
            }

            String linea = null;
            detalleformapago = "[";

            if (Double.valueOf(cCobroEfectivo) > 0) {
                linea = "{idmovimiento : " + referencia.getText() + ","
                        + "forma : " + "1" + ","
                        + "banco : " + "1" + ","
                        + "nrocheque : " + "XX" + ","
                        + "confirmacion: " + FechaProceso + ","
                        + "netocobrado : " + cCobroEfectivo + "},";
                detalleformapago += linea;
            }

            if (!detalleformapago.equals("[")) {
                detalleformapago = detalleformapago.substring(0, detalleformapago.length() - 1);
            }
            detalleformapago += "]";
        }
        try {
            //SE GRABA LA VENTA EN CABECERA DE VENTAS Y DETALLES DE VENTAS    
            grabarventa.AgregarFacturaVentaFerremax(Config.cToken, v, detalle);
            //SE MARCA LA PREVENTA COMO YA UTILIZADA EN CASO QUE HAYA SIDO UTILIZADA
            if (Integer.valueOf(preventa.getText()) > 0) {
                prevDAO.CerrarPreventa(Integer.valueOf(preventa.getText()));
            }
            //SE GESTIONA SI LA VENTA ES A CREDITO PARA GUARDARLO EN CUENTA CORRIENTE,
            //O SE GUARDA LA FORMA DE COBRO EN CASO QUE SEA VENTA AL CONTADO
            if (Integer.valueOf(this.comprobante.getText()) == 2) {
                cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                ctaDAO.guardarCuentaFerremax(detacuota);
            } else if (Integer.valueOf(this.comprobante.getText()) == 1) {
                detalle_forma_cobroDAO cobDAO = new detalle_forma_cobroDAO();
                cobDAO.guardarFormaPago(detalleformapago);
            }

            if (Integer.valueOf(this.comprobante.getText()) == 3 && Integer.valueOf(this.cliente.getText()) != 1) {
                cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
                ctaDAO.guardarCuentaFerremax(detacuota);
            }

            //SE SUMA UNO MAS AL NUMERO DE FACTURA EN LA CAJA CORRESPONDIENTE
            if (Integer.valueOf(this.comprobante.getText()) < 3) {
                caDAO.ActualizarFacturaCaja(Integer.valueOf(caja.getText()), Double.valueOf(cContadorFactura) + 1);
            } else {
                grabarventa.ActualizarNroNotaCredito(Integer.valueOf(sucursal.getText()), Double.valueOf(cContadorFactura) + 1);
            }

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (Integer.valueOf(this.comprobante.getText()) < 3) {
            this.imprimirpos.doClick();
        } else {
            this.impriminota();
            Object[] options = {"Salir", "ReImprimir"};
            int ret = JOptionPane.showOptionDialog(null, "Elige imprimir o cancelar", "Aviso",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            if (ret == 1) {
                this.impriminota();
            }
            this.limpiarobjetos();
            JOptionPane.showMessageDialog(null, "La transacción culminó con Éxito");
        }

        //this.Imprimir2();
        rt.gc();

    }

    private void FiltroPro() {
        trsfiltroproducto = new TableRowSorter(tablaproducto.getModel());
        tablaproducto.setRowSorter(trsfiltroproducto);
        trsfiltroproducto.setRowFilter(RowFilter.regexFilter(textoproducto.getText().toUpperCase(), indiceTabla));
    }

    private void limpiarobjetos() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = null;
        config = configDAO.consultar();
        UUID id = new UUID();
        cajaDAO cajDAO = new cajaDAO();
        caja ca = new caja();

        this.referencia.setText(UUID.crearUUID());
        this.referencia.setText(this.referencia.getText().substring(1, 25));
        this.sucursal_acceso.setText(String.valueOf(config.getSucursaldefecto().getCodigo()));
        this.nombresucursalacceso.setText(config.getSucursaldefecto().getNombre());
        this.sucursal.setText(String.valueOf(config.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(config.getSucursaldefecto().getNombre());
        this.caja_acceso.setText(String.valueOf(config.getCajadefecto().getCodigo()));
        this.nombrecajaacceso.setText(config.getCajadefecto().getNombre());
        try {
            System.out.println("CAJA " + this.caja_acceso.getText());
            ca = cajDAO.buscarId(Integer.valueOf(caja_acceso.getText()));
            this.caja.setText(String.valueOf(String.valueOf(ca.getCodigo())));
            this.nombrecaja.setText(ca.getNombre());

            this.nrotimbrado.setText(String.valueOf(config.getCajadefecto().getTimbrado()));
            this.vencetimbrado.setDate(config.getCajadefecto().getVencetimbrado());
            this.iniciotimbrado.setDate(config.getCajadefecto().getIniciotimbrado());
            System.out.println("EXPEDICION " + ca.getExpedicion());

            String cBoca = ca.getExpedicion().trim();
            Double nFactura = ca.getFactura();
            int n = (int) nFactura.doubleValue();
            String formatString = String.format("%%0%dd", 7);
            String formattedString = String.format(formatString, n);
            this.ingresofactura.setText(cBoca + "-" + formattedString);
            this.factura.setText(cBoca + "-" + formattedString);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        this.vendedor_acceso.setText(String.valueOf(config.getVendedordefecto().getCodigo()));
        this.nombrevendedoracceso.setText(config.getVendedordefecto().getNombre());

        this.nrotimbrado.setText(String.valueOf(config.getSucursaldefecto().getNrotimbrado()));

        this.vendedor.setText(String.valueOf(config.getVendedordefecto().getCodigo()));
        this.nombrevendedor.setText(config.getVendedordefecto().getNombre());

        this.comprobante.setText(String.valueOf(config.getComprobantedefecto().getCodigo()));
        this.nombrecomprobante.setText(String.valueOf(config.getComprobantedefecto().getNombre()));

        this.cliente.setText(String.valueOf(config.getClientedefecto().getCodigo()));
        this.nombrecliente.setText(config.getClientedefecto().getNombre());
        this.direccioncliente.setText(config.getClientedefecto().getDireccion());
        this.rucliente.setText(config.getClientedefecto().getRuc());
        this.limitecredito.setText("0");
        this.estacompra.setText("0");
        this.disponible.setText("0");
        this.preventa.setText("0");
        this.observacion.setText("");
        this.cobroefectivo.setText("0");
        this.sucambio.setText("0");
        this.totalneto.setText("0");
        this.total_dolar.setText("0");
        this.total_peso.setText("0");
        this.total_real.setText("0");
        this.codigoproducto.setText("");
        this.etiquetaproducto.setText("");
        this.observacion.setText("");
        this.precio.setText("0");
        this.cantidad.setText(formatea.format(1));
        this.obra.setText("0");
        this.nombreobra.setText("");
        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
    }

    public void filtrosuc(int nNumeroColumna) {
        trsfiltrosuc.setRowFilter(RowFilter.regexFilter(this.jTBuscarSucursal.getText(), nNumeroColumna));
    }

    private void limpiarItem() {
        this.codigoproducto.setText("");
        this.precio.setText("0");
        this.etiquetaproducto.setText("");
        this.cantidad.setText("1");
    }

    private void TitSuc() {
        modelosucursal.addColumn("Código");
        modelosucursal.addColumn("Nombre");
        modelosucursal.addColumn("Nro. Factura");
        modelosucursal.addColumn("Expedicion");
        modelosucursal.addColumn("N° Timbrado");
        modelosucursal.addColumn("Vence Timbrado");

        int[] anchos = {90, 200, 150, 120, 120, 120};
        for (int i = 0; i < modelosucursal.getColumnCount(); i++) {
            tablasucursal.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablasucursal.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablasucursal.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        this.tablasucursal.getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablasucursal.getColumnModel().getColumn(2).setMinWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);

        this.tablasucursal.getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablasucursal.getColumnModel().getColumn(3).setMinWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);

        this.tablasucursal.getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablasucursal.getColumnModel().getColumn(4).setMinWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);

        this.tablasucursal.getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablasucursal.getColumnModel().getColumn(5).setMinWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        this.tablasucursal.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablasucursal.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablasucursal.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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

    private void TitVendedor() {
        modelovendedor.addColumn("Código");
        modelovendedor.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelovendedor.getColumnCount(); i++) {
            tablavendedor.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablavendedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablavendedor.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablavendedor.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablavendedor.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void TituloComprobante() {
        modelocomprobante.addColumn("Código");
        modelocomprobante.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelocomprobante.getColumnCount(); i++) {
            tablacomprobante.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacomprobante.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacomprobante.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablacomprobante.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablacomprobante.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
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

    private void TituloPreventas() {
        modelopreventa.addColumn("Número"); //0
        modelopreventa.addColumn("Fecha");//1
        modelopreventa.addColumn("Nombre del Cliente");//2
        modelopreventa.addColumn("Importe");//3
        modelopreventa.addColumn("cliente");//4
        modelopreventa.addColumn("ruc");//5
        modelopreventa.addColumn("direccion");//6
        modelopreventa.addColumn("vendedor");//7
        modelopreventa.addColumn("nombrevendedor");//8
        modelopreventa.addColumn("comprobante");//9
        modelopreventa.addColumn("nombrecomprobante");//10
        modelopreventa.addColumn("observacion");//11
        modelopreventa.addColumn("obra");//12
        modelopreventa.addColumn("nombreobra");//13
        modelopreventa.addColumn("Descuento");//14
        modelopreventa.addColumn("Aprobado");//15
        modelopreventa.addColumn("Caja");//16

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        int[] anchos = {100, 90, 150, 100, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        for (int i = 0; i < modelopreventa.getColumnCount(); i++) {
            tablapreventa.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapreventa.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapreventa.getTableHeader().setFont(new Font("Arial Black", 1, 11));
        this.tablapreventa.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablapreventa.getColumnModel().getColumn(1).setCellRenderer(TablaCentro);
        this.tablapreventa.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);

        //recorro todas las columnas que voy a hacer invisibles
        for (int i = 4; i < 17; i++) {
            this.tablapreventa.getColumnModel().getColumn(i).setMaxWidth(0);
            this.tablapreventa.getColumnModel().getColumn(i).setMinWidth(0);
            this.tablapreventa.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
            this.tablapreventa.getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);
        }
        // Hacemos Invisible la Celda de Costos de los Productos

        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablapreventa.setFont(font);
    }

    private void TituloProductos() {
        modeloproducto.addColumn("Código");
        modeloproducto.addColumn("Descripción");
        modeloproducto.addColumn("Mayorista");
        modeloproducto.addColumn("Minorista");
        modeloproducto.addColumn("Stock");
        modeloproducto.addColumn("IVA");
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        int[] anchos = {90, 400, 110, 110, 80, 50};
        for (int i = 0; i < modeloproducto.getColumnCount(); i++) {
            tablaproducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaproducto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaproducto.getTableHeader().setFont(new Font("Arial Black", 1, 11));
        this.tablaproducto.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablaproducto.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.tablaproducto.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaproducto.setFont(font);
    }

    public void limpiarCliente() {
        this.codigo.setText("");
        this.nombre.setText("");
        this.ruc.setText("");
        this.celular.setText("");
        this.nacimiento.setCalendar(c2);
        this.direccion.setText("SD");
        this.fechaingreso.setCalendar(c2);
        this.control.setText("0");
        habilitado.setSelected(true);
    }

    public void limpiar() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = null;
        config = configDAO.consultar();
        this.vencetimbrado.setDate(config.getSucursaldefecto().getVencetimbrado());
        this.nrotimbrado.setText(config.getSucursaldefecto().getNrotimbrado());
//      this.factura.setText(formatosinpunto.format(config.getSucursaldefecto().getFactura()));

        this.vendedor.setText(String.valueOf(config.getVendedordefecto().getCodigo()));
        this.nombrevendedor.setText(config.getVendedordefecto().getNombre());

        this.comprobante.setText(String.valueOf(config.getComprobantedefecto().getCodigo()));
        this.nombrecomprobante.setText(String.valueOf(config.getComprobantedefecto().getNombre()));

        this.cliente.setText(String.valueOf(config.getClientedefecto().getCodigo()));
        this.rucliente.setText(config.getClientedefecto().getNombre());
        this.rucliente.setText(config.getClientedefecto().getDireccion());

        this.preventa.setText("0");
        this.totalneto.setText("0");
        this.totalcompleto.setText("0");

        if (config.getModificarprecio() == 0) {
            precio.setEnabled(false);
        } else {
            precio.setEnabled(true);
        }

    }

    public void filtropreventa(int nNumeroColumna) {
        trsfiltropreventa.setRowFilter(RowFilter.regexFilter(this.FiltrarPreventa.getText(), nNumeroColumna));
    }

    public void filtroformapago(int nNumeroColumna) {
        trsfiltroformapago.setRowFilter(RowFilter.regexFilter(this.jTBuscarForma.getText(), nNumeroColumna));
    }

    public void filtrovendedor(int nNumeroColumna) {
        trsfiltrovendedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarVendedor.getText(), nNumeroColumna));
    }

    public void filtroproducto(int nNumeroColumna) {
        trsfiltroproducto.setRowFilter(RowFilter.regexFilter(textoproducto.getText(), nNumeroColumna));
    }

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    public void filtrocomprobante(int nNumeroColumna) {
        trsfiltrocomprobante.setRowFilter(RowFilter.regexFilter(this.jTBuscarComprobante.getText(), nNumeroColumna));
    }

    public void sumatoria() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA
        double nPorcentajeIva = 0.00;
        double sumexentas = 0.00;
        double sumgravadas10 = 0.00;
        double sumgravadas5 = 0.00;
        double sumtotal = 0.00;
        double sumatoria = 0.00;
        String cValorImporte = "";
        String cPorcentaje = "";
        double sumtotalcompleto = 0.00;
        int nIva = 0;
        int totalRow = this.tabladetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //Primero capturamos el porcentaje del IVA
            //Luego capturamos el importe de la celda total del item
            this.tabladetalle.setValueAt(i + 1, i, 0);
            cValorImporte = String.valueOf(this.tabladetalle.getValueAt(i, 5));
            cValorImporte = cValorImporte.replace(".", "");
            cValorImporte = cValorImporte.replace(",", ".");
            //Calculamos el total
            sumtotal += Double.valueOf(cValorImporte);
            if (Double.valueOf(cValorImporte) > 0) {
                sumtotalcompleto += Double.valueOf(cValorImporte);
            }
            cPorcentaje = String.valueOf(this.tabladetalle.getValueAt(i, 6));
            nPorcentajeIva = Double.valueOf(cPorcentaje);

            if (nPorcentajeIva == 0) {
                sumatoria = Double.valueOf(cValorImporte);
                sumexentas += sumatoria;
            }
            //Calculamos el total del 5%
            if (nPorcentajeIva == 5) {
                sumatoria = Double.valueOf(cValorImporte);
                sumgravadas5 += sumatoria;
            }
            if (nPorcentajeIva == 10) {
                sumatoria = Double.valueOf(cValorImporte);
                sumgravadas10 += sumatoria;
            }
            //Calculamos el total de exentos
        }
        //CALCULAMOS EL IVA CON LA FUNCION DE REDONDEO
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalcompleto.setText(String.valueOf(sumtotalcompleto));
        this.total_dolar.setText(formatoextranjero.format(sumtotal / Double.valueOf(cCotizacionDolar)));
        this.total_real.setText(formatoextranjero.format(sumtotal / Double.valueOf(cCotizacionReal)));
        this.total_peso.setText(formatoextranjero.format(sumtotal / Double.valueOf(cCotizacionPeso)));
        this.totalneto.setText(formatea.format(sumtotal));
        this.exentas.setText(formatea.format(sumexentas));
        this.gravadas5.setText(formatea.format(sumgravadas5));
        this.gravadas10.setText(formatea.format(sumgravadas10));
        //formato.format(sumatoria1);
    }

    private void TituloPOS() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        modelo.addColumn("Item");//0
        modelo.addColumn("Código");//1
        modelo.addColumn("Descripción");//2
        modelo.addColumn("Cantidad");//3
        modelo.addColumn("Precio");//4
        modelo.addColumn("Total");//5
        modelo.addColumn("IVA");//6
        int[] anchos = {40, 100, 250, 100, 100, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tabladetalle.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tabladetalle.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tabladetalle.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        tabladetalle.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);

        this.tabladetalle.getColumnModel().getColumn(6).setMaxWidth(0);
        this.tabladetalle.getColumnModel().getColumn(6).setMinWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        this.tabladetalle.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        //Se usa para poner invisible una determinada celda
        tabladetalle.getTableHeader().setForeground(Color.BLUE);
        tabladetalle.getTableHeader().setFont(new Font("Arial Black", 1, 14));
        Font font = new Font("Arial Black", Font.BOLD, 14);
        this.tabladetalle.setFont(font);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        POS = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        panel3 = new org.edisoncor.gui.panel.Panel();
        jLabel16 = new javax.swing.JLabel();
        totalneto = new javax.swing.JFormattedTextField();
        imagendolar = new javax.swing.JLabel();
        imagenreal = new javax.swing.JLabel();
        imagenpeso = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cotizacion_dolar = new javax.swing.JFormattedTextField();
        cotizacion_real = new javax.swing.JFormattedTextField();
        cotizacion_peso = new javax.swing.JFormattedTextField();
        total_dolar = new javax.swing.JFormattedTextField();
        total_real = new javax.swing.JFormattedTextField();
        total_peso = new javax.swing.JFormattedTextField();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel10 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        rucliente = new javax.swing.JTextField();
        referencia = new javax.swing.JTextField();
        buscarCliente = new javax.swing.JButton();
        nombrecliente = new javax.swing.JTextField();
        direccioncliente = new javax.swing.JTextField();
        nrotimbrado = new javax.swing.JTextField();
        vencetimbrado = new com.toedter.calendar.JDateChooser();
        iniciotimbrado = new com.toedter.calendar.JDateChooser();
        jLabel66 = new javax.swing.JLabel();
        obra = new javax.swing.JTextField();
        buscarobra = new javax.swing.JButton();
        nombreobra = new javax.swing.JTextField();
        totalcompleto = new javax.swing.JTextField();
        panel2 = new org.edisoncor.gui.panel.Panel();
        jLabel5 = new javax.swing.JLabel();
        factura = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        vendedor = new javax.swing.JTextField();
        comprobante = new javax.swing.JTextField();
        nombrevendedor = new javax.swing.JTextField();
        nombrecomprobante = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        preventa = new javax.swing.JTextField();
        buscarcomprobante = new javax.swing.JButton();
        BuscarVendedor = new javax.swing.JButton();
        buscarpreventa = new javax.swing.JButton();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        sucursal = new javax.swing.JTextField();
        buscarsucursal = new javax.swing.JButton();
        caja = new javax.swing.JTextField();
        buscarcaja = new javax.swing.JButton();
        nombresucursal = new javax.swing.JTextField();
        nombrecaja = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        observacion = new javax.swing.JTextField();
        limitecredito = new javax.swing.JFormattedTextField();
        estacompra = new javax.swing.JFormattedTextField();
        disponible = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        codigoproducto = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        etiquetaproducto = new org.edisoncor.gui.label.LabelMetric();
        buscarproducto = new javax.swing.JButton();
        precio = new javax.swing.JFormattedTextField();
        cantidad = new javax.swing.JFormattedTextField();
        AgregarItem = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabladetalle = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        GrabarVenta = new javax.swing.JButton();
        salirpuntoventa = new javax.swing.JButton();
        CancelarVenta = new javax.swing.JButton();
        imprimirpos = new javax.swing.JButton();
        BorrarItemVenta = new javax.swing.JButton();
        gravadas10 = new javax.swing.JTextField();
        gravadas5 = new javax.swing.JTextField();
        exentas = new javax.swing.JTextField();
        iva = new javax.swing.JTextField();
        reimpresion = new javax.swing.JButton();
        cobrar = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        totalbrutoapagar = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        sucambio = new javax.swing.JFormattedTextField();
        jPanel16 = new javax.swing.JPanel();
        AceptarCobro = new javax.swing.JButton();
        SalirFormaCobro = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        cobroefectivo = new javax.swing.JFormattedTextField();
        BCliente = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BVendedor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combovendedor = new javax.swing.JComboBox();
        jTBuscarVendedor = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablavendedor = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        BComprobante = new javax.swing.JDialog();
        jPanel18 = new javax.swing.JPanel();
        combocomprobante = new javax.swing.JComboBox();
        jTBuscarComprobante = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablacomprobante = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        AceptarComprobante = new javax.swing.JButton();
        SalirComprobante = new javax.swing.JButton();
        BFormaPago = new javax.swing.JDialog();
        jPanel40 = new javax.swing.JPanel();
        comboforma = new javax.swing.JComboBox();
        jTBuscarForma = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaformapago = new javax.swing.JTable();
        jPanel41 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        BCaja = new javax.swing.JDialog();
        jPanel27 = new javax.swing.JPanel();
        combocaja = new javax.swing.JComboBox();
        jTBuscarCaja = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablacaja = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        AceptarCaja = new javax.swing.JButton();
        SalirCaja = new javax.swing.JButton();
        BSucursal = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        combosucursal = new javax.swing.JComboBox();
        jTBuscarSucursal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablasucursal = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarSuc = new javax.swing.JButton();
        SalirSuc = new javax.swing.JButton();
        RegistrarCliente = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        BuscarCliente = new javax.swing.JButton();
        nombre = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        fechaingreso = new com.toedter.calendar.JDateChooser();
        jLabel22 = new javax.swing.JLabel();
        nacimiento = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        ruc = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        celular = new javax.swing.JTextField();
        habilitado = new javax.swing.JCheckBox();
        control = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        jLabel19asdsad1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        GrabarCliente = new javax.swing.JButton();
        SalirCliente = new javax.swing.JButton();
        preventas = new javax.swing.JDialog();
        jPanel21 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        ComboBuscarPreventa = new javax.swing.JComboBox<>();
        FiltrarPreventa = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablapreventa = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        AceptarPreventa = new javax.swing.JButton();
        SalirPreventa = new javax.swing.JButton();
        SupervisorCajaB = new javax.swing.JDialog();
        jPanel30 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        nropreventa = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        AceptarSupervisor1 = new javax.swing.JButton();
        SalirSupervisor1 = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        pasesupervisor1 = new javax.swing.JPasswordField();
        medida_madera = new javax.swing.JDialog();
        jPanel33 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        ancho = new javax.swing.JFormattedTextField();
        longitud = new javax.swing.JFormattedTextField();
        espesor = new javax.swing.JFormattedTextField();
        subtotal = new javax.swing.JFormattedTextField();
        piezas = new javax.swing.JFormattedTextField();
        total = new javax.swing.JFormattedTextField();
        jPanel35 = new javax.swing.JPanel();
        AceptarMedida = new javax.swing.JButton();
        SalirMedida = new javax.swing.JButton();
        ingresos = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        GrabarIngreso = new javax.swing.JButton();
        SalirIngreso = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        importeingreso = new javax.swing.JFormattedTextField();
        conceptoingreso = new javax.swing.JTextField();
        cobranzas = new javax.swing.JDialog();
        jPanel39 = new javax.swing.JPanel();
        ConsultarEstadoCta = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        nrorecibo = new javax.swing.JTextField();
        codclie = new javax.swing.JTextField();
        BusCodCli = new javax.swing.JButton();
        nombreclientecobro = new javax.swing.JTextField();
        fechacobro = new com.toedter.calendar.JDateChooser();
        creferencia = new javax.swing.JTextField();
        ruccliecobro = new javax.swing.JTextField();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablacobranza = new javax.swing.JTable();
        jPanel43 = new javax.swing.JPanel();
        GrabarCobroPV = new javax.swing.JButton();
        SalirCobroPV = new javax.swing.JButton();
        jPanel44 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        totalcobro = new javax.swing.JFormattedTextField();
        saldocliente = new javax.swing.JFormattedTextField();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        PagoParcial = new javax.swing.JDialog();
        jPanel45 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        nrodocumento = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        concepto = new javax.swing.JTextField();
        importe_a_pagar = new javax.swing.JFormattedTextField();
        importe_pagado = new javax.swing.JFormattedTextField();
        nrocuota = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        calcularporcentaje = new javax.swing.JFormattedTextField();
        jLabel64 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        GrabarPagoParcial = new javax.swing.JButton();
        SalirPagoParcial = new javax.swing.JButton();
        BProducto = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        comboproducto = new javax.swing.JComboBox();
        textoproducto = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaproducto = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarProducto = new javax.swing.JButton();
        SalirProducto = new javax.swing.JButton();
        panelfoto = new javax.swing.JDialog();
        jPanel47 = new javax.swing.JPanel();
        etiquetanombreproducto = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        fotoProducto = new JPanelWebCam.JPanelWebCam();
        jPanel49 = new javax.swing.JPanel();
        SalirFoto = new javax.swing.JButton();
        BBancos = new javax.swing.JDialog();
        jPanel50 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        jTBuscarbanco = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel51 = new javax.swing.JPanel();
        Aceptar = new javax.swing.JButton();
        SalirBanco = new javax.swing.JButton();
        arqueo_caja = new javax.swing.JDialog();
        jPanel52 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        ImprimirArqueo = new javax.swing.JButton();
        SalirArqueo = new javax.swing.JButton();
        jPanel54 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        fecha_arqueo = new com.toedter.calendar.JDateChooser();
        SupervisorCaja = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        AceptarSupervisor = new javax.swing.JButton();
        SalirSupervisor = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        pasesupervisor = new javax.swing.JPasswordField();
        inventario = new javax.swing.JDialog();
        jPanel55 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tablastock = new javax.swing.JTable();
        jPanel56 = new javax.swing.JPanel();
        SalirStockSucursal = new javax.swing.JButton();
        jPanel57 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        etiquetacodigo = new javax.swing.JLabel();
        etiquetanombre = new javax.swing.JLabel();
        egresos = new javax.swing.JDialog();
        jPanel58 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        GrabarEgreso = new javax.swing.JButton();
        SalirEgreso = new javax.swing.JButton();
        jPanel60 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        importeegreso = new javax.swing.JFormattedTextField();
        conceptoegreso = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        importemonedalocal = new javax.swing.JFormattedTextField();
        jLabel77 = new javax.swing.JLabel();
        monedaegreso = new javax.swing.JFormattedTextField();
        jLabel78 = new javax.swing.JLabel();
        cotizacionegreso = new javax.swing.JFormattedTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        cajadestino = new javax.swing.JFormattedTextField();
        buscarbanco = new javax.swing.JButton();
        nombrebanco = new javax.swing.JTextField();
        BPago = new javax.swing.JDialog();
        jPanel61 = new javax.swing.JPanel();
        combopago = new javax.swing.JComboBox();
        jTBuscarPago = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablapago = new javax.swing.JTable();
        jPanel62 = new javax.swing.JPanel();
        AceptarCobrador = new javax.swing.JButton();
        SalirCobrador = new javax.swing.JButton();
        divisas = new javax.swing.JDialog();
        jPanel63 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        fecha_carga = new com.toedter.calendar.JDateChooser();
        monedaorigen = new javax.swing.JTextField();
        nombremonedaorigen = new javax.swing.JTextField();
        entrega = new javax.swing.JFormattedTextField();
        buscarmonedaorigen = new javax.swing.JButton();
        operacion = new javax.swing.JComboBox<>();
        jLabel85 = new javax.swing.JLabel();
        cantidadcambio = new javax.swing.JFormattedTextField();
        jLabel87 = new javax.swing.JLabel();
        monedadestino = new javax.swing.JTextField();
        nombremonedadestino = new javax.swing.JTextField();
        buscarmonedadestino = new javax.swing.JButton();
        jLabel88 = new javax.swing.JLabel();
        cotizacionmoneda = new javax.swing.JFormattedTextField();
        jLabel89 = new javax.swing.JLabel();
        recibe = new javax.swing.JFormattedTextField();
        jLabel90 = new javax.swing.JLabel();
        vuelto = new javax.swing.JFormattedTextField();
        jLabel91 = new javax.swing.JLabel();
        caja_divisa = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        BuscarClienteDivisa = new javax.swing.JButton();
        nombrebancodivisa = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        GrabarCarga = new javax.swing.JButton();
        SalirCarga = new javax.swing.JButton();
        jPanel65 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        BMoneda = new javax.swing.JDialog();
        jPanel66 = new javax.swing.JPanel();
        combomoneda = new javax.swing.JComboBox();
        jTBuscarMoneda = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        tablamoneda = new javax.swing.JTable();
        jPanel67 = new javax.swing.JPanel();
        AceptarMoneda = new javax.swing.JButton();
        SalirMoneda = new javax.swing.JButton();
        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        labelTask1 = new org.edisoncor.gui.label.LabelTask();
        jXPanel2 = new org.jdesktop.swingx.JXPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ingresofactura = new javax.swing.JTextField();
        sucursal_acceso = new javax.swing.JTextField();
        nombresucursalacceso = new javax.swing.JTextField();
        caja_acceso = new javax.swing.JTextField();
        nombrecajaacceso = new javax.swing.JTextField();
        vendedor_acceso = new javax.swing.JTextField();
        nombrevendedoracceso = new javax.swing.JTextField();
        BuscarSucAcceso = new javax.swing.JButton();
        BuscarVendedorAcceso = new javax.swing.JButton();
        BuscarCajaAcceso = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        turno = new javax.swing.JComboBox<>();
        jXPanel3 = new org.jdesktop.swingx.JXPanel();
        ingresopos = new javax.swing.JButton();
        salirpos = new javax.swing.JButton();

        POS.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        POS.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                POSWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                POSWindowLostFocus(evt);
            }
        });
        POS.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                POSWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                POSWindowClosing(evt);
            }
        });

        panel3.setBackground(new java.awt.Color(204, 204, 204));
        panel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel3.setColorPrimario(new java.awt.Color(4, 200, 230));
        panel3.setColorSecundario(new java.awt.Color(4, 200, 230));

        jLabel16.setBackground(new java.awt.Color(255, 255, 0));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Esta Venta");

        totalneto.setBackground(new java.awt.Color(255, 0, 0));
        totalneto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        totalneto.setForeground(new java.awt.Color(255, 255, 255));
        totalneto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        totalneto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalneto.setCaretColor(new java.awt.Color(255, 255, 255));
        totalneto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalneto.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        imagendolar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        imagendolar.setText("Dólar Americano");

        imagenreal.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        imagenreal.setText("Real ");

        imagenpeso.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        imagenpeso.setText("Peso Argentino");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("COTIZACIÓN DE MONEDAS");

        cotizacion_dolar.setEditable(false);
        cotizacion_dolar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion_dolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion_dolar.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        cotizacion_real.setEditable(false);
        cotizacion_real.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion_real.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion_real.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        cotizacion_peso.setEditable(false);
        cotizacion_peso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacion_peso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacion_peso.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        total_dolar.setEditable(false);
        total_dolar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        total_dolar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_dolar.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        total_real.setEditable(false);
        total_real.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        total_real.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_real.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        total_peso.setEditable(false);
        total_peso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        total_peso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_peso.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel13))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imagenpeso)
                            .addComponent(imagendolar)
                            .addComponent(imagenreal))
                        .addGap(18, 18, 18)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cotizacion_real, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cotizacion_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cotizacion_peso, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(total_dolar)
                            .addComponent(total_real)
                            .addComponent(total_peso, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalneto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imagendolar)
                    .addComponent(cotizacion_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_dolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imagenreal)
                    .addComponent(cotizacion_real, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_real, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imagenpeso)
                    .addComponent(cotizacion_peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel1.setBackground(new java.awt.Color(204, 204, 204));
        panel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel1.setColorPrimario(new java.awt.Color(4, 200, 230));
        panel1.setColorSecundario(new java.awt.Color(4, 200, 230));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Cliente");

        cliente.setBackground(new java.awt.Color(255, 255, 204));
        cliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cliente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cliente.setText("1");
        cliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        cliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cliente.setEnabled(false);
        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });

        rucliente.setBackground(new java.awt.Color(255, 255, 204));
        rucliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rucliente.setText("RUC Cliente");
        rucliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rucliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rucliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        rucliente.setEnabled(false);
        rucliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruclienteActionPerformed(evt);
            }
        });

        referencia.setEditable(false);
        referencia.setBackground(new java.awt.Color(255, 255, 204));
        referencia.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        buscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarClienteActionPerformed(evt);
            }
        });

        nombrecliente.setBackground(new java.awt.Color(255, 255, 204));
        nombrecliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nombrecliente.setText("Clientes Varios");
        nombrecliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nombrecliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nombrecliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecliente.setEnabled(false);
        nombrecliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreclienteActionPerformed(evt);
            }
        });

        direccioncliente.setBackground(new java.awt.Color(255, 255, 204));
        direccioncliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        direccioncliente.setText("Direccion Cliente");
        direccioncliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        direccioncliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        direccioncliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        direccioncliente.setEnabled(false);
        direccioncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionclienteActionPerformed(evt);
            }
        });

        nrotimbrado.setBackground(new java.awt.Color(255, 255, 204));
        nrotimbrado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nrotimbrado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrotimbrado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nrotimbrado.setEnabled(false);

        vencetimbrado.setBackground(new java.awt.Color(204, 204, 255));
        vencetimbrado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        vencetimbrado.setEnabled(false);
        vencetimbrado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        iniciotimbrado.setBackground(new java.awt.Color(204, 204, 255));
        iniciotimbrado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        iniciotimbrado.setEnabled(false);
        iniciotimbrado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel66.setText("Obra");

        obra.setBackground(new java.awt.Color(255, 255, 204));
        obra.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        obra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        obra.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        obra.setEnabled(false);

        buscarobra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarobra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarobraActionPerformed(evt);
            }
        });

        nombreobra.setBackground(new java.awt.Color(255, 255, 204));
        nombreobra.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombreobra.setText("Descripción Obra");
        nombreobra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nombreobra.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombreobra.setEnabled(false);

        totalcompleto.setEditable(false);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(obra, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarobra, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreobra, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(26, 26, 26)
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(rucliente, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(referencia, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(totalcompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(41, 41, 41)
                            .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(iniciotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rucliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(direccioncliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buscarobra, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel66)
                        .addComponent(obra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombreobra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalcompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nrotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vencetimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iniciotimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panel2.setBackground(new java.awt.Color(102, 102, 102));
        panel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel2.setColorPrimario(new java.awt.Color(4, 200, 230));
        panel2.setColorSecundario(new java.awt.Color(4, 200, 230));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("N° Factura");

        factura.setBackground(new java.awt.Color(255, 255, 204));
        factura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        factura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        factura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        factura.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        factura.setEnabled(false);
        factura.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Fecha");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Vendedor");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Comprobante");

        vendedor.setBackground(new java.awt.Color(255, 255, 204));
        vendedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        vendedor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vendedor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        vendedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        vendedor.setEnabled(false);

        comprobante.setBackground(new java.awt.Color(255, 255, 204));
        comprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        comprobante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        comprobante.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        comprobante.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        comprobante.setEnabled(false);

        nombrevendedor.setBackground(new java.awt.Color(255, 255, 204));
        nombrevendedor.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombrevendedor.setText("Nombre del Vendedor");
        nombrevendedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nombrevendedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrevendedor.setEnabled(false);

        nombrecomprobante.setBackground(new java.awt.Color(255, 255, 204));
        nombrecomprobante.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombrecomprobante.setText("Descripción Comprobante");
        nombrecomprobante.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nombrecomprobante.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecomprobante.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Observación");

        preventa.setBackground(new java.awt.Color(255, 255, 204));
        preventa.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        preventa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        preventa.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        preventa.setEnabled(false);

        buscarcomprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarcomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcomprobanteActionPerformed(evt);
            }
        });

        BuscarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarVendedorActionPerformed(evt);
            }
        });

        buscarpreventa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarpreventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarpreventaActionPerformed(evt);
            }
        });

        fecha.setBackground(new java.awt.Color(204, 204, 255));
        fecha.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fecha.setEnabled(false);
        fecha.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Sucursal");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Caja");

        sucursal.setBackground(new java.awt.Color(255, 255, 204));
        sucursal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        sucursal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucursal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        sucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        sucursal.setEnabled(false);

        buscarsucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarsucursal.setEnabled(false);
        buscarsucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarsucursalActionPerformed(evt);
            }
        });

        caja.setBackground(new java.awt.Color(255, 255, 204));
        caja.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        caja.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        caja.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        caja.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        caja.setEnabled(false);

        buscarcaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarcaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcajaActionPerformed(evt);
            }
        });

        nombresucursal.setBackground(new java.awt.Color(255, 255, 204));
        nombresucursal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombresucursal.setText("Denominación Sucursal");
        nombresucursal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nombresucursal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombresucursal.setEnabled(false);

        nombrecaja.setBackground(new java.awt.Color(255, 255, 204));
        nombrecaja.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        nombrecaja.setText("Denominación Caja");
        nombrecaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nombrecaja.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nombrecaja.setEnabled(false);

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel65.setText("Preventa");

        limitecredito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        limitecredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        limitecredito.setToolTipText("");
        limitecredito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        limitecredito.setEnabled(false);

        estacompra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        estacompra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        estacompra.setToolTipText("");
        estacompra.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        estacompra.setEnabled(false);

        disponible.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        disponible.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        disponible.setToolTipText("");
        disponible.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        disponible.setEnabled(false);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(nombrevendedor)
                        .addGap(341, 341, 341))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarsucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarcaja, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                            .addComponent(factura))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel65)))
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(BuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buscarcomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(nombrecomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(limitecredito, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(preventa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(buscarpreventa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel2Layout.createSequentialGroup()
                                                .addComponent(estacompra, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(disponible, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(observacion)
                        .addContainerGap())))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarsucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(caja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(buscarcaja, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(nombrecaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)))
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscarcomprobante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombrecomprobante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(preventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarpreventa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(limitecredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(estacompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(disponible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombrevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(observacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(4, 200, 230));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        codigoproducto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        codigoproducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigoproducto.setToolTipText("Teclas Especiales: Precio = $ -- Peso = \",\" -- Cantidad = * ");
        codigoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoproductoActionPerformed(evt);
            }
        });
        codigoproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoproductoKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("X");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Ingrese Código Producto");

        etiquetaproducto.setBackground(new java.awt.Color(0, 51, 255));
        etiquetaproducto.setForeground(new java.awt.Color(255, 0, 0));
        etiquetaproducto.setText("Descripción del Producto");
        etiquetaproducto.setColorDeSombra(new java.awt.Color(255, 255, 255));
        etiquetaproducto.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        buscarproducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscarproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarproductoActionPerformed(evt);
            }
        });

        precio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        precio.setEnabled(false);
        precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioActionPerformed(evt);
            }
        });

        cantidad.setEditable(false);
        cantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.000"))));
        cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cantidad.setEnabled(false);
        cantidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
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

        AgregarItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AgregarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItemActionPerformed(evt);
            }
        });

        jButton1.setText("Báscula");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cantidad)
                .addGap(18, 18, 18)
                .addComponent(etiquetaproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AgregarItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(codigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel15)
                        .addComponent(etiquetaproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addComponent(buscarproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabladetalle.setModel(modelo);
        tabladetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabladetalleKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabladetalle);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("[F2]Cobrar [F3]Clientes [F4]Tipo Venta [F5]Nuevo Cliente [F6]Cancelar [F8]Productos  [F10]Cobranzas");

        GrabarVenta.setText("Grabar");
        GrabarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarVentaActionPerformed(evt);
            }
        });

        salirpuntoventa.setText("Salir");
        salirpuntoventa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salirpuntoventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirpuntoventaActionPerformed(evt);
            }
        });

        CancelarVenta.setText("Cancelar");
        CancelarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CancelarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarVentaActionPerformed(evt);
            }
        });

        imprimirpos.setText("Imprimir");
        imprimirpos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirposActionPerformed(evt);
            }
        });

        BorrarItemVenta.setText("Eliminar Item");
        BorrarItemVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarItemVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarItemVentaActionPerformed(evt);
            }
        });

        reimpresion.setText("Imprimir");
        reimpresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reimpresionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(imprimirpos, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reimpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gravadas10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iva, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(CancelarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BorrarItemVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GrabarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(salirpuntoventa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(GrabarVenta)
                    .addComponent(salirpuntoventa)
                    .addComponent(CancelarVenta)
                    .addComponent(imprimirpos)
                    .addComponent(BorrarItemVenta)
                    .addComponent(gravadas10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gravadas5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reimpresion))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout POSLayout = new javax.swing.GroupLayout(POS.getContentPane());
        POS.getContentPane().setLayout(POSLayout);
        POSLayout.setHorizontalGroup(
            POSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, POSLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        POSLayout.setVerticalGroup(
            POSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel20.setText("Total a Cobrar");

        totalbrutoapagar.setEditable(false);
        totalbrutoapagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalbrutoapagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalbrutoapagar.setDisabledTextColor(new java.awt.Color(255, 51, 51));
        totalbrutoapagar.setEnabled(false);
        totalbrutoapagar.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalbrutoapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalbrutoapagar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel26.setText("Su Cambio");

        sucambio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        sucambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucambio.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        sucambio.setEnabled(false);
        sucambio.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        sucambio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sucambioFocusGained(evt);
            }
        });
        sucambio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sucambioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sucambio, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(sucambio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AceptarCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(SalirFormaCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCobro)
                    .addComponent(SalirFormaCobro))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel41.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        jLabel41.setText("Efectivo");

        cobroefectivo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        cobroefectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cobroefectivo.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        cobroefectivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cobroefectivoFocusGained(evt);
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

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                .addComponent(cobroefectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(cobroefectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout cobrarLayout = new javax.swing.GroupLayout(cobrar.getContentPane());
        cobrar.getContentPane().setLayout(cobrarLayout);
        cobrarLayout.setHorizontalGroup(
            cobrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        cobrarLayout.setVerticalGroup(
            cobrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cobrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        javax.swing.GroupLayout BClienteLayout = new javax.swing.GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BVendedor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BVendedor.setTitle("null");

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combovendedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combovendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combovendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combovendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combovendedorActionPerformed(evt);
            }
        });

        jTBuscarVendedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarVendedor.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarVendedorActionPerformed(evt);
            }
        });
        jTBuscarVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarVendedorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(combovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combovendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablavendedor.setModel(modelovendedor);
        tablavendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablavendedorMouseClicked(evt);
            }
        });
        tablavendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablavendedorKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablavendedor);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirVendedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarVendedor)
                    .addComponent(SalirVendedor))
                .addContainerGap())
        );

        javax.swing.GroupLayout BVendedorLayout = new javax.swing.GroupLayout(BVendedor.getContentPane());
        BVendedor.getContentPane().setLayout(BVendedorLayout);
        BVendedorLayout.setHorizontalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BVendedorLayout.setVerticalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BComprobante.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BComprobante.setTitle("null");

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocomprobante.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocomprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocomprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocomprobanteActionPerformed(evt);
            }
        });

        jTBuscarComprobante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarComprobante.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarComprobanteActionPerformed(evt);
            }
        });
        jTBuscarComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarComprobanteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(combocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablacomprobante.setModel(modelocomprobante);
        tablacomprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacomprobanteMouseClicked(evt);
            }
        });
        tablacomprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacomprobanteKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablacomprobante);

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarComprobante.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarComprobanteActionPerformed(evt);
            }
        });

        SalirComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirComprobante.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirComprobanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarComprobante)
                    .addComponent(SalirComprobante))
                .addContainerGap())
        );

        javax.swing.GroupLayout BComprobanteLayout = new javax.swing.GroupLayout(BComprobante.getContentPane());
        BComprobante.getContentPane().setLayout(BComprobanteLayout);
        BComprobanteLayout.setHorizontalGroup(
            BComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BComprobanteLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BComprobanteLayout.setVerticalGroup(
            BComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BComprobanteLayout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BFormaPago.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BFormaPago.setTitle("null");

        jPanel40.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboforma.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboforma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboforma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboforma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboformaActionPerformed(evt);
            }
        });

        jTBuscarForma.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarForma.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarFormaActionPerformed(evt);
            }
        });
        jTBuscarForma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarFormaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(comboforma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarForma, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboforma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaformapago.setModel(modeloformapago);
        tablaformapago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaformapagoMouseClicked(evt);
            }
        });
        tablaformapago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaformapagoKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(tablaformapago);

        jPanel41.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGirActionPerformed(evt);
            }
        });

        SalirGir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirGir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout BFormaPagoLayout = new javax.swing.GroupLayout(BFormaPago.getContentPane());
        BFormaPago.getContentPane().setLayout(BFormaPagoLayout);
        BFormaPagoLayout.setHorizontalGroup(
            BFormaPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFormaPagoLayout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BFormaPagoLayout.setVerticalGroup(
            BFormaPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BFormaPagoLayout.createSequentialGroup()
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCaja.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCaja.setTitle("Buscar Caja");

        jPanel27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(combocaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCaja)
                    .addComponent(SalirCaja))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCajaLayout = new javax.swing.GroupLayout(BCaja.getContentPane());
        BCaja.getContentPane().setLayout(BCajaLayout);
        BCajaLayout.setHorizontalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCajaLayout.setVerticalGroup(
            BCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCajaLayout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BSucursal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BSucursal.setTitle("null");

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combosucursal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combosucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combosucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combosucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combosucursalActionPerformed(evt);
            }
        });

        jTBuscarSucursal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarSucursal.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(combosucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarSuc.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSucActionPerformed(evt);
            }
        });

        SalirSuc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirSuc.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirSuc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirSuc, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSuc)
                    .addComponent(SalirSuc))
                .addContainerGap())
        );

        javax.swing.GroupLayout BSucursalLayout = new javax.swing.GroupLayout(BSucursal.getContentPane());
        BSucursal.getContentPane().setLayout(BSucursalLayout);
        BSucursalLayout.setHorizontalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BSucursalLayout.setVerticalGroup(
            BSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BSucursalLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        RegistrarCliente.setTitle("Registrar Cliente");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.jLabel1.text")); // NOI18N

        codigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        codigo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codigo.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.codigo.text")); // NOI18N
        codigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                codigoFocusLost(evt);
            }
        });
        codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoActionPerformed(evt);
            }
        });
        codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                codigoKeyPressed(evt);
            }
        });

        BuscarCliente.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.BuscarCliente.text")); // NOI18N
        BuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarClienteActionPerformed(evt);
            }
        });

        nombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nombre.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.codigo.text")); // NOI18N
        nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombreKeyReleased(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.jLabel6.text")); // NOI18N

        fechaingreso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fechaingreso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaingresoFocusGained(evt);
            }
        });
        fechaingreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaingresoKeyPressed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.jLabel5.text")); // NOI18N

        nacimiento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nacimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nacimientoFocusGained(evt);
            }
        });
        nacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nacimientoKeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.jLabel2.text")); // NOI18N

        ruc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ruc.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.codigo.text")); // NOI18N
        ruc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rucKeyPressed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.jLabel4.text")); // NOI18N

        celular.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        celular.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.codigo.text")); // NOI18N
        celular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                celularKeyPressed(evt);
            }
        });

        habilitado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        habilitado.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.habilitado.text")); // NOI18N

        control.setEditable(false);
        control.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.control.text")); // NOI18N
        control.setEnabled(false);

        direccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        direccion.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.codigo.text")); // NOI18N
        direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                direccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                direccionKeyReleased(evt);
            }
        });

        jLabel19asdsad1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19asdsad1.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.jLabel3.text")); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Nombre del Cliente");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19asdsad1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(habilitado)
                        .addGap(111, 111, 111)
                        .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(nacimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addComponent(fechaingreso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(29, 29, 29))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19asdsad1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(habilitado)
                    .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCliente.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.BotonGrabar.text")); // NOI18N
        GrabarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarClienteActionPerformed(evt);
            }
        });

        SalirCliente.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "clientes_sparta.BotonSalir.text")); // NOI18N
        SalirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(GrabarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(SalirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SalirCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout RegistrarClienteLayout = new javax.swing.GroupLayout(RegistrarCliente.getContentPane());
        RegistrarCliente.getContentPane().setLayout(RegistrarClienteLayout);
        RegistrarClienteLayout.setHorizontalGroup(
            RegistrarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        RegistrarClienteLayout.setVerticalGroup(
            RegistrarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistrarClienteLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        preventas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        preventas.setTitle("Buscar Preventa");

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("Filtrar por");

        ComboBuscarPreventa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ComboBuscarPreventa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N° de Preventa", "Nombre del Cliente" }));

        FiltrarPreventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FiltrarPreventaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FiltrarPreventaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(ComboBuscarPreventa, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FiltrarPreventa, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(ComboBuscarPreventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FiltrarPreventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablapreventa.setModel(modelopreventa);
        tablapreventa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablapreventaMouseClicked(evt);
            }
        });
        tablapreventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablapreventaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablapreventa);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarPreventa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        AceptarPreventa.setText("Aceptar");
        AceptarPreventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarPreventaActionPerformed(evt);
            }
        });

        SalirPreventa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SalirPreventa.setText("Salir");
        SalirPreventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirPreventaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(AceptarPreventa, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(SalirPreventa, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarPreventa)
                    .addComponent(SalirPreventa))
                .addContainerGap())
        );

        javax.swing.GroupLayout preventasLayout = new javax.swing.GroupLayout(preventas.getContentPane());
        preventas.getContentPane().setLayout(preventasLayout);
        preventasLayout.setHorizontalGroup(
            preventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        preventasLayout.setVerticalGroup(
            preventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preventasLayout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        SupervisorCajaB.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        SupervisorCajaB.setTitle("Solicitar Asistencia del Supervisor de Caja");

        jPanel30.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 0));
        jLabel38.setText("Autorizar Descuento de la Preventa");

        nropreventa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nropreventa.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nropreventa.setEnabled(false);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addComponent(nropreventa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(nropreventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel31.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSupervisor1.setText("Aceptar");
        AceptarSupervisor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSupervisor1ActionPerformed(evt);
            }
        });

        SalirSupervisor1.setText("Salir");
        SalirSupervisor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSupervisor1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(AceptarSupervisor1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SalirSupervisor1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSupervisor1)
                    .addComponent(SalirSupervisor1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 0));
        jLabel39.setText("Contraseña de Personal Autorizado");

        pasesupervisor1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pasesupervisor1.setText("Pssssss");
        pasesupervisor1.setSelectedTextColor(new java.awt.Color(51, 51, 255));
        pasesupervisor1.setSelectionColor(new java.awt.Color(0, 204, 255));
        pasesupervisor1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pasesupervisor1FocusGained(evt);
            }
        });
        pasesupervisor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasesupervisor1ActionPerformed(evt);
            }
        });
        pasesupervisor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pasesupervisor1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel39))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(pasesupervisor1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pasesupervisor1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SupervisorCajaBLayout = new javax.swing.GroupLayout(SupervisorCajaB.getContentPane());
        SupervisorCajaB.getContentPane().setLayout(SupervisorCajaBLayout);
        SupervisorCajaBLayout.setHorizontalGroup(
            SupervisorCajaBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SupervisorCajaBLayout.setVerticalGroup(
            SupervisorCajaBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupervisorCajaBLayout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        medida_madera.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        medida_madera.setTitle("Cálculo de Volumen de Madera");

        jPanel33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setText("Ancho");

        jLabel40.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel40.setText("Espesor");

        jLabel42.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel42.setText("Longitud");

        jLabel43.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel43.setText("SubTotal");

        jLabel44.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel44.setText("Piezas");

        jLabel45.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel45.setText("Total");

        ancho.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ancho.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ancho.setToolTipText("");
        ancho.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ancho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                anchoKeyPressed(evt);
            }
        });

        longitud.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        longitud.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        longitud.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        longitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                longitudKeyPressed(evt);
            }
        });

        espesor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        espesor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        espesor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        espesor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                espesorKeyPressed(evt);
            }
        });

        subtotal.setBackground(new java.awt.Color(204, 255, 255));
        subtotal.setForeground(new java.awt.Color(51, 102, 255));
        subtotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        subtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        subtotal.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        subtotal.setEnabled(false);
        subtotal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        piezas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));
        piezas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        piezas.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        piezas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                piezasFocusLost(evt);
            }
        });
        piezas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piezasActionPerformed(evt);
            }
        });
        piezas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                piezasKeyPressed(evt);
            }
        });

        total.setBackground(new java.awt.Color(204, 255, 255));
        total.setForeground(new java.awt.Color(51, 102, 255));
        total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        total.setEnabled(false);
        total.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addComponent(jLabel44)
                    .addComponent(jLabel43)
                    .addComponent(jLabel42)
                    .addComponent(jLabel40)
                    .addComponent(jLabel25))
                .addGap(65, 65, 65)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ancho, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(espesor)
                    .addComponent(longitud)
                    .addComponent(subtotal)
                    .addComponent(piezas)
                    .addComponent(total))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(ancho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(espesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(longitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(piezas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMedida.setText("Aceptar");
        AceptarMedida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMedidaActionPerformed(evt);
            }
        });

        SalirMedida.setText("Salir");
        SalirMedida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMedidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap(113, Short.MAX_VALUE)
                .addComponent(AceptarMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(SalirMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMedida)
                    .addComponent(SalirMedida))
                .addContainerGap())
        );

        javax.swing.GroupLayout medida_maderaLayout = new javax.swing.GroupLayout(medida_madera.getContentPane());
        medida_madera.getContentPane().setLayout(medida_maderaLayout);
        medida_maderaLayout.setHorizontalGroup(
            medida_maderaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        medida_maderaLayout.setVerticalGroup(
            medida_maderaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medida_maderaLayout.createSequentialGroup()
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel36.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 51, 102));
        jLabel47.setText("Este módulo registra la Apertura de Caja o cualquier ingreso.");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 51, 102));
        jLabel48.setText("Recuerde, este registro ya no puede eliminarse, sólo se ajusta.");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addContainerGap())
        );

        jPanel37.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarIngreso.setText("Grabar");

        SalirIngreso.setText("Salir");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(GrabarIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(SalirIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarIngreso)
                    .addComponent(SalirIngreso)))
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel50.setText("Concepto");

        jLabel51.setText("Importe");

        importeingreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importeingreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        conceptoingreso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                conceptoingresoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel51)
                    .addComponent(jLabel50))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(conceptoingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importeingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(conceptoingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(importeingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ingresosLayout = new javax.swing.GroupLayout(ingresos.getContentPane());
        ingresos.getContentPane().setLayout(ingresosLayout);
        ingresosLayout.setHorizontalGroup(
            ingresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ingresosLayout.setVerticalGroup(
            ingresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ingresosLayout.createSequentialGroup()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cobranzas.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                cobranzasWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel39.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ConsultarEstadoCta.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ConsultarEstadoCta.setText("Consultar");
        ConsultarEstadoCta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ConsultarEstadoCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarEstadoCtaActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel55.setText("Recibo N°");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel56.setText("Fecha");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel57.setText("Cuenta");

        nrorecibo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrorecibo.setEnabled(false);

        codclie.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        codclie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codclieActionPerformed(evt);
            }
        });

        BusCodCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BusCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BusCodCliActionPerformed(evt);
            }
        });

        nombreclientecobro.setEnabled(false);

        fechacobro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechacobroFocusGained(evt);
            }
        });

        ruccliecobro.setEnabled(false);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nrorecibo, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(codclie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(BusCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(nombreclientecobro, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ConsultarEstadoCta, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(fechacobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ruccliecobro, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel55)
                        .addComponent(jLabel56)
                        .addComponent(nrorecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fechacobro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(creferencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConsultarEstadoCta)
                    .addComponent(codclie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BusCodCli, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(nombreclientecobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ruccliecobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel42.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablacobranza.setModel(modelodetalle       );
        tablacobranza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacobranzaKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablacobranza);

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );

        jPanel43.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCobroPV.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        GrabarCobroPV.setText("Grabar");
        GrabarCobroPV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCobroPV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCobroPVActionPerformed(evt);
            }
        });

        SalirCobroPV.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        SalirCobroPV.setText("Salir");
        SalirCobroPV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCobroPV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCobroPVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarCobroPV, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(SalirCobroPV, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCobroPV)
                    .addComponent(SalirCobroPV))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel44.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel49.setText("F1 - Pago Total");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel52.setText("F2 - Pago Parcial");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel53.setText("F3 - Cancelar");

        totalcobro.setEditable(false);
        totalcobro.setBackground(new java.awt.Color(204, 204, 255));
        totalcobro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalcobro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalcobro.setToolTipText("");
        totalcobro.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        saldocliente.setEditable(false);
        saldocliente.setBackground(new java.awt.Color(204, 204, 255));
        saldocliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        saldocliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        saldocliente.setToolTipText("");
        saldocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setText("Saldo Total");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setText("Total a Pagar");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel59)
                                .addGap(44, 44, 44))
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addComponent(saldocliente, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalcobro, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saldocliente)
                    .addComponent(totalcobro, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout cobranzasLayout = new javax.swing.GroupLayout(cobranzas.getContentPane());
        cobranzas.getContentPane().setLayout(cobranzasLayout);
        cobranzasLayout.setHorizontalGroup(
            cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        cobranzasLayout.setVerticalGroup(
            cobranzasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cobranzasLayout.createSequentialGroup()
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        PagoParcial.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        PagoParcial.setTitle("Pago Parcial de Cuotas");

        jPanel45.setBackground(new java.awt.Color(204, 204, 204));
        jPanel45.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(102, 102, 255)));

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setText("N° Documento");

        nrodocumento.setEditable(false);
        nrodocumento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrodocumento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setText("Comprobante");

        concepto.setEditable(false);
        concepto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        concepto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conceptoActionPerformed(evt);
            }
        });

        importe_a_pagar.setEditable(false);
        importe_a_pagar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_a_pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_a_pagar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        importe_pagado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
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

        nrocuota.setEditable(false);
        nrocuota.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrocuota.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel61.setText("Cuota");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setText("Importe a Pagar");

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel63.setText("Su Pago");

        calcularporcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        calcularporcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        calcularporcentaje.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        calcularporcentaje.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                calcularporcentajeFocusLost(evt);
            }
        });
        calcularporcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularporcentajeActionPerformed(evt);
            }
        });
        calcularporcentaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                calcularporcentajeKeyPressed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel64.setText("Calcular %");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addComponent(jLabel60))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nrodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61)
                            .addComponent(jLabel62)
                            .addComponent(jLabel63)
                            .addComponent(jLabel64))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(importe_a_pagar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                .addComponent(importe_pagado)
                                .addComponent(nrocuota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(calcularporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(nrodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60)
                    .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nrocuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addGap(16, 16, 16)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importe_a_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addGap(18, 18, 18)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importe_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addGap(18, 18, 18)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calcularporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel46.setBackground(new java.awt.Color(204, 204, 204));
        jPanel46.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(GrabarPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(SalirPagoParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarPagoParcial)
                    .addComponent(SalirPagoParcial))
                .addContainerGap())
        );

        javax.swing.GroupLayout PagoParcialLayout = new javax.swing.GroupLayout(PagoParcial.getContentPane());
        PagoParcial.getContentPane().setLayout(PagoParcialLayout);
        PagoParcialLayout.setHorizontalGroup(
            PagoParcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PagoParcialLayout.setVerticalGroup(
            PagoParcialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagoParcialLayout.createSequentialGroup()
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        BProducto.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BProducto.setTitle("null");
        BProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BProductoFocusGained(evt);
            }
        });
        BProducto.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                BProductoWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboproducto.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        comboproducto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        comboproducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboproductoActionPerformed(evt);
            }
        });

        textoproducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        textoproducto.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
        textoproducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textoproductoFocusGained(evt);
            }
        });
        textoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoproductoActionPerformed(evt);
            }
        });
        textoproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textoproductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textoproductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoproductoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaproducto.setModel(modeloproducto);
        tablaproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaproductoMouseClicked(evt);
            }
        });
        tablaproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaproductoKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tablaproducto);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarProducto.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarProductoActionPerformed(evt);
            }
        });

        SalirProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirProducto.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(AceptarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarProducto)
                    .addComponent(SalirProducto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BProductoLayout = new javax.swing.GroupLayout(BProducto.getContentPane());
        BProducto.getContentPane().setLayout(BProductoLayout);
        BProductoLayout.setHorizontalGroup(
            BProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane8)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BProductoLayout.setVerticalGroup(
            BProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BProductoLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel47.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        etiquetanombreproducto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetanombreproducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiquetanombreproducto.setText("jLabel31");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etiquetanombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiquetanombreproducto)
                .addContainerGap())
        );

        jPanel48.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        fotoProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fotoProducto.setToolTipText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.fotoProducto.toolTipText")); // NOI18N
        fotoProducto.setACTIVARCAMARA(false);
        fotoProducto.setFONDO(false);
        fotoProducto.setEnabled(false);

        javax.swing.GroupLayout fotoProductoLayout = new javax.swing.GroupLayout(fotoProducto);
        fotoProducto.setLayout(fotoProductoLayout);
        fotoProductoLayout.setHorizontalGroup(
            fotoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        fotoProductoLayout.setVerticalGroup(
            fotoProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fotoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fotoProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel49.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirFoto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirFoto.setText("Salir de Imágen");
        SalirFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SalirFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelfotoLayout = new javax.swing.GroupLayout(panelfoto.getContentPane());
        panelfoto.getContentPane().setLayout(panelfotoLayout);
        panelfotoLayout.setHorizontalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelfotoLayout.setVerticalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfotoLayout.createSequentialGroup()
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BBancos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BBancos.setTitle(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "detalle_extracciones.BBancos.title")); // NOI18N

        jPanel50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarbanco.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "detalle_extracciones.jTBuscarbanco.text")); // NOI18N
        jTBuscarbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarbancoActionPerformed(evt);
            }
        });
        jTBuscarbanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarbancoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablabanco.setModel(modelobanco       );
        tablabanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablabancoMouseClicked(evt);
            }
        });
        tablabanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablabancoKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(tablabanco);

        jPanel51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Aceptar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Aceptar.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "detalle_extracciones.Aceptar.text")); // NOI18N
        Aceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        SalirBanco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirBanco.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "detalle_extracciones.SalirBanco.text")); // NOI18N
        SalirBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirBancoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SalirBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Aceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout BBancosLayout = new javax.swing.GroupLayout(BBancos.getContentPane());
        BBancos.getContentPane().setLayout(BBancosLayout);
        BBancosLayout.setHorizontalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBancosLayout.setVerticalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        arqueo_caja.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                arqueo_cajaWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel52.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 51, 102));
        jLabel67.setText("Informe de Arqueo de Caja");

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 51, 102));
        jLabel68.setText("Seleccione los parámetros para emitir el Reporte.");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel68)
                .addContainerGap())
        );

        jPanel53.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ImprimirArqueo.setText("Imprimir");
        ImprimirArqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImprimirArqueoActionPerformed(evt);
            }
        });

        SalirArqueo.setText("Salir");
        SalirArqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirArqueoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(ImprimirArqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(SalirArqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ImprimirArqueo)
                    .addComponent(SalirArqueo)))
        );

        jPanel54.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel70.setText("Fecha");

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jLabel70)
                .addGap(28, 28, 28)
                .addComponent(fecha_arqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(fecha_arqueo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout arqueo_cajaLayout = new javax.swing.GroupLayout(arqueo_caja.getContentPane());
        arqueo_caja.getContentPane().setLayout(arqueo_cajaLayout);
        arqueo_cajaLayout.setHorizontalGroup(
            arqueo_cajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        arqueo_cajaLayout.setVerticalGroup(
            arqueo_cajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(arqueo_cajaLayout.createSequentialGroup()
                .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        SupervisorCaja.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        SupervisorCaja.setTitle("Solicitar Asistencia del Supervisor de Caja");

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 0, 0));
        jLabel35.setText("Observación : Solicite la Asistencia del Administrador de Caja. ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarSupervisor.setText("Aceptar");
        AceptarSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarSupervisorActionPerformed(evt);
            }
        });

        SalirSupervisor.setText("Salir");
        SalirSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirSupervisorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(AceptarSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SalirSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarSupervisor)
                    .addComponent(SalirSupervisor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 0, 0));
        jLabel36.setText("Contraseña de Personal Autorizado");

        pasesupervisor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pasesupervisor.setText("Pssssss");
        pasesupervisor.setSelectedTextColor(new java.awt.Color(51, 51, 255));
        pasesupervisor.setSelectionColor(new java.awt.Color(0, 204, 255));
        pasesupervisor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pasesupervisorFocusGained(evt);
            }
        });
        pasesupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasesupervisorActionPerformed(evt);
            }
        });
        pasesupervisor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pasesupervisorKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel36))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(pasesupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pasesupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SupervisorCajaLayout = new javax.swing.GroupLayout(SupervisorCaja.getContentPane());
        SupervisorCaja.getContentPane().setLayout(SupervisorCajaLayout);
        SupervisorCajaLayout.setHorizontalGroup(
            SupervisorCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SupervisorCajaLayout.setVerticalGroup(
            SupervisorCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupervisorCajaLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        inventario.setTitle(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas_mercaderias.inventario.title")); // NOI18N

        jPanel55.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablastock.setModel(modelostock);
        jScrollPane16.setViewportView(tablastock);

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel56.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirStockSucursal.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas_mercaderias.SalirStock.text")); // NOI18N
        SalirStockSucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirStockSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirStockSucursalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel56Layout.createSequentialGroup()
                .addComponent(SalirStockSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SalirStockSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel57.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel69.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas_mercaderias.jLabel56.text")); // NOI18N

        jLabel71.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas_mercaderias.jLabel57.text")); // NOI18N

        etiquetacodigo.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas_mercaderias.etiquetacodigo.text")); // NOI18N

        etiquetanombre.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas_mercaderias.etiquetanombre.text")); // NOI18N

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel69)
                    .addComponent(jLabel71))
                .addGap(18, 18, 18)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetacodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetanombre, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(etiquetacodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(etiquetanombre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout inventarioLayout = new javax.swing.GroupLayout(inventario.getContentPane());
        inventario.getContentPane().setLayout(inventarioLayout);
        inventarioLayout.setHorizontalGroup(
            inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel55, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel57, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inventarioLayout.setVerticalGroup(
            inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        egresos.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                egresosWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel58.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 51, 102));
        jLabel72.setText("Este módulo registra el retiro de dinero de la caja.");

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 51, 102));
        jLabel73.setText("Recuerde, este registro ya no puede eliminarse, sólo se ajusta.");

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel73)
                .addContainerGap())
        );

        jPanel59.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarEgreso.setText("Grabar");
        GrabarEgreso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarEgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarEgresoActionPerformed(evt);
            }
        });

        SalirEgreso.setText("Salir");
        SalirEgreso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirEgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirEgresoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GrabarEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(SalirEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel59Layout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarEgreso)
                    .addComponent(SalirEgreso)))
        );

        jPanel60.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel74.setText("Concepto");

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel75.setText("Importe");

        importeegreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importeegreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importeegreso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        importeegreso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                importeegresoFocusLost(evt);
            }
        });
        importeegreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importeegresoKeyPressed(evt);
            }
        });

        conceptoegreso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        conceptoegreso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                conceptoegresoFocusLost(evt);
            }
        });
        conceptoegreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                conceptoegresoKeyPressed(evt);
            }
        });

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel76.setText("Importe M. L.");

        importemonedalocal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importemonedalocal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importemonedalocal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        importemonedalocal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importemonedalocalKeyPressed(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel77.setText("Moneda");

        monedaegreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        monedaegreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        monedaegreso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        monedaegreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monedaegresoActionPerformed(evt);
            }
        });
        monedaegreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                monedaegresoKeyPressed(evt);
            }
        });

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel78.setText("Cotización");

        cotizacionegreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        cotizacionegreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacionegreso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cotizacionegreso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cotizacionegresoFocusLost(evt);
            }
        });
        cotizacionegreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cotizacionegresoActionPerformed(evt);
            }
        });
        cotizacionegreso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cotizacionegresoKeyPressed(evt);
            }
        });

        jLabel27.setText(" 1-GS / 2-USD / 3-REAL / 4-PESO");

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel79.setText("Caja/Destino");

        cajadestino.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        cajadestino.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cajadestino.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cajadestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajadestinoActionPerformed(evt);
            }
        });
        cajadestino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cajadestinoKeyPressed(evt);
            }
        });

        buscarbanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarbancoActionPerformed(evt);
            }
        });

        nombrebanco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nombrebanco.setEnabled(false);

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel77)
                            .addComponent(jLabel75))
                        .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel60Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(importeegreso, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel60Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel27))))
                    .addGroup(jPanel60Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel76)
                            .addComponent(jLabel74)
                            .addComponent(jLabel79)
                            .addComponent(jLabel78))
                        .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel60Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(cajadestino, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel60Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(conceptoegreso)
                                        .addComponent(importemonedalocal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nombrebanco, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
                                    .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(monedaegreso, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cotizacionegreso, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(importeegreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(monedaegreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cotizacionegreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel78))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(importemonedalocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(conceptoegreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel79)
                    .addComponent(cajadestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout egresosLayout = new javax.swing.GroupLayout(egresos.getContentPane());
        egresos.getContentPane().setLayout(egresosLayout);
        egresosLayout.setHorizontalGroup(
            egresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        egresosLayout.setVerticalGroup(
            egresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(egresosLayout.createSequentialGroup()
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BPago.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BPago.setTitle("null");

        jPanel61.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combopago.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combopago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combopago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combopago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combopagoActionPerformed(evt);
            }
        });

        jTBuscarPago.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarPago.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarPagoActionPerformed(evt);
            }
        });
        jTBuscarPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarPagoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addComponent(combopago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combopago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablapago.setModel(modelopago);
        tablapago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablapagoMouseClicked(evt);
            }
        });
        tablapago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablapagoKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(tablapago);

        jPanel62.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCobrador.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCobradorActionPerformed(evt);
            }
        });

        SalirCobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCobrador.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCobradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCobrador, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCobrador)
                    .addComponent(SalirCobrador))
                .addContainerGap())
        );

        javax.swing.GroupLayout BPagoLayout = new javax.swing.GroupLayout(BPago.getContentPane());
        BPago.getContentPane().setLayout(BPagoLayout);
        BPagoLayout.setHorizontalGroup(
            BPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPagoLayout.createSequentialGroup()
                .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BPagoLayout.setVerticalGroup(
            BPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPagoLayout.createSequentialGroup()
                .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel63.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel80.setText("Fecha");

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel82.setText("Cambiar de");

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel84.setText("Entrega");

        fecha_carga.setBackground(new java.awt.Color(204, 204, 255));
        fecha_carga.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        fecha_carga.setEnabled(false);
        fecha_carga.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N

        monedaorigen.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        monedaorigen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        monedaorigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monedaorigenActionPerformed(evt);
            }
        });
        monedaorigen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                monedaorigenKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                monedaorigenKeyReleased(evt);
            }
        });

        nombremonedaorigen.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        nombremonedaorigen.setEnabled(false);

        entrega.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        entrega.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        entrega.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        entrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                entregaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                entregaKeyReleased(evt);
            }
        });

        buscarmonedaorigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarmonedaorigenActionPerformed(evt);
            }
        });

        operacion.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        operacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMPRA", "VENTA" }));
        operacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                operacionKeyPressed(evt);
            }
        });

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel85.setText("Operación");

        cantidadcambio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cantidadcambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cantidadcambio.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cantidadcambio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cantidadcambioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cantidadcambioKeyReleased(evt);
            }
        });

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel87.setText("Cantidad");

        monedadestino.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        monedadestino.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        monedadestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monedadestinoActionPerformed(evt);
            }
        });
        monedadestino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                monedadestinoKeyPressed(evt);
            }
        });

        nombremonedadestino.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        nombremonedadestino.setEnabled(false);

        buscarmonedadestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarmonedadestinoActionPerformed(evt);
            }
        });

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel88.setText("A");

        cotizacionmoneda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        cotizacionmoneda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cotizacionmoneda.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        cotizacionmoneda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cotizacionmonedaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cotizacionmonedaFocusLost(evt);
            }
        });
        cotizacionmoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cotizacionmonedaActionPerformed(evt);
            }
        });
        cotizacionmoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cotizacionmonedaKeyReleased(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel89.setText("Cotización");

        recibe.setEditable(false);
        recibe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        recibe.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        recibe.setEnabled(false);
        recibe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                recibeKeyPressed(evt);
            }
        });

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel90.setText("El Cliente Recibe");

        vuelto.setEditable(false);
        vuelto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        vuelto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        vuelto.setEnabled(false);
        vuelto.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        vuelto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vueltoKeyPressed(evt);
            }
        });

        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel91.setText("Vuelto");

        caja_divisa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel28.setText("Caja/Banco");

        nombrebancodivisa.setEnabled(false);

        jLabel29.setText("1 - GUARANÍES");

        jLabel30.setText("2 - DÓLARES AMERICANOS");

        jLabel31.setText("3 - REAL BRASILERO");

        jLabel32.setText("4 - PERO ARGENTINO");

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel85, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel87, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel80, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel82, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel88, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel63Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha_carga, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(operacion, 0, 87, Short.MAX_VALUE)
                                .addComponent(cantidadcambio, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addGap(35, 35, 35))
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel63Layout.createSequentialGroup()
                                .addComponent(monedadestino, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buscarmonedadestino, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel63Layout.createSequentialGroup()
                                .addComponent(monedaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buscarmonedaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombremonedadestino, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombremonedaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel63Layout.createSequentialGroup()
                                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel89, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel90, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel63Layout.createSequentialGroup()
                                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel91)
                                    .addComponent(jLabel84))
                                .addGap(26, 26, 26))))
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(recibe, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cotizacionmoneda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(caja_divisa, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vuelto)
                            .addComponent(entrega, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarClienteDivisa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrebancodivisa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel63Layout.createSequentialGroup()
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel80)
                            .addComponent(fecha_carga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel85)
                            .addComponent(operacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel87)
                            .addComponent(cantidadcambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(buscarmonedaorigen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel82)
                                    .addComponent(monedaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(nombremonedaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombremonedadestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(monedadestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel88))
                            .addComponent(buscarmonedadestino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cotizacionmoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel89))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(recibe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel90))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel84)
                            .addComponent(entrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vuelto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel91))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(caja_divisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28)
                                .addComponent(BuscarClienteDivisa, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombrebancodivisa))
                        .addContainerGap(14, Short.MAX_VALUE))))
        );

        jPanel64.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCarga.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GrabarCarga.setText("Grabar");
        GrabarCarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCargaActionPerformed(evt);
            }
        });
        GrabarCarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                GrabarCargaKeyReleased(evt);
            }
        });

        SalirCarga.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCarga.setText("Salir");
        SalirCarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCargaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(GrabarCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(SalirCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel64Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarCarga)
                    .addComponent(SalirCarga))
                .addContainerGap())
        );

        jPanel65.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel86.setText("Cambio de DIVISAS");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel86)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout divisasLayout = new javax.swing.GroupLayout(divisas.getContentPane());
        divisas.getContentPane().setLayout(divisasLayout);
        divisasLayout.setHorizontalGroup(
            divisasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        divisasLayout.setVerticalGroup(
            divisasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divisasLayout.createSequentialGroup()
                .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BMoneda.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BMoneda.setTitle("null");

        jPanel66.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combomoneda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combomoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combomoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combomoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combomonedaActionPerformed(evt);
            }
        });

        jTBuscarMoneda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarMoneda.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.jTBuscarClientes.text")); // NOI18N
        jTBuscarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarMonedaActionPerformed(evt);
            }
        });
        jTBuscarMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarMonedaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combomoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jScrollPane13.setViewportView(tablamoneda);

        jPanel67.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarMoneda.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarMonedaActionPerformed(evt);
            }
        });

        SalirMoneda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirMoneda.setText(org.openide.util.NbBundle.getMessage(punto_venta_multicaja_ferremax.class, "ventas.SalirCliente.text")); // NOI18N
        SalirMoneda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirMonedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel67Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarMoneda)
                    .addComponent(SalirMoneda))
                .addContainerGap())
        );

        javax.swing.GroupLayout BMonedaLayout = new javax.swing.GroupLayout(BMoneda.getContentPane());
        BMoneda.getContentPane().setLayout(BMonedaLayout);
        BMonedaLayout.setHorizontalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BMonedaLayout.setVerticalGroup(
            BMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BMonedaLayout.createSequentialGroup()
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Acceso al Punto de Venta");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jXPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelTask1.setText("Ingrese al Punto de Venta");
        labelTask1.setDescription("Seleccione las Distintas Opciones para el Acceso Correcto al POS");

        javax.swing.GroupLayout jXPanel1Layout = new javax.swing.GroupLayout(jXPanel1);
        jXPanel1.setLayout(jXPanel1Layout);
        jXPanel1Layout.setHorizontalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel1Layout.createSequentialGroup()
                .addComponent(labelTask1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jXPanel1Layout.setVerticalGroup(
            jXPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelTask1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jXPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione Sucursal");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Seleccione Caja");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Seleccione el Vendedor");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Ingrese el Nro. Factura Inicial");

        ingresofactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        sucursal_acceso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        nombresucursalacceso.setEditable(false);

        caja_acceso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        caja_acceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caja_accesoActionPerformed(evt);
            }
        });

        nombrecajaacceso.setEditable(false);

        vendedor_acceso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        nombrevendedoracceso.setEditable(false);

        BuscarSucAcceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarSucAccesoActionPerformed(evt);
            }
        });

        BuscarVendedorAcceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarVendedorAccesoActionPerformed(evt);
            }
        });

        BuscarCajaAcceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCajaAccesoActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setText("Turno");

        turno.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        turno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MAÑANA", "TARDE", "NOCHE" }));
        turno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jXPanel2Layout = new javax.swing.GroupLayout(jXPanel2);
        jXPanel2.setLayout(jXPanel2Layout);
        jXPanel2Layout.setHorizontalGroup(
            jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jXPanel2Layout.createSequentialGroup()
                        .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXPanel2Layout.createSequentialGroup()
                                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(97, 97, 97))
                                    .addComponent(jLabel4))
                                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jXPanel2Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(ingresofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jXPanel2Layout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(nombrecajaacceso, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel1))
                        .addContainerGap(52, Short.MAX_VALUE))
                    .addGroup(jXPanel2Layout.createSequentialGroup()
                        .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                        .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel2Layout.createSequentialGroup()
                                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(caja_acceso, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(vendedor_acceso, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sucursal_acceso, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BuscarSucAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BuscarCajaAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BuscarVendedorAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addComponent(turno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nombrevendedoracceso)
                            .addComponent(nombresucursalacceso, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(52, Short.MAX_VALUE))))
        );
        jXPanel2Layout.setVerticalGroup(
            jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jXPanel2Layout.createSequentialGroup()
                        .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(sucursal_acceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BuscarSucAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(caja_acceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCajaAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jXPanel2Layout.createSequentialGroup()
                        .addComponent(nombresucursalacceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nombrecajaacceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombrevendedoracceso)
                    .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(vendedor_acceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BuscarVendedorAcceso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(turno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jXPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ingresofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jXPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ingresopos.setText("Ingresar al Punto de Venta");
        ingresopos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ingresopos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresoposActionPerformed(evt);
            }
        });

        salirpos.setText("Abandonar");
        salirpos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirposActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXPanel3Layout = new javax.swing.GroupLayout(jXPanel3);
        jXPanel3.setLayout(jXPanel3Layout);
        jXPanel3Layout.setHorizontalGroup(
            jXPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel3Layout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(ingresopos)
                .addGap(33, 33, 33)
                .addComponent(salirpos, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jXPanel3Layout.setVerticalGroup(
            jXPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jXPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ingresopos)
                    .addComponent(salirpos))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jXPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jXPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirposActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirposActionPerformed

    private void ingresoposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresoposActionPerformed
        this.limpiarproductos();
        POS.setSize(1325, 755);
        POS.setLocationRelativeTo(null);
        POS.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        //Cargo el Vendedor en el punto de Vent

        this.factura.setText(this.ingresofactura.getText());
        this.caja.setText(this.caja_acceso.getText());
        this.sucursal.setText(this.sucursal_acceso.getText());
        this.nombresucursal.setText(this.nombresucursalacceso.getText());
        this.nombrecaja.setText(this.nombrecajaacceso.getText());
        this.vendedor.setText(this.vendedor_acceso.getText());
        this.nombrevendedor.setText(this.nombrevendedoracceso.getText());
        POS.setModal(true);
        POS.setVisible(true);
        this.codigoproducto.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_ingresoposActionPerformed


    private void POSWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_POSWindowGainedFocus
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_POSWindowGainedFocus

    private void GrabarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarVentaActionPerformed
        if (Integer.valueOf(comprobante.getText()) == 2) {
            clienteDAO cliDAO = new clienteDAO();
            cliente cl = null;
            cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
            cuenta_clientes cta = null;

            try {
                cl = cliDAO.buscarIdSimple(Integer.valueOf(cliente.getText()));
                cta = ctaDAO.MostrarAgrupadoxCliente(Integer.valueOf(cliente.getText()));
                limitecredito.setText(formatea.format(cl.getLimitecredito()));
                disponible.setText(formatea.format(cl.getLimitecredito() - cta.getSaldo().doubleValue()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            String cEstaCompra = this.totalneto.getText();
            cEstaCompra = cEstaCompra.replace(".", "");
            String cDisponible = this.disponible.getText();
            cDisponible = cDisponible.replace(".", "");
            if (Double.valueOf(cEstaCompra) > Double.valueOf(cDisponible)) {
                JOptionPane.showMessageDialog(null, "Crédito no Disponible");
                this.codigoproducto.requestFocus();
                return;
            }
        }

        if (this.totalneto.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Esta Venta no Tiene Detalles");
            this.codigoproducto.requestFocus();
            return;
        } else {
            if (Integer.valueOf(comprobante.getText()) == 1) {
                totalbrutoapagar.setText(totalneto.getText());
                cobrar.setSize(719, 320);
                cobrar.setLocationRelativeTo(null);
                cobrar.setModal(true);
                cobrar.setVisible(true);
                this.cobroefectivo.requestFocus();
            } else {
                Object[] opciones = {"  Si   ", "   No   "};
                int ret = JOptionPane.showOptionDialog(null, "Confirmar la Transacción ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
                if (ret == 0) {
                    this.GrabarVenta();
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarVentaActionPerformed

    private void salirpuntoventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirpuntoventaActionPerformed

        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        this.sumatoria();
        this.limpiarobjetos();
        this.limpiarproductos();
        POS.setModal(true);
        POS.setVisible(false);
        salirpos.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_salirpuntoventaActionPerformed

    private void sucambioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sucambioFocusGained

        // TODO add your handling code here:
    }//GEN-LAST:event_sucambioFocusGained

    private void AceptarCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobroActionPerformed

        String cTotalAPagar = totalbrutoapagar.getText();
        String cPagoEfectivo = cobroefectivo.getText();

        cTotalAPagar = cTotalAPagar.replace(".", "");
        cPagoEfectivo = cPagoEfectivo.replace(".", "");

        if (Double.valueOf(cPagoEfectivo) > Double.valueOf(cTotalAPagar)) {
            this.sucambio.setText(formatea.format(Double.valueOf(cPagoEfectivo) - Double.valueOf(cTotalAPagar)));
        } else {
            if (Double.valueOf(cPagoEfectivo) < Double.valueOf(cTotalAPagar)) {
                JOptionPane.showMessageDialog(null, "El Pago es Menor al Importe de la Factura");
                this.cobroefectivo.requestFocus();
                return;
            } else {
                this.sucambio.setText("0");
            }
        }

        Object[] opciones = {"  Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Confirmar la Venta ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            this.cobrar.setVisible(false);
            this.GrabarVenta();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCobroActionPerformed

    private void SalirFormaCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFormaCobroActionPerformed
        this.cobrar.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFormaCobroActionPerformed

    public void ImprimirRecibo() {
        /* Array para almacenar las impresoras del sistema */
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreRecibo = config.getNombrerecibo();

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0) {

            //se elige la impresora
            PrintService impresora = printer.buscar(suc.getImpresorarecibosuc());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport
                    String num = totalcobro.getText();
                    num = num.replace(".", "");
                    num = num.replace(",", ".");
                    numero_a_letras numero = new numero_a_letras();
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                    parameters.put("cNombreEmpresa", config.getEmpresa());
                    parameters.put("cReferencia", this.creferencia.getText());
                    parameters.put("nCliente", this.codclie.getText());

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    URL url = getClass().getClassLoader().getResource("Reports/" + suc.getNombrerecibosuc().trim());

                    jasperReport = (JasperReport) JRLoader.loadObject(url);
                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stm.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } else {
            System.out.println("NO HAY IMPRESORA");
        }
    }

    public void Imprimir2() {
        /* Array para almacenar las impresoras del sistema */
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreFactura = config.getNombrefactura();

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0)//si existen impresoras
        {
            //se elige la impresora
            PrintService impresora = printer.buscar(suc.getImpresorafacturasuc());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport
                    String num = totalneto.getText();
                    num = num.replace(".", "");
                    num = num.replace(",", ".");
                    numero_a_letras numero = new numero_a_letras();

                    parameters.put("cRuc", config.getRuc());
                    parameters.put("cTelefono", config.getTelefono());
                    parameters.put("cDireccion", config.getDireccion());
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                    parameters.put("cNombreEmpresa", config.getEmpresa());
                    parameters.put("cReferencia", this.referencia.getText());

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    if (Double.valueOf(num) > 0) {
                        URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.trim());
                        jasperReport = (JasperReport) JRLoader.loadObject(url);
                    } else {
                        URL url = getClass().getClassLoader().getResource("Reports/notacreditoferremax.jasper");
                        jasperReport = (JasperReport) JRLoader.loadObject(url);
                    }

                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stm.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    private void impriminota() {
        Object[] opciones = {"  Si   ", "   No   "};
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreFactura = config.getNombrefactura();

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(Integer.valueOf(2));
            cNombreFactura = suc.getNombrefacturasuc();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0)//si existen impresoras
        {
            //se elige la impresora
            PrintService impresora = printer.buscar(suc.getImpresorafacturasuc());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport
                    String num = totalneto.getText();
                    num = num.replace(".", "");
                    num = num.replace(",", ".");
                    num = num.replace("-", "");
                    numero_a_letras numero = new numero_a_letras();

                    parameters.put("cRuc", config.getRuc());
                    parameters.put("cTelefono", config.getTelefono());
                    parameters.put("cDireccion", config.getDireccion());
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                    parameters.put("cNombreEmpresa", config.getEmpresa());
                    parameters.put("cReferencia", this.referencia.getText());

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    System.out.println(cNombreFactura);
                    URL url = getClass().getClassLoader().getResource("Reports/notacreditoferremax.jasper");
                    jasperReport = (JasperReport) JRLoader.loadObject(url);

                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stm.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    private void GenerarImpresion() {
        Object[] opciones = {"  Si   ", "   No   "};
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreFactura = config.getNombrefactura();

        cajaDAO cajDAO = new cajaDAO();
        caja caj = new caja();
        try {
            caj = cajDAO.buscarId(Integer.valueOf(this.caja.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0)//si existen impresoras
        {
            //se elige la impresora
            PrintService impresora = printer.buscar(caj.getImpresoracaja());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport
                    //String num = totalneto.getText();
                    String num = totalcompleto.getText();
                    //num = num.replace(".", "");
                    //num = num.replace(",", ".");
                    numero_a_letras numero = new numero_a_letras();

                    parameters.put("cRamo", config.getRamo());
                    parameters.put("cResponsable", config.getResponsable());
                    parameters.put("cRuc", config.getRuc());
                    parameters.put("cTelefono", config.getTelefono());
                    parameters.put("cDireccion", config.getDireccion());
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                    parameters.put("cNombreEmpresa", config.getEmpresa());
                    parameters.put("cReferencia", this.referencia.getText());

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    URL url = getClass().getClassLoader().getResource("Reports/" + caj.getNombrefactura().trim());

                    jasperReport = (JasperReport) JRLoader.loadObject(url);
                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stm.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        Object[] opcion2 = {"  Si   ", "   No   "};
        int ret2 = JOptionPane.showOptionDialog(null, "La Impresión del Comprobante fue Exitosa ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret2 == 0) {
            this.limpiarobjetos();
            this.codigoproducto.requestFocus();
        } else {
            this.reimpresion.doClick();
        }
    }


    private void imprimirposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirposActionPerformed
        Object[] opciones = {"  Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir la Factura ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GenerarImpresion();
        } else {
            this.limpiarobjetos();
            this.codigoproducto.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_imprimirposActionPerformed

    private void sucambioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sucambioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sucambioKeyPressed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
//        this.botonbusquedacliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_clienteActionPerformed

    private void ruclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruclienteActionPerformed
        //      this.botonbusquedacliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_ruclienteActionPerformed

    private void buscarcomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcomprobanteActionPerformed
        comprobanteDAO cmDAO = new comprobanteDAO();
        comprobante cm = null;
        GrillaComprobante grillacm = new GrillaComprobante();
        Thread hiloca = new Thread(grillacm);
        hiloca.start();
        BComprobante.setModal(true);
        BComprobante.setSize(500, 575);
        BComprobante.setLocationRelativeTo(null);
        BComprobante.setVisible(true);
        BComprobante.setTitle("Buscar Comercio");
        BComprobante.setModal(false);
    }//GEN-LAST:event_buscarcomprobanteActionPerformed

    private void BuscarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarVendedorActionPerformed
        vendedorDAO veDAO = new vendedorDAO();
        vendedor vn = null;
        GrillaVendedor grillavn = new GrillaVendedor();
        Thread hilove = new Thread(grillavn);
        hilove.start();
        nGestorBusqueda = 1;
        BVendedor.setModal(true);
        BVendedor.setSize(500, 575);
        BVendedor.setLocationRelativeTo(null);
        BVendedor.setVisible(true);
        BVendedor.setTitle("Buscar Vendedor");
        BVendedor.setModal(false);
        //Establecemos un título para el jDialog
        codigoproducto.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarVendedorActionPerformed

    private void buscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarClienteActionPerformed
        nTipoBuscarCliente = 1;
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Cliente");
        BCliente.setVisible(true);
        //                giraduria.requestFocus();
        BCliente.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarClienteActionPerformed

    private void buscarpreventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarpreventaActionPerformed
        GrillaPreventas grillapre = new GrillaPreventas();
        Thread hilopreventa = new Thread(grillapre);
        hilopreventa.start();
        preventas.setModal(true);
        preventas.setSize(500, 575);
        preventas.setLocationRelativeTo(null);
        preventas.setTitle("Buscar Preventa");
        preventas.setVisible(true);
        //                giraduria.requestFocus();
        preventas.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarpreventaActionPerformed

    private void cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.codigoproducto.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadKeyPressed

    private void cantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cantidadFocusGained
        cantidad.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadFocusGained

    private void buscarproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarproductoActionPerformed
        /*        GrillaProductos grillaprd = new GrillaProductos();
        Thread hiloprod = new Thread(grillaprd);
        hiloprod.start();*/

 /*GrillaProd grillaprd = new GrillaProd();
        Thread hiloprod = new Thread(grillaprd);
        hiloprod.start();*/
        // TODO add your handling code here:
        int cantidadRegistro = modeloproducto.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloproducto.removeRow(0);
        }
        BProducto.setModal(true);
        BProducto.setSize(716, 575);
        BProducto.setLocationRelativeTo(null);
        BProducto.setVisible(true);
        codigoproducto.requestFocus();
        BProducto.setModal(false);
    }//GEN-LAST:event_buscarproductoActionPerformed

    private void codigoproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoproductoKeyPressed

        //Pulsamos el Botón F1 para DIVISAS
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            this.LimpiarDivisa();
            this.divisas.setSize(437, 450);
            divisas.setLocationRelativeTo(null);
            //Cargo el Vendedor en el punto de Vent
            divisas.setModal(true);
            divisas.setVisible(true);
            operacion.requestFocus();
        }
        //Pulsamos el Botón F2 para COBRAR
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            this.GrabarVenta.doClick();
        }

        //Pulsamos el Botón F3 para Buscar Clientes
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            buscarCliente.doClick();
        }

        //PULSAMOS F4 PARA BUSCAR COMPROBANTE
        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            buscarcomprobante.doClick();
        }

        //PULSAMOS F5 PARA AGREGAR NUEVO CLIENTE
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            this.limpiarCliente();
            this.RegistrarCliente.setSize(498, 390);
            RegistrarCliente.setLocationRelativeTo(null);
            //Cargo el Vendedor en el punto de Vent
            RegistrarCliente.setModal(true);
            RegistrarCliente.setVisible(true);
        }

        //PULSAMOS F6 PARA BUSCAR VENDEDOR
        if (evt.getKeyCode() == KeyEvent.VK_F6) {
            CancelarVenta.doClick();
        }

        //PULSAMOS F7 PARA BUSCAR VENDEDOR
        if (evt.getKeyCode() == KeyEvent.VK_F7) {
            BuscarVendedor.doClick();
        }

        //BUSCAR PRODUCTOS
        if (evt.getKeyCode() == KeyEvent.VK_F8) {
            this.buscarproducto.doClick();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F10) {
            this.LimpiarCobros();
            cajaDAO cajDAO = new cajaDAO();
            caja ca = null;
            try {
                ca = cajDAO.buscarId(Integer.valueOf(this.caja.getText()));
                if (ca.getCodigo() != 0) {
                    this.nrorecibo.setText(formatosinpunto.format(ca.getRecibo()));
                    //Establecemos un título para el jDialog
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }

            this.cobranzas.setSize(720, 520);
            cobranzas.setLocationRelativeTo(null);
            //Cargo el Vendedor en el punto de Vent
            cobranzas.setModal(true);
            cobranzas.setVisible(true);
            codclie.requestFocus();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F11) {
            //nSupervisorCaja = 3;
            //this.PantallaSupervisor();

            arqueo_caja.setSize(405, 310);
            arqueo_caja.setLocationRelativeTo(null);
            arqueo_caja.setModal(true);
            arqueo_caja.setVisible(true);
        }

        if (evt.getKeyCode() == KeyEvent.VK_F12) {
//            nSupervisorCaja = 4;
            //          this.PantallaSupervisor();
            LimpiarEgreso();
            egresos.setSize(580, 390);
            egresos.setLocationRelativeTo(null);
            egresos.setModal(true);
            egresos.setVisible(true);

        }

        //PULSAMOS LA TECLA DELETE PARA ELIMINAR ITEM
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            BorrarItemVenta.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_codigoproductoKeyPressed

    private void PantallaSupervisor() {
        pasesupervisor.setText("");
        SupervisorCaja.setSize(415, 210);
        SupervisorCaja.setLocationRelativeTo(null);
        SupervisorCaja.setModal(true);
        SupervisorCaja.setVisible(true);
        pasesupervisor.requestFocus();
    }

    private void LimpiarCobros() {
        this.nrorecibo.setText("");
        this.codclie.setText("");
        this.ruccliecobro.setText("");
        this.nombreclientecobro.setText("");
        this.saldocliente.setText("0");
        this.totalcobro.setText("0");
        this.fechacobro.setCalendar(c2);

        int cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }
    }


    private void codigoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoproductoActionPerformed
        if (this.codigoproducto.getText().length() > 0) {

            String cCodigoInterno = codigoproducto.getText().substring(0, 2).toString();
            //SE VERIFICA SI EL PRODUCTO VIENE DE LA BASCULA, SI CORRESPONDE
            //SE CAPTURA EL CODIGO INTERNO DEL PRODUCTO Y EL PESO DEL MISMO
            if (cCodigoInterno.equals("20") && codigoproducto.getText().length() == 13) {
                String cCodProducto = codigoproducto.getText().substring(2, 7).toString(); //CODIGO DEL PRODUCTO
                String cCantidad = codigoproducto.getText().substring(7, 12).toString();//PESO 
                Double nCantidad = Double.valueOf(cCantidad) / 1000;//PESO CONVERTIDO EN KILOGRAMOS
                cantidad.setText(formatcantidad.format(nCantidad));
                codigoproducto.setText(cCodProducto);
            }

            int nPosicionPrecio = codigoproducto.getText().indexOf("$"); //PARA CAPTURAR PRECIO
            int nPosicionCantidad = codigoproducto.getText().indexOf("*");//PARA CAPTURAR CANTIDAD
            int nPosicionPeso = codigoproducto.getText().indexOf(",");//PARA CAPTURAR PESO

            nPosicionPrecioMinorista = codigoproducto.getText().indexOf("+");//PARA CAPTURAR SI VA VENDER A PRECIO MINORISTA
            nPosicionPrecioCosto = codigoproducto.getText().indexOf("#");//PARA CAPTURAR SI VA VENDER A PRECIO MINORISTA
            //CAPTURAR PRECIO MINORISTA
            if (nPosicionPrecioMinorista >= 0) {
                String cCodigo = codigoproducto.getText().substring(1, codigoproducto.getText().length());
                codigoproducto.setText(cCodigo);
            }
            if (nPosicionPrecioCosto >= 0) {
                String cCodigo = codigoproducto.getText().substring(1, codigoproducto.getText().length());
                codigoproducto.setText(cCodigo);
            }

            //CAPTURAR PRECIO POR TECLADO
            if (nPosicionPrecio >= 0) {
                String cPrecio = codigoproducto.getText().substring(1, codigoproducto.getText().length());
                precio.setText(cPrecio);
                codigoproducto.setText("");
                codigoproducto.requestFocus();
                return;
            }

            //PARA CAPTURAR CANTIDAD POR TECLADO
            if (nPosicionCantidad >= 0) {
                Double nCantidad = Double.valueOf(codigoproducto.getText().substring(1, codigoproducto.getText().length()));
                cantidad.setText(formatcantidad.format(nCantidad));
                codigoproducto.setText("");
                codigoproducto.requestFocus();
                return;
            }

            //PARA CAPTURAR PESO POR TECLADO
            if (nPosicionPeso > 0) {
                String cCantidad = codigoproducto.getText();
                cCantidad = cCantidad.replace(",", ".");
                Double nCantidad = Double.valueOf(cCantidad);
                cantidad.setText(formatcantidad.format(nCantidad));
                codigoproducto.setText("");
                codigoproducto.requestFocus();
                return;
            }
        }

        if (Config.cCodProducto == "") {;
            productoDAO producto = new productoDAO();
            producto p = null;
            try {
                p = producto.BuscarProductoBasico(this.codigoproducto.getText());
                if (p.getCodigo() == null) {
                    BProducto.setModal(true);
                    BProducto.setSize(500, 575);
                    BProducto.setLocationRelativeTo(null);
                    BProducto.setVisible(true);
                    codigoproducto.requestFocus();
                    BProducto.setModal(false);
                } else {
                    //EN CASO QUE HAYA SELECCIONADO LA OPCION PARA VENDER A PRECIO
                    //MINORISTA
                    System.out.println("LISTA DE PRECIOS " + nListaPrecio);

                    if (nPosicionPrecioMinorista >= 0) {
                        double nPrecio = p.getPrecio_minimo().doubleValue();
                        String cPrecio = String.valueOf(Math.round(nPrecio));
                        //CAMBIA EL PRECIO EN CASO QUE NO HAYA CAMBIADO EL PRECIO POR TECLADO
                        if (Double.valueOf(precio.getText()) == 0) {
                            precio.setText(cPrecio);
                        }
                    }
                    if (nPosicionPrecioCosto >= 0) {
                        double nPrecio = p.getCosto().doubleValue();
                        String cPrecio = String.valueOf(Math.round(nPrecio));
                        if (Double.valueOf(precio.getText()) == 0) {
                            precio.setText(cPrecio);
                        }

                    } else {
                        double nPrecio = p.getPrecio_maximo().doubleValue();
                        String cPrecio = String.valueOf(Math.round(nPrecio));
                        if (Double.valueOf(precio.getText()) == 0) {
                            precio.setText(cPrecio);
                        }
                    }

                    this.etiquetaproducto.setText(p.getNombre());
                    this.iva.setText(String.valueOf(p.getIvaporcentaje()));
                    //Establecemos un título para el jDialog
                    cantidad.requestFocus();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
        }
        if (nListaPrecio == 2) {
            if (precio.getText().isEmpty()) {
                precio.setText("10000");
            }
        }

        nFila = tabladetalle.getRowCount() + 1;
        if (nTipoImpresora == 1) {
            if (nFila <= nItemProductos) {
                this.AgregarItem.doClick();
            } else {
                JOptionPane.showMessageDialog(null, "Los Items de Productos están Limitados a " + nItemProductos);
                this.codigoproducto.requestFocus();
                return;
            }
        } else {
            this.AgregarItem.doClick();
        }
        nPosicionPrecioMinorista = -1;
        nPosicionPrecioCosto = -1;
        this.limpiarproductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoproductoActionPerformed

    private void nombreclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreclienteActionPerformed

    private void direccionclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionclienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionclienteActionPerformed

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
        if (nTipoBuscarCliente == 1) {
            this.cliente.setText(this.tablacliente.getValueAt(nFila, 0).toString());
            this.nombrecliente.setText(this.tablacliente.getValueAt(nFila, 1).toString());
            this.direccioncliente.setText(this.tablacliente.getValueAt(nFila, 2).toString());
            this.rucliente.setText(this.tablacliente.getValueAt(nFila, 3).toString());
            codigoproducto.requestFocus();
        } else {
            this.codclie.setText(this.tablacliente.getValueAt(nFila, 0).toString());
            this.nombreclientecobro.setText(this.tablacliente.getValueAt(nFila, 1).toString());
            this.ruccliecobro.setText(this.tablacliente.getValueAt(nFila, 3).toString());
            this.codclie.requestFocus();
        }
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

    private void combovendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combovendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combovendedorActionPerformed

    private void jTBuscarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorActionPerformed

    private void jTBuscarVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarVendedorKeyPressed
        this.jTBuscarVendedor.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarVendedor.getText()).toUpperCase();
                jTBuscarVendedor.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combovendedor.getSelectedIndex()) {
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
                filtrovendedor(indiceColumnaTabla);
            }
        });
        trsfiltrovendedor = new TableRowSorter(tablavendedor.getModel());
        tablavendedor.setRowSorter(trsfiltrovendedor);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarVendedorKeyPressed

    private void tablavendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablavendedorMouseClicked
        this.AceptarVendedor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorMouseClicked

    private void tablavendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablavendedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarVendedor.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablavendedorKeyPressed

    private void AceptarVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarVendedorActionPerformed
        int nFila = this.tablavendedor.getSelectedRow();
        if (nGestorBusqueda == 1) {
            this.vendedor.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
            this.nombrevendedor.setText(this.tablavendedor.getValueAt(nFila, 1).toString());
        } else {
            this.vendedor_acceso.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
            this.nombrevendedoracceso.setText(this.tablavendedor.getValueAt(nFila, 1).toString());
        }
        nGestorBusqueda = 0;
        this.BVendedor.setVisible(false);
        this.jTBuscarVendedor.setText("");
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void combocomprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocomprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocomprobanteActionPerformed

    private void jTBuscarComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarComprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobanteActionPerformed

    private void jTBuscarComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarComprobanteKeyPressed
        this.jTBuscarComprobante.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarComprobante.getText()).toUpperCase();
                jTBuscarComprobante.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocomprobante.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrocomprobante(indiceColumnaTabla);
            }
        });
        trsfiltrocomprobante = new TableRowSorter(tablacomprobante.getModel());
        tablacomprobante.setRowSorter(trsfiltrocomprobante);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarComprobanteKeyPressed

    private void tablacomprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacomprobanteMouseClicked
        this.AceptarComprobante.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteMouseClicked

    private void tablacomprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacomprobanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarComprobante.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablacomprobanteKeyPressed

    private void AceptarComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarComprobanteActionPerformed
        int nFila = this.tablacomprobante.getSelectedRow();
        this.comprobante.setText(this.tablacomprobante.getValueAt(nFila, 0).toString());
        this.nombrecomprobante.setText(this.tablacomprobante.getValueAt(nFila, 1).toString());

        this.BComprobante.setVisible(false);
        this.jTBuscarComprobante.setText("");
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarComprobanteActionPerformed

    private void SalirComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirComprobanteActionPerformed
        this.BComprobante.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirComprobanteActionPerformed

    private void comboformaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboformaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboformaActionPerformed

    private void jTBuscarFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarFormaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFormaActionPerformed

    private void jTBuscarFormaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarFormaKeyPressed
        this.jTBuscarForma.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarForma.getText()).toUpperCase();
                jTBuscarForma.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (comboforma.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtroformapago(indiceColumnaTabla);
            }
        });
        trsfiltroformapago = new TableRowSorter(tablaformapago.getModel());
        tablaformapago.setRowSorter(trsfiltroformapago);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarFormaKeyPressed

    private void tablaformapagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaformapagoMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaformapagoMouseClicked

    private void tablaformapagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaformapagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaformapagoKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablaformapago.getSelectedRow();
        this.comprobante.setText(this.tablaformapago.getValueAt(nFila, 0).toString());
        this.nombrecomprobante.setText(this.tablaformapago.getValueAt(nFila, 1).toString());

        this.BFormaPago.setVisible(false);
        this.jTBuscarForma.setText("");
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BFormaPago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void AgregarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarItemActionPerformed
        if (this.codigoproducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código del Producto");
            this.codigoproducto.requestFocus();
            return;
        }

        String cPrecio = this.precio.getText();
        cPrecio = cPrecio.replace(".", "");
        String cValorCantidad = this.cantidad.getText();
        cValorCantidad = cValorCantidad.replace(".", "").replace(",", ".");
        Object[] fila = new Object[7];
        //  fila[0] = tabladetalle.getRowCount() + 1;
        fila[1] = this.codigoproducto.getText().toString();
        fila[2] = this.etiquetaproducto.getText().toString();
        fila[3] = formatcantidad.format(Double.valueOf(cValorCantidad));
        fila[4] = formatea.format(Double.valueOf(cPrecio));
        double nSubtotal = Double.valueOf((Double.valueOf(cValorCantidad) * Double.valueOf(cPrecio)));
        fila[5] = formatea.format(nSubtotal);
        fila[6] = this.iva.getText();
        modelo.addRow(fila);
        this.sumatoria();
        this.limpiarItem();
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AgregarItemActionPerformed

    private void precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioActionPerformed

    private void buscarsucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarsucursalActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();
        nGestorBusqueda = 2;
        BSucursal.setModal(true);
        BSucursal.setSize(500, 575);
        BSucursal.setLocationRelativeTo(null);
        BSucursal.setTitle("Buscar Sucursal");
        BSucursal.setVisible(true);
        BSucursal.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarsucursalActionPerformed

    private void buscarcajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcajaActionPerformed
        GrillaCaja grillaca = new GrillaCaja();
        Thread hilocaja = new Thread(grillaca);
        hilocaja.start();
        BCaja.setModal(true);
        BCaja.setSize(500, 575);
        BCaja.setLocationRelativeTo(null);
        BCaja.setVisible(true);
        BCaja.setTitle("Buscar Caja");
        BCaja.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcajaActionPerformed

    private void BuscarVendedorAccesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarVendedorAccesoActionPerformed
        vendedorDAO veDAO = new vendedorDAO();
        vendedor vn = null;
        GrillaVendedor grillavn = new GrillaVendedor();
        Thread hilove = new Thread(grillavn);
        hilove.start();
        nGestorBusqueda = 2;
        BVendedor.setModal(true);
        BVendedor.setSize(500, 575);
        BVendedor.setLocationRelativeTo(null);
        BVendedor.setVisible(true);
        BVendedor.setTitle("Buscar Vendedor");
        BVendedor.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarVendedorAccesoActionPerformed

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
                filtrocaja(indiceColumnaTabla);
            }
        });
        trsfiltrocaja = new TableRowSorter(tablacaja.getModel());
        tablacaja.setRowSorter(trsfiltrocaja);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarCajaKeyPressed
    public void filtrocaja(int nNumeroColumna) {
        trsfiltrocaja.setRowFilter(RowFilter.regexFilter(this.jTBuscarCaja.getText(), nNumeroColumna));
    }


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
        this.caja_acceso.setText(this.tablacaja.getValueAt(nFila, 0).toString());
        this.caja.setText(this.tablacaja.getValueAt(nFila, 0).toString());
        this.nombrecajaacceso.setText(this.tablacaja.getValueAt(nFila, 1).toString());
        this.nombrecaja.setText(this.tablacaja.getValueAt(nFila, 1).toString());
        String cBoca = tablacaja.getValueAt(nFila, 3).toString();
        Double nFactura = Double.valueOf(tablacaja.getValueAt(nFila, 2).toString());
        int n = (int) nFactura.doubleValue();
        String formatString = String.format("%%0%dd", 7);
        String formattedString = String.format(formatString, n);
        this.factura.setText(cBoca + "-" + formattedString);
        this.ingresofactura.setText(cBoca + "-" + formattedString);
        this.nrotimbrado.setText(tablacaja.getValueAt(nFila, 4).toString());
        try {
            this.iniciotimbrado.setDate(formatoFecha.parse(this.tablacaja.getValueAt(nFila, 5).toString()));
            this.vencetimbrado.setDate(formatoFecha.parse(this.tablacaja.getValueAt(nFila, 6).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        this.BCaja.setVisible(false);
        this.jTBuscarCaja.setText("");
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCajaActionPerformed

    private void SalirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCajaActionPerformed
        this.BCaja.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCajaActionPerformed

    private void BuscarCajaAccesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCajaAccesoActionPerformed
        cajaDAO cajDAO = new cajaDAO();
        caja ca = null;
        GrillaCaja grillaca = new GrillaCaja();
        Thread hilocaja = new Thread(grillaca);
        hilocaja.start();
        BCaja.setModal(true);
        BCaja.setSize(500, 575);
        BCaja.setLocationRelativeTo(null);
        BCaja.setVisible(true);
        BCaja.setTitle("Buscar Caja");
        BCaja.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCajaAccesoActionPerformed

    private void caja_accesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caja_accesoActionPerformed
        this.BuscarCajaAcceso.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_caja_accesoActionPerformed

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
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 2;
                        break;//por RUC
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
        if (nGestorBusqueda == 1) {
            this.sucursal_acceso.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
            this.nombresucursalacceso.setText(this.tablasucursal.getValueAt(nFila, 1).toString());
            this.caja_acceso.requestFocus();
        } else {
            this.sucursal.setText(this.tablasucursal.getValueAt(nFila, 0).toString());
            this.nombresucursal.setText(this.tablasucursal.getValueAt(nFila, 1).toString());
            this.codigoproducto.requestFocus();
        }

        String cBoca = tablasucursal.getValueAt(nFila, 3).toString();
        Double nFactura = Double.valueOf(tablasucursal.getValueAt(nFila, 2).toString());
        int n = (int) nFactura.doubleValue();
        String formatString = String.format("%%0%dd", 7);
        String formattedString = String.format(formatString, n);
        this.factura.setText(cBoca + "-" + formattedString);
        this.ingresofactura.setText(cBoca + "-" + formattedString);
        this.nrotimbrado.setText(tablasucursal.getValueAt(nFila, 4).toString());
        try {
            this.iniciotimbrado.setDate(formatoFecha.parse(this.tablasucursal.getValueAt(nFila, 5).toString()));
            this.vencetimbrado.setDate(formatoFecha.parse(this.tablasucursal.getValueAt(nFila, 5).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        nGestorBusqueda = 0;
        this.BSucursal.setVisible(false);
        this.jTBuscarSucursal.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSucActionPerformed

    private void SalirSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSucActionPerformed
        this.BSucursal.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSucActionPerformed

    private void BuscarSucAccesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarSucAccesoActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();
        nGestorBusqueda = 1;
        BSucursal.setModal(true);
        BSucursal.setSize(500, 575);
        BSucursal.setLocationRelativeTo(null);
        BSucursal.setTitle("Buscar Sucursal");
        BSucursal.setVisible(true);
        BSucursal.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarSucAccesoActionPerformed

    private void codigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codigoFocusLost
        if (Double.valueOf(this.codigo.getText()) != 0) {
            CalcularRuc rucDigito = new CalcularRuc();
            String cCodigo = this.codigo.getText();
            int base = 11;
            int digito = rucDigito.CalcularDigito(cCodigo, base);
            ruc.setText(cCodigo.toString() + '-' + String.valueOf(digito));
        }

        this.BuscarCliente.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoFocusLost

    private void codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoActionPerformed

    private void codigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_codigoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.BuscarCliente.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoKeyPressed

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.direccion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.codigo.requestFocus();
        }                // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyPressed

    private void nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyReleased
        String letras = ConvertirMayusculas.cadena(nombre);
        nombre.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreKeyReleased

    private void fechaingresoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaingresoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaingresoFocusGained

    private void fechaingresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaingresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.nacimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombre.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_fechaingresoKeyPressed

    private void nacimientoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nacimientoFocusGained

        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoFocusGained

    private void nacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nacimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.ruc.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fechaingreso.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_nacimientoKeyPressed

    private void rucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rucKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.celular.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nacimiento.requestFocus();
        }           // TODO add your handling code here:
    }//GEN-LAST:event_rucKeyPressed

    private void celularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_celularKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarCliente.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ruc.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_celularKeyPressed

    private void BuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarClienteActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        int nestado = 0;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codigo.getText()));
            if (cl.getCodigo() != 0) {
                control.setText("1");
                nestado = cl.getEstado();
                if (nestado == 1) {
                    habilitado.setSelected(true);
                } else {
                    habilitado.setSelected(false);
                }
                nombre.setText(cl.getNombre());
                ruc.setText(cl.getRuc());
                celular.setText(cl.getTelefono());
                fechaingreso.setDate(cl.getFechaingreso());
                nacimiento.setDate(cl.getFechanacimiento());
                direccion.setText(cl.getDireccion());
                //Establecemos un título para el jDialog
            }
            nombre.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarClienteActionPerformed

    private void GrabarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarClienteActionPerformed
        if (this.codigo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nro. de Cédula");
            this.codigo.requestFocus();
            return;
        }
        if (this.nombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nombre del Cliente");
            this.nombre.requestFocus();
            return;
        }
        if (this.ruc.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el N° de CI Cliente");
            this.ruc.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            Date dFechaCumple = ODate.de_java_a_sql(nacimiento.getDate());
            Date dFechaIngreso = ODate.de_java_a_sql(fechaingreso.getDate());
            clienteDAO grabarcliente = new clienteDAO();
            localidadDAO local = new localidadDAO();
            localidad loc = null;
            cliente c = new cliente();
            c.setCodigo(Integer.valueOf(codigo.getText()));
            c.setNombre(nombre.getText());
            c.setDireccion("SIN INFORMACION");
            if (habilitado.isSelected()) {
                c.setEstado(1);
            } else {
                c.setEstado(0);
            }
            try {
                loc = local.buscarLocalidad(1);
                c.setLocalidad(loc);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            c.setRuc(ruc.getText());
            c.setTelefono(celular.getText());
            c.setFechanacimiento(dFechaCumple);
            c.setFechaingreso(dFechaIngreso);
            c.setDireccion(direccion.getText());
            if (Integer.valueOf(control.getText()) == 0) {
                // Agregar nuevo Cliente
                if (codigo.getText().compareTo("") != 0 && nombre.getText().compareTo("") != 0
                        && ruc.getText().compareTo("") != 0) {
                    try {
                        grabarcliente.insertarCliente(c);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Algunos Datos son Necesarios");
                }
            } else {
                //Actualizar Datos de Clientes
                if (codigo.getText().compareTo("") != 0 && nombre.getText().compareTo("") != 0
                        && ruc.getText().compareTo("") != 0) {
                    try {
                        grabarcliente.actualizarCliente(c);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Algunos Datos son Necesarios");
                }
            }
        }
        this.cliente.setText(codigo.getText());
        this.nombrecliente.setText(nombre.getText());
        this.rucliente.setText(ruc.getText());
        this.direccioncliente.setText(direccion.getText());
        GrillaCliente grillacl = new GrillaCliente();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();
        this.SalirCliente.doClick();
        this.limpiarCliente();

        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarClienteActionPerformed

    private void SalirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirClienteActionPerformed
        RegistrarCliente.setModal(true);
        RegistrarCliente.setVisible(false);
        this.limpiarCliente();
    }//GEN-LAST:event_SalirClienteActionPerformed

    private void direccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.fechaingreso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nombre.requestFocus();
        }                // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionKeyPressed

    private void direccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionKeyReleased

    private void BorrarItemVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarItemVentaActionPerformed
        /*SupervisorCaja.setSize(415, 210);
        SupervisorCaja.setLocationRelativeTo(null);
        SupervisorCaja.setModal(true);
        SupervisorCaja.setVisible(true);
        pasesupervisor.requestFocus();*/
        int a = this.tabladetalle.getSelectedRow();
        if (a < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelo.removeRow(a);
                this.sumatoria();
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
            }
            this.SalirSupervisor.doClick();
        }

    }//GEN-LAST:event_BorrarItemVentaActionPerformed

    private void POSWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_POSWindowClosing

        // TODO add your handling code here:
    }//GEN-LAST:event_POSWindowClosing

    private void POSWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_POSWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_POSWindowClosed

    private void POSWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_POSWindowLostFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_POSWindowLostFocus

    private void FiltrarPreventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FiltrarPreventaKeyPressed
        this.ComboBuscarPreventa.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (FiltrarPreventa.getText()).toUpperCase();
                FiltrarPreventa.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (ComboBuscarPreventa.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 0;
                        break;//por numero
                    case 1:
                        indiceColumnaTabla = 2;
                        break;//por nombre
                }
                repaint();
                filtrosuc(indiceColumnaTabla);
            }
        });
        trsfiltropreventa = new TableRowSorter(tablapreventa.getModel());
        tablapreventa.setRowSorter(trsfiltropreventa);

        // TODO add your handling code here:
    }//GEN-LAST:event_FiltrarPreventaKeyPressed

    private void SalirPreventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPreventaActionPerformed
        this.preventas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPreventaActionPerformed

    private void AceptarPreventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarPreventaActionPerformed
        int nFila = this.tablapreventa.getSelectedRow();
        String cDescuento = this.tablapreventa.getValueAt(nFila, 14).toString();
        cDescuento = cDescuento.replace(".", "");
        String cFirma = this.tablapreventa.getValueAt(nFila, 15).toString();

        if (Double.valueOf(cDescuento) > 0 && cFirma.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Descuento aún no fue aprobado");
            codigoproducto.requestFocus();
            return;
        }

        this.preventa.setText(this.tablapreventa.getValueAt(nFila, 0).toString());
        this.nombrecliente.setText(this.tablapreventa.getValueAt(nFila, 2).toString());
        this.cliente.setText(this.tablapreventa.getValueAt(nFila, 4).toString());

        if (this.tablapreventa.getValueAt(nFila, 5).toString().isEmpty()) {
            this.rucliente.setText("SD");
        } else {
            this.rucliente.setText(this.tablapreventa.getValueAt(nFila, 5).toString());
        }

        if (this.tablapreventa.getValueAt(nFila, 6).toString().isEmpty()) {
            this.direccioncliente.setText("SD");
        } else {
            this.direccioncliente.setText(this.tablapreventa.getValueAt(nFila, 6).toString());
        }
        this.vendedor.setText(this.tablapreventa.getValueAt(nFila, 7).toString());
        this.nombrevendedor.setText(this.tablapreventa.getValueAt(nFila, 8).toString());
        this.comprobante.setText(this.tablapreventa.getValueAt(nFila, 9).toString());
        this.nombrecomprobante.setText(this.tablapreventa.getValueAt(nFila, 10).toString());
        if (this.tablapreventa.getValueAt(nFila, 11).toString().isEmpty()) {
            this.observacion.setText("Venta de Mercaderías");
        } else {
            this.observacion.setText(this.tablapreventa.getValueAt(nFila, 11).toString());
        }
        this.obra.setText(this.tablapreventa.getValueAt(nFila, 12).toString());
        this.nombreobra.setText(this.tablapreventa.getValueAt(nFila, 13).toString().trim());
        this.caja.setText(this.tablapreventa.getValueAt(nFila, 16).toString().trim());

        if (!cliente.getText().equals("1") && comprobante.getText().equals("1")) {
            cajaDAO sucDAO = new cajaDAO();
            caja caj = null;
            try {
                caj = sucDAO.buscarId(Integer.valueOf(caja.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            nombrecaja.setText(caj.getNombre());
            String cBoca = caj.getExpedicion();
            Double nFactura = caj.getFactura();
            int n = (int) nFactura.doubleValue();
            String formatString = String.format("%%0%dd", 7);
            String formattedString = String.format(formatString, n);
            this.ingresofactura.setText(cBoca + "-" + formattedString);
            this.factura.setText(cBoca + "-" + formattedString);
        } else {
            cajaDAO sucDAO = new cajaDAO();
            caja caj = null;
            try {
                caj = sucDAO.buscarId(2);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            caja.setText("2");
            nombrecaja.setText(caj.getNombre());
            String cBoca = caj.getExpedicion();
            Double nFactura = caj.getFactura();
            int n = (int) nFactura.doubleValue();
            String formatString = String.format("%%0%dd", 7);
            String formattedString = String.format(formatString, n);
            this.ingresofactura.setText(cBoca + "-" + formattedString);
            this.factura.setText(cBoca + "-" + formattedString);
        }

        if (Integer.valueOf(comprobante.getText()) == 3) {
            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            try {
                suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            String cBoca = suc.getExpedicion_nota();
            Double nFactura = suc.getNotacredito();
            int n = (int) nFactura.doubleValue();
            String formatString = String.format("%%0%dd", 7);
            String formattedString = String.format(formatString, n);
            this.factura.setText(cBoca + "-" + formattedString);
        }

        this.preventas.setVisible(false);
        this.FiltrarPreventa.setText("");
        /*CargaDetallePreventas detpreventa = new CargaDetallePreventas();
        Thread hilodetalle = new Thread(detpreventa);
        hilodetalle.start();*/
        this.CargaPreventa();
        codigoproducto.requestFocus();
        this.SalirPreventa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarPreventaActionPerformed

    private void AceptarSupervisor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSupervisor1ActionPerformed
        usuarioDAO usuDAO = new usuarioDAO();
        usuario usu = new usuario();
        try {
            usu = usuDAO.buscarSupervisor(pasesupervisor1.getText());
            if (usu.getEmployee_id() > 0) {
                System.out.println("ENTRE A USUARIOS");
                preventaDAO DAO = new preventaDAO();
                preventa pre = new preventa();
                pre.setNumero(Double.valueOf(nropreventa.getText()));
                pre.setFirma(usu.getLast_name());
                try {
                    DAO.AutorizarDescuento(pre);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                JOptionPane.showMessageDialog(null, "Descuento Autorizado");
                this.SalirSupervisor1.doClick();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario NO AUTORIZADO, Verifique");
                pasesupervisor1.requestFocus();
            }

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSupervisor1ActionPerformed

    private void SalirSupervisor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSupervisor1ActionPerformed
        SupervisorCajaB.setVisible(false);
        SupervisorCajaB.setModal(false);
        codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSupervisor1ActionPerformed

    private void pasesupervisor1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pasesupervisor1FocusGained
        pasesupervisor1.setText("");
        pasesupervisor1.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_pasesupervisor1FocusGained

    private void pasesupervisor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasesupervisor1ActionPerformed
        this.AceptarSupervisor1.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_pasesupervisor1ActionPerformed

    private void pasesupervisor1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pasesupervisor1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasesupervisor1KeyPressed

    private void CancelarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarVentaActionPerformed
        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        this.sumatoria();
        this.limpiarobjetos();
        this.limpiarproductos();
        // TODO add your handling code here:
    }//GEN-LAST:event_CancelarVentaActionPerformed

    private void tabladetalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabladetalleKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            BorrarItemVenta.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tabladetalleKeyPressed

    private void cobroefectivoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cobroefectivoFocusGained
        this.cobroefectivo.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoFocusGained

    private void cobroefectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cobroefectivoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.AceptarCobro.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoKeyPressed

    private void cobroefectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobroefectivoActionPerformed
        String cTotalAPagar = totalbrutoapagar.getText();
        String cSuPago = cobroefectivo.getText();

        cTotalAPagar = cTotalAPagar.replace(".", "");
        cSuPago = cSuPago.replace(".", "");

        if (Double.valueOf(cSuPago) > Double.valueOf(cTotalAPagar)) {
            this.sucambio.setText(formatea.format(Double.valueOf(cSuPago) - Double.valueOf(cTotalAPagar)));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_cobroefectivoActionPerformed

    private void reimpresionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reimpresionActionPerformed
        Object[] opciones = {"  Si   ", "   No   "};
        con = new Conexion();
        stm = con.conectar();
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = configDAO.consultar();
        String cNombreFactura = config.getNombrefactura();

        cajaDAO cajDAO = new cajaDAO();
        caja caj = new caja();
        try {
            caj = cajDAO.buscarId(Integer.valueOf(this.caja.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0)//si existen impresoras
        {
            //se elige la impresora
            PrintService impresora = printer.buscar(caj.getImpresoracaja());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport
                    String num = totalneto.getText();
                    num = num.replace(".", "");
                    num = num.replace(",", ".");
                    numero_a_letras numero = new numero_a_letras();

                    parameters.put("cRamo", config.getRamo());
                    parameters.put("cResponsable", config.getResponsable());
                    parameters.put("cRuc", config.getRuc());
                    parameters.put("cTelefono", config.getTelefono());
                    parameters.put("cDireccion", config.getDireccion());
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                    parameters.put("cNombreEmpresa", config.getEmpresa());
                    parameters.put("cReferencia", this.referencia.getText());

                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    URL url = getClass().getClassLoader().getResource("Reports/" + caj.getNombrefactura().trim());

                    jasperReport = (JasperReport) JRLoader.loadObject(url);
                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stm.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_reimpresionActionPerformed

    private void tablapreventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablapreventaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarPreventa.doClick();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            int nFila = tablapreventa.getSelectedRow();
            nropreventa.setText(tablapreventa.getValueAt(nFila, 0).toString());
            SupervisorCajaB.setSize(415, 210);
            SupervisorCajaB.setLocationRelativeTo(null);
            SupervisorCajaB.setModal(true);
            SupervisorCajaB.setVisible(true);
        }

        if (evt.getKeyCode() == KeyEvent.VK_F3) {

            con = new Conexion();
            stm = con.conectar();

            try {
                Map parameters = new HashMap();
                int nFila = tablapreventa.getSelectedRow();
                if (nFila < 0) {
                    JOptionPane.showMessageDialog(null,
                            "Debe seleccionar una fila de la tabla");
                    return;
                }
                String nNumero = tablapreventa.getValueAt(nFila, 0).toString();

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumero", nNumero);
                parameters.put("cRuc", Config.cRucEmpresa);
                parameters.put("cTelefono", Config.cTelefono);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/preventa.jasper");
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                System.out.println(e.getLocalizedMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 1);
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablapreventaKeyPressed

    private void tablapreventaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablapreventaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapreventaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new LeerPuertoSerial(1).start();
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void anchoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_anchoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.espesor.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_anchoKeyPressed

    private void espesorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_espesorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.longitud.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.ancho.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_espesorKeyPressed

    private void longitudKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_longitudKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.piezas.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.espesor.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_longitudKeyPressed

    private void piezasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_piezasKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.AceptarMedida.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.longitud.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_piezasKeyPressed

    private void piezasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piezasActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_piezasActionPerformed

    private void AceptarMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarMedidaActionPerformed
        this.cantidad.setText(total.getText());
        this.medida_madera.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMedidaActionPerformed

    private void SalirMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMedidaActionPerformed
        this.medida_madera.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMedidaActionPerformed

    private void piezasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_piezasFocusLost
        String cAncho = ancho.getText();
        cAncho = cAncho.replace(".", "").replace(",", ".");
        String cEspesor = espesor.getText();
        cEspesor = cEspesor.replace(".", "").replace(",", ".");
        String cLongitud = longitud.getText();
        cLongitud = cLongitud.replace(".", "").replace(",", ".");
        double validarsuma = Double.valueOf(cAncho) + Double.valueOf(cEspesor) + Double.valueOf(cLongitud);
        if (validarsuma <= 0) {
            JOptionPane.showMessageDialog(null, "No ingresó médida alguna");
            this.ancho.requestFocus();
            return;
        }

        int conteomedida = 0;
        if (Double.valueOf(cAncho) > 0) {
            conteomedida = conteomedida + 1;
        }
        if (Double.valueOf(cEspesor) > 0) {
            conteomedida = conteomedida + 1;
        }
        if (Double.valueOf(cLongitud) > 0) {
            conteomedida = conteomedida + 1;
        }
        double subtotal = 0;
        if (conteomedida < 2) {
            JOptionPane.showMessageDialog(null, "Debe ingresar dos médidas minimamente");
            this.ancho.requestFocus();
            return;
        } else {
            if (Double.valueOf(cAncho) == 0) {
                subtotal = Double.valueOf(cEspesor) * Double.valueOf(cLongitud);
            } else if (Double.valueOf(cEspesor) == 0) {
                subtotal = Double.valueOf(cAncho) * Double.valueOf(cLongitud);
            } else if (Double.valueOf(cLongitud) == 0) {
                subtotal = Double.valueOf(cAncho) * Double.valueOf(cEspesor);
            } else {
                subtotal = Double.valueOf(cAncho) * Double.valueOf(cEspesor) * Double.valueOf(cLongitud);
            }
        }
        this.subtotal.setText(formatcantidad.format(subtotal));
        this.total.setText(formatcantidad.format(subtotal * Integer.valueOf(piezas.getText())));
        // TODO add your handling code here:
    }//GEN-LAST:event_piezasFocusLost

    private void conceptoingresoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_conceptoingresoFocusLost

        String letras = ConvertirMayusculas.cadena(conceptoingreso);
        conceptoingreso.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoingresoFocusLost

    private void fechacobroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechacobroFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechacobroFocusGained

    private void ConsultarEstadoCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarEstadoCtaActionPerformed
        GrillaCuenta grillacta = new GrillaCuenta();
        Thread hilocta = new Thread(grillacta);
        hilocta.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_ConsultarEstadoCtaActionPerformed

    private void codclieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codclieActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.codclie.getText()));
            if (cl.getCodigo() == 0) {
                this.BuscarCliente.doClick();
            } else {
                this.nombreclientecobro.setText(cl.getNombre());
                this.ruccliecobro.setText(cl.getRuc());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_codclieActionPerformed

    private void SalirCobroPVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCobroPVActionPerformed
        cobranzas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCobroPVActionPerformed

    private void cobranzasWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_cobranzasWindowGainedFocus
        this.codclie.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_cobranzasWindowGainedFocus

    public void sumarsaldos() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double sumsaldo = 0.00;
        String csusaldo = "";
        int totalRow = modelodetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CUOTAS COBRADAS
            csusaldo = String.valueOf(modelodetalle.getValueAt(i, 6));
            csusaldo = csusaldo.replace(".", "").replace(",", ".");
            sumsaldo += Double.parseDouble(String.valueOf(csusaldo));
        }
        this.saldocliente.setText(formatea.format(sumsaldo));
        //formato.format(sumatoria1);
    }

    public void sumarcobros() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double sumpago = 0.00;
        String csupago = "";
        int totalRow = modelodetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CUOTAS COBRADAS
            csupago = String.valueOf(modelodetalle.getValueAt(i, 7));
            csupago = csupago.replace(".", "").replace(",", ".");
            sumpago += Double.parseDouble(String.valueOf(csupago));
        }
        this.totalcobro.setText(formatea.format(sumpago));
        //formato.format(sumatoria1);
    }


    private void tablacobranzaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacobranzaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            //SE REEMPLAZA LA CELDA DE PAGO CON EL VALOR A PAGAR
            this.tablacobranza.setValueAt(this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 6), this.tablacobranza.getSelectedRow(), 7);
            this.sumarcobros();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            nFila = this.tablacobranza.getSelectedRow();
            this.nrodocumento.setText(this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 1).toString());
            this.calcularporcentaje.setText("0");
            this.concepto.setText(this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 4).toString());
            this.nrocuota.setText(this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 5).toString());
            this.importe_a_pagar.setText(this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 6).toString());
            this.importe_pagado.setText(this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 7).toString());
            PagoParcial.setSize(460, 370);
            PagoParcial.setLocationRelativeTo(null);
            PagoParcial.setModal(true);
            PagoParcial.setVisible(true);
        }

        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            this.tablacobranza.setValueAt("0", this.tablacobranza.getSelectedRow(), 7);
            this.sumarcobros();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F4) {
            this.GrabarCobroPV.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablacobranzaKeyPressed

    private void conceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conceptoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoActionPerformed

    private void importe_pagadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importe_pagadoFocusGained
        this.importe_a_pagar.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoFocusGained

    private void importe_pagadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importe_pagadoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarPagoParcial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importe_pagadoKeyPressed

    private void calcularporcentajeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_calcularporcentajeFocusLost
        String cPorcentaje = this.calcularporcentaje.getText();
        cPorcentaje = cPorcentaje.replace(".", "").replace(",", ".");
        if (Double.valueOf(cPorcentaje) > 0) {
            String cApagar = this.importe_a_pagar.getText();
            cApagar = cApagar.replace(".", "").replace(",", ".");
            this.importe_pagado.setText(formatea.format(Math.round(Double.valueOf(cApagar) * Double.valueOf(cPorcentaje) / 100)));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_calcularporcentajeFocusLost

    private void calcularporcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularporcentajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calcularporcentajeActionPerformed

    private void calcularporcentajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calcularporcentajeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarPagoParcial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_calcularporcentajeKeyPressed

    private void GrabarPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarPagoParcialActionPerformed
        String cImportePago = this.importe_pagado.getText();
        cImportePago = cImportePago.replace(".", "").replace(",", ".");

        if (cImportePago.isEmpty() || cImportePago.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Pago");
            this.importe_pagado.requestFocus();
            return;
        }
        this.tablacobranza.setValueAt(formatea.format(Double.valueOf(cImportePago)), nFila, 7);
        PagoParcial.dispose();
        this.sumarcobros();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarPagoParcialActionPerformed

    private void SalirPagoParcialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirPagoParcialActionPerformed
        PagoParcial.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirPagoParcialActionPerformed

    private void GrabarCobro() {
        UUID id = new UUID();
        String idunico = UUID.crearUUID();
        idunico = idunico.substring(1, 25);
        creferencia.setText(idunico.trim());
        double nsupago = 0.00;

        Date FechaProceso = ODate.de_java_a_sql(fechacobro.getDate());

        String cTotalPago = totalcobro.getText();
        if (cTotalPago.trim().length() > 0) {
            cTotalPago = cTotalPago.replace(".", "").replace(",", ".");
        } else {
            cTotalPago = "0";
        }
        String cCotizacion = "1";
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
            cl = cliDAO.buscarId(Integer.valueOf(codclie.getText()));
            sc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
            co = cobroDAO.buscarId(1);
            usu = usuDAO.buscarId(Integer.valueOf(Config.CodUsuario));
            ca = caDAO.buscarId(Integer.valueOf(caja.getText()));
            mo = monDAO.buscarId(1);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        cob.setIdpagos(creferencia.getText().trim());
        String cNroRecibo = nrorecibo.getText();
        cNroRecibo = cNroRecibo.replace("-", "");
        BigDecimal nrec = new BigDecimal(cNroRecibo);
        cob.setNumero(nrec);
        cob.setFecha(FechaProceso);
        cob.setCliente(cl);
        cob.setMoneda(mo);
        BigDecimal TotalPago = new BigDecimal(cTotalPago);
        cob.setTotalpago(TotalPago);
        cob.setObservacion("Cobranzas s/Recibo N° " + nrorecibo.getText().trim());
        cob.setValores(TotalPago);
        cob.setCobrador(co);
        cob.setSucursal(sc);
        cob.setCotizacionmoneda(cotizacionmoneda);
        cob.setCodusuario(usu);
        cob.setCaja(ca);
        cob.setTurno(1);
        cob.setTipo(0);

        String detalle = "[";
        int Items = tablacobranza.getRowCount();
        for (int i = 0; i < Items; i++) {

            String csaldo = tablacobranza.getValueAt(i, 6).toString();
            csaldo = csaldo.replace(".", "").replace(",", ".");

            String supago = tablacobranza.getValueAt(i, 7).toString();
            supago = supago.replace(".", "").replace(",", ".");
            nsupago = Double.valueOf(supago);

            String idFactura = (tablacobranza.getValueAt(i, 0).toString().trim());
            String cFactura = (tablacobranza.getValueAt(i, 1).toString().trim());
            try {
                dEmision = ODate.de_java_a_sql(formatoFecha.parse(tablacobranza.getValueAt(i, 2).toString()));
                dVence = ODate.de_java_a_sql(formatoFecha.parse(tablacobranza.getValueAt(i, 3).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
            String cNumerocuota = (tablacobranza.getValueAt(i, 9).toString());
            String cCuota = (tablacobranza.getValueAt(i, 10).toString());
            String cComprobante = (tablacobranza.getValueAt(i, 8).toString());

            //En caso que el pago sea menor al monto a pagar   
            if (Double.valueOf(supago) != 0) {
                String linea = "{iddetalle : '" + cob.getIdpagos().trim() + "',"
                        + "idfactura : '" + idFactura + "',"
                        + "nrofactura : " + cFactura + ","
                        + "emision : " + dEmision + ","
                        + "comprobante : " + cComprobante + ","
                        + "saldo: " + csaldo + ","
                        + "pago : " + supago + ","
                        + "moneda : " + 1 + ","
                        + "vence : " + dVence + ","
                        + "cuota : " + cCuota + ","
                        + "numerocuota : " + cNumerocuota + ","
                        + "fechacobro: " + FechaProceso + "},";
                detalle += linea;
            }
        }
        if (!detalle.equals("[")) {
            detalle = detalle.substring(0, detalle.length() - 1);
        }
        detalle += "]";
        System.out.println(detalle);

        //INGRESAMOS LA FORMA DE PAGO
        String detalleformapago = "[";
        String cNrocheque = "XXXX";

        String linea = "{idmovimiento : '" + creferencia.getText() + "',"
                + "forma : " + 1 + ","
                + "banco : " + 1 + ","
                + "nrocheque : '" + cNrocheque + "',"
                + "confirmacion: " + FechaProceso + ","
                + "netocobrado : " + TotalPago + "},";
        detalleformapago += linea;

        if (!detalleformapago.equals("[")) {
            detalleformapago = detalleformapago.substring(0, detalleformapago.length() - 1);
        }
        detalleformapago += "]";

        System.out.println(detalleformapago);

        try {
            cajaDAO cDAO = new cajaDAO();
            cobDAO.insertarCobro(cob, detalle);
            cDAO.ActualizarReciboCaja(cob.getCaja().getCodigo(), cob.getNumero().doubleValue() + 1);
            detalle_forma_cobroDAO detallecobroDAO = new detalle_forma_cobroDAO();
            detallecobroDAO.guardarFormaCobranza(creferencia.getText(), detalleformapago);
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
            System.out.println("POR AGREGAR--->" + ex.getLocalizedMessage());
        }

        Object[] opciones = {"  Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Imprimir el Recibo ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            this.ImprimirRecibo();
        }

        Object[] options = {"Salir", "ReImprimir"};
        ret = JOptionPane.showOptionDialog(null, "Elige imprimir o cancelar", "Aviso",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
        if (ret == 1) {
            this.ImprimirRecibo();
        }

        JOptionPane.showMessageDialog(null, "La transacción culminó con Éxito");
    }


    private void GrabarCobroPVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCobroPVActionPerformed
        if (this.codclie.getText().isEmpty() || this.codclie.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Seleccione el Cliente");
            this.codclie.requestFocus();
            return;
        }
        String ctotalcobro = this.totalcobro.getText();
        ctotalcobro = ctotalcobro.replace(".", "").replace(",", ".");
        if (ctotalcobro.isEmpty() || ctotalcobro.equals("0")) {
            JOptionPane.showMessageDialog(null, "No tiene Cuentas Seleccionadas");
            caja.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Confirmar el Cobro de las Cuotas ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            this.GrabarCobro();
        }
        this.SalirCobroPV.doClick();
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCobroPVActionPerformed

    private void FiltrarPreventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FiltrarPreventaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_FiltrarPreventaKeyReleased

    private void comboproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboproductoActionPerformed

    private void textoproductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textoproductoFocusGained
        switch (comboproducto.getSelectedIndex()) {
            case 0:
                indiceTabla = 1;
                break;//por nombre
            case 1:
                indiceTabla = 0;
                break;//por codigo
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoFocusGained

    private void textoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoActionPerformed

    private void textoproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoproductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.tablaproducto.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoKeyPressed

    private void ConsultaProducto() {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;

        int cantidadRegistro = modeloproducto.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modeloproducto.removeRow(0);
        }
        try {
            String sql = "SELECT productos.codigo,productos.nombre,productos.costo,productos.precio_maximo,productos.precioventa,"
                    + "productos.precio_minimo,stock.stock,"
                    + "productos.ivaporcentaje,productos.estado,productos.cambiarprecio "
                    + " FROM productos "
                    + " LEFT JOIN stock "
                    + " ON stock.producto=productos.codigo ";
            if (comboproducto.getSelectedIndex() == 0) {
                sql = sql + " WHERE nombre LIKE '%" + textoproducto.getText() + "%'";
            } else {
                sql = sql + " WHERE codigo LIKE '%" + textoproducto.getText() + "%'";
            }
            sql = sql + " AND productos.estado=1 "
                    + " AND stock.sucursal= " + Integer.valueOf(sucursal.getText())
                    + " ORDER BY productos.codigo ";
            System.out.println(sql);

            results = stm.executeQuery(sql);
            while (results.next()) {
                // Se crea un array que será una de las filas de la tabla.
                Object[] fila = new Object[7]; // Hay 8 columnas en la tabla
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                fila[0] = results.getString("codigo");
                fila[1] = results.getString("nombre");
                fila[2] = formatea.format(results.getDouble("precio_minimo"));
                fila[3] = formatea.format(results.getDouble("precio_maximo"));
                fila[4] = formatea.format(results.getDouble("stock"));
                fila[5] = formatea.format(results.getDouble("ivaporcentaje"));
                fila[6] = formatea.format(results.getInt("cambiarprecio"));

                // Se añade al modelo la fila completa.
                modeloproducto.addRow(fila);
            }
            tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
            tablaproducto.updateUI();
            results.close();
            stm.close();
        } catch (SQLException ex2) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos de Productos ",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex2) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Conectarse a la Base de Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        }
        FormatoTablaStock ft = new FormatoTablaStock(3, 4);
        tablaproducto.setDefaultRenderer(Object.class, ft);

        /* BProducto.setModal(true);
        BProducto.setSize(716, 575);
        BProducto.setLocationRelativeTo(null);
        BProducto.setVisible(true);
        codigoproducto.requestFocus();
        BProducto.setModal(false);*/
    }


    private void textoproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoproductoKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.tablaproducto.requestFocus();
        }

        if (textoproducto.getText().length() > 2) {
            this.ConsultaProducto();
        } else {
            int cantidadRegistro = modeloproducto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproducto.removeRow(0);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoKeyReleased

    private void textoproductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoproductoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_textoproductoKeyTyped

    private void tablaproductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaproductoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoMouseClicked

    private void tablaproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaproductoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarProducto.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            int nFila = tablaproducto.getSelectedRow();
            String cProducto = tablaproducto.getValueAt(nFila, 0).toString();
            String cNombre = tablaproducto.getValueAt(nFila, 1).toString();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproducto.requestFocus();
                return;
            }

            albumfoto_producto al = null;
            albumfoto_productoDAO imgDAO = new albumfoto_productoDAO();

            try {
                al = imgDAO.buscaId(cProducto);
                if (al.getNombre() != null) {
                    fotoProducto.setImagen(al.getImagen());
                } else {
                    fotoProducto.setImagen(imagenfondo.getImage());
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            etiquetanombreproducto.setText(cProducto + "-" + cNombre);
            panelfoto.setModal(true);
            panelfoto.setSize(580, 460);
            //Establecemos un título para el jDialog
            panelfoto.setTitle("Imágen del Producto");
            panelfoto.setLocationRelativeTo(null);
            panelfoto.setVisible(true);
            SalirFoto.requestFocus();

        }

        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            int nFila = tablaproducto.getSelectedRow();
            String cProducto = tablaproducto.getValueAt(nFila, 0).toString();
            String cNombre = tablaproducto.getValueAt(nFila, 1).toString();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablaproducto.requestFocus();
                return;
            }

            int cantidadRegistro = modelostock.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelostock.removeRow(0);
            }

            stockDAO stDAO = new stockDAO();
            try {
                for (stock st : stDAO.BuscarStockProducto(cProducto)) {
                    String Datos[] = {st.getSucursal().getNombre(), formatea.format(st.getStock())};
                    modelostock.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablastock.setRowSorter(new TableRowSorter(modelostock));
            int cantFilas = tablastock.getRowCount();

            etiquetacodigo.setText(cProducto);
            etiquetanombre.setText(cNombre);
            inventario.setModal(true);
            inventario.setSize(470, 375);
            //Establecemos un título para el jDialog
            inventario.setLocationRelativeTo(null);
            inventario.setVisible(true);
            SalirStockSucursal.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaproductoKeyPressed

    private void AceptarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarProductoActionPerformed
        int nFila = this.tablaproducto.getSelectedRow();
        this.codigoproducto.setText(this.tablaproducto.getValueAt(nFila, 0).toString());
        this.etiquetaproducto.setText(this.tablaproducto.getValueAt(nFila, 1).toString());
        //this.cambiarprecio.setText(this.tablaproducto.getValueAt(nFila, 6).toString());

        if (Double.valueOf(this.precio.getText()) == 0) {
            this.precio.setText(this.tablaproducto.getValueAt(nFila, 3).toString());
        }

        this.iva.setText(this.tablaproducto.getValueAt(nFila, 5).toString());
        this.BProducto.dispose();
        this.textoproducto.setText("");
        Config.cCodProducto = this.tablaproducto.getValueAt(nFila, 0).toString();
        this.codigoproducto.requestFocus();
        this.textoproducto.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarProductoActionPerformed

    private void SalirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirProductoActionPerformed
        this.BProducto.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirProductoActionPerformed

    private void BProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BProductoFocusGained
        this.limpiarproductos();
        textoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BProductoFocusGained

    private void BProductoWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_BProductoWindowGainedFocus
        textoproducto.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_BProductoWindowGainedFocus

    private void BusCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BusCodCliActionPerformed
        nTipoBuscarCliente = 2;
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Cliente");
        BCliente.setVisible(true);
        //                giraduria.requestFocus();
        BCliente.setModal(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_BusCodCliActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        ingresopos.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowGainedFocus

    private void SalirFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirFotoActionPerformed
        panelfoto.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirFotoActionPerformed

    private void buscarobraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarobraActionPerformed
        GrillaObra grillab = new GrillaObra();
        Thread hilobanco = new Thread(grillab);
        hilobanco.start();
        BBancos.setModal(true);
        BBancos.setSize(482, 575);
        BBancos.setLocationRelativeTo(null);
        BBancos.setVisible(true);
        BBancos.setModal(true);        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarobraActionPerformed

    private void combobancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobancoActionPerformed

    private void jTBuscarbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarbancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarbancoActionPerformed

    private void jTBuscarbancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarbancoKeyPressed
        this.jTBuscarbanco.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarbanco.getText()).toUpperCase();
                jTBuscarbanco.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combobanco.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtrobanco(indiceColumnaTabla);
            }
        });
        trsfiltrobanco = new TableRowSorter(tablabanco.getModel());
        tablabanco.setRowSorter(trsfiltrobanco);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarbancoKeyPressed

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.jTBuscarbanco.getText(), nNumeroColumna));
    }

    private void tablabancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.Aceptar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Aceptar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.obra.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombreobra.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.BBancos.setVisible(false);
        this.jTBuscarbanco.setText("");
        this.codigoproducto.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarActionPerformed

    private void SalirBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirBancoActionPerformed
        this.BBancos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirBancoActionPerformed

    private void ImprimirArqueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImprimirArqueoActionPerformed
        Object[] opciones = {"  Si   ", "   No   "};
        con = new Conexion();
        stm = con.conectar();
        double ningreso = 0;
        // Verificar que existen ventas

        // configuracion
        Date FechaCierre = ODate.de_java_a_sql(fecha_arqueo.getDate());
        int nusuario = Integer.valueOf(Config.CodUsuario);

        sucursalDAO sucDAO = new sucursalDAO();
        sucursal suc = new sucursal();
        try {
            suc = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        BuscadorImpresora printer = new BuscadorImpresora();

        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);

        if (printService.length > 0)//si existen impresoras
        {
            //se elige la impresora
            PrintService impresora = printer.buscar(suc.getImpresorarecibosuc());
            if (impresora != null) //Si se selecciono una impresora
            {
                try {
                    Map parameters = new HashMap();
                    //esto para el JasperReport
                    String num = totalneto.getText();
                    num = num.replace(".", "");
                    num = num.replace(",", ".");
                    numero_a_letras numero = new numero_a_letras();

                    parameters.put("dFecha", FechaCierre);
                    parameters.put("nusuario", nusuario);
                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    //se carga el reporte
                    //URL in = this.getClass().getResource("reporte.jasper");
                    URL url = getClass().getClassLoader().getResource("Reports/ticket_arqueo_caja_usuario.jasper");

                    jasperReport = (JasperReport) JRLoader.loadObject(url);
                    //se procesa el archivo jasper
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, stm.getConnection());
                    //se manda a la impresora
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
        this.SalirArqueo.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_ImprimirArqueoActionPerformed

    private void SalirArqueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirArqueoActionPerformed
        this.arqueo_caja.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirArqueoActionPerformed

    private void arqueo_cajaWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_arqueo_cajaWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_arqueo_cajaWindowGainedFocus

    private void AceptarSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarSupervisorActionPerformed
        usuarioDAO usuDAO = new usuarioDAO();
        usuario usu = new usuario();
        try {
            usu = usuDAO.buscarSupervisor(pasesupervisor.getText());
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (usu.getEmployee_id() > 0) {
            if (nSupervisorCaja == 1) {
                int a = this.tabladetalle.getSelectedRow();
                if (a < 0) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla");
                } else {
                    int confirmar = JOptionPane.showConfirmDialog(null,
                            "Esta seguro que desea Eliminar el registro? ");
                    if (JOptionPane.OK_OPTION == confirmar) {
                        modelo.removeRow(a);
                        this.sumatoria();
                        JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    }
                }
            } else if (nSupervisorCaja == 2) {
                this.conceptoingreso.setText("");
                this.importeingreso.setText("0");
                ingresos.setSize(405, 310);
                ingresos.setLocationRelativeTo(null);
                ingresos.setModal(true);
                ingresos.setVisible(true);
            } else if (nSupervisorCaja == 3) {
                arqueo_caja.setSize(405, 310);
                arqueo_caja.setLocationRelativeTo(null);
                arqueo_caja.setModal(true);
                arqueo_caja.setVisible(true);
            } else if (nSupervisorCaja == 4) {
                LimpiarEgreso();
                egresos.setSize(580, 390);
                egresos.setLocationRelativeTo(null);
                egresos.setModal(true);
                egresos.setVisible(true);
            }
            this.SalirSupervisor.doClick();
            nSupervisorCaja = 0;

        } else {
            JOptionPane.showMessageDialog(null, "Usuario NO AUTORIZADO, Verifique");
            pasesupervisor.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarSupervisorActionPerformed

    private void SalirSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirSupervisorActionPerformed
        SupervisorCaja.setVisible(false);
        SupervisorCaja.setModal(false);
        codigoproducto.requestFocus();
        pasesupervisor.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirSupervisorActionPerformed

    private void pasesupervisorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pasesupervisorFocusGained
        // TODO add your handling code here:
        pasesupervisor.setText("");
        pasesupervisor.selectAll();
    }//GEN-LAST:event_pasesupervisorFocusGained

    private void pasesupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasesupervisorActionPerformed
        this.AceptarSupervisor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_pasesupervisorActionPerformed

    private void pasesupervisorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pasesupervisorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pasesupervisorKeyPressed

    private void SalirStockSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirStockSucursalActionPerformed
        this.inventario.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirStockSucursalActionPerformed

    private void GrabarEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarEgresoActionPerformed

        String cImporte = this.importeegreso.getText();
        cImporte = cImporte.replace(".", "").replace(",", ".");

        String cCotizacion = this.cotizacionegreso.getText();
        cCotizacion = cCotizacion.replace(".", "").replace(",", ".");

        String cMonto = this.importemonedalocal.getText();
        cMonto = cMonto.replace(".", "").replace(",", ".");

        if (this.conceptoegreso.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Concepto");
            this.conceptoegreso.requestFocus();
            return;
        }
        if (this.cajadestino.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Destino");
            this.cajadestino.requestFocus();
            return;
        }
        if (Double.valueOf(cImporte) == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Importe");
            this.importeegreso.requestFocus();
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar la Operación ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            aperturaDAO grabar = new aperturaDAO();
            apertura ap = new apertura();

            usuarioDAO usuDAO = new usuarioDAO();
            usuario usu = null;
            cajaDAO caDAO = new cajaDAO();
            caja ca = null;
            monedaDAO moDAO = new monedaDAO();
            moneda mo = null;
            bancosDAO bcoDAO = new bancosDAO();
            banco bco = null;

            extraccionDAO exDAO = new extraccionDAO();
            extraccion ext = new extraccion();

            try {
                usu = usuDAO.buscarId(Integer.valueOf(Config.CodUsuario));
                ca = caDAO.buscarId(Integer.valueOf(caja.getText()));
                mo = moDAO.buscarId(Integer.valueOf(monedaegreso.getText()));
                bco = bcoDAO.buscarId(Integer.valueOf(cajadestino.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            ap.setTurno(Integer.valueOf(turno.getSelectedIndex() + 1));
            ap.setFecha(FechaProceso);
            ap.setCaja(ca);
            ap.setUsuario(usu);
            ap.setMoneda(mo);
            ap.setImporte(Double.valueOf(cImporte) * -1);
            ap.setCotizacion(Double.valueOf(cCotizacion));
            ap.setMonto(Double.valueOf(cMonto));
            ap.setNombre(this.conceptoegreso.getText());
            ap.setBanco(bco);
            /*------------EXTRACCIONES------------------------------*/

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            try {
                suc = sucDAO.buscarId(Integer.valueOf(sucursal.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            UUID id = new UUID();
            String idmovimiento = UUID.crearUUID();
            idmovimiento = idmovimiento.substring(1, 25);
            String cDocumento = caja.getText() + monedaegreso.getText();
            String cTipo = "C";
            String cCheque = "";
            ext.setIdmovimiento(idmovimiento);
            ext.setDocumento(cDocumento);
            ext.setFecha(FechaProceso);
            ext.setSucursal(suc);
            ext.setBanco(bco);
            ext.setTipo(cTipo);
            ext.setCotizacion(new BigDecimal(Double.valueOf(cCotizacion)));
            ext.setImporte(new BigDecimal(Double.valueOf(cImporte)));
            ext.setObservaciones(conceptoegreso.getText());
            ext.setChequenro(cCheque);
            ext.setVencimiento(FechaProceso);
            ext.setMoneda(mo);

            try {
                grabar.insertarApertura(ap);
                exDAO.insertarMovBancoFerremax(ext);
                JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
        }
        SalirEgreso.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarEgresoActionPerformed

    private void LimpiarEgreso() {
        importeegreso.setText("0");
        monedaegreso.setText("1");
        cotizacionegreso.setText("1");
        importemonedalocal.setText("0");
        conceptoegreso.setText("");
        cajadestino.setText("0");
        nombrebanco.setText("");
    }

    private void SalirEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirEgresoActionPerformed
        this.egresos.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_SalirEgresoActionPerformed

    private void importeegresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeegresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.monedaegreso.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importeegresoKeyPressed

    private void conceptoegresoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_conceptoegresoFocusLost
        String letras = ConvertirMayusculas.cadena(conceptoegreso);
        conceptoegreso.setText(letras);
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoegresoFocusLost

    private void conceptoegresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_conceptoegresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cajadestino.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importemonedalocal.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_conceptoegresoKeyPressed

    private void egresosWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_egresosWindowGainedFocus
        // TODO add your handling code here:
    }//GEN-LAST:event_egresosWindowGainedFocus

    private void importemonedalocalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importemonedalocalKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.conceptoegreso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cotizacionegreso.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importemonedalocalKeyPressed

    private void monedaegresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaegresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cotizacionegreso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importeegreso.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaegresoKeyPressed

    private void cotizacionegresoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionegresoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importemonedalocal.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.monedaegreso.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionegresoKeyPressed

    private void monedaegresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaegresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaegresoActionPerformed

    private void cajadestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajadestinoActionPerformed
        buscarbanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_cajadestinoActionPerformed

    private void cajadestinoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cajadestinoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarEgreso.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.conceptoegreso.requestFocus();
        }        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_cajadestinoKeyPressed

    private void combopagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combopagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combopagoActionPerformed

    private void jTBuscarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPagoActionPerformed

    private void jTBuscarPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarPagoKeyPressed
        this.jTBuscarPago.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarPago.getText()).toUpperCase();
                jTBuscarPago.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combopago.getSelectedIndex()) {
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
                filtropago(indiceColumnaTabla);
            }
        });
        trsfiltropago = new TableRowSorter(tablapago.getModel());
        tablapago.setRowSorter(trsfiltropago);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarPagoKeyPressed

    public void filtropago(int nNumeroColumna) {
        trsfiltropago.setRowFilter(RowFilter.regexFilter(this.jTBuscarPago.getText(), nNumeroColumna));
    }

    private void tablapagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablapagoMouseClicked
        this.AceptarCobrador.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapagoMouseClicked

    private void tablapagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablapagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCobrador.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablapagoKeyPressed

    private void AceptarCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCobradorActionPerformed
        int nFila = this.tablapago.getSelectedRow();
        this.cajadestino.setText(this.tablapago.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablapago.getValueAt(nFila, 1).toString());

        this.BPago.setVisible(false);
        this.jTBuscarPago.setText("");
        this.GrabarEgreso.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCobradorActionPerformed

    private void SalirCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCobradorActionPerformed
        this.BPago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCobradorActionPerformed

    private void buscarbancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarbancoActionPerformed
        bancosDAO coDAO = new bancosDAO();
        banco cob = null;
        try {
            cob = coDAO.buscarId(Integer.valueOf(this.cajadestino.getText()));
            if (cob.getCodigo() == 0) {
                BPago.setTitle("Buscar Banco");
                BPago.setModal(true);
                BPago.setSize(500, 575);
                BPago.setLocationRelativeTo(null);
                BPago.setVisible(true);
                BPago.setModal(false);
            } else {
                nombrebanco.setText(cob.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        GrabarEgreso.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarbancoActionPerformed

    private void cotizacionegresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cotizacionegresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionegresoActionPerformed

    private void cotizacionegresoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cotizacionegresoFocusLost
        String cImporte = importeegreso.getText();
        cImporte = cImporte.replace(".", "").replace(",", ".");

        String cImpLocal = importemonedalocal.getText();
        cImpLocal = cImpLocal.replace(".", "").replace(",", ".");

        String cCotizacion = cotizacionegreso.getText();
        cCotizacion = cCotizacion.replace(".", "").replace(",", ".");

        if (Integer.valueOf(monedaegreso.getText()) == 1) {
            importemonedalocal.setText(formatea.format(Double.valueOf(cImporte)));
        } else {
            importemonedalocal.setText(formatea.format(Double.valueOf(cImporte) * Double.valueOf(cCotizacion)));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionegresoFocusLost

    private void importeegresoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importeegresoFocusLost
        String cImporte = importeegreso.getText();
        cImporte = cImporte.replace(".", "").replace(",", ".");

        String cImpLocal = importemonedalocal.getText();
        cImpLocal = cImpLocal.replace(".", "").replace(",", ".");

        String cCotizacion = cotizacionegreso.getText();
        cCotizacion = cCotizacion.replace(".", "").replace(",", ".");

        if (Integer.valueOf(monedaegreso.getText()) == 1) {
            importemonedalocal.setText(formatea.format(Double.valueOf(cImporte)));
        } else {
            importemonedalocal.setText(formatea.format(Double.valueOf(cImporte) * Double.valueOf(cCotizacion)));
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_importeegresoFocusLost

    private void monedaorigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedaorigenActionPerformed
        this.buscarmonedaorigen.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaorigenActionPerformed

    private void monedaorigenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaorigenKeyReleased

    }//GEN-LAST:event_monedaorigenKeyReleased

    private void entregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entregaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.caja_divisa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.recibe.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_entregaKeyPressed

    private void entregaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_entregaKeyReleased

    }//GEN-LAST:event_entregaKeyReleased

    private void buscarmonedaorigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarmonedaorigenActionPerformed
        monedaDAO monDAO = new monedaDAO();
        moneda mon = null;
        try {
            mon = monDAO.buscarId(Integer.valueOf(this.monedaorigen.getText()));
            if (mon.getCodigo() == 0) {
                nGestorBusqueda = 1;
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setVisible(true);
            } else {
                nombremonedaorigen.setText(mon.getNombre().trim());
                if (operacion.getSelectedIndex() == 0) {
                    cotizacionmoneda.setText(formatea.format(mon.getCompra()));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        this.monedadestino.requestFocus();        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarmonedaorigenActionPerformed

    private void operacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_operacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cantidad.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_operacionKeyPressed

    private void GrabarCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCargaActionPerformed

        if (this.cantidadcambio.getText().isEmpty() || this.cantidadcambio.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cantidad a Cambiar");
            this.cantidadcambio.requestFocus();
            return;
        }
        if (this.monedaorigen.getText().isEmpty() || this.monedaorigen.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código de Moneda");
            this.monedaorigen.requestFocus();
            return;
        }

        if (this.monedadestino.getText().isEmpty() || this.monedadestino.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código de Moneda");
            this.monedadestino.requestFocus();
            return;
        }

        if (this.cotizacionmoneda.getText().isEmpty() || this.cotizacionmoneda.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Cotización");
            this.cotizacionmoneda.requestFocus();
            return;
        }

        if (this.recibe.getText().isEmpty() || this.recibe.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Importe de Pago");
            this.recibe.requestFocus();
            return;
        }

        if (this.caja_divisa.getText().isEmpty() || this.caja_divisa.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Caja");
            this.caja_divisa.requestFocus();
            return;
        }

        if (this.entrega.getText().isEmpty() || this.entrega.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Importe");
            this.entrega.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar la Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            divisaDAO grabar = new divisaDAO();
            divisa div = new divisa();

            clienteDAO clDAO = new clienteDAO();
            cliente cl = null;

            bancosDAO caDAO = new bancosDAO();
            banco ca = null;

            formapagoDAO frDAO = new formapagoDAO();
            formapago fr = null;

            usuarioDAO usDAO = new usuarioDAO();
            usuario usu = null;

            UUID id = new UUID();
            String cOperacion = UUID.crearUUID();
            cOperacion = cOperacion.substring(1, 25);

            try {
                cl = clDAO.buscarId(1);
                ca = caDAO.buscarId(Integer.valueOf(caja.getText()));
                usu = usDAO.buscarId(Integer.valueOf(Config.CodUsuario));
                fr = frDAO.buscarId(1);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            Date dFecha = ODate.de_java_a_sql(fecha_carga.getDate());

            div.setCreferencia(cOperacion);
            div.setFecha(dFecha);
            div.setMonedadestino(Integer.valueOf(monedadestino.getText()));
            div.setMonedaorigen(Integer.valueOf(monedaorigen.getText()));
            div.setFormapago(fr);
            div.setCaja(ca);
            div.setCliente(cl);
            div.setUsuario(usu);
            div.setOperacion(this.operacion.getSelectedIndex() + 1);
            ///////////////////////EMPEZAMOS A CAPTURAR DATOS NUMERICOS///////////////
            String cCantidad = this.cantidadcambio.getText();
            cCantidad = cCantidad.replace(".", "").replace(",", ".");
            div.setCantidad(Double.valueOf(cCantidad));

            String cCambio = this.cotizacionmoneda.getText();
            cCambio = cCambio.replace(".", "").replace(",", ".");
            div.setCambio(Double.valueOf(cCambio));

            String cRecibe = this.recibe.getText();
            cRecibe = cRecibe.replace(".", "").replace(",", ".");
            div.setRecibe(Double.valueOf(cRecibe));

            String cEntrega = this.entrega.getText();
            cEntrega = cEntrega.replace(".", "").replace(",", ".");
            div.setEntregado(Double.valueOf(cEntrega));

            String cVuelto = this.vuelto.getText();
            cVuelto = cVuelto.replace(".", "").replace(",", ".");
            div.setVuelto(Double.valueOf(cVuelto));

            try {
                grabar.InsertarDivisa(div);
                JOptionPane.showMessageDialog(null, "Se Emitió el Comprobante N°: " + div.getNumero());
                //this.imprimirBoletas(ser.getNumero().intValue(), 1);
                divisas.setVisible(false);
                this.divisas.setModal(false);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_GrabarCargaActionPerformed

    private void LimpiarDivisa() {
        this.operacion.setSelectedIndex(1);
        this.monedaorigen.setText("1");
        this.nombremonedaorigen.setText("GUARANIES");
        this.monedadestino.setText("0");
        this.nombremonedadestino.setText("");
        this.cantidadcambio.setText("0");
        this.cotizacionmoneda.setText("0");
        this.recibe.setText("0");
        this.entrega.setText("0");
        this.vuelto.setText("0");
        this.caja_divisa.setText("0");
        this.nombrebancodivisa.setText("");
        this.fecha_carga.setCalendar(c2);

    }


    private void GrabarCargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GrabarCargaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.entrega.requestFocus();
        }   // TO        //         // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCargaKeyReleased

    private void SalirCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCargaActionPerformed
        this.divisas.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCargaActionPerformed

    private void cantidadcambioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadcambioKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.monedaorigen.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.operacion.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadcambioKeyPressed

    private void cantidadcambioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadcambioKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.monedaorigen.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.operacion.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadcambioKeyReleased

    private void buscarmonedadestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarmonedadestinoActionPerformed
        monedaDAO monDAO = new monedaDAO();
        moneda mon = null;
        try {
            mon = monDAO.buscarId(Integer.valueOf(this.monedadestino.getText()));
            if (mon.getCodigo() == 0) {
                nGestorBusqueda = 2;
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setVisible(true);
            } else {
                nombremonedadestino.setText(mon.getNombre().trim());
                if (operacion.getSelectedIndex() == 1) {
                    cotizacionmoneda.setText(formatea.format(mon.getVenta()));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        this.cotizacionmoneda.requestFocus();        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarmonedadestinoActionPerformed

    private void CalcularCambio() {
        String cCantidad = this.cantidadcambio.getText();
        cCantidad = cCantidad.replace(".", "").replace(",", ".");

        String cCambio = this.cotizacionmoneda.getText();
        cCambio = cCambio.replace(".", "").replace(",", ".");
        System.out.println("Cantidad " + cCantidad);
        System.out.println("Cotización " + cCambio);

        recibe.setText(formatea.format(Math.round(Double.valueOf(cCantidad) * Double.valueOf(cCambio))));

    }


    private void combomonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combomonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combomonedaActionPerformed

    private void jTBuscarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMonedaActionPerformed

    private void jTBuscarMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarMonedaKeyPressed
        this.jTBuscarMoneda.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarMoneda.getText()).toUpperCase();
                jTBuscarMoneda.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combomoneda.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                }
                repaint();
                filtromoneda(indiceColumnaTabla);
            }
        });
        trsfiltromoneda = new TableRowSorter(tablamoneda.getModel());
        tablamoneda.setRowSorter(trsfiltromoneda);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarMonedaKeyPressed

    public void filtromoneda(int nNumeroColumna) {
        trsfiltromoneda.setRowFilter(RowFilter.regexFilter(this.jTBuscarMoneda.getText(), nNumeroColumna));
    }

    private void tablamonedaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablamonedaMouseClicked
        this.AceptarMoneda.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaMouseClicked

    private void tablamonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablamonedaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarMoneda.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablamonedaKeyPressed

    private void AceptarMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarMonedaActionPerformed
        /*      int nFila = this.tablamoneda.getSelectedRow();
        this.moneda.setText(this.tablamoneda.getValueAt(nFila, 0).toString());
        this.nombremoneda.setText(this.tablamoneda.getValueAt(nFila, 1).toString());
        this.cotizacion.setText(this.tablamoneda.getValueAt(nFila, 2).toString());

        this.BMoneda.setVisible(false);
        this.jTBuscarMoneda.setText("");
        this.cotizacion.requestFocus();*/
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarMonedaActionPerformed

    private void SalirMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirMonedaActionPerformed
        this.BMoneda.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirMonedaActionPerformed

    private void cotizacionmonedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cotizacionmonedaKeyReleased
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.recibe.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.monedadestino.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionmonedaKeyReleased

    private void monedaorigenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedaorigenKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cantidad.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_monedaorigenKeyPressed

    private void monedadestinoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_monedadestinoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.monedaorigen.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_monedadestinoKeyPressed

    private void recibeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recibeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.entrega.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.cotizacionmoneda.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_recibeKeyPressed

    private void vueltoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vueltoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.cliente.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.entrega.requestFocus();
        }   // TODO add your handling code */
        // TODO add your handling code here:
    }//GEN-LAST:event_vueltoKeyPressed

    private void monedadestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monedadestinoActionPerformed
        this.buscarmonedadestino.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_monedadestinoActionPerformed

    private void cotizacionmonedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cotizacionmonedaFocusGained
        this.CalcularCambio();
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionmonedaFocusGained

    private void cotizacionmonedaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cotizacionmonedaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionmonedaFocusLost

    private void cotizacionmonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cotizacionmonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cotizacionmonedaActionPerformed

    /**
     * 11
     *
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
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(punto_venta_multicaja_ferremax.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(punto_venta_multicaja_ferremax.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(punto_venta_multicaja_ferremax.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(punto_venta_multicaja_ferremax.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new punto_venta_multicaja_ferremax().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JButton AceptarCaja;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarCobrador;
    private javax.swing.JButton AceptarCobro;
    private javax.swing.JButton AceptarComprobante;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarMedida;
    private javax.swing.JButton AceptarMoneda;
    private javax.swing.JButton AceptarPreventa;
    private javax.swing.JButton AceptarProducto;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton AceptarSupervisor;
    private javax.swing.JButton AceptarSupervisor1;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JButton AgregarItem;
    private javax.swing.JDialog BBancos;
    private javax.swing.JDialog BCaja;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BComprobante;
    private javax.swing.JDialog BFormaPago;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BPago;
    private javax.swing.JDialog BProducto;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JDialog BVendedor;
    private javax.swing.JButton BorrarItemVenta;
    private javax.swing.JButton BusCodCli;
    private javax.swing.JButton BuscarCajaAcceso;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton BuscarClienteDivisa;
    private javax.swing.JButton BuscarSucAcceso;
    private javax.swing.JButton BuscarVendedor;
    private javax.swing.JButton BuscarVendedorAcceso;
    private javax.swing.JButton CancelarVenta;
    private javax.swing.JComboBox<String> ComboBuscarPreventa;
    private javax.swing.JButton ConsultarEstadoCta;
    private javax.swing.JTextField FiltrarPreventa;
    private javax.swing.JButton GrabarCarga;
    private javax.swing.JButton GrabarCliente;
    private javax.swing.JButton GrabarCobroPV;
    private javax.swing.JButton GrabarEgreso;
    private javax.swing.JButton GrabarIngreso;
    private javax.swing.JButton GrabarPagoParcial;
    private javax.swing.JButton GrabarVenta;
    private javax.swing.JButton ImprimirArqueo;
    private javax.swing.JDialog POS;
    private javax.swing.JDialog PagoParcial;
    private javax.swing.JDialog RegistrarCliente;
    private javax.swing.JButton SalirArqueo;
    private javax.swing.JButton SalirBanco;
    private javax.swing.JButton SalirCaja;
    private javax.swing.JButton SalirCarga;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCliente;
    private javax.swing.JButton SalirCobrador;
    private javax.swing.JButton SalirCobroPV;
    private javax.swing.JButton SalirComprobante;
    private javax.swing.JButton SalirEgreso;
    private javax.swing.JButton SalirFormaCobro;
    private javax.swing.JButton SalirFoto;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirIngreso;
    private javax.swing.JButton SalirMedida;
    private javax.swing.JButton SalirMoneda;
    private javax.swing.JButton SalirPagoParcial;
    private javax.swing.JButton SalirPreventa;
    private javax.swing.JButton SalirProducto;
    private javax.swing.JButton SalirStockSucursal;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton SalirSupervisor;
    private javax.swing.JButton SalirSupervisor1;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JDialog SupervisorCaja;
    private javax.swing.JDialog SupervisorCajaB;
    private javax.swing.JFormattedTextField ancho;
    private javax.swing.JDialog arqueo_caja;
    private javax.swing.JButton buscarCliente;
    private javax.swing.JButton buscarbanco;
    private javax.swing.JButton buscarcaja;
    private javax.swing.JButton buscarcomprobante;
    private javax.swing.JButton buscarmonedadestino;
    private javax.swing.JButton buscarmonedaorigen;
    private javax.swing.JButton buscarobra;
    private javax.swing.JButton buscarpreventa;
    private javax.swing.JButton buscarproducto;
    private javax.swing.JButton buscarsucursal;
    private javax.swing.JTextField caja;
    private javax.swing.JTextField caja_acceso;
    private javax.swing.JFormattedTextField caja_divisa;
    private javax.swing.JFormattedTextField cajadestino;
    private javax.swing.JFormattedTextField calcularporcentaje;
    private javax.swing.JFormattedTextField cantidad;
    private javax.swing.JFormattedTextField cantidadcambio;
    private javax.swing.JTextField celular;
    private javax.swing.JTextField cliente;
    private javax.swing.JDialog cobranzas;
    private javax.swing.JDialog cobrar;
    private javax.swing.JFormattedTextField cobroefectivo;
    private javax.swing.JTextField codclie;
    private javax.swing.JTextField codigo;
    private javax.swing.JTextField codigoproducto;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox combocaja;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combocomprobante;
    private javax.swing.JComboBox comboforma;
    private javax.swing.JComboBox combomoneda;
    private javax.swing.JComboBox combopago;
    private javax.swing.JComboBox comboproducto;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JComboBox combovendedor;
    private javax.swing.JTextField comprobante;
    private javax.swing.JTextField concepto;
    private javax.swing.JTextField conceptoegreso;
    private javax.swing.JTextField conceptoingreso;
    private javax.swing.JTextField control;
    private javax.swing.JFormattedTextField cotizacion_dolar;
    private javax.swing.JFormattedTextField cotizacion_peso;
    private javax.swing.JFormattedTextField cotizacion_real;
    private javax.swing.JFormattedTextField cotizacionegreso;
    private javax.swing.JFormattedTextField cotizacionmoneda;
    private javax.swing.JTextField creferencia;
    private javax.swing.JTextField direccion;
    private javax.swing.JTextField direccioncliente;
    private javax.swing.JFormattedTextField disponible;
    private javax.swing.JDialog divisas;
    private javax.swing.JDialog egresos;
    private javax.swing.JFormattedTextField entrega;
    private javax.swing.JFormattedTextField espesor;
    private javax.swing.JFormattedTextField estacompra;
    private javax.swing.JLabel etiquetacodigo;
    private javax.swing.JLabel etiquetanombre;
    private javax.swing.JLabel etiquetanombreproducto;
    private org.edisoncor.gui.label.LabelMetric etiquetaproducto;
    private javax.swing.JTextField exentas;
    private javax.swing.JFormattedTextField factura;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fecha_arqueo;
    private com.toedter.calendar.JDateChooser fecha_carga;
    private com.toedter.calendar.JDateChooser fechacobro;
    private com.toedter.calendar.JDateChooser fechaingreso;
    private JPanelWebCam.JPanelWebCam fotoProducto;
    private javax.swing.JTextField gravadas10;
    private javax.swing.JTextField gravadas5;
    private javax.swing.JCheckBox habilitado;
    private javax.swing.JLabel imagendolar;
    private javax.swing.JLabel imagenpeso;
    private javax.swing.JLabel imagenreal;
    private javax.swing.JFormattedTextField importe_a_pagar;
    private javax.swing.JFormattedTextField importe_pagado;
    private javax.swing.JFormattedTextField importeegreso;
    private javax.swing.JFormattedTextField importeingreso;
    private javax.swing.JFormattedTextField importemonedalocal;
    private javax.swing.JButton imprimirpos;
    private javax.swing.JTextField ingresofactura;
    private javax.swing.JButton ingresopos;
    private javax.swing.JDialog ingresos;
    private com.toedter.calendar.JDateChooser iniciotimbrado;
    private javax.swing.JDialog inventario;
    private javax.swing.JTextField iva;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel19asdsad1;
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
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
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
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCaja;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarComprobante;
    private javax.swing.JTextField jTBuscarForma;
    private javax.swing.JTextField jTBuscarMoneda;
    private javax.swing.JTextField jTBuscarPago;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTBuscarVendedor;
    private javax.swing.JTextField jTBuscarbanco;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    private org.jdesktop.swingx.JXPanel jXPanel2;
    private org.jdesktop.swingx.JXPanel jXPanel3;
    private org.edisoncor.gui.label.LabelTask labelTask1;
    private javax.swing.JFormattedTextField limitecredito;
    private javax.swing.JFormattedTextField longitud;
    private javax.swing.JDialog medida_madera;
    private javax.swing.JTextField monedadestino;
    private javax.swing.JFormattedTextField monedaegreso;
    private javax.swing.JTextField monedaorigen;
    private com.toedter.calendar.JDateChooser nacimiento;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombrebanco;
    private javax.swing.JTextField nombrebancodivisa;
    private javax.swing.JTextField nombrecaja;
    private javax.swing.JTextField nombrecajaacceso;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreclientecobro;
    private javax.swing.JTextField nombrecomprobante;
    private javax.swing.JTextField nombremonedadestino;
    private javax.swing.JTextField nombremonedaorigen;
    private javax.swing.JTextField nombreobra;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nombresucursalacceso;
    private javax.swing.JTextField nombrevendedor;
    private javax.swing.JTextField nombrevendedoracceso;
    private javax.swing.JTextField nrocuota;
    private javax.swing.JTextField nrodocumento;
    private javax.swing.JTextField nropreventa;
    private javax.swing.JTextField nrorecibo;
    private javax.swing.JTextField nrotimbrado;
    private javax.swing.JTextField obra;
    private javax.swing.JTextField observacion;
    private javax.swing.JComboBox<String> operacion;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.Panel panel2;
    private org.edisoncor.gui.panel.Panel panel3;
    private javax.swing.JDialog panelfoto;
    private javax.swing.JPasswordField pasesupervisor;
    private javax.swing.JPasswordField pasesupervisor1;
    private javax.swing.JFormattedTextField piezas;
    private javax.swing.JFormattedTextField precio;
    private javax.swing.JTextField preventa;
    private javax.swing.JDialog preventas;
    private javax.swing.JFormattedTextField recibe;
    private javax.swing.JTextField referencia;
    private javax.swing.JButton reimpresion;
    private javax.swing.JTextField ruc;
    private javax.swing.JTextField ruccliecobro;
    private javax.swing.JTextField rucliente;
    private javax.swing.JFormattedTextField saldocliente;
    private javax.swing.JButton salirpos;
    private javax.swing.JButton salirpuntoventa;
    private javax.swing.JFormattedTextField subtotal;
    private javax.swing.JFormattedTextField sucambio;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTextField sucursal_acceso;
    private javax.swing.JTable tablabanco;
    private javax.swing.JTable tablacaja;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacobranza;
    private javax.swing.JTable tablacomprobante;
    private javax.swing.JTable tabladetalle;
    private javax.swing.JTable tablaformapago;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablapago;
    private javax.swing.JTable tablapreventa;
    private javax.swing.JTable tablaproducto;
    private javax.swing.JTable tablastock;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTable tablavendedor;
    private javax.swing.JTextField textoproducto;
    private javax.swing.JFormattedTextField total;
    private javax.swing.JFormattedTextField total_dolar;
    private javax.swing.JFormattedTextField total_peso;
    private javax.swing.JFormattedTextField total_real;
    private javax.swing.JFormattedTextField totalbrutoapagar;
    private javax.swing.JFormattedTextField totalcobro;
    private javax.swing.JTextField totalcompleto;
    private javax.swing.JFormattedTextField totalneto;
    private javax.swing.JComboBox<String> turno;
    private com.toedter.calendar.JDateChooser vencetimbrado;
    private javax.swing.JTextField vendedor;
    private javax.swing.JTextField vendedor_acceso;
    private javax.swing.JFormattedTextField vuelto;
    // End of variables declaration//GEN-END:variables

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

    private class GrillaComprobante extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocomprobante.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocomprobante.removeRow(0);
            }
            comprobanteDAO DAOcm = new comprobanteDAO();
            try {
                for (comprobante com : DAOcm.todosxtipo(2)) {
                    String Datos[] = {String.valueOf(com.getCodigo()), com.getNombre()};
                    modelocomprobante.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacomprobante.setRowSorter(new TableRowSorter(modelocomprobante));
            int cantFilas = tablacomprobante.getRowCount();
        }
    }

    private class GrillaProductos extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloproducto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproducto.removeRow(0);
            }
            productoDAO DAOpro = new productoDAO();
            try {
                for (producto pr : DAOpro.todosmini()) {
                    if (pr.getIvaporcentaje() == null) {
                        nPorcentajeIVA = 0.00;
                    } else {
                        nPorcentajeIVA = pr.getIvaporcentaje().doubleValue();
                    }
                    String Datos[] = {String.valueOf(pr.getCodigo()), pr.getNombre(), formatea.format(pr.getPrecio_maximo()), formatea.format(pr.getPrecio_minimo()), formatea.format(pr.getStock()), formatea.format(nPorcentajeIVA)};
                    modeloproducto.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
            int cantFilas = tablaproducto.getRowCount();

            BProducto.setModal(true);
            BProducto.setSize(500, 575);
            BProducto.setLocationRelativeTo(null);
            BProducto.setVisible(true);
            codigoproducto.requestFocus();
            BProducto.setModal(false);

        }
    }

    private class GrillaVendedor extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelovendedor.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelovendedor.removeRow(0);
            }
            vendedorDAO DAOve = new vendedorDAO();
            try {
                for (vendedor ve : DAOve.todosActivos()) {
                    String Datos[] = {String.valueOf(ve.getCodigo()), ve.getNombre()};
                    modelovendedor.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablavendedor.setRowSorter(new TableRowSorter(modelovendedor));
            int cantFilas = tablavendedor.getRowCount();
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
                    String Datos[] = {String.valueOf(suc.getCodigo()), suc.getNombre(), formatosinpunto.format(suc.getFactura()), suc.getExpedicion().toString(), suc.getNrotimbrado(), formatoFecha.format(suc.getVencetimbrado())};
                    modelosucursal.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            tablasucursal.setRowSorter(new TableRowSorter(modelosucursal));
            int cantFilas = tablasucursal.getRowCount();
        }
    }

    private class CargarCotizacion extends Thread {

        public void run() {
            monedaDAO mnDAO = new monedaDAO();
            try {
                for (moneda mn : mnDAO.todos()) {
                    switch (mn.getCodigo()) {
                        case 2:
                            cotizacion_dolar.setText(formatea.format(mn.getVenta()));
                            cCotizacionDolar = String.valueOf(mn.getVenta());
                            break;
                        case 3:
                            cotizacion_real.setText(formatea.format(mn.getVenta()));
                            cCotizacionReal = String.valueOf(mn.getVenta());
                            break;
                        case 4:
                            cotizacion_peso.setText(formatea.format(mn.getVenta()));
                            cCotizacionPeso = String.valueOf(mn.getVenta());
                            break;
                        // default: es opcional
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

        }
    }

    private class GrillaPreventas extends Thread {

        public void run() {
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopreventa.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopreventa.removeRow(0);
            }
            preventaDAO DAOpre = new preventaDAO();
            try {
                for (preventa prev : DAOpre.TodosFerremax()) {
                    System.out.println(prev.getTotaldescuento());
                    String Datos[] = {String.valueOf(formatosinpunto.format(prev.getNumero())),
                        formatoFecha.format(prev.getFecha()),
                        prev.getCliente().getNombre(),
                        formatea.format(prev.getTotalneto()),
                        String.valueOf(prev.getCliente().getCodigo()),
                        prev.getCliente().getRuc(),
                        prev.getCliente().getDireccion(),
                        String.valueOf(prev.getVendedor().getCodigo()),
                        prev.getVendedor().getNombre(),
                        String.valueOf(prev.getComprobante().getCodigo()),
                        prev.getComprobante().getNombre(),
                        prev.getObservacion(),
                        formatosinpunto.format(prev.getCuotas()),
                        prev.getNombreobra(), formatea.format(prev.getTotaldescuento()),
                        prev.getFirma(),
                        formatosinpunto.format(prev.getCaja())};
                    modelopreventa.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablapreventa.setRowSorter(new TableRowSorter(modelopreventa));
            int cantFilas = tablapreventa.getRowCount();
            FormatoTabla ft = new FormatoTabla(15, 14);
            tablapreventa.setDefaultRenderer(Object.class, ft);
        }
    }

    private class FacturaPOS extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreFactura = config.getNombrefactura();

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = totalneto.getText();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", referencia.getText());
                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreFactura.trim());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                if (config.getAntesimprimir() == 0) {
                    //Enviar a Vista Previa
                    JasperViewer ventana = new JasperViewer(masterPrint, false);
                    ventana.setTitle("Vista Previa");
                    ventana.setVisible(true);
                } else {
                    //Enviar a Impresora 
                    JasperPrintManager.printReport(masterPrint, false);
                    stm.close();
                }
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    public class LeerPuertoSerial extends Thread {

        private int tipo;

        public LeerPuertoSerial(int ntipo) {
            tipo = ntipo;
        }

        public void run() {
            //INSTANCIAMOS EL DAO Y EL MODELO CORRESPONDIENTE A LA CONFIGURACION
/*            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            //ASIGNAMOS LOS VALORES CORRESPONDIENTES
            String cPuerto = config.getPuerto().trim();
            int nVelocidad = config.getVelocidad();
            int nParidad = config.getParidad();
            int nDataBit = config.getDatabit();
            int nBitParada = config.getStopbit();*/

            String cPuerto = "COM1";
            int nVelocidad = 9600;
            int nParidad = 1;
            int nDataBit = 8;
            int nBitParada = 1;

            jssc.SerialPort serialPort = new jssc.SerialPort(cPuerto);
            try {
                serialPort.openPort();//Open serial port
                //CARGAMOS LOS PARAMETROS ANTES CAPTURADOS
                serialPort.setParams(nVelocidad, nParidad, nDataBit, nBitParada);//Set params.
                byte[] buffer = serialPort.readBytes(10);//Read 10 bytes from serial port
                String receivedData = null;
                if (buffer != null) {
                    receivedData = serialPort.readString(15);
                    System.out.println("READ STRING   " + receivedData);
                }
                serialPort.closePort();
                receivedData = receivedData.replace("w", "").replace("kg", "").replace("g", "").replace("n", "");
                System.out.println("COMO ENTRA   " + receivedData);
                //    receivedData = receivedData.replace(".", ",");
                System.out.println("COMO SALE   " + receivedData);
                cantidad.setText("");
                if (tipo == 1) {
                    cantidad.setText(formatcantidad.format(Double.valueOf(receivedData)));
                }
            } catch (SerialPortException ex) {
                Exceptions.printStackTrace(ex);
            }

        }
    }

    private class GrillaCuenta extends Thread {

        public void run() {
            double nPago = 0.00;
            String cMoneda = "1";
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            Date dFecha = ODate.de_java_a_sql(fecha.getDate());

            int cantidadRegistro = modelodetalle.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodetalle.removeRow(0);
            }

            cuenta_clienteDAO COBCAJA = new cuenta_clienteDAO();
            try {
                for (cuenta_clientes co : COBCAJA.MostrarxClienteMonedaRuc(Integer.valueOf(codclie.getText()), 1, ruccliecobro.getText())) {
                    String Datos[] = {co.getIddocumento(), co.getDocumento(), formatoFecha.format(co.getFecha()), formatoFecha.format(co.getVencimiento()), co.getComprobante().getNombre(), String.valueOf(co.getCuota()) + "/" + String.valueOf(co.getNumerocuota()), formatea.format(co.getSaldo()), formatea.format(nPago), String.valueOf(co.getComprobante().getCodigo()), String.valueOf(co.getNumerocuota()), String.valueOf(co.getCuota()), cMoneda};
                    modelodetalle.addRow(Datos);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
            tablacobranza.setRowSorter(new TableRowSorter(modelodetalle));
            int cantFilas = tablacobranza.getRowCount();
            sumarsaldos();
        }
    }

    private class GrillaProd extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            String sql = "SELECT productos.codigo,productos.nombre,productos.costo,productos.precio_maximo,productos.precioventa,"
                    + "productos.precio_minimo,productos.stock,"
                    + "productos.ivaporcentaje,productos.estado,productos.cambiarprecio "
                    + " FROM productos "
                    + " WHERE productos.estado=1 "
                    + " ORDER BY productos.codigo ";

            int cantidadRegistro = modeloproducto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproducto.removeRow(0);
            }

            try {
                results = stm.executeQuery(sql);
                while (results.next()) {
                    // Se crea un array que será una de las filas de la tabla.
                    Object[] fila = new Object[7]; // Hay 8 columnas en la tabla
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    fila[0] = results.getString("codigo");
                    fila[1] = results.getString("nombre");
                    fila[2] = formatea.format(results.getDouble("precio_minimo"));
                    fila[3] = formatea.format(results.getDouble("precio_maximo"));
                    fila[4] = formatea.format(results.getDouble("stock"));
                    fila[5] = formatea.format(results.getDouble("ivaporcentaje"));
                    fila[6] = formatea.format(results.getInt("cambiarprecio"));

                    // Se añade al modelo la fila completa.
                    modeloproducto.addRow(fila);
                }
                tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
                tablaproducto.updateUI();
                stm.close();
            } catch (SQLException ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Ingresar a los Datos de Proveedores",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Conectarse a la Base de Datos",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            }
            FormatoTablaStock ft = new FormatoTablaStock(3, 4);
            tablaproducto.setDefaultRenderer(Object.class, ft);

            BProducto.setModal(true);
            BProducto.setSize(716, 575);
            BProducto.setLocationRelativeTo(null);
            BProducto.setVisible(true);
            codigoproducto.requestFocus();
            BProducto.setModal(false);
        }
    }

    private class GrillaObra extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            obraDAO obDAO = new obraDAO();
            try {
                for (obra ba : obDAO.todosxCliente(Integer.valueOf(cliente.getText()))) {
                    String Datos[] = {String.valueOf(ba.getCodigo()), ba.getNombre()};
                    modelobanco.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablabanco.setRowSorter(new TableRowSorter(modelobanco));
            int cantFilas = tablabanco.getRowCount();
        }
    }

    private class GrillaProdxLetra extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            ResultSet results = null;
            String sql = "SELECT productos.codigo,productos.nombre,productos.costo,productos.precio_maximo,productos.precioventa,"
                    + "productos.precio_minimo,stock.stock,"
                    + "productos.ivaporcentaje,productos.estado,productos.cambiarprecio "
                    + " FROM productos "
                    + " LEFT JOIN stock "
                    + " ON stock.producto=productos.codigo "
                    + " WHERE nombre LIKE '%BOLSA%'"
                    + " AND productos.estado=1 "
                    + " AND stock.sucursal= " + Integer.valueOf(sucursal.getText())
                    + " ORDER BY productos.codigo ";

            int cantidadRegistro = modeloproducto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloproducto.removeRow(0);
            }
            try {
                results = stm.executeQuery(sql);
                while (results.next()) {
                    // Se crea un array que será una de las filas de la tabla.
                    Object[] fila = new Object[7]; // Hay 8 columnas en la tabla
                    // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                    fila[0] = results.getString("codigo");
                    fila[1] = results.getString("nombre");
                    fila[2] = formatea.format(results.getDouble("precio_minimo"));
                    fila[3] = formatea.format(results.getDouble("precio_maximo"));
                    fila[4] = formatea.format(results.getDouble("stock"));
                    fila[5] = formatea.format(results.getDouble("ivaporcentaje"));
                    fila[6] = formatea.format(results.getInt("cambiarprecio"));

                    // Se añade al modelo la fila completa.
                    modeloproducto.addRow(fila);
                }
                tablaproducto.setRowSorter(new TableRowSorter(modeloproducto));
                tablaproducto.updateUI();
                stm.close();
            } catch (SQLException ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Ingresar a los Datos de Proveedores",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex2) {
                JOptionPane.showMessageDialog(new JFrame(),
                        "El Sistema No Puede Conectarse a la Base de Datos",
                        "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            }
            FormatoTablaStock ft = new FormatoTablaStock(3, 4);
            tablaproducto.setDefaultRenderer(Object.class, ft);

            BProducto.setModal(true);
            BProducto.setSize(716, 575);
            BProducto.setLocationRelativeTo(null);
            BProducto.setVisible(true);
            codigoproducto.requestFocus();
            BProducto.setModal(false);
        }
    }

    private class GrillaFormaPago extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelopago.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelopago.removeRow(0);
            }
            bancosDAO bancoDAO = new bancosDAO();
            try {
                for (banco ba : bancoDAO.todos()) {
                    String Datos[] = {String.valueOf(ba.getCodigo()), ba.getNombre()};
                    modelopago.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablapago.setRowSorter(new TableRowSorter(modelopago));
            int cantFilas = tablapago.getRowCount();
        }
    }

}

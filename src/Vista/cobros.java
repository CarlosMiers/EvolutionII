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
import DAO.GenerarAsientosDAO;
import DAO.bancoplazaDAO;
import DAO.cabecera_asientoDAO;
import DAO.cajaDAO;
import DAO.clienteDAO;
import DAO.cobradorDAO;
import DAO.cobranzaDAO;
import DAO.config_contableDAO;
import DAO.configuracionDAO;
import DAO.cuenta_clienteDAO;
import DAO.detalle_forma_cobroDAO;
import DAO.detalle_ventaDAO;
import DAO.detallecobranzaDAO;
import DAO.formapagoDAO;
import Modelo.cuenta_clientes;
import DAO.monedaDAO;
import DAO.retenciones_ventasDAO;
import DAO.sucursalDAO;
import DAO.usuarioDAO;
import Modelo.Tablas;
import Modelo.bancoplaza;
import Modelo.caja;
import Modelo.cliente;
import Modelo.cobrador;
import Modelo.cobranza;
import Modelo.config_contable;
import Modelo.configuracion;
import Modelo.detalle_forma_cobro;
import Modelo.detalle_venta;
import Modelo.detallecobranza;
import Modelo.formapago;
import Modelo.moneda;
import Modelo.retenciones_ventas;
import Modelo.sucursal;
import Modelo.usuario;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
public class cobros extends javax.swing.JFrame {

    String supago = "";
    Date dConfirma;
    Conexion con = null;
    Statement stm, stm2 = null;
    BDConexion BD = new BDConexion();
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelo2 = new Tablas();
    Tablas modelodetalle = new Tablas();
    Tablas modelostock = new Tablas();
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
    Tablas modelobanco = new Tablas();
    Tablas modelosucursal = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelomoneda = new Tablas();
    Tablas modelocaja = new Tablas();
    Tablas modeloformapago = new Tablas();
    Tablas modelopagos = new Tablas();
    Tablas modelocobrador = new Tablas();
    Date dEmision;
    Date dVence;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c2 = new GregorianCalendar();
    private TableRowSorter trsfiltro, trsfiltrosuc, trsfiltrocli, trsfiltromoneda, trsfiltrocaja, trsfiltrocobrador, trsfiltrobanco, trsfiltroformapago;
    int counter = 0;
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
    ImageIcon iconograbar = new ImageIcon("src/Iconos/grabar.png");

    public cobros() {
        initComponents();

        fecha.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                counter++;
                if (evt.getPropertyName().equals("date")) {
                    caja.requestFocus();
                }
            }
        });

        this.grabarPago.setIcon(icononuevo);
        this.Grabar.setIcon(iconograbar);
        this.Salir.setIcon(iconosalir);
        this.salirPago.setIcon(iconosalir);

        this.NewItem.setIcon(icononuevo);
        this.Upditem.setIcon(iconoeditar);
        this.DelItem.setIcon(iconoborrar);

        this.Agregar.setIcon(icononuevo);
        this.Modificar.setIcon(iconoeditar);
        this.Anular.setIcon(iconoborrar);
        this.Listar.setIcon(iconoprint);
        this.Salir.setIcon(iconosalir);
        this.SalirCobranza.setIcon(iconosalir);
        this.refrescar.setIcon(icorefresh);
        this.buscarCaja.setIcon(iconobuscar);
        this.buscarSucursal.setIcon(iconobuscar);
        this.buscarCobrador.setIcon(iconobuscar);
        this.buscarMoneda.setIcon(iconobuscar);
        this.buscarSucursal.setIcon(iconobuscar);
        this.BuscarCliente.setIcon(iconobuscar);
        this.BuscarFormapago.setIcon(iconobuscar);
        this.BuscarBanco.setIcon(iconobuscar);

        this.jTable1.setShowGrid(false);
        this.jTable1.setOpaque(true);
        this.jTable1.setBackground(new Color(102, 204, 255));
        this.jTable1.setForeground(Color.BLACK);

        this.setLocationRelativeTo(null); //Centramos el formulario
        this.idcontrol.setVisible(false);
        this.idControl.setVisible(false);
        this.creferenciaret.setVisible(false);

        this.cModo.setVisible(false);
        this.tipoOp.setVisible(false);
        this.cargarTitulo();
        this.TitMercaderia();
        this.CargarTituloDetalle();
        this.CargarTituloFormaPago();
        this.TituloFormaPago();
        this.TituloBanco();
        this.Inicializar();
        this.TitClie();
        this.TitCaja();
        this.TitCobrador();
        this.TitMoneda();
        this.TitSuc();

        GrillaCliente grillacl = new GrillaCliente();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();

        GrillaSucursal grillasu = new GrillaSucursal();
        Thread hilosuc = new Thread(grillasu);
        hilosuc.start();

        this.refrescar.doClick();

    }

    Control hand = new Control();

    private void GrabarCobro() {
        if (tipoOp.getText().equals("0")) {
            UUID id = new UUID();
            String idunico = UUID.crearUUID();
            idunico = idunico.substring(1, 25);
            idControl.setText(idunico.trim());
        } else {
            idControl.setText(idcontrol.getText());
        }
        double nsupago = 0.00;

        Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());

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
        cob.setNumero(nrec);
        cob.setFecha(FechaProceso);
        cob.setCliente(cl);
        cob.setMoneda(mo);
        BigDecimal TotalPago = new BigDecimal(cTotalPago);
        cob.setTotalpago(TotalPago);
        cob.setObservacion(observaciones.getText());
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
                        + "moneda : " + moneda.getText() + ","
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
        int item = tablapagos.getRowCount();
        item -= 1;
        for (int i = 0; i <= item; i++) {
            supago = tablapagos.getValueAt(i, 6).toString();
            supago = supago.replace(".", "").replace(",", ".");
            String cNrocheque = tablapagos.getValueAt(i, 4).toString();
            if (cNrocheque.isEmpty()) {
                cNrocheque = "XXXX";
            }
            try {
                dConfirma = ODate.de_java_a_sql(formatoFecha.parse(tablapagos.getValueAt(i, 5).toString()));
            } catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }

            String linea = "{idmovimiento : '" + idControl.getText() + "',"
                    + "forma : " + tablapagos.getValueAt(i, 0).toString() + ","
                    + "banco : " + tablapagos.getValueAt(i, 2).toString() + ","
                    + "nrocheque : '" + cNrocheque + "',"
                    + "confirmacion: " + dConfirma + ","
                    + "netocobrado : " + supago + "},";
            detalleformapago += linea;
        }
        if (!detalleformapago.equals("[")) {
            detalleformapago = detalleformapago.substring(0, detalleformapago.length() - 1);
        }
        detalleformapago += "]";

        System.out.println(detalleformapago);

        if (tipoOp.getText().equals("0")) {
            cajaDAO cDAO = new cajaDAO();
            try {
                cobDAO.insertarCobro(cob, detalle);
                cDAO.ActualizarReciboCaja(cob.getCaja().getCodigo(), cob.getNumero().doubleValue() + 1);
                detalle_forma_cobroDAO detallecobroDAO = new detalle_forma_cobroDAO();
                detallecobroDAO.guardarFormaCobranza(idControl.getText(), detalleformapago);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println("POR AGREGAR--->" + ex.getLocalizedMessage());
                sucursal.requestFocus();
                return;
            }
        } else {
            try {
                cobDAO.ActualizarCobroStandard(cob, detalle);
                detalle_forma_cobroDAO detallecobroDAO = new detalle_forma_cobroDAO();
                detallecobroDAO.guardarFormaCobranza(idControl.getText(), detalleformapago);
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
                System.out.println("POR MODIFICAR--->" + ex.getLocalizedMessage());
                sucursal.requestFocus();
                return;
            }
        }
        config_contableDAO contableDAO = new config_contableDAO();
        config_contable contable = null;
        contable = contableDAO.consultar();
        GenerarAsientosDAO genDAO = new GenerarAsientosDAO();
        if (contable.getCobranzas() == 1) {
            genDAO.generarCobranzasItem(idControl.getText());
        }
        this.refrescar.doClick();

        JOptionPane.showMessageDialog(null, "Datos Agregados Correctamente");

    }

    private void TitMercaderia() {
        modelostock.addColumn("Producto");
        modelostock.addColumn("Cantidad");
        modelostock.addColumn("Precio");

        int[] anchos = {200, 100, 100};
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
        this.tablastock.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
    }

    public void sumarforma() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double supago = 0.00;
        double sumpago = 0.00;

        String csupago = "";
        String ctotalcobro = "";

        int totalRow = tablapagos.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL A PAGAR
            csupago = String.valueOf(tablapagos.getValueAt(i, 6));
            csupago = csupago.replace(".", "").replace(",", ".");
            supago = Double.valueOf(csupago);
            sumpago += supago;
        }
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalvalores.setText(formatea.format(sumpago));
        if (sumpago > 0) {
            this.Upditem.setEnabled(true);
            this.DelItem.setEnabled(true);
        } else {
            this.Upditem.setEnabled(false);
            this.DelItem.setEnabled(false);
        }
        //formato.format(sumatoria1);
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
        modeloformapago.addColumn("Código");
        modeloformapago.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloformapago.getColumnCount(); i++) {
            tablaformapago.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaformapago.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaformapago.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaformapago.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaformapago.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void CargarTituloFormaPago() {
        modelopagos.addColumn("Cód.");
        modelopagos.addColumn("Forma Pago");
        modelopagos.addColumn("Banco");
        modelopagos.addColumn("Denominación");
        modelopagos.addColumn("N° Cheque");
        modelopagos.addColumn("Confirmación");
        modelopagos.addColumn("Importe");
        int[] anchos = {80, 100, 80, 100, 100, 100, 100};
        for (int i = 0; i < modelopagos.getColumnCount(); i++) {
            this.tablapagos.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablapagos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablapagos.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        this.tablapagos.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        this.tablapagos.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
        this.tablapagos.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
    }

    public void limpiarformapago() {
        forma.setText("0");
        nombreformapago.setText("");
        banco.setText("0");
        nombrebanco.setText("");
        nrocheque.setText("");
        importecheque.setText("0");
        confirmacion.setCalendar(c2);
        totalvalores.setText("0");
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
    }

    public void sumarcobros() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 

        double supago = 0.00;
        double sumpago = 0.00;
        String csupago = "";
        int totalRow = modelodetalle.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL DE CUOTAS COBRADAS
            csupago = String.valueOf(modelodetalle.getValueAt(i, 7));
            csupago = csupago.replace(".", "").replace(",", ".");
            supago = Double.parseDouble(String.valueOf(csupago));
            sumpago += supago;
        }
        this.totalcobro.setText(formatea.format(sumpago));
        //formato.format(sumatoria1);
    }

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");
        modelocliente.addColumn("Dirección");
        modelocliente.addColumn("RUC");
        modelocliente.addColumn("Giraduría");

        int[] anchos = {90, 150, 100, 100, 100};
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
        modelocaja.addColumn("Nro. Recibo");

        int[] anchos = {90, 100, 90};
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

        this.tablacaja.getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablacaja.getColumnModel().getColumn(2).setMinWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        this.tablacaja.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
    }

    public void limpiar() {
        configuracionDAO configDAO = new configuracionDAO();
        configuracion config = null;
        config = configDAO.consultar();
        this.sucursal.setText(String.valueOf(config.getSucursaldefecto().getCodigo()));
        this.nombresucursal.setText(config.getSucursaldefecto().getNombre());
        this.caja.setText(String.valueOf(config.getCajadefecto().getCodigo()));
        this.nombrecaja.setText(config.getCajadefecto().getNombre());
        this.nrorecibo.setText(formatosinpunto.format(config.getCajadefecto().getRecibo()));
        this.sucambio.setText("0");
        this.cliente.setText("0");
        this.nombrecliente.setText("Ingrese la Cuenta");
        this.observaciones.setText("");
        //ASIGNAMOS FORMATO DE FACTURA

        this.cobrador.setText("0");
        this.nombrecobrador.setText("Ingrese el Código del Cobrador");

        this.moneda.setText(String.valueOf(config.getMonedadefecto().getCodigo()));
        this.nombremoneda.setText(config.getMonedadefecto().getNombre());
        this.cotizacion.setText(formatea.format(config.getMonedadefecto().getVenta()));

        this.cliente.setText(String.valueOf(config.getClientedefecto().getCodigo()));
        this.nombrecliente.setText(config.getClientedefecto().getNombre());
        this.direccion.setText(config.getClientedefecto().getDireccion());
        this.ruc.setText("");
        this.nombregiraduria.setText("");

        this.fecha.setCalendar(c2);
//        this.editaritem.setEnabled(false);
        //       this.delitem.setEnabled(false);
        //      this.nombrebanco.setText("");

        this.totalcobro.setText("0");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detalle_cobranza = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cliente = new javax.swing.JTextField();
        nombrecliente = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        tipoOp = new javax.swing.JTextField();
        idControl = new javax.swing.JTextField();
        ruc = new javax.swing.JTextField();
        MostrarCuentas = new javax.swing.JButton();
        BuscarCliente = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cModo = new javax.swing.JTextField();
        nombregiraduria = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablacobranza = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablapagos = new javax.swing.JTable();
        NewItem = new javax.swing.JButton();
        Upditem = new javax.swing.JButton();
        DelItem = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        totalvalores = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        SalirCobranza = new javax.swing.JButton();
        Grabar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        totalcobro = new javax.swing.JFormattedTextField();
        jPanel28 = new javax.swing.JPanel();
        boxcuotas = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        observaciones = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
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
        nrodocumento = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        concepto = new javax.swing.JTextField();
        importe_a_pagar = new javax.swing.JFormattedTextField();
        importe_pagado = new javax.swing.JFormattedTextField();
        nrocuota = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        calcularporcentaje = new javax.swing.JFormattedTextField();
        jLabel35 = new javax.swing.JLabel();
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
        retenciones = new javax.swing.JDialog();
        jPanel30 = new javax.swing.JPanel();
        GrabarRetencion = new javax.swing.JButton();
        SalirRetencion = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        nroretencion = new javax.swing.JTextField();
        fecharetencion = new com.toedter.calendar.JDateChooser();
        jLabel39 = new javax.swing.JLabel();
        nrofactura = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        sucursalret = new javax.swing.JTextField();
        nombresucursalret = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        monedaret = new javax.swing.JTextField();
        nombremonedaret = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        importe_sin_iva = new javax.swing.JFormattedTextField();
        jLabel43 = new javax.swing.JLabel();
        importe_iva = new javax.swing.JFormattedTextField();
        jLabel44 = new javax.swing.JLabel();
        importe_gravado_total = new javax.swing.JFormattedTextField();
        jLabel45 = new javax.swing.JLabel();
        porcentaje_retencion = new javax.swing.JFormattedTextField();
        jLabel52 = new javax.swing.JLabel();
        valor_retencion = new javax.swing.JFormattedTextField();
        enviarcta = new javax.swing.JCheckBox();
        creferenciaret = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        clienteret = new javax.swing.JTextField();
        nombreclienteret = new javax.swing.JTextField();
        formapago = new javax.swing.JDialog();
        jPanel38 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        forma = new javax.swing.JTextField();
        BuscarFormapago = new javax.swing.JButton();
        nombreformapago = new javax.swing.JTextField();
        banco = new javax.swing.JTextField();
        BuscarBanco = new javax.swing.JButton();
        nombrebanco = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        nrocheque = new javax.swing.JTextField();
        importecheque = new javax.swing.JFormattedTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        confirmacion = new com.toedter.calendar.JDateChooser();
        jPanel39 = new javax.swing.JPanel();
        grabarPago = new javax.swing.JButton();
        salirPago = new javax.swing.JButton();
        BFormaPago = new javax.swing.JDialog();
        jPanel40 = new javax.swing.JPanel();
        comboforma = new javax.swing.JComboBox();
        jTBuscarForma = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablaformapago = new javax.swing.JTable();
        jPanel41 = new javax.swing.JPanel();
        AceptarGir1 = new javax.swing.JButton();
        SalirGir1 = new javax.swing.JButton();
        BBancos = new javax.swing.JDialog();
        jPanel42 = new javax.swing.JPanel();
        combobanco = new javax.swing.JComboBox();
        jTBuscarbanco = new javax.swing.JTextField();
        jScrollPane12 = new javax.swing.JScrollPane();
        tablabanco = new javax.swing.JTable();
        jPanel43 = new javax.swing.JPanel();
        AceptarCasa = new javax.swing.JButton();
        SalirCasa = new javax.swing.JButton();
        mercaderias = new javax.swing.JDialog();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablastock = new javax.swing.JTable();
        jPanel33 = new javax.swing.JPanel();
        SalirStock = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        etiquetacodigo = new javax.swing.JLabel();
        etiquetanombre = new javax.swing.JLabel();
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
        Modificar = new javax.swing.JButton();
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

        tipoOp.setText(" ");

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

        jLabel8.setText("RUC");

        jLabel9.setText("Dirección");

        jLabel13.setText("Cliente/Socio");

        cModo.setText(" ");

        nombregiraduria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nombregiraduria.setEnabled(false);

        jLabel16.setText("Giraduría");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
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
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(MostrarCuentas))
                            .addComponent(nombregiraduria))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tipoOp)
                            .addComponent(idControl)
                            .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
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
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoOp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cModo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
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
        caja.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cajaFocusGained(evt);
            }
        });
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
        jLabel1.setText("N° Recibo");

        nrorecibo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrorecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nrorecibo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
                        .addGap(0, 14, Short.MAX_VALUE)))
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

        tablacobranza.setModel(modelodetalle);
        tablacobranza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablacobranzaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablacobranza);

        jTabbedPane1.addTab("Seleccionar las Facturas a Cobrar", jScrollPane2);

        tablapagos.setModel(modelopagos        );
        jScrollPane10.setViewportView(tablapagos);

        NewItem.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.NewItem.text")); // NOI18N
        NewItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewItemActionPerformed(evt);
            }
        });

        Upditem.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.Upditem.text")); // NOI18N
        Upditem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Upditem.setEnabled(false);
        Upditem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpditemActionPerformed(evt);
            }
        });

        DelItem.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.DelItem.text")); // NOI18N
        DelItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DelItem.setEnabled(false);
        DelItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelItemActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel47.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel45.text")); // NOI18N

        totalvalores.setEditable(false);
        totalvalores.setBackground(new java.awt.Color(0, 153, 255));
        totalvalores.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        totalvalores.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalvalores.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalvalores.setEnabled(false);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 923, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(DelItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Upditem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NewItem, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(totalvalores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                            .addComponent(jLabel47)
                            .addGap(15, 15, 15))))
                .addGap(26, 26, 26))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(NewItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Upditem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DelItem)
                .addGap(18, 18, 18)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalvalores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Ingrese las Formas de Cobro de las Facturas", jPanel29);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

        Grabar.setText("Grabar ");
        Grabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarActionPerformed(evt);
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
                .addComponent(Grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(totalcobro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalirCobranza)
                    .addComponent(Grabar))
                .addContainerGap())
        );

        jPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        boxcuotas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        boxcuotas.setText("Cobrar todas las Cuotas");
        boxcuotas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boxcuotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxcuotasActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("[F1] Seleccionar Cuota");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("[F2] Ingresar Pago Parcial");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("[F3] Borrar Cobro");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("[F4] Ingresar Cobro");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("[F5] Generar Descuento");

        observaciones.setColumns(20);
        observaciones.setRows(5);
        jScrollPane3.setViewportView(observaciones);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Observaciones");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("[F6] Ver Detalle Factura");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel11)
                            .addComponent(jLabel18))
                        .addGap(17, 17, 17)
                        .addComponent(boxcuotas))
                    .addComponent(jLabel20)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(boxcuotas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout detalle_cobranzaLayout = new javax.swing.GroupLayout(detalle_cobranza.getContentPane());
        detalle_cobranza.getContentPane().setLayout(detalle_cobranzaLayout);
        detalle_cobranzaLayout.setHorizontalGroup(
            detalle_cobranzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        detalle_cobranzaLayout.setVerticalGroup(
            detalle_cobranzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        PagoParcial.setTitle("Pago Parcial de Cuotas");

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(102, 102, 255)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("N° Documento");

        nrodocumento.setEditable(false);
        nrodocumento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nrodocumento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Comprobante");

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

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Cuota");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Importe a Pagar");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Su Pago");

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

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Calcular %");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nrodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(importe_a_pagar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                .addComponent(importe_pagado)
                                .addComponent(nrocuota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(calcularporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(nrodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(concepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                    .addComponent(jLabel25))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calcularporcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jTBuscarCobrador.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        AceptarCobrador.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCobrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCobradorActionPerformed(evt);
            }
        });

        SalirCobrador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCobrador.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.SalirCliente.text")); // NOI18N
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

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarRetencion.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.GrabarRetencion.text")); // NOI18N
        GrabarRetencion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarRetencion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarRetencionActionPerformed(evt);
            }
        });

        SalirRetencion.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.SalirRetencion.text")); // NOI18N
        SalirRetencion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirRetencion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirRetencionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(GrabarRetencion, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(SalirRetencion, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrabarRetencion)
                    .addComponent(SalirRetencion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel37.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel33.text")); // NOI18N

        jLabel38.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel34.text")); // NOI18N

        nroretencion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nroretencion.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.nroretencion.text")); // NOI18N
        nroretencion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nroretencionKeyPressed(evt);
            }
        });

        fecharetencion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fecharetencionFocusGained(evt);
            }
        });
        fecharetencion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fecharetencionKeyPressed(evt);
            }
        });

        jLabel39.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel35.text")); // NOI18N

        nrofactura.setEditable(false);
        nrofactura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrofactura.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.sucursalret.text")); // NOI18N

        jLabel40.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel36.text")); // NOI18N

        sucursalret.setEditable(false);
        sucursalret.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sucursalret.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.sucursalret.text")); // NOI18N

        nombresucursalret.setEditable(false);
        nombresucursalret.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.sucursalret.text")); // NOI18N

        jLabel41.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel37.text")); // NOI18N

        monedaret.setEditable(false);
        monedaret.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        monedaret.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.sucursalret.text")); // NOI18N

        nombremonedaret.setEditable(false);
        nombremonedaret.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.sucursalret.text")); // NOI18N

        jLabel42.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel38.text")); // NOI18N

        importe_sin_iva.setEditable(false);
        importe_sin_iva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_sin_iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_sin_iva.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.importe_sin_iva.text")); // NOI18N

        jLabel43.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel39.text")); // NOI18N

        importe_iva.setEditable(false);
        importe_iva.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_iva.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.importe_sin_iva.text")); // NOI18N

        jLabel44.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel40.text")); // NOI18N

        importe_gravado_total.setEditable(false);
        importe_gravado_total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importe_gravado_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe_gravado_total.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.importe_sin_iva.text")); // NOI18N

        jLabel45.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel41.text")); // NOI18N

        porcentaje_retencion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        porcentaje_retencion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        porcentaje_retencion.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.importe_sin_iva.text")); // NOI18N
        porcentaje_retencion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porcentaje_retencionActionPerformed(evt);
            }
        });
        porcentaje_retencion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                porcentaje_retencionKeyPressed(evt);
            }
        });

        jLabel52.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel52.text")); // NOI18N

        valor_retencion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        valor_retencion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valor_retencion.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.importe_sin_iva.text")); // NOI18N

        enviarcta.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.enviarcta.text")); // NOI18N

        creferenciaret.setEditable(false);
        creferenciaret.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        creferenciaret.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.creferenciaret.text")); // NOI18N

        jLabel53.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel53.text")); // NOI18N

        clienteret.setEditable(false);
        clienteret.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.clienteret.text")); // NOI18N

        nombreclienteret.setEditable(false);
        nombreclienteret.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.nombreclienteret.text")); // NOI18N

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(jLabel40))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addComponent(nroretencion, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(creferenciaret, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(clienteret)
                                    .addComponent(sucursalret, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addComponent(nombresucursalret, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(nombreclienteret)))))
                        .addGap(130, 130, 130))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel42)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel53)
                                            .addComponent(jLabel41))))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(importe_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(importe_sin_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(fecharetencion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel31Layout.createSequentialGroup()
                                        .addComponent(monedaret, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(nombremonedaret, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel43)
                            .addGroup(jPanel31Layout.createSequentialGroup()
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel52))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(porcentaje_retencion, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(importe_gravado_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(valor_retencion, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addComponent(enviarcta)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nroretencion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(creferenciaret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sucursalret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombresucursalret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(clienteret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombreclienteret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monedaret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombremonedaret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel38)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel39))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fecharetencion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nrofactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importe_sin_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(importe_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(importe_gravado_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(porcentaje_retencion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(valor_retencion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enviarcta))
                .addContainerGap())
        );

        javax.swing.GroupLayout retencionesLayout = new javax.swing.GroupLayout(retenciones.getContentPane());
        retenciones.getContentPane().setLayout(retencionesLayout);
        retencionesLayout.setHorizontalGroup(
            retencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        retencionesLayout.setVerticalGroup(
            retencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, retencionesLayout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel48.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel46.text")); // NOI18N

        jLabel49.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel47.text")); // NOI18N

        forma.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        forma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formaActionPerformed(evt);
            }
        });

        BuscarFormapago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarFormapago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarFormapagoActionPerformed(evt);
            }
        });

        banco.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        banco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bancoActionPerformed(evt);
            }
        });

        BuscarBanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarBancoActionPerformed(evt);
            }
        });

        jLabel50.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel48.text")); // NOI18N

        nrocheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nrocheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nrochequeKeyPressed(evt);
            }
        });

        importecheque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importecheque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importecheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importechequeKeyPressed(evt);
            }
        });

        jLabel51.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel49.text")); // NOI18N

        jLabel54.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jLabel50.text")); // NOI18N

        confirmacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmacionKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(forma, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarFormapago, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreformapago, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel49)
                                .addComponent(jLabel50))
                            .addGroup(jPanel38Layout.createSequentialGroup()
                                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel54))
                                .addGap(3, 3, 3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nrocheque, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(importecheque))
                                .addGroup(jPanel38Layout.createSequentialGroup()
                                    .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(72, 72, 72))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreformapago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BuscarFormapago, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel48)
                        .addComponent(forma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nombrebanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BuscarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(nrocheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importecheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(confirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel39.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        grabarPago.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.grabarPago.text")); // NOI18N
        grabarPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        grabarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarPagoActionPerformed(evt);
            }
        });
        grabarPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                grabarPagoKeyPressed(evt);
            }
        });

        salirPago.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.salirPago.text")); // NOI18N
        salirPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salirPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(grabarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(salirPago, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grabarPago)
                    .addComponent(salirPago))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout formapagoLayout = new javax.swing.GroupLayout(formapago.getContentPane());
        formapago.getContentPane().setLayout(formapagoLayout);
        formapagoLayout.setHorizontalGroup(
            formapagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        formapagoLayout.setVerticalGroup(
            formapagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formapagoLayout.createSequentialGroup()
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jTBuscarForma.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        AceptarGir1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarGir1.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarGir1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarGir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarGir1ActionPerformed(evt);
            }
        });

        SalirGir1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirGir1.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.SalirCliente.text")); // NOI18N
        SalirGir1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirGir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirGir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir1)
                    .addComponent(SalirGir1))
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

        BBancos.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BBancos.setTitle("null");

        jPanel42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combobanco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combobanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combobanco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combobanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobancoActionPerformed(evt);
            }
        });

        jTBuscarbanco.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarbanco.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(combobanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarbanco, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane12.setViewportView(tablabanco);

        jPanel43.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCasa.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCasaActionPerformed(evt);
            }
        });

        SalirCasa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCasa.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCasa)
                    .addComponent(SalirCasa))
                .addContainerGap())
        );

        javax.swing.GroupLayout BBancosLayout = new javax.swing.GroupLayout(BBancos.getContentPane());
        BBancos.getContentPane().setLayout(BBancosLayout);
        BBancosLayout.setHorizontalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BBancosLayout.setVerticalGroup(
            BBancosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BBancosLayout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mercaderias.setTitle("Mercaderias Vendidas");

        jPanel32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablastock.setModel(modelostock);
        jScrollPane8.setViewportView(tablastock);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        SalirStock.setText("Salir");
        SalirStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addComponent(SalirStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SalirStock, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel34.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setText("Código");

        jLabel31.setText("Descripción");

        etiquetacodigo.setText("jLabel32");

        etiquetanombre.setText("jLabel33");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel31))
                .addGap(18, 18, 18)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetacodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetanombre, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(etiquetacodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(etiquetanombre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mercaderiasLayout = new javax.swing.GroupLayout(mercaderias.getContentPane());
        mercaderias.getContentPane().setLayout(mercaderiasLayout);
        mercaderiasLayout.setHorizontalGroup(
            mercaderiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mercaderiasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mercaderiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mercaderiasLayout.setVerticalGroup(
            mercaderiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mercaderiasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        Listar.setText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.Listar.text")); // NOI18N
        Listar.setToolTipText(org.openide.util.NbBundle.getMessage(cobros.class, "ventas.Listar.toolTipText")); // NOI18N
        Listar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListarActionPerformed(evt);
            }
        });

        Modificar.setBackground(new java.awt.Color(255, 255, 255));
        Modificar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        Modificar.setText("Editar Registro");
        Modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Modificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(Salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Listar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Anular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(idcontrol))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Agregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Modificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Anular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Listar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Salir)
                .addGap(39, 39, 39)
                .addComponent(idcontrol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setText("Cobranzas ");

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
                .addContainerGap(384, Short.MAX_VALUE))
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
        this.caja.setEnabled(true);
        this.nombrecaja.setEnabled(true);
        MostrarCuentas.setEnabled(true);

        idControl.setText("0");
        tipoOp.setText("0");
        this.limpiar();
        //  this.limpiaritems();
        //Establecemos un título para el jDialog

        int cantidadRegistro = modelodetalle.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelodetalle.removeRow(0);
        }

        for (int i = 1; i <= cantidadRegistro; i++) {
            modelopagos.removeRow(0);
        }

        detalle_cobranza.setModal(true);
        detalle_cobranza.setSize(1140, 700);
        detalle_cobranza.setTitle("Generar Nueva Cobranza");
        detalle_cobranza.setLocationRelativeTo(null);
        detalle_cobranza.setVisible(true);
        fecha.requestFocus();
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
        int nFila = this.jTable1.getSelectedRow();
        this.idcontrol.setText(this.jTable1.getValueAt(nFila, 0).toString());
        double num = Double.valueOf(jTable1.getValueAt(nFila, 9).toString());
        String cEstado = jTable1.getValueAt(nFila, 11).toString();

        if (cEstado.equals("ANULADO")) {
            JOptionPane.showMessageDialog(null, "Este recibo ya fue Anulado");
            this.jTable1.requestFocus();
            return;
        }

        if (Config.cNivelUsuario.equals("1")) {
            if (!this.idcontrol.getText().isEmpty()) {
                Object[] opciones = {"   Si   ", "   No   "};
                int ret = JOptionPane.showOptionDialog(null, "Desea Anular esta Cobranza ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
                if (ret == 0) {
                    BDConexion BD = new BDConexion();
                    BD.BorrarDetalles("detalle_forma_cobro", "idmovimiento='" + this.idcontrol.getText().trim() + "'");
                    BD.BorrarDetalles("detalle_cobranzas", "iddetalle='" + this.idcontrol.getText().trim() + "'");
                    cobranzaDAO cobDAO = new cobranzaDAO();
                    cabecera_asientoDAO cabDAO = new cabecera_asientoDAO();
                    try {
                        cobDAO.AnularCobranza(this.idcontrol.getText());
                        cabDAO.eliminarAsiento(num);
                        JOptionPane.showMessageDialog(null, "Recibo Anulado con Éxito");
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
            this.refrescar.doClick();
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
        GrillaCobros GrillaCC = new GrillaCobros();
        Thread HiloGrilla = new Thread(GrillaCC);
        HiloGrilla.start();
    }//GEN-LAST:event_refrescarActionPerformed

    private void ListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListarActionPerformed
        ImprimirRecibo Emite = new ImprimirRecibo();
        Thread HiloReporte = new Thread(Emite);
        HiloReporte.start();
    }//GEN-LAST:event_ListarActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.cliente.getText()));
            if (cl.getCodigo() == 0) {
                this.BuscarCliente.doClick();
            } else {
                nombrecliente.setText(cl.getNombre());
                direccion.setText(cl.getDireccion());
                ruc.setText(cl.getRuc());
                nombregiraduria.setText(cl.getGiraduria().getNombre());
                //Establecemos un título para el jDialog
                MostrarCuentas.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

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
        BCliente.setTitle("Buscar Cliente");
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setVisible(true);
        MostrarCuentas.requestFocus();
        BCliente.setModal(false);
        MostrarCuentas.requestFocus();
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
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN || evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.fecha.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_sucursalKeyPressed

    private void buscarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarSucursalActionPerformed
        sucursalDAO sucDAO = new sucursalDAO();
        sucursal sucu = null;
        try {
            sucu = sucDAO.buscarId(Integer.valueOf(this.sucursal.getText()));
            if (sucu.getCodigo() == 0) {
                BSucursal.setTitle("Buscar Sucursal");
                BSucursal.setModal(true);
                BSucursal.setSize(500, 575);
                BSucursal.setLocationRelativeTo(null);
                BSucursal.setVisible(true);
                BSucursal.setModal(false);
            } else {
                nombresucursal.setText(sucu.getNombre());
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        fecha.requestFocus();
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
                GrillaMoneda grillamo = new GrillaMoneda();
                Thread hiloca = new Thread(grillamo);
                hiloca.start();
                BMoneda.setTitle("Buscar Moneda");
                BMoneda.setModal(true);
                BMoneda.setSize(500, 575);
                BMoneda.setLocationRelativeTo(null);
                BMoneda.setVisible(true);
                cotizacion.requestFocus();
                BMoneda.setModal(false);
            } else {
                nombremoneda.setText(mo.getNombre());
                cotizacion.setText(formatea.format(mo.getVenta()));
                //Establecemos un título para el jDialog
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        cotizacion.requestFocus();
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
                GrillaCobrador grillaco = new GrillaCobrador();
                Thread hilocobr = new Thread(grillaco);
                hilocobr.start();
                BCobrador.setTitle("Buscar Cobrador");
                BCobrador.setModal(true);
                BCobrador.setSize(500, 575);
                BCobrador.setLocationRelativeTo(null);
                BCobrador.setVisible(true);
                BCobrador.setModal(false);
            } else {
                nombrecobrador.setText(cob.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        moneda.requestFocus();
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
                BCaja.setTitle("Buscar Caja");
                BCaja.setModal(true);
                BCaja.setSize(500, 575);
                BCaja.setLocationRelativeTo(null);
                BCaja.setVisible(true);
                BCaja.setModal(false);
            } else {
                this.nrorecibo.setText(formatosinpunto.format(ca.getRecibo()));
                nombrecaja.setText(ca.getNombre());
                //Establecemos un título para el jDialog
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        cobrador.requestFocus();

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
        this.nombregiraduria.setText(this.tablacliente.getValueAt(nFila, 4).toString());
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
        detalle_cobranza.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCobranzaActionPerformed

    private void tablacobranzaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablacobranzaKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            nFila = this.tablacobranza.getSelectedRow();
            String idFactura = (tablacobranza.getValueAt(nFila, 0).toString().trim());
            String csaldo = tablacobranza.getValueAt(nFila, 6).toString();
            csaldo = csaldo.replace(".", "").replace(",", ".");
            if(Double.valueOf(csaldo)>0){
                JOptionPane.showMessageDialog(null, "No puede Eliminar Importes Positivos");
                return;
            }

            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Eliminar este Comprobante ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                   cuenta_clienteDAO DAOCTA = new cuenta_clienteDAO();
                try {
                    DAOCTA.borrarItem(idFactura);
                    this.MostrarCuentas.doClick();
                    JOptionPane.showMessageDialog(null, "Proceso Realizado con Ëxito");
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                   
            }

        }

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
            this.Grabar.doClick();
        }

        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            int nFila = this.tablacobranza.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablacobranza.requestFocus();
                return;
            }
            String cReferencia = tablacobranza.getValueAt(nFila, 0).toString();
            String cImporte = this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 6).toString();
            String cDocumento = this.tablacobranza.getValueAt(this.tablacobranza.getSelectedRow(), 1).toString();

            configuracionDAO confDAO = new configuracionDAO();
            configuracion configrete = new configuracion();
            configrete = confDAO.consultar();
            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());

            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Confirmar el Descuento a esta Cuota ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {

                cImporte = cImporte.replace(".", "").replace(",", ".");
                double nValorCuenta = Double.valueOf(cImporte);
                nValorCuenta = nValorCuenta * -1;

                String detacuota = "[";
                String iddoc = UUID.crearUUID();
                iddoc = iddoc.substring(1, 25);
                String lineacuota = "{iddocumento : " + iddoc + ","
                        + "creferencia : " + cReferencia + ","
                        + "documento : " + cDocumento + ","
                        + "fecha : " + FechaProceso + ","
                        + "vencimiento : " + FechaProceso + ","
                        + "cliente : " + cliente.getText() + ","
                        + "sucursal: " + sucursal.getText() + ","
                        + "moneda : " + moneda.getText() + ","
                        + "comprobante : " + configrete.getComprobanteinteres() + ","
                        + "vendedor : " + "1" + ","
                        + "importe : " + nValorCuenta + ","
                        + "numerocuota : " + "1" + ","
                        + "cuota : " + "1" + ","
                        + "saldo : " + nValorCuenta
                        + "},";
                detacuota += lineacuota;
                if (!detacuota.equals("[")) {
                    detacuota = detacuota.substring(0, detacuota.length() - 1);
                }
                detacuota += "]";
                System.out.println(detacuota);
                try {
                    cuenta_clienteDAO ctaDAOrete = new cuenta_clienteDAO();
                    ctaDAOrete.guardarCuenta(detacuota);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                MostrarCuentas.doClick();
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_F6) {

            int nFila = tablacobranza.getSelectedRow();
            System.out.println("Referencia " + tablacobranza.getValueAt(nFila, 12).toString());
            String cId = tablacobranza.getValueAt(nFila, 12).toString();
            String cFactura = tablacobranza.getValueAt(nFila, 1).toString();
            String cComprobante = tablacobranza.getValueAt(nFila, 4).toString();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
                this.tablacobranza.requestFocus();
                return;
            }
            int cantidadRegistro = modelostock.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelostock.removeRow(0);
            }

            detalle_ventaDAO stDAO = new detalle_ventaDAO();
            try {
                for (detalle_venta st : stDAO.MostrarDetalle(cId)) {
                    String Datos[] = {st.getCodprod().getNombre(),
                        formatea.format(st.getCantidad()),
                        formatea.format(st.getPrecio())};
                    modelostock.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablastock.setRowSorter(new TableRowSorter(modelostock));
            int cantFilas = tablastock.getRowCount();

            etiquetacodigo.setText(cFactura);
            etiquetanombre.setText(cComprobante);
            mercaderias.setModal(true);
            mercaderias.setSize(470, 375);
            //Establecemos un título para el jDialog
            mercaderias.setLocationRelativeTo(null);
            mercaderias.setVisible(true);
            SalirStock.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablacobranzaKeyPressed

    private void GrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarActionPerformed
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
        //totalvalores

        String ctotalvalores = this.totalvalores.getText();
        ctotalvalores = ctotalvalores.replace(".", "").replace(",", ".");
        if (ctotalvalores.isEmpty() || ctotalvalores.equals("0")) {
            Object[] fila = new Object[7];
            fila[0] = "1";
            fila[1] = "EFECTIVO";
            fila[2] = "1";
            fila[3] = "SIN DATOS";
            fila[4] = "0";
            fila[5] = formatoFecha.format(this.fecha.getDate());
            fila[6] = ctotalcobro;
            modelopagos.addRow(fila);
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Confirmar el Cobro de las Cuotas ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            this.GrabarCobro();
        }
        this.SalirCobranza.doClick();
        this.refrescar.doClick();

        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarActionPerformed

    private void calcularporcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularporcentajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calcularporcentajeActionPerformed

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

    private void calcularporcentajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calcularporcentajeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.GrabarPagoParcial.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_calcularporcentajeKeyPressed

    private void cajaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cajaFocusGained

        // TODO add your handling code here:
    }//GEN-LAST:event_cajaFocusGained

    private void GrabarRetencionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarRetencionActionPerformed
        if (this.nroretencion.getText().isEmpty() || this.nroretencion.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Número de Retención");
            this.nroretencion.requestFocus();
            return;
        }

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Retención ? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            configuracionDAO confDAO = new configuracionDAO();
            configuracion configrete = new configuracion();
            configrete = confDAO.consultar();

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cli = null;
            monedaDAO mnDAO = new monedaDAO();
            moneda mn = null;

            retenciones_ventas rete = new retenciones_ventas();
            retenciones_ventasDAO retDAO = new retenciones_ventasDAO();

            try {
                suc = sucDAO.buscarId(Integer.valueOf(sucursalret.getText()));
                cli = cliDAO.buscarId(Integer.valueOf(clienteret.getText()));
                mn = mnDAO.buscarId(Integer.valueOf(monedaret.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            rete.setSucursal(suc);
            rete.setMoneda(mn);
            rete.setCliente(cli);
            rete.setCreferencia(this.creferenciaret.getText());
            rete.setNroretencion(nroretencion.getText());
            rete.setNrofactura(nrofactura.getText());
            Date FechaProceso = ODate.de_java_a_sql(fecharetencion.getDate());
            rete.setFecha(FechaProceso);

            String cImporteSinIva = this.importe_sin_iva.getText();
            cImporteSinIva = cImporteSinIva.replace(".", "").replace(",", ".");
            rete.setImporte_sin_iva(Double.valueOf(cImporteSinIva));

            String cImporteIva = this.importe_iva.getText();
            cImporteIva = cImporteIva.replace(".", "").replace(",", ".");
            rete.setImporte_iva(Double.valueOf(cImporteIva));

            String cImporteGravadoTotal = this.importe_gravado_total.getText();
            cImporteGravadoTotal = cImporteGravadoTotal.replace(".", "").replace(",", ".");

            rete.setImporte_gravado_total(Double.valueOf(cImporteGravadoTotal));
            rete.setTotalneto(Double.valueOf(cImporteGravadoTotal));

            String cPorcentaje = this.porcentaje_retencion.getText();
            cPorcentaje = cPorcentaje.replace(".", "").replace(",", ".");
            rete.setPorcentaje_retencion(Double.valueOf(cPorcentaje));

            String cValorRetencion = this.valor_retencion.getText();
            cValorRetencion = cValorRetencion.replace(".", "").replace(",", ".");
            rete.setValor_retencion(Double.valueOf(cValorRetencion));

            String cCotizacion = this.cotizacion.getText();
            cCotizacion = cCotizacion.replace(".", "").replace(",", ".");
            rete.setCotizacion(Double.valueOf(cCotizacion));
            rete.setGenerarasiento(1);
            rete.setObservacion("Retención s/comprobante N° " + nrofactura.getText());

            int enviacta = 0;

            if (this.enviarcta.isSelected()) {
                enviacta = 1;
            } else {
                enviacta = 0;
            }
            rete.setEnviarcta(enviacta);

            double nValorCuenta = Double.valueOf(cValorRetencion);
            nValorCuenta = nValorCuenta * -1;

            String detacuota = "[";
            if (enviacta == 1) {
                String iddoc = UUID.crearUUID();
                iddoc = iddoc.substring(1, 25);
                String lineacuota = "{iddocumento : " + iddoc + ","
                        + "creferencia : " + this.creferenciaret.getText() + ","
                        + "documento : " + nrofactura.getText() + ","
                        + "fecha : " + FechaProceso + ","
                        + "vencimiento : " + FechaProceso + ","
                        + "cliente : " + clienteret.getText() + ","
                        + "sucursal: " + sucursalret.getText() + ","
                        + "moneda : " + monedaret.getText() + ","
                        + "comprobante : " + configrete.getCod_retencion() + ","
                        + "vendedor : " + "1" + ","
                        + "importe : " + nValorCuenta + ","
                        + "numerocuota : " + "1" + ","
                        + "cuota : " + "1" + ","
                        + "saldo : " + nValorCuenta
                        + "},";
                detacuota += lineacuota;
                if (!detacuota.equals("[")) {
                    detacuota = detacuota.substring(0, detacuota.length() - 1);
                }
                detacuota += "]";
                System.out.println(detacuota);
            }
            try {
                retDAO.borrarRetencion(this.creferenciaret.getText());
                retDAO.borrarCuenta(this.creferenciaret.getText(), configrete.getCod_retencion());
                retDAO.AgregarRetencionesVenta(rete);
                cuenta_clienteDAO ctaDAOrete = new cuenta_clienteDAO();
                if (enviacta == 1) {
                    ctaDAOrete.guardarCuenta(detacuota);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        retenciones.setVisible(false);
        MostrarCuentas.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarRetencionActionPerformed

    private void SalirRetencionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirRetencionActionPerformed
        retenciones.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirRetencionActionPerformed

    private void nroretencionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nroretencionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.porcentaje_retencion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nroretencion.requestFocus();
        }   // TODO add your handling code */

        // TODO add your handling code here:
    }//GEN-LAST:event_nroretencionKeyPressed

    private void fecharetencionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fecharetencionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fecharetencionFocusGained

    private void fecharetencionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fecharetencionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_fecharetencionKeyPressed

    private void porcentaje_retencionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porcentaje_retencionActionPerformed
        String cImporteIva = importe_iva.getText();
        String cPorcentaje = porcentaje_retencion.getText();
        cImporteIva = cImporteIva.replace(".", "").replace(",", ".");
        cPorcentaje = cPorcentaje.replace(".", "").replace(",", ".");
        valor_retencion.setText(formatea.format(Math.round(Double.valueOf(cImporteIva) * Double.valueOf(cPorcentaje) / 100)));
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentaje_retencionActionPerformed

    private void porcentaje_retencionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_porcentaje_retencionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.enviarcta.requestFocus();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_porcentaje_retencionKeyPressed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        this.limpiar();
        this.caja.setEnabled(false);
        this.nombrecaja.setEnabled(false);
        tipoOp.setText("1");
        int nFila = this.jTable1.getSelectedRow();
        this.idcontrol.setText(jTable1.getValueAt(nFila, 0).toString());
        String cEstado = jTable1.getValueAt(nFila, 11).toString();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.jTable1.requestFocus();
            return;
        }
        if (cEstado.equals("ANULADO")) {
            JOptionPane.showMessageDialog(null, "Este recibo ya fue Anulado");
            this.jTable1.requestFocus();
            return;
        }
        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            cobranza cob = null;
            cobranzaDAO cobDAO = new cobranzaDAO();

            try {
                cob = cobDAO.BuscarCobro(this.idcontrol.getText());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (cob != null) {
                nrorecibo.setText(formatosinpunto.format(cob.getNumero()));
                sucursal.setText(String.valueOf(cob.getSucursal().getCodigo()));
                nombresucursal.setText(cob.getSucursal().getNombre());
                fecha.setDate(cob.getFecha());
                cliente.setText(String.valueOf(cob.getCliente().getCodigo()));
                nombrecliente.setText(cob.getCliente().getNombre());
                direccion.setText(cob.getCliente().getDireccion());
                ruc.setText(cob.getCliente().getRuc());
                cobrador.setText(String.valueOf(cob.getCobrador().getCodigo()));
                nombrecobrador.setText(String.valueOf(cob.getCobrador().getNombre()));
                caja.setText(String.valueOf(cob.getCaja().getCodigo()));
                nombrecaja.setText(cob.getCaja().getNombre());
                moneda.setText(formatosinpunto.format(cob.getMoneda().getCodigo()));
                nombremoneda.setText(cob.getMoneda().getNombre());
                cotizacion.setText(formatea.format(cob.getCotizacionmoneda()));
                observaciones.setText(cob.getObservacion());

                // SE CARGAN LOS DETALLES DE COBROS
                int cantidadRegistro = modelodetalle.getRowCount();
                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelodetalle.removeRow(0);
                }

                detallecobranzaDAO detDAO = new detallecobranzaDAO();
                try {
                    for (detallecobranza co : detDAO.MostrarDetalle(idcontrol.getText())) {
                        String Detalle[] = {co.getIdfactura(), formatosinpunto.format(co.getNrofactura()), formatoFecha.format(co.getEmision()), formatoFecha.format(co.getVence()), co.getComprobante().getNombre(), String.valueOf(co.getCuota()) + "/" + String.valueOf(co.getNumerocuota()), formatea.format(co.getSaldo()), formatea.format(co.getPago()), String.valueOf(co.getComprobante().getCodigo()), String.valueOf(co.getNumerocuota()), String.valueOf(co.getCuota()), moneda.getText()};
                        modelodetalle.addRow(Detalle);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                // SE CARGAN LOS DETALLES DE COBROS SI LOS HAY
                this.limpiarformapago();
                detalle_forma_cobroDAO fDAO = new detalle_forma_cobroDAO();
                cantidadRegistro = modelopagos.getRowCount();

                for (int i = 1; i <= cantidadRegistro; i++) {
                    modelopagos.removeRow(0);
                }
                try {
                    for (detalle_forma_cobro ft : fDAO.MostrarDetalle(idcontrol.getText())) {
                        System.out.println("ENTRE");
                        String Datos[] = {String.valueOf(ft.getForma().getCodigo()), ft.getForma().getNombre(), String.valueOf(ft.getBanco().getCodigo()), ft.getBanco().getNombre(), ft.getNrocheque(), formatoFecha.format(ft.getConfirmacion()), formatea.format(ft.getNetocobrado())};
                        modelopagos.addRow(Datos);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }

                int tFilas = tablapagos.getRowCount();
                if (tFilas > 0) {
                    Upditem.setEnabled(true);
                    DelItem.setEnabled(true);
                } else {
                    Upditem.setEnabled(false);
                    DelItem.setEnabled(false);
                }
                this.sumarforma();
                this.sumarcobros();
                detalle_cobranza.setModal(true);
                detalle_cobranza.setSize(1140, 700);
                detalle_cobranza.setTitle("Actualizar Cobranza");
                detalle_cobranza.setLocationRelativeTo(null);
                detalle_cobranza.setVisible(true);
                fecha.requestFocus();
            }
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void NewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewItemActionPerformed
        String csupago = totalcobro.getText();
        csupago = csupago.replace(".", "").replace(",", ".");
        if (Double.valueOf(csupago) <= 0) {
            JOptionPane.showMessageDialog(null, "La Factura no tiene Totales");
        } else {
            limpiarformapago();
            this.cModo.setText("");
            formapago.setSize(513, 270);
            formapago.setTitle("Detalle de Pagos");
            formapago.setLocationRelativeTo(null);
            formapago.setModal(true);
            formapago.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_NewItemActionPerformed

    private void UpditemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpditemActionPerformed
        nFila = this.tablapagos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una Fila");
            return;
        }
        formapago.setSize(513, 270);
        formapago.setTitle("Detalle de Valores");
        formapago.setLocationRelativeTo(null);
        cModo.setText(String.valueOf(nFila));

        forma.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 0).toString());
        nombreformapago.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 1).toString());
        banco.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 2).toString());
        nombrebanco.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 3).toString());
        nrocheque.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 4).toString());
        try {
            confirmacion.setDate(formatoFecha.parse(this.tablapagos.getValueAt(nFila, 5).toString()));
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        importecheque.setText(tablapagos.getValueAt(tablapagos.getSelectedRow(), 6).toString());
        formapago.setModal(true);
        formapago.setVisible(true);
        forma.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_UpditemActionPerformed

    private void DelItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelItemActionPerformed
        nFila = this.tablapagos.getSelectedRow();
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar un Registro");
        } else {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que desea Eliminar el registro? ");
            if (JOptionPane.OK_OPTION == confirmar) {
                modelopagos.removeRow(nFila);
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                this.sumarforma();;
            }
        }        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_DelItemActionPerformed

    private void formaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formaActionPerformed
        this.BuscarFormapago.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_formaActionPerformed

    private void BuscarFormapagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarFormapagoActionPerformed
        formapagoDAO frmDAO = new formapagoDAO();
        formapago frm = null;
        try {
            frm = frmDAO.buscarId(Integer.valueOf(this.forma.getText()));
            if (frm.getCodigo() == 0) {
                GrillaFormaPago grillafrm = new GrillaFormaPago();
                Thread hilofrm = new Thread(grillafrm);
                hilofrm.start();
                BFormaPago.setModal(true);
                BFormaPago.setSize(482, 575);
                BFormaPago.setLocationRelativeTo(null);
                BFormaPago.setVisible(true);
                BFormaPago.setModal(true);
            } else {
                nombreformapago.setText(frm.getNombre());
                //Establecemos un título para el jDialog
            }
            banco.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarFormapagoActionPerformed

    private void bancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bancoActionPerformed
        this.BuscarBanco.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_bancoActionPerformed

    private void BuscarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarBancoActionPerformed
        bancoplazaDAO bDAO = new bancoplazaDAO();
        bancoplaza bco = null;
        try {
            bco = bDAO.buscarId(Integer.valueOf(this.banco.getText()));
            if (bco.getCodigo() == 0) {
                GrillaBanco grillabco = new GrillaBanco();
                Thread hilobco = new Thread(grillabco);
                hilobco.start();
                BBancos.setModal(true);
                BBancos.setSize(482, 575);
                BBancos.setLocationRelativeTo(null);
                BBancos.setVisible(true);
                BBancos.setModal(true);
            } else {
                nombrebanco.setText(bco.getNombre());
                //Establecemos un título para el jDialog
            }
            nrocheque.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarBancoActionPerformed

    private void nrochequeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nrochequeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importecheque.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.banco.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_nrochequeKeyPressed

    private void importechequeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importechequeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.confirmacion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.nrocheque.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importechequeKeyPressed

    private void confirmacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmacionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.grabarPago.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importecheque.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmacionKeyPressed

    private void grabarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarPagoActionPerformed
        String cImporteCheque = this.importecheque.getText();
        cImporteCheque = cImporteCheque.replace(".", "").replace(",", ".");

        if (cImporteCheque.isEmpty() || cImporteCheque.equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Importe");
            this.importecheque.requestFocus();
            return;
        }

        if (this.banco.getText().isEmpty() || this.banco.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código del Banco");
            this.banco.requestFocus();
            return;
        }

        if (this.forma.getText().isEmpty() || this.forma.getText().equals("0")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Forma de Banco");
            this.forma.requestFocus();
            return;
        }

        if (this.cModo.getText().isEmpty()) {
            Object[] fila = new Object[7];
            fila[0] = this.forma.getText().toString();
            fila[1] = this.nombreformapago.getText().toString();
            fila[2] = this.banco.getText();
            fila[3] = this.nombrebanco.getText();
            fila[4] = this.nrocheque.getText();
            fila[5] = formatoFecha.format(this.confirmacion.getDate());
            fila[6] = this.importecheque.getText();
            modelopagos.addRow(fila);
            this.banco.requestFocus();
        } else {
            this.tablapagos.setValueAt(this.forma.getText(), nFila, 0);
            this.tablapagos.setValueAt(this.nombreformapago.getText(), nFila, 1);
            this.tablapagos.setValueAt(this.banco.getText(), nFila, 2);
            this.tablapagos.setValueAt(this.nombrebanco.getText(), nFila, 3);
            this.tablapagos.setValueAt(this.nrocheque.getText(), nFila, 4);
            this.tablapagos.setValueAt(formatoFecha.format(this.confirmacion.getDate()), nFila, 5);
            this.tablapagos.setValueAt(this.importecheque.getText(), nFila, 6);
            nFila = 0;
            this.salirPago.doClick();
        }
        this.limpiarformapago();
        this.sumarforma();
        // TODO add your handling code here:
    }//GEN-LAST:event_grabarPagoActionPerformed

    private void grabarPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_grabarPagoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_grabarPagoKeyPressed

    private void salirPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirPagoActionPerformed
        formapago.setModal(false);
        formapago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirPagoActionPerformed

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

    public void filtrobanco(int nNumeroColumna) {
        trsfiltrobanco.setRowFilter(RowFilter.regexFilter(this.jTBuscarbanco.getText(), nNumeroColumna));
    }

    public void filtroformapago(int nNumeroColumna) {
        trsfiltroformapago.setRowFilter(RowFilter.regexFilter(this.jTBuscarForma.getText(), nNumeroColumna));
    }


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

    private void AceptarGir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGir1ActionPerformed
        int nFila = this.tablaformapago.getSelectedRow();
        this.forma.setText(this.tablaformapago.getValueAt(nFila, 0).toString());
        this.nombreformapago.setText(this.tablaformapago.getValueAt(nFila, 1).toString());

        this.BFormaPago.setVisible(false);
        this.jTBuscarForma.setText("");
        this.banco.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGir1ActionPerformed

    private void SalirGir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGir1ActionPerformed
        this.BFormaPago.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGir1ActionPerformed

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

    private void tablabancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabancoMouseClicked
        this.AceptarCasa.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoMouseClicked

    private void tablabancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablabancoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCasa.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tablabancoKeyPressed

    private void AceptarCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCasaActionPerformed
        int nFila = this.tablabanco.getSelectedRow();
        this.banco.setText(this.tablabanco.getValueAt(nFila, 0).toString());
        this.nombrebanco.setText(this.tablabanco.getValueAt(nFila, 1).toString());

        this.BBancos.setVisible(false);
        this.jTBuscarbanco.setText("");
        this.nrocheque.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCasaActionPerformed

    private void SalirCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCasaActionPerformed
        this.BBancos.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCasaActionPerformed

    private void boxcuotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxcuotasActionPerformed
        int totalRow = modelodetalle.getRowCount();
        totalRow -= 1;
        // sumatoria = 0.00;
        // sumtotal = 0.00;
        if (totalRow < 0) {
            JOptionPane.showMessageDialog(null, "No Existen Cuotas para Cobros");
            this.cliente.requestFocus();
            boxcuotas.setSelected(false);
            return;
        }

        if (boxcuotas.isSelected()) { // Si hemos dado clic en el jCheckBox
            for (int i = 0; i <= (totalRow); i++) {
                this.tablacobranza.setValueAt(this.tablacobranza.getValueAt(i, 6), i, 7);
            }
        } else {
            for (int i = 0; i <= (totalRow); i++) {
                this.tablacobranza.setValueAt(0, i, 8);
            }
        }
        this.sumarcobros();
    }//GEN-LAST:event_boxcuotasActionPerformed

    private void SalirStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirStockActionPerformed
        this.mercaderias.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirStockActionPerformed

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Recibo");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cuenta");
        modelo.addColumn("Denominación del Cliente");
        modelo.addColumn("Moneda");
        modelo.addColumn("Sucursal");
        modelo.addColumn("Cotización");
        modelo.addColumn("Total Cobro");
        modelo.addColumn("Asiento");
        modelo.addColumn("Cobrador");
        modelo.addColumn("Estado");
        modelo.addColumn("Usuario");

        int[] anchos = {3, 100, 90, 90, 200, 100, 100, 100, 100, 90, 200, 100, 100};
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
        this.jTable1.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.jTable1.getColumnModel().getColumn(10).setCellRenderer(TablaRenderer);
    }

    public void CargarTituloDetalle() {
        modelodetalle.addColumn("Id."); //0
        modelodetalle.addColumn("N° Documento");//1
        modelodetalle.addColumn("Emisión");//2
        modelodetalle.addColumn("Vence");//3
        modelodetalle.addColumn("Comprobante");//4
        modelodetalle.addColumn("Cuota");//5
        modelodetalle.addColumn("Saldo");//6
        modelodetalle.addColumn("Su Pago");//7
        modelodetalle.addColumn("CodCom");//8
        modelodetalle.addColumn("NumCuota");//9
        modelodetalle.addColumn("nCuota");//10
        modelodetalle.addColumn("nMoneda");//11
        modelodetalle.addColumn("creferencia");//12

        int[] anchos = {0, 90, 90, 90,
            100, 90, 100,
            100, 1, 1,
            1, 1, 1};
        for (int i = 0; i < modelodetalle.getColumnCount(); i++) {
            this.tablacobranza.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacobranza.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacobranza.getTableHeader().setFont(new Font("Arial Black", 1, 9));

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

        this.tablacobranza.getColumnModel().getColumn(12).setMaxWidth(0);
        this.tablacobranza.getColumnModel().getColumn(12).setMinWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(0);
        this.tablacobranza.getTableHeader().getColumnModel().getColumn(12).setMinWidth(0);
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
                new cobros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCaja;
    private javax.swing.JButton AceptarCasa;
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarCobrador;
    private javax.swing.JButton AceptarGir;
    private javax.swing.JButton AceptarGir1;
    private javax.swing.JButton AceptarSuc;
    private javax.swing.JButton Agregar;
    private javax.swing.JButton Anular;
    private javax.swing.JDialog BBancos;
    private javax.swing.JDialog BCaja;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BCobrador;
    private javax.swing.JDialog BFormaPago;
    private javax.swing.JDialog BMoneda;
    private javax.swing.JDialog BSucursal;
    private javax.swing.JButton BuscarBanco;
    private javax.swing.JButton BuscarCliente;
    private javax.swing.JButton BuscarFormapago;
    private javax.swing.JTextField BuscarMoneda;
    private javax.swing.JButton DelItem;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JButton Grabar;
    private javax.swing.JButton GrabarPagoParcial;
    private javax.swing.JButton GrabarRetencion;
    private javax.swing.JButton Listar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton MostrarCuentas;
    private javax.swing.JButton NewItem;
    private javax.swing.JDialog PagoParcial;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCaja;
    private javax.swing.JButton SalirCasa;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirCobrador;
    private javax.swing.JButton SalirCobranza;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton SalirGir1;
    private javax.swing.JButton SalirPagoParcial;
    private javax.swing.JButton SalirRetencion;
    private javax.swing.JButton SalirStock;
    private javax.swing.JButton SalirSuc;
    private javax.swing.JButton Upditem;
    private javax.swing.JTextField banco;
    private javax.swing.JCheckBox boxcuotas;
    private javax.swing.JButton buscarCaja;
    private javax.swing.JButton buscarCobrador;
    private javax.swing.JButton buscarMoneda;
    private javax.swing.JButton buscarSucursal;
    private javax.swing.JTextField cModo;
    private javax.swing.JTextField caja;
    private javax.swing.JFormattedTextField calcularporcentaje;
    private javax.swing.JTextField cliente;
    private javax.swing.JTextField clienteret;
    private javax.swing.JTextField cobrador;
    private javax.swing.JComboBox combobanco;
    private javax.swing.JComboBox combocaja;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combocobrador;
    private javax.swing.JComboBox comboforma;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JComboBox combosucursal;
    private javax.swing.JTextField concepto;
    private com.toedter.calendar.JDateChooser confirmacion;
    private javax.swing.JFormattedTextField cotizacion;
    private javax.swing.JTextField creferenciaret;
    private javax.swing.JDialog detalle_cobranza;
    private javax.swing.JTextField direccion;
    private javax.swing.JCheckBox enviarcta;
    private javax.swing.JLabel etiquetacodigo;
    private javax.swing.JLabel etiquetanombre;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fecharetencion;
    private javax.swing.JTextField forma;
    private javax.swing.JDialog formapago;
    private javax.swing.JButton grabarPago;
    private javax.swing.JTextField idControl;
    private javax.swing.JTextField idcontrol;
    private javax.swing.JFormattedTextField importe_a_pagar;
    private javax.swing.JFormattedTextField importe_gravado_total;
    private javax.swing.JFormattedTextField importe_iva;
    private javax.swing.JFormattedTextField importe_pagado;
    private javax.swing.JFormattedTextField importe_sin_iva;
    private javax.swing.JFormattedTextField importecheque;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
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
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
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
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
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
    private javax.swing.JTextField jTBuscarCobrador;
    private javax.swing.JTextField jTBuscarForma;
    private javax.swing.JTextField jTBuscarSucursal;
    private javax.swing.JTextField jTBuscarbanco;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private javax.swing.JDialog mercaderias;
    private javax.swing.JTextField moneda;
    private javax.swing.JTextField monedaret;
    private javax.swing.JTextField nombrebanco;
    private javax.swing.JTextField nombrecaja;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombreclienteret;
    private javax.swing.JTextField nombrecobrador;
    private javax.swing.JTextField nombreformapago;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nombremoneda;
    private javax.swing.JTextField nombremonedaret;
    private javax.swing.JTextField nombresucursal;
    private javax.swing.JTextField nombresucursalret;
    private javax.swing.JTextField nrocheque;
    private javax.swing.JTextField nrocuota;
    private javax.swing.JTextField nrodocumento;
    private javax.swing.JTextField nrofactura;
    private javax.swing.JTextField nrorecibo;
    private javax.swing.JTextField nroretencion;
    private javax.swing.JTextArea observaciones;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JFormattedTextField porcentaje_retencion;
    private javax.swing.JButton refrescar;
    private javax.swing.JDialog retenciones;
    private javax.swing.JTextField ruc;
    private javax.swing.JButton salirPago;
    private javax.swing.JFormattedTextField sucambio;
    private javax.swing.JTextField sucursal;
    private javax.swing.JTextField sucursalret;
    private javax.swing.JTable tablabanco;
    private javax.swing.JTable tablacaja;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacobrador;
    private javax.swing.JTable tablacobranza;
    private javax.swing.JTable tablaformapago;
    private javax.swing.JTable tablamoneda;
    private javax.swing.JTable tablapagos;
    private javax.swing.JTable tablastock;
    private javax.swing.JTable tablasucursal;
    private javax.swing.JTextField tipoOp;
    private javax.swing.JFormattedTextField totalcobro;
    private javax.swing.JFormattedTextField totalvalores;
    private javax.swing.JFormattedTextField valor_retencion;
    // End of variables declaration//GEN-END:variables

    private class GrillaCobros extends Thread {

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
                for (cobranza orden : DAO.MostrarxDiaCobro(dFechaInicio, dFechaFinal, 0)) {
                    String Datos[] = {orden.getIdpagos(),
                        formatosinpunto.format(orden.getNumero()),
                        formatoFecha.format(orden.getFecha()),
                        String.valueOf(orden.getCliente().getCodigo()),
                        orden.getCliente().getNombre(),
                        orden.getMoneda().getEtiqueta(),
                        orden.getSucursal().getNombre(),
                        formatea.format(orden.getCotizacionmoneda()),
                        formatea.format(orden.getTotalpago()),
                        formatosinpunto.format(orden.getAsiento()),
                        orden.getCobrador().getNombre(),
                        orden.getEstado(),
                        orden.getCodusuario().getLast_name()};
                    modelo.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            jTable1.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = jTable1.getRowCount();
            if (cantFilas > 0) {
                Anular.setEnabled(true);
                Modificar.setEnabled(true);
                Listar.setEnabled(true);
            } else {
                Anular.setEnabled(false);
                Modificar.setEnabled(false);
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
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getDireccion(), cli.getRuc(), cli.getGiraduria().getNombre()};
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
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre(), formatosinpunto.format(ca.getRecibo())};
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
            double nPago = 0.00;
            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            Date dFecha = ODate.de_java_a_sql(fecha.getDate());

            int cantidadRegistro = modelodetalle.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelodetalle.removeRow(0);
            }

            cuenta_clienteDAO COBCAJA = new cuenta_clienteDAO();
            try {
                for (cuenta_clientes cue : COBCAJA.MostrarxClienteMonedaRucVence(Integer.valueOf(cliente.getText()), Integer.valueOf(moneda.getText()), ruc.getText())) {
                    String Datos[] = {cue.getIddocumento(),
                        cue.getDocumento(),
                        formatoFecha.format(cue.getFecha()),
                        formatoFecha.format(cue.getVencimiento()),
                        cue.getComprobante().getNombre(),
                        String.valueOf(cue.getCuota()) + "/"
                        + String.valueOf(cue.getNumerocuota()),
                        formatea.format(cue.getSaldo()),
                        formatea.format(nPago),
                        String.valueOf(cue.getComprobante().getCodigo()),
                        String.valueOf(cue.getNumerocuota()),
                        String.valueOf(cue.getCuota()),
                        moneda.getText(),
                        cue.getCreferencia()};
                    modelodetalle.addRow(Datos);
                }
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            tablacobranza.setRowSorter(new TableRowSorter(modelodetalle));
            int cantFilas = tablacobranza.getRowCount();
        }
    }

    private class ImprimirRecibo extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreRecibo = config.getNombrerecibo();
            int nFila = jTable1.getSelectedRow();
            String cCliente = jTable1.getValueAt(nFila, 3).toString();
            System.out.println("Cliente " + cCliente);
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = jTable1.getValueAt(nFila, 8).toString();
                num = num.replace(".", "").replace(",", ".");
                numero_a_letras numero = new numero_a_letras();
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cRucEmpresa", Config.cRucEmpresa);
                parameters.put("cTelefonoEmpresa", Config.cTelefono);
                parameters.put("cReferencia", jTable1.getValueAt(nFila, 0).toString());
                parameters.put("Letra", numero.Convertir(num, true, 1));

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreRecibo.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
                // }
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class GrillaFormaPago extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloformapago.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloformapago.removeRow(0);
            }
            formapagoDAO formaDAO = new formapagoDAO();
            try {
                for (formapago fr : formaDAO.todos()) {
                    String Datos[] = {String.valueOf(fr.getCodigo()), fr.getNombre()};
                    modeloformapago.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaformapago.setRowSorter(new TableRowSorter(modeloformapago));
            int cantFilas = tablaformapago.getRowCount();
        }
    }

    private class GrillaBanco extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelobanco.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelobanco.removeRow(0);
            }
            bancoplazaDAO bancoDAO = new bancoplazaDAO();
            try {
                for (bancoplaza ba : bancoDAO.todos()) {
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

}

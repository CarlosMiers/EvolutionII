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
import DAO.clienteDAO;
import DAO.comprobanteDAO;
import DAO.configuracionDAO;
import DAO.giraduriaDAO;
import DAO.prestamoDAO;
import DAO.cuenta_clienteDAO;
import DAO.custodiaDAO;
import DAO.detalle_prestamoDAO;
import DAO.historico_custodiasDAO;
import DAO.monedaDAO;
import DAO.sucursalDAO;
import DAO.vendedorDAO;
import Modelo.Tablas;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.custodia;
import Modelo.detalle_prestamo;
import Modelo.giraduria;
import Modelo.historico_custodia;
import Modelo.moneda;
import Modelo.prestamo;
import Modelo.sucursal;
import Modelo.vendedor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
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
public class prestamos_gimenez_calvo extends javax.swing.JFrame {

    String referencia = null;
    Conexion con = null;
    Statement stm, stm2 = null;
    ResultSet results = null;
    Tablas modelo = new Tablas();
    Tablas modelocliente = new Tablas();
    Tablas modelovendedor = new Tablas();
    Tablas modelocomprobante = new Tablas();
    Tablas modelogiraduria = new Tablas();
    Tablas modelocustodia = new Tablas();
    Tablas modelocusto = new Tablas();
    Tablas modelocupon = new Tablas();
    Tablas modeloentidadcustodia = new Tablas();
    JScrollPane scroll = new JScrollPane();
    private TableRowSorter trsfiltro, trsfiltrogira, trsfiltrocustodia, trsfiltrocli, trsfiltrocomprobante, trsfiltrovendedor;
    ObtenerFecha ODate = new ObtenerFecha();
    Date dFechaInicio = null;
    Date dFechaFinal = null;
    String cTotalImprimir = null;
    String cCliente, cReferencia, cSql = null;
    String cCodigoGastos, idPrestamo = null;
    Calendar c2 = new GregorianCalendar();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); // FORMATO DE FECHA
    DecimalFormat formatoSinpunto = new DecimalFormat("######");
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    int tipoconsulta = 0;
    /**
     * Creates new form Template
     */
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

    public prestamos_gimenez_calvo() {
        initComponents();
        this.idmovimiento.setVisible(false);
        this.BotonImprimir.setIcon(iconoprint);
        this.BotonAgregar.setIcon(icononuevo);
        this.BotonEditar.setIcon(iconoeditar);
        this.BotonDelete.setIcon(iconoborrar);
        this.Salir.setIcon(iconosalir);
        this.Refrescar.setIcon(icorefresh);
        this.BotonSeleccionar.setVisible(false);
        this.BuscarCustodiaOrigen.setIcon(iconobuscar);
        this.BuscarCustodiaDestino.setIcon(iconobuscar);
        this.BuscarSocio.setIcon(iconobuscar);
        this.BuscarAsesor.setIcon(iconobuscar);
        this.BuscarPrestamo.setIcon(iconobuscar);
        this.BotonGrabar.setIcon(iconograbar);
        this.BotonSalir.setIcon(iconosalir);
        this.tablaprincipal.setShowGrid(false);
        this.tablaprincipal.setOpaque(true);
        this.tablaprincipal.setBackground(new Color(102, 204, 255));
        this.tablaprincipal.setForeground(Color.BLACK);
        this.vencimientos.setVisible(false);
        this.venceanterior.setVisible(false);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.creferencia.setVisible(false);
        this.cargarTitulo();
        this.TituloCupon();
        this.TitClie();
        this.TituloVendedor();
        this.Inicializar();
        this.cargarTabla();
        this.TituloCustodia();
        this.TituloCu();
        this.TituloComprobante();
        this.TituloEntidadCustodia();

        GrillaCustodia grillacm = new GrillaCustodia();
        Thread hiloca = new Thread(grillacm);
        hiloca.start();

        ///SOCIOS
        GrillaSocio grillacl = new GrillaSocio();
        Thread hilocl = new Thread(grillacl);
        hilocl.start();
        ///TIPO DE PRESTAMOS
        GrillaPrestamo grillapr = new GrillaPrestamo();
        Thread hilopr = new Thread(grillapr);
        hilopr.start();
        ///ASESORES
        GrillaAsesor grillaas = new GrillaAsesor();
        Thread hiloas = new Thread(grillaas);
        hiloas.start();
    }

    Control hand = new Control();

    private void ImprimirDocumentos(String reporte) throws SQLException {
        con = new Conexion();
        stm = con.conectar();
        try {
            Map parameters = new HashMap();

            int nFila = tablaprincipal.getSelectedRow();
            String num = tablaprincipal.getValueAt(nFila, 7).toString();

            num = num.replace(".", "").replace(",", ".");
            numero_a_letras numero = new numero_a_letras();

            parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
            parameters.put("Letra", numero.Convertir(num, true, Integer.valueOf(tablaprincipal.getValueAt(nFila, 19).toString())));
            parameters.put("nNumeroPrestamo", tablaprincipal.getValueAt(nFila, 1).toString());

            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            JasperReport jr = null;

            URL url = getClass().getClassLoader().getResource("Reports/" + reporte.trim());
            jr = (JasperReport) JRLoader.loadObject(url);
            JasperPrint masterPrint = null;
            //Se le incluye el parametro con el nombre parameters porque asi lo definimos

            masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
            JasperPrintManager.printReport(masterPrint, false);

        } catch (Exception e) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            JOptionPane.showMessageDialog(null, "No puede emitirse el Reporte");
            System.out.println(e);
        }
        stm.close();
// TODO add your handling code here:
    }

    private void generarcuotas() {
        java.util.Date fecha1, fecha2;
        String cdiferencia = null;
        String cdescuento = null;
        double ndescuento = 0.00;
        //Como siempre capturamos la moneda para poder hacer con decimales dependiendo
        // del tipo de moneda
        //Capturamos la tasa
        String cTasa = this.tasa.getText();
        cTasa = cTasa.replace(".", "");
        cTasa = cTasa.replace(",", ".");
        double nTotalInteres = 0.00;
        double nTotalPrestamo = 0.00;
        double nTasa = Double.parseDouble(cTasa);
        //Capturamos el importe del prestamo
        String cMonto = this.importe.getText();
        cMonto = cMonto.replace(".", "");
        cMonto = cMonto.replace(",", ".");
        String cMontoCuota = this.importecuota.getText();
        cMontoCuota = cMontoCuota.replace(".", "");
        cMontoCuota = cMontoCuota.replace(",", ".");

        double nCuotaAnterior = 0.00;
        double nMonto = 0.00;
        double nMontoCuota = 0.00;
        double nMontoCapital = 0.00;

        nMonto = Math.round(Double.parseDouble(cMonto));
        nMontoCuota = Double.parseDouble(cMontoCuota);
        nMontoCapital = nMonto;

        nCuotaAnterior = nMontoCuota;
        double nInteres = 0.00;
        double nAmortiza = 0.00;
        String cCuota = this.importecuota.getText();
        int nCantidad = Integer.parseInt(this.plazo.getText());

        Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
        calendar.setTime(this.primervencimiento.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
        this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 

        for (int i = 0; i < tablacupon.getRowCount(); i++) {
            modelocupon.removeRow(i);
            i -= 1;
        }

        for (int i = 1; i <= nCantidad; i++) {
            //Instanciamos la Clase DecimalFormat para darle formato numerico a las celdas.
            // Se crea un array que será una de las filas de la tabla.
            // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
            Object[] fila = new Object[7]; // Hay 8   columnas en la tabla

            if (i == 1) {
                final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
                fecha1 = this.fecha.getDate();
                fecha2 = this.vencimientos.getDate();
                long diferencia = (fecha2.getTime() - fecha1.getTime()) / MILLSECS_PER_DAY;
                cdiferencia = Long.toString(diferencia + 1);
            }
            fila[0] = i;
            fila[1] = "30";
            fila[2] = formatoFecha.format(this.fecha.getDate());
            fila[3] = formatoFecha.format(this.vencimientos.getDate());
            //Calculamos los valores de acuerdo al tipo de Moneda
            //Moneda Local sin decimales
            nInteres = Math.round(nMonto * (nTasa / 12) / 100);
            nAmortiza = Math.round(nMontoCuota - nInteres);
            if (i != nCantidad) {
                fila[4] = formatea.format(nAmortiza);
                fila[5] = formatea.format(nInteres);
                fila[6] = formatea.format(nMontoCuota);
            } else {
                double ndiferencia = nAmortiza - nMonto;
                if (ndiferencia > 0) {
                    nAmortiza = nAmortiza - ndiferencia;
                    nInteres = nInteres + ndiferencia;
                } else {
                    nAmortiza = nAmortiza + Math.abs(ndiferencia);
                    nInteres = nInteres - Math.abs(ndiferencia);
                }
                fila[4] = formatea.format(nAmortiza);
                fila[5] = formatea.format(nInteres);
                fila[6] = formatea.format(nMontoCuota);
            }
            modelocupon.addRow(fila);// Se añade al modelo la fila completa.
            calendar.setTime(this.vencimientos.getDate()); // Capturamos en el setTime el valor de la fecha ingresada
            this.venceanterior.setDate(calendar.getTime()); //Guardamos el vencimiento anterior

            // Se capturan el día y el mes para saber si hay que aumentar los días en el siguiente mes
            // esto se hace en caso que el mes sea febrero
            int mes = this.venceanterior.getCalendar().get(Calendar.MONTH) + 1;
            int dia = this.venceanterior.getCalendar().get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.MONTH, 1);  // numero de meses a añadir, o restar en caso de días<0

            if (mes == 2 && dia == 28) {
                calendar.add(Calendar.DATE, 2);  // en caso que sea Febrero 28 se aumentan a dos días 
                //el vencimiento
            }
            this.vencimientos.setDate(calendar.getTime()); //Y cargamos finalmente en el 
            nMonto = nMonto - nAmortiza;
            //Acumulamos los importes para los totales
            //Total Interes, Total Iva, Total Préstamo
            nTotalPrestamo = nTotalPrestamo + nMontoCuota;
            final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
            fecha1 = this.venceanterior.getDate();
            fecha2 = this.vencimientos.getDate();
            long diferencia = (fecha2.getTime() - fecha1.getTime()) / MILLSECS_PER_DAY;
            cdiferencia = Long.toString(diferencia);
        }
        this.sumar();
    }

    public void sumar() {
        ///ESTE PROCEDIMIENTO USAMOS PARA REALIZAR LAS SUMATORIAS DE
        ///CADA CELDA 
        double suamortizacion = 0.00;
        double suinteres = 0.00;
        double suprestamo = 0.00;

        String camortiza = "";
        String cinteres = "";
        String ctotal = "";

        int totalRow = tablacupon.getRowCount();
        totalRow -= 1;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA EL TOTAL A PAGAR
            camortiza = String.valueOf(tablacupon.getValueAt(i, 4));
            camortiza = camortiza.replace(".", "").replace(",", ".");
            suamortizacion += Double.valueOf(camortiza);

            cinteres = String.valueOf(tablacupon.getValueAt(i, 5));
            cinteres = cinteres.replace(".", "").replace(",", ".");
            suinteres += Double.valueOf(cinteres);

            ctotal = String.valueOf(tablacupon.getValueAt(i, 6));
            ctotal = ctotal.replace(".", "").replace(",", ".");
            suprestamo += Double.valueOf(ctotal);
        }
        //LUEGO RESTAMOS AL VALOR NETO A ENTREGAR
        this.totalamortizacion.setText(formatea.format(suamortizacion));
        this.totalinteres.setText(formatea.format(suinteres));
        this.totalprestamo.setText(formatea.format(suprestamo));

    }

    private void limpiar() {
        this.numeroprestamo.setText("0");
        this.tipoprestamo.setText("0");
        this.nombreprestamo.setText("");
        this.fecha.setCalendar(c2);
        this.primervencimiento.setCalendar(c2);
        this.socio.setText("0");
        this.nombresocio.setText("");
        this.importe.setText("0");
        this.tasa.setText("0");
        this.plazo.setText("0");
        this.importecuota.setText("0");
        this.asesor.setText("0");
        this.nombreasesor.setText("");
        this.totalinteres.setText("0");
        this.totalamortizacion.setText("0");
        this.totalprestamo.setText("0");
        int cantidadRegistro = modelocupon.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelocupon.removeRow(0);
        }

    }

    private void TituloComprobante() {
        modelocomprobante.addColumn("Código");
        modelocomprobante.addColumn("Nombre");
        modelocomprobante.addColumn("Tasa");

        int[] anchos = {90, 200, 90};
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
        this.tablacomprobante.getColumnModel().getColumn(2).setCellRenderer(TablaRenderer);
    }

    private void TitClie() {
        modelocliente.addColumn("Código");
        modelocliente.addColumn("Nombre");
        modelocliente.addColumn("Dirección");
        modelocliente.addColumn("RUC");
        modelocliente.addColumn("Plazo");

        int[] anchos = {90, 150, 100, 100, 90};
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

        this.tablacliente.getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacliente.getColumnModel().getColumn(4).setMinWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        this.tablacliente.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);

    }

    private void TituloCupon() {
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer TablaCentro = new DefaultTableCellRenderer();
        TablaCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        modelocupon.addColumn("N° Cuota");
        modelocupon.addColumn("Plazo");
        modelocupon.addColumn("Emisión");
        modelocupon.addColumn("Vence");
        modelocupon.addColumn("Amortiza");
        modelocupon.addColumn("Interes");
        modelocupon.addColumn("Cuota");
        int[] anchos = {120, 150, 150, 150, 150, 150, 150};
        for (int i = 0; i < modelocupon.getColumnCount(); i++) {
            tablacupon.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacupon.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacupon.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
        tablacupon.getColumnModel().getColumn(1).setCellRenderer(TablaCentro);
        tablacupon.getColumnModel().getColumn(2).setCellRenderer(TablaCentro);
        tablacupon.getColumnModel().getColumn(3).setCellRenderer(TablaCentro);
        tablacupon.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        tablacupon.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        tablacupon.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        //Se usa para poner invisible una determinada celda
        tablacupon.getTableHeader().setFont(new Font("Arial Black", 1, 9));
        Font font = new Font("Arial", Font.BOLD, 9);
        tablacupon.setFont(font);

    }

    private void TituloVendedor() {
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

    private void TituloEntidadCustodia() {
        modeloentidadcustodia.addColumn("Código");
        modeloentidadcustodia.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modeloentidadcustodia.getColumnCount(); i++) {
            tablaentidadcustodia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablaentidadcustodia.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaentidadcustodia.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablaentidadcustodia.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablaentidadcustodia.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    private void Inicializar() {
        this.FechaInicial.setCalendar(c2);
        this.FechaFinal.setCalendar(c2);
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genfacturaotros = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        salirfactura2 = new javax.swing.JButton();
        nroprestamo2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        fechaprestamo2 = new com.toedter.calendar.JDateChooser();
        grabarfactura2 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        nombrecliente2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        moneda2 = new javax.swing.JTextField();
        lblgastos = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        nrofactura2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacion2 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        interesordinario = new javax.swing.JFormattedTextField();
        custodiaPagare = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        numeroprestamo = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        idmovimiento = new javax.swing.JTextField();
        nombretitularprestamo = new javax.swing.JTextField();
        importeprestamo = new javax.swing.JFormattedTextField();
        jLabel36 = new javax.swing.JLabel();
        fechaemisionprestamo = new com.toedter.calendar.JDateChooser();
        jLabel37 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        origencustodia = new javax.swing.JTextField();
        BuscarCustodiaOrigen = new javax.swing.JButton();
        BuscarCustodiaDestino = new javax.swing.JButton();
        nombrecustodiaorigen = new javax.swing.JTextField();
        destinocustodia = new javax.swing.JTextField();
        fechaprocesocustodia = new com.toedter.calendar.JDateChooser();
        nombrecustodiadestino = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        GrabarCustodia = new javax.swing.JButton();
        BorrarCustodia = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablacustodia = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        BCustodia = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        combocustodia = new javax.swing.JComboBox();
        JTBuscarCustodia = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaentidadcustodia = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        AceptarCustodia = new javax.swing.JButton();
        SalirCustodia = new javax.swing.JButton();
        ExportarCustodia = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        origenc = new javax.swing.JTextField();
        BuscarCustodiaO = new javax.swing.JButton();
        BuscarCustodiaD = new javax.swing.JButton();
        nombrecustodiao = new javax.swing.JTextField();
        destinoc = new javax.swing.JTextField();
        fechaprocesoc = new com.toedter.calendar.JDateChooser();
        nombrecustodiad = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        GrabarCus = new javax.swing.JButton();
        BorrarC = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablacusto = new javax.swing.JTable();
        Salircus = new javax.swing.JButton();
        detalle_prestamo = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tipoprestamo = new javax.swing.JTextField();
        BuscarPrestamo = new javax.swing.JButton();
        nombreprestamo = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel46 = new javax.swing.JLabel();
        primervencimiento = new com.toedter.calendar.JDateChooser();
        jLabel47 = new javax.swing.JLabel();
        socio = new javax.swing.JTextField();
        BuscarSocio = new javax.swing.JButton();
        nombresocio = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        plazo = new javax.swing.JFormattedTextField();
        jLabel49 = new javax.swing.JLabel();
        tasa = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        importecuota = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        asesor = new javax.swing.JTextField();
        BuscarAsesor = new javax.swing.JButton();
        nombreasesor = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        importe = new javax.swing.JFormattedTextField();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablacupon = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        BotonGrabar = new javax.swing.JButton();
        BotonSalir = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        numero = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        totalamortizacion = new javax.swing.JFormattedTextField();
        totalinteres = new javax.swing.JFormattedTextField();
        totalprestamo = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BCliente = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combocliente = new javax.swing.JComboBox();
        jTBuscarCliente = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarCli = new javax.swing.JButton();
        SalirCli = new javax.swing.JButton();
        BPrestamo = new javax.swing.JDialog();
        jPanel23 = new javax.swing.JPanel();
        combocomprobante = new javax.swing.JComboBox();
        jTBuscarComprobante = new javax.swing.JTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablacomprobante = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        AceptarComprobante = new javax.swing.JButton();
        SalirComprobante = new javax.swing.JButton();
        BVendedor = new javax.swing.JDialog();
        jPanel25 = new javax.swing.JPanel();
        combovendedor = new javax.swing.JComboBox();
        jTBuscarVendedor = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        tablavendedor = new javax.swing.JTable();
        jPanel26 = new javax.swing.JPanel();
        AceptarVendedor = new javax.swing.JButton();
        SalirVendedor = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        BotonEditar = new javax.swing.JButton();
        BotonAgregar = new javax.swing.JButton();
        BotonDelete = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        creferencia = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        FechaInicial = new com.toedter.calendar.JDateChooser();
        FechaFinal = new com.toedter.calendar.JDateChooser();
        Refrescar = new javax.swing.JButton();
        BotonSeleccionar = new javax.swing.JButton();
        vencimientos = new com.toedter.calendar.JDateChooser();
        venceanterior = new com.toedter.calendar.JDateChooser();
        BotonImprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaprincipal = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        labelMetric1 = new org.edisoncor.gui.label.LabelMetric();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setText("N° Préstamo");

        salirfactura2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        salirfactura2.setText("Salir");
        salirfactura2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirfactura2ActionPerformed(evt);
            }
        });

        nroprestamo2.setEditable(false);
        nroprestamo2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel20.setText("Fecha");

        fechaprestamo2.setBackground(new java.awt.Color(255, 255, 255));
        fechaprestamo2.setForeground(new java.awt.Color(255, 255, 255));
        fechaprestamo2.setEnabled(false);

        grabarfactura2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        grabarfactura2.setText("Grabar Factura");
        grabarfactura2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabarfactura2ActionPerformed(evt);
            }
        });

        jLabel21.setText("Nombre Cliente");

        nombrecliente2.setEditable(false);
        nombrecliente2.setForeground(new java.awt.Color(255, 0, 0));

        jLabel22.setText("Moneda");

        moneda2.setEditable(false);
        moneda2.setForeground(new java.awt.Color(255, 0, 0));

        lblgastos.setText("Interés Ordinario");

        jLabel24.setText("Nº Factura");

        observacion2.setColumns(20);
        observacion2.setRows(5);
        jScrollPane3.setViewportView(observacion2);

        jButton7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton7.setText("Imprimir Factura");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel25.setText("Observaciones al píe del Comprobante");

        interesordinario.setEditable(false);
        interesordinario.setForeground(new java.awt.Color(255, 0, 0));
        interesordinario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(grabarfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(salirfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(lblgastos)
                            .addComponent(jLabel24))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombrecliente2)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(interesordinario, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                            .addComponent(moneda2))
                                        .addGap(0, 168, Short.MAX_VALUE)))
                                .addGap(88, 88, 88))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fechaprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(186, 186, 186))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(nroprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaprestamo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombrecliente2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneda2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(interesordinario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblgastos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(nrofactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salirfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grabarfactura2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        javax.swing.GroupLayout genfacturaotrosLayout = new javax.swing.GroupLayout(genfacturaotros.getContentPane());
        genfacturaotros.getContentPane().setLayout(genfacturaotrosLayout);
        genfacturaotrosLayout.setHorizontalGroup(
            genfacturaotrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        genfacturaotrosLayout.setVerticalGroup(
            genfacturaotrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel34.setText("N° Préstamo");

        numeroprestamo.setEditable(false);
        numeroprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel35.setText("Títular Préstamo");

        nombretitularprestamo.setEditable(false);

        importeprestamo.setEditable(false);
        importeprestamo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        importeprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel36.setText("Importe");

        fechaemisionprestamo.setBackground(new java.awt.Color(255, 255, 255));
        fechaemisionprestamo.setForeground(new java.awt.Color(255, 255, 255));
        fechaemisionprestamo.setEnabled(false);

        jLabel37.setText("Fecha Emisión");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombretitularprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importeprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numeroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaemisionprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(idmovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(numeroprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idmovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaemisionprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(nombretitularprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(importeprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel38.setText("Origen");

        origencustodia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        origencustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                origencustodiaActionPerformed(evt);
            }
        });

        BuscarCustodiaOrigen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaOrigenActionPerformed(evt);
            }
        });

        BuscarCustodiaDestino.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaDestinoActionPerformed(evt);
            }
        });

        nombrecustodiaorigen.setEditable(false);

        destinocustodia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        destinocustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinocustodiaActionPerformed(evt);
            }
        });

        fechaprocesocustodia.setBackground(new java.awt.Color(255, 255, 255));
        fechaprocesocustodia.setForeground(new java.awt.Color(255, 255, 255));

        nombrecustodiadestino.setEditable(false);

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCustodia.setText("Grabar");
        GrabarCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCustodiaActionPerformed(evt);
            }
        });

        BorrarCustodia.setText("Borrar");
        BorrarCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarCustodiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrabarCustodia, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(BorrarCustodia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(GrabarCustodia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BorrarCustodia)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel39.setText("Destino");

        jLabel40.setText("Fecha");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(65, 65, 65))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(origencustodia)
                                .addGap(18, 18, 18)
                                .addComponent(BuscarCustodiaOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel39)
                        .addGap(69, 69, 69)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(fechaprocesocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(nombrecustodiaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombrecustodiadestino, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(destinocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarCustodiaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel38)
                                .addComponent(jLabel39))
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(destinocustodia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(origencustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fechaprocesocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombrecustodiaorigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombrecustodiadestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablacustodia.setModel(modelocustodia        );
        jScrollPane5.setViewportView(tablacustodia);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton8.setText("Salir de la Opción Custodia");
        jButton8.setToolTipText("Salir");
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout custodiaPagareLayout = new javax.swing.GroupLayout(custodiaPagare.getContentPane());
        custodiaPagare.getContentPane().setLayout(custodiaPagareLayout);
        custodiaPagareLayout.setHorizontalGroup(
            custodiaPagareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        custodiaPagareLayout.setVerticalGroup(
            custodiaPagareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(custodiaPagareLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        BCustodia.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCustodia.setTitle("null");

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocustodia.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocustodia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocustodiaActionPerformed(evt);
            }
        });

        JTBuscarCustodia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        JTBuscarCustodia.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.jTBuscarClientes.text")); // NOI18N
        JTBuscarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTBuscarCustodiaActionPerformed(evt);
            }
        });
        JTBuscarCustodia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JTBuscarCustodiaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(combocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(JTBuscarCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combocustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTBuscarCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablaentidadcustodia.setModel(modeloentidadcustodia        );
        tablaentidadcustodia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaentidadcustodiaMouseClicked(evt);
            }
        });
        tablaentidadcustodia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaentidadcustodiaKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tablaentidadcustodia);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCustodia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCustodia.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCustodiaActionPerformed(evt);
            }
        });

        SalirCustodia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCustodia.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCustodia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCustodia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCustodiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCustodia, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCustodia)
                    .addComponent(SalirCustodia))
                .addContainerGap())
        );

        javax.swing.GroupLayout BCustodiaLayout = new javax.swing.GroupLayout(BCustodia.getContentPane());
        BCustodia.getContentPane().setLayout(BCustodiaLayout);
        BCustodiaLayout.setHorizontalGroup(
            BCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCustodiaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BCustodiaLayout.setVerticalGroup(
            BCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BCustodiaLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel41.setText("Origen");

        origenc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        origenc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                origencActionPerformed(evt);
            }
        });

        BuscarCustodiaO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaOActionPerformed(evt);
            }
        });

        BuscarCustodiaD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarCustodiaD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarCustodiaDActionPerformed(evt);
            }
        });

        nombrecustodiao.setEditable(false);

        destinoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        destinoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinocActionPerformed(evt);
            }
        });

        fechaprocesoc.setBackground(new java.awt.Color(255, 255, 255));
        fechaprocesoc.setForeground(new java.awt.Color(255, 255, 255));

        nombrecustodiad.setEditable(false);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        GrabarCus.setText("Grabar");
        GrabarCus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        GrabarCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrabarCusActionPerformed(evt);
            }
        });

        BorrarC.setText("Borrar");
        BorrarC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BorrarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GrabarCus, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(BorrarC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(GrabarCus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BorrarC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel42.setText("Destino");

        jLabel43.setText("Fecha");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombrecustodiao, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(jLabel41)
                            .addGap(65, 65, 65))
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(origenc)
                            .addGap(18, 18, 18)
                            .addComponent(BuscarCustodiaO, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addGap(117, 117, 117))
                    .addComponent(nombrecustodiad, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(destinoc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarCustodiaD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(fechaprocesoc, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(destinoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrecustodiad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(origenc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaprocesoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuscarCustodiaO, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombrecustodiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablacusto.setModel(modelocusto    );
        jScrollPane8.setViewportView(tablacusto);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        Salircus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Salircus.setText("Salir de la Opción Custodia");
        Salircus.setToolTipText("Salir");
        Salircus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salircus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalircusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ExportarCustodiaLayout = new javax.swing.GroupLayout(ExportarCustodia.getContentPane());
        ExportarCustodia.getContentPane().setLayout(ExportarCustodiaLayout);
        ExportarCustodiaLayout.setHorizontalGroup(
            ExportarCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Salircus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ExportarCustodiaLayout.createSequentialGroup()
                .addGroup(ExportarCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        ExportarCustodiaLayout.setVerticalGroup(
            ExportarCustodiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExportarCustodiaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Salircus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Tipo");

        tipoprestamo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tipoprestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoprestamoActionPerformed(evt);
            }
        });

        BuscarPrestamo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarPrestamoActionPerformed(evt);
            }
        });

        nombreprestamo.setEditable(false);
        nombreprestamo.setEnabled(false);

        jLabel45.setText("Fecha");

        fecha.setBackground(new java.awt.Color(255, 255, 255));
        fecha.setForeground(new java.awt.Color(255, 255, 255));
        fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechaKeyPressed(evt);
            }
        });

        jLabel46.setText("1ra. Cuota");

        primervencimiento.setBackground(new java.awt.Color(255, 255, 255));
        primervencimiento.setForeground(new java.awt.Color(255, 255, 255));
        primervencimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                primervencimientoKeyPressed(evt);
            }
        });

        jLabel47.setText("Socio");

        socio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        socio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                socioActionPerformed(evt);
            }
        });
        socio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                socioKeyPressed(evt);
            }
        });

        BuscarSocio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarSocioActionPerformed(evt);
            }
        });

        nombresocio.setEditable(false);
        nombresocio.setEnabled(false);

        jLabel48.setText("Capital");

        plazo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        plazo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        jLabel49.setText("Tasa");

        tasa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        tasa.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tasaActionPerformed(evt);
            }
        });
        tasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tasaKeyPressed(evt);
            }
        });

        jLabel5.setText("Cuota");

        importecuota.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importecuota.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importecuota.addFocusListener(new java.awt.event.FocusAdapter() {
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

        jLabel4.setText("Asesor");

        asesor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        asesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asesorActionPerformed(evt);
            }
        });
        asesor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                asesorKeyPressed(evt);
            }
        });

        BuscarAsesor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BuscarAsesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarAsesorActionPerformed(evt);
            }
        });

        nombreasesor.setEditable(false);
        nombreasesor.setEnabled(false);

        jLabel50.setText("Plazo");

        importe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        importe.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        importe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importeActionPerformed(evt);
            }
        });
        importe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                importeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel47))
                                .addGap(33, 33, 33)))
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(socio, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(primervencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(nombresocio, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                    .addGap(65, 65, 65)
                                    .addComponent(asesor)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BuscarAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(nombreasesor, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(37, 37, 37)
                        .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(41, 41, 41)
                        .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(jLabel48))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(73, 73, 73))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(BuscarPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(tipoprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(primervencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47)
                                    .addComponent(socio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BuscarSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombresocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel46)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(importe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(plazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importecuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BuscarAsesor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(asesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreasesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablacupon.setModel(modelocupon        );
        jScrollPane9.setViewportView(tablacupon);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BotonGrabar.setText("Grabar");
        BotonGrabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGrabarActionPerformed(evt);
            }
        });

        BotonSalir.setText("Salir");
        BotonSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(BotonGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(BotonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonGrabar)
                    .addComponent(BotonSalir))
                .addContainerGap())
        );

        jPanel22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 255));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Préstamo N°");

        numero.setEditable(false);
        numero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        numero.setForeground(new java.awt.Color(0, 0, 255));
        numero.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        numero.setBorder(null);
        numero.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        numero.setEnabled(false);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 255));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("Total");

        totalamortizacion.setBorder(null);
        totalamortizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        totalamortizacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalamortizacion.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        totalamortizacion.setEnabled(false);

        totalinteres.setBorder(null);
        totalinteres.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        totalinteres.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalinteres.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        totalinteres.setEnabled(false);

        totalprestamo.setBorder(null);
        totalprestamo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        totalprestamo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalprestamo.setDisabledTextColor(new java.awt.Color(51, 51, 255));
        totalprestamo.setEnabled(false);

        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("Amortización");

        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setText("Intereses");

        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setText("Total Préstamo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(totalamortizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(totalinteres, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel1)
                .addGap(62, 62, 62)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalamortizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalinteres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51)
                    .addComponent(totalprestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout detalle_prestamoLayout = new javax.swing.GroupLayout(detalle_prestamo.getContentPane());
        detalle_prestamo.getContentPane().setLayout(detalle_prestamoLayout);
        detalle_prestamoLayout.setHorizontalGroup(
            detalle_prestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        detalle_prestamoLayout.setVerticalGroup(
            detalle_prestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detalle_prestamoLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BCliente.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BCliente.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código", "Buscar por RUC" }));
        combocliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboclienteActionPerformed(evt);
            }
        });

        jTBuscarCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarCliente.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.jTBuscarClientes.text")); // NOI18N
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

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combocliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane6.setViewportView(tablacliente);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarCli.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarCliActionPerformed(evt);
            }
        });

        SalirCli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirCli.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.SalirCliente.text")); // NOI18N
        SalirCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirCliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarCli)
                    .addComponent(SalirCli))
                .addContainerGap())
        );

        javax.swing.GroupLayout BClienteLayout = new javax.swing.GroupLayout(BCliente.getContentPane());
        BCliente.getContentPane().setLayout(BClienteLayout);
        BClienteLayout.setHorizontalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BClienteLayout.setVerticalGroup(
            BClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BClienteLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        BPrestamo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BPrestamo.setTitle("Tipo de Préstamo");

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combocomprobante.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combocomprobante.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combocomprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combocomprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocomprobanteActionPerformed(evt);
            }
        });

        jTBuscarComprobante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(combocomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane10.setViewportView(tablacomprobante);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarComprobante.setText("Aceptar");
        AceptarComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarComprobanteActionPerformed(evt);
            }
        });

        SalirComprobante.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirComprobante.setText("Salir");
        SalirComprobante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SalirComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirComprobanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarComprobante)
                    .addComponent(SalirComprobante))
                .addContainerGap())
        );

        javax.swing.GroupLayout BPrestamoLayout = new javax.swing.GroupLayout(BPrestamo.getContentPane());
        BPrestamo.getContentPane().setLayout(BPrestamoLayout);
        BPrestamoLayout.setHorizontalGroup(
            BPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPrestamoLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BPrestamoLayout.setVerticalGroup(
            BPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BPrestamoLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jTBuscarVendedor.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.jTBuscarClientes.text")); // NOI18N
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
        jScrollPane11.setViewportView(tablavendedor);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AceptarVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AceptarVendedor.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.Aceptarcliente.text")); // NOI18N
        AceptarVendedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AceptarVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarVendedorActionPerformed(evt);
            }
        });

        SalirVendedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        SalirVendedor.setText(org.openide.util.NbBundle.getMessage(prestamos_gimenez_calvo.class, "ventas.SalirCliente.text")); // NOI18N
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
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BVendedorLayout.setVerticalGroup(
            BVendedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BVendedorLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        BotonEditar.setBackground(new java.awt.Color(255, 255, 255));
        BotonEditar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonEditar.setText("Editar Registro");
        BotonEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEditarActionPerformed(evt);
            }
        });

        BotonAgregar.setBackground(new java.awt.Color(255, 255, 255));
        BotonAgregar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonAgregar.setText(" Agregar Registro");
        BotonAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAgregarActionPerformed(evt);
            }
        });

        BotonDelete.setBackground(new java.awt.Color(255, 255, 255));
        BotonDelete.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonDelete.setText("Anular Préstamo");
        BotonDelete.setToolTipText("");
        BotonDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonDeleteActionPerformed(evt);
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

        creferencia.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar entre los Días"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FechaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(FechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
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

        BotonSeleccionar.setText("Custodia");
        BotonSeleccionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonSeleccionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BotonSeleccionarMouseClicked(evt);
            }
        });
        BotonSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSeleccionarActionPerformed(evt);
            }
        });

        vencimientos.setEnabled(false);

        venceanterior.setEnabled(false);

        BotonImprimir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        BotonImprimir.setText("Imprimir");
        BotonImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotonImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonSeleccionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Refrescar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(BotonImprimir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(creferencia, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BotonEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BotonAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BotonDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BotonAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BotonDelete)
                .addGap(7, 7, 7)
                .addComponent(BotonImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Salir)
                .addGap(69, 69, 69)
                .addComponent(creferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(venceanterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vencimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Refrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(BotonSeleccionar)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        tablaprincipal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jScrollPane1FocusGained(evt);
            }
        });

        tablaprincipal.setModel(modelo);
        tablaprincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaprincipal.setSelectionBackground(new java.awt.Color(51, 204, 255));
        tablaprincipal.setSelectionForeground(new java.awt.Color(0, 0, 255));
        tablaprincipal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablaprincipalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablaprincipalFocusLost(evt);
            }
        });
        tablaprincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaprincipalMouseClicked(evt);
            }
        });
        tablaprincipal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablaprincipalPropertyChange(evt);
            }
        });
        tablaprincipal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaprincipalKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaprincipal);

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        labelMetric1.setText("Préstamos");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del Socio", "N° de Operación" }));
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
                .addGap(21, 21, 21)
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(395, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        indiceColumnaTabla = 3;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 1;
                        break;//por codigo
                    case 2:
                        indiceColumnaTabla = 12;
                        break;//por Estado
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(tablaprincipal.getModel());
        tablaprincipal.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed

    private void BotonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAgregarActionPerformed
        limpiar();
        this.AbrirVentana();
    }//GEN-LAST:event_BotonAgregarActionPerformed

    private void AbrirVentana() {
        detalle_prestamo.setModal(true);
        detalle_prestamo.setSize(496, 540);
        //Establecemos un título para el jDialog
        detalle_prestamo.setTitle("Detalle de Préstamo");
        detalle_prestamo.setLocationRelativeTo(null);
        detalle_prestamo.setVisible(true);
    }
    private void tablaprincipalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaprincipalKeyPressed
        int nFila = this.tablaprincipal.getSelectedRow();
        this.creferencia.setText(this.tablaprincipal.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalKeyPressed

    private void tablaprincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaprincipalMouseClicked
        int nFila = this.tablaprincipal.getSelectedRow();
        this.creferencia.setText(this.tablaprincipal.getValueAt(nFila, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalMouseClicked

    private void BotonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEditarActionPerformed
        this.limpiar();
        int nFila = tablaprincipal.getSelectedRow();
        creferencia.setText(tablaprincipal.getValueAt(nFila, 0).toString());
        this.numeroprestamo.setText(tablaprincipal.getValueAt(nFila, 1).toString());
        if (nFila < 0) {
            JOptionPane.showMessageDialog(null, "Debe Seleccionar un Registro");
            this.tablaprincipal.requestFocus();
            return;
        }
        cuenta_clienteDAO saldoDAO = new cuenta_clienteDAO();
        cuenta_clientes saldo = null;
        try {
            saldo = saldoDAO.SaldoMovimiento(creferencia.getText());
            if (saldo.getDocumento() != null) {
                JOptionPane.showMessageDialog(null, "La Operación ya no puede Modificarse");
                return;
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            prestamoDAO prDAO = new prestamoDAO();
            prestamo pr = null;
            try {
                pr = prDAO.buscarMicroCredito(Integer.valueOf(this.numeroprestamo.getText()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
            }
            if (pr != null) {
                this.fecha.setDate(pr.getFecha());
                this.primervencimiento.setDate(pr.getPrimer_vence());
                this.tipoprestamo.setText(String.valueOf(pr.getTipo().getCodigo()));
                this.nombreprestamo.setText(pr.getTipo().getNombre());
                this.socio.setText(String.valueOf(pr.getSocio().getCodigo()));
                this.nombresocio.setText(pr.getSocio().getNombre());
                this.importe.setText(formatea.format(pr.getImporte()));
                this.plazo.setText(formatoSinpunto.format(pr.getPlazo()));
                this.importecuota.setText(formatea.format(pr.getMonto_cuota()));
                this.tasa.setText(formatea.format(pr.getTasa()));
                this.totalprestamo.setText(formatea.format(pr.getTotalprestamo()));
                this.asesor.setText(String.valueOf(pr.getAsesor().getCodigo()));
                this.nombreasesor.setText(pr.getAsesor().getNombre());
                detalle_prestamoDAO detDAO = new detalle_prestamoDAO();
                try {
                    for (detalle_prestamo det : detDAO.MostrarDetalle(Integer.valueOf(this.numeroprestamo.getText()))) {
                        String Detalle[] = {formatoSinpunto.format(det.getNrocuota()),
                            formatoSinpunto.format(det.getDias()),
                            formatoFecha.format(det.getEmision()),
                            formatoFecha.format(det.getVence()),
                            formatea.format(det.getAmortiza()),
                            formatea.format(det.getMinteres()),
                            formatea.format(det.getMonto())};
                        modelocupon.addRow(Detalle);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
                this.sumar();
                this.AbrirVentana();
                tipoprestamo.requestFocus();

            }
        }

    }//GEN-LAST:event_BotonEditarActionPerformed

    private void tablaprincipalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaprincipalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalFocusGained

    private void jScrollPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jScrollPane1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1FocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if (ControlGrabado.REGISTRO_GRABADO == "SI") {
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

    private void BotonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonDeleteActionPerformed
        int nFila = this.tablaprincipal.getSelectedRow();
        String cNumero = this.tablaprincipal.getValueAt(nFila, 1).toString();
        String cReferencia = this.tablaprincipal.getValueAt(nFila, 0).toString();
        if (Config.cNivelUsuario.equals("1")) {
            if (!this.creferencia.getText().isEmpty()) {
                BDConexion BD = new BDConexion();
                BD.borrarRegistro("detalle_prestamo", "nprestamo=" + cNumero);
                BD.BorrarDetalles("prestamos", "numero=" + cNumero);
                BD.borrarRegistro("deducciones", "nprestamo=" + cNumero);

                this.cargarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Debe Seleccionar una Celda para Eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar un Préstamo");
            return;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonDeleteActionPerformed

    private void tablaprincipalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablaprincipalFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalFocusLost

    private void RefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefrescarActionPerformed
        this.cargarTabla();
        BotonSeleccionar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_RefrescarActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        con = new Conexion();
        stm = con.conectar();
        String cSqlControl2 = "SELECT * FROM facturaprestamos WHERE opcion = 2 AND idprestamo='" + cReferencia + "'";
        System.out.println(cSqlControl2);
        try {
            stm.executeQuery(cSqlControl2);
            while (results.next()) {
                cReferencia = results.getString("idfactura");
            }
            stm.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (cReferencia.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La Factura aún no fue Generada");
            return;
        }
        Object[] opciones = {"   Si   ", "   No   "};
        int opcion = JOptionPane.showOptionDialog(null, "Desea Imprimir la Factura? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            ImprimirFacturaGastos GenerarReporte2 = new ImprimirFacturaGastos();
            Thread HiloReporte2 = new Thread(GenerarReporte2);
            HiloReporte2.start();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void grabarfactura2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grabarfactura2ActionPerformed

        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar la Factura por Gastos? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GrabarDetalleOtros GrabarGastos = new GrabarDetalleOtros();
            Thread HiloGrabarGastos = new Thread(GrabarGastos);
            HiloGrabarGastos.start();
            JOptionPane.showMessageDialog(null, "Factura Generada con Éxito");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_grabarfactura2ActionPerformed

    private void salirfactura2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirfactura2ActionPerformed
        this.genfacturaotros.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_salirfactura2ActionPerformed


    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        custodiaPagare.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void TituloCu() {
        modelocusto.addColumn("id");
        modelocusto.addColumn("N° Préstamo");
        modelocusto.addColumn("Fecha");
        modelocusto.addColumn("Cliente ");
        modelocusto.addColumn("Importe");

        int[] anchos = {1, 100, 100, 180, 120};
        for (int i = 0; i < modelocusto.getColumnCount(); i++) {
            tablacusto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        ((DefaultTableCellRenderer) tablacusto.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacusto.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        this.tablacusto.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacusto.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablacusto.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacusto.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.tablacusto.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablacusto.setFont(font);
    }

    private void TituloCustodia() {
        modelocustodia.addColumn("id");
        modelocustodia.addColumn("Fecha");
        modelocustodia.addColumn("Origen ");
        modelocustodia.addColumn("Destino");
        modelocustodia.addColumn("Registro");
        modelocustodia.addColumn("Hora");

        int[] anchos = {1, 100, 120, 120, 120, 120};
        for (int i = 0; i < modelocustodia.getColumnCount(); i++) {
            tablacustodia.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablacustodia.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablacustodia.getTableHeader().setFont(new Font("Arial Black", 1, 11));

        this.tablacustodia.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacustodia.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablacustodia.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablacustodia.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        Font font = new Font("Arial", Font.BOLD, 10);
        this.tablacustodia.setFont(font);
    }

    private void limpiaCustoda() {
        this.origencustodia.setText("0");
        this.destinocustodia.setText("0");
        this.nombrecustodiaorigen.setText("");
        this.nombrecustodiadestino.setText("");
        this.fechaprocesocustodia.setCalendar(c2);

        this.origenc.setText("0");
        this.destinoc.setText("0");
        this.nombrecustodiao.setText("");
        this.nombrecustodiad.setText("");
        this.fechaprocesocustodia.setCalendar(c2);
        this.fechaprocesoc.setCalendar(c2);
        this.idmovimiento.setText("");
    }

    private void combocustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocustodiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combocustodiaActionPerformed

    private void JTBuscarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JTBuscarCustodiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JTBuscarCustodiaActionPerformed

    private void JTBuscarCustodiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JTBuscarCustodiaKeyPressed
        this.JTBuscarCustodia.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (JTBuscarCustodia.getText()).toUpperCase();
                JTBuscarCustodia.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (combocustodia.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtrocustodia(indiceColumnaTabla);
            }
        });
        trsfiltrocustodia = new TableRowSorter(tablaentidadcustodia.getModel());
        tablaentidadcustodia.setRowSorter(trsfiltrocustodia);
        // TODO add your handling code here:
    }//GEN-LAST:event_JTBuscarCustodiaKeyPressed

    public void filtrocustodia(int nNumeroColumna) {
        trsfiltrocustodia.setRowFilter(RowFilter.regexFilter(this.JTBuscarCustodia.getText(), nNumeroColumna));
    }


    private void tablaentidadcustodiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaentidadcustodiaMouseClicked
        this.AceptarCustodia.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaentidadcustodiaMouseClicked

    private void tablaentidadcustodiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaentidadcustodiaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarCustodia.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablaentidadcustodiaKeyPressed

    private void AceptarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarCustodiaActionPerformed
        int nFila = this.tablaentidadcustodia.getSelectedRow();

        if (tipoconsulta == 1) {
            this.origencustodia.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiaorigen.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        } else if (tipoconsulta == 2) {
            this.destinocustodia.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiadestino.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        } else if (tipoconsulta == 3) {
            this.origenc.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiao.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        } else {
            this.destinoc.setText(this.tablaentidadcustodia.getValueAt(nFila, 0).toString());
            this.nombrecustodiad.setText(this.tablaentidadcustodia.getValueAt(nFila, 1).toString());
        }
        tipoconsulta = 0;
        this.BCustodia.setVisible(false);
        this.JTBuscarCustodia.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCustodiaActionPerformed

    private void SalirCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCustodiaActionPerformed
        this.BCustodia.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCustodiaActionPerformed

    private void BuscarCustodiaOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaOrigenActionPerformed
        tipoconsulta = 1;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.origencustodia.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiaorigen.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            destinocustodia.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaOrigenActionPerformed

    private void BuscarCustodiaDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaDestinoActionPerformed
        tipoconsulta = 2;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.destinocustodia.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiadestino.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            fechaprocesocustodia.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaDestinoActionPerformed

    private void origencustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_origencustodiaActionPerformed
        this.BuscarCustodiaOrigen.doClick();
// TODO add your handling code here:
    }//GEN-LAST:event_origencustodiaActionPerformed

    private void destinocustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinocustodiaActionPerformed
        this.BuscarCustodiaDestino.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_destinocustodiaActionPerformed

    private void GrabarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCustodiaActionPerformed
        if (Integer.valueOf(origencustodia.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            origencustodia.requestFocus();
            return;
        }
        if (Integer.valueOf(destinocustodia.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            destinocustodia.requestFocus();
            return;
        }

        historico_custodia ot = new historico_custodia();
        historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();
        Date dFechaHoy = ODate.de_java_a_sql(fechaprocesocustodia.getDate());
        ot.setOrigencustodia(Integer.valueOf(origencustodia.getText()));
        ot.setDestinocustodia(Integer.valueOf(destinocustodia.getText()));
        ot.setFechaproceso(dFechaHoy);
        ot.setIdmovimiento(idmovimiento.getText().toString());
        ot.setIdnumero(Integer.valueOf(numeroprestamo.getText()));
        //SE CAPTURA N° DE IP DE LA PC
        InetAddress addr;
        String ip = null;
        try {
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (UnknownHostException ex) {
            Exceptions.printStackTrace(ex);
        }
        ot.setPc(ip);;

        try {
            otcontrolDAO.InsertarCustodia(ot);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        RefrescarGrillaCustodia GrillaP = new RefrescarGrillaCustodia();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.origencustodia.setText("0");
        this.destinocustodia.setText("0");
        this.nombrecustodiaorigen.setText("");
        this.nombrecustodiadestino.setText("");
        this.origencustodia.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCustodiaActionPerformed

    private void BorrarCustodiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarCustodiaActionPerformed

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = this.tablacustodia.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nItem = Integer.valueOf(this.tablacustodia.getValueAt(nFila, 0).toString());

            historico_custodia ot = null;
            historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();

            try {
                otcontrolDAO.EliminarCustodia(nItem);
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar ");
            return;
        }

        RefrescarGrillaCustodia GrillaP = new RefrescarGrillaCustodia();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.origencustodia.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarCustodiaActionPerformed


    private void BotonSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSeleccionarActionPerformed
        //Obtener el número de filas seleccionadas 
        tablaprincipal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            //El método valueChange se debe sobreescribir obligatoriamente. 
            //Se ejecuta automáticamente cada vez que se hace una nueva selección. 
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Obtener el número de filas seleccionadas 
                int cuentaFilasSeleccionadas = tablaprincipal.getSelectedRowCount();
                System.out.println("Hay seleccionadas: " + cuentaFilasSeleccionadas + " filas");

                if (cuentaFilasSeleccionadas == 1) {
                    //Sólo hay una fila seleccionada 
                    int indiceFilaSeleccionada = tablaprincipal.getSelectedRow();
                    System.out.println("Fila seleccionada: " + indiceFilaSeleccionada);
                } else {
                    //Hay varias filas seleccionadas 
                    int[] indicesfilasSeleccionadas = tablaprincipal.getSelectedRows();
                    System.out.println("Filas seleccionadas: ");
                    for (int indice : indicesfilasSeleccionadas) {
                        System.out.print(indice + " ");
                        String cNumero = tablaprincipal.getValueAt(indice, 1).toString();
                        String cReferencia = tablaprincipal.getValueAt(indice, 0).toString();
                        System.out.print("Numero " + cNumero);
                        System.out.print("Creferencia " + cReferencia);

                    }
                    System.out.println();
                }
            }

        }
        );

        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSeleccionarActionPerformed


    private void tablaprincipalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablaprincipalPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaprincipalPropertyChange

    private void BotonSeleccionarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BotonSeleccionarMouseClicked
        Object[] opciones = {"   Si   ", "   No   "};
        Object[] fila = new Object[5];
        this.limpiaCustoda();
        int ret = JOptionPane.showOptionDialog(null, "Desea Exportar los Préstamos Seleccionados? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            int cantidadRegistro = modelocusto.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocusto.removeRow(0);
            }

            int[] indicesfilasSeleccionadas = tablaprincipal.getSelectedRows();
            System.out.println("Filas seleccionadas: ");
            for (int indice : indicesfilasSeleccionadas) {
                String cReferencia = tablaprincipal.getValueAt(indice, 0).toString();
                System.out.print(indice + " ");
                String cNumero = tablaprincipal.getValueAt(indice, 1).toString();
                String cFecha = tablaprincipal.getValueAt(indice, 2).toString();
                String cCliente = tablaprincipal.getValueAt(indice, 3).toString();
                String cMontoPrestamo = tablaprincipal.getValueAt(indice, 7).toString();
                fila[0] = cReferencia;
                fila[1] = cNumero;
                fila[2] = cFecha;
                fila[3] = cCliente;
                fila[4] = cMontoPrestamo;
                modelocusto.addRow(fila);
            }
            this.ExportarCustodia.setSize(645, 518);
            this.ExportarCustodia.setModal(true);
            ExportarCustodia.setTitle("Enviar a Custodia");
            ExportarCustodia.setLocationRelativeTo(null);
            ExportarCustodia.setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonSeleccionarMouseClicked

    private void origencActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_origencActionPerformed
        this.BuscarCustodiaO.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_origencActionPerformed

    private void BuscarCustodiaOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaOActionPerformed
        tipoconsulta = 3;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.origenc.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiao.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            destinoc.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaOActionPerformed

    private void BuscarCustodiaDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarCustodiaDActionPerformed
        tipoconsulta = 4;
        custodiaDAO cmDAO = new custodiaDAO();
        custodia cm = null;
        try {
            cm = cmDAO.buscarId(Integer.valueOf(this.destinoc.getText()));
            if (cm.getCodigo() == 0) {
                BCustodia.setModal(true);
                BCustodia.setSize(500, 575);
                BCustodia.setLocationRelativeTo(null);
                BCustodia.setVisible(true);
                BCustodia.setTitle("Buscar Entidad");
                BCustodia.setModal(false);
            } else {
                nombrecustodiad.setText(cm.getNombre());
                //Establecemos un título para el jDialog
            }
            fechaprocesoc.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarCustodiaDActionPerformed

    private void destinocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinocActionPerformed
        this.BuscarCustodiaD.doClick();        // TODO add your handling code here:
    }//GEN-LAST:event_destinocActionPerformed

    private void GrabarCusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrabarCusActionPerformed
        if (Integer.valueOf(origenc.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            origenc.requestFocus();
            return;
        }
        if (Integer.valueOf(destinoc.getText()) == 0) {
            JOptionPane.showMessageDialog(null, "Verifique los Datos a Ingresar");
            destinoc.requestFocus();
            return;
        }
        Date dFechaHoy = ODate.de_java_a_sql(fechaprocesoc.getDate());

        int totalRow = modelocusto.getRowCount();
        totalRow -= 1;
        InetAddress addr;
        String ip = null;

        for (int i = 0; i <= (totalRow); i++) {
            historico_custodia ot = new historico_custodia();
            historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();
            ot.setOrigencustodia(Integer.valueOf(origenc.getText()));
            ot.setDestinocustodia(Integer.valueOf(destinoc.getText()));
            ot.setFechaproceso(dFechaHoy);
            ot.setIdmovimiento(String.valueOf(modelocusto.getValueAt(i, 0)));
            ot.setIdnumero(Integer.valueOf(modelocusto.getValueAt(i, 1).toString()));
            //SE CAPTURA N° DE IP DE LA PC
            try {
                addr = InetAddress.getLocalHost();
                ip = addr.getHostAddress();
            } catch (UnknownHostException ex) {
                Exceptions.printStackTrace(ex);
            }
            ot.setPc(ip);;

            try {
                otcontrolDAO.InsertarCustodia(ot);
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        JOptionPane.showMessageDialog(null, "Proceso Terminado");

        this.origenc.setText("0");
        this.destinoc.setText("0");
        this.nombrecustodiao.setText("");
        this.nombrecustodiad.setText("");
        this.origenc.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_GrabarCusActionPerformed

    private void BorrarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarCActionPerformed

        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            int nFila = this.tablacusto.getSelectedRow();
            if (nFila < 0) {
                JOptionPane.showMessageDialog(null,
                        "Debe seleccionar una fila de la tabla");
                return;
            }
            //  if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            int nItem = Integer.valueOf(this.tablacusto.getValueAt(nFila, 0).toString());

            historico_custodia ot = null;
            historico_custodiasDAO otcontrolDAO = new historico_custodiasDAO();

            try {
                otcontrolDAO.EliminarCustodia(nItem);
                // TODO add your handling code here:
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No esta Autorizado para Eliminar ");
            return;
        }

        RefrescarGrillaCustodia GrillaP = new RefrescarGrillaCustodia();
        Thread Hilo2 = new Thread(GrillaP);
        Hilo2.start();
        this.origenc.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BorrarCActionPerformed

    private void SalircusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalircusActionPerformed
        ExportarCustodia.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalircusActionPerformed

    private void tipoprestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoprestamoActionPerformed
        BuscarPrestamo.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoprestamoActionPerformed

    private void BuscarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarPrestamoActionPerformed
        comprobanteDAO cmDAO = new comprobanteDAO();
        comprobante cm = null;
        try {
            cm = cmDAO.buscarIdxtipo(Integer.valueOf(this.tipoprestamo.getText()), 3);
            if (cm.getCodigo() == 0) {
                BPrestamo.setModal(true);
                BPrestamo.setSize(500, 575);
                BPrestamo.setLocationRelativeTo(null);
                BPrestamo.setVisible(true);
                BPrestamo.setTitle("Buscar Préstamo");
                BPrestamo.setModal(false);
            } else {
                nombreprestamo.setText(cm.getNombre());
                tasa.setText(formatea.format(cm.getTasainteres()));
                //Establecemos un título para el jDialog
            }
            fecha.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarPrestamoActionPerformed

    private void fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.primervencimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tipoprestamo.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaKeyPressed

    private void primervencimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_primervencimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.socio.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.fecha.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_primervencimientoKeyPressed

    private void socioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_socioActionPerformed

        clienteDAO clDAO = new clienteDAO();
        cliente cl = null;
        try {
            cl = clDAO.buscarId(Integer.valueOf(this.socio.getText()));
            if (cl.getCodigo() != 0) {
                nombresocio.setText(cl.getNombre());
                plazo.requestFocus();
            } else {
                this.BuscarSocio.doClick();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_socioActionPerformed

    private void socioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_socioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.primervencimiento.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_socioKeyPressed

    private void BuscarSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarSocioActionPerformed
        BCliente.setModal(true);
        BCliente.setSize(500, 575);
        BCliente.setLocationRelativeTo(null);
        BCliente.setTitle("Buscar Socio");
        BCliente.setVisible(true);
        BCliente.setModal(false);
        importe.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarSocioActionPerformed

    private void plazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plazoActionPerformed
    }//GEN-LAST:event_plazoActionPerformed

    private void plazoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_plazoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.importecuota.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.tasa.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoKeyPressed

    private void tasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tasaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.plazo.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importe.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaKeyPressed

    private void importecuotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importecuotaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.asesor.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.plazo.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importecuotaKeyPressed

    private void asesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asesorActionPerformed
        this.BuscarAsesor.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorActionPerformed

    private void asesorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_asesorKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.BotonGrabar.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.importecuota.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_asesorKeyPressed

    private void BuscarAsesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarAsesorActionPerformed
        vendedorDAO veDAO = new vendedorDAO();
        vendedor vn = null;
        try {
            vn = veDAO.buscarId(Integer.valueOf(this.asesor.getText()));
            if (vn.getCodigo() == 0) {
                BVendedor.setModal(true);
                BVendedor.setSize(500, 575);
                BVendedor.setLocationRelativeTo(null);
                BVendedor.setVisible(true);
                BVendedor.setTitle("Buscar Asesor");
                BVendedor.setModal(false);
            } else {
                nombreasesor.setText(vn.getNombre());
                //Establecemos un título para el jDialog
            }
            BotonGrabar.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarAsesorActionPerformed

    private void importeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_importeActionPerformed

    private void importeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_importeKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tasa.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.socio.requestFocus();
        }   // TO        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_importeKeyPressed

    private void BotonGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGrabarActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Guardar esta Operación? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {

            if (Integer.valueOf(numeroprestamo.getText()) == 0) {
                UUID id = new UUID();
                referencia = UUID.crearUUID();
                creferencia.setText(referencia.substring(1, 25));
            }

            Date FechaProceso = ODate.de_java_a_sql(fecha.getDate());
            Date FechaVence = ODate.de_java_a_sql(primervencimiento.getDate());

            sucursalDAO sucDAO = new sucursalDAO();
            sucursal suc = null;
            clienteDAO cliDAO = new clienteDAO();
            cliente cli = null;
            comprobanteDAO coDAO = new comprobanteDAO();
            comprobante com = null;
            monedaDAO mnDAO = new monedaDAO();
            moneda mn = null;
            vendedorDAO veDAO = new vendedorDAO();
            vendedor ve = null;

            prestamo pr = new prestamo();
            prestamoDAO GrabarDAO = new prestamoDAO();

            try {
                suc = sucDAO.buscarId(1);
                cli = cliDAO.buscarId(Integer.valueOf(socio.getText()));
                com = coDAO.buscarId(Integer.valueOf(tipoprestamo.getText()));
                mn = mnDAO.buscarId(1);
                ve = veDAO.buscarId(Integer.valueOf(asesor.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            pr.setIdprestamos(creferencia.getText());
            pr.setFecha(FechaProceso);
            pr.setPrimer_vence(FechaVence);
            pr.setMoneda(mn);
            pr.setSucursal(suc);
            pr.setSocio(cli);
            pr.setPlazo(Integer.valueOf(this.plazo.getText()));

            String cImporte = this.importe.getText();
            cImporte = cImporte.replace(".", "").replace(",", ".");
            pr.setImporte(new BigDecimal(cImporte));

            String cImporteCuota = this.importecuota.getText();
            cImporteCuota = cImporteCuota.replace(".", "").replace(",", ".");
            pr.setMonto_cuota(new BigDecimal(cImporteCuota));

            String cInteres = this.totalinteres.getText();
            cInteres = cInteres.replace(".", "").replace(",", ".");
            pr.setInteres(new BigDecimal(cInteres));

            String cTotal = this.totalprestamo.getText();
            cTotal = cTotal.replace(".", "").replace(",", ".");
            pr.setTotalprestamo(new BigDecimal(cTotal));

            pr.setTipo(com);

            String cTasa = this.tasa.getText();
            cTasa = cTasa.replace(".", "").replace(",", ".");
            pr.setTasa(new BigDecimal(cTasa));

            pr.setAsesor(ve);
            pr.setCodusuario(Integer.valueOf(Config.CodUsuario));
            pr.setDestino(1);

            String cEntregar = this.importe.getText();
            cEntregar = cEntregar.replace(".", "").replace(",", ".");
            pr.setEntregarneto(new BigDecimal(cEntregar));

            String detacuota = "[";
            String detalle = "[";
            Calendar calendar = Calendar.getInstance(); //Instanciamos Calendar
            calendar.setTime(primervencimiento.getDate()); // Capturamos en el setTime el valor de la fecha ingresada

            String iddoc = null;

            detacuota = "[";
            detalle = "[";
            int ncantidad = 0;
            int Items = tablacupon.getRowCount();
            for (int i = 0; i < Items; i++) {
                try {
                    iddoc = UUID.crearUUID();
                    iddoc = iddoc.substring(1, 25);
                    ncantidad++;
                    String cNrocuota = (tablacupon.getValueAt(i, 0).toString());
                    String cNroDias = (tablacupon.getValueAt(i, 1).toString());
                    Date dVence = ODate.de_java_a_sql(formatoFecha.parse(tablacupon.getValueAt(i, 3).toString()));
                    String cAmortiza = (tablacupon.getValueAt(i, 4).toString());
                    cAmortiza = cAmortiza.replace(".", "").replace(",", ".");
                    cInteres = (tablacupon.getValueAt(i, 5).toString());
                    cInteres = cInteres.replace(".", "").replace(",", ".");
                    String cCuota = (tablacupon.getValueAt(i, 6).toString());
                    cCuota = cCuota.replace(".", "").replace(",", ".");

                    String lineacuota = "{iddocumento : '" + iddoc + "',"
                            + "creferencia : '" + creferencia.getText() + "',"
                            + "documento : " + this.numeroprestamo.getText() + ","
                            + "fecha : " + FechaProceso + ","
                            + "vencimiento : " + dVence + ","
                            + "cliente : " + socio.getText() + ","
                            + "sucursal: " + 1 + ","
                            + "moneda : " + 1 + ","
                            + "comprobante : " + com.getCodigo() + ","
                            + "vendedor : " + asesor.getText() + ","
                            + "idedificio : " + 1 + ","
                            + "importe : " + cImporteCuota + ","
                            + "amortiza: " + cAmortiza + ","
                            + "minteres: " + cInteres + ","
                            + "numerocuota : " + this.plazo.getText() + ","
                            + "cuota : " + ncantidad + ","
                            + "saldo : " + cImporteCuota
                            + "},";

                    detacuota += lineacuota;

                    String lineadetalle = "{nprestamo : '" + numeroprestamo.getText() + "',"
                            + "nrocuota : '" + ncantidad + "',"
                            + "capital : " + cImporteCuota + ","
                            + "emision : " + FechaProceso + ","
                            + "vence : " + dVence + ","
                            + "dias : " + 30 + ","
                            + "amortiza : " + cAmortiza + ","
                            + "minteres : " + cInteres + ","
                            + "monto : " + cImporteCuota
                            + "},";

                    detalle += lineadetalle;
                } catch (ParseException ex) {
                    Exceptions.printStackTrace(ex);
                }

            }
            if (!detacuota.equals("[")) {
                detacuota = detacuota.substring(0, detacuota.length() - 1);
            }
            detacuota += "]";

            if (!detalle.equals("[")) {
                detalle = detalle.substring(0, detalle.length() - 1);
            }
            detalle += "]";

            cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();

            if (Integer.valueOf(numeroprestamo.getText()) == 0) {
                try {
                    GrabarDAO.InsertarPrestamoGCalvo(pr, detalle);
                    ctaDAO.guardarCuentaPrestamo(detacuota);
                    ctaDAO.ActualizarNumero(creferencia.getText());
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                try {
                    pr.setIdprestamos(creferencia.getText());
                    pr.setNumero(Integer.valueOf(numeroprestamo.getText()));
                    ctaDAO.borrarDetalleCuenta(creferencia.getText());
                    GrabarDAO.ActualizarPrestamoGCalvo(pr, detalle);
                    ctaDAO.guardarCuentaPrestamo(detacuota);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            this.BotonSalir.doClick();
            this.Refrescar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BotonGrabarActionPerformed

    private void BotonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSalirActionPerformed
        this.detalle_prestamo.setVisible(false);
    }//GEN-LAST:event_BotonSalirActionPerformed

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

    public void filtrocli(int nNumeroColumna) {
        trsfiltrocli.setRowFilter(RowFilter.regexFilter(this.jTBuscarCliente.getText(), nNumeroColumna));
    }

    public void filtrocomprobante(int nNumeroColumna) {
        trsfiltrocomprobante.setRowFilter(RowFilter.regexFilter(this.jTBuscarComprobante.getText(), nNumeroColumna));
    }

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
        this.socio.setText(this.tablacliente.getValueAt(nFila, 0).toString());
        this.nombresocio.setText(this.tablacliente.getValueAt(nFila, 1).toString());
        this.BCliente.setVisible(false);
        this.jTBuscarCliente.setText("");
        this.importe.requestFocus();

        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarCliActionPerformed

    private void SalirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirCliActionPerformed
        this.BCliente.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirCliActionPerformed

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
        this.tipoprestamo.setText(this.tablacomprobante.getValueAt(nFila, 0).toString());
        this.nombreprestamo.setText(this.tablacomprobante.getValueAt(nFila, 1).toString());
        this.tasa.setText(this.tablacomprobante.getValueAt(nFila, 2).toString());

        this.BPrestamo.setVisible(false);
        this.jTBuscarComprobante.setText("");
        this.fecha.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarComprobanteActionPerformed

    private void SalirComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirComprobanteActionPerformed
        this.BPrestamo.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirComprobanteActionPerformed

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

    public void filtrovendedor(int nNumeroColumna) {
        trsfiltrovendedor.setRowFilter(RowFilter.regexFilter(this.jTBuscarVendedor.getText(), nNumeroColumna));
    }


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
        this.asesor.setText(this.tablavendedor.getValueAt(nFila, 0).toString());
        this.nombreasesor.setText(this.tablavendedor.getValueAt(nFila, 1).toString());

        this.BVendedor.setVisible(false);
        this.jTBuscarVendedor.setText("");
        this.BotonGrabar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarVendedorActionPerformed

    private void SalirVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirVendedorActionPerformed
        this.BVendedor.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirVendedorActionPerformed

    private void importecuotaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_importecuotaFocusLost
        this.generarcuotas();
        // TODO add your handling code here:
    }//GEN-LAST:event_importecuotaFocusLost

    private void BotonImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonImprimirActionPerformed
        try {
            this.ImprimirDocumentos("pagare_gimenez_calvo.jasper");
            this.ImprimirDocumentos("liquidacion_prestamos_gc.jasper");
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_BotonImprimirActionPerformed

    private void plazoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_plazoFocusLost
        String cCapital = String.valueOf(this.importe.getText());
        cCapital = cCapital.replace(".", "").replace(",", ".");
        double Monto = Double.parseDouble(String.valueOf(cCapital));

        String cTasa = this.tasa.getText();
        cTasa = cTasa.replace(".", "").replace(",", ".");

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
        double Cuota_F = 0.00;
        Cuota_F = Math.round(Monto / A);
        this.importecuota.setText(formatea.format(Cuota_F));
        // TODO add your handling code here:
    }//GEN-LAST:event_plazoFocusLost

    private void tasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tasaActionPerformed

    private void importecuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importecuotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_importecuotaActionPerformed

    //El método valueChange se debe sobreescribir obligatoriamente. 
    //Se ejecuta automáticamente cada vez que se hace una nueva selección. 
    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(jTextField1.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("REF");
        modelo.addColumn("N° Operacion");
        modelo.addColumn("Fecha");
        modelo.addColumn("Denominación Cliente");
        modelo.addColumn("Moneda");
        modelo.addColumn("Capital");
        modelo.addColumn("Interés");
        modelo.addColumn("Total Préstamo");
        modelo.addColumn("Total Desembolso");
        modelo.addColumn("Tasa");
        modelo.addColumn("Tipo Préstamo");
        modelo.addColumn("Nombre Asesor");
        modelo.addColumn("Estado");
        modelo.addColumn("Cuenta");
        modelo.addColumn("IVA S/Interés");
        modelo.addColumn("Gastos");
        modelo.addColumn("Tipo");
        modelo.addColumn("IdFactura");
        modelo.addColumn("Comisiones");
        modelo.addColumn("Serv. Déb. Automático");
        modelo.addColumn("Importe Cuota");
        modelo.addColumn("Socio");

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 

        DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer();
        AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER); // aqui defines donde alinear 

        ((DefaultTableCellRenderer) tablaprincipal.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablaprincipal.getTableHeader().setFont(new Font("Arial Black", 1, 9));

        this.tablaprincipal.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaprincipal.getColumnModel().getColumn(0).setMinWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        this.tablaprincipal.getColumnModel().getColumn(16).setMaxWidth(0);
        this.tablaprincipal.getColumnModel().getColumn(16).setMinWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);

        this.tablaprincipal.getColumnModel().getColumn(17).setMaxWidth(0);
        this.tablaprincipal.getColumnModel().getColumn(17).setMinWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);

        this.tablaprincipal.getColumnModel().getColumn(21).setMaxWidth(0);
        this.tablaprincipal.getColumnModel().getColumn(21).setMinWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
        this.tablaprincipal.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);

        int[] anchos = {3, 120, 90, 350, 100, 100, 100, 100, 100, 90, 100, 100, 100, 90, 100, 100, 90, 100, 100, 100, 100, 1};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.tablaprincipal.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        this.tablaprincipal.getColumnModel().getColumn(1).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(2).setCellRenderer(AlinearCentro);
        this.tablaprincipal.getColumnModel().getColumn(5).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(6).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(7).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(8).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(9).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(14).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(15).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(18).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(19).setCellRenderer(TablaRenderer);
        this.tablaprincipal.getColumnModel().getColumn(20).setCellRenderer(TablaRenderer);

    }

    public void cargarTabla() {
        //Uso la Clase SimpleDateFormat para darle formato al campo fecha
        dFechaInicio = ODate.de_java_a_sql(this.FechaInicial.getDate());
        dFechaFinal = ODate.de_java_a_sql(this.FechaFinal.getDate());
        cSql = "SELECT prestamos.idprestamos,prestamos.numero,prestamos.fecha,prestamos.socio,prestamos.tasa,";
        cSql = cSql + "clientes.nombre AS nombrecliente,vendedores.nombre AS nombreasesor,prestamos.totalprestamo,monto_cuota,";
        cSql = cSql + "importe,monedas.etiqueta AS nombremoneda,comprobantes.nomalias AS nombreprestamo, ";
        cSql = cSql + "prestamos.interes,prestamos.monto_entregar,prestamos.estado,prestamos.ivainteres,prestamos.comision_deudor,prestamos.tipo,prestamos.idfactura,";
        cSql = cSql + "prestamos.gastos_escritura,prestamos.segurovida ";
        cSql = cSql + "FROM prestamos ";
        cSql = cSql + "LEFT JOIN comprobantes ";
        cSql = cSql + "ON comprobantes.codigo=prestamos.tipo ";
        cSql = cSql + "LEFT JOIN vendedores ";
        cSql = cSql + "ON vendedores.codigo=prestamos.asesor ";
        cSql = cSql + "LEFT JOIN monedas ";
        cSql = cSql + "ON monedas.codigo=prestamos.moneda ";
        cSql = cSql + "LEFT JOIN clientes ";
        cSql = cSql + "ON clientes.codigo=prestamos.socio ";
        cSql = cSql + "WHERE prestamos.fecha BETWEEN " + "'" + dFechaInicio + "'" + " AND " + "'" + dFechaFinal + "'";
        cSql = cSql + " ORDER BY prestamos.fecha";

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

                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[22]; // Cantidad de columnas en la tabla
                fila[0] = results.getString("idprestamos");
                fila[1] = results.getString("numero");
                fila[2] = formatoFecha.format(results.getDate("fecha"));
                fila[3] = results.getString("nombrecliente");
                fila[4] = results.getString("nombremoneda");
                fila[5] = formatea.format(results.getDouble("importe"));
                fila[6] = formatea.format(results.getDouble("interes"));
                fila[7] = formatea.format(results.getDouble("totalprestamo"));
                fila[8] = formatea.format(results.getDouble("monto_entregar"));
                fila[9] = formatea.format(results.getDouble("tasa"));
                fila[10] = results.getString("nombreprestamo");
                fila[11] = results.getString("nombreasesor");
                fila[12] = results.getString("estado");
                fila[13] = results.getString("socio");
                fila[14] = formatea.format(results.getDouble("ivainteres"));
                fila[15] = formatea.format(results.getDouble("comision_deudor"));
                fila[16] = results.getString("tipo");
                fila[17] = results.getString("idfactura");
                fila[18] = formatea.format(results.getDouble("gastos_escritura"));
                fila[19] = formatea.format(results.getDouble("segurovida"));
                fila[20] = formatea.format(results.getDouble("monto_cuota"));
                fila[21] = results.getInt("socio");
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.tablaprincipal.setRowSorter(new TableRowSorter(modelo));
            int cantFilas = this.tablaprincipal.getRowCount();
            if (cantFilas > 0) {
                this.BotonEditar.setEnabled(true);
                this.BotonDelete.setEnabled(true);
                this.BotonImprimir.setEnabled(true);
                this.BotonSeleccionar.setEnabled(true);
            } else {
                this.BotonEditar.setEnabled(false);
                this.BotonImprimir.setEnabled(false);
                this.BotonDelete.setEnabled(false);
                this.BotonSeleccionar.setEnabled(false);
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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new prestamos_gimenez_calvo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarCli;
    private javax.swing.JButton AceptarComprobante;
    private javax.swing.JButton AceptarCustodia;
    private javax.swing.JButton AceptarVendedor;
    private javax.swing.JDialog BCliente;
    private javax.swing.JDialog BCustodia;
    private javax.swing.JDialog BPrestamo;
    private javax.swing.JDialog BVendedor;
    private javax.swing.JButton BorrarC;
    private javax.swing.JButton BorrarCustodia;
    private javax.swing.JButton BotonAgregar;
    private javax.swing.JButton BotonDelete;
    private javax.swing.JButton BotonEditar;
    private javax.swing.JButton BotonGrabar;
    private javax.swing.JButton BotonImprimir;
    private javax.swing.JButton BotonSalir;
    private javax.swing.JButton BotonSeleccionar;
    private javax.swing.JButton BuscarAsesor;
    private javax.swing.JButton BuscarCustodiaD;
    private javax.swing.JButton BuscarCustodiaDestino;
    private javax.swing.JButton BuscarCustodiaO;
    private javax.swing.JButton BuscarCustodiaOrigen;
    private javax.swing.JButton BuscarPrestamo;
    private javax.swing.JButton BuscarSocio;
    private javax.swing.JDialog ExportarCustodia;
    private com.toedter.calendar.JDateChooser FechaFinal;
    private com.toedter.calendar.JDateChooser FechaInicial;
    private javax.swing.JButton GrabarCus;
    private javax.swing.JButton GrabarCustodia;
    private javax.swing.JTextField JTBuscarCustodia;
    private javax.swing.JButton Refrescar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirCli;
    private javax.swing.JButton SalirComprobante;
    private javax.swing.JButton SalirCustodia;
    private javax.swing.JButton SalirVendedor;
    private javax.swing.JButton Salircus;
    private javax.swing.JTextField asesor;
    private javax.swing.JComboBox combocliente;
    private javax.swing.JComboBox combocomprobante;
    private javax.swing.JComboBox combocustodia;
    private javax.swing.JComboBox combovendedor;
    private javax.swing.JTextField creferencia;
    private javax.swing.JDialog custodiaPagare;
    private javax.swing.JTextField destinoc;
    private javax.swing.JTextField destinocustodia;
    private javax.swing.JDialog detalle_prestamo;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechaemisionprestamo;
    private com.toedter.calendar.JDateChooser fechaprestamo2;
    private com.toedter.calendar.JDateChooser fechaprocesoc;
    private com.toedter.calendar.JDateChooser fechaprocesocustodia;
    private javax.swing.JDialog genfacturaotros;
    private javax.swing.JButton grabarfactura2;
    private javax.swing.JTextField idmovimiento;
    private javax.swing.JFormattedTextField importe;
    private javax.swing.JFormattedTextField importecuota;
    private javax.swing.JFormattedTextField importeprestamo;
    private javax.swing.JFormattedTextField interesordinario;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTBuscarCliente;
    private javax.swing.JTextField jTBuscarComprobante;
    private javax.swing.JTextField jTBuscarVendedor;
    private javax.swing.JTextField jTextField1;
    private org.edisoncor.gui.label.LabelMetric labelMetric1;
    private javax.swing.JLabel lblgastos;
    private javax.swing.JTextField moneda2;
    private javax.swing.JTextField nombreasesor;
    private javax.swing.JTextField nombrecliente2;
    private javax.swing.JTextField nombrecustodiad;
    private javax.swing.JTextField nombrecustodiadestino;
    private javax.swing.JTextField nombrecustodiao;
    private javax.swing.JTextField nombrecustodiaorigen;
    private javax.swing.JTextField nombreprestamo;
    private javax.swing.JTextField nombresocio;
    private javax.swing.JTextField nombretitularprestamo;
    private javax.swing.JTextField nrofactura2;
    private javax.swing.JTextField nroprestamo2;
    private javax.swing.JTextField numero;
    private javax.swing.JTextField numeroprestamo;
    private javax.swing.JTextArea observacion2;
    private javax.swing.JTextField origenc;
    private javax.swing.JTextField origencustodia;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JFormattedTextField plazo;
    private com.toedter.calendar.JDateChooser primervencimiento;
    private javax.swing.JButton salirfactura2;
    private javax.swing.JTextField socio;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablacomprobante;
    private javax.swing.JTable tablacupon;
    private javax.swing.JTable tablacusto;
    private javax.swing.JTable tablacustodia;
    private javax.swing.JTable tablaentidadcustodia;
    private javax.swing.JTable tablaprincipal;
    private javax.swing.JTable tablavendedor;
    private javax.swing.JFormattedTextField tasa;
    private javax.swing.JTextField tipoprestamo;
    private javax.swing.JFormattedTextField totalamortizacion;
    private javax.swing.JFormattedTextField totalinteres;
    private javax.swing.JFormattedTextField totalprestamo;
    private com.toedter.calendar.JDateChooser venceanterior;
    private com.toedter.calendar.JDateChooser vencimientos;
    // End of variables declaration//GEN-END:variables

    private class GenerarOp extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            try {
                Map parameters = new HashMap();
                int nFila = tablaprincipal.getSelectedRow();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String num = tablaprincipal.getValueAt(nFila, 8).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Referencia", tablaprincipal.getValueAt(nFila, 0).toString());
                parameters.put("Letra", numero.Convertir(num, true, 1));
                JasperReport jr = null;
                //OTRAS EMPRESAS
                //  URL url = getClass().getClassLoader().getResource("Reports/orden_pago_prestamos.jasper");
                //PRECIOS BAJOS    
                URL url = getClass().getClassLoader().getResource("Reports/orden_pago_preciosbajos.jasper");
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

    private class GenerarLiquidacion extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreLiquidacion = config.getNombreliquidacion();
            String cNombreLiquidacionPh = config.getNombreliquidacionph();
            try {
                Map parameters = new HashMap();
                int nFila = tablaprincipal.getSelectedRow();
                int nTipo = Integer.valueOf(tablaprincipal.getValueAt(nFila, 16).toString());
                System.out.println("tipo " + nTipo);
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", tablaprincipal.getValueAt(nFila, 1).toString());

                JasperReport jr = null;
                //URL url = getClass().getClassLoader().getResource("Reports/pagares.jasper");

                //PARA A+B
                /*   if (nTipo == 8) {
                    URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacion.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                } else {
                    URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacionPh.toString());
                    jr = (JasperReport) JRLoader.loadObject(url);
                }*/
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreLiquidacion.toString());
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

    private class ImprimirFacturaCapital extends Thread {

        public void run() {
            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte

                String num = cTotalImprimir;
                System.out.println(num);
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //  URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
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

    private class GenFacturaGastos extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            String cSql = "SELECT factura+1  AS nRegistro FROM sucursales";
            try {
                results = stm.executeQuery(cSql);
                if (results.next()) {
                    nrofactura2.setText(results.getString("nRegistro"));
                }
                stm.close();
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private class GrabarDetalleOtros extends Thread {

        public void run() {
            //SE CAPTURA LOS DATOS DE LA CABECERA
            //Dando formato a los datos tipo Fecha

            Date FechaProceso = ODate.de_java_a_sql(fechaprestamo2.getDate());
            Date PrimerVence = ODate.de_java_a_sql(fechaprestamo2.getDate());
            //Obteniendo Datos de los Combodatos
            String cmoneda = Config.cMonedaDefecto;
            //SE CAPTURAN LOS DATOS NUMERICOS
            //     String cImporte = this.importe.getText();
            String cFactura = nrofactura2.getText();
            String cGravadas10 = interesordinario.getText();
            cGravadas10 = cGravadas10.replace(".", "");
            cGravadas10 = cGravadas10.replace(",", ".");

            con = new Conexion();
            stm = con.conectar();
            String cCotizacion = "1";
            String cCodGasto = null;
            ResultSet results = null;
            Connection conn = null;

            try {
                conn = stm.getConnection();
                conn.setAutoCommit(false);

                String cSqlCab = "INSERT INTO cabecera_ventas (creferencia,fecha,factura,vencimiento,cliente,sucursal,moneda,giraduria,";
                cSqlCab += "comprobante,cotizacion,vendedor,caja,exentas,gravadas10,gravadas5,totalneto,observacion,idprestamo,idusuario)";
                cSqlCab += "VALUES ('" + cReferencia + "','" + FechaProceso + "','" + cFactura + "','" + PrimerVence + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "1" + "','";
                cSqlCab += "2" + "','" + cCotizacion + "','" + "1" + "','" + "1" + "','" + "0" + "','" + cGravadas10 + "','" + "0" + "','" + cGravadas10 + "','" + observacion2.getText().toString() + "','" + nroprestamo2.getText() + "','" + Config.CodUsuario + "')";
                try {
                    stm.executeUpdate(cSqlCab);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cMonto = interesordinario.getText();
                cMonto = cMonto.replace(".", "");
                cMonto = cMonto.replace(",", ".");

                String cCantidad = "1";

                String cPrecio = cMonto;

                String cIva = "10";
                double montoiva = Double.valueOf(cMonto);
                montoiva = Math.round(montoiva / 11);
                String cImpiva = String.valueOf(montoiva);
                //cImpiva = cImpiva.replace(".", "");
                //cImpiva = cImpiva.replace(",", ".");

                cCodGasto = Config.cInteresPrestamo;
                String cSqlDetalle = "INSERT INTO detalle_ventas(dreferencia, codprod, cantidad, prcosto, precio, monto, impiva, porcentaje)";
                cSqlDetalle += "VALUES ('" + cReferencia + "','" + cCodGasto + "','" + cCantidad + "','" + '0' + "','" + cPrecio + "','" + cMonto + "','" + cImpiva + "','" + cIva + "')";

                try {
                    stm.executeUpdate(cSqlDetalle);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cSqlCuentas = "INSERT INTO cuenta_clientes (iddocumento,creferencia,documento,fecha,vencimiento,";
                cSqlCuentas += "numerocuota,cuota,cliente,sucursal,moneda,comprobante,importe,saldo,totalconcedido,capital,minteres,amortiza,inversionista,tasaoperativa)";
                cSqlCuentas += "values('" + cReferencia + "','" + cReferencia + "','" + nroprestamo2.getText() + "','" + FechaProceso + "','" + PrimerVence + "','";
                cSqlCuentas += "1" + "','" + "1" + "','" + cCliente + "','" + "1" + "','" + cmoneda + "','" + "2" + "','";
                cSqlCuentas += cMonto + "','" + cMonto + "','" + cMonto + "','" + "0" + "','" + "0" + "','" + "0" + "','" + "0" + "','" + "0" + "')";

                try {
                    stm.executeUpdate(cSqlCuentas);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }

                String cSqlFactura = "UPDATE sucursales SET factura= '" + cFactura + "'";

                try {
                    stm.executeUpdate(cSqlFactura);
                } catch (SQLException ex) {
                    Exceptions.printStackTrace(ex);
                }
                String cSqlControlFactura2 = "INSERT INTO facturaprestamos (idprestamo,idfactura,opcion) VALUES('" + idPrestamo + "','" + cReferencia + "','" + '2' + "')";
                try {
                    stm.executeUpdate(cSqlControlFactura2);
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

    private class ImprimirFacturaGastos extends Thread {

        public void run() {

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                int nFila = tablaprincipal.getSelectedRow();
                String num = tablaprincipal.getValueAt(nFila, 6).toString();
                num = num.replace(".", "");
                num = num.replace(",", ".");
                numero_a_letras numero = new numero_a_letras();

                parameters.put("Letra", numero.Convertir(num, true, 1));

                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("cReferencia", cReferencia);
                JasperReport jr = null;
                //  URL url = getClass().getClassLoader().getResource("Reports/factura.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + Config.cNombreFactura.toString());
                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class ImprimirSolicitud extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombreSolicitud = config.getNombresolicitud();

            int nFila = tablaprincipal.getSelectedRow();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            cReferencia = tablaprincipal.getValueAt(nFila, 0).toString();
            int nCliente = Integer.valueOf(tablaprincipal.getValueAt(nFila, 21).toString());

            try {
                Map parameters = new HashMap();
                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                parameters.put("cReferencia", cReferencia);
                parameters.put("nCliente", nCliente);
                JasperReport jr = null;
                //Ficha de Cliente de PUERTO SEGURO
                //   URL url = getClass().getClassLoader().getResource("Reports/fichacliente.jasper");
                // URL url = getClass().getClassLoader().getResource("Reports/fichacliente_crediseguro.jasper");
                //             URL url = getClass().getClassLoader().getResource("Reports/fichapreciosbajos.jasper");
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombreSolicitud.toString());

                jr = (JasperReport) JRLoader.loadObject(url);
                JasperPrint masterPrint = null;
                //Se le incluye el parametro con el nombre parameters porque asi lo definimos
                masterPrint = JasperFillManager.fillReport(jr, parameters, stm.getConnection());
                JasperViewer ventana = new JasperViewer(masterPrint, false);
                ventana.setTitle("Vista Previa");
                ventana.setVisible(true);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    private class PagareCuotas extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            stm2 = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombrePagare = config.getNombrepagarecuota();
            int nFila = tablaprincipal.getSelectedRow();
            //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
            //en el reporte
            int nNumero = Integer.valueOf(tablaprincipal.getValueAt(nFila, 1).toString());
            String cMoneda = tablaprincipal.getValueAt(nFila, 4).toString();
            String nNumeroPrestamo = String.valueOf(nNumero);
            String cSql = "SELECT nrocuota,monto FROM detalle_prestamo WHERE nprestamo=" + nNumero + " ORDER BY nrocuota ";
            try {
                results = stm.executeQuery(cSql);
                while (results.next()) {
                    double nTotalNeto = results.getDouble("monto");
                    String nCuota = results.getString("nrocuota");
                    try {
                        Map parameters = new HashMap();
                        //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                        //en el reporte
                        String num = String.valueOf(nTotalNeto);
                        numero_a_letras numero = new numero_a_letras();
                        if (cMoneda.equals("GS.")) {
                            parameters.put("Letra", numero.Convertir(num, true, 1));
                        } else {
                            parameters.put("Letra", numero.Convertir(num, true, 2));
                        }
                        parameters.put("nNumeroPrestamo", nNumeroPrestamo);
                        parameters.put("nCuota", nCuota);
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

    private class GenerarPagare extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();
            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cNombrePagare = config.getNombrepagare();
            try {
                Map parameters = new HashMap();
                int nFila = tablaprincipal.getSelectedRow();
                String cMoneda = tablaprincipal.getValueAt(nFila, 4).toString();
                String cNumeroPrestamo = tablaprincipal.getValueAt(nFila, 1).toString();

                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = tablaprincipal.getValueAt(nFila, 7).toString();

                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = tablaprincipal.getValueAt(nFila, 6).toString();
                cpInteres = cpInteres.replace(".", "");
                System.out.println("Interes " + cpInteres);

                BigDecimal n1 = new BigDecimal(cpImporte);
//                BigDecimal n2 = new BigDecimal(cpInteres);
                //              n1 = n1.add(n2); // Se suma los valores 
                //double nTotal=n1;

                String num = String.valueOf(n1);
                numero_a_letras numero = new numero_a_letras();
                if (cMoneda.equals("GS.")) {
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                } else {
                    parameters.put("Letra", numero.Convertir(num, true, 2));
                }
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", cNumeroPrestamo.toString());
                System.out.println(num);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cNombrePagare.toString());
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

    private class GenerarContrato extends Thread {

        public void run() {
            con = new Conexion();
            stm = con.conectar();

            configuracionDAO configDAO = new configuracionDAO();
            configuracion config = configDAO.consultar();
            String cContrato1 = config.getNombrecontrato1();

            try {
                Map parameters = new HashMap();
                int nFila = tablaprincipal.getSelectedRow();
                String cMoneda = tablaprincipal.getValueAt(nFila, 4).toString();
                String cNumeroPrestamo = tablaprincipal.getValueAt(nFila, 1).toString();

                //Ahora que hay parametros le enviamos uno con el mismo nombre del que creamos
                //en el reporte
                String cpImporte = tablaprincipal.getValueAt(nFila, 7).toString();
                String cMonto_Entregar = tablaprincipal.getValueAt(nFila, 7).toString();
                cpImporte = cpImporte.replace(".", "");
                System.out.println("Capital " + cpImporte);
                String cpInteres = tablaprincipal.getValueAt(nFila, 6).toString();
                cpInteres = cpInteres.replace(".", "");
                System.out.println("Interes " + cpInteres);

                BigDecimal n1 = new BigDecimal(cpImporte);
//                BigDecimal n2 = new BigDecimal(cpInteres);
                //              n1 = n1.add(n2); // Se suma los valores 
                //double nTotal=n1;

                String num = String.valueOf(n1);
                numero_a_letras numero = new numero_a_letras();
                if (cMoneda.equals("GS.")) {
                    parameters.put("Letra", numero.Convertir(num, true, 1));
                } else {
                    parameters.put("Letra", numero.Convertir(num, true, 2));
                }
                parameters.put("cNombreEmpresa", Config.cNombreEmpresa);
                parameters.put("nNumeroPrestamo", cNumeroPrestamo.toString());
                parameters.put("cMonto_Entregar", cMonto_Entregar.toString());
                System.out.println(num);

                JasperReport jr = null;
                URL url = getClass().getClassLoader().getResource("Reports/" + cContrato1.toString());
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

    private class GrillaCustodia extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modeloentidadcustodia.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modeloentidadcustodia.removeRow(0);
            }
            custodiaDAO DAOCAJA = new custodiaDAO();
            try {
                for (custodia ca : DAOCAJA.todos()) {
                    String Datos[] = {String.valueOf(ca.getCodigo()), ca.getNombre()};
                    modeloentidadcustodia.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablaentidadcustodia.setRowSorter(new TableRowSorter(modeloentidadcustodia));
            int cantFilas = tablaentidadcustodia.getRowCount();
        }
    }

    private class RefrescarGrillaCustodia extends Thread {

        public void run() {
            int cantidadRegistro = modelocustodia.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocustodia.removeRow(0);
            }

            historico_custodiasDAO cuDAO = new historico_custodiasDAO();
            try {
                for (historico_custodia detcu : cuDAO.BuscarId(idmovimiento.getText())) {
                    String Detalle[] = {formatoSinpunto.format(detcu.getId()), formatoFecha.format(detcu.getFechaproceso()), detcu.getNombreorigen(), detcu.getNombredestino(), detcu.getPc(), detcu.getHora()};
                    modelocustodia.addRow(Detalle);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            int cantFilas = tablacustodia.getRowCount();
            if (cantFilas > 0) {
                BorrarCustodia.setEnabled(true);
            } else {
                BorrarCustodia.setEnabled(false);
            }
        }
    }

    private class GrillaSocio extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocliente.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocliente.removeRow(0);
            }

            clienteDAO DAOCLIE = new clienteDAO();
            try {
                for (cliente cli : DAOCLIE.todos()) {
                    String Datos[] = {String.valueOf(cli.getCodigo()), cli.getNombre(), cli.getDireccion(), cli.getRuc(), formatea.format(cli.getPlazocredito())};
                    modelocliente.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacliente.setRowSorter(new TableRowSorter(modelocliente));
            int cantFilas = tablacliente.getRowCount();
        }
    }

    private class GrillaPrestamo extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelocomprobante.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelocomprobante.removeRow(0);
            }
            comprobanteDAO DAOcm = new comprobanteDAO();
            try {
                for (comprobante com : DAOcm.todosxtipo(3)) {
                    String Datos[] = {String.valueOf(com.getCodigo()), com.getNombre(), formatea.format(com.getTasainteres())};
                    modelocomprobante.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablacomprobante.setRowSorter(new TableRowSorter(modelocomprobante));
            int cantFilas = tablacomprobante.getRowCount();
        }
    }

    private class GrillaAsesor extends Thread {

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

}

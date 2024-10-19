/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.generarQR;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import DAO.killconexionDAO;
import DAO.orden_trabajoDAO;
import DAO.prestamoDAO;
import Modelo.killconexion;
import Modelo.orden_trabajo;
import Modelo.pais;
import Modelo.producto;
import Modelo.proveedor;
import Modelo.rubro;
import Modelo.sucursal;
import Modelo.ubicacion;
import Modelo.vendedor;
import Modelo.venta;
import com.google.zxing.WriterException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.openide.util.Exceptions;

/**
 *
 * @author
 */
public class Portada extends javax.swing.JFrame {

    /**
     * Creates new form Portada
     */
    ImageIcon imagenfondo = new ImageIcon("src/Iconos/fondo.jpg");
    ObtenerFecha ODate = new ObtenerFecha();
    Calendar c2 = new GregorianCalendar();
    DecimalFormat formatosinpunto = new DecimalFormat("############");

    /**
     * ************** CAMPOS DEL CALENDARIO ************************
     */
    java.util.Date fechaActual = new java.util.Date();
    DefaultTableModel listaCal;
    String anhoInicial = "1900";

    /**
     * ************** CAMPOS DEL CALENDARIO ************************
     */
    public Portada() {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
        // this.setSize(700, 1380);
        this.EtiquetaQR.setText("");
        /*this.setIconImage(imagen);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ejemplo de codigo QR");
        this.getContentPane().add(EtiquetaQR);
        this.pack();        */
        this.fechaproceso.setCalendar(c2);
        this.fechaproceso.setVisible(false);
        this.setTitle(Config.cNombreEmpresa);
        //CALENDARIO ACTIVO
        //this.panelCalendario.setVisible(true);
        //this.calendario.setVisible(true);
        //CALENDARIO INACTIVO
        this.panelCalendario.setVisible(false);
        this.calendario.setVisible(false);

        this.marquesina.setText("");
        this.fondo.setIcon(imagenfondo);
        //fotoProducto.setImagen(imagenfondo.getImage());

        this.setIconImage(icon);
        jLUser.setText("Bienvenido " + Config.CodUsuario);

        this.setExtendedState(this.MAXIMIZED_BOTH);
        Conexion con = null;
        Statement st = null;
        con = new Conexion();
        st = con.conectar();
        ResultSet rs = null;
        Config configuracion = new Config();
        try {
            rs = st.executeQuery("select * from configuracion");
            if (rs.next()) {
                Config.cNombreEmpresa = rs.getString("empresa");
                Config.cRucEmpresa = rs.getString("ruc");
                Config.cTelefono = rs.getString("telefono");
                Config.cDireccion = rs.getString("direccion");
                Config.porcentajeiva = rs.getDouble("porcentajeiva");

                Config.cPagoExpress = rs.getString("cobropagoexpress");
                Config.cComanda = rs.getString("cobrocomanda");

                Config.limitedescuento = rs.getDouble("limitedescuento");
                Config.nIvaIncluido = rs.getInt("ivaincluido");
                Config.cNombreFactura = rs.getString("nombrefactura");
                Config.cNombrePagare = rs.getString("nombrepagare");
                Config.cMonedaDefecto = rs.getString("monedadefecto");
                Config.cCodCapitalPrestamo = rs.getString("codcapital");
                Config.cComprobanteDefecto = rs.getString("comprobantedefecto");

                Config.cCodColocacion = rs.getString("codcolocacion");
                Config.cInteresPrestamo = rs.getString("codinteres").toString();
                Config.cCodMora = rs.getString("codmora").toString();
                Config.cCodGastos = rs.getString("codgastos").toString();
                Config.cCodPunitorio = rs.getString("codpunitorio").toString();
                Config.cCodComision = rs.getString("comisiones");
                Config.cCodSeguroVida = rs.getString("segurovida");

                Config.nCobradorEnCasa = rs.getDouble("cobrador");
                Config.nArancelIva = rs.getDouble("aranceliva");
                Config.nArancelComision = rs.getDouble("arancelbanco");
                Config.nArancelSeguro = rs.getDouble("arancelseguro");
                Config.nArancelGastos = rs.getDouble("arancelgastos");
                Config.cIpServerCopia = rs.getString("ipservidor");
                Config.nMultiCaja = rs.getInt("multicaja");
                Config.nCentroCosto = rs.getInt("centro_costo");

                Config.usar_ean = rs.getInt("usar_ean");

                Config.ServidorCorreo = rs.getString("servidor_correo");;
                Config.PaseServidorCorreo = rs.getString("pase_servidor");;
                Config.PuertoCorreo = rs.getString("puerto");;
                Config.CorreoPrincipal = rs.getString("mail");;

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

        generarQR generaQR = new generarQR();
        BufferedImage imagen;
        try {
            imagen = generaQR.crearQR("LICENCIA CONCEDIDA A " + Config.cNombreEmpresa.trim() + " SOFTWARES & NEGOCIOS S.A. - LO MEJOR EN TECNOLOGIA - PARA SOPORTE TÉCNICO COMUNIQUESE AL (0983) 493 022 - GRACIAS POR SU PREFERENCIA ", 80, 80);
            ImageIcon icono = new ImageIcon(imagen);
            EtiquetaQR.setIcon(icono);
        } catch (WriterException ex) {
            Exceptions.printStackTrace(ex);
        }

        // if (Config.cNivelUsuario == "1") {
        // }
        ///Programamos la tarea para calcular los intereses diarios
        test.display();

        if (Integer.valueOf(Config.cMenuParametros) == 0) {
            this.parametros.setVisible(false);
        }
        if (Integer.valueOf(Config.cMenuMercaderias) == 0) {
            this.mercaderias.setVisible(false);
        }

        if (Integer.valueOf(Config.cMenuCompras) == 0) {
            this.compras.setVisible(false);
        }
        if (Integer.valueOf(Config.cMenuVentas) == 0) {
            this.ventas.setVisible(false);
        }
        if (Integer.valueOf(Config.cMenuOperaciones) == 0) {
            this.operaciones.setVisible(false);
        }
        if (Integer.valueOf(Config.cMenuFinanzas) == 0) {
            this.finanzas.setVisible(false);
        }

        if (Integer.valueOf(Config.cMenuRRHH) == 0) {
            this.rrhh.setVisible(false);
        }

        if (Integer.valueOf(Config.cMenuInmobiliaria) == 0) {
            this.inmobiliaria.setVisible(false);
        }

        if (Integer.valueOf(Config.cMenuAsociaciones) == 0) {
            this.asociaciones.setVisible(false);
        }

        if (Integer.valueOf(Config.cMenuHerramientas) == 0) {
            this.herramientas.setVisible(false);
        }

        if (Integer.valueOf(Config.cMenuContabilidad) == 0) {
            this.contabilidad.setVisible(false);
        }
        new HilosEspias(1, 1800000).start(); //CIERRE CONEXIONES DORMIDAS DEL SERVIDOR CADA 30 SEGUNDOS
        //    new HilosEspias(2, 3000).start(); //VERIFICA COBROS EN LA WEB Y ACTUALIZA CADA 30 SEGUNDOS
        //  new HilosEspias(3, 300000).start(); //BORRA TODAS LAS CUENTAS CANCELADAS

         new HilosEspias(6, 5400000).start(); //ACTUALIZANDO DATOS PARA BICSA
        /**
         * ************** INSTANCIAS DEL CALENDARIO ************************
         */
        month.setSelectedIndex(fechaActual.getMonth()); //seteamos el mes actual
        anho.setValue(fechaActual.getYear() + 1900); //seteamos el año actual 
        anhoInicial = anho.getValue().toString();
        /* OPERACIONES CON TABLA*/
        listaCal = (DefaultTableModel) calendario.getModel(); //COPIAR MODELO
        llenarCalendario(); //cargamos el calendario
        consultarCalendario();
        /**
         * ************** INSTANCIAS DEL CALENDARIO ************************
         */
    }

    /**
     * ********************** METODOS DEL CALENDARIO
     * ******************************
     */
    private void llenarCalendario() {
        listaCal.setRowCount(0);
        Calendar cal = Calendar.getInstance();
        int yearSeleccionado = Integer.valueOf(anho.getValue().toString());
        //cal.set(año, mes, dia);
        cal.set(yearSeleccionado, month.getSelectedIndex(), 1); //ponemos en el 1 del mes seleccionado
        int diaInicio = cal.get(Calendar.DAY_OF_WEEK), diaMaximo = cal.getActualMaximum(Calendar.DATE); //obtenemos la fecha maxima
        int maxPorFila = (diaInicio + diaMaximo) / 7; //obtenemos el apróximado de la cantidad de filas
        if (((diaInicio + diaMaximo) % 7) != 0) { //si la división no es entera
            maxPorFila++; //agregamos una fila - NO FUNCIONA CON FLOAT, NI IDEA PORQUÉ
        }
        int fecha = 1; //primer dia
        int f = 0, c = 0;
        for (int fila = 0; fila < maxPorFila; fila++) {//recorremos las filas
            Object[] fechas = new Object[7]; //creamos las fechas
            for (int columna = 0; columna < 7; columna++) { //recorremos las columnas
                fechas[columna] = " "; //columna vacia por defecto
                if ((fila != 0 || (fila == 0 && diaInicio <= columna + 1)) && (fecha < diaMaximo + 1)) {
                    fechas[columna] = fecha;//se establece la fecha
                    /**
                     *
                     */
                    //Comprobamos la fecha actual y el mes actual
                    String diaG = anho.getValue().toString() + "-" + (month.getSelectedIndex() + 1) + "-" + fecha;
                    String diaAc = (fechaActual.getYear() + 1900) + "-" + (fechaActual.getMonth() + 1) + "-" + (fechaActual.getDate());
                    //System.out.println("DiaG: "+diaG+" DiaA: "+diaAc);
                    try {
                        orden_trabajoDAO DAO = new orden_trabajoDAO(); //obtenemos trabajos del dia si existe
                        for (orden_trabajo ot : DAO.MostrarxFechaEntrega(Date.valueOf(diaG))) {
                            //si existe
                            fechas[columna] = "<html>"
                                    + "<div style=\""
                                    + "background: orange; "
                                    + "width: 51px; "
                                    + "height:40px; " //alto
                                    + "padding-top: 12px;"
                                    + "padding-left: 16px;"
                                    + "color: black; " + "\">"
                                    + "<b>" + fecha + "</b>"
                                    + "</div>"
                                    + "</html>";
                            break; //no necesitamos tantas vueltas para marcar
                        }
                    } catch (Exception e) {
                    }
                    if (diaAc.equals(diaG)) { //si es el dia de hoy, pintar de azul
                        f = fila;
                        c = columna;
                        fechas[columna] = "<html>"
                                + "<div style=\""
                                + "background: blue  ; "
                                + "width: 51px; "
                                + "height:40px; " //alto
                                + "padding-top: 12px;"
                                + "padding-left: 16px;"
                                + "color: white; " + "\">"
                                + "<b>" + fecha + "</b>"
                                + "</div>"
                                + "</html>";
                    }
                    /**
                     *
                     */
                    fecha++;
                }
                //alineamos:
                DefaultTableCellRenderer AlinearCentro = new DefaultTableCellRenderer(); //para alinear al centro
                AlinearCentro.setHorizontalAlignment(SwingConstants.CENTER); //par aalinear al centro
                calendario.getColumnModel().getColumn(columna).setCellRenderer(AlinearCentro);// alineamos al centro
            }
            listaCal.addRow(fechas);//agregamos las fechas en las filas correspondientes
            calendario.setRowHeight(fila, 59);
        }
        ((DefaultTableCellRenderer) calendario.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);//CENTRAR TITULO
        eventos.setText("");
        if (f > -1) { //cambiamos la selección solo si entro en la fecha actual
            calendario.changeSelection(f, c, false, false);
        }
    }

    private void adelantarMes() {
        int mesSeleccionado = month.getSelectedIndex();
        int yearSeleccionado = Integer.valueOf(anho.getValue().toString());
        if (mesSeleccionado == 11) { //Si está en Diciembre
            month.setSelectedIndex(0);  //Cambiamos a Enero
            anho.setValue(yearSeleccionado + 1); //Aumentamos el año
        } else { //si Está en Otro mes
            month.setSelectedIndex(mesSeleccionado + 1); //ponemos en mes-1
        }
        llenarCalendario();
    }

    private void retrocederMes() {
        int mesSeleccionado = month.getSelectedIndex();
        int yearSeleccionado = Integer.valueOf(anho.getValue().toString());
        if (mesSeleccionado == 0) { //Si está en Enero
            month.setSelectedIndex(11);  //Cambiamos a Diciembre
            anho.setValue(yearSeleccionado - 1); //Retrocedemos el año
        } else { //si Está en Otro mes
            month.setSelectedIndex(mesSeleccionado - 1); //ponemos en mes-1
        }
        llenarCalendario();
    }

    private void consultarCalendario() {
        GenerarConsulta consulta1 = new GenerarConsulta();
        Thread HiloConsulta1 = new Thread(consulta1);
        HiloConsulta1.start();
    }

    private class GenerarConsulta extends Thread {

        public void run() {
            //ArrayList<SalarioLiquidacion> planillaSalarios = new ArrayList<SalarioLiquidacion>();
            String diaS = calendario.getValueAt(calendario.getSelectedRow(), calendario.getSelectedColumn()).toString();
            if (diaS.length() > 2) { //en caso que esté pintado
                diaS = diaS.substring(120);
                diaS = diaS.replace("</b></div></html>", "");
            }
            if (diaS.trim().length() >= 1) { //solo consultar si el campo es una fecha
                orden_trabajoDAO DAO = new orden_trabajoDAO();
                String a = "<html>";
                try {
                    diaS = anho.getValue().toString() + "-" + (month.getSelectedIndex() + 1) + "-" + diaS;
                    Date diaI = Date.valueOf(diaS);
                    System.out.println("Dia: " + diaI);
                    for (orden_trabajo ot : DAO.MostrarxFechaEntrega(diaI)) {
                        String fechaI = "Sin Fecha de Inicio";
                        if (ot.getFechaentregaprevista() != null) {
                            fechaI = ot.getFechaentregaprevista().toString();
                        }
                        a = a + "<p>"
                                + "<p><b>Fecha de Emisión:</b>" + ot.getFechaemision() + "</p>"
                                + "<p><b>Fecha de Inicio:</b>" + ot.getFechainicio() + "</p>"
                                + "<p><b>Fecha de Entrega Prevista:</b>" + fechaI + "</p>"
                                + "<p><b>Tipo:</b>" + ot.getTipo() + "</p>"
                                + "<p><b>Trabajo a Realizar:</b><i>" + ot.getTrabajoarealizar().toUpperCase() + "</i></p>"
                                + "<p><b>Sucursal:</b>" + ot.getSucursal().getNombre() + "</p>"
                                + "</p><br>";
                    }
                    eventos.setText(a + "</html>");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                }
            }
        }
    }
    /**
     * ********************** METODOS DEL CALENDARIO
     * ******************************
     */
    Marquesina test = new Marquesina();

    Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/rep_clientes_servicios.jpg"));
    Money money = new Money();

    //ClockLabel clock = new ClockLabel(null);
    public void VerjDialog(JFrame ventana, int with, int heigt) {
        //ventana.setModal(true);
        ventana.setSize(with, heigt);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);
//      ventana.setIconImage(icon);
    }

    public void Dashboard() {
        jcMousePanel1.setSize(jPanel1.getSize());
        jPanel1.removeAll();
        jPanel1.add(jcMousePanel1);
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    public void openLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            System.out.println(e);
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

        jcMousePanel1 = new jcMousePanel.jcMousePanel();
        jMenuItem15 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        panel1 = new org.edisoncor.gui.panel.Panel();
        marquesina = new org.edisoncor.gui.label.LabelMetric();
        fondo = new org.edisoncor.gui.panel.PanelImage();
        jLUser = new javax.swing.JLabel();
        fechaproceso = new com.toedter.calendar.JDateChooser();
        EtiquetaQR = new javax.swing.JLabel();
        panelCalendario = new javax.swing.JPanel();
        contenedorCalendario = new javax.swing.JScrollPane();
        calendario = new javax.swing.JTable();
        previous = new javax.swing.JButton();
        month = new javax.swing.JComboBox<>();
        up = new javax.swing.JButton();
        down = new javax.swing.JButton();
        anho = new javax.swing.JSpinner();
        next = new javax.swing.JButton();
        eventos = new javax.swing.JLabel();
        buttonSeven1 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven2 = new org.edisoncor.gui.button.ButtonSeven();
        Descuentos = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven4 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven5 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven7 = new org.edisoncor.gui.button.ButtonSeven();
        buttonSeven6 = new org.edisoncor.gui.button.ButtonSeven();
        jMenuBar1 = new javax.swing.JMenuBar();
        parametros = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem59 = new javax.swing.JMenuItem();
        jSeparator41 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        jMenuItem260 = new javax.swing.JMenuItem();
        jSeparator116 = new javax.swing.JPopupMenu.Separator();
        jMenuItem154 = new javax.swing.JMenuItem();
        jMenuItem65 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator138 = new javax.swing.JPopupMenu.Separator();
        jMenuItem177 = new javax.swing.JMenuItem();
        jSeparator137 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        mercaderias = new javax.swing.JMenu();
        jMenuItem75 = new javax.swing.JMenuItem();
        jMenuItem76 = new javax.swing.JMenuItem();
        jMenuItem77 = new javax.swing.JMenuItem();
        jMenuItem78 = new javax.swing.JMenuItem();
        jMenuItem79 = new javax.swing.JMenuItem();
        jMenuItem80 = new javax.swing.JMenuItem();
        jSeparator58 = new javax.swing.JPopupMenu.Separator();
        jMenuItem81 = new javax.swing.JMenuItem();
        jSeparator60 = new javax.swing.JPopupMenu.Separator();
        jMenuItem92 = new javax.swing.JMenuItem();
        jSeparator61 = new javax.swing.JPopupMenu.Separator();
        jMenuItem93 = new javax.swing.JMenuItem();
        jSeparator64 = new javax.swing.JPopupMenu.Separator();
        jMenuItem96 = new javax.swing.JMenuItem();
        jSeparator139 = new javax.swing.JPopupMenu.Separator();
        jMenu16 = new javax.swing.JMenu();
        jMenuItem165 = new javax.swing.JMenuItem();
        jSeparator159 = new javax.swing.JPopupMenu.Separator();
        jMenuItem187 = new javax.swing.JMenuItem();
        jSeparator147 = new javax.swing.JPopupMenu.Separator();
        jMenuItem183 = new javax.swing.JMenuItem();
        jSeparator148 = new javax.swing.JPopupMenu.Separator();
        jMenuItem186 = new javax.swing.JMenuItem();
        jSeparator161 = new javax.swing.JPopupMenu.Separator();
        jMenuItem199 = new javax.swing.JMenuItem();
        jSeparator165 = new javax.swing.JPopupMenu.Separator();
        jMenuItem203 = new javax.swing.JMenuItem();
        jSeparator166 = new javax.swing.JPopupMenu.Separator();
        jMenuItem204 = new javax.swing.JMenuItem();
        jSeparator172 = new javax.swing.JPopupMenu.Separator();
        jMenuItem209 = new javax.swing.JMenuItem();
        jSeparator160 = new javax.swing.JPopupMenu.Separator();
        jMenuItem198 = new javax.swing.JMenuItem();
        compras = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator27 = new javax.swing.JPopupMenu.Separator();
        jMenuItem268 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItem101 = new javax.swing.JMenuItem();
        jSeparator191 = new javax.swing.JPopupMenu.Separator();
        jMenuItem224 = new javax.swing.JMenuItem();
        jSeparator28 = new javax.swing.JPopupMenu.Separator();
        jMenuItem102 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem111 = new javax.swing.JMenuItem();
        jSeparator29 = new javax.swing.JPopupMenu.Separator();
        jMenuItem112 = new javax.swing.JMenuItem();
        jSeparator70 = new javax.swing.JPopupMenu.Separator();
        jMenuItem113 = new javax.swing.JMenuItem();
        jSeparator78 = new javax.swing.JPopupMenu.Separator();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem122 = new javax.swing.JMenuItem();
        jSeparator79 = new javax.swing.JPopupMenu.Separator();
        jMenuItem123 = new javax.swing.JMenuItem();
        jSeparator80 = new javax.swing.JPopupMenu.Separator();
        jMenuItem124 = new javax.swing.JMenuItem();
        jSeparator81 = new javax.swing.JPopupMenu.Separator();
        jMenuItem125 = new javax.swing.JMenuItem();
        jSeparator85 = new javax.swing.JPopupMenu.Separator();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem126 = new javax.swing.JMenuItem();
        jSeparator82 = new javax.swing.JPopupMenu.Separator();
        jMenuItem127 = new javax.swing.JMenuItem();
        jSeparator83 = new javax.swing.JPopupMenu.Separator();
        jMenuItem128 = new javax.swing.JMenuItem();
        jSeparator84 = new javax.swing.JPopupMenu.Separator();
        jMenuItem129 = new javax.swing.JMenuItem();
        jSeparator96 = new javax.swing.JPopupMenu.Separator();
        jMenuItem131 = new javax.swing.JMenuItem();
        ventas = new javax.swing.JMenu();
        Cajas = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem116 = new javax.swing.JMenuItem();
        jSeparator55 = new javax.swing.JPopupMenu.Separator();
        jMenuItem211 = new javax.swing.JMenuItem();
        jSeparator140 = new javax.swing.JPopupMenu.Separator();
        jMenuItem254 = new javax.swing.JMenuItem();
        jMenuItem270 = new javax.swing.JMenuItem();
        jSeparator192 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        jSeparator176 = new javax.swing.JPopupMenu.Separator();
        jMenuItem178 = new javax.swing.JMenuItem();
        jSeparator77 = new javax.swing.JPopupMenu.Separator();
        Retenciones = new javax.swing.JMenuItem();
        jSeparator174 = new javax.swing.JPopupMenu.Separator();
        JMenuItemLibro = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator75 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator74 = new javax.swing.JPopupMenu.Separator();
        jMenuItem108 = new javax.swing.JMenuItem();
        jSeparator73 = new javax.swing.JPopupMenu.Separator();
        jMenuItem109 = new javax.swing.JMenuItem();
        jSeparator72 = new javax.swing.JPopupMenu.Separator();
        jMenuItem110 = new javax.swing.JMenuItem();
        jSeparator157 = new javax.swing.JPopupMenu.Separator();
        jMenuItem250 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem104 = new javax.swing.JMenuItem();
        jSeparator91 = new javax.swing.JPopupMenu.Separator();
        jMenuItem117 = new javax.swing.JMenuItem();
        jSeparator92 = new javax.swing.JPopupMenu.Separator();
        jMenuItem118 = new javax.swing.JMenuItem();
        jSeparator93 = new javax.swing.JPopupMenu.Separator();
        jMenuItem119 = new javax.swing.JMenuItem();
        jMenuItem249 = new javax.swing.JMenuItem();
        jSeparator94 = new javax.swing.JPopupMenu.Separator();
        jMenuItem120 = new javax.swing.JMenuItem();
        jSeparator95 = new javax.swing.JPopupMenu.Separator();
        jMenuItem121 = new javax.swing.JMenuItem();
        jSeparator45 = new javax.swing.JPopupMenu.Separator();
        jMenuItem269 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem130 = new javax.swing.JMenuItem();
        jSeparator86 = new javax.swing.JPopupMenu.Separator();
        jMenuItem132 = new javax.swing.JMenuItem();
        jSeparator87 = new javax.swing.JPopupMenu.Separator();
        jMenuItem133 = new javax.swing.JMenuItem();
        jSeparator49 = new javax.swing.JPopupMenu.Separator();
        jMenuItem145 = new javax.swing.JMenuItem();
        jSeparator88 = new javax.swing.JPopupMenu.Separator();
        jMenuItem215 = new javax.swing.JMenuItem();
        jSeparator179 = new javax.swing.JPopupMenu.Separator();
        jMenuItem134 = new javax.swing.JMenuItem();
        jSeparator89 = new javax.swing.JPopupMenu.Separator();
        jMenuItem135 = new javax.swing.JMenuItem();
        jSeparator90 = new javax.swing.JPopupMenu.Separator();
        jMenuItem21 = new javax.swing.JMenuItem();
        jSeparator149 = new javax.swing.JPopupMenu.Separator();
        jMenuItem188 = new javax.swing.JMenuItem();
        jSeparator48 = new javax.swing.JPopupMenu.Separator();
        jMenuItem142 = new javax.swing.JMenuItem();
        operaciones = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        Solicitudes = new javax.swing.JMenuItem();
        jMenuItem214 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jSeparator205 = new javax.swing.JPopupMenu.Separator();
        jMenuItem152 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem69 = new javax.swing.JMenuItem();
        jSeparator158 = new javax.swing.JPopupMenu.Separator();
        jMenuItem197 = new javax.swing.JMenuItem();
        jSeparator206 = new javax.swing.JPopupMenu.Separator();
        jMenuItem271 = new javax.swing.JMenuItem();
        jSeparator47 = new javax.swing.JPopupMenu.Separator();
        jMenuItem258 = new javax.swing.JMenuItem();
        jSeparator185 = new javax.swing.JPopupMenu.Separator();
        Cheques = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jSeparator42 = new javax.swing.JPopupMenu.Separator();
        jMenuItem60 = new javax.swing.JMenuItem();
        jSeparator130 = new javax.swing.JPopupMenu.Separator();
        jMenuItem170 = new javax.swing.JMenuItem();
        jSeparator183 = new javax.swing.JPopupMenu.Separator();
        jMenu18 = new javax.swing.JMenu();
        jMenu19 = new javax.swing.JMenu();
        jMenuItem218 = new javax.swing.JMenuItem();
        jSeparator186 = new javax.swing.JPopupMenu.Separator();
        jMenuItem219 = new javax.swing.JMenuItem();
        jSeparator187 = new javax.swing.JPopupMenu.Separator();
        jMenuItem220 = new javax.swing.JMenuItem();
        jSeparator188 = new javax.swing.JPopupMenu.Separator();
        jMenuItem222 = new javax.swing.JMenuItem();
        jSeparator189 = new javax.swing.JPopupMenu.Separator();
        jMenuItem221 = new javax.swing.JMenuItem();
        jSeparator190 = new javax.swing.JPopupMenu.Separator();
        jMenuItem223 = new javax.swing.JMenuItem();
        jSeparator184 = new javax.swing.JPopupMenu.Separator();
        jMenuItem181 = new javax.swing.JMenuItem();
        jSeparator193 = new javax.swing.JPopupMenu.Separator();
        jMenuItem226 = new javax.swing.JMenuItem();
        jSeparator168 = new javax.swing.JPopupMenu.Separator();
        jMenuItem252 = new javax.swing.JMenuItem();
        finanzas = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        jMenuItem27 = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        jMenuItem28 = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        jMenuItem208 = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        jMenuItem30 = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        jMenuItem34 = new javax.swing.JMenuItem();
        jSeparator171 = new javax.swing.JPopupMenu.Separator();
        jMenuItem31 = new javax.swing.JMenuItem();
        jSeparator53 = new javax.swing.JPopupMenu.Separator();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem44 = new javax.swing.JMenuItem();
        jSeparator63 = new javax.swing.JPopupMenu.Separator();
        jMenuItem73 = new javax.swing.JMenuItem();
        jSeparator66 = new javax.swing.JPopupMenu.Separator();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenuItem247 = new javax.swing.JMenuItem();
        jSeparator67 = new javax.swing.JPopupMenu.Separator();
        jMenuItem18 = new javax.swing.JMenuItem();
        jSeparator177 = new javax.swing.JPopupMenu.Separator();
        jMenuItem212 = new javax.swing.JMenuItem();
        jSeparator178 = new javax.swing.JPopupMenu.Separator();
        jMenuItem255 = new javax.swing.JMenuItem();
        jSeparator203 = new javax.swing.JPopupMenu.Separator();
        jMenuItem213 = new javax.swing.JMenuItem();
        jSeparator111 = new javax.swing.JPopupMenu.Separator();
        jMenuItem147 = new javax.swing.JMenuItem();
        jSeparator26 = new javax.swing.JPopupMenu.Separator();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem46 = new javax.swing.JMenuItem();
        jSeparator30 = new javax.swing.JPopupMenu.Separator();
        jMenuItem47 = new javax.swing.JMenuItem();
        jSeparator31 = new javax.swing.JPopupMenu.Separator();
        jMenuItem48 = new javax.swing.JMenuItem();
        jSeparator38 = new javax.swing.JPopupMenu.Separator();
        jMenuItem55 = new javax.swing.JMenuItem();
        jSeparator68 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem52 = new javax.swing.JMenuItem();
        jSeparator35 = new javax.swing.JPopupMenu.Separator();
        jMenuItem49 = new javax.swing.JMenuItem();
        jSeparator36 = new javax.swing.JPopupMenu.Separator();
        jMenuItem53 = new javax.swing.JMenuItem();
        jSeparator33 = new javax.swing.JPopupMenu.Separator();
        jMenuItem50 = new javax.swing.JMenuItem();
        jSeparator34 = new javax.swing.JPopupMenu.Separator();
        jMenuItem51 = new javax.swing.JMenuItem();
        jSeparator69 = new javax.swing.JPopupMenu.Separator();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem103 = new javax.swing.JMenuItem();
        jSeparator120 = new javax.swing.JPopupMenu.Separator();
        jMenuItem158 = new javax.swing.JMenuItem();
        jSeparator121 = new javax.swing.JPopupMenu.Separator();
        jMenuItem159 = new javax.swing.JMenuItem();
        jSeparator122 = new javax.swing.JPopupMenu.Separator();
        jMenuItem148 = new javax.swing.JMenuItem();
        jSeparator182 = new javax.swing.JPopupMenu.Separator();
        jMenuItem160 = new javax.swing.JMenuItem();
        jSeparator123 = new javax.swing.JPopupMenu.Separator();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem161 = new javax.swing.JMenuItem();
        jSeparator124 = new javax.swing.JPopupMenu.Separator();
        jMenuItem162 = new javax.swing.JMenuItem();
        jSeparator125 = new javax.swing.JPopupMenu.Separator();
        jMenuItem163 = new javax.swing.JMenuItem();
        jSeparator126 = new javax.swing.JPopupMenu.Separator();
        jMenuItem164 = new javax.swing.JMenuItem();
        cobranzas = new javax.swing.JMenu();
        jMenuItem175 = new javax.swing.JMenuItem();
        jSeparator135 = new javax.swing.JPopupMenu.Separator();
        jMenuItem90 = new javax.swing.JMenuItem();
        jSeparator56 = new javax.swing.JPopupMenu.Separator();
        jMenuItem38 = new javax.swing.JMenuItem();
        jSeparator181 = new javax.swing.JPopupMenu.Separator();
        jMenuItem39 = new javax.swing.JMenuItem();
        jSeparator24 = new javax.swing.JPopupMenu.Separator();
        jMenuItem41 = new javax.swing.JMenuItem();
        jSeparator25 = new javax.swing.JPopupMenu.Separator();
        jMenuItem42 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem217 = new javax.swing.JMenuItem();
        jSeparator118 = new javax.swing.JPopupMenu.Separator();
        jMenuItem156 = new javax.swing.JMenuItem();
        jSeparator65 = new javax.swing.JPopupMenu.Separator();
        jMenuItem97 = new javax.swing.JMenuItem();
        jSeparator175 = new javax.swing.JPopupMenu.Separator();
        jMenuItem43 = new javax.swing.JMenuItem();
        jSeparator37 = new javax.swing.JPopupMenu.Separator();
        jMenuItem54 = new javax.swing.JMenuItem();
        jSeparator44 = new javax.swing.JPopupMenu.Separator();
        jMenuItem264 = new javax.swing.JMenuItem();
        asociaciones = new javax.swing.JMenu();
        jMenuItem61 = new javax.swing.JMenuItem();
        jMenuItem63 = new javax.swing.JMenuItem();
        jMenu14 = new javax.swing.JMenu();
        jMenuItem64 = new javax.swing.JMenuItem();
        jSeparator100 = new javax.swing.JPopupMenu.Separator();
        jMenuItem95 = new javax.swing.JMenuItem();
        jSeparator101 = new javax.swing.JPopupMenu.Separator();
        jMenuItem71 = new javax.swing.JMenuItem();
        jSeparator102 = new javax.swing.JPopupMenu.Separator();
        jMenuItem72 = new javax.swing.JMenuItem();
        jSeparator103 = new javax.swing.JPopupMenu.Separator();
        jMenuItem137 = new javax.swing.JMenuItem();
        jSeparator105 = new javax.swing.JPopupMenu.Separator();
        jMenuItem98 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem68 = new javax.swing.JMenuItem();
        jSeparator97 = new javax.swing.JPopupMenu.Separator();
        jMenuItem94 = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JPopupMenu.Separator();
        jMenuItem33 = new javax.swing.JMenuItem();
        jSeparator98 = new javax.swing.JPopupMenu.Separator();
        jMenuItem136 = new javax.swing.JMenuItem();
        jSeparator99 = new javax.swing.JPopupMenu.Separator();
        jMenuItem20 = new javax.swing.JMenuItem();
        prestamos = new javax.swing.JMenuItem();
        jMenuItem74 = new javax.swing.JMenuItem();
        jMenuItem115 = new javax.swing.JMenuItem();
        jMenuItem107 = new javax.swing.JMenuItem();
        jMenuItem253 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem202 = new javax.swing.JMenuItem();
        jMenuItem89 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenu20 = new javax.swing.JMenu();
        jMenuItem256 = new javax.swing.JMenuItem();
        jSeparator204 = new javax.swing.JPopupMenu.Separator();
        jMenuItem257 = new javax.swing.JMenuItem();
        jMenuItem67 = new javax.swing.JMenuItem();
        jMenuItem62 = new javax.swing.JMenuItem();
        jMenuItem66 = new javax.swing.JMenuItem();
        jMenuItem70 = new javax.swing.JMenuItem();
        jMenuItem114 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem153 = new javax.swing.JMenuItem();
        jMenuItem157 = new javax.swing.JMenuItem();
        jMenuItem185 = new javax.swing.JMenuItem();
        jMenuItem216 = new javax.swing.JMenuItem();
        jMenuItem251 = new javax.swing.JMenuItem();
        inmobiliaria = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem82 = new javax.swing.JMenuItem();
        jMenuItem83 = new javax.swing.JMenuItem();
        jMenuItem84 = new javax.swing.JMenuItem();
        jMenuItem85 = new javax.swing.JMenuItem();
        jMenuItem86 = new javax.swing.JMenuItem();
        jMenuItem87 = new javax.swing.JMenuItem();
        jSeparator59 = new javax.swing.JPopupMenu.Separator();
        jMenuItem88 = new javax.swing.JMenuItem();
        jSeparator57 = new javax.swing.JPopupMenu.Separator();
        jMenuItem91 = new javax.swing.JMenuItem();
        jSeparator46 = new javax.swing.JPopupMenu.Separator();
        jMenuItem99 = new javax.swing.JMenuItem();
        jSeparator43 = new javax.swing.JPopupMenu.Separator();
        jMenuItem105 = new javax.swing.JMenuItem();
        jSeparator51 = new javax.swing.JPopupMenu.Separator();
        jMenuItem100 = new javax.swing.JMenuItem();
        jSeparator52 = new javax.swing.JPopupMenu.Separator();
        jMenuItem155 = new javax.swing.JMenuItem();
        jSeparator163 = new javax.swing.JPopupMenu.Separator();
        jMenuItem106 = new javax.swing.JMenuItem();
        jSeparator136 = new javax.swing.JPopupMenu.Separator();
        jMenuItem176 = new javax.swing.JMenuItem();
        jSeparator132 = new javax.swing.JPopupMenu.Separator();
        jMenuItem201 = new javax.swing.JMenuItem();
        jSeparator117 = new javax.swing.JPopupMenu.Separator();
        jMenuItem179 = new javax.swing.JMenuItem();
        jSeparator141 = new javax.swing.JPopupMenu.Separator();
        jMenuItem180 = new javax.swing.JMenuItem();
        jSeparator142 = new javax.swing.JPopupMenu.Separator();
        jMenuItem184 = new javax.swing.JMenuItem();
        jSeparator145 = new javax.swing.JPopupMenu.Separator();
        jMenu17 = new javax.swing.JMenu();
        jMenuItem172 = new javax.swing.JMenuItem();
        jSeparator133 = new javax.swing.JPopupMenu.Separator();
        jMenuItem182 = new javax.swing.JMenuItem();
        jSeparator144 = new javax.swing.JPopupMenu.Separator();
        jMenuItem173 = new javax.swing.JMenuItem();
        jSeparator134 = new javax.swing.JPopupMenu.Separator();
        jMenuItem174 = new javax.swing.JMenuItem();
        contabilidad = new javax.swing.JMenu();
        jMenuItem138 = new javax.swing.JMenuItem();
        jSeparator104 = new javax.swing.JPopupMenu.Separator();
        jMenuItem139 = new javax.swing.JMenuItem();
        jSeparator106 = new javax.swing.JPopupMenu.Separator();
        jMenuItem261 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        jMenuItem140 = new javax.swing.JMenuItem();
        jSeparator107 = new javax.swing.JPopupMenu.Separator();
        jMenuItem141 = new javax.swing.JMenuItem();
        jSeparator108 = new javax.swing.JPopupMenu.Separator();
        jMenuItem143 = new javax.swing.JMenuItem();
        jSeparator109 = new javax.swing.JPopupMenu.Separator();
        jMenuItem144 = new javax.swing.JMenuItem();
        jSeparator110 = new javax.swing.JPopupMenu.Separator();
        jMenuItem146 = new javax.swing.JMenuItem();
        jSeparator112 = new javax.swing.JPopupMenu.Separator();
        jMenuItem149 = new javax.swing.JMenuItem();
        jSeparator143 = new javax.swing.JPopupMenu.Separator();
        jMenuItem262 = new javax.swing.JMenuItem();
        jSeparator23 = new javax.swing.JPopupMenu.Separator();
        jMenuItem150 = new javax.swing.JMenuItem();
        jSeparator113 = new javax.swing.JPopupMenu.Separator();
        jMenuItem151 = new javax.swing.JMenuItem();
        jSeparator32 = new javax.swing.JPopupMenu.Separator();
        jMenuItem263 = new javax.swing.JMenuItem();
        rrhh = new javax.swing.JMenu();
        tablasrrhh = new javax.swing.JMenu();
        jMenuItem227 = new javax.swing.JMenuItem();
        jSeparator194 = new javax.swing.JPopupMenu.Separator();
        jMenuItem228 = new javax.swing.JMenuItem();
        jSeparator195 = new javax.swing.JPopupMenu.Separator();
        jMenuItem229 = new javax.swing.JMenuItem();
        jSeparator196 = new javax.swing.JPopupMenu.Separator();
        jMenuItem230 = new javax.swing.JMenuItem();
        jSeparator197 = new javax.swing.JPopupMenu.Separator();
        jMenuItem231 = new javax.swing.JMenuItem();
        jSeparator198 = new javax.swing.JPopupMenu.Separator();
        jMenuItem232 = new javax.swing.JMenuItem();
        jSeparator199 = new javax.swing.JPopupMenu.Separator();
        jMenuItem233 = new javax.swing.JMenuItem();
        jSeparator200 = new javax.swing.JPopupMenu.Separator();
        jMenuItem234 = new javax.swing.JMenuItem();
        jSeparator201 = new javax.swing.JPopupMenu.Separator();
        jMenuItem235 = new javax.swing.JMenuItem();
        jSeparator202 = new javax.swing.JPopupMenu.Separator();
        jMenuItem236 = new javax.swing.JMenuItem();
        jMenuItem238 = new javax.swing.JMenuItem();
        jMenuItem239 = new javax.swing.JMenuItem();
        jMenuItem240 = new javax.swing.JMenuItem();
        jMenuItem171 = new javax.swing.JMenuItem();
        jMenuItem196 = new javax.swing.JMenuItem();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem191 = new javax.swing.JMenuItem();
        jMenuItem192 = new javax.swing.JMenuItem();
        jMenuItem193 = new javax.swing.JMenuItem();
        jMenuItem189 = new javax.swing.JMenuItem();
        Anticipos = new javax.swing.JMenuItem();
        jMenuItem267 = new javax.swing.JMenuItem();
        jMenuItem190 = new javax.swing.JMenuItem();
        jMenuItem242 = new javax.swing.JMenuItem();
        jMenuItem241 = new javax.swing.JMenuItem();
        jMenuItem243 = new javax.swing.JMenuItem();
        jMenuItem205 = new javax.swing.JMenuItem();
        jMenuItem206 = new javax.swing.JMenuItem();
        jMenuItem207 = new javax.swing.JMenuItem();
        jMenuItem225 = new javax.swing.JMenuItem();
        jMenuItem248 = new javax.swing.JMenuItem();
        jMenuItem244 = new javax.swing.JMenuItem();
        jMenuItem245 = new javax.swing.JMenuItem();
        jMenuItem246 = new javax.swing.JMenuItem();
        jMenuItem265 = new javax.swing.JMenuItem();
        jMenuItem266 = new javax.swing.JMenuItem();
        Informes = new javax.swing.JMenu();
        jMenuItem166 = new javax.swing.JMenuItem();
        jSeparator127 = new javax.swing.JPopupMenu.Separator();
        jMenuItem167 = new javax.swing.JMenuItem();
        jSeparator128 = new javax.swing.JPopupMenu.Separator();
        jMenuItem168 = new javax.swing.JMenuItem();
        jSeparator129 = new javax.swing.JPopupMenu.Separator();
        jMenuItem169 = new javax.swing.JMenuItem();
        jSeparator131 = new javax.swing.JPopupMenu.Separator();
        jMenuItem194 = new javax.swing.JMenuItem();
        jSeparator150 = new javax.swing.JPopupMenu.Separator();
        jMenuItem195 = new javax.swing.JMenuItem();
        jSeparator152 = new javax.swing.JPopupMenu.Separator();
        jMenuItem200 = new javax.swing.JMenuItem();
        jSeparator155 = new javax.swing.JPopupMenu.Separator();
        jMenuItem237 = new javax.swing.JMenuItem();
        herramientas = new javax.swing.JMenu();
        jMenuItem56 = new javax.swing.JMenuItem();
        jSeparator39 = new javax.swing.JPopupMenu.Separator();
        jMenuItem57 = new javax.swing.JMenuItem();
        jSeparator40 = new javax.swing.JPopupMenu.Separator();
        jMenuItem58 = new javax.swing.JMenuItem();
        jMenuItem272 = new javax.swing.JMenuItem();
        jSeparator173 = new javax.swing.JPopupMenu.Separator();
        jMenuItem210 = new javax.swing.JMenuItem();
        jSeparator207 = new javax.swing.JPopupMenu.Separator();
        jMenuItem259 = new javax.swing.JMenuItem();

        jcMousePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background.png"))); // NOI18N

        javax.swing.GroupLayout jcMousePanel1Layout = new javax.swing.GroupLayout(jcMousePanel1);
        jcMousePanel1.setLayout(jcMousePanel1Layout);
        jcMousePanel1Layout.setHorizontalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1380, Short.MAX_VALUE)
        );
        jcMousePanel1Layout.setVerticalGroup(
            jcMousePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 719, Short.MAX_VALUE)
        );

        jMenuItem15.setText("jMenuItem15");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 0, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1380, 720));
        jPanel1.setPreferredSize(new java.awt.Dimension(1380, 650));

        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        marquesina.setText("S&N");
        marquesina.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(marquesina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(535, 535, 535))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(marquesina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLUser.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLUser.setForeground(new java.awt.Color(255, 255, 255));
        jLUser.setText("Bienvenido USER");

        fechaproceso.setEnabled(false);
        fechaproceso.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        fechaproceso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaprocesoFocusGained(evt);
            }
        });

        EtiquetaQR.setText("jLabel1");

        panelCalendario.setOpaque(false);
        panelCalendario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        calendario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        calendario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dom", "Lun", "Mar", "Mier", "Jue", "Vier", "Sáb"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        calendario.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        calendario.setColumnSelectionAllowed(true);
        calendario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        calendario.getTableHeader().setReorderingAllowed(false);
        calendario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendarioMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                calendarioMousePressed(evt);
            }
        });
        contenedorCalendario.setViewportView(calendario);
        calendario.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (calendario.getColumnModel().getColumnCount() > 0) {
            calendario.getColumnModel().getColumn(0).setResizable(false);
            calendario.getColumnModel().getColumn(0).setPreferredWidth(71);
            calendario.getColumnModel().getColumn(1).setResizable(false);
            calendario.getColumnModel().getColumn(1).setPreferredWidth(71);
            calendario.getColumnModel().getColumn(2).setResizable(false);
            calendario.getColumnModel().getColumn(2).setPreferredWidth(71);
            calendario.getColumnModel().getColumn(3).setResizable(false);
            calendario.getColumnModel().getColumn(3).setPreferredWidth(71);
            calendario.getColumnModel().getColumn(4).setResizable(false);
            calendario.getColumnModel().getColumn(4).setPreferredWidth(71);
            calendario.getColumnModel().getColumn(5).setResizable(false);
            calendario.getColumnModel().getColumn(5).setPreferredWidth(71);
            calendario.getColumnModel().getColumn(6).setResizable(false);
            calendario.getColumnModel().getColumn(6).setPreferredWidth(71);
        }

        panelCalendario.add(contenedorCalendario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 500, 390));

        previous.setText("<<");
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });
        panelCalendario.add(previous, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, -1, 34));

        month.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthActionPerformed(evt);
            }
        });
        panelCalendario.add(month, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 240, 33));

        up.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        up.setText("▲");
        up.setMargin(new java.awt.Insets(0, 0, 0, 0));
        up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upActionPerformed(evt);
            }
        });
        panelCalendario.add(up, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 19, 20, 18));

        down.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        down.setText("▼");
        down.setMargin(new java.awt.Insets(0, 0, 0, 0));
        down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downActionPerformed(evt);
            }
        });
        panelCalendario.add(down, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 36, 20, 18));

        anho.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        anho.setModel(new javax.swing.SpinnerNumberModel(1900, 1900, null, 1));
        anho.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                anhoStateChanged(evt);
            }
        });
        panelCalendario.add(anho, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 110, 33));

        next.setText(">>");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });
        panelCalendario.add(next, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 50, 34));

        eventos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        eventos.setOpaque(true);
        panelCalendario.add(eventos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 500, 110));

        buttonSeven1.setBackground(new java.awt.Color(204, 204, 255));
        buttonSeven1.setText("Clientes");
        buttonSeven1.setColorBrillo(new java.awt.Color(0, 51, 204));
        buttonSeven1.setColorDeSombra(new java.awt.Color(255, 0, 0));
        buttonSeven1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonSeven1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven1ActionPerformed(evt);
            }
        });

        buttonSeven2.setBackground(new java.awt.Color(204, 204, 255));
        buttonSeven2.setText("Préstamos");
        buttonSeven2.setColorBrillo(new java.awt.Color(0, 51, 204));
        buttonSeven2.setColorDeSombra(new java.awt.Color(255, 0, 0));
        buttonSeven2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonSeven2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven2ActionPerformed(evt);
            }
        });

        Descuentos.setBackground(new java.awt.Color(204, 204, 255));
        Descuentos.setText("Descuentos");
        Descuentos.setColorBrillo(new java.awt.Color(0, 51, 204));
        Descuentos.setColorDeSombra(new java.awt.Color(255, 0, 0));
        Descuentos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Descuentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescuentosActionPerformed(evt);
            }
        });

        buttonSeven4.setBackground(new java.awt.Color(204, 204, 255));
        buttonSeven4.setText("Mora");
        buttonSeven4.setColorBrillo(new java.awt.Color(0, 51, 204));
        buttonSeven4.setColorDeSombra(new java.awt.Color(255, 0, 0));
        buttonSeven4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonSeven4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven4ActionPerformed(evt);
            }
        });

        buttonSeven5.setBackground(new java.awt.Color(204, 204, 255));
        buttonSeven5.setText("Cobranzas");
        buttonSeven5.setColorBrillo(new java.awt.Color(0, 51, 204));
        buttonSeven5.setColorDeSombra(new java.awt.Color(255, 0, 0));
        buttonSeven5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonSeven5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven5ActionPerformed(evt);
            }
        });

        buttonSeven7.setBackground(new java.awt.Color(204, 204, 255));
        buttonSeven7.setText("Ingresos");
        buttonSeven7.setColorBrillo(new java.awt.Color(0, 51, 204));
        buttonSeven7.setColorDeSombra(new java.awt.Color(255, 0, 0));
        buttonSeven7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonSeven7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven7ActionPerformed(evt);
            }
        });

        buttonSeven6.setBackground(new java.awt.Color(204, 204, 255));
        buttonSeven6.setText("Vencimientos");
        buttonSeven6.setColorBrillo(new java.awt.Color(0, 51, 204));
        buttonSeven6.setColorDeSombra(new java.awt.Color(255, 0, 0));
        buttonSeven6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        buttonSeven6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EtiquetaQR)
                    .addComponent(buttonSeven1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven5, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Descuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSeven4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87)
                .addComponent(panelCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGap(0, 407, Short.MAX_VALUE)
                        .addComponent(jLUser))
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        fondoLayout.setVerticalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addComponent(panelCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(fondoLayout.createSequentialGroup()
                        .addGroup(fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLUser)
                            .addGroup(fondoLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(buttonSeven1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSeven2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Descuentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSeven4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSeven5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSeven7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSeven6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EtiquetaQR)
                        .addGap(100, 100, 100))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fechaproceso, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(247, 247, 247));

        parametros.setText("Parametros");
        parametros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem1.setText("Sucursales");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem1);
        parametros.add(jSeparator5);

        jMenuItem2.setText("Asesores Comerciales");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.setPreferredSize(new java.awt.Dimension(157, 20));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem2);
        parametros.add(jSeparator1);

        jMenuItem59.setText("Asesores Legales");
        jMenuItem59.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem59ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem59);
        parametros.add(jSeparator41);

        jMenuItem3.setText("Categoría de Clientes");
        jMenuItem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem3);
        parametros.add(jSeparator18);

        jMenuItem260.setText("Situación de Clientes");
        jMenuItem260.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem260.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem260ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem260);
        parametros.add(jSeparator116);

        jMenuItem154.setText("Departamentos");
        jMenuItem154.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem154.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem154ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem154);

        jMenuItem65.setText("Barrios");
        jMenuItem65.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem65ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem65);

        jMenuItem5.setText("Localidades");
        jMenuItem5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem5);

        jMenuItem4.setText("Clientes");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem4);
        parametros.add(jSeparator3);

        jMenuItem9.setText("Comprobantes");
        jMenuItem9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem9);
        parametros.add(jSeparator4);

        jMenuItem11.setText("Monedas");
        jMenuItem11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem11);
        parametros.add(jSeparator138);

        jMenuItem177.setText("Formas de Cobro/Pagos");
        jMenuItem177.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem177ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem177);
        parametros.add(jSeparator137);

        jMenuItem14.setText("Bancos de Plaza");
        jMenuItem14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem14);
        parametros.add(jSeparator7);

        jMenuItem13.setText("Salir del Sistema");
        jMenuItem13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem13);

        jMenuBar1.add(parametros);

        mercaderias.setText("Mercaderías");
        mercaderias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem75.setText("Origen - Procedencia");
        jMenuItem75.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem75ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem75);

        jMenuItem76.setText("Familias");
        jMenuItem76.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem76ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem76);

        jMenuItem77.setText("Rubros");
        jMenuItem77.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem77ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem77);

        jMenuItem78.setText("Marcas");
        jMenuItem78.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem78ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem78);

        jMenuItem79.setText("Ubicaciones");
        jMenuItem79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem79ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem79);

        jMenuItem80.setText("Unidades de Médida");
        jMenuItem80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem80ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem80);
        mercaderias.add(jSeparator58);

        jMenuItem81.setText("Ficha de Productos");
        jMenuItem81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem81ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem81);
        mercaderias.add(jSeparator60);

        jMenuItem92.setText("Ingreso de Mercaderías");
        jMenuItem92.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem92.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem92ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem92);
        mercaderias.add(jSeparator61);

        jMenuItem93.setText("Salida de Mercaderías");
        jMenuItem93.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem93.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem93ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem93);
        mercaderias.add(jSeparator64);

        jMenuItem96.setText("Transferencia de Mercaderías");
        jMenuItem96.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem96ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem96);
        mercaderias.add(jSeparator139);

        jMenu16.setText("Informes de Inventario");
        jMenu16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem165.setText("Resumen de Movimientos de Mercaderías");
        jMenuItem165.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem165.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem165ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem165);
        jMenu16.add(jSeparator159);

        jMenuItem187.setText("Kardex del Producto");
        jMenuItem187.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem187.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem187ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem187);
        jMenu16.add(jSeparator147);

        jMenuItem183.setText("Inventario por Rubro");
        jMenuItem183.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem183.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem183ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem183);
        jMenu16.add(jSeparator148);

        jMenuItem186.setText("Inventario Rubro por Fecha");
        jMenuItem186.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem186.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem186ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem186);
        jMenu16.add(jSeparator161);

        jMenuItem199.setText("Inventario por Familia");
        jMenuItem199.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem199.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem199ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem199);
        jMenu16.add(jSeparator165);

        jMenuItem203.setText("Inventario por Ubicación");
        jMenuItem203.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem203.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem203ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem203);
        jMenu16.add(jSeparator166);

        jMenuItem204.setText("Inventario por Marcas");
        jMenuItem204.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem204.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem204ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem204);

        mercaderias.add(jMenu16);
        mercaderias.add(jSeparator172);

        jMenuItem209.setText("Utilitario de Stock");
        jMenuItem209.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem209.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem209ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem209);
        mercaderias.add(jSeparator160);

        jMenuItem198.setText("Orden de Trabajo");
        jMenuItem198.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem198.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem198ActionPerformed(evt);
            }
        });
        mercaderias.add(jMenuItem198);

        jMenuBar1.add(mercaderias);

        compras.setText("Compras");
        compras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem10.setText("Proveedores");
        jMenuItem10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        compras.add(jMenuItem10);
        compras.add(jSeparator13);

        jMenuItem12.setText("Clasificar Rubro de Gastos");
        jMenuItem12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        compras.add(jMenuItem12);
        compras.add(jSeparator14);

        jMenuItem7.setText("Gastos a Pagar");
        jMenuItem7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        compras.add(jMenuItem7);
        compras.add(jSeparator27);

        jMenuItem268.setText("Órdenes de Compra");
        jMenuItem268.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem268.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem268ActionPerformed(evt);
            }
        });
        compras.add(jMenuItem268);
        compras.add(jSeparator8);

        jMenuItem101.setText("Compra de Mercaderías");
        jMenuItem101.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem101ActionPerformed(evt);
            }
        });
        compras.add(jMenuItem101);
        compras.add(jSeparator191);

        jMenuItem224.setText("Ajuste de Compras");
        jMenuItem224.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem224ActionPerformed(evt);
            }
        });
        compras.add(jMenuItem224);
        compras.add(jSeparator28);

        jMenuItem102.setText("Notas de Crédito");
        jMenuItem102.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem102.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem102ActionPerformed(evt);
            }
        });
        compras.add(jMenuItem102);
        compras.add(jSeparator2);

        jMenu9.setText("Libros de Compra");
        jMenu9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem111.setText("Libro Compra Consolidado");
        jMenuItem111.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem111ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem111);
        jMenu9.add(jSeparator29);

        jMenuItem112.setText("Libro Compra por Sucursal");
        jMenuItem112.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem112ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem112);
        jMenu9.add(jSeparator70);

        jMenuItem113.setText("Libro Compra por Proveedor");
        jMenuItem113.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem113.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem113ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem113);

        compras.add(jMenu9);
        compras.add(jSeparator78);

        jMenu10.setText("Resumen de Compras");
        jMenu10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem122.setText("Compras por Sucursal");
        jMenuItem122.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem122.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem122ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem122);
        jMenu10.add(jSeparator79);

        jMenuItem123.setText("Compra por Proveedor");
        jMenuItem123.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem123ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem123);
        jMenu10.add(jSeparator80);

        jMenuItem124.setText("Compras por Rubro");
        jMenuItem124.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem124.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem124ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem124);
        jMenu10.add(jSeparator81);

        jMenuItem125.setText("Compras por Marcas");
        jMenuItem125.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem125.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem125ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem125);

        compras.add(jMenu10);
        compras.add(jSeparator85);

        jMenu11.setText("Detalle de Compras");
        jMenu11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem126.setText("Detalle por Sucursales");
        jMenuItem126.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem126.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem126ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem126);
        jMenu11.add(jSeparator82);

        jMenuItem127.setText("Detalle por Proveedores");
        jMenuItem127.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem127.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem127ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem127);
        jMenu11.add(jSeparator83);

        jMenuItem128.setText("Detalle por Rubros");
        jMenuItem128.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem128.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem128ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem128);
        jMenu11.add(jSeparator84);

        jMenuItem129.setText("Detalle por Marcas");
        jMenuItem129.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem129.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem129ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem129);
        jMenu11.add(jSeparator96);

        jMenuItem131.setText("Detalle por Productos");
        jMenuItem131.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem131.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem131ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem131);

        compras.add(jMenu11);

        jMenuBar1.add(compras);

        ventas.setText("Ventas");
        ventas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Cajas.setText("Cajas");
        Cajas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Cajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajasActionPerformed(evt);
            }
        });
        ventas.add(Cajas);
        ventas.add(jSeparator9);

        jMenuItem116.setText("Preventas");
        jMenuItem116.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem116.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem116ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem116);
        ventas.add(jSeparator55);

        jMenuItem211.setText("Presupuestos - OT - Preventas");
        jMenuItem211.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem211.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem211ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem211);
        ventas.add(jSeparator140);

        jMenuItem254.setText("Anticipos");
        jMenuItem254.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem254.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem254ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem254);

        jMenuItem270.setText("Saldos de Anticipos");
        jMenuItem270.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem270.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem270ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem270);
        ventas.add(jSeparator192);

        jMenuItem17.setText("Ventas");
        jMenuItem17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem17);
        ventas.add(jSeparator176);

        jMenuItem178.setText("Punto de Venta");
        jMenuItem178.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem178.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem178ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem178);
        ventas.add(jSeparator77);

        Retenciones.setText("Notas de Crédito");
        Retenciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Retenciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RetencionesActionPerformed(evt);
            }
        });
        ventas.add(Retenciones);
        ventas.add(jSeparator174);

        JMenuItemLibro.setText("Retenciones");
        JMenuItemLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemLibroActionPerformed(evt);
            }
        });
        ventas.add(JMenuItemLibro);
        ventas.add(jSeparator10);

        jMenu8.setText("Libros de Venta");
        jMenu8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem19.setText("Libro Venta Consolidado");
        jMenuItem19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem19);
        jMenu8.add(jSeparator75);

        jMenuItem8.setText("Libro Venta por Sucursal");
        jMenuItem8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem8);
        jMenu8.add(jSeparator74);

        jMenuItem108.setText("Libro Venta por Vendedor");
        jMenuItem108.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem108.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem108ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem108);
        jMenu8.add(jSeparator73);

        jMenuItem109.setText("Libro Venta por Caja");
        jMenuItem109.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem109.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem109ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem109);
        jMenu8.add(jSeparator72);

        jMenuItem110.setText("Libro Venta por Cliente");
        jMenuItem110.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem110ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem110);
        jMenu8.add(jSeparator157);

        jMenuItem250.setText("Libro Venta por Usuario");
        jMenuItem250.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem250.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem250ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem250);

        ventas.add(jMenu8);
        ventas.add(jSeparator11);

        jMenu1.setText("Resumen de Ventas");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem104.setText("Resumen Consolidado");
        jMenuItem104.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem104ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem104);
        jMenu1.add(jSeparator91);

        jMenuItem117.setText("Resumen xSucursal");
        jMenuItem117.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem117.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem117ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem117);
        jMenu1.add(jSeparator92);

        jMenuItem118.setText("Resumen xCaja");
        jMenuItem118.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem118.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem118ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem118);
        jMenu1.add(jSeparator93);

        jMenuItem119.setText("Resumen x Cliente");
        jMenuItem119.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem119.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem119ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem119);

        jMenuItem249.setText("Resumen Agrupados por Clientes");
        jMenuItem249.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem249.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem249ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem249);
        jMenu1.add(jSeparator94);

        jMenuItem120.setText("Resumen x Vendedor");
        jMenuItem120.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem120.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem120ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem120);
        jMenu1.add(jSeparator95);

        jMenuItem121.setText("Resumen x Rubro");
        jMenuItem121.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem121.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem121ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem121);
        jMenu1.add(jSeparator45);

        jMenuItem269.setText("Utilidades por Ventas");
        jMenuItem269.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem269.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem269ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem269);

        ventas.add(jMenu1);
        ventas.add(jSeparator12);

        jMenu12.setText("Detalle de Ventas");
        jMenu12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem130.setText("Detalle Consolidado");
        jMenuItem130.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem130.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem130ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem130);
        jMenu12.add(jSeparator86);

        jMenuItem132.setText("Detalle por Cajas");
        jMenuItem132.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem132.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem132ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem132);
        jMenu12.add(jSeparator87);

        jMenuItem133.setText("Detalle por Clientes");
        jMenuItem133.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem133.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem133ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem133);
        jMenu12.add(jSeparator49);

        jMenuItem145.setText("Detalle de Venta x Cliente-Fecha");
        jMenuItem145.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem145.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem145ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem145);
        jMenu12.add(jSeparator88);

        jMenuItem215.setText("Detalle Venta de Producto por Cliente");
        jMenuItem215.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem215.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem215ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem215);
        jMenu12.add(jSeparator179);

        jMenuItem134.setText("Detalle por Vendedores");
        jMenuItem134.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem134.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem134ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem134);
        jMenu12.add(jSeparator89);

        jMenuItem135.setText("Detalle por  Rubros");
        jMenuItem135.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem135.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem135ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem135);
        jMenu12.add(jSeparator90);

        jMenuItem21.setText("Detalle por Productos");
        jMenuItem21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem21);

        ventas.add(jMenu12);
        ventas.add(jSeparator149);

        jMenuItem188.setText("Resumen de Ingresos ");
        jMenuItem188.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem188.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem188ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem188);
        ventas.add(jSeparator48);

        jMenuItem142.setText("Ajuste de Saldos");
        jMenuItem142.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem142.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem142ActionPerformed(evt);
            }
        });
        ventas.add(jMenuItem142);

        jMenuBar1.add(ventas);

        operaciones.setText("Operaciones");
        operaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu5.setText("Préstamos");
        jMenu5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem36.setText("Requisitos");
        jMenuItem36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem36);

        jMenuItem25.setText("Destino del Préstamo");
        jMenuItem25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem25);

        jMenuItem22.setText("Tipo de Garantías");
        jMenuItem22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem22);

        Solicitudes.setText("Proformas");
        Solicitudes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Solicitudes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolicitudesActionPerformed(evt);
            }
        });
        jMenu5.add(Solicitudes);

        jMenuItem214.setText("Entidad Custodia");
        jMenuItem214.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem214.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem214ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem214);

        jMenuItem23.setText("Préstamos");
        jMenuItem23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem23);
        jMenu5.add(jSeparator205);

        jMenuItem152.setText("Microcreditos");
        jMenuItem152.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem152.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem152ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem152);

        jMenuItem24.setText("Préstamos Desembolsados por Fecha");
        jMenuItem24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem24);

        jMenuItem37.setText("Préstamos Desembolsados por Asesor");
        jMenuItem37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem37);

        jMenuItem69.setText("Detalle Desembolsos");
        jMenuItem69.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem69ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem69);
        jMenu5.add(jSeparator158);

        jMenuItem197.setText("Informe de Intereses ");
        jMenuItem197.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem197.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem197ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem197);

        jSeparator206.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu5.add(jSeparator206);

        jMenuItem271.setText("Ajuste de Saldos");
        jMenuItem271.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem271.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem271ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem271);
        jMenu5.add(jSeparator47);

        jMenuItem258.setText("Configuración");
        jMenuItem258.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem258.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem258ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem258);

        operaciones.add(jMenu5);
        operaciones.add(jSeparator185);

        Cheques.setText("Cheques Adelantados");
        Cheques.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem16.setText(" Registros");
        jMenuItem16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        Cheques.add(jMenuItem16);
        Cheques.add(jSeparator42);

        jMenuItem60.setText("Informe de Cheques Negociados");
        jMenuItem60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem60ActionPerformed(evt);
            }
        });
        Cheques.add(jMenuItem60);
        Cheques.add(jSeparator130);

        jMenuItem170.setText("Vencimiento por Cheque");
        jMenuItem170.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem170.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem170ActionPerformed(evt);
            }
        });
        Cheques.add(jMenuItem170);

        operaciones.add(Cheques);
        operaciones.add(jSeparator183);

        jMenu18.setText("Mercado Bursátil");

        jMenu19.setText("Tablas ");
        jMenu19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem218.setText("Rubro del Emisor");
        jMenuItem218.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem218.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem218ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem218);
        jMenu19.add(jSeparator186);

        jMenuItem219.setText("Sociedad Emisora");
        jMenuItem219.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem219.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem219ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem219);
        jMenu19.add(jSeparator187);

        jMenuItem220.setText("Tipo de Acciones");
        jMenuItem220.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem220.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem220ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem220);
        jMenu19.add(jSeparator188);

        jMenuItem222.setText("Instrumentos");
        jMenuItem222.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem222.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem222ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem222);
        jMenu19.add(jSeparator189);

        jMenuItem221.setText("Títulos");
        jMenuItem221.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem221.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem221ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem221);
        jMenu19.add(jSeparator190);

        jMenuItem223.setText("Calificadoras de Riesgo");
        jMenuItem223.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem223.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem223ActionPerformed(evt);
            }
        });
        jMenu19.add(jMenuItem223);

        jMenu18.add(jMenu19);
        jMenu18.add(jSeparator184);

        jMenuItem181.setText("Captación de Fondos");
        jMenuItem181.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem181.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem181ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem181);
        jMenu18.add(jSeparator193);

        jMenuItem226.setText("Renta Fija");
        jMenuItem226.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem226.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem226ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem226);
        jMenu18.add(jSeparator168);

        jMenuItem252.setText("Vencimientos de Cartera por Emisor");
        jMenuItem252.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem252.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem252ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem252);

        operaciones.add(jMenu18);

        jMenuBar1.add(operaciones);

        finanzas.setText("Finanzas");
        finanzas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem26.setText("Cuentas Bancarias");
        jMenuItem26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        finanzas.add(jMenuItem26);
        finanzas.add(jSeparator15);

        jMenuItem27.setText("Depósitos");
        jMenuItem27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        finanzas.add(jMenuItem27);
        finanzas.add(jSeparator16);

        jMenuItem28.setText("Extracciones");
        jMenuItem28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        finanzas.add(jMenuItem28);
        finanzas.add(jSeparator17);

        jMenuItem208.setText("Transferencias / Reposiciones");
        jMenuItem208.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem208.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem208ActionPerformed(evt);
            }
        });
        finanzas.add(jMenuItem208);
        finanzas.add(jSeparator20);

        jMenuItem30.setText("Rendición de Gastos");
        jMenuItem30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        finanzas.add(jMenuItem30);
        finanzas.add(jSeparator19);

        jMenuItem34.setText("Extracto Bancario");
        jMenuItem34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        finanzas.add(jMenuItem34);
        finanzas.add(jSeparator171);

        jMenuItem31.setText("Informe Depósitos/Extracciones");
        jMenuItem31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        finanzas.add(jMenuItem31);
        finanzas.add(jSeparator53);

        jMenu6.setText("Clientes");
        jMenu6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem44.setText("Cartera de Clientes");
        jMenuItem44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem44);
        jMenu6.add(jSeparator63);

        jMenuItem73.setText("Resumen de Saldos x Operación");
        jMenuItem73.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem73ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem73);
        jMenu6.add(jSeparator66);

        jMenuItem45.setText("Detalle de Saldos x Operación");
        jMenuItem45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem45);

        jMenuItem247.setText("Detalle de Saldos - Incluye Cancelados");
        jMenuItem247.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem247.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem247ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem247);
        jMenu6.add(jSeparator67);

        jMenuItem18.setText("Facturas Pendientes");
        jMenuItem18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem18);
        jMenu6.add(jSeparator177);

        jMenuItem212.setText("Resumen de Saldos a la Fecha");
        jMenuItem212.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem212.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem212ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem212);
        jMenu6.add(jSeparator178);

        jMenuItem255.setText("Resumen de Saldos a la Fecha x RUC");
        jMenuItem255.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem255.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem255ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem255);
        jMenu6.add(jSeparator203);

        jMenuItem213.setText("Detalle de Saldos a la Fecha");
        jMenuItem213.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem213.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem213ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem213);
        jMenu6.add(jSeparator111);

        jMenuItem147.setText("Extracto de Cliente");
        jMenuItem147.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem147.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem147ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem147);
        jMenu6.add(jSeparator26);

        jMenu2.setText("Deudores en Mora");
        jMenu2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem46.setText("Morosidad por Cliente");
        jMenuItem46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem46);
        jMenu2.add(jSeparator30);

        jMenuItem47.setText("Morosidad por Asesor");
        jMenuItem47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem47ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem47);
        jMenu2.add(jSeparator31);

        jMenuItem48.setText("Morosidad por Inversionista");
        jMenuItem48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu2.add(jMenuItem48);
        jMenu2.add(jSeparator38);

        jMenuItem55.setText("Resumen de Morosidad x Plazo");
        jMenuItem55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem55ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem55);

        jMenu6.add(jMenu2);
        jMenu6.add(jSeparator68);

        jMenu3.setText("Vencimientos");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem52.setText("Vencimientos x Comprobantes");
        jMenuItem52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem52ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem52);
        jMenu3.add(jSeparator35);

        jMenuItem49.setText("Vencimientos x Clientes");
        jMenuItem49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem49ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem49);
        jMenu3.add(jSeparator36);

        jMenuItem53.setText("Vencimientos x RUC");
        jMenuItem53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem53ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem53);
        jMenu3.add(jSeparator33);

        jMenuItem50.setText("Vencimientos x Asesor");
        jMenuItem50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu3.add(jMenuItem50);
        jMenu3.add(jSeparator34);

        jMenuItem51.setText("Vencimientos x Giraduría");
        jMenuItem51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem51);

        jMenu6.add(jMenu3);

        finanzas.add(jMenu6);
        finanzas.add(jSeparator69);

        jMenu7.setText("Proveedores");
        jMenu7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu7ActionPerformed(evt);
            }
        });

        jMenuItem103.setText("Pago de Facturas");
        jMenuItem103.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem103.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem103ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem103);
        jMenu7.add(jSeparator120);

        jMenuItem158.setText("Resumen de Saldos");
        jMenuItem158.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem158.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem158ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem158);
        jMenu7.add(jSeparator121);

        jMenuItem159.setText("Detalle de Saldos");
        jMenuItem159.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem159.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem159ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem159);
        jMenu7.add(jSeparator122);

        jMenuItem148.setText("Extracto de Proveedores");
        jMenuItem148.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem148.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem148ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem148);
        jMenu7.add(jSeparator182);

        jMenuItem160.setText("Vencimientos de Facturas");
        jMenuItem160.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem160.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem160ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem160);
        jMenu7.add(jSeparator123);

        jMenu15.setText("Informe de Pagos");
        jMenu15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem161.setText("Resumen x Sucursal");
        jMenuItem161.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem161.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem161ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem161);
        jMenu15.add(jSeparator124);

        jMenuItem162.setText("Resumen x Proveedor");
        jMenuItem162.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem162.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem162ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem162);
        jMenu15.add(jSeparator125);

        jMenuItem163.setText("Detalle x Sucursal");
        jMenuItem163.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem163.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem163ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem163);
        jMenu15.add(jSeparator126);

        jMenuItem164.setText("Detalle x Proveedor");
        jMenuItem164.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem164.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem164ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem164);

        jMenu7.add(jMenu15);

        finanzas.add(jMenu7);

        jMenuBar1.add(finanzas);

        cobranzas.setText("Cobranzas");
        cobranzas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem175.setText("Cobradores");
        jMenuItem175.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem175.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem175ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem175);
        cobranzas.add(jSeparator135);

        jMenuItem90.setText("Cajas");
        jMenuItem90.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem90ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem90);
        cobranzas.add(jSeparator56);

        jMenuItem38.setText("Registro de Cobros");
        jMenuItem38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem38);
        cobranzas.add(jSeparator181);

        jMenuItem39.setText("Gestión de Mora");
        jMenuItem39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem39);
        cobranzas.add(jSeparator24);

        jMenuItem41.setText("Cobranzas por Servicios-Comprobantes");
        jMenuItem41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem41);
        cobranzas.add(jSeparator25);

        jMenuItem42.setText("Cobranzas por Clientes");
        jMenuItem42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem42);
        cobranzas.add(jSeparator6);

        jMenuItem217.setText("Cobranzas por Cobrador");
        jMenuItem217.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem217.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem217ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem217);
        cobranzas.add(jSeparator118);

        jMenuItem156.setText("Cobranzas por Usuarios");
        jMenuItem156.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem156.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem156ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem156);
        cobranzas.add(jSeparator65);

        jMenuItem97.setText("Cobranzas por Caja");
        jMenuItem97.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem97.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem97ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem97);
        cobranzas.add(jSeparator175);

        jMenuItem43.setText("Cobranzas por Valores");
        jMenuItem43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem43);
        cobranzas.add(jSeparator37);

        jMenuItem54.setText("Cobranzas por Categoría de Clientes");
        jMenuItem54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem54ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem54);
        cobranzas.add(jSeparator44);

        jMenuItem264.setText("Resumen de Cobranzas");
        jMenuItem264.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem264.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem264ActionPerformed(evt);
            }
        });
        cobranzas.add(jMenuItem264);

        jMenuBar1.add(cobranzas);

        asociaciones.setText("Asociaciones");
        asociaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem61.setText("Giradurías");
        jMenuItem61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem61ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem61);

        jMenuItem63.setText("Casas Comerciales");
        jMenuItem63.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem63ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem63);

        jMenu14.setText("Ordenes de Crédito");
        jMenu14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem64.setText("Emisión");
        jMenuItem64.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem64ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem64);
        jMenu14.add(jSeparator100);

        jMenuItem95.setText("Reporte de Emisiones");
        jMenuItem95.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem95ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem95);
        jMenu14.add(jSeparator101);

        jMenuItem71.setText("Reportes de Emisión por Giraduría");
        jMenuItem71.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem71ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem71);
        jMenu14.add(jSeparator102);

        jMenuItem72.setText("Reportes de Emisión por Fecha");
        jMenuItem72.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem72ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem72);
        jMenu14.add(jSeparator103);

        jMenuItem137.setText("Pagarés Vencidos");
        jMenuItem137.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem137.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem137ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem137);
        jMenu14.add(jSeparator105);

        jMenuItem98.setText("Auditar Ordenes de Crédito");
        jMenuItem98.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem98ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem98);

        asociaciones.add(jMenu14);

        jMenu13.setText("Ordenes de Compra");
        jMenu13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem68.setText("Emisión de Ordenes de Compra");
        jMenuItem68.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem68ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem68);
        jMenu13.add(jSeparator97);

        jMenuItem94.setText("Reporte por Fecha de Emisión");
        jMenuItem94.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem94.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem94ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem94);
        jMenu13.add(jSeparator22);

        jMenuItem33.setText("Reporte por Fecha de Proceso");
        jMenuItem33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem33);
        jMenu13.add(jSeparator98);

        jMenuItem136.setText("Pagarés Vencidos");
        jMenuItem136.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem136.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem136ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem136);
        jMenu13.add(jSeparator99);

        jMenuItem20.setText("Auditoría");
        jMenuItem20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem20);

        asociaciones.add(jMenu13);

        prestamos.setText("Préstamos");
        prestamos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        prestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prestamosActionPerformed(evt);
            }
        });
        asociaciones.add(prestamos);

        jMenuItem74.setText("Contratos");
        jMenuItem74.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem74ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem74);

        jMenuItem115.setText("Contratos-Servicios Emitidos");
        jMenuItem115.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem115.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem115ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem115);

        jMenuItem107.setText("Descuentos Varios");
        jMenuItem107.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem107ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem107);

        jMenuItem253.setText("Rescisiones");
        jMenuItem253.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem253.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem253ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem253);

        jMenuItem6.setText("Aportes - Cuota Social - Solidaridad");
        jMenuItem6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem6);

        jMenuItem202.setText("Estado de Cuenta - Detallado");
        jMenuItem202.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem202.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem202ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem202);

        jMenuItem89.setText("Estado de Cuenta  Resumido x Vencimiento");
        jMenuItem89.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem89.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem89ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem89);

        jMenuItem29.setText("Estado de Cuenta Resumido x Emisión");
        jMenuItem29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem29);

        jMenu20.setText("Vencimientos de Casas Comerciales");
        jMenu20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem256.setText("Detalle por Entidad Comercial");
        jMenuItem256.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem256.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem256ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem256);
        jMenu20.add(jSeparator204);

        jMenuItem257.setText("Resumen por Entidad Comercial");
        jMenuItem257.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem257.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem257ActionPerformed(evt);
            }
        });
        jMenu20.add(jMenuItem257);

        asociaciones.add(jMenu20);

        jMenuItem67.setText("Vencimientos por Giradurías");
        jMenuItem67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem67ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem67);

        jMenuItem62.setText("Cobranzas x Servicio");
        jMenuItem62.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem62ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem62);

        jMenuItem66.setText("Actualizar Cuentas para Débitos");
        jMenuItem66.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem66ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem66);

        jMenuItem70.setText("Planilla de Gestión de Cobranza");
        jMenuItem70.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem70ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem70);

        jMenuItem114.setText("Extracto de Descuentos");
        jMenuItem114.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem114.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem114ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem114);

        jMenuItem35.setText("Extracto Detallado (Descuentos-Saldos)");
        jMenuItem35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem35);

        jMenuItem32.setText("Recibos por Giraduría");
        jMenuItem32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem32);

        jMenuItem153.setText("Ajuste de Aportes");
        jMenuItem153.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem153.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem153ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem153);

        jMenuItem157.setText("Resumen de Saldos de Aportes");
        jMenuItem157.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem157.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem157ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem157);

        jMenuItem185.setText("Asignar Plazos de Gracia");
        jMenuItem185.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem185ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem185);

        jMenuItem216.setText("Generar Seguros");
        jMenuItem216.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem216.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem216ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem216);

        jMenuItem251.setText("Planilla Casas Comerciales");
        jMenuItem251.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem251.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem251ActionPerformed(evt);
            }
        });
        asociaciones.add(jMenuItem251);

        jMenuBar1.add(asociaciones);

        inmobiliaria.setText("Inmobiliaria");
        inmobiliaria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenu4.setText("Tablas");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem82.setText("Paises");
        jMenuItem82.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem82ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem82);

        jMenuItem83.setText("Departamentos");
        jMenuItem83.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem83ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem83);

        jMenuItem84.setText("Barrios");
        jMenuItem84.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem84ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem84);

        jMenuItem85.setText("Localidades");
        jMenuItem85.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem85ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem85);

        jMenuItem86.setText("Tipo de Documentos");
        jMenuItem86.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem86ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem86);

        jMenuItem87.setText("Tipo de Inmuebles");
        jMenuItem87.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem87ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem87);

        inmobiliaria.add(jMenu4);
        inmobiliaria.add(jSeparator59);

        jMenuItem88.setText(" Propietarios");
        jMenuItem88.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem88ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem88);
        inmobiliaria.add(jSeparator57);

        jMenuItem91.setText("Inmuebles");
        jMenuItem91.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem91ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem91);
        inmobiliaria.add(jSeparator46);

        jMenuItem99.setText("Unidades");
        jMenuItem99.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem99ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem99);
        inmobiliaria.add(jSeparator43);

        jMenuItem105.setText("Clasificar Tipo de Contratos");
        jMenuItem105.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem105ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem105);
        inmobiliaria.add(jSeparator51);

        jMenuItem100.setText("Solicitudes de Locación");
        jMenuItem100.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem100ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem100);
        inmobiliaria.add(jSeparator52);

        jMenuItem155.setText("Clasificación de Gastos");
        jMenuItem155.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem155.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem155ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem155);
        inmobiliaria.add(jSeparator163);

        jMenuItem106.setText("Gastos por Unidades");
        jMenuItem106.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem106.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem106ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem106);
        inmobiliaria.add(jSeparator136);

        jMenuItem176.setText("Cobranzas de Alquileres");
        jMenuItem176.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem176.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem176ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem176);
        inmobiliaria.add(jSeparator132);

        jMenuItem201.setText("Registro de Saldos");
        jMenuItem201.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem201.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem201ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem201);
        inmobiliaria.add(jSeparator117);

        jMenuItem179.setText("Informe de Cobranza por Propietarios");
        jMenuItem179.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem179.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem179ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem179);
        inmobiliaria.add(jSeparator141);

        jMenuItem180.setText("Vencimientos de Alquileres por Propietarios");
        jMenuItem180.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem180ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem180);
        inmobiliaria.add(jSeparator142);

        jMenuItem184.setText("Alquileres pendientes por Locatarios");
        jMenuItem184.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem184.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem184ActionPerformed(evt);
            }
        });
        inmobiliaria.add(jMenuItem184);
        inmobiliaria.add(jSeparator145);

        jMenu17.setText("Loteamientos");
        jMenu17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem172.setText("Registrar Loteamientos");
        jMenuItem172.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem172.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem172ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem172);
        jMenu17.add(jSeparator133);

        jMenuItem182.setText("Registrar Manzanas");
        jMenuItem182.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem182ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem182);

        jSeparator144.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu17.add(jSeparator144);

        jMenuItem173.setText("Registrar Lotes");
        jMenuItem173.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem173.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem173ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem173);
        jMenu17.add(jSeparator134);

        jMenuItem174.setText("Registrar Venta de Lotes");
        jMenuItem174.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem174.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem174ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem174);

        inmobiliaria.add(jMenu17);

        jMenuBar1.add(inmobiliaria);

        contabilidad.setText("Contabilidad");
        contabilidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem138.setText("Plan de Cuentas");
        jMenuItem138.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem138.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem138ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem138);
        contabilidad.add(jSeparator104);

        jMenuItem139.setText("Plan de Cuentas SET");
        jMenuItem139.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem139.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem139ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem139);
        contabilidad.add(jSeparator106);

        jMenuItem261.setText("Centros de Costo");
        jMenuItem261.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem261.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem261ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem261);
        contabilidad.add(jSeparator21);

        jMenuItem140.setText("Asientos Diarios");
        jMenuItem140.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem140.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem140ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem140);
        contabilidad.add(jSeparator107);

        jMenuItem141.setText("Libro Diario");
        jMenuItem141.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem141.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem141ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem141);
        contabilidad.add(jSeparator108);

        jMenuItem143.setText("Registro de Operaciones");
        jMenuItem143.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem143.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem143ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem143);
        contabilidad.add(jSeparator109);

        jMenuItem144.setText("Libro Mayor");
        jMenuItem144.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem144.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem144ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem144);
        contabilidad.add(jSeparator110);

        jMenuItem146.setText("Balance General");
        jMenuItem146.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem146.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem146ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem146);
        contabilidad.add(jSeparator112);

        jMenuItem149.setText("Cuadro de Resultados");
        jMenuItem149.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem149.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem149ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem149);
        contabilidad.add(jSeparator143);

        jMenuItem262.setText("Resultados por Centros de Costo");
        jMenuItem262.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem262ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem262);
        contabilidad.add(jSeparator23);

        jMenuItem150.setText("Generar Asientos Contables");
        jMenuItem150.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem150.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem150ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem150);
        contabilidad.add(jSeparator113);

        jMenuItem151.setText("Foliación de Formularios");
        jMenuItem151.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem151.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem151ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem151);
        contabilidad.add(jSeparator32);

        jMenuItem263.setText("Configuración Contable");
        jMenuItem263.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem263.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem263ActionPerformed(evt);
            }
        });
        contabilidad.add(jMenuItem263);

        jMenuBar1.add(contabilidad);

        rrhh.setText("RR.HH");
        rrhh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tablasrrhh.setText("Tablas ");
        tablasrrhh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem227.setText("Sucursales");
        jMenuItem227.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem227.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem227ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem227);
        tablasrrhh.add(jSeparator194);

        jMenuItem228.setText("Nacionalidades");
        jMenuItem228.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem228.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem228ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem228);
        tablasrrhh.add(jSeparator195);

        jMenuItem229.setText("Localidades");
        jMenuItem229.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem229.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem229ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem229);
        tablasrrhh.add(jSeparator196);

        jMenuItem230.setText("Departamentos");
        jMenuItem230.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem230.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem230ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem230);
        tablasrrhh.add(jSeparator197);

        jMenuItem231.setText("Secciones");
        jMenuItem231.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem231.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem231ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem231);
        tablasrrhh.add(jSeparator198);

        jMenuItem232.setText("Perfil de Cargos");
        jMenuItem232.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem232.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem232ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem232);
        tablasrrhh.add(jSeparator199);

        jMenuItem233.setText("Profesiones");
        jMenuItem233.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem233.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem233ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem233);
        tablasrrhh.add(jSeparator200);

        jMenuItem234.setText("Tipo de Contratos");
        jMenuItem234.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem234.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem234ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem234);
        tablasrrhh.add(jSeparator201);

        jMenuItem235.setText("Motivos de Ausencia");
        jMenuItem235.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem235.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem235ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem235);
        tablasrrhh.add(jSeparator202);

        jMenuItem236.setText("Concepto Salarios");
        jMenuItem236.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem236.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem236ActionPerformed(evt);
            }
        });
        tablasrrhh.add(jMenuItem236);

        rrhh.add(tablasrrhh);

        jMenuItem238.setText("Vacancias");
        jMenuItem238.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem238.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem238ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem238);

        jMenuItem239.setText("LLamadas a Concurso");
        jMenuItem239.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem239.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem239ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem239);

        jMenuItem240.setText("Registrar Postulantes");
        jMenuItem240.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem240.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem240ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem240);

        jMenuItem171.setText("Ficha del Personal");
        jMenuItem171.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem171.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem171ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem171);

        jMenuItem196.setText("Asistencias");
        jMenuItem196.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem196ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem196);

        jMenuItem40.setText("Créditos");
        jMenuItem40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem40);

        jMenuItem191.setText("Vacaciones");
        jMenuItem191.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem191.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem191ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem191);

        jMenuItem192.setText("Generar Horas Extras");
        jMenuItem192.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem192.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem192ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem192);

        jMenuItem193.setText("Ausencias");
        jMenuItem193.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem193.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem193ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem193);

        jMenuItem189.setText("Débitos");
        jMenuItem189.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem189.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem189ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem189);

        Anticipos.setText("Anticipos");
        Anticipos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Anticipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnticiposActionPerformed(evt);
            }
        });
        rrhh.add(Anticipos);

        jMenuItem267.setText("Embargos");
        jMenuItem267.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem267.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem267ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem267);

        jMenuItem190.setText("Generar Llegadas Tardías");
        jMenuItem190.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem190.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem190ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem190);

        jMenuItem242.setText("Registrar Sanciones");
        jMenuItem242.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem242.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem242ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem242);

        jMenuItem241.setText("Registrar Permisos");
        jMenuItem241.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem241.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem241ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem241);

        jMenuItem243.setText("Registrar Desvinculaciones");
        jMenuItem243.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem243.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem243ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem243);

        jMenuItem205.setText("Generación de Planillas de Salario");
        jMenuItem205.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem205.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem205ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem205);

        jMenuItem206.setText("Planilla de Salarios");
        jMenuItem206.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem206.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem206ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem206);

        jMenuItem207.setText("Recibo de Salarios");
        jMenuItem207.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem207.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem207ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem207);

        jMenuItem225.setText("Anticipo de Aguinaldos");
        jMenuItem225.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem225.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem225ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem225);

        jMenuItem248.setText("Carga Manual Aguinaldos");
        jMenuItem248.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem248.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem248ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem248);

        jMenuItem244.setText("Generar Aguinaldos");
        jMenuItem244.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem244.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem244ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem244);

        jMenuItem245.setText("Planilla de Aguinaldos");
        jMenuItem245.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem245.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem245ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem245);

        jMenuItem246.setText("Recibo Aguinaldos");
        jMenuItem246.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem246ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem246);

        jMenuItem265.setText("Extracto de Funcionarios");
        jMenuItem265.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem265.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem265ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem265);

        jMenuItem266.setText("Resumen de Saldos");
        jMenuItem266.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem266.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem266ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem266);

        Informes.setText("Informes");
        Informes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem166.setText("Créditos ");
        jMenuItem166.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem166.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem166ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem166);
        Informes.add(jSeparator127);

        jMenuItem167.setText("Anticipos");
        jMenuItem167.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem167.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem167ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem167);
        Informes.add(jSeparator128);

        jMenuItem168.setText("Descuentos");
        jMenuItem168.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem168.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem168ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem168);
        Informes.add(jSeparator129);

        jMenuItem169.setText("Llegadas Tardías");
        jMenuItem169.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem169.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem169ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem169);
        Informes.add(jSeparator131);

        jMenuItem194.setText("Ausencias");
        jMenuItem194.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem194.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem194ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem194);
        Informes.add(jSeparator150);

        jMenuItem195.setText("Asistencias");
        jMenuItem195.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem195.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem195ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem195);
        Informes.add(jSeparator152);

        jMenuItem200.setText("Horas Extras");
        jMenuItem200.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem200.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem200ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem200);
        Informes.add(jSeparator155);

        jMenuItem237.setText("Vacaciones");
        jMenuItem237.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem237.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem237ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem237);

        rrhh.add(Informes);

        jMenuBar1.add(rrhh);

        herramientas.setText("Herramientas");
        herramientas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem56.setText("Actualizar WEB");
        jMenuItem56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem56ActionPerformed(evt);
            }
        });
        herramientas.add(jMenuItem56);
        herramientas.add(jSeparator39);

        jMenuItem57.setText("Usuarios del Sistema");
        jMenuItem57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem57ActionPerformed(evt);
            }
        });
        herramientas.add(jMenuItem57);
        herramientas.add(jSeparator40);

        jMenuItem58.setText("Generar Facturas");
        jMenuItem58.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem58ActionPerformed(evt);
            }
        });
        herramientas.add(jMenuItem58);

        jMenuItem272.setText("Generar Rifas");
        jMenuItem272.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem272.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem272ActionPerformed(evt);
            }
        });
        herramientas.add(jMenuItem272);
        herramientas.add(jSeparator173);

        jMenuItem210.setText("Transferir Mercaderías a Sucursales");
        jMenuItem210.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem210.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem210ActionPerformed(evt);
            }
        });
        herramientas.add(jMenuItem210);
        herramientas.add(jSeparator207);

        jMenuItem259.setText("Importar Ventas");
        jMenuItem259.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem259.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem259ActionPerformed(evt);
            }
        });
        herramientas.add(jMenuItem259);

        jMenuBar1.add(herramientas);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new sucursales().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new asesores().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new Clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        new comprobantes().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        new categoria_clientes().setVisible(true);

// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        this.cerrarVentana();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void cerrarVentana() {
        int opcion = JOptionPane.showOptionDialog(null, "Está seguro de Salir del Sistema ?",
                "Mensaje del Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (opcion == 0) {
            System.exit(0);
        }
    }

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        new bancosplaza().setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        new localidades().setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        new monedas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        new proveedores().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        new gastos().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        new libroventaconsolidado().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
//      new prestamos().setVisible(true); //PRECIOS BAJOS ---- CREDI CLASS
//        new prestamos_aso().setVisible(true); // FAPASA
       new prestamos_incresa().setVisible(true); // INCRESA

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        new tipo_garantias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        new depositos_bancarios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        new extracto_bancario().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        new prestamosxfecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem36ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        new destino_prestamos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        new rubro_compras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        new cuentas_bancarias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        new extracciones_bancarias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem37ActionPerformed
        new prestamosxasesor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem37ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        new descuento_cheques().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem47ActionPerformed

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem51ActionPerformed
        new vencimientoxgiraduria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem51ActionPerformed

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem52ActionPerformed
        //   new vencimiento_cuotas().setVisible(true);
        new vencimientosxfecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem52ActionPerformed

    private void jMenuItem49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem49ActionPerformed
        new vencimientos_cuotas_clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem49ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        new cobranzasxcomprobantes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem54ActionPerformed
        new cobranzasxcategoria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem54ActionPerformed

    private void jMenuItem55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem55ActionPerformed
        new saldoclientexplazo().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem55ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
//        if (Integer.valueOf(Config.cNivelUsuario) < 3) {
            if (Config.nMultiCaja == 0) {
                new ventas().setVisible(true);
            } else if (Config.nMultiCaja == 2) {
                new ventas_mercaderias().setVisible(true);
            } else if (Config.nMultiCaja == 3) {
                new ventas_mercaderias().setVisible(true);
            } else if (Config.nMultiCaja == 4) {
                new ventas_restaurantes().setVisible(true);
            } else if (Config.nMultiCaja == 7) {
                new ventas_fapasa().setVisible(true);
            } else if (Config.nMultiCaja == 1) {
                new ventas_mercaderias().setVisible(true);
            } else if (Config.nMultiCaja == 5) {
                new ventas_mercaderias().setVisible(true);
            } else if (Config.nMultiCaja == 8) {
                new ventas_mercaderias().setVisible(true);
            } else if (Config.nMultiCaja == 9) {
                new ventas_mercaderias_plazodias().setVisible(true);
            } else if (Config.nMultiCaja == 10) {
                new ventas_mercaderias_comentarios().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
            }
  //      }
            // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void SolicitudesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolicitudesActionPerformed
        new proformaprestamos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_SolicitudesActionPerformed

    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem46ActionPerformed
        new cobrosxclientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem46ActionPerformed

    private void RetencionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetencionesActionPerformed
        new nota_credito_ventas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_RetencionesActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
//         new cobranzaspb().setVisible(true); // PRECIOS BAJOS FAPASA
//      new cobranzas().setVisible(true); // PUERTO SEGURO

          new cobros().setVisible(true); 
/*      if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new cobros().setVisible(true);
        } else {
            new cobrosafuma().setVisible(true); //AFUMA
        }*/
        //    new cobrosafuma().setVisible(true); //AFUMA
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem56ActionPerformed
        new actualizar_web().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem56ActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem43ActionPerformed
        new resumen_cobranza_sucursal().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        // A + B 
        // new saldosxclientes().setVisible(true);
        // PRECIOS BAJOS
        new saldosxclientespb().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem57ActionPerformed
//        new usuarios().setVisible(true); standart
        new usuariosafuma().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem57ActionPerformed

    private void jMenuItem58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem58ActionPerformed
        new generar_facturas_aso().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem58ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        new resumen_cartera_clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        new cobrosxclientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem42ActionPerformed

    private void jMenuItem59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem59ActionPerformed
        new abogados().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem59ActionPerformed

    private void jMenuItem60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem60ActionPerformed
        new cheques_negociados().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem60ActionPerformed

    private void jMenuItem61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem61ActionPerformed
        new giradurias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem61ActionPerformed

    private void jMenuItem63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem63ActionPerformed
        new casas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem63ActionPerformed

    private void jMenuItem64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem64ActionPerformed
//        new ordenes_credito().setVisible(true); //FAPASA
//      new ordenes_credito_crediseguro().setVisible(true); //CREDITO 
        new ordenes_creditoafuma().setVisible(true); //Afuma
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem64ActionPerformed

    private void jMenuItem65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem65ActionPerformed
        new barrios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem65ActionPerformed

    private void jMenuItem66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem66ActionPerformed
        new actualizargiraduria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem66ActionPerformed

    private void jMenuItem67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem67ActionPerformed
        new vencimientoxgiraduria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem67ActionPerformed

    private void jMenuItem68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem68ActionPerformed
        new ordenes_de_compras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem68ActionPerformed

    private void jMenuItem69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem69ActionPerformed
        new prestamosxfactura().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem69ActionPerformed

    private void jMenuItem70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem70ActionPerformed
        new planillagiraduria().setVisible(true);
    }//GEN-LAST:event_jMenuItem70ActionPerformed

    private void jMenuItem71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem71ActionPerformed
        new ordenescreditoxgiraduria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem71ActionPerformed

    private void jMenuItem72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem72ActionPerformed
        new ordenescreditoxfecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem72ActionPerformed

    private void jMenuItem73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem73ActionPerformed
        new saldoclientexfecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem73ActionPerformed

    private void jMenuItem74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem74ActionPerformed
        new contratos_servicios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem74ActionPerformed

    private void jMenuItem75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem75ActionPerformed
        new paises().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem75ActionPerformed

    private void jMenuItem76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem76ActionPerformed
        new familias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem76ActionPerformed

    private void jMenuItem77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem77ActionPerformed
        new rubros().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem77ActionPerformed

    private void jMenuItem78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem78ActionPerformed
        new marcas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem78ActionPerformed

    private void jMenuItem79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem79ActionPerformed
        new ubicaciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem79ActionPerformed

    private void jMenuItem80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem80ActionPerformed
        new unidades().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem80ActionPerformed

    private void jMenuItem81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem81ActionPerformed
        new productos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem81ActionPerformed

    private void jMenuItem85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem85ActionPerformed
        new localidades().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem85ActionPerformed

    private void jMenuItem82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem82ActionPerformed
        new paises().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem82ActionPerformed

    private void jMenuItem83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem83ActionPerformed
        new tabdepartamentos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem83ActionPerformed

    private void jMenuItem86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem86ActionPerformed
        new tipo_documentos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem86ActionPerformed

    private void jMenuItem87ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem87ActionPerformed
        new tipo_inmuebles().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem87ActionPerformed

    private void jMenuItem88ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem88ActionPerformed
        new propietarios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem88ActionPerformed

    private void jMenuItem89ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem89ActionPerformed
        new saldosocioxvencimiento().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem89ActionPerformed

    private void jMenuItem90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem90ActionPerformed
        new cajas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem90ActionPerformed

    private void jMenuItem84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem84ActionPerformed
        new barrios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem84ActionPerformed

    private void jMenuItem91ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem91ActionPerformed
        new inmuebles().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem91ActionPerformed

    private void jMenuItem92ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem92ActionPerformed
        new ajuste_mercaderias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem92ActionPerformed

    private void jMenuItem93ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem93ActionPerformed
        new salida_de_mercaderias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem93ActionPerformed

    private void jMenuItem94ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem94ActionPerformed
        new ordenescompraxgiraduria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem94ActionPerformed

    private void jMenuItem95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem95ActionPerformed
        new ordenescreditoasoxgiraduria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem95ActionPerformed

    private void jMenuItem96ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem96ActionPerformed
        new transferencia_de_mercaderias().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem96ActionPerformed

    private void jMenuItem97ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem97ActionPerformed
        new cobranzasxcaja().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem97ActionPerformed

    private void jMenuItem98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem98ActionPerformed
        new ordenescreditoauditoria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem98ActionPerformed

    private void jMenuItem100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem100ActionPerformed
        new solicitud_locaciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem100ActionPerformed

    private void jMenuItem99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem99ActionPerformed
        new edificios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem99ActionPerformed

    private void jMenuItem101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem101ActionPerformed
        new compras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem101ActionPerformed

    private void jMenuItem102ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem102ActionPerformed
        new nota_credito_compras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem102ActionPerformed

    private void jMenuItem62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem62ActionPerformed
        new cierrescobradosxservicios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem62ActionPerformed

    private void jMenuItem103ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem103ActionPerformed
        new pagos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem103ActionPerformed

    private void jMenuItem104ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem104ActionPerformed
        new resumen_ventasconsolidado().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem104ActionPerformed

    private void jMenu7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu7ActionPerformed
        new pagos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu7ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        new ordenescomprauditoria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem105ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem105ActionPerformed
        new tipo_contratos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem105ActionPerformed

    private void jMenuItem106ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem106ActionPerformed
        new gasto_unidades().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem106ActionPerformed

    private void jMenuItem107ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem107ActionPerformed
        new cabecera_descuentos_varios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem107ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new generar_aportes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new libroventaxsucursal().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem108ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem108ActionPerformed
        new libroventaxvendedores().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem108ActionPerformed

    private void jMenuItem109ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem109ActionPerformed
        new libroventaxcaja().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem109ActionPerformed

    private void jMenuItem110ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem110ActionPerformed
        new libroventaxcliente().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem110ActionPerformed

    private void jMenuItem112ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem112ActionPerformed
        new Librocompraxsucursal().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem112ActionPerformed

    private void jMenuItem113ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem113ActionPerformed
        new Librocompraxproveedor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem113ActionPerformed

    private void jMenuItem111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem111ActionPerformed
        new Librocompraconsolidado().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem111ActionPerformed

    private void jMenuItem114ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem114ActionPerformed
        new extracto_descuentoxsocio().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem114ActionPerformed

    private void jMenuItem115ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem115ActionPerformed
        new contratosemitidosxcasa().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem115ActionPerformed

    private void jMenuItem116ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem116ActionPerformed

        //           new preventa_repuestos().setVisible(true);
        //               new CargarPreventas().setVisible(true);
        new CargarPreventasRosmar().setVisible(true);
//         new ot_presupuestos_cedro().setVisible(true); //PARA CEDRO MADERAS
//      new pedidosvemay().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem116ActionPerformed

    private void jMenuItem117ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem117ActionPerformed
        new resumen_ventasxsucursal().setVisible(true);
    }//GEN-LAST:event_jMenuItem117ActionPerformed

    private void jMenuItem118ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem118ActionPerformed
        new resumen_ventasxcaja().setVisible(true);
    }//GEN-LAST:event_jMenuItem118ActionPerformed

    private void jMenuItem119ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem119ActionPerformed
        new resumen_ventasxclientes().setVisible(true);
    }//GEN-LAST:event_jMenuItem119ActionPerformed

    private void jMenuItem120ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem120ActionPerformed
        new resumen_ventasxvendedor().setVisible(true);
    }//GEN-LAST:event_jMenuItem120ActionPerformed

    private void jMenuItem121ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem121ActionPerformed
        new resumen_ventasxrubro().setVisible(true);
    }//GEN-LAST:event_jMenuItem121ActionPerformed

    private void jMenuItem125ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem125ActionPerformed
        new resumen_comprasxmarcas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem125ActionPerformed

    private void jMenuItem122ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem122ActionPerformed
        new resumen_comprasxconsolidado().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem122ActionPerformed

    private void jMenuItem123ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem123ActionPerformed
        new resumen_comprasxproveedor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem123ActionPerformed

    private void jMenuItem124ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem124ActionPerformed
        new resumen_comprasxrubro().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem124ActionPerformed

    private void jMenuItem126ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem126ActionPerformed
        new detalle_comprasxconsolidados().setVisible(true);
    }//GEN-LAST:event_jMenuItem126ActionPerformed

    private void jMenuItem127ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem127ActionPerformed
        new detalle_comprasxproveedor().setVisible(true);
    }//GEN-LAST:event_jMenuItem127ActionPerformed

    private void jMenuItem128ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem128ActionPerformed
        new detalle_comprasxrubros().setVisible(true);
    }//GEN-LAST:event_jMenuItem128ActionPerformed

    private void jMenuItem129ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem129ActionPerformed
        new detalle_comprasxmarcas().setVisible(true);
    }//GEN-LAST:event_jMenuItem129ActionPerformed

    private void jMenuItem130ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem130ActionPerformed
        new detalle_ventasxconsolidados().setVisible(true);
    }//GEN-LAST:event_jMenuItem130ActionPerformed

    private void jMenuItem132ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem132ActionPerformed
        new detalle_ventasxcajas().setVisible(true);
    }//GEN-LAST:event_jMenuItem132ActionPerformed

    private void jMenuItem133ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem133ActionPerformed
        new detalle_ventasxclientes().setVisible(true);
    }//GEN-LAST:event_jMenuItem133ActionPerformed

    private void jMenuItem134ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem134ActionPerformed
        new detalle_ventasxvendedor().setVisible(true);
    }//GEN-LAST:event_jMenuItem134ActionPerformed

    private void jMenuItem135ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem135ActionPerformed
        new detalle_ventasxrubros().setVisible(true);
    }//GEN-LAST:event_jMenuItem135ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        new detalle_ventasxproductos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem131ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem131ActionPerformed
        new detalle_comprasxproductos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem131ActionPerformed

    private void jMenuItem136ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem136ActionPerformed
        new ordenescompra_pagares().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem136ActionPerformed

    private void jMenuItem137ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem137ActionPerformed
        new ordenescredito_pagares().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem137ActionPerformed

    private void jMenuItem139ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem139ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem139ActionPerformed

    private void jMenuItem143ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem143ActionPerformed
        try {
            new Registro_de_Operaciones().setVisible(true);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jMenuItem143ActionPerformed

    private void jMenuItem144ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem144ActionPerformed
        new LibroMayor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem144ActionPerformed

    private void jMenuItem140ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem140ActionPerformed
        new asientos_diarios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem140ActionPerformed

    private void jMenuItem138ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem138ActionPerformed
        new PlanCta().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem138ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        new resumen_extracto_bancario().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        new recibos_giraduria().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        new gestion_cobranzas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem39ActionPerformed

    private void jMenuItem153ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem153ActionPerformed
        new saldos_aportes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem153ActionPerformed

    private void jMenuItem154ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem154ActionPerformed
        new tabdep().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem154ActionPerformed

    private void jMenuItem141ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem141ActionPerformed
        try {
            new Libro_Diario().setVisible(true);
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_jMenuItem141ActionPerformed

    private void jMenuItem155ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem155ActionPerformed
        new rubro_compras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem155ActionPerformed

    private void jMenuItem156ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem156ActionPerformed
        new cobranzasxusuario().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem156ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        new recibo_extracto_especial().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem157ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem157ActionPerformed
        new reporte_saldos_aportes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem157ActionPerformed

    private void jMenuItem158ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem158ActionPerformed
        new saldo_proveedor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem158ActionPerformed

    private void jMenuItem159ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem159ActionPerformed
        new detalle_saldo_proveedor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem159ActionPerformed

    private void jMenuItem160ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem160ActionPerformed
        new vencimiento_por_proveedor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem160ActionPerformed

    private void jMenuItem162ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem162ActionPerformed
        new resumen_pagos_proveedor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem162ActionPerformed

    private void jMenuItem164ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem164ActionPerformed
        new detalle_pagos_proveedores().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem164ActionPerformed

    private void jMenuItem161ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem161ActionPerformed
        new resumen_pagos_sucursal().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem161ActionPerformed

    private void jMenuItem163ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem163ActionPerformed
        new detalle_pagos_sucursal().setVisible(true);
    }//GEN-LAST:event_jMenuItem163ActionPerformed

    private void jMenuItem165ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem165ActionPerformed
        new movimiento_de_mercaderias().setVisible(true);
    }//GEN-LAST:event_jMenuItem165ActionPerformed

    private void jMenuItem170ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem170ActionPerformed
        new vencimiento_cartera_cheque().setVisible(true);
    }//GEN-LAST:event_jMenuItem170ActionPerformed

    private void CajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajasActionPerformed
        new cajas().setVisible(true);
    }//GEN-LAST:event_CajasActionPerformed

    private void jMenuItem172ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem172ActionPerformed
        new loteamientos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem172ActionPerformed

    private void jMenuItem175ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem175ActionPerformed
        new cobradores().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem175ActionPerformed

    private void jMenuItem173ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem173ActionPerformed
        new lotes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem173ActionPerformed

    private void jMenuItem176ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem176ActionPerformed
        new cobranza_alquileres().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem176ActionPerformed

    private void jMenuItem146ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem146ActionPerformed
        new Balance_General().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem146ActionPerformed

    private void jMenuItem149ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem149ActionPerformed
        new CuadroDeResultado().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem149ActionPerformed

    private void jMenuItem150ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem150ActionPerformed
        new generar_asiento_contable().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem150ActionPerformed

    private void jMenuItem151ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem151ActionPerformed
        new Foliacion_formulario().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem151ActionPerformed

    private void jMenuItem177ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem177ActionPerformed
        new formas_pago().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem177ActionPerformed

    private void jMenuItem178ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem178ActionPerformed
        if (Config.nMultiCaja == 0) {
            new ingreso_punto_venta_supermercado().setVisible(true);
        } else if (Config.nMultiCaja == 1) {
//          new punto_venta_multicaja_vemay();
//        new ingreso_punto_venta().setVisible(true);
            new ingreso_punto_venta_multicaja().setVisible(true);
        } else if (Config.nMultiCaja == 2) {
            new puntoventa_listaprecios().setVisible(true);
        } else if (Config.nMultiCaja == 3) {
            new ingreso_punto_venta_multicaja().setVisible(true);
        } else if (Config.nMultiCaja == 4) {
            new ingreso_punto_venta_restaurantes().setVisible(true);
        } else if (Config.nMultiCaja == 5) {
            new ingreso_punto_ventacantidad().setVisible(true);
        } else if (Config.nMultiCaja == 6) {
            new ingreso_punto_venta_librerias1().setVisible(true);
        } else if (Config.nMultiCaja == 7) {
            new ingreso_punto_venta_san_andres().setVisible(true);
        } else if (Config.nMultiCaja == 8) {
            new punto_venta_multicaja_vemay().setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem178ActionPerformed

    private void jMenuItem179ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem179ActionPerformed
        new cobranzasxpropietarios().setVisible(true);
    }//GEN-LAST:event_jMenuItem179ActionPerformed

    private void jMenuItem180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem180ActionPerformed
        new cobranzasvencxpropietarios().setVisible(true);
    }//GEN-LAST:event_jMenuItem180ActionPerformed

    private void jMenuItem174ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem174ActionPerformed
        new venta_lotes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem174ActionPerformed

    private void fechaprocesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaprocesoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprocesoFocusGained

    private void jMenuItem182ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem182ActionPerformed
        new manzanas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem182ActionPerformed

    private void jMenuItem183ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem183ActionPerformed
        new inventariorubro().setVisible(true);
    }//GEN-LAST:event_jMenuItem183ActionPerformed

    private void jMenuItem184ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem184ActionPerformed
        new alquilerxlocatario().setVisible(true);
    }//GEN-LAST:event_jMenuItem184ActionPerformed

    private void jMenuItem185ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem185ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new refinanciar_deudas().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem185ActionPerformed

    private void jMenuItem186ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem186ActionPerformed
        new inventariorubroxfecha().setVisible(true);
    }//GEN-LAST:event_jMenuItem186ActionPerformed

    private void jMenuItem187ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem187ActionPerformed
        new kardexProducto().setVisible(true);
    }//GEN-LAST:event_jMenuItem187ActionPerformed

    private void jMenuItem188ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem188ActionPerformed
        if (Config.cNivelUsuario.equals("1")) {
            new resumen_ingreso_sucursal().setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem188ActionPerformed

    private void jMenuItem197ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem197ActionPerformed
        new prestamos_informe_intereses().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem197ActionPerformed

    private void jMenuItem198ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem198ActionPerformed
        new orden_trabajos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem198ActionPerformed

    private void jMenuItem199ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem199ActionPerformed
        new inventarioxfamilia().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem199ActionPerformed

    private void jMenuItem201ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem201ActionPerformed
        new saldos_inmobiliarias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem201ActionPerformed

    private void jMenuItem202ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem202ActionPerformed
        new saldosocioxdetallado().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem202ActionPerformed

    private void jMenuItem203ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem203ActionPerformed
        new inventarioxubicacion().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem203ActionPerformed

    private void jMenuItem204ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem204ActionPerformed
        new inventarioxmarca().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem204ActionPerformed

    private void calendarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarioMouseClicked
        consultarCalendario();
    }//GEN-LAST:event_calendarioMouseClicked

    private void calendarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarioMousePressed
        consultarCalendario();
    }//GEN-LAST:event_calendarioMousePressed

    private void previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousActionPerformed
        retrocederMes();
    }//GEN-LAST:event_previousActionPerformed

    private void monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthActionPerformed
        try {
            if (month.getItemCount() > 0) {
                llenarCalendario();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_monthActionPerformed

    private void upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upActionPerformed
        adelantarMes();
    }//GEN-LAST:event_upActionPerformed

    private void downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downActionPerformed
        retrocederMes();
    }//GEN-LAST:event_downActionPerformed

    private void anhoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_anhoStateChanged
        if (!anho.getValue().toString().equals(anhoInicial)) {
            try {
                anhoInicial = anho.getValue().toString();
                llenarCalendario();
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_anhoStateChanged

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        adelantarMes();
    }//GEN-LAST:event_nextActionPerformed

    private void jMenuItem208ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem208ActionPerformed
        new transferencia_bancarias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem208ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        if (Config.nCentroCosto == 0) {
            new rendicion_de_fondos().setVisible(true);
        } else {
            new rendicion_de_fondos_cuenta().setVisible(true);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void buttonSeven1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven1ActionPerformed
        new Clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSeven1ActionPerformed

    private void buttonSeven2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven2ActionPerformed
        new prestamos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSeven2ActionPerformed

    private void DescuentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescuentosActionPerformed
        new descuento_cheques().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_DescuentosActionPerformed

    private void buttonSeven4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven4ActionPerformed
        new cobrosxclientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSeven4ActionPerformed

    private void buttonSeven5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven5ActionPerformed
        //new cobranzas().setVisible(true);
        new cobros().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSeven5ActionPerformed

    private void buttonSeven6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven6ActionPerformed
        new vencimiento_cuotas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSeven6ActionPerformed

    private void buttonSeven7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven7ActionPerformed
        new cobranzasxcaja().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSeven7ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        new saldosocioxfecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        new ordenescompraxgiraduriaproceso().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem209ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem209ActionPerformed

        new actualizar_stock().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem209ActionPerformed

    private void jMenuItem210ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem210ActionPerformed
        new exportar_transferencias_sucursales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem210ActionPerformed

    private void JMenuItemLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemLibroActionPerformed
        new libroventaretencion().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_JMenuItemLibroActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        new cuotaspendientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem53ActionPerformed
        new vencimientos_cuotas_ruc().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem53ActionPerformed

    private void jMenuItem211ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem211ActionPerformed
        if (Config.nMultiCaja == 1) {
            new ot_presupuestos().setVisible(true); //STANDARD
            //         new ot_presupuestos_cedro().setVisible(true); //PARA CEDRO MADERAS
        } else if (Config.nMultiCaja == 8) {
            new ot_presupuestos_vemay().setVisible(true); //PARA VEMAY
        } else if (Config.nMultiCaja == 9) {
            new ot_presupuestos().setVisible(true); //STANDARD
        }

// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem211ActionPerformed

    private void jMenuItem213ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem213ActionPerformed
        new saldofacturas_alafecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem213ActionPerformed

    private void jMenuItem212ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem212ActionPerformed
        new resumen_saldos_aunafecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem212ActionPerformed

    private void jMenuItem214ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem214ActionPerformed
        new custodias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem214ActionPerformed

    private void jMenuItem215ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem215ActionPerformed
        new detalle_ventas_productos_clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem215ActionPerformed

    private void jMenuItem216ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem216ActionPerformed
        new generar_seguros().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem216ActionPerformed

    private void jMenuItem217ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem217ActionPerformed
        new cobranzasxcobrador().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem217ActionPerformed

    private void jMenuItem147ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem147ActionPerformed
        new extracto_clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem147ActionPerformed

    private void jMenuItem148ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem148ActionPerformed
        new extracto_proveedores().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem148ActionPerformed

    private void jMenuItem181ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem181ActionPerformed
        new captacion_fondos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem181ActionPerformed

    private void jMenuItem218ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem218ActionPerformed
        new rubro_emisores().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem218ActionPerformed

    private void jMenuItem219ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem219ActionPerformed
        new emisores().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem219ActionPerformed

    private void jMenuItem222ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem222ActionPerformed
        new instrumentos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem222ActionPerformed

    private void jMenuItem220ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem220ActionPerformed
        new tipo_acciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem220ActionPerformed

    private void jMenuItem223ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem223ActionPerformed
        new calificadoras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem223ActionPerformed

    private void jMenuItem224ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem224ActionPerformed
        new ajuste_de_compras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem224ActionPerformed

    private void jMenuItem226ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem226ActionPerformed
        new operaciones_renta_fija().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem226ActionPerformed

    private void jMenuItem221ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem221ActionPerformed
        new titulos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem221ActionPerformed

    private void jMenuItem207ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem207ActionPerformed
        new salariosrecibos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem207ActionPerformed

    private void jMenuItem206ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem206ActionPerformed
        new salariosplanilla().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem206ActionPerformed

    private void jMenuItem205ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem205ActionPerformed
        new salariosliquidacion().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem205ActionPerformed

    private void jMenuItem193ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem193ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new ausencias().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem193ActionPerformed

    private void jMenuItem190ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem190ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new llegadas_tardias().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem190ActionPerformed

    private void jMenuItem189ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem189ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new debitosxsalarios().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem189ActionPerformed

    private void jMenuItem225ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem225ActionPerformed
        new anticipo_aguinaldos_vista().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem225ActionPerformed

    private void AnticiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnticiposActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new anticipos().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AnticiposActionPerformed

    private void jMenuItem192ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem192ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new horas_extras().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem192ActionPerformed

    private void jMenuItem191ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem191ActionPerformed
        new vacaciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem191ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        new creditosxsalarios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem196ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem196ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new jornaleros().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem196ActionPerformed

    private void jMenuItem171ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem171ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new ficha_de_empleados().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem171ActionPerformed

    private void jMenuItem236ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem236ActionPerformed
        new concepto_movimiento().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem236ActionPerformed

    private void jMenuItem235ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem235ActionPerformed
        new motivo_ausencias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem235ActionPerformed

    private void jMenuItem234ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem234ActionPerformed
        new clase_contratos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem234ActionPerformed

    private void jMenuItem233ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem233ActionPerformed
        new profesiones().setVisible(true);
    }//GEN-LAST:event_jMenuItem233ActionPerformed

    private void jMenuItem232ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem232ActionPerformed
        new cargos().setVisible(true);
    }//GEN-LAST:event_jMenuItem232ActionPerformed

    private void jMenuItem231ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem231ActionPerformed
        new secciones().setVisible(true);
    }//GEN-LAST:event_jMenuItem231ActionPerformed

    private void jMenuItem230ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem230ActionPerformed
        new departamentos_laborales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem230ActionPerformed

    private void jMenuItem229ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem229ActionPerformed
        new localidadesrrhh().setVisible(true);
    }//GEN-LAST:event_jMenuItem229ActionPerformed

    private void jMenuItem228ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem228ActionPerformed
        new paises().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem228ActionPerformed

    private void jMenuItem227ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem227ActionPerformed
        // TODO add your handling code here:
        new sucursales_rrhh().setVisible(true);
    }//GEN-LAST:event_jMenuItem227ActionPerformed

    private void jMenuItem238ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem238ActionPerformed
        new vacancias_laborales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem238ActionPerformed

    private void jMenuItem239ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem239ActionPerformed
        new concursos_llamados().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem239ActionPerformed

    private void jMenuItem240ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem240ActionPerformed
        new postulantes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem240ActionPerformed

    private void jMenuItem241ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem241ActionPerformed
        new permisos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem241ActionPerformed

    private void jMenuItem242ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem242ActionPerformed
        new sanciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem242ActionPerformed

    private void jMenuItem243ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem243ActionPerformed
        new desvinculaciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem243ActionPerformed

    private void jMenuItem244ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem244ActionPerformed
        new generar_aguinaldos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem244ActionPerformed

    private void jMenuItem245ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem245ActionPerformed
        new aguinaldo_planilla().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem245ActionPerformed

    private void jMenuItem246ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem246ActionPerformed
        new recibo_aguinaldo().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem246ActionPerformed

    private void jMenuItem166ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem166ActionPerformed
        new creditos_salarios_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem166ActionPerformed

    private void jMenuItem167ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem167ActionPerformed
        new anticipos_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem167ActionPerformed

    private void jMenuItem168ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem168ActionPerformed
        new descuentos_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem168ActionPerformed

    private void jMenuItem169ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem169ActionPerformed
        new llegadas_tardias_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem169ActionPerformed

    private void jMenuItem194ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem194ActionPerformed
        new ausencias_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem194ActionPerformed

    private void jMenuItem195ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem195ActionPerformed
        new asistencia_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem195ActionPerformed

    private void jMenuItem200ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem200ActionPerformed
        new horas_extras_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem200ActionPerformed

    private void jMenuItem237ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem237ActionPerformed
        new vacaciones_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem237ActionPerformed

    private void jMenuItem247ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem247ActionPerformed
        new saldocliente_detallado().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem247ActionPerformed

    private void jMenuItem248ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem248ActionPerformed
        new aguinaldos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem248ActionPerformed

    private void jMenuItem249ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem249ActionPerformed
        new resumen_ventasxclientesruc().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem249ActionPerformed

    private void jMenuItem250ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem250ActionPerformed
        new libroventaxusuario().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem250ActionPerformed

    private void jMenuItem251ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem251ActionPerformed
        new planilla_comercios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem251ActionPerformed

    private void jMenuItem252ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem252ActionPerformed
        new resumen_vencimiento_cartera_emisor().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem252ActionPerformed

    private void jMenuItem253ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem253ActionPerformed
        new rescisiones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem253ActionPerformed

    private void jMenuItem254ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem254ActionPerformed
        new anticipo_alumnos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem254ActionPerformed

    private void jMenuItem255ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem255ActionPerformed
        new resumen_saldos_aunafechaxruc().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem255ActionPerformed

    private void jMenuItem256ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem256ActionPerformed
        new vencimientos_casascomerciales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem256ActionPerformed

    private void jMenuItem257ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem257ActionPerformed
        new resumen_vencimientos_casascomerciales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem257ActionPerformed

    private void jMenuItem152ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem152ActionPerformed
        new microcreditos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem152ActionPerformed

    private void jMenuItem258ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem258ActionPerformed
        new configuraciones_cooperativa().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem258ActionPerformed

    private void jMenuItem259ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem259ActionPerformed
        new importar_ventas_sucursales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem259ActionPerformed

    private void prestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestamosActionPerformed
        new prestamos_gimenez_calvo().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_prestamosActionPerformed

    private void jMenuItem260ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem260ActionPerformed
        new situacion_clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem260ActionPerformed

    private void jMenuItem261ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem261ActionPerformed
        new centro_costos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem261ActionPerformed

    private void jMenuItem262ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem262ActionPerformed
        new centro_costo_resultados().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem262ActionPerformed

    private void jMenuItem263ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem263ActionPerformed
        new configuracion_contable().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem263ActionPerformed

    private void jMenuItem264ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem264ActionPerformed
        new resumen_cobranzas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem264ActionPerformed


    private void jMenuItem265ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem265ActionPerformed
        new extracto_funcionarios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem265ActionPerformed

    private void jMenuItem266ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem266ActionPerformed
        new extracto_resumen_funcionarios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem266ActionPerformed

    private void jMenuItem267ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem267ActionPerformed
        new embargos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem267ActionPerformed

    private void jMenuItem268ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem268ActionPerformed
        new orden_compras().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem268ActionPerformed

    private void jMenuItem269ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem269ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new utilidadesxventas().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem269ActionPerformed

    private void jMenuItem270ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem270ActionPerformed
        new resumen_anticipo_alumnos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem270ActionPerformed

    private void jMenuItem271ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem271ActionPerformed
        new ajuste_saldos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem271ActionPerformed

    private void jMenuItem142ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem142ActionPerformed
        new ajuste_saldos_ventas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem142ActionPerformed

    private void jMenuItem145ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem145ActionPerformed
        new detalle_ventasxclientesxfecha().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem145ActionPerformed

    private void jMenuItem272ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem272ActionPerformed
        new generar_rifas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem272ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            Plastic3DLookAndFeel.setCurrentTheme(new ExperienceGreen());
//             UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
//        } catch (Exception e) {
//        }
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {

        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new Portada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Anticipos;
    private javax.swing.JMenuItem Cajas;
    private javax.swing.JMenu Cheques;
    private org.edisoncor.gui.button.ButtonSeven Descuentos;
    private javax.swing.JLabel EtiquetaQR;
    private javax.swing.JMenu Informes;
    private javax.swing.JMenuItem JMenuItemLibro;
    private javax.swing.JMenuItem Retenciones;
    private javax.swing.JMenuItem Solicitudes;
    private javax.swing.JSpinner anho;
    private javax.swing.JMenu asociaciones;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven1;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven2;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven4;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven5;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven6;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven7;
    private javax.swing.JTable calendario;
    private javax.swing.JMenu cobranzas;
    private javax.swing.JMenu compras;
    private javax.swing.JMenu contabilidad;
    private javax.swing.JScrollPane contenedorCalendario;
    private javax.swing.JButton down;
    private javax.swing.JLabel eventos;
    private com.toedter.calendar.JDateChooser fechaproceso;
    private javax.swing.JMenu finanzas;
    private org.edisoncor.gui.panel.PanelImage fondo;
    private javax.swing.JMenu herramientas;
    private javax.swing.JMenu inmobiliaria;
    private javax.swing.JLabel jLUser;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenu jMenu19;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu20;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem100;
    private javax.swing.JMenuItem jMenuItem101;
    private javax.swing.JMenuItem jMenuItem102;
    private javax.swing.JMenuItem jMenuItem103;
    private javax.swing.JMenuItem jMenuItem104;
    private javax.swing.JMenuItem jMenuItem105;
    private javax.swing.JMenuItem jMenuItem106;
    private javax.swing.JMenuItem jMenuItem107;
    private javax.swing.JMenuItem jMenuItem108;
    private javax.swing.JMenuItem jMenuItem109;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem110;
    private javax.swing.JMenuItem jMenuItem111;
    private javax.swing.JMenuItem jMenuItem112;
    private javax.swing.JMenuItem jMenuItem113;
    private javax.swing.JMenuItem jMenuItem114;
    private javax.swing.JMenuItem jMenuItem115;
    private javax.swing.JMenuItem jMenuItem116;
    private javax.swing.JMenuItem jMenuItem117;
    private javax.swing.JMenuItem jMenuItem118;
    private javax.swing.JMenuItem jMenuItem119;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem120;
    private javax.swing.JMenuItem jMenuItem121;
    private javax.swing.JMenuItem jMenuItem122;
    private javax.swing.JMenuItem jMenuItem123;
    private javax.swing.JMenuItem jMenuItem124;
    private javax.swing.JMenuItem jMenuItem125;
    private javax.swing.JMenuItem jMenuItem126;
    private javax.swing.JMenuItem jMenuItem127;
    private javax.swing.JMenuItem jMenuItem128;
    private javax.swing.JMenuItem jMenuItem129;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem130;
    private javax.swing.JMenuItem jMenuItem131;
    private javax.swing.JMenuItem jMenuItem132;
    private javax.swing.JMenuItem jMenuItem133;
    private javax.swing.JMenuItem jMenuItem134;
    private javax.swing.JMenuItem jMenuItem135;
    private javax.swing.JMenuItem jMenuItem136;
    private javax.swing.JMenuItem jMenuItem137;
    private javax.swing.JMenuItem jMenuItem138;
    private javax.swing.JMenuItem jMenuItem139;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem140;
    private javax.swing.JMenuItem jMenuItem141;
    private javax.swing.JMenuItem jMenuItem142;
    private javax.swing.JMenuItem jMenuItem143;
    private javax.swing.JMenuItem jMenuItem144;
    private javax.swing.JMenuItem jMenuItem145;
    private javax.swing.JMenuItem jMenuItem146;
    private javax.swing.JMenuItem jMenuItem147;
    private javax.swing.JMenuItem jMenuItem148;
    private javax.swing.JMenuItem jMenuItem149;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem150;
    private javax.swing.JMenuItem jMenuItem151;
    private javax.swing.JMenuItem jMenuItem152;
    private javax.swing.JMenuItem jMenuItem153;
    private javax.swing.JMenuItem jMenuItem154;
    private javax.swing.JMenuItem jMenuItem155;
    private javax.swing.JMenuItem jMenuItem156;
    private javax.swing.JMenuItem jMenuItem157;
    private javax.swing.JMenuItem jMenuItem158;
    private javax.swing.JMenuItem jMenuItem159;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem160;
    private javax.swing.JMenuItem jMenuItem161;
    private javax.swing.JMenuItem jMenuItem162;
    private javax.swing.JMenuItem jMenuItem163;
    private javax.swing.JMenuItem jMenuItem164;
    private javax.swing.JMenuItem jMenuItem165;
    private javax.swing.JMenuItem jMenuItem166;
    private javax.swing.JMenuItem jMenuItem167;
    private javax.swing.JMenuItem jMenuItem168;
    private javax.swing.JMenuItem jMenuItem169;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem170;
    private javax.swing.JMenuItem jMenuItem171;
    private javax.swing.JMenuItem jMenuItem172;
    private javax.swing.JMenuItem jMenuItem173;
    private javax.swing.JMenuItem jMenuItem174;
    private javax.swing.JMenuItem jMenuItem175;
    private javax.swing.JMenuItem jMenuItem176;
    private javax.swing.JMenuItem jMenuItem177;
    private javax.swing.JMenuItem jMenuItem178;
    private javax.swing.JMenuItem jMenuItem179;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem180;
    private javax.swing.JMenuItem jMenuItem181;
    private javax.swing.JMenuItem jMenuItem182;
    private javax.swing.JMenuItem jMenuItem183;
    private javax.swing.JMenuItem jMenuItem184;
    private javax.swing.JMenuItem jMenuItem185;
    private javax.swing.JMenuItem jMenuItem186;
    private javax.swing.JMenuItem jMenuItem187;
    private javax.swing.JMenuItem jMenuItem188;
    private javax.swing.JMenuItem jMenuItem189;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem190;
    private javax.swing.JMenuItem jMenuItem191;
    private javax.swing.JMenuItem jMenuItem192;
    private javax.swing.JMenuItem jMenuItem193;
    private javax.swing.JMenuItem jMenuItem194;
    private javax.swing.JMenuItem jMenuItem195;
    private javax.swing.JMenuItem jMenuItem196;
    private javax.swing.JMenuItem jMenuItem197;
    private javax.swing.JMenuItem jMenuItem198;
    private javax.swing.JMenuItem jMenuItem199;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem200;
    private javax.swing.JMenuItem jMenuItem201;
    private javax.swing.JMenuItem jMenuItem202;
    private javax.swing.JMenuItem jMenuItem203;
    private javax.swing.JMenuItem jMenuItem204;
    private javax.swing.JMenuItem jMenuItem205;
    private javax.swing.JMenuItem jMenuItem206;
    private javax.swing.JMenuItem jMenuItem207;
    private javax.swing.JMenuItem jMenuItem208;
    private javax.swing.JMenuItem jMenuItem209;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem210;
    private javax.swing.JMenuItem jMenuItem211;
    private javax.swing.JMenuItem jMenuItem212;
    private javax.swing.JMenuItem jMenuItem213;
    private javax.swing.JMenuItem jMenuItem214;
    private javax.swing.JMenuItem jMenuItem215;
    private javax.swing.JMenuItem jMenuItem216;
    private javax.swing.JMenuItem jMenuItem217;
    private javax.swing.JMenuItem jMenuItem218;
    private javax.swing.JMenuItem jMenuItem219;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem220;
    private javax.swing.JMenuItem jMenuItem221;
    private javax.swing.JMenuItem jMenuItem222;
    private javax.swing.JMenuItem jMenuItem223;
    private javax.swing.JMenuItem jMenuItem224;
    private javax.swing.JMenuItem jMenuItem225;
    private javax.swing.JMenuItem jMenuItem226;
    private javax.swing.JMenuItem jMenuItem227;
    private javax.swing.JMenuItem jMenuItem228;
    private javax.swing.JMenuItem jMenuItem229;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem230;
    private javax.swing.JMenuItem jMenuItem231;
    private javax.swing.JMenuItem jMenuItem232;
    private javax.swing.JMenuItem jMenuItem233;
    private javax.swing.JMenuItem jMenuItem234;
    private javax.swing.JMenuItem jMenuItem235;
    private javax.swing.JMenuItem jMenuItem236;
    private javax.swing.JMenuItem jMenuItem237;
    private javax.swing.JMenuItem jMenuItem238;
    private javax.swing.JMenuItem jMenuItem239;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem240;
    private javax.swing.JMenuItem jMenuItem241;
    private javax.swing.JMenuItem jMenuItem242;
    private javax.swing.JMenuItem jMenuItem243;
    private javax.swing.JMenuItem jMenuItem244;
    private javax.swing.JMenuItem jMenuItem245;
    private javax.swing.JMenuItem jMenuItem246;
    private javax.swing.JMenuItem jMenuItem247;
    private javax.swing.JMenuItem jMenuItem248;
    private javax.swing.JMenuItem jMenuItem249;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem250;
    private javax.swing.JMenuItem jMenuItem251;
    private javax.swing.JMenuItem jMenuItem252;
    private javax.swing.JMenuItem jMenuItem253;
    private javax.swing.JMenuItem jMenuItem254;
    private javax.swing.JMenuItem jMenuItem255;
    private javax.swing.JMenuItem jMenuItem256;
    private javax.swing.JMenuItem jMenuItem257;
    private javax.swing.JMenuItem jMenuItem258;
    private javax.swing.JMenuItem jMenuItem259;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem260;
    private javax.swing.JMenuItem jMenuItem261;
    private javax.swing.JMenuItem jMenuItem262;
    private javax.swing.JMenuItem jMenuItem263;
    private javax.swing.JMenuItem jMenuItem264;
    private javax.swing.JMenuItem jMenuItem265;
    private javax.swing.JMenuItem jMenuItem266;
    private javax.swing.JMenuItem jMenuItem267;
    private javax.swing.JMenuItem jMenuItem268;
    private javax.swing.JMenuItem jMenuItem269;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem270;
    private javax.swing.JMenuItem jMenuItem271;
    private javax.swing.JMenuItem jMenuItem272;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    private javax.swing.JMenuItem jMenuItem51;
    private javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem54;
    private javax.swing.JMenuItem jMenuItem55;
    private javax.swing.JMenuItem jMenuItem56;
    private javax.swing.JMenuItem jMenuItem57;
    private javax.swing.JMenuItem jMenuItem58;
    private javax.swing.JMenuItem jMenuItem59;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem60;
    private javax.swing.JMenuItem jMenuItem61;
    private javax.swing.JMenuItem jMenuItem62;
    private javax.swing.JMenuItem jMenuItem63;
    private javax.swing.JMenuItem jMenuItem64;
    private javax.swing.JMenuItem jMenuItem65;
    private javax.swing.JMenuItem jMenuItem66;
    private javax.swing.JMenuItem jMenuItem67;
    private javax.swing.JMenuItem jMenuItem68;
    private javax.swing.JMenuItem jMenuItem69;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem70;
    private javax.swing.JMenuItem jMenuItem71;
    private javax.swing.JMenuItem jMenuItem72;
    private javax.swing.JMenuItem jMenuItem73;
    private javax.swing.JMenuItem jMenuItem74;
    private javax.swing.JMenuItem jMenuItem75;
    private javax.swing.JMenuItem jMenuItem76;
    private javax.swing.JMenuItem jMenuItem77;
    private javax.swing.JMenuItem jMenuItem78;
    private javax.swing.JMenuItem jMenuItem79;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem80;
    private javax.swing.JMenuItem jMenuItem81;
    private javax.swing.JMenuItem jMenuItem82;
    private javax.swing.JMenuItem jMenuItem83;
    private javax.swing.JMenuItem jMenuItem84;
    private javax.swing.JMenuItem jMenuItem85;
    private javax.swing.JMenuItem jMenuItem86;
    private javax.swing.JMenuItem jMenuItem87;
    private javax.swing.JMenuItem jMenuItem88;
    private javax.swing.JMenuItem jMenuItem89;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItem90;
    private javax.swing.JMenuItem jMenuItem91;
    private javax.swing.JMenuItem jMenuItem92;
    private javax.swing.JMenuItem jMenuItem93;
    private javax.swing.JMenuItem jMenuItem94;
    private javax.swing.JMenuItem jMenuItem95;
    private javax.swing.JMenuItem jMenuItem96;
    private javax.swing.JMenuItem jMenuItem97;
    private javax.swing.JMenuItem jMenuItem98;
    private javax.swing.JMenuItem jMenuItem99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator100;
    private javax.swing.JPopupMenu.Separator jSeparator101;
    private javax.swing.JPopupMenu.Separator jSeparator102;
    private javax.swing.JPopupMenu.Separator jSeparator103;
    private javax.swing.JPopupMenu.Separator jSeparator104;
    private javax.swing.JPopupMenu.Separator jSeparator105;
    private javax.swing.JPopupMenu.Separator jSeparator106;
    private javax.swing.JPopupMenu.Separator jSeparator107;
    private javax.swing.JPopupMenu.Separator jSeparator108;
    private javax.swing.JPopupMenu.Separator jSeparator109;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator110;
    private javax.swing.JPopupMenu.Separator jSeparator111;
    private javax.swing.JPopupMenu.Separator jSeparator112;
    private javax.swing.JPopupMenu.Separator jSeparator113;
    private javax.swing.JPopupMenu.Separator jSeparator116;
    private javax.swing.JPopupMenu.Separator jSeparator117;
    private javax.swing.JPopupMenu.Separator jSeparator118;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator120;
    private javax.swing.JPopupMenu.Separator jSeparator121;
    private javax.swing.JPopupMenu.Separator jSeparator122;
    private javax.swing.JPopupMenu.Separator jSeparator123;
    private javax.swing.JPopupMenu.Separator jSeparator124;
    private javax.swing.JPopupMenu.Separator jSeparator125;
    private javax.swing.JPopupMenu.Separator jSeparator126;
    private javax.swing.JPopupMenu.Separator jSeparator127;
    private javax.swing.JPopupMenu.Separator jSeparator128;
    private javax.swing.JPopupMenu.Separator jSeparator129;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator130;
    private javax.swing.JPopupMenu.Separator jSeparator131;
    private javax.swing.JPopupMenu.Separator jSeparator132;
    private javax.swing.JPopupMenu.Separator jSeparator133;
    private javax.swing.JPopupMenu.Separator jSeparator134;
    private javax.swing.JPopupMenu.Separator jSeparator135;
    private javax.swing.JPopupMenu.Separator jSeparator136;
    private javax.swing.JPopupMenu.Separator jSeparator137;
    private javax.swing.JPopupMenu.Separator jSeparator138;
    private javax.swing.JPopupMenu.Separator jSeparator139;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator140;
    private javax.swing.JPopupMenu.Separator jSeparator141;
    private javax.swing.JPopupMenu.Separator jSeparator142;
    private javax.swing.JPopupMenu.Separator jSeparator143;
    private javax.swing.JPopupMenu.Separator jSeparator144;
    private javax.swing.JPopupMenu.Separator jSeparator145;
    private javax.swing.JPopupMenu.Separator jSeparator147;
    private javax.swing.JPopupMenu.Separator jSeparator148;
    private javax.swing.JPopupMenu.Separator jSeparator149;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator150;
    private javax.swing.JPopupMenu.Separator jSeparator152;
    private javax.swing.JPopupMenu.Separator jSeparator155;
    private javax.swing.JPopupMenu.Separator jSeparator157;
    private javax.swing.JPopupMenu.Separator jSeparator158;
    private javax.swing.JPopupMenu.Separator jSeparator159;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator160;
    private javax.swing.JPopupMenu.Separator jSeparator161;
    private javax.swing.JPopupMenu.Separator jSeparator163;
    private javax.swing.JPopupMenu.Separator jSeparator165;
    private javax.swing.JPopupMenu.Separator jSeparator166;
    private javax.swing.JPopupMenu.Separator jSeparator168;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator171;
    private javax.swing.JPopupMenu.Separator jSeparator172;
    private javax.swing.JPopupMenu.Separator jSeparator173;
    private javax.swing.JPopupMenu.Separator jSeparator174;
    private javax.swing.JPopupMenu.Separator jSeparator175;
    private javax.swing.JPopupMenu.Separator jSeparator176;
    private javax.swing.JPopupMenu.Separator jSeparator177;
    private javax.swing.JPopupMenu.Separator jSeparator178;
    private javax.swing.JPopupMenu.Separator jSeparator179;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator181;
    private javax.swing.JPopupMenu.Separator jSeparator182;
    private javax.swing.JPopupMenu.Separator jSeparator183;
    private javax.swing.JPopupMenu.Separator jSeparator184;
    private javax.swing.JPopupMenu.Separator jSeparator185;
    private javax.swing.JPopupMenu.Separator jSeparator186;
    private javax.swing.JPopupMenu.Separator jSeparator187;
    private javax.swing.JPopupMenu.Separator jSeparator188;
    private javax.swing.JPopupMenu.Separator jSeparator189;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JPopupMenu.Separator jSeparator190;
    private javax.swing.JPopupMenu.Separator jSeparator191;
    private javax.swing.JPopupMenu.Separator jSeparator192;
    private javax.swing.JPopupMenu.Separator jSeparator193;
    private javax.swing.JPopupMenu.Separator jSeparator194;
    private javax.swing.JPopupMenu.Separator jSeparator195;
    private javax.swing.JPopupMenu.Separator jSeparator196;
    private javax.swing.JPopupMenu.Separator jSeparator197;
    private javax.swing.JPopupMenu.Separator jSeparator198;
    private javax.swing.JPopupMenu.Separator jSeparator199;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator20;
    private javax.swing.JPopupMenu.Separator jSeparator200;
    private javax.swing.JPopupMenu.Separator jSeparator201;
    private javax.swing.JPopupMenu.Separator jSeparator202;
    private javax.swing.JPopupMenu.Separator jSeparator203;
    private javax.swing.JPopupMenu.Separator jSeparator204;
    private javax.swing.JPopupMenu.Separator jSeparator205;
    private javax.swing.JPopupMenu.Separator jSeparator206;
    private javax.swing.JPopupMenu.Separator jSeparator207;
    private javax.swing.JPopupMenu.Separator jSeparator21;
    private javax.swing.JPopupMenu.Separator jSeparator22;
    private javax.swing.JPopupMenu.Separator jSeparator23;
    private javax.swing.JPopupMenu.Separator jSeparator24;
    private javax.swing.JPopupMenu.Separator jSeparator25;
    private javax.swing.JPopupMenu.Separator jSeparator26;
    private javax.swing.JPopupMenu.Separator jSeparator27;
    private javax.swing.JPopupMenu.Separator jSeparator28;
    private javax.swing.JPopupMenu.Separator jSeparator29;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator30;
    private javax.swing.JPopupMenu.Separator jSeparator31;
    private javax.swing.JPopupMenu.Separator jSeparator32;
    private javax.swing.JPopupMenu.Separator jSeparator33;
    private javax.swing.JPopupMenu.Separator jSeparator34;
    private javax.swing.JPopupMenu.Separator jSeparator35;
    private javax.swing.JPopupMenu.Separator jSeparator36;
    private javax.swing.JPopupMenu.Separator jSeparator37;
    private javax.swing.JPopupMenu.Separator jSeparator38;
    private javax.swing.JPopupMenu.Separator jSeparator39;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator40;
    private javax.swing.JPopupMenu.Separator jSeparator41;
    private javax.swing.JPopupMenu.Separator jSeparator42;
    private javax.swing.JPopupMenu.Separator jSeparator43;
    private javax.swing.JPopupMenu.Separator jSeparator44;
    private javax.swing.JPopupMenu.Separator jSeparator45;
    private javax.swing.JPopupMenu.Separator jSeparator46;
    private javax.swing.JPopupMenu.Separator jSeparator47;
    private javax.swing.JPopupMenu.Separator jSeparator48;
    private javax.swing.JPopupMenu.Separator jSeparator49;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator51;
    private javax.swing.JPopupMenu.Separator jSeparator52;
    private javax.swing.JPopupMenu.Separator jSeparator53;
    private javax.swing.JPopupMenu.Separator jSeparator55;
    private javax.swing.JPopupMenu.Separator jSeparator56;
    private javax.swing.JPopupMenu.Separator jSeparator57;
    private javax.swing.JPopupMenu.Separator jSeparator58;
    private javax.swing.JPopupMenu.Separator jSeparator59;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator60;
    private javax.swing.JPopupMenu.Separator jSeparator61;
    private javax.swing.JPopupMenu.Separator jSeparator63;
    private javax.swing.JPopupMenu.Separator jSeparator64;
    private javax.swing.JPopupMenu.Separator jSeparator65;
    private javax.swing.JPopupMenu.Separator jSeparator66;
    private javax.swing.JPopupMenu.Separator jSeparator67;
    private javax.swing.JPopupMenu.Separator jSeparator68;
    private javax.swing.JPopupMenu.Separator jSeparator69;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator70;
    private javax.swing.JPopupMenu.Separator jSeparator72;
    private javax.swing.JPopupMenu.Separator jSeparator73;
    private javax.swing.JPopupMenu.Separator jSeparator74;
    private javax.swing.JPopupMenu.Separator jSeparator75;
    private javax.swing.JPopupMenu.Separator jSeparator77;
    private javax.swing.JPopupMenu.Separator jSeparator78;
    private javax.swing.JPopupMenu.Separator jSeparator79;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator80;
    private javax.swing.JPopupMenu.Separator jSeparator81;
    private javax.swing.JPopupMenu.Separator jSeparator82;
    private javax.swing.JPopupMenu.Separator jSeparator83;
    private javax.swing.JPopupMenu.Separator jSeparator84;
    private javax.swing.JPopupMenu.Separator jSeparator85;
    private javax.swing.JPopupMenu.Separator jSeparator86;
    private javax.swing.JPopupMenu.Separator jSeparator87;
    private javax.swing.JPopupMenu.Separator jSeparator88;
    private javax.swing.JPopupMenu.Separator jSeparator89;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JPopupMenu.Separator jSeparator90;
    private javax.swing.JPopupMenu.Separator jSeparator91;
    private javax.swing.JPopupMenu.Separator jSeparator92;
    private javax.swing.JPopupMenu.Separator jSeparator93;
    private javax.swing.JPopupMenu.Separator jSeparator94;
    private javax.swing.JPopupMenu.Separator jSeparator95;
    private javax.swing.JPopupMenu.Separator jSeparator96;
    private javax.swing.JPopupMenu.Separator jSeparator97;
    private javax.swing.JPopupMenu.Separator jSeparator98;
    private javax.swing.JPopupMenu.Separator jSeparator99;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    private org.edisoncor.gui.label.LabelMetric marquesina;
    private javax.swing.JMenu mercaderias;
    private javax.swing.JComboBox<String> month;
    private javax.swing.JButton next;
    private javax.swing.JMenu operaciones;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JPanel panelCalendario;
    private javax.swing.JMenu parametros;
    private javax.swing.JMenuItem prestamos;
    private javax.swing.JButton previous;
    private javax.swing.JMenu rrhh;
    private javax.swing.JMenu tablasrrhh;
    private javax.swing.JButton up;
    private javax.swing.JMenu ventas;
    // End of variables declaration//GEN-END:variables

    private class Money extends Thread {

        private String[] monedas = {"   Dolar: 5.150", "   Euro: 5.900", "   Peso Ar: 400", "   Peso Ur: 260", "   Real: 1.630", "   Yen: 42"};
        int contador = 0;

        public void run() {
            while (true) {
                try {
                    // jLMoneda.setText(monedas[contador]);
                    contador++;
                    if (contador == monedas.length) {
                        contador = 0;
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Portada.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private class Marquesina {

        private void display() {
            String s = Config.cNombreEmpresa;
            Marque mp = new Marque(s, 330); // Indica en que parte de la Pantalla Empieza la Marquesina
            mp.start();
        }
    }

    class Marque implements ActionListener {

        private static final int RATE = 12;
        private final javax.swing.Timer timer = new javax.swing.Timer(1500 / RATE, this);
        private final String s;
        private final int n;
        private int index;

        public Marque(String s, int n) {
            if (s == null || n < 1) {
                throw new IllegalArgumentException("Null string or n < 1");
            }
            StringBuilder sb = new StringBuilder(n);
            for (int i = 0; i < n; i++) {
                sb.append(' ');
            }
            this.s = sb + s + sb;
            this.n = n;
            marquesina.setText(sb.toString());
            //this.add(LblMarquesina);
        }

        public void start() {
            timer.start();
        }

        public void stop() {
            timer.stop();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            index++;
            if (index > s.length() - n) {
                index = 0;
            }
            marquesina.setText(s.substring(index, index + n));
        }
    }

    public class HilosEspias extends Thread {

        private int minutos = 0, segundos = 0;
        private int tipo;
        private int delay;
        String c1, c2;

        public HilosEspias(int ntipo, int delayTime) {
            tipo = ntipo;
            delay = delayTime;
        }

        public void run() {
            try {
                for (;;) {
                    if (segundos == 59) {
                        segundos = 0;
                        minutos++;
                    }
                    segundos++;
                    //ESTE HILO MATA LAS CONEXIONES QUE YA NO SON UTILIZADAS
                    //Y EVITA QUE COLAPSE EL SERVIDOR
                    //DE NADA
                    if (tipo == 1) {
                        System.out.println("ESTOY EN EL HILO 1 MATANDO CONEXIONES");
                        killconexionDAO killDAO = new killconexionDAO();
                        killconexion id = null;
                        try {
                            for (killconexion ki : killDAO.Todos()) {
                                if (ki.getTime() > 240) {
                                    killDAO.MatarConexion(ki.getId());
                                }
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                        }
                    }

                    if (tipo == 6) {
                        System.out.println("ESTOY EN BICSA");
                        prestamoDAO preDAO = new prestamoDAO();
                        try {
                            preDAO.actualizarBICSA();
                        } catch (SQLException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                    System.out.println("tiempo  " + minutos + ":" + segundos);
                    Thread.sleep(delay);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

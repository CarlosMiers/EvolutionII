/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.CalcularMoraDiaria;
import Clases.Config;
import Clases.generarQR;
import Conexion.ClockLabel;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import DAO.cabecera_compraDAO;
import DAO.cajaDAO;
import DAO.clienteDAO;
import DAO.comprobanteDAO;
import DAO.cuenta_clienteDAO;
import DAO.detalle_ventaDAO;
import DAO.deudaDAO;
import DAO.familiaDAO;
import DAO.giraduriaDAO;
import DAO.killconexionDAO;
import DAO.localidadDAO;
import DAO.marcaDAO;
import DAO.medidaDAO;
import Modelo.detalle_forma_cobro;
import DAO.monedaDAO;
import DAO.orden_trabajoDAO;
import DAO.paisDAO;
import DAO.productoDAO;
import DAO.proveedorDAO;
import DAO.rubroDAO;
import DAO.sucursalDAO;
import DAO.ubicacionDAO;
import DAO.vendedorDAO;
import DAO.ventaDAO;
import ExportaVtaDAO.ExportaClienteDAO;
import ExportaVtaDAO.ExportaVentaDAO;
import ExportaVtaDAO.Exportadetalle_ventaDAO;
import ExportaVtaDAO.ExportaFormaCobroDAO;
import ExportaVtaDAO.AnularVentasDAO;
import DAO.detalle_forma_cobroDAO;
import DAOBACKUP.ImportaClienteDAO;
import DAOBACKUP.ImportaCompraDAO;
import DAOBACKUP.ImportaProductoDAO;
import DAOBACKUP.ImportaProveedorDAO;
import DAOBACKUP.ImportaVentaDAO;
import DAOBACKUP.Importadetalle_compraDAO;
import DAOBACKUP.Importadetalle_ventaDAO;
import ExportaVtaDAO.ExportaClienteDAO;
import ExportaVtaDAO.ExportaFormaCobroDAO;
import ExportaVtaDAO.ExportaVentaDAO;
import ExportaVtaDAO.Exportadetalle_ventaDAO;
import Modelo.cabecera_compra;
import Modelo.caja;
import Modelo.cliente;
import Modelo.comprobante;
import Modelo.cuenta_clientes;
import Modelo.detalle_compra;
import Modelo.detalle_venta;
import Modelo.deudas;
import Modelo.familia;
import Modelo.giraduria;
import Modelo.killconexion;
import Modelo.localidad;
import Modelo.marca;
import Modelo.medida;
import Modelo.moneda;
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
public class PortadaRRHH extends javax.swing.JFrame {

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
    public PortadaRRHH() {
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
        //CALENDARIO
        this.marquesina.setText("");
        this.fondo.setIcon(imagenfondo);
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
                Config.cRamos = rs.getString("ramo");
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
                Config.cCodigoAsesor = rs.getString("codasesor");

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

        if (Integer.valueOf(Config.cMenuRRHH) == 0) {
            this.rrhh.setVisible(false);
        }


        if (Integer.valueOf(Config.cMenuHerramientas) == 0) {
            this.herramientas.setVisible(false);
        }
        new HilosEspias(1, 1800000).start(); //CIERRE CONEXIONES DORMIDAS DEL SERVIDOR CADA 30 SEGUNDOS
        //    new HilosEspias(2, 3000).start(); //VERIFICA COBROS EN LA WEB Y ACTUALIZA CADA 30 SEGUNDOS
        //  new HilosEspias(3, 300000).start(); //BORRA TODAS LAS CUENTAS CANCELADAS
        if (Config.cIpServerCopia != null && !Config.cIpServerCopia.equals("")) {
//            new HilosEspias(4, 5400000).start(); //IMPORTANDO VENTAS
        }

    }

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
        jMenuBar1 = new javax.swing.JMenuBar();
        parametros = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem200 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem154 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem167 = new javax.swing.JMenuItem();
        jSeparator23 = new javax.swing.JPopupMenu.Separator();
        jMenuItem168 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem169 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem194 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem195 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        rrhh = new javax.swing.JMenu();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jMenuItem171 = new javax.swing.JMenuItem();
        jSeparator155 = new javax.swing.JPopupMenu.Separator();
        jMenuItem196 = new javax.swing.JMenuItem();
        jSeparator157 = new javax.swing.JPopupMenu.Separator();
        jMenuItem40 = new javax.swing.JMenuItem();
        jSeparator150 = new javax.swing.JPopupMenu.Separator();
        jMenuItem191 = new javax.swing.JMenuItem();
        jSeparator152 = new javax.swing.JPopupMenu.Separator();
        jMenuItem192 = new javax.swing.JMenuItem();
        jSeparator170 = new javax.swing.JPopupMenu.Separator();
        Anticipos = new javax.swing.JMenuItem();
        jSeparator153 = new javax.swing.JPopupMenu.Separator();
        jMenuItem189 = new javax.swing.JMenuItem();
        jSeparator151 = new javax.swing.JPopupMenu.Separator();
        jMenuItem190 = new javax.swing.JMenuItem();
        jSeparator154 = new javax.swing.JPopupMenu.Separator();
        jMenuItem193 = new javax.swing.JMenuItem();
        jSeparator167 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        jMenuItem205 = new javax.swing.JMenuItem();
        jMenuItem206 = new javax.swing.JMenuItem();
        jMenuItem207 = new javax.swing.JMenuItem();
        jSeparator169 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        Informes1 = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jSeparator24 = new javax.swing.JPopupMenu.Separator();
        jMenuItem24 = new javax.swing.JMenuItem();
        jSeparator25 = new javax.swing.JPopupMenu.Separator();
        jMenuItem25 = new javax.swing.JMenuItem();
        jSeparator26 = new javax.swing.JPopupMenu.Separator();
        jMenuItem26 = new javax.swing.JMenuItem();
        jSeparator27 = new javax.swing.JPopupMenu.Separator();
        jMenuItem27 = new javax.swing.JMenuItem();
        jSeparator28 = new javax.swing.JPopupMenu.Separator();
        jMenuItem28 = new javax.swing.JMenuItem();
        jSeparator29 = new javax.swing.JPopupMenu.Separator();
        jMenuItem29 = new javax.swing.JMenuItem();
        jSeparator30 = new javax.swing.JPopupMenu.Separator();
        jMenuItem30 = new javax.swing.JMenuItem();
        Informes = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        jMenuItem18 = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        jMenuItem20 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        jMenuItem21 = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JPopupMenu.Separator();
        jMenuItem23 = new javax.swing.JMenuItem();
        herramientas = new javax.swing.JMenu();
        jMenuItem57 = new javax.swing.JMenuItem();

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
                .addContainerGap(810, Short.MAX_VALUE)
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

        javax.swing.GroupLayout fondoLayout = new javax.swing.GroupLayout(fondo);
        fondo.setLayout(fondoLayout);
        fondoLayout.setHorizontalGroup(
            fondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EtiquetaQR)
                .addGap(785, 785, 785)
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
                .addComponent(jLUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 495, Short.MAX_VALUE)
                .addComponent(EtiquetaQR)
                .addGap(100, 100, 100))
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

        parametros.setText("Parámetros");
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

        jMenuItem200.setText("Nacionalidades");
        jMenuItem200.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem200.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem200ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem200);
        parametros.add(jSeparator7);

        jMenuItem5.setText("Localidades");
        jMenuItem5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem5);
        parametros.add(jSeparator1);

        jMenuItem154.setText("Departamentos");
        jMenuItem154.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem154.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem154ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem154);
        parametros.add(jSeparator2);

        jMenuItem167.setText("Secciones");
        jMenuItem167.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem167.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem167ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem167);
        parametros.add(jSeparator23);

        jMenuItem168.setText("Perfil de Cargos");
        jMenuItem168.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem168.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem168ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem168);
        parametros.add(jSeparator3);

        jMenuItem169.setText("Profesiones");
        jMenuItem169.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem169.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem169ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem169);
        parametros.add(jSeparator4);

        jMenuItem2.setText("Tipo de Contratos");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem2);
        parametros.add(jSeparator6);

        jMenuItem194.setText("Motivos de Ausencia");
        jMenuItem194.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem194.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem194ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem194);
        parametros.add(jSeparator9);

        jMenuItem195.setText("Concepto Salarios");
        jMenuItem195.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem195.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem195ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem195);
        parametros.add(jSeparator10);

        jMenuItem13.setText("Salir del Sistema");
        jMenuItem13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem13);

        jMenuBar1.add(parametros);

        rrhh.setText("Procesos");
        rrhh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rrhh.add(jSeparator11);

        jMenuItem3.setText("Vacancias");
        jMenuItem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem3);
        rrhh.add(jSeparator8);

        jMenuItem4.setText("LLamadas a Concurso");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem4);
        rrhh.add(jSeparator12);

        jMenuItem6.setText("Registrar Postulantes");
        jMenuItem6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem6);
        rrhh.add(jSeparator13);

        jMenuItem171.setText("Registrar Legajos");
        jMenuItem171.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem171.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem171ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem171);
        rrhh.add(jSeparator155);

        jMenuItem196.setText("Registrar Asistencias");
        jMenuItem196.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem196.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem196ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem196);
        rrhh.add(jSeparator157);

        jMenuItem40.setText("Registrar Créditos");
        jMenuItem40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem40);
        rrhh.add(jSeparator150);

        jMenuItem191.setText("Registrar Vacaciones");
        jMenuItem191.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem191.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem191ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem191);
        rrhh.add(jSeparator152);

        jMenuItem192.setText("Generar Horas Extras");
        jMenuItem192.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem192.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem192ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem192);
        rrhh.add(jSeparator170);

        Anticipos.setText("Registrar Anticipos");
        Anticipos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Anticipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnticiposActionPerformed(evt);
            }
        });
        rrhh.add(Anticipos);
        rrhh.add(jSeparator153);

        jMenuItem189.setText("Registrar Descuentos");
        jMenuItem189.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem189.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem189ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem189);
        rrhh.add(jSeparator151);

        jMenuItem190.setText("Registrar Llegadas Tardías");
        jMenuItem190.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem190.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem190ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem190);
        rrhh.add(jSeparator154);

        jMenuItem193.setText("Registrar Ausencias");
        jMenuItem193.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem193.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem193ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem193);
        rrhh.add(jSeparator167);

        jMenuItem7.setText("Registrar Permisos");
        jMenuItem7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem7);
        rrhh.add(jSeparator14);

        jMenuItem8.setText("Registrar Sanciones");
        jMenuItem8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem8);

        jMenuItem9.setText("Registrar Desvinculaciones");
        jMenuItem9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem9);
        rrhh.add(jSeparator15);

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
        rrhh.add(jSeparator169);

        jMenuItem10.setText("Generar Aguinaldos");
        jMenuItem10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem10);

        jMenuItem11.setText("Planilla de Aguinaldos");
        jMenuItem11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem11);

        jMenuItem16.setText("Recibo Aguinaldos");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        rrhh.add(jMenuItem16);

        Informes1.setText("Informes");
        Informes1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem22.setText("Créditos ");
        jMenuItem22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem22);
        Informes1.add(jSeparator24);

        jMenuItem24.setText("Anticipos");
        jMenuItem24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem24);
        Informes1.add(jSeparator25);

        jMenuItem25.setText("Descuentos");
        jMenuItem25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem25);
        Informes1.add(jSeparator26);

        jMenuItem26.setText("Llegadas Tardías");
        jMenuItem26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem26);
        Informes1.add(jSeparator27);

        jMenuItem27.setText("Ausencias");
        jMenuItem27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem27);
        Informes1.add(jSeparator28);

        jMenuItem28.setText("Asistencias");
        jMenuItem28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem28);
        Informes1.add(jSeparator29);

        jMenuItem29.setText("Horas Extras");
        jMenuItem29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem29);
        Informes1.add(jSeparator30);

        jMenuItem30.setText("Vacaciones");
        jMenuItem30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        Informes1.add(jMenuItem30);

        rrhh.add(Informes1);

        jMenuBar1.add(rrhh);

        Informes.setText("Informes");
        Informes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem12.setText("Créditos ");
        jMenuItem12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem12);
        Informes.add(jSeparator16);

        jMenuItem14.setText("Anticipos");
        jMenuItem14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem14);
        Informes.add(jSeparator17);

        jMenuItem17.setText("Descuentos");
        jMenuItem17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem17);
        Informes.add(jSeparator18);

        jMenuItem18.setText("Llegadas Tardías");
        jMenuItem18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem18);
        Informes.add(jSeparator19);

        jMenuItem19.setText("Ausencias");
        jMenuItem19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem19);
        Informes.add(jSeparator20);

        jMenuItem20.setText("Asistencias");
        jMenuItem20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem20);
        Informes.add(jSeparator21);

        jMenuItem21.setText("Horas Extras");
        jMenuItem21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem21);
        Informes.add(jSeparator22);

        jMenuItem23.setText("Vacaciones");
        jMenuItem23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        Informes.add(jMenuItem23);

        jMenuBar1.add(Informes);

        herramientas.setText("Acceso");
        herramientas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem57.setText("Usuarios del Sistema");
        jMenuItem57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem57ActionPerformed(evt);
            }
        });
        herramientas.add(jMenuItem57);

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
        new sucursales_rrhh().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        new localidadesrrhh().setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem57ActionPerformed
        new usuariosrrhh().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem57ActionPerformed

    private void jMenuItem154ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem154ActionPerformed
        new departamentos_laborales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem154ActionPerformed

    private void jMenuItem167ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem167ActionPerformed
        new secciones().setVisible(true);
    }//GEN-LAST:event_jMenuItem167ActionPerformed

    private void jMenuItem168ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem168ActionPerformed
        new cargos().setVisible(true);
    }//GEN-LAST:event_jMenuItem168ActionPerformed

    private void jMenuItem169ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem169ActionPerformed
        new profesiones().setVisible(true);
    }//GEN-LAST:event_jMenuItem169ActionPerformed

    private void jMenuItem171ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem171ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new ficha_de_empleados().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem171ActionPerformed

    private void fechaprocesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaprocesoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprocesoFocusGained

    private void jMenuItem194ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem194ActionPerformed
        new motivo_ausencias().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem194ActionPerformed

    private void jMenuItem190ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem190ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new llegadas_tardias().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem190ActionPerformed

    private void jMenuItem195ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem195ActionPerformed
        new concepto_movimiento().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem195ActionPerformed

    private void jMenuItem193ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem193ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new ausencias().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem193ActionPerformed

    private void jMenuItem189ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem189ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new debitosxsalarios().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem189ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        new creditosxsalarios().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem40ActionPerformed

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

    private void jMenuItem196ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem196ActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new jornaleros().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem196ActionPerformed

    private void jMenuItem200ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem200ActionPerformed
        new paises().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem200ActionPerformed

    private void jMenuItem205ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem205ActionPerformed
        new salariosliquidacion().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem205ActionPerformed

    private void jMenuItem206ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem206ActionPerformed
        new salariosplanilla().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem206ActionPerformed

    private void jMenuItem207ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem207ActionPerformed
        new salariosrecibos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem207ActionPerformed

    private void AnticiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnticiposActionPerformed
        if (Integer.valueOf(Config.cNivelUsuario) == 1) {
            new anticipos().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no Autorizado");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_AnticiposActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new clase_contratos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new vacancias_laborales().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new concursos_llamados().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new postulantes().setVisible(true);
       // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        new permisos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new sanciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        new desvinculaciones().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        new asistencia_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        new creditos_salarios_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        new anticipos_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        new descuentos_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        new llegadas_tardias_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        new ausencias_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        new horas_extras_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        new vacaciones_informes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        new generar_aguinaldos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        new aguinaldo_planilla().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        new recibo_aguinaldo().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem30ActionPerformed

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
    private javax.swing.JLabel EtiquetaQR;
    private javax.swing.JMenu Informes;
    private javax.swing.JMenu Informes1;
    private com.toedter.calendar.JDateChooser fechaproceso;
    private org.edisoncor.gui.panel.PanelImage fondo;
    private javax.swing.JMenu herramientas;
    private javax.swing.JLabel jLUser;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem154;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem167;
    private javax.swing.JMenuItem jMenuItem168;
    private javax.swing.JMenuItem jMenuItem169;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem171;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem189;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem190;
    private javax.swing.JMenuItem jMenuItem191;
    private javax.swing.JMenuItem jMenuItem192;
    private javax.swing.JMenuItem jMenuItem193;
    private javax.swing.JMenuItem jMenuItem194;
    private javax.swing.JMenuItem jMenuItem195;
    private javax.swing.JMenuItem jMenuItem196;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem200;
    private javax.swing.JMenuItem jMenuItem205;
    private javax.swing.JMenuItem jMenuItem206;
    private javax.swing.JMenuItem jMenuItem207;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem57;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator150;
    private javax.swing.JPopupMenu.Separator jSeparator151;
    private javax.swing.JPopupMenu.Separator jSeparator152;
    private javax.swing.JPopupMenu.Separator jSeparator153;
    private javax.swing.JPopupMenu.Separator jSeparator154;
    private javax.swing.JPopupMenu.Separator jSeparator155;
    private javax.swing.JPopupMenu.Separator jSeparator157;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator167;
    private javax.swing.JPopupMenu.Separator jSeparator169;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator170;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator20;
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
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    private org.edisoncor.gui.label.LabelMetric marquesina;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JMenu parametros;
    private javax.swing.JMenu rrhh;
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
                Logger.getLogger(PortadaRRHH.class.getName()).log(Level.SEVERE, null, ex);
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

                if (tipo == 2) {
                    System.out.println("ESTOY EN EL HILO 2 ACTUALIZANDO CUENTAS");
                    cuenta_clienteDAO exDAO = new cuenta_clienteDAO();
                    deudaDAO deuDAO = new deudaDAO();
                    deudas dv = new deudas();
                    try {
                        for (deudas deu : deuDAO.todos()) {
                            deuDAO.borrarDeudaWeb(deu.getCliente());
                            //                  dvtadestino.setIddocumento(deu.getIddocumento());
                            //                System.out.println("DV " + dvtadestino.getIddocumento());
                            deuDAO.VerificarBorrados(dv);
                            String detacuota = "[";
                            for (cuenta_clientes cta : exDAO.CuentaPuertoSeguro(deu.getCliente(), deu.getCliente())) {
                                String lineacuota = "{cedula_ruc : " + cta.getCliente().getRuc() + ","
                                        + "cliente:" + cta.getCliente().getCodigo() + ","
                                        + "nombre_apellido : '" + cta.getCliente().getNombre() + "',"
                                        + "numero_operacion : " + cta.getDocumento() + ","
                                        + "descripcion_operacion : '" + cta.getComprobante().getNombre() + "',"
                                        + "numero_factura : " + cta.getDocumento() + ","
                                        + "nro_cuota : " + cta.getCuota() + ","
                                        + "moneda: " + "PYG" + ","
                                        + "monto : " + Math.round(cta.getSaldo().doubleValue()) + ","
                                        + "fecha_venc : " + cta.getVencimiento() + ","
                                        + "estado : " + "PE" + ","
                                        + "iddocumento : '" + cta.getIddocumento() + "',"
                                        + "creferencia : '" + cta.getCreferencia() + "',"
                                        + "gastos : " + Math.round(cta.getGastos().doubleValue()) + ","
                                        + "mora : " + Math.round(cta.getMora().doubleValue()) + ","
                                        + "punitorio : " + Math.round(cta.getPunitorio().doubleValue()) + ","
                                        + "tipoop : " + cta.getComprobante().getCodigo()
                                        + "},";
                                detacuota += lineacuota;
                            }
                            if (!detacuota.equals("[")) {
                                detacuota = detacuota.substring(0, detacuota.length() - 1);
                            }
                            detacuota += "]";
                            deuDAO.guardarCuentaWeb(detacuota);
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
                    }
                }

                if (tipo == 3) {
                    System.out.println("ESTOY EN EL HILO 3");
                    deudaDAO deuwebDAO = new deudaDAO();
                    try {
                        deuwebDAO.borrarDeudaWebCancelados();
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                ///VENTAS   
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.CalcularMoraDiaria;
import Clases.Config;
import Clases.SwingBrowser;
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
import DAO.prestamoDAO;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.openide.util.Exceptions;

/**
 *
 * @author
 */
public class PortadaFaisAsesor extends javax.swing.JFrame {

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
    public PortadaFaisAsesor() {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
        // this.setSize(700, 1380);
        SwingBrowser browser = new SwingBrowser();
        browser.loadURL("http://fais.com.py");
        // browser.setBounds(1, 1, fondo.getWidth() - 1, fondo.getHeight() - 1);
        browser.setBounds(1, 1, 1600, 700);
        fondo.add(browser);

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
//      this.fondo.setIcon(imagenfondo);
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
        new HilosEspias(1, 1800000).start(); //CIERRE CONEXIONES DORMIDAS DEL SERVIDOR CADA 30 SEGUNDOS
        //    new HilosEspias(2, 3000).start(); //VERIFICA COBROS EN LA WEB Y ACTUALIZA CADA 30 SEGUNDOS
        //  new HilosEspias(3, 300000).start(); //BORRA TODAS LAS CUENTAS CANCELADAS
        if (Config.cIpServerCopia != null && !Config.cIpServerCopia.equals("")) {
            new HilosEspias(4, 5400000).start(); //IMPORTANDO VENTAS
        }

        //    new HilosEspias(6, 5400000).start(); //ACTUALIZANDO DATOS PARA BICSA
        /**
         * ************** INSTANCIAS DEL CALENDARIO ************************
         * /** ************** INSTANCIAS DEL CALENDARIO ************************
         */
    }

    /**
     * ********************** METODOS DEL CALENDARIO
     * ******************************
     */
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
        jMenuBar1 = new javax.swing.JMenuBar();
        parametros = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator32 = new javax.swing.JPopupMenu.Separator();
        jMenuItem22 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();

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
                        .addGap(0, 0, Short.MAX_VALUE)
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

        parametros.setText("Parametros");
        parametros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem4.setText("Clientes");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem4);
        parametros.add(jSeparator32);

        jMenuItem22.setText("Gestionar Cartera");
        jMenuItem22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem22);
        parametros.add(jSeparator1);

        jMenuItem3.setText("Vencimientos por Clientes");
        jMenuItem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem3);
        parametros.add(jSeparator4);

        jMenuItem5.setText("Vencimientos por Emisor");
        jMenuItem5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem5);
        parametros.add(jSeparator2);

        jMenuItem1.setText("Cartera de Renta Fija");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem1);
        parametros.add(jSeparator5);

        jMenuItem2.setText("Cartera General");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem2);
        parametros.add(jSeparator3);

        jMenuItem13.setText("Salir del Sistema");
        jMenuItem13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        parametros.add(jMenuItem13);

        jMenuBar1.add(parametros);

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

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new ClientesFais().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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

    private void fechaprocesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaprocesoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaprocesoFocusGained

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        new gestion_cartera_fais().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new cartera_general_clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new cartera_general_renta_fija_clientes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new resumen_vencimiento_cartera_cliente().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        new resumen_vencimiento_cartera_cliente().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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
    private javax.swing.JLabel EtiquetaQR;
    private com.toedter.calendar.JDateChooser fechaproceso;
    private org.edisoncor.gui.panel.PanelImage fondo;
    private javax.swing.JLabel jLUser;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator32;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private jcMousePanel.jcMousePanel jcMousePanel1;
    private org.edisoncor.gui.label.LabelMetric marquesina;
    private org.edisoncor.gui.panel.Panel panel1;
    private javax.swing.JMenu parametros;
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
                    Logger.getLogger(PortadaFaisAsesor.class.getName()).log(Level.SEVERE, null, ex);
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

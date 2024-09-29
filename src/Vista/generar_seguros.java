/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.Config;
import Clases.Job;
import Clases.UUID;
import Clases.jcThread;
import Conexion.Conexion;
import Conexion.ObtenerFecha;
import DAO.clienteDAO;
import DAO.configuracionDAO;
import DAO.cuenta_clienteDAO;
import DAO.cuenta_segurosDAO;
import DAO.giraduriaDAO;
import Modelo.Tablas;
import Modelo.cliente;
import Modelo.configuracion;
import Modelo.cuenta_clientes;
import Modelo.giraduria;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import org.openide.util.Exceptions;

/**
 *
 * @author hp
 */
public class generar_seguros extends javax.swing.JFrame {

    /**
     * Creates new form actualizar_web
     */
    Conexion con = null;
    Statement stm, stmcab, stmdet, stmfac = null;
    Tablas modelogiraduria = new Tablas();

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    ObtenerFecha ODate = new ObtenerFecha();
    private TableRowSorter trsfiltrogira;

    ImageIcon iconoaceptar = new ImageIcon("src/Iconos/aceptar.png");
    ImageIcon iconosalir = new ImageIcon("src/Iconos/salir.png");
    ImageIcon iconobuscar = new ImageIcon("src/Iconos/buscar.png");

    public generar_seguros() {
        initComponents();
        Calendar c2 = new GregorianCalendar();
        this.buscar.setIcon(iconobuscar);
        this.Ejecutar.setIcon(iconoaceptar);
        this.Salir.setIcon(iconosalir);
        this.giraduria.setText("0");
        this.porcentaje.setText("0");
        this.nombregiraduria.setText("");
        this.servicio.setText("20");
        this.nombreservicio.setText("SEGURO DE VIDA");
        this.vencimiento.setCalendar(c2);
        this.setLocationRelativeTo(null);
        this.TitGir();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGiraduria = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        combogiraduria = new javax.swing.JComboBox();
        jTBuscarGiraduria = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablagiraduria = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        AceptarGir = new javax.swing.JButton();
        SalirGir = new javax.swing.JButton();
        labelTask1 = new org.edisoncor.gui.label.LabelTask();
        jPanel1 = new javax.swing.JPanel();
        Ejecutar = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        Salir = new javax.swing.JButton();
        vencimiento = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        giraduria = new javax.swing.JTextField();
        buscar = new javax.swing.JButton();
        nombregiraduria = new javax.swing.JTextField();
        servicio = new javax.swing.JTextField();
        nombreservicio = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        porcentaje = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        BGiraduria.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        BGiraduria.setTitle("null");

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        combogiraduria.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combogiraduria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buscar por Nombre", "Buscar por Código" }));
        combogiraduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combogiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combogiraduriaActionPerformed(evt);
            }
        });

        jTBuscarGiraduria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTBuscarGiraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscarGiraduriaActionPerformed(evt);
            }
        });
        jTBuscarGiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscarGiraduriaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combogiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTBuscarGiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tablagiraduria.setModel(modelogiraduria);
        tablagiraduria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablagiraduriaMouseClicked(evt);
            }
        });
        tablagiraduria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablagiraduriaKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tablagiraduria);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(AceptarGir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(SalirGir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AceptarGir)
                    .addComponent(SalirGir))
                .addContainerGap())
        );

        javax.swing.GroupLayout BGiraduriaLayout = new javax.swing.GroupLayout(BGiraduria.getContentPane());
        BGiraduria.getContentPane().setLayout(BGiraduriaLayout);
        BGiraduriaLayout.setHorizontalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BGiraduriaLayout.setVerticalGroup(
            BGiraduriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BGiraduriaLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelTask1.setText("GENERACIÓN DE SEGUROS");
        labelTask1.setDescription("Generación de Seguros sobre Órdenes");

        Ejecutar.setText("Aceptar");
        Ejecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EjecutarActionPerformed(evt);
            }
        });

        jProgressBar1.setForeground(new java.awt.Color(153, 153, 255));

        Salir.setText("Salir");
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Giraduría");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Vencimiento");

        giraduria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        giraduria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giraduriaActionPerformed(evt);
            }
        });

        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        nombregiraduria.setEditable(false);
        nombregiraduria.setEnabled(false);

        servicio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        nombreservicio.setEnabled(false);

        porcentaje.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        porcentaje.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Servicio");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Comisión");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(Ejecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(servicio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(nombregiraduria, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                        .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(giraduria, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(nombreservicio))
                                    .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(giraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombregiraduria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(servicio)
                        .addComponent(jLabel2))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreservicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(porcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(vencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Salir)
                            .addComponent(Ejecutar)))
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTask1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirActionPerformed
    private void TitGir() {
        modelogiraduria.addColumn("Código");
        modelogiraduria.addColumn("Nombre");

        int[] anchos = {90, 200};
        for (int i = 0; i < modelogiraduria.getColumnCount(); i++) {
            tablagiraduria.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        ((DefaultTableCellRenderer) tablagiraduria.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);// Este código es para centrar las cabeceras de la tabla.
        tablagiraduria.getTableHeader().setFont(new Font("Arial Black", 1, 10));

        // Hacemos Invisible la Celda de Costos de los Productos
        Font font = new Font("Arial", Font.BOLD, 9);
        this.tablagiraduria.setFont(font);

        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.tablagiraduria.getColumnModel().getColumn(0).setCellRenderer(TablaRenderer);
    }

    public void filtrogira(int nNumeroColumna) {
        trsfiltrogira.setRowFilter(RowFilter.regexFilter(this.jTBuscarGiraduria.getText(), nNumeroColumna));
    }


    private void EjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EjecutarActionPerformed
        Object[] opciones = {"   Si   ", "   No   "};
        int ret = JOptionPane.showOptionDialog(null, "Desea Generar los Aportes Mensuales? ", "Confirmación", 0, 3, null, opciones, opciones[0]);
        if (ret == 0) {
            GenerarAportes aportes = new GenerarAportes();
            Thread genaporte = new Thread(aportes);
            genaporte.start();
            new Thread(new Job(Integer.valueOf("5"))).start();
            new Thread(new jcThread(jProgressBar1, Integer.valueOf("1000"))).start();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_EjecutarActionPerformed

    private void combogiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combogiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combogiraduriaActionPerformed

    private void jTBuscarGiraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaActionPerformed

    private void jTBuscarGiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscarGiraduriaKeyPressed
        this.jTBuscarGiraduria.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (jTBuscarGiraduria.getText()).toUpperCase();
                jTBuscarGiraduria.setText(cadena);
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
                filtrogira(indiceColumnaTabla);
            }
        });
        trsfiltrogira = new TableRowSorter(tablagiraduria.getModel());
        tablagiraduria.setRowSorter(trsfiltrogira);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscarGiraduriaKeyPressed

    private void tablagiraduriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablagiraduriaMouseClicked
        this.AceptarGir.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaMouseClicked

    private void tablagiraduriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablagiraduriaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.AceptarGir.doClick();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tablagiraduriaKeyPressed

    private void AceptarGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarGirActionPerformed
        int nFila = this.tablagiraduria.getSelectedRow();
        this.giraduria.setText(this.tablagiraduria.getValueAt(nFila, 0).toString());
        this.nombregiraduria.setText(this.tablagiraduria.getValueAt(nFila, 1).toString());

        this.BGiraduria.setVisible(false);
        this.jTBuscarGiraduria.setText("");
        this.vencimiento.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_AceptarGirActionPerformed

    private void SalirGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirGirActionPerformed
        this.BGiraduria.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_SalirGirActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        giraduriaDAO girDAO = new giraduriaDAO();
        giraduria gi = null;
        try {
            gi = girDAO.buscarId(Integer.valueOf(this.giraduria.getText()));
            if (gi.getCodigo() == 0) {
                GrillaBuscar grillagi = new GrillaBuscar();
                Thread hilogi = new Thread(grillagi);
                hilogi.start();
                BGiraduria.setModal(true);
                BGiraduria.setSize(482, 575);
                BGiraduria.setLocationRelativeTo(null);
                BGiraduria.setVisible(true);
                BGiraduria.setTitle("Buscar Giraduria");
                BGiraduria.setModal(false);
            } else {
                nombregiraduria.setText(gi.getNombre());
                //Establecemos un título para el jDialog
            }
            vencimiento.requestFocus();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR:" + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarActionPerformed

    private void giraduriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giraduriaActionPerformed
        this.buscar.doClick();
        // TODO add your handling code here:
    }//GEN-LAST:event_giraduriaActionPerformed

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
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(generar_seguros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(generar_seguros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(generar_seguros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(generar_seguros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new generar_seguros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AceptarGir;
    private javax.swing.JDialog BGiraduria;
    private javax.swing.JButton Ejecutar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton SalirGir;
    private javax.swing.JButton buscar;
    private javax.swing.JComboBox combogiraduria;
    private javax.swing.JTextField giraduria;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTBuscarGiraduria;
    private org.edisoncor.gui.label.LabelTask labelTask1;
    private javax.swing.JTextField nombregiraduria;
    private javax.swing.JTextField nombreservicio;
    private javax.swing.JFormattedTextField porcentaje;
    private javax.swing.JTextField servicio;
    private javax.swing.JTable tablagiraduria;
    private com.toedter.calendar.JDateChooser vencimiento;
    // End of variables declaration//GEN-END:variables

    private class GenerarAportes extends Thread {

        public void run() {
            Date Fecha = ODate.de_java_a_sql(vencimiento.getDate());
            UUID id = new UUID();
            String iddoc = null;
            int mes = vencimiento.getCalendar().get(Calendar.MONTH);
            mes = mes + 1;
            int anual = vencimiento.getCalendar().get(Calendar.YEAR);
            int nGiraduria = Integer.valueOf(giraduria.getText());
            cuenta_clienteDAO ctaDAO = new cuenta_clienteDAO();
            cuenta_segurosDAO segDAO = new cuenta_segurosDAO();

            try {
                ctaDAO.borrarCuentaxGiraduria(nGiraduria, mes, anual, Integer.valueOf(servicio.getText()));
                segDAO.borrarSegurosxGiraduria(nGiraduria, mes, anual, Integer.valueOf(servicio.getText()));
            } catch (SQLException ex) {
                Exceptions.printStackTrace(ex);
            }

            String cComision = porcentaje.getText();
            cComision = cComision.replace(",", ".");
            String detacuota = "[";
            String detaseg = "[";
            String cSuc = "1";
            String cVend = "1";
            String cMon = "1";
            String cCuota = "1";
            clienteDAO cliDAO = new clienteDAO();
            // cuenta_clientes cta = new cuenta_clientes();
            try {
                for (cuenta_clientes cta : ctaDAO.ResumenSaldoOrdenes(nGiraduria, Fecha)) {
                    double nImporteSeguro = Math.round(cta.getSaldo().doubleValue() * Double.valueOf(cComision) / 100);
                    System.out.println(nImporteSeguro);
                    if (nImporteSeguro > 0) {
                        iddoc = UUID.crearUUID();
                        iddoc = iddoc.substring(1, 25);
                        String lineacuota = "{iddocumento : '" + iddoc + "',"
                                + "creferencia : '" + iddoc + "',"
                                + "documento : " + String.valueOf(cta.getCliente().getCodigo()) + ","
                                + "fecha : " + Fecha + ","
                                + "vencimiento : " + Fecha + ","
                                + "giraduria : " + giraduria.getText() + ","
                                + "cliente : " + String.valueOf(cta.getCliente().getCodigo()) + ","
                                + "sucursal: " + cSuc + ","
                                + "moneda : " + cMon + ","
                                + "comprobante : " + servicio.getText() + ","
                                + "vendedor : " + cVend + ","
                                + "importe : " + String.valueOf(nImporteSeguro) + ","
                                + "numerocuota : " + cCuota + ","
                                + "cuota : " + cCuota + ","
                                + "saldo : " + String.valueOf(nImporteSeguro)
                                + "},";
                        detacuota += lineacuota;

                        String lineaseg = "{iddocumento : '" + iddoc + "',"
                                + "creferencia : '" + iddoc + "',"
                                + "documento : " + String.valueOf(cta.getCliente().getCodigo()) + ","
                                + "fecha : " + Fecha + ","
                                + "vencimiento : " + Fecha + ","
                                + "giraduria : " + giraduria.getText() + ","
                                + "cliente : " + String.valueOf(cta.getCliente().getCodigo()) + ","
                                + "sucursal: " + cSuc + ","
                                + "moneda : " + cMon + ","
                                + "comprobante : " + servicio.getText() + ","
                                + "vendedor : " + cVend + ","
                                + "importe : " + String.valueOf(nImporteSeguro) + ","
                                + "numerocuota : " + cCuota + ","
                                + "cuota : " + cCuota + ","
                                + "saldo : " + String.valueOf(cta.getSaldo()) + ","
                                + "importeseguro : " + String.valueOf(nImporteSeguro)
                                + "},";
                        detaseg += lineaseg;
                    }

                }
                if (!detacuota.equals("[")) {
                    detacuota = detacuota.substring(0, detacuota.length() - 1);
                }
                detacuota += "]";

                System.out.println(detacuota);

                if (!detaseg.equals("[")) {
                    detaseg = detaseg.substring(0, detaseg.length() - 1);
                }
                detaseg += "]";

                System.out.println(detaseg);

                ctaDAO.guardarCuentaxGiraduria(detacuota);
                segDAO.guardarCuentaSegurosxGiraduria(detaseg);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Proceso de Generación de Seguros, Terminado: ");

        }
    }

    private class GrillaBuscar extends Thread {

        public void run() {

            //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
            int cantidadRegistro = modelogiraduria.getRowCount();
            for (int i = 1; i <= cantidadRegistro; i++) {
                modelogiraduria.removeRow(0);
            }
            giraduriaDAO DAOGIR = new giraduriaDAO();
            try {
                for (giraduria gi : DAOGIR.todos()) {
                    String Datos[] = {String.valueOf(gi.getCodigo()), gi.getNombre()};
                    modelogiraduria.addRow(Datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
            }

            tablagiraduria.setRowSorter(new TableRowSorter(modelogiraduria));
            int cantFilas = tablagiraduria.getRowCount();
        }
    }

}

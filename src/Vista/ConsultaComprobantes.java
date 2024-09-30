/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConsultaUsuarios.java
 *
 * Created on 26-mar-2011, 15:51:22
 */
package Vista;

import Clases.ConsultaModelo;
import Clases.Parametros;
import Conexion.Conexion;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author One
 */
public class ConsultaComprobantes extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    ConsultaModelo modelo = new ConsultaModelo();
    private TableRowSorter trsfiltro;

    /**
     * Creates new form ConsultaTablas
     *
     */
    //Recibe como Parametro el nombre de la tabla que va consultar
    public ConsultaComprobantes(String tabla) {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
        cargarTitulo();
        cargarTabla("SELECT codigo,nombre,tasainteres FROM " + tabla +" where tipo=6 ORDER BY nombre ");
        Buscar.setText("");
        Buscar.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonElegir = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        panelTranslucidoComplete21 = new org.edisoncor.gui.panel.PanelTranslucidoComplete2();
        Buscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccionar Cliente");
        setAlwaysOnTop(true);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(modelo);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTable1);

        jButtonElegir.setText("Seleccionar");
        jButtonElegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonElegirActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Salir");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        panelTranslucidoComplete21.setColorPrimario(new java.awt.Color(102, 153, 255));
        panelTranslucidoComplete21.setColorSecundario(new java.awt.Color(0, 204, 255));

        Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Filtrar Busqueda por");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Código" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTranslucidoComplete21Layout = new javax.swing.GroupLayout(panelTranslucidoComplete21);
        panelTranslucidoComplete21.setLayout(panelTranslucidoComplete21Layout);
        panelTranslucidoComplete21Layout.setHorizontalGroup(
            panelTranslucidoComplete21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucidoComplete21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(33, 33, 33)
                .addGroup(panelTranslucidoComplete21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelTranslucidoComplete21Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Buscar))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        panelTranslucidoComplete21Layout.setVerticalGroup(
            panelTranslucidoComplete21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTranslucidoComplete21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTranslucidoComplete21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(panelTranslucidoComplete21Layout.createSequentialGroup()
                        .addGroup(panelTranslucidoComplete21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jButtonElegir)
                .addGap(35, 35, 35)
                .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelTranslucidoComplete21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(panelTranslucidoComplete21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonElegir))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonElegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonElegirActionPerformed
        // TODO add your handling code here:
        String texto = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        Parametros.CODIGO_ELEGIDO = Integer.parseInt(texto);
        Parametros.NOMBRE_ELEGIDO = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
        dispose();
    }//GEN-LAST:event_jButtonElegirActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.Buscar.requestFocus();
    }//GEN-LAST:event_formWindowGainedFocus

    private void BuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarKeyPressed
        this.Buscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (Buscar.getText()).toUpperCase();
                Buscar.setText(cadena);
                int indiceColumnaTabla = 0;
                switch (jComboBox1.getSelectedIndex()) {
                    case 0:
                        indiceColumnaTabla = 1;
                        break;//por nombre
                    case 1:
                        indiceColumnaTabla = 0;
                        break;//por codigo
                }
                repaint();
                filtro(indiceColumnaTabla);
            }
        });
        trsfiltro = new TableRowSorter(jTable1.getModel());
        jTable1.setRowSorter(trsfiltro);
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarKeyPressed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed
    public void filtro(int nNumeroColumna) {
        trsfiltro.setRowFilter(RowFilter.regexFilter(Buscar.getText(), nNumeroColumna));
    }

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Denominación");
    }

    private void cargarTabla(String sql) {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        int[] anchos = {120, 250};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable1.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        try {
            results = stm.executeQuery(sql);
            while (results.next()) {
                // Se crea un array que será una de las filas de la tabla.
                Object[] fila = new Object[2]; // Hay 8 columnas en la tabla
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                fila[0] = results.getString("codigo");
                fila[1] = results.getString("nombre");
                // Se añade al modelo la fila completa.
                modelo.addRow(fila);
            }
            this.jTable1.setRowSorter(new TableRowSorter(modelo));
            this.jTable1.updateUI();
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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
               // new ConsultaTablas().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Buscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonElegir;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private org.edisoncor.gui.panel.PanelTranslucidoComplete2 panelTranslucidoComplete21;
    // End of variables declaration//GEN-END:variables
}
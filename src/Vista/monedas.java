/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Conexion.BDConexion;
import Conexion.Conexion;
import Conexion.Control;
import Conexion.ObtenerFecha;
import Conexion.ObtenerNumero;
import Modelo.Tablas;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author FedeXavier
 */
public class monedas extends javax.swing.JFrame {

    Conexion con = null;
    Statement stm = null;
    Tablas modelo = new Tablas();
    JScrollPane scroll = new JScrollPane();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat formato = new DecimalFormat("#,###.##");

    /**
     * Creates new form Template2
     */
    public monedas() {
        initComponents();
        this.jTextEtiqueta.setEnabled(false);
        this.preciocompra.setEnabled(false);
        this.precioventa.setEnabled(false);
        this.jTextField3.setVisible(false);
        this.setLocationRelativeTo(null); //Centramos el formulario
        this.cargarTitulo();
        this.cargarTabla("SELECT * FROM monedas ORDER BY codigo");
        //Asignamos el formato a los campos tipo date
        // damos formato de seleccion completo a estos objetos
        DefaultTableCellRenderer TablaRenderer = new DefaultTableCellRenderer();
        TablaRenderer.setHorizontalAlignment(SwingConstants.RIGHT); // aqui defines donde alinear 
        this.jTable2.getColumnModel().getColumn(3).setCellRenderer(TablaRenderer);
        this.jTable2.getColumnModel().getColumn(4).setCellRenderer(TablaRenderer);
        this.preciocompra.setHorizontalAlignment(JTextField.RIGHT);
        this.precioventa.setHorizontalAlignment(JTextField.RIGHT);
    }
    ObtenerNumero on = new ObtenerNumero();
    Control hand = new Control();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextNombre = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextEtiqueta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        preciocompra = new org.jdesktop.swingx.JXFormattedTextField();
        precioventa = new org.jdesktop.swingx.JXFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/document_edit.png"))); // NOI18N
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setEnabled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/document_delete.png"))); // NOI18N
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Close.png"))); // NOI18N
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.JLabel() {
            public javax.swing.Icon getIcon() {
                try {
                    return new javax.swing.ImageIcon(
                        new java.net.URL("file:/C:/Sistemas/EvolutionII/EvolutionII/src/Images/accept.png")
                    );
                } catch (java.net.MalformedURLException e) {
                }
                return null;
            }
        }.getIcon());
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.setEnabled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/document_add.png"))); // NOI18N
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel1.setText("Código");

        jTextCodigo.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jTextCodigo.setEnabled(false);
        jTextCodigo.setSelectionColor(new java.awt.Color(0, 63, 62));

        jLabel2.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel2.setText("Descripción");

        jTextNombre.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jTextNombre.setEnabled(false);
        jTextNombre.setSelectionColor(new java.awt.Color(0, 63, 62));
        jTextNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNombreActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setEnabled(false);

        jTextEtiqueta.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jTextEtiqueta.setEnabled(false);
        jTextEtiqueta.setSelectionColor(new java.awt.Color(0, 63, 62));
        jTextEtiqueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextEtiquetaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel4.setText("Etiqueta");

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel6.setText("Precio C.");

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel7.setText("Precio V.");

        preciocompra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));

        precioventa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(precioventa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                                .addComponent(preciocompra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(preciocompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precioventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(44, 44, 44)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        jTable2.setModel(modelo);
        jTable2.setSelectionBackground(new java.awt.Color(51, 204, 255));
        jTable2.setSelectionForeground(new java.awt.Color(0, 0, 255));
        jTable2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable2FocusGained(evt);
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jTable2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable2PropertyChange(evt);
            }
        });
        jTable2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setForeground(new java.awt.Color(255, 0, 0));
        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Monedas - Cotizaciones");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTable2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable2PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2PropertyChange

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int nFila = this.jTable2.getSelectedRow();
        this.jTextCodigo.setText(this.jTable2.getValueAt(nFila, 0).toString());
        this.jTextNombre.setText(this.jTable2.getValueAt(nFila, 1).toString());
        this.jTextEtiqueta.setText(this.jTable2.getValueAt(nFila, 2).toString());
        this.preciocompra.setText(this.jTable2.getValueAt(nFila, 3).toString());
        this.precioventa.setText(this.jTable2.getValueAt(nFila, 4).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable2KeyPressed
        int nFila = this.jTable2.getSelectedRow();
        this.jTextCodigo.setText(this.jTable2.getValueAt(nFila, 0).toString());
        this.jTextNombre.setText(this.jTable2.getValueAt(nFila, 1).toString());
        this.jTextEtiqueta.setText(this.jTable2.getValueAt(nFila, 2).toString());
        this.preciocompra.setText(this.jTable2.getValueAt(nFila, 3).toString());
        this.precioventa.setText(this.jTable2.getValueAt(nFila, 4).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2KeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        BDConexion BD = new BDConexion();
        BD.borrarRegistro("monedas", "codigo=" + this.jTextCodigo.getText().trim());
        this.cargarTabla("SELECT * FROM monedas ORDER BY codigo");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void Limpiar() {
        this.jTextCodigo.setText("");
        this.jTextNombre.setText("");
        this.jTextEtiqueta.setText("");
        this.preciocompra.setText("");
        this.precioventa.setText("");
    }

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        this.Limpiar();
        // Habilitamos los botones y objetos que necesitamos
        this.jButton9.setEnabled(true);

        this.jTextNombre.setEnabled(true);
        this.jTextEtiqueta.setEnabled(true);
        this.preciocompra.setEnabled(true);
        this.precioventa.setEnabled(true);

        this.jButton6.setEnabled(false);
        this.jButton7.setEnabled(false);
        this.jButton10.setEnabled(false);
        this.jTextNombre.requestFocus();
        this.jTextField3.setText("A");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        //Funcion para agregar Registros
        BDConexion BD = new BDConexion();
        String cParametro = this.jTextField3.getText();
        String cLetra = "A";

        String Compra = this.preciocompra.getText();
        String Venta = this.precioventa.getText();
        // con el metodo replace borramos los puntos y remplazamos la coma decimal
        // por el punto para su correcta captura en la base de datos
        if (this.preciocompra.getText().trim().length() > 0) {
            Compra = Compra.replace(".", "");
            Compra = Compra.replace(",", ".");
        }

        if (this.precioventa.getText().trim().length() > 0) {
            Venta = Venta.replace(".", "");
            Venta = Venta.replace(",", ".");
        }

        if (cParametro.equals(cLetra)) {
            BD.insertarRegistro("monedas", "nombre,etiqueta,compra,venta", "'" + this.jTextNombre.getText() + "','" + this.jTextEtiqueta.getText() + "','" + Compra + "','" + Venta + "'");
        } else {
            BD.actualizarRegistro("monedas", "nombre ='" + this.jTextNombre.getText() + "',etiqueta='" + this.jTextEtiqueta.getText() + "',compra='" + Compra + "',venta='" + Venta + "'", "codigo= " + this.jTextCodigo.getText());
        }
        this.Limpiar();
        this.cargarTabla("SELECT * FROM monedas ORDER BY codigo");
        this.jTextNombre.setText("");
        this.jTextField3.setText("");
        this.jTextNombre.setEnabled(false);
        this.preciocompra.setEnabled(false);
        this.precioventa.setEnabled(false);
        this.jTextEtiqueta.setText("");
        this.jTextEtiqueta.setEnabled(false);
        this.preciocompra.setText("");
        this.precioventa.setText("");
        this.jTable2.grabFocus();
        this.jButton10.setEnabled(true);
        this.jButton9.setEnabled(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTable2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable2FocusGained
        int nFila = this.jTable2.getRowCount();
        this.jTextCodigo.setText(this.jTable2.getValueAt(nFila - 1, 0).toString());
        this.jTextNombre.setText(this.jTable2.getValueAt(nFila - 1, 1).toString());
        this.jTextEtiqueta.setText(this.jTable2.getValueAt(nFila - 1, 2).toString());
        this.preciocompra.setText(this.jTable2.getValueAt(nFila - 1, 3).toString());
        this.precioventa.setText(this.jTable2.getValueAt(nFila - 1, 4).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2FocusGained

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.jButton9.setEnabled(true);
        // Habilitamos los botones y objetos que necesitamos

        this.jTextNombre.setEnabled(true);
        this.jTextEtiqueta.setEnabled(true);
        this.preciocompra.setEnabled(true);
        this.precioventa.setEnabled(true);
        this.jButton7.setEnabled(false);
        this.jButton6.setEnabled(false);
        this.jButton10.setEnabled(false);
        this.jTextNombre.requestFocus();
        this.jTextField3.setText("M");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextEtiquetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextEtiquetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextEtiquetaActionPerformed

    private void jTextNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNombreActionPerformed

    private void cargarTitulo() {
        modelo.addColumn("Código");
        modelo.addColumn("Descripción");
        modelo.addColumn("Etiqueta");
        modelo.addColumn("Compra");
        modelo.addColumn("Venta");
    }

    private void cargarTabla(String sql) {
        //Llamo a la clase conexion para conectarme a la base de datos
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        //Antes de Cargar el Jtable Ajustamos el ancho de las columnas con el Ancho que se nos antoje
        int[] anchos = {70, 155, 90, 100, 100};
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            this.jTable2.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        int cantidadRegistro = modelo.getRowCount();
        for (int i = 1; i <= cantidadRegistro; i++) {
            modelo.removeRow(0);
        }
        try {
            results = stm.executeQuery(sql);
            while (results.next()) {
                // Se crea un array que será una de las filas de la tabla.
                // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
                Object[] fila = new Object[6]; // Hay 10 columnas en la tabla
                fila[0] = results.getString("codigo");
                fila[1] = results.getString("nombre");
                fila[2] = results.getString("etiqueta");
                fila[3] = formato.format(results.getDouble("compra"));
                fila[4] = formato.format(results.getDouble("venta"));
                modelo.addRow(fila);          // Se añade al modelo la fila completa.
            }
            this.jTable2.setRowSorter(new TableRowSorter(modelo));
            this.jTable2.updateUI();
            int cantFilas = this.jTable2.getRowCount();
            if (cantFilas > 0) {
                this.jButton6.setEnabled(true);
                this.jButton7.setEnabled(true);
                this.jButton10.setEnabled(true);
            } else {
                this.jButton6.setEnabled(false);
                this.jButton7.setEnabled(false);
                this.jButton10.setEnabled(true);
            }
            stm.close();
            this.jTable2.requestFocus();
        } catch (SQLException ex2) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Ingresar a los Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex2) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "El Sistema No Puede Conectarse a la Base de Datos",
                    "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(monedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(monedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(monedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(monedas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new monedas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextCodigo;
    private javax.swing.JTextField jTextEtiqueta;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextNombre;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.jdesktop.swingx.JXFormattedTextField preciocompra;
    private org.jdesktop.swingx.JXFormattedTextField precioventa;
    // End of variables declaration//GEN-END:variables
}
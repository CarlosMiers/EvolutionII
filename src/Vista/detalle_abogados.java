/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.ControlGrabado;
import Clases.ConvertirMayusculas;
import Conexion.BDConexion;
import Conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Conexion.ObtenerFecha;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author MEC
 */
public class detalle_abogados extends javax.swing.JFrame {

    //Inicializamos las variables de conexion a la base de Datos
    Conexion con = null;
    Statement stm = null;

    public detalle_abogados(String Opcion) {
        initComponents();
        this.setLocationRelativeTo(null); //Centramos el formulario
        //Verificamos el parametro y determinamos si quiero un registro nuevo
        //o va actualizar uno existente
        this.jTextCodigo.setHorizontalAlignment(this.jTextCodigo.RIGHT);
        if (Opcion == "new") {
            this.jTextCodigo.setText("");
            // Si es nuevo el registro asignamos la fecha de hoy al jDataChosser
            Calendar c2 = new GregorianCalendar();
            this.jDateNacimiento.setCalendar(c2);
        } else {
            this.jTextCodigo.setText(Opcion);
            this.consultarTabla(this.jTextCodigo.getText());
        }
        // Damos el formato de fecha adecuado al objeto jDateChooser
        this.jDateNacimiento.setDateFormatString("dd/MM/yyyy");
        // Enviamos el foco al objeto del nombre
        this.jTextnombre.requestFocus();
        this.jTextnombre.selectAll();
    }
    ObtenerFecha ODate = new ObtenerFecha();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Panel1Asesores = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextCodigo = new javax.swing.JTextField();
        jTextConyugue = new javax.swing.JTextField();
        jTextnombre = new javax.swing.JTextField();
        jComboEstadocivil = new javax.swing.JComboBox();
        jTextcedula = new javax.swing.JTextField();
        jDateNacimiento = new com.toedter.calendar.JDateChooser();
        jTextDireccion = new javax.swing.JTextField();
        jTextFono = new javax.swing.JTextField();
        jTextCelular = new javax.swing.JTextField();
        jTextmail = new javax.swing.JTextField();
        jComboEstado = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setForeground(new java.awt.Color(255, 0, 0));
        panel1.setColorPrimario(new java.awt.Color(102, 153, 255));
        panel1.setColorSecundario(new java.awt.Color(0, 204, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel15.text")); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel1.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel2.text")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel3.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel4.text")); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel7.text")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel8.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel8.text")); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel9.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel9.text")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel10.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel10.text")); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel11.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel11.text")); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel12.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel12.text")); // NOI18N

        jTextCodigo.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextCodigo.text")); // NOI18N
        jTextCodigo.setEnabled(false);

        jTextConyugue.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextConyugue.text")); // NOI18N
        jTextConyugue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextConyugueKeyPressed(evt);
            }
        });

        jTextnombre.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextnombre.text")); // NOI18N
        jTextnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextnombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextnombreKeyReleased(evt);
            }
        });

        jComboEstadocivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Casado/a", "Soltero/a", "Separado/a", "Divorciado/a", "Viudo/a" }));
        jComboEstadocivil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboEstadocivilKeyPressed(evt);
            }
        });

        jTextcedula.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextcedula.text")); // NOI18N
        jTextcedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextcedulaKeyPressed(evt);
            }
        });

        jDateNacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateNacimientoKeyPressed(evt);
            }
        });

        jTextDireccion.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextDireccion.text")); // NOI18N
        jTextDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDireccionKeyPressed(evt);
            }
        });

        jTextFono.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextFono.text")); // NOI18N
        jTextFono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFonoKeyPressed(evt);
            }
        });

        jTextCelular.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextCelular.text")); // NOI18N
        jTextCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextCelularActionPerformed(evt);
            }
        });
        jTextCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextCelularKeyPressed(evt);
            }
        });

        jTextmail.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jTextmail.text")); // NOI18N
        jTextmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextmailKeyPressed(evt);
            }
        });

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Inactivo", "Activo" }));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel13.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel13.text")); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel14.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jLabel14.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextCelular, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                .addComponent(jTextFono, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jTextcedula, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboEstadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextmail, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(125, 125, 125)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(113, 113, 113)
                                .addComponent(jTextnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextcedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboEstadocivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextConyugue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(83, Short.MAX_VALUE))))
        );

        Panel1Asesores.addTab(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel1Asesores)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel1Asesores)
        );

        jButton1.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jButton1.text")); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(org.openide.util.NbBundle.getMessage(detalle_abogados.class, "detalle_abogados.jButton2.text")); // NOI18N
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(602, 602, 602)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void consultarTabla(String campoclave) {
        con = new Conexion();
        stm = con.conectar();
        ResultSet results = null;
        try {
            results = stm.executeQuery("select * from abogados where codigo=" + this.jTextCodigo.getText());
            if (results.next()) {
                this.jTextnombre.setText(results.getString("nombre"));
                this.jTextcedula.setText(results.getString("cedula"));
                this.jComboEstadocivil.setSelectedItem(results.getString("estadocivil"));
                this.jTextConyugue.setText(results.getString("conyugue"));
                this.jDateNacimiento.setDate(results.getDate("nacimiento"));
                this.jTextDireccion.setText(results.getString("direccion"));
                this.jTextFono.setText(results.getString("telefono"));
                this.jTextCelular.setText(results.getString("celular"));
                this.jTextmail.setText(results.getString("mail"));
                this.jComboEstado.setSelectedIndex(results.getInt("estado"));
            }
            stm.close();
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

    private void jTextnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextnombreKeyPressed
        //Pasar el foco a otro objeto con la tecla Enter o cursor
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextcedula.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextnombreKeyPressed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void jTextcedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextcedulaKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboEstadocivil.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextnombre.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextcedulaKeyPressed

    private void jTextConyugueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextConyugueKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jDateNacimiento.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jComboEstadocivil.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextConyugueKeyPressed

    private void jDateNacimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateNacimientoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextDireccion.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextConyugue.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateNacimientoKeyPressed

    private void jTextDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDireccionKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextFono.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jDateNacimiento.requestFocus();
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jTextDireccionKeyPressed

    private void jTextFonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFonoKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextCelular.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextDireccion.requestFocus();
        }   // TODO add your handling code here:
    }//GEN-LAST:event_jTextFonoKeyPressed

    private void jTextCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCelularActionPerformed

    private void jTextCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCelularKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextmail.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextFono.requestFocus();
        }   // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextCelularKeyPressed

    private void jTextmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextmailKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jComboEstado.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextCelular.requestFocus();
        }   // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextmailKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ControlGrabado.REGISTRO_GRABADO="";
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ControlGrabado.REGISTRO_GRABADO="SI";
        BDConexion BD = new BDConexion();
        String Operacion = this.jTextCodigo.getText();
        Date FechaNac = ODate.de_java_a_sql(this.jDateNacimiento.getDate());

        if (Operacion.isEmpty()) {
            BD.insertarRegistro("abogados", "nombre,cedula,nacimiento,estadocivil,conyugue,direccion,telefono,celular,mail,estado", "'" + this.jTextnombre.getText() + "','" + this.jTextcedula.getText() + "','" + FechaNac + "','" + this.jComboEstadocivil.getSelectedItem() + "','" + this.jTextConyugue.getText() + "','" + this.jTextDireccion.getText() + "','" + this.jTextFono.getText() + "','" + this.jTextCelular.getText() + "','" + this.jTextmail.getText() + "','" + this.jComboEstado.getSelectedIndex() + "'");
        } else {
           BD.actualizarRegistro("abogados", " nombre='"+ this.jTextnombre.getText()+"',cedula='"+this.jTextcedula.getText()+"',nacimiento='"+ FechaNac + "',estadocivil='"+this.jComboEstadocivil.getSelectedItem()+ "',conyugue='"+ this.jTextConyugue.getText() +"',direccion='"+ this.jTextDireccion.getText()+"',telefono='"+ this.jTextFono.getText()+"',celular='"+this.jTextCelular.getText() +"',mail='"+this.jTextmail.getText()+ "',estado ='"+this.jComboEstado.getSelectedIndex()+ "'", "codigo= " + this.jTextCodigo.getText());
        }
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboEstadocivilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboEstadocivilKeyPressed
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.jTextConyugue.requestFocus();
        }
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.jTextcedula.requestFocus();
        }   // TODO add your handling code
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboEstadocivilKeyPressed

    private void jTextnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextnombreKeyReleased
     String letras = ConvertirMayusculas.cadena(jTextnombre);
        jTextnombre.setText(letras);        // TODO add your handling code here:
    }//GEN-LAST:event_jTextnombreKeyReleased

    public void MostrarDatos(String Operacion) {
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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(detalle_abogados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detalle_abogados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detalle_abogados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detalle_abogados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //    new detalle_asesores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Panel1Asesores;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboEstado;
    private javax.swing.JComboBox jComboEstadocivil;
    private com.toedter.calendar.JDateChooser jDateNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextCelular;
    private javax.swing.JTextField jTextCodigo;
    private javax.swing.JTextField jTextConyugue;
    private javax.swing.JTextField jTextDireccion;
    private javax.swing.JTextField jTextFono;
    private javax.swing.JTextField jTextcedula;
    private javax.swing.JTextField jTextmail;
    private javax.swing.JTextField jTextnombre;
    private org.edisoncor.gui.panel.Panel panel1;
    // End of variables declaration//GEN-END:variables
}
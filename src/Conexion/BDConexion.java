/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Conexion.Conexion;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import Clases.Busqueda;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import org.openide.util.Exceptions;

/**
 *
 * @author MEC
 */
public class BDConexion {

    Conexion con = null;
    Statement stm, st = null;
    int resultado;

    public boolean BorrarDetalles(String tabla, String condicion) {
        con = new Conexion();
        stm = con.conectar();
        try {
            stm.executeUpdate("delete from " + tabla + " where " + condicion);
            System.out.println(condicion);

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return true;
    }

    public boolean borrarRegistro(String tabla, String condicion) {
        con = new Conexion();
        stm = con.conectar();
        try {

            JOptionPane optionPane = new JOptionPane();
            Object[] opciones = {"   Si   ", "   No   "};

            int ret = JOptionPane.showOptionDialog(null, "Desea Borrar? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);

            if (ret == 0) {
                int i = stm.executeUpdate("delete from " + tabla + " where " + condicion);
                System.out.println("delete from " + tabla + " where " + condicion);
            }
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se puede borrar el registro\nEs posible que el mismo dato se este usando en otra interfaz\nVerifique y vuelva a intentar...", "Atencion", 1);
            return false;
        }
        return true;
    }

    public boolean insertarRegistro(String tabla, String campos, String valores) {
        try {
            con = new Conexion();
            stm = con.conectar();
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                int i = stm.executeUpdate("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            }
            // System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            stm.close();
        } catch (Exception e) {
            int resultado;
            System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            JOptionPane.showMessageDialog(null, "No se pudo realizar la insercion en la base de datos\nVerifique que se hayan completado todos los campos necesarios\nVuelva a intentar...\n" + e.getMessage(), "Atencion", 1);
            return false;
        }
        return true;
    }

    public boolean InsertarRegistroDetalle(String tabla, String campos, String valores) {
        try {
            con = new Conexion();
            stm = con.conectar();
            stm.executeUpdate("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            //System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            stm.close();
        } catch (Exception e) {
            int resultado;
            System.out.println("insert into " + tabla + " (" + campos + ") values (" + valores + ")");
            JOptionPane.showMessageDialog(null, "No se pudo realizar la insercion en la base de datos\nVerifique que se hayan completado todos los campos necesarios\nVuelva a intentar...\n" + e.getMessage(), "Atencion", 1);
            return false;
        }
        return true;
    }

    public boolean actualizarRegistro(String tabla, String campos, String criterio) {
        try {
            con = new Conexion();
            stm = con.conectar();
            Object[] opciones = {"   Si   ", "   No   "};
            int ret = JOptionPane.showOptionDialog(null, "Desea Guardar este Registro ? ", "Confirmacion", 0, 3, null, opciones, opciones[0]);
            if (ret == 0) {
                int i = stm.executeUpdate("update " + tabla + " set " + campos + " where " + criterio);
            }
        } catch (Exception e) {
            int resultado;
            JOptionPane.showMessageDialog(null, "No se pudieron actualizar los datos\nEs posible que los datos se\nesten usando en otra interfaz..." + e.getMessage(), "Atencion", 1);

            System.out.println("update " + tabla + " set " + campos + " where " + criterio);
            return false;
        }
        return true;
    }

    public boolean actualizarRegistro2(String tabla, String campos, String criterio) {
        try {
            con = new Conexion();
            stm = con.conectar();
            resultado = stm.executeUpdate("update " + tabla + " set " + campos + " where " + criterio);
        } catch (Exception e) {
            int resultado;
            JOptionPane.showMessageDialog(null, "Ocurrio Un error\n" + e.getMessage(), "Atencion", 1);

            System.out.println("update " + tabla + " where " + criterio);
            return false;
        }
        return true;
    }

    //Para buscar registros y mostrar en el combo
    public ResultSet Select(String campos, String tabla, String condicion) {//recibe un parametro de tipo string
        con = new Conexion();
        st = con.conectar();
        ResultSet registro = null;//esta variable  esta preparada para almacenar una serie de registros como una tabla el resultado de las sentencias de SQL
        try {
            registro = st.executeQuery("SELECT " + campos + " from " + tabla + " " + condicion + "");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error!!", "Atención", JOptionPane.ERROR_MESSAGE);
        }
        return registro;
    }

    public ResultSet Select(String sql) {
        con = new Conexion();
        stm = con.conectar();
        ResultSet registros = null;
        try {
            registros = stm.executeQuery(sql);
        } catch (SQLException ex) {
        }
        return registros;
    }

    public void cargarCombo(String sql, JComboBox combo) {
        con = new Conexion();
        stm = con.conectar();
        ResultSet registros = null;

        int contar = 0;
        try {
            registros = stm.executeQuery(sql);
            while (registros.next()) {
            String columnas[] = new String[2];
                columnas[0] = registros.getString(1);
                columnas[1] = registros.getString(2);
                combo.addItem(columnas);
                System.out.println(registros.getString(1) + "-" + registros.getString(2));
                contar++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurri\u00f3 Un error: " + e.toString(), "Atenci\u00f3n",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (contar > 0) {
            combo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(
                        JList l, Object o, int i, boolean s, boolean f) {
                    try {
                        return new JLabel(((String[]) o)[1]);
                        //return new JLabel (((String[])o)[1]);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ocurri\u00f3 Un error", "Atenci\u00f3n",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
            });
        }
        return;
    }
}

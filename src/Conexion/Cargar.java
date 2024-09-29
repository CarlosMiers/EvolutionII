/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Federico Lopez
 */
public class Cargar {
    Conexion con = null;
    Statement stm = null;
    ObtenerNumero on = new ObtenerNumero();
    DefaultTableModel modelo = new DefaultTableModel();
    ResultSet rs = null;
    Statement st = null;
    private int can;

    public JComboBox combobox(JComboBox cb, String sentencia) {
      Conexion con = new Conexion();
        try {
            // cb = new JComboBox();
            ResultSet rs = st.executeQuery(sentencia);
            while (rs.next()) {
                cb.addItem(rs.getObject(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cb;
    }

    public JList LlenarList(JList lista, String sentencia) {
        try {
            DefaultListModel modeloLista = new DefaultListModel();
            rs = st.executeQuery(sentencia);
            while (rs.next()) {
                modeloLista.addElement(rs.getObject(1));
            }
            lista.setModel(modeloLista);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public JTable cargar(JTable jTable, ResultSet rs) {

        try {
            Conexion con = new Conexion();
            jTable.setModel(modelo);
            modelo.setColumnCount(0);
            modelo.setRowCount(0);
            //rs = Consulta.consulta(c, sentencia);
            ResultSetMetaData rsd = rs.getMetaData();
            can = rsd.getColumnCount();
            for (int i = 1; i <= can; i++) {
                modelo.addColumn(rsd.getColumnName(i));
            }
            while (rs.next()) {
                String[] dato = new String[can];
                for (int i = 0; i < can; i++) {
                    dato[i] = rs.getString(i + 1);
                }
                modelo.addRow(dato);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Cargar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jTable;
    }

    public JTable cargar_corrido(JTable jTable, ResultSet rs) {

        try {
            DefaultTableModel modelo_corrido = (DefaultTableModel) jTable.getModel();
            jTable.setModel(modelo_corrido);
            //modelo_corrido.setColumnCount(0);
            // modelo_corrido.setRowCount(0);
            ResultSetMetaData rsd = rs.getMetaData();
            can = rsd.getColumnCount();
            if (modelo_corrido.getColumnCount() == 0) {
                for (int i = 1; i <= can; i++) {
                    modelo_corrido.addColumn(rsd.getColumnName(i));
                }
            }
            // modelo_corrido.addRow(new Object[0]);
            while (rs.next()) {
                String[] dato = new String[can];
                for (int i = 0; i < can; i++) {
                    dato[i] = rs.getString(i + 1);
                }
                modelo_corrido.addRow(dato);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Cargar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jTable;
    }

    public int filas() {
        return modelo.getRowCount();
    }

    public JTable ancho(JTable tabla, int anchura[]) {

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchura[i]);
        }

        return tabla;
    }

    public ArrayList cargarAL(ArrayList<String> al, ResultSet rs) {
        try {
           Conexion con = new Conexion();
            //  ResultSetMetaData rsd = rs.getMetaData();
            //   can = rsd.getColumnCount();
            while (rs.next()) {
                al.add(String.valueOf(rs.getObject(1)));
            }
            //  c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Cargar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return al;
    }

    public void limpiar(JTable tabla) {
        DefaultTableModel m = new DefaultTableModel();
        tabla.setModel(m);
    }

    public int total(JTable tabla, int fila) {
        int total = 0;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String nro = String.valueOf(tabla.getValueAt(i, fila));
            int nuevo = on.point_to_number(nro);
            total = total + nuevo;
        }
        return total;
    }

    public JTable agregar_detalle(JTable tabla, int col, String detalle) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String det = String.valueOf(tabla.getValueAt(i, col)) + detalle;
            tabla.setValueAt(det, i, col);
        }
        return tabla;
    }

    public JTable poner_puntos(JTable tabla, int col) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String puntos = String.valueOf(tabla.getValueAt(i, col));
            int pun = on.point_to_number(puntos);
            tabla.setValueAt(on.getNumero(pun), i, col);
        }
        return tabla;
    }

    public JTable cambiarnulos(JTable tabla) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                if (tabla.getValueAt(i, j) == null) {
                    tabla.setValueAt("", i, j);
                }
            }
        }
        return tabla;
    }

    public DefaultTableModel establecermodelo() {
        DefaultTableModel model;
        model = new DefaultTableModel() {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        return model;
    }

    public JTable poner_puntos_concoma(JTable tabla, int col) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            String puntos = String.valueOf(tabla.getValueAt(i, col));
            double number = Double.parseDouble(puntos);
            int pun = (int) number;
            tabla.setValueAt(on.getNumero(pun), i, col);
        }
        return tabla;
    }
    
    public JTable nombreCol(JTable tabla, String nombre[]) {
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            //System.out.print(tabla.getColumn(i));
            tabla.getColumn(tabla.getColumnName(i)).setHeaderValue(nombre[i]);
        }
        return tabla;
    }

}

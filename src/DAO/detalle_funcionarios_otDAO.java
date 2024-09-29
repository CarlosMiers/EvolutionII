/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_funcionarios_ot;
import Modelo.ficha_empleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_funcionarios_otDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_funcionarios_ot> MostrarDetalle(Double id) throws SQLException {
        ArrayList<detalle_funcionarios_ot> lista = new ArrayList<detalle_funcionarios_ot>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT detalle_funcionarios_ot.dnumero,"
                    +"detalle_funcionarios_ot.item,"
                    +"detalle_funcionarios_ot.empleado,"
                    +"CONCAT(empleados.nombres,' ',empleados.apellidos) AS nombreempleado "
                    +" FROM detalle_funcionarios_ot "
                    +" LEFT JOIN empleados "
                    +" ON empleados.codigo=detalle_funcionarios_ot.empleado "
                    +" WHERE detalle_funcionarios_ot.dnumero=? "
                    +" ORDER BY item";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_funcionarios_ot dc = new detalle_funcionarios_ot();
                    ficha_empleado ficha = new ficha_empleado();
                    dc.setEmpleado(ficha);
                    dc.getEmpleado().setCodigo(rs.getInt("empleado"));
                    dc.getEmpleado().setNombreempleado(rs.getString("nombreempleado"));
                    dc.setDnumero(rs.getDouble("dnumero"));
                    dc.setItem(rs.getInt("item"));
                    lista.add(dc);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalle(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_funcionarios_ot WHERE dnumero=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    
        public detalle_funcionarios_ot insertarPersonal(detalle_funcionarios_ot pe) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        boolean guardado = false;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO detalle_funcionarios_ot (dnumero,item,empleado) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, pe.getDnumero());
        ps.setInt(2, pe.getItem());
        ps.setInt(3, pe.getEmpleado().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return pe;
    }

    public detalle_funcionarios_ot borrarPersonal(detalle_funcionarios_ot pe) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_funcionarios_ot WHERE dnumero=? and item=?");
        ps.setDouble(1, pe.getDnumero());
        ps.setInt(2, pe.getItem());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        return pe;
      }
  
}

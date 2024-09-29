/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_tarea_orden_trabajo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_tarea_orden_trabajoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_tarea_orden_trabajo> MostrarDetalle(double id) throws SQLException {
        ArrayList<detalle_tarea_orden_trabajo> lista = new ArrayList<detalle_tarea_orden_trabajo>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT detalle_tarea_orden_trabajo.dnumero,detalle_tarea_orden_trabajo.item,"
                    + "detalle_tarea_orden_trabajo.descripcion,"
                    + "detalle_tarea_orden_trabajo.estado "
                    + " FROM detalle_tarea_orden_trabajo "
                    + "WHERE detalle_tarea_orden_trabajo.dnumero=? "
                    + " order by detalle_tarea_orden_trabajo.item";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_tarea_orden_trabajo dc = new detalle_tarea_orden_trabajo();
                    dc.setDnumero(rs.getDouble("dnumero"));
                    dc.setItem(rs.getInt("item"));
                    dc.setDescripcion(rs.getString("descripcion")); 
                    dc.setEstado(rs.getString("estado"));
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

    public boolean borrarDetalleTareaOT(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_tarea_orden_trabajo WHERE dnumero=?");
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

}

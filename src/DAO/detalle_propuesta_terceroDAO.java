/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_propuesta_tercero;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_propuesta_terceroDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_propuesta_tercero> MostrarDetalle(double id) throws SQLException {
        ArrayList<detalle_propuesta_tercero> lista = new ArrayList<detalle_propuesta_tercero>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT detalle_propuesta_terceros.dnumero,detalle_propuesta_terceros.item,"
                    + "detalle_propuesta_terceros.proveedor,detalle_propuesta_terceros.presupuestado,"
                    + "detalle_propuesta_terceros.aprobado "
                    + " FROM detalle_propuesta_terceros "
                    + "WHERE detalle_propuesta_terceros.dnumero=? "
                    + " order by detalle_propuesta_terceros.item";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_propuesta_tercero dc = new detalle_propuesta_tercero();
                    dc.setDnumero(rs.getDouble("dnumero"));
                    dc.setProveedor(rs.getString("proveedor"));
                    dc.setItem(rs.getInt("item"));
                    dc.setPresupuestado(rs.getDouble("presupuestado")); 
                    dc.setAprobado(rs.getString("aprobado"));
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

    public boolean borrarDetallePropuestaOT(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_propuesta_terceros WHERE dnumero=?");
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

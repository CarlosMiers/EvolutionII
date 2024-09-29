/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_consulta_servicios;
import Modelo.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class detalle_consulta_serviciosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_consulta_servicios> MostrarDetalle(Double id) throws SQLException {
        ArrayList<detalle_consulta_servicios> lista = new ArrayList<detalle_consulta_servicios>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT detalle_consulta_servicios.dnumero,detalle_consulta_servicios.servicio,"
                    + "detalle_consulta_servicios.importe,productos.nombre AS nombreservicio "
                    + " FROM detalle_consulta_servicios "
                    + " LEFT JOIN productos "
                    + " ON productos.codigo=detalle_consulta_servicios.servicio "
                    + " WHERE detalle_consulta_servicios.dnumero= ?  ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_consulta_servicios dt = new detalle_consulta_servicios();
                    producto pr = new producto();
                    dt.setServicio(pr);;
                    dt.getServicio().setCodigo(rs.getString("servicio"));
                    dt.getServicio().setNombre(rs.getString("nombreservicio"));
                    dt.setImporte(rs.getDouble("importe"));
                    dt.setDnumero(rs.getDouble("dnumero"));
                    lista.add(dt);
                }
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalle(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM detalle_consulta_servicios WHERE dnumero=?");
        ps.setDouble(1, id);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ventas_anuladas;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pc_Server
 */
public class ventas_anuladasDAO {

    Conexion con = null;
    Statement st = null;

    public ventas_anuladas buscarId(String id) throws SQLException {
        ventas_anuladas v = new ventas_anuladas();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT formatofactura "
                    + " FROM ventas_anuladas "
                    + " WHERE formatofactura = '"+id+"'"
                    + " ORDER BY formatofactura ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    v.setFormatofactura(rs.getString("formatofactura"));
                    System.out.println(v.getFormatofactura());
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return v;
    }

}

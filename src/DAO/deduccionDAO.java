/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.deducciones;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class deduccionDAO {

    Conexion con = null;
    Statement st = null;

    public deducciones buscarId(int id) throws SQLException {

        deducciones ds = new deducciones();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select nprestamo,sum(supago) as total "
                    + "from deducciones "
                    + "where deducciones.nprestamo = ? "
                    + " group by deducciones.nprestamo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ds.setNprestamo(rs.getInt("nprestamo"));
                    ds.setTotaldescuento(rs.getString("total"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ds;
    }
}

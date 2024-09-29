/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.tipo_inmueble;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class tipo_inmuebleDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<tipo_inmueble> todos() throws SQLException {
        ArrayList<tipo_inmueble> lista = new ArrayList<tipo_inmueble>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM tipo_inmueble "
                + " ORDER BY tipo_inmueble.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipo_inmueble ba = new tipo_inmueble();
                ba.setCodigo(rs.getInt("codigo"));
                ba.setNombre(rs.getString("nombre"));
                lista.add(ba);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public tipo_inmueble buscarId(int id) throws SQLException {

        tipo_inmueble b = new tipo_inmueble();
        b.setCodigo(0);
        b.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select codigo,nombre  "
                    + " from tipo_inmueble "
                    + "where tipo_inmueble.codigo = ? "
                    + "order by tipo_inmueble.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    b.setCodigo(rs.getInt("codigo"));
                    b.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return b;
    }
    
}

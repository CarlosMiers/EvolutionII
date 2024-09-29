/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.familia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class familiaDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<familia> todos() throws SQLException {
        ArrayList<familia> lista = new ArrayList<familia>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM familias "
                + " ORDER BY familias.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                familia pa = new familia();
                pa.setCodigo(rs.getInt("codigo"));
                pa.setNombre(rs.getString("nombre"));
                lista.add(pa);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    
    public familia buscarId(int id) throws SQLException {
        familia familia = new  familia();
        familia.setCodigo(0);
        familia.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from familias "
                    + "where familias.codigo = ? "
                    + "order by familias.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    familia.setCodigo(rs.getInt("codigo"));
                    familia.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return familia;
    }
   
}

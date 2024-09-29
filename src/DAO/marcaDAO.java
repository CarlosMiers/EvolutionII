/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.marca;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class marcaDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<marca> todos() throws SQLException {
        ArrayList<marca> lista = new ArrayList<marca>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM marcas "
                + " ORDER BY marcas.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                marca pa = new marca();
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

    
    public marca buscarId(int id) throws SQLException {
        marca marca = new  marca();
        marca.setCodigo(0);
        marca.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from marcas "
                    + "where marcas.codigo = ? "
                    + "order by marcas.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    marca.setCodigo(rs.getInt("codigo"));
                    marca.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return marca;
    }
  
}

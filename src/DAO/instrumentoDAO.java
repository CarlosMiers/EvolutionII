/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.instrumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class instrumentoDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<instrumento> todos() throws SQLException {
        ArrayList<instrumento> lista = new ArrayList<instrumento>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM instrumentos "
                + " ORDER BY instrumentos.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                instrumento pa = new instrumento();
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

    
    public instrumento buscarId(int id) throws SQLException {

        instrumento instrumento = new  instrumento();
        instrumento.setCodigo(0);
        instrumento.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from instrumentos "
                    + "where instrumentos.codigo = ? "
                    + "order by instrumentos.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    instrumento.setCodigo(rs.getInt("codigo"));
                    instrumento.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return instrumento;
        
    }
 
}

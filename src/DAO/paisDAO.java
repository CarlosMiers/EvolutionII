/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.pais;
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
public class paisDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<pais> todos() throws SQLException {
        ArrayList<pais> lista = new ArrayList<pais>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM paises "
                + " ORDER BY paises.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pais pa = new pais();
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

    
    public pais buscarId(int id) throws SQLException {

        pais pais = new  pais();
        pais.setCodigo(0);
        pais.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from paises "
                    + "where paises.codigo = ? "
                    + "order by paises.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    pais.setCodigo(rs.getInt("codigo"));
                    pais.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return pais;
        
    }
    
    
     public pais buscarpaisId(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        pais pa = new pais();

        try {
            String sql = "select * "
                    + "from paises "
                    + "where paises.codigo = ? "
                    + "order by paises.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                  
                    
                  
                    pa.setCodigo(rs.getInt("codigo"));
                    pa.setNombre(rs.getString("nombre"));
            
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return pa;
    }
}

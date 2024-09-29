/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.rubro_emisor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class rubro_emisorDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<rubro_emisor> todos() throws SQLException {
        ArrayList<rubro_emisor> lista = new ArrayList<rubro_emisor>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM rubro_emisor "
                + " ORDER BY rubro_emisor.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rubro_emisor pa = new rubro_emisor();
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

    
    public rubro_emisor buscarId(int id) throws SQLException {
        rubro_emisor rubro = new  rubro_emisor();
        rubro.setCodigo(0);
        rubro.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from rubro_emisor "
                    + "where rubro_emisor.codigo = ? "
                    + "order by rubro_emisor.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rubro.setCodigo(rs.getInt("codigo"));
                    rubro.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return rubro;
    }
   
}

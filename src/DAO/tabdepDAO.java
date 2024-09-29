/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.tabdep;
import Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class tabdepDAO {
    Conexion con = null;
    Statement st = null;
    
      public ArrayList<tabdep> todos() throws SQLException {
        ArrayList<tabdep> lista = new ArrayList<tabdep>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM tabdep "
                + " ORDER BY tabdep.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tabdep pa = new tabdep();
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

    
    public tabdep buscarId(int id) throws SQLException {
        tabdep tabdep = new  tabdep();
        tabdep.setCodigo(0);
        tabdep.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from tabdep "
                    + "where tabdep.codigo = ? "
                    + "order by tabdep.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tabdep.setCodigo(rs.getInt("codigo"));
                    tabdep.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return tabdep;
    }
   
}

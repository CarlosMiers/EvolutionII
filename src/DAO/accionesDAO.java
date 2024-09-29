/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.acciones;
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
public class accionesDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<acciones> todos() throws SQLException {
        ArrayList<acciones> lista = new ArrayList<acciones>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM acciones "
                + " ORDER BY acciones.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                acciones pa = new acciones();
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

    
    public acciones buscarId(int id) throws SQLException {

        acciones acciones = new  acciones();
        acciones.setCodigo(0);
        acciones.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from acciones "
                    + "where acciones.codigo = ? "
                    + "order by acciones.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    acciones.setCodigo(rs.getInt("codigo"));
                    acciones.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return acciones;
        
    }
   

}

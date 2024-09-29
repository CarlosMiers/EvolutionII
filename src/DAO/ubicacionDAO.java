/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.ubicacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ubicacionDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<ubicacion> todos() throws SQLException {
        ArrayList<ubicacion> lista = new ArrayList<ubicacion>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM ubicaciones "
                + " ORDER BY ubicaciones.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ubicacion pa = new ubicacion();
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

    
    public ubicacion buscarId(int id) throws SQLException {
        ubicacion ubicacion = new  ubicacion();
        ubicacion.setCodigo(0);
        ubicacion.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from ubicaciones "
                    + "where ubicaciones.codigo = ? "
                    + "order by ubicaciones.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ubicacion.setCodigo(rs.getInt("codigo"));
                    ubicacion.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ubicacion;
    }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.medida;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class medidaDAO {

    Conexion con = null;
    Statement st = null;
    
      public ArrayList<medida> todos() throws SQLException {
        ArrayList<medida> lista = new ArrayList<medida>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM unidades "
                + " ORDER BY unidades.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                medida pa = new medida();
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

    
    public medida buscarId(int id) throws SQLException {
        medida medida = new  medida();
        medida.setCodigo(0);
        medida.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from unidades "
                    + "where unidades.codigo = ? "
                    + "order by unidades.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    medida.setCodigo(rs.getInt("codigo"));
                    medida.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return medida;
    }
   
}

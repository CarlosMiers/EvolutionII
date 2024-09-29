/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.tipo_documento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class tipo_documentoDAO {
  Conexion con = null;
    Statement st = null;
    
      public ArrayList<tipo_documento> todos() throws SQLException {
        ArrayList<tipo_documento> lista = new ArrayList<tipo_documento>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM tipo_documento "
                + " ORDER BY tipo_documento.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipo_documento pa = new tipo_documento();
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

    
    public tipo_documento buscarId(int id) throws SQLException {

        tipo_documento tipo_documento = new  tipo_documento();
        tipo_documento.setCodigo(0);
        tipo_documento.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from tipo_documento "
                    + "where tipo_documento.codigo = ? "
                    + "order by tipo_documento.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tipo_documento.setCodigo(rs.getInt("codigo"));
                    tipo_documento.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return tipo_documento;
    }    
}

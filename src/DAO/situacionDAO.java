/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.situacion;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pc_Server
 */
public class situacionDAO {
      Conexion con = null;
    Statement st = null;

    public ArrayList<situacion> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT situacion.codigo, situacion.nombre "
                + " FROM situacion ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                situacion secc = new situacion();
                secc.setCodigo(rs.getInt("codigo"));
                secc.setNombre(rs.getString("nombre"));
                lista.add(secc);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public situacion buscarId(int id) throws SQLException {

        situacion secc = new situacion();

        secc.setCodigo(0);
        secc.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT situacion.codigo, situacion.nombre "
                    + " FROM situacion "
                    + " where situacion.codigo = ? "
                    + "order by situacion.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    secc.setCodigo(rs.getInt("codigo"));
                    secc.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return secc;
    }

    public situacion insertarsituacion(situacion secc) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO situacion (codigo,nombre) VALUES (?,?)");
        ps.setInt(1, secc.getCodigo());
        ps.setString(2, secc.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return secc;
    }

    public boolean actualizarsituacion(situacion secc) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE situacion SET nombre=? WHERE codigo=" + secc.getCodigo());
        ps.setString(1, secc.getNombre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarsituacion(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM situacion WHERE codigo=?");
        ps.setInt(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }
  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.custodia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class custodiaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<custodia> todos() throws SQLException {
        ArrayList<custodia> lista = new ArrayList<custodia>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT codigo,nombre,ruc,direccion,telefono,"
                + "contacto,estado "
                + " FROM custodias "
                + " ORDER BY codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                custodia cu = new custodia();
                cu.setCodigo(rs.getInt("codigo"));
                cu.setNombre(rs.getString("nombre"));
                cu.setRuc(rs.getString("ruc"));
                cu.setDireccion(rs.getString("direccion"));
                cu.setTelefono(rs.getString("telefono"));
                cu.setContacto(rs.getString("contacto"));
                cu.setEstado(rs.getInt("estado"));
                lista.add(cu);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    public custodia buscarId(int id) throws SQLException {
        custodia cu = new custodia();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT codigo,nombre,ruc,direccion,telefono,"
                    + "contacto,estado "
                    + " FROM custodias "
                    + " WHERE codigo=? "
                    + " ORDER BY codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cu.setCodigo(rs.getInt("codigo"));
                    cu.setNombre(rs.getString("nombre"));
                    cu.setRuc(rs.getString("ruc"));
                    cu.setDireccion(rs.getString("direccion"));
                    cu.setTelefono(rs.getString("telefono"));
                    cu.setContacto(rs.getString("contacto"));
                    cu.setEstado(rs.getInt("estado"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cu;
    }

    public custodia insertarCustodia(custodia cu) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO custodias (nombre,ruc,direccion,telefono,contacto,estado) "
                + " VALUES (?,?,?,?,?,?)");
        ps.setString(1, cu.getNombre());
        ps.setString(2, cu.getRuc());
        ps.setString(3, cu.getDireccion());
        ps.setString(4, cu.getTelefono());
        ps.setString(5, cu.getContacto());
        ps.setInt(6, cu.getEstado());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        conn.close();
        return cu;
    }

    public boolean actualizarCustodia(custodia cu) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        Connection conn = st.getConnection();

        ps = st.getConnection().prepareStatement("UPDATE custodias "
                + " SET nombre=?,ruc=?,direccion=?,telefono=?,contacto=?,estado=?"
                + " WHERE codigo=" + cu.getCodigo());
        ps.setString(1, cu.getNombre());
        ps.setString(2, cu.getRuc());
        ps.setString(3, cu.getDireccion());
        ps.setString(4, cu.getTelefono());
        ps.setString(5, cu.getContacto());
        ps.setInt(6, cu.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCustodia(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM custodias WHERE codigo=?");
        ps.setInt(1, cod);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

}

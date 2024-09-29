/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.vendedor;
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
public class vendedorDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<vendedor> todos() throws SQLException {
        ArrayList<vendedor> lista = new ArrayList<vendedor>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT * "
                + " FROM vendedores "
                + " ORDER BY codigo ";
        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vendedor ve = new vendedor();
                ve.setCodigo(rs.getInt("codigo"));
                ve.setNombre(rs.getString("nombre"));
                ve.setComisioncontado(rs.getDouble("comisioncontado"));
                ve.setComisioncredito(rs.getDouble("comisioncredito"));
                lista.add(ve);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<vendedor> todosActivos() throws SQLException {
        ArrayList<vendedor> lista = new ArrayList<vendedor>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT * "
                + " FROM vendedores "
                + " WHERE estado=1 "
                + " ORDER BY codigo ";
        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vendedor ve = new vendedor();
                ve.setCodigo(rs.getInt("codigo"));
                ve.setNombre(rs.getString("nombre"));
                lista.add(ve);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public vendedor buscarId(int id) throws SQLException {

        vendedor vendedor = new vendedor();
        vendedor.setCodigo(0);
        vendedor.setNombre("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select codigo,nombre,comisioncontado,comisioncredito  "
                    + " from vendedores "
                    + "where codigo = ? "
                    + "order by vendedores.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    vendedor.setCodigo(rs.getInt("codigo"));
                    vendedor.setNombre(rs.getString("nombre"));
                    vendedor.setComisioncontado(rs.getDouble("comisioncontado"));
                    vendedor.setComisioncredito(rs.getDouble("comisioncredito"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return vendedor;
    }

    public vendedor insertarVendedor(vendedor ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO vendedores (nombre,localidad,direccion,ruc,telefono,fax,email,estado,bonificacion) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarVendedor(vendedor ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE vendedores SET nombre=?,localidad=?,direccion=?,ruc=?,telefono=?,fax=?,email=?,estado=?,bonificacion=? WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarVendedor(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM vendedores WHERE codigo=?");
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

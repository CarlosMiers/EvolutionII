/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.casa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class casaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<casa> todos() throws SQLException {
        ArrayList<casa> lista = new ArrayList<casa>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT casas.codigo,casas.nombre,localidad,localidades.nombre as nombrelocalidad,direccion,ruc,telefono,fax,email,timbrado,vencimiento,estado,bonificacion "
                + " FROM casas "
                + " INNER JOIN localidades   "
                + "ON localidades.codigo = casas.localidad "
                + " ORDER BY casas.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                casa ca = new casa();
                ca.setCodigo(rs.getInt("codigo"));
                ca.setNombre(rs.getString("nombre"));
                ca.setDireccion(rs.getString("direccion"));
                ca.setTelefono(rs.getString("telefono"));
                ca.setRuc(rs.getString("ruc"));
                ca.setEstado(rs.getInt("estado"));
                ca.setFax(rs.getString("fax"));
                ca.setVencimiento(rs.getDate("vencimiento"));
                ca.setTimbrado(rs.getString("timbrado"));
                ca.setNombrelocalidad(rs.getString("nombrelocalidad"));
                ca.setBonificacion(rs.getDouble("bonificacion"));
                lista.add(ca);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public casa buscarId(int id) throws SQLException {

        casa casa = new casa();
        casa.setCodigo(0);
        casa.setNombre("");
        casa.setRuc("");
        casa.setDireccion("");
        casa.setTelefono("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select casas.codigo,casas.nombre,localidad,direccion,ruc,telefono,fax,email,timbrado,vencimiento,estado,bonificacion,idcta "
                    + " from casas "
                    + "where casas.codigo = ? "
                    + "order by casas.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    casa.setCodigo(rs.getInt("codigo"));
                    casa.setNombre(rs.getString("nombre"));
                    casa.setDireccion(rs.getString("direccion"));
                    casa.setTelefono(rs.getString("telefono"));
                    casa.setBonificacion(rs.getInt("bonificacion"));
                    casa.setFax(rs.getString("fax"));
                    casa.setEmail(rs.getString("email"));
                    casa.setEstado(rs.getInt("estado"));
                    casa.setIdcta(rs.getString("idcta"));
                    casa.setRuc(rs.getString("ruc"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return casa;
    }

    public casa insertarCasas(casa ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO casas (nombre,localidad,direccion,ruc,telefono,fax,email,estado,bonificacion) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getLocalidad());
        ps.setString(3, ca.getDireccion());
        ps.setString(4, ca.getRuc());
        ps.setString(5, ca.getTelefono());
        ps.setString(6, ca.getFax());
        ps.setString(7, ca.getEmail());
        ps.setInt(8, ca.getEstado());
        ps.setDouble(9, ca.getBonificacion());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarCasas(casa ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE casas SET nombre=?,localidad=?,direccion=?,ruc=?,telefono=?,fax=?,email=?,estado=?,bonificacion=? WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setInt(2, ca.getLocalidad());
        ps.setString(3, ca.getDireccion());
        ps.setString(4, ca.getRuc());
        ps.setString(5, ca.getTelefono());
        ps.setString(6, ca.getFax());
        ps.setString(7, ca.getEmail());
        ps.setInt(8, ca.getEstado());
        ps.setDouble(9, ca.getBonificacion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCasas(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM casas WHERE codigo=?");
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

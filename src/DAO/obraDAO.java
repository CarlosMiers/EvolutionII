/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.localidad;
import Modelo.obra;
import Modelo.cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class obraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<obra> todos() throws SQLException {
        ArrayList<obra> lista = new ArrayList<obra>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT obras.codigo,obras.nombre,obras.localidad,localidades.nombre as nombrelocalidad,"
                + "direccionobra,telefonocontacto,obras.fax,obras.contacto,obras.estado,obras.propietario,"
                + "clientes.nombre as nombrecliente "
                + " FROM obras "
                + " LEFT JOIN localidades   "
                + "ON localidades.codigo = obras.localidad "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=obras.propietario  "
                + " ORDER BY obras.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                obra ca = new obra();
                localidad loc = new localidad();
                cliente cl = new cliente();
                ca.setPropietario(cl);
                ca.setLocalidad(loc);
                ca.setCodigo(rs.getInt("codigo"));
                ca.setNombre(rs.getString("nombre"));
                ca.setDireccion(rs.getString("direccionobra"));
                ca.setEstado(rs.getInt("estado"));
                ca.setFax(rs.getString("fax"));
                ca.setTelefonocontacto(rs.getString("telefonocontacto"));
                ca.setContacto(rs.getString("contacto"));
                ca.getLocalidad().setCodigo(rs.getInt("localidad"));
                ca.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                ca.getPropietario().setCodigo(rs.getInt("propietario"));
                ca.getPropietario().setNombre(rs.getString("nombrecliente"));
                lista.add(ca);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<obra> todosxCliente(int cod) throws SQLException {
        ArrayList<obra> lista = new ArrayList<obra>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT obras.codigo,obras.nombre,obras.localidad,localidades.nombre as nombrelocalidad,"
                + "direccionobra,telefonocontacto,obras.fax,obras.contacto,obras.estado,obras.propietario,"
                + "clientes.nombre as nombrecliente "
                + " FROM obras "
                + " LEFT JOIN localidades   "
                + "ON localidades.codigo = obras.localidad "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=obras.propietario  "
                + " WHERE obras.propietario=?"
                + " ORDER BY obras.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, cod);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                obra ca = new obra();
                localidad loc = new localidad();
                cliente cl = new cliente();
                ca.setPropietario(cl);
                ca.setLocalidad(loc);
                ca.setCodigo(rs.getInt("codigo"));
                ca.setNombre(rs.getString("nombre"));
                ca.setDireccion(rs.getString("direccionobra"));
                ca.setEstado(rs.getInt("estado"));
                ca.setFax(rs.getString("fax"));
                ca.setTelefonocontacto(rs.getString("telefonocontacto"));
                ca.setContacto(rs.getString("contacto"));
                ca.getLocalidad().setCodigo(rs.getInt("localidad"));
                ca.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                ca.getPropietario().setCodigo(rs.getInt("propietario"));
                ca.getPropietario().setNombre(rs.getString("nombrecliente"));
                lista.add(ca);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<obra> todosxClienteActivo(int cod) throws SQLException {
        ArrayList<obra> lista = new ArrayList<obra>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT obras.codigo,obras.nombre,obras.localidad,localidades.nombre as nombrelocalidad,"
                + "direccionobra,telefonocontacto,obras.fax,obras.contacto,obras.estado,obras.propietario,"
                + "clientes.nombre as nombrecliente "
                + " FROM obras "
                + " LEFT JOIN localidades   "
                + "ON localidades.codigo = obras.localidad "
                + "LEFT JOIN clientes "
                + "ON clientes.codigo=obras.propietario  "
                + " WHERE obras.propietario=? and obras.estado=1 "
                + " ORDER BY obras.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, cod);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                obra ca = new obra();
                localidad loc = new localidad();
                cliente cl = new cliente();
                ca.setPropietario(cl);
                ca.setLocalidad(loc);
                ca.setCodigo(rs.getInt("codigo"));
                ca.setNombre(rs.getString("nombre"));
                ca.setDireccion(rs.getString("direccionobra"));
                ca.setEstado(rs.getInt("estado"));
                ca.setFax(rs.getString("fax"));
                ca.setTelefonocontacto(rs.getString("telefonocontacto"));
                ca.setContacto(rs.getString("contacto"));
                ca.getLocalidad().setCodigo(rs.getInt("localidad"));
                ca.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                ca.getPropietario().setCodigo(rs.getInt("propietario"));
                ca.getPropietario().setNombre(rs.getString("nombrecliente"));
                lista.add(ca);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public obra buscarId(int id) throws SQLException {

        obra ca = new obra();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT obras.codigo,obras.nombre,obras.localidad,localidades.nombre as nombrelocalidad,"
                    + "direccionobra,telefonocontacto,obras.fax,obras.contacto,obras.estado,obras.propietario,"
                    + "clientes.nombre as nombrecliente "
                    + " FROM obras "
                    + " LEFT JOIN localidades   "
                    + "ON localidades.codigo = obras.localidad "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=obras.propietario  "
                    + " WHERE obras.codigo=? "
                    + " ORDER BY obras.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    localidad loc = new localidad();
                    cliente cl = new cliente();
                    ca.setPropietario(cl);
                    ca.setLocalidad(loc);
                    ca.setCodigo(rs.getInt("codigo"));
                    ca.setNombre(rs.getString("nombre"));
                    ca.setDireccion(rs.getString("direccionobra"));
                    ca.setEstado(rs.getInt("estado"));
                    ca.setFax(rs.getString("fax"));
                    ca.setTelefonocontacto(rs.getString("telefonocontacto"));
                    ca.setContacto(rs.getString("contacto"));
                    ca.getLocalidad().setCodigo(rs.getInt("localidad"));
                    ca.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    ca.getPropietario().setCodigo(rs.getInt("propietario"));
                    ca.getPropietario().setNombre(rs.getString("nombrecliente"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return ca;
    }

    public obra insertarObra(obra ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO obras (nombre,contacto,direccionobra,localidad,"
                + "telefonocontacto,fax,estado,propietario ) VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getContacto());
        ps.setString(3, ca.getDireccion());
        ps.setInt(4, ca.getLocalidad().getCodigo());
        ps.setString(5, ca.getTelefonocontacto());
        ps.setString(6, ca.getFax());
        ps.setInt(7, ca.getEstado());
        ps.setInt(8, ca.getPropietario().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarObra(obra ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE obras SET "
                + " nombre=?,contacto=?,direccionobra=?,localidad=?,"
                + "telefonocontacto=?,fax=?,estado=?,propietario=? WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getContacto());
        ps.setString(3, ca.getDireccion());
        ps.setInt(4, ca.getLocalidad().getCodigo());
        ps.setString(5, ca.getTelefonocontacto());
        ps.setString(6, ca.getFax());
        ps.setInt(7, ca.getEstado());
        ps.setInt(8, ca.getPropietario().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarObra(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM obras WHERE codigo=?");
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

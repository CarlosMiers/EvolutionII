/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clases.Config;
import Conexion.Conexion;
import Modelo.localidad;
import Modelo.plan;
import Modelo.proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Webmaster
 */
public class proveedorDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<proveedor> todos() throws SQLException {
        ArrayList proveedores = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT codigo,nombre,direccion,telefono,ruc,timbrado,vencimiento "
                + "FROM proveedores "
                + "ORDER BY codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                proveedor proveedor = new proveedor();
                proveedor.setCodigo(rs.getInt("codigo"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setRuc(rs.getString("ruc"));
                proveedor.setVencimiento(rs.getDate("vencimiento"));
                proveedor.setTimbrado(rs.getString("timbrado"));
                proveedores.add(proveedor);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return proveedores;
    }

    public proveedor buscarId(int id) throws SQLException {

        proveedor proveedor = new proveedor();
        proveedor.setCodigo(0);
        proveedor.setNombre("");
        proveedor.setRuc("");
        proveedor.setDireccion("");
        proveedor.setTelefono("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT proveedores.codigo,proveedores.nombre,proveedores.localidad,proveedores.direccion,proveedores.ruc,"
                    + "proveedores.telefono,proveedores.fax,proveedores.email,proveedores.web,proveedores.timbrado,proveedores.vencimiento,"
                    + "proveedores.estado,proveedores.idcta,proveedores.contacto,proveedores.plazo,"
                    + "localidades.nombre AS nombrelocalidad,plan.nombre AS nombrecuenta "
                    + "FROM proveedores "
                    + "LEFT JOIN localidades "
                    + "ON localidades.codigo=proveedores.localidad "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=proveedores.idcta "
                    + " WHERE proveedores.codigo=? "
                    + "ORDER BY proveedores.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    plan p = new plan();
                    localidad l = new localidad();
                    proveedor.setIdcta(p);
                    proveedor.setLocalidad(l);
                    proveedor.setCodigo(rs.getInt("codigo"));
                    proveedor.setNombre(rs.getString("nombre"));
                    proveedor.setDireccion(rs.getString("direccion"));
                    proveedor.setTelefono(rs.getString("telefono"));
                    proveedor.setRuc(rs.getString("ruc"));
                    proveedor.setVencimiento(rs.getDate("vencimiento"));
                    proveedor.setTimbrado(rs.getString("timbrado"));
                    proveedor.getIdcta().setCodigo(rs.getString("idcta"));
                    proveedor.getIdcta().setNombre(rs.getString("nombrecuenta"));
                    proveedor.getLocalidad().setCodigo(rs.getInt("localidad"));
                    proveedor.getLocalidad().setNombre(rs.getString("nombrelocalidad"));
                    
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return proveedor;
    }

    public boolean eliminarProveedor(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM proveedores WHERE codigo=?");
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

    public boolean EnlaceContableProveedor(proveedor p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE proveedores SET idcta=? WHERE codigo=" + p.getCodigo());
        ps.setString(1, p.getIdcta().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

        public proveedor buscarProvServer(int id) throws SQLException {

        proveedor proveedor = new proveedor();
        proveedor.setCodigo(0);
        proveedor.setNombre("");
        proveedor.setRuc("");
        proveedor.setDireccion("");
        proveedor.setTelefono("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        
        try {

            String sql = "SELECT proveedores.codigo "
                    +" FROM proveedores "
                    + " WHERE proveedores.codigo=? "
                    + "ORDER BY proveedores.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    proveedor.setCodigo(rs.getInt("codigo"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return proveedor;
    }

    public proveedor insertarProveedor(proveedor p) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO proveedores (codigo,nombre,localidad,direccion,ruc,telefono,vencimiento,timbrado,estado) VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, p.getCodigo());
        ps.setString(2, p.getNombre());
        ps.setInt(3, p.getLocalidad().getCodigo());
        ps.setString(4, p.getDireccion());
        ps.setString(5, p.getRuc());
        ps.setString(6, p.getTelefono());
        ps.setDate(7, p.getVencimiento());
        ps.setString(8, p.getTimbrado());
        ps.setInt(9, p.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return p;
    }
    
    
    
}

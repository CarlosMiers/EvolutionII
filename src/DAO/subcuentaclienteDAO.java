/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import java.sql.Connection;
import Modelo.subcuentacliente;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class subcuentaclienteDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<subcuentacliente> MostrarxCliente(int ncliente) throws SQLException {
        ArrayList<subcuentacliente> lista = new ArrayList<subcuentacliente>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT idcuenta,nombre,cedula,ruc,fechanacimiento,"
                    + "direccion,telefono,celular,correo "
                    + " FROM subcuentaclientes "
                    + " WHERE idcliente=?"
                    + " ORDER BY idcuenta ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, ncliente);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    subcuentacliente cer = new subcuentacliente();
                    cer.setIdcuenta(rs.getInt("idcuenta"));
                    cer.setNombre(rs.getString("nombre"));
                    cer.setCedula(rs.getString("cedula"));
                    cer.setRuc(rs.getString("ruc"));
                    cer.setFechanacimiento(rs.getDate("fechanacimiento"));
                    cer.setDireccion(rs.getString("direccion"));
                    cer.setTelefono(rs.getString("telefono"));
                    cer.setCelular(rs.getString("celular"));
                    cer.setCorreo(rs.getString("correo"));
                    lista.add(cer);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

    
public subcuentacliente buscarId(Double id) throws SQLException {
        subcuentacliente cer = new subcuentacliente();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT idcuenta,nombre,cedula,ruc,fechanacimiento,"
                    + "direccion,telefono,celular,correo "
                    + "FROM subcuentaclientes "
                    + " WHERE idcuenta=?"
                    + " ORDER BY idcuenta ";


            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cer.setIdcuenta(rs.getInt("idcuenta"));
                    cer.setNombre(rs.getString("nombre"));
                    cer.setCedula(rs.getString("cedula"));
                    cer.setRuc(rs.getString("ruc"));
                    cer.setFechanacimiento(rs.getDate("fechanacimiento"));
                    cer.setDireccion(rs.getString("direccion"));
                    cer.setTelefono(rs.getString("telefono"));
                    cer.setCelular(rs.getString("celular"));
                    cer.setCorreo(rs.getString("correo"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return cer;
    }
    
    
    
    
    public subcuentacliente InsertarSubCuenta(subcuentacliente ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO subcuentaclientes "
                + "(nombre,cedula,ruc,fechanacimiento,"
                + "direccion,telefono,celular,correo,idcliente) "
                + "VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getCedula());
        ps.setString(3, ca.getRuc());
        ps.setDate(4, ca.getFechanacimiento());
        ps.setString(5, ca.getDireccion());
        ps.setString(6, ca.getTelefono());
        ps.setString(7, ca.getCelular());
        ps.setString(8, ca.getCorreo());
        ps.setInt(9, ca.getIdcliente());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarSubCuenta(subcuentacliente ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE subcuentaclientes "
                + "SET nombre=?,cedula=?,ruc=?,fechanacimiento=?,"
                + "direccion=?,telefono=?,celular=?,correo=?"
                + " WHERE idcuenta=" + ca.getIdcuenta());
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getCedula());
        ps.setString(3, ca.getRuc());
        ps.setDate(4, ca.getFechanacimiento());
        ps.setString(5, ca.getDireccion());
        ps.setString(6, ca.getTelefono());
        ps.setString(7, ca.getCelular());
        ps.setString(8, ca.getCorreo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarSubcuenta(Double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM subcuentaclientes WHERE idcuenta=?");
        ps.setDouble(1, cod);
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

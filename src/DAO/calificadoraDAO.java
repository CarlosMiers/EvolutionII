/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.calificadora;
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
public class calificadoraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<calificadora> todos() throws SQLException {
        ArrayList<calificadora> lista = new ArrayList<calificadora>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT calificadoras.codigo,calificadoras.nombre,ruc,direccion,nacimiento,"
                + "web,telefono,mail,estado "
                + "FROM calificadoras "
                + " ORDER BY calificadoras.codigo ";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                calificadora calif = new calificadora();
                calif.setCodigo(rs.getInt("codigo"));
                calif.setNombre(rs.getString("nombre"));
                calif.setRuc(rs.getString("ruc"));
                calif.setDireccion(rs.getString("direccion"));
                calif.setNacimiento(rs.getDate("nacimiento"));
                calif.setWeb(rs.getString("web"));
                calif.setTelefono(rs.getString("telefono"));
                calif.setMail(rs.getString("mail"));
                calif.setEstado(rs.getInt("estado"));
                lista.add(calif);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<calificadora> todosActivos() throws SQLException {
        ArrayList<calificadora> lista = new ArrayList<calificadora>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT calificadoras.codigo,calificadoras.nombre,ruc,direccion,nacimiento,"
                + "web,telefono,mail,estado "
                + "FROM calificadoras "
                + " WHERE calificadoras.estado=1 "
                + " ORDER BY calificadoras.codigo ";
        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                calificadora calif = new calificadora();
                calif.setCodigo(rs.getInt("codigo"));
                calif.setNombre(rs.getString("nombre"));
                calif.setRuc(rs.getString("ruc"));
                calif.setDireccion(rs.getString("direccion"));
                calif.setNacimiento(rs.getDate("nacimiento"));
                calif.setWeb(rs.getString("web"));
                calif.setTelefono(rs.getString("telefono"));
                calif.setMail(rs.getString("mail"));
                calif.setEstado(rs.getInt("estado"));
                lista.add(calif);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public calificadora buscarId(int id) throws SQLException {
        calificadora calif = new calificadora();
        calif.setCodigo(0);
        calif.setNombre("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
        String sql = "SELECT calificadoras.codigo,calificadoras.nombre,ruc,direccion,nacimiento,"
                + "web,telefono,mail,estado "
                + "FROM calificadoras "
                +" WHERE calificadoras.codigo=?"
                + " ORDER BY calificadoras.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    calif.setCodigo(rs.getInt("codigo"));
                    calif.setNombre(rs.getString("nombre"));
                    calif.setRuc(rs.getString("ruc"));
                    calif.setDireccion(rs.getString("direccion"));
                    calif.setNacimiento(rs.getDate("nacimiento"));
                    calif.setWeb(rs.getString("web"));
                    calif.setTelefono(rs.getString("telefono"));
                    calif.setMail(rs.getString("mail"));
                    calif.setEstado(rs.getInt("estado"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return calif;
    }

    public calificadora insertarCalificadora(calificadora ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO calificadoras "
                + "(nombre,ruc,direccion,"
                + "nacimiento,telefono,mail,web,estado)"
                + "VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getRuc());
        ps.setString(3, ca.getDireccion());
        ps.setDate(4, ca.getNacimiento());
        ps.setString(5, ca.getTelefono());
        ps.setString(6, ca.getMail());
        ps.setString(7, ca.getWeb());
        ps.setInt(8, ca.getEstado());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarCalificadora(calificadora ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE calificadoras "
                + "SET nombre=?,ruc=?,direccion=?,"
                + "nacimiento=?,telefono=?,mail=?,web=?,estado=?"
                + " WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getRuc());
        ps.setString(3, ca.getDireccion());
        ps.setDate(4, ca.getNacimiento());
        ps.setString(5, ca.getTelefono());
        ps.setString(6, ca.getMail());
        ps.setString(7, ca.getWeb());
        ps.setInt(8, ca.getEstado());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarCalificadora(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM calificadoras WHERE codigo=?");
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

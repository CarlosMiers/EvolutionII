/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import java.sql.Connection;
import Modelo.cumplimiento_requisitos;
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
public class cumplimiento_requisitosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cumplimiento_requisitos> MostrarxTipo(int ntipo) throws SQLException {
        ArrayList<cumplimiento_requisitos> lista = new ArrayList<cumplimiento_requisitos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT id,tipo,descripcion"
                    + " FROM cumplimiento_requisitos "
                    + " WHERE tipo=? "
                    + " ORDER BY id ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, ntipo);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    cumplimiento_requisitos cer = new cumplimiento_requisitos();
                    cer.setId(rs.getInt("id"));
                    cer.setDescripcion(rs.getString("descripcion"));
                    cer.setTipo(rs.getInt("tipo"));
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

    
public cumplimiento_requisitos buscarId(Double id) throws SQLException {
        cumplimiento_requisitos cer = new cumplimiento_requisitos();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT id,tipo,descripcion"
                    + "direccion,telefono,celular,correo "
                    + "FROM cumplimiento_requisitos "
                    + " WHERE id=?"
                    + " ORDER BY id ";


            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cer.setId(rs.getInt("id"));
                    cer.setDescripcion(rs.getString("descripcion"));
                    cer.setTipo(rs.getInt("tipo"));
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
    
    
    public cumplimiento_requisitos Insertar(cumplimiento_requisitos ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cumplimiento_requisitos "
                + "(tipo,descripcion)"
                + "VALUES (?,?)");
        ps.setInt(1, ca.getTipo());
        ps.setString(2, ca.getDescripcion());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizar(cumplimiento_requisitos ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("UPDATE cumplimiento_requisitos "
                + "SET descripcion?,tipo=? WHERE id=" + ca.getId());
        ps.setInt(1, ca.getTipo());
        ps.setString(2, ca.getDescripcion());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminar(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cumplimiento_requisitos WHERE id=?");
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

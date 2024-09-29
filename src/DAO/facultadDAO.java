/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.facultad;
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
public class facultadDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<facultad> todos() throws SQLException {
        ArrayList<facultad> lista = new ArrayList<facultad>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT facultad.codigo,facultad.nombre,rector,decano,secretario_general "
                + "FROM facultad "
                + " ORDER BY facultad.codigo ";

        System.out.println(sql);
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                facultad facu = new facultad();
                facu.setCodigo(rs.getInt("codigo"));
                facu.setNombre(rs.getString("nombre"));
                facu.setDecano(rs.getString("decano"));
                facu.setRector(rs.getString("rector"));
                facu.setSecretario_general(rs.getString("secretario_general"));
                lista.add(facu);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public facultad buscarId(int id) throws SQLException {
        facultad facu = new facultad();
        facu.setCodigo(0);
        facu.setNombre("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "SELECT facultad.codigo,facultad.nombre,rector,decano,secretario_general "
                    + "FROM facultad "
                    + " WHERE facultad.codigo=? "
                    + " ORDER BY facultad.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    facu.setCodigo(rs.getInt("codigo"));
                    facu.setNombre(rs.getString("nombre"));
                    facu.setDecano(rs.getString("decano"));
                    facu.setRector(rs.getString("rector"));
                    facu.setSecretario_general(rs.getString("secretario_general"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return facu;
    }

    public facultad insertarFacultad(facultad ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO facultad "
                + "(nombre,rector,decano,secretario_general)"
                + " VALUES (?,?,?,?)");
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getRector());
        ps.setString(3, ca.getDecano());
        ps.setString(4, ca.getSecretario_general());
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        ps.close();
        return ca;
    }

    public boolean actualizarFacultad(facultad ca) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE facultad "
                + "SET nombre=?,rector=?,decano=?,secretario_general=? "
                + " WHERE codigo=" + ca.getCodigo());
        ps.setString(1, ca.getNombre());
        ps.setString(2, ca.getRector());
        ps.setString(3, ca.getDecano());
        ps.setString(4, ca.getSecretario_general());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarFacultad(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM facultad WHERE codigo=?");
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

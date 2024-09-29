/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.motivo_ausencia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class motivo_ausenciaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<motivo_ausencia> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT motivo_ausencias.codigo, motivo_ausencias.nombre "
                + " FROM motivo_ausencias ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                motivo_ausencia mot = new motivo_ausencia();

                mot.setCodigo(rs.getInt("codigo"));
                mot.setNombre(rs.getString("nombre"));
                lista.add(mot);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public motivo_ausencia buscarId(int id) throws SQLException {

        motivo_ausencia mot = new motivo_ausencia();

        mot.setCodigo(0);
        mot.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT motivo_ausencias.codigo, motivo_ausencias.nombre "
                    + " FROM motivo_ausencias "
                    + " where motivo_ausencias.codigo = ? "
                    + "order by motivo_ausencias.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    mot.setCodigo(rs.getInt("codigo"));
                    mot.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return mot;
    }

    public motivo_ausencia insertarausencia(motivo_ausencia mot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO motivo_ausencias (codigo,nombre) VALUES (?,?)");
        ps.setInt(1, mot.getCodigo());
        ps.setString(2, mot.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return mot;
    }

    public boolean actualizarausencia(motivo_ausencia mot) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE motivo_ausencias SET nombre=? WHERE codigo=" + mot.getCodigo());
        ps.setString(1, mot.getNombre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarausencia(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM motivo_ausencias WHERE codigo=?");
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

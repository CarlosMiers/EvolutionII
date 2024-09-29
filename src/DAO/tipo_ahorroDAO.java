/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.tipo_ahorro;
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
public class tipo_ahorroDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<tipo_ahorro> todos() throws SQLException {
        ArrayList<tipo_ahorro> lista = new ArrayList<tipo_ahorro>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        String sql = "SELECT codigo,nombre,"
                + "interesanual,pagointeres "
                + " FROM tipo_ahorros "
                + "ORDER BY codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tipo_ahorro caj = new tipo_ahorro();
                caj.setCodigo(rs.getInt("codigo"));
                caj.setNombre(rs.getString("nombre"));
                caj.setInteresanual(rs.getDouble("interesanual"));
                caj.setPagointeres(rs.getString("pagointeres"));
                lista.add(caj);
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

    public tipo_ahorro buscarId(int id) throws SQLException {
        tipo_ahorro caj = new tipo_ahorro();
        caj.setCodigo(0);
        caj.setNombre("");
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select * "
                    + "from tipo_ahorros "
                    + "where tipo_ahorros.codigo = ? "
                    + "order by tipo_ahorros.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    caj.setCodigo(rs.getInt("codigo"));
                    caj.setNombre(rs.getString("nombre"));
                    caj.setInteresanual(rs.getDouble("interesanual"));
                    caj.setPagointeres(rs.getString("pagointeres"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return caj;
    }

    public tipo_ahorro insertarTipoAhorro(tipo_ahorro g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO tipo_ahorros "
                + "(nombre,interesanual,pagointeres) VALUES (?,?,?)");
        ps.setString(1, g.getNombre());
        ps.setDouble(2, g.getInteresanual());
        ps.setString(3, g.getPagointeres());
        ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        return g;
    }

    public boolean actualizarTipoAhorro(tipo_ahorro g) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE tipo_ahorros SET nombre=?,"
                + "interesanual=?,pagointeres=? WHERE codigo=" + g.getCodigo());
        ps.setString(1, g.getNombre());
        ps.setDouble(2, g.getInteresanual());
        ps.setString(3, g.getPagointeres());
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

    public boolean eliminarTipoAhorro(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM tipo_ahorros WHERE codigo=?");
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

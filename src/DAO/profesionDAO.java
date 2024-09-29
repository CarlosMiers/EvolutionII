/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.profesion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class profesionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<profesion> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT profesiones.codigo, profesiones.nombre "
                + " FROM profesiones ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                profesion pro = new profesion();

                pro.setCodigo(rs.getInt("codigo"));
                pro.setNombre(rs.getString("nombre"));
                lista.add(pro);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public profesion buscarId(int id) throws SQLException {

        profesion pro = new profesion();

        pro.setCodigo(0);
        pro.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT profesiones.codigo, profesiones.nombre "
                    + " FROM profesiones "
                    + " where profesiones.codigo = ? "
                    + "order by profesiones.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    pro.setCodigo(rs.getInt("codigo"));
                    pro.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return pro;
    }

    public profesion insertarprofesion(profesion pro) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO profesiones (codigo,nombre) VALUES (?,?)");
        ps.setInt(1, pro.getCodigo());
        ps.setString(2, pro.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return pro;
    }

    public boolean actualizarprofesion(profesion pro) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE profesiones SET nombre=? WHERE codigo=" + pro.getCodigo());
        ps.setString(1, pro.getNombre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarprofesion(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM profesiones WHERE codigo=?");
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

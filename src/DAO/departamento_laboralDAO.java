/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.departamento_laboral;
import Modelo.profesion;
import Vista.departamentos_laborales;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class departamento_laboralDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<departamento_laboral> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT departamento_laboral.codigo, departamento_laboral.nombre "
                + " FROM departamento_laboral ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                departamento_laboral dlab = new departamento_laboral();

                dlab.setCodigo(rs.getInt("codigo"));
                dlab.setNombre(rs.getString("nombre"));
                lista.add(dlab);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public departamento_laboral buscarId(int id) throws SQLException {

        departamento_laboral dlab = new departamento_laboral();

        dlab.setCodigo(0);
        dlab.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT departamento_laboral.codigo, departamento_laboral.nombre "
                    + " FROM departamento_laboral "
                    + " where departamento_laboral.codigo = ? "
                    + "order by departamento_laboral.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    dlab.setCodigo(rs.getInt("codigo"));
                    dlab.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return dlab;
    }

    public departamento_laboral insertardepartamento(departamento_laboral dlab) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO departamento_laboral (codigo,nombre) VALUES (?,?)");
        ps.setInt(1, dlab.getCodigo());
        ps.setString(2, dlab.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return dlab;
    }

    public boolean actualizardepartamento(departamento_laboral dlab) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE departamento_laboral SET nombre=? WHERE codigo=" + dlab.getCodigo());
        ps.setString(1, dlab.getNombre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminardepartamento(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM departamento_laboral WHERE codigo=?");
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

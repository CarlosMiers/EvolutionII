/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.centro_costo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class centro_costoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<centro_costo> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT centro_costo.codigo, centro_costo.nombre "
                + " FROM centro_costo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                centro_costo car = new centro_costo();

                car.setCodigo(rs.getInt("codigo"));
                car.setNombre(rs.getString("nombre"));
                lista.add(car);
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public centro_costo buscarId(int id) throws SQLException {

        centro_costo car = new centro_costo();

        car.setCodigo(0);
        car.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT centro_costo.codigo, centro_costo.nombre "
                    + " FROM centro_costo "
                    + " where centro_costo.codigo = ? "
                    + "order by centro_costo.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    car.setCodigo(rs.getInt("codigo"));
                    car.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return car;
    }

    public centro_costo insertarcentro(centro_costo car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO centro_costo (codigo,nombre) VALUES (?,?)");
        ps.setInt(1, car.getCodigo());
        ps.setString(2, car.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return car;
    }

    public boolean actualizarcentro(centro_costo car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE centro_costo SET nombre=? WHERE codigo=" + car.getCodigo());
        ps.setString(1, car.getNombre());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarcentro(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM centro_costo WHERE codigo=?");
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

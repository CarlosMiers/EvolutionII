/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cargo;
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
public class cargoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<cargo> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT cargos.codigo, cargos.nombre "
                + " FROM cargos ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cargo car = new cargo();

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

    public cargo buscarId(int id) throws SQLException {

        cargo car = new cargo();

        car.setCodigo(0);
        car.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT cargos.codigo, cargos.nombre "
                    + " FROM cargos "
                    + " where cargos.codigo = ? "
                    + "order by cargos.codigo ";
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

    public cargo insertarcargo(cargo car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO cargos (codigo,nombre) VALUES (?,?)");
        ps.setInt(1, car.getCodigo());
        ps.setString(2, car.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return car;
    }

    public boolean actualizarcargo(cargo car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE cargos SET nombre=? WHERE codigo=" + car.getCodigo());
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

    public boolean eliminarcargo(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM cargos WHERE codigo=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.categoria_cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class categoria_clienteDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<categoria_cliente> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT categoria_clientes.codigo, categoria_clientes.nombre "
                + " FROM categoria_clientes ORDER BY codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoria_cliente car = new categoria_cliente();

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

    public categoria_cliente buscarId(int id) throws SQLException {

        categoria_cliente car = new categoria_cliente();

        car.setCodigo(0);
        car.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT categoria_clientes.codigo, categoria_clientes.nombre "
                    + " FROM categoria_clientes "
                    + " where categoria_clientes.codigo = ? "
                    + "order by categoria_clientes.codigo ";
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

    public categoria_cliente insertarcategoria_cliente(categoria_cliente car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO categoria_clientes (nombre) VALUES (?)");
        ps.setString(1, car.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return car;
    }

    public boolean actualizarcategoria_cliente(categoria_cliente car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE categoria_clientes SET nombre=? WHERE codigo=" + car.getCodigo());
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

    public boolean eliminarcategoria_cliente(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM categoria_clientes WHERE codigo=?");
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

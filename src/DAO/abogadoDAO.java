/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.abogado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class abogadoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<abogado> Todos() throws SQLException {
        ArrayList lista = new ArrayList();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT abogados.codigo, abogados.nombre "
                + " FROM abogados ORDER BY codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                abogado car = new abogado();

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

    public abogado buscarId(int id) throws SQLException {

        abogado car = new abogado();

        car.setCodigo(0);
        car.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT abogados.codigo, abogados.nombre "
                    + " FROM abogados "
                    + " where abogados.codigo = ? "
                    + "order by abogados.codigo ";
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

    public abogado insertarabogado(abogado car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO abogados (codigo,nombre) VALUES (?,?)");
        ps.setInt(1, car.getCodigo());
        ps.setString(2, car.getNombre());
        ps.executeUpdate();
        st.close();
        ps.close();
        return car;
    }

    public boolean actualizarabogado(abogado car) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE abogados SET nombre=? WHERE codigo=" + car.getCodigo());
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

    public boolean eliminarabogado(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM abogados WHERE codigo=?");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.moneda;
import Modelo.plan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class planDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<plan> todos() throws SQLException {
        ArrayList<plan> lista = new ArrayList<plan>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT plan.codigo,plan.nombre,plan.naturaleza,plan.nivel,plan.asentable,plan.moneda "
                + " FROM plan "
                + " ORDER BY plan.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                moneda mn = new moneda();
                plan p = new plan();
                p.setMoneda(mn);

                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setNaturaleza(rs.getString("naturaleza"));
                p.setNivel(rs.getInt("nivel"));
                p.setAsentable(rs.getInt("asentable"));
                p.getMoneda().setCodigo(rs.getInt("moneda"));
                lista.add(p);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("-->Todas  " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<plan> TodoAsentables() throws SQLException {
        ArrayList<plan> lista = new ArrayList<plan>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT plan.codigo,plan.nombre,plan.naturaleza,"
                + "plan.nivel,plan.asentable,plan.moneda "
                + " FROM plan "
                + " WHERE asentable=1 "
                + " ORDER BY plan.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                moneda mn = new moneda();

                plan p = new plan();

                p.setMoneda(mn);
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setNaturaleza(rs.getString("naturaleza"));
                p.setNivel(rs.getInt("nivel"));
                p.setAsentable(rs.getInt("asentable"));
                p.getMoneda().setCodigo(rs.getInt("moneda"));
                lista.add(p);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("-->Asentables " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public plan buscarId(String id) throws SQLException {
        plan plan = new plan();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT plan.codigo,plan.nombre,plan.naturaleza,plan.nivel,"
                    + "plan.asentable,plan.moneda, monedas.nombre AS nombremoneda,plan.tipo_cuenta "
                    + " FROM plan "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=plan.moneda "
                    + " where plan.codigo = ? and asentable=1 "
                    + "order by plan.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    moneda mn = new moneda();
                    plan.setMoneda(mn);
                    plan.setCodigo(rs.getString("codigo"));
                    plan.setNombre(rs.getString("nombre"));
                    plan.setNaturaleza(rs.getString("naturaleza"));
                    plan.setNivel(rs.getInt("nivel"));
                    plan.setTipo_cuenta(rs.getInt("tipo_cuenta"));
                    plan.setAsentable(rs.getInt("asentable"));
                    plan.getMoneda().setCodigo(rs.getInt("moneda"));
                    plan.getMoneda().setNombre(rs.getString("nombremoneda"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->Busqueda " + ex.getLocalizedMessage());
        }
        st.close();
        return plan;
    }

    public plan buscarIdTodos(String id) throws SQLException {
        plan plan = new plan();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT plan.codigo,plan.nombre,plan.naturaleza,"
                    + "plan.nivel,plan.asentable,plan.moneda,"
                    + " monedas.nombre AS nombremoneda,plan.tipo_cuenta "
                    + " FROM plan "
                    + " LEFT JOIN monedas "
                    + " ON monedas.codigo=plan.moneda "
                    + " where plan.codigo = ? "
                    + "order by plan.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    moneda mn = new moneda();
                    plan.setMoneda(mn);
                    plan.setCodigo(rs.getString("codigo"));
                    plan.setNombre(rs.getString("nombre"));
                    plan.setNaturaleza(rs.getString("naturaleza"));
                    plan.setNivel(rs.getInt("nivel"));
                    plan.setTipo_cuenta(rs.getInt("tipo_cuenta"));
                    plan.setAsentable(rs.getInt("asentable"));
                    plan.getMoneda().setCodigo(rs.getInt("moneda"));
                    plan.getMoneda().setNombre(rs.getString("nombremoneda"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("-->Busqueda " + ex.getLocalizedMessage());
        }
        st.close();
        return plan;
    }

    public plan insertarPlan(plan pl) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO plan (codigo,nombre,"
                + "naturaleza,nivel,asentable,moneda,tipo_cuenta) VALUES (?,?,?,?,?,?,?)");
        ps.setString(1, pl.getCodigo());
        ps.setString(2, pl.getNombre());
        ps.setString(3, pl.getNaturaleza());
        ps.setInt(4, pl.getNivel());
        ps.setInt(5, pl.getAsentable());
        ps.setInt(6, pl.getMoneda().getCodigo());
        ps.setInt(7, pl.getTipo_cuenta());
        ps.executeUpdate();
        st.close();
        ps.close();
        return pl;
    }

    public boolean actualizarPlan(plan pl) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE plan SET nombre=?,"
                + "naturaleza=?,nivel=?,asentable=?,moneda=?,tipo_cuenta=? WHERE codigo= " +pl.getCodigo());
       // ps.setString(1, pl.getCodigo());
        ps.setString(1, pl.getNombre());
        ps.setString(2, pl.getNaturaleza());
        ps.setInt(3, pl.getNivel());
        ps.setInt(4, pl.getAsentable());
        ps.setInt(5, pl.getMoneda().getCodigo());
        ps.setInt(6, pl.getTipo_cuenta());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarPlan(String cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM plan WHERE codigo=?");
        ps.setString(1, cod);
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

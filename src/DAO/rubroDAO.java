/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.plan;
import Modelo.rubro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class rubroDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<rubro> todos() throws SQLException {
        ArrayList<rubro> lista = new ArrayList<rubro>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT rubros.codigo,rubros.nombre,rubros.idcta,plan.nombre AS nombrecuenta "
                + " FROM rubros "
                + "LEFT JOIN plan "
                + "ON plan.codigo=rubros.idcta "
                + " ORDER BY rubros.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rubro r = new rubro();
                plan plan = new plan();
                r.setCodigo(rs.getInt("codigo"));
                r.setNombre(rs.getString("nombre"));
                lista.add(r);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public rubro buscarId(int id) throws SQLException {
        rubro rubro = new rubro();
        rubro.setCodigo(0);
        rubro.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select * "
                    + "from rubros "
                    + "where rubros.codigo = ? "
                    + "order by rubros.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rubro.setCodigo(rs.getInt("codigo"));
                    rubro.setNombre(rs.getString("nombre"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return rubro;
    }

    public boolean EnlaceContableCompra(rubro r) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE rubros SET idctamercaderia=? WHERE codigo=" + r.getCodigo());
        ps.setString(1, r.getIdctamercaderia());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EnlaceContableVenta(rubro r) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE rubros SET idcta=? WHERE codigo=" + r.getCodigo());
        ps.setString(1, r.getIdcta());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EnlaceContableCosto(rubro r) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE rubros SET idctacosto=? WHERE codigo=" + r.getCodigo());
        ps.setString(1, r.getIdctacosto());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public rubro buscarIdCtaVenta(int id) throws SQLException {
        rubro rubro = new rubro();
        rubro.setCodigo(0);
        rubro.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select rubros.codigo,rubros.nombre as nombrerubro, "
                    + "plan.nombre as nombrecuenta,rubros.idcta "
                    + "from rubros "
                    + "left join plan "
                    + "on plan.codigo=rubros.idcta "
                    + " where rubros.codigo = ? "
                    + "order by rubros.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rubro.setCodigo(rs.getInt("codigo"));
                    rubro.setNombre(rs.getString("nombrerubro"));
                    rubro.setIdcta(rs.getString("idcta"));
                    rubro.setCuentaventa(rs.getString("nombrecuenta"));
                    System.out.println(rs.getString("nombrecuenta"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return rubro;
    }

    public rubro buscarIdCtaCompra(int id) throws SQLException {
        rubro rubro = new rubro();
        rubro.setCodigo(0);
        rubro.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select rubros.codigo,rubros.nombre as nombrerubro, "
                    + "plan.nombre as nombrecuenta,rubros.idctamercaderia "
                    + "from rubros "
                    + "left join plan "
                    + "on plan.codigo=rubros.idctamercaderia "
                    + " where rubros.codigo = ? "
                    + "order by rubros.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rubro.setCodigo(rs.getInt("codigo"));
                    rubro.setNombre(rs.getString("nombrerubro"));
                    rubro.setIdctamercaderia(rs.getString("idctamercaderia"));
                    rubro.setCtacompra(rs.getString("nombrecuenta"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return rubro;
    }

    public rubro buscarIdCtaCosto(int id) throws SQLException {
        rubro rubro = new rubro();
        rubro.setCodigo(0);
        rubro.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select rubros.codigo,rubros.nombre as nombrerubro, "
                    + "plan.nombre as nombrecuenta,rubros.idctacosto "
                    + "from rubros "
                    + "left join plan "
                    + "on plan.codigo=rubros.idctacosto "
                    + " where rubros.codigo = ? "
                    + "order by rubros.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rubro.setCodigo(rs.getInt("codigo"));
                    rubro.setNombre(rs.getString("nombrerubro"));
                    rubro.setIdctacosto(rs.getString("idctacosto"));
                    rubro.setCuentacosto(rs.getString("nombrecuenta"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return rubro;
    }

}

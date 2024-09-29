/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Usuario
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Conexion.Conexion;
import Modelo.plan;
import Modelo.rubro_compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Webmaster
 */
public class rubro_compraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<rubro_compra> todos() throws SQLException {
        ArrayList<rubro_compra> lista = new ArrayList<rubro_compra>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT rubro_compras.codigo,rubro_compras.nombre,rubro_compras.idcta,plan.nombre AS nombrecuenta "
                + " FROM rubro_compras "
                + "LEFT JOIN plan "
                + "ON plan.codigo=rubro_compras.idcta "
                + " ORDER BY rubro_compras.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rubro_compra r = new rubro_compra();
                plan plan = new plan();
                r.setIdcta(plan);
                r.setCodigo(rs.getInt("codigo"));
                r.setNombre(rs.getString("nombre"));
                r.getIdcta().setCodigo(rs.getString("idcta"));
                r.getIdcta().setNombre(rs.getString("nombrecuenta"));
                lista.add(r);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public rubro_compra buscarRubroBanco(int codrubro) throws SQLException {
        rubro_compra r = new rubro_compra();
        plan plan = new plan();
        r.setCodigo(0);
        r.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String cSql = "SELECT rubro_compras.codigo,rubro_compras.nombre,rubro_compras.idcta,plan.nombre AS nombrecuenta "
                    + " from rubro_compras "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=rubro_compras.idcta "
                    + " where rubro_compras.codigo = ? "
                    + " order by rubro_compras.codigo ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(cSql)) {
                ps.setInt(1, codrubro);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    r.setIdcta(plan);
                    r.setCodigo(rs.getInt("codigo"));
                    r.setNombre(rs.getString("nombre"));
                    r.getIdcta().setCodigo(rs.getString("idcta"));
                    r.getIdcta().setNombre(rs.getString("nombrecuenta"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return r;
    }

    public rubro_compra insertarubrobanco(rubro_compra ru) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("INSERT INTO rubro_compras (nombre,idcta) VALUES (?,?)");
        ps.setString(1, ru.getNombre());
        ps.setString(2, ru.getIdcta().getCodigo());
        ps.executeUpdate();
        st.close();
        ps.close();
        return ru;
    }

    public boolean actualizarrubrobanco(rubro_compra ru) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE rubro_compras SET nombre=?,idcta=? WHERE codigo=" + ru.getCodigo());
        ps.setString(1, ru.getNombre());
        ps.setString(2, ru.getIdcta().getCodigo());
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean delrubrobanco(int cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM rubro_compras WHERE codigo=?");
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

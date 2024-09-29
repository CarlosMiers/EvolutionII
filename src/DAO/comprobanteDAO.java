/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
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
public class comprobanteDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<comprobante> todos() throws SQLException {
        ArrayList<comprobante> lista = new ArrayList<comprobante>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM comprobantes "
                + " ORDER BY comprobantes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comprobante pa = new comprobante();
                pa.setCodigo(rs.getInt("codigo"));
                pa.setNombre(rs.getString("nombre"));
                pa.setTasainteres(rs.getBigDecimal("tasainteres"));
                pa.setTasainteresmora(rs.getBigDecimal("tasainteresmora"));
                lista.add(pa);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public ArrayList<comprobante> todosxtipo(int ntipo) throws SQLException {
        ArrayList<comprobante> lista = new ArrayList<comprobante>();
        con = new Conexion();
        st = con.conectar();
        String sql = "SELECT *  "
                + " FROM comprobantes "
                + " WHERE comprobantes.tipo=?"
                + " ORDER BY comprobantes.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ntipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comprobante pa = new comprobante();
                pa.setCodigo(rs.getInt("codigo"));
                pa.setNombre(rs.getString("nombre"));
                pa.setTasainteres(rs.getBigDecimal("tasainteres"));
                pa.setTasainteresmora(rs.getBigDecimal("tasainteresmora"));
                lista.add(pa);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public comprobante buscarIdxtipo(int id, int ntipo) throws SQLException {

        comprobante c = new comprobante();
        c.setCodigo(0);
        c.setNombre("");
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "select *  "
                    + "from comprobantes "
                    + "where comprobantes.codigo = ? and comprobantes.tipo=? "
                    + " order by comprobantes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, ntipo);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    c.setCodigo(rs.getInt("codigo"));
                    c.setNombre(rs.getString("nombre"));
                    c.setTasainteres(rs.getBigDecimal("tasainteres"));
                    c.setTasainteresmora(rs.getBigDecimal("interesmora"));
                }
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        return c;
    }

    public comprobante buscarId(int id) throws SQLException {

        comprobante c = new comprobante();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        
        try {
            String sql = "select * "
                    + " from comprobantes "
                    + "where comprobantes.codigo = ? "
                    + "order by comprobantes.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    c.setCodigo(rs.getInt("codigo"));
                    c.setNombre(rs.getString("nombre"));
                    c.setTasainteresmora(rs.getBigDecimal("tasainteresmora"));
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return c;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.moneda;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class monedaDAO {

    Conexion con = null;
    Statement st = null;

     public ArrayList<moneda> todos() throws SQLException {
        ArrayList<moneda> lista = new ArrayList<moneda>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        
        String sql = "SELECT * "
                + " FROM monedas "
                + " ORDER BY monedas.codigo ";
        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                moneda mo = new moneda();
                mo.setCodigo(rs.getInt("codigo"));
                mo.setNombre(rs.getString("nombre"));
                mo.setCompra(rs.getBigDecimal("compra"));
                mo.setVenta(rs.getBigDecimal("venta"));
                mo.setEtiqueta(rs.getString("etiqueta"));
                lista.add(mo);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
            conn.close();
        }
        st.close();
        conn.close();
        return lista;
    }

    
    public moneda buscarId(int id) throws SQLException {
     moneda moneda = new moneda();
        moneda.setCodigo(0);
        moneda.setNombre("");
        moneda.setCompra(BigDecimal.ZERO);
        moneda.setVenta(BigDecimal.ZERO);
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {
            String sql = "select * "
                    + "from monedas "
                    + "where monedas.codigo = ? "
                    + "order by monedas.codigo ";
            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    moneda.setCodigo(rs.getInt("codigo"));
                    moneda.setNombre(rs.getString("nombre"));
                    moneda.setVenta(rs.getBigDecimal("venta"));
                    moneda.setCompra(rs.getBigDecimal("compra"));
                    moneda.setEtiqueta(rs.getString("etiqueta"));
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return moneda;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_ajuste_mercaderia;
import Modelo.producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class detalle_ajuste_mercaderiaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_ajuste_mercaderia> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_ajuste_mercaderia> lista = new ArrayList<detalle_ajuste_mercaderia>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT detalle_ajuste_mercaderias.dreferencia,detalle_ajuste_mercaderias.producto,"
                    + "detalle_ajuste_mercaderias.cantidad,detalle_ajuste_mercaderias.costo,productos.nombre AS nombreproducto  "
                    + "FROM detalle_ajuste_mercaderias  "
                    + "INNER JOIN productos "
                    + "ON productos.codigo=detalle_ajuste_mercaderias.producto "
                    + "WHERE detalle_ajuste_mercaderias.dreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_ajuste_mercaderia dt = new detalle_ajuste_mercaderia();
                    producto prod = new producto();
                    dt.setProducto(prod);
                    dt.getProducto().setCodigo(rs.getString("producto"));
                    dt.getProducto().setNombre(rs.getString("nombreproducto"));
                    dt.setDreferencia(rs.getString("dreferencia"));
                    dt.setCantidad(rs.getBigDecimal("cantidad"));
                    dt.setCosto(rs.getBigDecimal("costo"));
                    lista.add(dt);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalleEntrada(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_ajuste_mercaderias WHERE dreferencia=?");
        ps.setString(1, referencia);
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

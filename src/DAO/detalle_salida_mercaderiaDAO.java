/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_salida_mercaderia;
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
public class detalle_salida_mercaderiaDAO {
    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_salida_mercaderia> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_salida_mercaderia> lista = new ArrayList<detalle_salida_mercaderia>();
        con = new Conexion();
        st = con.conectar();
        try {
            String sql = "SELECT detalle_salida_mercaderias.dreferencia,detalle_salida_mercaderias.producto,"
                    + "detalle_salida_mercaderias.cantidad,detalle_salida_mercaderias.costo,productos.nombre AS nombreproducto  "
                    + "FROM detalle_salida_mercaderias  "
                    + "INNER JOIN productos "
                    + "ON productos.codigo=detalle_salida_mercaderias.producto "
                    + "WHERE detalle_salida_mercaderias.dreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_salida_mercaderia dt = new detalle_salida_mercaderia();
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

    public boolean borrarDetalleSalida(String referencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_salida_mercaderias WHERE dreferencia=?");
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

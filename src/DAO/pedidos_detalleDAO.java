/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.pedidos_detalle;
import Modelo.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_Server
 */
public class pedidos_detalleDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<pedidos_detalle> MostrarDetalle(Double id) throws SQLException {
        ArrayList<pedidos_detalle> lista = new ArrayList<pedidos_detalle>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT idpedido,item,producto,cantidad,pedidos_detalle.costo,total,"
                    + "productos.nombre AS nombreproducto "
                    + " FROM pedidos_detalle "
                    + "INNER JOIN productos "
                    + "ON productos.codigo=pedidos_detalle.producto "
                    + "WHERE pedidos_detalle.idpedido= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    pedidos_detalle dt = new pedidos_detalle();
                    producto prod = new producto();
                    dt.setProducto(prod);
                    dt.getProducto().setCodigo(rs.getString("producto"));
                    dt.getProducto().setNombre(rs.getString("nombreproducto"));
                    dt.setIdpedido(rs.getDouble("idpedido"));
                    dt.setCantidad(rs.getDouble("cantidad"));
                    dt.setCosto(rs.getDouble("costo"));
                    dt.setTotal(rs.getDouble("total"));
                    lista.add(dt);
                }
                rs.close();
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean EliminarDetallePreventa(Double cod) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM pedidos_detalle WHERE idpedido=?");
        ps.setDouble(1, cod);
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

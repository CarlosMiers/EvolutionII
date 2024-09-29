/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.lista_precios;
import Modelo.producto;
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
public class lista_preciosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<lista_precios> todos(String id) throws SQLException {
        ArrayList<lista_precios> lista = new ArrayList<lista_precios>();
        con = new Conexion();
        st = con.conectar();

        String sql = "SELECT lista_precio_cantidad.producto,productos.nombre AS nombreproducto,lista_precio_cantidad.itemid,"
                + "lista_precio_cantidad.limitecantidad,lista_precio_cantidad.precioventa,lista_precio_cantidad.utilidad "
                + "FROM lista_precio_cantidad "
                + "LEFT JOIN productos "
                + "ON productos.codigo=lista_precio_cantidad.producto "
                + " WHERE lista_precio_cantidad.producto=?"
                + " ORDER BY lista_precio_cantidad.producto,lista_precio_cantidad.limitecantidad ";

        try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista_precios lis = new lista_precios();
                producto p = new producto();
                lis.setProducto(p);
                lis.getProducto().setCodigo(rs.getString("producto"));
                lis.getProducto().setNombre(rs.getString("nombreproducto"));
                lis.setLimitecantidad(rs.getDouble("limitecantidad"));
                lis.setUtilidad(rs.getDouble("utilidad"));
                lis.setPrecioventa(rs.getDouble("precioventa"));
                lis.setItemid(rs.getDouble("itemid"));
                lista.add(lis);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
            st.close();
        }
        return lista;
    }

    public lista_precios insertarPrecios(lista_precios lp) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("INSERT INTO lista_precio_cantidad (producto,limitecantidad,precioventa,utilidad) VALUES (?,?,?,?)");
        ps.setString(1, lp.getProducto().getCodigo());
        ps.setDouble(2, lp.getLimitecantidad());
        ps.setDouble(3, lp.getPrecioventa());
        ps.setDouble(4, lp.getUtilidad());
        ps.executeUpdate();
        ps.close();
        st.close();
        return lp;
    }

    public boolean borrarItem(Double nItem) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM lista_precio_cantidad WHERE itemid=?");
        ps.setDouble(1, nItem);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public lista_precios actualizarItemPrecio(lista_precios ocr) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection cnn = st.getConnection();
        boolean guardado = false;
        int id = 0;
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE lista_precio_cantidad "
                + " SET limitecantidad=?,precioventa=?,utilidad=?"
                + " WHERE itemid=" + ocr.getItemid());
        ps.setDouble(1, ocr.getLimitecantidad());
        ps.setDouble(2, ocr.getPrecioventa());
        ps.setDouble(3, ocr.getUtilidad());
        try {
            ps.executeUpdate();
            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ACTUALIZAR PRECIOS -> " + ex.getLocalizedMessage());
        }

        st.close();
        ps.close();
        cnn.close();
        return ocr;
    }

}

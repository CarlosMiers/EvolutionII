/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_venta;
import Modelo.plan;
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
public class detalle_ventaDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_venta> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_venta> lista = new ArrayList<detalle_venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT dreferencia,codprod,cantidad,comentario,prcosto,precio,"
                    + "monto,impiva,porcentaje,detalle_item,comision_venta,porcentaje_descuento,suc,"
                    + "productos.nombre AS nombreproducto "
                    + "FROM detalle_ventas "
                    + "INNER JOIN productos "
                    + "ON productos.codigo=detalle_ventas.codprod "
                    + "WHERE detalle_ventas.dreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_venta dt = new detalle_venta();
                    producto prod = new producto();
                    dt.setCodprod(prod);
                    dt.getCodprod().setCodigo(rs.getString("codprod"));
                    dt.getCodprod().setNombre(rs.getString("nombreproducto"));
                    dt.setDreferencia(rs.getString("dreferencia"));
                    dt.setCantidad(rs.getBigDecimal("cantidad"));
                    dt.setComentario(rs.getString("comentario"));
                    dt.setPrcosto(rs.getBigDecimal("prcosto"));
                    dt.setPrecio(rs.getBigDecimal("precio"));
                    dt.setMonto(rs.getBigDecimal("monto"));
                    dt.setImpiva(rs.getBigDecimal("impiva"));
                    dt.setPorcentaje(rs.getBigDecimal("porcentaje"));
                    dt.setDetalle_item(rs.getString("detalle_item"));
                    dt.setComision_venta(rs.getBigDecimal("comision_venta"));
                    dt.setSuc(rs.getInt("suc"));
                    lista.add(dt);
                }
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalleVenta(String creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_ventas WHERE dreferencia=?");
        ps.setString(1, creferencia);
        int rowsUpdated = ps.executeUpdate();
        st.close();
        ps.close();
        conn.close();
        if (rowsUpdated > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<detalle_venta> MostrarDetalleCobro(String id) throws SQLException {
        ArrayList<detalle_venta> lista = new ArrayList<detalle_venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT detalle_ventas.item,codprod,productos.nombre AS nombreproducto,"
                    + "productos.ctadebe,plan.nombre AS  nombreplan,"
                    + "detalle_ventas.monto,"
                    + "detalle_ventas.supago,"
                    + "COALESCE(j.totalpagos,0) AS totalpagos "
                    + "FROM detalle_ventas "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=detalle_ventas.codprod "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=productos.ctadebe "
                    + "LEFT JOIN (SELECT cobrositem.iditem, SUM(cobrositem.pago) AS totalpagos "
                    + "FROM cobrositem "
                    + "GROUP BY cobrositem.iditem) j ON j.iditem=detalle_ventas.item "
                    + "WHERE detalle_ventas.dreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_venta dt = new detalle_venta();
                    producto prod = new producto();
                    plan pl = new plan();
                    dt.setCodprod(prod);
                    dt.setCtadebe(pl);
                    dt.getCodprod().setCodigo(rs.getString("codprod"));
                    dt.getCodprod().setNombre(rs.getString("nombreproducto"));
                    dt.getCtadebe().setCodigo(rs.getString("ctadebe"));
                    dt.getCtadebe().setNombre(rs.getString("nombreplan"));
                    dt.setMonto(rs.getBigDecimal("monto"));
                    dt.setSupago(rs.getDouble("supago"));
                    dt.setItem(rs.getDouble("item"));
                    dt.setSaldo(rs.getDouble("monto") - rs.getDouble("totalpagos"));
                    lista.add(dt);
                }
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public ArrayList<detalle_venta> MostrarDetalleCobroUpdate(String id) throws SQLException {
        ArrayList<detalle_venta> lista = new ArrayList<detalle_venta>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();

        try {

            String sql = "SELECT detalle_ventas.item,codprod,productos.nombre AS nombreproducto,"
                    + "productos.ctadebe,plan.nombre AS  nombreplan,"
                    + "detalle_ventas.monto,"
                    + "detalle_ventas.supago,"
                    + "cobrositem.pago AS totalpagos "
                    + "FROM detalle_ventas "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=detalle_ventas.codprod "
                    + "LEFT JOIN plan "
                    + "ON plan.codigo=productos.ctadebe "
                    + "LEFT JOIN  cobrositem "
                    + "ON cobrositem.iditem=detalle_ventas.item "
                    + "WHERE cobrositem.idpagos=? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_venta dt = new detalle_venta();
                    producto prod = new producto();
                    plan pl = new plan();
                    dt.setCodprod(prod);
                    dt.setCtadebe(pl);
                    dt.getCodprod().setCodigo(rs.getString("codprod"));
                    dt.getCodprod().setNombre(rs.getString("nombreproducto"));
                    dt.getCtadebe().setCodigo(rs.getString("ctadebe"));
                    dt.getCtadebe().setNombre(rs.getString("nombreplan"));
                    dt.setMonto(rs.getBigDecimal("monto"));
                    dt.setSupago(rs.getDouble("totalpagos"));
                    dt.setItem(rs.getDouble("item"));
                    dt.setSaldo(rs.getDouble("monto"));
                    lista.add(dt);
                }
                ps.close();
                st.close();
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

}

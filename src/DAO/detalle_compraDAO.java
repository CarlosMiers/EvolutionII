/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_compra;
import Modelo.producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_compraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_compra> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_compra> lista = new ArrayList<detalle_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT detalle_compras.dreferencia,detalle_compras.codprod,"
                    +"detalle_compras.cantidad,detalle_compras.prcosto,detalle_compras.monto,"
                    + "detalle_compras.impiva,detalle_compras.porcentaje,detalle_compras.centro,"
                    + "detalle_compras.utilidad1,detalle_compras.utilidad2,"
                    + "detalle_compras.precio1,detalle_compras.precio2,detalle_compras.precioviejo1,"
                    + "detalle_compras.precioviejo2,detalle_compras.suc,detalle_compras.costo_moneda_local,"
                    + "detalle_compras.participacion_costo,"
                    + "detalle_compras.participacion_gastos,detalle_compras.costo_real,"
                    + "productos.nombre AS nombreproducto  "
                    + "FROM detalle_compras "
                    + "INNER JOIN productos "
                    + "ON productos.codigo=detalle_compras.codprod  "
                    + "WHERE detalle_compras.dreferencia= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_compra dc = new detalle_compra();
                    producto prod = new producto();
                    dc.setCodprod(prod);
                    dc.getCodprod().setCodigo(rs.getString("codprod"));
                    dc.getCodprod().setNombre(rs.getString("nombreproducto"));
                    dc.setDreferencia(rs.getString("dreferencia"));
                    dc.setCantidad(rs.getBigDecimal("cantidad"));
                    dc.setPrcosto(rs.getBigDecimal("prcosto"));
                    dc.setMonto(rs.getBigDecimal("monto"));
                    dc.setImpiva(rs.getBigDecimal("impiva"));
                    dc.setPorcentaje(rs.getBigDecimal("porcentaje"));
                    dc.setCentro(rs.getString("centro"));
                    dc.setUtilidad1(rs.getDouble("utilidad1"));
                    dc.setUtilidad2(rs.getDouble("utilidad2"));
                    dc.setPrecioviejo1(rs.getDouble("precioviejo1"));
                    dc.setPrecioviejo2(rs.getDouble("precioviejo2"));
                    dc.setPrecio1(rs.getDouble("precio1"));
                    dc.setPrecio2(rs.getDouble("precio2"));
                    dc.setSuc(rs.getInt("suc"));
                    dc.setCosto_moneda_local(rs.getBigDecimal("costo_moneda_local"));
                    dc.setParticipacion_costo(rs.getBigDecimal("participacion_costo"));
                    dc.setParticipacion_gastos(rs.getBigDecimal("participacion_gastos"));
                    dc.setCosto_real(rs.getBigDecimal("costo_real"));
                    System.out.println("PRECIO MINORISTA "+dc.getPrecio1());
                    System.out.println("PRECIO MAYORISTA "+dc.getPrecio2());
                    lista.add(dc);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }
    
    public boolean borrarDetallecompra(String creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_compras WHERE dreferencia=?");
        ps.setString(1, creferencia);
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

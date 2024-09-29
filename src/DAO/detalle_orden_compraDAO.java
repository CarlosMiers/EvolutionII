/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_orden_compra;
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
public class detalle_orden_compraDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_orden_compra> MostrarDetalle(double id) throws SQLException {
        ArrayList<detalle_orden_compra> lista = new ArrayList<detalle_orden_compra>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT iddetalle,"
                    + "codprod,"
                    + "cantidad,"
                    + "precio,"
                    + "impuesto,"
                    + "importeiva,"
                    + "totalitem,"
                    +" productos.nombre AS nombreproducto "
                    + "FROM detalle_orden_compra "
                    + "LEFT JOIN productos "
                    + "ON productos.codigo=detalle_orden_compra.codprod "
                    + "WHERE detalle_orden_compra.iddetalle= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_orden_compra dc = new detalle_orden_compra();
                    producto prod = new producto();
                    dc.setCodprod(prod);
                    dc.setIddetalle(rs.getDouble("iddetalle"));
                    dc.getCodprod().setCodigo(rs.getString("codprod"));
                    dc.getCodprod().setNombre(rs.getString("nombreproducto"));
                    dc.setCantidad(rs.getDouble("cantidad"));
                    dc.setPrecio(rs.getDouble("precio"));
                    dc.setImporteiva(rs.getDouble("importeiva"));
                    dc.setImpuesto(rs.getDouble("impuesto"));
                    dc.setTotalitem(rs.getDouble("totalitem"));
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

    public boolean borrarDetallecompra(double creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_orden_compra WHERE iddetalle=?");
        ps.setDouble(1, creferencia);
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

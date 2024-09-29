/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_orden_trabajo;
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
public class detalle_orden_trabajoDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_orden_trabajo> MostrarDetalle(double id) throws SQLException {
        ArrayList<detalle_orden_trabajo> lista = new ArrayList<detalle_orden_trabajo>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT detalle_orden_trabajo.dnumero,detalle_orden_trabajo.item,"
                    + "detalle_orden_trabajo.codprod,detalle_orden_trabajo.cantidad,"
                    + "detalle_orden_trabajo.costo,detalle_orden_trabajo.disponible,"
                    + "detalle_orden_trabajo.total,"
                    + "detalle_orden_trabajo.potrero,"
                    + "productos.nombre as nombreproducto "
                    + " FROM detalle_orden_trabajo "
                    + " INNER JOIN productos "
                    + " ON productos.codigo=detalle_orden_trabajo.codprod "
                    + "WHERE detalle_orden_trabajo.dnumero=? "
                    + " order by detalle_orden_trabajo.item";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_orden_trabajo dc = new detalle_orden_trabajo();
                    producto prod = new producto();
                    dc.setCodprod(prod);
                    dc.getCodprod().setCodigo(rs.getString("codprod"));
                    dc.getCodprod().setNombre(rs.getString("nombreproducto"));
                    dc.setCantidad(rs.getDouble("cantidad"));
                    dc.setCosto(rs.getDouble("costo"));
                    dc.setDisponible(rs.getDouble("disponible"));
                    dc.setDnumero(rs.getDouble("dnumero"));
                    dc.setItem(rs.getInt("item"));
                    dc.setTotal(rs.getDouble("total"));
                    dc.setPotrero(rs.getString("potrero"));
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

    public boolean borrarDetalleMercaderiaOT(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("UPDATE detalle_orden_trabajo SET borrado=1 WHERE dnumero=?");
        ps.setDouble(1, id);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.detalle_asientos;
import Modelo.moneda;
import Modelo.plan;
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
public class detalle_asientosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_asientos> MostrarDetalle(Double id) throws SQLException {
        ArrayList<detalle_asientos> lista = new ArrayList<detalle_asientos>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "select detalle_asientos.asi_asient,detalle_asientos.asi_codigo,detalle_asientos.asi_numero,detalle_asientos.moneda,"
                    + "detalle_asientos.importe,detalle_asientos.cotizacion,detalle_asientos.asi_codigo,detalle_asientos.asi_descri,"
                    + "detalle_asientos.impdebe,detalle_asientos.imphaber,detalle_asientos.item,detalle_asientos.centro,"
                    + "detalle_asientos.ntipo,plan.nombre as nombrecuenta,monedas.nombre as nombremoneda "
                    + "FROM detalle_asientos "
                    + "inner join plan "
                    + "ON plan.codigo=detalle_asientos.asi_codigo "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=detalle_asientos.moneda "
                    + "WHERE detalle_asientos.asi_asient= ? "
                    + " ORDER BY detalle_asientos.asi_asient,detalle_asientos.item";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setDouble(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_asientos dt = new detalle_asientos();
                    plan pl = new plan();
                    moneda mn = new moneda();
                    dt.setAsi_codigo(pl);;
                    dt.setMoneda(mn);
                    dt.getAsi_codigo().setCodigo(rs.getString("asi_codigo"));
                    dt.getAsi_codigo().setNombre(rs.getString("nombrecuenta"));
                    dt.getMoneda().setCodigo(rs.getInt("moneda"));
                    dt.getMoneda().setNombre(rs.getString("nombremoneda"));
                    dt.setAsi_asient(rs.getDouble("asi_asient"));
                    dt.setAsi_descri(rs.getString("asi_descri"));
                    dt.setAsi_numero(rs.getString("asi_numero"));
                    dt.setCotizacion(rs.getDouble("cotizacion"));
                    dt.setImporte(rs.getDouble("importe"));
                    dt.setImpdebe(rs.getDouble("impdebe"));
                    dt.setImphaber(rs.getDouble("imphaber"));
                    dt.setItem(rs.getInt("item"));
                    dt.setCentro(rs.getInt("centro"));
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

    public boolean borrarDetalleAsiento(Double id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        PreparedStatement ps = null;
        ps = st.getConnection().prepareStatement("DELETE FROM detalle_asientos WHERE asi_asient=?");
        ps.setDouble(1, id);
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
}

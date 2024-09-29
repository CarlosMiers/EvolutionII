/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.comprobante;
import Modelo.detalle_rescision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_rescisionDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_rescision> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_rescision> lista = new ArrayList<detalle_rescision>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT iddetalle,idfactura,nrofactura,emision,comprobante,"
                    + "pago,numerocuota,cuota,vence,detalle_rescisiones.moneda,"
                    + "comprobantes.nombre AS nombrecomprobante,"
                    + "detalle_rescisiones.saldo "
                    + " FROM detalle_rescisiones "
                    + " LEFT JOIN comprobantes "
                    + " ON comprobantes.codigo=detalle_rescisiones.comprobante "
                    + " WHERE detalle_rescisiones.iddetalle= ?  ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_rescision dt = new detalle_rescision();
                    comprobante cm = new comprobante();
                    dt.setComprobante(cm);
                    dt.setIddetalle(rs.getString("iddetalle"));
                    dt.setIdfactura(rs.getString("idfactura"));
                    dt.setNrofactura(rs.getDouble("nrofactura"));
                    dt.setEmision(rs.getDate("emision"));
                    dt.getComprobante().setCodigo(rs.getInt("comprobante"));
                    dt.getComprobante().setNombre(rs.getString("nombrecomprobante"));
                    dt.setSaldo(rs.getDouble("saldo"));
                    dt.setPago(rs.getDouble("pago"));
                    dt.setNumerocuota(rs.getInt("numerocuota"));
                    dt.setCuota(rs.getInt("cuota"));
                    dt.setVence(rs.getDate("vence"));
                    dt.setMoneda(rs.getInt("moneda"));
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

    public boolean borrarDetallePago(String id) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_rescisiones WHERE iddetalle=?");
        ps.setString(1, id);
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

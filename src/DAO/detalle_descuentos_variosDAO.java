/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.cliente;
import Modelo.detalle_descuentos_varios;
import Modelo.detalle_venta;
import Modelo.producto;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class detalle_descuentos_variosDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<detalle_descuentos_varios> MostrarDetalle(String id) throws SQLException {
        ArrayList<detalle_descuentos_varios> lista = new ArrayList<detalle_descuentos_varios>();
        con = new Conexion();
        st = con.conectar();
        try {

            String sql = "SELECT id_detalle,socio,descuento,"
                    + "clientes.nombre AS nombrecliente "
                    + "FROM detalle_descuentos_varios "
                    + "INNER JOIN clientes "
                    + "ON clientes.codigo=detalle_descuentos_varios.socio "
                    + " WHERE detalle_descuentos_varios.id_detalle= ? ";

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_descuentos_varios dtv = new detalle_descuentos_varios();
                    cliente cli = new cliente();
                    
                    
                    dtv.setSocio(cli);
                    dtv.getSocio().setCodigo(rs.getInt("socio"));
                    dtv.getSocio().setNombre(rs.getString("nombrecliente"));
                    dtv.setId_detalle(rs.getString("id_detalle"));
                    dtv.setDescuento(rs.getBigDecimal("descuento"));
                    lista.add(dtv);
                }
                ps.close();
                st.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        return lista;
    }

    public boolean borrarDetalleVenta(String creferencia) throws SQLException {
        con = new Conexion();
        st = con.conectar();
        PreparedStatement ps = null;

        ps = st.getConnection().prepareStatement("DELETE FROM detalle_descuentos_varios WHERE id_detalle=?");
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

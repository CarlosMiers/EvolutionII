/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOBACKUP;

import DAO.*;
import Conexion.Conexion;
import Conexion.ConexionEspejo;
import Modelo.detalle_venta;
import Modelo.producto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author SERVIDOR
 */
public class Importadetalle_ventaDAO {

    ConexionEspejo conEsp = null;
    Statement stEspejo = null;
    String ip2 = "45.180.183.178";
    String ip3 = "45.180.183.152";

    public ArrayList<detalle_venta> MostrarDetalle(String id, Integer nsuc) throws SQLException {
        ArrayList<detalle_venta> lista = new ArrayList<detalle_venta>();
        conEsp = new ConexionEspejo();
        if (nsuc == 2) {
            stEspejo = conEsp.conectarEspejo(ip2);
        } else if (nsuc == 3) {
            stEspejo = conEsp.conectarEspejo(ip3);
        }

        Connection conne = stEspejo.getConnection();
        try {
            String sql = "SELECT dreferencia,codprod,cantidad,comentario,prcosto,precio,"
                    + "monto,impiva,porcentaje,detalle_item,comision_venta,porcentaje_descuento,suc "
                    + "FROM detalle_ventas "
                    + "INNER JOIN cabecera_ventas "
                    + "ON cabecera_ventas.creferencia=detalle_ventas.dreferencia "
                    + "WHERE cabecera_ventas.creferencia= ?  ORDER BY cabecera_ventas.creferencia";

            try (PreparedStatement ps = stEspejo.getConnection().prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    detalle_venta dt = new detalle_venta();
                    producto prod = new producto();
                    dt.setCodprod(prod);
                    dt.getCodprod().setCodigo(rs.getString("codprod"));
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
                stEspejo.close();
                conne.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> detalle de venta origen " + ex.getLocalizedMessage());
        }
        return lista;
    }


}

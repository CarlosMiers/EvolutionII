/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.Conexion;
import Modelo.centro_costo_reporte;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class centro_costo_reporteDAO {

    Conexion con = null;
    Statement st = null;

    public ArrayList<centro_costo_reporte> MostrarxReferencia(Date fechaini, Date fechafin, int ncentro, int nmoneda) throws SQLException {
        ArrayList<centro_costo_reporte> lista = new ArrayList<centro_costo_reporte>();
        con = new Conexion();
        st = con.conectar();
        Connection conn = st.getConnection();
        try {

            String sql = "SELECT gastos_compras.formatofactura,gastos_compras.fecha,"
                    + "SUM(detalle_centro_costo.monto) AS monto,detalle_centro_costo.idcentro,"
                    + "detalle_centro_costo.tipo,centro_costo.nombre AS nombrecentro,"
                    + "proveedores.nombre AS nombreproveedor,"
                    + "monedas.nombre AS nombremoneda,gastos_compras.moneda,"
                    + "gastos_compras.idcta,plan.nombre as nombrecuenta "
                    + "FROM gastos_compras "
                    + "LEFT JOIN proveedores "
                    + "ON proveedores.codigo=gastos_compras.proveedor "
                    +" LEFT JOIN plan "
                    +" ON plan.codigo=gastos_compras.idcta "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=gastos_compras.moneda "
                    + "INNER JOIN detalle_centro_costo "
                    + "ON detalle_centro_costo.dreferencia=gastos_compras.creferencia "
                    + "LEFT JOIN centro_costo "
                    + "ON centro_costo.codigo=detalle_centro_costo.idcentro "
                    + "WHERE gastos_compras.fecha "
                    + "BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "AND gastos_compras.moneda=" + nmoneda
                    + " AND IF(" + ncentro + "<>0,detalle_centro_costo.idcentro=" + ncentro + ",TRUE)"
                    +" GROUP BY idcta,idcentro "
                    + " UNION "
                    + "SELECT cabecera_ventas.formatofactura,cabecera_ventas.fecha,"
                    + "SUM(detalle_centro_costo.monto) AS monto,detalle_centro_costo.idcentro,"
                    + "detalle_centro_costo.tipo,centro_costo.nombre AS nombrecentro,"
                    + "clientes.nombre AS nombreproveedor,"
                    + "monedas.nombre AS nombremoneda,cabecera_ventas.moneda, "
                    +" rubros.idcta,plan.nombre as nombrecuenta "
                    + "FROM cabecera_ventas "
                    + "LEFT JOIN clientes "
                    + "ON clientes.codigo=cabecera_ventas.cliente "
                    +" LEFT JOIN detalle_ventas "
                    +" ON detalle_ventas.dreferencia=cabecera_ventas.creferencia "
                    +" LEFT JOIN productos "
                    +" ON productos.codigo=detalle_ventas.codprod "
                    +" LEFT JOIN rubros "
                    +" ON rubros.codigo=productos.rubro "
                    +" LEFT JOIN plan "
                    +" ON plan.codigo=rubros.idcta "
                    + "LEFT JOIN monedas "
                    + "ON monedas.codigo=cabecera_ventas.moneda "
                    + "INNER JOIN detalle_centro_costo "
                    + "ON detalle_centro_costo.dreferencia=cabecera_ventas.creferencia "
                    + "LEFT JOIN centro_costo "
                    + "ON centro_costo.codigo=detalle_centro_costo.idcentro "
                    + "WHERE cabecera_ventas.fecha "
                    + "BETWEEN '" + fechaini + "' AND '" + fechafin + "' "
                    + "AND cabecera_ventas.moneda=" + nmoneda
                    + " AND IF(" + ncentro + "<>0,detalle_centro_costo.idcentro=" + ncentro + ",TRUE)"
                    +" GROUP BY idcta,idcentro "
                    + " ORDER BY tipo,idcentro ";

            System.out.println(sql);

            try (PreparedStatement ps = st.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    centro_costo_reporte ot = new centro_costo_reporte();
                    ot.setFormatofactura(rs.getString("formatofactura"));
                    ot.setFecha(rs.getDate("fecha"));
                    ot.setMonto(rs.getDouble("monto"));
                    ot.setIdcentro(rs.getInt("idcentro"));
                    ot.setMoneda(rs.getInt("moneda"));
                    ot.setNombremoneda(rs.getString("nombremoneda"));
                    ot.setNombrecentro(rs.getString("nombrecentro"));
                    ot.setNombreproveedor(rs.getString("nombreproveedor"));
                    ot.setTipo(rs.getInt("tipo"));
                    lista.add(ot);
                }
                ps.close();
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("--> " + ex.getLocalizedMessage());
        }
        st.close();
        conn.close();
        return lista;
    }

}
